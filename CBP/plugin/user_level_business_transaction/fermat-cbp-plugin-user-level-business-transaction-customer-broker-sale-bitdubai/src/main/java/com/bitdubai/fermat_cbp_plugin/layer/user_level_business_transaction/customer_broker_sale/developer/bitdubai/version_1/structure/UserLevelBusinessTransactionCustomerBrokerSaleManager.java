package com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.dmp_module.notification.NotificationType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSendNotificationReviewNegotiation;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_purchase.event.ReviewNegotiationNotificationEvent;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_sale.interfaces.CustomerBrokerSaleManager;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.UserLevelBusinessTransactionCustomerBrokerSalePluginRoot;

/**
 * Created by franklin on 15/12/15.
 */
public class UserLevelBusinessTransactionCustomerBrokerSaleManager implements CustomerBrokerSaleManager {
    public static String REVIEW_NEGOTIATION_NOTIFICATION = "Review negotiation";
    UserLevelBusinessTransactionCustomerBrokerSalePluginRoot userLevelBusinessTransactionCustomerBrokerSalePluginRoot = new UserLevelBusinessTransactionCustomerBrokerSalePluginRoot();

    public UserLevelBusinessTransactionCustomerBrokerSaleManager() {
    }

    @Override
    public void notificationReviewNegotiation(String publicKey, String tittle, String body) throws CantSendNotificationReviewNegotiation {

        ReviewNegotiationNotificationEvent event = (ReviewNegotiationNotificationEvent) userLevelBusinessTransactionCustomerBrokerSalePluginRoot.getEventManager().getNewEvent(com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType.REVIEW_NEGOTIATION_NOTIFICATION);
        event.setLocalPublicKey(publicKey);
        event.setAlertTitle(getSourceString(UserLevelBusinessTransactionCustomerBrokerSalePluginRoot.EVENT_SOURCE));
        event.setTextTitle(tittle);
        event.setTextBody(body);
        event.setNotificationType(NotificationType.REVIEW_NOTIFICATION.getCode());
        event.setSource(UserLevelBusinessTransactionCustomerBrokerSalePluginRoot.EVENT_SOURCE);
        this.userLevelBusinessTransactionCustomerBrokerSalePluginRoot.getEventManager().raiseEvent(event);
        System.out.println(new StringBuilder().append("UserLevelBusinessTransactionCustomerBrokerSALEPluginRoot - ReviewNegotiationNotificationEvent fired!: ").append(event.toString()).toString());
    }

    private String getSourceString(EventSource eventSource) {
        switch (eventSource) {

            case USER_LEVEL_CUSTOMER_BROKER_SALE_MANAGER:
                return REVIEW_NEGOTIATION_NOTIFICATION;
            default:
                return "Method: getSourceString - NO TIENE valor ASIGNADO para RETURN";

        }

    }
}
