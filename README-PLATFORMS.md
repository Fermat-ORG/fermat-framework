<!-- all links tested by laderuner -->
![alt text](https://github.com/bitDubai/media-kit/blob/master/MediaKit/Fermat%20Branding/Fermat%20Logotype/Fermat_Logo_3D.png "Fermat
Logo")

<br><br>
## Introduction: Platforms & Super Layers

First of all we need to explain how we have separated each large structure of the framework into two concepts: '_Platforms_' and '_Super Layers_'.
<br>
When it comes to code they are both the same, however the main difference is that the Super Layers can be consumed by all 
Platforms (IE.: '_Communication_' or '_Blockchain_' Platform).

There is also another difference which is that the Platforms can consume each other in an incremental order from 
left to right, so that each Platform can consume all the previous ones. An example of this can be the following:
The '_Fermat PIP_' Platform will only be able to consume the '_Fermat COR_' Platform, and the '_Fermat WPD_' Platform 
will be able to consume the two previosuly mentioned Platforms, and so on.

The Super Layers behave in a similar way, in that they can consume each other in a descending order from top to bottom,
so that each Super Layer is able to consume the previous ones. An example of this case is pretty straightforward: 
The '_Fermat BCH_' Super Layer is only able to consume the '_Fermat OSA_' Super Layer, and the '_Fermat P2P_' Super Layer
is able to consume the previously mentioned Super Layers.

As a reference, please enter the [fermat developer web's] (http://dev.fermat.org/) '_Architecture_' segment, in which you will be able to see all the previously mentioned relationships.

Before we move forward with other topics, we think it will be useful to point out the system **Layout**, in order for you to be able to put altogether the concepts of this document. It goes as follows:

* Platforms :arrow_right: Layers :arrow_right: Plug-ins :arrow_right: Developers :arrow_right: Versions

-
### Layers

Now that we have covered the two main aspects of the system's architecture (Platforms & Super Layers), we will go into
detail about their components.

Within each Platform there are one or more Layers, and one of the main aspects of them is that they have the
functionality to group up several '_Plug-ins_' that have the same behavior.
<br>
There is another aspect related to this, which is that the Layers also act as **Merchants** in the way that given a 
specific request (i.e.: We need to compare the exchange rate of two different coins) they will only consume the required
Plug-ins (and only the required functionalities) to fulfill the request. 
[//]: # (lcommunication example)

-
### SubSystem

Following Layers (see reference _Layout_ in the _Introduction_) we have the Sub System Classes in which you can register all the Add-ons and Plug-in's Developers.

-
### Developers

This last group is the one that has the logic to deliver the best Version of the Plug-in, with the possibility to perform migrations (i.e.: Update the Database of a given Plug-in, update files, etc.)

[//]: # (S/CER manages prices and quotes, this layer will have an index that shows the prices/quotes)
[//]: # (IE: Blockchain platform- crypto network layer, it holds the network of all XXX available)

---
##New Platform Generation

We will now continue to describe the **Platform Directory Distribution** standard for the Platforms on Fermat:

* PLATFORM_NAME/
* libraries/
  * core
  * api
* plugin
* addon
* android
* linux

As this is the standard, depending on the Platform there are many more features to include in this list.  

-
###Platform Core Classes Structure

By default, the structure we follow for the _Platform Core Classes_ when we either need to register or create all the components is as follows:

* AbstractPlatform
  * registerLayers
* AbstractLayer
  * registerAddons/ register Plugins
* AbstractAddonSubsystem
  * registerDevelopers
* AbstractPluginSubsystem
  * registerDevelopers

Please keep in mind that in order to obtain a readable code, we create all the components alphabetically.

-
####settings.gradle
Each platform mantains its own settings.gradle ordered by layer and component type.
<br>
Please keep the elements ordered alphabetically in order to make the code more readable.
 * Type (_addon/android/plugin_)
 * Layer name.
 * Name.

-
###Fermat Core Classes Structure
[//]: # (Insert content)

-
###FermatSystem
The _FermatSystem_ starts all the components of the platform and manages them, here we have to register all the platforms we need for the framework.
<br>
In the _build.gradle_ of the core we must also add all the pertinent references to the _Platform's Core Modules_.
When you create a new instance of the fermat system, you must provide it with an _osContext_ and an _osaPlatform_,
this is the way we found to make the framework _Multi-OS_, each operative system can provide its own _osa_ libraries and add-ons to use as needed.

* FermatSystemContext
The _FermatSystemContext_ holds all the references of the main components of Fermat, it initializes all the Platforms taking the parameters mentioned in _FermatSystem_, it also allows you to add a new _Layer_ to a given Platform.
<br>
This class also possesses several methods which allows you to initialize several components such as:
 * _AddonVersion, addonDeveloper, PluginVersion, pluginDeveloper, PluginSubsystem, Layer_ or even a _Platform_ instance.

* FermatAddonManager
The _FermatAddonManager_ class centralizes all the required service actions (Start, Pause, Stop, Resume) of the _Add-ons_ in Fermat.

* FermatPluginManager
The _FermatPluginManager_ class centralizes all the required service actions (Start, Pause, Stop, Resume) of the _Plug-ins_ in Fermat.
<br>
It also allows you to get an existing or a new instance of the _FermatPluginIdsManager_.

* FermatPluginIdsManager
The _FermatPluginIdsManager_ contains all the main functionalities to manage all the Fermat _Plugins Ids_.
<br>
This class contains methods that allows you to perform a number of actions such as:
 * Charge or Create Saved Ids.
 * Obtain a _PluginsId_ or even the _PluginIdsFile_, as well as registering a new _PluginsId_ or save a _PluginIdsFile_.
