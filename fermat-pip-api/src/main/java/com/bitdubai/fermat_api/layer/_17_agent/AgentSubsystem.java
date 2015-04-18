package com.bitdubai.fermat_api.layer._17_agent;


/**
 * Created by ciencias on 21.01.15.
 */
public interface AgentSubsystem {
    public void start () throws CantStartSubsystemException;
    public AIAgent getAIAgent();
}
