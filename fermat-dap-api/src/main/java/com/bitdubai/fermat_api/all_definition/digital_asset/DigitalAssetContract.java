package com.bitdubai.fermat_api.all_definition.digital_asset;

import com.bitdubai.fermat_api.all_definition.contracts.Contract;
import com.bitdubai.fermat_api.all_definition.contracts.ContractProperty;
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
        redeemable = new ContractProperty(ContractPropertyName.REDEEMABLE.toString(), Boolean.TRUE);
        transferable = new ContractProperty(ContractPropertyName.TRANSFERABLE.toString(), Boolean.TRUE);
        expirationDate= new ContractProperty(ContractPropertyName.EXPIRATION_DATE.toString(), Date.valueOf(""));
        properties.add(redeemable);
        properties.add(transferable);
        properties.add(expirationDate);
    }

    @Override
    public List<ContractProperty> getContractProperties() {
        return properties;
    }

    @Override
    public void setContractProperty(String name, Object value) {
        for (int i=0;i<properties.size();i++){
            if (properties.get(i).getName() == name){
                properties.get(i).setValue(value);
            }
        }
    }
}
