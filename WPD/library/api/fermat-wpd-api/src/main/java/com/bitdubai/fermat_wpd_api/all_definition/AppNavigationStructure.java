package com.bitdubai.fermat_wpd_api.all_definition;

import com.bitdubai.fermat_api.layer.all_definition.enums.FermatApps;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.developer.interfaces.DeveloperIdentity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Matias Furszyfer on 2015.07.23..
 */

public class AppNavigationStructure implements FermatStructure,Serializable{

    /**
     * AppNavigationStructure identifiers
     */

    private String publicKey;

    /**
     * Screens in a AppNavigationStructure
     */

    private Map<Activities, Activity> activities = new HashMap<Activities, Activity>();

    /**
     * Activity used to block something
     */
    private Activities blockActivity;

    /**
     * Last active screen
     */

    private Activities lastActivity;

    private int size;


    private DeveloperIdentity developer;
    private String actualStart;
    private Platforms platform;

    /**
     * AppNavigationStructure constructor
     */
    public AppNavigationStructure() {
    }

    /**
     *
     * @param activities
     */
    public AppNavigationStructure(Map<Activities, Activity> activities) {
        this.activities = activities;
    }

    public void setPlatform(Platforms platform) {
        this.platform = platform;
    }

    @Override
    public FermatApps getFermatApp() {
        return null;
    }

    @Override
    public FermatAppType getFermatAppType() {
        return FermatAppType.WALLET;
    }

    @Override
    public Platforms getPlatform() {
        return platform;
    }

    /**
     * AppNavigationStructure interface implementation.
     */

    @Override
    public String getPublicKey() {
        return publicKey;
    }


    /**
     * RuntimeWallet interface implementation.
     */
    @Override
    public Activity getActivity(Activities activities) {
        this.lastActivity=activities;
        return this.activities.get(activities);
    }

    /**
     *  Get main screen
     *
     * @return Activity
     */
    @Override
    public Activity getStartActivity() throws InvalidParameterException {
        return activities.get(Activities.getValueFromString(actualStart));
    }

    /**
     *  Get the last Screen used
     * @return Activity
     */
    @Override
    public Activity getLastActivity() throws InvalidParameterException {
        if (lastActivity == null) {
            try {
                return activities.get(Activities.getValueFromString(actualStart));
            } catch (InvalidParameterException e) {
                throw new InvalidParameterException();
            }
        }
        return activities.get(lastActivity);
    }

    @Override
    public void changeActualStartActivity(String activityCode)throws IllegalArgumentException{
//        try {
//            if(activities.get(Activities.getValueFromString(activityCode))==null) throw new IllegalArgumentException();
//        } catch (InvalidParameterException e) {
//            throw new IllegalArgumentException(activityCode);
//        }
        this.actualStart = activityCode;
    }


    public void setPublicKey(String publicKey) {
        this.publicKey=publicKey;
    }

    @Override
    public void clear() {
        for(Activity activity : activities.values()){
            SideMenu sideMenu = activity.getSideMenu();
            if (sideMenu!=null){
                sideMenu.clearSelected();
            }

        }
    }

    /**
     *  Screens in a AppNavigationStructure
     *
     * @return Map<Activities, Activity>
     */

    public Map<Activities, Activity> getActivities() {
        return activities;
    }

    public void setActivities(Map<Activities, Activity> activities) {
        this.activities = activities;
    }

    /**
     *  Add Screen to AppNavigationStructure
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if(actualStart==null){
            actualStart = activity.getType().getCode();
        }
        activities.put(activity.getType(), activity);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public DeveloperIdentity getDeveloper() {
        return developer;
    }

    public void setDeveloper(DeveloperIdentity developer) {
        this.developer = developer;
    }
}
