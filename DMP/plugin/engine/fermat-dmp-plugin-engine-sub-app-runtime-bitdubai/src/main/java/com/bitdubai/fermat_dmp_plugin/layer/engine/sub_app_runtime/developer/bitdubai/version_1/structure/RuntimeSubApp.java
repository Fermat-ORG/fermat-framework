package com.bitdubai.fermat_dmp_plugin.layer.engine.sub_app_runtime.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.FermatApps;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.LanguagePackage;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
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

    private String actualActivityStart;

    Activities lastActivity;

    Map<String,LanguagePackage> languagePackages = new HashMap<String,LanguagePackage>();
    private int bannerRes;


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
        if(actualActivityStart==null){
            actualActivityStart = activity.getType().getCode();
        }
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
    public FermatApps getFermatApp() {
        return null;
    }

    @Override
    public FermatAppType getFermatAppType() {
        return FermatAppType.SUB_APP;
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
    public Activity getStartActivity() throws IllegalAccessException {
        if(!activities.isEmpty())
            try {
                return activities.get(Activities.getValueFromString(actualActivityStart));
            } catch (InvalidParameterException e) {
                throw new IllegalAccessException(actualActivityStart);
            }
        else throw new IllegalAccessException();
    }

    @Override
    public Activity getLastActivity() throws InvalidParameterException {
        if(lastActivity==null){
            return activities.get(Activities.getValueFromString(actualActivityStart));
        }
        return activities.get(lastActivity);
    }

    @Override
    public void changeActualStartActivity(String activityCode) throws IllegalArgumentException, InvalidParameterException {
       // if(activities.get(Activities.getValueFromString(activityCode))==null) throw new IllegalArgumentException("Activity code:"+activityCode+" is not in the activities list, add first and then change the start");
        this.actualActivityStart = activityCode;
    }

    @Override
    public Map<String,LanguagePackage> getLanguagePackages(){
        return languagePackages;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getAppName() {
        return type.getCode();
    }

    @Override
    public String getAppPublicKey() {
        return publicKey;
    }

    @Override
    public AppsStatus getAppStatus() {
        return null;
    }

    @Override
    public FermatAppType getAppType() {
        return FermatAppType.SUB_APP;
    }

    @Override
    public byte[] getAppIcon() {
        return new byte[0];
    }

    @Override
    public int getIconResource() {
        return 0;
    }

    @Override
    public void setBanner(int res) {
        this.bannerRes = res;
    }

    @Override
    public int getBannerRes() {
        return bannerRes;
    }
}
