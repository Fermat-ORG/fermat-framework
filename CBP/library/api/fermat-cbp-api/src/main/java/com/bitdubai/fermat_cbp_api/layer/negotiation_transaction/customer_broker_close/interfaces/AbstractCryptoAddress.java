package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatVaultEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CallToGetByCodeOnNONEException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantCryptoAddressesNewException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGenerateAndRegisterCryptoAddressException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGenerateCryptoAddressException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGetDefaultWalletException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantIdentifyVaultException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantIdentifyWalletManagerException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantRegisterCryptoAddressBookException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils.CryptoVaultSelector;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils.WalletManagerSelector;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantGetInstalledWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.DefaultWalletNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;

/**
 * Created by Yordin Alayn on 27.12.15.
 */
public abstract class AbstractCryptoAddress {

    private final CryptoAddressBookManager cryptoAddressBookManager;
    private final CryptoVaultSelector cryptoVaultSelector;
    private final WalletManagerSelector walletManagerSelector;
    private final CryptoCurrencyVault currencyVault;

    //TODO YORDIN: ADAPTATION TO FERMATS. ADD PARAMETER currencyVault. SEE WHAT TO DO WITH currencyVault
    public AbstractCryptoAddress(
            final CryptoAddressBookManager cryptoAddressBookManager,
            final CryptoVaultSelector cryptoVaultSelector,
            final WalletManagerSelector walletManagerSelector,
            final CryptoCurrencyVault currencyVault
    ) {
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.cryptoVaultSelector = cryptoVaultSelector;
        this.walletManagerSelector = walletManagerSelector;
        this.currencyVault = currencyVault;
    }

    /*GET THE CRYPTO ADDRESS*/
    protected final CryptoAddress getAddress(
            final VaultType vaultType,
            final CryptoCurrency cryptoCurrency)
            throws CantGenerateCryptoAddressException {

        try {

            return cryptoVaultSelector.getVault(vaultType, cryptoCurrency).getCryptoAddress(BlockchainNetworkType.getDefaultBlockchainNetworkType());

        } catch (CantIdentifyVaultException e) {
            throw new CantGenerateCryptoAddressException(e, "", "Problem identifying vault.");
        } catch (GetNewCryptoAddressException e) {
            throw new CantGenerateCryptoAddressException(e, "", "Problem identifying vault.");
        }
    }

    /*GET THE DEFAULT WALLET FOR CRPYTO ADDRESS*/
    protected final InstalledWallet getDefaultWallet(
            final Actors actorType,
            final CryptoCurrency cryptoCurrency,
            final BlockchainNetworkType networkType,
            final Platforms platform)
            throws CantGetDefaultWalletException {

        try {

            return walletManagerSelector.getWalletManager(platform).getDefaultWallet(cryptoCurrency, actorType, networkType);

        } catch (CantIdentifyWalletManagerException e) {
            throw new CantGetDefaultWalletException(e, "", "Problem identifying wallet manager.");
        } catch (DefaultWalletNotFoundException e) {
            throw new CantGetDefaultWalletException(e, "", "Default wallet not found.");
        } catch (CantGetInstalledWalletException e) {

            throw new CantGetDefaultWalletException(e, "", "There was a problem trying to get the default wallet.");
        }
    }

    /*REGISTER THE CRYPTO ADDRESS*/
    protected final void registerCryptoAddress(
            final CryptoAddress cryptoAddress,
            final CustomerBrokerCloseCryptoAddressRequest request,
            final InstalledWallet installedWallet,
            final VaultType vaultType
    ) throws CantRegisterCryptoAddressBookException {

        try {

            FermatVaultEnum fermatVaultEnum = cryptoVaultSelector.getVaultEnum(vaultType, request.getCryptoCurrency());

            ReferenceWallet referenceWallet = ReferenceWallet.getByCategoryAndIdentifier(installedWallet.getWalletCategory(), installedWallet.getWalletPlatformIdentifier());

            //REGITER THE ADDRESS IN THE CRYPTO ADDRESS BOOK
            cryptoAddressBookManager.registerCryptoAddress(
                    cryptoAddress,
                    request.getIdentityPublicKeyRequesting(),
                    request.getIdentityTypeRequesting(),
                    request.getIdentityPublicKeyResponding(),
                    request.getIdentityTypeResponding(),
                    installedWallet.getPlatform(),
                    vaultType,
                    fermatVaultEnum.getCode(),
                    installedWallet.getWalletPublicKey(),
                    referenceWallet
            );

        } catch (InvalidParameterException | CallToGetByCodeOnNONEException e) {
            throw new CantRegisterCryptoAddressBookException(e, new StringBuilder().append("walletCategory: ").append(installedWallet.getWalletCategory()).append(" - walletPlatformIdentifier: ").append(installedWallet.getWalletPlatformIdentifier()).toString(), "Error trying to get the reference wallet type.");
        } catch (CantRegisterCryptoAddressBookRecordException e) {
            throw new CantRegisterCryptoAddressBookException(e, "", "Error trying to register the crypto address.");
        }
    }

    /*GENERATE AND REGISTER THE CRYPTO ADDRESS*/
    //TODO YORDIN: ADAPTATION TO FERMATS. ADD PARAMETER currencyVault
    protected final CryptoAddress generateAndRegisterCryptoAddress(
            final Platforms platform,
            final VaultType vaultType,
            final CustomerBrokerCloseCryptoAddressRequest request
    ) throws CantGenerateAndRegisterCryptoAddressException {

        try {

            InstalledWallet wallet = this.getDefaultWallet(
                    request.getIdentityTypeResponding(),
                    request.getCryptoCurrency(),
                    request.getBlockchainNetworkType(),
                    platform
            );

            final CryptoAddress cryptoAddress = this.getAddress(vaultType, request.getCryptoCurrency());


            this.registerCryptoAddress(
                    cryptoAddress,
                    request,
                    wallet,
                    vaultType
            );

            return cryptoAddress;

        } catch (CantGetDefaultWalletException e) {
            throw new CantGenerateAndRegisterCryptoAddressException(e, "", "There was a problem trying to get a wallet.");
        } catch (CantRegisterCryptoAddressBookException e) {
            throw new CantGenerateAndRegisterCryptoAddressException(e, "", "There was a problem trying to register the crypto address.");
        } catch (CantGenerateCryptoAddressException e) {
            throw new CantGenerateAndRegisterCryptoAddressException(e, "", "There was a problem trying to generate the crypto address.");
        }

    }

    //REQUEST THE GENERATE AND REGISTER OF THE CRYPTO ADDRESS
    public abstract CryptoAddress CryptoAddressesNew(final CustomerBrokerCloseCryptoAddressRequest request) throws CantCryptoAddressesNewException;
}
