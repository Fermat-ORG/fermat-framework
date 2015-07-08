package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

/**
 * Created by Natalia on 25/03/2015.
 */

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;

/**
 * This class define methods to access the properties of the object Database Record
 * *
 */

public class AndroidRecord implements DatabaseRecord {

    /**
     * DatabaseRecord Interface member variables.
     */

    private String recordName;
    private String recordValue;
    private boolean recordChange;
    private boolean ifvariable;


    /**
     * DatabaseRecord interface implementation.
     */

    /**
     * <p>Returns record field name
     *
     * @return String record name
     */
    @Override
    public String getName(){
        return this.recordName;
    }

    /**
     *<p>Returns record field value
     *
     * @return String record value
     */
    @Override
    public String getValue(){
        return this.recordValue;
    }

    /**
     * Returns true if the record record was changed
     *
     * @return boolean if change
     */
    @Override
    public boolean getChange(){
        return this.recordChange;
    }

    /**
     * Returns true if the record record was changed
     *
     * @return boolean if change
     */
    @Override
    public boolean getUseValueofVariable(){
        return this.ifvariable;
    }

    /**
     * Set the field record name
     * @param name field name
     */
    @Override
    public void setName (String name){
        this.recordName = name;
    }

    /**
     * Set the field record value
     * @param value field value
     */
    @Override
    public void setValue (String value){
        this.recordValue = value;
    }


    /**
     * Set if the record was changed
     * @param change boolean if change
     */
    @Override
    public void setChange (boolean change){
        this.recordChange = change;
    }

    public void setUseValueofVariable (boolean ifvariable){
        this.ifvariable = ifvariable;
    }

    @Override
    public String toString(){
        return recordName + "=" + recordValue;
    }
}
