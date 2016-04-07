package org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 29/09/15.
 */
public interface AssetTransactionService {

    void start() throws CantStartServiceException;

    void stop();

    ServiceStatus getStatus();

}
