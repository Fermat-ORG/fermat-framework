package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 30/11/15.
 */
public abstract class AbstractOpenContractEventHandler implements FermatEventHandler {

    public OpenContractRecorderService openContractRecorderService;

    public void setOpenContractRecorderService(OpenContractRecorderService openContractRecorderService) throws CantSetObjectException {
        if (openContractRecorderService == null) {
            throw new CantSetObjectException("openContractRecorderService is null");
        }
        this.openContractRecorderService = openContractRecorderService;
    }
}
