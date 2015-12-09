![alt text](https://github.com/bitDubai/media-kit/blob/master/Readme%20Image/Fermat%20Logotype/Fermat_Logo_3D.png "Fermat Logo")

<br><br>

## Introduction

GUI Components are one of the three basic components that can be added into the Fermat Framework. The two others are Add-ons, and Plug-ins. Each GUI component has a well defined responsibility within the system and usually collaborates from within one or more workflows in which it participates.  

To accomplish its mission, a GUI component must have a wireframe.


<br>

## Part I: Concepts

Several new concepts are introduced...

### Walllet

A Wallet is a GUI Component that allows a user to carry out financial transactions like send and receive crypto currencies using different plug-ins that Fermat offers through Modules. Each Module Wallet has a partner, that is; there is a one on one relationship between a wallet and a module.

### Sub-App

A SubApp is a GUI Component that allows a user to carry out non-financial operations , such as creating identities within the platform , administrative tasks , etc. using the various plug-ins that Fermat offers through Modules. Generally they serve to complement the functionality of the wallets . Each SubApp has an associated Module, there is a one on one relationship between SubApps and Modules.

### Session

One of the problems is the share of information in a fermat app life cycle, a fragment is eliminated when not visible, and its re-created when looked for again. These data must be saved in some place just in case a user wants to change Wallet and leave the session open. 

To resolve this, there exists something called Sessions : These objects works like share memory between the different screens that a wallet or SubApp may have. Information such as the Module of the wallet or SubApp , reference to Error Mananger (object that handles fermat exceptions generated in the platform) and any other data you need to share between fragments within a Map that works with a key and the object that needs saving.

The sessions are created for every wallet or SubApp and management of such meetings is held through a Wallet Manager and SubApp Manager , thus having the opportunity to return to the time when the user was when switching screens.

Each GUI component folder has a intended session . Todas las clases que representen sesiones han de extender de `AbstractFermatSession`. 

ejemplo 

```java
 public class ReferenceWalletSession extends AbstractFermatSession<InstalledWallet,CryptoWalletManager,ProviderManager> implements WalletSession {


```
La clase InstalledWallet es una instancia de wallet instalada.
La clase CryptoWalletManager es el module que le corresponde a dicha wallet
El ProviderManager (que no se encuentra en uso en este momento) es nuestro equivalente a la clase R a android.

<br>

### Fragment factory

Each GUI component has a folder designated to the fragment factory, that is in charge of connecting what is already developed in the Navigation Structure with the controlling fragments of such screens.

Consta de dos elementos: los enums *Fragment Enum Types* y las clases *Fragment Factory*. Estos elementos se han de ubicar en la carpeta fragmentFactory del proyecto que representa tu app.

Los Fragment Enum Types representan identificadores para los fragmentos que conforman las apps. Cada app ha de tener su propio Fragment Enum Type y este ha de heredar de `FermatFragmentsEnumType` (link a ejemplo)

Los Fragment Factory son clases que retornan instancias de los fragmentos identificados por su correspondiente Enum Type. Cada app ha de tener su propio Fragment Factory y este ha de heredar de `FermatWalletFragmentFactory` si los fragmentos representan una wallet o de `FermatSubAppFragmentFactory` si los fragmentos representan una subapp



FermatFragmentsEnumType example:
```java
public enum IntraUserIdentityFragmentsEnumType implements FermatFragmentsEnumType<IntraUserIdentityFragmentsEnumType> {

   CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT("CCPSACCIMF"),
   CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT("CCPSACCICIF")
   ;

   private String key;

   IntraUserIdentityFragmentsEnumType(String key) {
       this.key = key;
   }

   @Override
   public String getKey() {
       return this.key;
   }


   @Override
   public String toString() {
       return key;
   }

   public static IntraUserIdentityFragmentsEnumType getValue(String name) {
       for (IntraUserIdentityFragmentsEnumType fragments : IntraUserIdentityFragmentsEnumType.values()) {
           if (fragments.key.equals(name)) {
               return fragments;
           }
       }
       return null;
   }
}
```
FermatSubAppFragmentFactory
```java
public class IntraUserIdentityFragmentFactory extends FermatSubAppFragmentFactory<IntraUserIdentitySubAppSession, IntraUserIdentityPreferenceSettings, IntraUserIdentityFragmentsEnumType> {


   @Override
   public FermatFragment getFermatFragment(IntraUserIdentityFragmentsEnumType fragments) throws FragmentNotFoundException {

       if (fragments.equals(IntraUserIdentityFragmentsEnumType.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT))
           return IntraUserIdentityListFragment.newInstance();

       if (fragments.equals(IntraUserIdentityFragmentsEnumType.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT))
           return CreateIntraUserIdentityFragment.newInstance();


       throw createFragmentNotFoundException(fragments);
   }

   @Override
   public IntraUserIdentityFragmentsEnumType getFermatFragmentEnumType(String key) {
       return IntraUserIdentityFragmentsEnumType.getValue(key);
   }

   private FragmentNotFoundException createFragmentNotFoundException(FermatFragmentsEnumType fragments) {
       String possibleReason, context;

       if (fragments == null) {
           possibleReason = "The parameter 'fragments' is NULL";
           context = "Null Value";
       } else {
           possibleReason = "Not found in switch block";
           context = fragments.toString();
       }

       return new FragmentNotFoundException("Fragment not found", new Exception(), context, possibleReason);
   }
}
```

<br>

### Navigation Structure
 Fermat is an application different from other Android applications; it has its own navigation structure, that is based on screens and sub-screens that begin to “draw” from uploaded objects when executed, from files that deliver information about what it is needed to draw in each screen/sub-screen and in what order.

Permite definir el flujo de interacción entre las distintas pantallas de la aplicación que se encuentra en desarrollo. Se definen aspectos visuales como fondo,colores,tamaños.

También definen la existencia de  header,footer, navigation drawer, Tabs, Menus, entre otros objetos al mejor estilo wordpress.
Para agregar una estructura de navegación se debe ir a DMP/plugin/engine/fermat-dmp-plugin-engine-sub-app-runtime-bitdubai y agregar en el plugin root dicha estructura de navegación, 
se encuentra provisoriamente en dicha carpeta, en un futuro como primer paso se deberá leer de un xml en el repositorio de fermat en github y como segundo paso debera poder obtenerse de los otros nodos de la red fermat.

#### Navigation structure Concepts

Activity
	Una actividad en el contexto de Fermat es un contenedor base el cual le dice al core de android como va a estar diseñada la pantalla, cual va a ser su flujo , que elementos la componen. (Esto se realiza de esta forma para que en un futuro no desarrolladores puedan integrarse a Fermat), Un developer al contrario de android no debe desarrollar la clase Activity de android para poder correr sus fragmentos, si no que con declararlos en el runtime bajo un objeto Activity(FermatActivity) es suficiente para que se pinten en la pantalla.

Ejemplo de la Wallet user identity:

 ```java
RuntimeSubApp runtimeSubApp = new RuntimeSubApp();
runtimeSubApp.setType(SubApps.CCP_INTRA_USER_IDENTITY);
String intraUserIdentityPublicKey = "public_key_ccp_intra_user_identity";
runtimeSubApp.setPublicKey(intraUserIdentityPublicKey);

// Screen: Create New Identity
runtimeActivity = new Activity();
runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY);
runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY.getCode());
runtimeActivity.setColor("#03A9F4");
runtimeSubApp.addActivity(runtimeActivity);
runtimeSubApp.setStartActivity(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY);

runtimeTitleBar = new TitleBar();
runtimeTitleBar.setLabel("Identity Manager");
runtimeTitleBar.setColor("#1189a4");
runtimeTitleBar.setTitleColor("#ffffff");
runtimeTitleBar.setLabelSize(18);
runtimeTitleBar.setIsTitleTextStatic(true);
runtimeActivity.setTitleBar(runtimeTitleBar);

statusBar = new StatusBar();
statusBar.setColor("#1189a4");
runtimeActivity.setStatusBar(statusBar);

runtimeFragment = new Fragment();
runtimeFragment.setType(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
runtimeActivity.addFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);

‘’’

### Add Header in your fragment!
Es posible agregar un header expandible y colapsable en una actividad de tu app. Esto se realiza en tres pasos:

Definir en la estructura de navegación que la actividad posee un header a traves del metodo setHeader(runtimeHeader).
Crear una Clase `<nombreScreen>HeaderViewPainter` que implemente `HeaderViewPainter` en la carpeta `commons/header/` Por ejemplo en la bitcoin wallet sería commons/header/HomeHeaderViewPainter.java
El mismo debe incluirse en el metodo onActivityCreated pasando lo como parametro a `getPaintActivtyFeactures().addHeaderView()` del fragmento que va a contener el header

Lo mismo para el footer y para el navigation drawer :p

Ejemplo: 

```java
runtimeHeader = new Header();
runtimeHeader.setLabel("Market rate");
runtimeActivity.setHeader(runtimeHeader);
```


### Add Footer in your fragment!
Es posible agregar un footer deslizable, para esto se debe declarar una carpeta llamada footer en el plugin y hacer extender en dos pasos:

Este está conformado por 2 miembros, el llamado “ViewSlider”, este elemento es el view del footer que se encuentra siempre visible para poder despl

### Add Navigation drawer!

Es posible agregar un Navigation drawer que te permita dirigirte a las diferentes pantallas de tu app. Esta se define en varios pasos

Crear en la estructura de navegación un objeto SideMenu que representa el Navigztion drawer en la estructura de navegacion y una serie de objetos MenuItem que representan los items de ese menu
Cada uno de estos items se les asigna varios atributos, entre los que destaca `setLinkToActivity()` que vincula la actividad con el item
Definir en la estructura de navegación que la actividad va a mostrar el side menu
Crear una Clase `<nombreScreen>HeaderViewPainter` que implemente `HeaderViewPainter` en la carpeta `commons/header/` Por ejemplo en la bitcoin wallet sería commons/header/HomeHeaderViewPainter.java
El mismo debe incluirse en el metodo onActivityCreated pasando lo como parametro a `getPaintActivtyFeactures().addHeaderView()` del fragmento que va a contener el header

Ejemplo:

```java
// Side Menu
runtimeSideMenu = new SideMenu();

runtimeMenuItem = new MenuItem();
runtimeMenuItem.setLabel("Home");
runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
runtimeMenuItem.setAppLinkPublicKey(publicKey);
runtimeSideMenu.addMenuItem(runtimeMenuItem);

runtimeMenuItem = new MenuItem();
runtimeMenuItem.setLabel("Contracts History");
runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY);
runtimeMenuItem.setAppLinkPublicKey(publicKey);
runtimeSideMenu.addMenuItem(runtimeMenuItem);

runtimeMenuItem = new MenuItem();
runtimeMenuItem.setLabel("Earnings");
runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_EARNINGS);
runtimeMenuItem.setAppLinkPublicKey(publicKey);
runtimeSideMenu.addMenuItem(runtimeMenuItem);

runtimeMenuItem = new MenuItem();
runtimeMenuItem.setLabel("Settings");
runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS);
runtimeMenuItem.setAppLinkPublicKey(publicKey);
runtimeSideMenu.addMenuItem(runtimeMenuItem);
```

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

Issues that needs to be linked to its parent must have their first line starting with "Parent: " + http link to parent issue. 

##### Tagging the team leader

Team leaders are tagged in the second line in order to ask them to assign the issue to you and at the same time subscribe to any issue update. This helps team leaders to follow the issue events and provide assistance or guidance is they see something wrong. The suggested format is:

"@team-leader-user-name please assign this issue to me."

<br>
#### Plug-in Issue Structure

The mandatory initial structure is the following: (note: the word ISSUE it is not part of the name)

<br>
##### ISSUE: '_Plugin Name_' - Plug-In

This is the root of your issue structure and must be labeled as _SUPER ISSUE_. It is closed only when all of its children and grand children are closed.

<br>
##### ISSUE: '_Plugin Name_' - Analysis

This is the Analysis root. It is closed whenever all analysis is done. This issue must be linked to the root of the issue structure.

<br>
1 - ISSUE: **'_Plugin Name_' - Module - prototype**

This is the hardcoded module. Used for making the GUI prototype without use the fermat platform. This issue must be linked to the root of the issue structure.

<br>
2 - ISSUE: **'_Plugin Name_' - Module - connection**

This is the module connected with fermat platform. Used for making the GUI using the fermat platform. This issue must be linked to the root of the issue structure.

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

The GUI components are grouped in projects that could represent a Wallet, SubApp or a Desktop for a platform; For example CBP has 2 Wallets and 4 SubApps. 

#### Where to put your projects
 
Whenever you wish you create a new Wallet, SubApp or Desktop, you must create the project that will hold the GUI components in any of the three directories that are shown below following this structure: 
    
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

Where:

- **Platform_Name**: Refers to the platform where you’re going to create your components. 
- **Client_Type**: Refers to the device where the client is going to create, either Android, IPhone, a web or desktop application, etc. At the moment the client we are using is Android, therefore the name of this folder is “android”
- **desktop**: You place desktop, SubApps and Wallets projects here.
- **reference_wallet**: Here you will create the projects that have their GUI components that represent Wallets.
- **sub_app**: Here you will create the projects that have their GUI components that represent SubApps. 
   
Here’s an example:

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

This means that i have a total of 8 projects that hold GUI components, from which 2 are Desktops, 2 are Wallets and 4 are SubApps.


#### Project Names Conventions

The name of the projects follow this pattern:

    fermat-[platform_name]-[client_type]-[project_type]-[name_of_the_project]-[org_name]

Where:                                                                               

- **platform_Name**: Refers to the platform where you will create your components.
- **client_type**: Refers to the device where the client is going to create, either Android, IPhone, a web or desktop application, etc. At the moment the client we are using is Android, therefore the name of this folder is “android”.
- **project_type**: Refers to the type of project you’re goint to create GUI components for. They could be **desktop**, **reference-wallet** or **sub-app**.  
- **name_of_the_project**: This is the name of the project. For example: if your project is named **Crypto Broker Community** then you have to name it **crypto-broker-community**.
- **org_name**: This is the name of the developer organization o company that is creating the project, for example: **bitdubai**.  
 
Here’s an example:

    fermat-cbp-android-sub-app-crypto-broker-community-bitdubai

Where: **cbp** is the platform, **android** is the device, **sub-app** is the type of project, **crypto-broker-community** es is the name of the project and **bitdubai** is the organization responsible for the components of this project. This means that the project is a SubApp called Crypto Broker Community developed for Android devices and created by BitDubai for the CBP platform.


#### What's Inside an Android GUI Components Project

A GUI component project for Android in Fermat has the following basic structure (Label: **+** folder, **>** package, **-** file):

    + fermat-[platform_name]-[client_type]-[project_type]-[name_of_the_project]-[org_name]
      - .gitignore
      - build.gradle
      - proguard-rules.pro
      + src
        + main
          + java
            > com.bitdubai.[project_type].[name_of_the_project]
              > fragmentFactory
              > fragments
              > preference_settings
              > session
          + res
            + drawable
            + layout
            + menu
            + values
        + test
          + java
            > unit.com.bitdubai.[project_type].[name_of_the_project]

Where:

- Everything that goes in the `src` folder are files and resources you will need to develop your Wallet/SubApp/Desktop in Android.
- Inside `src/main/java` you will find the package where you will place java files (classes, intefaces, enums..) with your Android code. It has the following basic packages: **fragmentFactory**, **fragments**, **preference_settings** and **session**. Each one of them explained in detail later on in this README.
- Inside `src/main/res` there are `xml` files found that represent *layouts, menus, colors, strings and sizes* as well as image files and others that represent visual resources with which you’re going to interact the java classes that have an Android logic.
- Everything that goes in the `test` folder is code that is used to make Unit Testing on the funtionalities you’re developing in `src`.
- The Unit Test are created inside the package `unit.com.bitdubai.[project_type].[name_of_the_project]` in `test/java`
- The file `build.gradle` is where you define the dependencies of the project with others of the platforms or with third party libraries and those that Android offers but not as default (the Support Libraries for example). Also the minimal version of the OS is defined where the app is going to run like the SDK Android version that is going to be used among other things (for more information see [this link](http://developer.android.com/tools/building/configuring-gradle.html))
- The file `proguard-rules.pro` configures the Proguard tool. (for more information see  [this link](http://developer.android.com/guide/developing/tools/proguard.html)). **NOTE:** *we do not configure this file at the moment, therefore it is empty*

### Add to your project the file settings.gradle

At the beginning when you create your Android project to develop your Wallet/SubApp/Desktop, it won’t be recognized as such in the dependencies structure of the root project (Fermat) and it will show like one more directory. So your project is included in dependencies structure it is necessary to add the following lines in the file `settings.gradle` that is found in the folder of the platform where you’re going to work:

```Gradle
include ':fermat-[platform_name]-[client_type]-[project_type]-[name_of_the_project]-[org_name]'
project(':fermat-[platform_name]-[client_type]-[project_type]-[name_of_the_project]-[org_name]').projectDir = new File('platform_name/client_type/project_type/fermat-[platform_name]-[client_type]-[project_type]-[name_of_the_project]-[org_name]')
```
Here's an example of part of the file `settings.gradle` of the CBP platform (`fermat/CBP/settings.gradle`):

```Gradle
...

//Desktop
include ':fermat-cbp-android-desktop-sub-app-manager-bitdubai'
project(':fermat-cbp-android-desktop-sub-app-manager-bitdubai').projectDir = new File('CBP/android/desktop/fermat-cbp-android-desktop-sub-app-manager-bitdubai')
include ':fermat-cbp-android-desktop-wallet-manager-bitdubai'
project(':fermat-cbp-android-desktop-wallet-manager-bitdubai').projectDir = new File('CBP/android/desktop/fermat-cbp-android-desktop-wallet-manager-bitdubai')

//Reference Wallet
include ':fermat-cbp-android-reference-wallet-crypto-broker-bitdubai'
project(':fermat-cbp-android-reference-wallet-crypto-broker-bitdubai').projectDir = new File('CBP/android/reference_wallet/fermat-cbp-android-reference-wallet-crypto-broker-bitdubai')
include ':fermat-cbp-android-reference-wallet-crypto-customer-bitdubai'
project(':fermat-cbp-android-reference-wallet-crypto-customer-bitdubai').projectDir = new File('CBP/android/reference_wallet/fermat-cbp-android-reference-wallet-crypto-customer-bitdubai')

//Sub App
include ':fermat-cbp-android-sub-app-crypto-broker-community-bitdubai'
project(':fermat-cbp-android-sub-app-crypto-broker-community-bitdubai').projectDir = new File('CBP/android/sub_app/fermat-cbp-android-sub-app-crypto-broker-community-bitdubai')
include ':fermat-cbp-android-sub-app-crypto-broker-identity-bitdubai'
project(':fermat-cbp-android-sub-app-crypto-broker-identity-bitdubai').projectDir = new File('CBP/android/sub_app/fermat-cbp-android-sub-app-crypto-broker-identity-bitdubai')
include ':fermat-cbp-android-sub-app-crypto-customer-community-bitdubai'
project(':fermat-cbp-android-sub-app-crypto-customer-community-bitdubai').projectDir = new File('CBP/android/sub_app/fermat-cbp-android-sub-app-crypto-customer-community-bitdubai')
include ':fermat-cbp-android-sub-app-crypto-customer-identity-bitdubai'
project(':fermat-cbp-android-sub-app-crypto-customer-identity-bitdubai').projectDir = new File('CBP/android/sub_app/fermat-cbp-android-sub-app-crypto-customer-identity-bitdubai')
include ':fermat-cbp-android-sub-app-customers-bitdubai'
project(':fermat-cbp-android-sub-app-customers-bitdubai').projectDir = new File('CBP/android/sub_app/fermat-cbp-android-sub-app-customers-bitdubai')

//PLUG-INS
...

```

<br>

### Modules
 
 A GUI component in Fermat is divided into 2 Plug-ins, the graphic interfaces and the module of such interface, is the one that has the following funtionalities:
* Works as a connection between the Plug-ins of the platform, cosuming the services that they provide.
* It covers the logic of the presentation, gathering, organizing and grouping Plug-in data.

----------------------


### Navigation structure
 
 -- Navigation structure with examples..
 
  Fermat is an application different from other Android applications; it has its own surfing structure, that is based on screens and sub-screens that begin to “paint” from uploaded objects when executed, from files that deliver information about what it is needed to draw in each screen/sub-screen and in what order.

----------------------

### Android api

 Fermat has a collection of libraries and packages developed specially to work on Android. Continue...

----------------------

### Fragments

- You have to show the way in which the fragments are created and that all extend FermatFragment.

----------------------

### Fragment factory

- You have to explain the fragment factory, what does it work for and how it is used.

Each GUI component has a folder designated to the fragment factory, that is in charge of connecting what is already developed in the surfing structure with the controlling fragments of such screens.
Continue..

----------------------

### Sessions

- You have to give a brief explanation of the sessions.

One of the problems is the persistance of memory data since in a life cycle, a fragment is eliminated when not visible, and its re-created when looked for again. These data must be saved in some place just in case a user wants to change Wallet and leave the session open. 

To resolve this, there exists something called Sessions : These are objects that allow to persist in memory and share between the different fragments that a wallet or SubApp may have, information such as the reference to the module of the wallet or SubApp , reference to Error Mananger (object that handles the exceptions Fermat generated in the platform) and any other data you need to trascend between these fragments within a Map that works with a key and the object that needs saving.

The sessions are created for every wallet or SubApp and management of such meetings is held through a Wallet Manager and SubApp Manager , thus having the opportunity to return to the time when the user was when switching screens.

Each GUI component folder has a intended session . Depending on the type of component that is being developed it could be a class that inherits from WalletSession or SubAppSession.
Continue...

----------------------

#### Wallet session

They are sessions that manage Wallet information, these inherit from WalletSession.

----------------------

#### Sub app session

They are sessions that manage SubApp information, these inherit from WalletSession.

----------------------

#### Settings

Brief explanation of settings with examples
- 
----------------------


## Part IV: References

<br>



<br><br><br><br><br><br><br>


