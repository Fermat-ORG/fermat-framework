package com.bitdubai.fermat_cbp_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_core.layer.actor.ActorLayer;
import com.bitdubai.fermat_cbp_core.layer.actor_connection.ActorConnectionLayer;
import com.bitdubai.fermat_cbp_core.layer.actor_network_service.ActorNetworkServiceLayer;
import com.bitdubai.fermat_cbp_core.layer.business_transaction.BusinessTransactionLayer;
import com.bitdubai.fermat_cbp_core.layer.contract.ContractLayer;
import com.bitdubai.fermat_cbp_core.layer.identity.IdentityLayer;
import com.bitdubai.fermat_cbp_core.layer.middleware.MiddlewareLayer;
import com.bitdubai.fermat_cbp_core.layer.negotiation.NegotiationLayer;
import com.bitdubai.fermat_cbp_core.layer.negotiation_transaction.NegotiationTransactionLayer;
import com.bitdubai.fermat_cbp_core.layer.network_service.NetworkServiceLayer;
import com.bitdubai.fermat_cbp_core.layer.stock_transactions.StockTransactionsLayer;
import com.bitdubai.fermat_cbp_core.layer.sub_app_module.SubAppModuleLayer;
import com.bitdubai.fermat_cbp_core.layer.user_level_business_transaction.UserLevelBusinessTransactionLayer;
import com.bitdubai.fermat_cbp_core.layer.wallet.WalletLayer;
import com.bitdubai.fermat_cbp_core.layer.wallet_module.WalletModuleLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartPlatformException;

/**
 * The class <code>com.bitdubai.fermat_cbp_core.CBPPlatform</code>
 * contains all the necessary business logic to start the CBP platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/11/2015.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CBPPlatform extends AbstractPlatform {

    public CBPPlatform() {
        super(new PlatformReference(Platforms.CRYPTO_BROKER_PLATFORM));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new ActorLayer());
            registerLayer(new ActorConnectionLayer());
            registerLayer(new ActorNetworkServiceLayer());
            registerLayer(new BusinessTransactionLayer());
            registerLayer(new ContractLayer());
            registerLayer(new IdentityLayer());
            registerLayer(new MiddlewareLayer());
            registerLayer(new NegotiationLayer());
            registerLayer(new NegotiationTransactionLayer());
            registerLayer(new NetworkServiceLayer());
            registerLayer(new StockTransactionsLayer());
            registerLayer(new SubAppModuleLayer());
            registerLayer(new UserLevelBusinessTransactionLayer());
            registerLayer(new WalletLayer());
            registerLayer(new WalletModuleLayer());

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "CBP Platform.",
                    "Problem trying to register a layer."
            );
        }
    }
}
