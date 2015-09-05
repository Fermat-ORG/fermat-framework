package com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotGetCryptoStatusException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InsufficientMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.VaultNotConnectedToNetworkException;
import com.bitdubai.fermat_dap_api.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/09/15.
 */
public class DigitalAssetCryptoTransactionFactory implements CryptoVaultManager, DealsWithErrors{

    ErrorManager errorManager;
    @Override
    public void connectToBitcoin() throws VaultNotConnectedToNetworkException {

    }

    @Override
    public void disconnectFromBitcoin() {

    }

    @Override
    public CryptoAddress getAddress() {

        //TODO: avoid null in full implementation
        CryptoAddress digitalAssetCryptoAddress=null;
        try{

            if(digitalAssetCryptoAddress==null){
                throw new ObjectNotSetException("digitalAssetCryptoAddress is null");
            }

        } catch(ObjectNotSetException exception){
            //TODO:Implement catch
            //this.errorManager.reportUnexpectedPluginException(Plugins.,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        catch (Exception e) {
            //TODO:Implement catch
            //FermatException e = new CantDeliverDatabaseException(CantDeliverDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "WalletId: " + developerDatabase.getName(),"Check the cause");
            //this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);

        }

        return digitalAssetCryptoAddress;
    }

    @Override
    public List<CryptoAddress> getAddresses(int amount) {
        return null;
    }

    @Override
    public String sendBitcoins(String walletPublicKey, UUID FermatTrId, CryptoAddress addressTo, long satoshis) throws InsufficientMoneyException, InvalidSendToAddressException, CouldNotSendMoneyException, CryptoTransactionAlreadySentException {
        return null;
    }

    @Override
    public boolean isValidAddress(CryptoAddress addressTo) {
        return false;
    }

    @Override
    public CryptoStatus getCryptoStatus(UUID transactionId) throws CouldNotGetCryptoStatusException {
        return null;
    }

    @Override
    public TransactionProtocolManager<CryptoTransaction> getTransactionManager() {
        return null;
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }
}
