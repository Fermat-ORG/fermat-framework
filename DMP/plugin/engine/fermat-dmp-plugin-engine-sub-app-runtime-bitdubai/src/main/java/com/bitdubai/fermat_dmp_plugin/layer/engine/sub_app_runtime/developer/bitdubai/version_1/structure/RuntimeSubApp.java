package com.bitdubai.fermat_dmp_plugin.layer.engine.sub_app_runtime.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.LanguagePackage;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 24/07/15.
 */

public class RuntimeSubApp implements SubApp {

    SubApps type;

    String publicKey;

    Map<Activities, Activity> activities = new  HashMap<Activities, Activity>();

    private List<Activities> startActivities = new ArrayList<>();;
    private int actualStart = 0;

    Activities lastActivity;

    Map<String,LanguagePackage> languagePackages = new HashMap<String,LanguagePackage>();


    /**
     * RuntimeSubApp interface implementation.
     */
    public void setType(SubApps type) {
        this.type = type;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void addActivity (Activity activity){
        activities.put(activity.getType(), activity);
    }


    public void addLanguagePackage (LanguagePackage languagePackage){
        languagePackages.put(languagePackage.getName(),languagePackage);
    }

    /**
     * SubApp interface implementation.
     */

    @Override
    public SubApps getType() {
        return type;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
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
    public Activity getStartActivity() {
        if(!startActivities.isEmpty())
        return activities.get(startActivities.get(actualStart));
        else return activities.get(0);
    }

    @Override
    public Activity getLastActivity() {
        if(lastActivity==null){
            return activities.get(startActivities.get(actualStart));
        }
        return activities.get(lastActivity);
    }

    @Override
    public void changeActualStartActivity(int option)throws IllegalArgumentException{
        if(option>activities.size() || option<0) throw new IllegalArgumentException();
        this.actualStart = option;
    }

    @Override
    public void addPosibleStartActivity(Activities activity) {
        this.startActivities.add(activity);
    }


    @Override
    public Map<String,LanguagePackage> getLanguagePackages(){
        return languagePackages;
    }

    @Override
    public String getAppName() {
        return type.getCode();
    }

    @Override
    public String getAppPublicKey() {
        return publicKey;
    }
}
