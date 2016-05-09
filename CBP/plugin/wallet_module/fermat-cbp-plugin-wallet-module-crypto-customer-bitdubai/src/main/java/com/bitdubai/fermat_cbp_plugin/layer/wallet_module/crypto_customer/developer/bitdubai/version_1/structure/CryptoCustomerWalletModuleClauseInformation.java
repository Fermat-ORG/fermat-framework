package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;

import java.util.UUID;

/**
 * Created by nelson on 05/11/15.
 */
public class CryptoCustomerWalletModuleClauseInformation implements ClauseInformation {
    private ClauseType clauseType;
    private String value;
    private ClauseStatus status;

    public CryptoCustomerWalletModuleClauseInformation(ClauseType clauseType, String value, ClauseStatus status) {
        this.clauseType = clauseType;
        this.value = value;
        this.status = status;
    }

    public CryptoCustomerWalletModuleClauseInformation(Clause clause) {
        this.clauseType = clause.getType();

        switch (clause.getType()){
            case CUSTOMER_PAYMENT_METHOD:
                try {
                    this.value = MoneyType.getByCode(clause.getValue()).getFriendlyName();
                } catch (InvalidParameterException e) {}
            break;
            case BROKER_PAYMENT_METHOD:
                try {
                    this.value = MoneyType.getByCode(clause.getValue()).getFriendlyName();
                } catch (InvalidParameterException e) {}
            break;
            default:
                this.value = clause.getValue();
            break;
        }

        this.status = clause.getStatus();
    }

    @Override
    public UUID getClauseID() {
        return null;
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
