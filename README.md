Technical Readme
================

The system is written in Java language. The platform itself is portable to any operative system. Other modules of the system are operative system dependent. As of today the system as a whole is divided in two main parts:

#### 1. Android APK.
#### 2. Android Independent Libraries:

##### Fermat Core

##### Fermat Api

##### Fermat Plug-ins:
	
*	Bitcoin Crypto Network 
*	Wallet Manager
*	Wallet Store
*	Wallet Publisher
*	etc.


# Android APK

The Android APK is the container of the platform while running on the Android operative system. It holds a few modules of the platform that are operative system dependent like the File System and the Database System.

The whole APK from the users perspective is an APP that contains inside itself other sub APPs. In its initial version these sub APPs are: 

* The Wallet Manager, is the sub APP that allow users to manage all the installed wallets.
* The Wallet Runtime, dynamically hosts whatever wallet the user has installed.
* The Wallet Store, a sub APP where the end user chooses which wallet to install.
* The Wallet Factory, a sub APP which allows the user to re-brand any of the available wallets.
* The Wallet Publisher, a sub APP which allows the user to manage their relationship with the user base of a published wallet. 
* The Shop sub APP, where shop owners can define their shop and its products, and interact with customers.

Its main component is the App Runtime, a module that is responsible for dynamically interpreting the navigational structure of the application, sub applications and wallets and loading the code for the part of the application where the end user is. 

The navigational structure is provided by the platform itself, and depends on what wallets the end user have installed. 

# The Platform

The platform is written in two separate libriaries, one the Platform Core, holding the classes with the logic implemented for the platform. The other is the Platform API, holding the interfaces that both Platform Core classes implement and also Addons and Plug-ins as well.

The platform is a multilayer system, where lower layers are unaware of who exists in upper layers and who are the consumers of the services they provide. A variable number of plug-ins lives in each layer. The platform understands that each plug-in might be developed by different people / companies and that the same functionality might have more than one implementation available by different developers.

For that reason each layer is divided in subsystems. Each subsystem knows about the implementations available and chooses one based on contextual information. Once an implementation is chosen, the object representing the developer decides which version of the plug-in to run.

The current layers of the platform are these:

*  1-Definitions
*  2-Platform Services
*  3-OS
*  4-Hardware
*  5-User
*  6-License
*  7-World
*  8-Crypto Network
*  9-Crypto
* 10-Communication
* 11-Network Services 
* 12-Middleware
* 13-Transaction
* 14-Module
* 15-Agent

Plug-ins on layers 1 to 6 are called Addons. From layer 7 to 15 they are called Plug-ins. The distinction is made in orther to separete crytical plug-ins (Addons) that should be developed by the same company developing the platform or trusted third parties, from the rest of the plug-ins developed by any third party. As of today, in contrast to Addons, Plug-ins have an identity within the Platform and they can only access files or databases attached to that identity.

## 1- Definitions

This layer holds platform wide definitions valid for all the upper layers. 

## 2- Platform Services

In this layer lives low level services to be consumed locally in the same device: Error Manager and Event Manager.

## 3- OS

Here is where it lives the OS dependent functionality. Basically we wrap this functionality in a way that the rest of the platform or its plug-ins don't need any knowledge of the OS they are running at.

## 4- Hardware

The pug-ins here manages information about related to the device where the platform is running locally or the devices which this local instance has contact with.

## 5- User

In this layer we have 3 Addons

	* Device User: Supports the multiple users per device functionality.
	* Intra User: Manages the information of other users within the network of platform users.
	* Entra User: Manages the information of entities which are outside the platform, like users of crypto currencies that are not using the platform but third party wallets.

## 6- License

In this layer lives the Addons implementing the micro use license functionality. This allows both plug-ins developers as well as segmented wallet developers to define use license for their contribution and monetize their investment.

## 7- World

Plug-ins in this layer connect to the existing and future industry deployed services, like price indexes, wallet service providers, payment processors, exchanges, and so on.

## 8- Crypto Network

Each plug-in in this layer encapsulates the access to one crypto network like bitcoin for example.

## 9- Crypto

Some crypto related functionality that is not exactly wrapping a crypto network as before, is found in this layer.

## 10- Communication

This layer provides communication services to upper layers plug-ins and components in general. Between the plug-ins living here we have:

### Cloud

Uses a cloud service as an intermediary to connect to other devices.

### P2P

Uses an application level P2P network to  connect to other devices.

### Geo Fenced P2P

Uses a P2P network based on proximity.

### Lan, Bluetooth, etc.

## 11- Network Services

Each plug-in here represents a network service that connects to another instance of itself on a remote device via any of the communication channels available. In fact the plug-ins are abstracted from which communication channel they are connected. Some of the first network services are:

### Bank Notes

Manages the exchange of images representing bank notes. For the highest level of abstraction, where users are dealing with digital images of their local fiat money, this service allow wallets standing over that abstraction layer to acquire the bank-notes of the country where the user is from peers that already have them.

### Crypto Addresses

Manages the exchange of crypto addresses between users of the platform.

### Intra User

Manages exchange of user information between users of the platform.

### Money

While the value on a transaction is transmitted through the crypto network, the information about the transaction is transmitted through this service, when the transaction is between platform users.

### Money Request

Transmits and receives money request from other platform users.

### Wallet Community

This service enable the localization of other users of the same wallet, hence of that wallet's community.

### Wallet Resources

This service localizes and downloads the resources like images needed for a certain wallet to run.

## 12- Middleware

In this layer, we have plug-ins which serves more than one sub-APP, like for example:

App Runtime, Wallet, Wallet Contacts, etc.
 
## 13- Transaction

The plug-ins in charge of coordinating the different types of transactions lives in this layer.

## 14- Module

The plug-ins in this layer have a one to one relationship with the sub-APPs running on top of the platform. They encapsulate their business logic, in a way that enables that this sub-APPs behave exactly in the same way no matter in which OS they are running.

## 15- Agent

Agent plug-ins provide the last layer of abstraction for the wallets that should need them. They take actions on behalf of the end user and for its benefit.

