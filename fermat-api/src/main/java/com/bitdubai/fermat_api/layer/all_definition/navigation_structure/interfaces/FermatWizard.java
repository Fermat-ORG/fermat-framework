package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WizardPage;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Fermat Wizard Interface
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public interface FermatWizard extends Serializable {

    /**
     * Add Wizard Page
     *
     * @param page Wizard Page
     */
    void addPage(WizardPage page);

    ArrayList<WizardPage> getPages();

    WizardPage getPageAt(int index);

}
