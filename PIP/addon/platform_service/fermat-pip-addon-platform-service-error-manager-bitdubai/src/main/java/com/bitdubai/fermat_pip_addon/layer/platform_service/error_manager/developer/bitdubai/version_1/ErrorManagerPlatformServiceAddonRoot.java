package com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;

import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.*;
import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.exceptions.CantStartAgentException;
import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.structure.ErrorManagerDatabaseFactory;
import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.structure.ErrorManagerRegistry;
import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.structure.ErrorManagerReportAgent;

import java.util.Calendar;

/**
 * Created by ciencias on 05.02.15
 * Modified by Federico Rodriguez on 01.05.15
 */
public class ErrorManagerPlatformServiceAddonRoot implements Addon,DealsWithPlatformDatabaseSystem, ErrorManager, Service {

    /**
     * ErrorManagerRegistry variable
     */
    ErrorManagerRegistry errorManagerRegistry;

    /**
     * ErrorManagerReportAgent variable
     */
    ErrorManagerReportAgent errorManagerReportAgent;

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    private PlatformDatabaseSystem platformDatabaseSystem;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    private ErrorManagerDatabaseFactory errorManagerDatabaseFactory;

    @Override
    public void setPlatformDatabaseSystem(PlatformDatabaseSystem platformDatabaseSystem) {
        this.platformDatabaseSystem = platformDatabaseSystem;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */

    /**
     * ErrorManager Interface implementation.
     */
    @Override
    public void reportUnexpectedPlatformException(PlatformComponents exceptionSource, UnexpectedPlatformExceptionSeverity unexpectedPlatformExceptionSeverity, Exception exception) {
        try {
            //I need the timestamp when the Exception occurred
            Calendar timeStamp = Calendar.getInstance();
            //Creates a new ErrorManagerRegistry based on the Exception information provided
            this.errorManagerRegistry.createNewErrorRegistry("Platform",
                    exceptionSource.toString(),
                    unexpectedPlatformExceptionSeverity.toString(),
                    exception.getMessage(),
                    0L,
                    timeStamp.getTimeInMillis());
        }
        catch (Exception e)
        {
            //TODO (LUIS) Ver que se hace acá
        }


    }

    @Override
    public void reportUnexpectedPluginException(Plugins exceptionSource, UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception) {

        try {
            //I need the timestamp when the Exception occurred
            Calendar timeStamp = Calendar.getInstance();
            //Creates a new ErrorManagerRegistry based on the Exception information provided
            this.errorManagerRegistry.createNewErrorRegistry("Plugins",
                    exceptionSource.toString(),
                    unexpectedPluginExceptionSeverity.toString(),
                    exception.getMessage(),
                    0L,
                    timeStamp.getTimeInMillis());
        }
        catch (Exception e)
        {
            //TODO (LUIS) Ver que se hace acá
        }
        
        

    }

    @Override
    public void reportUnexpectedWalletException(Wallets exceptionSource, UnexpectedWalletExceptionSeverity unexpectedWalletExceptionSeverity, Exception exception) {




        try {
            //I need the timestamp when the Exception occurred
            Calendar timeStamp = Calendar.getInstance();
            //Creates a new ErrorManagerRegistry based on the Exception information provided
            this.errorManagerRegistry.createNewErrorRegistry("Wallets",
                    exceptionSource.toString(),
                    unexpectedWalletExceptionSeverity.toString(),
                    exception.getMessage(),
                    0L,
                    timeStamp.getTimeInMillis());
        }
        catch (Exception e)
        {
            //TODO (LUIS) Ver que se hace acá
        }
    }

    @Override
    public void reportUnexpectedAddonsException(Addons exceptionSource, UnexpectedAddonsExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception) {



        try {
            //I need the timestamp when the Exception occurred
            Calendar timeStamp = Calendar.getInstance();
            //Creates a new ErrorManagerRegistry based on the Exception information provided
            this.errorManagerRegistry.createNewErrorRegistry("Addons",
                    exceptionSource.toString(),
                    unexpectedAddonsExceptionSeverity.toString(),
                    exception.getMessage(),
                    0L,
                    timeStamp.getTimeInMillis());
        }
        catch (Exception e)
        {
            //TODO (LUIS) Ver que se hace acá
        }
        

    }


    /**
     * Service Interface implementation.
     */

    @Override
    public void start() {

        try {
            this.serviceStatus = ServiceStatus.STARTED;

            this.errorManagerRegistry = new ErrorManagerRegistry();
            this.errorManagerRegistry.Initialize();

            this.errorManagerReportAgent = new ErrorManagerReportAgent();
            this.errorManagerReportAgent.setErrorManagerRegistry(this.errorManagerRegistry);
            try {
                this.errorManagerReportAgent.start();
            } catch (CantStartAgentException e) {
                //Implement ErrorManager catching exception
            }
        }
        catch (Exception e)
        {
            //TODO (LUIS) Ver que se hace acá
        }


    }

    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;

    }

    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }



}
