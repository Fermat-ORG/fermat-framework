Story: Crypto Broker Wallet Installation

In order to perform activities of money brokerage through Fermat
 I need to install a Crypto Broker Wallet
Using the Wallet Store SubApp

Scenario: First Crypto Broker Wallet Installation
  Given I haven't installed any Crypto Broker Wallet
    And I haven't installed any Crypto Customer Wallet
  When I want to install a Crypto Broker Wallet in the Wallet Store
  Then Fermat should install the selected Crypto Broker Wallet
    And the Crypto Broker SubApp Android
    And the Customers SubApp Android
    And the Wallet Manager SubApp Android
    And the SubApp Manager SubApp Android

Scenario: Crypto Broker Wallet Installation for an existing Crypto Customer
  Given I haven't installed any Crypto Broker Wallet
    And I have already installed a Crypto Customer Wallet
  When I want to install a Crypto Broker Wallet
  Then Fermat should install the selected Crypto Broker Wallet
    And the Customers SubApp Android

Scenario: Following Crypto Broker Wallet Installations
  Given I have already installed one or more Crypto Broker Wallets
  When I want to install a different Crypto Broker Wallet
  Then Fermat should install the selected Crypto Broker Wallet
