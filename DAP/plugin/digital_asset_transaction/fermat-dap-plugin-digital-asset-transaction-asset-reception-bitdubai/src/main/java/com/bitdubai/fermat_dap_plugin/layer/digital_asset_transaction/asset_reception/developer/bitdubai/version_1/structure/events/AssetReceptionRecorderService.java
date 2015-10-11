package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AssetTransactionService;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/10/15.
 */
public class AssetReceptionRecorderService implements DealsWithEvents, AssetTransactionService {
    @Override
    public void start() throws CantStartServiceException {

    }

    @Override
    public void stop() {

    }

    @Override
    public ServiceStatus getStatus() {
        return null;
    }

    @Override
    public void setEventManager(EventManager eventManager) {

    }
}
