package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionState;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;

import java.io.Serializable;
import java.util.UUID;

/**
 * The Class <code>FermatWalletTransaction</code>
 * TODO WRITE DETAILS
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 11/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface FermatWalletModuleTransaction extends Serializable {

    Actor getInvolvedActor();

    UUID getContactId();

    UUID getTransactionId();

    String getTransactionHash();

    CryptoAddress getAddressFrom();

    CryptoAddress getAddressTo();

    String getActorToPublicKey();

    String getActorFromPublicKey();

    Actors getActorToType();

    Actors getActorFromType();

    BalanceType getBalanceType();

    TransactionType getTransactionType();

    TransactionState getTransactionState();

    long getTimestamp();

    long getAmount();

    long getRunningBookBalance();

    long getRunningAvailableBalance();

    String getMemo();

}
