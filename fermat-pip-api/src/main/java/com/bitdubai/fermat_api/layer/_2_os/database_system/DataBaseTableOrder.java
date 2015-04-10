package com.bitdubai.fermat_api.layer._2_os.database_system;

/**
 * Created by Natalia on 27/03/2015.
 */
public interface DataBaseTableOrder {

    public void setColumName (String column);

    public void setDirection (DatabaseFilterOrder direction);

    public String getColumName ();

    public DatabaseFilterOrder getDirection ();
}
