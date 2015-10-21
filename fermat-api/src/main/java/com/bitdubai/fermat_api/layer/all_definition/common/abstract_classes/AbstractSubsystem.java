package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartSubsystemException;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractSubsystem</code>
 * contains all the basic functionality of a Subsystem class.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractSubsystem {

    protected Plugin plugin;

    public final Plugin getPlugin() {
        return plugin;
    }

    public abstract void start() throws CantStartSubsystemException;

}
