package simulation;

import OSPAnimator.AnimImageItem;
import OSPAnimator.AnimQueue;
import OSPAnimator.AnimShape;
import OSPAnimator.AnimShapeItem;
import OSPDataStruct.SimQueue;
import OSPStat.Stat;
import OSPStat.WStat;
import agents.agentvyroby.*;
import agents.agentokolia.*;
import agents.agentpresunov.*;
import agents.agentmodelu.*;
import agents.agentb.*;
import agents.agenta.*;
import agents.agentc.*;
import furniture.Furniture;
import furniture.Furnitures;
import worker.Worker;
import worker.Workers;
import workingplace.WorkingPlaces;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;


public class MySimulation extends OSPABA.Simulation {
    private SimQueue<Furniture> storage;
    private WStat dlzkaStorageStat;
    private PriorityQueue<Furniture> queueSkladaniaPriority;
    private PriorityQueue<Furniture> queueMoreniaPriority;
    private PriorityQueue<Furniture> queueKovaniaPriority;


    private int orderId;
    private Random seedGenerator;

    private Stat priemernaDobaObjednavkyVSystemeStat;
    private Stat priemernaDobaTovaruVSystemeStat;
    private Stat workloadA;
    private Stat workloadB;
    private Stat workloadC;

    private Stat unstartedOrdersStat;

    private Stat someProcessTimeStat;

    private AnimQueue animQueue;

    private double averageOrderTime;

    private FileWriter csvWriter;

    private int A;
    private int B;
    private int C;
    private int WP;

    public MySimulation() {
        init();
    }

    public void settings(int A, int B, int C, int WP) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.WP = WP;
    }

    @Override
    public void prepareSimulation() {
        super.prepareSimulation();
        // Create global statistcis
        this.seedGenerator = new Random(1234567987L); // 1234567987L //12345679101L

        //dlzkaStorageStat = new WStat(this);
        //storage = new SimQueue<>(dlzkaStorageStat);

        priemernaDobaObjednavkyVSystemeStat = new Stat();
        priemernaDobaTovaruVSystemeStat = new Stat();
        someProcessTimeStat = new Stat();
        workloadA = new Stat();
        workloadB = new Stat();
        workloadC = new Stat();
        unstartedOrdersStat = new Stat();

        this.startAnimation();

        try {
            csvWriter = new FileWriter("results.csv", true);
            // napíš hlavičku
            //csvWriter.append("replications;A;B;C;WP;orderTimeMean;workloadA;workloadB;workloadC;unstartedOrders\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAnimation() {
        if (this.animatorExists()) {
            AnimImageItem storageItem = new AnimImageItem(Data.storageImg, 400, 1000);
            storageItem.setPosition(new Point(1300, 0));
            animator().register(storageItem);

            this.animQueue = new AnimQueue(animator(), 1000, 20, 0, 20, 0);

            AnimShapeItem queueItem = new AnimShapeItem(AnimShape.RECTANGLE, 1050, Data.FURNITURE_SIZE);
            queueItem.setPosition(new Point(0, 20));
            queueItem.setColor(Color.LIGHT_GRAY);
            animator().register(queueItem);
            for (Furniture f : storage) {
                f.loadAnimItems(this);
                animQueue.insert(f.getAnimImageItem());
            }
            animQueue.setVisible(true);

            for (Furniture f : _agentVyroby.getFurnitureList()) {
                f.loadAnimItems(this);
            }

            getWorkingPlaces().loadAnimItems(this);
            getWorkersA().loadAnimItems(this);
            getWorkersB().loadAnimItems(this);
            getWorkersC().loadAnimItems(this);
        }
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Reset entities, queues, local statistics, etc...
        agentA().setNumberOfWorkingPlaces(WP);
        agentVyroby().setSizes(A, B, C);
        averageOrderTime = 0;

        this.orderId = 0;

        this.dlzkaStorageStat = new WStat(this);
        this.storage = new SimQueue<>(dlzkaStorageStat);
        queueSkladaniaPriority = new PriorityQueue<>();
        queueMoreniaPriority = new PriorityQueue<>();
        queueKovaniaPriority = new PriorityQueue<>();

    }

    @Override
    public void replicationFinished() {
        for (Worker w : this.getWorkersC().getWorkers()) {
            w.getUtilityWStat().updateAfterReplication();
        }
        for (Worker w : this.getWorkersB().getWorkers()) {
            w.getUtilityWStat().updateAfterReplication();
        }
        for (Worker w : this.getWorkersA().getWorkers()) {
            w.getUtilityWStat().updateAfterReplication();
        }
        // Collect local statistics into global, update UI, etc...
        super.replicationFinished();
        priemernaDobaObjednavkyVSystemeStat.addSample(agentVyroby().getOrderTimeInSystemStat().mean());
        priemernaDobaTovaruVSystemeStat.addSample(agentVyroby().getTovarTimeInSystemStat().mean());
        workloadA.addSample(agentVyroby().getWorkersA().getAverageUtilization());
        workloadB.addSample(agentVyroby().getWorkersB().getAverageUtilization());
        workloadC.addSample(agentVyroby().getWorkersC().getAverageUtilization());
        unstartedOrdersStat.addSample(agentVyroby().getFinishedFurnitureList().getUnstartedFurnituresCount());
        averageOrderTime += agentVyroby().getOrderTimeInSystem() / agentVyroby().getFinishedOrderCount();

//        System.out.println("Priemerne vytazenie A" + agentVyroby().getWorkersA().getAverageUtilization());
//        System.out.println("Priemerne vytazenie B" + agentVyroby().getWorkersB().getAverageUtilization());
//        System.out.println("Priemerne vytazenie C" + agentVyroby().getWorkersC().getAverageUtilization());
//        System.out.println("Priemerne vytazenie pracoviska" + agentA().getWorkingPlaces().getAverageUtilization());
//
//        System.out.println("Celkovy pocet nedokoncenych objednávok: " + unstartedOrdersStat.mean());
//
//
//        System.out.println("Replikácia číslo " + currentReplication() + ". Celková priemerná doba objednávky v systéme: " + (priemernaDobaObjednavkyVSystemeStat.mean() / 60 / 60 / 1000));
//        System.out.println("Replikácia číslo " + currentReplication() + ". Celková priemerná doba tovaru v systéme: " + (priemernaDobaTovaruVSystemeStat.mean() / 60 / 60 / 1000));
//
//        System.out.println("Priemerna doba objednavky v systeme: " + (averageOrderTime / 60 / 60 / 1000));
    }

    @Override
    public void simulationFinished() {
        // Display simulation results
        super.simulationFinished();
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Celková priemerná doba objednávky v systéme: " + (priemernaDobaObjednavkyVSystemeStat.mean() / 60 / 60 / 1000));
        System.out.println("Celková priemerná doba tovaru v systéme: " + (priemernaDobaTovaruVSystemeStat.mean() / 60 / 60 / 1000));
        System.out.println("Celkový počet nedokoncenych objednávok: " + unstartedOrdersStat.mean());

        System.out.println("Workload A: " + workloadA.mean());
        System.out.println("Workload B: " + workloadB.mean());
        System.out.println("Workload C: " + workloadC.mean());
        if(replicationCount() > 1) {

            String result = String.format("%.2f<%.2f - %.2f>",
                    priemernaDobaObjednavkyVSystemeStat.mean() / 60 / 60 / 1000,
                    priemernaDobaObjednavkyVSystemeStat.confidenceInterval_95()[0] / 60 / 60 / 1000,
                    priemernaDobaObjednavkyVSystemeStat.confidenceInterval_95()[1] / 60 / 60 / 1000);

            try {
                csvWriter.append(String.format("%d;%d;%d;%d;%d;%s;%s;%s;%s;%s\n",
                        super.replicationCount(),
                        A,
                        B,
                        C,
                        WP,
                        result,
                        String.format("%.2f <%.2f - %.2f>", workloadA.mean() * 100, workloadA.confidenceInterval_95()[0] * 100, workloadA.confidenceInterval_95()[1] * 100),
                        String.format("%.2f <%.2f - %.2f>", workloadB.mean() * 100, workloadB.confidenceInterval_95()[0] * 100, workloadB.confidenceInterval_95()[1] * 100),
                        String.format("%.2f <%.2f - %.2f>", workloadC.mean() * 100, workloadC.confidenceInterval_95()[0] * 100, workloadC.confidenceInterval_95()[1] * 100),
                        String.format("%.2f <%.2f - %.2f>", unstartedOrdersStat.mean(), unstartedOrdersStat.confidenceInterval_95()[0], unstartedOrdersStat.confidenceInterval_95()[1])
                ));
                csvWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        setAgentModelu(new AgentModelu(Id.agentModelu, this, null));
        setAgentOkolia(new AgentOkolia(Id.agentOkolia, this, agentModelu()));
        setAgentVyroby(new AgentVyroby(Id.agentVyroby, this, agentModelu()));
        setAgentA(new AgentA(Id.agentA, this, agentVyroby()));
        setAgentPresunov(new AgentPresunov(Id.agentPresunov, this, agentVyroby()));
        setAgentB(new AgentB(Id.agentB, this, agentVyroby()));
        setAgentC(new AgentC(Id.agentC, this, agentVyroby()));
    }

    private AgentModelu _agentModelu;

    public AgentModelu agentModelu() {
        return _agentModelu;
    }

    public void setAgentModelu(AgentModelu agentModelu) {
        _agentModelu = agentModelu;
    }

    private AgentOkolia _agentOkolia;

    public AgentOkolia agentOkolia() {
        return _agentOkolia;
    }

    public void setAgentOkolia(AgentOkolia agentOkolia) {
        _agentOkolia = agentOkolia;
    }

    private AgentVyroby _agentVyroby;

    public AgentVyroby agentVyroby() {
        return _agentVyroby;
    }

    public void setAgentVyroby(AgentVyroby agentVyroby) {
        _agentVyroby = agentVyroby;
    }

    private AgentA _agentA;

    public AgentA agentA() {
        return _agentA;
    }

    public void setAgentA(AgentA agentA) {
        _agentA = agentA;
    }

    private AgentPresunov _agentPresunov;

    public AgentPresunov agentPresunov() {
        return _agentPresunov;
    }

    public void setAgentPresunov(AgentPresunov agentPresunov) {
        _agentPresunov = agentPresunov;
    }

    private AgentB _agentB;

    public AgentB agentB() {
        return _agentB;
    }

    public void setAgentB(AgentB agentB) {
        _agentB = agentB;
    }

    private AgentC _agentC;

    public AgentC agentC() {
        return _agentC;
    }

    public void setAgentC(AgentC agentC) {
        _agentC = agentC;
    }
    //meta! tag="end"

    public Random getSeedGenerator() {
        return seedGenerator;
    }

    public int getOrderId() {
        return orderId++;
    }

    public List<Furniture> getFurnitures() {
        return agentVyroby().getFurnitureList();
    }

    public SimQueue<Furniture> getStorage() {
        return this.storage;
    }

    public PriorityQueue<Furniture> getQueueSkladaniaPriority() {
        return queueSkladaniaPriority;
    }

    public PriorityQueue<Furniture> getQueueMoreniaPriority() {
        return queueMoreniaPriority;
    }

    public PriorityQueue<Furniture> getQueueKovaniaPriority() {
        return queueKovaniaPriority;
    }

    public WorkingPlaces getWorkingPlaces() {
        return agentA().getWorkingPlaces();
    }

    public Workers getWorkersA() {
        return agentVyroby().getWorkersA();
    }

    public Workers getWorkersB() {
        return agentVyroby().getWorkersB();
    }

    public Workers getWorkersC() {
        return agentVyroby().getWorkersC();
    }

    public Stat getSomeProcessTimeStat() {
        return someProcessTimeStat;
    }

    public AnimQueue getAnimQueue() {
        return animQueue;
    }

    public Stat getPriemernaDobaObjednavkyVSystemeStat() {
        return priemernaDobaObjednavkyVSystemeStat;
    }
}