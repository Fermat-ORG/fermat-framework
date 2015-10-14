package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantGetSkinVideoPreviewException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletIconException;

import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 7/22/15.
 */
public class Skin implements com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Skin {

    UUID id;
    String name;
    UUID walletId; //Todo: Refactor a String para que acepte PublicKey
    Version version;
    Version initialWalletVersion;
    Version finalWalletVersion;
    byte[] presentationImage;
    List<byte[]> previewImageList;
    boolean hasVideoPreview;
    List<URL> videoPreviews;
    URL url;
    long skinSizeInBytes;
    DesignerIdentity designer;
    boolean isDefault;
    ScreenSize screenSize;

    /**
     * default constructor
     */
    public Skin() {
    }

    /**
     * overloaded constructor
     *
     * @param id
     * @param name
     * @param walletId
     * @param version
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @param presentationImage
     * @param previewImageList
     * @param hasVideoPreview
     * @param videoPreviews
     * @param url
     * @param skinSizeInBytes
     * @param designer
     */
    public Skin(UUID id, String name, UUID walletId, Version version, Version initialWalletVersion, Version finalWalletVersion, byte[] presentationImage, List<byte[]> previewImageList, boolean hasVideoPreview, List<URL> videoPreviews, URL url, long skinSizeInBytes, Designer designer, ScreenSize screenSize) {
        this.id = id;
        this.name = name;
        this.walletId = walletId;
        this.version = version;
        this.initialWalletVersion = initialWalletVersion;
        this.finalWalletVersion = finalWalletVersion;
        this.presentationImage = presentationImage;
        this.previewImageList = previewImageList;
        this.hasVideoPreview = hasVideoPreview;
        this.videoPreviews = videoPreviews;
        this.url = url;
        this.skinSizeInBytes = skinSizeInBytes;
        this.designer = designer;
        this.screenSize = screenSize;
    }

    @Override
    public UUID getSkinId() {
        return id;
    }

    @Override
    public String getSkinName() {
        return name;
    }

    @Override
    public UUID getWalletId() {
        return walletId;
    }

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public Version getInitialWalletVersion() {
        return initialWalletVersion;
    }

    @Override
    public Version getFinalWalletVersion() {
        return finalWalletVersion;
    }

    @Override
    public byte[] getPresentationImage() throws CantGetWalletIconException {
        return presentationImage;
    }

    @Override
    public List<byte[]> getPreviewImageList() throws CantGetWalletIconException {
        return previewImageList;
    }

    @Override
    public boolean hasVideoPreview() {
        return hasVideoPreview;
    }

    @Override
    public List<URL> getVideoPreviews() throws CantGetSkinVideoPreviewException {
        return videoPreviews;
    }

    @Override
    public long getSkinSizeInBytes() {
        return skinSizeInBytes;
    }

    @Override
    public com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity getDesigner() {
        return designer;
    }

    public ScreenSize getScreenSize() {
        return this.screenSize;
    }

    @Override
    public boolean isDefault() {
        return this.isDefault;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public void setInitialWalletVersion(Version initialWalletVersion) {
        this.initialWalletVersion = initialWalletVersion;
    }

    public void setFinalWalletVersion(Version finalWalletVersion) {
        this.finalWalletVersion = finalWalletVersion;
    }

    public void setPresentationImage(byte[] presentationImage) {
        this.presentationImage = presentationImage;
    }

    public void setPreviewImageList(List<byte[]> previewImageList) {
        this.previewImageList = previewImageList;
    }

    public void setHasVideoPreview(boolean hasVideoPreview) {
        this.hasVideoPreview = hasVideoPreview;
    }

    public void setVideoPreviews(List<URL> videoPreviews) {
        this.videoPreviews = videoPreviews;
    }

    public void setSkinSizeInBytes(long skinSizeInBytes) {
        this.skinSizeInBytes = skinSizeInBytes;
    }

    public void setDesigner(com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity designer) {
        this.designer = designer;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setScreenSize(ScreenSize screenSize) {
        this.screenSize = screenSize;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Id: ");
        stringBuilder.append(this.id);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("SkinName: ");
        stringBuilder.append(this.getSkinName());
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("walletId: ");
        stringBuilder.append(this.walletId.toString());
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("IsDefault: ");
        stringBuilder.append(this.isDefault);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("ScreenSize: ");
        stringBuilder.append(this.screenSize);
        return stringBuilder.toString();
    }
}
