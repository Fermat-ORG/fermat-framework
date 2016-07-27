package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.interfaces;

/**
 * Created by ciencias on 01.02.15.
 */
public interface DatabaseTableFilter {


    public void setColumn(String column);

    public void setType(DatabaseFilterType type);

    public void setValue(String value);

    public String getColumn();

    public String getValue();

    public DatabaseFilterType getType();

}
