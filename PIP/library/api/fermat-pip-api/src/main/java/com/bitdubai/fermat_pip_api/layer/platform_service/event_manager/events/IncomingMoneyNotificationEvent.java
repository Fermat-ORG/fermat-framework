package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
//import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events.*;

/**
 * Created by Matias Furszyfer on 2015.09.03..
 */
public class IncomingMoneyNotificationEvent extends AbstractFermatEvent {


    private String walletPublicKey;
    private long amount;
    private CryptoCurrency cryptoCurrency;
    private String actorId;
    private Actors actorType;
    private String intraUserIdentityPublicKey;
    private String transactionHash;


    public IncomingMoneyNotificationEvent(EventType eventType, String intraUserIdentityPublicKey, String walletPublicKey, long amount, CryptoCurrency cryptoCurrency, String actorId, Actors actorType, String transactionHash) {
        super(eventType);
        this.walletPublicKey = walletPublicKey;
        this.amount = amount;
        this.cryptoCurrency = cryptoCurrency;
        this.actorId = actorId;
        this.actorType = actorType;
        this.intraUserIdentityPublicKey = intraUserIdentityPublicKey;
        this.transactionHash = transactionHash;
    }

    public IncomingMoneyNotificationEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType) {
        super(eventType);
    }


    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    public String getIntraUserIdentityPublicKey() {
        return intraUserIdentityPublicKey;
    }

    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }

    public void setIntraUserIdentityPublicKey(String intraUserIdentityPublicKey) {
        this.intraUserIdentityPublicKey = intraUserIdentityPublicKey;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public Actors getActorType() {
        return actorType;
    }

    public void setActorType(Actors actorType) {
        this.actorType = actorType;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }


}
