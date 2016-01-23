package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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


    public final void cancelTransaction(T transaction) throws InvalidParameterException {
        throw new InvalidParameterException();

        //TODO:

        //Si el hilo no esta running no hay transacciones, throw CantFind


        // De lo contrario stop el hilo, buscar la transaccion, si existe borrarla, reiniciar el hilo si quedan transacciones y luego return
        // De lo contrario reiniciar el hilo y lanzar una excepcion
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

            doProcess();

            if (transactionThread.isInterrupted()) {
                cleanResources();
                return;
            }
        }
    }

    private final void doProcess() {
        System.out.println("AsyncTransactionAgent - Transaction Agent LOOP");

        for(Iterator<Map.Entry<Long, T>> it = transactionList.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Long, T> transaction = it.next();
            System.out.println("AsyncTransactionAgent - Transaction Agent new entry");

            long timestamp = transaction.getKey().longValue();

            //Si ya han pasado n segundos
            if(transactionDelayExpired(transaction.getKey())){
                System.out.println("AsyncTransactionAgent - Transaction Agent time expired");

                this.processTransaction(transaction.getValue());

                //sacar la transaccion del mapa
                it.remove();

            }
        }

        //Si no hay transacciones, frenar el agente.
        if(transactionList.size() == 0) {
            System.out.println("AsyncTransactionAgent - Transaction Agent size =0 stopping");

            this.stop();
        }
    }

    private final void cleanResources() {
        transactionList.clear();
    }




    /* INTERNAL HELPER METHODS */
    private boolean transactionDelayExpired(long timestamp)
    {
        System.out.println("AsyncTransactionAgent - TimeExpiredFunc - timestamp = " + (timestamp * 1000L));
        System.out.println("AsyncTransactionAgent - TimeExpiredFunc - currenttime = " + System.currentTimeMillis());

        long timeDifference = System.currentTimeMillis() - (timestamp * 1000L);
        System.out.println("AsyncTransactionAgent - TimeExpiredFunc - timeDifference = " + timeDifference);
        System.out.println("AsyncTransactionAgent - TimeExpiredFunc - transactDelay = " + this.TRANSACTION_DELAY);

        return (timeDifference > this.TRANSACTION_DELAY);

    }

}
