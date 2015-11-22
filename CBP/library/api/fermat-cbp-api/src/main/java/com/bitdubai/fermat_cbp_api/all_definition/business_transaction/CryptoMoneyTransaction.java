package com.bitdubai.fermat_cbp_api.all_definition.business_transaction;


import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by franklin on 16/11/15.
 */
public interface CryptoMoneyTransaction {
    //TODO: Documentar
    /**
     * The property <code>TransactionId</code>  represented identifier the record
     *
     * @return the UUID
     */
    UUID getTransactionId();
    void setTransactionId(UUID transactionId);

    /**
     * The property <code>ActorPublicKey</code>  represented the public key Actor
     *
     * @return the Sting
     */
    String getActorPublicKey();
    void setActorPublicKey(String actorPublicKey);

    CryptoCurrency getCryptoCurrency();
    void setCryptoCurrency(CryptoCurrency cryptoCurrency);

    String getCbpWalletPublicKey();
    void setCbpWalletPublicKey(String cbpWalletPublicKey);

    String getCryWalletPublicKey();
    void setCryWalletPublicKey(String cryWalletPublicKey);

    String getConcept();
    void setConcept(String concept);

    float getAmount();
    void setAmount(float amount);

    Timestamp getTimeStamp();
    void setTimeStamp(Timestamp timeStamp);

    String getMemo();
    void setMemo(String memo);

    TransactionStatusRestockDestock getTransactionStatus();
    void setTransactionStatus(TransactionStatusRestockDestock transactionStatus);

}
