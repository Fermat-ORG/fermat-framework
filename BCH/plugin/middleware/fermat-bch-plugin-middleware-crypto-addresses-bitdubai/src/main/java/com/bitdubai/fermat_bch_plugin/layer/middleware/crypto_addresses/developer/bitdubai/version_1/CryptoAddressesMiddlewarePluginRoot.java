package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.event_handlers.CryptoAddressesNewsEventHandler;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantExecutePendingActionsException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressMiddlewareExecutorService;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressesMiddlewareDealersFactory;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.utils.CryptoVaultSelector;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.utils.WalletManagerSelector;
import com.bitdubai.fermat_ccp_api.all_definition.enums.EventType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.PlatformCryptoVaultManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
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

public class CryptoAddressesMiddlewarePluginRoot extends AbstractPlugin {

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
    private PlatformCryptoVaultManager cryptoVaultManager;

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

            // execute pending crypto addresses requests
            executorService.executePendingActions();

            FermatEventListener cryptoAddressesNewsEventListener = eventManager.getNewListener(EventType.CRYPTO_ADDRESSES_NEWS);
            cryptoAddressesNewsEventListener.setEventHandler(new CryptoAddressesNewsEventHandler(executorService, this));
            eventManager.addListener(cryptoAddressesNewsEventListener);
            listenersAdded.add(cryptoAddressesNewsEventListener);

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (CantExecutePendingActionsException e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, "", "Error trying to execute pending actions in network service.");
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
                cryptoVaultManager
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
