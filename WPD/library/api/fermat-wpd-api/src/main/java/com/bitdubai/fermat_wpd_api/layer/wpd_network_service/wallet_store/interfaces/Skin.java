package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantGetSkinVideoPreviewException;

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
    UUID getSkinId();

    String getSkinName();

    UUID getWalletId(); //Todo: Refactor a String para que acepte PublicKey

    ScreenSize getScreenSize();

    /**
     * Version information, current, Inicial and Final versions accepted.
     */
    Version getVersion();

    Version getInitialWalletVersion();

    Version getFinalWalletVersion();

    /**
     * Skin presentation information
     */
    byte[] getPresentationImage() throws CantGetWalletIconException;

    List<byte[]> getPreviewImageList() throws CantGetWalletIconException;

    boolean hasVideoPreview();

    List<URL> getVideoPreviews() throws CantGetSkinVideoPreviewException;

    /**
     * Skin resource information
     */

    long getSkinSizeInBytes();

    /**
     * Skin designer information
     */
    DesignerIdentity getDesigner();

    boolean isDefault();

}
