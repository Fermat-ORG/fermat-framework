Story: Crypto Broker Wallet Stock Replenishment

In order to perform the activities of a Crypto Broker
  As a Crypto Broker
  I need to Replenish my Stock
Using the Crypto Broker Wallet SubApp Android
  And the Crypto Broker Wallet Module Java

Scenario: Crypto Market Money Wallet address request
  Given I have configured the "Crypto Market Money" I want to use in my Crypto Broker Wallet
  When I request a public address for my Crypto Broker Wallet
  Then the Crypto Broker Wallet should request a new Crypto Public Address to the Crypto Broker Crypto Market Money Wallet
    And the Crypto Broker Crypto Market Money Wallet should request the address to the "Crypto Vault"
    And the Crypto Broker Crypto Market Money Wallet should register the address in the Crypto Address Book Crypto Module BCH
Permutations:
|Crypto Market Money  |Crypto Vault                       |
|Bitcoin              |Bitcoin Currency Crypto Vault BCH  |

Scenario: Creation of a Crypto Market Money Stock Replenishment Transaction from outside of Fermat
  Given I have configured the "Crypto Market Money" I want to use in my Crypto Broker Wallet
  When I create a Crypto Market Money Stock Replenishment Business Transaction in my Crypto Broker Wallet
  Then the Crypto Broker Wallet should provide me with a Crypto Public Address for the Transaction
  And the Business Transaction status should be set as PENDING

Scenario: Incoming crypto stock arrives though the Blockchain
  Given I have created a Crypto Market Money Stock Business Transaction
    And that I have sent the Crypto Market Money through an external resource to the Crypto Public Address of the operation
  When the Incoming Crypto Crypto Router BCH notifies of an incoming transaction to the Crypto Public Address
  Then the Crypto Market Money Business Transaction should listen to the event trigerred by the Incoming Crypto
    And it should add a credit to the Crypto Broker Crypto Market Money Wallet
    And it should set its status as COMPLETE

Scenario: Fiat Offline Replenishment of Stock
  Given I haven't stocked any value for a "Type of Merchandise" in my Crypto Broker Wallet
  When I perform a "Stock Business Transaction" operation with the value of "X" in the Crypto Broker Wallet
    And I register it as a "Type of Stock"
  Then the "Stock Business Transaction" should add a credit in my "Stock Wallet" with the value of "X"
    And the consolidated balance for the "Stock Wallet" should be "X"
Permutations:
|Type of Merchandise  |Type of Stock  |Stock Business Transaction                               |Stock Wallet           |
|Fiat Money           |Cash           |Cash Fiat Money Stock Repenishment Business Transaction  |Cash Fiat Money Wallet |
|Fiat Money           |Bank           |Cash Fiat Money Stock Repenishment Business Transaction  |Bank Fiat Money Wallet |

Scenario: Fiat Offline Replenishment over an existing Stock
  Given I have a Stock in my Crypto Broker Wallet for a "Type of Merchandise"
    And the value of the Stock is "X"
  When I perform a "Stock Business Transaction" with the value of "Y" in the Crypto Broker Wallet
      And I register it as a "Type of Stock"
  Then "Stock Business Transaction" should add a credit in my "Stock Wallet" with the value of "Y"
    And the consolidated balance for the "Stock Wallet" should be "X+Y"
Permutations:
|Type of Merchandise  |Type of Stock  |Stock Business Transaction                               |Stock Wallet           |
|Fiat Money           |Cash           |Cash Fiat Money Stock Repenishment Business Transaction  |Cash Fiat Money Wallet |
|Fiat Money           |Bank           |Cash Fiat Money Stock Repenishment Business Transaction  |Bank Fiat Money Wallet |
