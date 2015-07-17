package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.common;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectResource;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.FileManagerHelper</code>
 * contains all the functionality related with file management in this plugin.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FileManagerHelper implements DealsWithPluginFileSystem, DealsWithPluginIdentity {

    /**
     * DealsWithPluginFileSystem interface variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginFileSystem interface variables.
     */
    UUID pluginId;

    public void addResourceFile(WalletFactoryProjectResource walletFactoryProjectResource, WalletFactoryProjectSkin projectSkin) {
        try {
            PluginBinaryFile newFile = pluginFileSystem.createBinaryFile(pluginId, createResourcePath(walletFactoryProjectResource, projectSkin), walletFactoryProjectResource.getName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            newFile.loadFromMedia();
            newFile.setContent(walletFactoryProjectResource.getResource());
            newFile.persistToMedia();
        } catch (CantPersistFileException|CantCreateFileException|CantLoadFileException e) {

        }
    }

    public void updateResourceFile(WalletFactoryProjectResource walletFactoryProjectResource, WalletFactoryProjectSkin projectSkin) {
        try {
            PluginBinaryFile newFile = pluginFileSystem.getBinaryFile(pluginId, createResourcePath(walletFactoryProjectResource, projectSkin), walletFactoryProjectResource.getName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            newFile.loadFromMedia();
            newFile.setContent(walletFactoryProjectResource.getResource());
            newFile.persistToMedia();
        } catch (FileNotFoundException|CantCreateFileException|CantPersistFileException|CantLoadFileException e) {

        }
    }

    public void deleteResourceFile(WalletFactoryProjectResource walletFactoryProjectResource, WalletFactoryProjectSkin projectSkin) {
        try {
            PluginBinaryFile newFile = pluginFileSystem.getBinaryFile(pluginId, createResourcePath(walletFactoryProjectResource, projectSkin), walletFactoryProjectResource.getName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            //newFile.deleteFile();
            // TODO DELETE FILE IN PLUGINFILESYSTEM
        } catch (FileNotFoundException|CantCreateFileException e) {

        }
    }

    public byte[] getResourceFile(WalletFactoryProjectResource walletFactoryProjectResource, WalletFactoryProjectSkin projectSkin) {
        try {
            PluginBinaryFile newFile = pluginFileSystem.createBinaryFile(pluginId, createResourcePath(walletFactoryProjectResource, projectSkin), walletFactoryProjectResource.getName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            newFile.loadFromMedia();
            return newFile.getContent();
        } catch (CantCreateFileException|CantLoadFileException e) {

        }
        return null;
    }

    private String createResourcePath(WalletFactoryProjectResource walletFactoryProjectResource, WalletFactoryProjectSkin projectSkin) {
        String initialPath = "/wallet_factory_projects";
        String resourcePath = "";
        WalletFactoryProjectProposal projectProposal = projectSkin.getWalletFactoryProjectProposal();
        WalletFactoryProject project = projectProposal.getProject();
        switch(walletFactoryProjectResource.getResourceType()) {
            case VIDEO:
                resourcePath = "videos/";
                break;
            case SOUND:
                resourcePath = "sounds/";
                break;
            case FONT_STYLE:
                resourcePath = "font_styles/";
                break;
            case IMAGE:
                resourcePath = "images/";
                break;
            case LAYOUT:
                resourcePath = "layouts/";
                break;
        }

        return initialPath + "/" +
               project.getName() + "/" +
               projectProposal.getAlias() + "/" +
               projectSkin .getName() + "/" +
               resourcePath;
    }


    /**
     * DealsWithPluginFileSystem interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealsWithPluginIdentity interface implementation.
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
