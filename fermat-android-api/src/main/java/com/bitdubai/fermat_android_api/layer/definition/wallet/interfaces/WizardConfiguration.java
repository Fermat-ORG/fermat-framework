package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

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
