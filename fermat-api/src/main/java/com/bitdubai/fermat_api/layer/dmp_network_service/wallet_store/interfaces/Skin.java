package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantGetSkinVideoPreviewException;

import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.07.16..
 */
public interface Skin {

    public Version getVersion();
    public UUID getSkinId();

    public String getSkinName();
    public String getSkinDesignerName();
    public String getSkinDesignerPublicKey();

    public byte[] getPresentationImage();

    public List<String> getPreviewImageList();
    public byte[] getImage(String imageName) throws CantGetWalletIconException;

    public boolean hasVideoPreview();
    public List<URL> getVideoPreviews() throws CantGetSkinVideoPreviewException;

    public long getSkinSizeInBytes();
}
