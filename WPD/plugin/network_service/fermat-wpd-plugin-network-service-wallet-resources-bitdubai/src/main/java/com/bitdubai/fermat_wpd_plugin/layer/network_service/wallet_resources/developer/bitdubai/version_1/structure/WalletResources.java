package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenOrientation;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.exceptions.CantGetLanguageFileException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.exceptions.CantGetSkinFileException;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.07.31..
 */
public class WalletResources implements com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResources {

    private UUID id;

    private Version version;

    private Skin skin;


    @Override
    public UUID getResourcesId() {
        return id;
    }


    @Override
    public Skin getSkinFile(String fileName) throws CantGetSkinFileException {
        return skin;
    }

    @Override
    public String getLanguageFile(String fileName) throws CantGetLanguageFileException {
        return "Method: getLanguageFile - NO TIENE valor ASIGNADO para RETURN";
    }

    @Override
    public byte[] getImageResource(String imageName,ScreenOrientation orientation) throws CantGetResourcesException {
        switch (orientation){
            case PORTRAIT:
                //Resource resource= skin.getLstPortraitResources().get(imageName);

                break;
            case LANDSCAPE:
                break;
        }
        return new byte[0];
    }

    @Override
    public byte[] getVideoResource(String videoName) throws CantGetResourcesException {
        return new byte[0];
    }

    @Override
    public byte[] getSoundResource(String soundName) throws CantGetResourcesException {
        return new byte[0];
    }

    @Override
    public String getFontStyle(String styleName) {
        return "Method: getFontStyle - NO TIENE valor ASIGNADO para RETURN";
    }

    @Override
    public String getLayoutResource(String layoutName) throws CantGetResourcesException {
        return "Method: getLayoutResource - NO TIENE valor ASIGNADO para RETURN";
    }
}
