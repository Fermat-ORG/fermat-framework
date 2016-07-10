package org.fermat.fermat_dap_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartPlatformException;

import org.fermat.fermat_dap_core.layer.actor.ActorLayer;
import org.fermat.fermat_dap_core.layer.actor_network_service.ActorNetworkServiceLayer;
import org.fermat.fermat_dap_core.layer.digital_asset_transaction.DigitalAssetTransactionLayer;
import org.fermat.fermat_dap_core.layer.funds_transaction.FundsTransactionLayer;
import org.fermat.fermat_dap_core.layer.identity.IdentityLayer;
import org.fermat.fermat_dap_core.layer.metadata.MetadataLayer;
import org.fermat.fermat_dap_core.layer.middleware.MiddlewareLayer;
import org.fermat.fermat_dap_core.layer.network_service.NetworkServiceLayer;
import org.fermat.fermat_dap_core.layer.offer.OfferLayer;
import org.fermat.fermat_dap_core.layer.sub_app_module.SubAppModuleLayer;
import org.fermat.fermat_dap_core.layer.wallet.WalletLayer;
import org.fermat.fermat_dap_core.layer.wallet_module.WalletModuleLayer;

/**
 * The class <code>DAPPlatform</code>
 * This core has the logic to instance all the modules of the DAP Platform.
 * <p/>
 * Created by PatricioGesualdi - (pmgesualdi@hotmail.com) on 11/10/2015.
 */
public final class DAPPlatform extends AbstractPlatform {

    public DAPPlatform() {
        super(new PlatformReference(Platforms.DIGITAL_ASSET_PLATFORM));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new ActorLayer());
            registerLayer(new ActorNetworkServiceLayer());
//            registerLayer(new BusinessTransactionLayer());
//            registerLayer(new CryptoTransactionLayer());
            registerLayer(new DigitalAssetTransactionLayer());
            registerLayer(new FundsTransactionLayer());
            registerLayer(new IdentityLayer());
            registerLayer(new MetadataLayer());
//            registerLayer(new MetadataTransactionLayer());
            registerLayer(new MiddlewareLayer());
            registerLayer(new NetworkServiceLayer());
            registerLayer(new OfferLayer());
//            registerLayer(new OfferTransactionLayer());
//            registerLayer(new StatisticAggregatorLayer());
//            registerLayer(new StatisticCollectorLayer());
            registerLayer(new SubAppModuleLayer());
//            registerLayer(new SwapTransactionLayer());
//            registerLayer(new UserLevelBusinessTransactionLayer());
            registerLayer(new WalletLayer());
            registerLayer(new WalletModuleLayer());

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "",
                    "Problem trying to register a layer in DAP Platform."
            );
        }
    }
}
