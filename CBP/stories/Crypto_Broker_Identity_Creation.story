Story: Crypto Broker Identity Creation

In order to use any Crypto Broker Wallet
  I need to have an Identity as a Crypto Broker
Using the Crypto Broker SubApp Android plugin
  And the the Crypto Broker SubApp Module Java plugin
  And the Crypto Broker Identity Java plugin

Scenario: First Crypto Broker Identity
  Given I don't have any Crypto Broker Identities in Fermat
  When I want to create an Identity in the Crypto Broker SubApp
    And I provide an Alias
    And I provide a Photo
  Then the Crypto Broker Identity Java Plugin should Create a Private Key
    And Derive a Public Key
    And associate them with the Alias and the Photo
    And the Crypto Broker SubApp should display it in a list of My Identities

Scenario: Multiple Crypto Broker Identities
  Given I have already created a Crypto Broker Identity
  When I want to create an Identity in the Crypto Broker SubApp
    And I provide an Alias
    And I provide a Photo
    And the Alias has not been used before in my device
  Then the Crypto Broker Identity Java Plugin should Create a Private Key
    And Derive a Public Key
    And associate them with the Alias and the Photo
    And the Crypto Broker SubApp should display it in a list of My Identities

Scenario: Creation of a duplicated Crypto Broker Identity
  Given I have already created a Crypto Broker Identity
  When I want to create an Identity in the Crypto Broker SubApp
    And I provide an Alias
    And I provide an Image
    And the Alias has already been used in my device
  Then the Crypto Broker Identity Java Plugin should not be able to create a new Crypto Broker Identity
    And the Crypto Broker SubApp should display an error message
