package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.device_connectivity;

import com.bitdubai.fermat_api.layer._2_os.device_connectivity.ConnectionType;
import com.bitdubai.fermat_api.layer._2_os.device_connectivity.Network;

/**
 * Created by Natalia on 06/05/2015.
 */
public class AndroidNetwork implements Network {

    /**
     * Network Interface member variables.
     */

    private ConnectionType type;
    private boolean isconnected;

    /**
     * Network Interface implementation.
     */
    public ConnectionType getType(){
        return this.type;
    }

    public boolean getIsConnected(){
        return this.isconnected;
    }


    public void setType(ConnectionType type){

        this.type = type;
    }

    public void setIsConnected(boolean connected){
        this.isconnected = connected;
    }
}
