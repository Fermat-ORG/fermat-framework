package org.fermat.fermat_dap_core.layer.funds_transaction;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

import org.fermat.fermat_dap_core.layer.funds_transaction.asset_buyer.AssetBuyerPluginSubsystem;
import org.fermat.fermat_dap_core.layer.funds_transaction.asset_seller.AssetSellerPluginSubsystem;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 16/03/16.
 */
public class FundsTransactionLayer extends AbstractLayer {

    //VARIABLE DECLARATION

    //CONSTRUCTORS
    public FundsTransactionLayer() {
        super(Layers.FUNDS_TRANSACTION);
    }

    //PUBLIC METHODS
    @Override
    public void start() throws CantStartLayerException {
        try {
            registerPlugin(new AssetSellerPluginSubsystem());
            registerPlugin(new AssetBuyerPluginSubsystem());
        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "Funds Transaction Layer of DAP Platform",
                    "Problem trying to register a plugin."
            );
        }
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
