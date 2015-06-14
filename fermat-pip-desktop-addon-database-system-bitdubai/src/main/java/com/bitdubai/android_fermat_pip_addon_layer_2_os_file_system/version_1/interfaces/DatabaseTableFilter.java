package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.interfaces;

/**
 * Created by ciencias on 01.02.15.
 */
public interface DatabaseTableFilter {
    
    
    public void setColumn(String column);

    public void setType (DatabaseFilterType type);

    public void setValue(String value);

    public String  getColumn();

    public String getValue();

    public DatabaseFilterType getType();
    
}
