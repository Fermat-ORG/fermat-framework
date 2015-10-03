package com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantCreateExtraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantGetExtraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantSetPhotoException;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.ExtraUserNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantGetIntraUsersException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUser;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.DealsWithIntraUsersActor;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionSummary;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.DealsWithCCPIntraWalletUser;

import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUser;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUserManager;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsSearch;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetAllIntraUserConnectionsException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantFindWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetActorTransactionHistoryException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantSaveTransactionDescriptionException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantUpdateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.TransactionNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.ActorTransactionSummary;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserActor;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.DealsWithOutgoingExtraUser;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.OutgoingExtraUserManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.CantGetTransactionManagerException;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.CantSendFundsException;
import com.bitdubai.fermat_api.layer.dmp_actor.Actor;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.DealsWithWalletContacts;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsRegistry;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantEnrichIntraUserException;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantEnrichTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantGetActorException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.interfaces.DealsWithExtraUsers;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.DealsWithCryptoAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantCreateOrRegisterActorException;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantInitializeCryptoWalletManagerException;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantRequestOrRegisterCryptoAddressException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleManager</code>
 * haves all methods for the contacts activity of a bitcoin wallet
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 11/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoWalletWalletModuleManager implements CryptoWallet, DealsWithCCPIntraWalletUser,DealsWithBitcoinWallet, DealsWithCryptoVault, DealsWithErrors, DealsWithExtraUsers, DealsWithIntraUsersActor, DealsWithOutgoingExtraUser, DealsWithWalletContacts, DealsWithCryptoAddressBook {

    /**
     * DealsWithBitcoinWallet Interface member variables.
     */
    private BitcoinWalletManager bitcoinWalletManager;

    /**
     * DealsWithCryptoVault Interface member variables.
     */
    private CryptoVaultManager cryptoVaultManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithExtraUsers Interface member variables.
     */
    private ExtraUserManager extraUserManager;

    /**
     * DealsWithIntraUsersActor Interface member variables.
     */
    private ActorIntraUserManager intraUserManager;


    /**
     * DealsWithCCPIntraWalletUser Interface member variables.
     */

    private IntraWalletUserManager intraWalletUserManager;

    /**
     * DealsWithOutgoingExtraUser Interface member variables.
     */
    private OutgoingExtraUserManager outgoingExtraUserManager;

    /**
     * DealsWithWalletContacts Interface member variable
     */
    private WalletContactsManager walletContactsManager;
    private WalletContactsRegistry walletContactsRegistry;

    /**
     * DealsWithCryptoAddressBook Interface member variable
     */
    private CryptoAddressBookManager cryptoAddressBookManager;


    public void initialize() throws CantInitializeCryptoWalletManagerException {
        try {
            walletContactsRegistry = walletContactsManager.getWalletContactsRegistry();
        } catch (CantGetWalletContactRegistryException e){
            throw new CantInitializeCryptoWalletManagerException(CantInitializeCryptoWalletManagerException.DEFAULT_MESSAGE, e);
        }  catch (Exception e){
            throw new CantInitializeCryptoWalletManagerException(CantInitializeCryptoWalletManagerException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<CryptoWalletWalletContact> listWalletContacts(String walletPublicKey) throws CantGetAllWalletContactsException {
        try {
            List<CryptoWalletWalletContact> finalRecordList = new ArrayList<>();
            finalRecordList.clear();
            WalletContactsSearch walletContactsSearch = walletContactsRegistry.searchWalletContact(walletPublicKey);
            for(WalletContactRecord r : walletContactsSearch.getResult()){
                byte[] image = null;
                switch (r.getActorType()) {
                    case EXTRA_USER:
                        Actor actor = extraUserManager.getActorByPublicKey(r.getActorPublicKey());
                        if (actor != null)
                            image = actor.getPhoto();
                        break;
                    default:
                        throw new CantGetAllWalletContactsException("UNEXPECTED ACTOR TYPE",null,"","incomplete switch");
                }
                finalRecordList.add(new CryptoWalletWalletModuleWalletContact(r, image));
            }
            return  finalRecordList;
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e);
        }  catch (Exception e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<CryptoWalletWalletContact> listWalletContactsScrolling(String  walletPublicKey,
                                                                       Integer max,
                                                                       Integer offset) throws CantGetAllWalletContactsException {
        try {
            List<CryptoWalletWalletContact> finalRecordList = new ArrayList<>();
            finalRecordList.clear();
            WalletContactsSearch walletContactsSearch = walletContactsRegistry.searchWalletContact(walletPublicKey);
            for(WalletContactRecord r : walletContactsSearch.getResult(max, offset)){
                byte[] image = null;
                switch (r.getActorType()) {
                    case EXTRA_USER:
                        Actor actor = extraUserManager.getActorByPublicKey(r.getActorPublicKey());
                        if (actor != null)
                            image = actor.getPhoto();
                        break;
                    default:
                        throw new CantGetAllWalletContactsException("UNEXPECTED ACTOR TYPE",null,"","incomplete switch");
                }
                finalRecordList.add(new CryptoWalletWalletModuleWalletContact(r, image));
            }
            return  finalRecordList;
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e);
        } catch (Exception e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<CryptoWalletIntraUserActor> listAllIntraUserConnections(String  intraUserSelectedPublicKey,
                                                                        String  walletPublicKey,
                                                                        Integer max,
                                                                        Integer offset) throws CantGetAllIntraUserConnectionsException {
        try {
            List<CryptoWalletIntraUserActor> intraUserActorList = new ArrayList<>();

            List<ActorIntraUser> intraUserList = intraUserManager.getAllIntraUsers(intraUserSelectedPublicKey, max, offset);

            for(ActorIntraUser intraUser : intraUserList)
                intraUserActorList.add(enrichIntraUser(intraUser, walletPublicKey));

            return intraUserActorList;
        } catch (CantGetIntraUsersException e) {
            throw new CantGetAllIntraUserConnectionsException(CantGetAllIntraUserConnectionsException.DEFAULT_MESSAGE, e, "", "Problem trying yo get actors from Intra-User Actor plugin.");
        } catch (CantEnrichIntraUserException e) {
            throw new CantGetAllIntraUserConnectionsException(CantGetAllIntraUserConnectionsException.DEFAULT_MESSAGE, e, "", "Problem trying to enrich Intra-Users.");
        } catch (Exception e) {
            throw new CantGetAllIntraUserConnectionsException(CantGetAllIntraUserConnectionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    private CryptoWalletIntraUserActor enrichIntraUser(ActorIntraUser actorIntraUser,
                                                       String walletPublicKey) throws CantEnrichIntraUserException {
        try {
            walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(actorIntraUser.getPublicKey(), walletPublicKey);

            return new CryptoWalletWalletModuleIntraUserActor(
                    actorIntraUser.getName(),
                    true,
                    actorIntraUser.getProfileImage(),
                    actorIntraUser.getPublicKey()
            );
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
            return new CryptoWalletWalletModuleIntraUserActor(
                    actorIntraUser.getName(),
                    false,
                    actorIntraUser.getProfileImage(),
                    actorIntraUser.getPublicKey()
                    );
        } catch (CantGetWalletContactException e) {
            throw new CantEnrichIntraUserException(CantEnrichIntraUserException.DEFAULT_MESSAGE, e, "", "There was a problem trying to enrich the intra user record.");
        }
    }

    @Override
    public CryptoWalletWalletContact createWalletContactWithPhoto(CryptoAddress receivedCryptoAddress,
                                                                  String        actorAlias,
                                                                  String        actorFirstName,
                                                                  String        actorLastName,
                                                                  Actors        actorType,
                                                                  String        walletPublicKey,
                                                                  byte[]        photo) throws CantCreateWalletContactException, ContactNameAlreadyExistsException {
        try{
            try {
                walletContactsRegistry.getWalletContactByAliasAndWalletPublicKey(actorAlias, walletPublicKey);
                throw new ContactNameAlreadyExistsException(ContactNameAlreadyExistsException.DEFAULT_MESSAGE, null, null, null);

            } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
                String actorPublicKey = createActor(actorAlias, actorType, photo);
                List<CryptoAddress> cryptoAddresses = new ArrayList<>();
                cryptoAddresses.add(receivedCryptoAddress);
                WalletContactRecord walletContactRecord = walletContactsRegistry.createWalletContact(
                        actorPublicKey,
                        actorAlias,
                        actorFirstName,
                        actorLastName,
                        actorType,
                        cryptoAddresses,
                        walletPublicKey
                );
                return new CryptoWalletWalletModuleWalletContact(walletContactRecord, photo);
            }

        } catch (ContactNameAlreadyExistsException e) {
            throw e;
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e);
        } catch (CantCreateOrRegisterActorException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "Error creating or registering actor.", null);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "Error creation a wallet contact.", null);
        } catch (Exception e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public CryptoWalletWalletContact createWalletContact(CryptoAddress receivedCryptoAddress,
                                                         String        actorAlias,
                                                         String        actorFirstName,
                                                         String        actorLastName,
                                                         Actors        actorType,
                                                         String        walletPublicKey) throws CantCreateWalletContactException, ContactNameAlreadyExistsException {
        try{
            try {
                walletContactsRegistry.getWalletContactByAliasAndWalletPublicKey(actorAlias, walletPublicKey);
                throw new ContactNameAlreadyExistsException(ContactNameAlreadyExistsException.DEFAULT_MESSAGE, null, null, null);

            } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {

                String actorPublicKey = createActor(actorAlias, actorType);
                List<CryptoAddress> cryptoAddresses = new ArrayList<>();
                cryptoAddresses.add(receivedCryptoAddress);
                WalletContactRecord walletContactRecord = walletContactsRegistry.createWalletContact(
                        actorPublicKey,
                        actorAlias,
                        actorFirstName,
                        actorLastName,
                        actorType,
                        cryptoAddresses,
                        walletPublicKey
                );
                return new CryptoWalletWalletModuleWalletContact(walletContactRecord);
            }

        } catch (ContactNameAlreadyExistsException e) {
            throw e;
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e);
        } catch (CantCreateOrRegisterActorException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "Error creating or registering actor.", null);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "Error creation a wallet contact.", null);
        } catch (Exception e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void updateContactPhoto(String actorPublicKey, Actors actor, byte[] photo) throws CantUpdateWalletContactException {
        try {
            switch (actor) {
                case EXTRA_USER:
                    this.extraUserManager.setPhoto(actorPublicKey, photo);
                    break;
                default:
                    throw new CantUpdateWalletContactException("Actor not expected", null, "The actor type is:" + actor.getCode(), "Incomplete switch");
            }
        } catch (ExtraUserNotFoundException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, null, "The actor type is:" + actor.getCode(), " i cannot find the actor ");
        } catch (CantSetPhotoException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, null, "The actor type is:" + actor.getCode(), " error trying to get the actor photo");
        }
    }

    @Override
    public CryptoWalletWalletContact findWalletContactById(UUID contactId) throws CantFindWalletContactException, WalletContactNotFoundException {
        try {
            WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByContactId(contactId);
            byte[] image = null;
            switch (walletContactRecord.getActorType()) {
                case EXTRA_USER:
                    Actor actor = extraUserManager.getActorByPublicKey(walletContactRecord.getActorPublicKey());
                    if (actor != null)
                        image = actor.getPhoto();
                    break;
                default:
                    throw new CantGetAllWalletContactsException("UNEXPECTED ACTOR TYPE",null,"","incomplete switch");
            }
            return new CryptoWalletWalletModuleWalletContact(walletContactRecord, image);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantFindWalletContactException(CantFindWalletContactException.DEFAULT_MESSAGE, e);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
            throw new WalletContactNotFoundException(WalletContactNotFoundException.DEFAULT_MESSAGE, e);
        } catch (Exception e) {
            throw new CantFindWalletContactException(CantFindWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public CryptoWalletWalletContact addIntraUserActorLikeContact(String intraUserPublicKey,
                                                                  String alias,
                                                                  String firstName,
                                                                  String lastName,
                                                                  String walletPublicKey) throws CantCreateWalletContactException {
        try {
            WalletContactRecord walletContactRecord = walletContactsRegistry.createWalletContact(
                    intraUserPublicKey,
                    alias,
                    firstName,
                    lastName,
                    Actors.INTRA_USER,
                    null,
                    walletPublicKey
            );

            return new CryptoWalletWalletModuleWalletContact(walletContactRecord);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException e) {
            throw new CantCreateWalletContactException();
        } catch (Exception e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public CryptoAddress requestAddressToNewExtraUser(String deliveredByActorPublicKey,
                                                      Actors deliveredByActorType,
                                                      String deliveredToActorName,
                                                      Platforms platform,
                                                      VaultType vaultType,
                                                      String vaultIdentifier,
                                                      String walletPublicKey,
                                                      ReferenceWallet walletType) throws CantRequestCryptoAddressException {
        // TODO implement this method
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public CryptoAddress requestAddressToKnownUser(String deliveredByActorPublicKey,
                                        Actors deliveredByActorType,
                                        String deliveredToActorPublicKey,
                                        Actors deliveredToActorType,
                                        Platforms platform,
                                        VaultType vaultType,
                                        String vaultIdentifier,
                                        String walletPublicKey,
                                        ReferenceWallet walletType) throws CantRequestCryptoAddressException {
        try {
            CryptoAddress deliveredCryptoAddress;
            deliveredCryptoAddress = requestCryptoAddressByReferenceWallet(walletType);
            cryptoAddressBookManager.registerCryptoAddress(
                    deliveredCryptoAddress,
                    deliveredByActorPublicKey,
                    deliveredByActorType,
                    deliveredToActorPublicKey,
                    deliveredToActorType,
                    platform,
                    vaultType,
                    vaultIdentifier,
                    walletPublicKey,
                    walletType
            );
            System.out.println("im a delivered address: " + deliveredCryptoAddress.getAddress());
            return deliveredCryptoAddress;
        } catch (CantRequestOrRegisterCryptoAddressException e) {
            throw new CantRequestCryptoAddressException(CantRequestCryptoAddressException.DEFAULT_MESSAGE, e);
        } catch (CantRegisterCryptoAddressBookRecordException e) {
            throw new CantRequestCryptoAddressException(CantRequestCryptoAddressException.DEFAULT_MESSAGE, e, "", "Check if all the params are sended.");
        } catch(Exception exception){
            throw new CantRequestCryptoAddressException(CantRequestCryptoAddressException.DEFAULT_MESSAGE, FermatException.wrapException(exception));
        }
    }

    @Override
    public void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName) throws CantUpdateWalletContactException {
        try {
            List<CryptoAddress> cryptoAddresses = new ArrayList<>();
            cryptoAddresses.add(receivedCryptoAddress);
            walletContactsRegistry.updateWalletContact(
                    contactId,
                    actorName,
                    null,
                    null,
                    cryptoAddresses
            );
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantUpdateWalletContactException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, e);
        }  catch (Exception e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException {
        try {
            walletContactsRegistry.deleteWalletContact(contactId);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantDeleteWalletContactException e) {
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, e);
        } catch (Exception e) {
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public long getBalance(BalanceType balanceType,
                           String walletPublicKey) throws CantGetBalanceException {
        try {
            BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            return bitcoinWalletWallet.getBalance(balanceType).getBalance();
        } catch (CantLoadWalletException e) {
            throw new CantGetBalanceException(CantGetBalanceException.DEFAULT_MESSAGE, e, "", "Cant Load Wallet.");
        }  catch (CantCalculateBalanceException e) {
            throw new CantGetBalanceException(CantGetBalanceException.DEFAULT_MESSAGE, e, "", "Cant Calculate Balance of the wallet.");
        } catch(Exception e){
            throw new CantGetBalanceException(CantGetBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<CryptoWalletTransaction> getTransactions(BalanceType balanceType,TransactionType transactionType,
                                                         String walletPublicKey,
                                                         int max,
                                                         int offset) throws CantListTransactionsException {
        try {
            BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            List<CryptoWalletTransaction> cryptoWalletTransactionList = new ArrayList<>();
            List<BitcoinWalletTransaction> bitcoinWalletTransactionList = bitcoinWalletWallet.listTransactions(balanceType, transactionType, max, offset);

            for (BitcoinWalletTransaction bwt : bitcoinWalletTransactionList) {
                cryptoWalletTransactionList.add(enrichTransaction(bwt));
            }

            return cryptoWalletTransactionList;
        } catch (CantLoadWalletException | com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantListTransactionsException e) {
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<CryptoWalletTransaction> listTransactionsByActor(BalanceType balanceType,
                                                                 String walletPublicKey,
                                                                 String actorPublicKey,
                                                                 int max,
                                                                 int offset) throws CantListTransactionsException {
        try {
            BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            List<CryptoWalletTransaction> cryptoWalletTransactionList = new ArrayList<>();
            List<BitcoinWalletTransaction> bitcoinWalletTransactionList = bitcoinWalletWallet.listTransactionsByActor(actorPublicKey, balanceType, max, offset);

            for (BitcoinWalletTransaction bwt : bitcoinWalletTransactionList) {
                cryptoWalletTransactionList.add(enrichTransaction(bwt));
            }

            return cryptoWalletTransactionList;
        } catch (CantLoadWalletException | com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantListTransactionsException e) {
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public ActorTransactionSummary getActorTransactionHistory(BalanceType balanceType,
                                                              String walletPublicKey,
                                                              String actorPublicKey) throws CantGetActorTransactionHistoryException {
        try {
            BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            return constructActorTransactionSummary(bitcoinWalletWallet.getActorTransactionSummary(actorPublicKey, balanceType));
        } catch (CantLoadWalletException | CantGetActorTransactionSummaryException e) {
            throw new CantGetActorTransactionHistoryException(CantGetActorTransactionHistoryException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantGetActorTransactionHistoryException(CantGetActorTransactionHistoryException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }


    @Override
    public List<CryptoWalletTransaction> listLastActorTransactionsByTransactionType(BalanceType     balanceType,
                                                                                    TransactionType transactionType,
                                                                                    String          walletPublicKey,
                                                                                    int             max,
                                                                                    int             offset) throws CantListTransactionsException {

        try {
            BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            List<CryptoWalletTransaction> cryptoWalletTransactionList = new ArrayList<>();
            List<BitcoinWalletTransaction> bitcoinWalletTransactionList = bitcoinWalletWallet.listLastActorTransactionsByTransactionType(
                    balanceType,
                    transactionType,
                    max,
                    offset
            );

            for (BitcoinWalletTransaction bwt : bitcoinWalletTransactionList) {
                cryptoWalletTransactionList.add(enrichTransaction(bwt));
            }

            return cryptoWalletTransactionList;
        } catch (CantLoadWalletException | com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantListTransactionsException e) {
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void setTransactionDescription(String walletPublicKey,
                                          UUID   transactionID,
                                          String description) throws CantSaveTransactionDescriptionException, TransactionNotFoundException {

        try {
            BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            bitcoinWalletWallet.setTransactionDescription(transactionID, description);
        } catch (CantLoadWalletException | CantStoreMemoException e) {
            throw new CantSaveTransactionDescriptionException(CantSaveTransactionDescriptionException.DEFAULT_MESSAGE, e);
        } catch (CantFindTransactionException e) {
            throw new TransactionNotFoundException(TransactionNotFoundException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantSaveTransactionDescriptionException(CantSaveTransactionDescriptionException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    private ActorTransactionSummary constructActorTransactionSummary(BitcoinWalletTransactionSummary transactionSummary) {
        return new CryptoWalletWalletModuleActorTransactionSummary(
                transactionSummary.getSentTransactionsNumber(),
                transactionSummary.getReceivedTransactionsNumber(),
                transactionSummary.getSentAmount(),
                transactionSummary.getReceivedAmount()
        );
    }

    @Override
    public void send(long cryptoAmount, CryptoAddress destinationAddress, String notes, String walletPublicKey, String deliveredByActorPublicKey, Actors deliveredByActorType, String deliveredToActorPublicKey, Actors deliveredToActorType) throws CantSendCryptoException, InsufficientFundsException {
        try {
            outgoingExtraUserManager.getTransactionManager().send(walletPublicKey, destinationAddress, cryptoAmount, notes, deliveredByActorPublicKey, deliveredByActorType, deliveredToActorPublicKey, deliveredToActorType);
        } catch (com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.InsufficientFundsException e) {
            throw new InsufficientFundsException(InsufficientFundsException.DEFAULT_MESSAGE, e);
        } catch (CantSendFundsException | CantGetTransactionManagerException e) {
            throw new CantSendCryptoException(CantSendCryptoException.DEFAULT_MESSAGE, e);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantSendCryptoException(CantSendCryptoException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<PaymentRequest> listSentPaymentRequest() {
        List<PaymentRequest> lst =  new ArrayList<PaymentRequest>();

        CryptoWalletWalletModulePaymentRequest cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("1 hour ago","Starbucks coffe",500000,null,PaymentRequest.SEND_PAYMENT,"accepted");
        lst.add(cryptoWalletPaymentRequest);
        cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("2 hour ago","Hamburguer from MC donald",100000,null,PaymentRequest.SEND_PAYMENT,"accepted");

        lst.add(cryptoWalletPaymentRequest);

        return lst;
    }

    @Override
    public List<PaymentRequest> listReceivedPaymentRequest() {
        List<PaymentRequest> lst =  new ArrayList<>();

        CryptoWalletWalletModulePaymentRequest cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("1 hour ago","Starbucks coffe",500000,null,PaymentRequest.RECEIVE_PAYMENT,"accepted");
        lst.add(cryptoWalletPaymentRequest);
        cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("2 hour ago","Hamburguer from MC donald",100000,null,PaymentRequest.RECEIVE_PAYMENT,"accepted");

        lst.add(cryptoWalletPaymentRequest);

        return lst;
    }

    @Override
    public List<PaymentRequest> listPaymentRequestDatOrder() {
        List<PaymentRequest> lst =  new ArrayList<>();
        CryptoWalletWalletModulePaymentRequest cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("1 hour ago","Starbucks coffe",500000,null,PaymentRequest.SEND_PAYMENT,"accepted");
        lst.add(cryptoWalletPaymentRequest);
        cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("1 hour ago","Hamburguer from MC donald",100000,null,PaymentRequest.SEND_PAYMENT,"accepted");
        lst.add(cryptoWalletPaymentRequest);

        cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("1 hour ago","Starbucks coffe",500000,null,PaymentRequest.RECEIVE_PAYMENT,"accepted");
        lst.add(cryptoWalletPaymentRequest);
        cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("2 hour ago","Hamburguer from MC donald",100000,null,PaymentRequest.RECEIVE_PAYMENT,"accepted");
        lst.add(cryptoWalletPaymentRequest);


        cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("2 hour ago","Starbucks coffe",500000,null,PaymentRequest.RECEIVE_PAYMENT,"accepted");
        lst.add(cryptoWalletPaymentRequest);
        cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("3 hour ago","Hamburguer from MC donald",100000,null,PaymentRequest.RECEIVE_PAYMENT,"accepted");
        lst.add(cryptoWalletPaymentRequest);

        cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("3 hour ago","Starbucks coffe",500000,null,PaymentRequest.SEND_PAYMENT,"accepted");
        lst.add(cryptoWalletPaymentRequest);
        cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest("4 hour ago","Hamburguer from MC donald",100000,null,PaymentRequest.SEND_PAYMENT,"accepted");
        lst.add(cryptoWalletPaymentRequest);

        return lst;
    }

    @Override
    public boolean isValidAddress(CryptoAddress cryptoAddress) {
        return cryptoVaultManager.isValidAddress(cryptoAddress);
    }

    @Override
    public List<CryptoWalletIntraUserIdentity> getAllIntraWalletUsersFromCurrentDeviceUser() throws CantListCryptoWalletIntraUserIdentityException {

        try {

            List<CryptoWalletIntraUserIdentity> cryptoWalletIntraUserIdentityList = new  ArrayList<CryptoWalletIntraUserIdentity>();

            for (IntraWalletUser  intraWalletUser : this.intraWalletUserManager.getAllIntraWalletUsersFromCurrentDeviceUser()) {

                CryptoWalletIntraUserIdentity cryptoWalletIntraUserIdentity = new CryptoWalletWalletIntraUserIdentity(intraWalletUser.getPublicKey(),intraWalletUser.getAlias(),intraWalletUser.getProfileImage());

                cryptoWalletIntraUserIdentityList.add(cryptoWalletIntraUserIdentity);
            }

            //TODO Harcoder
            cryptoWalletIntraUserIdentityList.add(new CryptoWalletWalletIntraUserIdentity("afd0647a-87de-4c56-9bc9-be736e0c5059", "mati", new byte[0]));


            return cryptoWalletIntraUserIdentityList;

        }
        catch(CantListIntraWalletUsersException e)
        {
            throw new CantListCryptoWalletIntraUserIdentityException(CantListIntraWalletUsersException.DEFAULT_MESSAGE,e,"","");
        }
        catch(Exception e)
        {
            throw new CantListCryptoWalletIntraUserIdentityException(CantListIntraWalletUsersException.DEFAULT_MESSAGE,e,"","unknown error");
        }

    }



    private String createActor(String actorName, Actors actorType, byte[] photo) throws CantCreateOrRegisterActorException {
        switch (actorType){
            case EXTRA_USER:
                try {
                    Actor actor = extraUserManager.createActor(actorName, photo);
                    return actor.getActorPublicKey();
                } catch (CantCreateExtraUserException e) {
                    throw new CantCreateOrRegisterActorException(CantCreateOrRegisterActorException.DEFAULT_MESSAGE, e, "", "Check if all the params are sended.");
                }
            default:
                throw new CantCreateOrRegisterActorException(CantCreateOrRegisterActorException.DEFAULT_MESSAGE, null, "", "ActorType is not Compatible.");
        }
    }


    private String createActor(String actorName, Actors actorType) throws CantCreateOrRegisterActorException {
        switch (actorType){
            case EXTRA_USER:
                try {
                    Actor actor = extraUserManager.createActor(actorName);
                    return actor.getActorPublicKey();
                } catch (CantCreateExtraUserException e) {
                    throw new CantCreateOrRegisterActorException(CantCreateOrRegisterActorException.DEFAULT_MESSAGE, e, "", "Check if all the params are sended.");
                }
            default:
                throw new CantCreateOrRegisterActorException(CantCreateOrRegisterActorException.DEFAULT_MESSAGE, null, "", "ActorType is not Compatible.");
        }
    }

    private CryptoAddress requestCryptoAddressByReferenceWallet(ReferenceWallet referenceWallet) throws CantRequestOrRegisterCryptoAddressException {
        switch (referenceWallet){
            case BASIC_WALLET_BITCOIN_WALLET:
                return cryptoVaultManager.getAddress();
            default:
                throw new CantRequestOrRegisterCryptoAddressException(CantRequestOrRegisterCryptoAddressException.DEFAULT_MESSAGE, null, "", "ReferenceWallet is not Compatible.");
        }
    }

    private CryptoWalletTransaction enrichTransaction(BitcoinWalletTransaction bitcoinWalletTransaction) throws CantEnrichTransactionException {
        try {
            Actor involvedActor = null;
            UUID contactId = null;
            switch (bitcoinWalletTransaction.getTransactionType()) {
                case CREDIT:
                    try {
                        involvedActor = getActorByActorPublicKeyAndType(bitcoinWalletTransaction.getActorToPublicKey(), bitcoinWalletTransaction.getActorToType());
                        WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(bitcoinWalletTransaction.getActorToPublicKey(), null);
                        if (walletContactRecord != null)
                            contactId = walletContactRecord.getContactId();

                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
                        throw new CantEnrichTransactionException(CantEnrichTransactionException.DEFAULT_MESSAGE, e, "Cant get Contact Information", "");
                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
                        contactId = null;
                    }
                    break;
                case DEBIT:
                    try {
                        involvedActor = getActorByActorPublicKeyAndType(bitcoinWalletTransaction.getActorFromPublicKey(), bitcoinWalletTransaction.getActorFromType());
                        WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(bitcoinWalletTransaction.getActorFromPublicKey(), null);
                        if (walletContactRecord != null)
                            contactId = walletContactRecord.getContactId();

                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
                        throw new CantEnrichTransactionException(CantEnrichTransactionException.DEFAULT_MESSAGE, e, "Cant get Contact Information", "");
                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
                        contactId = null;
                    }
                    break;
            }
            return new CryptoWalletWalletModuleTransaction(bitcoinWalletTransaction, contactId, involvedActor);
        } catch (Exception e) {
            throw new CantEnrichTransactionException(CantEnrichTransactionException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    private Actor getActorByActorPublicKeyAndType(String actorPublicKey, Actors actorType) throws CantGetActorException {
        Actor actor;
        switch (actorType) {
            case EXTRA_USER:
                try {
                    actor = extraUserManager.getActorByPublicKey(actorPublicKey);
                    return actor;
                } catch (CantGetExtraUserException | ExtraUserNotFoundException e) {
                    throw new CantGetActorException(CantGetActorException.DEFAULT_MESSAGE, e, null, null);
                }
            default:
                throw new CantGetActorException(CantGetActorException.DEFAULT_MESSAGE, null, null, null);
        }
    }


    /**
     * DealsWithBitcoinWallet Interface implementation.
     */
    @Override
    public void setBitcoinWalletManager(BitcoinWalletManager bitcoinWalletManager) {
        this.bitcoinWalletManager = bitcoinWalletManager;
    }

    /**
     * DealsWithCryptoVault Interface implementation.
     */
    @Override
    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager = cryptoVaultManager;
    }

    /**
     * DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithExtraUsers Interface implementation.
     */
    @Override
    public void setExtraUserManager(ExtraUserManager extraUserManager) {
        this.extraUserManager = extraUserManager;
    }

    @Override
    public void setActorIntraUserManager(ActorIntraUserManager actorIntraUserManager) {
        this.intraUserManager = actorIntraUserManager;
    }

    /**
     * DealsWithOutgoingExtraUser Interface implementation.
     */
    @Override
    public void setOutgoingExtraUserManager(OutgoingExtraUserManager outgoingExtraUserManager) {
        this.outgoingExtraUserManager = outgoingExtraUserManager;
    }

    /**
     * DealsWithWalletContacts Interface implementation.
     */
    @Override
    public void setWalletContactsManager(WalletContactsManager walletContactsManager) {
        this.walletContactsManager = walletContactsManager;
    }

    /**
     * DealsWithCryptoAddressBook Interface implementation.
     */
    @Override
    public void setCryptoAddressBookManager(CryptoAddressBookManager cryptoAddressBookManager) {
        this.cryptoAddressBookManager = cryptoAddressBookManager;
    }

    @Override
    public void setIntraUserManager(IntraWalletUserManager intraWalletUserManager) {
        this.intraWalletUserManager = intraWalletUserManager;
    }
}
