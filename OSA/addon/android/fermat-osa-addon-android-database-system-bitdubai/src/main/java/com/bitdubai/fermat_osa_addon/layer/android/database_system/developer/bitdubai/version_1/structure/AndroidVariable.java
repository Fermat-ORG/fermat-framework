package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseVariable;

/**
 * Created by natalia on 07/07/15.
 */
public class AndroidVariable implements DatabaseVariable {

    /**
     * DatabaseVariable Interface member variables.
     */

    private String variableName;
    private String variableValue;



    /**
     * DatabaseVariable interface implementation.
     */

    /**
     * <p>Returns record field name
     *
     * @return String record name
     */
    @Override
    public String getName(){
        return this.variableName;
    }

    /**
     *<p>Returns record field value
     *
     * @return String record value
     */
    @Override
    public String getValue(){
        return this.variableValue;
    }



    /**
     * Set the field record name
     * @param name field name
     */
    @Override
    public void setName (String name){
        this.variableName = name;
    }

    /**
     * Set the field record value
     * @param value field value
     */
    @Override
    public void setValue (String value){
        this.variableValue = value;
    }




    @Override
    public String toString(){
        return variableName + "=" + variableValue;
    }
}
