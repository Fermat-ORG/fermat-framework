package com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.structure;

/**
 * Created by ciencias on 3/30/15.
 */
public class IncomingCryptoRelayerAgent {

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
     * * * * * * 
     */
    
}
