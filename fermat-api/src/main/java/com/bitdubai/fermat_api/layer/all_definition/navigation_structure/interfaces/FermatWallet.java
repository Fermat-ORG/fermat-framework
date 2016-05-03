package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

/**
 * Created by Matias Furszyfer on 2015.07.23..
 */
public interface FermatWallet extends FermatStructure{

    String getPublicKey();

    Activity getActivity(Activities activities);

    Activity getStartActivity() throws InvalidParameterException;

    Activity getLastActivity() throws InvalidParameterException;

    void setPublicKey(String publicKey);


    void clear();
}
