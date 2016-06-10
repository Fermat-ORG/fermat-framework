<!-- all links tested by laderuner -->

![alt text](https://github.com/bitDubai/media-kit/blob/master/MediaKit/Fermat%20Branding/Fermat%20Logotype/Fermat_Logo_3D.png "Fermat Logo")

<br><br>

## Introduction

GUI Components are one of three basic components that can be added to the Fermat Framework. The two others are Add-ons and Plug-ins. Each GUI component has a well defined responsibility within the system, and usually collaborates from within one or more workflows in which it participates.  

To accomplish its mission, a GUI component must have a wireframe.

<br>

## Part I: Concepts

### Wallet

A Wallet is a GUI Component that allows a user to carry out financial transactions, like sending and receiving crypto currencies, using different Plug-ins that Fermat offers through Modules. Each Module Wallet has an associated Module, thus, there is a one-on-one relationship between a Wallet and its Module.

### SubApp

A SubApp is a GUI Component that allows a user to carry out non-financial operations, such as creating identities within the platform, administrative tasks, among others. All of this, using various Plug-ins that Fermat offers through Modules. SubApps serve generally to complement the functionality of a wallet. Just like Wallets, each SubApp has an associated Module (there is also a one-on-one relationship between a SubApp and a Module).

### Modules
 
A GUI component in Fermat is divided into 2 Plug-ins, the graphic interfaces and the module of such interface. This last one has the following functionalities:

- Works as a connection between the Plug-ins of the platform, consuming the services that they provide.
- It covers the logic of the presentation, gathering, organizing and grouping Plug-in data.

For more information about how to create a Module refer to [this documentation](https://github.com/bitDubai/fermat/blob/master/README-PLUG-INS.md)

<!--COMENTARIO !!!!-->
<!--**Creo que aqui antes de hablar de los problemas de persistencia de info entre fragmentos y hablar de la solucion (las sesiones), hay que hablar de fragmentos, y el fragment factory y el enum de fragmentos quiza**-->

### Session

One of the problems when using fragments to construct wallets or subApps is the sharing of information between fragments on a fermat app life cycle, since a fragment is eliminated when not visible, and has to be recreated when it regains focus. This data must be saved in some place just in case a user wants to change something in a Wallet, and leave the session open. 

To resolve this, there exists something called Sessions: Objects that works like shared memory between the different screens that your Wallet or SubApp may have. These apps must have their own session object to share any information they need to share. There is also data that every session always shares, such as the Module of the Wallet or SubApp, its Public Key, a reference to the Error Manager (object that handles exceptions generated in the platform) and a Map (<Key,Value> pair object) that lets an app hold the data it needs to share.

Managing fermat sessions is done by using a Wallet Manager or a SubApp Manager. These objects hold the state of a Wallet or a SubApp ready for when the user switches back to the Wallet ot SubApp screen.

Every class made to represent a Session has to extend its functionality from `AbstractFermatSession`. Here's an example: 

```java

public class ReferenceWalletSession extends AbstractFermatSession<InstalledWallet,CryptoWalletManager,ProviderManager> implements WalletSession {
    ...
 
```
Where:
- `InstalledWallet` holds a reference to the installed wallet.
- `CryptoWalletManager` is the module corresponding to the wallet mentioned.
- `ProviderManager` (not used at this time) is a Fermat equivalent to android's R class.

<br>

### Fragment Factory
Each GUI component has a folder designated to the fragment factory, that is in charge of connecting what is already developed in the Navigation Structure with the controlling fragments of such screens.

A Fragment Factory consists of two elements: an enum *Enum Type Fragment* and a *Fragment Factory* class. These elements are to be placed in the project folder `fragmentFactory` representing your app.

- The *Enum Type fragments* represent identifiers for fragments that make up your Wallet or SubApp. These enums are to inherit from `FermatFragmentsEnumType` as shown in the following example:

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

- The *Fragment Factory* are classes that return forms of the fragments identified by its corresponding *Fragments Enum Type*. Every app must have its own *Fragment Factory* and it has to inherit from `FermatWalletFragmentFactory` if the fragments form part of a wallet or from `FermatSubAppFragmentFactory` if the fragments are part of a SubApp, as shown in this example:

```java
public class IntraUserIdentityFragmentFactory extends FermatSubAppFragmentFactory<IntraUserIdentitySubAppSession, IntraUserIdentityPreferenceSettings, IntraUserIdentityFragmentsEnumType> {

   @Override
   public AbstractFermatFragment getFermatFragment(IntraUserIdentityFragmentsEnumType fragments) throws FragmentNotFoundException {

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

### App Connections Class

The `AppConnections` abstract class lets you include instances of the things that your Wallet or Sub-App needs to run; these things are: the *Module*, the *Session* and the *Fragment Factory*. You can also include other instances such as: a *Navigation View Painter* (in case you'd like to include a custom a side menu), a *Footer Painter* (in case you'd want to include a footer) and a *Header Painter* (the same, only a header). These three elements can be defined in the *Navigation Structure* of your Wallet or Sub-App.

To include an AppConnections class inside your project, you first need to extend it and modify it, adding the instances of your project's classes. Then, you need to add an instance of your new `AppConnections` class in the `FermatAppConnectionManager`, which manages all the the different `AppConnections` classes inside Fermat.

This is a example of an `AppConnections` class:

```java
public class CryptoCustomerWalletFermatAppConnection extends AppConnections {

    ActorIdentity identity;

    public CryptoCustomerWalletFermatAppConnection(Activity activity, ActorIdentity identity) {
        super(activity);
        this.identity = identity;
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new CryptoCustomerWalletFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return new PluginVersionReference(Platforms.CRYPTO_BROKER_PLATFORM, Layers.WALLET_MODULE,
                Plugins.CRYPTO_CUSTOMER, Developers.BITDUBAI, new Version());
    }

    @Override
    protected AbstractFermatSession getSession() {
        return new CryptoCustomerWalletSession();
    }


    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new CustomerNavigationViewPainter(getActivity(), identity);
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return new CryptoCustomerWalletHeaderPainter();
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }
}
```

This is a example of the `FermatAppConnectionManager`

```java
public class FermatAppConnectionManager {

    public static AppConnections switchStatement(Activity activity,String publicKey){
        AppConnections fermatAppConnection = null;

        switch (publicKey){
            //CCP WALLET
            case "reference_wallet":
                fermatAppConnection = new BitcoinWalletFermatAppConnection(activity);
                break;
            //CCP Sub Apps
            case "public_key_ccp_intra_user_identity":
                fermatAppConnection = new CryptoWalletUserFermatAppConnection(activity);
                break;
            case "public_key_intra_user_community":
                fermatAppConnection = new CryptoWalletUserCommunityFermatAppConnection(activity);
                break;

            //DAP WALLETS
            case "asset_issuer" :
                fermatAppConnection = new WalletAssetIssuerFermatAppConnection(activity);
                break;
            case "asset_user"   :
                fermatAppConnection = new WalletAssetUserFermatAppConnection(activity);
                break;
            case "redeem_point" :
                fermatAppConnection = new WalletRedeemPointFermatAppConnection(activity);
                break;
            //DAP Sub Apps
            case "public_key_dap_asset_issuer_identity":
                fermatAppConnection = new AssetIssuerFermatAppConnection(activity);
                break;
            case "public_key_dap_asset_user_identity":
                fermatAppConnection = new AssetUserFermatAppConnection(activity);
                break;
            case "public_key_dap_redeem_point_identity":
                fermatAppConnection = new RedeemPointFermatAppConnection(activity);
                break;
            case "public_key_dap_factory":
                fermatAppConnection = new AssetFactoryFermatAppConnection(activity);
                break;
            case "public_key_dap_issuer_community":
                fermatAppConnection = new CommunityAssetIssuerFermatAppConnection(activity);
                break;
            case "public_key_dap_user_community":
                fermatAppConnection = new CommunityAssetUserFermatAppConnection(activity);
                break;
            case "public_key_dap_redeem_point_community":
                fermatAppConnection = new CommunityRedeemPointFermatAppConnection(activity);
                break;

            //PIP Sub Apps
            case "public_key_pip_developer_sub_app":
                fermatAppConnection = new DeveloperFermatAppConnection(activity);
                break;

            //CBP WALLETS
            case "crypto_broker_wallet":
                fermatAppConnection = new CryptoBrokerWalletFermatAppConnection(activity, null);
                break;
            case "crypto_customer_wallet":
                fermatAppConnection = new CryptoCustomerWalletFermatAppConnection(activity, null);
                break;
            //CBP Sub Apps
            case "public_key_crypto_broker_community":
                fermatAppConnection = new CryptoBrokerCommunityFermatAppConnection(activity);
                break;
            case "sub_app_crypto_broker_identity":
                fermatAppConnection = new CryptoBrokerIdentityFermatAppConnection(activity);
                break;
            case "sub_app_crypto_customer_identity":
                fermatAppConnection = new CryptoCustomerIdentityFermatAppConnection(activity);
                break;

            //CASH WALLET
            case "cash_wallet":
                fermatAppConnection = new CashMoneyWalletFermatAppConnection(activity, null);
                break;

            //BANKING WALLET
            case "banking_wallet":
                fermatAppConnection = new BankMoneyWalletFermatAppConnection(activity);
                break;

            // WPD Sub Apps
            case "public_key_store":
                fermatAppConnection = new WalletStoreFermatAppConnection(activity);
        }

        return fermatAppConnection;
    }

    public static AppConnections getFermatAppConnection(String publicKey, Activity activity, FermatSession referenceAppFermatSession) {
        AppConnections fermatAppConnection = switchStatement(activity,publicKey);
        fermatAppConnection.setFullyLoadedSession(referenceAppFermatSession);
        return fermatAppConnection;
    }

    public static AppConnections getFermatAppConnection(String appPublicKey, Activity activity) {
        AppConnections fermatAppConnection = switchStatement(activity,appPublicKey);
        return fermatAppConnection;
    }
}
```

### Navigation Structure

Fermat is an application different from other Android applications; it has its own navigation structure, that is based on screens and sub-screens that begin to “draw” from uploaded objects when executed based on files that deliver information about what it is needed to draw in each screen/sub-screen and in what order.

It allows to set the flow of interaction between the different application screens just as certain visual aspects of these screens like backgrounds, colors , and sizes.

It also allows to set for screens the existence of Headers, Footers, Navigation Drawers, Tabs and TabStrips, Menus, among other elements following a similar style to *WordPress*.

Depending on whether you want to create a SubApp or Wallet, you have to add your navigation structure in the `void factoryReset()` method to any of these two classes:

- `SubAppRuntimeEnginePluginRoot` Located in `DMP/plugin/engine/fermat-dmp-plugin-engine-sub-app-runtime-bitdubai/` in the case of a SubApp
- `WalletRuntimeEnginePluginRoot` Located in `DMP/plugin/engine/fermat-dmp-plugin-engine-wallet-runtime-bitdubai/` in the case of a Wallet

This is a simple example of how to create the navigation structure for a SubApp:

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
    
    // Adding the Navigation Structure in the platform
    listSubApp.put(runtimeSubApp.getPublicKey(), runtimeSubApp);
    
    ...
}
```

**NOTE:** For a more complete example of navigation structures you can review the `private WalletNavigationStructure createCryptoBrokerWalletNavigationStructure()` method in the case of a Wallet and `private void createWalletStoreNavigationStructure()` in case of a SubApp.

The location of the navigation structure in these files is temporary; in the future, as a first step it should be read from an XML in the Fermat repository in github and as a second step it should be able to be obtained from the other nodes of the Fermat network.

#### Elements of the Navigation Structure

As indicated in the previous point, Fermat offers a number of objects to build the navigation structure of a Wallet or SubApp, following a similar style to WordPress. In these following sections we’ll talk a little more in detail about the different objects provided and an example of how to use it.

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

An activity in the context of Fermat is a base container which tells the android core how the screen will be designed, what will its flow be and what elements make part of it (This is done this way so that in the future developers could join Fermat). Unlike android a developer should not develop Android Activity class in order to run their fragments, but declare them in the runtime under Activity object (FermatActivity) is enough for them to draw on screen.


Activity IDs must be created in the `Activities` enum package
`com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums`

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
// and setting it has a Start Fragment (is going to show first for this activity)
runtimeActivity.addFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey(), runtimeFragment);
runtimeActivity.setStartFragment(Fragments.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT.getKey());

...
```

A fragment on Fermat has the same meaning as in 
[Android](http://developer.android.com/intl/es/training/basics/fragments/index.html), and has a representation in the navigation structure to be assigned to an activity or a Tab. 


Fragment IDs must be created in the `Fragments` enum package
`com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.`

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
// Creating a Header
runtimeHeader = new Header();
runtimeHeader.setLabel("Market rate");

// Seting the Header in the Activity
runtimeActivity.setHeader(runtimeHeader);
...
```
  
It’s possible to add an expandable and collapsible header in an activity of your app. This is done in several steps:

- Set in the navigation structure that the activity has a header:
 
- Create a class `<ScreenName>HeaderViewPainter` that implements `HeaderViewPainter` in the `commons/headers/` folder of your GUI Project. For example, in the bitcoin wallet it would be `commons/headers/HomeHeaderViewPainter.java`. This class contains the view wanted to be shown as a header.

- Inside the `onActivityCreated` method of the fragment that the header will contain, `getPaintActivityFeatures().addHeaderView()` must be passed as a standard, a form of `<ScreenName>HeaderViewPainter`

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

It’s  possible to add a sliding *Footer* in an activity of your app. This is done in several steps:

- Set in the navigation structure for the activity to have a *Footer* and assign a fragment to it
 
- Create a `<ScreenName>FooterViewPainter` class that implements `FooterViewPainter` in the `commons/footers/` folder of your GUI project; for example in the Crypto Broker wallet it would be `commons/footers/HomeFooterViewPainter.java`. This class contains the views that make up the *Footer*:
  - `slide_container` is the *Footer* view that is always visible to be able to display the content.
  - `footer_container` is the *Footer* view that represents its content, and it shows up when the *Footer* is displayed.

- Within the `onActivityCreated` method of the fragment that will contain the *Footer*, the standard must be passed `getPaintActivtyFeactures().addFooterView()` a form of  `<nombreScreen>FooterViewPainter`

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

It’s possible to add a *Navigation Drawer* (or Side Menu) that allows you to go to different screens of your app. This is set in several steps

- Set in the navigation structure that the activity has a *Side Menu* and that the latter has a number of *Menu Items*

- Create a `<AppName>NavigationViewAdapter` class (with its View Holder) that implements `FermatAdapter` in the `commons/navigationView/` folder. For example in the bitcoin wallet it would be `commons/navigationDrawer/BitcoinWalletNavigationViewAdapter.java`. This adapter represents the items that are to be displayed in the navigation drawer and are related to those already set in the navigation structure

- Create a `<AppName>NavigationViewPainter` class that implements `NavigationViewPainter` in the `commons/navigationView/` folder. For example in the bitcoin wallet it would be `commons/navigationDrawer/BitcoinWalletNavigationViewPainter.java`. This class contains the elements that make up the *Navigation Drawer*:
  - *Header*: A View that represents the header of the Navigation Drawer. Usually the photo of the person logged in is placed here.
  - *Adapter*: a form `<AppName>NavigationViewAdapter` that represents the menu items
  - *Content*: A View that is drawn after the menu items, anything can be placed here, for example a footer to show more information 
  
- Within the `onActivityCreated` method of the fragment that will contain the *Navigation Drawer*, as a standard we must pass  `getPaintActivityFeactures().addNavigationView()` a form of `<AppName>NavigationViewPainter`

##### Tabs and TabStrip
##### TitleBar and StatusBar
##### Wizard and WizardPage

### Android api
#### API Organization
#### AbstractFermatFragment Class

In your GUI project, you must create the fragments that you set in the navigation structure, creating classes that inherit from `AbstractFermatFragment` and placing them within the `fragments` folder of your project. `AbstractFermatFragment` has a reference to your Wallet or SubApp `Session`, as well as, to `Settings` and `ProviderManager`. There are several subclasses in `fermat-api` that extend `AbstractFermatFragment` and facilitate certain jobs, such as handling lists and some more specialized things like drop-down lists, wirzards, etc:

- `FermatExpandableListFragment`
- `FermatListFragment`
- `FermatWalletExpandableListFragment`
- `FermatWalletListFragment`
- `FermatWizardPageFragment`

For more information about these and other fragments please see the `fermat-api` project. 

Here is an example of a basic fragment extending from `AbstractFermatFragment` where we obtain reference to the app `Session` to which it belongs, its `Module` and its `Error Manager`:

```java
public class SettingsActivityFragment extends AbstractFermatFragment {

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
            getPaintActivityFeatures().addNavigationView(navigationViewPainter);
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

The mandatory initial structure is the following: (note: the word ISSUE is not part of the name)

<br>
##### ISSUE: '_Plugin Name_' - Plug-In

This is the root of your issue structure and must be labeled as _SUPER ISSUE_. It is closed only when all of its children and grand children are closed.

<br>

##### ISSUE: '_Plugin Name_' - Analysis

This is the Analysis root. It is closed whenever all analysis is done. This issue must be linked to the root of the issue structure.

<br>
1 - ISSUE: **'_Plugin Name_' - Module - prototype**

This is the hardcoded module. Used for making the GUI prototype without using the fermat platform. This issue must be linked to the root of the issue structure.

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
      + Client_Type (Android, nowadays)
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
- **project_type**: Refers to the type of project you’re goint to create GUI components for. They could be **desktop**, **reference wallet** or **SubApp**.  
- **name_of_the_project**: This is the name of the project. For example: if your project is named **Crypto Broker Community** then you have to name it **crypto broker community**.
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
- Inside `src/main/java` you will find the package where you will place java files (classes, interfaces, enums..) with your Android code. It has the following basic packages: **fragmentFactory**, **fragments**, **preference_settings** and **session**. Each one of them explained in detail later on in this README.
- Inside `src/main/res` there are `xml` files found that represent *layouts, menus, colors, strings and sizes* as well as image files and others that represent visual resources with which you’re going to interact the java classes that have an Android logic.
- Everything that goes in the `test` folder is code that is used to make Unit Testing on the functionalities you’re developing in `src`.
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
 When connecting the corresponding FragmentFactory to the developed Plug-in we must add to the android core so it can have reference to itself, we must follow these steps:

Include dependency to the build.gradle module
Find in the folder the /android-core/common/version_1/fragment_factory/ route Here you will find the SubAppFragmentFactory and WalletFragmentFactory classes, that are in charge of creating the fragmentFactory form of the previously created module and so just as in the adroid core be able to do a petition.
#### Connect to Session Manager
 When connecting the corresponding session to the developed plug-in we must add it to the android core so that it can have referente to itself, we must follow these steps:
Include dependency to the build.gradle module 
Find in the folder the /android-core/common/version_1/sessions/ route Here you will find the SubAppSessionManager and WalletSessionManager classes, that are in charge of creating the form to the Session of the previously created module and so just as in the adroid core be able to do a petition.

#### Desktop
Under construction…
it is temporarily located in the /android-core/common/version_1/ProvisoryData class
#### Connect Module
#### Put an Icon from your app in the Main screen
#### Interacting with the Session and the FragmentFactory﻿

### Steps to Create a Reference Wallet or Sub-App from Scratch

1. Create the Android Reference Wallet code base: 

- Create the proyect structure has said in **Where to put your projects** section of this document
- Add the following to the project: 
  - In src/main/java: 
    - A class that extends from `WalletFragmentFactory`, 
    - A Enum that extends from `WalletFragmentsEnumType`, 
    - A class that extends from `PreferenceSettings` 
    - A class that extends from `AbstractFermatSession`
  - In src/main/res:  
    - at least one `layout.xml`
    - `colors.xml`, `dimension.xml` and `strings.xml`
	
2. Create the `AppConnections` class and add it in the `FermatAppConnectionsManager`

- In `src/main/java/app_connection` create a class that extend from `AppConnections` and fill the required methods, as described in the **App Connections Class** section of this document

3. Register the Activities and Fragments in the fermat-api project:

- Go to: `fermat_api/layer/all_definition/navigation_structure/enums` and:
  - In `Activities` enum: Enter the wallet or Sub-App activities (the home, for starters) and add it to the `getValueFromString(...)` method of this enum. Example: `CSH_CASH_MONEY_WALLET_HOME("CSHCMWH")`
  - In `Fragments` enum: Enter at least a Fragment that belongs to the home. Example: `CSH_CASH_MONEY_WALLET_BALANCE_SUMMARY("CSHCMWBS")`
- The enums made in `Fragments` must also be created in your `FermatFragmentsEnumType` and used in your Wallet or Sub-App `FermatFragmentFactory`.

4. Create the Navigation Structure:

- If you are creating a Wallet go to: `WPD/plugin/engine/fermat-wpd-plugin-engine-wallet-runtime-bitdubai/.../WalletRuntimeEnginePluginRoot.java`
- If you are creating a Sub-App go to: `DMP/plugin/engine/fermat-pip-plugin-engine-sub-app-runtime-bitdubai/.../SubAppRuntimeEnginePluginRoot.java`
- Add the Navigation Structure as described in the **Navigation Structure** section of this document using the Activities and Fragments created above.

5. Create an icon in the Desktop:

- If you are creating a Wallet go to: `DMP/android/sub_app/fermat-wpd-android-sub-app-wallet-manager-bitdubai/.../DesktopFragment.java`
- If you are creating a Sub-App go to: `PIP/android/sub_app/fermat-pip-android-sub-app-sub-app-manager-bitdubai/.../DesktopSubAppFragment.java`
- Add in the `getMoreData()` method the Wallet or Sub-App, in a way so the Desktop shows the wallet icon in order to be able to open it.

## References:
- How to Create a wallet/subapp from scratch: https://www.youtube.com/watch?v=-gIZqKwvhac
- The AppConection class, What it is and How to use it: https://www.youtube.com/watch?v=S0hqL3Smcko
- Where to put the Icons in the Fermat Desktop: https://www.youtube.com/watch?v=vhI-bq0Nz0Y
- Android api - Dialog Presentation Template: https://www.youtube.com/watch?v=oTDJN7RKajw
