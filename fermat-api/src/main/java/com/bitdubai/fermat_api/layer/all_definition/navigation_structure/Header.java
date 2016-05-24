package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatHeader;

/**
 * Created by Matias Furszyfer on 2015.10.01..
 */
public class Header implements FermatHeader{

    String label;
    boolean hasExpandable = false;
    boolean removeHeaderScroll=false;
    boolean startCollapse;
    public Header() {
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean hasExpandable() {
        return hasExpandable;
    }

    public void setHasExpandable(boolean hasExpandable) {
        this.hasExpandable = hasExpandable;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setRemoveHeaderScroll(boolean removeHeaderScroll){this.removeHeaderScroll=removeHeaderScroll;}

    public boolean getRemoveHeaderScroll(){return this.removeHeaderScroll;    }

    public void setStartCollapse(boolean startCollapse){this.startCollapse=startCollapse;}

    public boolean getStartCollapse(){return this.startCollapse;}
}
