package com.bitdubai.platform.layer._6_world.commerce.transaction;

/**
 * Created by ciencias on 25.12.14.
 */

/**
 * Merchants often conduct a wide range of transactions. Some of these are similar to personal transactions, but many
 * are unique. An example of such a merchant transaction is gift card processing. Other transactions include cash sales,
 * refunds, and holds.
 *
 * If all of the merchants in the world were considered, it is likely that cash sales would be the most common merchant
 * transaction. It is also one of the least innovative. Cash sales tend to simply involve a consumer presenting cash to
 * a merchant for some product or service.
 *
 * Many people purchase goods and services with credit cards. This involves a merchant transaction known as credit card
 * processing. Such a transaction occurs when a person with the authority to access a given account decides to make a
 * purchase without cash. That individual authorizes the merchant to request the funds from a third party. In such
 * instances, the merchant is paid by the third party, not the consumer.
 *
 * Sometimes consumers need products or services but the amount of those items are not initially known. To reduce the
 * risk of non-payment, a merchant may hold a certain amount of money available on a credit card or bank card. When a
 * merchant does this, no money is transferred between any parties. However, whatever amount is put on hold becomes
 * available to the merchant and unavailable to the account holder until the hold is released.
 *
 * Gift card processing is another merchant transaction that can eliminate the need for cash. This system allows a
 * person to store value on a card that can be used at a later date to redeem goods and services. In most cases, this
 * is done when one person wants to give another person a gift without choosing the object. The person who receives a
 * gift card can take it to a merchant that accepts it and have the value on the card deducted from the cost of her
 * purchase.
 *
 * For numerous reasons, a consumer may change her mind about a purchase. When this is permissible, it often involves a
 * merchant transaction known as a refund. Refunds can be issued in a number of ways. Some merchants will only issue
 * credit for the amount previously spent, thereby ensuring that the spent monies remain in their accounts. Others will
 * return the full amount in whatever form it was paid to them.
 *
 * A rebate is another type of merchant transaction. Rebates involve consumers paying for a merchant’s goods or
 * services. Once that payment has been verified, the merchant will return a portion of the money to the consumer.
 * This is often used by merchants to encourage people to buy their goods.
 */


public interface MerchantTransaction extends CommercialTransaction{
}
