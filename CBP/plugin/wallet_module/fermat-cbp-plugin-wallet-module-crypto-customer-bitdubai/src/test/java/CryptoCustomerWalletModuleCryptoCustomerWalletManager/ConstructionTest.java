package CryptoCustomerWalletModuleCryptoCustomerWalletManager;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.CantCreateNewWalletException;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractDetailType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationBankAccount;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationLocations;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListPlatformsException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantUpdateActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.ActorExtraDataManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.BrokerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.QuotesExtraData;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks.CustomerBrokerContractPurchaseMock;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.ListsForStatusPurchase;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantCreateCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantGetCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantDeleteLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListBankAccountsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListLocationsPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateBankAccountPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateLocationPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetNextClauseTypeException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantGetCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantGetListCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNewManager;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_purchase.interfaces.CustomerBrokerPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractHistoryException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationInformationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CouldNotCancelNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CouldNotConfirmNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.MerchandiseExchangeRate;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetAssociatedCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCurrentIndexSummaryForCurrenciesOfInterestException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCryptoCustomerWalletAssociatedSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCryptoCustomerWalletProviderSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCustomerBrokerNegotiationInformationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantSaveCryptoCustomerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CouldNotStartNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.BrokerIdentityBusinessInfo;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletProviderSetting;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleCryptoCustomerWalletManager;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantFindProcessException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantGetInstalledWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantInstallLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantInstallSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantRemoveWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantRenameWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantUninstallLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantUninstallSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantUninstallWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.DefaultWalletNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletInstallationProcess;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by roy on 7/02/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {
    public static final String PATH_DIRECTORY = "cbpwallet/setting";

    private final WalletManagerManager walletManagerManager = new WalletManagerManager() {
        @Override
        public void createNewWallet(UUID walletIdInTheDevice, String newName) throws CantCreateNewWalletException {

        }

        @Override
        public List<InstalledWallet> getInstalledWallets() throws CantListWalletsException {
            return null;
        }

        @Override
        public void installLanguage(UUID walletCatalogueId, UUID languageId, Languages language, String label, Version version) throws CantInstallLanguageException {

        }

        @Override
        public void installSkin(UUID walletCatalogueId, UUID skinId, String alias, String Preview, Version version) throws CantInstallSkinException {

        }

        @Override
        public WalletInstallationProcess installWallet(WalletCategory walletCategory, String walletPlatformIdentifier) throws CantFindProcessException {
            return null;
        }

        @Override
        public void uninstallLanguage(UUID walletCatalogueId, UUID languageId) throws CantUninstallLanguageException {

        }

        @Override
        public void uninstallSkin(UUID walletCatalogueId, UUID skinId) throws CantUninstallSkinException {

        }

        @Override
        public void uninstallWallet(UUID walletIdInThisDevice) throws CantUninstallWalletException {

        }

        @Override
        public void removeWallet(UUID walletIdInTheDevice) throws CantRemoveWalletException {

        }

        @Override
        public void renameWallet(UUID walletIdInTheDevice, String newName) throws CantRenameWalletException {

        }

        @Override
        public InstalledWallet getInstalledWallet(String walletPublicKey) throws CantCreateNewWalletException {
            return null;
        }

        @Override
        public InstalledWallet getDefaultWallet(CryptoCurrency cryptoCurrency, Actors actorType, BlockchainNetworkType blockchainNetworkType) throws CantGetInstalledWalletException, DefaultWalletNotFoundException {
            return null;
        }
    };
    private final CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager = new CustomerBrokerPurchaseNegotiationManager() {
        @Override
        public void createCustomerBrokerPurchaseNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantCreateCustomerBrokerPurchaseNegotiationException {

        }

        @Override
        public void updateCustomerBrokerPurchaseNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {

        }

        @Override
        public void updateNegotiationNearExpirationDatetime(UUID negotiationId, Boolean status) throws CantUpdateCustomerBrokerPurchaseNegotiationException {

        }

        @Override
        public void cancelNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {

        }

        @Override
        public boolean closeNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            return false;
        }

        @Override
        public void sendToBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {

        }

        @Override
        public void waitForBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {

        }

        @Override
        public Collection<CustomerBrokerPurchaseNegotiation> getNegotiations() throws CantGetListPurchaseNegotiationsException {
            return null;
        }

        @Override
        public CustomerBrokerPurchaseNegotiation getNegotiationsByNegotiationId(UUID negotiationId) throws CantGetListPurchaseNegotiationsException {
            return null;
        }

        @Override
        public Collection<CustomerBrokerPurchaseNegotiation> getNegotiationsByStatus(NegotiationStatus status) throws CantGetListPurchaseNegotiationsException {
            return null;
        }

        @Override
        public ClauseType getNextClauseType(ClauseType type) throws CantGetNextClauseTypeException {
            return null;
        }

        @Override
        public ClauseType getNextClauseTypeByCurrencyType(CurrencyType paymentMethod) throws CantGetNextClauseTypeException {
            return null;
        }

        @Override
        public void createNewLocation(String location, String uri) throws CantCreateLocationPurchaseException {

        }

        @Override
        public void updateLocation(NegotiationLocations location) throws CantUpdateLocationPurchaseException {

        }

        @Override
        public void deleteLocation(NegotiationLocations location) throws CantDeleteLocationPurchaseException {

        }

        @Override
        public Collection<NegotiationLocations> getAllLocations() throws CantGetListLocationsPurchaseException {
            return null;
        }

        @Override
        public void createNewBankAccount(NegotiationBankAccount bankAccount) throws CantCreateBankAccountPurchaseException {

        }

        @Override
        public void updateBankAccount(NegotiationBankAccount bankAccount) throws CantUpdateBankAccountPurchaseException {

        }

        @Override
        public void deleteBankAccount(NegotiationBankAccount bankAccount) throws CantDeleteBankAccountPurchaseException {

        }

        @Override
        public Collection<NegotiationBankAccount> getAllBankAccount() throws CantGetListBankAccountsPurchaseException {
            return null;
        }

        @Override
        public Collection<NegotiationBankAccount> getBankAccountByCurrencyType(FiatCurrency currency) throws CantGetListBankAccountsPurchaseException {
            return null;
        }

        @Override
        public Collection<FiatCurrency> getCurrencyTypeAvailableBankAccount() throws CantGetListBankAccountsPurchaseException {
            return null;
        }
    };
    private final UUID pluginId = UUID.randomUUID();
    private final PluginFileSystem pluginFileSystem = new PluginFileSystem() {
        @Override
        public PluginTextFile getTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException, CantCreateFileException {
            return null;
        }

        @Override
        public PluginTextFile createTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException {
            return null;
        }

        @Override
        public PluginBinaryFile getBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException, CantCreateFileException {
            return null;
        }

        @Override
        public PluginBinaryFile createBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException {
            return null;
        }

        @Override
        public void deleteTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException, FileNotFoundException {

        }

        @Override
        public void deleteBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException, FileNotFoundException {

        }
    };
    private final CryptoCustomerIdentityManager cryptoCustomerIdentityManager = new CryptoCustomerIdentityManager() {
        @Override
        public List<CryptoCustomerIdentity> getAllCryptoCustomerFromCurrentDeviceUser() throws CantGetCryptoCustomerIdentityException {
            return null;
        }

        @Override
        public CryptoCustomerIdentity createCryptoCustomerIdentity(String alias, byte[] profileImage) throws CantCreateCryptoCustomerIdentityException {
            return null;
        }
    };
    private final CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager = new CustomerBrokerContractPurchaseManager() {
        @Override
        public Collection<CustomerBrokerContractPurchase> getAllCustomerBrokerContractPurchase() throws CantGetListCustomerBrokerContractPurchaseException {
            return null;
        }

        @Override
        public CustomerBrokerContractPurchase getCustomerBrokerContractPurchaseForContractId(String ContractId) throws CantGetListCustomerBrokerContractPurchaseException {
            return null;
        }

        @Override
        public Collection<CustomerBrokerContractPurchase> getCustomerBrokerContractPurchaseForStatus(ContractStatus status) throws CantGetListCustomerBrokerContractPurchaseException {
            return null;
        }

        @Override
        public ListsForStatusPurchase getCustomerBrokerContractHistory() throws CantGetListCustomerBrokerContractPurchaseException {
            return null;
        }

        @Override
        public CustomerBrokerContractPurchase createCustomerBrokerContractPurchase(CustomerBrokerContractPurchase contract) throws CantCreateCustomerBrokerContractPurchaseException {
            return null;
        }

        @Override
        public void updateStatusCustomerBrokerPurchaseContractStatus(String contractId, ContractStatus status) throws CantUpdateCustomerBrokerContractPurchaseException {

        }

        @Override
        public void updateContractNearExpirationDatetime(String contractId, Boolean status) throws CantUpdateCustomerBrokerContractPurchaseException {

        }
    };
    private final CustomerBrokerNewManager customerBrokerNewManager = new CustomerBrokerNewManager() {
        @Override
        public void createCustomerBrokerNewPurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException {

        }

        @Override
        public CustomerBrokerNew getCustomerBrokerNewNegotiationTranasction(UUID transactionId) throws CantGetCustomerBrokerNewNegotiationTransactionException {
            return null;
        }

        @Override
        public List<CustomerBrokerNew> getAllCustomerBrokerNewNegotiationTranasction() throws CantGetListCustomerBrokerNewNegotiationTransactionException {
            return null;
        }
    };
    private final CurrencyExchangeProviderFilterManager currencyExchangeProviderFilterManager = new CurrencyExchangeProviderFilterManager() {
        @Override
        public Map<UUID, String> getProviderNames() throws CantGetProviderInfoException {
            return null;
        }

        @Override
        public Map<UUID, String> getProviderNamesListFromCurrencyPair(CurrencyPair currencyPair) throws CantGetProviderInfoException {
            return null;
        }

        @Override
        public CurrencyExchangeRateProviderManager getProviderReference(UUID providerId) throws CantGetProviderException {
            return null;
        }

        @Override
        public Collection<CurrencyExchangeRateProviderManager> getProviderReferencesFromCurrencyPair(CurrencyPair currencyPair) throws CantGetProviderException {
            return null;
        }
    };
    private final ActorExtraDataManager actorExtraDataManager = new ActorExtraDataManager() {
        @Override
        public void createBrokerExtraData(ActorExtraData actorExtraData) throws CantCreateNewActorExtraDataException {

        }

        @Override
        public void updateBrokerExtraData(ActorExtraData actorExtraData) throws CantUpdateActorExtraDataException {

        }

        @Override
        public Collection<ActorExtraData> getAllActorExtraData() throws CantGetListActorExtraDataException {
            return null;
        }

        @Override
        public Collection<ActorExtraData> getAllActorExtraDataConnected() throws CantGetListActorExtraDataException {
            return null;
        }

        @Override
        public ActorExtraData getActorExtraDataByIdentity(ActorIdentity identity) throws CantGetListActorExtraDataException {
            return null;
        }

        @Override
        public ActorExtraData getActorExtraDataLocalActor() throws CantGetListActorExtraDataException {
            return null;
        }

        @Override
        public Collection<Platforms> getPlatformsSupport(String brokerPublicKey, Currency currency) throws CantGetListPlatformsException {
            return null;
        }

        @Override
        public BrokerIdentityWalletRelationship createNewBrokerIdentityWalletRelationship(ActorIdentity identity, UUID wallet) throws CantCreateNewBrokerIdentityWalletRelationshipException {
            return null;
        }

        @Override
        public Collection<BrokerIdentityWalletRelationship> getAllBrokerIdentityWalletRelationship() throws CantGetListBrokerIdentityWalletRelationshipException {
            return null;
        }

        @Override
        public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByIdentity(ActorIdentity identity) throws CantGetListBrokerIdentityWalletRelationshipException {
            return null;
        }

        @Override
        public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByWallet(UUID wallet) throws CantGetListBrokerIdentityWalletRelationshipException {
            return null;
        }
    };
    private String merchandise = null, typeOfPayment = null, paymentCurrency = null;

    private List<ContractBasicInformation> contractsHistory = new ArrayList<ContractBasicInformation>();
    private List<ContractBasicInformation> openContracts = new ArrayList<ContractBasicInformation>();
    private List<CustomerBrokerNegotiationInformation> openNegotiations = new ArrayList<CustomerBrokerNegotiationInformation>();
    private List<BrokerIdentityBusinessInfo> connectedBrokers = new ArrayList<BrokerIdentityBusinessInfo>();

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        CryptoCustomerWalletModuleCryptoCustomerWalletManager cryptoCustomerWalletModuleCryptoCustomerWalletManager = new CryptoCustomerWalletModuleCryptoCustomerWalletManager(
                this.walletManagerManager,
                this.customerBrokerPurchaseNegotiationManager,
                this.pluginId,
                this.pluginFileSystem,
                this.cryptoCustomerIdentityManager,
                this.customerBrokerContractPurchaseManager,
                this.customerBrokerNewManager,
                this.currencyExchangeProviderFilterManager,
                this.actorExtraDataManager
        );
        assertThat(cryptoCustomerWalletModuleCryptoCustomerWalletManager).isNotNull();
    }

}
