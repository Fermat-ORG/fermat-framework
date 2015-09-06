Story: Crypto Broker Discovery

In order to buy merchandise from a Crypto Broker
  As a Crypto Customer
  I need to get select a Crypto Broker to Buy from
Using the Crypto Broker SubApp Android plugin
  And the Crypto Broker SubApp Module Java plugin
  And the Crypto Broker Actor Java plugin
  And the Crypto Broker Network Service Java plugin

Scenario: Crypto Customer has not interacted with a Crypto Broker
  Given I haven't performed any Business Transactions with a Crypto Broker
  When I access the list of Crypto Brokers in the Crypto Broker SubApp
  Then the Crypto Broker Actor Java should obtain a list of Crypto Broker Public Identities from the Crypto Broker Network Service
    And the Crypto Broker SubApp should get a list of Crypto Broker Public Identities from the Crypto Broker Actor Java
    And I should visualize the Public Identities of the Crypto Brokers sorted by proximity to my location

Scenario: Crypto Customer has already interacted with Crypto Brokers
  Given I have already performed one Business Transactions with a Crypto Broker
  When I access the list of Crypto Brokers in the Crypto Broker SubApp
  Then I should get 2 lists of Crypto Brokers from the Crypto Broker Actor Java
    And the first list should be of the Crypto Brokers with whom I've already established contact
    And the second list should be of all the Crypto Brokers sorted by proximity to my location
    And in each list I should visualize the Public Identities of the Crypto Brokers


Scenario: Crypto Customer selects a Crypto Broker y este lo acepta
  Given I am visualizing the list of discovered Crypto Brokers in the Crypto Broker SubApp
    And Información del stock de cada Cryto Broker proporcionada por el Cryto Broker Identity Wallet Linker
   When I select a Crypto Broker from the list
    And I confirm my selection
    And El Crypto Broker recibe a través del Crypto Broker Network Service una solicitud para asociarce al Crypto Customer que lo selecciono
    And El Crypto Broker acepta la solicitud del Crypto Customer desde Crypto Broker SubApp Module
    And El Crypto Customer recibe a través del Crypto Broker Network Service la confirmacion del Crypto Broker
   Then Crypto Customer Actor Java should set the selected Crypto Broker as my Broker for Business Transactions
    And Crypto Broker Actor Java establece al Crypto Customer como un cliente para realizar Business Transactions

Scenario: Crypto Customer selects a Crypto Broker y este lo rechaza
  Given I am visualizing the list of discovered Crypto Brokers in the Crypto Broker SubApp
    And Información del stock de cada Cryto Broker proporcionada por el Cryto Broker Identity Wallet Linker
   When I select a Crypto Broker from the list
    And I confirm my selection
    And El Crypto Broker recibe a través del Crypto Broker Network Service una solicitud para asociarce al Crypto Customer que lo selecciono
    And El Crypto Broker rechaza la solicitud del Crypto Customer desde Crypto Broker SubApp Module


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