package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatWizard;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Wizard
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class Wizard implements FermatWizard, Serializable {

    protected ArrayList<WizardPage> pages;

    @Override
    public void addPage(WizardPage page) {
        if (page == null)
            throw new NullPointerException("page cannot be null");
        if (pages == null)
            pages = new ArrayList<>();
        pages.add(page);
    }

    @Override
    public ArrayList<WizardPage> getPages() {
        return pages == null ? new ArrayList<WizardPage>() : pages;
    }

    @Override
    public WizardPage getPageAt(int index) {
        return pages.get(index);
    }
}
