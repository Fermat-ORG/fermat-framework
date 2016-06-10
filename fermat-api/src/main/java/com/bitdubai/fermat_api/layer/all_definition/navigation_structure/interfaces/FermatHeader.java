package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import java.io.Serializable;

/**
 * Created by mati on 2015.09.29..
 */
public interface FermatHeader extends Serializable {


    String getLabel();


    boolean hasExpandable();


    boolean getRemoveHeaderScroll();

    boolean getStartCollapsed();
}
