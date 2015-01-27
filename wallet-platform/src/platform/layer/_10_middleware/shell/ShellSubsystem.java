package platform.layer._10_middleware.shell;


import platform.layer._10_middleware.CantStartSubsystemException;
import platform.layer._10_middleware.MiddlewareEngine;
import platform.layer._10_middleware.MiddlewareSubsystem;
import platform.layer._10_middleware.shell.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by ciencias on 20.01.15.
 */
public class ShellSubsystem implements MiddlewareSubsystem {

    MiddlewareEngine mMiddlewareEngine;

    @Override
    public MiddlewareEngine getMiddlewareEngine() {
        return mMiddlewareEngine;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            mMiddlewareEngine = developerBitDubai.getMiddlewareEngine();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
