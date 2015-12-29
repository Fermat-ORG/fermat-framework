package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
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

    public CustomerBrokerCloseNegotiationCryptoAddress(
        CryptoAddressBookManager    cryptoAddressBookManager,
        CryptoVaultManager          cryptoVaultManager,
        WalletManagerManager        walletManagerManager
    ){
        this.cryptoAddressBookManager   = cryptoAddressBookManager;
        this.cryptoVaultManager         = cryptoVaultManager;
        this.walletManagerManager       = walletManagerManager;
    }

    //UPDATE THE PURCHASE NEGOTIATION FOR ADD NEW CRYPTO ADDRESS
    public CustomerBrokerPurchaseNegotiation getNegotiationAddCryptoAdreess(CustomerBrokerPurchaseNegotiation negotiation) throws CantNegotiationAddCryptoAdreessException {

        try {

            CustomerBrokerPurchaseNegotiation newNegotiation = negotiation;

            if (isCryptoCurrency(negotiation.getClauses(),ClauseType.CUSTOMER_PAYMENT_METHOD)) {

                CustomerBrokerCloseCryptoAddressRequest request             = getRequest(negotiation);
                Collection<Clause>                      negotiationClauses  = addCryptoAdreess(negotiation.getClauses(), request, ClauseType.CUSTOMER_CRYPTO_ADDRESS);

                newNegotiation = new CustomerBrokerPurchaseNegotiationImpl(
                    negotiation.getNegotiationId(),
                    negotiation.getCustomerPublicKey(),
                    negotiation.getBrokerPublicKey(),
                    negotiation.getStartDate(),
                    negotiation.getNegotiationExpirationDate(),
                    negotiation.getStatus(),
                    negotiationClauses
                );

            }

            return newNegotiation;

        } catch (CantGetListClauseException e){
            throw new CantNegotiationAddCryptoAdreessException(e.getMessage(),e, CantNegotiationAddCryptoAdreessException.DEFAULT_MESSAGE, "ERROR ADD CRYPTO ADREESS AN PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantNegotiationAddCryptoAdreessException(e.getMessage(), FermatException.wrapException(e), CantNegotiationAddCryptoAdreessException.DEFAULT_MESSAGE, "ERROR ADD CRYPTO ADREESS AN PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }

    }

    //UPDATE THE SALE NEGOTIATION FOR ADD NEW CRYPTO ADDRESS
    public CustomerBrokerSaleNegotiation getNegotiationAddCryptoAdreess(CustomerBrokerSaleNegotiation negotiation) throws CantNegotiationAddCryptoAdreessException {

        try {

            CustomerBrokerSaleNegotiation newNegotiation = negotiation;

            if (isCryptoCurrency(negotiation.getClauses(),ClauseType.BROKER_PAYMENT_METHOD)) {

                CustomerBrokerCloseCryptoAddressRequest request             = getRequest(negotiation);
                Collection<Clause>                      negotiationClauses  = addCryptoAdreess(negotiation.getClauses(), request, ClauseType.BROKER_CRYPTO_ADDRESS);

                newNegotiation = new CustomerBrokerSaleNegotiationImpl(
                    negotiation.getNegotiationId(),
                    negotiation.getCustomerPublicKey(),
                    negotiation.getBrokerPublicKey(),
                    negotiation.getStartDate(),
                    negotiation.getNegotiationExpirationDate(),
                    negotiation.getStatus(),
                    negotiationClauses
                );

            }

            return newNegotiation;
        } catch (CantGetListClauseException e){
            throw new CantNegotiationAddCryptoAdreessException(e.getMessage(),e, CantNegotiationAddCryptoAdreessException.DEFAULT_MESSAGE, "ERROR ADD CRYPTO ADREESS AN SALE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantNegotiationAddCryptoAdreessException(e.getMessage(), FermatException.wrapException(e), CantNegotiationAddCryptoAdreessException.DEFAULT_MESSAGE, "ERROR ADD CRYPTO ADREESS AN SALE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //CHECK IF IS CRYPTO CURRENCY
    public boolean isCryptoCurrency(Collection<Clause> negotiationClauses, ClauseType paymentMethod) throws CantDetermineCryptoCurrencyException {

        try {

            for (Clause clause : negotiationClauses) {
                if (clause.getType().equals(paymentMethod)) {
                    if (clause.getValue().equals(CurrencyType.CRYPTO_MONEY)) {
                        return true;
                    }
                }
            }

            return false;

        } catch (Exception e){
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
            throw new CantAddClauseNegotiationException(e.getMessage(), FermatException.wrapException(e), CantAddClauseNegotiationException.DEFAULT_MESSAGE, "ERROR ADD CLAUSE IN THE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //RETURN PURCHASE REQUEST THE CRYPTO ADDRESS
    private CustomerBrokerCloseCryptoAddressRequest getRequest(CustomerBrokerPurchaseNegotiation negotiation) throws CantGetRequestCryptoAddressException{

        try {

            CustomerBrokerCloseCryptoAddressRequest request = new CustomerBrokerCloseCryptoAddressRequestImpl(
                Actors.CBP_CRYPTO_BROKER,
                Actors.CBP_CRYPTO_CUSTOMER,
                negotiation.getBrokerPublicKey(),
                negotiation.getCustomerPublicKey(),
                CryptoCurrency.BITCOIN,
                BlockchainNetworkType.TEST
            );

            return request;

        } catch (Exception e){
            throw new CantGetRequestCryptoAddressException(e.getMessage(), FermatException.wrapException(e), CantGetRequestCryptoAddressException.DEFAULT_MESSAGE, "ERROR GET REQUEST THE CRYPTO ADDRESS IN THE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //RETURN SALE REQUEST THE CRYPTO ADDRESS
    private CustomerBrokerCloseCryptoAddressRequest getRequest(CustomerBrokerSaleNegotiation negotiation) throws CantGetRequestCryptoAddressException{

        try {

            CustomerBrokerCloseCryptoAddressRequest request = new CustomerBrokerCloseCryptoAddressRequestImpl(
                Actors.CBP_CRYPTO_CUSTOMER,
                Actors.CBP_CRYPTO_BROKER,
                negotiation.getCustomerPublicKey(),
                negotiation.getBrokerPublicKey(),
                CryptoCurrency.BITCOIN,
                BlockchainNetworkType.TEST
            );

            return request;

        } catch (Exception e){
            throw new CantGetRequestCryptoAddressException(e.getMessage(), FermatException.wrapException(e), CantGetRequestCryptoAddressException.DEFAULT_MESSAGE, "ERROR GET REQUEST THE CRYPTO ADDRESS IN THE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //GENERATE AND REGISTER THE NEW CRYPTO ADDRESS OF THE ACTOR
    private String cryptoAdreessActor(CustomerBrokerCloseCryptoAddressRequest request) throws CantGetGenerateCryptoAddressException{

        CryptoVaultSelector     cryptoVaultSelector     = new CryptoVaultSelector(this.cryptoVaultManager);
        WalletManagerSelector   walletManagerSelector   = new WalletManagerSelector(this.walletManagerManager);
        String                  adreess                 = null;
        CryptoAddress           cryptoAdreess;

        try {

            CustomerBrokerCloseCryptoAddress customerBrokerCloseCryptoAddress = new CustomerBrokerCloseCryptoAddress(
                    this.cryptoAddressBookManager,
                    cryptoVaultSelector,
                    walletManagerSelector
            );

            cryptoAdreess   = customerBrokerCloseCryptoAddress.CryptoAddressesNew(request);
            adreess         = cryptoAdreess.getAddress();

        } catch (CantCryptoAddressesNewException e){
            throw new CantGetGenerateCryptoAddressException(e.getMessage(),e, CantGetGenerateCryptoAddressException.DEFAULT_MESSAGE, "ERROR GET CRYPTO ADDRESS GENERATE AND REGISTER, FAILED GENERATION THE CRYPTO ADDRESS.");
        } catch (Exception e){
            throw new CantGetGenerateCryptoAddressException(e.getMessage(), FermatException.wrapException(e), CantGetGenerateCryptoAddressException.DEFAULT_MESSAGE, "ERROR GET CRYPTO ADDRESS GENERATE AND REGISTER, UNKNOWN FAILURE.");
        }

        return adreess;

    }
}
