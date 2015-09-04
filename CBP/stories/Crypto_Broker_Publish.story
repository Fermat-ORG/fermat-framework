Story: Crypto Broker Publish

In order to be discovered by Crypto Customers
  As a Crypto Broker
  I need to publish my Public Identity
  And my sale prices
Using the Crypto Broker SubApp Android
  And the Crypto Broker SubApp Module Java
  And the Crypto Broker Network Service Java
  And the Fiat Index World Java
  And the Market Index World Java
  And the Crypto Broker Agent

Scenario: Public Information Publish
  Given I have Crypto Broker Identity
  When I decide to publish my Crypto Broker Identity in the Crypto Broker SubApp
    And I select the "Fiat Money Type for Price Reference"
    And I select the "Stocked Fiat Money Types"
    And I select the "Stocked Market Money Types"
    And I select the "Fiat Money Types for Payment"
    And I select the "Market Money Types for Payment"
    And I specify the maximum "Fiat Money Amount" that I support per operation
    And I specify the maximum "Market Money Amount" that I support per operation
  Then the Crypto Broker SubApp obtains the exchange rates between my "Stocked Fiat Money" and the "Fiat Money Type for Price Reference" from the Fiat Index World
    And the Crypto Broker SubApp obtains the exchange rates between my "Stocked Market Money" and the "Fiat Money Type for Price Reference" from the Market Index World
    And the Crypto Broker SubApp should register this information in the Fermat Network through the Crypto Broker Network Service

Scenario: Business Transaction Public Information Update
  Given I have performed a "Business Transaction"
  When the "Business Transaction" is completed
    And the Crypto Broker Agent consolidates the data of the Business Transactions
  Then the Crypto Broker Agent should update the exchange rates between my "Stocked Fiat Money" and the "Fiat Money Type for Price Reference" in my Crypto Broker Public Identity
    And the Crypto Broker Agent should update the exchange rates between my "Stocked Fiat Money" and the "Fiat Money Type for Price Reference" in my Crypto Broker Public Identity
    And the Crypto Broker Agent should publish this update through the Crypto Broker Network Service

Public Identity Crypto Broker Example:
- Photo
- Alias
- Market Money exchange rates
  - Bitcoin: 230 USD
  - Litecoin: 10 USD
- Fiat Money exchange rates
  - Bolivares: 630 x 1 USD
  - Pesos Argentino: 14 x 1 USD
  - Euros: 1,2 USD
- Market Money Accepted Payments
  - Bitcoin
  - Litecoin
  - Ethereum
- Fiat Money Accepted Payments
  - Forines Hungaros
  - Dolares Estadounidenses
  - Pesos Colombianos
