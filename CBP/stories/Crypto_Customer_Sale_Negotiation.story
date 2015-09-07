Story: Crypto Customer Sale Negotiation

In order sale Merchandise to a Crypto Customer
  As a Crypto Broker
  I should perform a Sale Negotiation
Using the Crypto Broker Wallet Android plugin
  And the Crypto Broker Wallet Module Java plugin
  And the Crypto Customer Actor plugin
  And the Crypto Broker Network Service Java plugin
  And the Crypto Customer Network Service Java plugin
  
Scenario: The Crypto Broker replies a request of Buy Negotiation
  Given I receives a request to start a Negotiation from a Crypto Customer through the Crypto Customer Actor Plugin
    And I decide in which of my wallets place the Buy Negotiation depending of the "Type of Merchandise" the Crypto Customer is requesting
  When I create a Sale Negotiation in the Crypto Broker Wallet
    And I set in the Crypto Broker Wallet the price for the "Type of Merchandise" the Crypto Customer is requesting
    And I set other information has the "Payment Type" and the Currency for the Payment"
  Then the Crypto Broker Wallet should Updated a "Sale Negotiation Contract"
    And it should send a message to the Crypto Customer through the Crypto Customer Network Service with the "Contract" Information
    And it should be added to a list of Pending Sale Negotiations in the Crypto Broker Wallet
    And it should add a regiter in the "Buy Negotiation Contract" Transactions Log 
Permutations:
|Type of Merchandise  |Sale Negotiation Contract              |Payment Type   |Currency for the Payment
|Market Money         |Market Money Buy Negotiation Contract  |Bank           |Market Money
|Fiat Money           |Fiat Money Buy Negotiation Contract    |Cash on Hand   |Fiat Money
|                     |                                       |Crypto         | 

Scenario: The Crypto Broker postpone the request of Sale Negotiation
  Given I receives a request to start a Negotiation from a Crypto Customer through the Crypto Customer Actor Plugin
    And I decide to postpone the request to start the Negotiation
  When I postpone a Sale Negotiation in the Crypto Broker Wallet
  Then the Crypto Broker Wallet should Updated a "Buy Negotiation Contract" regitrer the postpone
    And it set the "Sale Negotiation Contract" in a state of PAUSED
    And it should add a regiter in the "Buy Negotiation Contract" Transactions Log

Scenario: The Crypto Broker provides additional information to a Sale Negotiation
  Given I have updated a "Sale Negotiation Contract" in the Crypto Broker Wallet
    And it has been reviewed by the Crypto Customer
    And the Crypto Customer has provided additional information
  When I provide additional information for the "Sale Negotiation Contract"
  Then the "Sale Negotiation Contract" should be updated with the information
    And it should send a message through the Crypto Customer Network Service with the updated Contract Information to the Crypto Customer
    And it should be visible inside the Crypto Broker Wallet
    And the Crypto Broker Wallet should add the update the "Sale Negotiation Contract" Transactions Log

Scenario: The Crypto Customer accepts the terms of the "Buy Negotiation Contract"
  Given I have updated a "Buy Negotiation Contract" in the Crypto Broker Wallet
    And it has been reviewed and updated by the Crypto Customer
  When The Crypto Customer accept the terms of the "Sale Negotiation Contract"
  Then the "Sale Negotiation Contract" should be marked as ACCEPTED
    And the Crypto Broker Wallet should create a "Business Transaction" according to "Type of Merchandise" and the agreed "Type of Execution" in the "Sale Negotiation Contract"
    And "Business Transaction" should be visible inside the Crypto Broker Wallet as continuation of the Negotiation
    And the Crypto Broker Wallet should add the update the "Sale Negotiation Contract" Transactions Log
Permutations:
|Type of Merchandise  |Buy Negotiation Contract               |Type of Execution  |Business Transaction  |
|Market Money         |Market Money Buy Negotiation Contract  |                   |                      |
|Fiat Money           |Fiat Money Buy Negotiation Contract    |                   |                      |
