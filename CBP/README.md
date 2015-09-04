# CBP Modules Specification

This is the simplified specification of the Crypto Broker Platform plugins

## Android

### Wallet
* **crypto-broker**: frontend de las actividades financieras de un Crypto Broker(vender crypto, vender cash, inyeccion de capital) y provee un balance contable unificado
* **Crypto Customer**: frontend de las actividades financieras de un Crypto Customer(comprar crypto, comprar cash) y provee un balance contable unificado

### SubApp
* **Crypto Broker**: frontend de la administracion de identidades de los Brokers, la relacion con otros brokers y descubrimiento de Brokers para los Customers (solicitudes de conexion, acuerdos especiales, etc)
* **Crypto Customer**: frontend de la administracion de identidades de los Customers y la relacion con otros Customers (notificaciones de un Broker, acuerdos especiales, etc)
* **Customers**: frontend de la gestion de contactos entre un Broker y sus Customers
* **SubApp Manager**: frontend del gestor de las SubApps instaladas en el dispositivo
* **Wallet Manager**: frontend del gestor de las Wallets instaladas

## Java

### Wallet Module
* **Crypto Broker**: gestion de la informacion y las actividades financieras de un Broker
* **Crypto Customer**: gestion de la informacion y las actividades financieras de un Customer

### SubApp Module
* **Crypto Broker**: administracion de identidades de los Brokers y la relacion con otros Brokers
* **Crypto Customer**: administracion de identidades de los Customers y la relacion con otros Customers
* **Customers**: gestion de contactos entre un Broker y sus Customers
* **SubApp Manager**: gestor de las SubApps instaladas en el dispositivo
* **Wallet Manager**: gestor de las Wallets instaladas

### Contract
* **Crypto Broker Fiat Money Buy**: solicitud de compra de Crypto de un Crypto Customer
* **Crypto Broker Fiat Money Buy**: solicitud de compra de Fiat Cash de un Crypto Customer
* **Crypto Customer Market Money Sell**: solicitud de compra de Crypto de un Crypto Customer
* **Crypto Customer Fiat Money Sell**: solicitud de compra de Fiat Cash de un Crypto Customer

### Middleware
* **Customers**: administra y subclasifica a los Crypto Customers de un Crypto Broker

### Actor
* **Crypto Broker**: administra la relacion con los Brokers (establecer conexion, listar contactos de este tipo, etc)
* **Crypto Customer**: administra la relacion con los Customers

### Agent
* **Crypto Broker**: evaluacion de balances consolidados y calculo de indices para la ganancia en las Business Transactions de un Crypto Broker.

### World
* **Fiat Index**: establece la relacion de valor entre dos monedas Fiat (por ejemplo: bolivar vs dolar).

### Business Transaction
* **CRYPTO BROKER**
 * **Stock Market Crypto**: recarga de stock Crypto de Market Money.
 * **Stock Fiat Cash**: recarga de stock Cash de Fiat Money.
 * **Stock Fiat Bank**: recarga de stock Bank de Market Money.
 * **Crypto Customer Market Crypto Sale**: venta de monedas Crypto del Broker a otros actores.
 * **Crypto Customer Fiat Cash Sale**: venta de monedas Cash Fiat del Broker a otros actores.
 * **Crypto Customer Fiat Bank Sale**: venta de monedas Cash Fiat del Broker a otros actores.
* **CRYPTO CUSTOMER**
 * **Crypto Broker Market Crypto Buy**: compra de monedas Crypto del Customer al Broker.
 * **Crypto Broker Fiat Cash Buy**: compra de monedas Cash Fiat del Customer al Broker.
 * **Crypto Broker Fiat Bank Buy**: compra de monedas Cash Fiat del Customer al Broker.

### Crypto Transaction (no esta cerrada la idea)(reutilizamos lo del CCP?)
* **Send Crypto**: envio de monedas Crypto a traves del Blockchain.
* **Receive Crypto**: recepcion de monedas Crypto a traves del Blockchain.

### Fiat Cash Transaction
* **Give Fiat Cash On Hand**: registro de pago con efectivo Fiat Cash.
* **Receive Fiat Cash On Hand**: registro de cobro con efectivo Fiat Cash.
* **Send Fiat Cash Delivery**: envio de Fiat Cash a traves de un tercero.
* **Receive Fiat Cash Delivery**: recepcion de Fiat Cash a traves de un tercero.

### Fiat Bank Transaction
* **Make Offline Fiat Bank Deposit**: registro manual de un deposito hecho con Fiat en una cuenta bancaria.
* **Receive Offline Fiat Bank Deposit**: registro manual de recepcion de un deposito hecho con Fiat en una cuenta bancaria.

### Wallet (reutilizamos las wallets crypto del CCP?)
* **CRYPTO BROKER**
 * **Crypto Broker**: gestiona el balance crypto del Crypto Broker.
 * **Cash**: gestiona el balance efectivo del Crypto Broker.
 * **Bank**: gestiona el balance bancario del Crypto Broker (puede estar relacionado a multiples cuentas bancarias).
* **CRYPTO CUSTOMER**
 * **Crypto Customer**: gestiona el balance crypto del Crypto Customer.

### Identity
* **Crypto Broker**: gestiona la Clave Privada y Publica del Broker asociadas con un alias.
* **Crypto Customer**: gestiona la Clave Privava y Publica del Customer asociadas con un alias.

### Network Service
* **Crypto Broker**: maneja la comunicacion entre Brokers y los actores que deseen comunicarse con el (incluido otros Brokers)
* **Crypto Customer**: maneja la comunicacion Customer - Broker.
