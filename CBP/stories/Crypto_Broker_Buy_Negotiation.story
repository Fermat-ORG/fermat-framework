Story: Crypto Broker Buy Negotiation

In order to buy Merchandise from a Crypto Broker
  As a Crypto Customer
  I should perform a Buy Negotiation
Using the Crypto Customer Wallet Android
  And the Crypto Customer Wallet Module Plugin
  And the Crypto Broker Actor Plugin
  And the Crypto Broker Network Service Plugin
  And the Crypto Customer Network Service Plugin
  And the Crypto Broker Purchase Request Plugin
  And the Market Money Buy Contract Plugin
  And the Fiat Money Buy Contract Plugin

Scenario: The Crypto Customer initiates a Buy Request
  Given I have set conection whit a Crypto Broker in the Crypto Broker Actor Plugin
    And the Crypto Broker offers the "Type of Merchandise" I want to Buy
  When I want to create a Crypto Broker Buy Request in the Crypto Customer Wallet
  Then I have to specify the Crypto Broker I want to negotiate with
    And I have to specify the "Type of Merchandise" I want to Buy
    And I have to specify the Amount of Merchandise I want to Buy
Permutations:
|Type of Merchandise  |
|Market Money         |
|Fiat Money           |

Scenario: The Crypto Customer creates a Buy Request
  Given I have specified the required information
  When I create a Crypto Broker Buy Request in the Crypto Customer Wallet
  Then the Crypto Customer Wallet Module Plugin should create a new Crypto Broker Buy Request using the Crypto Broker Purchase Request Plugin
    And the Crypto Broker Purchase Request Plugin should mark the "Request" as IN_PROCESS
    And it should send a message with the Crypto Broker Buy Request  through the Crypto Broker Network Service Plugin to the Crypto Broker
    And the Crypto Customer Wallet Android should put the Crypto Broker Buy Request in the list of Pending Buy Requests

Scenario: The Crypto Broker accepts the Buy Request
  Given I have already sent a Crypto Broker Buy Request to the Crypto Broker
    And I received a message with the acceptance of the Crypto Broker Buy Request
  When the Crypto Customer Network Service Plugin receive the message
  Then it should notify the Crypto Broker Purchase Request Plugin with the acceptance of the Crypto Broker Buy Request
    And the Crypto Broker Purchase Request Plugin should mark the "Request" as ACCEPTED
    And it should notify the Crypto Customer Wallet Module Plugin
    And the Crypto Customer Wallet Module Plugin should create a "Buy Negotiation Contract" for the "Type of Merchandise"
    And the Crypto Customer Wallet Android should update the list of Crypto Broker Buy Requests as correspond
Permutations:
|Type of Merchandise  |Buy Negotiation Contract   |
|Market Money         |Market Money Buy Contract  |
|Fiat Money           |Fiat Money Buy Contract    |

Scenario: The Crypto Broker postpone the Buy Request
  Given I have already sent a Crypto Broker Buy Request to the Crypto Broker
    And I received a message with the postponing of the Crypto Broker Buy Request
  When the Crypto Customer Network Service Plugin receive the message
  Then it should notify the Crypto Broker Purchase Request PLugin with the postpone of "Request"
    And the Crypto Broker Purchase Request PLugin should mark the "Request" as POSTPONED
    And the Crypto Customer Wallet Android should update the list of Crypto Broker Buy Requests as correspond

Scenario: Creation of a Buy Negotiation Contract
  Given I have received the acceptance of a Crypto Broker Buy Request
    And it specified a "Type of Merchandise"
  When the "Buy Negotiation Contract" is created
  Then "Buy Negotiation Contract" should be marked has NEGOCIACION
    And it should send a message through the Crypto Broker Network Service PLugin with the "Contract" Information to the Crypto Broker
    And it should be added to a list of Buy Negotiations in the Crypto Customer Wallet
    And it should add a register in the "Buy Negotiation Contract" Transactions Log
Permutations:
|Type of Merchandise  |Buy Negotiation Contract   |
|Market Money         |Market Money Buy Contract  |
|Fiat Money           |Fiat Money Buy Contract    |

Scenario: The Crypto Broker provides additional information to a Buy Negotiation
  Given I have updated a "Buy Negotiation Contract" in the Crypto Customer Wallet
    And it has been reviewed by the Crypto Broker
    And the Crypto Broker send a message that contains information of the "Buy Negotiation Contract"
  When the Crypto Customer Network Service Plugin receives the message
  Then the "Buy Negotiation Contract" should be updated with the supplied information
    And the Crypto Broker Wallet Module Plugin should be notified of the change
    And the updated "Buy Negotiation Contract" should be visible inside the Crypto Customer Wallet
    And the Crypto Customer Wallet Module Plugin should add a register to the "Buy Negotiation Contract" Transactions Log
Permutations:
|Buy Negotiation Contract   |
|Market Money Buy Contract  |
|Fiat Money Buy Contract    |

Scenario: The Crypto Customer provides additional information to a Buy Negotiation
  Given I have updated a "Buy Negotiation Contract" in the Crypto Customer Wallet
    And it has been reviewed by the Crypto Broker
    And the Crypto Broker has provided additional information
  When I provide additional information for the "Buy Negotiation Contract"
  Then the "Buy Negotiation Contract" should be updated with the information
    And it should send a message through the Crypto Broker Network Service Plugin with the updated Contract Information to the Crypto Broker
    And it should be visible inside the Crypto Customer Wallet
    And the Crypto Customer Wallet Module Plugin hould add a register to the "Buy Negotiation Contract" Transactions Log
Permutations:
|Buy Negotiation Contract   |
|Market Money Buy Contract  |
|Fiat Money Buy Contract    |

Scenario: The Crypto Customer accepts the terms of the "Buy Negotiation Contract"
  Given I updated a "Buy Negotiation Contract" in the Crypto Customer Wallet
    And it has been reviewed and updated by the Crypto Broker
    And it has been reviewed and updated by me
  When I accept the terms of the "Buy Negotiation Contract"
  Then the "Buy Negotiation Contract" should be marked as PENDIENTE_PAGO
    And it should send a message through the Crypto Broker Network Service Plugin with the updated Contract Information to the Crypto Broker
    And the Crypto Customer Wallet Module Plugin should create a "Business Transaction" according to "Type of Merchandise" and the agreed "Type of Execution" in the "Buy Negotiation Contract"
    And should be visible inside the Crypto Customer Wallet as continuation of the Buy Negotiation
    And the Crypto Customer Wallet Module Plugin should add a register to the "Buy Negotiation Contract" Transactions Log
Permutations:
|Type of Merchandise  |Buy Negotiation Contract   |Type of Execution  |Business Transaction                  |
|Market Money         |Market Money Buy Contract  |Market Crypto      |Crypto Broker Market Crypto Purchase  |
|Fiat Money           |Fiat Money Buy Contract    |Fiat Cash          |Crypto Broker Fiat Cash Purchase      |
|Fiat Money           |Fiat Money Buy Contract    |Fiat Bank          |Crypto Broker Fiat Bank Purchase      |

Scenario: The Crypto Customer rejects the terms of the "Buy Negotiation Contract"
  Given I have updated a "Buy Negotiation Contract" in the Crypto Customer Wallet
    And it has been reviewed and updated by the Crypto Broker
    And it has been reviewed and update by the me
  When I rejects the terms of the "Buy Negotiation Contract"
  Then the Buy Negotiation should be marked as CANCELADO
    And it should send a message through the Crypto Broker Network Service Plugin with the updated Contract Information to the Crypto Broker
Permutations:
|Buy Negotiation Contract               |
|Market Money Buy Negotiation Contract  |
|Fiat Money Buy Negotiation Contract    |
