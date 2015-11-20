package com.bitdubai.fermat_dap_api.layer.all_definition.contracts;


/**
 * The Class <code>com.bitdubai.fermat_dap_api.layer.all_definition.contracts.developer.bitdubai.version_1.ContractProperty</code>
 * defines a Property assigned to a contract defined at a Digital Asset.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 06/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ContractProperty {
    String name;
    Object value;


    /**
     * Overloaded constructor
     * @param name the Contract property name
     * @param value the Contract property value
     */
    public ContractProperty(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Default Constructor
     */
    public ContractProperty() {
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
