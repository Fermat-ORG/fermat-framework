package com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatAddonsEnum;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonReference;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractAddonSubsystem</code>
 * contains all the basic functionality of an addon Subsystem class.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public abstract class AbstractAddonSubsystem {

    private final AddonReference addonReference;

    public AbstractAddonSubsystem(final FermatAddonsEnum addonEnum) {

        this.addonReference = new AddonReference(addonEnum);
    }

    protected Addon addon;

    public final Addon getAddon() {
        return addon;
    }

    public final AddonReference getAddonReference() {
        return addonReference;
    }

    public abstract void start() throws CantStartSubsystemException;

}
