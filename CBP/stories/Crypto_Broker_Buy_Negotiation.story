Story: Crypto Broker Buy Negotiation

In order to buy Merchandise from a Crypto Broker
  As a Crypto Customer
  I should perform a Buy Negotiation
Using the Crypto Customer Wallet Android plugin
  And the Crypto Customer Wallet Module Java plugin
  And the Crypto Broker Actor Java plugin
  And the Crypto Broker Network Service Java plugin
  And the Crypto Customer Network Service Java plugin

Scenario: The Crypto Customer creates a Buy Negotiation
  Given I have set a Crypto Broker for Business Transactions in the Crypto Broker Actor Java
    And the Crypto Broker offers the "Type of Merchandise" I want to Buy
  When I create a Buy Negotiation in the Crypto Customer Wallet
    And I specify the "Type of Merchandise" I want to Buy
    And I specify the Amount of Merchandise I want to Buy
  Then Crypto Customer Wallet should create a "Buy Negotiation Contract" for the the "Type of Merchandise"
    And it should send a message through the Crypto Broker Network Service with the "Contract" Information to the Crypto Broker
    And it should be added to a list of Pending Buy Negotiations in the Crypto Customer Wallet
Permutations:
|Type of Merchandise  |Buy Negotiation Contract                         |
|Market Money         |Crypto Broker Market Money Contract Java plugin  |
|Fiat Money           |Crypto Broker Fiat Money Contract Java plugin    |

Scenario: The Crypto Broker replies to a Buy Negotiation
  Given I have created a Buy Negotiation Contract in the Crypto Customer Wallet
    And it has been reviewed by the Crypto Broker I have set for Business Transactions in the Crypto Broker Actor Java
  When the Crypto Customer Network Service receives a message from the Crypto Broker
    And it contains information of the Buy Negotiation Contract
  Then the Buy Negotiation Contract should be updated with the supplied information
    And the Crypto Broker Wallet should be notified of the change
    And the updated Buy Negotiation Contract should be visible inside the Crypto Customer Wallet

Scenario: The Crypto Customer provides additional information to a Buy Negotiation
  Given I have created a Buy Negotiation in the Crypto Customer Wallet
    And it has been reviewed by the Crypto Broker I have set for Business Transactions in the Crypto Broker Actor Java
    And the Crypto Broker has provided additional information
  When I provide additional information for the Buy Negotiation Contract
  Then the Buy Contract should be updated with the information
    And it should send a message through the Crypto Broker Network Service with the updated Contract Information to the Crypto Broker
    And it should be visible inside the Crypto Customer Wallet

Scenario: The Crypto Customer accepts the terms of the Buy Negotiation Contract
  Given I have created a "Buy Negotiation Contract" in the Crypto Customer Wallet
    And it has been reviewed and updated by the Crypto Broker
    And it has been reviewed and update by the Crypto Customer
  When I accept the terms of the Buy Negotiation Contract
  Then the Buy Negotiation should be marked as ACCEPTED
    And it should send a message through the Crypto Broker Network Service to the Crypto Broker marking the Buy Negotiation as ACCEPTED
    And the Crypto Customer Wallet should create a "Business Transaction" according to "Type of Merchandise" and the agreed "Type of Execution" in the Buy Negotiation Contract
    But it should be visible inside the Crypto Customer Wallet as continuation of the Buy Negotiation
Permutations:
|Type of Merchandise  |Buy Negotiation Contract                             |Type of Execution  |Business Transaction             |
|Market Money         |Crypto Broker Market Money Buy Contract Java plugin  |Market Crypto      |Crypto Broker Market Crypto Buy  |
|Fiat Money           |Crypto Broker Fiat Money Buy Contract Java plugin    |Fiat Cash          |Crypto Broker Fiat Cash Buy      |
|Fiat Money           |Crypto Broker Fiat Money Buy Contract Java plugin    |Fiat Bank          |Crypto Broker Fiat Bank Buy      |
