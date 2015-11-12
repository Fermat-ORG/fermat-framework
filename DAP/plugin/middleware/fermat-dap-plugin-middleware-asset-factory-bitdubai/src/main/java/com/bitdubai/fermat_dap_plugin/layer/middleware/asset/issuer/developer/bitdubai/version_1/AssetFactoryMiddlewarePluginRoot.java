package com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums.AssetBehavior;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateEmptyAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantDeleteAsserFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantPublishAssetFactoy;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.AssetFactoryMiddlewareManager;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.AssetIssuerIdentity;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.database.AssertFactoryMiddlewareDatabaseConstant;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.database.AssetFactoryMiddlewareDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.database.AssetFactoryMiddlewareDeveloperFactory;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.events.AssetFactoryMiddlewareMonitorAgent;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by rodrigo on 9/7/15.
 */
public class AssetFactoryMiddlewarePluginRoot extends AbstractPlugin implements
        AssetFactoryManager,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem        ;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM   , layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_MANAGER)
    private WalletManagerManager walletManagerManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM   , layer = Layers.DIGITAL_ASSET_TRANSACTION, plugin = Plugins.ASSET_ISSUING)
    private AssetIssuingManager assetIssuingManager;

    AssetFactoryMiddlewareManager assetFactoryMiddlewareManager;

    public AssetFactoryMiddlewarePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private AssetFactoryMiddlewareMonitorAgent assetFactoryMiddlewareMonitorAgent;

    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the asset issuing plugin
     * @throws CantGetLoggedInDeviceUserException
     * @throws CantSetObjectException
     * @throws CantStartAgentException
     */
    private void startMonitorAgent() throws CantGetLoggedInDeviceUserException, CantSetObjectException, CantStartAgentException {
        if(assetFactoryMiddlewareMonitorAgent == null) {
            assetFactoryMiddlewareMonitorAgent = new AssetFactoryMiddlewareMonitorAgent(
                    assetFactoryMiddlewareManager,
                    assetIssuingManager,
                    errorManager
            );

            assetFactoryMiddlewareMonitorAgent.start();
        }else assetFactoryMiddlewareMonitorAgent.start();
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        AssetFactoryMiddlewareDeveloperFactory assetFactoryMiddlewareDeveloperFactory = new AssetFactoryMiddlewareDeveloperFactory(pluginDatabaseSystem, pluginId);
        return assetFactoryMiddlewareDeveloperFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        AssetFactoryMiddlewareDeveloperFactory assetFactoryMiddlewareDeveloperFactory = new AssetFactoryMiddlewareDeveloperFactory(pluginDatabaseSystem, pluginId);
        return assetFactoryMiddlewareDeveloperFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        AssetFactoryMiddlewareDeveloperFactory assetFactoryMiddlewareDeveloperFactory = new AssetFactoryMiddlewareDeveloperFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            assetFactoryMiddlewareDeveloperFactory.initializeDatabase();
            developerDatabaseTableRecordList = assetFactoryMiddlewareDeveloperFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println("******* Error trying to get database table list for plugin Asset Factory ******");
        }
        return developerDatabaseTableRecordList;
    }

    @Override
    public void start() throws CantStartPluginException {
        assetFactoryMiddlewareManager = new AssetFactoryMiddlewareManager(assetIssuingManager, pluginDatabaseSystem, pluginFileSystem, pluginId, walletManagerManager) ;
        try {
            Database database = pluginDatabaseSystem.openDatabase(pluginId, AssertFactoryMiddlewareDatabaseConstant.DATABASE_NAME);
            //TODO: Borrar luego solo es para Test
            //Para probar primero descomente el metodo testSaveAssetFactory para que sea guardado el Asset en Factory
            //Luego comente de nuevo el metodo testSaveAssetFactory
            //Luego Descomente el bloque de codigo de metodo testPublishAsset, para que proceda con la publicacion del Asset
            //testSaveAssetFactory();
/*            try
            {
                testPublishAsset();
            }catch(CantIssueDigitalAssetsException e){
                System.out.println("******* Metodo testAssetFactory, issuerAsset, Error. Franklin ******" );
                e.printStackTrace();
            }*/
            //Check Assets in Draft for star Agent
            checkAssetDraft();

            database.closeDatabase();
        }
        catch (CantOpenDatabaseException | DatabaseNotFoundException | CantGetLoggedInDeviceUserException | CantSetObjectException | CantStartAgentException | CantLoadTableToMemoryException e)
        {
            try
            {
                AssetFactoryMiddlewareDatabaseFactory assetFactoryMiddlewareDatabaseFactory = new AssetFactoryMiddlewareDatabaseFactory(this.pluginDatabaseSystem);
                assetFactoryMiddlewareDatabaseFactory.createDatabase(this.pluginId, AssertFactoryMiddlewareDatabaseConstant.DATABASE_NAME);
            }
            catch(CantCreateDatabaseException cantCreateDatabaseException)
            {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            }catch (Exception exception) {
                throw new CantStartPluginException("Cannot start AssetFactoryMiddleware plugin.", FermatException.wrapException(exception), null, null);
            }
        }
        this.serviceStatus = ServiceStatus.STARTED;
    }

    public void checkAssetDraft() throws CantLoadTableToMemoryException, CantGetLoggedInDeviceUserException, CantSetObjectException, CantStartAgentException {
        //TODO: Revisar el metodo assetFactoryMiddlewareManager.checkAssetDraft()
        boolean isCheckAssetDraft = true;//assetFactoryMiddlewareManager.checkAssetDraft();
        if (isCheckAssetDraft){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
            startMonitorAgent();
        }
    }

    public void testSaveAssetFactory()
    {
        try {
            java.util.Date date= new java.util.Date();
            System.out.println(new Timestamp(date.getTime()));
            AssetFactory assetFactory = assetFactoryMiddlewareManager.getNewAssetFactory();
            System.out.println(assetFactory.getPublicKey());
            //assetFactory.setPublicKey("ASD-125412541-BS-854");
            assetFactory.setDescription("Asset de Prueba");
            assetFactory.setAssetBehavior(AssetBehavior.RECUPERATION_BITCOINS);
            assetFactory.setAmount(1);
            assetFactory.setFee(1);
            assetFactory.setIsRedeemable(false);
            assetFactory.setName("Asset de Mcdonald - modificado");
            assetFactory.setCreationTimestamp(new Timestamp(date.getTime()));
            assetFactory.setExpirationDate(new Timestamp(date.getTime()));
            assetFactory.setLastModificationTimeststamp(new Timestamp(date.getTime()));
            assetFactory.setQuantity(2);
            assetFactory.setState(State.DRAFT);
            assetFactory.setWalletPublicKey(new ECCKeyPair().getPublicKey());
//            Resource resource = new Resource();
//            List<Resource> resources = new ArrayList<>();
//            resource.setId(UUID.randomUUID());
//            resource.setName("Foto 1");
//            resource.setFileName("imagen2.png");
//            resource.setResourceType(ResourceType.IMAGE);
//            resource.setResourceDensity(ResourceDensity.HDPI);
//            resource.setResourceBinayData(new byte[]{0xa, 0x2, 0xf, (byte) 0xff, (byte) 0xff, (byte) 0xff});
//            resources.add(resource);
            assetFactory.setResources(null);
            AssetIssuerIdentity assetIssuerIdentity = new AssetIssuerIdentity();
            assetIssuerIdentity.setAlias("Franklin Marcano");
            assetIssuerIdentity.setPublicKey("ASDS-10087982");
            assetFactory.setIdentityAssetIssuer(assetIssuerIdentity);
            assetFactoryMiddlewareManager.saveAssetFactory(assetFactory);
        }catch (Exception e){
            System.out.println("******* Metodo testAssetFactory, Error. Franklin ******" );
            e.printStackTrace();
        }
    }

    public AssetFactory testPublishAsset() throws CantIssueDigitalAssetsException {
        try{
            java.util.Date date= new java.util.Date();
            System.out.println(new Timestamp(date.getTime()));
            String walletPublicKey = assetFactoryMiddlewareManager.getInstallWallets().get(0).getWalletPublicKey();
            AssetFactory assetFactory = assetFactoryMiddlewareManager.getNewAssetFactory();
            assetFactory = assetFactoryMiddlewareManager.getAssetFactory("ASD-125412541-BS-854");
//            DigitalAsset digitalAsset = new DigitalAsset();
//            DigitalAssetContract digitalAssetContract = new DigitalAssetContract();

//            for(ContractProperty property : assetFactory.getContractProperties())
//            {
//                ContractProperty contractProperty = digitalAssetContract.getContractProperty(property.getName());
//                digitalAssetContract.setContractProperty(contractProperty);
//            }
//            ContractProperty redeemable;
//            ContractProperty expirationDate;
//            redeemable = new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, assetFactory.getIsRedeemable());
//            expirationDate = new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, assetFactory.getExpirationDate());
//            ContractProperty redeemable1 = assetFactory.getContractProperties().set(0, redeemable);
//            ContractProperty expirationDate1 = assetFactory.getContractProperties().set(1, expirationDate);
//            redeemable1.setValue(assetFactory.getIsRedeemable());
//            expirationDate1.setValue(assetFactory.getExpirationDate());
//            try {
//
//                digitalAssetContract.setContractProperty(redeemable1);
//            }
//            catch (Exception e){
//                digitalAssetContract.setContractProperty(expirationDate1);
//            }
//            digitalAsset.setContract(digitalAssetContract);
//            digitalAsset.setName(assetFactory.getName());
//            digitalAsset.setDescription(assetFactory.getDescription());
//            digitalAsset.setPublicKey(assetFactory.getPublicKey());
//            digitalAsset.setGenesisAmount(assetFactory.getAmount());
//            digitalAsset.setState(assetFactory.getState());
//            digitalAsset.setIdentityAssetIssuer(assetFactory.getIdentyAssetIssuer());
//            digitalAsset.setResources(assetFactory.getResources());
            //Actualiza el State a Pending_Final del objeto assetFactory
            assetFactory.setState(State.DRAFT);
            assetFactory.setWalletPublicKey(walletPublicKey);
            saveAssetFactory(assetFactory);
            //Llama al metodo AssetIssuer de la transaction

            publishAsset(assetFactory, BlockchainNetworkType.DEFAULT);
            //assetIssuingManager.issueAssets(digitalAsset, 1, BlockchainNetworkType.DEFAULT);
            return assetFactory;
        }
        //catch(CantIssueDigitalAssetsException e){
        //    throw new CantIssueDigitalAssetsException(e, "Metodo testAssetFactory", "issueAssets");
        //}
        catch (Exception e){
            System.out.println("******* Metodo testAssetFactory, Error. Franklin ******" );
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AssetFactory getAssetFactoryByPublicKey(String assetPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryMiddlewareManager.getAssetFactory(assetPublicKey);
    }

    @Override
    public List<AssetFactory> getAssetFactoryByIssuer(String issuerIdentityPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        //TODO:Modifcar este metodo ya que tenemos que buscar en la tabla del Identity, leer todo los registros asociados a el buscarlo en la tabla asset factory y devolver un objeto lleno del asset factory con todas sus propiedades
        return assetFactoryMiddlewareManager.getAssetFactoryByIssuer(issuerIdentityPublicKey);
    }

    @Override
    public List<AssetFactory> getAssetFactoryByState(State state) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryMiddlewareManager.getAssetFactoryByState(state);
    }

    @Override
    public List<AssetFactory> getAssetFactoryAll() throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryMiddlewareManager.getAssetFactoryAll();
    }

    @Override
    public AssetFactory createEmptyAssetFactory() throws CantCreateEmptyAssetFactoryException, CantCreateAssetFactoryException {
        return assetFactoryMiddlewareManager.getNewAssetFactory();
    }

    @Override
    public void saveAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException, CantCreateFileException, CantPersistFileException {
        assetFactoryMiddlewareManager.saveAssetFactory(assetFactory);
    }

    @Override
    public void markAssetFactoryState(State state, String assetPublicKey) throws CantSaveAssetFactoryException, CantGetAssetFactoryException, CantCreateFileException, CantPersistFileException {
        assetFactoryMiddlewareManager.markAssetFactoryState(state, assetPublicKey);
    }

    @Override
    public void removeAssetFactory(String publicKey) throws CantDeleteAsserFactoryException {
        assetFactoryMiddlewareManager.removeAssetFactory(publicKey);
    }

    @Override
    public long getAvailableBalance(long amount) {
        //TODO:Implementar de la Crypto Vault
        return 0;
    }

    @Override
    public void publishAsset(final AssetFactory assetFactory, BlockchainNetworkType blockchainNetworkType) throws CantSaveAssetFactoryException{
        assetFactoryMiddlewareManager.publishAsset(assetFactory, blockchainNetworkType);
    }

    @Override
    public List<com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet> getInstallWallets() throws CantListWalletsException {
        return assetFactoryMiddlewareManager.getInstallWallets();
    }

    @Override
    public boolean isReadyToPublish(String assetPublicKey) throws CantPublishAssetFactoy {
        try
        {
            return assetFactoryMiddlewareManager.isReadyToPublish(assetPublicKey);
        }catch (Exception exception){
            throw new CantPublishAssetFactoy(exception, "Cant Publish Asset Factory", "Asset Factory incomplete");
        }
    }


}
