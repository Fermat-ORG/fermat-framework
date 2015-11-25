package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.Unhold.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;

import java.util.UUID;

/**
 * Created by Franklin Marcano on 21/11/2015.
 */
public interface CryptoTransaction {
    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    UUID getTransactionId();
    void setTransactionId(UUID transactionId);

    /**
     * Returns the public key of the CRYPTO wallet linked to this transaction
     * @return      Public key of the CRYPTO wallet
     */
    String getPublicKeyWallet();
    void   setPublicKeyWallet(String publicKeyWallet);

    /**
     * Returns the public key of the Actor which made the transaction
     * @return      Public key of the Actor which made the transaction
     */
    String getPublicKeyActor();
    void   setPublicKeyActor(String publicKeyActor);

    /**
     * Returns the public key of the Plugin which made the transaction
     * @return      Public key of the Plugin which made the transaction
     */
    String getPublicKeyPlugin();
    void   setPublicKeyPlugin(String publicKeyPlugin);

    /**
     * Returns the amount of the transaction
     * @return      Amount of the transaction
     */
    float getAmount();
    void  setAmount(float amount);

    /**
     * Returns the currency of the transaction
     * @return      Currency of the transaction
     */
    CryptoCurrency getCurrency();
    void           setCurrency(CryptoCurrency currency);

    /**
     * Returns the memo of the transaction
     * @return      Memo of the transaction
     */
    String getMemo();
    void   setMemo(String memo);

}
