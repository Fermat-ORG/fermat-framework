package com.bitdubai.fermat_pip_addon.layer.platform_service.event_manager.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantGetFeatureForDevelopersException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.MissingReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FeatureForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.DevelopersUtilReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_pip_addon.layer.platform_service.event_manager.developer.bitdubai.version_1.structure.EventManagerPlatformServiceEventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This addon is the responsible to manage all the events in fermat platform.
 *
 * Created by by Leon Acosta (laion.cj91@gmail.com) on 22-08-2015.
 */
public class EventManagerPlatformServiceAddonRoot extends AbstractAddon implements EventManager {

    private ErrorManager       errorManager      ;
    private FermatEventMonitor fermatEventMonitor;

    /**
     * EventManager Interface member variables.
     */
    private final Map<String, List<FermatEventListener>> listenersMap;

    public EventManagerPlatformServiceAddonRoot() {
        super(new AddonVersionReference(new Version()));

        listenersMap = new ConcurrentHashMap<>();
    }

    /**
     * EventManager Interface implementation.
     */
    @Override
    public FermatEventListener getNewListener(FermatEventEnum eventType) {
        return eventType.getNewListener(fermatEventMonitor);
    }

    @Override
    public FermatEvent getNewEvent(FermatEventEnum eventType) {
        return eventType.getNewEvent();
    }

    @Override
    public void addListener(final FermatEventListener listener) {

        String eventKey = buildMapKey(listener.getEventType());

        List<FermatEventListener> listenersList = listenersMap.get(eventKey);

        if (listenersList == null)
            listenersList = new ArrayList<>();

        listenersList.add(listener);

        listenersMap.put(eventKey, listenersList);
    }

    @Override
    public void removeListener(final FermatEventListener listener) {

        String eventKey = buildMapKey(listener.getEventType());

        List<FermatEventListener> listenersList = listenersMap.get(eventKey);

        listenersList.remove(listener);

        listenersMap.put(eventKey, listenersList);

        listener.setEventHandler(null);

    }

    @Override
    public void raiseEvent(final FermatEvent fermatEvent) {

        String eventKey = buildMapKey(fermatEvent.getEventType());

        List<FermatEventListener> listenersList = listenersMap.get(eventKey);

        if (listenersList != null) {
            for (FermatEventListener fermatEventListener : listenersList) {
                fermatEventListener.raiseEvent(fermatEvent);
            }
        }
    }

    private String buildMapKey(final FermatEventEnum fermatEnum) {
        StringBuilder builder = new StringBuilder();

        if (fermatEnum.getPlatform() != null)
            builder.append(fermatEnum.getPlatform().getCode());

        builder.append(fermatEnum.getCode());

        return builder.toString();
    }

    @Override
    public final void start() throws CantStartPluginException {

        try {

            validateAndAssignReferences();

            this.fermatEventMonitor = new EventManagerPlatformServiceEventMonitor(this.errorManager);

        } catch (final MissingReferencesException e) {

            throw new CantStartPluginException(
                    "",
                    "There was an error trying to assign references to Fermat EventManager."
            );

        } catch (final Exception e) {

            throw new CantStartPluginException(
                    "",
                    "Unhandled Error starting Fermat Event Manager."
            );
        }
    }

    @Override
    public List<AddonVersionReference> getNeededAddonReferences() {

        final List<AddonVersionReference> addonsNeeded = new ArrayList<>();

        addonsNeeded.add(new AddonVersionReference(Platforms.PLUG_INS_PLATFORM, Layers.PLATFORM_SERVICE, Addons.ERROR_MANAGER, Developers.BITDUBAI, new Version()));

        return addonsNeeded;
    }

    @Override
    public List<DevelopersUtilReference> getAvailableDeveloperUtils() {
        return new ArrayList<>();
    }

    @Override
    public FeatureForDevelopers getFeatureForDevelopers(final DevelopersUtilReference developersUtilReference) throws CantGetFeatureForDevelopersException {
        return null;
    }

    @Override
    protected void validateAndAssignReferences() throws MissingReferencesException {

        this.errorManager = (ErrorManager) this.getAddonReference(new AddonVersionReference(Platforms.PLUG_INS_PLATFORM, Layers.PLATFORM_SERVICE, Addons.ERROR_MANAGER, Developers.BITDUBAI, new Version()));

        if (errorManager == null) {
            throw new MissingReferencesException(
                "errorManager: "+ errorManager,
                "There is missing references for Event Manager Addon."
            );
        }

    }

}
