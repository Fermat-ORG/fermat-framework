//package com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.structure;
//
//
//import android.content.Context;
//
//import android.net.NetworkInfo;
//
//import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
//import com.bitdubai.fermat_api.CantStartAgentException;
//import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
//import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
//import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
//import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.interfaces.ConnectionType;
//import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.interfaces.ConnectivityAgent;
//import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.interfaces.Network;
//import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
//import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
//import UnexpectedAddonsExceptionSeverity;
//import UnexpectedPluginExceptionSeverity;
//import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
//import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
//
///**
// * Created by Natalia on 08/05/2015.
// */
//
///**
// * The DeviceConnectivityAgent class is the implementation of ConnectivityAgent interfaces that is handled to DeviceManager.
// * That class check connections networks changes and raise an event.
// */
//public class DeviceConnectivityAgent  implements ConnectivityAgent,DealsWithErrors,DealsWithEvents {
//
//    /**
//     * ConnectivityAgent Member Variables.
//     */
//    Thread agentThread;
//    MonitorAgent monitorAgent;
//
//    Network connectionIfo = new DeviceNetwork();
//
//    Context context;
//
//    ConnectionType activeNetwork;
//
//    /**
//     * DealsWithErrors Interface member variables.
//     */
//    ErrorManager errorManager;
//
//    /**
//     * DealsWithEvents Interface member variables.
//     */
//    EventManager eventManager;
//
//
//    /**
//     * <p>DeviceConnectivityAgent implementation constructor
//     *
//     * @param activeNetwork ConnectionType enum
//     */
//    public DeviceConnectivityAgent(ConnectionType activeNetwork){
//        this.activeNetwork = activeNetwork;
//    }
//
//    /**
//     * ConnectivityMonitor Interface implementation.
//     */
//
//
//    @Override
//    public void start() throws CantStartAgentException{
//        this.monitorAgent = new MonitorAgent ();
//        try {
//            this.monitorAgent.Initialize();
//            this.monitorAgent.setErrorManager(this.errorManager);
//
//            this.agentThread = new Thread(this.monitorAgent);
//            this.agentThread.start();
//        }
//        catch (Exception exception) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
//            throw new CantStartAgentException();
//        }
//
//    }
//
//    @Override
//    public void stop(){
//        this.agentThread.interrupt();
//    }
//
//    /**
//     * <p>This method gets the active connection info
//     *
//     * @return Network object
//     */
//    @Override
//    public Network getConnectionInfo(){
//        return connectionIfo;
//    }
//
//
//    /**
//     * DealsWithErrors Interface implementation.
//     */
//    public void setErrorManager (ErrorManager errorManager){
//        this.errorManager = errorManager;
//    }
//
//    /**
//     * DealWithEvents Interface implementation.
//     */
//    @Override
//    public void setEventManager(EventManager eventManager) {
//        this.eventManager = eventManager;
//    }
//
//    private class MonitorAgent implements DealsWithErrors, Runnable  {
//
//        /**
//         * DealsWithErrors Interface member variables.
//         */
//        ErrorManager errorManager;
//
//        private static final int SLEEP_TIME = 5000;
//
//        /**
//         *DealsWithErrors Interface implementation.
//         */
//        @Override
//        public void setErrorManager(ErrorManager errorManager) {
//            this.errorManager = errorManager;
//        }
//
//
//        /**
//         * MonitorAgent interface implementation.
//         */
//        private void Initialize () {
//
//        }
//
//        /**
//         * Runnable Interface implementation.
//         */
//        @Override
//        public void run() {
//
//            /**
//             * Infinite loop.
//             */
//            while (true) {
//
//                /**
//                 * Sleep for a while.
//                 */
//                try {
//                    Thread.sleep(SLEEP_TIME);
//                } catch (InterruptedException interruptedException) {
//                    cleanResources();
//                    return;
//                }
//
//                /**
//                 * Now I do the main task.
//                 */
//                doTheMainTask();
//
//                /**
//                 * Check if I have been Interrupted.
//                 */
//                if (Thread.currentThread().isInterrupted()) {
//                    cleanResources();
//                    return;
//                }
//            }
//        }
//
//        private void doTheMainTask() {
//            try{
//                //Search active network and compare with last, if different i raise event
//
//                android.net.ConnectivityManager connectivityManager = ( android.net.ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
//                NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
//                //  NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(android.net.ConnectivityManager.TYPE_MOBILE );
//                if ( activeNetInfo != null )
//                {
//                    connectionIfo.setIsConnected(activeNetInfo.isConnected());
//
//                    switch(activeNetInfo.getType()) {
//
//                        case android.net.ConnectivityManager.TYPE_MOBILE_DUN:
//                            connectionIfo.setType(ConnectionType.MOBILE_DUN);
//
//                            break;
//                        case android.net.ConnectivityManager.TYPE_MOBILE_HIPRI:
//                            connectionIfo.setType(ConnectionType.MOBILE_HIPRI);
//
//                            break;
//                        case android.net.ConnectivityManager.TYPE_MOBILE_SUPL:
//                            connectionIfo.setType(ConnectionType.MOBILE_SUPL);
//
//                            break;
//                        case android.net.ConnectivityManager.TYPE_MOBILE_MMS:
//                            connectionIfo.setType(ConnectionType.MOBILE_MMS);
//
//                            break;
//                        case android.net.ConnectivityManager.TYPE_MOBILE:  //0
//                            connectionIfo.setType(ConnectionType.MOBILE_DATA);
//                            break;
//                        case android.net.ConnectivityManager.TYPE_WIFI: //1
//                            connectionIfo.setType(ConnectionType.WI_FI);
//
//                            break;
//                        case android.net.ConnectivityManager.TYPE_WIMAX: //6
//                            connectionIfo.setType(ConnectionType.WIMAX);
//
//                            break;
//                        case android.net.ConnectivityManager.TYPE_ETHERNET://9
//                            connectionIfo.setType(ConnectionType.ETHERNET);
//
//                            break;
//                        case android.net.ConnectivityManager.TYPE_BLUETOOTH://7
//                            connectionIfo.setType(ConnectionType.BLUETOOTH);
//
//                            break;
//                        default:
//                        	break;
//                    }
//
//                    /**
//                     * network change -raise event
//                     */
//
//                    if (activeNetwork != connectionIfo.getType() ){
//                        activeNetwork = connectionIfo.getType();
//                        FermatEvent fermatEvent = eventManager.getNewEvent(null);
//                        fermatEvent.setSource(EventSource.DEVICE_CONNECTIVITY);
//                        eventManager.raiseEvent(fermatEvent);
//                    }
//
//                }
//
//            }
//            catch (Exception exception) {
//                errorManager.reportUnexpectedAddonsException(Addons.DEVICE_CONNECTIVITY, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, exception);
//            }
//        }
//
//        private void cleanResources() {
//
//            /**
//             * Disconnect from database and explicitly set all references to null.
//             */
//
//        }
//    }
//}
