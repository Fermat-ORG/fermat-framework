Historia: Crypto Broker Wallet Configuration

Como quiero poder configurar los elementos propios de la Wallet
  Como un Crypto Broker

Utilizando la Crypto Broker Wallet
  Y el Crypto Broker Wallet Module
  Y el Crypto Broker Identity SubApp
  Y el Crypto Broker Identity SubApp Module
  Y el Crypto Broker Identity Plugin
  Y el Crypto Broker Wallet Identity Middleware Plugin
  Y el Wallet Settings Middleware Plugin WPD

Escenario: Instalo primera Crypto Broker Wallet y la asocio a una identidad
  Dado que no tengo ninguna Crypto Broker Wallet instalada
  Cuando instale mi primera Crypto Broker Wallet
  Entonces se debe crear una identidad mediante el Crypto Broker Identity Plugin
    Y asociar esta identidad a la Crypto Broker Wallet mediante el Crypto Broker Wallet Identity Middleware Plugin

Escenario: Instalo otra Crypto Broker Wallet y la asocio a una identidad existente
  Dado que he instalado una Crypto Broker Wallet
   	 Y asocie una identidad a esa Crypto Broker Wallet
  Cuando instale una nueva Crypto Broker Wallet
     Y desee asociar una Identidad existente a la nueva Wallet desde la Crypto Broker Wallet
     Y seleccione una identidad de la lista que muestra la Crypto Broker Identity SubApp
  Entonces se debe asociar la identidad seleccionada a la Crypto Broker Wallet usando el Crypto Broker Wallet Identity Middleware Plugin

Escenario: Instalo otra Crypto Broker Wallet y la asocio a una nueva identidad
  Dado que tengo instalada una Crypto Broker Wallet
    Y he asociado una identidad a la Crypto Broker Wallet
  Cuando instale una nueva Crypto Broker Wallet
    Y desee asociar una nueva Indentidad desde la Crypto Broker Wallet
  Entonces la Crypto Broker Wallet ejecutar la Crypto Broker SubApp para crear una nueva identidad
    Y se debe crear una nueva identidad mediante el Crypto Broker Identity Plugin
    Y se debe asociar la identidad creada a la Crypto Broker Wallet mediante el Crypto Broker Wallet Identity Middleware Plugin

Escenario: Establesco la moneda de referencia (Dolar, Euro...) que voy a usar para trabajar con la Crypto Broker Wallet
  Dado que tengo instalada una Crypto Broker Wallet
    Y he asociado una identidad a la Crypto Broker Wallet
  Cuando seleccione la moneda de referencia que se usará para los calculos en la Crypto Broker Wallet
  Entonces se debe almacenar en la Crypto Broker Wallet esta informacion como una configuracion usando el Wallet Settings Middleware Plugin WPD

Escenario: Establesco la mercaderia que voy a manejar con una Crypto Broker Wallet
  Dado que tengo instalada una Crypto Broker Wallet
    Y he asociado una identidad a la Crypto Broker Wallet
  Cuando seleccione las direrentes mercaderias que voy a manejar, tanto Market Money como Fiat Money en la Crypto Broker Wallet
  Entonces se debe almacenar en la Crypto Broker Wallet esta informacion como una configuracion usando el Wallet Settings Middleware Plugin WPD

Escenario: Establesco los tipos de pago que voy a manejar con una Crypto Broker Wallet
  Dado que tengo instalada una Crypto Broker Wallet
    Y he asociado una identidad a la Crypto Broker Wallet
  Cuando seleccione los diferentes tipos de pago que va a manejar
  Entonces se debe almacenar en la Crypto Broker Wallet esta informacion como una configuracion usando el Wallet Settings Middleware Plugin WPD
  
Escenario: Establesco las tasas de referencia de cada mercaderia que voy a manejar con una Crypto Broker Wallet
  Dado que tengo instalada una Crypto Broker Wallet
    Y he asociado una identidad a la Crypto Broker Wallet
    Y he establecido la moneda de referencia con la que trabajará
    Y he seleccionado los tipos de mercaderia con los que trabajará
  Cuando ingrese las tasas de referencia para cada mercaderia que desee manejar en la Wallet en la Crypto Broker Wallet
  Entonces se debe almacenar en la Crypto Broker Wallet esta informacion como una configuracion usando el Wallet Settings Middleware Plugin WPD

Escenario: Establesco la información de la Crypto Broker Wallet que deseo hacer publica
  Dado que tengo instalada una Crypto Broker Wallet
    Y he asociado una identidad a la Crypto Broker Wallet
    Y he establecido la moneda de referencia con la que trabajará 
    Y he seleccionado los tipos de mercaderia con las que trabajará
    Y he establecido las tasas de referencias para cada mercaderia
    Y he establecido los tipos de pago con los que trabajará
  Cuando seleccione la información de la wallet que deseo hacer publica en la Crypto Broker Wallet
  Entonces se debe almacenar en la Crypto Broker Wallet esta informacion como una configuracion usando el Wallet Settings Middleware Plugin WPD
