package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/12/15.
 */
public abstract class AbstractCloseContractEventHandler implements FermatEventHandler {

    public CloseContractRecorderService closeContractRecorderService;

    public void setCloseContractRecorderService(CloseContractRecorderService closeContractRecorderService) throws CantSetObjectException {
        if (closeContractRecorderService == null) {
            throw new CantSetObjectException("closeContractRecorderService is null");
        }
        this.closeContractRecorderService = closeContractRecorderService;
    }

}
