package agents.agenta;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPStat.WStat;
import furniture.Furniture;
import furniture.Furnitures;
import simulation.*;
import agents.agenta.continualassistants.*;
import worker.Workers;
import workingplace.WorkingPlaces;

import java.util.ArrayList;
import java.util.List;


//meta! id="4"
public class AgentA extends OSPABA.Agent {
    private WorkingPlaces workingPlaces;
    private SimQueue<Furnitures> storage;
    private WStat dlzkaStorageStat;
    private List<Furniture> furnitureList; //asi to bude musiet ist do agenta vyroby uvidime

    public AgentA(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        dlzkaStorageStat = new WStat(mySim());
        storage = new SimQueue<>(dlzkaStorageStat);

        furnitureList = new ArrayList<>();

        workingPlaces = new WorkingPlaces(20);

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
        addOwnMessage(Mc.pripravaMaterialu);
        addOwnMessage(Mc.koniecRezania);
    }

    //meta! tag="end"
    public SimQueue<Furnitures> getStorage() {
        return storage;
    }

    public WorkingPlaces getWorkingPlaces() {
        return workingPlaces;
    }

    public void addFurniture(Furniture furniture) {
        furnitureList.add(furniture);
    }

    public List<Furniture> getFurnitureList() {
        return furnitureList;
    }
}