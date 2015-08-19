package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.XML;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database.WalletFactoryMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.exceptions.MissingProjectDataException;

import java.util.UUID;

/**
 * Created by rodrigo on 8/17/15.
 */
public class WalletFactoryProjectManager implements DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem {

    UUID pluginId;

    /**
     * DealsWithPluginDatabaseSystem interface variable and implementation
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginFileSystem interface variable and implementation
     */
    PluginFileSystem pluginFileSystem;
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * constructor
     * @param pluginId
     * @param pluginDatabaseSystem
     * @param pluginFileSystem
     */
    public WalletFactoryProjectManager(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
    }


    private void saveWalletFactoryProjectDatainDatabase(WalletFactoryProject walletFactoryProject) throws DatabaseOperationException, MissingProjectDataException {
        WalletFactoryMiddlewareDao dao = new WalletFactoryMiddlewareDao(pluginDatabaseSystem, pluginId);
        dao.insertWalletFactoryProjectData(walletFactoryProject);
    }

    private void saveWalletFactoryProjectSkinXML(WalletFactoryProject walletFactoryProject) throws CantCreateFileException, CantPersistFileException {
        //If I have a skin in the project, I will save it.
        Skin defaultSkin =  walletFactoryProject.getDefaultSkin();
        if (defaultSkin != null){
            // I will save the default skin into an xml
            PluginTextFile defaultSkinFile;
            try {
                defaultSkinFile = pluginFileSystem.getTextFile(pluginId, walletFactoryProject.getProjectPublicKey(), defaultSkin.getId().toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            } catch (FileNotFoundException e) {
                // if the file doesn't exists, I'll create it.
                defaultSkinFile =  pluginFileSystem.createTextFile(pluginId, walletFactoryProject.getProjectPublicKey(), defaultSkin.getId().toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            }

            defaultSkinFile.setContent(XMLParser.parseObject(defaultSkin));
            defaultSkinFile.persistToMedia();

            // I will also save any other skin available
            if (!walletFactoryProject.getSkins().isEmpty()){
                for (Skin skin : walletFactoryProject.getSkins()){
                    PluginTextFile skinFile;
                    try {
                        skinFile = pluginFileSystem.getTextFile(pluginId, walletFactoryProject.getProjectPublicKey(), skin.getId().toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                    } catch (FileNotFoundException e) {
                        skinFile = pluginFileSystem.createTextFile(pluginId, walletFactoryProject.getProjectPublicKey(), skin.getId().toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                    }
                    skinFile.setContent(XMLParser.parseObject(skin));
                    skinFile.persistToMedia();
                }
            }
        }
    }

    private void saveWalletFactoryProjectLanguageXML(WalletFactoryProject walletFactoryProject) throws CantCreateFileException, CantPersistFileException {
        //If I have a Language in the project, I will save it.
        Language defaultLanguage =  walletFactoryProject.getDefaultLanguage();
        if (defaultLanguage!= null){
            // I will save the default skin into an xml
            PluginTextFile defaultLanguageFile;
            try {
                defaultLanguageFile = pluginFileSystem.getTextFile(pluginId, walletFactoryProject.getProjectPublicKey(), defaultLanguage.getId().toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            } catch (FileNotFoundException e) {
                // if the file doesn't exists, I'll create it.
                defaultLanguageFile =  pluginFileSystem.createTextFile(pluginId, walletFactoryProject.getProjectPublicKey(), defaultLanguage.getId().toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            }

            defaultLanguageFile.setContent(XMLParser.parseObject(defaultLanguage));
            defaultLanguageFile.persistToMedia();

            // I will also save any other skin available
            if (!walletFactoryProject.getLanguages().isEmpty()){
                for (Language language : walletFactoryProject.getLanguages()){
                    PluginTextFile languageFile;
                    try {
                        languageFile = pluginFileSystem.getTextFile(pluginId, walletFactoryProject.getProjectPublicKey(), language.getId().toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                    } catch (FileNotFoundException e) {
                        languageFile = pluginFileSystem.createTextFile(pluginId, walletFactoryProject.getProjectPublicKey(), language.getId().toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                    }
                    languageFile.setContent(XMLParser.parseObject(language));
                    languageFile.persistToMedia();
                }
            }
        }
    }

    private void saveWalletFactoryProjectNavigationStructureXML(WalletFactoryProject walletFactoryProject) throws CantCreateFileException, CantPersistFileException {
        WalletNavigationStructure navigationStructure = walletFactoryProject.getNavigationStructure();
        if (navigationStructure != null){
            PluginTextFile navigationStructureFile;
            try{
                navigationStructureFile = pluginFileSystem.getTextFile(pluginId, walletFactoryProject.getProjectPublicKey(), navigationStructure.getPublicKey(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            } catch (FileNotFoundException e) {
                navigationStructureFile = pluginFileSystem.createTextFile(pluginId, walletFactoryProject.getProjectPublicKey(), navigationStructure.getPublicKey(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            }
            navigationStructureFile.setContent(XMLParser.parseObject(navigationStructure));
            navigationStructureFile.persistToMedia();
        }
    }

    /**
     * Will save to database and disk all the information stored in the WalletFactoryProject class
     * It includes persisting the information in the database and XML on files.
     * @param walletFactoryProject
     */
    public void saveNewWalletFactoryProject(WalletFactoryProject walletFactoryProject) throws CantCreateWalletFactoryProjectException {
        try {
            saveWalletFactoryProjectDatainDatabase(walletFactoryProject);
            saveWalletFactoryProjectSkinXML(walletFactoryProject);
            saveWalletFactoryProjectLanguageXML(walletFactoryProject);
            saveWalletFactoryProjectNavigationStructureXML(walletFactoryProject);

        } catch (DatabaseOperationException | MissingProjectDataException | CantPersistFileException | CantCreateFileException e) {
            throw new CantCreateWalletFactoryProjectException(CantCreateWalletFactoryProjectException.DEFAULT_MESSAGE, e, walletFactoryProject.getName(), null);
        } catch (Exception exception){
            throw new CantCreateWalletFactoryProjectException(CantCreateWalletFactoryProjectException.DEFAULT_MESSAGE, exception, walletFactoryProject.getName(), null);
        }
    }

}
