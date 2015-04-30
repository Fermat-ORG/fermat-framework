package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.device_location;

import com.bitdubai.fermat_api.layer._2_os.device_location.Location;

/**
 * Created by Natalia on 30/04/2015.
 */
public class AndroidLocation implements Location {

    private double lat;
    private double lng;

    public double getLatitude(){
        return lat;
    }

    public double getLongitude(){
        return lng;
    }

    public void setLatitude(double lat){
        this.lat = lat;
    }

    public void setLongitude (double lng){
        this.lng = lng;
    }
}
