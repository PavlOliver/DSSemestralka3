package agents.agentvyroby;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPStat.WStat;
import furniture.Furniture;
import simulation.*;
import worker.Workers;


//meta! id="3"
public class AgentVyroby extends OSPABA.Agent {
    Workers workersA;
    Workers workersC;
    Workers workersB;
    private SimQueue<Furniture> queueKovania;
    private WStat dlzkaKovaniaQueueStat;

    public AgentVyroby(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        workersA = new Workers(5, 'A');
        workersC = new Workers(5, 'C');
        workersB = new Workers(5, 'B');

        dlzkaKovaniaQueueStat = new WStat(mySim());
        queueKovania = new SimQueue<>(dlzkaKovaniaQueueStat);
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerVyroby(Id.managerVyroby, mySim(), this);
        addOwnMessage(Mc.kovanie);
        addOwnMessage(Mc.skladanie);
        addOwnMessage(Mc.morenie);
        addOwnMessage(Mc.prijemTovaru);
        addOwnMessage(Mc.spracujObjednavku);
        addOwnMessage(Mc.presun);
    }
    //meta! tag="end"

    public Workers getWorkersA() {
        return workersA;
    }

    public Workers getWorkersC() {
        return workersC;
    }

    public Workers getWorkersB() {
        return workersB;
    }

    public SimQueue<Furniture> getQueueKovania() {
        return queueKovania;
    }
}