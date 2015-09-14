Story: Crypto Broker Wallet Installation

In order Comprar mercancia a un Crypto Brokers que trabaje con Fermat
 I need to install a Crypto Broker Wallet
Using the Wallet Store SubApp
  And the Wallet Manager Desktop
  And the Wallet Manager Desktop Module
  And the Wallet Manager Middleware Plugin
  And the Wallet Resources Network Service Plugin WPD
  And the Wallet Runtime Engine WPD
  And the SubApp Manager Middleware Plugin

Scenario: First Crypto Broker Wallet Installation
  Given I dont have installed a Crypto Broker Wallet
  When I want to install a Crypto Broker Wallet
  Then the Wallet Store SubApp pass the control to the Wallet Manager Desktop
    And the Wallet Manager Desktop pass the requeriment to the Wallet Manager Desktop Module
    And the Wallet Manager Middleware plugin request the resources to the Wallet Resources Network Service Plugin WPD
    And the Wallet Resources Network Service Plugin WPD execute the Wallet Runtime Engine Plugin WPD when the loading of resurces is done
    And the Wallet Runtime Engine Plugin WPD finish the installation of the Crypto Broker Wallet
    And the SubApp Manager Middleware Plugin listen the event of the Wallet Manager Desktop Module the says that a Crypto Broker Wallet has been installed
    And the SubApp Manager Middleware Plugin install the SubApp Manager Desktop
    And the SubApp Manager Middleware Plugin install the Crypto Broker Community SubApp
    And the SubApp Manager Middleware Plugin install the Crypto Broker Identity SubApp
    And the SubApp Manager Middleware Plugin install the Customers SubApp

Scenario: Installation of another Crypto Broker Wallet
  Given I have installed a Crypto Broker Wallet
  When I want to install a diferent Crypto Broker Wallet
  Then the Wallet Store SubApp pass the control to the Wallet Manager Desktop
    And the Wallet Manager Desktop pass the requeriment to the Wallet Manager Desktop Module
    And the Wallet Manager Middleware plugin request the resources to the Wallet Resources Network Service Plugin WPD
    And the Wallet Resources Network Service Plugin WPD execute the Wallet Runtime Engine Plugin WPD when the loading of resurces is done
    And the Wallet Runtime Engine Plugin WPD finish the installation of the Crypto Broker Wallet
