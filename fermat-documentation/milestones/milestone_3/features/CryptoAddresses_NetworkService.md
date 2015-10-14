# Crypto Addresses Network Service
* Maybe we have to change the name of this network service.
* The Network Service pretends to negotiate contact relationships.
* When an Intra-User wants to add another Intra-User like Contact, he sends a Contact Request with one of his addresses.
 * Attached to the address, some data is sended to analyze the compatibility of the wallets of the other user (the one who receives the request).
* When a request arrives, an event with the request data is raised to the Wallet Manager of the Middleware Layer. This one, reads the data and find the compatible wallets and informs that information to the NS.
* With all this information, the NS raise an event to the Graphic Interface, awaiting for an user response (deciding in which wallet add the contact).
* The user can only Accept or Deny the request.

## Scenarios
* Send a Contact Request
 * The user is logged throw an Intra-User Identity.
 * He decides to search an Intra-User Actor in the Platform for add him to Contacts.
 * He finds the actor.
 * He add the actor.
 * The actor must be agree with this.
 * Then:
   * If the actor is already a connection.
   * Then he haves to send an address to the actor, and wait for the actor's address.
   * If the actor isn't a connection.
   * Then he haves to send an address to the actor requesting to be a connection, and wait for the actor's address and his acceptance.

* Receive an Acceptance for a Contact Request
 * The actor informs an acceptance and converts in a Contact.

* Receive a Denial for a Contact Request
 * The actor informs a denial, the user will not be reported.

* Receive a Contact Request
 * The user receives a Contact Request.

 * He is informed in the graphic interface by showing him a profile imageMiddleware and the name of the actor.

 * He also can accept or deny the request.

* Accept a received Contact Request
 * The actor must be informed of the acceptance.

* Deny a received Contact Request
 * The actor must be informed of the denial.

* List Contact Requests
 * The actor can list the contact requests:
   * Received.
   * Sended.
   * Accepted.

* Get Contact Request
 * The requests can be consulted throw its id and returns all the information.

* Confirm Contact Request
 * Once the contact request has decided his destiny is confirmed and deleted.

## Flujos
* Send a Contact Request
 * The network service is called throw method <code>createContactRequest</code> and retrieve the next information:
   * walletPublicKey - wallet which is sending the request
   * referenceWallet - type of reference wallet that is sending the request
   * cryptoAddressToSend - a cryptoaddress generated that is sended to the actor
   * intraUserToContactPublicKey - the intra-user with whom is wanted to be contacts
   * requesterIntraUserPublicKey - the intra-user public key that is sending the request
   * requesterIntraUserName - the intra-user name that is sending the request

   * requesterIntraUserProfileImage - the intra-user profile imageMiddleware that is sending the request

 * The network sevice saves the data in the actions table with a requestId (a random UUID) and a State: PENDING_REMOTE_ACCEPTANCE.
 * Then send all the information including the requestId generated to the counterparty.

* Receive an Acceptance for a Contact Request
 * The actor informs an acceptance.
 * The network service receive the acceptance.
 * The information that receives is:
   * requestId - the id of the request sended before.
   * walletAcceptingTheRequestPublicKey - wallet which is accepting the request
   * referenceWallet - type of reference wallet which is accepting the request
   * cryptoAddressReceived - a cryptoaddress that is received
   * intraUserAcceptingTheRequestPublicKey - the intra-user public key that is accepting the request
 * The network sevice updates adding this data in the actions table and updates the State to: ACCEPTED.
 * Then raises a CONTACT_REQUEST_ACCEPTED event and post the requestId.

* Receive a Denial for a Contact Request
 * The actor informs a denial.
 * The network service receives the denial.
 * The information received is:
   * requestId - the id of the request sended before.
 * The network service deletes the record in the actions table.

* Receive a Contact Request
 * The network service receive a contact request.
 * The information that receives is:
   * requestId - the id of the request received.
   * walletPublicKey - wallet which that have sended the request
   * referenceWallet - type of reference wallet that have sended the request
   * cryptoAddressReceived - a cryptoaddress generated that by the sender
   * intraUserToContactPublicKey - the intra-user with whom the actor wants to be contact
   * requesterIntraUserPublicKey - the intra-user public key that have sended the request
   * requesterIntraUserName - the intra-user name that have sended the request

   * requesterIntraUserProfileImage - the intra-user profile imageMiddleware that have sended the request

 * The network sevice saves the data in the actions table with the State: PENDING_LOCALLY_ACCEPTANCE.
 * Then raises a CONTACT_REQUEST_RECEIVED event and post the requestId.

* Accept a received Contact Request
 * The network service is called throw method <code>acceptContactRequest</code> and receive by parameter the next information:
   * requestId - the id of the request received.
   * walletAcceptingTheRequestPublicKey - wallet that accepted the request
   * referenceWallet - type of reference wallet that accepted the request
   * cryptoAddressToSend - a cryptoaddress to send to the counterparty
   * intraUserAcceptingTheRequestPublicKey - the intra-user that accepted the request
 * The network service will send the information to the counterparty.
 * If it's successful deletes the data in the actions table.

* Deny a received Contact Request
 * The network service is called throw method <code>denyContactRequest</code> and receive by parameter the next information:
   * requestId - the id of the request received.
 * The network service will send the information to the counterparty.
 * If it's successful deletes the record in the actions table.

* List Contact Requests
 * The network service is called throw method <code>listContactRequests</code> and receive by parameter the next information:
   * intraUserLoggedInPublicKey - is the intra-user identityPublicKey public key of the logged in intra-user
   * walletPublicKey - is the public Key of the wallet where the user is working in
   * contactRequestState - is the State of the contact requests that you want to list.
 * The network service will return the contact requests that match with that state, if the state is null, the method returns everything.

* Get Contact Request
 * The network service is called throw method <code>getPendingRequest</code> and receive by parameter the next information:
   * requestId - the id of the request received.
 * The requests can be consulted throw its id.

* Confirm Contact Request
 * The network service is called throw method <code>confirmContactRequest</code> and receive by parameter the next information:
   * requestId - the id of the request received.
 * The network serviec deletes the record in the actions table.

## Persistance
* All the images will be saved in files with the publicKey name of the entity like name.
* All the actions will be saved in the Contacts Actions Table
* The fields will be:
 * requestId - randomly generated or received from the counterparty

 -- sender fields
 * walletPublicKeyToSend - wallet which is sending the request
 * referenceWalletToSend - type of reference wallet that is sending the request
 * cryptoAddressToSend - a cryptoaddress generated that is sended to the actor
 * requesterIntraUserPublicKey - the intra-user public key that is sending the request
 * requesterIntraUserName - the intra-user name that is sending the request

 * requesterIntraUserProfileImage - the intra-user profile imageMiddleware that is sending the request


 -- receiver fields
 * walletAcceptingTheRequestPublicKey - wallet which is accepting the request
 * referenceWalletReceived - type of reference wallet which is accepting the request
 * cryptoAddressReceived - a cryptoaddress that is received when the intra-user accepts the request
 * intraUserAcceptingTheRequestPublicKey - the intra-user public key that is accepting the request

 * state - the state of the request: PENDING_LOCALLY_ACCEPTANCE - PENDING_REMOTE_ACCEPTANCE - ACCEPTED

## Future tasks
* Update the crypto address for a contact.

## Other Information
* The information of wallet compatibilities will be given throw the wallet module, when the user decides to do something with the request in the graphic interface.