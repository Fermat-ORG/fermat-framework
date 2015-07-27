package com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionSender;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;

/**
 * The interface <code>com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager</code>
 * define the methods that the Incoming Crypto plugin exposes to its clients. As this plugin only function is to
 * route crypto transactions it extends <code></code>TransactionSender<CryptoTransaction> interface
 *
 * @author Ezequiel Postan
 * @since Java JDK 1.7
 */
public interface IncomingCryptoManager extends TransactionSender<CryptoTransaction> {
}
