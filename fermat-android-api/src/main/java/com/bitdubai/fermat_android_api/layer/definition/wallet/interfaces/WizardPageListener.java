package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

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

}
