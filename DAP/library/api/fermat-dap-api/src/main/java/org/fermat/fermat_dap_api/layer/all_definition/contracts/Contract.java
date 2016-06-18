package org.fermat.fermat_dap_api.layer.all_definition.contracts;

import org.fermat.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException;

import java.io.Serializable;
import java.util.List;


/**
 * Created by rodrigo on 9/4/15.
 */
public interface Contract extends Serializable {
    List<ContractProperty> getContractProperties();

    ContractProperty getContractProperty(String propertyName);

    void addPropertyValue(String propertyName, Object propertyValue) throws CantDefineContractPropertyException;
}
