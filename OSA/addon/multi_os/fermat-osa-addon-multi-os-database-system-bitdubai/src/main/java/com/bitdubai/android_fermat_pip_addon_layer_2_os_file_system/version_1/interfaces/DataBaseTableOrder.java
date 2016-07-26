package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.interfaces;

/**
 * Created by Natalia on 27/03/2015.
 */
public interface DataBaseTableOrder {

    public void setColumName(String column);

    public void setDirection(DatabaseFilterOrder direction);

    public String getColumName();

    public DatabaseFilterOrder getDirection();
}
