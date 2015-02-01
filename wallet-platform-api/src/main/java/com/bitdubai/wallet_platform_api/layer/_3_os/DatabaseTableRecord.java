package com.bitdubai.wallet_platform_api.layer._3_os;

import java.util.List;

/**
 * Created by ciencias on 01.02.15.
 */
public interface DatabaseTableRecord {
    
    public List<String> getValues();
    
    public void setValues (List<String> values);
    
}
