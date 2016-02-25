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

    String getPublicKey();
    Activity getActivity(Activities activities);
    Activity getStartActivity();
    Activity getLastActivity();
    void changeActualStartActivity(int option)throws IllegalArgumentException;
}
