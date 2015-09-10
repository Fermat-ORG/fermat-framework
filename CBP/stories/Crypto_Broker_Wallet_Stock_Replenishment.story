Story: Crypto Broker Wallet Stock Replenishment

In order to perform the activities of a Crypto Broker
  As a Crypto Broker
  I need to Replenish my Stock
Using the Crypto Broker Wallet SubApp Android
  And the Crypto Broker Wallet Module Java

Scenario: Initial Replenishment of Stock
  Given I haven't stocked any value for a "Type of Merchandise" in my Crypto Broker Wallet
  When I perform a "Stock Business Transaction" operation with the value of "X" in the Crypto Broker Wallet
    And I register it as a "Type of Stock"
  Then the "Stock Business Transaction" should add a credit in my "Stock Wallet" with the value of "X"
    And the consolidated balance for the "Stock Wallet" should be "X"
Permutations:
  |Type of Merchandise  |Type of Stock  |Stock Business Transaction                            |Stock Wallet                     |
  |Market Money         |Crypto         |Stock Market Crypto Business Transaction Java plugin  |Market Crypto Wallet Java plugin |
  |Fiat Money           |Cash           |Stock Fiat Cash Business Transaction Java plugin      |Fiat Cash Wallet Java plugin     |
  |Fiat Money           |Bank           |Stock Fiat Bank Business Transaction Java plugin      |Fiat Bank Wallet Java plugin     |

Scenario: Replenishment over an existing Stock
  Given I have a Stock in my Crypto Broker Wallet for a "Type of Merchandise"
    And the value of the Stock is "X"
  When I perform a "Stock Business Transaction" with the value of "Y" in the Crypto Broker Wallet
      And I register it as a "Type of Stock"
  Then "Stock Business Transaction" should add a credit in my "Stock Wallet" with the value of "Y"
    And the consolidated balance for the "Stock Wallet" should be "X+Y"
Permutations:
  |Type of Merchandise  |Type of Stock  |Stock Business Transaction                            |Stock Wallet                     |
  |Market Money         |Crypto         |Stock Market Crypto Business Transaction Java plugin  |Market Crypto Wallet Java plugin |
  |Fiat Money           |Cash           |Stock Fiat Cash Business Transaction Java plugin      |Fiat Cash Wallet Java plugin     |
  |Fiat Money           |Bank           |Stock Fiat Bank Business Transaction Java plugin      |Fiat Bank Wallet Java plugin     |
