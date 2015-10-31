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

