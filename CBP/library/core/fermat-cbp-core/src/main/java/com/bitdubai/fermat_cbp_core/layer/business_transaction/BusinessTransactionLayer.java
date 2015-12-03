package com.bitdubai.fermat_cbp_core.layer.business_transaction;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cbp_core.layer.business_transaction.close_conctract.CloseContractPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.business_transaction.open_contract.OpenContractPluginSubsystem;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/11/15.
 */
public class BusinessTransactionLayer extends AbstractLayer {
    public BusinessTransactionLayer() {
        super(Layers.BUSINESS_TRANSACTION);
    }

    @Override
    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new OpenContractPluginSubsystem());
            registerPlugin(new CloseContractPluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }

    }
}
