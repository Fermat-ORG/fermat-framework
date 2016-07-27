package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

/**
 * Created by mati on 2015.11.14..
 */
public interface FermatRuntime {


    /**
     * Change the back activity
     * This is for communication between apps
     *
     * @param appBackPublicKey
     * @param activityCode
     */
    void changeActivityBack(String appBackPublicKey, String activityCode);

    /**
     * Change start activity of an app,
     *
     * @param activityCode is the position added in the runtime
     * @return
     */

    void changeStartActivity(String appPublicKey, String activityCode);

    void changeTabNumber(String appPublicKey, String appActivityCode, int number);
}
