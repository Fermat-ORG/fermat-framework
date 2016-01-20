package com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;

/**
 * The class <code>com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.SpecialistAndCryptoStatus</code>
 * provides the specialist and crypto status to notify associated to a group of transactions
 */
public class SpecialistAndCryptoStatus {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 2437;
    private static final int HASH_PRIME_NUMBER_ADD = 757;

    private Specialist specialist;
    private CryptoStatus cryptoStatus;

    public SpecialistAndCryptoStatus(Specialist specialist, CryptoStatus cryptoStatus){
        this.specialist = specialist;
        this.cryptoStatus = cryptoStatus;
    }

    public Specialist getSpecialist(){
        return this.specialist;
    }

    public CryptoStatus getCryptoStatus(){
        return this.cryptoStatus;
    }

    @Override
    public boolean equals(Object other){
        return other != null &&
                other instanceof SpecialistAndCryptoStatus &&
                 ((SpecialistAndCryptoStatus) other).getCryptoStatus().equals(this.cryptoStatus) &&
                  ((SpecialistAndCryptoStatus) other).getSpecialist().equals(this.specialist);
    }

    @Override
    public int hashCode() {
        int finalHash = 0;
        finalHash += this.cryptoStatus.hashCode();
        finalHash += this.specialist.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + finalHash;
    }
}
