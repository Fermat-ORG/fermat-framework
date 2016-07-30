package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.event_handler;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 02/05/16.
 */
//public class IncomingNewOnlineStatusUpdateEventHandler extends AbstractChatMiddlewareEventHandler {
//
//    private ErrorManager errorManager;
//
//    @Override
//    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
//        if(this.chatMiddlewareRecorderService.getStatus()== ServiceStatus.STARTED) {
//
//            try {
////                this.chatMiddlewareRecorderService.IncomingNewOnlineStatusUpdateEventHandler((IncomingNewOnlineStatusUpdate) fermatEvent);
//            } catch(CantSaveEventException exception){
//                errorManager.reportUnexpectedPluginException(Plugins.CHAT_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
//                throw new CantSaveEventException(exception,"Handling the IncomingNewChatStatusUpdate", "Check the cause");
//            } catch(ClassCastException exception){
//                errorManager.reportUnexpectedPluginException(Plugins.CHAT_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
//                //Logger LOG = Logger.getGlobal();
//                //LOG.info("EXCEPTION DETECTOR----------------------------------");
//                //exception.printStackTrace();
//                throw new CantSaveEventException(FermatException.wrapException(exception), "Handling the IncomingNewChatStatusUpdate", "Cannot cast this event");
//            } catch(Exception exception){
//                errorManager.reportUnexpectedPluginException(Plugins.CHAT_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
//                throw new CantSaveEventException(exception,"Handling the IncomingNewChatStatusUpdate", "Unexpected exception");
//            }
//
//        }else {
//            throw new TransactionServiceNotStartedException();
//        }
//    }
//}
