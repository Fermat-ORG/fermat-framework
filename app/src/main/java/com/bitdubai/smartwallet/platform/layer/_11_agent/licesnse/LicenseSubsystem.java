package com.bitdubai.smartwallet.platform.layer._11_agent.licesnse;

import com.bitdubai.smartwallet.platform.layer._11_agent.AIAgent;
import com.bitdubai.smartwallet.platform.layer._11_agent.AgentSubsystem;
import com.bitdubai.smartwallet.platform.layer._11_agent.CantStartSubsystemException;

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
