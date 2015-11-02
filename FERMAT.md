
##Fermat: A Trust-less Financial Application Framework


### Abstract

A trust-less financial application framework would allow standalone crypto wallets to evolve into any kind of trust-less financial applications. Crypto networks provide part of the solution, but the main benefits are lost if a trusted third party is still required to transport meta-data, synchronize devices, hold wallet files or keys, manage identities, interface crypto networks or the legacy financial system.

We propose a Peer-to-Peer network for transporting meta-data and inter-connecting clients one to the other. A synchronization scheme complements it, transforming a standalone app into a distributed application across several devices still owned by the same user.

The framework replaces the standalone wallet and enables the development of trust-less financial applications that are both crypto-currency and digital-asset-enabled.

### Introduction

Standalone bitcoin wallets were the first trust-less financial applications since they required to trust no third party inheriting this property from the bitcoin network itself. As the ecosystem evolved trusted third parties were introduced again and they took over the wallet space because of technical capabilities that are easier to be built in a centralized way: communication between wallets, synchronization between devices, interfacing the legacy financial system, etc., and they consistently took the biggest share of funding, leaving standalone wallets far behind and at the same time trashing the trust-less property of bitcoin, one of its key features. Applications trying to use the blockchain to transport meta-data were considered spammers and trust-less wallets were effectively left behind.

What is needed on top of all existing protocols is a layer that faces the end user and that finishes the job bitcoin started. By using crypto networks for transporting value or as a registry for digital assets and the Fermat network for transporting the required meta-data at a client level it would allow financial apps to run any user-level interconnected-functionality without ever going through a trusted party.

A plug-ins architecture allows any developer to add their own re-usable plug-ins. A micro-use-licensing scheme enforced by the system itself guarantees plug-ins, wallets, and apps developers a revenue stream. OS dependent GUI components are built on top of the multi-layered plug-ins structure to face the end user as niche-wallets or financial applications in general. Apps and wallets with similar functionality are wrapped into platforms, each one introducing new actors and plug-ins, to the ever increasing functionality of the whole system.

A built-in wallet-factory allows developers to reuse the highest level components and create niche-wallets or niche-financial-apps by combining existing functionality and adding their own code to the combo. A built-in wallet-editor allows non-developers to reuse any of these niche-wallets to build new branded-wallets just by changing their look and feel. A built-in p2p-wallet-store allows end users to choose which wallets or financial apps to install from the ever growing catalog.

### Framework

The solution we propose begins with a Framework that must be portable into different OS. On a multilayered format, the bottom most layer is interfacing the OS and must be built with replaceable components implementing the same set of public interfaces (polymorphism) in order to build on top of it a single set of OS-independent components. At the same time, the upper most layers will be again OS-dependent, providing a native GUI on each device.

We identify 3 different kind of components that we arbitrary call Addons, Plug-in, and GUI components. We define Addons as low level components that do not need to identify themselves to consume services from other components. This impacts in that they have broad access to the file system and databases. On the other side, Plug-ins have their own identity and must identify themselves to other plugins or addons to consume their services which in turn restrict the scope of their services based on the caller's identity (for example the filesystem addon would only give access to a plugin's own folder structure, the database system addon would only give access to a plugin's own databases, and so on). In this way is handled the problem of running plugins from untrusted sources.

The Framework's core is in charge of initializing Addons and Plug-ins and managing Plug-ins identities. An API library holds the public interfaces that each component exposes to the rest of it's peers within the same device in orther to allow then to consume their services. This provides a strong encapsulation of each component's business logic allowing them to freely define their internal structure and data model.

### Crypto Networks

A set of plug-ins is needed for each crypto network that want to be supported. One for interfacing the network and being able to push outgoing transactions and monitor incoming transactions. Another couple being the digital vaults where the crypto currency value and digital assets are stored.

Wallets are higher level abstractions and have their own set of Plug-ins for keeping the accounting of each kind of them. This means that the we split the accounting from the value having components at different layers to handle each of them.

### Fermat Network

The network is intended for two main things: 

a. clients finding other clients
b. clients calling other clients.  

Every Node has a copy of a distributed geo-localized inventory of all network nodes. They run a protocol that allows them to keep their copy syncronized. 

To be able to be found, a client registers itself and some inner componets and identities with the geographically closest node. When it needs to find another client it runs a protocol based on the approximate location of the client it is willing to connect to.






### Incentive

### Transactions

* workflows
* responsability
* 

### Platforms


### Identities

### GUI


### Syncronization








