package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_buyer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_buyer.exceptions.CantGetBuyingTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_buyer.exceptions.CantProcessBuyingTransactionException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 10/02/16.
 */
public interface AssetBuyerManager extends FermatManager {

    void acceptNegotiation(UUID negotiationId) throws CantProcessBuyingTransactionException;

    void declineNegotiation(UUID negotiationId) throws CantProcessBuyingTransactionException;

    void changeDeal(UUID negotiationId, int newQuantityToBuy) throws CantProcessBuyingTransactionException;

    List<AssetNegotiation> getNewNegotiations() throws CantGetBuyingTransactionsException;
}
