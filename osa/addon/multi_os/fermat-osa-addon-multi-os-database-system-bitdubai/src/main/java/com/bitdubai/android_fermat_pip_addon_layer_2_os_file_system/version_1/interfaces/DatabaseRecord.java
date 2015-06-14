package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.interfaces;

/**
 * Created by Natalia on 25/03/2015.
 */
public interface DatabaseRecord {

    public String getName();

    public String getValue();

    public boolean getChange();

    public void setName(String name);

    public void setValue(String value);

    public void setChange(boolean change);

}
