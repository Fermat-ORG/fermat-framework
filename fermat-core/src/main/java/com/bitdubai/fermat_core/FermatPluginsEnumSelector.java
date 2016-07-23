package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The class <code>com.bitdubai.fermat_core.FermatPluginsEnumSelector</code>
 * provides the necessary logic to identify plugins enums.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/12/2015.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class FermatPluginsEnumSelector {

    public static FermatPluginsEnum getByPlatformAndCode(final Platforms platforms,
                                                         final String code) throws InvalidParameterException {

        switch (platforms) {

            default:
                return Plugins.getByCode(code);
        }
    }
}
