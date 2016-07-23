package com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseType;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/12/15.
 */
public class ContractClauseRecord implements ContractClause, Serializable {

    UUID clauseId;
    ContractClauseType type;
    Integer executionOrder;
    ContractClauseStatus status;

    @Override
    public UUID getClauseId() {
        return this.clauseId;
    }

    @Override
    public ContractClauseType getType() {
        return this.type;
    }

    @Override
    public Integer getExecutionOrder() {
        return this.executionOrder;
    }

    @Override
    public ContractClauseStatus getStatus() {
        return this.status;
    }

    public void setClauseId(UUID clauseId) {
        this.clauseId = clauseId;
    }

    public void setType(ContractClauseType type) {
        this.type = type;
    }

    public void setExecutionOrder(Integer executionOrder) {
        this.executionOrder = executionOrder;
    }

    public void setStatus(ContractClauseStatus status) {
        this.status = status;
    }

}
