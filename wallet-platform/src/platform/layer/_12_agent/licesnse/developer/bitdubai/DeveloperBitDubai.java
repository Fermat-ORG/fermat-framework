package platform.layer._12_agent.licesnse.developer.bitdubai;

import platform.layer._12_agent.AIAgent;
import platform.layer._12_agent.AgentLayerDeveloper;
import platform.layer._12_agent.licesnse.developer.bitdubai.version_1.LicenseAgent;

/**
 * Created by ciencias on 21.01.15.
 */
public class DeveloperBitDubai implements AgentLayerDeveloper {

    AIAgent mAIAgent;

    @Override
    public AIAgent getAIAgent() {
        return mAIAgent;
    }

    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to run. Now there is only one, so
         * it is easy to choose.
         */

        mAIAgent = new LicenseAgent();

    }
}
