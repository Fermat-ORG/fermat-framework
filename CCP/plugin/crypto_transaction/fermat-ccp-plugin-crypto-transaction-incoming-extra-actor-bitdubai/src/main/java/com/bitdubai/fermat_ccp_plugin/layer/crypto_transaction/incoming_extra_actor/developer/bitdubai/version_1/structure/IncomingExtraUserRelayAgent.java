package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.interfaces.DealsWithRegistry;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ciencias on 3/30/15.
 * Modified by Arturo Vallone 25/04/2015
 *
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


public class IncomingExtraUserRelayAgent implements DealsWithRegistry, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.interfaces.TransactionAgent {


    /*
    * DealsWithBitcoinWallet Interface member variables.
    */
    private CryptoWalletManager cryptoWalletManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithRegistry Interface member variables.
     */
    private IncomingExtraUserRegistry registry;

    /**
     * DealsWithCryptoAddressBook Interface member variables.
     */
    private CryptoAddressBookManager cryptoAddressBookManager;

    /**
     * DealsWithEventManager
     */
    private EventManager eventManager;


    /**
     * TransactionAgent Member Variables.
     */
    private Thread agentThread;
    private RelayAgent relayAgent;

    private Broadcaster broadcaster;

    private BitcoinLossProtectedWalletManager lossProtectedWalletManager;


    /**
     * The Specialized Constructor
     */
    public IncomingExtraUserRelayAgent(final CryptoWalletManager cryptoWalletManager, final ErrorManager errorManager, EventManager eventManager,final IncomingExtraUserRegistry registry, final CryptoAddressBookManager cryptoAddressBookManager,Broadcaster broadcaster, BitcoinLossProtectedWalletManager lossProtectedWalletManager){
        this.cryptoWalletManager = cryptoWalletManager;
        this.errorManager = errorManager;
        this.registry = registry;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.eventManager = eventManager;
        this.broadcaster = broadcaster;
        this.lossProtectedWalletManager = lossProtectedWalletManager;
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
    public void start() throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantStartAgentException {

        relayAgent = new RelayAgent(cryptoWalletManager, cryptoAddressBookManager, errorManager,eventManager, registry, broadcaster,lossProtectedWalletManager);
        try {
            relayAgent.initialize();
            agentThread = new Thread(this.relayAgent);
            agentThread.start();
        }
        catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantStartAgentException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantStartAgentException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "You should inspect the cause");
        }

    }

    public boolean isRunning(){
        return this.relayAgent != null && this.relayAgent.isRunning();
    }

    @Override
    public void stop()  {
        if(isRunning())
            this.relayAgent.stop();
    }

    private static class RelayAgent implements Runnable  {


        private AtomicBoolean running = new AtomicBoolean(false);

        private final CryptoWalletManager cryptoWalletManager;
        private final CryptoAddressBookManager cryptoAddressBookManager;
        private final ErrorManager errorManager;
        private final EventManager eventManager;
        private final IncomingExtraUserRegistry registry;
        private IncomingExtraUserTransactionHandler transactionHandler;
        private final Broadcaster broadcaster;
        private BitcoinLossProtectedWalletManager lossProtectedWalletManager;

        private static final int SLEEP_TIME = 10000;

        public RelayAgent(final CryptoWalletManager cryptoWalletManager, final CryptoAddressBookManager cryptoAddressBookManager, final ErrorManager errorManager,EventManager eventManager, final IncomingExtraUserRegistry registry, final Broadcaster broadcaster, BitcoinLossProtectedWalletManager lossProtectedWalletManager){
            this.cryptoWalletManager = cryptoWalletManager;
            this.cryptoAddressBookManager = cryptoAddressBookManager;
            this.errorManager = errorManager;
            this.registry = registry;
            this.eventManager = eventManager;
            this.broadcaster = broadcaster;
            this.lossProtectedWalletManager = lossProtectedWalletManager;
        }

        public boolean isRunning(){
            return running.get();
        }

        public void stop(){
            running.set(false);
        }

        /**
         * MonitorAgent interface implementation.
         */
        private void initialize () {
            transactionHandler = new IncomingExtraUserTransactionHandler(cryptoWalletManager, cryptoAddressBookManager, eventManager,broadcaster,lossProtectedWalletManager);
        }

        /**
         * Runnable Interface implementation.
         */
        @Override
        public void run() {

            running.set(true);

            /**
             * Infinite loop.
             */
            while (running.get()) {
                /**
                 * Sleep for a while.
                 */
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                    break;
                }

                /**
                 * Now I do the main task.
                 */
                doTheMainTask();

                /**
                 * Check if I have been Interrupted.
                 */
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            cleanResources();
        }

        private void doTheMainTask() {

            /*
            El RelayAgent del IncomingIncomingExtraUser analizará las transacciones con estado (RESPONSIBLE,TO_BE_APPLIED).
            */
            List<Transaction<CryptoTransaction>> responsibleTransactionList = new ArrayList<>();
            try {
                responsibleTransactionList.addAll(registry.getResponsibleTBATransactions());
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }

            if(responsibleTransactionList.isEmpty())
                return;

            System.out.println("TTF - EXTRA USER RELAY: " +responsibleTransactionList.size() + " TRANSACTION(s) TO BE APPLIED");
            // Por cada transacción en estado (RESPONSIBLE,TO_BE_APPLIED)
            // Aplica la transacción en la wallet correspondiente
            // y luego pasa la transacción al estado (RESPONSIBLE,APPLIED)

            for(Transaction<CryptoTransaction> transaction : responsibleTransactionList){
                // TODO: INVOCAR AL TRANSACTION EXECUTOR. SI DA EXEPCION RETORNAR SIN CONFIRMAR O HACER UN CONTINUE
                // TODO: CORREGIR LA LÓGICA DE LLAMAR AL BOOK Y AVAILABLE BALANCE
                try {
                    transactionHandler.handleTransaction(transaction);
                    registry.setToApplied(transaction.getTransactionID());
                    System.out.println("TTF - EXTRA USER RELAY: TRANSACTION APPLIED");
                } catch (Exception e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);


                }
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
