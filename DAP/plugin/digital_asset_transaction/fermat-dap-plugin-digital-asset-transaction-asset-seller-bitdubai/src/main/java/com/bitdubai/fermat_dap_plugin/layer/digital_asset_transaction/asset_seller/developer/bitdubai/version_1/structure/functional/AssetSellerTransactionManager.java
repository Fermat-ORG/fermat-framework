package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetNegotiationContentMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.DAPContentMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantSendMessageException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendDigitalAssetMetadataException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_seller.exceptions.CantStartAssetSellTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.bitdubai.version_1.structure.database.AssetSellerDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public final class AssetSellerTransactionManager {

    //VARIABLE DECLARATION
    private final AssetUserWalletManager assetUserWalletManager;
    private final AssetTransmissionNetworkServiceManager assetTransmission;
    private final ActorAssetUserManager actorAssetUserManager;
    private final AssetSellerDAO dao;

    //CONSTRUCTORS
    public AssetSellerTransactionManager(AssetUserWalletManager assetUserWalletManager, AssetTransmissionNetworkServiceManager assetTransmission, ActorAssetUserManager actorAssetUserManager, AssetSellerDAO dao) {
        this.assetUserWalletManager = assetUserWalletManager;
        this.assetTransmission = assetTransmission;
        this.actorAssetUserManager = actorAssetUserManager;
        this.dao = dao;
    }

    //PUBLIC METHODS
    public void requestAssetSell(ActorAssetUser userToDeliver, AssetNegotiation negotiation) throws CantStartAssetSellTransactionException, CantLoadWalletException, CantGetAssetUserActorsException, CantSetObjectException, CantSendDigitalAssetMetadataException {
        try {
            BlockchainNetworkType networkType = negotiation.getNetworkType();
            DigitalAsset assetToOffer = negotiation.getAssetToOffer();
            int quantity = negotiation.getQuantityToBuy();
            AssetUserWallet userWallet = getUserWallet(networkType);
            ActorAssetUser mySelf = actorAssetUserManager.getActorAssetUser();
            if (weHaveEnoughAssets(quantity, userWallet, assetToOffer)) {
                dao.saveAssetNegotiation(negotiation);
                lockAssetsOnWallet();
                startSellingTransactions(userWallet, negotiation, userToDeliver);
                sendMessage(negotiation, mySelf, userToDeliver);
            } else {
                throw new CantStartAssetSellTransactionException("We don't have that much assets");
            }
        } catch (CantInsertRecordException | CantSendMessageException | CantGetTransactionsException | CantGetDigitalAssetFromLocalStorageException e) {
            throw new CantStartAssetSellTransactionException(e);
        }
    }

    //PRIVATE METHODS
    private AssetUserWallet getUserWallet(BlockchainNetworkType networkType) throws CantLoadWalletException {
        return assetUserWalletManager.loadAssetUserWallet(WalletUtilities.WALLET_PUBLIC_KEY, networkType);
    }

    private void lockAssetsOnWallet() {
        //TODO
    }

    private void startSellingTransactions(AssetUserWallet userWallet, AssetNegotiation negotiation, ActorAssetUser actorTo) throws CantGetDigitalAssetFromLocalStorageException, CantGetTransactionsException, CantInsertRecordException {
        for (AssetUserWalletTransaction transaction : userWallet.getAllAvailableTransactions(negotiation.getAssetToOffer().getPublicKey())) {
            DigitalAssetMetadata metadata = userWallet.getDigitalAssetMetadata(transaction.getGenesisTransaction());
            dao.startNewSelling(metadata, actorTo, negotiation.getNegotiationId());
        }
    }

    private void sendMessage(AssetNegotiation negotiation, DAPActor from, DAPActor to) throws CantSetObjectException, CantSendMessageException {
        DAPContentMessage content = new AssetNegotiationContentMessage(AssetSellStatus.WAITING_CONFIRMATION, negotiation);
        DAPMessage message = new DAPMessage(content, from, to, DAPMessageSubject.NEW_NEGOTIATION_STARTED);
        assetTransmission.sendMessage(message);
    }

    private List<DigitalAssetMetadata> getAvailableAssetMetadata(AssetUserWallet assetUserWallet, DigitalAsset digitalAsset) throws CantGetTransactionsException, CantGetDigitalAssetFromLocalStorageException {
        List<AssetUserWalletTransaction> transactions = assetUserWallet.getAllAvailableTransactions(digitalAsset.getPublicKey());
        List<DigitalAssetMetadata> toReturn = new ArrayList<>();
        for (AssetUserWalletTransaction transaction : transactions) {
            toReturn.add(assetUserWallet.getDigitalAssetMetadata(transaction.getGenesisTransaction()));
        }
        return toReturn;
    }

    private boolean weHaveEnoughAssets(int requestedQuantity, AssetUserWallet assetUserWallet, DigitalAsset digitalAsset) {
        try {
            return requestedQuantity >= getAvailableAssetMetadata(assetUserWallet, digitalAsset).size();
        } catch (CantGetTransactionsException | CantGetDigitalAssetFromLocalStorageException e) {
            return false;
        }
    }
    //GETTER AND SETTERS

    //INNER CLASSES
}
