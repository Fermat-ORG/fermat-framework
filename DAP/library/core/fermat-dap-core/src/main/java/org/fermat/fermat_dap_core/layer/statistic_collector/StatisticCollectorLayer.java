package org.fermat.fermat_dap_core.layer.statistic_collector;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

import org.fermat.fermat_dap_core.layer.statistic_collector.asset_issuing.AssetIssuingPluginSubsystem;
import org.fermat.fermat_dap_core.layer.statistic_collector.asset_swap.AssetSwapPluginSubsystem;
import org.fermat.fermat_dap_core.layer.statistic_collector.asset_transfer.AssetTransferPluginSubsystem;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/04/16.
 */
public class StatisticCollectorLayer extends AbstractLayer {
    //VARIABLE DECLARATION

    //CONSTRUCTORS
    public StatisticCollectorLayer() {
        super(Layers.STATISTIC_COLLECTOR);
    }

    //PUBLIC METHODS
    @Override
    public void start() throws CantStartLayerException {
        try {

            registerPlugin(new AssetIssuingPluginSubsystem());
            registerPlugin(new AssetSwapPluginSubsystem());
            registerPlugin(new AssetTransferPluginSubsystem());

        } catch (Exception e) {

            throw new CantStartLayerException(
                    e,
                    "Statistic Collector Layer of DAP Platform",
                    "Problem trying to register a plugin."
            );
        }
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
