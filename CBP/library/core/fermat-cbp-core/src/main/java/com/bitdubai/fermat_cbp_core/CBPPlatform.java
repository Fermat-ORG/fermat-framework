package com.bitdubai.fermat_cbp_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_core.layer.actor_connection.ActorConnectionLayer;
import com.bitdubai.fermat_cbp_core.layer.actor_network_service.ActorNetworkServiceLayer;
import com.bitdubai.fermat_cbp_core.layer.business_transaction.BusinessTransactionLayer;
import com.bitdubai.fermat_cbp_core.layer.identity.IdentityLayer;
import com.bitdubai.fermat_cbp_core.layer.negotiation_transaction.NegotiationTransactionLayer;
import com.bitdubai.fermat_cbp_core.layer.network_service.NetworkServiceLayer;
import com.bitdubai.fermat_cbp_core.layer.stock_transactions.StockTransactionsLayer;
import com.bitdubai.fermat_cbp_core.layer.sub_app_module.SubAppModuleLayer;
import com.bitdubai.fermat_cbp_core.layer.wallet_module.WalletModuleLayer;

/**
 * The class <code>com.bitdubai.fermat_cbp_core.CBPPlatform</code>
 * haves all the necessary business logic to start the CBP platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/11/2015.
 */
public class CBPPlatform extends AbstractPlatform {

    public CBPPlatform() {
        super(new PlatformReference(Platforms.CRYPTO_BROKER_PLATFORM));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new ActorConnectionLayer());
            registerLayer(new ActorNetworkServiceLayer());
            registerLayer(new BusinessTransactionLayer());
            registerLayer(new IdentityLayer());
            registerLayer(new NetworkServiceLayer());
            registerLayer(new StockTransactionsLayer());
            registerLayer(new SubAppModuleLayer());
            registerLayer(new WalletModuleLayer());
            registerLayer(new StockTransactionsLayer());
            registerLayer(new NetworkServiceLayer());
            registerLayer(new NegotiationTransactionLayer());

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "CBP Platform.",
                    "Problem trying to register a layer."
            );
        }
    }
}
