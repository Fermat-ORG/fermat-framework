Story: Crypto Broker Configuration

In order poder configurar los elementos propios de la wallet

Using el Crypto Broker SubApp
  And el Cripto Broker Subpp Module
  And el Crypto Broker Identity
  And el Crypto Broker Identity Wallet Linker

Scenario: un Crypto Broker instala su primera Crypto Broker Wallet y la asocia a una identidad
   Given haber instalado una Crypto Broker Wallet
    Then crear una identidad mediante el plugin Crypto Broker Identity
     And asociar esta identidad a la Crypto Broker Wallet mediante el plugin Crypto Broker Identity Wallet Linker

Scenario: un Crypto Broker instala otra Crypto Broker Wallet y la asocia a una identidad existente
   Given haber instalado una Crypto Broker Wallet
   	 And haber asociado una identidad a la Crypto Broker Wallet
    When instalar una nueva Crypto Broker Wallet
     And seleccionar asociar en la Crypto Broker SubApp que desea asociar la Wallet a una identidad existente.
     And seleccionar una identidad de la lista pasada por el plugin Crypto Broker Identity Wallet Linker
    Then asociar la identidad seleccionada a la Crypto Broker Wallet mediante el plugin Crypto Broker Identity Wallet Linker

Scenario: un Crypto Broker instala otra Crypto Broker Wallet y la asocia a una nueva identidad
   Given haber instalado una Crypto Broker Wallet
   	 And haber asociado una identidad a la Crypto Broker Wallet
    When instalar una nueva Crypto Broker Wallet
     And seleccionaren la Crypto Broker SubApp que desea crear una nueva identidad para la nueva Crypto Broker Wallet
    Then crear una nueva identidad mediante el Crypto Broker Identity
     And asociar la identidad creada a la Crypto Broker Wallet mediante el plugin Crypto Broker Identity Wallet Linker

Scenario: un Crypto Broker establece la moneda de referencia (Dolar, Euro...)
   Given haber instalado una Crypto Broker Wallet
   	 And haber asociado una identidad a la Crypto Broker Wallet
   	When seleccionar la moneda de referencia que se usará para los calculos en la Wallet en la Crypto Broker SubApp
    Then el plugin Crypto Broker Identity Wallet Linker asocia la moneda de referencia a la wallet que se esta configurando

Scenario: un Crypto Broker establece la mercaderia que va a manejar con su Crypto Broker Wallet
   Given haber instalado una Crypto Broker Wallet
   	 And haber asociado una identidad a la Crypto Broker Wallet
   	When seleccionar las direrentes mercaderias que va a manejar en la Wallet, tanto Market Money como Fiat Money en la Crypto Broker SubApp
    Then el plugin Crypto Broker Identity Wallet Linker asocia las diferentes mercaderias seleccionadas a la wallet que se esta configurando

Scenario: un Crypto Broker establece los tipos de pago con los que trabajará en la Crypto Broker Wallet
   Given haber instalado una Crypto Broker Wallet
   	 And haber asociado una identidad a la Crypto Broker Wallet
   	When seleccionar los direrentes tipos de pago que va a manejar en la Wallet en la Crypto Broker SubApp
    Then el plugin Crypto Broker Identity Wallet Linker asocia los diferentes tipos de pagos seleccionados a la wallet que se esta configurando

Scenario: un Crypto Broker establece las tasas de referencia de cada mercaderia
   Given haber instalado una Crypto Broker Wallet
   	 And haber asociado una identidad a la Crypto Broker Wallet
   	 And haber establecido la moneda de referencia con la que trabajará la Crypto Broker Wallet
   	 And haber seleccionado los tipos de mercaderia con las que va a trabajar en la wallet que esta configurando
   	When ingresar las tasa de referencia para cada mercaderia que desee manejar en la Wallet en la Crypto Broker SubApp
    Then el plugin Crypto Broker Identity Wallet Linker asocia las diferentes tasas ingresadas a la mercaderia correpondiente en la wallet que se esta configurando

Scenario: un Crypto Broker establece la información que desea hacer publica
   Given haber instalado una Crypto Broker Wallet
   	 And haber asociado una identidad a la Crypto Broker Wallet
   	 And haber establecido la moneda de referencia con la que trabajará
   	 And haber seleccionado los tipos de mercaderia con las que va a trabajar
   	 And haber establecido las tasas de referencias para cada mercaderia
   	 And haber establecido los tipos de pago con los que va a trabajar
   	When selecciona la información que desea hacer publica de la wallet que esta configurando en la Crypto Broker SubApp
    Then el plugin Crypto Broker Identity Wallet Linker guarda la configuración de visibilidad publica de la wallet que se esta configurando