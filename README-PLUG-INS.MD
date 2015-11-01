![alt text](https://github.com/bitDubai/fermat-graphic-design/blob/master/2D%20Design/Fermat/Fermat%20Logotype/logo_jpg/logo_github_readme.jpg "Fermat Logo")

<br><br>

## Introduction

Plug-ins are one of the three basic components that can be added into the Fermat Framework. The two others are Addons, and GUI components. Enach Plug-in has a well defined responsability within the system at usually collaborate from within one or more workflows in which it participates.  

To accomplish it's mission, a Plug-in may havey have it's own database and files to persist it's state. It also has an internal structure of classes designed specifically to fullfil it's goals. Some of these classes have public interfaces which exposes the Plug-in public services to other Fermat components.


<br>

## Part I: Concepts

Several new concepts are introduced...

<br>
### Developer Class

<br>
### Plug-in Root

<br>
### Plug-in Public Interface

<br>
### Internal Structure

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




# Adding a New Plugin to Fermat

Before adding a new Plugin to Fermat, you should perform an analysis of the problem you are seeking to solve with this plugin and how you're going to address this problem.

## Issues Definition

Before we create a new Plugin we should perform a proper analysis an create a set of Issues inside Github that define the work we're going to do.

Consult the [Adding a New Plugin - Issues Definition](AddingNewPlugin-Issues.md) document for a detailed description of this process.

## Location of a Plugin

You must identify in which Sub-Platform or Super-Layer your Plugin will reside.

There is a specific route where the different layers of a Sub-Platform or Super-Layer are located.

For example if you want to add a new Plugin in the Marketing Platform, you must go to **MKT -> plugins**

Once you've defined this, you must select the specific layer where you're going to create the Plugin.

Following the priot example, if you want to create a new Plugin in the *Actor* layer, you must add it to the **MKT -> plugins -> actor** folder.

The name of the folder where your project will be added must reflect this location, the selected name of the Plugin, and the developer..

Continuing with the example, if bitDubai adds the Marketer Actor plugin, the name of the folder would be **fermat-mkt-plugin-actor-marketer-bitdubai**

## The build.gradle File

### Gradle Plugins

## Folder Structure

### Main Packages

### Test Packages

## The PluginRoot class

## The Database Script

