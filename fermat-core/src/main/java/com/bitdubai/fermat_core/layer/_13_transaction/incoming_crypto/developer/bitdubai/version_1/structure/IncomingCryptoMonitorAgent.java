package com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.structure;

/**
 * Created by ciencias on 3/30/15.
 */
public class IncomingCryptoMonitorAgent {


    /**
     * Este agente corre en su propio Thread.
     * 
     * Se despierta cada unos segundo a ver si se han registrado eventos de incoming crypto.
     * 
     * Si se han registrado, entonces se activa y procede a ir a buscar al plugin que corresponda la transaccion entrante.
     * 
     * Si no se han registrado, igual cada cierto tiempo va y verifica contra la lista de plugins que pueden recibir incoming crypto.
     * 
     * Cuando hace la verificacion contra un plugin, registra la transaccion en su base de datos propia y le confirma al plugin la recepcion.
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * * * * * * * * * * * * * * * * * * * * * * * 
     */
    
}
