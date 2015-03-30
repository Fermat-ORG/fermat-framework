package com.bitdubai.fermat_core.layer._13_transaction.incoming_crypto.developer.bitdubai.version_1.structure;

/**
 * Created by ciencias on 3/30/15.
 */
public class IncomingCryptoRegistry {

    /**
     * Esta clase maneja una tabla en su base de datos con la siguiente estructura:
     * 
     * Tabla: IncomingCryptoRegistry
     * 
     * Campos:
     * 
     * Id
     * AddressTo
     * CryptoCurrency
     * CryptoAmount
     * SourcePlugIn
     * CryptoStatus (Identified, Received, Confirmed, Reversed)
     * ReceptionStatus (Registered, Owned)
     * RelayStatus (Pending, Relayed, Delivered)
     * Timestamp
     * 
     * La clase basicamente maneja consultas a su tabla IncomingCryptoRegistry.
     * 
     * 
     * Tabla: EventsRecorded
     * 
     * Campos;
     * 
     * Id
     * Event  (codigo de 3 caracteres tomados de getCode del enum que define los eventos que este plugin escucha)
     * Status (Recorded, Processed )
     * Timestamp
     * 
     * 
     * 
     * 
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
     */
    
}
