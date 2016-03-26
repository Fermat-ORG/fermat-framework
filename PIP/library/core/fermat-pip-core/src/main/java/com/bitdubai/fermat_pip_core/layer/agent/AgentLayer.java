package com.bitdubai.fermat_pip_core.layer.agent;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by rodrigo on 3/26/16.
 */
public class AgentLayer extends AbstractLayer {

    public AgentLayer() {
        super(Layers.AGENT);
    }

    @Override
    public void start() throws CantStartLayerException {

    }
}
