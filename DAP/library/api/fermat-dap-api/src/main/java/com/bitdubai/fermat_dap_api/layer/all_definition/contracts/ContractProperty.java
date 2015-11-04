package com.bitdubai.fermat_dap_api.layer.all_definition.contracts;


/**
 * Created by rodrigo on 9/4/15.
 */
public class ContractProperty {
    String name;
    Object value;


    public ContractProperty(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
