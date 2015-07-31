package com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.OutgoingIntraUserCantSendFundsExceptions;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.OutgoingIntraUserInsufficientFundsException;

/**
 * Created by eze on 2015.07.30..
 */
public interface OutgoingIntraUserTransactionManager {

    /**
     * The method <code>sendCrypto</code> is used to send crypto currency to another intra user
     *
     * @param referenceWallet           the reference wallet associated to the wallet sending the money.
     * @param walletPublicKey           the public key of the wallet sending the funds
     * @param destinationAddress        the crypto address of the user to send the money to
     * @param cryptoAmount              the amount of crypto currency to be sent
     * @param notes                     a note to register in the wallet balance to describe the transaction
     * @param deliveredByActorPublicKey the public key of the actor sending the transaction
     * @param deliveredByActorType      the type of the actor sending the transaction
     * @param deliveredToActorPublicKey the public key of the actor that we are sending the transaction to
     * @param deliveredToActorType      the public key of the actor that we are sending the transaction to
     */
    public void sendCrypto(ReferenceWallet referenceWallet,
                           String walletPublicKey,
                           CryptoAddress destinationAddress,
                           long cryptoAmount,
                           String notes,
                           String deliveredByActorPublicKey,
                           Actors deliveredByActorType,
                           String deliveredToActorPublicKey,
                           Actors deliveredToActorType) throws OutgoingIntraUserCantSendFundsExceptions,
                                                               OutgoingIntraUserInsufficientFundsException;


    /**
     * The method <code>sendMFiat</code> is used to send fiat currency to another intra user.
     *
     * @param referenceWallet           the reference wallet associated to the wallet sending the money.
     * @param walletPublicKey           the public key of the wallet sending the funds
     * @param destinationAddress        the crypto address of the user to send the money to
     * @param cryptoAmount              the amount of crypto currency to be sent
     * @param fiatCurrency              the type of fiat currency
     * @param fiatAmount                the amount of fiat to be sent
     * @param notes                     a note to register in the wallet balance to describe the transaction
     * @param deliveredByActorPublicKey the public key of the actor sending the transaction
     * @param deliveredByActorType      the type of the actor sending the transaction
     * @param deliveredToActorPublicKey the public key of the actor that we are sending the transaction to
     * @param deliveredToActorType      the public key of the actor that we are sending the transaction to
     */
    public void sendMFiat(ReferenceWallet referenceWallet,
                          String walletPublicKey,
                          CryptoAddress destinationAddress,
                          long cryptoAmount,
                          FiatCurrency fiatCurrency,
                          long fiatAmount,
                          String notes,
                          String deliveredByActorPublicKey,
                          Actors deliveredByActorType,
                          String deliveredToActorPublicKey,
                          Actors deliveredToActorType) throws OutgoingIntraUserCantSendFundsExceptions,
                                                              OutgoingIntraUserInsufficientFundsException;
}
