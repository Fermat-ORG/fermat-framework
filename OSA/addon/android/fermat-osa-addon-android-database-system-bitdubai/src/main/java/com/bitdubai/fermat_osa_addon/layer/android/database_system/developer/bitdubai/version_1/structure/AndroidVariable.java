package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

/**
 * Created by natalia on 07/07/15.
 * Modified by Leon Acosta (laion.cj91@gmail.com.ar) on 03/02/2016.
 */
public class AndroidVariable {

    private final String variableName;
    private final String variableValue;

    public AndroidVariable(String variableName,
                           String variableValue) {

        this.variableName = variableName;
        this.variableValue = variableValue;
    }

    public String getName() {
        return this.variableName;
    }

    public String getValue() {
        return this.variableValue;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(variableName).append("=").append(variableValue).toString();
    }
}
