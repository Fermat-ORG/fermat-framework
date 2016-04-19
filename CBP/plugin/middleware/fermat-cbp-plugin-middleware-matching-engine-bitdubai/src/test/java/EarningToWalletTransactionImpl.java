import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantTransferEarningsToWalletException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningToWalletTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.exceptions.CantCreateBankMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.interfaces.BankMoneyDestockManager;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created by nelsonalfo on 18/04/16.
 */
public class EarningToWalletTransactionImpl implements EarningToWalletTransaction {

    private BankMoneyDestockManager bankMoneyDestockManager;

    public EarningToWalletTransactionImpl(BankMoneyDestockManager bankMoneyDestockManager) {
        this.bankMoneyDestockManager = bankMoneyDestockManager;
    }

    @Override
    public void transferEarningsToEarningWallet(EarningsPair earningsPair, List<EarningTransaction> earningTransactions)
            throws CantTransferEarningsToWalletException {

        if (earningsPair == null)
            throw new CantTransferEarningsToWalletException("Cant Transfer the earnings to the Earning Wallet", null,
                    "N/A", "The earningsPair parameter cannot be null");

        if (earningTransactions == null)
            throw new CantTransferEarningsToWalletException("Cant Transfer the earnings to the Earning Wallet", null,
                    "N/A", "The list of earning cannot be null");


        if (!earningTransactions.isEmpty()) {
            for (EarningTransaction earningTransaction : earningTransactions) {
                try {
                    bankMoneyDestockManager.createTransactionDestock(
                            "Actor",
                            FiatCurrency.getByCode(earningsPair.getEarningCurrency().getCode()),
                            WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode(),
                            earningsPair.getEarningsWallet().getPublicKey(),
                            "123456789",
                            BigDecimal.valueOf(earningTransaction.getAmount()),
                            "Transference of earnings from the Broker Wallet",
                            BigDecimal.ZERO,
                            OriginTransaction.EARNING_EXTRACTION,
                            earningTransaction.getId().toString());

                } catch (CantCreateBankMoneyDestockException e) {
                    throw new CantTransferEarningsToWalletException(
                            "Cant Transfer the earnings to the Earning Wallet", e,
                            "Trying to make the destock of the merchandise",
                            "Verify the params are correct");

                } catch (InvalidParameterException e) {
                    throw new CantTransferEarningsToWalletException(
                            "Cant Transfer the earnings to the Earning Wallet", e,
                            "Trying to get the earning currency to make the destock",
                            "Verify the code is set or the currency for the appropriate type");
                }
            }
        }
    }
}