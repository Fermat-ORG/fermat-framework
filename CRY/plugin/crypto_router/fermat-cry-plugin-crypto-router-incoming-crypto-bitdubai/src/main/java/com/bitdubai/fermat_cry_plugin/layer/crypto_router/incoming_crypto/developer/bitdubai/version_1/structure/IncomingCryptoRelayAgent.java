package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.ActorAddressBookManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.DealsWithActorAddressBook;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.interfaces.DealsWithRegistry;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.interfaces.TransactionAgent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.SpecialistSelector;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantSelectSpecialistException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartAgentException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.EventsLauncher;

import java.util.List;

/**
 * Created by ciencias on 3/30/15.
 * Modified by Arturo Vallone 25/04/2015
 */


/**
 * Este es un proceso que toma las transacciones registradas en el registry en un estado pendiente de anunciar, 
 * las lee una por una y dispara el evento que corresponda en cada caso.
 *
 * Para cada transaccion, consulta el Address Book enviandole la direccion en la que se recibio la crypto.
 * El Address book devolvera el User al cual esa direccion fue entregada. De esta manera esta clase podra determinar
 * contra que tipo de usuario se esta ejecutando esta transaccion y a partir de ahi podra disparar el evento que 
 * corresponda para cada tipo de usuario.
 *
 * Al ser un Agent, la ejecucion de esta clase es en su propio Thread. Seguir el patron de diseño establecido para esto.
 * *
 * * * * * * * 
 *
 * * * * * * public
 */


public class IncomingCryptoRelayAgent implements DealsWithActorAddressBook , DealsWithErrors, DealsWithEvents, DealsWithRegistry , TransactionAgent {


    /**
     * DealsWithActorAddressBook Interface member variables.
     */
    private ActorAddressBookManager actorAddressBook;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * DealsWithRegistry Interface member variables.
     */
    private IncomingCryptoRegistry registry;


    /**
     * TransactionAgent Member Variables.
     */
    private Thread agentThread;
    private RelayAgent relayAgent;

    /**
     * DealsWithActorAddressBook Interface implementation.
     */
    @Override
    public void setUserAddressBookManager(ActorAddressBookManager actorAddressBook) {
        this.actorAddressBook = actorAddressBook;
    }

    /**
     *DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    /**
     * DealWithRegistry Interface implementation.
     */
    @Override
    public void setRegistry(IncomingCryptoRegistry registry) {
        this.registry = registry;
    }


    /**
     * TransactionAgent Interface implementation.
     */
    @Override
    public void start() throws CantStartAgentException {

        this.relayAgent = new RelayAgent ();
        try {
            this.relayAgent.setUserAddressBookManager(this.actorAddressBook);
            this.relayAgent.setErrorManager(this.errorManager);
            this.relayAgent.setEventManager(this.eventManager);
            this.relayAgent.setRegistry(this.registry);
            this.relayAgent.initialize();

            this.agentThread = new Thread(this.relayAgent);
            this.agentThread.start();
        }
        catch (Exception exception) {
            System.out.println("Exception: " + exception.getMessage());
            exception.printStackTrace();
            throw new CantStartAgentException();
        }

    }

    @Override
    public void stop() {

    }

    /*
      ¿Qué quizo hacer arturo acá?!

    private void eventsToRaise(){

        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_EXTRA_USER);
        platformEvent.setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent);

        PlatformEvent platformEvent_1 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_INTRA_USER);
        platformEvent_1.setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent_1);

        PlatformEvent platformEvent_2 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_IDENTIFIED_FROM_DEVICE_USER);
        platformEvent_2.setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent_2);

        PlatformEvent platformEvent_3 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEIVED_FROM_EXTRA_USER);
        platformEvent_3.setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent_3);

        PlatformEvent platformEvent_4 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEIVED_FROM_INTRA_USER);
        platformEvent_4.setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent_4);

        PlatformEvent platformEvent_5 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEIVED_FROM_DEVICE_USER);
        platformEvent_5.setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent_5);

        PlatformEvent platformEvent_6 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_EXTRA_USER);
        platformEvent_6.setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent_6);

        PlatformEvent platformEvent_7 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_INTRA_USER);
        platformEvent_7.setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent_7);

        PlatformEvent platformEvent_8 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_RECEPTION_CONFIRMED_FROM_DEVICE_USER);
        platformEvent_8.setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent_8);

        PlatformEvent platformEvent_9 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_REVERSED_FROM_EXTRA_USER);
        platformEvent_9.setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent_9);

        PlatformEvent platformEvent_10 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_REVERSED_FROM_INTRA_USER);
        platformEvent_10.setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent_10);

        PlatformEvent platformEvent_11 = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_REVERSED_FROM_DEVICE_USER);
        platformEvent_11.setSource(EventSource.CRYPTO_ADDRESS_BOOK);
        eventManager.raiseEvent(platformEvent_11);

    }
    */

    private static class RelayAgent implements DealsWithActorAddressBook , DealsWithErrors, DealsWithEvents, DealsWithRegistry , Runnable  {

        /**
         * DealsWithActorAddressBook Interface member variables.
         */
        private ActorAddressBookManager actorAddressBook;

        /**
         * DealsWithErrors Interface member variables.
         */
        private ErrorManager errorManager;

        /**
         * DealsWithEvents Interface member variables.
         */
        private EventManager eventManager;

        /**
         * DealsWithRegistry Interface member variables.
         */
        private IncomingCryptoRegistry registry;




        private SpecialistSelector specialistSelector;
        private EventsLauncher eventsLauncher;

        private static final int SLEEP_TIME = 5000;



        /**
         * DealsWithActorAddressBook Interface implementation.
         */
        @Override
        public void setUserAddressBookManager(ActorAddressBookManager actorAddressBook) {
            this.actorAddressBook = actorAddressBook;
        }

        /**
         *DealsWithErrors Interface implementation.
         */
        @Override
        public void setErrorManager(ErrorManager errorManager) {
            this.errorManager = errorManager;
        }


        /**
         * DealWithEvents Interface implementation.
         */
        @Override
        public void setEventManager(EventManager eventManager) {
            this.eventManager = eventManager;
        }


        /**
         * DealWithRegistry Interface implementation.
         */
        @Override
        public void setRegistry(IncomingCryptoRegistry registry) {
            this.registry = registry;
        }


        /**
         * MonitorAgent interface implementation.
         */
        private void initialize () {
            this.eventsLauncher = new EventsLauncher();
            this.eventsLauncher.setEventManager(this.eventManager);

            this.specialistSelector = new SpecialistSelector();
            this.specialistSelector.setUserAddressBookManager(actorAddressBook);
        }

        /**
         * Runnable Interface implementation.
         */
        @Override
        public void run() {

            /**
             * Infinite loop.
             */
            while (true) {

                /**
                 * Sleep for a while.
                 */
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    cleanResources();
                    return;
                }

                /**
                 * Now I do the main task.
                 */
                doTheMainTask();

                /**
                 * Check if I have been Interrupted.
                 */
                if (Thread.currentThread().isInterrupted()) {
                    cleanResources();
                    return;
                }
            }
        }

        private void doTheMainTask() {
            // errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);

            /*
            El RelayAgent del IncomingCrypto analizará las transacciones con estado (RESPONSIBLE,NO_ACTION_REQUIRED).
            */
            List<Transaction<CryptoTransaction>> responsibleTransactionList = this.registry.getResponsibleNARTransactions();

            // Por cada una de ellas haría los siguientes pasos en el orden enunciado:
            // Deduciría a partir de la información de las mismas su Specialist y lo marcaría.
            // Pasaría la transacción al estado (RESPONSIBLE,TO_BE_NOTIFIED)
            for(Transaction<CryptoTransaction> transaction : responsibleTransactionList){
                try {
                    this.registry.setToNotify(transaction.getTransactionID(),
                                    this.specialistSelector.getSpecialist(transaction.getInformation()));
                } catch (CantSelectSpecialistException e) {
                    // TODO: MANAGE EXCEPTION
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }

            /*
            Cuando termina de recorrer la lista recorre ahora todas las que están con TransactonStatus RESPONSIBLE y ProtocolStatus TO_BE_NOTIFIED o SENDING_NOTIFIED. Registra todos los especialistas que vio en este recoorido (no intentar optimizar usando el recorrido anterior porque puede perderse si el sistema se cae) y realiza los siguente pasos en el orden enunciado:
            Por cada Specialist registrado en el recorrido anterior lanza el evento correspondiente (IncomingCryptTransactionsWaitingTransferenceSpecalistEvent)
            */
            //
            List<Specialist> specialistList = null;
            try {
                specialistList = this.registry.getSpecialists();
            } catch (InvalidParameterException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
            }

            this.eventsLauncher.sendEvents(specialistList);

            //  Pasa cada transacción con ProtocolStatus TO_BE_NOTIFIED a SENDING_NOTIFED.
            this.registry.setToSendingNotified();

            // Aquí termina su tarea, será el receptor de las transacciones quien las confirmará
            // al recibirlas

        }

        private void cleanResources() {

            /**
             * Disconnect from database and explicitly set all references to null.
             */

        }


    }

}
