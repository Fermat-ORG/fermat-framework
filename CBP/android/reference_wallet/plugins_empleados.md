(A = Aprobado, ? = por determinar, I = implentado Android)

Notas:
- Los contratos no se cancelan (por ahora) y para evitar que se mantenga siempre abierto, se ha de colocar un estado EXPIRADO para indicar que el contrato paso las fechas para su ejecucion y asi filtrarlo de la lista de contratos abierto
- layer CER con inteligencia, al estipo de P2P (plugins de index ya no irian, porque al parecer ya no se usan)
- Settings de locaciones, metodos de pago y cuentas bancarias (practicamente los datos que pueden ser seleccionables en una negociacion) en Plugin de Customer Broker Sale y Purchase Negotiation ya que ellso tienen la responsabildiad de llevar la informacion de las negociaciones
- Wallet Plugin: setting de wallets asociadas para que el tenga toda la informacion que necesita para hacer sus debitos y creditos. El Stock de una mercaderia puede estar descompuesto en varias wallets
- Wallets que manejan ganancia en Plugin Matching Engine, ya que el tiene la responsabilidad de obtener las ganancias
- con respecto a los datos de la negociacion se llego a lo siguiente:
  - las fechas en las clausulas van a ser tipo long
  - Negotiation Expiration Date va ser un campo tipo long de `NegotiationInformation` no va a ser una clausula
  - Memo va a ser un campo tipo String (texto libre) de `NegotiationInformation` no va a ser una clausula
  - Cancel Reason va a ser un campo tipo String (texto libre) de `NegotiationInformation` no va a ser una clausula

### Crypto Broker Reference Wallet

#### Home Open Negotiations Tab [I]
  - **(A) Esperando por Broker** >> `Customer Broker Sale Negotiation`
  - **(A) Esperando por Customer** >> `Customer Broker Sale Negotiation`

#### Home Open Contracts Tab [I]
  - **(A) Esperando por Broker** >> `Customer Broker Sale Contract`
  - **(A) Esperando por Customer** >> `Customer Broker Sale Contract`

#### Negotiation Details
  - **(A) Agregar notas** >> `Customer Broker Sale Negotiation` 
    - Se registra en el campo MEMO
  - **(A) Locaciones del broker** >> `Customer Broker Sale Negotiation` 
    - Esto esta como un setting de `Customer Broker Sale Negotiation` 
    - Se envia al customer a traves del plugin `Customer Broker Update Negotiation Transmition` solo la opcion seleccionada por el
    - Se asigna esta lista cuando el Broker confirme que quiere aceptar Cash on Hand y Cash Delivery para recibir el pago
  - **(A) Cuentas bancarias del broker** >> `Customer Broker Sale Negotiation` 
    - Esto esta como un setting de `Customer Broker Sale Negotiation` y se envia al customer a traves del plugin `Customer Broker Update Negotiation Transmition`
    - Se asigna esta lista cuendo el Broker confirme que quiere aceptar Bank Transfer como pago
  - **(A) Locacion del customer** >> `Customer Broker Sale Negotiation` 
    - La asigna el customer en su wallet cuando confirme que quiere aceptar la mercancia como Cash
    - No va a ser editable por el broker
  - **(A) Cuenta bancaria del customer** >> `Customer Broker Sale Negotiation`
    - La asigna el customer en su wallet cuando confirme que quiere aceptar la mercancia como Bank Transfer
    - No va a ser editable por el broker
  - **(A) Monedas que acepta el broker como pago** >> `Customer Broker Sale Negotiation`
    - Se guarda en un xml ya que esto viene de la cotizacion que se obtiene de la lista de brokers
  - **(A) Precio de mercado** >> Plugin de super capa `Currency Exchage Rates`
    - Obtengo referencia del plugin Provider para ese par de mercancias desde los settings de `Crypto Broker Wallet` 
    - Obtengo el precio del mercado del plugin Provider 
  - **(A) Precio sugerido** >> `Crypto Broker Wallet`
  - **(A) Datos ingresados** >> `Customer Broker Sale Negotiation`
    - El plugin tiene un metodo que me devuelve las clausulas con los datos ingresados en el orden correcto, una clausula a la vez
    - Deberia existir un metodo que me devuelva todas esas clausulas ya ordenadas
  - **(A) Modificar Datos** >> `Customer Broker Sale Negotiation`
    - Me creo un wrapper para tener la data modificada y la actualice el plugin `Customer Broker Sale Negotiation`
  - **(A) Enviar Datos** >> `Customer Broker Update Negotiation Transmition`
    - Se envia el objeto `NegotaitonInformation` con la informacion de la negociacion
  - **(A) Cerrar Negociacion** >> `Customer Broker Close Negotiation Transmition`
    - Se envia el objeto `NegotaitonInformation` con la informacion de la negociacion
  - **(A) Cancelar Negociacion** >> `Customer Broker Update Negotiation Transmition`
    - Se cambia el estado de la negociacion a CANCELED usando `Customer Broker Sale Negotiation` 
    - se envia la informacion de la negociacion a `Customer Broker Update Negotiation Transmition` para que se encargue de actualizar la info en la wallet del customer
    - Se va a usar un campo CANCELED Reason (Texto libre) para indicar el porque de la cancelacion

#### Contract Details
  - **(A) Datos ingresados** >> `Customer BrokerSale Contract` usando `Customer Broker Sale Negotiation` para obtener el detalle de la negociacion y terminar de armar la data a mostrar
  - **(A) Referencia a la negociation** >> `Customer Broker Sale Contract`
  - **(A) Enviar Confirmacion de pago** >> `Broker Ack Offline Payment Business Transaction` o `Broker Ack Online Payment Business Transaction` dependiendo de la moneda a vender
    - En el caso de `Broker Ack Online Payment Business Transaction` el module no lo ejecuta directamente
    - Se envia el contractID
  - **(A) Enviar Mercancia** >> `Broker Offline Business Transaction` o `Broker Online Business Transaction` dependiendo de la moneda a vender
    - Se envia el contractID

#### Market Rates [I]
  - **(A) Lista de referencias a los proveedores** >> `Customer Broker Wallet`
  - **(A) Tasa de cambin actual** >> me la da el Plugin de cada proveedor referenciado en `Customer Broker Wallet`
  - **(A) Historico de Tasas de Cambio para un par de mercancias** >> me la da el Plugin de cada proveedor 

#### Stock Merchandise
  - **(A) Lista de mercancias seleccionadas como stock** >> `Crypto Broker Wallet`
  - **(A) Lista de Stock actual de mercancias** >> `Crypto Broker Wallet`
  - **(A) Historico de stock para una mercancia** >> `Crypto Broker Wallet` 
    - Debe darme info de inicio y fin del Stock para cada uno de los dias en ese historico

#### Contract History [I]
  - **(A) Lista de contratos cerrados y cancelados** >> `Customer Broker Sale Contract`
  - **(A) Metodo para filtrar contratos por estado** >> `Customer Broker Sale Contract`

#### Earnings
  - **(A) Lista de mercancias seleccionadas como ganancia** >> Settings `Matching Engine Middleware` 
  - **(A) Historico de ganancias para una mercancia** >> `Matching Engine Middleware`

#### Wizard Identity
  - **(A) Lista de Identidades** >> `Crypto Broker Identity`
  - **(A) Asociar Wallet con Identidad** >> `Crypto Broker Actor`

#### Wizard Merchandises
  - **(A) Plataformas (tipos de wallet: bank, cash o crypto)** >> Enum `Platforms`
  - **(A) Wallets Disponibles para plataforma seleccionada** >> `WPD Wallet Manager`
  - **(A) Asociar Wallet como stock** >> Settings de `Crypto Broker Wallet` 
    - Se va a registrar en una tabla que representa el setting de wallets asociadas
    - Con las wallets asociadas se conoce que mercancias se van a utilizar
    - Se debe guardar en el setting: 
      - la plataforma
      - el public_key de la wallet que voy a asociar
      - la mercaderia que maneja esa wallet. 
        - En el caso de una wallet bank se debe guardar tambien de que cuenta va a salir su stock (el stock sale de una unica cuenta)
  - **(A) Asociar Wallet como ganancia** >> Settings `Matching Engine Middleware`
    - Se debe guardar en el setting: 
      - la plataforma
      - el public_key de la wallet que voy a asociar
      - la mercaderia que maneja esa wallet.
  - **(A) Spread** >> Settings de `Crypto Broker Wallet`
    - este es un setting de valor unico y es un numero entre 0 y 1 (es un porcentaje)

#### Wizard Providers
  - **(A) Lista de Proveedores** >> la implementacion de la super capa CER
    - Los puedo mostrar todos sin importar que wallets he asociado. 
    - Deberia existir una interface o plugin que me perimta listarlos y obtener referencias a ellos
  - **(A) Asociar proveedores con la wallet** >> Settings de `Crypto Broker Wallet`
    - va a ser un setting con multiples valores
    - UUID del plugin, nombre descripyivo (esto es temporal, hasta que se confirme que va a ser asi)

#### Wizard Locations
  - **(A) Agregar Locaciones** >> Settings de `Customer Broker Sale Negotiation`
    - El setting va a ser un String con el texto de la ubicacion
    - URI que devuelve googlemaps para el texto ingresado como locacion. Esto va a ser un campo opcional
    - Es un setting con multiples registros

#### Wizard Bank Accounts
  - **(A) Seleccionar cuentas bancarias** >> Settings de `Customer Broker Sale Negotiation`
    - Depende de haber seleccionado una Bank Wallet como ganancia.
    - La informacion de las cuentas deberia venir de alli.
    - El `Crypto Broker Wallet Module` se encarga de registrar las cuentas seleccionadas por el usuario en el setting de `Customer Broker Sale Negotiation`
    - La info a guardar es un unico String con la informacion de la cuenta bancaria: numero de cuenta, banco, tipo cuenta, nombre del titular
    - Es un setting con multiples registros
  - **(A) Lista de cuentas bancarias** `Bank Money Wallet`
    - le paso la public_key de la wallet
   
#### Settings Merchandises
  NOTA: por ahora la public_key de la cbp wallet a de proveermela el WPD Wallet Manager, esto para hacer restock o destock, porque es uno de los parametros que necesitan esos plugins
  - **(A) Lista de Wallets asociadas** >> `Crypto Broker Wallet` (informacion registrada como setting)
  - **(A) Hacer Restock** >> `Crypto Money Restock` o `Bank Money Restock` o `Cash Money Restock` dendiendo de la wallet devuelta
  - **(A) Hacer Destock** >> `Crypto Money Restock` o `Bank Money Restock` o `Cash Money Restock`

#### Settings Locations
  - **(A) Lista de Locaciones actuales** >> `Customer Broker Sale Negotiation`
  - **(A) Agregar Locaciones** >> metodo save en `Customer Broker Sale Negotiation`
  - **(A) Modificar Locaciones** >> metodo save en `Customer Broker Sale Negotiation`
  - **(A) Eliminar Locaciones** >> metodo delete en `Customer Broker Sale Negotiation`

#### Settings Bank Accounts
  - **(A) Lista de wallets tipo bank que son ganancia** >> `Matching Engine`
    - Esta registrado como un setting de ese plugin
  - **(A) Seleccionar (agregar y eliminar) cuentas bancarias** >> Settings de `Customer Broker Sale Negotiation`
    - Depende de haber seleccionado una Bank Wallet como ganancia.
    - La informacion de las cuentas deberia venir de alli.
    - El `Crypto Broker Wallet Module` se encarga de registrar las cuentas seleccionadas por el usuario en el setting de `Customer Broker Sale Negotiation`
    - La info a guardar es un unico String con la informacion de la cuenta bancaria: numero de cuenta, banco, tipo cuenta, nombre del titular
    - Es un setting con multiples registros
  - **Lista de cuentas bancarias** `Bank Money Wallet`
    - le paso la public_key de la wallet


------------------------------------


-----------------------------------------------


### Crypto Customer Reference Wallet

#### Home Open Negotiations Tab [I]
  - **(A) Esperando por Broker** >> `Customer Broker Purchase Negotiation`
  - **(A) Esperando por Customer** >> `Customer Broker Purchase Negotiation`

#### Home Open Contracts Tab [I]
  - **(A) Esperando por Broker** >> `Customer Broker Purchase Contract`
  - **(A) Esperando por Customer** >> `Customer Broker Purchase Contract`

#### Negotiation Details
  - **(A) Agregar notas** >> `Customer Broker Purchase Negotiation` 
    - Se registra en el campo MEMO
  - **(A) Locacion del broker** >> `Customer Broker Purchase Negotiation` 
    - La asigna el broker en su wallet cuando confirme que quiere aceptar Cash on Hand y Cash Delivery como pago
    - No va a ser editable por el customer
  - **(A) Cuenta bancaria del broker** >> `Customer Broker Purchase Negotiation`
    - La asigna el broker en su wallet cuando confirme que quiere aceptar Bank Transfer como pago
    - No va a ser editable por el customer
  - **(A) Locaciones del customer** >> `Customer Broker Purchase Negotiation` 
    - Esto esta como un setting de `Customer Broker Purchase Negotiation` 
    - Se envia al broker a traves del plugin `Customer Broker Update Negotiation Transmition` solo la opcion seleccionada
    - Se muestra esta opcion si el broker ofrece la mercancia como Cash
  - **(A) Cuenta(s) bancaria(s) del customer** >> `Customer Broker Purchase Negotiation`
    - Esto esta como un setting de `Customer Broker Purchase Negotiation` 
    - Se envia al broker a traves del plugin `Customer Broker Update Negotiation Transmition` solo la opcion seleccionada
    - Se muestra esta opcion si el broker ofrece la mercancia por Bank Transfer
  - **(A) Monedas que acepta el broker como pago** >> `Crypto Customer Wallet Module`
    - Se guarda en un xml como cache en el Module de la Wallet ya que esto viene de la cotizacion que se obtiene de la lista de brokers
  - **(A) Precio de mercado** >> Plugin de super capa `Currency Exchage Rates`
    - Obtengo referencia del plugin Provider para ese par de mercancias desde los settings de `Crypto Customer Wallet Module`
    - Obtengo el precio del mercado del plugin Provider 
  - **(A) Datos ingresados** >> `Customer Broker Purchase Negotiation`
    - El plugin tiene un metodo que me devuelve las clausulas con los datos ingresados en el orden correcto, una clausula a la vez
  - **(?) Modificar Datos** >> `Customer Broker Purchase Negotiation`
    - me creo un wrapper para tener la data modificada y se actualice en `Customer Broker Purchase Negotiation`
  - **(A) Modificar Datos** >> `Customer Broker Purchase Negotiation`
    - Me creo un wrapper para tener la data modificada y la actualice el plugin `Customer Broker Purchase Negotiation`
  - **(A) Enviar Datos** >> `Customer Broker Update Negotiation Transmition`
    - Se envia el objeto `NegotaitonInformation` con la informacion de la negociacion
  - **(A) Cerrar Negociacion** >> `Customer Broker Close Negotiation Transmition`
    - Se envia el objeto `NegotaitonInformation` con la informacion de la negociacion
  - **(A) Cancelar Negociacion** >> `Customer Broker Update Negotiation Transmition`
    - Se cambia el estado de la negociacion a CANCELED usando `Customer Broker Purchase Negotiation` 
    - se envia la informacion de la negociacion a `Customer Broker Update Negotiation Transmition` para que se encargue de actualizar la info en la wallet del broker
    - Se va a usar un campo CANCELED Reason (Texto libre) para indicar el porque de la cancelacion

#### Contract Details
  - **Estado del contrato** >> `Customer Broker Purchase Contract`
    - este me devuelve un `ContractStatus` que me indica en que paso del contrato nos encontramos, y asi puedo tildar aquellos pasos que ya se procesaron, o sencillamente mostrar informacion resumida sobre el contrato en caso de que este haya sido completado o cancelado (este cancelado, hasta donde tengo entendido, se refiere a una negociacion que se cancela)
  - **(A) Datos ingresados** >> `Customer Broker Purchase Contract` usando `Customer Broker Purchase Negotiation` para obtener el detalle de la negociacion y terminar de armar la data a mostrar
  - **(A) Referencia a la negociation** >> `Customer Broker Purchase Contract`
  - **(A) Enviar Pago** >> `Customer Online Payment Business Transaction` o `Customer Offline Payment Business Transaction` dependiendo de la moneda de pago
  - **(A) Enviar Confirmacion de mercancia** >> `Customer Ack Offline Merchandise Business Transaction` o `Customer Ack Online Merchandise Transaction` dependiendo del tipo de mercancia
    - En el caso de `Customer Ack Online Merchandise Transaction` el module no lo ejecuta directamente

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
  - **Obtener formas en que se ofrece la mercancia (Bank, Cash, Crypto)** >> `Crypto Customer Module`
    - Me recorro la lista de brokers que me da `Crypto Broker Actor Connection`
    - Por cada Broker llamo a `Crypto Broker Actor` para que me devuelva las formas que tiene el broker para ofrecer la mercancia
    - Esto va a depender de las wallets que tiene asociadas un broker en su wallet para una mercancia

#### Start Negotiation
  - **(A) Mostrar Mercancia a comprar** >> `Crypto Customer Module`
    - Cuando se selecciona un broker de la lista de broker, automaticamente se selecciona la mercancia a comprar
  - **Como ofrece la mercancia el broker** >> `Crypto Customer Module`
    - Ya este valor se obtuvo de la lista lista de brokers y fue registrado en el module como cache en un xml
  - **(A) Mostrar Moneda de pago** >> `Crypto Customer Module`
    - Ya este valor se obtuvo de la lista lista de brokers y fue registrado en el module como cache en un xml
  - **(A) Mostrar Precio de Cotizacion** >> `Crypto Customer Module`
    - Ya este valor se obtuvo de la lista lista de brokers y fue registrado en el module como cache en un xml
  - **Mostrar Metodos de Pago** >> `Crypto Customer Module`
    - Ya este valor se obtuvo de la lista lista de brokers y fue registrado en el module como cache en un xml
  - **Locacion de customer** >> `Customer Broker Purchase Negotiation`
    - depende de si el broker ofrece la mercancia como cash
  - **Cuentas bancarias del customer** >> `Customer Broker Purchase Negotiation`
    - depende de si el broker ofrece la mercancia como bank
  - **(A) Crear Nueva negociacion** >> `Crypto Customer New Negotiation Transaction`
    - Debe permitirme registrar la siguiente data: Mercancia a comprar (Moneda), cantidad a comprar, como me ofrece la mercancia, moneda de pago, el modo de pago, la tasa de cambio a pagar, cuenta bancaria del customer, locacion del customer, publicKeyCustomer, publicKeyBroker
    - Me deberia devolver un registro `NegotiationInformation` con los datos de esta nueva negociacion

#### Market Rates [I]
  - **(A) Lista de Tasas de Cambio actual por pares de mercancia** >> se devuelven las referencias a los proveedores registrados en el Setting de `Crypto Customer Wallet Module`
    - Obtengo del plugin proveedor el precio del mercado

#### Contract History [I]
  - **(A) Lista de contratos cerrados y cancelados** >> `Customer Broker Purchase Contract`
  - **(A) Metodo para filtrar contratos por estado** >> `Customer Broker Purchase Contract`

#### Wizard Identity
  - **(A) Lista de Identidades** >> `Crypto Customer Identity`
  - **(A) Asociar Wallet con Identidad** >> `Crypto Customer Actor`

#### Wizard Merchandises
  - **(A) Wallets Disponibles para plataforma seleccionada** >> `WPD Wallet Manager`
    - Solo se van a mostrar wallets de tipo Crypto
  - **(?) Asociar Wallet** >> Settings de `Crypto Customer Wallet Module`
    - Se debe guardar en el setting la plataforma y el public_key de la wallet

#### Wizard Currencies of Interest
  - **(A) Lista de Currencies que soporta Fermat** >> Enums `CryptoCurrency` y `FiatCurrency`
  - **(A) Agregar Currency** >> Settings de `Crypto Customer Wallet Module`

#### Wizard Locations
  - **(A) Agregar Locaciones** >> Settings de `Customer Broker Purchase Negotiation`

#### Wizard Bank Accounts
  - **(A) Agregar cuentas bancarias** >>Settings de `Customer Broker Purchase Negotiation`

#### Settings
No usa plugins, es solo Android

#### Setting Currencies of Interest
  - **(A) Lista de Currencies configuradas** >> Settings de `Crypto Customer Wallet Module`
  - **(A) Lista de Currencies que soporta Fermat** >> Enums `CryptoCurrency` y `FiatCurrency`
  - **(A) Agregar Currency** >> Settings de `Crypto Customer Wallet Module`
  - **(A) Modificar Currency** >> Settings de `Crypto Customer Wallet Module`
  - **(A) Eliminar Currency** >> Settings de `Crypto Customer Wallet Module`

#### Setting Locations
  - **(A) Agregar Locaciones** >> Settings de `Customer Broker Purchase Negotiation`
  - **(A) Modificar Locaciones** >> Settings de `Customer Broker Purchase Negotiation`
  - **(A) Eliminar Locaciones** >> Settings de `Customer Broker Purchase Negotiation`

#### Setting Bank Accounts
  - **(A) Lista de Currencies configuradas** >> Settings de `Customer Broker Purchase Negotiation`
  - **(?) Agregar cuentas bancarias** >>Settings de `Customer Broker Purchase Negotiation`
  - **(A) Modificar cuentas bancarias** >> Settings de `Customer Broker Purchase Negotiation`
  - **(A) Eliminar cuentas bancarias** >> Settings de `Customer Broker Purchase Negotiation`
