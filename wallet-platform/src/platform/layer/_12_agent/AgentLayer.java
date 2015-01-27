package platform.layer._12_agent;

import platform.layer.CantStartLayerException;
import platform.layer.PlatformLayer;
import platform.layer._12_agent.licesnse.LicenseSubsystem;


/**
 * Created by ciencias on 03.01.15.
 */
public class AgentLayer implements PlatformLayer {

    AIAgent mLicenseAgent;

    public AIAgent getLicenseAgent() {
        return mLicenseAgent;
    }

    @Override
    public void start()  throws CantStartLayerException {


        /**
         * I will start now the license agent.
         */
        AgentSubsystem licenseSubsystem = new LicenseSubsystem();

        try {
            licenseSubsystem.start();
            mLicenseAgent = ((AgentSubsystem) licenseSubsystem).getAIAgent();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * The license agent is vital for the platform to work. Without it starting there is no way to use the
             * platform itself.
             */
            throw new CantStartLayerException();
        }
    }
}
