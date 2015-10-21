package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatAddonsEnum;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractAddonSubsystem</code>
 * contains all the basic functionality of an addon Subsystem class.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractAddonSubsystem {

    private final FermatAddonsEnum addonEnum;

    public AbstractAddonSubsystem(final FermatAddonsEnum addonEnum) {

        this.addonEnum = addonEnum;
    }

    protected Addon addon;

    public final Addon getAddon() {
        return addon;
    }

    public final FermatAddonsEnum getAddonEnum() {
        return addonEnum;
    }

    public abstract void start() throws CantStartSubsystemException;

}
