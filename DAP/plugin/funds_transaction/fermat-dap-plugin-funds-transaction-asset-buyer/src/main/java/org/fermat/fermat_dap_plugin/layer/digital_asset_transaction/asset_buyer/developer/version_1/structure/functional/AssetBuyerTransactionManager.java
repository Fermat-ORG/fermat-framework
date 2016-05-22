package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetNegotiationContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetSellContentMessage;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 17/02/16.
 */
public class AssetBuyerTransactionManager {

    //VARIABLE DECLARATION
    private final AssetBuyerDAO dao;
    private final ActorAssetUserManager userManager;
    private final AssetTransmissionNetworkServiceManager assetTransmission;

    //CONSTRUCTORS
    public AssetBuyerTransactionManager(AssetBuyerDAO dao, ActorAssetUserManager userManager, AssetTransmissionNetworkServiceManager assetTransmission) {
        this.dao = dao;
        this.userManager = userManager;
        this.assetTransmission = assetTransmission;
    }

    //PUBLIC METHODS
    public List<AssetNegotiation> getNewNegotiations(BlockchainNetworkType networkType) throws DAPException {
        List<AssetNegotiation> toReturn = new ArrayList<>();
        for (NegotiationRecord record : dao.getNewNegotiations(networkType)) {
            for (int i = 0; i < record.getForProcess(); i++) {
                toReturn.add(record.getNegotiation());
            }
        }
        return toReturn;
    }

    public ActorAssetUser getSellerFromNegotiation(UUID negotiationID) throws DAPException {

        ActorAssetUser seller;
        seller = dao.getNegotiationRecord(negotiationID).getSeller();
        return seller;

    }

    public DAPMessage constructNegotiationMessage(NegotiationRecord negotiationRecord) throws CantGetAssetUserActorsException, CantSetObjectException {
        AssetNegotiationContentMessage content = new AssetNegotiationContentMessage(negotiationRecord.getNegotiationStatus(), negotiationRecord.getNegotiation());
        ActorAssetUser mySelf = userManager.getActorAssetUser();
        return new DAPMessage(content, mySelf, negotiationRecord.getSeller(), DAPMessageSubject.NEGOTIATION_ANSWER);
    }

    public DAPMessage constructSellingMessage(BuyingRecord buyingRecord, AssetSellStatus status) throws CantSetObjectException, CantGetAssetUserActorsException {
        AssetSellContentMessage content = new AssetSellContentMessage(buyingRecord.getRecordId(), buyingRecord.getBuyerTransaction().serialize(), buyingRecord.getBuyerTransaction().getValue(), status, buyingRecord.getMetadata(), buyingRecord.getNegotiationId(), null, null);
        ActorAssetUser mySelf = userManager.getActorAssetUser();
        return new DAPMessage(content, mySelf, buyingRecord.getSeller(), DAPMessageSubject.TRANSACTION_SIGNED);
    }

    public void acceptAsset(UUID negotiationId, String btcWalletPk) throws DAPException, CantUpdateRecordException, CantLoadTableToMemoryException {
        dao.acceptNegotiation(negotiationId, btcWalletPk);
        NegotiationRecord record = dao.getNegotiationRecord(negotiationId);
        record.setNegotiationStatus(AssetSellStatus.NEGOTIATION_CONFIRMED);
        DAPMessage message = null;
        try {
            message = constructNegotiationMessage(record);
        } catch (CantSetObjectException e) {
            e.printStackTrace();
        }
        assetTransmission.sendMessage(message);
    }

    public void declineAsset(UUID negotiationId) throws DAPException, CantUpdateRecordException, CantLoadTableToMemoryException {
        dao.rejectNegotiation(negotiationId);
        NegotiationRecord record = dao.getNegotiationRecord(negotiationId);
        record.setNegotiationStatus(AssetSellStatus.NEGOTIATION_REJECTED);
        DAPMessage message = null;
        try {
            message = constructNegotiationMessage(record);
        } catch (CantSetObjectException e) {
            e.printStackTrace();
        }
        assetTransmission.sendMessage(message);
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
