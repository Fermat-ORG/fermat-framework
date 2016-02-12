package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;

/**
 * Created by Matias Furszyfer on 2015.12.22..
 */
public class OutgoingIntraRollbackNotificationEvent extends AbstractFermatEvent {

        private String walletPublicKey;
        private long amount;
        private CryptoStatus cryptoCurrency;
        private String actorId;
        private Actors actorType;
        private String intraUserIdentityPublicKey;


        public OutgoingIntraRollbackNotificationEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType, String intraUserIdentityPublicKey,String walletPublicKey, long amount, CryptoStatus cryptoCurrency, String actorId, Actors actorType) {
            super(eventType);
            this.walletPublicKey = walletPublicKey;
            this.amount = amount;
            this.cryptoCurrency = cryptoCurrency;
            this.actorId = actorId;
            this.actorType = actorType;
            this.intraUserIdentityPublicKey = intraUserIdentityPublicKey;
        }

        public OutgoingIntraRollbackNotificationEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType) {
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

        public CryptoStatus getCryptoStatus() {
            return cryptoCurrency;
        }

        public void setCryptoStatus(CryptoStatus cryptoCurrency) {
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
}
