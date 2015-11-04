Story: Crypto Customer Wallet Installation

In order to buy money from the Crypto Brokers who work through Fermat
 I need to install a Crypto Customer Wallet
Using the Wallet Store SubApp
  And the Wallet Manager Desktop
  And the Wallet Manager Desktop Module
  And the Wallet Manager Middleware Plugin
  And the Wallet Resources Network Service Plugin WPD
  And the Wallet Runtime Engine WPD
  And the SubApp Manager Middleware Plugin

Scenario: First Crypto Customer Wallet Installation
  Given I haven't installed any Crypto Customer Wallets
  When I want to install a Crypto Customer Wallet
  Then la Wallet Store SubApp le sede el control al Wallet Manager Desktop
    And la Wallet Manager Desktop le pasa el control al Wallet Manager Desktop Module plugin
    And la Wallet Desktop Manager Module plugin le pasa el control al Wallet Manager Middleware plugin
    And la Wallet Manager Middleware plugin pide los recursos de la Crypto Customer Wallet al Wallet Resources Network Service Plugin WPD
    And cuando el Wallet Resources Network Service Plugin WPD termina de cargar los recursos manda a ejecutar al Wallet Runtime Engine WPD
    And la Wallet Runtime Engine WPD finaliza la instalacion de la Crypto Customer Wallet
    And el Wallet Manager Middleware Plugin escucha el evento del Wallet Runtime Engine WPD indicando que se instalo una Crypto Customer Wallet
    And el Wallet Manager Desktop Module Plugin instala la Crypto Broker SubApp

Scenario: Following Crypto Customer Wallet Installation
  Given I have already installed one Crypto Customer Wallet
  When I want to install a different Crypto Customer Wallet
  Then la Wallet Store SubApp le sede el control al Wallet Manager Desktop
    And el Wallet Manager Desktop le pasa el control al Wallet Manager Desktop Module plugin
    And el Wallet Manager Desktop Module plugin pide los recursos de la Crypto Customer Wallet al Wallet Resources Network Service Plugin WPD
    And cuando el Wallet Resources Network Service Plugin WPD termina de cargar los recursos manda a ejecutar al Wallet Runtime Engine WPD
    And el Wallet Runtime Engine WPD finaliza la instalacion de la Crypto Customer Wallet

Scenario: Crypto Customer Wallet Installation for a Crypto Broker
  Given I have already installed one Crypto Broker Wallet
    And I haven't installed any Crypto Customer Wallets
  When I want to install a Crypto Customer Wallet
  Then la Wallet Store SubApp le sede el control al Wallet Manager Desktop
    And el Wallet Manager Desktop le pasa el control al Wallet Manager Desktop Module plugin
    And el Wallet Manager Desktop Module plugin pide los recursos de la Crypto Customer Wallet al Wallet Resources Network Service Plugin WPD
    And cuando el Wallet Resources Network Service Plugin WPD termina de cargar los recursos manda a ejecutar al Wallet Runtime Engine WPD
    And el Wallet Runtime Engine WPD finaliza la instalacion de la Crypto Customer Wallet
