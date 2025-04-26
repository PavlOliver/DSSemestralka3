package simulation;

import agents.agentvyroby.*;
import OSPABA.*;
import agents.agentokolia.*;
import agents.agentpresunov.*;
import agents.agentmodelu.*;
import agents.agentb.*;
import agents.agenta.*;
import agents.agentc.*;

import java.util.Random;


public class MySimulation extends OSPABA.Simulation {
    private int orderId = 0;
    private final Random seedGenerator = new Random(1234567910L);

    public MySimulation() {
        init();
    }

    @Override
    public void prepareSimulation() {
        super.prepareSimulation();
        // Create global statistcis
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Reset entities, queues, local statistics, etc...
    }

    @Override
    public void replicationFinished() {
        // Collect local statistics into global, update UI, etc...
        super.replicationFinished();
    }

    @Override
    public void simulationFinished() {
        // Display simulation results
        super.simulationFinished();
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
}