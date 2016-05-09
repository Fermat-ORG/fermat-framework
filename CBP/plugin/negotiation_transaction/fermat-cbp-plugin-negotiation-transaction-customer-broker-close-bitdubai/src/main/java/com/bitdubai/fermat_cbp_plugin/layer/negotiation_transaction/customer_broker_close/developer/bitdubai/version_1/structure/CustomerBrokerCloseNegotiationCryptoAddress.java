package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantCryptoAddressesNewException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerCloseCryptoAddressRequest;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils.CryptoVaultSelector;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils.WalletManagerSelector;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantAddClauseNegotiationException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantAddCryptoAddressNegotiationException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantDetermineCryptoCurrencyException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantGetGenerateCryptoAddressException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantGetRequestCryptoAddressException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantNegotiationAddCryptoAdreessException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Yordin Alayn on 28.12.15.
 */
public class CustomerBrokerCloseNegotiationCryptoAddress {

    /*Represent Address Book Manager*/
    private CryptoAddressBookManager    cryptoAddressBookManager;

    /*Represent Vault Manager*/
    private CryptoVaultManager          cryptoVaultManager;

    /*Represent Wallet Manager*/
    private WalletManagerManager        walletManagerManager;

    /*Represent the Error Manager*/
    private ErrorManager                errorManager;

    /*Represent the Plugins Version*/
    private PluginVersionReference      pluginVersionReference;

    private  IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    public CustomerBrokerCloseNegotiationCryptoAddress(
        CryptoAddressBookManager    cryptoAddressBookManager,
        CryptoVaultManager          cryptoVaultManager,
        WalletManagerManager        walletManagerManager,
        ErrorManager                errorManager,
        PluginVersionReference      pluginVersionReference,
        IntraWalletUserIdentityManager intraWalletUserIdentityManager
    ){
        this.cryptoAddressBookManager   = cryptoAddressBookManager;
        this.cryptoVaultManager         = cryptoVaultManager;
        this.walletManagerManager       = walletManagerManager;
        this.errorManager               = errorManager;
        this.pluginVersionReference     = pluginVersionReference;
        this.intraWalletUserIdentityManager = intraWalletUserIdentityManager;
    }

    //UPDATE THE PURCHASE NEGOTIATION FOR ADD NEW CRYPTO ADDRESS
    public CustomerBrokerPurchaseNegotiation getNegotiationAddCryptoAdreess(CustomerBrokerPurchaseNegotiation negotiation) throws CantNegotiationAddCryptoAdreessException {

        try {

            System.out.print("\n\n**** 3.1.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. ADD CRYPTO ADDRESS ****\n");

            CustomerBrokerPurchaseNegotiation newNegotiation = negotiation;

            if (isCryptoCurrency(negotiation.getClauses(),ClauseType.BROKER_PAYMENT_METHOD)) {

                CustomerBrokerCloseCryptoAddressRequest request             = getRequest(negotiation);
                Collection<Clause>                      negotiationClauses  = addCryptoAdreess(negotiation.getClauses(), request, ClauseType.CUSTOMER_CRYPTO_ADDRESS);

                newNegotiation = new CustomerBrokerPurchaseNegotiationImpl(
                    negotiation.getNegotiationId(),
                    negotiation.getCustomerPublicKey(),
                    negotiation.getBrokerPublicKey(),
                    negotiation.getStartDate(),
                    negotiation.getNegotiationExpirationDate(),
                    negotiation.getStatus(),
                    negotiation.getNearExpirationDatetime(),
                    negotiationClauses,
                    negotiation.getLastNegotiationUpdateDate(),
                    negotiation.getCancelReason(),
                    negotiation.getMemo()
                );

            }

            return newNegotiation;

        } catch (CantGetListClauseException e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantNegotiationAddCryptoAdreessException(e.getMessage(),e, CantNegotiationAddCryptoAdreessException.DEFAULT_MESSAGE, "ERROR ADD CRYPTO ADREESS AN PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantNegotiationAddCryptoAdreessException(e.getMessage(), FermatException.wrapException(e), CantNegotiationAddCryptoAdreessException.DEFAULT_MESSAGE, "ERROR ADD CRYPTO ADREESS AN PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }

    }

    //UPDATE THE SALE NEGOTIATION FOR ADD NEW CRYPTO ADDRESS
    public CustomerBrokerSaleNegotiation getNegotiationAddCryptoAdreess(CustomerBrokerSaleNegotiation negotiation) throws CantNegotiationAddCryptoAdreessException {

        try {

            System.out.print("\n\n**** 3.1.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - SALE NEGOTIATION - CUSTOMER BROKER CLOSE SALE NEGOTIATION TRANSACTION. ADD CRYPTO ADDRESS ****\n");

            CustomerBrokerSaleNegotiation newNegotiation = negotiation;

            if (isCryptoCurrency(negotiation.getClauses(),ClauseType.CUSTOMER_PAYMENT_METHOD)) {

                CustomerBrokerCloseCryptoAddressRequest request             = getRequest(negotiation);
                Collection<Clause>                      negotiationClauses  = addCryptoAdreess(negotiation.getClauses(), request, ClauseType.BROKER_CRYPTO_ADDRESS);

                newNegotiation = new CustomerBrokerSaleNegotiationImpl(
                    negotiation.getNegotiationId(),
                    negotiation.getCustomerPublicKey(),
                    negotiation.getBrokerPublicKey(),
                    negotiation.getStartDate(),
                    negotiation.getNegotiationExpirationDate(),
                    negotiation.getStatus(),
                    negotiation.getNearExpirationDatetime(),
                    negotiationClauses,
                    negotiation.getLastNegotiationUpdateDate(),
                    negotiation.getCancelReason(),
                    negotiation.getMemo()
                );

            }

            return newNegotiation;
        } catch (CantGetListClauseException e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantNegotiationAddCryptoAdreessException(e.getMessage(),e, CantNegotiationAddCryptoAdreessException.DEFAULT_MESSAGE, "ERROR ADD CRYPTO ADREESS AN SALE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantNegotiationAddCryptoAdreessException(e.getMessage(), FermatException.wrapException(e), CantNegotiationAddCryptoAdreessException.DEFAULT_MESSAGE, "ERROR ADD CRYPTO ADREESS AN SALE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //CHECK IF IS CRYPTO CURRENCY
    public boolean isCryptoCurrency(Collection<Clause> negotiationClauses, ClauseType paymentMethod) throws CantDetermineCryptoCurrencyException {

        try {

            for (Clause clause : negotiationClauses) {
                if (clause.getType().equals(paymentMethod)) {
                    if (clause.getValue().equals(MoneyType.CRYPTO.getCode())) {
                        return true;
                    }
                }
            }
            
            return false;

        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantDetermineCryptoCurrencyException(e.getMessage(), FermatException.wrapException(e), CantDetermineCryptoCurrencyException.DEFAULT_MESSAGE, "ERROR DETERMINE CRYPTO CURRENCY, UNKNOWN FAILURE.");
        }

    }

    //ADD NEW CRYPTO ADDRESS A THE CLAUSES
    private Collection<Clause> addCryptoAdreess(
        Collection<Clause> negotiationClauses,
        CustomerBrokerCloseCryptoAddressRequest request,
        ClauseType cryptoAddressType)
    throws CantAddCryptoAddressNegotiationException{

        try {

            System.out.print("\n\n**** 3.1.1.2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. ADD CRYPTO ADDRESS ****\n");

            Collection<Clause> negotiationClausesNew = new ArrayList<>();

            for (Clause clause : negotiationClauses) {
                if (clause.getType() == cryptoAddressType) {
                    negotiationClausesNew.add(
                            addClause(clause, cryptoAdreessActor(request))
                    );
                } else {
                    negotiationClausesNew.add(
                            addClause(clause, clause.getValue())
                    );
                }
            }

            return negotiationClausesNew;

        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantAddCryptoAddressNegotiationException(e.getMessage(), FermatException.wrapException(e), CantAddCryptoAddressNegotiationException.DEFAULT_MESSAGE, "ERROR ADD CRYPTO ADDRESS IN THE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //ADD VALUES A THE CLAUSES
    private Clause addClause(Clause clause, String value) throws CantAddClauseNegotiationException{

        try {

            Clause newClause = new CustomerBrokerNegotiationClauseImpl(
                clause.getClauseId(),
                clause.getType(),
                value,
                clause.getStatus(),
                clause.getProposedBy(),
                clause.getIndexOrder()
            );

            return newClause;

        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantAddClauseNegotiationException(e.getMessage(), FermatException.wrapException(e), CantAddClauseNegotiationException.DEFAULT_MESSAGE, "ERROR ADD CLAUSE IN THE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //RETURN PURCHASE REQUEST THE CRYPTO ADDRESS
    private CustomerBrokerCloseCryptoAddressRequest getRequest(CustomerBrokerPurchaseNegotiation negotiation) throws CantGetRequestCryptoAddressException{
        IntraWalletUserIdentity intraUser;
        try {
            if(intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser().isEmpty()){
                throw new CantGetRequestCryptoAddressException(CantGetRequestCryptoAddressException.DEFAULT_MESSAGE, null, CantGetRequestCryptoAddressException.DEFAULT_MESSAGE, "ERROR GET REQUEST THE CRYPTO ADDRESS IN THE NEGOTIATION, UNKNOWN FAILURE.");
            }else{
                intraUser= intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser().get(0);
            }

            System.out.print("\n\n**** 3.1.1.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. GET REQUEST ****\n");
            CustomerBrokerCloseCryptoAddressRequest request = new CustomerBrokerCloseCryptoAddressRequestImpl(
                Actors.INTRA_USER,
                Actors.CBP_CRYPTO_CUSTOMER,
                intraUser.getPublicKey(),
                negotiation.getCustomerPublicKey(),
                CryptoCurrency.BITCOIN,
                BlockchainNetworkType.getDefaultBlockchainNetworkType()
            );

            return request;

        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantGetRequestCryptoAddressException(e.getMessage(), FermatException.wrapException(e), CantGetRequestCryptoAddressException.DEFAULT_MESSAGE, "ERROR GET REQUEST THE CRYPTO ADDRESS IN THE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //RETURN SALE REQUEST THE CRYPTO ADDRESS
    private CustomerBrokerCloseCryptoAddressRequest getRequest(CustomerBrokerSaleNegotiation negotiation) throws CantGetRequestCryptoAddressException{
        IntraWalletUserIdentity intraUser;
        try {

            if(intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser().isEmpty()){
                throw new CantGetRequestCryptoAddressException(CantGetRequestCryptoAddressException.DEFAULT_MESSAGE, null, CantGetRequestCryptoAddressException.DEFAULT_MESSAGE, "ERROR GET REQUEST THE CRYPTO ADDRESS IN THE NEGOTIATION, UNKNOWN FAILURE.");
            }else{
                intraUser= intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser().get(0);
            }
            System.out.print("\n\n**** 3.1.1.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - SALE NEGOTIATION - CUSTOMER BROKER CLOSE SALE NEGOTIATION TRANSACTION. GET REQUEST ****\n");
            CustomerBrokerCloseCryptoAddressRequest request = new CustomerBrokerCloseCryptoAddressRequestImpl(
                    Actors.INTRA_USER,
                    Actors.CBP_CRYPTO_BROKER,
                    intraUser.getPublicKey(),
                    negotiation.getBrokerPublicKey(),
                    CryptoCurrency.BITCOIN,
                    BlockchainNetworkType.getDefaultBlockchainNetworkType()
            );
            /*CustomerBrokerCloseCryptoAddressRequest request = new CustomerBrokerCloseCryptoAddressRequestImpl(
                Actors.CBP_CRYPTO_CUSTOMER,
                Actors.CBP_CRYPTO_BROKER,
                negotiation.getCustomerPublicKey(),
                negotiation.getBrokerPublicKey(),
                CryptoCurrency.BITCOIN,
                BlockchainNetworkType.getDefaultBlockchainNetworkType()
            );*/

            return request;

        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantGetRequestCryptoAddressException(e.getMessage(), FermatException.wrapException(e), CantGetRequestCryptoAddressException.DEFAULT_MESSAGE, "ERROR GET REQUEST THE CRYPTO ADDRESS IN THE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //GENERATE AND REGISTER THE NEW CRYPTO ADDRESS OF THE ACTOR
    private String cryptoAdreessActor(CustomerBrokerCloseCryptoAddressRequest request) throws CantGetGenerateCryptoAddressException{

        CryptoVaultSelector     cryptoVaultSelector     = new CryptoVaultSelector(this.cryptoVaultManager);
        WalletManagerSelector   walletManagerSelector   = new WalletManagerSelector(this.walletManagerManager);
        String                  adreess                 = null;
        CryptoAddress           cryptoAdreess;
        IntraWalletUserIdentity intraUser;
        try {
            if(intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser().isEmpty()){
                throw new CantGetRequestCryptoAddressException(CantGetRequestCryptoAddressException.DEFAULT_MESSAGE, null, CantGetRequestCryptoAddressException.DEFAULT_MESSAGE, "ERROR GET REQUEST THE CRYPTO ADDRESS IN THE NEGOTIATION, UNKNOWN FAILURE.");
            }else{
                intraUser= intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser().get(0);
            }
            System.out.print("\n\n**** 3.1.1.2.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. GET CRYPTO ADDRESS ****\n");
            CustomerBrokerCloseCryptoAddress customerBrokerCloseCryptoAddress = new CustomerBrokerCloseCryptoAddress(
                    this.cryptoAddressBookManager,
                    cryptoVaultSelector,
                    walletManagerSelector,
                    errorManager,
                    pluginVersionReference
            );

            cryptoAdreess   = customerBrokerCloseCryptoAddress.CryptoAddressesNew(request);
            adreess         = cryptoAdreess.getAddress();
            adreess = adreess +":"+intraUser.getPublicKey();
            System.out.print("\n\n**** 3.1.1.2.2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. CRYPTO ADDRESS: "+adreess+" ****\n");

        } catch (CantCryptoAddressesNewException e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantGetGenerateCryptoAddressException(e.getMessage(),e, CantGetGenerateCryptoAddressException.DEFAULT_MESSAGE, "ERROR GET CRYPTO ADDRESS GENERATE AND REGISTER, FAILED GENERATION THE CRYPTO ADDRESS.");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantGetGenerateCryptoAddressException(e.getMessage(), FermatException.wrapException(e), CantGetGenerateCryptoAddressException.DEFAULT_MESSAGE, "ERROR GET CRYPTO ADDRESS GENERATE AND REGISTER, UNKNOWN FAILURE.");
        }

        return adreess;

    }
}
