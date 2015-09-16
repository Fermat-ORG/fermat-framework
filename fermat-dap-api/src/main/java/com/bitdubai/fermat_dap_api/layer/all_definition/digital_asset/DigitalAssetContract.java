package com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset;

import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.ContractProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 9/4/15.
 */
public class DigitalAssetContract implements com.bitdubai.fermat_dap_api.layer.all_definition.contracts.Contract {
    ContractProperty redeemable;
    ContractProperty transferable;
    ContractProperty expirationDate;
    List<ContractProperty> properties;

    /**
     * Default constructor sets default values for properties
     */
    public DigitalAssetContract() {
        properties = new ArrayList<>();
        redeemable = new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, null);
        transferable = new ContractProperty(DigitalAssetContractPropertiesConstants.TRANSFERABLE, null);
        expirationDate= new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE.toString(), null);
        properties.add(redeemable);
        properties.add(transferable);
        properties.add(expirationDate);
    }

    @Override
    public List<ContractProperty> getContractProperties() {
        return properties;
    }

    @Override
    public void setContractProperty(ContractProperty contractProperty) throws com.bitdubai.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException {
        boolean isExistingProperty = false;
        for (ContractProperty property : properties){
            if (contractProperty.getName() == property.getName()){
                property.setValue(contractProperty.getValue());
                isExistingProperty = true;
            }

            if (!isExistingProperty)
                throw new com.bitdubai.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException(com.bitdubai.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException.DEFAULT_MESSAGE, null, "Property " + contractProperty.toString() + " does not exists in the contract.", null);
        }
    }

    @Override
    public ContractProperty getContractProperty(String propertyName) {
        ContractProperty returnedContractProperty = null;
        for (ContractProperty contractProperty : properties){
            if (contractProperty.getName() == propertyName)
                returnedContractProperty = contractProperty;
        }

        return returnedContractProperty;
    }
}
