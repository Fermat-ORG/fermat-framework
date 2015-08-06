package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;

import java.util.ArrayList;

/**
 * Fermat Wizard Interface
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public interface FermatWizard<T extends Fragment> {

    /**
     * Add Wizard Page
     *
     * @param page Wizard Page
     */
    void addPage(T page);

    ArrayList<T> getPages();

    T getPageAt(int index);

}
