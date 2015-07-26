package com.bitdubai.fermat_api.layer.all_definition.resources_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.resources_structure.interfaces.FermatSkin</code>
 * indicates the functionality of a Fermat Skin.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface FermatSkin {

    // TODO DESIGNER.

    UUID getId();

    // name of skin, first skin is default
    String getName();

    // get all resources from a skin
    Map<UUID, FermatResource> getResources();

    // version of skin
    Version getVersion();

    // add Resource to the skin
    void addResource(FermatResource fermatResource);

    // delete Resource from the skin
    void deleteResource(FermatResource fermatResource);

}
