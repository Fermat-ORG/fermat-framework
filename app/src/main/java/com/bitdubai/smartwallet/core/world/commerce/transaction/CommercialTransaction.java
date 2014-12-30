package com.bitdubai.smartwallet.core.world.commerce.transaction;

/**
 * Created by ciencias on 25.12.14.
 */

/**
 * Commercial transactions are exchanges that involve some sort of payment for a good or service. These types of
 * transactions are usually governed by commercial law and must follow specific guidelines. There are many forms of
 * business transactions, including those that occur between two separate businesses, consumers and businesses, between
 * internal divisions of a company and between two individual consumers.
 *
 * Exchanges of money for a product or professional service are usually classified as commercial transactions. Some of
 * the more obvious exchanges of this nature include a consumer purchasing goods in a retail store and a manufacturer
 * selling its products to wholesale companies. Commercial transactions can occur in a physical store location, online,
 * or through direct interaction with a sales representative or direct seller. The exchange of money may happen at the
 * time the goods or services are delivered, beforehand or after the fact.
 */

public interface CommercialTransaction extends Transaction {

    public long getTimestamp();

}
