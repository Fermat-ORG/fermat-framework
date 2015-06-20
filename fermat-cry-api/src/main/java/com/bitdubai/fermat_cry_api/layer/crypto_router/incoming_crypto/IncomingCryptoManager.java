package com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto;

import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionManager;

/**
 * Created by loui on 18/03/15.
 */
public interface IncomingCryptoManager {

    public Registry getRegistry();

    public TransactionManager getTransactionManager();

}
