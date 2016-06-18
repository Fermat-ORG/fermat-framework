package org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantGetAssetIssuerIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateEmptyAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantDeleteAsserFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.DatabaseOperationException;
import org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.MissingAssetDataException;
import org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database.AssetFactoryMiddlewareDao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>AssetFactoryMiddlewareManager</code>
 * contains all the business logic of todo what?
 * <p/>
 * Created by franklin on 07/09/15.
 */
public final class AssetFactoryMiddlewareManager {

    private final AssetIssuingManager assetIssuingManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final PluginFileSystem pluginFileSystem;
    private final UUID pluginId;
    private final WalletManagerManager walletManagerManager;
    private final IdentityAssetIssuerManager identityAssetIssuerManager;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem database system reference.
     * @param pluginFileSystem     file system reference,
     * @param pluginId             of this module.
     * @param assetIssuingManager  transaction manager instance.
     * @param walletManagerManager wallet manager instance.
     */
    public AssetFactoryMiddlewareManager(final AssetIssuingManager assetIssuingManager,
                                         final PluginDatabaseSystem pluginDatabaseSystem,
                                         final PluginFileSystem pluginFileSystem,
                                         final UUID pluginId,
                                         final WalletManagerManager walletManagerManager,
                                         final IdentityAssetIssuerManager identityAssetIssuerManager) {

        this.assetIssuingManager = assetIssuingManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.walletManagerManager = walletManagerManager;
        this.identityAssetIssuerManager = identityAssetIssuerManager;
    }

    private AssetFactoryMiddlewareDao getAssetFactoryMiddlewareDao() {

        return new AssetFactoryMiddlewareDao(pluginDatabaseSystem, pluginFileSystem, pluginId);
    }

    private boolean areObjectsSettled(AssetFactory assetFactory) {
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

    private void saveAssetFactoryInDatabase(AssetFactory assetFactory) throws DatabaseOperationException, MissingAssetDataException, CantCreateFileException, CantPersistFileException {
        try {
//            List<ContractProperty> contractProperties = new ArrayList<>();
//            ContractProperty redeemable;
//            ContractProperty expirationDate;
//            redeemable = new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, assetFactory.getIsRedeemable());
//            expirationDate = new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, assetFactory.getExpirationDate());
//            contractProperties.add(redeemable);
//            contractProperties.add(expirationDate);
//            assetFactory.setContractProperties(contractProperties);
//            assetFactory.getContractProperties().add(expirationDate);
            try {
                assetFactory.setIdentityAssetIssuer(identityAssetIssuerManager.getIdentityAssetIssuer());
            } catch (CantGetAssetIssuerIdentitiesException cantCreateFileException) {
                throw new CantCreateFileException(CantCreateFileException.DEFAULT_MESSAGE, cantCreateFileException, "Asset Factory Method: saveAssetFactoryInDatabase", "Failed Identity Asset Issuer");
            }

            getAssetFactoryMiddlewareDao().saveAssetFactoryData(assetFactory);
            getAssetFactoryMiddlewareDao().persistNewImageFactory(assetFactory);

//            if (assetFactory.getResources() != null) {
//                for (Resource resource : assetFactory.getResources()) {
//                    PluginBinaryFile imageFile = pluginFileSystem.createBinaryFile(pluginId, PATH_DIRECTORY, resource.getId().toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
//                    imageFile.setContent(resource.getResourceBinayData());
//                    imageFile.persistToMedia();
//                }
//            }
        } catch (CantCreateFileException cantCreateFileException) {
            throw new CantCreateFileException(CantCreateFileException.DEFAULT_MESSAGE, cantCreateFileException, "Asset Factory Method: saveAssetFactoryInDatabase", "cant create el file");
        } catch (CantPersistFileException cantPersistFileException) {
            throw new CantPersistFileException(CantPersistFileException.DEFAULT_MESSAGE, cantPersistFileException, "Asset Factory Method: saveAssetFactoryInDatabase", "cant persist el file");
        }
    }

    private void saveMarkFactoryInDatabase(AssetFactory assetFactory) throws CantSaveAssetFactoryException, DatabaseOperationException, MissingAssetDataException {
        try {
            getAssetFactoryMiddlewareDao().markAssetFactoryData(assetFactory);
        } catch (DatabaseOperationException | MissingAssetDataException e) {
            throw new CantSaveAssetFactoryException(e, assetFactory.getName(), "Mark Save Asset Factory");
        }
    }

    private List<AssetFactory> getAssetFactories(DatabaseTableFilter... filters) throws DatabaseOperationException, InvalidParameterException, CantLoadTableToMemoryException, CantCreateFileException {
        List<AssetFactory> assetFactories = new ArrayList<>();

        for (AssetFactory assetFactory : getAssetFactoryMiddlewareDao().getAssetFactoryList(filters)) {
            assetFactories.add(assetFactory);
        }

        return assetFactories;
    }


    private void markAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException, CantCreateFileException, CantPersistFileException {
        try {
            saveMarkFactoryInDatabase(assetFactory);
        } catch (org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.DatabaseOperationException | org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.MissingAssetDataException e) {
            throw new CantSaveAssetFactoryException(e, assetFactory.getName(), "Save Asset Factory");
        }
    }

    public List<com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet> getInstallWallets() throws CantListWalletsException {
        try {
            return walletManagerManager.getInstalledWallets();
        } catch (CantListWalletsException exception) {
            throw new CantListWalletsException("Load Wallet installed", exception, null, null);
        }

    }

    public void saveAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException, CantCreateFileException, CantPersistFileException {
        try {
            saveAssetFactoryInDatabase(assetFactory);
        } catch (org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.DatabaseOperationException | org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.MissingAssetDataException e) {
            throw new CantSaveAssetFactoryException(e, assetFactory.getName(), "Save Asset Factory");
        }
    }

    public AssetFactory getAssetFactoryByAssetPublicKey(final String publicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        // I define the filter to search for the public Key
        DatabaseTableFilter publicKeyFilter = new DatabaseTableFilter() {
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
                return org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database.AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_PUBLIC_KEY_COLUMN;
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
            assetFactories = getAssetFactories(publicKeyFilter);
            return assetFactories.get(0);
        } catch (org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.DatabaseOperationException | InvalidParameterException | CantLoadTableToMemoryException e) {
            throw new CantGetAssetFactoryException("Asset Factory", e, "Method: getAssetFactoryByAssetPublicKey()", "PublicKey " + publicKey);
        }
    }

    public DatabaseTableFilter getNetworkTypeFilter(final BlockchainNetworkType networkType) {
        return new DatabaseTableFilter() {
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
                return org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database.AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_NETWORK_TYPE;
            }

            @Override
            public String getValue() {
                return networkType.getCode();
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };
    }


    public AssetFactory getAssetFactoryPendingFinalByAssetPublicKey(final String publicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        // I define the filter to search for the public Key
        DatabaseTableFilter assetPublicKey = new DatabaseTableFilter() {
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
                return org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database.AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_PUBLIC_KEY_COLUMN;
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

        DatabaseTableFilter stateFilter = new DatabaseTableFilter() {
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
                return org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database.AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_STATE_COLUMN;
            }

            @Override
            public String getValue() {
                return State.PENDING_FINAL.getCode();
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };

        List<AssetFactory> assetFactories;
        try {
            assetFactories = getAssetFactories(assetPublicKey, stateFilter);
            return assetFactories.get(0);
        } catch (org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.DatabaseOperationException | InvalidParameterException | CantLoadTableToMemoryException e) {
            throw new CantGetAssetFactoryException("Asset Factory", e, "Method: getAssetFactoryByAssetPublicKey()", "PublicKey " + publicKey);
        }
    }

    public AssetFactory getAssetFactoryById(final String factoryId) throws CantGetAssetFactoryException, CantCreateFileException {
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
                return org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database.AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ID_COLUMN;
            }

            @Override
            public String getValue() {
                return factoryId;
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };

        List<AssetFactory> assetFactories;
        try {
            assetFactories = getAssetFactories(filter);
            return assetFactories.get(0);
        } catch (org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.DatabaseOperationException | InvalidParameterException | CantLoadTableToMemoryException e) {
            throw new CantGetAssetFactoryException("Asset Factory", e, "Method: getAssetFactoryByAssetPublicKey()", "PublicKey " + factoryId);
        }
    }

    public List<AssetFactory> getAssetFactoryByIssuer(final String issuerIdentityPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
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
                return org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database.AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ISSUER_IDENTITY_PUBLIC_KEY_COLUMN;
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
        } catch (org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.DatabaseOperationException | InvalidParameterException | CantLoadTableToMemoryException e) {
            throw new CantGetAssetFactoryException("Asset Factory", e, "Method: getAssetFactoryByIssuer()", "IssuerIdentityPublicKey " + issuerIdentityPublicKey);
        }
    }

    public List<AssetFactory> getFactoryByStateAndName(final AssetFactory assetFactory, final State state) throws CantGetAssetFactoryException, CantCreateFileException {
        // I define the filter to search for the state
        DatabaseTableFilter stateFilter = new DatabaseTableFilter() {
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
                return org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database.AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_STATE_COLUMN;
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

        DatabaseTableFilter assetPublicKeyFilter = new DatabaseTableFilter() {
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
                return org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database.AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_NAME_COLUMN;
            }

            @Override
            public String getValue() {
                return assetFactory.getName();
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };
        List<AssetFactory> assetFactories;
        try {
            assetFactories = getAssetFactories(stateFilter, assetPublicKeyFilter);
            return assetFactories;
        } catch (org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.DatabaseOperationException | InvalidParameterException | CantLoadTableToMemoryException e) {
            throw new CantGetAssetFactoryException("Asset Factory", e, "Method: getAssetFactoryByState()", "State " + state.getCode());
        }
    }

    public List<AssetFactory> getFactoryByStateAndAssetPublicKey(final AssetFactory assetFactory, final State state) throws CantGetAssetFactoryException, CantCreateFileException {
        // I define the filter to search for the state
        DatabaseTableFilter stateFilter = new DatabaseTableFilter() {
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
                return org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database.AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_STATE_COLUMN;
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

        DatabaseTableFilter assetPublicKeyFilter = new DatabaseTableFilter() {
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
                return org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database.AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_PUBLIC_KEY_COLUMN;
            }

            @Override
            public String getValue() {
                return assetFactory.getAssetPublicKey();
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };
        List<AssetFactory> assetFactories;
        try {
            assetFactories = getAssetFactories(stateFilter, assetPublicKeyFilter);
            return assetFactories;
        } catch (org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.DatabaseOperationException | InvalidParameterException | CantLoadTableToMemoryException e) {
            throw new CantGetAssetFactoryException("Asset Factory", e, "Method: getAssetFactoryByState()", "State " + state.getCode());
        }
    }

    public List<AssetFactory> getAssetFactoryByState(final State state, BlockchainNetworkType networkType) throws CantGetAssetFactoryException, CantCreateFileException {
        // I define the filter to search for the state
        DatabaseTableFilter stateFilter = new DatabaseTableFilter() {
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
                return org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database.AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_STATE_COLUMN;
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
            assetFactories = getAssetFactories(stateFilter, getNetworkTypeFilter(networkType));
            return assetFactories;
        } catch (org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.DatabaseOperationException | InvalidParameterException | CantLoadTableToMemoryException e) {
            throw new CantGetAssetFactoryException("Asset Factory", e, "Method: getAssetFactoryByState()", "State " + state.getCode());
        }
    }

    public List<AssetFactory> getAssetFactoryAll(BlockchainNetworkType networkType) throws CantGetAssetFactoryException, CantCreateFileException {
        // I define the filter to null for all
        DatabaseTableFilter filter = getNetworkTypeFilter(networkType);

        List<AssetFactory> assetFactories;
        try {
            assetFactories = getAssetFactories(filter);
            return assetFactories;
        } catch (org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.DatabaseOperationException | InvalidParameterException | CantLoadTableToMemoryException e) {
            throw new CantGetAssetFactoryException("Asset Factory", e, "Method: getAssetFactoryAll", "");
        }
    }

    public byte[] getAssetFactoryResource(Resource resource) throws FileNotFoundException, CantCreateFileException, CantGetResourcesException {
        return getAssetFactoryMiddlewareDao().getImageFactory(resource);
    }

    public boolean isReadyToPublish(String asssetPublicKey) throws org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.CantPublishAssetException {
        try {
            AssetFactory assetFactory = getAssetFactoryByAssetPublicKey(asssetPublicKey);
            return areObjectsSettled(assetFactory);
        } catch (Exception exception) {
            throw new org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.CantPublishAssetException("Cant Publish Asset Factory", exception, null, "Asset Factory incomplete");
        }
    }

    public void removeAssetFactory(String publicKey) throws CantDeleteAsserFactoryException {
        try {
            AssetFactory assetFactory = getAssetFactoryByAssetPublicKey(publicKey);
            if (assetFactory.getState() != State.DRAFT)
                throw new CantDeleteAsserFactoryException(null, "Error delete Asset Factory", "Asset Factory is not DRAFT");
            else
                getAssetFactoryMiddlewareDao().removeAssetFactory(assetFactory, true);
        } catch (Exception exception) {
            throw new CantDeleteAsserFactoryException(exception, "Error delete Asset Factory", "Asset Factory - Delete");
        }
    }

    public void removePendingFinalFactory(String publicKey) throws CantDeleteAsserFactoryException {
        try {
            AssetFactory assetFactory = getAssetFactoryPendingFinalByAssetPublicKey(publicKey);
            if (assetFactory.getState() == State.DRAFT)
                throw new CantDeleteAsserFactoryException(null, "Error delete Asset Factory", "Asset Factory in DRAFT");
            else
                getAssetFactoryMiddlewareDao().removeAssetFactory(assetFactory, false);
        } catch (Exception exception) {
            throw new CantDeleteAsserFactoryException(exception, "Error delete Asset Factory", "Asset Factory - Delete");
        }
    }

    public void publishAsset(final AssetFactory assetFactory) throws CantSaveAssetFactoryException {
        try {
            if (assetFactory.getState() == State.DRAFT) {
                DigitalAsset digitalAsset = new DigitalAsset();
                DigitalAssetContract digitalAssetContract = new DigitalAssetContract();
                digitalAssetContract.addPropertyValue(DigitalAssetContractPropertiesConstants.REDEEMABLE, assetFactory.getIsRedeemable());
                digitalAssetContract.addPropertyValue(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, assetFactory.getExpirationDate());
                digitalAssetContract.addPropertyValue(DigitalAssetContractPropertiesConstants.SALEABLE, assetFactory.getIsExchangeable());
                digitalAssetContract.addPropertyValue(DigitalAssetContractPropertiesConstants.TRANSFERABLE, assetFactory.getIsTransferable());
                digitalAsset.setContract(digitalAssetContract);
                digitalAsset.setName(assetFactory.getName());
                digitalAsset.setDescription(assetFactory.getDescription());
                digitalAsset.setPublicKey(assetFactory.getAssetPublicKey());
                digitalAsset.setGenesisAmount(assetFactory.getAmount());
                digitalAsset.setState(assetFactory.getState());
                digitalAsset.setIdentityAssetIssuer(identityAssetIssuerManager.getIdentityAssetIssuer());
                digitalAsset.setResources(assetFactory.getResources());
                markAssetFactoryState(State.PENDING_FINAL, assetFactory.getAssetPublicKey());
                assetIssuingManager.issueAssets(digitalAsset, assetFactory.getQuantity(), /* Issuer Wallet PK */WalletUtilities.WALLET_PUBLIC_KEY, /* BTC Wallet PK*/assetFactory.getWalletPublicKey(), assetFactory.getNetworkType());
            } else {
                throw new org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.CantPublishAssetException(org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions.CantPublishAssetException.DEFAULT_MESSAGE);
            }

        } catch (Exception e) {
            try {
                markAssetFactoryState(State.DRAFT, assetFactory.getAssetPublicKey());
            } catch (Exception e1) {
                //Well, we're fucked.
            }
            throw new CantSaveAssetFactoryException(e, "Exception CantIssueDigitalAssetsException", "Method: issueAssets");
        }
    }

    public void updateAssetFactoryQuantity(int newQuantity, String factoryId) throws CantSaveAssetFactoryException, CantGetAssetFactoryException, CantCreateFileException, CantPersistFileException {
        AssetFactory assetFactory = getAssetFactoryById(factoryId);
        assetFactory.setQuantity(newQuantity);
        markAssetFactory(assetFactory);
    }

    public void markAssetFactoryState(State state, String assetPublicKey) throws CantSaveAssetFactoryException, CantGetAssetFactoryException, CantCreateFileException, CantPersistFileException {
        AssetFactory assetFactory = getAssetFactoryByAssetPublicKey(assetPublicKey);
        assetFactory.setState(state);
        markAssetFactory(assetFactory);
    }

    public AssetFactory getNewAssetFactory() throws CantCreateAssetFactoryException, CantCreateEmptyAssetFactoryException {
        return new AssetFactoryRecord();
    }
}