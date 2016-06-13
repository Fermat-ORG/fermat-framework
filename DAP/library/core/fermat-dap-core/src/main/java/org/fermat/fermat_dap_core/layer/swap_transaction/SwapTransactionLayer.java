package org.fermat.fermat_dap_core.layer.swap_transaction;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

import org.fermat.fermat_dap_core.layer.swap_transaction.asset_for_bitcoin.AssetForBitcoinPluginSubsystem;
import org.fermat.fermat_dap_core.layer.swap_transaction.bitcoin_for_asset.BitcoinForAssetPluginSubsystem;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/04/16.
 */
public class SwapTransactionLayer extends AbstractLayer {
    //VARIABLE DECLARATION

    //CONSTRUCTORS
    public SwapTransactionLayer() {
        super(Layers.SWAP_TRANSACTION);
    }

    //PUBLIC METHODS
    @Override
    public void start() throws CantStartLayerException {
        try {

            registerPlugin(new AssetForBitcoinPluginSubsystem());
            registerPlugin(new BitcoinForAssetPluginSubsystem());

        } catch (Exception e) {

            throw new CantStartLayerException(
                    e,
                    "Swap Transaction Layer of DAP Platform",
                    "Problem trying to register a plugin."
            );
        }
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
