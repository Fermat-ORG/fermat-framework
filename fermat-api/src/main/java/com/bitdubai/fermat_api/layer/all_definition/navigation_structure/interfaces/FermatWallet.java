package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.23..
 */
public interface FermatWallet extends Serializable{

    public String getPublicKey();

    public Activity getActivity(Activities activities);

    public Activity getStartActivity();

    public void setStartActivity(Activities activity);

    public Activity getLastActivity();

    public void setPublicKey(String publicKey);


}
