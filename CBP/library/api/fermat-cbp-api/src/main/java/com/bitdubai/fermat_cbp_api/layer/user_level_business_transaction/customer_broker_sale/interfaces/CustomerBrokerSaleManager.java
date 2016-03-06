package com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_sale.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSendNotificationReviewNegotiation;

/**
 * Created by franklin on 11/12/15.
 */
public interface CustomerBrokerSaleManager extends FermatManager {
    void notificationReviewNegotiation(String publicKey, String tittle, String body) throws CantSendNotificationReviewNegotiation;
}
