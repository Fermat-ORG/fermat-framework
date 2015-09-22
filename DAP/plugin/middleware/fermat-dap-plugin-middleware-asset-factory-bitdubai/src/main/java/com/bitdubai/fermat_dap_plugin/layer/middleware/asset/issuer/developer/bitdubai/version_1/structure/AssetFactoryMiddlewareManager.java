package com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums.AssetBehavior;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateEmptyAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.*;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.DealsWithAssetIssuing;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.exceptions.MissingAssetDataException;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.database.AssertFactoryMiddlewareDatabaseConstant;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.database.AssetFactoryMiddlewareDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;

import org.bouncycastle.asn1.dvcs.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 07/09/15.
 */
public class AssetFactoryMiddlewareManager implements DealsWithAssetIssuing, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem {
    /**
     * AssetFactoryMiddlewareManager member variables
     */
    UUID pluginId;

    /**
     * DealsWithErrors interface member variables
     */
    com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager errorManager;

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

    /**
     * Constructor
     *
     * @param errorManager
     * @param logManager
     * @param pluginDatabaseSystem
     * @param pluginFileSystem
     */
    public AssetFactoryMiddlewareManager(com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    private AssetFactoryMiddlewareDao getAssetFactoryMiddlewareDao()
    {
        AssetFactoryMiddlewareDao dao = new AssetFactoryMiddlewareDao(pluginDatabaseSystem, pluginId);
        return dao;
    }

    //De esa forma poder almacenarlo en la tabla de contract seteando la variable assetFactory.setContractProperties
    //Asi mismo cuando se vaya a enviar el DigitalAsset a la transaccion traer el objeto AssetFactory lleno, y las propiedades del contrato
    //asignarselas mas adelante al objeto DigitalAssetContract, que a su vez sera seteado a ala propiedad setContract del DigitalAsset
    private void saveAssetFactoryInDatabase(AssetFactory assetFactory) throws DatabaseOperationException, MissingAssetDataException{
        List<ContractProperty> contractProperties = new ArrayList<>();
        ContractProperty redeemable;
        ContractProperty expirationDate;
        redeemable = new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, assetFactory.getIsRedeemable());
        expirationDate = new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, assetFactory.getExpirationDate());
        contractProperties.add(redeemable);
        contractProperties.add(expirationDate);
        assetFactory.setContractProperties(contractProperties);
        getAssetFactoryMiddlewareDao().saveAssetFactoryData(assetFactory);
    }

    private List<AssetFactory> getAssetFactories(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException, CantLoadTableToMemoryException
    {
        List<AssetFactory> assetFactories = new ArrayList<>();

        for (AssetFactory assetFactory : getAssetFactoryMiddlewareDao().getAssetFactoryList(filter))
        {
            assetFactories.add(assetFactory);
        }

        return assetFactories;
    }

    @Override
    public void setAssetIssuingManager(AssetIssuingManager assetIssuingManager) throws CantSetObjectException {
        this.assetIssuingManager = assetIssuingManager;
    }

    @Override
    public void setErrorManager(com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager errorManager) {
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

    public void saveAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException
    {
        try {
            saveAssetFactoryInDatabase(assetFactory);
        }
        catch (DatabaseOperationException | MissingAssetDataException e)
        {
            throw new CantSaveAssetFactoryException(e, assetFactory.getName(), "Save Asset Factory");
        }
    }

    public AssetFactory getAssetFactory(final String publicKey) throws CantGetAssetFactoryException
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
            ContractProperty redeemable;
            ContractProperty expirationDate;
            redeemable = new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, assetFactory.getIsRedeemable());
            expirationDate = new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, assetFactory.getExpirationDate());
            ContractProperty redeemable1 = assetFactory.getContractProperties().set(0, redeemable);
            ContractProperty expirationDate1 = assetFactory.getContractProperties().set(1, expirationDate);
            assetFactory.setIsRedeemable(Boolean.valueOf(redeemable1.getValue().toString()));
            assetFactory.setExpirationDate(Timestamp.valueOf(expirationDate1.getValue().toString()));
            return assetFactory;
        }
        catch (DatabaseOperationException  | InvalidParameterException | CantLoadTableToMemoryException e)
        {
            throw new CantGetAssetFactoryException("Asset Factory", e, "Method: getAssetFactory()", "PublicKey " + publicKey);
        }
    }

    public List<AssetFactory> getAssetFactoryByIssuer(final String issuerIdentityPublicKey) throws CantGetAssetFactoryException
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

    public List<AssetFactory> getAssetFactoryByState(final State state) throws CantGetAssetFactoryException
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

    public List<AssetFactory> getAssetFactoryAll() throws CantGetAssetFactoryException
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

    public void publishAsset(final AssetFactory assetFactory) throws CantSaveAssetFactoryException
    {
        try {
            DigitalAsset digitalAsset = new DigitalAsset();
            DigitalAssetContract digitalAssetContract = new DigitalAssetContract();

            for(ContractProperty property : assetFactory.getContractProperties())
            {
                ContractProperty contractProperty = digitalAssetContract.getContractProperty(property.getName());
                digitalAssetContract.setContractProperty(contractProperty);
            }
            digitalAsset.setContract(digitalAssetContract);
            digitalAsset.setName(assetFactory.getName());
            digitalAsset.setDescription(assetFactory.getDescription());
            digitalAsset.setPublicKey(assetFactory.getPublicKey());
            digitalAsset.setGenesisAmount(assetFactory.getAmount());
            digitalAsset.setState(assetFactory.getState());
            digitalAsset.setIdentityAssetIssuer(assetFactory.getIdentyAssetIssuer());
            digitalAsset.setResources(assetFactory.getResources());
            //Actualiza el State a Pending_Final del objeto assetFactory
            assetFactory.setState(State.PENDING_FINAL);
            saveAssetFactory(assetFactory);
            //Llama al metodo AssetIssuer de la transaction
            assetIssuingManager.issueAssets(digitalAsset, assetFactory.getQuantity());
        }
        catch (CantSaveAssetFactoryException exception)
        {
            throw new CantSaveAssetFactoryException(exception, "Cant Save Asset Factory", "Method: publishAsset");
        }
        catch (Exception e){
            throw new CantSaveAssetFactoryException(e, "Exception General", "Method: publishAsset");
        }
    }

    public void markAssetFactoryState(State state, String assetPublicKey) throws CantSaveAssetFactoryException, CantGetAssetFactoryException{
        AssetFactory assetFactory = getAssetFactory(assetPublicKey);
        assetFactory.setState(state);
        saveAssetFactory(assetFactory);
    }

    public AssetFactory getNewAssetFactory() throws  CantCreateAssetFactoryException, CantCreateEmptyAssetFactoryException
    {
        AssetFactory assetFactory = new AssetFactory() {
            String publicKey;
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