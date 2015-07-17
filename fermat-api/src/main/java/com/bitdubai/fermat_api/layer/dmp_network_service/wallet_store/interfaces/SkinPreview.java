package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetNextSkinPreviewImageException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions_milestone2.CantGetSkinVideoPreviewException;

import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.07.16..
 */
public interface SkinPreview {

    public UUID getSkinId();

    public String getSkinName();
    public String getSkinDesigner();
    public byte[] getPresentationImage();

    public void stepInFirstPreviewImage();
    public boolean hasMorePreviewImages();
    public byte[] getNextImageSnapshot() throws CantGetNextSkinPreviewImageException;

    public boolean hasVideoPreview();
    public List<URL> getVideoPreviews() throws CantGetSkinVideoPreviewException;

    public long getSizeInBytes();
}
