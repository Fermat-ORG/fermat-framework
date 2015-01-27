package platform.layer._2_event;

import platform.layer.CantStartLayerException;
import platform.layer.PlatformLayer;
import platform.layer._2_event.manager.ManagerSubsystem;

/**
 * Created by ciencias on 23.01.15.
 */
public class EventLayer implements PlatformLayer {

    EventManager eventManager;

    public EventManager getEventManager() {
        return this.eventManager;
    }

    @Override
    public void start() throws CantStartLayerException {


        /**
         * For now, the only way to communicate with other devices is through a cloud service.
         */
        EventSubsystem managerSubsystem = new ManagerSubsystem();

        try {
            managerSubsystem.start();
            this.eventManager = ((EventSubsystem) managerSubsystem).getEventManager();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }
    }


}
