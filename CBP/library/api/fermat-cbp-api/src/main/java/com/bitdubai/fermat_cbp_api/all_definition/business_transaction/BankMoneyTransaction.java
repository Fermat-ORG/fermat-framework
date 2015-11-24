package com.bitdubai.fermat_cbp_api.all_definition.business_transaction;


import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by franklin on 16/11/15.
 */
public interface BankMoneyTransaction {
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

    FiatCurrency getFiatCurrency();
    void setFiatCurrency(FiatCurrency fiatCurrency);

    String getCbpWalletPublicKey();
    void setCbpWalletPublicKey(String cbpWalletPublicKey);

    String getBnkWalletPublicKey();
    void setBnkWalletPublicKey(String bnkWalletPublicKey);

    String getBankAccount();
    void setBankAccount(String bankAccount);

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
