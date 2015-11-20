package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 29/09/15.
 */
public interface AssetTransactionService {

    public void start () throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;

    public void stop();

    public ServiceStatus getStatus ();

}
