package com.bitdubai.fermat_dap_plugin.layer.module.asset_issuer.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.enums.State;
import com.bitdubai.fermat_dap_api.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_api.asset_issuing.interfaces.DealsWithAssetIssuing;
import com.bitdubai.fermat_dap_api.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.module.asset_issuer.exceptions.CantCreateAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.module.asset_issuer.exceptions.CantSaveAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.module.asset_issuer.interfaces.AssetIssuer;
import com.bitdubai.fermat_dap_api.layer.module.asset_issuer.interfaces.AssetIssuerManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.ErrorManager;

/**
 * Created by rodrigo on 9/7/15.
 */
public class AssetIssuerModulePluginRoot implements DealsWithErrors, DealsWithLogger, DealsWithEvents, AssetIssuerManager, Plugin, DatabaseManagerForDevelopers, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, Service {
    /**
     * DealsWithAssetIssuing Interface member variables.
     */
    private DealsWithAssetIssuing dealsWithAssetIssuing;

    /**
     * DealsWithErrors interface member variables
     */
    com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager errorManager;

    /**
     * DealsWithDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();

    private com.bitdubai.fermat_dap_plugin.layer.module.asset_issuer.version_1.structure.AssetIssuerManager getAssetIssuerManager(){
        com.bitdubai.fermat_dap_plugin.layer.module.asset_issuer.version_1.structure.AssetIssuerManager assetIssuerManager = new com.bitdubai.fermat_dap_plugin.layer.module.asset_issuer.version_1.structure.AssetIssuerManager(errorManager, logManager, pluginDatabaseSystem, pluginFileSystem, pluginId);
        return assetIssuerManager;
    }

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return null;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setErrorManager(com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        //TODO: Implement
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
        //TODO: Implement
    }

    @Override
    public void resume() {
        //TODO: Implement
    }

    @Override
    public void stop() {
        //TODO: Implement
    }

    @Override
    public ServiceStatus getStatus() {
        return null;
    }

    @Override
    public List<AssetIssuer> getAllAssetIssuer() {
        return null;
    }

    @Override
    public List<AssetIssuer> getAssetIssuerByIssuer(String issuerPublicKey) {
        return null;
    }

    @Override
    public List<AssetIssuer> getAssetIssuerByState(State state) {
        return null;
    }

    @Override
    public AssetIssuer createEmptyAssetIssuer() {
        return null;
    }

    @Override
    public void createAssetIssuer(AssetIssuer assetIssuer)  throws CantCreateAssetIssuerException {

    }

    @Override
    public void saveAssetIssuer(AssetIssuer assetIssuer) throws CantSaveAssetIssuerException {

    }

    @Override
    public void removeAssetIssuer(AssetIssuer assetIssuer) {

    }

    //Implementara el metodo de la capa transaccional a traves del DealsWithAssetIssuing
    @Override
    public boolean verifiedGenesisAmount(AssetIssuer assetIssuer) {
        return getAssetIssuerManager().verifiedGenesisAmount(assetIssuer.getDigitalAsset());
    }

    //Implementara el metodo de la capa transaccional a traves del DealsWithAssetIssuing
    @Override
    public long getEstimatedFeeValue(AssetIssuer assetIssuer) {
        return getAssetIssuerManager().getEstimatedFeeValue(assetIssuer.getDigitalAsset());
    }

    //Implementara el metodo de la capa transaccional a traves del DealsWithAssetIssuing
    @Override
    public void IssueAsset(AssetIssuer assetIssuer) {
        getAssetIssuerManager().IssueAsset(assetIssuer.getDigitalAsset());
    }

}
