Historia: Crypto Broker Wallet Configuration

Como quiero poder configurar los elementos propios de la Wallet
  Como un Crypto Broker

Utilizando el Crypto Broker SubApp
  Y el Crypto Broker Subpp Module
  Y el Crypto Broker Identity
  Y el Crypto Broker Identity Wallet Linker

Escenario: Instalar primera Crypto Broker Wallet y la asocia a una identidad
  Dado que no tengo ninguna Crypto Broker Wallet instalada
  Cuando instale mi primera Crypto Broker Wallet
  Entonces se debe crear una identidad mediante el plugin Crypto Broker Identity
    Y asociar esta identidad a la Crypto Broker Wallet mediante el plugin Crypto Broker Identity Wallet Linker

Escenario: un Crypto Broker instala otra Crypto Broker Wallet y la asocia a una identidad existente
  Dado que he instalado una Crypto Broker Wallet
   	 Y asocie una identidad a la Crypto Broker Wallet
  Cuando instale una nueva Crypto Broker Wallet
     Y seleccione asociar en la Crypto Broker SubApp que desea asociar la Wallet a una identidad existente.
     Y seleccione una identidad de la lista pasada por el plugin Crypto Broker Identity Wallet Linker
  Entonces se debe asociar la identidad seleccionada a la Crypto Broker Wallet mediante el plugin Crypto Broker Identity Wallet Linker

Escenario: un Crypto Broker instala otra Crypto Broker Wallet y la asocia a una nueva identidad
  Dado que tengo instalada una Crypto Broker Wallet
    Y he asociado una identidad a la Crypto Broker Wallet
  Cuando instale una nueva Crypto Broker Wallet
    Y seleccione en la Crypto Broker SubApp que deseo crear una nueva identidad para la nueva Crypto Broker Wallet
  Entonces se debe crear una nueva identidad mediante el Crypto Broker Identity
    Y se debe asociar la identidad creada a la Crypto Broker Wallet mediante el plugin Crypto Broker Identity Wallet Linker

Escenario: un Crypto Broker establece la moneda de referencia (Dolar, Euro...)
  Dado que tengo instalada una Crypto Broker Wallet
    Y he asociado una identidad a la Crypto Broker Wallet
  Cuando seleccione la moneda de referencia que se usará para los calculos en la Wallet en la Crypto Broker SubApp
  Entonces el plugin Crypto Broker Identity Wallet Linker asocia la moneda de referencia a la wallet que se esta configurando

Escenario: un Crypto Broker establece la mercaderia que va a manejar con su Crypto Broker Wallet
  Dado que tengo instalada una Crypto Broker Wallet
    Y he asociado una identidad a la Crypto Broker Wallet
  Cuando seleccione las direrentes mercaderias que va a manejar en la Wallet, tanto Market Money como Fiat Money en la Crypto Broker SubApp
  Entonces el plugin Crypto Broker Identity Wallet Linker asocia las diferentes mercaderias seleccionadas a la wallet que se esta configurando

Escenario: un Crypto Broker establece los tipos de pago con los que trabajará en la Crypto Broker Wallet
  Dado que tengo instalada una Crypto Broker Wallet
    Y he asociado una identidad a la Crypto Broker Wallet
  Cuando seleccione los direrentes tipos de pago que va a manejar en la Wallet en la Crypto Broker SubApp
  Entonces el plugin Crypto Broker Identity Wallet Linker asocia los diferentes tipos de pagos seleccionados a la wallet que se esta configurando

Escenario: un Crypto Broker establece las tasas de referencia de cada mercaderia
  Dado que tengo instalada una Crypto Broker Wallet
    Y he asociado una identidad a la Crypto Broker Wallet
    Y he establecido la moneda de referencia con la que trabajará la Crypto Broker Wallet
    Y he seleccionado los tipos de mercaderia con las que va a trabajar en la wallet que esta configurando
  Cuando ingrese las tasas de referencia para cada mercaderia que desee manejar en la Wallet en la Crypto Broker SubApp
  Entonces el plugin Crypto Broker Identity Wallet Linker asocia las diferentes tasas ingresadas a la mercaderia correpondiente en la Crypto Broker Wallet que se esta configurando

Escenario: un Crypto Broker establece la información que desea hacer publica
  Dado que tengo instalada una Crypto Broker Wallet
    Y he asociado una identidad a la Crypto Broker Wallet
    Y he establecido la moneda de referencia con la que trabajará
    Y he seleccionado los tipos de mercaderia con las que va a trabajar
    Y he establecido las tasas de referencias para cada mercaderia
    Y he establecido los tipos de pago con los que va a trabajar
  Cuando seleccione la información que desea hacer publica de la wallet que esta configurando en la Crypto Broker SubApp
  Entonces el plugin Crypto Broker Identity Wallet Linker guarda la configuración de visibilidad publica de la Crypto Broker Wallet que se esta configurando
