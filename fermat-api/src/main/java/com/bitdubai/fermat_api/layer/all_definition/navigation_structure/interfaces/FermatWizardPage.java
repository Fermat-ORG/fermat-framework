package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardPageTypes;

/**
 * Fermat Wizard Page Interface
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public interface FermatWizardPage {

    void setType(WizardPageTypes pageType);

    WizardPageTypes getType();

    String getFragment();
}
