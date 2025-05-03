package simulation;

import OSPDataStruct.SimQueue;
import OSPStat.Stat;
import agents.agentvyroby.*;
import agents.agentokolia.*;
import agents.agentpresunov.*;
import agents.agentmodelu.*;
import agents.agentb.*;
import agents.agenta.*;
import agents.agentc.*;
import furniture.Furniture;
import furniture.Furnitures;
import worker.Workers;
import workingplace.WorkingPlaces;

import java.util.List;
import java.util.Random;


public class MySimulation extends OSPABA.Simulation {
    private int orderId = 0;
    private final Random seedGenerator = new Random(12345679101L);

    private Stat priemernaDobaObjednavkyVSystemeStat;
    private Stat priemernaDobaTovaruVSystemeStat;

    private Stat someProcessTimeStat;

    public MySimulation() {
        init();
    }

    @Override
    public void prepareSimulation() {
        super.prepareSimulation();
        // Create global statistcis
        priemernaDobaObjednavkyVSystemeStat = new Stat();
        priemernaDobaTovaruVSystemeStat = new Stat();
        someProcessTimeStat = new Stat();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Reset entities, queues, local statistics, etc...
        agentA().setNumberOfWorkingPlaces(80);
        agentVyroby().setSizes(15, 5, 40);
    }

    @Override
    public void replicationFinished() {
        // Collect local statistics into global, update UI, etc...
        super.replicationFinished();
        priemernaDobaObjednavkyVSystemeStat.addSample(agentVyroby().getOrderTimeInSystemStat().mean());
        priemernaDobaTovaruVSystemeStat.addSample(agentVyroby().getTovarTimeInSystemStat().mean());
        System.out.println("Priemerne vytazenie A" + agentVyroby().getWorkersA().getAverageUtilization());
        System.out.println("Priemerne vytazenie B" + agentVyroby().getWorkersB().getAverageUtilization());
        System.out.println("Priemerne vytazenie C" + agentVyroby().getWorkersC().getAverageUtilization());
        System.out.println("Priemerne vytazenie pracoviska" + agentA().getWorkingPlaces().getAverageUtilization());


        System.out.println("Replikácia číslo " + currentReplication() + ". Celková priemerná doba objednávky v systéme: " + (priemernaDobaObjednavkyVSystemeStat.mean() / 60 / 60 / 1000));
        System.out.println("Replikácia číslo " + currentReplication() + ". Celková priemerná doba tovaru v systéme: " + (priemernaDobaTovaruVSystemeStat.mean() / 60 / 60 / 1000));
        System.out.println("xx:" + (agentVyroby().getTovarTimeInSystem() / 60 / 60 / 1000 / agentVyroby().getFinishedTovarCount()));
        System.out.println("Some statistic: " + someProcessTimeStat.mean());
    }

    @Override
    public void simulationFinished() {
        // Display simulation results
        super.simulationFinished();
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Celková priemerná doba objednávky v systéme: " + (priemernaDobaObjednavkyVSystemeStat.mean() / 60 / 60 / 1000));
        System.out.println("Celková priemerná doba tovaru v systéme: " + (priemernaDobaTovaruVSystemeStat.mean() / 60 / 60 / 1000));
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

    public SimQueue<Furnitures> getStorage() {
        return agentA().getStorage();
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
}