package com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.ImageNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.UrlNotFoundException;

import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.07.18..
 */
public interface WalletStoreSkin {

    public InstallationStatus getInstallationStatus();

    public UUID getSkinId();

    public String getSkinName();
    public String getSkinDesignerName();
    public String getSkinDesignerPublicKey();

    public byte[] getPresentationImage();

    public List<String> getPreviewImageList();
    public byte[] getImage(String imageName) throws ImageNotFoundException;

    public boolean hasVideoPreview();
    public List<URL> getVideoPreviews() throws UrlNotFoundException;

    public long getSkinSizeInBytes();
}
