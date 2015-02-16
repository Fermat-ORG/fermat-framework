package com.bitdubai.wallet_platform_draft.layer._13_agent.licesnse;

import com.bitdubai.wallet_platform_api.layer._13_agent.AIAgent;
import com.bitdubai.wallet_platform_api.layer._13_agent.AgentSubsystem;
import com.bitdubai.wallet_platform_api.layer._13_agent.CantStartSubsystemException;

/**
 * Created by ciencias on 21.01.15.
 */
public class LicenseSubsystem implements AgentSubsystem {

    AIAgent mAIAgent;

    @Override
    public AIAgent getAIAgent() {
        return mAIAgent;
    }

    @Override
    public void start() throws CantStartSubsystemException {

    }
}
