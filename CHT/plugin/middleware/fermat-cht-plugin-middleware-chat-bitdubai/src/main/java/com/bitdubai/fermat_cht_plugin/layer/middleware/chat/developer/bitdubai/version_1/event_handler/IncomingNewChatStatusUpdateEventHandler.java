package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingChat;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingNewChatStatusUpdate;

/**
 * Created by José Vilchez on 09/03/16.
 */
public class IncomingNewChatStatusUpdateEventHandler extends AbstractChatMiddlewareEventHandler {
    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if(this.chatMiddlewareRecorderService.getStatus()== ServiceStatus.STARTED) {

            try {
                this.chatMiddlewareRecorderService.IncomingNewChatStatusUpdateEventHandler((IncomingNewChatStatusUpdate) fermatEvent);
            } catch(CantSaveEventException exception){
                throw new CantSaveEventException(exception,"Handling the IncomingNewChatStatusUpdate", "Check the cause");
            } catch(ClassCastException exception){
                //Logger LOG = Logger.getGlobal();
                //LOG.info("EXCEPTION DETECTOR----------------------------------");
                //exception.printStackTrace();
                throw new CantSaveEventException(FermatException.wrapException(exception), "Handling the IncomingNewChatStatusUpdate", "Cannot cast this event");
            } catch(Exception exception){
                throw new CantSaveEventException(exception,"Handling the IncomingNewChatStatusUpdate", "Unexpected exception");
            }

        }else {
            throw new TransactionServiceNotStartedException();
        }
    }
}
