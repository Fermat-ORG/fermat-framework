package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.BitcoinTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.DealsWithActorAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.DealsWithWalletAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces.DealsWithRegistry;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces.TransactionAgent;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util.SpecialistSelector;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.CantSelectSpecialistException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.CantStartAgentException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util.EventsLauncher;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util.TransactionExecutor;

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


public class IncomingExtraUserRelayAgent implements DealsWithWalletAddressBook , DealsWithErrors, DealsWithEvents, DealsWithRegistry , TransactionAgent {


    /**
     * DealsWithActorAddressBook Interface member variables.
     */
    private WalletAddressBookManager walletAddressBookManager;

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
    private IncomingExtraUserRegistry registry;


    /**
     * TransactionAgent Member Variables.
     */
    private Thread agentThread;
    private RelayAgent relayAgent;

    /**
     * DealsWithActorAddressBook Interface implementation.
     */
    @Override
    public void setWalletAddressBookManager(WalletAddressBookManager walletAddressBookManager) {
        this.walletAddressBookManager = walletAddressBookManager;
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
    public void setRegistry(IncomingExtraUserRegistry registry) {
        this.registry = registry;
    }


    /**
     * TransactionAgent Interface implementation.
     */
    @Override
    public void start() throws CantStartAgentException {

        this.relayAgent = new RelayAgent ();
        try {
            this.relayAgent.setActorAddressBookManager(this.actorAddressBook);
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



    private static class RelayAgent implements DealsWithWalletAddressBook , DealsWithBitcoinWallet, DealsWithErrors, DealsWithEvents, DealsWithRegistry , Runnable  {

        /**
         * DealsWithActorAddressBook Interface member variables.
         */
        private WalletAddressBookManager walletAddressBookManager;

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
        private IncomingExtraUserRegistry registry;



        private TransactionExecutor transactionExecutor;

        private static final int SLEEP_TIME = 5000;



        /**
         * DealsWithActorAddressBook Interface implementation.
         */
        @Override
        public void setWalletAddressBookManager(WalletAddressBookManager walletAddressBookManager) {
            this.walletAddressBookManager = walletAddressBookManager;
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
        public void setRegistry(IncomingExtraUserRegistry registry) {
            this.registry = registry;
        }


        /**
         * MonitorAgent interface implementation.
         */
        private void initialize () {
            this.transactionExecutor = new TransactionExecutor();
            this.transactionExecutor.setWalletAddressBookManager(this.walletAddressBookManager);
            this.transactionExecutor.setBitcoinWalletManager();
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

            /*
            El RelayAgent del IncomingIncomingExtraUser analizará las transacciones con estado (RESPONSIBLE,TO_BE_APPLIED).
            */
            List<Transaction<CryptoTransaction>> responsibleTransactionList = this.registry.getResponsibleTBATransactions();

            // Por cada transacción en estado (RESPONSIBLE,TO_BE_APPLIED)
            // Aplica la transacción en la wallet correspondiente
            // y luego pasa la transacción al estado (RESPONSIBLE,APPLIED)

            for(Transaction<CryptoTransaction> transaction : responsibleTransactionList){
                    //TODO: INVOCAR AL TRANSACTION EXECUTOR. SI DA EXEPCION RETORNAR SIN CONFIRMAR O HACER UN CONTINUE
                    this.registry.setToApplied(transaction.getTransactionID());
            }

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
