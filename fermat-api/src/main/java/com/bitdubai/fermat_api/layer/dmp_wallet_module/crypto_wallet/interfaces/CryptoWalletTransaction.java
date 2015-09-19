package com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_actor.Actor;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransaction;

import java.io.Serializable;
import java.util.UUID;

/**
 * The Class <code>CryptoWalletTransaction</code>
 * TODO WRITE DETAILS
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 11/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface CryptoWalletTransaction extends Serializable {

    BitcoinWalletTransaction getBitcoinWalletTransaction();

    Actor getInvolvedActor();

    UUID getContactId();

}
