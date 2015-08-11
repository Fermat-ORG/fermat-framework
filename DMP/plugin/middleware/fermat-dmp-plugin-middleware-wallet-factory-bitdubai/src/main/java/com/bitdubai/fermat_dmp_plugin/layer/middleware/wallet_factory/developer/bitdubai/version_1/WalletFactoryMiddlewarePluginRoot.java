package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
//import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantExportWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantImportWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.GitHubCantGetDirectoryContent;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.GitHubCantReadFileContent;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProjectNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.DescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryManager;

//import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
//import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposalManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantGetWalletSkinStructureException;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubCredentialsExpectedException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubNotAuthorizedException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.GitHubRepositoryNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.interfaces.DealsWithWalletSkin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.interfaces.WalletSkinManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.exceptions.CantInitializeWalletFactoryMiddlewareDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database.WalletFactoryMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database.WalletFactoryMiddlewareDeveloperDatabaseFactory;

import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProject;

import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectSkin;


import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.DescriptorFactoryMiddlewareProject;

import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.utils.RepositoryManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;


import org.kohsuke.github.GHRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Allows to create a Wallet Factory Project and associate it to a DeveloperIdentity
 * Manage the relation Developer / Projects
 * Each project generates different versions of the new Wallet while the developer exports it in a format readable by the wallet publisher.
 *
 * You can create a project since nothing.
 * You can clone an existing project.
 * If the project is cloned or forked, its structure is copied from the other project and do a local copy.
 *
 * One project consist in a navigation structure and multimedia resources (images, etc.).
 *
 * Can delivery the list of the projects associated to the current logged developer.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewarePluginRoot implements DatabaseManagerForDevelopers, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, DealsWithWalletSkin, LogManagerForDevelopers, Plugin, Service, WalletFactoryManager {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    /**
     * WalletFactoryManager Interfaces member variables.
     */

    private final String WALLET_FACTORY_PROJECTS_PATH = "wallet_factory_projects";
    WalletFactoryMiddlewareDao walletFactoryMiddlewareProjectDao;

    private WalletSkinManager walletSkinManager;

    @Override
    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
        walletFactoryMiddlewareProjectDao = new WalletFactoryMiddlewareDao(pluginDatabaseSystem);
        try {
            walletFactoryMiddlewareProjectDao.initializeDatabase(pluginId, pluginId.toString());
        } catch (CantInitializeWalletFactoryMiddlewareDatabaseException e) {
            this.serviceStatus = ServiceStatus.STOPPED;
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "", "");
        }

    }
    @Override
    public void pause(){
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume(){
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop(){
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    @Override
    public DescriptorFactoryMiddlewareProject createEmptyWalletFactoryProject(String name, Wallets walletType) throws CantCreateWalletFactoryProjectException {
        try {
            // TODO GET CURRENT LOGGED DEVELOPER
            String developerPublicKey = "";

            DescriptorFactoryMiddlewareProject walletFactoryMiddlewareProject = new DescriptorFactoryMiddlewareProject(name, developerPublicKey, walletType, WALLET_FACTORY_PROJECTS_PATH);
            walletFactoryMiddlewareProjectDao.create(walletFactoryMiddlewareProject);

            return walletFactoryMiddlewareProject;
        } catch (CantCreateWalletFactoryProjectException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public void importWalletFactoryProjectFromDevice(String newName, UUID resourcesId, UUID navigationStructureId) throws CantImportWalletFactoryProjectException {
        // TODO LOOK FOR A WAY TO TO THIS
    }

    /**
     * Modified by Manuel Perez on 07/06/2015
     * */
    @Override
    public void importWalletFactoryProjectFromRepository(String user, String password, String repository, String folderRepositoryLink) throws CantImportWalletFactoryProjectException {
        // TODO LOOK FOR A WAY TO TO THIS
        WalletFactoryMiddlewareProject importedWalletFactoryProject=null;
        //String mainFileWalletRepository;
        String xml;
        String xmlSkin;
        String xmlLanguage;
        String mainFolderWalletRepository=folderRepositoryLink+"/navigation_structure";
        Skin importedSkin;
        List<String> directoryGenericsPaths;
        WalletFactoryMiddlewareProjectSkin walletFactoryMiddlewareProjectSkin=null;
        //String repositoryURL;

        try{
            //Import from repository
            RepositoryManager repositoryManager=new RepositoryManager();
            GHRepository ghRepository=repositoryManager.getRepository(user, password, repository);
            //We get all the versions published of this wallet
            List<String> directoryPaths=repositoryManager.getDirectoryContent(ghRepository, mainFolderWalletRepository);
            for(String mainFileWalletRepository: directoryPaths){

                xml=repositoryManager.getFileContent(ghRepository, mainFileWalletRepository);
                //Convert XML read from a repository file, we cast this information to a WalletFactoryProject
                importedWalletFactoryProject=(WalletFactoryMiddlewareProject)XMLParser.parseXML(xml,importedWalletFactoryProject);
                //Persists this wallet in the Database
               // walletFactoryMiddlewareProjectDao.create(importedWalletFactoryProject);
                //TODO: Deals with skins
                //Now, we get the Skins from the repository
                directoryGenericsPaths=repositoryManager.getDirectoryContent(ghRepository,folderRepositoryLink+"/skins");
                for(String path: directoryGenericsPaths){

                    xmlSkin=null;
                    try{
                        xmlSkin=repositoryManager.getFileContent(ghRepository,path);

                    }catch (GitHubCantReadFileContent exception){
                        continue;
                    }
                    importedSkin=this.walletSkinManager.getSkinFromXmlString(xmlSkin);
                    //WalletSkinMiddlewarePluginRoot walletSkinMiddlewarePluginRoot=new WalletSkinMiddlewarePluginRoot();
                    //walletSkinMiddlewarePluginRoot.setPluginDatabaseSystem(this.pluginDatabaseSystem);

                }

                //TODO:Deals with languages
                //WalletLanguageMiddlewarePluginRoot walletLanguageMiddlewarePluginRoot=new WalletLanguageMiddlewarePluginRoot();
                //walletLanguageMiddlewarePluginRoot.setPluginDatabaseSystem(this.pluginDatabaseSystem);
                //walletLanguageMiddlewarePluginRoot.setId(this.pluginId);
                directoryGenericsPaths=repositoryManager.getDirectoryContent(ghRepository,folderRepositoryLink+"/languages");
                for(String path: directoryGenericsPaths){

                    try{
                        xmlLanguage=repositoryManager.getFileContent(ghRepository,path);

                    }catch (GitHubCantReadFileContent exception){
                        continue;
                    }

                    //Language language=walletLanguageMiddlewarePluginRoot.getLanguageFromXmlString(xmlLanguage);
                    //this.walletFactoryMiddlewareProjectDao.

                }

            }


        }catch(GitHubNotAuthorizedException| GitHubRepositoryNotFoundException| GitHubCredentialsExpectedException exception){

            throw new CantImportWalletFactoryProjectException(GitHubCredentialsExpectedException.DEFAULT_MESSAGE, exception,"importWalletFactoryProjectFromRepository","Check the cause" );

        }catch(GitHubCantReadFileContent exception){

            throw new CantImportWalletFactoryProjectException(GitHubCantReadFileContent.DEFAULT_MESSAGE,exception,"importWalletFactoryProjectFromRepository","Check the cause" );

        }/*catch(CantCreateWalletFactoryProjectException exception){

            throw new CantImportWalletFactoryProjectException(CantCreateWalletFactoryProjectException.DEFAULT_MESSAGE,exception,"importWalletFactoryProjectFromRepository","Check the cause");

        }*/catch(GitHubCantGetDirectoryContent exception){

            throw new CantImportWalletFactoryProjectException(GitHubCantGetDirectoryContent.DEFAULT_MESSAGE,exception,"importWalletFactoryProjectFromRepository","Check the cause");


        }catch(CantGetWalletSkinStructureException exception){

            throw new CantImportWalletFactoryProjectException(CantGetWalletSkinStructureException.DEFAULT_MESSAGE,exception,"importWalletFactoryProjectFromRepository","Check the cause");

        }



    }

    /**
     * Modified by Manuel Perez on 07/06/2015
     * */
    @Override
    public void exportWalletFactoryProjectToRepository(String name, String password,String repository, DescriptorFactoryProject walletFactoryProject) throws CantExportWalletFactoryProjectException {

        //TODO: Where can I find the github login information?

        String commitComment="Upload new wallet factory project to fermat repository";

        String walletFactoryProjectPath;
        String walletFactoryProjectName;

        try {

            String xml=XMLParser.parseObject(walletFactoryProject);
            walletFactoryProjectPath=walletFactoryProject.getPath();
            walletFactoryProjectName=walletFactoryProject.getName();
            PluginTextFile pluginTextFile=pluginFileSystem.createTextFile(pluginId, walletFactoryProjectPath, walletFactoryProjectName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            pluginTextFile.setContent(xml);
            //Upload to Repository
            RepositoryManager repositoryManager=new RepositoryManager();
            GHRepository ghRepository=repositoryManager.getRepository(name,password,repository);
            repositoryManager.createGitHubFile(ghRepository, walletFactoryProjectPath, walletFactoryProjectName, commitComment);

        }catch(GitHubNotAuthorizedException| GitHubRepositoryNotFoundException| GitHubCredentialsExpectedException exception){

            throw new CantExportWalletFactoryProjectException(GitHubCredentialsExpectedException.DEFAULT_MESSAGE, exception,"exportWalletFactoryProjectToRepository","Check the cause" );

        }
        catch(CantCreateFileException exception){

            throw new CantExportWalletFactoryProjectException(CantCreateFileException.DEFAULT_MESSAGE, exception,"exportWalletFactoryProjectToRepository","Check the cause");

        }
        catch (Exception exception) {

            throw new CantExportWalletFactoryProjectException(CantExportWalletFactoryProjectException.DEFAULT_MESSAGE, FermatException.wrapException(exception),"exportWalletFactoryProjectToRepository","Check the cause");

        }

    }

    @Override
    public List<DescriptorFactoryProject> getAllWalletFactoryProjects() throws CantGetWalletFactoryProjectsException {
        try {
            // TODO GET CURRENT LOGGED DEVELOPER
            String developerPublicKey = "";
            return walletFactoryMiddlewareProjectDao.findAll(developerPublicKey);
        } catch (CantGetWalletFactoryProjectsException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public DescriptorFactoryProject getWalletFactoryProject(String name) throws CantGetWalletFactoryProjectException, ProjectNotFoundException {
        try {
            // TODO GET CURRENT LOGGED DEVELOPER
            String developerPublicKey = "";
            return walletFactoryMiddlewareProjectDao.findByName(name, developerPublicKey);
        } catch (CantGetWalletFactoryProjectException|ProjectNotFoundException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }


    /**
     * DatabaseManagerForDevelopers Interface Implementation
     */
    /**
     * DatabaseManagerForDevelopers interface implementation
     * Returns the list of databases implemented on this plug in.
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        WalletFactoryMiddlewareDeveloperDatabaseFactory dbFactory = new WalletFactoryMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    /**
     * returns the list of tables for the given database
     *
     * @param developerObjectFactory
     * @param developerDatabase
     * @return
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        WalletFactoryMiddlewareDeveloperDatabaseFactory dbFactory = new WalletFactoryMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    /**
     * returns the list of records for the passed table
     *
     * @param developerObjectFactory
     * @param developerDatabase
     * @param developerDatabaseTable
     * @return
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        WalletFactoryMiddlewareDeveloperDatabaseFactory dbFactory = new WalletFactoryMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            dbFactory.initializeDatabase();
            developerDatabaseTableRecordList = dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println("******* Error trying to get database table list for plugin Wallet Factory");
        }
        return developerDatabaseTableRecordList;
    }

    /**
     * DealsWithLogger Interface implementation.
     */

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.WalletFactoryMiddlewarePluginRoot");

        /**
         * I return the values.
         */
        return returnedClasses;
    }


    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (WalletFactoryMiddlewarePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletFactoryMiddlewarePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletFactoryMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletFactoryMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }


    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    /**
     * DealWithPluginFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * Plugin methods implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void setWalletSkinManager(WalletSkinManager walletSkinManager) {

        this.walletSkinManager=walletSkinManager;

    }
}
