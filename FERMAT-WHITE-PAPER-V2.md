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

This document is a DRAFT with no formal reviews yet. Some changes are being done from time to time. If you want, you  can make a formal review of this document. We will be happy to add you to the _reviewers_ section.


<br>
## Abstract

We envision a world where crypto currency is already mass adopted, where fiat currencies are already digital and where people have the freedom to choose which currencies to use and where to store their value: at third party regulated institutions with all their known pros and cons, or at their own devices, out of the reach of hackers, tech giants, marketeers, bankers and governments.

In this future people can manage multiple online identities and interact with the rest of the world using the one that best fit their needs, exposing only the information they find tune to be exposed in each situation. They can also transact with anyone in the world with whatever privacy level they choose: from attaching their real life identity and using formal channels, to detaching from any real life reference and not leaving traces of their transactions and information exchange with others.

People of this future have in their pocket not only their money, but a whole software suite that can use their digital money to enable and sustain all sort of p2p business models. A whole ecosystem of powerful APPs that share some common properties: they allow an intermediate-free running of present time business in different industries, that can operate with both digital fiat and crypto currency, and that are censorship resistant.

We are building Fermat to get into that future in the fastest way possible. And to discover what kind of system to build, we traced back the path from that future into present times, we have identified all the challenges that needed to be addressed and all the obstacles that needed to be overcome. We put everything that had to be done into a huge master plan and started developing the first phase of it. The result is a system that rocks the foundations of many established paradigms and with such, a disruptive potential that its final effects are difficult to measure or even project at present times.

This white paper does not describe a theoretical system that might work one day to get us to that future. It describes a system that already works and it is in alpha testing right now. It also describes the organization of several networks of people spanning different disciplines and countries collaborating to produce the most ambitious project in the industry.

## Part I : Overview

In this section you will get an overview of the Fermat System to describe the general idea. After this, you will be ready to re-explore the concepts more detailed in the next section.

### Introduction

Since the last technical barrier to issue digital money has already been overcome with the invention of bitcoins [1] - the double spend problem -, there is little doubt that governments of the world are on the way of digitalizing cash. As soon as the US or EU achieve this, the rest of the world will follow suit. So, this part of our envisioned future is already on the way to happen and there is no need for us to intervene in any way except to handle in Fermat the fact that money is going to be digital and paper money is going to disappear. This means that our mission can be condensed into designing a system that:

* Makes crypto currency technically possible to go mainstream.
* Encourages the code production to make this happen in the least amount of time possible. 
* Allows people to safely store digital value by themselves.
* Creates APPs that understand digital fiat and crypto currencies.
 
To achieve these goals the system must:

**Be Censorship Resistant**

In order to survive overregulation the day that crypto currencies really start competing with fiat money.

**Address All Use Cases** 

It must guarantee that either crypto currency gets mass adopted or that there is no other way to reach that goal.

**Promote Collaboration** 

It must produce a shift of paradigm in software development, moving it from competition to collaboration, which helps to avoid the software waste which is produced when startups fail.

**Reuse What Already Works**

It must reuse what the industry has already taken time and effort to produce and to make work, for example bitcoin and several other low level protocols and systems.

**Enable Segmentation**

People around the world are different one from the other. There is a strong need to deliver each audience custom tailored solutions.

**Clear Governance Model**

It must be governed by a [BDFL](https://en.wikipedia.org/wiki/Benevolent_dictator_for_life) model like the most successful open source projects (i.e. Linux) in order to avoid deadlocks decisions -like the bitcoin block-size- in the short and middle terms, and by a proof-of- stake mechanism on the long term.

**Multi OS**

It must be able to run in major operating systems.

**Community Driven**

It must be open to anyone in the community in order to participate developing the system or using it as end users or business operators.

### System Properties

We designed Fermat to hold all these eight properties at the same time, what originated certain designing constraints we are going to examine now.

#### Censorship Resistance

Bitcoin is a clear example of a censorship resistant system because of its key properties:

* It is open source.
* It runs on a p2p network which anyone can join.
* Its client software runs on end user devices (cloud services using bitcoin are not censorship resistant).

Fermat extends these properties up to the stack to inherit the censorship resistant qualities of bitcoin. This is the reason why Fermat:

* Is open source.
* Runs on a two level p2p network composed by network nodes and network clients. 
* Runs its client software on end user devices (third party functionality on Fermat may access cloud servers rendering that specific functionality not censorship resistant).

By Fermat being censorship resistant, all Fermat APPs built on top of it will inherit this property and they will be shielded in hostile jurisdictions enabling each of them to reach mass adoption even in places where their competition to local currencies or businesses is not welcomed. 

 
#### All Use Cases

Nobody knows which use case will bring mass adoption and certainly nobody can guarantee that the single one use case they are working on is going to achieve it. The only way to guarantee that mass adoption is reached (or accept that it is not possible) is to implement all possible use cases. But in this industry all possible use cases have not even been imagined yet. This means that we need to think about an open ended system.

**Open Ended System**

We must think of Fermat as a system with no visible end, so it must be designed to grow horizontally implementing all the known use cases, and open for not yet imagined use cases to be added in the future. And everything needs to fit into the same architecture.

**Master Plan**

We define a long term master plan with everything that needs to be done, and considering all possible use cases. Then we all collaborate building the pieces of that master plan, in order, phase by phase. The master excludes duplicate functionality thus we eliminate duplicate efforts and save time and money.

#### Promote Collaboration

Most of the startups die. That is a known fact and with this collapse, they throw away the written code most of the time. Even when their code is open source, when the maintainer leaves the project, it is trashed. That is not the most efficient way to move the industry forward, it is just the way it has been working until now. 

The current startup model is comparable to evolution: there is a constant competition between individuals and the one who survives has the right to evolve and transmit their winning DNA to their children and so on. That model was OK on species until modern times, but it is not necessarily the most efficient considering the human time scale. The collaboration model of science achieved in a few centuries many improvements in knowledge and technology that would have taken isolated individuals much longer, like the usual times in evolution, measured by thousands and millions of years. Not collaborating is like to sit and wait for evolution to finds a cure to each of the multiple diseases itself, affecting human beings instead of trying to find it with all our scientific power working together.

We believe in the collaboration model of science (a massive world wide collaboration effort) with an integrated business model that supports whoever decides to participate on the common venture.

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

This means that Fermat can be used by any regular Mobile APP as a local back-end at the same device they are running in, transmitting to them their core properties like censorship resistance, and all the services available within the Fermat Framework. At the same time giving mobile APP developers the freedom to develop their mobile APPs without external constraints.

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

Fermat must be designed for massive collaboration. We expect thousands of developers to join the project and the system therefore project management must be able to deal with this fact. Several human networks need to exist to produce such a massive system:

**Developers Networks**

Fermat developers organize themselves in small teams. In some cases teams arrange themselves in networks with their own hierarchies. The largest network working on Fermat has over 35+ full time developers working on the project by Feb 2016. [6]

**Designers Networks**

Building front-end APPs requires tons of designer hours. Fermat has a growing network of designers. Between them we can find graphic designers, UI, UX, 3D designers and Video producers. 

**Business Operators**

On top of the software layer where developers and designers are encouraged by these micro payments, business operators run real world business models, reusing the same codebase, but fine tunning their business parameters to target different audiences at different corners of the world. 

#### Other Design Considerations

Designing a system that is going to deal with digital money and is going to run on an uncontrolled environment (end users devices) where anyone can add new components its quite challenging. Several concerns have to be addressed in order to make the system reliable. Some very important ones are:

* How do we prevent money from being lost in the event that one device suddenly shuts down and the transactional process gets interrupted?
* How do we prevent one component from stealing funds from others?
* How do we prevent users to loose their funds if their device is lost or stolen?

Thinking carefully about each of the system requirements described above, a lot of similar questions arise. So far most of those questions have been addressed and a system architecture has emerged out of finding the answers to all those questions. 

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

Fermat runs on end users devices. Phones, tablets, laptops, PCs. It can also run on web servers or wherever it is needed. One of our design constraints was to create a censorship resistant system. Since these devices need to communicate to each other in order to exchange information the need for a p2p network to enable this communication without going through third parties arose. If device to device communications were to go through centralized service providers, then the system could be shut down by censoring those centralized points.

Architecturally, we describe the Fermat p2p Network as having two levels:

**Network Nodes** : Are the devices that interconnects other devices and lets them talk between each other through them.
**Network Clients** : Are the devices that use Network Nodes as a bridge to get connected to other Network Clients.

The Fermat p2p Network of Nodes is the main tissue connecting devices running the Fermat Framework, though it is not the only one. You will learn later that the Framework is designed to use the Fermat p2p Network when it cannot find a more direct way of connecting to the target device, like a shared WiFi connection, blue-tooth, NFC, etc.

To avoid confusion we call the Fermat p2p Network of Nodes directly "The Fermat p2p Network", and when we want to also include Network Clients, we use the term "The Fermat p2p Extended Network".

##### Fermat p2p Network

By allowing devices to interconnect to each other we open many possibilities to our system. Now transactional processes can span one device and continue into others, and in some cases the workflow comes back to the original device to finish the transaction. This is extremely powerful since we are not talking about a device accessing a third party API, but a system accessing another copy of itself running on another device. 

Think about two biological cells, each one with the same DNA, and both being part of a higher level organism built out of these cells. When we talk about the Fermat system, we refer to this higher level organism of many cells.

In this metaphor, the Fermat p2p Network would be the nervous system that allows these cells to send information to each other and in that way coordinate whatever tasks or business they have between each other. Cryto currency networks would be like the circulatory system that is not designed to transport information, but to transport value. Fermat has no intention in replicating what is already working, in fact one of our premises is "Reuse What Already Works".

>>>> INSERT INFO GRAPHIC HERE EXPLAINING THE PREVIOUS PARAGRAPHS  <<<<

#### The Fermat Framework

We need a Framework to run Fermat APPs on top of it. This Framework sits on top of the OS and extends it in order to support Fermat APPs. 

We define Fermat APPs as end user APPs running on top of the Fermat Framework. These APPs are in fact a set of reusable components, one of them being the user interface. 

Fermat APPs are not downloaded by end users from the Apple APP Store or Google Play Store. The only thing downloaded from those stores is Fermat itself. Fermat APPs are an integral part of the Fermat system and they do not need to be downloaded. On the other side the resources these APPs uses (images, sound and video files, language packages, etc.) do need to be downloaded upon the request of the end user.

That means that we need a special Fermat APP to give the end user the illusion they are downloading and installing a Fermat APP. We call this Fermat APP the _Fermat APP Store_. So no APP on this store catalog is really downloaded, what is really happening is that they are activated and the Framework makes them visible, because the pre-installed components are already there. Besides that, resource files do need to be downloaded. The _Fermat APP Store_ will try to download those resources from other peers and if it is not possible it will go to a centralized place to find the seeds.

Up to this point Fermat is a Framework that extends the OS capabilities enabling it to run Fermat APPs, which in turn are very similar to mobile APPs from then end users point of view, but underneath the skin they are a set of components interacting between each other. Probably a few of them were built for an specific Fermat APP while the rest are part of a pool of reusable components that increases each time a new Fermat APP is added to the system.

##### Multi-Layer Design Pattern

We choose d a multi-layer design pattern to arrange the thousands of components that this system is going to require. The core idea is simple: 

* Components give services to other components on upper layers.
* Components consume services from other components on lower layers.

This simple rule helps to organize this crowd of components with some criteria, but it doesn't help by its own to define how many or which are the layers needed. To discover the needed layers we designed several Fermat APPs targeting different use cases, then we split these APPs into atomic components, each one with different responsibilities, we compared all the components and defined layers for components with the same set of responsibilities.

In this way we introduced our concept of layers: a logical place where components with the same set of responsibilities live inside the Fermat Framework.

Throughout the first 18 months of the project, the following layers emerged:


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

As you might have noticed we chose Java as the programming language for most of the components inside Fermat, except for the ones that live at the upmost and lowest layers. The rationale for doing so is that we wanted most of the codebase to be portable to other OS. At the upmost layers we decided to allow native user interfaces, and for that reason, our first target OS, Android, needs its components to be written in Java-Android.

In the case of the lowest layers we encapsulate the access to the OS into several components that are OS dependent, while the rest of the components at the top layers are not (except the ones related to the user interface).

Finally, we noticed that many layers were independent of end user's use cases implemented above them. We grouped these layers into what we called _Super Layers_. A _Super Layer_ is then a group of use case independent layers that have some logical connection between them.

#### The Ecosystem of Fermat APPs

Our design constraint here was that we need to implement all possible use cases and do it according to a master plan, phase by phase. We already know that Fermat APPs are a set of reusable components that goes to a pool organized in layers. Still, there is a need to organize that pool into different compartments with all the components related to similar use cases.

So vertical division (in contrast to the horizontal division in layers) emerged and we called each compartment a _Fermat Platform_. We noticed some things that were common to all platforms:

* They introduce new actors.
* They introduce new use cases, usually between the new actors.
* Some platforms reuse components and their services from other platforms.

After analyzing several use cases we found out that it was possible to create a stack of platforms, where platforms up the stack reuse components from platforms down the stack. And at the same time components where organized in layers inside each platform. For the first phase of the Fermat System Master Plan, we identified the following low level platforms and implemented some of them:

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

<br>
### Business Model

We had to design a business model that works fine for several different actors. On one side we need Fermat p2p Network nodes operators to provide their hardware and bandwidth to interconnect end users. On the other side we need developers to write the code of this massive system. We also need entrepreneurs to run business on top of what developers build. And finally we need end users to find value on the Fermat APPs they use.

We divided the challenge of creating incentives for everybody in the following way:

#### Fermat Miners

We believe there is a need for an application token system that can be mined in order to encourage nodes operators to put their hardware and bandwith to work for the Fermat p2p Network. Lets call these tokens _band-with tokens_ or _b-fermats_.

The business model for miners consist of:

* End users paying miners a fee in _b-fermats_for the bandwith provided.
* Miners getting newly _b-fermats_ issued by the Fermat Protocol according to a _Proof of Work_ algorithm based on bandwith, not CPU power as bitcoin.

So basically is the same model that bitcoin in the sense that we need to bootstrap the Fermat Network at the beginning by letting the protocol to issue tokens, that users are going to need in order to pay for the fees required to use that network. 

As we have a premise of reusing what is working, instead of handling these tokens transactions by ourselves, we are outsourcing the bitcoin network to do that for us. With this in mind, our blockchain will only need to record _coinbase_ or newly issued tokens transaction, while transfers from user to user can be handled directly by the bitcoin network itself.

We chose the same economic parameters than the bitcoin network itself: 21 million units, generated every 10 minutes approximately, staring with 50 per block and having this number halved every 4 years. The intended effect would be that a diverse community of nodes operators arise encouraged by the block reward, while there is not enough user base to profit from the selling of bandwith to end users.

NOTE: The current implementation of the Fermat p2p Network does not include the mining scheme yet, meaning that no _b-fermats_ have been mined at present time.

#### Developers

#### Business Operators




## Part II - In Details



