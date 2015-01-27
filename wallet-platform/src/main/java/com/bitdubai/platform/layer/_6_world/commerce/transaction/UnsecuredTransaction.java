package com.bitdubai.platform.layer._6_world.commerce.transaction;

/**
 * Created by ciencias on 25.12.14.
 */

/**
 * Secured transactions are transactions in which a creditor has a security interest in property held by the debtor.
 * In the event that the debtor defaults on the debt, the creditor can act upon the security interest to settle the
 * debt. Unsecured transactions are those in which the creditor does not have a security interest and must take the
 * debtor to court to recover the debt if the debtor fails to repay the debt in a timely fashion. One of the most
 * common forms of secured transaction is a mortgage, in which bank lends someone money to buy a house and the house
 * serves as the security interest.
 *
 * In a secured transaction, the creditor wants to make sure that the security interest or collateral on the loan will
 * actually satisfy the debt in the event of a problem. For example, a debtor could not offer a car as security
 * interest for a mortgage because the value of the car at resale would likely be less than that of the house. The
 * creditor wants to make sure that if the debtor reneges, it will be able to reclaim most of the money, rather than
 * ending up with outstanding loans on its books which it has no hope of recovering. Secured transactions can become
 * risky when they involve assets which may depreciate over time.
 */

public interface UnsecuredTransaction extends Transaction {
}
