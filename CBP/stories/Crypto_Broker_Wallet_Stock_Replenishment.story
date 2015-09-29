Story: Crypto Broker Wallet Stock Replenishment

In order to perform the activities of a Crypto Broker
  As a Crypto Broker
  I need to Replenish my Stock
Using the Crypto Broker Wallet
  And the Crypto Broker Wallet Module
  And the Crypto Money Stock Replenishment Business Transaction Plugin
  And the Cash Money Stock Repenishment Business Transaction Plugin
  And the Cash Money Stock Repenishment Business Transaction Plugin
  And the Bitcoin Currency Crypto Vault Plugin BCH
  And the Incoming Crypto Crypto Router Plugin BCH
  And the Crypto Address Book Crypto Module BCH
  And the Bitcoin Wallet Wallet Plugin CCP
  And the Cash Money Wallet Plugin CSH
  And the Bank Money Wallet Plugin BNK

Scenario: Crypto Market Money Wallet address request
  Given I have configured the "Crypto Market Money" I want to use in my Crypto Broker Wallet
  When I request a public address for my Crypto Broker Wallet
  Then the Crypto Broker Wallet should request a new Crypto Public Address to the Bitcoin Wallet Wallet CCP
    And the Bitcoin Wallet Wallet Plugin CCP should request the address to the "Crypto Vault"
    And the Bitcoin Wallet Wallet Plugin CCP should register the address in the Crypto Address Book Crypto Module BCH
Permutations:
|Crypto Market Money  |Crypto Vault                       |
|Bitcoin              |Bitcoin Currency Crypto Vault BCH  |

Scenario: Creation of a Crypto Money Stock Replenishment Transaction from outside of Fermat
  Given I have configured the "Crypto Market Money" I want to use in my Crypto Broker Wallet
  When I create a Crypto Money Stock Replenishment Business Transaction in my Crypto Broker Wallet
  Then the Crypto Broker Wallet should provide me with a Crypto Public Address for the Transaction
    And the Business Transaction status should be set as PENDING

Scenario: Incoming crypto stock arrives through the Blockchain
  Given I have created a Crypto Money Stock Business Transaction
    And that I have sent the Crypto Money through an external resource to the Crypto Public Address of the operation
  When the Incoming Crypto Crypto Router BCH notifies of an incoming transaction to the Crypto Public Address
  Then the Crypto Money Business Transaction should listen to the event trigerred by the Incoming Crypto
    And it should add a credit to the Bitcoin Wallet Wallet CCP
    And it should set its status as COMPLETE

Scenario: Fiat Offline Replenishment of Stock
  Given I haven't stocked any value for a "Type of Merchandise" in my Crypto Broker Wallet
  When I perform a "Stock Business Transaction" operation with the value of "X" in the Crypto Broker Wallet
    And I register it as a "Type of Stock"
  Then the "Stock Business Transaction" should add a credit in my "Stock Wallet" with the value of "X"
    And the consolidated balance for the "Stock Wallet" should be "X"
Permutations:
|Type of Merchandise  |Type of Stock  |Stock Business Transaction                          |Stock Wallet          |
|Fiat Money           |Cash           |Cash Money Stock Repenishment Business Transaction  |Cash Money Wallet CSH |
|Fiat Money           |Bank           |Bank Money Stock Repenishment Business Transaction  |Bank Money Wallet BNK |

Scenario: Fiat Offline Replenishment over an existing Stock
  Given I have a Stock in my Crypto Broker Wallet for a "Type of Merchandise"
    And the value of the Stock is "X"
  When I perform a "Stock Business Transaction" with the value of "Y" in the Crypto Broker Wallet
    And I register it as a "Type of Stock"
  Then "Stock Business Transaction" should add a credit in my "Stock Wallet" with the value of "Y"
    And the consolidated balance for the "Stock Wallet" should be "X+Y"
Permutations:
|Type of Merchandise  |Type of Stock  |Stock Business Transaction                          |Stock Wallet          |
|Fiat Money           |Cash           |Cash Money Stock Repenishment Business Transaction  |Cash Money Wallet CSH |
|Fiat Money           |Bank           |Bank Money Stock Repenishment Business Transaction  |Bank Money Wallet BNK |
