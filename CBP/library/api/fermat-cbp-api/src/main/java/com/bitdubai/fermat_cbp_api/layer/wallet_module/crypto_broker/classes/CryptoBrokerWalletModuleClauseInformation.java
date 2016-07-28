package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.classes;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;

import java.io.Serializable;
import java.util.UUID;


/**
 * Created by Nelson Ramirez
 *
 * @since 05/11/15.
 */
public class CryptoBrokerWalletModuleClauseInformation implements ClauseInformation, Serializable {
    private ClauseType clauseType;
    private String value;
    private ClauseStatus status;
    private UUID clauseId;

    public CryptoBrokerWalletModuleClauseInformation(ClauseType clauseType, String value, ClauseStatus status) {
        this.clauseType = clauseType;
        this.value = value;
        this.status = status;

        clauseId = UUID.randomUUID();
    }

    public CryptoBrokerWalletModuleClauseInformation(Clause clause) {
        this.clauseType = clause.getType();
        this.value = clause.getValue();
        this.status = ClauseStatus.DRAFT; // TODO verificar esto: clause.getStatus();
        this.clauseId = clause.getClauseId();
    }

    public CryptoBrokerWalletModuleClauseInformation(ClauseInformation clauseInformation) {
        this.clauseType = clauseInformation.getType();
        this.value = clauseInformation.getValue();
        this.status = clauseInformation.getStatus();
        this.clauseId = clauseInformation.getClauseID();
    }

    @Override
    public UUID getClauseID() {
        return clauseId;
    }

    @Override
    public ClauseType getType() {
        return clauseType;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public ClauseStatus getStatus() {
        return status;
    }

    public void setStatus(ClauseStatus status) {
        this.status = status;
    }

    public void setClauseType(ClauseType clauseType) {
        this.clauseType = clauseType;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setClauseId(UUID clauseId) {
        this.clauseId = clauseId;
    }
}
