package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartSubsystemException;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractAddonSubsystem</code>
 * contains all the basic functionality of an addon Subsystem class.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractAddonSubsystem {

    protected Addon addon;

    public final Addon getAddon() {
        return addon;
    }

    public abstract void start() throws CantStartSubsystemException;

}
