package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetNextSkinPreviewImageException;

import java.net.URL;
import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface NewCatalogueItem {

    public boolean hasImagePreview();

    public boolean hasVideoPreview();

    public int getDefaultSizeInBytes();

    public String getDeveloperName();

    public String getDeveloperPublicKey();

    public byte[] getPreviewImage() throws CantGetNextSkinPreviewImageException;

    public URL getPreviewVideoUrl();

    /**
     * This id will be used in the peer to peer version of this milestone
     *
     * @return a unique identifier for the resources
     */
    public UUID getResourcesId();

    public UUID getWalletIdInCatalog();

    public String getWalletDescription();

    public String getWalletName();

    public String getWalletVersion();
}
