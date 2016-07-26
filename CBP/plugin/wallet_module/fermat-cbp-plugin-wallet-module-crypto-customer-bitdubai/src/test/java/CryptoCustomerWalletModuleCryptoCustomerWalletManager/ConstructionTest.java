package CryptoCustomerWalletModuleCryptoCustomerWalletManager;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by roy on 7/02/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    /*
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
        public void waitForCustomer(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {

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
        public Collection<CustomerBrokerPurchaseNegotiation> getNegotiationsBySendAndWaiting(ActorType actorType) throws CantGetListPurchaseNegotiationsException {
            return null;
        }

        @Override
        public ClauseType getNextClauseType(ClauseType type) throws CantGetNextClauseTypeException {
            return null;
        }

        @Override
        public ClauseType getNextClauseTypeByCurrencyType(MoneyType paymentMethod) throws CantGetNextClauseTypeException {
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
        public CryptoCustomerIdentity createCryptoCustomerIdentity(String alias, byte[] profileImage) throws CantCreateCryptoCustomerIdentityException {
            return null;
        }

        @Override
        public List<CryptoCustomerIdentity> listAllCryptoCustomerFromCurrentDeviceUser() throws CantListCryptoCustomerIdentityException {
            return null;
        }

        @Override
        public void updateCryptoCustomerIdentity(String alias, String publicKey, byte[] imageProfile) throws CantUpdateCustomerIdentityException {

        }

        @Override
        public CryptoCustomerIdentity getCryptoCustomerIdentity(String publicKey) throws CantGetCryptoCustomerIdentityException, IdentityNotFoundException {
            return null;
        }

        @Override
        public void publishIdentity(String publicKey) throws CantPublishIdentityException, IdentityNotFoundException {

        }

        @Override
        public void hideIdentity(String publicKey) throws CantHideIdentityException, IdentityNotFoundException {

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
        public void createCustomerBrokerNewPurchaseNegotiationTransaction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantCreateCustomerBrokerNewPurchaseNegotiationTransactionException {

        }

        @Override
        public CustomerBrokerNew getCustomerBrokerNewNegotiationTransaction(UUID transactionId) throws CantGetCustomerBrokerNewNegotiationTransactionException {
            return null;
        }

        @Override
        public List<CustomerBrokerNew> getAllCustomerBrokerNewNegotiationTransaction() throws CantGetListCustomerBrokerNewNegotiationTransactionException {
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
        public CustomerIdentityWalletRelationship createNewCustomerIdentityWalletRelationship(ActorIdentity identity, String wallet) throws CantCreateNewCustomerIdentityWalletRelationshipException {
            return null;
        }

        @Override
        public Collection<CustomerIdentityWalletRelationship> getAllCustomerIdentityWalletRelationship() throws CantGetCustomerIdentityWalletRelationshipException {
            return null;
        }

        @Override
        public CustomerIdentityWalletRelationship getCustomerIdentityWalletRelationshipByIdentity(String publicKey) throws CantGetCustomerIdentityWalletRelationshipException {
            return null;
        }

        @Override
        public CustomerIdentityWalletRelationship getCustomerIdentityWalletRelationshipByWallet(String walletPublicKey) throws CantGetCustomerIdentityWalletRelationshipException, RelationshipNotFoundException {
            return null;
        }

        @Override
        public void createCustomerExtraData(ActorExtraData actorExtraData) throws CantCreateNewActorExtraDataException {

        }

        @Override
        public void updateCustomerExtraData(ActorExtraData actorExtraData) throws CantUpdateActorExtraDataException {

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
        public ActorExtraData getActorExtraDataByIdentity(String customerPublicKey, String brokerPublicKey) throws CantGetListActorExtraDataException {
            return null;
        }

        @Override
        public ActorIdentity getActorInformationByPublicKey(String publicKeyBroker) throws CantGetListActorExtraDataException {
            return null;
        }

        @Override
        public Collection<Platforms> getPlatformsSupport(String brokerPublicKey, Currency currency) throws CantGetListPlatformsException {
            return null;
        }

        @Override
        public void requestBrokerExtraData(ActorExtraData actorExtraData) throws CantRequestBrokerExtraDataException {

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
    */

}
