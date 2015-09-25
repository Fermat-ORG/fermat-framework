package com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.OutgoingIntraUserCantSendFundsExceptions;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.OutgoingIntraUserInsufficientFundsException;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.interfaces-IntraUserCryptoTransactionManager</code>
 * provides the method to create crypto and money payments to other users.
 *
 * @author Ezequiel Postan
 */
public interface IntraUserCryptoTransactionManager {

    /**
     * The method <code>payCryptoRequest</code> sends the payment of a request.
     *
     * @param walletPublicKey           The public key of the wallet sending the transaction
     * @param requestId                 The request identifier
     * @param destinationAddress        The crypto address of the user to send the money to
     * @param cryptoAmount              The amount of crypto currency to be sent
     * @param description               A note to register in the wallet balance to describe the transaction
     * @param senderPublicKey           The public key of the actor sending the transaction
     * @param receptorPublicKey         The public key of the actor that we are sending the transaction to
     * @param senderActorType           The type of actor sending the transaction
     * @param receptorActorType         The type of actor receiving the transaction
     * @throws OutgoingIntraUserCantSendFundsExceptions
     * @throws OutgoingIntraUserInsufficientFundsException
     */
    public void payCryptoRequest(UUID requestId,
                                 String walletPublicKey,
                                 CryptoAddress destinationAddress,
                                 long cryptoAmount,
                                 String description,
                                 String senderPublicKey,
                                 String receptorPublicKey,
                                 Actors senderActorType,
                                 Actors receptorActorType) throws OutgoingIntraUserCantSendFundsExceptions,
                                                                  OutgoingIntraUserInsufficientFundsException;

    /**
     * The method <code>sendCrypto</code> is used to send crypto currency to another intra user
     *
     * @param walletPublicKey           The public key of the wallet sending the transaction
     * @param destinationAddress        The crypto address of the user to send the money to
     * @param cryptoAmount              The amount of crypto currency to be sent
     * @param description               A note to register in the wallet balance to describe the transaction
     * @param senderPublicKey           The public key of the actor sending the transaction
     * @param receptorPublicKey         The public key of the actor that we are sending the transaction to
     * @param senderActorType           The type of actor sending the transaction
     * @param receptorActorType         The type of actor receiving the transaction
     * @param referenceWallet           The type of the wallet sending the transaction
     * @throws OutgoingIntraUserCantSendFundsExceptions
     * @throws OutgoingIntraUserInsufficientFundsException
     */
    public void sendCrypto(String walletPublicKey,
                           CryptoAddress destinationAddress,
                           long cryptoAmount,
                           String description,
                           String senderPublicKey,
                           String receptorPublicKey,
                           Actors senderActorType,
                           Actors receptorActorType,
                           ReferenceWallet referenceWallet) throws OutgoingIntraUserCantSendFundsExceptions,
                                                                   OutgoingIntraUserInsufficientFundsException;

    public String sendCrypto(String walletPublicKey,
                           CryptoAddress destinationAddress,
                           long cryptoAmount,
                             String op_Return,
                           String description,
                           String senderPublicKey,
                           String receptorPublicKey,
                           Actors senderActorType,
                           Actors receptorActorType,
                           ReferenceWallet referenceWallet) throws OutgoingIntraUserCantSendFundsExceptions,
            OutgoingIntraUserInsufficientFundsException;


    /**
     * TODO: THIS METHOD WILL BE MOVED TO A NEW TRANSACTIONAL PLUGIN SPECIALIZED IN FIAT TRANSACTIONS
     *
     * The method <code>sendFiat</code> is used to send fiat currency to another intra user.
     *
     * @param walletPublicKey           The public key of the wallet sending the transaction
     * @param destinationAddress        the crypto address of the user to send the money to
     * @param cryptoAmount              the amount of crypto currency to be sent
     * @param fiatCurrency              the type of fiat currency
     * @param fiatAmount                the amount of fiat to be sent
     * @param description               a note to register in the wallet balance to describe the transaction
     * @param senderPublicKey           the public key of the actor sending the transaction
     * @param receptorPublicKey         the public key of the actor that we are sending the transaction to
     * @param senderActorType           The type of actor sending the transaction
     * @param receptorActorType         The type of actor receiving the transaction
     * @throws OutgoingIntraUserCantSendFundsExceptions
     * @throws OutgoingIntraUserInsufficientFundsException
     *
    public void sendFiat(String walletPublicKey,
                          CryptoAddress destinationAddress,
                          long cryptoAmount,
                          FiatCurrency fiatCurrency,
                          long fiatAmount,
                          String description,
                          String senderPublicKey,
                          String receptorPublicKey,
                          Actors senderActorType,
                          Actors receptorActorType) throws OutgoingIntraUserCantSendFundsExceptions,
                                                              OutgoingIntraUserInsufficientFundsException;
    */
}
