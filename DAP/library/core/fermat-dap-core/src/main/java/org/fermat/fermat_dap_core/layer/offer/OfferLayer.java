package org.fermat.fermat_dap_core.layer.offer;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

import org.fermat.fermat_dap_core.layer.offer.asset_specific.AssetSpecificPluginSubsystem;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/04/16.
 */
public class OfferLayer extends AbstractLayer {
    //VARIABLE DECLARATION

    //CONSTRUCTORS
    public OfferLayer() {
        super(Layers.OFFER);
    }

    //PUBLIC METHODS
    @Override
    public void start() throws CantStartLayerException {
        try {

//            registerPlugin(new AssetGeneralPluginSubsystem());
            registerPlugin(new AssetSpecificPluginSubsystem());

        } catch (Exception e) {

            throw new CantStartLayerException(
                    e,
                    "Offer Layer of DAP Platform",
                    "Problem trying to register a plugin."
            );
        }
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
