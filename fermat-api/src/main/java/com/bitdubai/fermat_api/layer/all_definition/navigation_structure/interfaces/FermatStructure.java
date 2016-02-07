package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FermatApps;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;

/**
 * Created by Matias Furszyfer on 2016.01.06..
 */
public interface FermatStructure {

    FermatApps getFermatApp();
    FermatAppType getFermatAppType();

    public String getPublicKey();
    public Activity getActivity(Activities activities);
    public Activity getStartActivity();
    public Activity getLastActivity();
    public void changeActualStartActivity(int option)throws IllegalArgumentException;
}
