![alt text](https://github.com/bitDubai/media-kit/blob/master/Readme%20Image/Fermat%20Logotype/Fermat_Logo_3D.png "Fermat Logo")

<br><br>

# NETWORK SERVICES

## Introduction

Communication is a vital need among systems to expand their functionality and interaction between different devices. Fermat in this case is no exception. Communication is important for the transmission of data and sending relevant information.

To meet this important need has been defined as part of the Network Services solution.


Network Services fall into the category of plug-in components defined types that can be found within the Fermat system. This type of plug-in is dedicated to a well-defined task within the architecture, it acts as an intermediary between the communication layer and the other components of the platform, and thus act as a service for the other components.

As network services are intermediaries, and are the only interact directly with the communications layer, they are responsible for all tasks and encapsulate logic necessary for this interaction, and among these basic tasks are the following:

 * Request the communications layer recording with other components Server Cloud (Cloud Server).
 * Request the communications layer discovery and search for other registered components, and are running on another device.
 * Request the communications layer to establish a new communication channel with another specific component and can send and receive messages.
 * Keep an administration established channels.
 * Receive and store incoming messages.
 * Send and store outgoing messages.

Network Services also have the responsibility of defining a communications protocol for message-based placement with other network services running on another device running the system Fermat. An important point to consider in terms of network services point is that they can only be interaction or communication with other network service of the same type to run in another device, so that the same send and receive messages according to the defined protocol and can be understood.

<br>
## Part I: Concepts

### Network Services

It is a network service that aims to define the behavior and protocol for communication between the components of a platform that runs within the Framework Fermat.

* Network Services ** ** Actors who are responsible for managing the activities reacted with identities (Actors) located within the platform.

* ** ** Network Services generals who are responsible for managing business logic or data transfer protocols.

### Connections

They are the channels established by the communication layer to the network service to send and receive messages.

### Menssajes

It is defined as information that the sender sends the receiver through a certain communication channel, and it has a unique meaning.

<br>

## Part II: Workflow

This section will help you understand the workflow necessary to continue to implement a Fermat Network Service System.
<br>
### Organize
---------------------

#### Issues

It is mandatory that an initial set of GitHub Issues before proceeding further with the workflow is created. This will show the other teams that someone is working on this functionality and prevent labor disputes from the beginning. The team leader will also engage in their workflow and enabling it to guide and advise you when necessary.

A basic hierarchy GitHub Issues are created as a first step. The subjects are linked to one another by simply placing a link on the first comment.

##### Naming convention name

When we refer to '_Plugin name_' what we hope is the following:

* Platform or Super Layer name - 3 characters.
Name of the layer
* Plug-in Name

All separated by "-".

##### Parents Linking Issues

The Issues that should be linked to his father should be your first line "Parent:" + http link to Issues father.

##### Labelling team leader

Team leaders labeled the second line in order to ask them to assign the Issue to you while signing up for any upgrade issue. This helps team leaders to follow the events of Issue and provide assistance or guidance to do something wrong. The suggested format is:

"User Name @ leader of the team, please assign this matter to me."

Issue #### Plug-in Structure

Compulsory initial structure is as follows: (Note: the word ISSUE is not part of the name)

##### ISSUE: '_Plugin Name_' - Plug-In

This is the root of the structure of the problem and must be labeled _SUPER ISSUE_. It is closed only when all their children and grandchildren are closed.

##### ISSUE: '_Plugin Name_' - Analysis

This is the root for analysis. It is closed every time you carry out all analyzes. This Issue should be linked to the root of the structure of matter.

1 - ISSUE: ** '_ Plugin name_' - Case Uses **

This is where you specify each potential use cases to cover the problem.

##### ISSUE: '_Plugin Name_' - Implementation

This is the root for example's implementation. It is closed every time you carry out all implemntaciones. This Issue should be linked to the root of the structure of matter.

<br>
1 - ISSUE: ** '_ Plugin name_' - Interfaces **

This is where you specify each of the possible interfaces to implement.

2 - ISSUE: ** '_ Plugin name_' - Data Base **

This is where specified componenetes each database.

##### ISSUE: '_Plugin name_' - Tests

This is the root of evidence. It is closed whenever all tests performed. ISSUE This must be linked to the root of the structure of matter.

* ISSUE: ** '_ Plugin name_' - Testing - Unit Testing **

* ISSUE: ** '_ Plugin name_' - Testing - Integration Test **

##### ISSUE: '_Plugin name_' - QA

This is the result of quality control. It is closed when the quality control tests are passed. This problem must be linked to the root of the structure of matter.

It expected to have children here Issues in the form '_Plugin name_' QA - Bug Fix n, where n is the number and name of error.

##### ISSUE: '_Plugin name_' - Production

This is the root of production. It closes whenever the plug-in reaches production. It can be reopened if bugs are problems in production and closed again once they are solved. This Issue should be linked to the root of the structure of matter.

It expected to have children here Issues in the form '_Plugin name_' Production - Bug Fix n, where n is the number and name of error.

### Structure Projects
----------------------

The components of the Network Services may be grouped and distributed as follows.

#### Where to put your projects

Whenever you want to create a new folder, or desktop SubApp must create the project that will perform the GUI components in any of the three directories shown below following this structure:

 
    + PLATFORM_NAME (3 Character)
      + plugin
        + actor_network_services
          - fermat-platformname-plugin-actor-network-service-name_1
          - fermat-platformname-plugin-actor-network-service-name_2
        + network_services
          - fermat-platformname-plugin-network-service-name_1
          - fermat-platformname-plugin-network-service-name_2
          - fermat-platformname-plugin-network-service-name_3

Where:

- **PLATFORM_NAME**: it refers to the platform on which you are creating components.
- **plugin**: paguete directory or where the components are type plug refers.
- **actor_network_services**: directory or go paguete where specialized network services for the actors concerned.
- **network_services**: paguete directory or go where network services of general type is concerned.

Here's an example:

     + DAP
      + plugin
        + actor
        + actor_network_services
          - fermat-dap-plugin-actor-network-service-asset-issuer-bitdubai
          - fermat-dap-plugin-actor-network-service-asset-user-bitdubai
          - fermat-dap-plugin-actor-network-service-redeem-point-bitdubai
        + digital_asset_transaction
        + identity
        + middleware
        + network_services
          - fermat-dap-plugin-network-service-asset-transmission-bitdubai
        + sub_app_module
        + wallet
        + wallet_module

As can be seen in the structure of the platform "Digital Asset Platform (DAP)" has three service type Actor Network and General Service Network for a specific task.

#### Project Conventions for Names

The project names follow this pattern:

    fermat-[platform_name]-plugin-[network_service_type]-[name_of_the_project]-[org_name]

Where:

- **PLATFORM_NAME**: Refers to the platform that will create its components.
- **Network_service_type**: Refers to the type of network service that is to be created, if it is for an actor or not.
- **Name_of_the_project**: This is the name of the project.
- **Org_name**: This is the name of the company or organization promoting the project you are creating, for example: **bitdubai**.
 
Here's an example:

    fermat-dap-plugin-actor-network-service-asset-issuer-bitdubai
    
Where: **DAP** is the platform **plugin** is the type of component, an **actor-network-service** is the type of network service, **asset-issuer** is the name of the project and **bitdubai** is responsible for the components of this project organization. This means that the project is a plugin called Asset Issuer developed by BitDubai platform for DAP.

#### What's in a project of Network Service component?

A project components Network Service on Fermat has the following basic structure (Tags: folder **+** **>** Package **-** file):

    + fermat-[platform_name]-plugin-[network_service_type]-[name_of_the_project]-[org_name]
      - .gitignore
      - build.gradle
      - proguard-rules.pro
      + src
        + main
          + java
            > com.bitdubai.[project_type].[name_of_the_project]
              > structure
                > version_1
                  > structure
                    > communications
                    > database
                    > event_handlers
                    > exceptions
                - Developer.java
        + test
          + java
            > unit.com.bitdubai.[project_type].[name_of_the_project]

Where:

- The `build.gradle` file is where the project dependencies with other platforms or third-party libraries, but not by default (support libraries, for example) are defined.
- The `Proguard-rules.pro` file configures the Proguard tool. (For details see [this link] (http://developer.android.com/guide/developing/tools/proguard.html)). **NOTE:** not configure this file at the time, therefore, it is empty *
- Inside the folder `src/main/java` package is where to place the Java files (classes, intefaces, enumerations ..)
- Inside the folder 'test' is the code used for unit testing on the features you are developing the `src` folder.
- Inside the package `structure` it is well organized in classes other packages the plug-in component that is being developed.
- Unit tests are created within the `unit.com.bitdubai package [project_type] [name_of_the_project]` in the `test/java`..

### Add your project file settings.gradle

At first, when the project is created, it will not be recognized as such in the dependency structure of the root project (Fermat) and displayed as a directory. So your project should be included in the structure of dependencies, you must add the following lines in the file `settings.gradle` found in the folder of the platform on which you will work:
<br>
```Gradle
include ':fermat-[platform_name]-plugin-[network_service_type]-[name_of_the_project]-[org_name]'
project(':fermat-[platform_name]-plugin-[network_service_type]-[name_of_the_project]-[org_name]').projectDir = new File('platform_name/plugin/project_type/fermat-[platform_name]-plugin-[network_service_type]-[name_of_the_project]-[org_name]')
```

<br>
Here is an example of a part of `settings.gradle` for DAP (`Fermat/DAP/settings.gradle`) platform:<br>
```Gradle
...

// actor network service
include ':fermat-dap-plugin-actor-network-service-asset-user-bitdubai'
project(':fermat-dap-plugin-actor-network-service-asset-user-bitdubai').projectDir = new File('DAP/plugin/actor_network_service/fermat-dap-plugin-actor-network-service-asset-user-bitdubai')

include ':fermat-dap-plugin-actor-network-service-redeem-point-bitdubai'
project(':fermat-dap-plugin-actor-network-service-redeem-point-bitdubai').projectDir = new File('DAP/plugin/actor_network_service/fermat-dap-plugin-actor-network-service-redeem-point-bitdubai')

include ':fermat-dap-plugin-actor-network-service-asset-issuer-bitdubai'
project(':fermat-dap-plugin-actor-network-service-asset-issuer-bitdubai').projectDir = new File('DAP/plugin/actor_network_service/fermat-dap-plugin-actor-network-service-asset-issuer-bitdubai')

// network-service
include ':fermat-dap-plugin-network-service-asset-transmission-bitdubai'
project(':fermat-dap-plugin-network-service-asset-transmission-bitdubai').projectDir = new File('DAP/plugin/network_service/fermat-dap-plugin-network-service-asset-transmission-bitdubai')

...

```

<br>


## Part III: Implementación

<br>



<br><br><br><br><br><br><br>

## Part IV: Local Test Server
           
It is possible to execute a local server for testing a plug-in Network Service, this speeds up the process of debugging the plugin source code and the error detection process thereof.
           
For the execution server in a local development environment, follow these steps:
           
* You must enter to where the project is saved on the hard disk parent folder.
```Shell
cd $PATH_TO_FERMAT_REPO/fermat
```
* After the command to run the compilation run:
 ```Shell
cd $PATH_TO_gradle/gradle fatjar
```
* Now you enter the folder in which the executable file as a result of the above compilation is:

```Shell
P2P/plugin/communication/fermat-p2p-plugin-ws-communications-cloud-server-bitdubai/build/libs/
```
* You should verify the contents of the previous folder, for this use the command:
```Shell
ls
```
* Once the list of files in P2P _ **/plugin/communication/fermat-p2p-plugin-ws-communications-cloud-server-bitdubai/build/libs/_** must copy the JAR file name contains in its name dependencies ** ** With
* To implement the server, you must run the following command:
```Shell
java -jar $NOMBRE_DEL_ARCHIVO_OBTENIDO_EN_EL_PASO_ANTERIOR -jar
```
* When the server is running must observe the messages printed on the console, including the IP address you are using the local print server, this address should be copied and used to configure the local client communications. The local communications client is in **_com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.structure.util.ServerConf_**.
* The IP address obtained in the previous step should be used in the following configuration variable:
```Java
    /**
     * Represent the SERVER_IP in the local environment
     */
    public static final String SERVER_IP_DEVELOPER_LOCAL = "<LOCAL IP SERVER>";
```
* After setting the IP address must run the application, when it starts, if the Plugin Network Service is running properly and recording should get in the server console a text like the one shown below:

```Shell
ComponentRegistrationRequestPacketProcessor - processingPackage
ComponentRegistrationRequestPacketProcessor - packetContentJsonStringRepresentation = {"ns-t":"TESTING PLUGIN","pr":"{\"alias\":\"testingpluginnetworkservice\",\"communicationCloudClientIdentity\":\"048B5A003C40B82563BCC002A08D2F6F5257D2B1A0487BCDD11CED7E2845918D57F5998FEF38900A9B665A27235BDEF2A5E6D6BE9AFBA4CED545045F00C14FCDBF\",\"identityPublicKey\":\"04DC8F6915AE276E602EEAA29155EA094A072F876C86B9E9C3A75E6F3FD46B2FA0846BC1C445CDA77C1C07DA0AA566541810FDDD0B28340BE3D99A83F945D526F6\",\"name\":\"Testing Plugin Network Service\",\"networkServiceType\":\"TESTING_PLUGIN\",\"platformComponentType\":\"NETWORK_SERVICE\"}"}
ComponentRegistrationRequestPacketProcessor - registerNetworkServiceComponent
ComponentRegistrationRequestPacketProcessor - Total Network Service Component Registered (TESTING_PLUGIN) = 1
```
