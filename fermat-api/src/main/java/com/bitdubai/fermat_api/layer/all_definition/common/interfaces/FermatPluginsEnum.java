package com.bitdubai.fermat_api.layer.all_definition.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * The class <code>FermatPluginsEnum</code>
 * haves the representation of the basic functionality of a Fermat Plugins Enum.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public interface FermatPluginsEnum extends FermatEnum {

    /**
     * Throw the method <code>getPlatform</code> you can know to which platform the plugin belongs.
     * @return an element of Platforms enum.
     */
    Platforms getPlatform();

}
