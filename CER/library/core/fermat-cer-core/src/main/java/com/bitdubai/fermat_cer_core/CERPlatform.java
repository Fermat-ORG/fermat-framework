package com.bitdubai.fermat_cer_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cer_core.layer.provider.ProviderLayer;
import com.bitdubai.fermat_cer_core.layer.search.SearchLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartPlatformException;

/**
 * Created by Alejandro Bicelis on 11/25/2015.
 */
public class CERPlatform extends AbstractPlatform {

    public CERPlatform() {
        super(new PlatformReference(Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {
            registerLayer(new ProviderLayer());
            registerLayer(new SearchLayer());
        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "",
                    "Problem trying to register a layer."
            );
        }
    }
}