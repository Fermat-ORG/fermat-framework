package com.bitdubai.fermat_wpd_api.all_definition;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.AppStructureType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.notifications.FermatNotification;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.developer.interfaces.DeveloperIdentity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Matias Furszyfer on 2015.07.23..
 */

public class AppNavigationStructure implements FermatStructure,Serializable{

    /**
     * AppNavigationStructure identifiers
     */
    private String appPublicKey;
    /**
     * App Structure Type, default is Reference App
     */
    private AppStructureType appStructureType = AppStructureType.REFERENCE;
    /**
     * Screens in a AppNavigationStructure
     */
    private Map<Activities, Activity> activities = new HashMap<Activities, Activity>();

    /**
     * Notifications
     */
    private Map<String,FermatNotification> notifications;
    /**
     * Last active screen
     */
    private Activities lastActivity;

    private DeveloperIdentity developer;
    private String actualStart;
    private Platforms platform;
    private List<String> appsKeyConsumed;

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
    public Platforms getPlatform() {
        return platform;
    }

    /**
     * AppNavigationStructure interface implementation.
     */

    @Override
    public String getPublicKey() {
        return appPublicKey;
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


    public void setPublicKey(String appPublicKey) {
        this.appPublicKey=appPublicKey;
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


    public DeveloperIdentity getDeveloper() {
        return developer;
    }

    public void setDeveloper(DeveloperIdentity developer) {
        this.developer = developer;
    }

    public void setAppStructureType(AppStructureType appStructureType) {
        this.appStructureType = appStructureType;
    }

    public AppStructureType getAppStructureType() {
        return appStructureType;
    }

    @Override
    public List<String> getAppsKeyConsumed() {
        return appsKeyConsumed;
    }

    public void addAppKeyToConsume(String appPublicKey){
        if(appsKeyConsumed==null){
            appsKeyConsumed = new ArrayList<>();
        }
        this.appsKeyConsumed.add(appPublicKey);
    }

    public void addNotification(String key,FermatNotification fermatNotification){
        if(notifications==null){
            notifications = new HashMap<>();
        }
        notifications.put(key,fermatNotification);
    }

    @Override
    public String toString() {
        return "AppNavigationStructure{" +
                "appPublicKey='" + appPublicKey + '\'' +
                ", appStructureType=" + appStructureType +
                ", activities=" + activities +
                ", lastActivity=" + lastActivity +
                ", developer=" + developer +
                ", actualStart='" + actualStart + '\'' +
                ", platform=" + platform +
                ", appsKeyConsumed=" + appsKeyConsumed +
                '}';
    }
}
