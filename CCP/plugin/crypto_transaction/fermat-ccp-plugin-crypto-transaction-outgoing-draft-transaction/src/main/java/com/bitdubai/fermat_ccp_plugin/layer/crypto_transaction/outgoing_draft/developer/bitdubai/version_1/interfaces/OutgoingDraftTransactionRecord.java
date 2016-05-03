package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_draft.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;

import java.util.UUID;

/**
 * Created by mati on 2016.02.19..
 */
public interface OutgoingDraftTransactionRecord {

    CryptoAddress getBuyerCryptoAddress();
    DraftTransaction getBitcoinTransaction();
    CryptoAddress getSellerCryptoAddress();
    long getValueToSend();
    CryptoAddress getAddressTo();
    String getWalletPublicKey();
    ReferenceWallet getReferenceWallet();
    UUID getRequestId();


}
