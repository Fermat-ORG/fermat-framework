package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatHeader;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2015.10.01..
 */
public class Header implements FermatHeader, Serializable {

    String label;
    boolean isExpandable = false;
    boolean removeHeaderScroll = false;
    boolean startCollapsed;

    public Header() {
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean hasExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        this.isExpandable = expandable;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setRemoveHeaderScroll(boolean removeHeaderScroll) {
        this.removeHeaderScroll = removeHeaderScroll;
    }

    public boolean getRemoveHeaderScroll() {
        return this.removeHeaderScroll;
    }

    public void setStartCollapsed(boolean startCollapsed) {
        this.startCollapsed = startCollapsed;
    }

    public boolean getStartCollapsed() {
        return this.startCollapsed;
    }
}
