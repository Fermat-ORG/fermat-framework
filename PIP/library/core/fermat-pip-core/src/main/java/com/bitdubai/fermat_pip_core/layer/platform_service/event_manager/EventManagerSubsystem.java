package com.bitdubai.fermat_pip_core.layer.platform_service.event_manager;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractAddonSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_pip_addon.layer.platform_service.event_manager.developer.bitdubai.DeveloperBitDubai;

/**
 * The class <code>com.bitdubai.fermat_pip_core.layer.platform_service.error_manager.EventManagerSubsystem</code>
 * haves all the necessary business logic to start the EventManager Subsystem of the PIP Platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 26/10/2015.
 */
public class EventManagerSubsystem extends AbstractAddonSubsystem {

    public EventManagerSubsystem() {
        super(new AddonReference(Addons.EVENT_MANAGER));
    }

    @Override
    public void start() throws CantStartSubsystemException {

        try {

            registerDeveloper(new DeveloperBitDubai());

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
