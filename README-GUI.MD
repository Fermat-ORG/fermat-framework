![alt text](https://github.com/bitDubai/fermat-graphic-design/blob/master/2D%20Design/Fermat/Fermat%20Logotype/logo_jpg/logo_github_readme.jpg "Fermat Logo")

<br><br>

## Introduction

GUI Components are one of the three basic components that can be added into the Fermat Framework. The two others are Addons, and Plugins. Each GUI component has a well defined responsibility within the system at usually collaborate from within one or more workflows in which it participates.  

To accomplish it's mission, a GUI component must have a wireframe.


<br>

## Part I: Concepts

Several new concepts are introduced...

<br>

## Part II: Workflow

This section will help you understand the workflow needed to be followed in order to implement a GUI component in Fermat.

<br>
### Getting Organized
---------------------

#### Issues

It is mandatory that you create an initial set of GitHub issues before you proceed further on the workflow. This will show the rest of the teams that someone is working in this functionality and avoid conflicting work early on. It will also hook the team leader into your workflow and allow him to guide and advise you when needed.

A basic hierarchy of issues is created as a first step. The issues are linked one to the other just by placing a link on the first comment.

##### Naming Convention

Where we refer to '_Plugin Name_' what we expect is the following information:

* Platform or Super Layer name - 3 characters.
* Layer name
* Plug-in name

All of them separated by " - ". 

##### Linking to parent Issue

Issues that needs to be linked to it's parent must have their first line starting with "Parent: " + http link to parent issue. 

##### Tagging the team leader

Team leaders are tagged in the second line in order to ask them to assign the issue to you and at the same time subscribe to any issue update. This helps team leaders to follow the issue events and provide assistance or guidance is they see something wrong. The suggested format is:

"@team-leader-user-name please assign this issue to me."

<br>
#### Plug-in Issue Structure

The mandatory initial structure is the following: (note: the word ISSUE it is not part of the name)

<br>
##### ISSUE: '_Plugin Name_' - Plug-In

This is the root of your issue structure and must be labeled as _SUPER ISSUE_. It is closed only when all of it's children and grand children are closed.

<br>
##### ISSUE: '_Plugin Name_' - Analysis

This is the Analysis root. It is closed whenever all analysis is done. This issue must be linked to the root of the issue structure.

<br>
1 - ISSUE: **'_Plugin Name_' - Module - prototype**

This is the hardcoded module. Used for make the GUI prototype without use the fermat platform. This issue must be linked to the root of the issue structure.

<br>
2 - ISSUE: **'_Plugin Name_' - Module - connection**

This is the module connected with fermat platform. Used for make the GUI using the fermat platform. This issue must be linked to the root of the issue structure.

<br>
3 - ISSUE: **'_Plugin Name_' - GUI - screen - <screen_name>**

This issue is for a specific wireframe of a screen.

<br>

<br>
##### ISSUE: '_Plugin Name_' - Testing

This is the Testing root. It is closed whenever all testing is done. This issue must be linked to the root of the issue structure.

* ISSUE: **'_Plugin Name_' - Testing - Unit Testing**

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

### Projects Structure
----------------------

Los componentes de GUI se agrupan en proyectos que pueden representar una Wallet, una SubApp o un Desktop para una plataforma, por ejemplo: la plataforma CBP tiene 2 Wallets y 4 SubApps. 

#### Where to put your projects
 
Cuando desees crear una nueva Wallet o Subapp o Desktop debes crear el proyecto que contendrá los componentes GUI en alguno de tres directorios que se muestran abajo siguiendo esta estructura:
    
    + Platform_Name
      + Client_Type (actualmente android)
        + desktop
          - desktop_project_name_1
          - desktop_project_name_2
        + reference_wallet
          - wallet_project_name_1
          - wallet_project_name_2
          - wallet_project_name_n
        + sub_app
          - sub_app_project_name_1
          - sub_app_project_name_2
          - sub_app_project_name_3
          - sub_app_project_name_n

Donde:

- **Platform_Name**: se refiere a la plataforma done vas a crear tus componentes. 
- **Client_Type**: se refiere a que dispositivo donde se va a crear el cliente, llamese Android, IPhone, una aplicacion Web, una aplicacion Desktop, etc, donde actualmente el ciente que estamos usando es Android por lo que el nombre de esta carpeta es "android".
- **desktop**: aqui colocas los proyectos que para los desktops de las subapps y las wallets.
- **reference_wallet**: aqui vas a crear los proyectos que representen wallets los cuales van a tener sus componentes GUI.
- **sub_app**: aqui vas a crear los proyectos que representen SubApps los cuales van a tener sus componentes GUI. 
   
He aqui un ejemplo:

    + CBP                       
      + android     
        + desktop
          - fermat-cbp-android-desktop-sub-app-manager-bitdubai
          - fermat-cbp-android-desktop-wallet-manager-bitdubai
        + reference_wallet
          - fermat-cbp-android-reference-wallet-crypto-broker-bitdubai
          - fermat-cbp-android-reference-wallet-crypto-customer-bitdubai 
        + sub_app
          - fermat-cbp-android-sub-app-crypto-broker-community-bitdubai
          - fermat-cbp-android-sub-app-crypto-broker-identity-bitdubai
          - fermat-cbp-android-sub-app-crypto-customer-community-bitdubai
          - fermat-cbp-android-sub-app-crypto-customer-identity-bitdubai
          
Esto quiere decir que tengo un total de 8 proyectos que albergan componentes GUI, de los cuales 2 son Desktops, 2 son Wallets y 4 son SubApps.


#### Project Names Conventions

Los nombres de los proyectos siguen el siguiente patron:

    fermat-[platform_name]-[client_type]-[project_type]-[name_of_the_project]-[org_name]
    
Donde:                                                                               
                                                                                 
- **platform_Name**: se refiere a la plataforma done vas a crear tus componentes.
- **client_type**: se refiere a que dispositivo donde se va a crear el cliente, llamese Android, IPhone, una aplicacion Web, una aplicacion Desktop, etc, donde actualmente el ciente que estamos usando es Android por lo que el nombre de esta carpeta es "android".
- **project_type**: se refiere al tipo de proyecto para los que vas a crear los componentes GUI. Pueden ser **desktop**, **reference-wallet** o **sub-app**.  
- **name_of_the_project**: este es el nombre del proyecto. Por ejemplo: si tu proyecto se llama **Crypto Broker Community** entonces has de colocar **crypto-broker-community**.
- **org_name**: este es el nombre de la organicacion desarrollador o empresa que esta creando el proyecto, por ejemplo: **bitdubai**.  
 
He aqui un ejemplo: 

    fermat-cbp-android-sub-app-crypto-broker-community-bitdubai

Donde: **cbp** es la plataforma, **android** es el dispositivo, **sub-app** es el tipo de proyecto, **crypto-broker-community** es el nombre del proyecto y **bitdubai** es la organizacion resposable de los componentes de este proyecto. Esto quiere decir que el proyecto es una SubApp llamada Crypto Broker Community desarrollada para dispositivos Android y creada por BitDubai para la plataforma CBP.

<br>

### Navigation structure
----------------------

### Android api
----------------------

### Fragments
----------------------

### Fragment factory
----------------------

### Sessions
----------------------

#### Wallet session
----------------------

#### Sub app session
----------------------

#### Settings
----------------------

## Part IV: References

<br>



<br><br><br><br><br><br><br>

