package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;

import java.util.UUID;


/**
 * Created by Nelson Ramirez
 * @since 05/11/15.
 */
public class CryptoBrokerWalletModuleClauseInformation implements ClauseInformation {
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
}
