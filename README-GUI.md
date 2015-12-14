![alt text](https://github.com/bitDubai/media-kit/blob/master/Readme%20Image/Fermat%20Logotype/Fermat_Logo_3D.png "Fermat Logo")

<br><br>

## Introduction

GUI Components are one of three basic components that can be added to the Fermat Framework. The two others are Add-ons and Plug-ins. Each GUI component has a well defined responsibility within the system, and usually collaborates from within one or more workflows in which it participates.  

To accomplish its mission, a GUI component must have a wireframe.

<br>

## Part I: Concepts

### Wallet

A Wallet is a GUI Component that allows a user to carry out financial transactions, like sending and receiving crypto currencies, using different plug-ins that Fermat offers through Modules. Each Module Wallet has an associated Module, thus, there is a one-on-one relationship between a Wallet and its Module.

### Sub-App

A SubApp is a GUI Component that allows a user to carry out non-financial operations, such as creating identities within the platform, administrative tasks, among others. All of this, using various plug-ins that Fermat offers through Modules. Sub-Apps serve generally to complement the functionality of a wallet. Just like Wallets, each Sub-App has an associated Module (there is also a one-on-one relationship between a SubApp and a Module).

### Modules
 
A GUI component in Fermat is divided into 2 Plug-ins, the graphic interfaces and the module of such interface. This last one has the following funtionalities:

- Works as a connection between the Plug-ins of the platform, cosuming the services that they provide.
- It covers the logic of the presentation, gathering, organizing and grouping Plug-in data.

For more information about how to create a Module refer to [this documentation](https://github.com/bitDubai/fermat/blob/master/README-PLUG-INS.md)


**Creo que aqui antes de hablar de los problemas de persistencia de info entre fragmentos y hablar de la solucion (las sesiones), hay que hablar de fragmentos, y el fragment factory y el enum de fragmentos quiza**

### Session

One of the problems when using fragments to construct wallets or sub-apps is the sharing of information between fragments on a fermat app life cycle, since a fragment is eliminated when not visible, and has to be re-created when it regains focus. This data must be saved in some place just in case a user wants to change something in a Wallet, and leave the session open. 

To resolve this, there exists something called Sessions: Objects that works like shared memory between the different screens that your Wallet or SubApp may have. These apps must have they're own session object to share any information they need to share. There is also data that every session always shares, such as as the Module of the Wallet or SubApp, its Public Key, a reference to the Error Mananger (object that handles exceptions generated in the platform) and a Map (<Key,Value> pair object) that lets an app hold the data it needs to share.

Managing fermat sessions is done using a Wallet Manager or a SubApp Manager. These objects hold the state of a Wallet or a Sub-App ready for when the user switches back to the Wallet ot Sub-App's screen.

Every class made to represent a Session has to extend its functionality from `AbstractFermatSession`. Here's an example: 

```java

public class ReferenceWalletSession extends AbstractFermatSession<InstalledWallet,CryptoWalletManager,ProviderManager> implements WalletSession {
    ...
 
```
Where:
- `InstalledWallet` holds a reference to the installed wallet.
- `CryptoWalletManager` is the module corresponding to said wallet.
- `ProviderManager` (unused at this time) is a Fermat equivalent to android's R class.

<br>


### Fragment factory
Each GUI component has a folder designated to the fragment factory, that is in charge of connecting what is already developed in the Navigation Structure with the controlling fragments of such screens.

Un Fragment Factory consta de dos elementos: un enum *Fragments Enum Type* y una clase *Fragment Factory*. Estos elementos se han de ubicar en la carpeta `fragmentFactory` del proyecto que representa tu app.

- Los *Fragments Enum Type* representan identificadores para los fragmentos que conforman tu Wallet o SubApp. estos enums han de heredar de `FermatFragmentsEnumType` como se demuestra en el siguiente ejemplo:

```java
public enum IntraUserIdentityFragmentsEnumType implements FermatFragmentsEnumType<IntraUserIdentityFragmentsEnumType> {

   CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT("CCPSACCIMF"),
   CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT("CCPSACCICIF")
   ;

   private String key;

   IntraUserIdentityFragmentsEnumType(String key) { this.key = key; }

   @Override
   public String getKey() { return this.key; }

   @Override
   public String toString() { return key; }

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

- Los *Fragment Factory* son clases que retornan instancias de los fragmentos identificados por su correspondiente *Fragments Enum Type*. Cada app ha de tener su propio *Fragment Factory* y este ha de heredar de `FermatWalletFragmentFactory` si los fragmentos forman parde te una Wallet o de `FermatSubAppFragmentFactory` si los fragmentos forman parte de una SubApp, tal como se demuestra en este ejemplo:

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

Fermat is an application different from other Android applications; it has its own navigation structure, that is based on screens and sub-screens that begin to “draw” from uploaded objects when executed based on files that deliver information about what it is needed to draw in each screen/sub-screen and in what order.

Permite definir el flujo de interacción entre las distintas pantallas de la aplicación, asi como ciertos aspectos visuales de estas pantallas tales como fondos, colores, y tamaños.

También permite definir para las pantallas la existencia de Headers, Footers, Navigation Drawers, Tabs y TabStrips, Menus, entre otros elementos siguiendo un estilo similar a *WordPress*.

Dependiendo de si quieres crear una SubApp o una Wallet, has de agregar tu estructura de navegacion en el metodo `void factoryReset()` de alguna de estas dos clases:

- `SubAppRuntimeEnginePluginRoot` ubicada en `DMP/plugin/engine/fermat-dmp-plugin-engine-sub-app-runtime-bitdubai/` en el caso de una SuApp
- `WalletRuntimeEnginePluginRoot` ubicada en `DMP/plugin/engine/fermat-dmp-plugin-engine-wallet-runtime-bitdubai/` en el caso de una Wallet 

Este es un ejemplo simple de como crear la estructura de navegacion para una subapp:

```java
private void factoryReset(){
    ...
    
    // Creating the Navigation Structure for the Intra User Identity SubApp
    RuntimeSubApp runtimeSubApp = new RuntimeSubApp();
    runtimeSubApp.setType(SubApps.CCP_INTRA_USER_IDENTITY);
    String intraUserIdentityPublicKey = "public_key_ccp_intra_user_identity";
    runtimeSubApp.setPublicKey(intraUserIdentityPublicKey);
    
    // Creating a Activity. Screen: Create New Identity
    runtimeActivity = new Activity();
    runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY);
    runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY.getCode());
    runtimeActivity.setColor("#03A9F4");
    
    // Adding the Activity in the Navigation Structure
    runtimeSubApp.addActivity(runtimeActivity);
    runtimeSubApp.setStartActivity(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY);
    
    // Title Bar (a.k.a Action Bar) of the Activity
    runtimeTitleBar = new TitleBar();
    runtimeTitleBar.setLabel("Identity Manager");
    runtimeTitleBar.setColor("#1189a4");
    runtimeTitleBar.setTitleColor("#ffffff");
    runtimeTitleBar.setLabelSize(18);
    runtimeTitleBar.setIsTitleTextStatic(true);
    
    // Adding the Title Bar in the Activity
    runtimeActivity.setTitleBar(runtimeTitleBar);
    
    // Status Bar of the Activity
    statusBar = new StatusBar();
    statusBar.setColor("#1189a4");
    
    // Adding the Status Bar in the Activity
    runtimeActivity.setStatusBar(statusBar);
    
    // Fragment for this activity
    runtimeFragment = new Fragment();
    runtimeFragment.setType(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
    
    // Adding the Fragment in the Activity 
    // and seting it has a Start Fragment (is going to show first for this activiy)
    runtimeActivity.addFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
    runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());
    
    // Adding the Navigation Structure in the plataform
    listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);
    
    ...
}
```

**NOTA:** Para ver un ejemplos mas completos de estructuras de navegacion puedes revisar el metodo `private WalletNavigationStructure createCryptoBrokerWalletNavigationStructure()` en el caso de una wallet y `private void createWalletStoreNavigationStructure()` en el caso de una SubApp

La ubicacion de las estructuras de navegacion en estos archivos es provisoria; en un futuro como primer paso se deberá leer de un XML en el repositorio de Fermat en github y como segundo paso debera poder obtenerse de los otros nodos de la red fermat.

#### Elements of the Navigation Structure

Como se indico en el punto anterior, Fermat ofrece una serie de objetos armar la estructura de navegacion de wallet o subapp,. siguiendo un estilo similar al de WordPress. En estos siguientes apartados hablamos un poco mas en detalle sobre los diferentes objetos que se proveen y un ejemplo de como usarlo.

##### Activity

```java
...
 
// Creating a Activity. Screen: Create New Identity
runtimeActivity = new Activity();
runtimeActivity.setType(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY);
runtimeActivity.setActivityType(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY.getCode());
runtimeActivity.setColor("#03A9F4");

// Adding the Activity in the Navigation Structure 
runtimeSubApp.addActivity(runtimeActivity);
// Seting the Activity has a Start Activity (is going to show first for this app)
runtimeSubApp.setStartActivity(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY);

...
```

Una actividad en el contexto de Fermat es un contenedor base el cual le dice al core de android como va a estar diseñada la pantalla, cual va a ser su flujo y que elementos la componen. (Esto se realiza de esta forma para que en un futuro no desarrolladores puedan integrarse a Fermat). Un developer al contrario de android no debe desarrollar la clase Activity de android para poder correr sus fragmentos, si no que con declararlos en el runtime bajo un objeto Activity (FermatActivity) es suficiente para que se dibujen en pantalla.

Los identificadores de las actividades se deben crear en el enum `Activities` del paquete `com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums`

```java
public enum Activities {

    CWP_SHELL_LOGIN("CSL"),
    CWP_SHOP_MANAGER_MAIN("CSMM"),
    CWP_WALLET_MANAGER_MAIN("CWMM"),
    CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN("CWRWAKAB1M"),

    // Reference wallet
    CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN("CWRWBWBV1M"),
    CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_TRANSACTIONS("CWRWBWBV1T"),
    CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_PAYMENT_REQUEST("CWRWBWBV1PR"),
    CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_CONTACTS("CWRWBWBV1C"),
    CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_SETTINGS("CWRWBWBV1S"),

    CCP_BITCOIN_WALLET_SEND_FORM_ACTIVITY("CCPBWSFA"),
    CCP_BITCOIN_WALLET_REQUEST_FORM_ACTIVITY("CCPBWRFA"),
    CCP_BITCOIN_WALLET_CONTACT_DETAIL_ACTIVITY("CCPBWCDA"),
    CCP_BITCOIN_WALLET_SETTINGS_ACTIVITY("CCPBWSA"),
    CCP_BITCOIN_WALLET_ADD_CONNECTION_ACTIVITY("CCPBWACA"),
    CCP_BITCOIN_WALLET_NO_IDENTITY_ACTIVITY("CCPBWNIA"),
    
    ...
}
```

##### Fragment

```java
...

// Fragment for this activity
runtimeFragment = new Fragment();
runtimeFragment.setType(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

// Adding the Fragment in the Activity 
// and seting it has a Start Fragment (is going to show first for this activiy)
runtimeActivity.addFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

...
```

Un fragmento en Fermat tiene el mismo significado que en [Android](http://developer.android.com/intl/es/training/basics/fragments/index.html), y tiene una representacion en la Estructura de Navegacion para ser asignada a una actividad o a un Tab. 

Los identificadores de los fragmentos se deben crear en el enum `Fragments` del paquete `com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.`

```java
public enum Fragments implements FermatFragments {
    CWP_SHELL_LOGIN("CSL"),
    CWP_WALLET_MANAGER_MAIN("CWMM"),
    CWP_SUB_APP_DEVELOPER("CSAD"),
    CWP_WALLET_MANAGER_SHOP("CWMS"),
    CWP_SHOP_MANAGER_MAIN("CSMM"),
    CWP_SHOP_MANAGER_FREE("CSMF"),
    CWP_SHOP_MANAGER_PAID("CSMP"),
    CWP_SHOP_MANAGER_ACCEPTED_NEARBY("CSMAN"),

    CWP_WALLET_PUBLISHER_MAIN_FRAGMENT("CWPMF"),

    // Wallet Store
    CWP_WALLET_STORE_MAIN_ACTIVITY("CWPWSMA"),
    CWP_WALLET_STORE_DETAIL_ACTIVITY("CWPWSDA"),
    CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY("CWPWSMDA"),
    
    ...
}
```

##### Header

```java
...
// Createting a Header
runtimeHeader = new Header();
runtimeHeader.setLabel("Market rate");

// Seting the Header in the Activity
runtimeActivity.setHeader(runtimeHeader);
...
```
  
Es posible agregar un header expandible y colapsable en una actividad de tu app. Esto se realiza en varios pasos:

- Definir en la estructura de navegación que la actividad posee un header:
 
- Crear una Clase `<nombreScreen>HeaderViewPainter` que implemente `HeaderViewPainter` en la carpeta `commons/headers/` de tu proyecto GUI. Por ejemplo en la bitcoin wallet sería `commons/headers/HomeHeaderViewPainter.java`. Esta clase contiene la vista que se quiere mostrar como un header

- Dentro del metodo `onActivityCreated` del fragmento que va a contener el header, se debe pasar como parametro a `getPaintActivtyFeactures().addHeaderView()` una instancia de `<nombreScreen>HeaderViewPainter`

##### Footer

```java
...

// Creating a Footer
Footer runtimeFooter = new Footer();
runtimeFooter.setBackgroundColor("#AAAAAA");

// Creating the Fragment for the Footer
runtimeFragment = new Fragment();
runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS.getKey());
runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS.getKey(), runtimeFragment);

// Associating the Fragment with the Footer
runtimeFooter.setFragmentCode(Fragments.CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS.getKey());

// Associating the Footer with the Activity
runtimeActivity.setFooter(runtimeFooter);

...
```

Es posible agregar un *Footer* deslizable en una actividad de tu app. Esto se realiza en varios pasos:

- Definir en la estructura de navegación que la actividad posee un *Footer* y asignarle un fragmento
 
- Crear una Clase `<nombreScreen>FooterViewPainter` que implemente `FooterViewPainter` en la carpeta `commons/footers/` de tu proyecto GUI; por ejemplo en la Crypto Boroker Wallet sería `commons/footers/HomeFooterViewPainter.java`. Esta clase contiene la vistas que conforman el *Footer*:
  - `slide_container` es el View del *Footer* que se encuentra siempre visible para poder desplegar el contenido
  - `footer_container` es el View del *Footer* que representa su contenido, y se mustra cuando el *Footer* despliega

- Dentro del metodo `onActivityCreated` del fragmento que va a contener el *Footer*, se debe pasar como parametro a `getPaintActivtyFeactures().addFooterView()` una instancia de `<nombreScreen>FooterViewPainter`

##### SideMenu (Navigation Drawer) and MenuItem

```java
...

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

...
```

Es posible agregar un *Navigation Drawer* (o Side Menu) que te permita dirigirte a las diferentes pantallas de tu app. Esta se define en varios pasos

- Definir en la estructura de navegacion que la actividad posee un *Side Menu* que este ultimo posee una serie de *Menu Items*

- Crear una Clase `<nombreApp>NavigationViewAdapater` (con su View Holder) que implemente `FermatAdapter` en la carpeta `commons/navigationView/`. Por ejemplo en la bitcoin wallet sería `commons/navigationDrawer/BitcoinWalletNavigationViewAdapter.java`. Este adapter representa los items que se han de mostrar en el navigation drawer y tienen relacion con los definidos en la estructura de navegacion

- Crear una Clase `<nombreApp>NavigationViewPainter` que implemente `NavigationViewPainter` en la carpeta `commons/navigationView/` Por ejemplo en la bitcoin wallet sería `commons/navigationDrawer/BitcoinWalletNavigationViewPainter.java`. Esta clase contiene los elementos que conforman el *Navigation Drawer*:
  - *Header*: un View que representa el header del Navigation Drawer. Se suele colocar la imagen de la persona logueada
  - *Adapter*: una instancia de `<nombreApp>NavigationViewAdapater` y representa los items del menu
  - *Content*: un View que se dibuja despues de los items del menu y se puede colocar cualquier cosa, como por ejemplo un footer para mostrar mas informacion 
  
- Dentro del metodo `onActivityCreated` del fragmento que va a contener el *Navigation Drawer*, se debe pasar como parametro a `getPaintActivtyFeactures().addNavigationView()` una instancia de `<nombreApp>NavigationViewPainter`

##### Tabs and TabStrip
##### TitleBar and StatusBar
##### Wizard and WizardPage

### Android api
#### API Organitation
#### FermatFragment Class

En tu proyecto GUI debes crear los fragmentos que defines en la estructura de navegacion, creando clases que hereden de `FermatFragment` y colocandolas dentro de la carpeta `fragments` de tu proyecto. `FermatFragment` tiene referencias a la `Session` de tu Wallet o SubApp, asi como a los `Settings` y al `ProviderManager`. Existen varias subclases en `fermat-api` que extienden `FermatFragment` y facilitan ciertos trabajos, como es el caso de manejar listas y cosas mas especializadas como listas desplegables, wirzards, etc:

- `FermatExpandableListFragment`
- `FermatListFragment`
- `FermatWalletExpandableListFragment`
- `FermatWalletListFragment`
- `FermatWizardPageFragment`

Para mas informacion sobre estos u otros fragmentos por favor revisa el proyecto `fermat-api`. 

Aqui tenemos un ejemplo de un fragmento basico extendiendo de `FermatFragment` donde obtiene referencia a a la `Session` de la app a la que pertenece, su `Module` y su `Error Manager`:

```java
public class SettingsActivityFragment extends FermatFragment {

    // Constants
    private static final String TAG = "SettingsActivityFragment";

    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;


    public static SettingsActivityFragment newInstance() {
        return new SettingsActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((CryptoBrokerWalletSession) walletSession).getModuleManager();
            errorManager = walletSession.getErrorManager();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            BrokerNavigationViewPainter navigationViewPainter = new BrokerNavigationViewPainter(getActivity(), null);
            getPaintActivtyFeactures().addNavigationView(navigationViewPainter);
        } catch (Exception e) {
            makeText(getActivity(), "Oops! recovering from system error", Toast.LENGTH_SHORT).show();
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }
}
```

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

## Part III: How To:

### Project creation

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

### Create a Navigation Structure for your app 
### Android Core Connection
#### Connect a Fragment Factory
 Al momento de conectar el FragmentFactory respectivo al plugin desarrollado se debe agregar en el android core para que pueda tener referencia al mismo, se deben seguir los siguientes pasos:
Incluir la dependencia al módulo en el build.gradle
Ubicarse en el folder la ruta /android-core/common/version_1/fragment_factory/  aquí se encontrarán las clases de SubAppFragmentFactory y WalletFragmentFactory, las cuales son las encargadas de crear la instancia al fragmentFactory del modulo previamente creado y así es como el android core puede instanciarlo.
#### Connect to Session Manager
 Al momento de conectar la session respectiva al plugin desarrollado se debe agregar en el android core para que pueda tener referencia al mismo, se deben seguir los siguientes pasos:
Incluir la dependencia al módulo en el build.gradle
Ubicarse en el folder la ruta /android-core/common/version_1/sessions/  aquí se encontrarán las clases de SubAppSessionManager y WalletSessionManager, las cuales son las encargadas de crear la instancia al Session del modulo previamente creado y así es como el android core puede instanciarla.

#### Desktop
En construcción…
Provisionalmente se encuentra ubicado en la clase /android-core/common/version_1/ProvisoryData
#### Connect Module
#### Put an Icon from your app in the Main screen
#### Interacting with the Session and the FragmentFactory﻿



#### Basic 'Hello World' Fermat Wallet code


PASOS PARA CREAR UN REFERENCE WALLET FROM SCRATCH:
---------------------------------------------------------------



Crear el codigo base del Android Reference Wallet: 
	Esto incluye: 
		Code: WalletFragmentFactory, WalletFragmentsEnumType, PreferenceSettings y WalletSession
		Res:  Al menos un layout.xml, values (colors,dimens,strings)
	


Crear el codigo base del wallet Module Manager Plugin y registrarlo en el core
	Codigo base incluye el WalletModuleManager en el api
	El PluginRoot del ModuleManager en el plugin
	Registrar el plugin en el core del platform




Registrar los Activities y Fragments en el fermat-api:

	Ir a: fermat_api/layer/all_definition/navigation_structure/enums:
	Activities enum: Ingresar los Activities del wallet(el home, for starters?)
	Fragments enum: Ingresar al menos in Fragment que pertenezca al home (cash balance summary fragment?)
	Nota: Los fragments hechos en el Fragments enum se deben crear tambien en FramentsEnumType y usar en el FragmentFactory del wallet



Crear el Navigation Structure:
	Ir a: WPD/plugin/engine/wallet-runtime/PluginRoot:
	Agregar el Navigation Structure, usando los Activities y Fragments arriba creados.



Crear un icono en el Desktop:
	Ir a: DMP/android/sub_app/sub-app-wallet-manager/fragment/DesktopFragment:
	Agregar el wallet, de tal forma que el Desktop muestra el icono del wallet para poderla abrir.



Registrar el FragmentFactory en el android-core:
	Ir a: fermat-android-core/com.bitdubai.android_core.app/common.version_1/fragment_factory/WalletFragmentFactory
	y registrar el FragmentFactory del Reference Wallet aqui.



Registrar el WalletSession en el android-core:
	Ir a:  fermat-android-core/com.bitdubai.android_core.app/common.version_1/Sessions/WalletSessionManager
	y registrar el WalletSession del Reference wallet junto al WalletModuleManager Plugin aqui.
Nota: Hay que modificar ademas la Interfaz: fermat-android-apilayer\definition\wallet\interfaces\WalletSessionManager.java
Nota2: Tambien \fermat-android-core\com.bitdubai\android_core\app\FermatActivity.java
