package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSaveWalletFactoryProyect;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubNotAuthorizedException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubRepositoryNotFoundException;
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

            String skinXML = XMLParser.parseObject(defaultSkin);
            defaultSkinFile.setContent(skinXML);
            defaultSkinFile.persistToMedia();

            // I will also save any other skin available
            if (walletFactoryProject.getSkins() !=null){
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
            if (walletFactoryProject.getLanguages() !=null){
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
        } catch (GitHubRepositoryNotFoundException | GitHubNotAuthorizedException e) {
            //for now I'm ignoring github errors.
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
                String publicKey;
                String name;
                String description;
                WalletType walletType;
                WalletFactoryProjectState walletFactoryProjectState;
                Timestamp creationTimestamp;
                Timestamp lastModificationTimestamp;
                Skin skin;
                List<Skin> skins;
                Language language;
                List<Language> languages;
                WalletNavigationStructure navigationStructure;
                int size;
                WalletCategory walletCategory;
                FactoryProjectType factoryProjectType;


                @Override
                public String getProjectPublicKey() {
                    return publicKey;
                }

                @Override
                public void setProjectPublickKey(String publickKey) {
                    this.publicKey = publickKey;
                }

                @Override
                public String getName() {
                    return this.name;
                }

                @Override
                public void setName(String name) {
                    this.name = name;
                }

                @Override
                public String getDescription() {
                    return this.description;
                }

                @Override
                public void setDescription(String description) {
                    this.description = description;
                }

                @Override
                public WalletType getWalletType() {
                    return walletType;
                }

                @Override
                public void setWalletType(WalletType walletType) {
                    this.walletType = walletType;
                }

                @Override
                public WalletFactoryProjectState getProjectState() {
                    return walletFactoryProjectState;
                }

                @Override
                public void setProjectState(WalletFactoryProjectState projectState) {
                    this.walletFactoryProjectState = projectState;
                }

                @Override
                public Timestamp getCreationTimestamp() {
                    return creationTimestamp;
                }

                @Override
                public void setCreationTimestamp(Timestamp timestamp) {
                    this.creationTimestamp = timestamp;
                }

                @Override
                public Timestamp getLastModificationTimestamp() {
                    return lastModificationTimestamp;
                }

                @Override
                public void setLastModificationTimeststamp(Timestamp timestamp) {
                    lastModificationTimestamp = timestamp;
                }

                @Override
                public Skin getDefaultSkin() {
                    return skin;
                }

                @Override
                public void setDefaultSkin(Skin skin) {
                    this.skin = skin;
                }

                @Override
                public List<Skin> getSkins() {
                    return this.skins;
                }

                @Override
                public Language getDefaultLanguage() {
                    return language;
                }

                @Override
                public void setDefaultLanguage(Language language) {
                    this.language = language;
                }

                @Override
                public List<Language> getLanguages() {
                    return languages;
                }

                @Override
                public WalletNavigationStructure getNavigationStructure() {
                    return navigationStructure;
                }

                @Override
                public void setNavigationStructure(WalletNavigationStructure navigationStructure) {
                    this.navigationStructure = navigationStructure;
                }


                @Override
                public void setSkins(List<Skin> skins) {
                    this.skins = skins;
                }

                @Override
                public void setLanguages(List<Language> languages) {
                    this.languages = languages;
                }

                @Override
                public int getSize() {
                    return size;
                }

                @Override
                public void setSize(int size) {
                    this.size = size;
                }

                @Override
                public WalletCategory getWalletCategory() {
                    return walletCategory;
                }

                @Override
                public void setWalletCategory(WalletCategory walletCategory) {
                    this.walletCategory = walletCategory;
                }

                @Override
                public FactoryProjectType getFactoryProjectType() {
                    return factoryProjectType;
                }

                @Override
                public void setFactoryProjectType(FactoryProjectType factoryProjectType) {
                    this.factoryProjectType = factoryProjectType;

                }
            };


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
        Skin skin = new Skin();

        PluginTextFile skinFile = pluginFileSystem.getTextFile(this.pluginId, projectPublicKey, id.toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        String skinXMLContent = skinFile.getContent();
        skin = (Skin) XMLParser.parseXML(skinXMLContent, skin);
        return skin;

    }

    private WalletNavigationStructure loadNavigationStructureFromXML(String projectPublicKey, String publicKey) throws FileNotFoundException, CantCreateFileException {
        WalletNavigationStructure navigationStructure = new WalletNavigationStructure();

        PluginTextFile navigationStructureFile = pluginFileSystem.getTextFile(this.pluginId, projectPublicKey, publicKey, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        navigationStructure = (WalletNavigationStructure) XMLParser.parseXML(navigationStructureFile.getContent(), navigationStructure);
        return navigationStructure;

    }

    private Language loadLanguageFromXML(String projectPublicKey, UUID id) throws FileNotFoundException, CantCreateFileException {
        Language language = new Language();

        PluginTextFile languageFile = pluginFileSystem.getTextFile(this.pluginId, projectPublicKey, id.toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        language = (Language) XMLParser.parseXML(languageFile.getContent(), language);
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
                System.err.println("Method: getWalletFactoryProject - TENGO RETURN NULL");
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
                System.err.println("Method: getColumn - TENGO RETURN NULL");
                return null;
            }

            @Override
            public String getValue() {
                System.err.println("Method: getValue - TENGO RETURN NULL");
                return null;
            }

            @Override
            public DatabaseFilterType getType() {
                System.err.println("Method: getType - TENGO RETURN NULL");
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
