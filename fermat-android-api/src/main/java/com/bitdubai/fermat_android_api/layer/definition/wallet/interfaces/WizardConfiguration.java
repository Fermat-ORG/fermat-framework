package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;

/**
 * Created by francisco on 05/08/15.
 */
public interface WizardConfiguration {

    void showWizard(WizardTypes key);

    void dismissWizard();

}
