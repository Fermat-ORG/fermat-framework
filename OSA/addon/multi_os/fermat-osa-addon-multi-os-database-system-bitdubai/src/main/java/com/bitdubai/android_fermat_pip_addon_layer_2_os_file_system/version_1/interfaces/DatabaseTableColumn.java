package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.interfaces;

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
