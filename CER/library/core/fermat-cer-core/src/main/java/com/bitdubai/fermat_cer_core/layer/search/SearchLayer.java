package com.bitdubai.fermat_cer_core.layer.search;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cer_core.layer.search.provider_filter.ProviderFilterSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by Alejandro Bicelis on 11/25/2015.
 */

public class SearchLayer extends AbstractLayer {

    public SearchLayer() {
        super(Layers.SEARCH);
    }

    public void start() throws CantStartLayerException {

        try {
            registerPlugin(new ProviderFilterSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
