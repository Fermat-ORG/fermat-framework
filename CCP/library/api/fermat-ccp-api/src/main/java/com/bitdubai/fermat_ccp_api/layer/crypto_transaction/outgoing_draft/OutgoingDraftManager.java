package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_draft;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransaction;

/**
 * Created by mati on 2016.02.13..
 */
public interface OutgoingDraftManager {


    BitcoinWalletTransaction sigTransaction(BitcoinWalletTransaction bitcoinWalletTransaction,String walletPublicKey,ReferenceWallet referenceWallet);

}
