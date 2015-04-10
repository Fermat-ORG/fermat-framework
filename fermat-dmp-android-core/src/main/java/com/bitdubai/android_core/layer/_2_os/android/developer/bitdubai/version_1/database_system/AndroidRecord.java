package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.database_system;

/**
 * Created by Natalia on 25/03/2015.
 */

import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseRecord;

public class AndroidRecord implements DatabaseRecord {

    /**
     * DatabaseRecord Interface member variables.
     */

    private String recordName;
    private String recordValue;
    private boolean recordChange;


    /**
     * DatabaseRecord interface implementation.
     */

    @Override
    public String getName(){
        return this.recordName;
    }

    @Override
    public String getValue(){
        return this.recordValue;
    }

    @Override
    public boolean getChange(){
        return this.recordChange;
    }

    @Override
    public void setName (String name){
        this.recordName = name;
    }

    @Override
    public void setValue (String value){
        this.recordValue = value;
    }


    @Override
    public void setChange (boolean change){
        this.recordChange = change;
    }
}
