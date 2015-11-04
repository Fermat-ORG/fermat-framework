package com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset;

import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.Contract;
import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 9/4/15.
 */
public class DigitalAssetContract implements Contract {
    ContractProperty redeemable;
    ContractProperty expirationDate;
    List<ContractProperty> properties;

    /**
     * Default constructor sets default values for properties
     */
    public DigitalAssetContract() {
        properties = new ArrayList<>();
        redeemable = new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, null);
        expirationDate= new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, null);
        properties.add(redeemable);
        properties.add(expirationDate);
    }

    @Override
    public List<ContractProperty> getContractProperties() {
        return properties;
    }

    @Override
    public void setContractProperty(ContractProperty contractProperty) throws CantDefineContractPropertyException {
        boolean isExistingProperty = false;
        for (ContractProperty property : properties){
            if (property.getName().equals(contractProperty.getName())){
                property.setValue(contractProperty.getValue());
                isExistingProperty = true;
            }
        }
        if (!isExistingProperty)
            throw new CantDefineContractPropertyException(CantDefineContractPropertyException.DEFAULT_MESSAGE, null, "Property " + contractProperty.toString() + " does not exists in the contract.", null);
    }

    @Override
    public ContractProperty getContractProperty(String propertyName) {
        ContractProperty returnedContractProperty = null;
        for (ContractProperty contractProperty : properties){
            if (contractProperty.getName().equals(propertyName))
                returnedContractProperty = contractProperty;
        }

        return returnedContractProperty;
    }

}
