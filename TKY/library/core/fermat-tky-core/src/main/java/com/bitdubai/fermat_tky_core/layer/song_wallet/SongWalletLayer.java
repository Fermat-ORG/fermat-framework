package com.bitdubai.fermat_tky_core.layer.song_wallet;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_tky_core.layer.song_wallet.tokenly.TokenlySongWalletPluginSubsystem;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/03/16.
 */
public class SongWalletLayer extends AbstractLayer {

    public SongWalletLayer() {
        super(Layers.SONG_WALLET);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new TokenlySongWalletPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}