package com.bitdubai.fermat_dmp_plugin.layer.engine.sub_app_runtime.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.LanguagePackage;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 24/07/15.
 */

public class RuntimeSubApp implements SubApp {

    SubApps type;

    String publicKey;

    Map<Activities, Activity> activities = new  HashMap<Activities, Activity>();

    Activities startActivity;

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
    public Activity getLastActivity() {
        if(lastActivity==null){
            return activities.get(startActivity);
        }
        return activities.get(lastActivity);
    }

    @Override
    public void setStartActivity(Activities activity) {
        this.startActivity=activity;
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
