package agents.agentc;

import OSPABA.*;
import simulation.*;

//meta! id="7"
public class ManagerC extends OSPABA.Manager
{
	public ManagerC(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication

		if (petriNet() != null)
		{
			petriNet().clear();
		}
	}

	//meta! sender="AgentVyroby", id="22", type="Request"
	public void processKovanie(MessageForm message)
	{
	}

	//meta! sender="ProcessMorenie", id="44", type="Finish"
	public void processFinishProcessMorenie(MessageForm message)
	{
	}

	//meta! sender="ProcessLakovanie", id="46", type="Finish"
	public void processFinishProcessLakovanie(MessageForm message)
	{
	}

	//meta! sender="ProcessKovanie", id="48", type="Finish"
	public void processFinishProcessKovanie(MessageForm message)
	{
	}

	//meta! sender="AgentVyroby", id="21", type="Request"
	public void processMorenie(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
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
			switch (message.sender().id())
			{
			case Id.processMorenie:
				processFinishProcessMorenie(message);
			break;

			case Id.processLakovanie:
				processFinishProcessLakovanie(message);
			break;

			case Id.processKovanie:
				processFinishProcessKovanie(message);
			break;
			}
		break;

		case Mc.kovanie:
			processKovanie(message);
		break;

		case Mc.morenie:
			processMorenie(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentC myAgent()
	{
		return (AgentC)super.myAgent();
	}

}