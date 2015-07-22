package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions_milestone2.CantGetSkinVideoPreviewException;

import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.07.16..
 */
public interface Skin {


    /**
     * Skin identified information
     */
    public UUID getSkinId();
    public String getSkinName();
    public UUID getWalletId();


    /**
     * Version information, current, Inicial and Final versions accepted.
     */
    public Version getVersion();
    public Version getInitialWalletVersion();
    public Version getFinalWalletVersion();

    /**
     * Skin presentation information
     */
    public byte[] getPresentationImage() throws CantGetWalletIconException;
    public List<Byte[]> getPreviewImageList() throws CantGetWalletIconException;
    public boolean hasVideoPreview();
    public List<URL> getVideoPreviews() throws CantGetSkinVideoPreviewException;

    /**
     * Skin resource information
     */
    public String getSkinURL();
    public long getSkinSizeInBytes();

    /**
     * Skin designer information
     */
    public String getSkinDesignerName();
    public String getSkinDesignerPublicKey();
}
