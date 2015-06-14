package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.interfaces;

/**
 * Created by ciencias on 01.02.15.
 */
public interface DatabaseTableColumn {
    
    public String getName();
    
    public void setName(String name);

    public DatabaseDataType getType();

    public void setType(DatabaseDataType type);

    public void setDataTypeSize(int dataTypeSize);

    public int getDataTypeSize();

    public void setPrimaryKey(boolean primaryKey);

    public boolean getPrimaryKey();
    
}
