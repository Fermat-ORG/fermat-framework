package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.interfaces-IntraActorCryptoTransactionManager</code>
 * provides the method to create crypto and money payments to other users.
 *
 * @author Ezequiel Postan
 */
public interface IntraActorCryptoTransactionManager extends FermatManager {

    /**
     * The method <code>payCryptoRequest</code> sends the payment of a request.
     *
     * @param walletPublicKey    The public key of the wallet sending the transaction
     * @param requestId          The request identifier
     * @param destinationAddress The crypto address of the user to send the money to
     * @param cryptoAmount       The amount of crypto currency to be sent
     * @param description        A note to register in the wallet balance to describe the transaction
     * @param senderPublicKey    The public key of the actor sending the transaction
     * @param receptorPublicKey  The public key of the actor that we are sending the transaction to
     * @param senderActorType    The type of actor sending the transaction
     * @param receptorActorType  The type of actor receiving the transaction
     * @throws com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions
     * @throws com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException
     */
    void payCryptoRequest(UUID requestId,
                          String walletPublicKey,
                          CryptoAddress destinationAddress,
                          long cryptoAmount,
                          String description,
                          String senderPublicKey,
                          String receptorPublicKey,
                          Actors senderActorType,
                          Actors receptorActorType,
                          ReferenceWallet referenceWallet,
                          BlockchainNetworkType blockchainNetworkType,
                          CryptoCurrency cryptoCurrency,
                          long fee, FeeOrigin feeOrigin) throws com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions,
            com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException;

    /**
     * The method <code>sendCrypto</code> is used to send crypto currency to another intra user
     *
     * @param walletPublicKey    The public key of the wallet sending the transaction
     * @param destinationAddress The crypto address of the user to send the money to
     * @param cryptoAmount       The amount of crypto currency to be sent
     * @param description        A note to register in the wallet balance to describe the transaction
     * @param senderPublicKey    The public key of the actor sending the transaction
     * @param receptorPublicKey  The public key of the actor that we are sending the transaction to
     * @param senderActorType    The type of actor sending the transaction
     * @param receptorActorType  The type of actor receiving the transaction
     * @param referenceWallet    The type of the wallet sending the transaction
     * @throws com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions
     * @throws com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException
     */
    UUID sendCrypto(String walletPublicKey,
                    CryptoAddress destinationAddress,
                    long cryptoAmount,
                    String description,
                    String senderPublicKey,
                    String receptorPublicKey,
                    Actors senderActorType,
                    Actors receptorActorType,
                    ReferenceWallet referenceWallet,
                    BlockchainNetworkType blockchainNetworkType,
                    CryptoCurrency cryptoCurrency,
                    long fee,FeeOrigin feeOrigin) throws com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions,
            com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException;

    /**
     * This method overload is used to send crypto currency to an intra user actor,
     * in this one you can specify if its for an intra user in the same device.
     * {@code true} in that flag if so.
     */
    UUID sendCrypto(String walletPublicKey,
                    CryptoAddress destinationAddress,
                    long cryptoAmount,
                    String op_Return,
                    String description,
                    String senderPublicKey,
                    String receptorPublicKey,
                    Actors senderActorType,
                    Actors receptorActorType,
                    ReferenceWallet referenceWallet,
                    boolean sendFromSameDevice,
                    BlockchainNetworkType blockchainNetworkType,
                    CryptoCurrency cryptoCurrency,
                    long fee,FeeOrigin feeOrigin) throws OutgoingIntraActorCantSendFundsExceptions, OutgoingIntraActorInsufficientFundsException;


    /**
     * Gets the Transaction Hash generated by the vault for the Crypto Transaction generated to send bitcoins.
     *
     * @param transactionId
     * @return
     * @throws com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions
     */
    String getSendCryptoTransactionHash(UUID transactionId) throws com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetSendCryptoTransactionHashException;

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
     * @throws com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions
     * @throws com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException
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
    Actors receptorActorType) throws OutgoingIntraActorCantSendFundsExceptions,
    OutgoingIntraActorInsufficientFundsException;
     */
}
