package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Alejandro Bicelis on 21/1/2016.
 */
public abstract class AsyncTransactionAgent<T> extends FermatAgent {

    private int SLEEP = 1000;
    private int TRANSACTION_DELAY = 15000;
    public Map<Long, T> transactionList;
    private Thread transactionThread;


    public AsyncTransactionAgent(){
        this.transactionList = new HashMap<>();


    }



    /* Public methods */
    public final void queueNewTransaction(T transaction){
        transactionList.put(System.currentTimeMillis() / 1000L, transaction);

        if (!isRunning())
            this.start();
    }


    public final void cancelTransaction(T transaction) throws Exception {

        //If agent isn's running, there are no transactions, throw exception.
        if(!isRunning())
            throw new Exception("Could not find transaction");

        //Agent is running, stop it.
        this.stop();

        //Try to find transaction
        boolean foundTransaction = false;
        for(Iterator<Map.Entry<Long, T>> it = transactionList.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Long, T> t = it.next();

            //If it exists, remove it
            if(transaction.equals(t)){
                it.remove();
                foundTransaction = true;
            }
        }

        //Restart agent
        this.start();

        //If no transaction was found, throw exception
        if(!foundTransaction)
            throw new Exception("Could not find transaction");
    }


    public final List<T> getQueuedTransactions()
    {
        List<T> transactions = new ArrayList<>();
        for(Map.Entry<Long, T> transaction : transactionList.entrySet()) {
            transactions.add(transaction.getValue());
        }
        return transactions;
    }

    public final void setTransactionDelayMillis(int delay)
    {
        this.TRANSACTION_DELAY = delay;

        if(TRANSACTION_DELAY < SLEEP)
            SLEEP = TRANSACTION_DELAY;
    }

    public abstract void processTransaction(T transaction);




    /**
     * FermatAgent Interface implementation.
     */
    @Override
    public final void start() {
        System.out.println("AsyncTransactionAgent - Transaction Agent START");

        this.transactionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    process();
            }
        });
        this.transactionThread.start();
        this.status = AgentStatus.STARTED;
    }

    @Override
    public final void stop() {
        System.out.println("AsyncTransactionAgent - Transaction Agent STOP");

        if (isRunning())
            this.transactionThread.interrupt();
        this.status = AgentStatus.STOPPED;
    }

    private final void process() {

        while (isRunning()) {

            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException interruptedException) {
                cleanResources();
                return;
            }

            if (transactionThread.isInterrupted()) {
                cleanResources();
                return;
            }

            doProcess();
        }
    }

    private final void doProcess() {
        System.out.println("AsyncTransactionAgent - Transaction Agent LOOP");

        for(Iterator<Map.Entry<Long, T>> it = transactionList.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Long, T> transaction = it.next();

            long timestamp = transaction.getKey().longValue();

            //Si ya han pasado n segundos
            if(transactionDelayExpired(transaction.getKey())){
                System.out.println("AsyncTransactionAgent - Transaction Agent time expired for transaction: " + transaction.toString());

                this.processTransaction(transaction.getValue());

                //sacar la transaccion del mapa
                it.remove();

            }
        }

        //Si no hay transacciones, frenar el agente.
        if(transactionList.size() == 0) {
            System.out.println("AsyncTransactionAgent - Transaction Agent list size=0, stopping");

            this.stop();
        }
    }

    private final void cleanResources() {
        transactionList.clear();
    }




    /* INTERNAL HELPER METHODS */
    private boolean transactionDelayExpired(long timestamp)
    {
        long timeDifference = System.currentTimeMillis() - (timestamp * 1000L);
        return (timeDifference > this.TRANSACTION_DELAY);

    }

}
