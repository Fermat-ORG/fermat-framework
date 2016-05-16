package com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantSelectSpecialistException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantStartAgentException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CryptoStatusNotHandledException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.SpecialistNotRegisteredException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.interfaces.DealsWithRegistry;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.interfaces.TransactionAgent;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.EventsLauncher;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.SpecialistAndCryptoStatus;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.SpecialistSelector;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

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


public class IncomingCryptoRelayAgent implements DealsWithRegistry , TransactionAgent {

    private final CryptoAddressBookManager cryptoAddressBookManager;
    private final ErrorManager errorManager;
    private final EventManager eventManager;

    /**
     * DealsWithRegistry Interface member variables.
     */
    private IncomingCryptoRegistry registry;

    public IncomingCryptoRelayAgent(CryptoAddressBookManager cryptoAddressBookManager,
                                    ErrorManager errorManager,
                                    EventManager eventManager) {
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
    }

    /**
     * TransactionAgent Member Variables.
     */
    private Thread agentThread;
    private RelayAgent relayAgent;

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

        this.relayAgent = new RelayAgent (cryptoAddressBookManager, errorManager, eventManager);
        try {

            this.relayAgent.setRegistry(this.registry);
            this.relayAgent.initialize();

            this.agentThread = new Thread(this.relayAgent);
            this.agentThread.start();
        }
        catch (Exception exception) {
            throw new CantStartAgentException("Agent failed to start",exception,"","");
        }

    }

    @Override
    public void stop() {

    }


    public boolean isRunning(){
        return this.relayAgent.isRunning();
    }

    private static class RelayAgent implements DealsWithRegistry , Runnable  {

        private AtomicBoolean running = new AtomicBoolean(false);

        public boolean isRunning(){
            return running.get();
        }

        private final CryptoAddressBookManager cryptoAddressBookManager;
        private final ErrorManager errorManager;
        private final EventManager eventManager;

        /**
         * DealsWithRegistry Interface member variables.
         */
        private IncomingCryptoRegistry registry;

        public RelayAgent(CryptoAddressBookManager cryptoAddressBookManager,
                                        ErrorManager errorManager,
                                        EventManager eventManager) {
            this.cryptoAddressBookManager = cryptoAddressBookManager;
            this.errorManager = errorManager;
            this.eventManager = eventManager;
        }





        private SpecialistSelector specialistSelector;
        private EventsLauncher eventsLauncher;

        private static final int SLEEP_TIME = 5000;

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

            this.specialistSelector = new SpecialistSelector(cryptoAddressBookManager);
        }

        /**
         * Runnable Interface implementation.
         */
        @Override
        public void run() {

            /**
             * Infinite loop.
             */
            running.set(true);
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

            /*
            El RelayAgent del IncomingCrypto analizará las transacciones con estado (RESPONSIBLE,NO_ACTION_REQUIRED).
            */
            List<Transaction<CryptoTransaction>> responsibleTransactionList = null;
            try {
                responsibleTransactionList = this.registry.getResponsibleNARTransactions();
            } catch (InvalidParameterException e) {
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                return;
            }

            if(responsibleTransactionList.isEmpty())
                return;

            //System.out.println("TTF - INCOMING CRYPTO RELAY: " + responsibleTransactionList.size() + " TRANSACTION(s) DETECTED");

            // Por cada una de ellas haría los siguientes pasos en el orden enunciado:
            // Deduciría a partir de la información de las mismas su Specialist y lo marcaría.
            // Pasaría la transacción al estado (RESPONSIBLE,TO_BE_NOTIFIED)
            for (Transaction<CryptoTransaction> transaction : responsibleTransactionList) {
                try {
                    this.registry.setToNotify(transaction.getTransactionID(),
                                              this.specialistSelector.getSpecialist(transaction.getInformation()));
                    //System.out.println("TTF - INCOMING CRYPTO RELAY: SPECIALIST SETTED");
                } catch (CantSelectSpecialistException e) {
                    // TODO: MANAGE EXCEPTION
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }

            /*
            Cuando termina de recorrer la lista recorre ahora todas las que están con TransactonStatus RESPONSIBLE y ProtocolStatus TO_BE_NOTIFIED o SENDING_NOTIFIED. Registra todos los especialistas que vio en este recoorido (no intentar optimizar usando el recorrido anterior porque puede perderse si el sistema se cae) y realiza los siguente pasos en el orden enunciado:
            Por cada Specialist registrado en el recorrido anterior lanza el evento correspondiente (IncomingCryptTransactionsWaitingTransferenceSpecalistEvent)
            */
            Set<SpecialistAndCryptoStatus> specialistSet;
            try {
                specialistSet = this.registry.getSpecialists();
            } catch (InvalidParameterException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                System.out.println("TTF - INCOMING CRYPTO RELAY: GET SPECIALISTS FAILED");
                return;
            }

            //System.out.println("TTF - INCOMING CRYPTO RELAY: SPECIALIST LIST CALCULATED");
            //System.out.println("TTF - INCOMING CRYPTO RELAY: " + specialistSet.size() + " SPECIALIST(s) TO CALL");


            try {
                this.eventsLauncher.sendEvents(specialistSet);
            } catch (SpecialistNotRegisteredException | CryptoStatusNotHandledException e) {
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                return;
            }

            //System.out.println("TTF - INCOMING CRYPTO RELAY: SPECIALIST(s) INFORMED");


            //  Pasa cada transacción con ProtocolStatus TO_BE_NOTIFIED a SENDING_NOTIFED.
            this.registry.setToSendingNotified();
            //System.out.println("TTF - INCOMING CRYPTO RELAY: TRANSACTION(s) SETTED TO NOTIFIED");


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
