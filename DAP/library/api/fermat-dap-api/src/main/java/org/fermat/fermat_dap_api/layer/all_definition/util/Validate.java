package org.fermat.fermat_dap_api.layer.all_definition.util;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;

import java.util.Map;

/**
 * Created by rodrigo on 4/9/16.
 */
public class Validate {
    private Validate() {
        throw new AssertionError(); //NO INSTANCES.
    }

    public static boolean isValidTransaction(DraftTransaction signedTransaction, DraftTransaction generatedTransaction) {
        Map<CryptoAddress, Long> signedFunds = signedTransaction.getFundsDistribution();
        Map<CryptoAddress, Long> generatedFunds = generatedTransaction.getFundsDistribution();

        for (Map.Entry<CryptoAddress, Long> entry : generatedFunds.entrySet()) {
            if (!signedFunds.entrySet().contains(entry)) {
                return false;
            }
        }
        return true;
    }

}
