package com.bitdubai.fermat_api.layer.osa_android.device_power;


/**
 * Created by Natalia on 04/05/2015.
 */
public interface PowerManager {

    int getLevel();

    PowerStatus getStatus();

    PlugType getPlugType();

    void setContext(Object context);
}
