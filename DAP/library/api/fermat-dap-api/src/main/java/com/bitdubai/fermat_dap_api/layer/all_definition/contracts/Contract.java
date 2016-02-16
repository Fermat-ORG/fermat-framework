package com.bitdubai.fermat_dap_api.layer.all_definition.contracts;

import java.util.List;


/**
 * Created by rodrigo on 9/4/15.
 */
public interface Contract {
    List<ContractProperty> getContractProperties();
    ContractProperty getContractProperty(String propertyName);

    void addPropertyValue(String propertyName, Object propertyValue) throws com.bitdubai.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException;
}
