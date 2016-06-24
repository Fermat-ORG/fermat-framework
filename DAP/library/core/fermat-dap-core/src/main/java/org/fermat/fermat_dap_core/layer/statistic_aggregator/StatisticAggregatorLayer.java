package org.fermat.fermat_dap_core.layer.statistic_aggregator;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

import org.fermat.fermat_dap_core.layer.statistic_aggregator.asset_issuer.AssetIssuerPluginSubsystem;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/04/16.
 */
public class StatisticAggregatorLayer extends AbstractLayer {
    //VARIABLE DECLARATION

    //CONSTRUCTORS
    public StatisticAggregatorLayer() {
        super(Layers.STATISTIC_AGGREGATOR);
    }

    //PUBLIC METHODS
    @Override
    public void start() throws CantStartLayerException {
        try {

            registerPlugin(new AssetIssuerPluginSubsystem());

        } catch (Exception e) {

            throw new CantStartLayerException(
                    e,
                    "Statistic Aggregator Layer of DAP Platform",
                    "Problem trying to register a plugin."
            );
        }
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
