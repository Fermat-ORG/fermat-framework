package org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_buyer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_buyer.exceptions.CantGetBuyingTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_buyer.exceptions.CantProcessBuyingTransactionException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 10/02/16.
 */
public interface AssetBuyerManager extends FermatManager {
    /**
     * @param negotiationId   the {@link UUID} that represents the negotiation that I am going to accept.
     * @param walletPublicKey the BTC wallet from where I'll get the bitcoins for buying that asset.
     * @throws CantProcessBuyingTransactionException
     */
    void acceptAsset(UUID negotiationId, String walletPublicKey) throws CantProcessBuyingTransactionException;

    void declineAsset(UUID negotiationId) throws CantProcessBuyingTransactionException;

    List<AssetNegotiation> getNewNegotiations(BlockchainNetworkType networkType) throws CantGetBuyingTransactionsException;

    ActorAssetUser getSellerFromNegotiation(UUID negotiationID) throws CantGetBuyingTransactionsException;
}
