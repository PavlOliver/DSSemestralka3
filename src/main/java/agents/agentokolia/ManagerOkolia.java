package agents.agentokolia;

import OSPABA.*;
import simulation.*;

//meta! id="2"
public class ManagerOkolia extends OSPABA.Manager {
    public ManagerOkolia(int id, Simulation mySim, Agent myAgent) {
        super(id, mySim, myAgent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

        if (petriNet() != null) {
            petriNet().clear();
        }
    }

	//meta! sender="PlanovacPrichodov", id="26", type="Finish"
	public void processFinish(MessageForm message) {
    }

	//meta! sender="AgentModelu", id="16", type="Notice"
	public void processInicializacia(MessageForm message) {
        message.setAddressee(myAgent().findAssistant(Id.planovacPrichodov));
        startContinualAssistant(message);
    }

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message) {
        switch (message.code()) {
            case Mc.prichodObjednavky:
                message.setAddressee(myAgent().parent());
                notice(message);
                //naplanujem dalsi prichod objednavky
                MyMessage newMessage = (MyMessage) message.createCopy();
                newMessage.setCode(Mc.prichodObjednavky);
                newMessage.setAddressee(myAgent().findAssistant(Id.planovacPrichodov));
                startContinualAssistant(newMessage);
                break;
        }
    }

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init()
	{
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.finish:
			processFinish(message);
		break;

		case Mc.inicializacia:
			processInicializacia(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

    @Override
    public AgentOkolia myAgent() {
        return (AgentOkolia) super.myAgent();
    }

}