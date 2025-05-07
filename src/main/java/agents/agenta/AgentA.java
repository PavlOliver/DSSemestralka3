package agents.agenta;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPStat.WStat;
import furniture.Furniture;
import furniture.Furnitures;
import simulation.*;
import agents.agenta.continualassistants.*;
import workingplace.WorkingPlaces;


//meta! id="4"
public class AgentA extends OSPABA.Agent {
    private WorkingPlaces workingPlaces;

    public AgentA(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

        //workingPlaces = new WorkingPlaces(80);
        addOwnMessage(Mc.pripravaMaterialu);
        addOwnMessage(Mc.koniecRezania);
        addOwnMessage(Mc.koniecKovania);
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerA(Id.managerA, mySim(), this);
        new ProcessRezania(Id.processRezania, mySim(), this);
        new ProcessPripravy(Id.processPripravy, mySim(), this);
        new ProcessKovanieA(Id.processKovanieA, mySim(), this);
        addOwnMessage(Mc.kovanie);
        addOwnMessage(Mc.prijemTovaru);
        addOwnMessage(Mc.presun);
    }

    //meta! tag="end"
    public WorkingPlaces getWorkingPlaces() {
        return workingPlaces;
    }

    public void setNumberOfWorkingPlaces(int number) {
        workingPlaces = new WorkingPlaces(number, (MySimulation) mySim());
    }
}