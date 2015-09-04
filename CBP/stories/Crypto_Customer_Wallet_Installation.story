Story: Crypto Customer Wallet Installation

In order to buy money from the Crypto Brokers who work through Fermat
 I need to install a Crypto Customer Wallet
Using the Wallet Store SubApp

Scenario: First Crypto Customer Wallet Installation
  Given I haven't installed any Crypto Customer Wallets
  When I want to install a Crypto Customer Wallet
  Then Fermat should install the selected Crypto Customer Wallet
    And the Crypto Broker SubApp
    And the Wallet Manager SubApp
    And the SubApp Manager SubApp

Scenario: Following Crypto Customer Wallet Installation
  Given I have already installed one Crypto Customer Wallet
  When I want to install a different Crypto Customer Wallet
  Then Fermat should install the selected Crypto Customer Wallet

Scenario: Crypto Customer Wallet Installation for a Crypto Broker
  Given I have already installed one Crypto Broker Wallet
    And I haven't installed any Crypto Customer Wallets
  When I want to install a different Crypto Customer Wallet
  Then Fermat should install the selected Crypto Customer Wallet
