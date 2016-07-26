package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by angel on 03/12/15.
 */
public interface NegotiationLocations extends Serializable {

    UUID getLocationId();

    String getLocation();

    String getURI();
}
