package org.fermat.fermat_dap_api.layer.dap_transaction.asset_redemption.interfaces;

import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;

/**
 * This is just a marker interface, since RPR
 * is an agent that receives the redemption requests through
 * {@link AssetTransmissionNetworkServiceManager}
 * you shouldn't 'directly' pass it an event.
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 03/11/15.
 */
public interface RedeemPointRedemptionManager {// extends FermatManager {
}
