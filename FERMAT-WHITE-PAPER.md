![alt text](https://github.com/bitDubai/media-kit/blob/master/MediaKit/Fermat%20Branding/Fermat%20Logotype/Fermat_Logo_3D.png "Fermat Logo")


# Fermat

## The Front-End of Blockchain APPs

**Author** : [Luis Fernando Molina](https://github.com/Luis-Fernando-Molina)

**Contributors** :

**Advisors** :

**Reviewers** :

February 2015

_www.fermat.org_

<br>
## Document Status

This document is a DRAFT with no formal reviews yet. Some changes are being done from time to time. If you want, you  can make a formal review of this document. We will be happy to add you at the _reviewers_ section.


<br>
## Abstract

We envision a world where crypto currency is already mass adopted, where fiat currencies are already digital and where people have the freedom to choose which currencies to use and where to store their value: at third party regulated institutions with all their known pros and cons, or at their own devices, out of the reach of hackers, tech giants, marketeers, bankers and governments.

In this future people can manage multiple on-line identities and interact with the rest of the world using the one that best fit their needs, exposing only the information they fine tune to be exposed in each situation. They can also transact with anyone in the world with whatever privacy level they choose: from attaching their real life identity and using formal channels, to detaching from any real life reference and not leaving traces of their transactions and information exchange with others.

People of this future have in their pocket not only their money, but a whole suite of software that can use their digital money to enable and sustain all sort of p2p business models. A whole ecosystem of powerful APPs that share some common properties: they allow an intermediate-free running of present time business in different industries, they can operate with both digital fiat and crypto currency, and they are censorship resistant.

We are building Fermat to get into that future in the fastest way possible. And to discover what kind of system to build, we traced back the path from that future into present times, we have identified all the challenges that needed to be addressed and all the obstacles that needed to be overcome. We put everything that had to be done into a huge master plan and started developing the first phase of it. The result is a system that rocks the foundations of many established paradigms and with such a disruptive potential that its final effects are difficult to measure or even project at present times.

From end users perspective, Fermat is an extension of the operating system. In the same way Windows is deployed with some fundamental APPs like the notepad, calculator, photo editor, and many others, Fermat is distributed with a set of fundamental P2P APPs like chat, crypto wallets, digital assets, crypto broker, shops and many others. The equivalent of the Microsoft Office suite of applications in Fermat are the P2P version of popular services like Uber but without Uber Inc., AirBNB without AirBNB inc, LinkedIn, Tinder, eBay, etc., all of them without the middlemen.  

This white paper does not describe a theoretical system that might work one day to get us to that future. It describes a system that already works and some parts of it are in alpha testing right now. It also describes the organization of several networks of people spanning different disciplines and countries collaborating to produce the most ambitious project in the industry.

<br><br>

## Part I : Overview

In this section you will get an overview of the Fermat System to describe the general idea. After this, you will be ready to re-explore the concepts in more detail in the next section.

<br>
### Introduction

Since the last technical barrier to issue digital money has already been overcome with the invention of bitcoin [1] - the double spend problem -, there is little doubt that governments of the world are on the way of digitalizing cash. As soon as the US or EU can achieve this, the rest of the world will follow suit. So, this part of our envisioned future is already on the way to happen and there is no need for us to intervene in any way except to handle in Fermat the fact that money is going to be digital and paper money is going to disappear. This means that our mission can be condensed into designing a system that:

* Makes it technically possible for crypto currency to go mainstream.
* Encourages the code production to make this happen in the least time possible. 
* Allows people to safely store digital value by themselves.
* Creates APPs that understand digital fiat and crypto.
 
To achieve these goals the system must:

**Be Censorship Resistant**

In order to survive over-regulation the day that crypto currencies really start competing with fiat money.

**Address All Use Cases** 

It must guarantee that either crypto currency gets mass adopted or that there is no other way to reach that goal.

**Promote Collaboration** 

It must produce a shift of paradigm in software development, moving it from competition to collaboration, which helps to avoid the software waste which is produced when startups fail.

**Reuse What Already Works**

It must reuse what in the industry has already taken time and effort to produce and make it work, for example bitcoin and several other low level protocols and systems.

**Enable Segmentation**

People around the world are different one from the other. There is a strong need to deliver each audience custom tailored solutions.

**Clear Governance Model**

It must be governed by a [BDFL](https://en.wikipedia.org/wiki/Benevolent_dictator_for_life) model like the most successful open source projects (i.e. Linux) in order to avoid decisions deadlocks -like the bitcoin block-size- in the short and middle terms, and by a proof-of- stake mechanism on the long term.

**Multi OS**

It must be able to run in major operating systems.

**Community Driven**

It must be open to anyone in the community to participate developing the system or using it as end users or business operators.

<br>
### System Properties

We designed Fermat to hold all these eight properties at the same time, what originated certain designing constraints we are going to examine now.

#### Censorship Resistance

Bitcoin is a clear example of a censorship resistant system because of its key properties:

* It is open source.
* It runs on a p2p network which anyone can join.
* Its client software runs on end user devices (cloud services using bitcoin are not censorship resistant).

Fermat extend these properties up to the stack to inherit the censorship resistant qualities of bitcoin. This is the reason why Fermat:

* It is open source.
* It runs on a two level p2p network composed by network nodes and network clients. 
* Its client software runs on end user devices (third party functionality on Fermat may access cloud servers rendering that specific functionality not censorship resistant).

By being Fermat censorship resistant itself, all Fermat APPs built on top of it will inherit this property and they will be shielded in hostile jurisdictions enabling each of them to reach mass adoption even in places where their competition to local currencies or business is not welcomed. 

 
#### All Use Cases

Nobody knows which use case will bring mass adoption and certainly nobody can guarantee that the single one use case they are working on is going to achieve it. The only way to guarantee that mass adoption is reached (or accept it is not possible) is to implement all possible use cases. But in this industry all possible use cases have not even been imagined yet. This means that we need to think about an open ended system.

**Open Ended System**

We must think of Fermat as a system with no visible end, so it must be designed to grow horizontally implementing all the known use cases, and open for not yet imagined use cases to be added in the future. And everything needs to fit into the same architecture.

**Master Plan**

We define a long term master plan with everything that needs to be done, and considering all possible use cases. Then we all collaborate building the pieces of that master plan, in order, phase by phase. The master plan excludes duplicate functionality thus we eliminate duplicate efforts and save time and money.

#### Promote Collaboration

Most of the startups die. That is a known fact and with this collapse, they throw away the code written most of the time. Even when their code is open source, when the maintainer leaves the project, it is trashed. That is not the most efficient way to move the industry forward, it is just the way it has been working until now. 

The current startup model is comparable to evolution: there is a constant competition between individuals and the one who survives has the right to evolve and transmit their winning DNA to their children and so on. That model was OK on species until modern times, but it is not necessarily the most efficient considering the human time scale. Science collaboration model achieved in a few centuries many improvements in knowledge and technology what would have taken isolated individuals much longer, like the usual times in evolution, measured by thousands and millions of years. Not collaborating is like to sit and wait that evolution from itself finds a cure to each of the multiple diseases affecting human beings instead of trying to find it with all our scientific power working together.

We believe in science collaboration model (a massive world wide collaboration effort) with an integrated business model that support whoever decides to participate on the common venture.

Fermat's design must include:

**Reusable Components**

We need components to be atomic enough in order to be reused. Traditional mobile APPs are hard to be reused since they are not built for this purpose. We need to break down the traditional APP model and envision a new type of APPs that are designed as a set of reusable components. We call this new type of APPs  _Fermat APPs_.

**Developers Incentive**

We designed an incentive model for developers around the world to produce reusable components. The core idea is that these components are owned by whoever writes their code and they are entitled to receive recurring micro payments from end users using directly or indirectly their components.

#### Reuse What Already Works

The industry has already produced several mature systems that can be reused as the foundations of Fermat. All of these systems can be consider back end systems, pushing Fermat itself towards the Front End. These considerations makes Fermat:

**Blockchain Agnostic**

We designed Fermat to use any crypto currency blockchain for transporting value, and any other blockchain for other use cases like smart contracts, notarization, distributed storage, etc.

**Front-End Framework**

We designed Fermat as a framework to create the front end of blockchain APPs. Nevertheless there is nothing to prevent Fermat to evolve into a more general purpose front-end framework.

#### Enable Segmentation

The underlying reason for the "bitcoin is difficult to use" adoption barrier is in fact a problem of lack of segmentation and variety of options. Indeed it is difficult to most segments of the world population, but certainly not to everybody because we have witnessed a positive growth of the blockchain industry last years. To succeed in taking crypto currency to the masses we must deliver each audience custom tailored solutions that they can understand and find of value for themselves before discarding them. For these reasons Fermat must allow:

**Fermat APPs Specialization**

This means that any Fermat APP can be specialized to target an specific audience by a third party developer and still produce micro payments to the original author. We call this _Fermat Niche APPs_.

**Fermat APPs Rebranding**

This means that any Fermat APP can have its look and feel changed without programing and still produce micro payments to the original author. We call this _Fermat Branded APPs_.

**Fermat as Mobile-APPs local Back-End**

This means that Fermat can be used by any regular Mobile APP as a local back-end in at the same device they are running, transmiting to them their core properties like censorship resistance, and all the services available within the Fermat Framework. At the same time giving mobile APPs developers the freedom to developt their mobile APPs without external constraints.

#### Clear Governance Model

Open source projects that do not have a clear governance model either fail or take more time to reach their goal since deadlocks can occur like the "block-size debate" of 2015. Fermat governance model is:

**[BDFL Governance Model](https://en.wikipedia.org/wiki/Benevolent_dictator_for_life)**

Although the industry favors decentralization and a proof-of-stake model, we believe is not the right way to do it at early stages, since a disruptive system could be high jacked just by buying all the voting power. It might work when it is big enough and there is not enough money to buy the majority of the voting power. That said, Fermat will evolve in the distant future to a proof-of-stake governance model when those conditions are met. Meanwhile it will be run with a BDFL model. 

#### Multi OS

Fermat is written in JAVA in order to be portable into major operating systems. Out of this requirement Fermat has a:

**Multi-layer Architecture**

Fermat has a multi-layer architecture being the bottom most layer together with the upper most layer OS dependent, while the rest of the layers in between are independent and portable. 

**Native user Interfaces**

Users interfaces in Fermat are native to the OS they are running, allowing developers to use that OS specifics in order to reach the best possible user experience without limitations.

#### Community Driven

Fermat is a community driven open source project with several networks of people collaborating to develop the system. This community was started in 2014 by the project founder, Luis Molina in Budapest, Hungary. Since then it spanned several countries and by the time of the last revision of this paper on Feb 2016, 50+ people were working full-time on the project plus many more were collaborating part-time or with casual contributions. 

Fermat is designed for massive collaboration. We expect thousands of developers join the project and the system and project management must be able to deal with this fact. Several human networks need to exist to produce such a massive system:

**Developers Networks**

Fermat developers organize themselves in small teams. In some cases teams arrange themselves in networks with their own hierarchies. The largest network working on Fermat has over 35+ full time developers working on the project by Feb 2016. [6]

**Designers Networks**

Building front-end APPs requires requires tons of designers hours. Fermat has a growing network of designers. Between them we can find graphic designers, UI, UX, 3D designers and Video producers. 

**Business Operators**

On top of the software layer where developers and designers are encouraged by these micro payments, business operators run real world business models, reusing the same codebase, but fine tunning their business parameters to target different audiences at different corners of the world. 

#### Other Design Considerations

Designing a system that is going to deal with digital money and is going to run on an uncontrolled environment (end users devices) where anyone can add new components its quite challenging. Several concerns have to be addressed in order to make the system reliable. The most obvious ones are:

* How do we prevent money from being lost in the event that one device suddenly shut down and the transactional processes are interrupted?
* How do we prevent one component from stealing funds from others?
* How do we prevent users to loose their funds if their device is lost or stolen?

Thinking carefully about each of the system requirements described above, a lot of questions similar to these arise. So far most of those questions had been addressed and a system architecture has emerged out of finding the answers to all those questions. 

<br>
### Architecture

The Fermat System has three major architectural components:

**The Fermat Network**

The Fermat P2P network interconnects devices between each other in order to exchange application data without needing to go through a centralized service.

**The Fermat Framework**

The Fermat Framework is what keeps all the reusable pieces together and enables developers to add more pieces to the system.

**The Ecosystem of Fermat APPs**

The Ecosystem of Fermat APPs offers different products to end users. From the outside it may seem like a set of APPs targeting different use cases for different people, but on the inside there is a system built from hundreds of components collaborating between each other. These APPs are just the user interfaces, or skins of the underlying mechanisms.


#### The Fermat Network

Fermat runs on end users devices. Phones, tablets, laptops, PCs. It can also run on web servers or wherever is needed. One of our design constraints was to create a censorship resistant system. Since these devices need to communicate to each other in order to exchange information the need for a p2p network to enable this communication without going through third parties arose. If device to device communications were to go thought centralized service providers, then the system could be shut down buy censoring those centralized points.

##### Why is it needed?

A well known peer-to-peer software is [BitTorrent](https://en.wikipedia.org/wiki/BitTorrent) and its purpose is to enable its users to share files between each other. BitTorrent clients are the network nodes themselves. Anyone could argue why the need in Fermat to have specialized nodes for interconecting devices and don't let them connect between each other freely.

There are two main reasons for this:

1. Fermat is mobile first, and many mobile operators forbids incomming connections into mobile phones. This leaves these mobile devices with the only option to initiate the connection. Fermat nodes can be seen as relay servers described at the [NAT traversal](https://en.wikipedia.org/wiki/NAT_traversal) techniques.

2. Mobile devices that their telco company do allows them to accept incomming connections are not good candidates to be relay servers either since not all mobile data plans globally have unlimitted data transfer.

This creates the need for specialized nodes within Fermat that can take the role of relay servers and an incentive mechanism for their operators in order tu run them. Operators would usually set up these nodes at hardware that can receive incomming conections naturally or with some configuration, and at places were they are charged for bandwith with unlimited data traffic.

##### Network Architecture

Architecturally, we describe the Fermat p2p Network as having two levels:

**Network Nodes** : Are the devices that interconnects other devices and let them talk between each other through them.

**Network Clients** : Are the devices that use Network Nodes as a bridge to get connected to other Network Clients.

The Fermat p2p Network of Nodes is the main tissue connecting devices running the Fermat Framework, though it is not the only one. You will learn later that the Framework is designed to use the Fermat p2p Network when it can not find a more direct way of connecting to the target device, like a shared WiFi connection, blue-tooth, NFC, etc.

To avoid confusion we call the Fermat p2p Network of Nodes directly "The Fermat p2p Network", and when we want to include also Network Clients, we use the term "The Fermat p2p Extended Network".

##### Fermat p2p Network

By allowing devices to interconnect to each other we open many possibilities to our system. Now transactional processes can span one device and continue into others, and in some cases the workflow comes back to the original device to finish the transaction. This is extremely powerful since we are not talking about a device accessing a third party API, but a system accessing another copy of itself running on another device. 

Think about two biological cells, each one with the same DNA, and both being part of a higher level organism built out of these cells. When we talk about Fermat, the system, we refer to this higher level organism many cells.

In this metaphor, the Fermat p2p Network would be the nervous system that allows these cells to send information to each other and in that way coordinate whatever tasks or business they have between each other. Cryto currency networks would be like the circulatory system that is not designed to transport information, but to transport value. Fermat has no intention in replicating what is already working, in fact one of our premises is "Reuse What Already Works".

>>>> INSERT INFO GRAPHIC #1 HERE EXPLAINING THE PREVIOUS PARAGRAPHS  <<<<

#### The Fermat Framework

We need a Framework to run Fermat APPs on top of it. This Framework sits on top of the OS and extends it in order to support Fermat APPs. 

We define Fermat APPs as end user APPs running on top of the Fermat Framework. These APPs are in fact a set of reusable components, one of them being the user interface. 

Fermat APPs are not downloaded by end users from the Apple APP Store or Google Play Store. The only thing downloaded from there is Fermat itself. Fermat APPs are an integral part of the Fermat system and they do not need to be downloaded. On the other side the resources these APPs uses (images, sound and video files, language packages, etc.) they do need to be downloaded upon request of the end user.

That means that we need a special Fermat APP to give the end user the illusion they are downloading and installing a Fermat APP. We call this Fermat APP the _Fermat APP Store_. So no APP on this store catalog is really downloaded, what is really happening is that they are activated and the Framework makes them visible, because the pre-installed components are already there. Besides that, resource files do need to be downloaded. The _Fermat APP Store_ will try to download those resources from other peers and if it is not possible it will go to a centralized place to find the seeds.

Up to this point Fermat is a Framework that extends the OS capabilities enabling it to run Fermat APPs, which in turn are very similar to mobile APPs from then end users point of view, but underneath the skin they are a set of components interacting between each other. Probably a few of them were built for an specific Fermat APP while the rest are part of a pool or reusable components that increases each time a new Fermat APP is added to the system.

>>>> INSERT INFO GRAPHIC #2 HERE EXPLAINING THE PREVIOUS PARAGRAPH (frameweork)  <<<<

##### Multi-Layer Design Pattern

We choosed a multi-layer design pattern to arrange the thousands of components that this system is going to require. The core idea is simple: 

* Components give services to other components on upper layers.
* Components consume services from other components on lower layers.

This simple rule helps to organize this crowd of components with some criteria, but it doesn't help by its own to define how many or which are the layers needed. To discover the needed layers we designed several Fermat APPs targeting different use cases, then we split these APPs into atomic components, each one with different responsibilities, we compared all the components and defined layers for components with the same set of responsibilities.

In this way we introduced our concept of layers: a logical place where components with the same set of responsibilities live inside the Fermat Framework.

Throughout the first 18 months of the project, the following layers emerged:


**Table: Fermat Framwork Layers.**

| Super Layer | Layer Name | Language | 
|:-----------:|:----------:|:---------|
||UI Core|Java-Android|
||Niche Wallet|Java-Android|
||Reference Wallet|Java-Android|
||Sub App|Java-Android|
||Desktop|Java-Android|
||||
||Core|Java|
||||
||Engine|Java|
||Wallet Module|Java|
||Sub App Module|Java|
||Module|Java|
||Desktop Module|Java|
||Agent|Java|
||Actor|Java|
||Middleware|Java|
||Request|Java|
||User Level Business Transaction|Java|
||Business Transaction|Java|
||Negotiation Transaction|Java|
||Stock Transactions|Java|
||Digital Asset Transaction|Java|
||Crypto Transaction|Java|
||Cash Money Transaction|Java|
||Bank Money Transaction|Java|
||Contract|Java|
||Negotiation|Java|
||Composite Wallet|Java|
||Wallet|Java|
||World|Java|
||Identity|Java|
||Actor Connection|Java|
||Actor Network Service|Java|
||Network Service|Java|
||||
|CER|Search|Java|
|CER|Provider|Java|
||||
|P2P|Communication|Java|
||||
|Blockchain|Crypto Router|Java|
|Blockchain|Crypto Module|Java|
|Blockchain|Crypto Vault|Java|
|Blockchain|Crypto Network|Java|
||||
|Add-ons|License|Java|
|Add-ons|Plug-in|Java|
|Add-ons|User|Java|
|Add-ons|Hardware|Java|
|Add-ons|Platform Service|Java|
||||
||Api|Java|
||||
|OSA|Multi OS|Java|
|OSA|Android|Java-Android|

As you might have noticed we chose Java as the programming language for most of the components inside Fermat, except for the ones that live at the upmost and the lowest layers. The rationale for doing so is that we wanted most of the codebase to be portable to other OS. At the upmost layers we decided to allow native user interfaces, and for that reason, at our first target OS, Android, those components need to be written in Java-Android.

In the case of the lowest layers we encapsulate the access to the OS into several components that are OS dependent, while the rest of the components at the top layers are not (except the ones related to the user interface).

Finally, we noticed that many layers were independent of end user's use cases implemented above them. We grouped these layers into what we called _Super Layers_. A _Super Layer_ is then a group of use case independent layers that have some logical connection between them.

>>>> INSERT INFO GRAPHIC #3 HERE EXPLAINING THE PREVIOUS PARAGRAPH (layers)  <<<<

#### The Ecosystem of Fermat APPs

Our design constraint here was that we need to implement all possible use cases and do it according to a master plan, phase by phase. We already know that Fermat APPs are a set of reusable components that goes to a pool organized in layers. Still, there is a need to organize that pool into different compartments with all the components related to similar use cases.

So vertical division (in contrast to the horizontal division in layers) emerged and we called each compartment a _Fermat Platform_. We noticed some things that were common to all platforms:

* They introduce new actors.
* They introduce new use cases, usually between the new actors.
* Some platforms reuses components and their services from other platforms.

After analyzing several use cases we found out that it was possible to create a stack of platforms, where platforms up the stack reuses components from platforms down the stack. And at the same time components where organized in layers inside each platform. For the first phase of the Fermat System Master Plan, we identified the following low level platforms and implemented some of them:

**Table: Fermat Platforms.**

| Code Name | Platform Name |
|:---------:|:--------------|
|PIP|Plug-ins Platform|
|CHT|Chat Platform|
|WPD|Wallet Production & Distribution Platform|
|CCP|Crypto Currency Platform|
|CCM|Crypto Commodity Money|
|BNP|Bank Notes Platform|
|SHP|Shopping Platform|
|DAP|Digital Assets Platform|
|MKT|Marketing Platform|
|CSH|Cash Platform|
|BNK|Banking Platform|
|CBP|Crypto Broker Platform|
|CDN|Crypto Distribution Network|

Each platform usually has one or more Fermat APPs on top of it, and in this way is how the Ecosystem of Fermat APPs is organized.

>>>> INSERT INFO GRAPHIC #4 HERE EXPLAINING THE PREVIOUS PARAGRAPH (platforms)  <<<<
<br>
### Key Value Propositions

We considerd in our design a business model that works fine for several different actors. On one side we need Fermat p2p Network nodes operators to provide their hardware and bandwidth to inter-connect end users. On the other side we need developers to write the code. We also need entrepreneurs to run business on top of what developers build. And finally we need end users to find value on the Fermat APPs they use.

We divided the challenge of creating incentives for everybody in the following way:

#### Fermat Miners - Nodes Operators

We believe there is a need for an application token system that can be mined in order to encourage nodes operators to put their hardware and bandwith to work for the Fermat p2p Network. Lets call these tokens _fermats_.

The business model for miners consist of:

* End users paying miners a fee in _fermats_for the bandwith provided.
* Miners getting newly _fermats_ issued by the Fermat Protocol according to a _Proof of Work_ algorithm based on bandwith, not CPU power as bitcoin.

So basically is the same model that bitcoin in the sense that at the begining we need to bootstrap the Fermat Network by letting the Fermat Protocol to issue tokens. Users need them in order to pay for the fees required to use that network. 

As we have a premise of reusing what is working, instead of handling these tokens transactions by ourselves, we are outsourcing the bitcoin network to do that for us. With this in mind, our blockchain will only need to record _coinbase_ or newly issued tokens transaction, while transfers from user to user can be handled directly by the bitcoin network itself.

We chose the same economic parameters than the bitcoin network itself: 21 million units, generated every 10 minutes approximately, staring with 50 per block and halving this number every 4 years. The intended effect would be that a diverse community of nodes operators arise encouraged by the block reward, while there is not enough user base to profit from the selling of bandwith to end users.

#### Developers

In bitcoin, the focus of the system is in creating an unmutalbe distributed layer across parties that don't trust each other. In that case the issuing of their token, bitcoins is tightly coupled with the mining process, that is the key element to achieve their goal.

In Fermat, the key element to achieve our goal (mass adoption) is to have all use cases covered as soon as possible, and the whole system design orbits around that. Having a p2p network for exchanging information is needed for the system to be censorship resistant. For this reason, _fermats_ are equally issued to incentivize network node operators and developers. 

Developers have several business models they can choose from. Each one are initially managed as development programs run by the Fermat Foundation. These programs are:

**Casual Contribution Program** : Casual contributors are rewarded in _fermats_ for isolated bug discoveries, or sporadic time spent on the project doing assorted tasks.

**Regular Contribution Program** : Regular contributors are rewarded monthly in tokens for their continious dedication to the project.

**Bounty Program** : The Fermat bounty program defines the roadmap of the project, assigning bounties to different needed functionality.

**Component Ownership Program** : This program allow developers or teams to own components, entitle them to receive micro-payments from end users using them.

**Component Mantainance Program** : This program allows mantainers to be compensated for taking care of individual components, improving its performance, security, relayability, etc.

**Fermat APPs Queue Program** : This program allows startups to request functionality to the Fermat Developers Network. These requested functionality is procesed in parallel from the official project's roadmap.

All these programs are run and managed by the Fermat Foundation. There is an intention to automate most of these programs inside the fermat system itself in order to avoid single points of failure.

With all these options, the Fermat project aims to recruit thousands of developers into a global network with the highest software developing power ever. This way developers can achieve sustainable self-employment disintermediating software development corporations.

The key idea here is the ownership of developed components. This is the core that enables developers to live out of a stream of micro payments flowing from around the globe. The Fermat system even allow them to trade that ownership by selling it to someone else.

#### Investors

Developing Fermat components is an investment done by individual developers, dev teams, startups or investors betting on the success of the project.

In order to safeguard the investment developers do while coding Fermat APPs and components, the Fermat Open Source project establishes the following rules and it's management will enforce them through the lifetime of the project. Following, a set of definitions:

**Component Owner**

Each component has an Owner from its very inception. During the life cycle of a component, the first owner is the Fermat Foundation that is the one designing the Fermat Master Plan. Once a component is assigned to a developer, the ownership is transfered to the original author or whoever financed its development. Later the ownership can be transferred or sold to anyone using the Fermat system itself to do so. Ownership can be shared by more than one entity in any proportion they see fit. Ownership is tracked within the Fermat system itself. Ownership can be partially or totally lost and returned to the Fermat Foundation if owners don't comply with the terms of service agreements.

**Component Maintainer**

Each component has a Maintainer team. Owners assign a Maintainer team with which they share part of the revenues (micro use licenses) that that component produces in whatever proportion they agree upon. Maintainers are responsible for keeping components updated, well performing, documented, and without bugs. They also process all the upgrades needed to be done in order to support needed functionality not implemented in the original version for any reason in a timely manner.

**Component Support Team**

Each component has a Technical Support team. Owners create these teams and share with them some of the micro-use-licenses fees. Fermat has an embedded technical support functionality that enables the possibility to give support to end users when technical problems arise at their devices. This system allows problems to be addressed in a distributed environment and several support teams to interact with each other in order to track the reasons of the failure and agree on the component responsible of it. Once the responsibility is established the Maintainer team of that component has some dead lines to solve the issue before triggering some procedures that would alert the Owner.

**Component Level**

Components are classified at a the master plan level according to their difficulty and how critical they are, from 1 to 10. Each level is then translated into the amount of fermats a component collect per month as micro-use-licenses. The level also determines the service level that is required for that component in terms of time to fix bugs, make upgrades, availability of their support team, and so on.

**Fermat Property Law**

These are a set of rules to safeguard developers and investors on building Fermat components.

1. Fermat Components have an specific well defined role or responsibility and there will not be other components in the system with an overlapping role or responsibility. This rule is enforced by design at the master plan level.

2. Components not being correctly maintained according to the Fermat Project Guidelines and rules and the service agreement, are subject to be reclaimed by the Fermat Foundation and their ownership partially or totally revoked. This is specially true when the terms of service of the component are not met (time to fix critical bugs, provide technical support, etc.). This can only be done under a special procedure that includes several warning to the owner and orders to replace the maintainer / support team and to futfill their obligations. 

3. The Fermat System will provide an API to be consumed by other mobile APPs ruining on the same device. This API will expose only high level components or transactions to other mobile APPs in order to safeguard the investment on the development of lower level components within the system. This means that outside Fermat APPs replicating inside Fermat functionality wont be able to bypass the copied functionality by invoking directly lower level components. They are forced to pay micro-use-licenses even running outside the Fermat environment.

4. Fermat Nodes before servicing clients (end user devices) with their connectivity services, will check if these clients are paying the micro-use-license fees. This can be enforced since nodes know who the owners of the components are, and can query the bitcoin blockchain to confirm there are transactions from the client's identities going to components owners. This prevents outside APPs to access the Fermat Network without going through the established channels that include micro-use-licenses, or recompiled versions of Fermat with the micro-use-licenses removed or altered.


#### Business Operators

On top of the functionality built by the developers network, business operators can run their business. One example here are Crypto Brokers using the Crypto Brokers Wallet to run a localized business of buying and selling crypto currency to nearby end users.

This functionality was developed by the developers network with the only intention that business operator would pick it up and run their business using their code.

There is no limit on the different kind of business that the Fermat system can support. Fermat is distributed with different layers of applications. The first layer includes very basic infraestructure APPs that often don't need business operators at all. Examples of these APPs are Chat, Crypto Wallets, etc. Besides those simple APPs, the next layer includes a suite of APPs that requires business operators to run them: Crypto Broker APPs, Digital Assets APPs, etc. A third layer contains the most popular global services like Uber, AirBNB, social networks, dating APPs, etc. In this case these APPs will cut cost to end users since the business operator will only be required optionally and for specific situations were the value they add cannot be automated. That opens the door for thousands of regional operators to fine tune these business models to adapt them to local audiences. 

The sinergy produced by having more and more use cases covered by the same platform would be unmatcheable. This openess allows any of these use cases to increase the overal system user base, which in turn benefits all other use cases since one of the key problem for adoption is how end users end up with crypto currency in the first place. If one use case brings them in, they would likely jump to other use cases available since that barrier has already been overcome.

#### End Users

The key value proposition to end users is to reduce pricing (by eliminating or minimizing the need of third party intermediaries) and accelerate the participation in shared economy. To achive this the Fermat Master Plan includes the p2p version of several popular services spanning many different industries:

**Transportation** : A p2p version of an Uber like service with several optional shades of third party operator required. End users are allowed to choose between a fully automated and operator-free network of drivers and operated networks where the role of the operator shifts from the current one of matching riders and drivers and focus on value added services like drivers background checks, insurance, marketing, and others.

**Lodging** : A p2p version of an Airbnb like service, again with end user freedom to choose between no third party at all doing the guest and host matching, or some third party operated networks for cases were these operatos add value added services like insurance, marketing, etc.

**e-Commerce** : A p2p version of an eBay like service, without the third party. In this case the matching between buyers and sellers is automated as well as the value exchange that is also peer to peer.

**Classified Advertising** : Users of this p2p version with no third parties serve classified ads directly from their devices while other users can see them with a specialized Fermat APP. No middle man needed.

**Online Dating Service** : A p2p version of a personal introductory system where individuals can find and contact each other over the Internet to arrange a date. Fully automated with no middle men.

**Professional Network Service** : A p2p version of a LinkedIn like service with no intermediaries.

**Job Boards** : A p2p version designed to allow employers to post job requirements for a position. Fully automated with no intermediaries.  


>>>> INSERT INFO GRAPHIC #5 HERE EXPLAINING THE PREVIOUS PARAGRAPHS (human networks and business model)  <<<<

<br><br>
## Part II - Fermat In Detail

<br>
### Introduction

A peer-to-peer financial application framework could allow standalone crypto wallets to evolve into any kind of peer-to-peer financial applications.

Developing peer-to-peer financial applications is challenging. Bitcoin provides part of the solution as a P2P system of electronic cash [1], but the main benefits are lost if a trusted third party is still required to transport meta-data, synchronize devices, hold wallets files or keys, manage identities, interface crypto networks or the legacy financial system.

We propose a peer-to-peer network for transporting meta-data and inter-connect network clients between each other. A synchronization scheme running on top of it transforms a standalone app into a distributed application across several devices still owned by the same user.

We propose a framework to replace the standalone wallet application. This framework handles the full stack on top of crypto networks up to the user interface and it is intended to easily build and run APPs designed for that framework. In this way we enable the development of peer-to-peer financial applications that are both crypto currency and digital-asset-enabled, and that does not require a trusted third party of any sort.

Standalone Bitcoin wallets were the first generation of trust-less financial applications since they didn't require to trust any third party, inheriting this property from the Bitcoin network itself. As the ecosystem evolved, trusted third parties were introduced again and they took over the wallet space because of technical capabilities that are easier to build in a centralized way: communication between wallets, synchronization between devices, interfacing the legacy financial system, securing funds, etc., and they consistently took the biggest share of funding, leaving standalone wallets far behind and at the same time trashing the Bitcoin benefit of not relying on trust, one of its key features. Applications trying to use the blockchain to transport meta-data were considered spammers and standalone wallets were effectively left behind.

What is needed on top of all existing protocols is a layer that faces end users and that finishes the job Bitcoin started regarding its core principles of openness, decentralization and privacy. Using crypto networks for transporting value or as a registry for digital assets and a new P2P Network for transporting the required meta-data at a network client level. This would allow financial apps to run any user level interconnected functionality without ever going through a trusted third party.

Fermat is a Framework, a P2P Network and an Ecosystem of Fermat APPs. The Fermat Framework is an open source software that runs on end user devices. This software can run Fermat APPs, which are like mobile APPs but that run on the Fermat Framework.

By choosing a plug-in architecture for the Framework we make it possible for any developer to add their own reusable components. We define the micro use licensing scheme as the mechanism for developers to monetize their work. The Framework itself enforces these micro use licenses and guarantees developers a revenue stream.

The Fermat P2P network interconnects devices between each other in order to exchange application data without needing to go through a centralized service.

The Ecosystem of Fermat APPs offers different products to end users. From the outside it may seem like a set of APPs targeting different use cases for different people, but on the inside there is a system built from hundreds of components collaborating between each other. These APPs are just the user interfaces, or skins of the underlying mechanisms.


![alt text](https://github.com/bitDubai/media-kit/blob/master/MediaKit/Slides/slide-core-concept.png "Core Concept")


<br>
### The Fermat Framework

The solution we propose begins with a Framework that must be portable into different OSs. On a multi-layered format, the bottom most layer is interfacing the OS and must be built with replaceable components implementing the same set of public interfaces in order to build on top a single set of OS-independent components. At the same time, the upper most layers are again OS-dependent, providing a native GUI on each device.

We identify 3 different kinds of components that we arbitrarily call **Add-ons**, **Plug-ins**, and **GUI** components. We define Add-ons as low level components that do not need to identify themselves to consume services from other components. They have broad access to the file system and databases. Plug-ins have their own identity and must identify themselves to other components to use their services which in return restrict the scope of their services based on the caller's identity (for example the filesystem Add-on would only give access to the Plug-in's own folder structure, the database system Add-on would only give access to a Plug-in's own databases, and so on). In this way we handle the problem of Plug-ins accessing the information of other Plug-ins.

The core framework is in charge of initializing Add-ons and Plug-ins and managing Plug-ins identities. An internal API library defines the public interfaces that each component exposes to the rest of the components within the same device in order to allow them to use their services locally. This provides a strong encapsulation of each components business logic allowing them to freely define their internal structure and data model.


<br>
### The Fermat Network

#### Peer-to-Peer Network Architecture

The Fermat Network is structured as a peer-to-peer network architecture on top of the Internet. Fermat nodes are peers to each other, meaning that they are all equal and there are no "special" nodes. All the nodes share the burden of providing all services. The network nodes interconnect to each other only when they need to do so according to the Fermat P2P Protocol. There is no server, no centralized service, and no hierarchy within the network.

The term "Fermat Network" refers to the collection of nodes running the Fermat P2P Protocol. There are two other protocols such as the Fermat Consensus Protocol which allows the network to agree which transactions are going to be recorded on the blockchain, and the Fermat Client Protocol which is used for communicating Fermat Clients between each other and to Fermat Nodes. We use the term "Extended Fermat Network" to refer to the overall network that includes both Fermat Nodes and Clients.

#### Fermat Nodes Roles

Fermat nodes perform several tasks at the same time. For each one of them, the protocol has its own set of rules:

##### Maintain the Distributed Nodes Catalogue

Each node maintains a full catalogue of all nodes registered in the network. This role is ruled by the Fermat P2P Protocol.

##### Maintain an Identities Catalogue

Each node maintains a part of a distributed catalogue of end users identities. This catalogue is designed to facilitate end users to find each other. This role is ruled by the Fermat Client Protocol.

##### Act as Identities Home

Each node is home to a set of end users identities. These identities can receive calls only through their home node. This role is also ruled by the Fermat Client Protocol.

##### Acts as a Call Bridge

Each node interconnects clients to each other in order to let them freely transfer information between them. This role is ruled by the Fermat Client Protocol.

##### Maintain the Fermat Blockchain

Each node maintains the Fermat Blockchain: a public record of all _coinbase_ transactions where the protocol issues new _fermats_, the native token of the Fermat System. This role is ruled by the Fermat Consensus Protocol.

#### Fermat Clients

Fermat clients run the Fermat Framework, which in turn runs the Fermat Components (Libraries, Add-ons, Plug-ins, GUIs, etc.). Clients adhere to the Fermat Client Protocol.


<br>
### The Ecosystem of Fermat APPs

A Fermat APP is an APP designed to run on the Fermat Framework. Fermat APPs are abstractions that make end users believe they are installing and running custom tailored APPs for some specific use cases. Technically a Fermat APP is a set of components interacting between each other with a certain user interface.

These components are part of an ever incresing pool of reusable components. Some new Fermat APPs add more components to this pool, some others just reuse components that are already there.

Underneath the sight of end users all these components collaborate with each other to provide the functionality of all Fermat APPs in the ecosystem. Their interactions are divided in _workflows_. Fermat APPs and all their components are part of a big master plan designed to maximaze reusability.

<br>
### Fermat Application Tokens

End Users requires tokens for at least two different unavoidable reasons:

**to pay for using components** : They are required to pay micro use licenses to component developers.

**to pay for bandwith** : They are required to pay Fermat Miners for bandwidth.

Ona first phase, the Fermat Foundation issues _fermats_ to incentivise the development of Fermat Infrastructure and Fermat APPs according to a road map and master plan. End Users must acquire these tokens from developers and holders in order to run Fermat APPs that require the payment of micro use licenses.

On a second phase, the Fermat Protocol issues _fermats_, with a predetermined algorithm that cannot be changed, and those tokens are necessary for The Fermat Network to function. Fermat miners are rewarded with _fermats_ for their contributions in running the Fermat network. End Users must acquire these tokens from miners and holders in order to pay for bandwidth on the Fermat Network. 

These application tokens are native to the Fermat system and are necessary to have access to the application. Fermat's blockchain only records the issuing of new _fermats_ and outsources the transaction processing from the bitcoin network. In this sense Fermat is a type II Dapp.[2]. Fermat tokens or _fermats_ are issued by the Fermat Foundation as digital assets on the first phase, and by the Fermat Protocol on the second phase.

![alt text](https://github.com/bitDubai/media-kit/blob/master/MediaKit/Tokens/Fermat%20App%20Token/PerspView/HQ_1920x1080.jpg "Fermat Application Token")

#### Token Records

Fermat's tokens (_fermats_) issuing data and records are cryptographically stored in a public, decentralized blockchain in order to avoid any central points of failure. This blockchain is stored at Fermat Nodes.

Fermat's tokens (_fermats_) issuing data and records are cryptographically stored in a public, decentralized blockchain of digital assets in order to avoid any central points of failure. This blockchain is stored at a Device Public Network owned by the Fermat Foundation and ran on top of the Fermat Network.


#### Token Generation & Distribution

Fermat implements three different mechanisms for token generation and distribution:

#### Fundraising Mechanism

With the fundraising mechanism, developer tokens (_fermats_) are distributed to those who fund the initial development of the Fermat System. The funds collected are used to fund the development of the core of the Fermat System (core libraries, api libraries, add-ons and the fermat.org web site). The tokens generated during the fundraising are recorded at the Fermat token developer blockchain.

##### Development Mechanism

With the development mechanism, development tokens are generated using a predefined mechanism and are only available for the development of Fermat components (plug-ins, GUI components, skins, language packages, and analysis of future development). These fermat components become available through a predetermined schedule and are distributed in two ways:

1. Via a community-driven contribution program [3] where different teams of developers and designers work either part-time or full-time on the project.

2. Via a community-driven bounty program [4] where decisions are made based on the proof-of-stake mechanism.

##### Mining Mechanism

The Fermat Protocol generates bandwidth tokens (_fermats_) according to a standard crytptographic algorithm acting as a proof of the value nodes are contributing to the application (Fermat uses a kind of Proof of Work Algorithm designed for the particular services Fermat nodes are providing).

With the mining mechanism, tokens are distributed to those who contribute most work to the operation of the Fermat Network. In this case, _fermats_ are distributed through a predetermined algorithm to the miners that connect clients between each other and allow clients to talk through them.

#### Token Issuing

The Fermat Open Source Project founders started issuing Fermat development tokens (_fermats_) in 2014. A complete disclosure of the issued tokens and distribution can be found at a separate document called Fermat Tokens. [5]

Bandwidth tokens (_fermats_) have not been issued yet. Their issuing will start once the Fermat Blockchain is implemented and miners start mining _fermats_. There will be no premined _fermats_ .

##### Fermat.org (Non-profit organization)

Fundraiser Fermat developer tokens (_fermats_) are issued by a non-profit organization called Fermat.org that will never receive financial benefits from the Fermat system. This organization has the following responsibilities:

* Issuing initial  _fermats_ tokens
* Holding developer  _fermats_ tokens
* Managing bounty payments
* Determining the Fermat System direction
* Collecting and distributing statistical information from the Fermat System

Fermat.org will make decisions in a decentralized manner, using a “proof-of-stake” voting mechanism for any decision once the project is mature enough. Meanwhile the direction of the project is determined by its founders.

##### Fermat Protocol

The protocol itself issues bandwidth tokens for miners. In the case of developer tokens, they are generated by the non-profit organization Fermat.org until awarded as bounty or contribution payments for developers.

#### Token Usage

End users automatically acquire _fermats_ by receiving bitcoins into a _fermat_ wallet of their own. They can go back to bitcoins by transfering _fermats_ into a Bitcoin wallet.

Development tokens can be acquired from the _fermat_ wallet from holders willing to sell them. From this same wallet the Fermat Framework debits the fees corresponding to the micro use licenses that End Users need to pay.

Fermat tokens are necessary for users to pay for three things:

##### Communication

End users pay Fermat Nodes with _fermats_ to be able to receive calls from other devices.

##### Use of Fermat Components

End users pay Fermat Component developers with _fermats_ to be able to use their plug-ins, GUI components, skins, language packages, etc. Developers defines a Micro Use License for each component. Products like Wallets or Financial APPs use these components, so the cost for using these products is the sum of the cost of the Micro Use Licenses defined by each developer involved. This is the way that developers are paid for developing and mantaining their components.

##### Technical Support

End users pay with _fermats_ to receive personalized technical support from Fermat APPs developers.

<br>
### Fermat Blockchain

Fermat's blockchain keeps track of all Fermat Bandwidth Tokens issued. Fermat outsources the transaction procesing of Fermat Tokens to one or more crypto networks. This means that Fermat newly issued _fermat_ tokens are registered at some crypto network with a technique similar to a _digital asset_ or _colored coin_, and the transactions representing the transfers of ownership are handled directly by these crypto networks with no intervention of the Fermat Blockchain or Fermat Network whatsoever. The first implementation of Fermat Tokens is using only the bitcoin network as it is currently the most reliable. Later, however, the decision of where to register new fermats will be taken by the miners winning the block reward. They will be able to choose between all the crypto networks integrated into the Fermat system at the moment of that event happening. During the rest of this document all further explanations will be referring to the bitcoin network as it is the first of multiple possible future implementations.  

The data structure is an ordered, back-linked list of blocks of transactions. In our case all the transactions are _coinbase_ transactions, meaning that they are transactions where new fermats are issued by the protocol. Blocks are linked "back", each referring to the previous block in the chain.

Each block within the blockchain:

* Is identified by a hash, generated using the SHA256 cryptographic hash algorithm on the header of the block.
* References a previous block, known as the parent block, through the "previous block hash" field in the block header.

<br>
### Mining

Mining is the process by which new _fermats_ are added to the token supply. Mining also serves to the main purpose of the Fermat Network: to enable devices to communicate with each other without going through trusted third parties. Miners provide bandwidth to the Extended Fermat Network in exchange for the opportunity to be rewarded with fermats.

Miners interconnect devices and act as a bridge relaying data from one device to the other. A new block, containing transactions that occurred since the last block, is "mined" every 10 minutes approximately, thereby adding those transactions to the blockchain. Transactions that become part of a block and added to the blockchain are considered "confirmed," which allows the new owners of _fermats_ to spend the _fermats_ they received in those transactions.

A transaction at the Fermat Blockchain is considered "irreversible" as soon as it is added to a block. This is true because it is based on information read from the bitcoin blockchain that it is already on an irreversible state. The Fermat Blockchain is synchronized with the bitcoin blockchain but 6 blocks behind the bitcoin blockchain's head.

#### Rewards

Miners receive two types of rewards for mining: new _fermats_ tokens created with each new block, and subscription fees from all the network clients that use that node as a home.

##### New Minted _fermats_

To earn this reward, the miners compete to sell incoming bandwidth to network clients, i.e. being their home node, Network Clients are free to choose which node to use as their home and at some point they pay in _fermats_ to these nodes for their services. Fermat "proof of work" consists on nodes proving that they have received payments for being a home node.

The amount of newly created _fermats_ that can be added to a block decreases approximately every four years. It starts at 50 fermats per block and it halves by 2 every 4 years. Based on this formula, fermat mining rewards decrease exponentially until all fermats (21,000,000 million) have been issued. After that, no new fermats will be issued.

##### Home Node Fees

Network clients will try to establish their home base at a nearby Node. This will help end users to find the end user behind the network client by knowing approximately where he or she lives (city & country). But the network client will scan all nearby nodes and finally decide where to stablish its home base on the plans and tariffs each node is charging for their services.

<br>
### Decentralized Consensus

The Fermat blockchain is not created by a central authority, but is assembled independently by every node in the network. The Fermat Protocol provides a set of rules that defines which _coinbase_ transactions are going to be added to the blockchain. As Fermat outsources the transaction processing features of the bitcoin network, it is easier for Fermat to arrive to a consensus.

#### Proof of Work

Fermat Proof of Work algorithm is designed in a way to prevent dishonest nodes to lie about the value they are adding to the network.

According to the Fermat Protocol these are the rules to be followed:

* Each node scans the transactions at the bitcoin blockchain block height: head - 6.
* They search for fermat transactions and aggregate all the ones that are payments to nodes in their node catalog.
* They order the node list considering the ones with the highest amount collected in fees first.
* If they are between the 25% of the nodes that:

a. Received the biggest amount payed by adding all payments.

b. Have been payed with the biggest number of different transactions.

c. The sum of the bitcoin mining fees is the greatest.

Then they are allowed to propose themselves as candidates to receive new fermats by participating in the Race to the Blockchain.

In other words, if the node making all these calculations is at the same time in three different sets of nodes, selected by different criteria, then they should consider themselves candidates for the race and should continue with the next step.


#### Race to the Blockchain

Immediately when a new block is mined at the bitcoin network, the following actions are taken by each qualifying node in order to see if they can earn the new fermats.

They create a _coinbase_ transaction racing between each other to be incorporated first by a bitcoin miner into the bitcoin blockchain at the next block mined. The first 10 % of valid transactions to be incorporated at the bitcoin blockchain will be the ones recorded by every Fermat Node on the Fermat blockchain by adding them on a new block. The recording will happen when that block has 6 more blocks on top of it.

As every node is reading confirmed bitcoin transactions and they all share a synchronized copy of the node catalogue, the Proof of Work algorithm should give exactly the same result to every node in the network. This means every node knows how many nodes should be part of the race, and how many _fermats_ they should add on their own _coinbase_ transaction in order for the 10% of all these nodes not to exceed the amount of fermats per block.

The sum of the amounts of all these transactions must not exceed the amount of fermats per block allowed by the Fermat Protocol.

#### Rationale

By using the fees payed by network clients as "proof-of-work" we discourage dishonest nodes to lie to the rest of the network about the value added. Network Clients will often pay after the service is delivered. Of course node operators can create fake fees to qualify for the race, but they will need to pay bitcoin mining fees for this and they are not guaranteed that they will win the race to the blockchain. In fact to have better chances to win the race they will have to invest in higher bitcoin miner fees in order to be included first, again without any guarantee of being among the first 10%.


<br>
### Independent Verification of Transactions

In Fermat, _coinbase_ transactions are recorded on the Fermat Blockchain. Previously, nodes recorded the candidate _coinbase_ transactions on the bitcoin network. Those transactions include the transaction hash on the OP_RETURN field that later is going to be critical to recognize the satoshis present on the other outputs as fermats by Fermat wallets.

#### Fermat Genesis Transactions

We call a _Fermat Genesis Transaction_ to each _coinbase_ transaction recorded on the bitcoin blockchain that has also been included on the Fermat blockchain. The structure of that transaction is the following:


| Input # | Contains | Output # | Contains |
|:-------:|:--------:|:--------:|:--------:|
| 1 | bitcoins | 1 | fermats |
| 2 | bitcoins | 2 | fermats |
| 3 | bitcoins | 3 | fermats |
| ... | ........ | ... | ....... |
| n | bitcoins | m - 1 | bitcoins |
|   |  | m | OP_RETURN |

Note that the _Genesis Transaction_ can have _n_ number of UTXO as INPUTS, all of them, bitcoins (or satoshis to be precise). It can also have _m_ number of OUTPUTS where all of them will represent _fermats_ except _m - 1_ which is reserved for bitcoin change and the _m_ which is used to place the Fermat _Coinbase_ Transaction hash on the OP_RETURN field.

As usual any difference between the sum of all OUTPUTS and the sum of all INPUTS are the bitcoin miner's fees.

So the amount of satoshis on each OUTPUT from _1_ to _m - 2_ are turned into _fers_ that is how we call a ten thousand part of a fermat.

1 fermat = 1,000 fers

We know that by doing this, 1 fermat has a minimun market value of 0.00001 bitcoins.

<br>
### Incentive

#### For developers

Plug-in developers declare a _Micro Use License_ for each plug-in they add to the Framework. Wallet or Financial Apps developers declare a _Micro Use License_ for their components. End users install the Apps (wallets) of their choice. The license to be paid is the sum of the Apps _Micro Use License_ plus all the _Micro Use Licenses_ of the plug-ins used by that App.

The Framework is responsible to charge end users and distribute the payments to all developers involved.

#### For network nodes

Network clients establish a _Home Node_ where they check themselves and their _actors_ in so as to be found by other network clients. They must pay a subscription fee to their _Home Node_ for its services. Finding and calling other clients through other nodes is free for the caller. The nodes income is covered by those network clients for whom they act as their _Home Node_.


<br>
### Crypto Networks

A set of Plug-ins is needed for each crypto network to be supported. One for interfacing the network, pushing outgoing transactions and monitoring incoming transactions. Another couple being the digital vaults where the crypto currency value and digital assets are stored.

Wallets are higher level abstractions and have their own set of plug-ins to keep each kind of accounting. This means that we split the accounting from the handling of the value by having components on different layers to handle each activity.

<br>
### Identities

We handle identities at different levels for multiple reasons. In all cases, identities are represented by private and public keys.

#### End Users Identities

The need to handle multiple logins on the same device brings with it, the first kind of identity which we call _device-user_. This identity lives only at a certain device and not even a public key is exposed to the network.

Besides, the end users can have multiple types of identities (we call them _actors_), and within each type as many instances as they want. Each type of identity corresponds to a role in real life or an _actor_ in a Use Case. Usually each Platform introduces a set of _actors_ and all the Platform's functionality orbits around all the use cases derived on the interactions between those _actors_.

The Framework handles a hierarchy of identities. One of them is what we call the _root identity_. At root level end users can set a standard set of information that can be overwritten at any level down the hierarchy, narrowing or expanding that information as needed. All these identities are exposed to the Fermat Network in a way that from the outside, no one could tell they are related between each other or to a certain end user.

#### Component Identities

Many components have their identities for a variety of reasons:

a. Plug-ins to identify themselves to Add-ons in order to get access to identity-specific resources, such as Databases or their own share of the File System.

b. _Network Services_ to encrypt the communication between each other.

c. Network Clients to encrypt the communication with nodes.

d. Nodes to recognize each other even when their IP location or other profile information changes.

<br>
### Platforms

We define as a _Platform_ a set of interrelated functionalities. _Platforms_ may consume services from other _platforms_ and their dependencies form a hierarchical stack.

Each _Platform_ may introduce new workflows to the system, Add-ons, Plug-ins, GUI components (Apps, wallets) and _actors_. This enables the system to target different use cases with different _actors_ involved.


<br>
### Workflows

We define workflows as high level processes that require several components to achieve a certain goal. Many workflows start at a GUI component triggered by end users and spans through several plug-ins on the same device, and in some cases jumping into other devices. Other workflows may start at some plug-ins, triggered by events happening within the same device.

From a workflow point of view, each plug-in runs a task and is fully responsible for doing its job. Workflows are a chain of tasks that may split into several paths and may span through more than one device.

In some cases workflows interconnect with each other, forming a _workflow chain_ that usually spans more than one _Platform_.

<br>
### Transactions

#### Transactional Workflows

As the Framework runs on potentially unstable devices such as mobile phones, each plug-in must be prepared to overcome the difficulties caused by a device shutting down at any moment and it must be able to complete its intended job later and never leave information on an inconsistent state. This is quite challenging but not impossible.

The solution is to make each plug-in responsible for the workflow while they are handling part of a transaction on a transactional workflow. This responsibility is transferred to each step of the chain using what we call a _Responsibility Transfer Protocol_. This means that the component that is responsible at the moment of a black out is the one that must resume and do its best to get rid of that responsibility moving it further down the chain within the transactional workflow.

#### Value Transactions

We handle monetary and digital assets transactions dividing the accounting from the value. Usually transactions start on specialized plug-ins which are in charge of coordinating the whole transaction. These plug-ins usually interact with wallet plug-ins debiting or crediting the accounts involved. The accounting of the currency or digital asset involved are kept by these wallet plug-ins. Later the transactional workflow splits between moving the value (usually crypto currency) and moving the meta-data associated to the transaction.

Through two different paths, the value and the meta-data arrive to the recipients device and they are combined together by the remote counterparty transaction component which in return interacts with the remote wallet plug-in to record the accounting as appropriate.

<br>
### Synchronization

We define a Private Device Network as a network of devices owned by the same end user. Using the Fermat Network, the Framework synchronizes the information on all nodes of the Private Network. By doing so the information and system identities belonging to the end user are available at any end user's devices.

Crypto funds are kept in a _Multi-Sig-Vault_ and in a _Petty-Cash-Vault_. The funds at the _Petty-Cash-Vault_ are accessible from all nodes even when they are offline from this Private Network. An automated process monitors the _Petty-Cash-Vault_ and tops it up when needed. Several nodes must sign the top-up transaction in order to proceed.

If a device is lost or stolen, only the funds at the _Petty-Cash-Vault_ are at risk. End users can eject stolen devices from their Private Network and if they act quickly, the system might be on time to recreate the _Petty-Cash-Vault_ under the new configuration of the Private Network and be able to move the funds from the previous _Petty-Cash-Vault_ to the new one.

<br>
### User Interface

The Framework handles a stack of layers. Starting from the bottom we have the _OS API level_, then the _Blockchain Level_, the _Communication Level_, _Platform Level_ and the _User Interface Level_. With the goal in mind for allowing even non-developers to deploy their own peer-to-peer financial applications, we define several concepts:

**Wallet**: Any kind of financial application that handles either crypto or digital assets for any purpose.

**Reference Wallet**: A primitive wallet that is used by a single _actor_ for a handful of use cases.

**Niche Wallet**: A combination of several _Reference Wallets_ into a single product with its own look and feel and possibly extra functionality.

**Branded Wallet**: A _niche wallet_ turned into a new product owned by a different end user. Achieved by a process similar to building a WordPress site but locally, on the end user's device. Usually it involves reusing the business logic of the _niche wallet_ it derives from and adding a new look and feel (different skin and navigation structure).

**External Wallet**: A third party APP running on the same device that uses Fermat as a backend for different reasons. For example to benefit from its infrastructure to interface crypto networks, transporting data through its p2p network, or storing data on the end user's _Private Device Network_.

<br>
Several tools were designed with the purpose of enabling the development of new wallets, and their distribution.

**Wallet Factory**: Is a built-in functionality that enables the development of reference and niche wallets.

**Wallet Editor**: Enables the creation of _Branded Wallets_ by non-developers based on any one of the _Niche Wallets_ available.

**Wallet Store**: Is a distributed application which manages a shared wallet catalogue and enables end users to download from peers the different wallets available for the Framework.

<br>
### Privacy

The proposed system complements the privacy properties of crypto networks, extending them to the full stack needed to run different kind of financial applications. By using its own P2P network with point to point encryption for transporting meta-data both value and information are under a similar privacy standard.

Identities are public keys related to private keys kept by end users and never shared to anyone in any way.

The collection of system information for visualization and statistics uses hashes of public keys to protect end users privacy and at the same time preserve the relationships between them.


<br>
### Conclusion

We have proposed a system for developing and running peer-to-peer financial applications. Fermat shows the way of how to keep end users away from trusted third parties at a higher level. We have proposed solutions to several problems at the same time. The highlights of our work are:

* How to exchange meta-data in a peer-to-peer way
* How to prevent the loss of private keys (funds and identities)
* How to maximize reusability by building with plug-ins
* How to enable even non-developers to create and use their own wallets and financial applications.

We have shifted the paradigm from competition to collaboration by designing and building pieces of a large master plan where anyone can collaborate and be part of it. At the same time we are giving end users the "illusion" of using a custom tailored financial application, while underneath the skin a set of plug-ins are collaborating to provide end users the desired functionality and present it in the exact way they feel comfortable with.

As Fermat applications and wallets are a set of plug-ins, the more applications built, the more plug-ins are added to the system and more unique components are ready to be reused. This produces a positive feedback loop on the development of Fermat applications.

We have created an environment where the user base of the system is a shared asset and anyone can profit from it, and by doing so, all contributions to the system are aligned to the shared goal of driving mass adoption.


<br>
### References

* [1] Satoshi Nakamoto, "Bitcoin: A Peer-to-Peer Electronic Cash System", https://bitcoin.org/bitcoin.pdf, 2008

* [2] David Johnston & others, "The General Theory of Decentralized Applications, Dapps", https://github.com/DavidJohnstonCEO/DecentralizedApplications/blob/master/README.md

* [3] Fermat Contribution Program, [https://github.com/bitDubai/contribution-program](https://github.com/bitDubai/contribution-program)

* [4] Fermat Bounty Program, [https://github.com/bitDubai/bounty-program](https://github.com/bitDubai/bounty-program)

* [5] Fermat Tokens, [https://github.com/bitDubai/fermat-tokens](https://github.com/bitDubai/fermat-tokens)




<br>
### Further Reading

* DRAFT: Fermat Book - https://github.com/bitDubai/fermat/tree/master/fermat-book
