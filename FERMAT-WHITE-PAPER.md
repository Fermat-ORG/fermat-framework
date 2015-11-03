# Fermat

## A Trust-less Financial Application Framework

<br>
### Abstract

A trust-less financial application framework would allow standalone crypto wallets to evolve into any kind of trust-less financial applications. Crypto networks provide part of the solution, but the main benefits are lost if a trusted third party is still required to transport meta-data, synchronize devices, hold wallet files or keys, manage identities, interface crypto networks or the legacy financial system.

We propose a Peer-to-Peer network for transporting meta-data and inter-connect clients one to the other. A synchronization scheme complements it, transforming a standalone app into a distributed application across several devices still owned by the same user.

The framework replaces the standalone wallet and enables the development of trust-less financial applications that are both crypto-currency and digital-asset-enabled.

<br>
### Introduction

Standalone bitcoin wallets were the first trust-less financial applications since they required to trust no third party inheriting this property from the bitcoin network itself. As the ecosystem evolved trusted third parties were introduced again and they took over the wallet space because of technical capabilities that are easier to be built in a centralized way: communication between wallets, synchronization between devices, interfacing the legacy financial system, etc., and they consistently took the biggest share of funding, leaving standalone wallets far behind and at the same time trashing the trust-less property of bitcoin, one of its key features. Applications trying to use the blockchain to transport meta-data were considered spammers and trust-less wallets were effectively left behind.

What is needed on top of all existing protocols is a layer that faces the end user and that finishes the job bitcoin started. By using crypto networks for transporting value or as a registry for digital assets and the Fermat network for transporting the required meta-data at a client level it would allow financial apps to run any user-level interconnected-functionality without ever going through a trusted party.

A plug-ins architecture allows any developer to add their own re-usable plug-ins. A micro-use-licensing scheme enforced by the system itself guarantees plug-ins, wallets, and apps developers a revenue stream. OS dependent GUI components are built on top of the multi-layered plug-ins structure to face the end user as niche-wallets or financial applications in general. Apps and wallets with similar functionality are wrapped into platforms, each one introducing new actors and plug-ins, to the ever increasing functionality of the whole system.

A built-in wallet-factory allows developers to reuse the highest level components and create niche-wallets or niche-financial-apps by combining existing functionality and adding their own code to the combo. A built-in wallet-editor allows non-developers to reuse any of these niche-wallets to build new branded-wallets just by changing their look and feel. A built-in p2p-wallet-store allows end users to choose which wallets or financial apps to install from the ever growing catalog.

<br>
### Framework

The solution we propose begins with a Framework that must be portable into different OS. On a multi layered format, the bottom most layer is interfacing the OS and must be built with replaceable components implementing the same set of public interfaces (polymorphism) in order to build on top of it a single set of OS-independent components. At the same time, the upper most layers will be again OS-dependent, providing a native GUI on each device.

We identify 3 different kind of components that we arbitrary call Add-ons, Plug-in, and GUI components. We define Add-ons as low level components that do not need to identify themselves to consume services from other components. This impacts in that they have broad access to the file system and databases. On the other side, Plug-ins have their own identity and must identify themselves to other plugins or add-ons to consume their services which in turn restrict the scope of their services based on the caller's identity (for example the filesystem addon would only give access to a plugin's own folder structure, the database system addon would only give access to a plugin's own databases, and so on). In this way is handled the problem of running plugins from untrusted sources.

The Framework core is in charge of initializing Add-ons and Plug-ins and managing Plug-ins identities. An internal API library defines the public interfaces that each component exposes to the rest of the components within the same device in order to allow then to consume their services. This provides a strong encapsulation of each component's business logic allowing them to freely define their internal structure and data model.

<br>
### Crypto Networks

A set of plug-ins is needed for each crypto network that want to be supported. One for interfacing the network and being able to push outgoing transactions and monitor incoming transactions. Another couple being the digital vaults where the crypto currency value and digital assets are stored.

Wallets are higher level abstractions and have their own set of Plug-ins for keeping the accounting of each kind of them. This means that the we split the accounting from the value having components at different layers to handle each of them.

<br>
### Fermat Network

The network is intended for two main purposes: 

a. clients finding other clients

b. clients calling other clients.  

Every Node has a copy of a distributed geo-localized inventory of all network nodes. They run a protocol that allows them to keep their copy synchronized. 

To be able to be found, a client registers itself and some inner components and identities with the geographically closest node. When it needs to find other clients it runs a protocol based on the approximate location of the client it is willing to connect to.

<br>
### Incentive

#### To developers

Plug-ins developers declare a micro-use-license for each plug-in they add to the Framework. Wallet or Financial Apps developers declare a micro-use-license for their components. End users install the Apps (wallets) of their choice and the license paid is the summary of the App's micro-use-license plus all the micro-use-licenses of the plug-ins used by that App. 

The Framework assumes the responsibility to enforce the agreements, charge the end user and distribute the payments to all developers involved.

#### To network nodes

Clients establishes a home node where they register themselves and their network services and identities in order to be found by other clients. They will pay a subscription fee to their home node for it's services. Finding and calling other clients through other nodes is free as those nodes income is coming from the clients for which they are the home node.

<br>
### Platforms

We define as a Platform to a set of interrelated functionality. Platforms may consume services from other platforms and their dependencies form a hirearchical stack. 

Each Platform may introduce to the system new workflows, Add-ons, Plug-ins, identities and GUI components (Apps, wallets). This enables the system to target different use cases with different users involved in a clean way. 

<br>
### Identities

We handle identities at different levels for multiple purposes. In all cases identities are represented by private and public keys.

#### End User Identities

The need to handle multiple logins on the same device brings with it the first kind of identity which we call _device-user_. This identity lives only at a certain device and not even the public key is exposed to the network.

Besides that, the end users can have multiple types of identities, and within each type as many instances as they want. Each type of identity corresponds to a role or actor in a use case. Usually each Platform introduces a set of indentities and all the Platform's functionality orbits around all the use cases derived on the interactions between those actors. 

Even having a hierarchy of identities, end users have a root identity. At that level thay can set a standard set of information and overwrite it at any level down the hierarchy, narrowing or expanding that information as needed. All these identities are exposed to the Fermat Network in a way that from the outside, no one can tell they are related between each other or to a certain end user. 

#### Componets Identities

Many components have their identities for a variety of purposes:

a. Plug-ins to identify themselves to Add-ons in order to get access to identity-specific resources as Databases or areas of the File System.

b. Network Services to encrypt the communications between each other.

c. Clients to encrypt the communication with nodes.

d. Nodes to recognize each other even when their IP, location or other profile information changes.

<br>
### Workflows

We define workflows as high level processes that requires several components to achieve a certain goal. Manny workflows start at a GUI component triggered by the end user and spans through several Plug-ins on the same device, and in some cases jumps into other devices. Other workflows may start directly at some Plug-ins, triggered by events happening within the same device.

From a workflow point of view, each Plug-ins executes a certain task and is fully responsible for doing it's job. Workflows are a chain of tasks that may split in several paths and may span through more than one device.

<br>
### Transactions

#### Transactional Workflows

As the Fermat Framework runs on potentially unstable devices such as mobile phones, each Plug-in must be prepared to overcome the difficulties caused by a device shutting down at any moment and it must be able to complete later on it's intended job and never to leave information on an inconsistent state. This is quite challenging but not impossible. 

Part of the solution is to make each Plug-in assume a responsability while they are handling part of a transaction on a transactional workflow. So this responsability is transfered on each step of the chain using a _Responsibility Transfer Protocol_. This means that the one who is responsible at the moment of a black out is the one which must resume and do it's best to get rid of that responsability moving it further down the chain within the transactional workflow.
 
#### Monetary Transactions

We handle monetary transactions dividing the accounting from the value. Usually transactions start on specialized Plug-ins which are in charge of coordinating the whole transaction. These Plug-ins usually interact with wallets-Plug-ins debiting or crediting the accounts involved. The accounting of the currency or asset involved are keeped by these wallets-Plug-ins. Later the transactional workflow splits between moving the value (crypto currency) and moving the meta-data associated to the transaction.

Through two different paths, the value and the meta-data arrives to the recipient's device and they are combined together by the remote counter-party transaction component which in turn interacts with the remote wallet-Plu-ins to record the accounting as appropriate.

<br>
### Syncronization

We define a Private Device Network as a network of devices owned by the same end user. Using the Fermat Network, the Framework synchronizes the information on all nodes of this Private Network. In this way all the information and system wide identities belonging to the End User are available at any device.

Crypto funds are kept into a Multi-Sig vault and there is a shared Petty-Cash-Vault accessible from all nodes even when they are off-line from this Private Network. An automated process monitors the Petty-Cash-Vault and tops it up when needed. Several nodes must sign the top up transaction in order to proceed. In this way if a device is lost or stolen, only the Petty-Cash fund is at risk. End users can eject stolen devices from it's Private Network and if they act quickly they might be on time to re-create the Petty-Cash fund under the new configuration and saving those funds.

<br>
### Privacy

The proposed system complements the privacy properties of crypto networks, extending them to the full stack needed to run different kind of financial applications. By using it's own P2P network with point to point encryption for transporting meta-data both value and information are under similar privacy standard.

Identities are public keys related to private keys kept by the End User and not shared to anyone in any way. 

Even the collection of system information for visualization and statistics uses hashes of public keys to protect End User's privacy and at the same time preserve the relationships between them.

<br>
### Conclusion

We have proposed a Framework developing and running Trust-less Financial Applications. Bitcoin provided part of the solution but standalone wallets fell short to show the way of how to keep the End User away from trusted third parties at a higher level. Without solving obvious problems like how to exchange meta-data in a trust-less way, and how to prevent the lost of private keys (funds and identities) the industry evolved introducing trust again into the system.

With a Framework we not only solve the problem for a single wallet application, but also to any kind of financial application that could benefit from being crypto and digital asset enabled and trust-less at the same time.

<br>
### References

[1] Satoshi Nakamoto, "Bitcoin: A Peer-to-Peer Electronic Cash System", https://bitcoin.org/bitcoin.pdf, 2008 
 
