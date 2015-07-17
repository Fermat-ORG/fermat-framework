package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetNextSkinPreviewImageException;

import java.net.URL;
import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface NewCatalogueItem {

    public String walletVersion();

    public boolean hasVideoPreview();

    public URL getPreviewVideoUrl();

    public boolean hasImagePreview();

    public byte[] getPreviewImage() throws CantGetNextSkinPreviewImageException;

    public int navigationStructureSizeInBytes();

    public String getWalletName();

    public String getDeveloperPublicKey();

    public String getDeveloperName();

    public String getWalletVersion();

    public String getWalletDescription();

    public UUID getWalletCatalogId();

    public UUID getResourcesId();
}
