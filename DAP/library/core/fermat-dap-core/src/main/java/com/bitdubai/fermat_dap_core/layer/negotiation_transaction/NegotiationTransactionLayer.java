package com.bitdubai.fermat_dap_core.layer.negotiation_transaction;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_dap_core.layer.negotiation_transaction.negotiation_direct_sell.NegotiationDirectSellPluginSubsystem;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 16/03/16.
 */
public class NegotiationTransactionLayer extends AbstractLayer {

    //VARIABLE DECLARATION

    //CONSTRUCTORS

    public NegotiationTransactionLayer() {
        super(Layers.NEGOTIATION_TRANSACTION);
    }

    //PUBLIC METHODS
    @Override
    public void start() throws CantStartLayerException {
        try {
            registerPlugin(new NegotiationDirectSellPluginSubsystem());
        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "Negotiation Transaction Layer of DAP Platform",
                    "Problem trying to register a plugin."
            );
        }
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
