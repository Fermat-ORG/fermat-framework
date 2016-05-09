package com.bitdubai.fermat_pip_plugin.layer.engine.desktop_runtime.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.FermatApps;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.LanguagePackage;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime.DesktopObject;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer 16/9/2015
 */

public class RuntimeDesktopObject implements DesktopObject,Serializable {

    //SubApps type;

    /**
     *  Tipo de desktop
     *  si es subApp, wallet
     *  Deberia tomaerlo como String
     */
     private String desktopType;

    private String appPublicKey;

    /**
     *  Desktop identifier
     */
    private String identifier;

    private Map<Activities, Activity> activities = new  HashMap<Activities, Activity>();

    private Activities startActivity;

    private Activities lastActivity;

    private Map<String,LanguagePackage> languagePackages = new HashMap<String,LanguagePackage>();




    /**
     * RuntimeSubApp interface implementation.
     */
    public void setType(String desktopType) {
        this.desktopType = desktopType;
    }
    
    public void addActivity (Activity activity){
        activities.put(activity.getType(), activity);
    }


    public void addLanguagePackage (LanguagePackage languagePackage){
        languagePackages.put(languagePackage.getName(),languagePackage);
    }

    /**
     * DesktopObject interface implementation.
     */

    @Override
    public String getType() {
        return desktopType;
    }

    @Override
    public Map<Activities, Activity> getActivities(){
        return activities;
    }

    @Override
    public FermatApps getFermatApp() {
        return FermatApps.MAIN_DESKTOP;
    }

    @Override
    public FermatAppType getFermatAppType() {
        return FermatAppType.DESKTOP;
    }

    @Override
    public Platforms getPlatform() {
        return null;
    }

    @Override
    public String getPublicKey() {
        return appPublicKey;
    }

    @Override
    public Activity getActivity(Activities activities) {
        this.lastActivity=activities;
        return this.activities.get(activities);
    }

    @Override
    public Activity getStartActivity() {
        return activities.get(startActivity);
    }

    @Override
    public Activity getLastActivity() {
        if(lastActivity==null){
            return activities.get(startActivity);
        }
        return activities.get(lastActivity);
    }

    @Override
    public void changeActualStartActivity(String activityCode) throws IllegalArgumentException {
        try {
            if(activities.get(Activities.getValueFromString(activityCode))==null) throw new IllegalArgumentException();
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException();
        }
        try {
            this.startActivity = Activities.getValueFromString(activityCode);
            //todo: sacar esto
            if(lastActivity!=null) {
                if (lastActivity.equals(Activities.DESKTOP_WIZZARD_WELCOME)) {
                    this.lastActivity = Activities.getValueFromString(activityCode);
                }
            }else{
                this.lastActivity = Activities.getValueFromString(activityCode);
            }
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException(activityCode);
        }
    }

    @Override
    public void clear() {

    }

    public void setStartActivity(Activities activity) {
        this.startActivity=activity;
    }


    @Override
    public Map<String,LanguagePackage> getLanguagePackages(){
        return languagePackages;
    }


    public void setAppPublicKey(String appPublicKey) {
        this.appPublicKey = appPublicKey;
    }
}
