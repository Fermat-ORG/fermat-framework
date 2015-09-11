# CBP Modules Specification

This is the simplified specification of the Crypto Broker Platform plugins

## Android

### Reference Wallet
* [Crypto Broker](android/reference_wallet/fermat-android-reference-wallet-crypto-broker-bitdubai/): frontend de las actividades financieras de un Crypto Broker(vender crypto, vender cash, inyeccion de capital) y provee un balance contable unificado
* [Crypto Customer](android/reference_wallet/fermat-android-reference-wallet-crypto-customer-bitdubai/): frontend de las actividades financieras de un Crypto Customer(comprar crypto, comprar cash) y provee un balance contable unificado

### SubApp
* [Crypto Broker Identity](android/sup_app/fermat-android-sub-app-crypto-broker-identity-bitdubai/): frontend de administracion de identidades de los Brokers
* [Crypto Broker Community](android/sup_app/fermat-android-sub-app-crypto-broker-community-bitdubai/): frontend de administracion de la relacion entre un broker y otros brokers y el descubrimiento de Brokers para los Customers (solicitudes de conexion, acuerdos especiales, etc)
* [Customers]
* [Crypto Customer Identity](android/sup_app/fermat-android-sub-app-crypto-customer-bitdubai/): frontend de la administracion de identidades de los Customers y la relacion con otros Customers (notificaciones de un Broker, acuerdos especiales, etc)
* [Crypto Customer Community]

## Desktop
* [SubApp Manager](android/sup_app/fermat-android-desktop-sub-app-manager-bitdubai/): frontend del gestor de las SubApps instaladas en el dispositivo
* [Wallet Manager](android/sup_app/fermat-android-desktop-wallet-manager/): frontend del gestor de las Wallets instaladas

## Plugins

### Wallet Module
* [Crypto Broker](plugin/wallet_module/fermat-cbp-plugin-wallet-module-crypto-broker-bitdubai/): gestion de la informacion y las actividades financieras de un Broker
* [Crypto Customer](plugin/wallet_module/fermat-cbp-plugin-wallet-module-crypto-customer-bitdubai/): gestion de la informacion y las actividades financieras de un Customer

### SubApp Module
* [Crypto Broker Identity](plugin/sub_app_module/fermat-cbp-plugin-sub-app-module-crypto-broker-identity-bitdubai/): administracion de identidades de los Brokers
* [Crypto Broker Community](plugin/sub_app_module/fermat-cbp-plugin-sub-app-module-crypto-broker-community-bitdubai/): administracion de la relacion entre un broker y otros brokers y el descubrimiento de Brokers para los Customers
* [Crypto Customer](plugin/sub_app_module/fermat-cbp-plugin-sub-app-module-crypto-customer-bitdubai/): administracion de identidades de los Customers y la relacion con otros Customers
* [Customers](plugin/sub_app_module/fermat-cbp-plugin-sub-app-module-customers-bitdubai/): gestion de contactos entre un Broker y sus Customers
* [SubApp Manager](plugin/sub_app_module/fermat-cbp-plugin-sub-app-module-sub-app-manager-bitdubai/): gestor de las SubApps instaladas en el dispositivo
* [Wallet Manager](plugin/sub_app_module/fermat-cbp-plugin-sub-app-module-wallet-manager-bitdubai/): gestor de las Wallets instaladas

### Contract
* [Crypto Broker Market Money Buy](plugin/contract/fermat-cbp-plugin-contract-crypto-broker-market-money-buy-bitdubai/): contrato de compra de Crypto de un Crypto Customer
* [Crypto Broker Fiat Money Buy](plugin/contract/fermat-cbp-plugin-contract-crypto-broker-fiat-money-buy-bitdubai/): contrato de compra de Fiat Cash de un Crypto Customer
* [Crypto Customer Market Money Sale](plugin/contract/fermat-cbp-plugin-contract-crypto-customer-market-money-sale-bitdubai/): contrato de venta de Crypto de un Crypto Customer
* [Crypto Customer Fiat Money Sale](plugin/contract/fermat-cbp-plugin-contract-crypto-customer-fiat-money-sale-bitdubai/): contrato de venta de Fiat Cash de un Crypto Customer

### Request
* [Crypto Broker Purchase](plugin/request/fermat-cbp-plugin-request-crypto-broker-purchase-bitdubai): gestiona la solicitud de compra del Crypto Customer al Crypto Broker

### Middleware
* [Customers](plugin/middleware/fermat-cbp-plugin-middleware-customers-bitdubai/): administra y subclasifica a los Crypto Customers de un Crypto Broker
* [Crypto Broker Wallet Identity](plugin/middleware/fermat-cbp-plugin-middleware-crypto-broker-wallet-identity-bitdubai/): relaciona una Crypto Broker Identity con una Crypto Broker Wallet.
* [Crypto Broker Wallet Settings](plugin/middleware/fermat-cbp-plugin-middleware-crypto-broker-wallet-settings-bitdubai/): Maneja la confguracion de las Crypto Broker Wallets
* [Wallet Manager](plugin/middleware/fermat-cbp-plugin-middleware-wallet-manager-bitdubai/): conocimiento de como instalar y desinstalar una **CBP Wallet**
* [Sub App Manager](plugin/middleware/fermat-cbp-plugin-middleware-sub-app-manager-bitdubai/): conocimiento de como instalar y desinstalar una **CBP Sub App**

### Actor
* [Crypto Broker](plugin/actor/fermat-cbp-plugin-actor-crypto-broker-bitdubai/): administra la relacion con los Brokers (establecer conexion, listar contactos de este tipo, etc)
* [Crypto Customer](plugin/actor/fermat-cbp-plugin-actor-crypto-customer-bitdubai/): administra la relacion con los Customers

### Agent
* [Crypto Broker](plugin/agent/fermat-cbp-plugin-agent-crypto-broker-bitdubai/): evaluacion de balances consolidados y calculo de indices para la ganancia en las Business Transactions de un Crypto Broker.

### World
* [Fiat Index](plugin/world/fermat-cbp-plugin-world-fiat-index-bitdubai): establece la relacion de valor entre dos monedas Fiat (por ejemplo: bolivar vs dolar).

### Business Transaction
* [Crypto Market Money Stock Replenishment](plugin/business_transaction/fermat-cbp-plugin-business-transaction-crypto-broker-market-crypto-stock-replenish-bitdubai): recarga de stock Crypto de Market Money.
  * [Cash Fiat Money Stock Replenishment](plugin/business_transaction/fermat-cbp-plugin-business-transaction-crypto-broker-fiat-cash-stock-replenish-bitdubai): recarga de stock Cash de Fiat Money.
* [Bank Fiat Money Stock Replenishment](plugin/business_transaction/fermat-cbp-plugin-business-transaction-crypto-broker-fiat-bank-stock-replenish-bitdubai): recarga de stock Bank de Market Money.
* [Crypto Customer Market Crypto Sale](plugin/business_transaction/fermat-cbp-plugin-business-transaction-crypto-customer-market-crypto-sale-bitdubai): venta de monedas Crypto del Broker a otros actores.
* [Crypto Customer Fiat Cash Sale](plugin/business_transaction/fermat-cbp-plugin-business-transaction-crypto-customer-fiat-cash-sale-bitdubai): venta de monedas Cash Fiat del Broker a otros actores.
* [Crypto Customer Fiat Bank Sale](plugin/business_transaction/fermat-cbp-plugin-business-transaction-crypto-customer-fiat-bank-sale-bitdubai): venta de monedas Cash Fiat del Broker a otros actores.
* [Crypto Broker Market Crypto Purchase](plugin/business_transaction/fermat-cbp-plugin-business-transaction-crypto-broker-market-crypto-purchase-bitdubai): compra de monedas Crypto del Customer al Broker.
  * [Crypto Broker Fiat Cash Purchase](plugin/business_transaction/fermat-cbp-plugin-business-transaction-crypto-broker-fiat-cash-purchase-bitdubai): compra de monedas Cash Fiat del Customer al Broker.
* [Crypto Broker Fiat Bank Purchase](plugin/business_transaction/fermat-cbp-plugin-business-transaction-crypto-broker-fiat-bank-purchase-bitdubai): compra de monedas Cash Fiat del Customer al Broker.

### Market Crypto Transaction
* [Send Crypto](plugin/market_crypto_transaction/fermat-cbp-plugin-market-crypto-transaction-send-market-crypto-bitdubai): envio de monedas Crypto a traves del Blockchain.
* [Receive Crypto](plugin/market_crypto_transaction/fermat-cbp-plugin-market-crypto-transaction-receive-market-crypto-bitdubai): recepcion de monedas Crypto a traves del Blockchain.

### Fiat Cash Transaction
* [Give Fiat Cash On Hand](plugin/fiat_cash_transaction/fermat-cbp-plugin-fiat-cash-transaction-give-fiat-cash-on-hand-bitdubai): registro de pago con efectivo Fiat Cash.
* [Receive Fiat Cash On Hand](plugin/fiat_cash_transaction/fermat-cbp-plugin-fiat-cash-transaction-receive-fiat-cash-on-hand-bitdubai): registro de cobro con efectivo Fiat Cash.
* [Send Fiat Cash Delivery](plugin/fiat_cash_transaction/fermat-cbp-plugin-fiat-cash-transaction-send-fiat-cash-delivery-bitdubai): envio de Fiat Cash a traves de un tercero.
* [Receive Fiat Cash Delivery](plugin/fiat_cash_transaction/fermat-cbp-plugin-fiat-cash-transaction-receive-fiat-cash-delivery-bitdubai): recepcion de Fiat Cash a traves de un tercero.

### Fiat Bank Transaction
* [Make Offline Fiat Bank Transfer](plugin/fiat_bank_transaction/fermat-cbp-plugin-fiat-bank-transaction-make-offline-fiat-bank-transfer-bitdubai): registro manual de un deposito hecho con Fiat en una cuenta bancaria.
* [Receive Offline Fiat Bank Transfer](plugin/fiat_bank_transaction/fetmat-cbp-plugin-fiat-bank-transaction-receive-offline-fiat-bank-transfer-bitdubai): registro manual de recepcion de un deposito hecho con Fiat en una cuenta bancaria.

### Wallet
* [Crypto Broker Crypto Market Money](plugin/wallet/fermat-cbp-plugin-wallet-crypto-broker-market-crypto-bitdubai): gestiona el balance crypto del Crypto Broker.
* [Crypto Broker Cash Fiat Money](plugin/wallet/fermat-cbp-plugin-wallet-crypto-broker-fiat-cash-bitdubai): gestiona el balance efectivo del Crypto Broker.
* [Crypto Broker Bank Fiat Money](plugin/wallet/fermat-cbp-plugin-wallet-crypto-broker-fiat-bank-bitdubai): gestiona el balance bancario del Crypto Broker (puede estar relacionado a multiples cuentas bancarias).
* [Crypto Customer Crypto Market Money](plugin/wallet/fermat-cbp-plugin-wallet-crypto-customer-market-crypto): gestiona el balance crypto del Crypto Customer.

### Identity
* [Crypto Broker](plugin/identity/fermat-cbp-plugin-identity-crypto-broker-bitdubai): gestiona la Clave Privada y Publica del Broker asociadas con un alias.
* [Crypto Customer](plugin/identity/fermat-cbp-plugin-identity-crypto-customer-bitdubai): gestiona la Clave Privava y Publica del Customer asociadas con un alias.

### Network Service
* [Crypto Broker](plugin/network_service/fermat-cbp-plugin-network-service-crypto-broker-bitdubai): maneja la comunicacion entre Brokers y los actores que deseen comunicarse con el (incluido otros Brokers)
* [Crypto Customer](plugin/network_service/fermat-cbp-plugin-network-service-crypto-customer-bitdubai): maneja la comunicacion Customer - Broker.
