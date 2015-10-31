# Adding a New Plugin - Issues Definition

Before adding a new Plugin to Fermat, you should perform an analysis of the problem you are seeking to solve with this plugin and how you're going to address this problem.

After you've done a proper analysis, you must got to https://github.com/bitDubai/fermat/issues and define a few issues to reflect the work you're going to perform guided by your analysis.

## Basic Issues Structure

* **SUPER ISSUE: 'Plugin Name' Implementation**: this is the parent issue for all the issues you're going to create for your Plugin, you should create at least the following structure linking to this issue* 

* **'Plugin Name' Implementation - Developer Class**

* **'Plugin Name' Implementation - Plugin Root**

* **'Plugin Name' Implementation - Database**
 * **'Plugin Name' Database - Database Factory**
 * **'Plugin Name' Database - Database Constants**
 * **'Plugin Name' Database - Developer Database Factory**
 * **'Plugin Name' Database - Database Factory Exceptions**
 * **'Plugin Name' Database - DAO Class**

* **'Plugin Name' Implementation - Public Interfaces**
 * **'Plugin Name' Public Interfaces - Interface 1**
 * **'Plugin Name' Public Interfaces - Interface 2**
 * **'Plugin Name' Public Interfaces - Interface N**

* **'Plugin Name' Implementation - Internal Structure**
 * **'Plugin Name' Internal Structure - Class 1**
 * **'Plugin Name' Internal Structure - Class 2**
 * **'Plugin Name' Internal Structure - Class N**

* **'Plugin Name' Implementation - Event Handling**
 * **'Plugin Name' Event Handling - Event Handler 1**
 * **'Plugin Name' Event Handling - Event Handler 2**
 * **'Plugin Name' Event Handling - Event Handler N**
 

The desired granularity is that you create one issue for each new functionality or implementation that you'll define in one of these categories.  

## Example

Let's assume you have want to create a Plugin Called 'Litecoin Index', you'll create the following Issues 
```
Title: SUPER ISSUE: Litecoin Index Implementation
Description: 
 We are going to create a price index exclusive to Litecoins
```
GitHub will provide an identifier to this issue, let's assume this identifier is **1**
 
Then we'll create the following Issues
```
Title: Litecoin Index Implementation - Database
Description: 
 We need to implement the Database Structure and Data Access Objects for this Index. 
 Issue Parent #1
Generated Id: 2
```

```
Title: Litecoin Index Implementation - Event Handling
Description: 
 We need to create the Agents that will listen to the Events this Index needs to handle. 
 Issue Parent #1
Generated Id: 3
```

```
Title: Litecoin Index Implementation - Public Interfaces
Description: 
 We'll implement some public interfaces that other plugins will use to communicate with the Index. 
 We should implement at least one Manager. 
 Issue Parent #1
Generated Id: 3
```

```
Title: Litecoin Index Implementation - Plugin Root
Description: 
 We must create the plugin root that will control the behaviour of this plugin in the platform. 
 We must implement at least the following interfaces DealsWithErrors, DealsWithLogger, LogManagerForDevelopers, Service, Plugin. 
 Issue Parent #1
Generated Id: 4
```

```
Title: Litecoin Index Implementation - Developer Class
Description:
 The developer class will provide the Plugin to the Platform and identify the Developer. 
 We must implement at least the interface PluginDeveloper.
 Issue Parent #1
Generated Id: 5
```
If we want to expand a functionality or a specific implementation we must create a new Issue, for example if we want to define that we want to implement the interface **PluginLicensor** in the Developer Class
```
Title: Litecoin Index Developer Class - Implement PluginLicensor
Description: 
 We want to monetise the usage of our plugin so we must implement PluginLicensor. 
 Issue Parent #5
Generated Id: 6
```
Notice that the parent issue is not linked to the **SUPER ISSUE**(*#1*) but to the categorized one(*#5*)
