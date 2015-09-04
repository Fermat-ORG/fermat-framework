package com.bitdubai.fermat_api.all_definition.contracts;

import java.util.List;


/**
 * Created by rodrigo on 9/4/15.
 */
public interface Contract {
    List<ContractProperty> getContractProperties();
    void setContractProperty (String property, Object value);
}
