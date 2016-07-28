package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;


/**
 * Created by Furszyfer Matias on 2015.07.23..
 */
public interface FermatScreenSwapper {

    void changeScreen(String screen, int idContainer, Object[] objects);

    void changeActivity(String activityName, String appBackPublicKey, Object... objects);

    void connectWithOtherApp(String fermatAppPublicKey, Object[] objectses);

    void onControlledActivityBack(String activityCodeBack);

    void setChangeBackActivity(Activities activityCodeBack);
}
