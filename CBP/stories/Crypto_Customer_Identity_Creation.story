Story: Crypto Customer Identity Creation

In order to use any Crypto Customer Wallet
  I need to have an Identity as a Crypto Customer
Using the Crypto Customer Identity SubApp Android
  And the Crypto Customer Identity SubApp Module Plugin

Scenario: First Crypto Customer Identity
  Given I don't have any Crypto Customer Identities in Fermat
  When I want to create an Identity in the Crypto Customer Identity SubApp Android
    And I provide an Alias
    And I provide a Photo
  Then the Crypto Customer Identity SubApp Module Plugin should Create a Private Key
    And Derive a Public Key
    And associate them with the Alias and the Photo
    And the Crypto Customer Identity SubApp Android should display it in a list of My Identities

Scenario: Multiple Crypto Customer Identities
  Given I have already created a Crypto Customer Identity
  When I want to create an Identity in the Crypto Customer Identity SubApp Android
    And I provide an Alias
    And I provide a Photo
    And the Alias has not been used before in my device
  Then the Crypto Customer Identity SubApp Module Plugin should Create a Private Key
    And Derive a Public Key
    And associate them with the Alias and the Photo
    And the Crypto Customer Identity SubApp Android should display it in a list of My Identities

Scenario: Creation of a duplicated Crypto Customer Identity
  Given I have already created a Crypto Customer Identity
  When I want to create an Identity in the Crypto Customer Identity SubApp Android
    And I provide an Alias
    And I provide an Image
    And the Alias has already been used in my device
  Then the Crypto Customer Identity SubApp Module Plugin should not be able to create a new Crypto Customer Identity
    And the Crypto Customer Identity SubApp Android should display an error message
