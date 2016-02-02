## Flujos:

1. Crear Identidad de Broker y asociarla a la wallet:
Para crear la identidad se puede hacer desde dos sitios: La Sub App Broker Identity y en el wizard de la Broker Wallet con el dialog para eso
La Identidad se debe hacer Visible para que pueda ser seleccionada en la Broker Community Sub App 

Plugins: 
- Crypto Broker Reference Wallet (Franklin en los wizard)
- Crypto Broker Wallet Module (Franklin)
- Crypto Broker Identity Sub App (Angel)
- Crypto Broker Identity Module (Angel)
 
 
2. Configuracion inicial de Broker Wallet: 
En el wizard de la Broker Wallet realizar lo siguiente:

2.1. Asociar Wallets como mercancia
Plugins: 
- Crypto Broker Reference Wallet (Franklin en los wizard)
- Crypto Broker Wallet Module (Franklin)
- Crypto Broker Wallet (Franklin)
    
2.2. Asociar wallets como earnings
Plugins: 
- Crypto Broker Reference Wallet (Franklin en los wizard)
- Crypto Broker Wallet Module (Franklin)
- Matching Engine (Leon) [WARNING] 
    - Acaba de crear la interface con los metodos para los settings que se van a registrar en esta plugin. Necesitamos implementar esto ya.
    
2.3. Asociar proveedores para pares de mercancia
Plugins: 
- Crypto Broker Reference Wallet (Franklin en los wizard)
- Crypto Broker Wallet Module (Franklin)
- Crypto Broker Wallet (Franklin)
    
    
3. Rellenar de stock la broker wallet
Plugins: 
- Crypto Broker Reference Wallet (Franklin en los settings. Ya Guillermo habia implementado una pantalla para esto)
- Crypto Broker Wallet Module (Franklin)
- Plugins de Restock y Destock (Franklin)


4. Crear Identidad de Customer y asociarla a la wallet
Para crear la identidad se puede hacer desde dos sitios: La Sub App Customer Identity y en el wizard de la Customer Wallet con el dialog para eso

Plugins: 
- Crypto Customer Reference Wallet (Franklin en los wizard)
- Crypto Customer Wallet Module (Franklin)
- Crypto Customer Identity Sub App (Angel)
- Crypto Customer Identity Module (Angel)


5. Configuracion inicial de Customer Wallet
En el wizard de la Customer Wallet realizar lo siguiente:

5.1. Asociar Bitcoin Wallet a Customer Wallet
Plugins: 
- Crypto Customer Reference Wallet (Franklin en los wizard)
- Crypto Customer Wallet Module (Franklin)
    
5.2. Asociar proveedores para las mercancias que desee comprar
Plugins: 
- Crypto Customer Reference Wallet (Franklin en los wizard)
- Crypto Customer Wallet Module (Franklin)
    
5.3. agregar cuentas bancarias como setting
Plugins: 
- Crypto Customer Reference Wallet (Franklin en los wizard)
- Crypto Customer Wallet Module (Franklin)
- Customer Broker Purchase Negotiation (Angel)
    
5.4. agregar locaciones como setting
Plugins: 
- Crypto Customer Reference Wallet (Franklin en los wizard)
- Crypto Customer Wallet Module (Franklin)
- Customer Broker Purchase Negotiation (Angel)


6. Ver un Broker disponible y Enviarle solicitud de conexion
El customer entra en la broker community, selecciona un broker de la lista visible y le envia una sulicitud de conexion 
El Broker en la customer community recibe la notificacion de conexion de un custoner y la acepta
El broker y en customer quedan conectados.

Plugins: 
- Crypto Broker Community (Alejandro)
- Crypto Broker Community Module (Leon)
- Crypto Broker Actor Network Service (Leon)
