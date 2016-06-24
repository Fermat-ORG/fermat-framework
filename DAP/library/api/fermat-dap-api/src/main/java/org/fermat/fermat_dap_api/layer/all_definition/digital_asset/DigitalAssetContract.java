package org.fermat.fermat_dap_api.layer.all_definition.digital_asset;

import org.fermat.fermat_dap_api.layer.all_definition.contracts.Contract;
import org.fermat.fermat_dap_api.layer.all_definition.contracts.ContractProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 9/4/15.
 */
public class DigitalAssetContract implements Contract, Serializable {
    List<ContractProperty> properties;

    /**
     * Default constructor sets default values for properties
     */
    public DigitalAssetContract() {
        properties = new ArrayList<>();
        properties.add(new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, true));
        properties.add(new ContractProperty(DigitalAssetContractPropertiesConstants.TRANSFERABLE, true));
        properties.add(new ContractProperty(DigitalAssetContractPropertiesConstants.SALEABLE, true));
        properties.add(new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, null));
    }

    @Override
    public List<ContractProperty> getContractProperties() {
        return properties;
    }

    @Override
    public ContractProperty getContractProperty(String propertyName) {
        ContractProperty returnedContractProperty = null;
        for (ContractProperty contractProperty : properties) {
            if (contractProperty.getName().equals(propertyName))
                returnedContractProperty = contractProperty;
        }

        return returnedContractProperty;
    }

    @Override
    public void addPropertyValue(String propertyName, Object propertyValue) throws org.fermat.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException {
        boolean isExistingProperty = false;
        for (ContractProperty property : properties) {
            if (property.getName().equals(propertyName)) {
                property.setValue(propertyValue);
                isExistingProperty = true;
            }
        }
        if (!isExistingProperty)
            throw new org.fermat.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException(org.fermat.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException.DEFAULT_MESSAGE, null, "Property " + propertyName + " does not exists in the contract.", null);
    }

}
