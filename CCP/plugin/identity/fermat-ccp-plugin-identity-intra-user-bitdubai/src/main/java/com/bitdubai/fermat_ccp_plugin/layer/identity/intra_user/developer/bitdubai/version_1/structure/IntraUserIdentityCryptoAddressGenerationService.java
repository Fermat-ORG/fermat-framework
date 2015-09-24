package com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CallToGetByCodeOnNONEException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantGetInstalledWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantGetPendingAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantHandleCryptoAddressRequestEventException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantIdentifyVaultException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressGenerationService;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.PendingAddressExchangeRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.VaultAdministrator;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure.IntraUserIdentityCryptoAddressGenerationService</code>
 * is intended to contain all the necessary business logic to generate and deliver address requested to this type of Actor.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IntraUserIdentityCryptoAddressGenerationService implements CryptoAddressGenerationService {

    private final CryptoAddressesManager cryptoAddressesManager;

    private final CryptoAddressBookManager cryptoAddressBookManager;

    private final VaultAdministrator vaultAdministrator;

    private final WalletManagerManager walletManagerManager;

    public IntraUserIdentityCryptoAddressGenerationService(CryptoAddressesManager cryptoAddressesManager, CryptoAddressBookManager cryptoAddressBookManager, VaultAdministrator vaultAdministrator, WalletManagerManager walletManagerManager) {
        this.cryptoAddressesManager = cryptoAddressesManager;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.vaultAdministrator = vaultAdministrator;
        this.walletManagerManager = walletManagerManager;
    }

    @Override
    public void handleCryptoAddressRequestedEvent(UUID requestId) throws CantHandleCryptoAddressRequestEventException {

        try {
            PendingAddressExchangeRequest pendingAddressExchangeRequest = cryptoAddressesManager.getPendingRequest(requestId);
            CryptoCurrency cryptoCurrencyInvolved = pendingAddressExchangeRequest.getCryptoCurrency();
            BlockchainNetworkType blockchainNetworkTypeInvolved = pendingAddressExchangeRequest.getBlockchainNetworkType();

            InstalledWallet installedWallet = walletManagerManager.getDefaultWallet(
                    cryptoCurrencyInvolved,
                    Actors.INTRA_USER,
                    blockchainNetworkTypeInvolved
            );

            CryptoVaultManager cryptoVaultManager = vaultAdministrator.getVaultByCryptoCurrency(cryptoCurrencyInvolved);

            //CryptoAddress cryptoAddress = cryptoVaultManager.getAddress(installedWallet.getWalletPublicKey(), blockchainNetworkTypeInvolved);

            CryptoAddress cryptoAddress = cryptoVaultManager.getAddress();

            ReferenceWallet referenceWallet = ReferenceWallet.getByCategoryAndIdentifier(
                    installedWallet.getWalletCategory(),
                    installedWallet.getWalletPlatformIdentifier()
            );

            cryptoAddressBookManager.registerCryptoAddress(
                    cryptoAddress,
                    pendingAddressExchangeRequest.getRequesterActorPublicKey(),
                    pendingAddressExchangeRequest.getActorType(), // TODO CHANGE
                    pendingAddressExchangeRequest.getActorToRequestPublicKey(),
                    pendingAddressExchangeRequest.getActorType(), // TODO CHANGE
                    installedWallet.getPlatform(),
                    VaultType.CRYPTO_CURRENCY_VAULT,
                    CryptoCurrencyVault.BITCOIN_VAULT.getCode(),
                    installedWallet.getWalletPublicKey(),
                    referenceWallet

            );

        } catch (CantRegisterCryptoAddressBookRecordException | CallToGetByCodeOnNONEException | InvalidParameterException e) {

            throw new CantHandleCryptoAddressRequestEventException(e);
        } catch (CantGetPendingAddressExchangeRequestException | PendingRequestNotFoundException e) {

            throw new CantHandleCryptoAddressRequestEventException(e, "RequestId: "+requestId);
        } catch (CantIdentifyVaultException e) {

            throw new CantHandleCryptoAddressRequestEventException(e, "Can't identify a vault to work with.");
        } catch (CantGetInstalledWalletException e) {

            throw new CantHandleCryptoAddressRequestEventException(e, "Can't get an Installed Wallet to work with.");
        }

    }
}
