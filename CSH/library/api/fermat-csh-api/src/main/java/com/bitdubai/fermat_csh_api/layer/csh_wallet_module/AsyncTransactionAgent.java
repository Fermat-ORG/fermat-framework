package com.bitdubai.fermat_csh_api.layer.csh_wallet_module;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 21/1/2016.
 */
public class AsyncTransactionAgent<T> extends FermatAgent {

    private static final int SLEEP = 5000;
    private Map<Long, T> transactionList;
    private Thread transactionThread;

    ErrorManager errorManager;
    CashMoneyWalletModuleManager cashMoneyWalletModuleManager;


    public AsyncTransactionAgent(final ErrorManager errorManager, final CashMoneyWalletModuleManager cashMoneyWalletModuleManager){
        this.cashMoneyWalletModuleManager = cashMoneyWalletModuleManager;
        this.errorManager = errorManager;
        this.transactionList = new HashMap<>();

        this.transactionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    process();
            }
        });
    }

    void addNewTransaction(T transaction){
        transactionList.put(System.currentTimeMillis() / 1000L, transaction);
    }

















    /**
     * FermatAgent Interface implementation.
     */
    @Override
    public void start() {
        System.out.println("CASH - Transaction Agent START");

        this.transactionThread.start();
        this.status = AgentStatus.STARTED;
    }

    @Override
    public void stop() {
        if (isRunning())
            this.transactionThread.interrupt();
        this.status = AgentStatus.STOPPED;
    }

    public void process() {

        while (isRunning()) {

            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException interruptedException) {
                cleanResources();
                return;
            }

            doTheMainTask();

            if (transactionThread.isInterrupted()) {
                cleanResources();
                return;
            }
        }
    }

    private void doTheMainTask() {
        System.out.println("CASH - Transaction Agent LOOP");


        for (Map.Entry<Long, T> transaction : transactionList.entrySet()) {
            long timestamp = transaction.getKey().longValue();

            //Si ya han pasado n segundos
            if(true){
                //llamar a una funcion externa en el module pasandole la transaccion
                //esa funcion debera enviar un evento al gui y aplicar la transaccion en la wallet abajo

                //sacar la transaccion del mapa



            }

        }


        //Si no hay transacciones, parar el agente.

    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }



}
