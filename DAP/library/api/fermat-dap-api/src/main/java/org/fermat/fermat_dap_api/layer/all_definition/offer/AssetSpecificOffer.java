package org.fermat.fermat_dap_api.layer.all_definition.offer;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/04/16.
 */
public class AssetSpecificOffer implements AssetOffer {

    //VARIABLE DECLARATION
    private UUID offerId;

    //CONSTRUCTORS

    public AssetSpecificOffer() {
    }

    public AssetSpecificOffer(UUID offerId) {
        this.offerId = offerId;
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS
    @Override
    public UUID getOfferId() {
        return offerId;
    }

    public void setOfferId(UUID offerId) {
        this.offerId = offerId;
    }
    //INNER CLASSES
}
