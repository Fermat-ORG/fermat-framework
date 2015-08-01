package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.CantGetLanguageFileException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.CantGetManifestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.CantGetSkinFileException;

import java.util.UUID;

/**
 * Created by mati on 2015.07.31..
 */
public class WalletResources implements com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResources {

    private String publicKey;

    private Version version;


    @Override
    public UUID getResourcesId() {
        return null;
    }

    @Override
    public String getManifest() throws CantGetManifestException {
        return null;
    }

    @Override
    public String getSkinFile(String fileName) throws CantGetSkinFileException {
        return null;
    }

    @Override
    public String getLanguageFile(String fileName) throws CantGetLanguageFileException {
        return null;
    }

    @Override
    public byte[] getImageResource(String imageName) throws CantGetResourcesException {
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
        return null;
    }

    @Override
    public String getLayoutResource(String layoutName) throws CantGetResourcesException {
        return null;
    }
}
