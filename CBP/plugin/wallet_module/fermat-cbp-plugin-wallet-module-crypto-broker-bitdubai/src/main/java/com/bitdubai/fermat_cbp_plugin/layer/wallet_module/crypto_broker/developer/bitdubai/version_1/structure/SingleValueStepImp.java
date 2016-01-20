package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.SingleValueStep;

/**
 * Created by nelson on 11/12/15.
 */
public class SingleValueStepImp implements SingleValueStep {
    private int stepNumber;
    private NegotiationStepType type;
    private String value;
    private NegotiationStepStatus status;

    public SingleValueStepImp(int stepNumber, NegotiationStepType type, String value) {
        this.stepNumber = stepNumber;
        this.type = type;
        this.value = value;
        status = NegotiationStepStatus.CONFIRM;
    }

    @Override
    public NegotiationStepType getType() {
        return type;
    }

    @Override
    public NegotiationStepStatus getStatus() {
        return status;
    }

    @Override
    public int getStepNumber() {
        return stepNumber;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setStatus(NegotiationStepStatus status) {
        this.status = status;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
