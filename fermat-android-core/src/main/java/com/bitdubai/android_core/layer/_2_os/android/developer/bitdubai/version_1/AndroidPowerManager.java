package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.osa_android.device_power.PlugType;
import com.bitdubai.fermat_api.layer.osa_android.device_power.PowerManager;
import com.bitdubai.fermat_api.layer.osa_android.device_power.PowerStatus;

import android.content.Context;
import android.os.BatteryManager;

/**
 * Created by Natalia on 04/05/2015.
 */
public class AndroidPowerManager  implements PowerManager {

    /**
     * PowerManager Interface member variables.
     */
    private Context context;

    /**
     * PowerManager Interface implementation.
     */

    @Override
    public int getLevel(){

       //BatteryManager man = new BatteryManager ();

        android.os.PowerManager power =  (android.os.PowerManager)context.getSystemService(Context.POWER_SERVICE);

        return 0;
    }

    @Override
    public PowerStatus getStatus(){
        return null;
    }

    /**
     * Return the source which is connected to the battery
     */
    @Override
    public PlugType getPlugType(){
        return null;
    }

    @Override
    public void setContext (Object context){
        this.context = (Context)context;
    }
}

/*private String getHealthString(int health) {
		String healthString = "Unknown";

		switch (health) {
		case BatteryManager.BATTERY_HEALTH_DEAD:
			healthString = "Dead";
			break;
		case BatteryManager.BATTERY_HEALTH_GOOD:
			healthString = "Good";
			break;
		case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
			healthString = "Over Voltage";
			break;
		case BatteryManager.BATTERY_HEALTH_OVERHEAT:
			healthString = "Over Heat";
			break;
		case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
			healthString = "Failure";
			break;
		}

		return healthString;
	}
	*/
