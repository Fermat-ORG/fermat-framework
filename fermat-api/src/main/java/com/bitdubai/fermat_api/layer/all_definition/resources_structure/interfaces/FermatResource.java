package com.bitdubai.fermat_api.layer.all_definition.resources_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.resources_structure.interfaces.FermatResource</code>
 * indicates the functionality of a Fermat Resource.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface FermatResource {

    UUID getId();

    // name of the resource
    String getName();

    // fileName of the resource
    String getFileName();

    // resource type
    ResourceType getResourceType();

}
