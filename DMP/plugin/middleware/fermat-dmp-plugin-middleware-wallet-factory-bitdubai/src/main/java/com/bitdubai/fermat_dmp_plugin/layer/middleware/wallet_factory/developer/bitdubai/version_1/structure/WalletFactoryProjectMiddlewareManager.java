package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSaveWalletFactoryProyect;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database.WalletFactoryMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database.WalletFactoryMiddlewareDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.exceptions.MissingProjectDataException;

import org.apache.commons.codec.language.bm.Lang;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by rodrigo on 8/17/15.
 */
public class WalletFactoryProjectMiddlewareManager implements DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem {

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
    public WalletFactoryProjectMiddlewareManager(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
    }

    private WalletFactoryMiddlewareDao getDao(){
        WalletFactoryMiddlewareDao dao = new WalletFactoryMiddlewareDao(pluginDatabaseSystem, pluginId);
        return dao;
    }

    private void saveWalletFactoryProjectDataInDatabase(WalletFactoryProject walletFactoryProject) throws DatabaseOperationException, MissingProjectDataException {
        getDao().saveWalletFactoryProjectData(walletFactoryProject);
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
    public void saveWalletFactoryProject(WalletFactoryProject walletFactoryProject) throws CantSaveWalletFactoryProyect {
        try {
            saveWalletFactoryProjectDataInDatabase(walletFactoryProject);
            saveWalletFactoryProjectSkinXML(walletFactoryProject);
            saveWalletFactoryProjectLanguageXML(walletFactoryProject);
            saveWalletFactoryProjectNavigationStructureXML(walletFactoryProject);

            //once I have every saved locally (both in db and XML files) I will upload them to github
            GithubManager githubManager = new GithubManager("acostarodrigo/testFermat", "acostarodrigo", "");
            githubManager.saveWalletFactoryProject(walletFactoryProject);

        } catch (DatabaseOperationException | MissingProjectDataException | CantPersistFileException | CantCreateFileException e) {
            throw new CantSaveWalletFactoryProyect(CantSaveWalletFactoryProyect.DEFAULT_MESSAGE, e, walletFactoryProject.getName(), null);
        } catch (Exception exception){
            throw new CantSaveWalletFactoryProyect(CantSaveWalletFactoryProyect.DEFAULT_MESSAGE, exception, walletFactoryProject.getName(), null);
        }
    }

    /**
     * Creates and empty project just with basic information as the publicKey, state and creation time.
     * It also persists all the information needed on disk and database.
     * @return
     */
    public WalletFactoryProject getNewWalletFactoryProject() throws CantCreateWalletFactoryProjectException {
        try{
            WalletFactoryProject walletFactoryProject = new WalletFactoryProject() {
                @Override
                public String getProjectPublicKey() {
                    ECCKeyPair publicKey = new ECCKeyPair();
                    return publicKey.getPublicKey().toString();
                }

                @Override
                public void setProjectPublickKey(String publickKey) {

                }

                @Override
                public String getName() {
                    return null;
                }

                @Override
                public void setName(String name) {

                }

                @Override
                public String getDescription() {
                    return null;
                }

                @Override
                public void setDescription(String description) {

                }

                @Override
                public WalletType getWalletType() {
                    return null;
                }

                @Override
                public void setWalletType(WalletType walletType) {

                }

                @Override
                public WalletFactoryProjectState getProjectState() {
                    return WalletFactoryProjectState.IN_PROGRESS;
                }

                @Override
                public void setProjectState(WalletFactoryProjectState projectState) {

                }

                @Override
                public Timestamp getCreationTimestamp() {
                    java.util.Date date = new java.util.Date();
                    return new Timestamp(date.getTime());
                }

                @Override
                public void setCreationTimestamp(Timestamp timestamp) {

                }

                @Override
                public Timestamp getLastModificationTimestamp() {
                    return null;
                }

                @Override
                public void setLastModificationTimeststamp(Timestamp timestamp) {

                }

                @Override
                public Skin getDefaultSkin() {
                    return null;
                }

                @Override
                public void setDefaultSkin(Skin skin) {

                }

                @Override
                public List<Skin> getSkins() {
                    return null;
                }


                @Override
                public void deleteSkin(Skin skin) {

                }

                @Override
                public Language getDefaultLanguage() {
                    return null;
                }

                @Override
                public void setDefaultLanguage(Language language) {

                }

                @Override
                public List<Language> getLanguages() {
                    return null;
                }


                @Override
                public void deleteLanguage(Language language) {

                }

                @Override
                public WalletNavigationStructure getNavigationStructure() {
                    return null;
                }

                @Override
                public void setNavigationStructure(WalletNavigationStructure navigationStructure) {

                }

                @Override
                public void setSkins(List<Skin> skins) {

                }

                @Override
                public void setLanguages(List<Language> languages) {

                }

                @Override
                public int getSize() {
                    return 0;
                }

                @Override
                public void setSize(int size) {
                }
            };
            saveWalletFactoryProject(walletFactoryProject);

            return walletFactoryProject;
        } catch (Exception exception){
            throw new CantCreateWalletFactoryProjectException(CantCreateWalletFactoryProjectException.DEFAULT_MESSAGE, exception, "error creating new project.", null);
        }
    }

    /**
     * Will upload all the information to default repository
     * @param walletFactoryProject
     * @throws CantSaveWalletFactoryProyect
     */
    public void uploadWalletFactoryProjectToRepository(WalletFactoryProject walletFactoryProject) throws CantSaveWalletFactoryProyect {
        try{
            //once I have every saved locally (both in db and XML files) I will upload them to github
            GithubManager githubManager = new GithubManager("acostarodrigo/testFermat", "acostarodrigo", "");
            githubManager.saveWalletFactoryProject(walletFactoryProject);
        } catch (Exception e){
            throw new CantSaveWalletFactoryProyect(CantSaveWalletFactoryProyect.DEFAULT_MESSAGE, e, "error trying to upload project to repository", null);
        }
    }

    public void exportProjectToRepository(WalletFactoryProject walletFactoryProject, String repository, String userName, String password) throws CantSaveWalletFactoryProyect {
        try{
            //once I have every saved locally (both in db and XML files) I will upload them to github
            GithubManager githubManager = new GithubManager(repository, userName, password);
            githubManager.saveWalletFactoryProject(walletFactoryProject);
        } catch (Exception e){
            throw new CantSaveWalletFactoryProyect(CantSaveWalletFactoryProyect.DEFAULT_MESSAGE, e, "error trying to upload project to repository", "wrong repository, user or password");
        }
    }

    private Skin loadSkinFromXML(String projectPublicKey, UUID id) throws FileNotFoundException, CantCreateFileException {
        Skin skin;

        PluginTextFile skinFile = pluginFileSystem.getTextFile(this.pluginId, projectPublicKey, id.toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        skin = (Skin) XMLParser.parseXML(skinFile.getContent(), Skin.class);
        return skin;

    }

    private WalletNavigationStructure loadNavigationStructureFromXML(String projectPublicKey, String publicKey) throws FileNotFoundException, CantCreateFileException {
        WalletNavigationStructure navigationStructure;

        PluginTextFile navigationStructureFile = pluginFileSystem.getTextFile(this.pluginId, projectPublicKey, publicKey, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        navigationStructure = (WalletNavigationStructure) XMLParser.parseXML(navigationStructureFile.getContent(), WalletNavigationStructure.class);
        return navigationStructure;

    }

    private Language loadLanguageFromXML(String projectPublicKey, UUID id) throws FileNotFoundException, CantCreateFileException {
        Language language;

        PluginTextFile languageFile = pluginFileSystem.getTextFile(this.pluginId, projectPublicKey, id.toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        language = (Language) XMLParser.parseXML(languageFile.getContent(), Language.class);
        return language;
    }

    private List<WalletFactoryProject> getWalletFactoryProjects(DatabaseTableFilter filter) throws DatabaseOperationException, CantLoadTableToMemoryException, FileNotFoundException, CantCreateFileException {
        List<WalletFactoryProject> walletFactoryProjects = new ArrayList<>();

        for (WalletFactoryProject walletFactoryProject : getDao().getWalletFactoryProjects(filter)){
            //I will re create the skin object from the XML files using the Ids saved in database
            if (walletFactoryProject.getDefaultSkin() != null)
                walletFactoryProject.setDefaultSkin(loadSkinFromXML(walletFactoryProject.getProjectPublicKey(), walletFactoryProject.getDefaultSkin().getId()));

            List<Skin> skins = new ArrayList<>();
            for (Skin skin : walletFactoryProject.getSkins()){
                    skins.add(loadSkinFromXML(walletFactoryProject.getProjectPublicKey(), skin.getId()));
            }
            walletFactoryProject.setSkins(skins);

            // Now I will do the same with the Languages
            if (walletFactoryProject.getDefaultLanguage() != null)
                walletFactoryProject.setDefaultLanguage(loadLanguageFromXML(walletFactoryProject.getProjectPublicKey(), walletFactoryProject.getDefaultLanguage().getId()));

            List<Language> languages = new ArrayList<>();
            for (Language language : walletFactoryProject.getLanguages()){
                languages.add(loadLanguageFromXML(walletFactoryProject.getProjectPublicKey(), language.getId()));
            }
            walletFactoryProject.setLanguages(languages);

            // Lastly wil load the Navigation Structure from the XML if it is defined in the DB
            if (walletFactoryProject.getNavigationStructure() != null)
                walletFactoryProject.setNavigationStructure(loadNavigationStructureFromXML(walletFactoryProject.getProjectPublicKey(), walletFactoryProject.getNavigationStructure().getPublicKey()));

            // At this point I have a WalletFactoryProject totally loaded from the XML files
            walletFactoryProjects.add(walletFactoryProject);
        }

        return walletFactoryProjects;
    }

    /**
     * Returns the WalletFactoryProject for the specified publicKey
     * @param publicKey
     * @return
     * @throws CantGetWalletFactoryProjectException
     */
    public WalletFactoryProject getWalletFactoryProject(final String publicKey) throws CantGetWalletFactoryProjectException{
        // I define the filter to search for the public Key
        DatabaseTableFilter filter = new DatabaseTableFilter() {
            @Override
            public void setColumn(String column) {

            }

            @Override
            public void setType(DatabaseFilterType type) {

            }

            @Override
            public void setValue(String value) {

            }

            @Override
            public String getColumn() {
                return WalletFactoryMiddlewareDatabaseConstants.PROJECT_PUBLICKEY_COLUMN_NAME;
            }

            @Override
            public String getValue() {
                return publicKey;
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };
        List<WalletFactoryProject> walletFactoryProjects;
        try {
            walletFactoryProjects = getWalletFactoryProjects(filter);

            if (walletFactoryProjects.size() > 1)
                throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, null, "PublicKey: " + publicKey, "Unexpected value returned. Duplicated projects in database.");

            if (walletFactoryProjects.size() == 1)
                return walletFactoryProjects.get(0);
            else
                return null;

        } catch (DatabaseOperationException | CantLoadTableToMemoryException e) {
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, "PublicKey: " + publicKey, "database error");
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, "PublicKey: " + publicKey, "Mismatch between database records and XML files.");
        } catch (Exception e){
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, "PublicKey: " + publicKey, "Unknown.");
        }
    }

    /**
     * Gets the list of WalletFactoryProjects for the specified State
     * @param projectState
     * @return
     * @throws CantGetWalletFactoryProjectException
     */
    public List<WalletFactoryProject> getWalletFactoryProjectsByState(final WalletFactoryProjectState projectState) throws CantGetWalletFactoryProjectException {
        // I define the filter to search for project state
        DatabaseTableFilter filter = new DatabaseTableFilter() {
            @Override
            public void setColumn(String column) {

            }

            @Override
            public void setType(DatabaseFilterType type) {

            }

            @Override
            public void setValue(String value) {

            }

            @Override
            public String getColumn() {
                return WalletFactoryMiddlewareDatabaseConstants.PROJECT_STATE_COLUMN_NAME;
            }

            @Override
            public String getValue() {
                return projectState.value();
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };
        List<WalletFactoryProject> walletFactoryProjects;
        try {
            walletFactoryProjects = getWalletFactoryProjects(filter);

            return walletFactoryProjects;

        } catch (DatabaseOperationException | CantLoadTableToMemoryException e) {
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, "ProjectState: " + projectState.toString(), "database error");
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, "ProjectState: " + projectState.toString(), "Mismatch between database records and XML files.");
        } catch (Exception e){
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, "ProjectState: " + projectState.toString(), "Unknown.");
        }
    }

    /**
     * Gets all WalletFactoryProjects stored
     * @return
     * @throws CantGetWalletFactoryProjectException
     */
    public List<WalletFactoryProject> getAllFactoryProjects() throws CantGetWalletFactoryProjectException {
        // I define a blank filter
        DatabaseTableFilter filter = new DatabaseTableFilter() {
            @Override
            public void setColumn(String column) {

            }

            @Override
            public void setType(DatabaseFilterType type) {

            }

            @Override
            public void setValue(String value) {

            }

            @Override
            public String getColumn() {
                return null;
            }

            @Override
            public String getValue() {
                return null;
            }

            @Override
            public DatabaseFilterType getType() {
                return null;
            }
        };
        List<WalletFactoryProject> walletFactoryProjects;
        try {
            walletFactoryProjects = getWalletFactoryProjects(filter);

            return walletFactoryProjects;

        } catch (DatabaseOperationException | CantLoadTableToMemoryException e) {
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, null, "database error");
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, null, "Mismatch between database records and XML files.");
        } catch (Exception e){
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, null, "Unknown.");
        }
    }

}
