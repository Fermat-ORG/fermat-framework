package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FermatApps;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2016.01.06..
 */
public interface FermatStructure extends Serializable{

    FermatApps getFermatApp();
    FermatAppType getFermatAppType();
    Platforms getPlatform();

    String getPublicKey();
    Activity getActivity(Activities activities);
    Activity getStartActivity() throws IllegalAccessException, InvalidParameterException;
    Activity getLastActivity() throws InvalidParameterException;
    void changeActualStartActivity(String activityCode) throws IllegalArgumentException, InvalidParameterException;

    void clear();
}
