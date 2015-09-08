Story: Crypto Customer Wallet Installation

In order to buy money from the Crypto Brokers who work through Fermat
 I need to install a Crypto Broker Wallet
Using the Wallet Store SubApp
  And the Wallet Manager SubApp (Desktop)
  And the Wallet Manager Module plugin
  And the Wallet Resources Plugin
  And the Wallet Runtime (es independiente de la plataforma) 
  And the SubApp Manager Module Plugin

Scenario: First Crypto Broker Wallet Installation
  Given I haven't installed any Crypto Broker Wallets
  When I want to install a Crypto Broker Wallet
  Then la Wallet Store SubApp le sede el control al Wallet Manager SubApp
    And la Wallet Manager SubApp le pasa el control al Wallet Manager Module plugin
    And la Wallet Manager Module plugin instala pide los recursos de la Crypto Broker Wallet al Wallet Resources Plugin
    And cuando el Wallet Resources Plugin termina de cargar los recursos manda a ejecutar al Wallet Runtime
    And la Wallet Runtime finaliza la instalacion de la Crypto Broker Wallet
    And el SubApp Manager Module Plugin escucha el evento del Wallet Manager Module Plugin indicando que se instalo una Crypto Broker Wallet
    And el SubApp Manager Module Plugin instala la Crypto Broker SubApp
    And el SubApp Manager Module Plugin instala la Crypto Customers SubApp

Scenario: Following Crypto Broker Wallet Installation
  Given I have already installed one Crypto Broker Wallet
  When I want to install a different Crypto Broker Wallet
  Then la Wallet Store SubApp le sede el control al Wallet Manager SubApp
    And el Wallet Manager SubApp le pasa el control al Wallet Manager Module plugin
    And el Wallet Manager Module plugin instala pide los recursos de la Crypto Broker Wallet al Wallet Resources Plugin
    And cuando el Wallet Resources Plugin termina de cargar los recursos manda a ejecutar al Wallet Runtime
    And el Wallet Runtime finaliza la instalacion de la Crypto Broker Wallet

Scenario: Crypto Broker Wallet Installation for a Crypto Crypto Customer
  Given I have already installed one Crypto Customer Wallet
    And I haven't installed any Crypto Broker Wallets
  When I want to install a Crypto Broker Wallet
  Then la Wallet Store SubApp le sede el control al Wallet Manager SubApp
    And el Wallet Manager SubApp le pasa el control al Wallet Manager Module plugin
    And el Wallet Manager Module plugin instala pide los recursos de la Crypto Broker Wallet al Wallet Resources Plugin
    And cuando el Wallet Resources Plugin termina de cargar los recursos manda a ejecutar al Wallet Runtime
    And el Wallet Runtime finaliza la instalacion de la Crypto Broker Wallet
