package com.bitdubai.fermat_draft.layer._6_world.accounting.transaction;

/**
 * Created by ciencias on 25.12.14.
 */

/**
 * Financial transactions may be purely financial. More often, financial transactions are a hybrid of a financial
 * transaction and one of the other two types of transactions: Product Transactions and Distributive Transactions.
 *
 * Buying that pack of gum with a credit card is an example of a combination financial and products transaction.
 * The convenience store gives the gum chewer gum. To pay for it, the gum chewer takes out a small loan with the
 * financial institution that issued his or her credit card. The gum chewer signs a receipt telling the credit card
 * company to pay the convenience store the value of the gum. So the product transaction takes place between the gum
 * chewer and the convenience store, but the financial transaction takes place between those two and a financial
 * institution.
 */

public interface HybridFinancialTransaction extends FinancialTransaction {
}
