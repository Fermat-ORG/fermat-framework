package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.interfaces;

/**
 * Created by Natalia on 27/03/2015.
 */
public interface DataBaseTableOrder {

    public void setColumName(String column);

    public void setDirection (DatabaseFilterOrder direction);

    public String getColumName();

    public DatabaseFilterOrder getDirection();
}
