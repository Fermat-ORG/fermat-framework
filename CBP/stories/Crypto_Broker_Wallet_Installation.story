Story: Crypto Customer Wallet Installation

In order Comprar mercancia a un Crypto Brokers que trabaje con Fermat
 I need instalar a Crypto Broker Wallet
Using the Wallet Store SubApp
  And the Wallet Manager Desktop
  And the Wallet Manager Desktop Module
  And the Wallet Manager Middleware Plugin
  And the Wallet Resources Network Service Plugin WPD
  And the Wallet Runtime Engine WPD
  And the SubApp Manager Middleware Plugin

Scenario: Primera Crypto Broker Wallet Installation
  Given No tengo instalada una Crypto Broker Wallet
  When Debo instalar una Crypto Broker Wallet
  Then la Wallet Store SubApp le pasa el enlace al Wallet Manager Desktop
    And la Wallet Manager Desktop le pasa los requerimiento al Wallet Manager Desktop Module
    And la Wallet Manager Middleware plugin solicita los recursos a la Wallet Resources Network Service Plugin WPD
    And cuando el Wallet Resources Network Service Plugin WPD termina de cargar los recursos manda a ejecutar al Wallet Runtime Engine Plugin WPD
    And la Wallet Runtime Engine Plugin WPD finaliza la instalacion de la Crypto Broker Wallet
    And el SubApp Manager Middleware Plugin escucha el evento del Wallet Manager Desktop Module indicando que se instalo una Crypto Broker Wallet
    And el SubApp Manager Middleware Plugin instala la SubApp Manager Desktop
    And el SubApp Manager Middleware Plugin instala la Crypto Broker Community SubApp
    And el SubApp Manager Middleware Plugin instala la Crypto Broker Identity SubApp
    And el SubApp Manager Middleware Plugin instala la Customers SubApp

Scenario: Instalacion de otra Crypto Broker Wallet
  Given Ya he instalado una Crypto Broker Wallet
  When Debo instalar una diferente Crypto Broker Wallet
  Then  la Wallet Store SubApp le pasa el enlace al Wallet Manager Desktop
    And la Wallet Manager Desktop le pasa los requerimiento al Wallet Manager Desktop Module
    And la Wallet Manager Middleware Plugin solicita los recursos a la Wallet Resources Network Service Plugin WPD
    And cuando el Wallet Resources Network Service Plugin WPD termina de cargar los recursos manda a ejecutar al Wallet Runtime Engine WPD
    And el Wallet Runtime Engine WPD finaliza la instalacion de la Crypto Broker Wallet
