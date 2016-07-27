package com.bitdubai.fermat_cbp_api.all_definition.negotiation_transaction;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 10.12.15.
 */
public class NegotiationPurchaseRecord implements CustomerBrokerPurchaseNegotiation, Serializable {

    UUID negotiationId;
    String customerPublicKey;
    String brokerPublicKey;
    //    String              publicKeyCustomer;
//    String              publicKeyBroker;
    Long startDate;
    //    Long                startDataTime;
    Long negotiationExpirationDate;
    NegotiationStatus status;
    //    NegotiationStatus   statusNegotiation;
    Boolean nearExpirationDatetime;
    Collection<Clause> clauses;
    Long lastNegotiationUpdateDate;
    String cancelReason;
    String memo;
    boolean dataHasChanged;

    public UUID getNegotiationId() {
        return this.negotiationId;
    }

    //    public String getCustomerPublicKey(){ return this.publicKeyCustomer; }
    public String getCustomerPublicKey() {
        return this.customerPublicKey;
    }

    //    public String getBrokerPublicKey(){ return this.publicKeyBroker; }
    public String getBrokerPublicKey() {
        return this.brokerPublicKey;
    }

    //    public Long getStartDate(){ return this.startDataTime; }
    public Long getStartDate() {
        return this.startDate;
    }

    public Long getNegotiationExpirationDate() {
        return this.negotiationExpirationDate;
    }

    //    public NegotiationStatus getStatus(){return this.statusNegotiation; }
    public NegotiationStatus getStatus() {
        return this.status;
    }

    public Collection<Clause> getClauses() throws CantGetListClauseException {
        return this.clauses;
    }

    public Boolean getNearExpirationDatetime() {
        return nearExpirationDatetime;
    }

    public Long getLastNegotiationUpdateDate() {
        return this.lastNegotiationUpdateDate;
    }

    public void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate) {
        this.lastNegotiationUpdateDate = lastNegotiationUpdateDate;
    }

    public void setNearExpirationDatetime(Boolean nearExpirationDatetime) {
        this.nearExpirationDatetime = nearExpirationDatetime;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getCancelReason() {
        return this.cancelReason;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo() {
        return this.memo;
    }


    public boolean dataHasChanged() {
        return dataHasChanged;
    }

    public void changeInfo(CustomerBrokerNegotiationInformation negotiationInfo, NegotiationStatus status) {
    }

    public String toString() {
        return "";
    }

}
