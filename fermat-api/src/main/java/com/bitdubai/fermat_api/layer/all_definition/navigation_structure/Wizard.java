package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatWizard;

import java.util.ArrayList;

/**
 * Wizard
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class Wizard<T extends Fragment> implements FermatWizard<T> {

    protected ArrayList<T> pages;

    @Override
    public void addPage(T page) {
        if (page == null)
            throw new NullPointerException("page cannot be null");
        if (pages == null)
            pages = new ArrayList<>();
        pages.add(page);
    }

    @Override
    public ArrayList<T> getPages() {
        return pages == null ? new ArrayList<T>() : pages;
    }

    @Override
    public T getPageAt(int index) {
        return pages.get(index);
    }
}
