package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

/**
 * Created by ciencias on 30.12.14.
 */
public interface Service {

    void start() throws CantStartException;

    void pause();

    void resume();

    void stop();

    ServiceStatus getStatus();

}
