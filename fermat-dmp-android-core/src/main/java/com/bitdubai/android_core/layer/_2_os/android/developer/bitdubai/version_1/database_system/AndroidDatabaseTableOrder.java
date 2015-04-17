package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.database_system;

import com.bitdubai.fermat_api.layer._2_os.database_system.DataBaseTableOrder;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseFilterOrder;


/**
 * Created by Natalia on 27/03/2015.
 */
public class AndroidDatabaseTableOrder implements DataBaseTableOrder {

    /**
     * DataBaseTableOrder Interface member variables.
     */
    private String columnName;
    private DatabaseFilterOrder direction;

    /**
     * DataBaseTableOrder interface implementation.
     */

    @Override
    public void setColumName (String columnName){
        this.columnName = columnName;
    }

    @Override
    public void setDirection (DatabaseFilterOrder direction){
        this.direction = direction;
    }

    @Override
    public String getColumName (){

        return this.columnName;
    }

    @Override
    public DatabaseFilterOrder getDirection (){
        return this.direction;
    }

}
