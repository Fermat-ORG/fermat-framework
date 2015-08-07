package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WizardPage;

import java.util.ArrayList;

/**
 * Fermat Wizard Interface
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public interface FermatWizard {

    /**
     * Add Wizard Page
     *
     * @param page Wizard Page
     */
    void addPage(WizardPage page);

    ArrayList<WizardPage> getPages();

    WizardPage getPageAt(int index);

}
