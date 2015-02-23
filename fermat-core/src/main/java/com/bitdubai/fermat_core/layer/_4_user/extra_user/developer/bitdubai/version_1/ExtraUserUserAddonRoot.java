package com.bitdubai.fermat_core.layer._4_user.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._4_user.User;

/**
 * Created by loui on 22/02/15.
 */
public class ExtraUserUserAddonRoot implements Service, User, Addon {

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() {

        this.serviceStatus = ServiceStatus.STARTED;

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
