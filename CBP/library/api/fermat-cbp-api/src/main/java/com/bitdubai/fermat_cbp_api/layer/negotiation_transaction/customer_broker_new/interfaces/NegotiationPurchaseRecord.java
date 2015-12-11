package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 10.12.15.
 */
public class NegotiationPurchaseRecord implements CustomerBrokerPurchaseNegotiation {

    String              customerPublicKey;
    
    String              brokerPublicKey;

    UUID                negotiationId;

    Long                startDate;

    Long                lastNegotiationUpdateDate;

    Long                negotiationExpirationDate;
            
    NegotiationStatus   status;
            
    Collection<Clause>  clauses;

    String              cancelReason;

    String              memo;

    public String getCustomerPublicKey(){
        return this.customerPublicKey;
    }

    public String getBrokerPublicKey(){
        return this.brokerPublicKey;
    }

    public UUID getNegotiationId(){
        return this.negotiationId;
    }

    public Long getStartDate(){
        return this.startDate;
    }

    public Long getLastNegotiationUpdateDate(){
        return this.lastNegotiationUpdateDate;
    }

    public void setLastNegotiationUpdateDate(Long lastNegotiationUpdateDate){
        this.lastNegotiationUpdateDate = lastNegotiationUpdateDate;
    }

    public Long getNegotiationExpirationDate(){
        return this.negotiationExpirationDate;
    }

    public NegotiationStatus getStatus(){
        return this.status;
    }

    public Collection<Clause> getClauses() throws CantGetListClauseException{
        return this.clauses;
    }

    public void setCancelReason(String cancelReason){
        this.cancelReason = cancelReason;
    }

    public String getCancelReason(){
        return this.cancelReason;
    }

    public void setMemo(String memo){
        this.memo = memo;
    }

    public String getMemo(){
        return this.memo;
    }

}
