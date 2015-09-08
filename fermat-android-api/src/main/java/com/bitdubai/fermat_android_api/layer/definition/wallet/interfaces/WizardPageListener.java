package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import java.util.Map;

/**
 * Wizard Page Methods
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public interface WizardPageListener {

    /**
     * Validate Fields before move to the next step
     *
     * @return true if was successfully validated, otherwise false.
     */
    boolean validate();

    /**
     * Save all Fields before move to the next page
     */
    void savePage();

    /**
     * Execute Finish Method
     *
     * @param data Custom key -> object temp shared data between pages
     */
    void onWizardFinish(Map<String, Object> data);


    /**
     * It's called on WizardActivity Fragment Page Changed to notify when the fragment is active
     *
     * @param data Custom key -> object temp shared data between pages
     */
    public void onActivated(Map<String, Object> data);


    /**
     * Get Step Title for ActionBar
     *
     * @return CharSequence Title
     */
    CharSequence getTitle();

}
