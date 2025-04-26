package simulation;

import OSPABA.*;
import furniture.Furniture;
import worker.Worker;
import workingplace.WorkingPlace;

public class MyMessage extends OSPABA.MessageForm {
    private Furniture furniture;
    private Worker worker;
    private WorkingPlace workingPlace;

    public MyMessage(Simulation mySim) {
        super(mySim);
    }

    public MyMessage(MyMessage original) {
        super(original);
        // copy() is called in superclass
    }

    @Override
    public MessageForm createCopy() {
        return new MyMessage(this);
    }

    @Override
    protected void copy(MessageForm message) {
        super.copy(message);
        MyMessage original = (MyMessage) message;
        // Copy attributes
        this.furniture = original.furniture;
        this.worker = original.worker;
        this.workingPlace = original.workingPlace;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setFurniture(Furniture furniture) {
        this.furniture = furniture;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    public void setWorkingPlace(WorkingPlace workingPlace) {
        this.workingPlace = workingPlace;
    }

    public WorkingPlace getWorkingPlace() {
        return workingPlace;
    }

    public void nullMessage() {
        this.furniture = null;
        this.worker = null;
        this.workingPlace = null;
    }
}