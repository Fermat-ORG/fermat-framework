package org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_seller.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

import org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_seller.exceptions.CantStartAssetSellTransactionException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 10/02/16.
 */
public interface AssetSellerManager extends FermatManager {
    /**
     * This method will start a selling transaction. When requesting this, this plugin will
     * make sure that we have enough assets for sell the requested quantity, then block these amount of assets
     * on its wallet until we receive the answer from the user. Then we will proceed to unlock these assets
     * if he don't want them, or start the selling for these assets.
     *
     * @param negotiation   The {@link org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation} object which has to contains all the needed information for this
     *                      operation to occur.
     * @param userToDeliver The {@link org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser} which I want to sell the asset. This actor
     *                      has to be an connected user, because we will need his {@link com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress}.
     * @throws CantStartAssetSellTransactionException
     */
    void requestAssetSell(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser userToDeliver, org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation negotiation) throws CantStartAssetSellTransactionException;
}
