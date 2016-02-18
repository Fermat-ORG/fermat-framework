package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;

/**
 * Created by nelson on 17/02/16.
 */
public class NegotiationWrapper {

    private CustomerBrokerNegotiationInformation negotiationInformation;
    private ClauseStatus expirationTimeStatus;
    private boolean expirationTimeConfirmButtonClicked;

    public CustomerBrokerNegotiationInformation getNegotiationInformation(){
        return negotiationInformation;
    }

    public ClauseStatus getExpirationTimeStatus() {
        return expirationTimeStatus;
    }

    public boolean isExpirationTimeConfirmButtonClicked() {
        return expirationTimeConfirmButtonClicked;
    }

    public void setExpirationTimeConfirmButtonClicked(boolean expirationTimeConfirmButtonClicked) {
        this.expirationTimeConfirmButtonClicked = expirationTimeConfirmButtonClicked;
    }

    public void setNegotiationInformation(CustomerBrokerNegotiationInformation negotiationInformation) {
        this.negotiationInformation = negotiationInformation;
    }


    public boolean haveNote() {
        if (negotiationInformation.getMemo() == null)
            return false;

        return !negotiationInformation.getMemo().isEmpty();
    }
}
