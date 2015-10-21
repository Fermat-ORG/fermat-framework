package com.bitdubai.fermat_api.layer.all_definition.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum</code>
 * haves the representation of the basic functionality of a Fermat Addons Enum.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public interface FermatAddonsEnum extends FermatEnum {

    /**
     * Throw the method <code>getPlatform</code> you can know to which platform the addon belongs.
     * @return an element of Platforms enum.
     */
    Platforms getPlatform();

    /**
     * Throw the method <code>getLayer</code> you can know to which layer the addon belongs.
     * @return an element of Layers enum.
     */
    Layers getLayer();

    /**
     * Throw the method <code>getDeveloper</code> you can know to which developer the addon belongs.
     * @return an element of Developers enum.
     */
    Developers getDeveloper();

}
