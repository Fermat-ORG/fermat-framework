(A = Aprobado, ? = por determinar)

### Crypto Broker Reference Wallet

#### Home Open Negotiations Tab 
  - **(A) Esperando por Broker** >> `Customer Broker Sale Negotiation`
  - **(A) Esperando por Customer** >> `Customer Broker Sale Negotiation`

#### Home Open Contracts Tab 
  - **(A) Esperando por Broker** >> `Customer Broker Sale Contract`
  - **(A) Esperando por Customer** >> `Customer Broker Sale Contract`

#### Negotiation Details
  - **(A) Locaciones del broker** >> `Customer Broker Sale Negotiation` 
    - Se asigna esta lista cuando el Broker confirme que quiere aceptar Cash on Hand y Cash Delivery como pago 
  - **(A) Locaciones del customer** >> `Customer Broker Sale Negotiation` 
    - Se asigna esta lista cuando el Customer confirme que quiere aceptar Cash on Hand y Cash Delivery como pago 
  - **(A) Cuenta(s) bancaria(s) del broker** >> `Customer Broker Sale Negotiation` 
    - Se asigna esta lista cuendo el Broker confirme que quiere aceptar Bank Transfer como pago
  - **(A) Cuenta(s) bancaria(s) del customer** >> `Customer Broker Sale Negotiation`
    - Se asigna esta lista cuendo el Customer confirme que quiere que le entreguen la mercancia a traves de Bank Transfer
  - **(A) Monedas que acepta el broker como pago** >> `Broker Wallet Module` 
    - Se guarda en un xml ya que esto viene de la cotizacion que se obtiene de la lista de brokers
  - **(?) Precio de mercado** >> Proveedores de la super capa CER registrados en el setting de la `Crypto Broker Wallet`
  - **(?) Precio sugerido** >> `Crypto Broker Wallet`
  - **(A) Datos ingresados** >> `Customer Broker Sale Negotiation`
    - El plugin tiene un metodo que me devuelve las clausulas con los datos ingresados en el orden correcto, una clausula a la vez
    - Deberia existir un metod que me devuelva todas esas clausulas ya ordenadas
  - **(A) Enviar Datos** >> `Customer Broker Update Negotiation Transmition`

#### Contract Details
  - **(A) Datos ingresados** >> `CustomerBrokerSale Contract` usando `Customer Broker Sale Negotiation` para obtener el detalle de la negociacion y terminar de armar la data a mostrar
  - **(A) Referencia a la negociation** >> `Customer Broker Sale Contract`
  - **(A) Enviar Confirmacion de pago** >> `Broker Ack Offline Payment Business Transaction` o `Broker Ack Online Payment Business Transaction` dependiendo de la moneda a vender
    - en el caso de `Broker Ack Online Payment Business Transaction` el module no lo ejecuta directamente
  - **(A) Enviar Mercancia** >> `Broker Offline Business Transaction` o `Broker Online Business Transaction` dependiendo de la moneda a vender

#### Market Rates
  - **(A) Lista de Tasas de Cambio actual por pares de mercancia** >> `Crypto Index` y `Fiat Index` 
    - Ahora hay que ver como se va a hacer con la nueva super capa CER
  - **(A) Historico de Tasas de Cambio para un par de mercancias** >> `Crypto Index` y `Fiat Index` 
    - Ahora hay que ver como se va a hacer con la nueva super capa CER

#### Stock Merchandise
  - **(?) Lista de mercancias seleccionadas como ganancia** >> `Crypto Broker Wallet Module`
  - **(A) Lista de Stock actual de mercancias** >> `Crypto Broker Wallet`
  - **(A) Historico de stock para una mercancia** >> `Crypto Broker Wallet` 
    - Debe darme info de inicio y fin del Stock para cada uno de los dias en ese historico

#### Contract History
  - **(A) Lista de contratos cerrados y cancelados** >> `Customer Broker Sale Contract`
  - **(A) Metodo para filtrar contratos por estado** >> `Customer Broker Sale Contract`

#### Earnings
  - **(?) Lista de mercancias seleccionadas como ganancia** >> Settings de `Crypto Broker Wallet` o `Crypto Broker Wallet Module` 
  - **(?) Historico de ganancias para una mercancia** >> `Matching Engine Middleware`

#### Settings
No usa plugins, es solo Android

#### Settings Merchandises
  - **(?) Lista de Wallets** >> Settings de `Crypto Broker Wallet` o `Crypto Broker Wallet Module`
  - **Hacer Restock** >> `Crypto Money Restock` 

#### Settings Providers
  - **(A) Lista de Proveedores** >> Plugins de Super Layer CER 
    - Los puedo mostrar todos sin importar que wallets he asociado. 
    - Deberia existir una interface o plugin que me perimta listarlos y obtener referencias a ellos
  - **(A) Asociar proveedores con la wallet** >> Settings de `Crypto Broker Wallet`

#### Settings Locations
  - **(A) Agregar Locaciones** >> Settings de `Crypto Broker Wallet Module`
  - **(A) Modificar Locaciones** >> Settings de `Crypto Broker Wallet Module`
  - **(A) Eliminar Locaciones** >> Settings de `Crypto Broker Wallet Module`

#### Wizard Identity
  - **(A) Lista de Identidades** >> `Crypto Broker Identity`
  - **(A) Asociar Wallet con Identidad** >> `Crypto Broker Actor`

#### Wizard Merchandises
  - **(A) Plataformas (tipos de wallet: bank, cash o crypto)** >> Enum `Platforms`
  - **(A) Wallets Disponibles para plataforma seleccionada** >> `WPD Wallet Manager`
  - **(?) Asociar Wallet como stock** >> Settings de `Crypto Broker Wallet` o `Crypto Broker Wallet Module` 
    - Si mal no recuerdo es el setting de la Wallet. 
    - Con las wallets asociadas se conoce que mercancias se van a utilizar
    - Se debe guardar en el setting la plataforma y el public_key de la wallet
    - En el caso de uan wallet bank se debe guardar tambien de que cuenta va a salir su stock 
  - **(?) Asociar Wallet como ganancia** >> Settings de `Crypto Broker Wallet` o `Crypto Broker Wallet Module` 
    - Si mal no recuerdo es el setting de la Wallet. 
    - Se debe guardar en el setting la plataforma y el public_key de la wallet
    - En el caso de uan wallet bank se debe guardar tambien de que cuenta(s) va a usar como medio de pago
  - **(?) Spread** >> Settings de `Crypto Broker Wallet` o `Crypto Broker Module` 
    - Si mal no recuerdo es el setting de la Wallet

#### Wizard Providers
  - **(A) Lista de Proveedores** >> Plugins de Super Layer CER 
    - Los puedo mostrar todos sin importar que wallets he asociado. 
    - Deberia existir una interface o plugin que me perimta listarlos y obtener referencias a ellos
  - **(A) Asociar proveedores con la wallet** >> Settings de `Crypto Broker Wallet`

#### Wizard Locations
  - **(A) Agregar Locaciones** >> Settings de `Crypto Broker Wallet Module`

#### Wizard Bank Accounts
  - **(?) Seleccionar cuentas bancarias** >>Settings de `Crypto Broker Wallet Module` o `Wallet` 
    - Depende de haber seleccionado una Bank Wallet como ganancia. 
    - La informacion de las cuentas deberia venir de alli.


------------------------------------


### Crypto Customer Reference Wallet

#### Home Open Negotiations Tab 
  - **(A) Esperando por Broker** >> `Customer Broker Purchase Negotiation`
  - **(A) Esperando por Customer** >> `Customer Broker Purchase Negotiation`

#### Home Open Contracts Tab 
  - **(A) Esperando por Broker** >> `Customer Broker Purchase Contract`
  - **(A) Esperando por Customer** >> `Customer Broker Purchase Contract`

#### Negotiation Details
  - **(A) Locaciones del broker** >> `Customer Broker Purchase Negotiation` 
    - Se asigna esta lista cuando el Broker acepte que quiere aceptar esa forma de pago Cash on Hand y Cash Delivery
  - **(A) Locaciones del customer** >> `Customer Broker Purchase Negotiation` 
  - **() Modos de pago** >> `Customer Broker Purchase Negotiation` 
    - Viene con la data ingresada por el broker para la negociacion
    - Se asigna esta lista cuando el Customer acepte que quiere aceptar esa forma de pago Cash on Hand y Cash Delivery
  - **(A) Cuenta(s) bancaria(s) del broker** >> `Customer Broker Purchase Negotiation` 
    - Se asigna esta lista cuendo el Broker confirme que quiere aceptar Bank Transfer como pago
  - **(A) Cuenta(s) bancaria(s) del customer** >> `Customer Broker Purchase Negotiation`
    - Se asigna esta lista cuendo el Customer confirme que quiere que le entreguen la mercancia a traves de Bank Transfer
  - **(A) Monedas que acepta el broker como pago** >> `Crypto Customer Wallet Module` 
    - Se guarda en un xml ya que esto viene de la cotizacion que se obtiene de la lista de brokers
  - **(A) Precio de mercado** >> `Crypto Index` y `Fiat Index`
  - **(A) Datos ingresados** >> `Customer Broker Purchase Negotiation`
    - El plugin tiene un metodo que me devuelve las clausulas con los datos ingresados en el orden correcto, una clausula a la vez
    - Deberia existir un metod que me devuelva todas esas clausulas ya ordenadas
  - **(A) Enviar Datos** >> `Customer Broker Update Negotiation Transmition`

#### Contract Details
  - **(A) Datos ingresados** >> `Customer Broker Purchase Contract` usando `Customer Broker Purchase Negotiation` para obtener el detalle de la negociacion y terminar de armar la data a mostrar
  - **(A) Referencia a la negociation** >> `Customer Broker Purchase Contract`
  - **(A) Enviar Pago** >> `Customer Online Payment Business Transaction` o `Customer Offline Payment Business Transaction` dependiendo de la moneda de pago
  - **(A) Enviar Confirmacion de mercancia** >> `Customer Ack Offline Merchandise Business Transaction` o `Customer Ack Online Merchandise Transaction` dependiendo del tipo de mercancia
    - En el caso de `Customer Ack Online Merchandise Transaction` el module no lo ejecuta directamente

#### Start Negotiation


#### Market Rates
  - **(A) Lista de Tasas de Cambio actual por pares de mercancia** >> `Crypto Index` y `Fiat Index` 
    - Ahora hay que ver como se va a hacer con la nueva super capa CER

#### Contract History
  - **(A) Lista de contratos cerrados y cancelados** >> `Customer Broker Purchase Contract`
  - **(A) Metodo para filtrar contratos por estado** >> `Customer Broker Purchase Contract`

#### Broker List
  - **(A) Obtener lista de brokers** >> `Crypto Broker Actor Connection`
  - **(A) Obtener Mercancias que vende un broker** >> `Crypto Customer Module`y `Crypto Broker Actor`
    - Me recorro la lista de brokers que me da `Crypto Broker Actor Connection`
    - Por cada Broker llamo a `Crypto Broker Actor` para que me devuelva la lista de mercancias que vende el broker
    - Guardo esa data en un xml dentro del module como si fuera una cache. 
    - Cada ves que vaya obtener la lista de brokers verifico si esta data esta desactualizada para ejecutar nuevamente el ciclo
  - **(A) Obtener cotizaciones por broker** >> `Crypto Customer Module`y `Crypto Broker Actor`
    - Me recorro la lista de brokers que me da `Crypto Broker Actor Connection`
    - Por cada Broker llamo a `Crypto Broker Actor` para que me devuelva la lista cotizacines por cada mercancia que vende el broker
    - Guardo esa data en un xml dentro del module como si fuera una cache indicando un tiempo de caducidad.
    - Cada ves que vaya obtener la lista de brokers verifico si ya se paso el tiempo de caducidad para ejecutar nuevamente el ciclo

#### Settings
No usa plugins, es solo Android

#### Setting Currencies of Interest
  - **(A) Lista de Currencies configuradas** >> Settings de `Crypto Customer Wallet Module`
  - **(A) Lista de Currencies que soporta Fermat** >> Enums `CryptoCurrency` y `FiatCurrency`
  - **(A) Agregar Currency** >> Settings de `Crypto Customer Wallet Module`
  - **(A) Modificar Currency** >> Settings de `Crypto Customer Wallet Module`
  - **(A) Eliminar Currency** >> Settings de `Crypto Customer Wallet Module`

#### Setting Locations
  - **(A) Agregar Locaciones** >> Settings de `Crypto Broker Wallet Module`
  - **(A) Modificar Locaciones** >> Settings de `Crypto Broker Wallet Module`
  - **(A) Eliminar Locaciones** >> Settings de `Crypto Broker Wallet Module`

#### Setting Bank Accounts
  - **(?) Agregar cuentas bancarias** >>Settings de `Crypto Customer Wallet Module`

#### Wizard Identity
  - **(A) Lista de Identidades** >> `Crypto Customer Identity`
  - **(A) Asociar Wallet con Identidad** >> `Crypto Customer Actor`

#### Wizard Merchandises
  - **(A) Wallets Disponibles para plataforma seleccionada** >> `WPD Wallet Manager`
    - Solo se van a mostrar wallets de tipo Crypto
  - **(?) Asociar Wallet** >> Settings de `Crypto Customer Wallet Module`
    - Se debe guardar en el setting la plataforma y el public_key de la wallet
    - En el caso de uan wallet bank se debe guardar tambien de que cuenta(s) va a usar como medio de pago

#### Wizard Currencies of Interest
  - **(A) Lista de Currencies que soporta Fermat** >> Enums `CryptoCurrency` y `FiatCurrency`
  - **(A) Agregar Currency** >> Settings de `Crypto Customer Wallet Module`

#### Wizard Locations
  - **(A) Agregar Locaciones** >> Settings de `Crypto Broker Wallet Module`

#### Wizard Bank Accounts
  - **(?) Agregar cuentas bancarias** >>Settings de `Crypto Customer Wallet Module`
