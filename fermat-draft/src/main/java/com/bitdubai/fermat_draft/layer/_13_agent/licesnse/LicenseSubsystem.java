package com.bitdubai.fermat_draft.layer._13_agent.licesnse;

import com.bitdubai.fermat_api.layer._14_agent.AIAgent;
import com.bitdubai.fermat_api.layer._14_agent.AgentSubsystem;
import com.bitdubai.fermat_api.layer._14_agent.CantStartSubsystemException;

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
