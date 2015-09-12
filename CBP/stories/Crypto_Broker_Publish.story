# Este escenario esta mal estructurado
Escenario: Publicacion de Informacion del broker
  Dado que como Crypto Broker deseo darme a conocer en la comunidad para ofercer mis servicios
  Cuando decido publicar mi identidad como broker para ser descubierto por un Crypto Customer
  Entonces voy a la Subapp del Crypto Broker
    Y establesco que datos de mercado deseo mostrar a los Crypto Customers (moneda de referencia, mercaderia, montos maximos, metodos y monedas que acepta de pago)
    Y en la Subapp del Crypto Broker Selecciono la opcion de publicar mi informacion en la plataforma
    Y la SubApp del Crypto Broker le cede el control al Module de la Subapp del Crypto Broker
    Y el Module de la Subapp del Cryto Broker invoca al Plugin Crypto Broker Identity Wallet Linker
    Y el Plugin Crypto Broker Identity Wallet Linker registra los datos de mercado deseo mostrar con mi identidad
    Y el Module de la Subapp del Cryto Broker invoca al Plugin Crypto Broker de la capa Actor
    Y el Plugin Crypto Broker de la capa Actor registra mi identidad
    Y el Plugin Crypto Broker de la capa Actor se encarga de activar mi identidad colocandome en linea
    Y el Plugin Crypto Broker de la capa Actor le notifica el Module de la SubApp del Crypto Broker que estoy en linea
    Y el Module de la SubApp del Crypto Broker hace que la SubApp del Crypto Customer me muestre en la lista de brokers que puede consultar el crypto customer

........................

Story: Crypto Broker Publish

Identity o Community?

In order to be discovered by Crypto Customers
  As a Crypto Broker
  I need to publish my Public Identity
  And my sale prices
Using the Crypto Broker SubApp Android
  And the Crypto Broker SubApp Module Java Plugin
  And the Crypto Broker Network Service Java Plugin
  And the Crypto Broker Agent Java Plugin
  And the Fiat Index World Java Plugin
  And the Market Index World Java Plugin

#Este escenario esta mal estructurado
Scenario: Public Information Publish
  Given I have Crypto Broker Identity
  When I decide to publish my Crypto Broker Identity in the Crypto Broker SubApp
    And I select the "Fiat Money Type for Price Reference"
    And I select the "Stocked Fiat Money Types"
    And I select the "Stocked Market Money Types"
    And I select the "Fiat Money Types for Payment"
    And I select the "Market Money Types for Payment"
    And I specify the maximum "Fiat Money Amount" that I support per operation
    And I specify the maximum "Market Money Amount" that I support per operation
  Then the Crypto Broker SubApp obtains the exchange rates between my "Stocked Fiat Money" and the "Fiat Money Type for Price Reference" from the Fiat Index World
    And the Crypto Broker SubApp obtains the exchange rates between my "Stocked Market Money" and the "Fiat Money Type for Price Reference" from the Market Index World
    And the Crypto Broker SubApp should register this information in the Fermat Network through the Crypto Broker Network Service

Scenario: Business Transaction Public Information Update
  Given I have performed a "Business Transaction"
    And the "Business Transaction" is completed
  When the Crypto Broker Agent consolidates the data of the Business Transactions
  Then the Crypto Broker Agent should update the exchange rates between my "Stocked Fiat Money" and the "Fiat Money Type for Price Reference" in my Crypto Broker Public Identity
    And the Crypto Broker Agent should update the exchange rates between my "Stocked Fiat Money" and the "Fiat Money Type for Price Reference" in my Crypto Broker Public Identity
    And the Crypto Broker Agent should publish this update through the Crypto Broker Network Service

--------

Public Identity Crypto Broker Example:
- Photo
- Alias
- Market Money exchange rates
  - Bitcoin: 230 USD
  - Litecoin: 10 USD
- Fiat Money exchange rates
  - Bolivares: 630 x 1 USD
  - Pesos Argentino: 14 x 1 USD
  - Euros: 1,2 USD
- Market Money Accepted Payments
  - Bitcoin
  - Litecoin
  - Ethereum
- Fiat Money Accepted Payments
  - Forines Hungaros
  - Dolares Estadounidenses
  - Pesos Colombianos
: Informacion Publica, Conexiones Establecidas  
--------
