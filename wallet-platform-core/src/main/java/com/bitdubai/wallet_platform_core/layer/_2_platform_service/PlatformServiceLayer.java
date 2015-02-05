package com.bitdubai.wallet_platform_core.layer._2_platform_service;

import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.PlatformServiceSubsystem;
import com.bitdubai.wallet_platform_core.layer._2_platform_service.error_manager.ErrorManagerSubsystem;
import com.bitdubai.wallet_platform_core.layer._2_platform_service.event_manager.EventManagerSubsystem;

/**
 * Created by ciencias on 23.01.15.
 */
public class PlatformServiceLayer implements PlatformLayer {

    Service eventManager;
    Service errorManager;

    public Service getEventManager() {
        return this.eventManager;
    }

    public Service getErrorManager() {
        return this.errorManager;
    }

    @Override
    public void start() throws CantStartLayerException {


        /**
         * I will start the event Manager.
         */
        PlatformServiceSubsystem eventManagerSubsystem = new EventManagerSubsystem();

        try {
            eventManagerSubsystem.start();
            this.eventManager = ((PlatformServiceSubsystem) eventManagerSubsystem).getService();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }

        /**
         * FI will start the error Manager.
         */
        PlatformServiceSubsystem errorManagerSubsystem = new ErrorManagerSubsystem();

        try {
            errorManagerSubsystem.start();
            this.errorManager = ((PlatformServiceSubsystem) errorManagerSubsystem).getService();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }
        
    }


}
