(A = Aprobado, ? = por determinar, I = implentado Android)

Notas:
- Los contratos no se cancelan (por ahora) y para evitar que se mantenga siempre abierto, se ha de colocar un estado EXPIRADO para indicar que el contrato paso las fechas para su ejecucion y asi filtrarlo de la lista de contratos abierto
- Definir al completo la nueva super capa CER con Luis, porque vemos que hacen falta otras capas sobre ella
- Leon propone hacer un plugin de Settings para CBP que permita guardar configuracines como las locaciones, las wallets asociadas, etc, tanto para el customer como para el broker

- layer CER con inteligencia, al estipo de P2P (plugins de index ya no irian, porque al parecer ya no se usan)
- Settings de locaciones, metodos de pago y cuentas bancarias (practicamente los datos que pueden ser seleccionables en una negociacion) en Plugin de Customer Broker Sale y Purchase Negotiation ya que ellso tienen la responsabildiad de llevar la informacion de las negociaciones
- Wallet Plugin: setting de wallets asociadas para que el tenga toda la informacion que necesita para hacer sus debitos y creditos. El Stock de una mercaderia puede estar descompuesto en varias wallets 
- Wallets que manejan ganancia en Plugin Matching Engie, ya que el tiene la responsabilidad de obtener las ganancias

### Crypto Broker Reference Wallet

#### Home Open Negotiations Tab [I]
  - **(A) Esperando por Broker** >> `Customer Broker Sale Negotiation`
  - **(A) Esperando por Customer** >> `Customer Broker Sale Negotiation`

#### Home Open Contracts Tab [I]
  - **(A) Esperando por Broker** >> `Customer Broker Sale Contract`
  - **(A) Esperando por Customer** >> `Customer Broker Sale Contract`

#### Negotiation Details
  - **(A) Locaciones del broker** >> `Customer Broker Sale Negotiation` 
    - Se asigna esta lista cuando el Broker confirme que quiere aceptar Cash on Hand y Cash Delivery como pago 
    - La lista de locaciones debe ser envidad por el `Crypto Broker Wallet Module` a traves del plugin `Customer Broker Update Negotation Transaction` para que el lo registre `Customer Broker Sale Negotiation` y se envia al customer
  - **(A) Agregar notas** >> `Customer Broker Sale Negotiation` 
    - Se registra en el campo MEMO
  - **(A) Locaciones del customer** >> `Customer Broker Sale Negotiation` 
    - Se asigna esta lista cuando el Customer confirme que quiere aceptar Cash on Hand y Cash Delivery para recibir la mercancia
  - **(A) Cuenta(s) bancaria(s) del broker** >> `Customer Broker Sale Negotiation` 
    - Se asigna esta lista cuendo el Broker confirme que quiere aceptar Bank Transfer como pago
  - **(A) Cuenta(s) bancaria(s) del customer** >> `Customer Broker Sale Negotiation`
    - Se asigna esta lista cuendo el Customer confirme que quiere que le entreguen la mercancia a traves de Bank Transfer
  - **(A) Monedas que acepta el broker como pago** >> `Broker Wallet Module` 
    - Se guarda en un xml ya que esto viene de la cotizacion que se obtiene de la lista de brokers
  - **(A) Precio de mercado** >> Proveedores de la super capa CER registrados en el setting de la `Crypto Broker Wallet`
  - **(A) Precio sugerido** >> `Crypto Broker Wallet`
  - **(A) Datos ingresados** >> `Customer Broker Sale Negotiation`
    - El plugin tiene un metodo que me devuelve las clausulas con los datos ingresados en el orden correcto, una clausula a la vez
    - Deberia existir un metod que me devuelva todas esas clausulas ya ordenadas
  - **(?) Modificar Datos** >> `Customer Broker Sale Negotiation`
    - me creo un wrapper para tener la data modificada y se actualice en `Customer Broker Sale Negotiation`
  - **(A) Enviar Datos** >> `Customer Broker Update Negotiation Transmition`
    - se envia el objeto devuelto por `Customer Broker Sale Negotiation`
  - **(A) Cerrar Negociacion** >> `Customer Broker Close Negotiation Transmition`
    - se le envie el objeto `NegotaitonInformation` con la informacion de la negociacion
  - **(?) Cancelar Negociacion** >> `Customer Broker Update Negotiation Transmition`
    - Se cambia el estado a cancelado en el `NegotaitonInformation` usando  `Customer Broker Sale Negotiation` para ser enviado entonces a `Customer Broker Update Negotiation Transmition` y el se encargue de actualizar esa info en la wallet del customer
    - Se va a usar un campo MEMO para (Texto libre) indicar la razon del porque

#### Contract Details
  - **(A) Datos ingresados** >> `Customer BrokerSale Contract` usando `Customer Broker Sale Negotiation` para obtener el detalle de la negociacion y terminar de armar la data a mostrar
  - **(A) Referencia a la negociation** >> `Customer Broker Sale Contract`
  - **(A) Enviar Confirmacion de pago** >> `Broker Ack Offline Payment Business Transaction` o `Broker Ack Online Payment Business Transaction` dependiendo de la moneda a vender
    - En el caso de `Broker Ack Online Payment Business Transaction` el module no lo ejecuta directamente
    - Se envia el contractID
  - **(A) Enviar Mercancia** >> `Broker Offline Business Transaction` o `Broker Online Business Transaction` dependiendo de la moneda a vender
    - Se envia el contractID

#### Market Rates [I]
  - **(A) Lista de Tasas de Cambio actual por pares de mercancia** >> `Crypto Index` y `Fiat Index`
    - Ahora hay que ver como se va a hacer con la nueva super capa CER
  - **(A) Historico de Tasas de Cambio para un par de mercancias** >> `Crypto Index` y `Fiat Index`
    - Ahora hay que ver como se va a hacer con la nueva super capa CER

#### Stock Merchandise
  - **(A) Lista de mercancias seleccionadas como stock** >> `Crypto Broker Wallet`
  - **(A) Lista de Stock actual de mercancias** >> `Crypto Broker Wallet`
  - **(A) Historico de stock para una mercancia** >> `Crypto Broker Wallet` 
    - Debe darme info de inicio y fin del Stock para cada uno de los dias en ese historico

#### Contract History [I]
  - **(A) Lista de contratos cerrados y cancelados** >> `Customer Broker Sale Contract`
  - **(A) Metodo para filtrar contratos por estado** >> `Customer Broker Sale Contract`

#### Earnings
  - **(?) Lista de mercancias seleccionadas como ganancia** >> Settings `Crypto Broker Wallet Module` 
  - **(A) Historico de ganancias para una mercancia** >> `Matching Engine Middleware`

#### Settings
No usa plugins, es solo Android

#### Settings Merchandises
  - **(?) Lista de Wallets** >> Settings `Crypto Broker Wallet Module`
  - **Hacer Restock** >> `Crypto Money Restock` o `Bank Money Restock` o `Cash Money Restock`

#### Settings Providers
  - **(A) Lista de Proveedores** >> Plugins de Super Layer CER 
    - Los puedo mostrar todos sin importar que wallets he asociado. 
    - Deberia existir una interface o plugin que me perimta listarlos y obtener referencias a ellos
  - **(?) Asociar proveedores con la wallet** >> Settings de `Crypto Broker Wallet`

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
  - **(?) Asociar Wallet como stock** >> Settings de `Crypto Broker Wallet Module` 
    - las mercaderias que se manejen en las wallets asociadas se han de pasar a la `Crypto Broker Wallet` para que ella lo registre como parte de sus setting
    - Con las wallets asociadas se conoce que mercancias se van a utilizar
    - Se debe guardar en el setting de `Crypto Broker Wallet Module` la plataforma, el public_key de la wallet y la mercancia que maneja esa wallet. En el caso de uan wallet bank se debe guardar tambien de que cuenta va a salir su stock 
    - Se debe guardar en el setting de `Crypto Broker Wallet` la mercancia que manejan las wallet
  - **(?) Asociar Wallet como ganancia** >> Settings `Crypto Broker Wallet Module`
    - Se debe guardar en el setting de `Crypto Broker Wallet Module` la plataforma, el public_key de la wallet y la mercancia que maneja esa wallet. En el caso de uan wallet bank se debe guardar tambien de que cuenta va a salir su stock
  - **(?) Spread** >> Settings de `Crypto Broker Wallet`

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

#### Home Open Negotiations Tab [I]
  - **(A) Esperando por Broker** >> `Customer Broker Purchase Negotiation`
  - **(A) Esperando por Customer** >> `Customer Broker Purchase Negotiation`

#### Home Open Contracts Tab [I]
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
  - **(?) Cancelar Negociacion** >> No se a que plugin debo consumir en este caso...

#### Contract Details
  - **Estado del contrato** >> `Customer Broker Purchase Contract`
    - este me devuelve un `ContractStatus` que me indica en que paso del contrato nos encontramos, y asi puedo tildar aquellos pasos que ya se procesaron, o sencillamente mostrar informacion resumida sobre el contrato en caso de que este haya sido completado o cancelado (este cancelado, hasta donde tengo entendido, se refiere a una negociacion que se cancela)
  - **(A) Datos ingresados** >> `Customer Broker Purchase Contract` usando `Customer Broker Purchase Negotiation` para obtener el detalle de la negociacion y terminar de armar la data a mostrar
  - **(A) Referencia a la negociation** >> `Customer Broker Purchase Contract`
  - **(A) Enviar Pago** >> `Customer Online Payment Business Transaction` o `Customer Offline Payment Business Transaction` dependiendo de la moneda de pago
  - **(A) Enviar Confirmacion de mercancia** >> `Customer Ack Offline Merchandise Business Transaction` o `Customer Ack Online Merchandise Transaction` dependiendo del tipo de mercancia
    - En el caso de `Customer Ack Online Merchandise Transaction` el module no lo ejecuta directamente

#### Start Negotiation
A esta pantalla le falta algo mas de analisis, porque con los que nos sugirió Luis con respecto a como obtener parte de la data de la negociacion (donde el broker selecciona que modo de pago quiere aceptar y esa informacion junto con las opciones de esa clausula van en la negociacion, asi mismo con los datos del customer), me cuesta ver como encajar lo que esta en el Issue https://github.com/bitDubai/fermat-graphic-design/issues/252
  - **(A) Mostrar Mercancia a comprar** >> `Crypto Customer Module`
    - Cuando se selecciona un broker de la lista de broker, automaticamente se selecciona la mercancia a comprar
  - **(A) Mostrar Moneda de pago** >> `Crypto Customer Module`
    - Ya este valor se obtuvo de la lista lista de brokers y fue registrado en el module
  - **(A) Mostrar Precio de Cotizacion** >> `Crypto Customer Module`
    - Ya este valor se obtuvo de la lista lista de brokers y fue registrado en el module
  - **(A) Crear Nueva negociacion** >> `Crypto Customer New Negotiation Transaction`
    - Debe permitirme registrar la siguiente data: Mercancia a comprar (Moneda), cantidad a comprar, moneda de pago, tasa de cambio a pagar, publicKeyCustomer, publicKeyBroker
    - Me deberia devolver un registro `NegotiationInformation` con los datos de esta nueva negociacion

#### Market Rates [I]
  - **(A) Lista de Tasas de Cambio actual por pares de mercancia** >> `Crypto Index` y `Fiat Index` 
    - Ahora hay que ver como se va a hacer con la nueva super capa CER

#### Contract History [I]
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
  - **(A) Agregar Locaciones** >> Settings de `Crypto Customer Wallet Module`
  - **(A) Modificar Locaciones** >> Settings de `Crypto Customer Wallet Module`
  - **(A) Eliminar Locaciones** >> Settings de `Crypto Customer Wallet Module`

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
