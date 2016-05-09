package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.interfaces.WatchOnlyVaultManager;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.event_handlers.CryptoAddressesNewsEventHandler;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantExecutePendingActionsException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressMiddlewareExecutorService;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressesMiddlewareDealersFactory;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.utils.CryptoVaultSelector;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.utils.WalletManagerSelector;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.ArrayList;
import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.CryptoAddressesMiddlewarePluginRoot</code>
 * contains the main functionality of the plugin Crypto Addresses Middleware of Blockchains Platform Version 1.
 *
 * This plugin was created to manage the crypto address requests between fermat actors.
 * - Generate crypto address in the correct vault.
 * - Register it in the crypto address book.
 *
 * Created by Leon Acosta (laion.cj91@gmail.com) on 31/10/2015.
 */

public class CryptoAddressesMiddlewarePluginRoot extends AbstractPlugin implements FermatManager {

    /*
     * Init References list..
     */

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.CRYPTO_ADDRESSES)
    private CryptoAddressesManager cryptoAddressesManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_MODULE, plugin = Plugins.CRYPTO_ADDRESS_BOOK)
    private CryptoAddressBookManager cryptoAddressBookManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITCOIN_VAULT)
    private CryptoVaultManager cryptoVaultManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITCOIN_ASSET_VAULT)
    private AssetVaultManager assetVaultManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_VAULT, plugin = Plugins.BITCOIN_WATCH_ONLY_VAULT)
    private WatchOnlyVaultManager watchOnlyVaultManagera;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_MANAGER)
    private WalletManagerManager walletManagerManager;

    /**
     * End  References List..
     */

    private final List<FermatEventListener> listenersAdded;

    public CryptoAddressesMiddlewarePluginRoot() {
        super(new PluginVersionReference(new Version()));

        listenersAdded = new ArrayList<>();
    }

    /**
     * Service Interface implementation.
     */

    @Override
    public final void start() throws CantStartPluginException {

        try {

            CryptoAddressMiddlewareExecutorService executorService = new CryptoAddressMiddlewareExecutorService(
                    cryptoAddressesManager,
                    this.buildDealersFactory(),
                    errorManager,
                    getPluginVersionReference()
            );

            try {
                // execute pending crypto addresses requests
                executorService.executePendingActions();
            } catch (CantExecutePendingActionsException e) {

                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                e.printStackTrace();
            }

            FermatEventListener cryptoAddressesNewsEventListener = eventManager.getNewListener(EventType.CRYPTO_ADDRESSES_NEWS);
            cryptoAddressesNewsEventListener.setEventHandler(new CryptoAddressesNewsEventHandler(executorService, this));
            eventManager.addListener(cryptoAddressesNewsEventListener);
            listenersAdded.add(cryptoAddressesNewsEventListener);

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (Exception e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, "", "Unhandled error trying to start crypto addresses middleware plugin root.");
        }

    }

    private CryptoAddressesMiddlewareDealersFactory buildDealersFactory() {

        return new CryptoAddressesMiddlewareDealersFactory(
                cryptoAddressesManager      ,
                cryptoAddressBookManager    ,
                buildCryptoVaultSelector()  ,
                buildWalletManagerSelector()
        );
    }

    private CryptoVaultSelector buildCryptoVaultSelector() {

        return new CryptoVaultSelector(
                cryptoVaultManager, assetVaultManager, watchOnlyVaultManagera
        );
    }

    private WalletManagerSelector buildWalletManagerSelector() {

        return new WalletManagerSelector(
                walletManagerManager
        );
    }

    @Override
    public final void stop() {

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }
        listenersAdded.clear();

        this.serviceStatus = ServiceStatus.STOPPED;
    }
}
