package com.bitdubai.fermat_cht_api.all_definition.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantStartServiceException;

/**
 * This interface must be implemented for the recorder service in this platform
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/01/16.
 */
public interface CHTService {

    void start() throws CantStartServiceException;

    void stop();

    ServiceStatus getStatus();

}
