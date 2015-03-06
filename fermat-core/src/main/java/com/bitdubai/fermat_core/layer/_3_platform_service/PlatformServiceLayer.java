package com.bitdubai.fermat_core.layer._3_platform_service;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer._3_platform_service.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._3_platform_service.PlatformServiceSubsystem;
import com.bitdubai.fermat_core.layer._3_platform_service.error_manager.ErrorManagerSubsystem;
import com.bitdubai.fermat_core.layer._3_platform_service.event_manager.EventManagerSubsystem;

/**
 * Created by ciencias on 23.01.15.
 */
public class PlatformServiceLayer implements PlatformLayer {

    Addon eventManager;
    Addon errorManager;

    public Addon getEventManager() {
        return this.eventManager;
    }

    public Addon getErrorManager() {
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
            this.eventManager = ((PlatformServiceSubsystem) eventManagerSubsystem).getAddon();

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
            this.errorManager = ((PlatformServiceSubsystem) errorManagerSubsystem).getAddon();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }
        
    }


}
