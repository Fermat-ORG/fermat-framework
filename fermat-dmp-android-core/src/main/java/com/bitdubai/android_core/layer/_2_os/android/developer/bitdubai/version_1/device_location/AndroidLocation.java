package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.device_location;

import com.bitdubai.fermat_api.layer._2_os.device_location.Location;
import com.bitdubai.fermat_api.layer._2_os.device_location.LocationProvider;

/**
 * Created by Natalia on 30/04/2015.
 */
public class AndroidLocation implements Location {

    private double lat;
    private double lng;
    private long time;
    private double altitude;
    private LocationProvider provider;

   public AndroidLocation(double lat,double lng,long time,double altitude,LocationProvider provider){
      this.lat = lat;
       this.lng = lng;
       this.time = time;
       this.altitude = altitude;
       this.provider = provider;
   }

    public double getLatitude(){
        return lat;
    }

    public double getLongitude(){
        return lng;
    }

    public double getAltitude(){
        return altitude;
    }

    public long getTime(){
        return time;
    }

    public LocationProvider getProvider(){
        return this.provider;
    }


}
