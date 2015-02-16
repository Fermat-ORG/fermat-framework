package com.bitdubai.wallet_platform_api.layer._13_agent;


/**
 * Created by ciencias on 21.01.15.
 */
public interface AgentSubsystem {
    public void start () throws CantStartSubsystemException;
    public AIAgent getAIAgent();
}
