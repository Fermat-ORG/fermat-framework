## Fermat: A Trust-less Financial Application Framework

Luis Fernando Molina

luis.molina@bitDubai.com

<br>
### Abstract

A trust-less financial application framework would allow standalone crypto wallets to evolve into any kind of trust-less financial applications. Crypto networks provide part of the solution, but the main benefits are lost if a trusted third party is still required to transport meta-data, synchronize devices, hold wallet files or keys, manage identities, interface crypto networks or the legacy financial system.

We propose a Peer-to-Peer network for transporting meta-data and inter-connect clients between each other. A synchronization scheme complements it, transforming a standalone app into a distributed application across several devices still owned by the same user.

The framework replaces the standalone wallet and enables the development of trust-less financial applications that are both crypto-currency and digital-asset-enabled.

<br>
### Introduction

Standalone bitcoin wallets were the first trust-less financial applications since they required not to trust any third party inheriting this property from the bitcoin network itself. As the ecosystem evolved trustfull third parties were introduced again and they took over the wallet space because of technical capabilities that are easier to be built in a centralized way: communication between wallets, synchronization between devices, interfacing the legacy financial system, etc., and they consistently took the biggest share of funding, leaving standalone wallets far behind and at the same time trashing the trust-less property of bitcoin, one of its key features. Applications trying to use the blockchain to transport meta-data were considered spammers and trust-less wallets were effectively left behind.

What is needed on top of all existing protocols is a layer that faces the End User and that finishes the job bitcoin started. By using crypto networks for transporting value or as a registry for digital assets and the Fermat Network for transporting the required meta-data at a client level, would allow financial apps to run any user-level interconnected-functionality without ever going through a trusted third party network or server.

A plug-in architecture allows any developer to add their own reusable plug-ins. A micro-use-licensing-scheme enforced by the system itself guarantees plug-in, wallet, and app developers a revenue stream. OS dependent GUI components are built on top of the multi-layered plug-in structure to face the end user as niche-wallets or financial applications in general. Apps and wallets with similar functionality are wrapped into platforms, each one introducing new actors and plug-ins, to the ever increasing functionality of the whole system.

A built-in _wallet-factory_ allows developers to reuse the highest level components and create niche-wallets or niche-financial-apps by combining existing functionality and adding their own code to the combo. A built-in _wallet-editor_ allows non-developers to reuse any of these niche-wallets to build new branded-wallets just by changing their look and feel. A built-in _p2p-wallet-store_ allows end users to choose which wallets or financial apps to install from the ever growing catalog.

<br>
### Framework

The solution we propose begins with a Framework that must be portable into different OS. On a multi-layered format, the bottom most layer is interfacing the OS and must be built with replaceable components implementing the same set of public interfaces (polymorphism) in order to build on top of it a single set of OS-independent components. At the same time, the upper most layers are again OS-dependent, providing a native GUI on each device.

We identify 3 different kind of components that we arbitrarily call **Add-ons**, **Plug-ins**, and **GUI** components. We define Add-ons as low level components that do not need to identify themselves to consume services from other components. They have broad access to the file system and databases. Plug-ins have their own identity and must identify themselves to other components to use their services which in turn restrict the scope of their services based on the caller's identity (for example the filesystem addon would only give access to the Plug-in's own folder structure, the database system addon would only give access to the plugin's own databases, and so on). This way we handle the problem of running Plug-ins from untrusted sources.

The Framework core is in charge of initializing Add-ons and Plug-ins and managing Plug-ins identities. An internal API library defines the public interfaces that each component exposes to the rest of the components within the same device in order to allow them to consume their services locally. This provides a strong encapsulation of each component's business logic allowing them to freely define their internal structure and data model.

<br>
### Crypto Networks

A set of Plug-ins is needed for each crypto network to be supported. One for interfacing the network, pushing outgoing transactions and monitoring incoming transactions. Another couple being the digital vaults where the crypto currency value and digital assets are stored.

Wallets are higher level abstractions and have their own set of Plug-ins for keeping the accounting of each kind of them. This means that we split the accounting from the handling of the value by having components at different layers to handle each activity.

<br>
### Fermat Network

The network is intended for two main purposes: 

a. clients finding other clients

b. clients calling other clients.  

Every Node has a copy of a distributed geo-localized inventory of all network nodes. They run a protocol that allows them to keep their copy synchronized. 

An **actor** is a type of role an End User might play and in this context represents one of the End Users identities.
 
To be able to be found, a network client checks itself and its actors in with the geographically closest node. We call this the _Home Node_. When an actor needs to find another actor, it follows a protocol that requires the approximate location of the actor it is willing to connect to. This is possible because the system deals with End Users and usually people know the city, state or country where the people they know actually live. This way we avoid using phone numbers, emails or any other possible personal identifier that could compromise End Users privacy.

For scalability reasons, we moved the responsibility of finding actors within the network, from the network itself to the network clients. Each node has a distributed catalog of Actors and is responsible to keep it updated. But instead of every node having the full catalog, only a set of nodes nearby the _Home Node_ of an actor knows that actor and which node is its home. This allows any actor who knows the approximate location of the actor it is searching for to easily find a trace and follow it to the current _Home Node_ where its friend is checked in, and stablish a connection between them. 

<br>
### Incentive

#### To developers

Plug-in developers declare a _Micro-Use-License_ for each plug-in they add to the Framework. Wallet or Financial Apps developers declare a _Micro-Use-License_ for their components. End users install the Apps (wallets) of their choice. The license to be paid is the summary of the App's _Micro-Use-License_ plus all the _Micro-Use-Licenses_ of the plug-ins used by that App. 

The Framework is responsible to enforce the license agreements, charge the end user and distribute the payments to all developers involved.

#### To network nodes

Network clients establishes a _Home Node_ where they check themselves and their actors in so as to be found by other network clients. They must pay a subscription fee to their _Home Node_ for its services. Finding and calling other clients through other nodes is free for the caller. Those nodes income is covered by those network clients for whom they act as their _Home Node_.

<br>
### Platforms

We define as a _Platform_  a set of interrelated functionality. _Platforms_ may consume services from other _platforms_ and their dependencies form a hierarchical stack. 

Each _Platform_ may introduce to the system new workflows, Add-ons, Plug-ins, GUI components (Apps, wallets) and Actors. This enables the system to target different use cases with different actors involved. 

<br>
### Identities

We handle identities at different levels for multiple purposes. In all cases identities are represented by private and public keys.

#### End User Identities

The need to handle multiple logins on the same device brings with it the first kind of identity which we call _device-user_. This identity lives only at a certain device and not even the public key is exposed to the network.

Besides, the End Users can have multiple types of identities (we call this _Actors_), and within each type as many instances as they want. Each type of identity corresponds to a role in real life or an actor in a Use Case. Usually each Platform introduces a set of actors and all the Platform's functionality orbits around all the use cases derived on the interactions between those actors. 

The Framework handles a hierarchy of identities. One of them is what we call the _root identity_. At root level End Users can set a standard set of information that can be overwritten at any level down the hierarchy, narrowing or expanding that information as needed. All these identities are exposed to the Fermat Network in a way that from the outside, no one can tell they are related between each other or to a certain End User. 

#### Components Identities

Many components have their identities for a variety of purposes:

a. Plug-ins to identify themselves to Add-ons in order to get access to identity-specific resources as Databases or their own share of the File System.

b. _Network Services_ to encrypt the communications between each other.

c. Network Clients to encrypt the communication with nodes.

d. Nodes to recognize each other even when their IP, location or other profile information changes.

<br>
### Workflows

We define workflows as high level processes that requires several components to achieve a certain goal. Manny workflows start at a GUI component triggered by the End User and spans through several Plug-ins on the same device, and in some cases jumping into other devices. Other workflows may start at some Plug-ins, triggered by events happening within the same device.

From a workflow point of view, each Plug-in executes a certain task and is fully responsible for doing its job. Workflows are a chain of tasks that may split in several paths and may span through more than one device.

In some cases workflows interconnect with each other, forming a _workflow chain_ that usually spans more than one _Platform_. 

<br>
### Transactions

#### Transactional Workflows

As the Framework runs on potentially unstable devices such as mobile phones, each Plug-in must be prepared to overcome the difficulties caused by a device shutting down at any moment and it must be able to complete later on its intended job and never to leave information on an inconsistent state. This is quite challenging but not impossible. 

The solution is to make each Plug-in responsible for the workflow while they are handling part of a transaction on a transactional workflow. So this responsibility is transferred on each step of the chain using a _Responsibility Transfer Protocol_. This means that the one who is responsible at the moment of a black out is the one which must resume and do it's best to get rid of that responsibility moving it further down the chain within the transactional workflow.
 
#### Value Transactions

We handle monetary and digital assets transactions dividing the accounting from the value. Usually transactions start on specialized Plug-ins which are in charge of coordinating the whole transaction. These Plug-ins usually interact with wallets-Plug-ins debiting or crediting the accounts involved. The accounting of the currency or digital asset involved are kept by these wallet-Plug-ins. Later the transactional workflow splits between moving the value (usually crypto currency) and moving the meta-data associated to the transaction.

Through two different paths, the value and the meta-data arrives to the recipient's device and they are combined together by the remote counter-party transaction component which in turn interacts with the remote wallet-Plug-in to record the accounting as appropriate.

<br>
### Synchronization

We define a Private Device Network as a network of devices owned by the same End User. Using the Fermat Network, the Framework synchronizes the information on all nodes of this Private Network. In this way all the information and system wide identities belonging to the End User are available at any device.

Crypto funds are kept into a Multi-Sig vault and there is a shared _Petty-Cash-Vault_ accessible from all nodes even when they are off-line from this Private Network. An automated process monitors the Petty-Cash-Vault and tops it up when needed. Several nodes must sign the top up transaction in order to proceed. In this way if a device is lost or stolen, only the Petty-Cash fund is at risk. End Users can eject stolen devices from its Private Network and if they act quickly they might be on time to re-create the Petty-Cash fund under the new configuration and save those funds.

<br>
### User Interface

The Framework handles a stack of layers. Starting down we have the _OS API level_, then the _Blockchain Level_, the _Communication Level_, _Platform Level_ up to the _User Interface Level_. With the goal in mind for allowing even non-developers to deploy their own trust-less financial applications, we define several concepts:

**Wallet**: Any kind of financial application that handles either crypto or digital assets for any purpose. 

**Reference Wallet**: A primitive wallet that is used by a single actor for a handful of use cases. 

**Niche Wallet**: A combination of several _Reference Wallets_ into a single product with its own look and feel and possibly extra functionality.

**Branded Wallet**: A _niche wallet_ turned into a new product owned by a different End User. Achieved by a process similar to building a Wordpress site but locally on the End Users device. Usually involves re-using a set of the business logic of the _niche wallet_ it derives from and a new look and feel (different skin and navigation structure).

**External Wallet**: A third party APP running on the same device that uses Fermat as a backend for different reasons. For example to benefit from its infrastructure to interface crypto networks, transporting data through its p2p network, or storing data on the End Users _Private Device Network_. 

<br>
Several tools were designed with the purpose of enabling the development of new wallets, an their distribution.

**Wallet Factory**: Is a built-in functionality that enables the development of reference and niche wallets.

**Wallet Editor**: Enables the creation by non-developers of _Branded Wallets_ based on any one of the _Niche Wallets_ available.

**Wallet Store**: Is a distributed application which manages a shared wallet catalog and enables the End User to download from peers the different wallets available for the Framework.

<br>
### Privacy

The proposed system complements the privacy properties of crypto networks, extending them to the full stack needed to run different kind of financial applications. By using its own P2P network with point to point encryption for transporting meta-data both value and information are under a similar privacy standard.

Identities are public keys related to private keys kept by the End User and never shared to anyone in any way. 

The collection of system information for visualization and statistics uses hashes of public keys to protect End User's privacy and at the same time preserve the relationships between them.


<br>
### Conclusion

We have proposed a Framework for developing and running trust-less financial applications. The Fermat Framework shows the way of how to keep the End User away from trusted third parties at a higher level. We propose a solution to several problems at the same time. The highlights of our work are:

* How to exchange meta-data in a trust-less way
* How to prevent the loss of private keys (funds and identities)
* How to maximize reusability by building with Plug-ins
* How to enable even non-developers to create and deploy their own wallets in a WordPress style. 

With this system we enable a new ecosystem of trust-less financial applications that are both crypto and digital asset enabled.

<br>
### References

[1] Satoshi Nakamoto, "Bitcoin: A Peer-to-Peer Electronic Cash System", https://bitcoin.org/bitcoin.pdf, 2008 
 
