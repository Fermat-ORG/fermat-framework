![alt text](https://github.com/bitDubai/fermat-graphic-design/blob/master/2D%20Design/Fermat/Fermat%20Logotype/logo_jpg/logo_github_readme.jpg "Fermat Logo")

<br><br>

## Introduction

Plug-ins are one of the three basic components that can be added into the Fermat Framework. The two others are Addons, and GUI components. Enach Plug-in has a well defined responsability within the system at usually collaborate from within one or more workflows in which it participates.  

To accomplish it's mission, a Plug-in may have have it's own database and files to persist it's state. It also has an internal structure of classes designed specifically to fullfill it's goals. Some of these classes have public interfaces which exposes the Plug-in public services to other Fermat components.


<br>

## Part I: Concepts

Several new concepts are introduced...

<br>
### Subsystem Class
* Is where we define and identify the plugin, we have to assign a Plugin Reference to it.
* Here we will register all the Developers who've made a version of the plugin.

<br>
### Developer Class
* Each developer of a plugin must have his own Developer Class and define it in a specific package with his name.
* Here we can register all the Versions of the Plugin created by the Developer.
* (Plugin Licensor? Explanation?)

<br>
### Plug-in Root
* Is the main Class of each Version of the plug-in. This Class must implements AbstractPlugin of fermat-api.
* A plug-in haves two particular behaviors:
 * It's a Service, and with this, we're saying that it can be managed like one, for this haves four basic methods, start, stop, pause or resume, and to keep the status we have a **ServiceStatus** [CREATED-STARTED-STOPPED-PAUSED].
 * if its necessary to do something during the start-up of the plugin, you must override services methdos.
 * It haves an identity, given by the fermat-core. We may use this **pluginId** to add security in our plug-in. Throw it we should encrypt and decrypt the databases, files, etc. that the plug-in manages. 
* It can be referenced, for this it haves a PluginVersionReference.
* It can ask for dependences to the core, in the original core we specify the dependences throw the implementation of DealsWithOtherPluginOrAddon Interface, now we've two annotations to ask for this dependences: @NeededAddonReference @NeededPluginReference

<br>
### Plug-in Public Interfaces
* The must of the plug-ins can be consumed. For this we'll define in the api of the platform where the plug-in belongs the public interfaces of the same.
* If any method of the interface throws an exception, enums or any other, this exception must be public too, and must be created in the api too. 
* We've a structure for this:
 * Interfaces:  Layer -> PluginName -> Interfaces
 * Exceptions:  Layer -> PluginName -> Exceptions
 * Enums:       Layer -> PluginName -> Enums
 * Events:      Layer -> PluginName -> Events
 * Annotations: Layer -> PluginName -> Annotations
 * Utils:       Layer -> PluginName -> Utils

<br>
### Internal Structure
* We tend to repeat the same conventions in each part of the fermat, with this we're trying to say that, like in the interfaces defini

<br>
### Event Handler

<br>
### Database

<br>
### Agents


<br>

## Parte II: Workflow

This section will help you undestand the workflow needed to be followed in order to implement a Plugin in Fermat.

<br>
### Getting Organized

#### Issues

It is mandatory that you create an initial set of github issues before you proceed further on the workflow. This will show the rest of the temas that someone is working in this functionality and avoid conflicting work early on. It will also hook the team leader into your workflow and allow him to guide and advise you when needed.

A basic hiriarchy of issues is created as a first step. The issues are linked one to the other just by placing a link on the first commet.

##### Naming Convention

Where we refer to '_Plugin Name_' what we expect is the following information:

* Platform or Super Layer name - 3 characters.
* Layer name
* Plug-in name

All of them seprated by " - ". 

##### Linking to parent Issue

Issues that needs to be linked to it's parent must have their first line starting with "Parent: " + http link to parent issue. 

##### Tagging the team leader

Team leaders are tagged in the second line in order to ask them to assign the issue to you and at the same time suscribe to any issue update. This helps team leaders to follow the issue events and provide assistance or guidance is they see something wrong. The suggested format is:

"@team-leader-user-name please assign this issue to me."

<br>
#### Plug-in Issue Structure

The mandatory initial structure is the following: (note: the word ISSUE it is not part of the name)

<br>
##### ISSUE: '_Plugin Name_' - Plug-In

This is the root of your issue structure and must be labeled as _SUPER ISSUE_. It is closed only when all of it's children and grand children are closed.

<br>
##### ISSUE: '_Plugin Name_' - Analisys

This is the Analisys root. It is closed whenever all analisys is done. This issue must be linked to the root of the issue structure.

<br>
##### ISSUE: '_Plugin Name_' - Implementation

This is the Implementation root. It is closed whenever all implementation is done. This issue must be linked to the root of the issue structure.

<br>
1 - ISSUE: **'_Plugin Name_' - Implementation - Developer Class** 
 
This issue is closed when this class if fully implemented. 

<br>
2 - ISSUE: **'_Plugin Name_' - Implementation - Plug-in Root** 

This issue is closed when this class if fully implemented. 

<br>
3 - ISSUE: **'_Plugin Name_' - Implementation - Database** 
 
This issue is closed when all database classes are fully implemented. 

* ISSUE: '_Plugin Name_' - Implementation - Database - Database Factory Class

This issue is closed when this class if fully implemented. 

* ISSUE: '_Plugin Name_' - Implementation - Database - Database Constants Class

This issue is closed when this class if fully implemented.

* ISSUE: '_Plugin Name_' - Implementation - Database - Developer Database Factory Class

This issue is closed when this class if fully implemented.

* ISSUE: '_Plugin Name_' - Implementation - Database - Database Factory Exceptions Class

This issue is closed when this class if fully implemented.

* ISSUE: '_Plugin Name_' - Implementation - Database - DAO Class 

This issue is closed when this class if fully implemented.

<br>
4 - ISSUE: **'_Plugin Name_' - Implementation - Public Interfaces**

This issue is closed when all public interface's code is written.  Note that the 1, 2, n must be replaced with the actual interfase names.

* ISSUE: '_Plugin Name_' - Implementation - Public Interfaces - Interface 1

This issue is closed when the first public interface is written.

* ISSUE: '_Plugin Name_' - Implementation - Public Interfaces - Interface 2

This issue is closed when the second public interface is written.

* ISSUE: '_Plugin Name_' Implementation - Public Interfaces - Interface n

This issue is closed when the n public interfaces are written.

<br>
5 - ISSUE: **'_Plugin Name_' - Implementation - Internal Structure**

This issue is closed when all interna structure's code is written.  Note that the 1, 2, n must be replaced with the actual class names.

* ISSUE: '_Plugin Name_' - Implementation - Internal Structure - Class 1
  
This issue is closed when this class if fully implemented.

* ISSUE: '_Plugin Name_' - Implementation - Internal Structure - Class 2
  
This issue is closed when this class if fully implemented.

* ISSUE: '_Plugin Name_' - Implementation - Internal Structure - Class n
  
This issue is closed when this class if fully implemented.

<br>
6 - ISSUE: **'_Plugin Name_' - Implementation - Event Handling**

This issue is closed when all event handler classes are written. Note that the 1, 2, n must be replaced with the actual class names.

* ISSUE: '_Plugin Name_' - Implementation - Event Handling - Event Handler 1

This issue is closed when this Event Hndler class if fully implemented.

* ISSUE: '_Plugin Name_' - Implementation - Event Handling - Event Handler 2

This issue is closed when this Event Hndler class if fully implemented.

* ISSUE: '_Plugin Name_' - Implementation - Event Handling - Event Handler n

This issue is closed when this Event Hndler class if fully implemented.

<br>
##### ISSUE: '_Plugin Name_' - Testing

This is the Testing root. It is closed whenever all testing is done. This issue must be linked to the root of the issue structure.

* ISSUE: '_Plugin Name_' - Testing - Unit Testing

* ISSUE: '_Plugin Name_' - Testing - Integration Testing
 
 
<br>
##### ISSUE: '_Plugin Name_' - QA
 

This is the QA root. It is closed whenever QA tests are passed. This issue must be linked to the root of the issue structure.

It is expected to have here child issues in the form '_Plugin Name_' QA - Bug Fix n, where n is both the number and the bug name.

<br>
##### ISSUE: '_Plugin Name_' - Production

This is the Production root. It is closed whenever the Plug-in reaches production. It can be re-opened if bug issues are found on production and closed again once they are fixed. This issue must be linked to the root of the issue structure.

It is expected to have here child issues in the form  '_Plugin Name_' Production - Bug Fix n, where n is both the number and the bug name.


<br>
### Analisys

#### Pulic Interfaces

#### Database Model

#### Internal Structure Class Model

<br>
### Implementation

#### Developer Class

#### Plug-in Root

#### Database

#### Structure

##### Structure Root

##### Structure Clasess

##### Agents

#### Event Handlers


<br>

## Parte III: How to do it

<br>
### Analisys

#### Pulic Interfaces

##### How do we define the public interfaces?

##### How many interfaces can a Plug-in have?

##### Where do public interfaces must be loceted?

While you are analizing the public interfaces you might wanto to do it by writing it's code. Then your next question may be where to do it.

#### Database Model

##### How do we think about the Database Model of the Plug-in?

##### How do we choose between a Database or a File?

##### How much data is it allowed to keep?

##### Which are the sustainability policies?

#### Internal Structure Class Model

##### How free Am I to choose a class model for the internal structure?


<br>
### Implementation

#### Developer Class

#### Plug-in Root

#### Database

#### Structure

##### Structure Root

##### Structure Clasess

##### Agents

#### Event Handlers


<br>

## Parte IV: References

<br>
















<br><br><br><br><br><br><br>





### FALTA HABLAR DE:

* Location of a Plugin

* The build.gradle File

* Gradle Plugins

* Folder Structure

* Main Packages

* Test Packages

* The Database Script

