package com.bitdubai.fermat_cbp_api.all_definition.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 30/11/15.
 */
public interface CBPService {

    void start() throws CantStartServiceException;

    void stop();

    ServiceStatus getStatus();

}
