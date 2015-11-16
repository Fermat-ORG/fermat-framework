package com.bitdubai.fermat_pip_plugin.layer.engine.desktop_runtime.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.LanguagePackage;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime.DesktopObject;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer 16/9/2015
 */

public class RuntimeDesktopObject implements DesktopObject {

    //SubApps type;

    /**
     *  Tipo de desktop
     *  si es subApp, wallet
     *  Deberia tomaerlo como String
     */
     private String desktopType;

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
    public Activity getActivity(Activities activities) {
        this.lastActivity=activities;
        return this.activities.get(activities);
    }

    @Override
    public Activity getLastActivity() {
        if(lastActivity==null){
            return activities.get(startActivity);
        }
        return activities.get(lastActivity);
    }

    public void setStartActivity(Activities activity) {
        this.startActivity=activity;
    }


    @Override
    public Map<String,LanguagePackage> getLanguagePackages(){
        return languagePackages;
    }

}
