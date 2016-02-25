package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractDetailType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 21/01/16.
 * Modified by Alejandro Bicelis on 22/02/2016
 */
public class ContractDetail {


    int contractStep;
    ContractStatus contractStatus;
    UUID contractId;

    public ContractDetail(int contractStep, ContractStatus contractStatus, UUID contractId) {
        this.contractStep = contractStep;
        this.contractStatus = contractStatus;
        this.contractId = contractId;
    }

    public int getContractStep() {return contractStep;}
    public void setContractStep(int contractStep) {this.contractStep = contractStep;}

    public ContractStatus getContractStatus() {return contractStatus;}
    public void setContractStatus(ContractStatus contractStatus) {this.contractStatus = contractStatus;}


    public UUID getContractId() {return contractId;}
    public void setContractId(UUID contractId) {this.contractId = contractId;}





}
