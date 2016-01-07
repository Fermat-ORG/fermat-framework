package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

/**
 * Created by Matias Furszyfer on 2016.01.06..
 */
public interface FermatStructure {

    public String getPublicKey();
    public Activity getActivity(Activities activities);
    public Activity getStartActivity();
    public Activity getLastActivity();
}
