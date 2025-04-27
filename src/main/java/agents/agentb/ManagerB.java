package agents.agentb;

import OSPABA.*;
import simulation.*;

//meta! id="6"
public class ManagerB extends OSPABA.Manager
{
	public ManagerB(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="AgentVyroby", id="20", type="Request"
	public void processSkladanie(MessageForm message)
	{
	}

	//meta! sender="ProcessSkladania", id="41", type="Finish"
	public void processFinish(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! sender="AgentVyroby", id="54", type="Response"
	public void processPresun(MessageForm message)
	{
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
		case Mc.skladanie:
			processSkladanie(message);
		break;

		case Mc.presun:
			processPresun(message);
		break;

		case Mc.finish:
			processFinish(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentB myAgent()
	{
		return (AgentB)super.myAgent();
	}

}