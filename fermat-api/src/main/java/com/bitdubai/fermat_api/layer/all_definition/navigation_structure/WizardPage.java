package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardPageTypes;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatWizardPage;

/**
 * Wizard Page
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class WizardPage implements FermatWizardPage {

    private WizardPageTypes type;

    private String fragment;

    @Override
    public void setType(WizardPageTypes pageType) {
        type = pageType;
    }

    @Override
    public WizardPageTypes getType() {
        return type;
    }

    @Override
    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }
}
