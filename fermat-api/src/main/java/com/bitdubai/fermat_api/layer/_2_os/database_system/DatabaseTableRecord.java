package com.bitdubai.fermat_api.layer._2_os.database_system;

import java.util.List;

/**
 * Created by ciencias on 01.02.15.
 */
public interface DatabaseTableRecord {
    
    public List<String> getValues();
    
    public void setValues (List<String> values);
    
}
