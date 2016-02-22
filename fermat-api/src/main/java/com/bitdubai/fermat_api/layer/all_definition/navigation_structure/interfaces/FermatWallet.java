package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.23..
 */
public interface FermatWallet extends Serializable,FermatStructure{

    String getPublicKey();

    Activity getActivity(Activities activities);

    Activity getStartActivity();

    void addPosibleStartActivity(Activities activity);

    Activity getLastActivity();

    void setPublicKey(String publicKey);


    void clear();
}
