package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.BuyRequest;

import java.util.List;
import java.util.UUID;

/**
 * Created by nelson on 22/09/15.
 */
public interface Wallet {

    List<BuyRequest> getListOfBuyRequest(int max, int offset);

    List<ContractBasicInformation> getListOfDealsWaitingForBroker(int max, int offset);

    List<ContractBasicInformation> getListOfDealsWaitingForCustomer(int max, int offset);

    List<ContractBasicInformation> getListOfContractsWaitingForBroker(int max, int offset);

    List<ContractBasicInformation> getListOfContractsWaitingForCustomer(int max, int offset);

    CryptoBrokerContractInformation getContractDetails(UUID contractId);

    void updateContractInformation(CryptoBrokerContractInformation contractInformation);

    void acceptDeal(UUID contractId);

    void acceptBuyRequest(UUID requestId, float merchandisePrice);

    void cancelBuyRequest(UUID requestId);

    void confirmPayment(UUID contractId);

    StockInformation getCurrentStock(String stockCurrency);
}
