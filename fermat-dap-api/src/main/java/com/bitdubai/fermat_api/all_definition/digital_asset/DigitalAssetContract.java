package com.bitdubai.fermat_api.all_definition.digital_asset;

import com.bitdubai.fermat_api.all_definition.contracts.Contract;
import com.bitdubai.fermat_api.all_definition.contracts.ContractProperty;
import com.bitdubai.fermat_api.all_definition.contracts.exceptions.CantDefineContractPropertyException;
import com.bitdubai.fermat_api.all_definition.digital_asset.enums.ContractPropertyName;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 9/4/15.
 */
public class DigitalAssetContract implements Contract {
    ContractProperty redeemable;
    ContractProperty transferable;
    ContractProperty expirationDate;
    List<ContractProperty> properties;

    /**
     * Default constructor sets default values for properties
     */
    public DigitalAssetContract() {
        properties = new ArrayList<>();
        redeemable = new ContractProperty(ContractPropertyName.REDEEMABLE.toString(), null);
        transferable = new ContractProperty(ContractPropertyName.TRANSFERABLE.toString(), null);
        expirationDate= new ContractProperty(ContractPropertyName.EXPIRATION_DATE.toString(), null);
        properties.add(redeemable);
        properties.add(transferable);
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
            if (contractProperty.getName() == property.getName()){
                property.setValue(contractProperty.getValue());
                isExistingProperty = true;
            }

            if (!isExistingProperty)
                throw new CantDefineContractPropertyException(CantDefineContractPropertyException.DEFAULT_MESSAGE, null, "Property " + contractProperty.toString() + " does not exists in the contract.", null);
        }
    }
}
