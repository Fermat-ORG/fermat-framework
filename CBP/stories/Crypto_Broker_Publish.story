Historia: Crypto Broker Publish Identity

Como quiero ser descubierto por los Crypto Customers
  Como un Crypto Broker

Necesito publicar mi Identidad Publica
  Y la información sobre mi mercaderia

Utilizando los plugins Cryto Broker Actor
  Y el Crypto Broker Identity SubApp
  Y el Crypto Broker Identity SubApp Module
  Y el Crypto Broker Wallet Identity Middleware
  Y el Wallet Settings Middleware Plugin WPD
  Y el Crypto Broker Network Service

Escenario: Publicacion de Información del broker
  Dado que tengo instalada una Crypto Broker Wallet
     Y tengo una Crypto Broker Identity
     Y ya establecí las configuraciones de la Wallet a través del Wallet Settings Middleware Plugin WPD
  Cuando decida publicar mi Crypto Broker Identity en la Crypto Broker Identity SubApp
  Entonces la Crypto Broker Identity SubApp le indica al Crypto Broker Identity SubApp Module que establezca la identidad a publica.

Escenario: El Discovery pide la información publica de una identidad
  Dado que ya establecí las configuraciones de la Wallet a través del Wallet Settings Middleware Plugin WPD
     Y tengo mi identidad establecida en un status publico
  Cuando el Cryto Broker Actor requiera mi información publica para usarla en el proceso de Discovery
  Entonces el Cryto Broker Actor le pide al Crypto Broker Wallet Identity Middleware las Wallets asociadas a la identidad
         Y luego por cada Wallet el Cryto Broker Actor le pide al Wallet Settings Middleware Plugin WPD el XML con la información de cada Wallet
         Y genera un listado con la información publica pasada en cada XML
         Y la envia al Cryto Customer a través del Crypto Broker Network Service

-------------------------------------------------------------------------------------------------------------------------------------------

Ejemplo del listado con la información publica de una identidad del Crypto Broker:

  - Foto
  - Alias
  - Tipos de Market Money disponibles
    - Bitcoin: 230 USD
    - Litecoin: 10 USD
  - Tipos de Fiat Money disponibles
    - Bolivares: 630 x 1 USD
    - Pesos Argentino: 14 x 1 USD
    - Euros: 1,2 USD
  - Tipos de pagos aceptados en Market Money
    - Bitcoin
    - Litecoin
    - Ethereum
  - Tipos de pagos aceptados en Fiat Money
    - Forines Hungaros
    - Dolares Estadounidenses
    - Pesos Colombianos 

-------------------------------------------------------------------------------------------------------------------------------------------
