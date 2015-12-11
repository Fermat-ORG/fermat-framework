![alt text](https://github.com/bitDubai/media-kit/blob/master/Readme%20Image/Fermat%20Logotype/Fermat_Logo_3D.png "Fermat
Logo")

<br><br>

## Introduction: Platforms & Super Layers

First of all we need to explain how we have separated each large structure of the framework into two concepts: '_Platforms_' and '_Super Layers_'.
When it comes to code they are both the same, however the main difference is that the SuperLayers can be consumed by all 
Platforms (IE.: '_Communication_' or '_Blockchain_' Platform).

There is also another difference which is that the Platforms can consume each other in an incremental order from 
left to right, so that each Platform can consume all the previous ones. An example of this can be the following:
The '_Fermat PIP_' Platform will only be able to consume the '_Fermat COR_' Platform, and the '_Fermat WPD_' Platform 
will be able to consume the two previosuly mentioned Platforms, and so on.

The Super Layers behave in a similar way, in that they can consume each other in a descending order from top to bottom,
so that each Super Layer is able to consume the previous ones. An example of this case is pretty straigtforward: 
The '_Fermat BCH_' Super Layer is only able to consume the '_Fermat OSA_' Super Layer, and the '_Fermat P2P_' Super Layer
is able to consume the previously mentioned Super Layers.

As a reference, please enter the [fermat web's] (http://fermat.org/) '_Architecture_' segment, in which you will be able to see all the previously mentioned relationships.

Before we move forward with the other topics, we think it will be usefull to point out the **Layout** of the system, in order for you to be able to put alltogether the concepts of this document. This goes as follows:

* Platforms :arrow_right: Layers :arrow_right: Plugins :arrow_right: Developers :arrow_right: Versions

-
### Layers

Now that we have covered the two main aspects of the system's architecture (Platforms & Super Layers), we will go into
detail about their components.

Within each Platform there are one or more Layers, and one of the main aspects of them is that they have the
functionality to group up several '_Plugins_' that have the same behavior. 
There is another aspect related to this, which is that the Layers also act as **Merchants** in the way that given a 
specific request (IE.: We need to compare the exchange rate of two different coins) they will only consume the required
Plugins (and only the required functionalities) to fulfill the request. ---lcommunication

-
### SubSystem

Following Layers (see reference _Layout_ in the _Introduction_) we have the Sub System Classes in which you can register all the Addons and Plugins Developers.

## Developers

This last group is the one that has the logic to deliver the best version of the Plugin, with the possibility to perform Migrations (IE.: Update the Database of a given Plugin, update files, etc.)

4. dentro de developer esta la logica para entregar la mejor version del plugin, con la posibilidad de realizar
migraciones (updt db plugin X, updt files, "pasaje" files).

S/CER maneja los precios y cotizaciones, esta capa va a tener un index que pasa las cotizaciones 

Tareas: explicar Core, Gradle (referencias a fermat-api), no indagar en Android(link al de furzyfer)

centralizador.
IE: Blockchain platform- cryptonetwork layer, la cual posee las network de todos los XXX available.


New Platform Generation

Platform Directory Distribution

PLATFORM_NAME/
libraries/
  core
  api
plugin
addon
android
linux
etc. and much more else



Platform Core Classes Structure

By default we always register or create the things ordered alphabetically

AbstractPlatform
-registerLayers
AbstractLayer
-registerAddons / register Plugins
AbstractAddonSubsystem
-registerDevelopers
AbstractPluginSubsystem
-registerDevelopers

settings.gradle
  each platform mantains its own settings.gradle ordered by layer and component type
   Please keep the elements ordered alphabetically.
 * -Type (addon/android/plugin)
 * -Layer name.
 * -Name.

Fermat Core Classes Structure

FermatSystem
starts all the component of the platform and manage it.
Here we have to register all the platforms
In the build.gradle of the core we must add all the references to the platform core modules
when you create a new instance of the fermat system, you must provide it with an os context and an osa platform
is the way that we found to make it multi-os, each operative system can provide its own osa libraries and add-ons

FermatSystemContext
the system context hold all the  references of the mains components of fermat.

FermatAddonManager
centralizes all service actions of the addons in fermat.

FermatPluginManager
centralizes all service actions of the plugins in fermat.

FermatPluginIdsManager
contains all the main functionality to manage fermat plugins ids.
