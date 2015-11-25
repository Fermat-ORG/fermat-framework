package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;

/**
 * Wizard Configuration Interface
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public interface WizardConfiguration {

    //void showWizard(WizardTypes key);

    void showWizard(String key, Object... args);

}
