package com.bitdubai.fermat_csh_api.layer.csh_wallet_module;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 21/1/2016.
 */
public abstract class AsyncTransactionAgent<T> extends FermatAgent {

    private int SLEEP = 1000;
    private int TRANSACTION_DELAY = 15000;
    private Map<Long, T> transactionList;
    private Thread transactionThread;


    public AsyncTransactionAgent(){
        this.transactionList = new HashMap<>();

        this.transactionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    process();
            }
        });
    }



    /* Public methods */
    public void addNewTransaction(T transaction){
        transactionList.put(System.currentTimeMillis() / 1000L, transaction);

        if (!isRunning())
            this.start();
    }


    public abstract void processTransaction(T transaction);

    public abstract void transactionFailed(T transaction, Exception exception);


    public void setTransactionDelayMillis(int delay)
    {
        this.TRANSACTION_DELAY = delay;

        if(TRANSACTION_DELAY < SLEEP)
            SLEEP = TRANSACTION_DELAY;
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
            if(transactionDelayExpired(transaction.getKey())){

                this.processTransaction(transaction.getValue());                        //esta funcion debera enviar un evento al gui y aplicar la transaccion en la wallet abajo

                //sacar la transaccion del mapa
                transactionList.remove(transaction.getKey());

            }

        }

        //Si no hay transacciones, frenar el agente.
        if(transactionList.size() == 0)
            this.stop();

    }

    private void cleanResources() {
        /**
         * Disconnect from database and explicitly set all references to null.
         */
    }





    /* HELPER METHODS */
    private boolean transactionDelayExpired(long timestamp)
    {
        long currentTimestamp = (System.currentTimeMillis() / 1000L);
        long timeDifference = currentTimestamp - timestamp;
        return (timeDifference > this.TRANSACTION_DELAY);

    }



}
