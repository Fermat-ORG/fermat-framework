package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.DealsWithWalletAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces.DealsWithRegistry;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces.TransactionAgent;

import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.CantStartAgentException;

import java.util.ArrayList;
import java.util.List;
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


public class IncomingExtraUserRelayAgent implements DealsWithBitcoinWallet, DealsWithErrors, DealsWithRegistry , DealsWithWalletAddressBook ,TransactionAgent {


    /*
    * DealsWithBitcoinWallet Interface member variables.
    */
    private BitcoinWalletManager bitcoinWalletManager;

    private ActorAddressBookManager actorAddressBookManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithRegistry Interface member variables.
     */
    private IncomingExtraUserRegistry registry;

    /**
     * DealsWithWalletAddressBook Interface member variables.
     */
    private WalletAddressBookManager walletAddressBookManager;


    /**
     * TransactionAgent Member Variables.
     */
    private Thread agentThread;
    private RelayAgent relayAgent;

    /**
     * The Specialized Constructor
     */
    public IncomingExtraUserRelayAgent(final BitcoinWalletManager bitcoinWalletManager, final ActorAddressBookManager actorAddressBookManager,final ErrorManager errorManager, final IncomingExtraUserRegistry registry, final WalletAddressBookManager walletAddressBookManager){
        this.bitcoinWalletManager = bitcoinWalletManager;
        this.actorAddressBookManager = actorAddressBookManager;
        this.errorManager = errorManager;
        this.registry = registry;
        this.walletAddressBookManager = walletAddressBookManager;
    }


    /**
     * DealsWithBitcoinWallet Interface implementation.
     */
    @Override
    public void setBitcoinWalletManager(BitcoinWalletManager bitcoinWalletManager){
        this.bitcoinWalletManager = bitcoinWalletManager;
    }

    /**
     *DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * DealWithRegistry Interface implementation.
     */
    @Override
    public void setRegistry(IncomingExtraUserRegistry registry) {
        this.registry = registry;
    }

    /**
     * DealsWithWalletAddressBook Interface implementation.
     */
    @Override
    public void setWalletAddressBookManager(WalletAddressBookManager walletAddressBookManager) {
        this.walletAddressBookManager = walletAddressBookManager;
    }

    /**
     * TransactionAgent Interface implementation.
     */
    @Override
    public void start() throws CantStartAgentException {

        relayAgent = new RelayAgent(bitcoinWalletManager,actorAddressBookManager ,walletAddressBookManager, errorManager, registry);
        try {
            relayAgent.initialize();
            agentThread = new Thread(this.relayAgent);
            agentThread.start();
        }
        catch (Exception exception) {
            throw new CantStartAgentException(CantStartAgentException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "You should inspect the cause");
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

        private final BitcoinWalletManager bitcoinWalletManager;
        private final WalletAddressBookManager walletAddressBookManager;
        private final ErrorManager errorManager;
        private final IncomingExtraUserRegistry registry;
        private final ActorAddressBookManager actorAddressBookManager;
        private IncomingExtraUserTransactionHandler transactionHandler;

        private static final int SLEEP_TIME = 5000;

        public RelayAgent(final BitcoinWalletManager bitcoinWalletManager, final ActorAddressBookManager actorAddressBookManager,final WalletAddressBookManager walletAddressBookManager, final ErrorManager errorManager, final IncomingExtraUserRegistry registry){
            this.bitcoinWalletManager = bitcoinWalletManager;
            this.actorAddressBookManager = actorAddressBookManager;
            this.walletAddressBookManager = walletAddressBookManager;
            this.errorManager = errorManager;
            this.registry = registry;
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
            transactionHandler = new IncomingExtraUserTransactionHandler();
            transactionHandler.setBitcoinWalletManager(this.bitcoinWalletManager);
            transactionHandler.setActorAddressBookManager(this.actorAddressBookManager);
            transactionHandler.setWalletAddressBookManager(this.walletAddressBookManager);
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
            } catch (InvalidParameterException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
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
