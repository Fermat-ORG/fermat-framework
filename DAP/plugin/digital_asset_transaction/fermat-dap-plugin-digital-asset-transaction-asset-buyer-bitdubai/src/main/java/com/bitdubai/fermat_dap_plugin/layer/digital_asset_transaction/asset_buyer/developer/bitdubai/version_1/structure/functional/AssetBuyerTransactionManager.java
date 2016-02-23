package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetNegotiationContentMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetSellContentMessage;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.bitdubai.version_1.structure.database.AssetBuyerDAO;

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

    //CONSTRUCTORS
    public AssetBuyerTransactionManager(AssetBuyerDAO dao, ActorAssetUserManager userManager) {
        this.dao = dao;
        this.userManager = userManager;
    }

    //PUBLIC METHODS

    public void acceptNegotiation(UUID negotiationId) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        dao.updateNegotiationStatus(negotiationId, AssetSellStatus.NEGOTIATION_CONFIRMED);
    }

    public void declineNegotiation(UUID negotiationId) throws RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        dao.updateNegotiationStatus(negotiationId, AssetSellStatus.NEGOTIATION_REJECTED);
    }

    public void changeDeal(UUID negotiationId, int quantityToBuy) throws DAPException, CantUpdateRecordException, CantLoadTableToMemoryException {
        AssetNegotiation negotiation = dao.getNegotiationRecord(negotiationId).getNegotiation();
        negotiation.setQuantityToBuy(quantityToBuy);
        dao.updateAssetNegotiation(negotiation);
        dao.updateNegotiationStatus(negotiationId, AssetSellStatus.DEAL_CHANGED);
    }

    public List<AssetNegotiation> getNewNegotiations() throws DAPException {
        List<AssetNegotiation> toReturn = new ArrayList<>();
        for (NegotiationRecord record : dao.getNewNegotiations()) {
            toReturn.add(record.getNegotiation());
        }
        return toReturn;
    }

    public DAPMessage constructNegotiationMessage(NegotiationRecord negotiationRecord) throws CantGetAssetUserActorsException, CantSetObjectException {
        AssetNegotiationContentMessage content = new AssetNegotiationContentMessage(negotiationRecord.getNegotiationStatus(), negotiationRecord.getNegotiation());
        ActorAssetUser mySelf = userManager.getActorAssetUser();
        return new DAPMessage(content, mySelf, negotiationRecord.getSeller(), DAPMessageSubject.DEFAULT);
    }

    public DAPMessage constructSellingMessage(BuyingRecord buyingRecord) throws CantSetObjectException, CantGetAssetUserActorsException {
        AssetSellContentMessage content = new AssetSellContentMessage(buyingRecord.getRecordId(), buyingRecord.getBuyerTransaction().serialize(), buyingRecord.getStatus(), buyingRecord.getMetadata(), buyingRecord.getNegotiationId());
        ActorAssetUser mySelf = userManager.getActorAssetUser();
        return new DAPMessage(content, mySelf, buyingRecord.getSeller(), DAPMessageSubject.TRANSACTION_SIGNED);
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
