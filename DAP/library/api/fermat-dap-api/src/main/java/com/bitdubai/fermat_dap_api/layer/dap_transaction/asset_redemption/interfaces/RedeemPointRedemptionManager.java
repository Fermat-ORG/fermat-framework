package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_redemption.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

/**
 * This is just a marker interface, since RPR
 * is an agent that receives the redemption requests through
 * {@link com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager}
 * you shouldn't 'directly' pass it an event.
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 03/11/15.
 */
public interface RedeemPointRedemptionManager extends FermatManager {
}
