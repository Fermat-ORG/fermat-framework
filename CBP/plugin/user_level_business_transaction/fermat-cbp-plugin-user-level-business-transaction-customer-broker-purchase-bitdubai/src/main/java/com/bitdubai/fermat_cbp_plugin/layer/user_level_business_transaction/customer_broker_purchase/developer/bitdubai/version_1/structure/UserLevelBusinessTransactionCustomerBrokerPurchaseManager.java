package com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.dmp_module.notification.NotificationType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSendNotificationReviewNegotiation;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_purchase.event.ReviewNegotiationNotificationEvent;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_purchase.interfaces.CustomerBrokerPurchaseManager;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.UserLevelBusinessTransactionCustomerBrokerPurchasePluginRoot;

/**
 * Created by franklin on 11/12/15.
 */
public class UserLevelBusinessTransactionCustomerBrokerPurchaseManager implements CustomerBrokerPurchaseManager {
    public static String REVIEW_NEGOTIATION_NOTIFICATION = "Review negotiation";
    UserLevelBusinessTransactionCustomerBrokerPurchasePluginRoot userLevelBusinessTransactionCustomerBrokerPurchasePluginRoot = new UserLevelBusinessTransactionCustomerBrokerPurchasePluginRoot();

    public UserLevelBusinessTransactionCustomerBrokerPurchaseManager() {
    }

    @Override
    public void notificationReviewNegotiation(String publicKey, String tittle, String body) throws CantSendNotificationReviewNegotiation {

        ReviewNegotiationNotificationEvent event = (ReviewNegotiationNotificationEvent) userLevelBusinessTransactionCustomerBrokerPurchasePluginRoot.getEventManager().getNewEvent(com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType.REVIEW_NEGOTIATION_NOTIFICATION);
        event.setLocalPublicKey(publicKey);
        event.setAlertTitle(getSourceString(UserLevelBusinessTransactionCustomerBrokerPurchasePluginRoot.EVENT_SOURCE));
        event.setTextTitle(tittle);
        event.setTextBody(body);
        event.setNotificationType(NotificationType.REVIEW_NOTIFICATION.getCode());
        event.setSource(UserLevelBusinessTransactionCustomerBrokerPurchasePluginRoot.EVENT_SOURCE);
        this.userLevelBusinessTransactionCustomerBrokerPurchasePluginRoot.getEventManager().raiseEvent(event);
        System.out.println(new StringBuilder().append("UserLevelBusinessTransactionCustomerBrokerPurchasePluginRoot - ReviewNegotiationNotificationEvent fired!: ").append(event.toString()).toString());
    }

    private String getSourceString(EventSource eventSource) {
        switch (eventSource) {

            case USER_LEVEL_CUSTOMER_BROKER_PURCHASE_MANAGER:
                return REVIEW_NEGOTIATION_NOTIFICATION;
            default:
                return "Method: getSourceString - NO TIENE valor ASIGNADO para RETURN";

        }

    }
}
