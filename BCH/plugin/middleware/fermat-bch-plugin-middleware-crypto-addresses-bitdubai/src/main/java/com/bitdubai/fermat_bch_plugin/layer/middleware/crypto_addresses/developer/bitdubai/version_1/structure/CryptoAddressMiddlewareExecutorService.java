package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantExecutePendingActionsException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressesNewException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CryptoAddressDealerNotSupportedException;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.interfaces.CryptoAddressDealer;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingCryptoAddressRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure.CryptoAddressMiddlewareExecutorService</code>
 * haves all consumable methods from the plugin
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/15.
 * @version 1.0
 */
public final class CryptoAddressMiddlewareExecutorService {

    private final CryptoAddressesManager                  cryptoAddressesManager;
    private final CryptoAddressesMiddlewareDealersFactory dealersFactory        ;
    private final ErrorManager                            errorManager          ;
    private final PluginVersionReference                  pluginVersionReference;

    public CryptoAddressMiddlewareExecutorService(final CryptoAddressesManager                  cryptoAddressesManager,
                                                  final CryptoAddressesMiddlewareDealersFactory dealersFactory        ,
                                                  final ErrorManager                            errorManager          ,
                                                  final PluginVersionReference                  pluginVersionReference) {

        this.cryptoAddressesManager = cryptoAddressesManager;
        this.dealersFactory         = dealersFactory        ;
        this.errorManager           = errorManager          ;
        this.pluginVersionReference = pluginVersionReference;
    }

    public final void executePendingActions() throws CantExecutePendingActionsException {

        try {

            final List<CryptoAddressRequest> cryptoAddressRequestRespondedList = cryptoAddressesManager.listPendingCryptoAddressRequests();

            System.out.println("******* Crypto Addresses -> List of pending Crypto Address Requests -> "+cryptoAddressRequestRespondedList);

            for (final CryptoAddressRequest request : cryptoAddressRequestRespondedList) {
                if (request.getRequestType() == RequestType.RECEIVED){
                    try {
                        final CryptoAddressDealer dealer = dealersFactory.getCryptoAddressDealer(request.getCryptoAddressDealer());

                        dealer.handleCryptoAddressesNew(request);

                    } catch(CryptoAddressDealerNotSupportedException |
                            CantHandleCryptoAddressesNewException    e) {

                        errorManager.reportUnexpectedPluginException(pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

                    }
                }
            }

        } catch(CantListPendingCryptoAddressRequestsException e) {

            throw new CantExecutePendingActionsException(e, "", "There was a problem trying to execute pending actions.");
        }
    }

}