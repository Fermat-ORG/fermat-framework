package com.bitdubai.fermat_art_core.layer.actor_connection;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_art_core.layer.actor_connection.artist.ArtistActorConnectionPluginSubsystem;
import com.bitdubai.fermat_art_core.layer.actor_connection.fan.FanActorConnectionPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/03/16.
 */
public class ActorConnectionLayer extends AbstractLayer {

    public ActorConnectionLayer() {
        super(Layers.ACTOR_CONNECTION);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new ArtistActorConnectionPluginSubsystem());
            registerPlugin(new FanActorConnectionPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
