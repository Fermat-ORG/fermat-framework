/*
* @#DesktopRecord.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure.DesktopRecord</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DesktopRecord  implements DatabaseRecord {

    /**
     * DatabaseRecord Interface member variables.
     */

    private String recordName;
    private String recordValue;
    private boolean recordChange;


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

    @Override
    public boolean getUseValueofVariable() {
        return false;
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

    @Override
    public void setUseValueofVariable(boolean ifvariable) {

    }

}
