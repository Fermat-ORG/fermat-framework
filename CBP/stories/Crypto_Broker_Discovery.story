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

Scenario: Crypto Customer selects a Crypto Broker
  Given I am visualizing the list of discovered Crypto Brokers in the Crypto Broker SubApp
  When I select a Crypto Broker from the list
    And I confirm my selection
  Then Crypto Broker Actor Java should set the selected Crypto Broker as my Broker for Business Transactions

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
