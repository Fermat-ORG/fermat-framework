package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingMessage;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 17/08/16.
 */
public class IncomingMessageEventHandler  extends AbstractChatMiddlewareEventHandler  {

    private ErrorManager errorManager;

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (this.chatMiddlewareRecorderService.getStatus() == ServiceStatus.STARTED) {

            try {
                this.chatMiddlewareRecorderService.incomingMessageEventHandler((IncomingMessage) fermatEvent);
            } catch (CantSaveEventException exception) {
                throw new CantSaveEventException(exception, "Handling the IncomingChat", "Check the cause");
            } catch (ClassCastException exception) {
                //Logger LOG = Logger.getGlobal();
                //LOG.info("EXCEPTION DETECTOR----------------------------------");
                //exception.printStackTrace();
                throw new CantSaveEventException(FermatException.wrapException(exception), "Handling the IncomingChat", "Cannot cast this event");
            } catch (Exception exception) {
                errorManager.reportUnexpectedPluginException(Plugins.CHAT_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantSaveEventException(exception, "Handling the IncomingChat", "Unexpected exception");
            }

        } else {
            throw new TransactionServiceNotStartedException();
        }
    }
}
