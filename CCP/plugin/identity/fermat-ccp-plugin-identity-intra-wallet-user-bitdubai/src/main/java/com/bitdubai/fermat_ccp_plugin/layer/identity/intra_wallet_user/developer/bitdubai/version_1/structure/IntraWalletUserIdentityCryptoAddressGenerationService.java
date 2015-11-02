package com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CallToGetByCodeOnNONEException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantGetInstalledWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.DefaultWalletNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantAcceptAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantDenyAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantGetPendingAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressRequestEventException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantIdentifyVaultException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure.IntraWalletUserIdentityCryptoAddressGenerationService</code>
 * is intended to contain all the necessary business logic to generate and deliver address requested to this type of Actor.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraWalletUserIdentityCryptoAddressGenerationService {

    public static final Actors actorType = Actors.INTRA_USER;

    private final CryptoAddressesManager                    cryptoAddressesManager  ;
    private final CryptoAddressBookManager                  cryptoAddressBookManager;
    private final IntraWalletUserIdentityVaultAdministrator vaultAdministrator      ;
    private final WalletManagerManager                      walletManagerManager    ;

    public IntraWalletUserIdentityCryptoAddressGenerationService(final CryptoAddressesManager                    cryptoAddressesManager  ,
                                                                 final CryptoAddressBookManager                  cryptoAddressBookManager,
                                                                 final IntraWalletUserIdentityVaultAdministrator vaultAdministrator      ,
                                                                 final WalletManagerManager                      walletManagerManager    ) {

        this.cryptoAddressesManager   = cryptoAddressesManager;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.vaultAdministrator       = vaultAdministrator;
        this.walletManagerManager     = walletManagerManager;
    }

    public void handleCryptoAddressRequestedEvent(final UUID requestId) throws CantHandleCryptoAddressRequestEventException {

        try {
            CryptoAddressRequest request = cryptoAddressesManager.getPendingRequest(requestId);
            handleCryptoAddressRequestedEvent(request);
        } catch (CantGetPendingAddressExchangeRequestException | PendingRequestNotFoundException e) {

            throw new CantHandleCryptoAddressRequestEventException(e, "RequestId: "+requestId);
        }
    }

    public void handleCryptoAddressRequestedEvent(final CryptoAddressRequest request) throws CantHandleCryptoAddressRequestEventException {

        try {

            try {
                InstalledWallet wallet = walletManagerManager.getDefaultWallet(
                        request.getCryptoCurrency(),
                        actorType,
                        request.getBlockchainNetworkType()
                );

                CryptoCurrencyVault cryptoCurrencyVault = CryptoCurrencyVault.getByCryptoCurrency(request.getCryptoCurrency());

                CryptoVaultManager vault = vaultAdministrator.getVault(cryptoCurrencyVault);

                CryptoAddress cryptoAddress = vault.getAddress();

                ReferenceWallet referenceWallet = ReferenceWallet.getByCategoryAndIdentifier(wallet.getWalletCategory(), wallet.getWalletPlatformIdentifier());

                System.out.println("************ Crypto Addresses -> updating ok.");

                cryptoAddressBookManager.registerCryptoAddress(
                        cryptoAddress,
                        request.getIdentityPublicKeyResponding(),
                        request.getIdentityTypeResponding(),
                        request.getIdentityPublicKeyRequesting(),
                        request.getIdentityTypeRequesting(),
                        wallet.getPlatform(),
                        cryptoCurrencyVault.getVaultType(),
                        cryptoCurrencyVault.getCode(),
                        wallet.getWalletPublicKey(),
                        referenceWallet
                );

                System.out.println("************ Crypto Addresses ->registered crypto address.");

                System.out.println("************ Crypto Addresses -> i will accept the address exchange request.");

                cryptoAddressesManager.acceptAddressExchangeRequest(
                        request.getRequestId(),
                        cryptoAddress
                );

                System.out.println("************ Crypto Addresses -> i already accept the address exchange request.");

            } catch(DefaultWalletNotFoundException z) {

                cryptoAddressesManager.denyAddressExchangeRequest(request.getRequestId());
            }

        } catch (PendingRequestNotFoundException              |
                 CantDenyAddressExchangeRequestException      |
                 CantAcceptAddressExchangeRequestException    |
                 CantRegisterCryptoAddressBookRecordException |
                 CallToGetByCodeOnNONEException               |
                 InvalidParameterException e                  ) {

            throw new CantHandleCryptoAddressRequestEventException(e);
        } catch (CantIdentifyVaultException e) {

            throw new CantHandleCryptoAddressRequestEventException(e, "Can't identify a vault to work with.");
        } catch (CantGetInstalledWalletException e) {

            throw new CantHandleCryptoAddressRequestEventException(e, "Can't get an Installed Wallet to work with.");
        }
    }
}
