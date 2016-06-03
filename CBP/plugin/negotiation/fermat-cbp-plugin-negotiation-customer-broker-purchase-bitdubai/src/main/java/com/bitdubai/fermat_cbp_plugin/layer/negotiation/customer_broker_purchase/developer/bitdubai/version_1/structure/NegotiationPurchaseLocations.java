package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by angel on 07/12/15.
 */
public class NegotiationPurchaseLocations implements NegotiationLocations, Serializable {

    private final UUID locationId;
    private final String location;
    private final String uri;

    public NegotiationPurchaseLocations(UUID locationId, String location, String uri){
        this.locationId = locationId;
        this.location = location;
        this.uri = uri;
    }

    @Override
    public UUID getLocationId() {
        return this.locationId;
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public String getURI() {
        return this.uri;
    }
}
