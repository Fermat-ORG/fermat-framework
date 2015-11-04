package com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantDeleteAsserFactoryException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;
//import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.WalletsListFailedToLoadException;
//import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;
//import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.WalletManagerModule;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums.AssetBehavior;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateEmptyAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.exceptions.CantPublishAssetException;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.exceptions.MissingAssetDataException;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.database.AssertFactoryMiddlewareDatabaseConstant;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.database.AssetFactoryMiddlewareDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 07/09/15.
 */
public class AssetFactoryMiddlewareManager implements  DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem {
    public static final String PATH_DIRECTORY = "assetFactory/resources";
    /**
     * AssetFactoryMiddlewareManager member variables
     */
    UUID pluginId;

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface mmeber variables
     */
    LogManager logManager;

    /**
     * DealsWithPluginDatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem interface member variables
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithAssetIssuing interface member variables
     */
    AssetIssuingManager assetIssuingManager;

    WalletManagerManager walletManagerManager;

    /**
     * Constructor
     *
     * @param errorManager
     * @param logManager
     * @param pluginDatabaseSystem
     * @param pluginFileSystem
     */
    public AssetFactoryMiddlewareManager(ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId, AssetIssuingManager assetIssuingManager, WalletManagerManager walletManagerManager) {
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.assetIssuingManager = assetIssuingManager;
        this.walletManagerManager = walletManagerManager;
    }

    private AssetFactoryMiddlewareDao getAssetFactoryMiddlewareDao()
    {
        AssetFactoryMiddlewareDao dao = new AssetFactoryMiddlewareDao(pluginDatabaseSystem, pluginId);
        return dao;
    }

    private boolean areObjectsSettled(AssetFactory assetFactory)
    {
        boolean isBoolean = true;
        //TODO: Descomentar luego solo es para la prueba y testeo
        //if (assetFactory.getResources() == null) isBoolean = false;
//        if (assetFactory.getState() == null) isBoolean = false;
//        if (assetFactory.getName() == null) isBoolean = false;
//        if (assetFactory.getDescription() == null) isBoolean = false;
//        if (assetFactory.getQuantity() == 0) isBoolean = false;
//        if (assetFactory.getAmount() == 0) isBoolean = false;
//        if (assetFactory.getAssetBehavior() == null) isBoolean = false;
        return isBoolean;
    }

    private void saveAssetFactoryInDatabase(AssetFactory assetFactory) throws DatabaseOperationException, MissingAssetDataException, CantCreateFileException, CantPersistFileException{
        try {
            List<ContractProperty> contractProperties = new ArrayList<>();
            ContractProperty redeemable;
            ContractProperty expirationDate;
            redeemable = new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, assetFactory.getIsRedeemable());
            expirationDate = new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, assetFactory.getExpirationDate());
            contractProperties.add(redeemable);
            contractProperties.add(expirationDate);
            assetFactory.setContractProperties(contractProperties);
            //TODO: Borrar luego cuado funcione el Identity debe venir desde el dispositivo
            AssetIssuerIdentity assetIssuerIdentity = new AssetIssuerIdentity();
            assetIssuerIdentity.setAlias("Franklin Marcano");
            assetIssuerIdentity.setPublicKey("ASDS-10087982");
            assetFactory.setIdentityAssetIssuer(assetIssuerIdentity);
            getAssetFactoryMiddlewareDao().saveAssetFactoryData(assetFactory);
            if (assetFactory.getResources() != null){
                for (Resource resource : assetFactory.getResources()) {
                    PluginBinaryFile imageFile = pluginFileSystem.createBinaryFile(pluginId, PATH_DIRECTORY, resource.getId().toString(), FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
                    imageFile.setContent(resource.getResourceBinayData());
                    imageFile.persistToMedia();
                }
            }
        }catch (CantCreateFileException cantCreateFileException)
        {
            throw new CantCreateFileException(CantCreateFileException.DEFAULT_MESSAGE, cantCreateFileException, "Asset Factory Method: saveAssetFactoryInDatabase", "cant create el file");
        }
        catch (CantPersistFileException cantPersistFileException)
        {
            throw new CantPersistFileException(CantPersistFileException.DEFAULT_MESSAGE, cantPersistFileException, "Asset Factory Method: saveAssetFactoryInDatabase", "cant persist el file");
        }

    }

    private void saveMarkFactoryInDatabase(AssetFactory assetFactory) throws CantSaveAssetFactoryException, DatabaseOperationException, MissingAssetDataException{
        try {
            getAssetFactoryMiddlewareDao().markAssetFactoryData(assetFactory);
        }
        catch (DatabaseOperationException | MissingAssetDataException e)
        {
            throw new CantSaveAssetFactoryException(e, assetFactory.getName(), "Mark Save Asset Factory");
        }
    }

    private List<AssetFactory> getAssetFactories(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException, CantLoadTableToMemoryException, CantCreateFileException
    {
        List<AssetFactory> assetFactories = new ArrayList<>();

        for (AssetFactory assetFactory : getAssetFactoryMiddlewareDao().getAssetFactoryList(filter))
        {
            assetFactories.add(assetFactory);
        }

        return assetFactories;
    }


    private void markAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException, CantCreateFileException, CantPersistFileException
    {
        try {
            saveMarkFactoryInDatabase(assetFactory);
        }
        catch (DatabaseOperationException | MissingAssetDataException e)
        {
            throw new CantSaveAssetFactoryException(e, assetFactory.getName(), "Save Asset Factory");
        }
    }


    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    public List<com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet> getInstallWallets()  throws CantListWalletsException {
        try
        {
            return walletManagerManager.getInstalledWallets();
        }catch (CantListWalletsException exception){
            throw new CantListWalletsException("Load Wallet installed", exception, null, null);
        }

    }

    public void saveAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException, CantCreateFileException, CantPersistFileException
    {
        try {
            saveAssetFactoryInDatabase(assetFactory);
        }
        catch (DatabaseOperationException | MissingAssetDataException e)
        {
            throw new CantSaveAssetFactoryException(e, assetFactory.getName(), "Save Asset Factory");
        }
    }

    public AssetFactory getAssetFactory(final String publicKey) throws CantGetAssetFactoryException, CantCreateFileException
    {
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
                return AssertFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_PUBLIC_KEY_COLUMN;
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

        List<AssetFactory> assetFactories;
        try {
            assetFactories = getAssetFactories(filter);
            AssetFactory assetFactory = assetFactories.get(0);
//            ContractProperty redeemable;
//            ContractProperty expirationDate;
//            redeemable = new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, assetFactory.getIsRedeemable());
//            ContractProperty redeemable1 = assetFactory.getContractProperties().set(0, redeemable);
//            expirationDate = new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, assetFactory.getExpirationDate());
//            ContractProperty expirationDate1 = assetFactory.getContractProperties().set(1, expirationDate);
//            if (redeemable1.getValue() != null)
//                assetFactory.setIsRedeemable(Boolean.valueOf(redeemable1.getValue().toString()));
//            else  assetFactory.setIsRedeemable(assetFactory.getIsRedeemable());
//            if (expirationDate1.getValue() != null)
//                assetFactory.setExpirationDate(Timestamp.valueOf(expirationDate1.getValue().toString()));
//            else assetFactory.setExpirationDate(assetFactory.getExpirationDate());

            return assetFactory;
        }
        catch (DatabaseOperationException  | InvalidParameterException | CantLoadTableToMemoryException e)
        {
            throw new CantGetAssetFactoryException("Asset Factory", e, "Method: getAssetFactory()", "PublicKey " + publicKey);
        }
    }

    public List<AssetFactory> getAssetFactoryByIssuer(final String issuerIdentityPublicKey) throws CantGetAssetFactoryException, CantCreateFileException
    {
        // I define the filter to search for the issuer identity public Key
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
                return AssertFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ISSUER_IDENTITY_PUBLIC_KEY_COLUMN;
            }

            @Override
            public String getValue() {
                return issuerIdentityPublicKey;
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };

        List<AssetFactory> assetFactories;
        try {
            assetFactories = getAssetFactories(filter);
            return assetFactories;
        }
        catch (DatabaseOperationException  | InvalidParameterException | CantLoadTableToMemoryException e)
        {
            throw new CantGetAssetFactoryException("Asset Factory", e, "Method: getAssetFactoryByIssuer()", "IssuerIdentityPublicKey " + issuerIdentityPublicKey);
        }
    }

    public List<AssetFactory> getAssetFactoryByState(final State state) throws CantGetAssetFactoryException, CantCreateFileException
    {
        // I define the filter to search for the state
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
                return AssertFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_STATE_COLUMN;
            }

            @Override
            public String getValue() {
                return state.getCode();
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };

        List<AssetFactory> assetFactories;
        try {
            assetFactories = getAssetFactories(filter);
            return assetFactories;
        }
        catch (DatabaseOperationException  | InvalidParameterException | CantLoadTableToMemoryException e)
        {
            throw new CantGetAssetFactoryException("Asset Factory", e, "Method: getAssetFactoryByState()", "State " + state.getCode());
        }
    }

    public boolean checkAssetDraft() throws CantLoadTableToMemoryException{
        //TODO: Descomentar el metodo getAssetFactoryMiddlewareDao, cuando sepamos que funciona el agente
        boolean isAssetDraft  = getAssetFactoryMiddlewareDao().checkAssetDraft();
        isAssetDraft = true;
        return isAssetDraft;
    }

    public List<AssetFactory> getAssetFactoryAll() throws CantGetAssetFactoryException, CantCreateFileException
    {
        // I define the filter to null for all
        DatabaseTableFilter filter = null;

        List<AssetFactory> assetFactories;
        try {
            assetFactories = getAssetFactories(filter);
            return assetFactories;
        }
        catch (DatabaseOperationException  | InvalidParameterException | CantLoadTableToMemoryException e)
        {
            throw new CantGetAssetFactoryException("Asset Factory", e, "Method: getAssetFactoryAll", "");
        }
    }

    public boolean isReadyToPublish(String asssetPublicKey) throws CantPublishAssetException
    {
        try {
            AssetFactory assetFactory = getAssetFactory(asssetPublicKey);
            return areObjectsSettled(assetFactory);
        }
        catch (Exception exception){
            throw new CantPublishAssetException("Cant Publish Asset Factory", exception, null, "Asset Factory incomplete");
        }
    }

    public void removeAssetFactory(String publicKey) throws CantDeleteAsserFactoryException
    {
        try {
            AssetFactory assetFactory = getAssetFactory(publicKey);
            if (assetFactory.getState().getCode() != State.DRAFT.getCode())
                throw new CantDeleteAsserFactoryException(null, "Error delete Asset Factory", "Asset Factory in DRAFT");
            else
                getAssetFactoryMiddlewareDao().removeAssetFactory(assetFactory);
        }catch (Exception exception){
            throw new CantDeleteAsserFactoryException(exception, "Error delete Asset Factory", "Asset Factory - Delete");
        }
    }

    public void publishAsset(final AssetFactory assetFactory, BlockchainNetworkType blockchainNetworkType) throws CantSaveAssetFactoryException
    {
        try {
            if(assetFactory.getState() == State.DRAFT) {
                DigitalAsset digitalAsset = new DigitalAsset();
                DigitalAssetContract digitalAssetContract = new DigitalAssetContract();
                ContractProperty redeemable = new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, null);
                redeemable.setValue(assetFactory.getIsRedeemable());
                ContractProperty expirationDate = new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, null);
                expirationDate.setValue(assetFactory.getExpirationDate());
                digitalAssetContract.setContractProperty(redeemable);
                digitalAssetContract.setContractProperty(expirationDate);
                digitalAsset.setContract(digitalAssetContract);
                digitalAsset.setName(assetFactory.getName());
                digitalAsset.setDescription(assetFactory.getDescription());
                digitalAsset.setPublicKey(assetFactory.getPublicKey());
                digitalAsset.setGenesisAmount(assetFactory.getAmount());
                digitalAsset.setState(assetFactory.getState());
                AssetIssuerIdentity aseetIssuerIdentity = new AssetIssuerIdentity();
                aseetIssuerIdentity.setAlias(assetFactory.getIdentyAssetIssuer().getAlias());
                aseetIssuerIdentity.setPublicKey(assetFactory.getIdentyAssetIssuer().getPublicKey());
                aseetIssuerIdentity = (AssetIssuerIdentity)assetFactory.getIdentyAssetIssuer();
                digitalAsset.setIdentityAssetIssuer(aseetIssuerIdentity);
                digitalAsset.setResources(assetFactory.getResources());
                markAssetFactoryState(State.PENDING_FINAL, assetFactory.getPublicKey());
                //Method the DealsWithAssetIssuing
                assetIssuingManager.issueAssets(digitalAsset, assetFactory.getQuantity(), assetFactory.getWalletPublicKey(), blockchainNetworkType);
                //assetIssuingManager.issueAssets(digitalAsset, assetFactory.getQuantity(), "wallet_public_key", blockchainNetworkType);

            }
            else
            {
                throw new CantPublishAssetException(CantPublishAssetException.DEFAULT_MESSAGE);
            }

        }catch (CantIssueDigitalAssetsException e){
            throw new CantSaveAssetFactoryException(e, "Exception CantIssueDigitalAssetsException", "Method: issueAssets");
        }
        catch (Exception e){
            throw new CantSaveAssetFactoryException(e, "Exception General", "Method: publishAsset");
        }
    }

    public void markAssetFactoryState(State state, String assetPublicKey) throws CantSaveAssetFactoryException, CantGetAssetFactoryException, CantCreateFileException, CantPersistFileException{
        AssetFactory assetFactory = getAssetFactory(assetPublicKey);
        assetFactory.setState(state);
        markAssetFactory(assetFactory);
        //saveAssetFactory(assetFactory);
    }

    public AssetFactory getNewAssetFactory() throws  CantCreateAssetFactoryException, CantCreateEmptyAssetFactoryException
    {
            AssetFactory assetFactory = new AssetFactory() {
                String walletPublicKey;
                String publicKey = new ECCKeyPair().getPublicKey();
                String name;
                String description;
                List<Resource> resources;
                DigitalAssetContract digitalAssetContract;
                State state;
                List<ContractProperty> contractProperties;
                int quantity;
                long amount;
                long fee;
                Timestamp creationTimestamp;
                Timestamp lastModificationTimestamp;
                boolean isRedeemable;
                Timestamp expirationDate;
                AssetBehavior assetBehavior;
                IdentityAssetIssuer identityAssetIssuer;

                @Override
                public IdentityAssetIssuer getIdentyAssetIssuer() {
                    return identityAssetIssuer;
                }

                @Override
                public void setIdentityAssetIssuer(IdentityAssetIssuer identityAssetIssuer) {
                    this.identityAssetIssuer = identityAssetIssuer;
                }

                @Override
                public String getWalletPublicKey() {
                    return walletPublicKey;
                }

                @Override
                public void setWalletPublicKey(String walletPublicKey) {
                    this.walletPublicKey = walletPublicKey;
                }

                @Override
                public String getPublicKey() {
                    return publicKey;
                }

                @Override
                public void setPublicKey(String publicKey) {
                    this.publicKey = publicKey;
                }

                @Override
                public String getName() {
                    return name;
                }

                @Override
                public void setName(String name) {
                    this.name = name;
                }

                @Override
                public String getDescription() {
                    return description;
                }

                @Override
                public void setDescription(String description) {
                    this.description = description;
                }

                @Override
                public List<Resource> getResources() {
                    return resources;
                }

                @Override
                public void setResources(List<Resource> resources) {
                    this.resources = resources;
                }

                @Override
                public DigitalAssetContract getContract() {
                    return digitalAssetContract;
                }

                @Override
                public void setContract(DigitalAssetContract contract) {
                    this.digitalAssetContract = contract;
                }

                @Override
                public List<ContractProperty> getContractProperties() {
                    return contractProperties;
                }

                @Override
                public void setContractProperties(List<ContractProperty> contractProperties) {
                    this.contractProperties = contractProperties;
                }

                @Override
                public State getState() {
                    return state;
                }

                @Override
                public void setState(State state) {
                    this.state = state;
                }

                @Override
                public int getQuantity() {
                    return quantity;
                }

                @Override
                public void setQuantity(int quantity) {
                    this.quantity = quantity;
                }

                @Override
                public long getAmount() {
                    return amount;
                }

                @Override
                public void setAmount(long amount) {
                    this.amount = amount;
                }

                @Override
                public long getFee() {
                    return fee;
                }

                @Override
                public void setFee(long fee) {
                    this.fee = fee;
                }

                @Override
                public boolean getIsRedeemable() {
                    return isRedeemable;
                }

                @Override
                public void setIsRedeemable(boolean isRedeemable) {
                    this.isRedeemable = isRedeemable;
                }

                @Override
                public Timestamp getExpirationDate() {
                    return expirationDate;
                }

                @Override
                public void setExpirationDate(Timestamp expirationDate) {
                    this.expirationDate = expirationDate;
                }

                @Override
                public AssetBehavior getAssetBehavior() {
                    return assetBehavior;
                }

                @Override
                public void setAssetBehavior(AssetBehavior assetBehavior) {
                    this.assetBehavior = assetBehavior;
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
                    this.lastModificationTimestamp = timestamp;
                }
            };

            return assetFactory;
    }


}