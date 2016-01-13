package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by nelson on 10/01/16.
 */
//TODO Revisar Franklin
public class EmptyCustomerBrokerNegotiationInformation implements CustomerBrokerNegotiationInformation {
    private Long lastNegotiationUpdateDate;
    private ActorIdentity customer;
    private ActorIdentity broker;
    private Map<ClauseType, String> negotiationSummary;
    private Map<ClauseType, ClauseInformation> clauses;
    private NegotiationStatus status;
    private String memo;
    private UUID negotiationId;
    private long negotiationExpirationDate;
    private String cancelReason;

    public EmptyCustomerBrokerNegotiationInformation() {
        negotiationSummary = new HashMap<>();
        clauses = new HashMap<>();
        negotiationId = UUID.randomUUID();
    }

    public ClauseInformation putClause(final ClauseType clauseType, final String value) {
        ClauseInformation clauseInformation = new ClauseInformation() {
            @Override
            public UUID getClauseID() {
                return UUID.randomUUID();
            }

            @Override
            public ClauseType getType() {
                return clauseType;
            }

            @Override
            public String getValue() {
                return (value != null) ? value : "";
            }

            @Override
            public ClauseStatus getStatus() {
                return ClauseStatus.SENT_TO_BROKER;
            }
        };

        clauses.put(clauseType, clauseInformation);

        return clauseInformation;
    }

    public ClauseInformation putClause(final ClauseInformation clause, final String value) {
        final ClauseType type = clause.getType();

        ClauseInformation clauseInformation = new ClauseInformation() {
            @Override
            public UUID getClauseID() {
                return clause.getClauseID();
            }

            @Override
            public ClauseType getType() {
                return type;
            }

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public ClauseStatus getStatus() {
                return clause.getStatus();
            }
        };

        clauses.put(type, clauseInformation);

        return clauseInformation;
    }

    @Override
    public ActorIdentity getCustomer() {
        return customer;
    }

    @Override
    public ActorIdentity getBroker() {
        return broker;
    }

    @Override
    public Map<ClauseType, String> getNegotiationSummary() {
        return negotiationSummary;
    }

    @Override
    public Map<ClauseType, ClauseInformation> getClauses() {
        return clauses;
    }

    @Override
    public NegotiationStatus getStatus() {
        return status;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public long getLastNegotiationUpdateDate() {
        return lastNegotiationUpdateDate;
    }

    @Override
    public void setLastNegotiationUpdateDate(Long setLastNegotiationUpdateDate) {
        this.lastNegotiationUpdateDate = setLastNegotiationUpdateDate;
    }

    @Override
    public long getNegotiationExpirationDate() {
        return negotiationExpirationDate;
    }

    @Override
    public UUID getNegotiationId() {
        return negotiationId;
    }

    @Override
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    @Override
    public String getCancelReason() {
        return cancelReason;
    }

    public void setCustomer(ActorIdentity customer) {
        this.customer = customer;
    }

    public void setBroker(ActorIdentity broker) {
        this.broker = broker;
    }

    public void setStatus(NegotiationStatus status) {
        this.status = status;
    }

    public void setNegotiationExpirationDate(long negotiationExpirationDate) {
        this.negotiationExpirationDate = negotiationExpirationDate;
    }
}
