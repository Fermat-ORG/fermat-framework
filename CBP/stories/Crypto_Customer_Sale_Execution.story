Historia: Crypto Customer Sale Execution

Como deseo poder finalizar la compra establecida en la negociación con un Crypto Customer
 Como un Crypto Broker

Utilizando el plugin Receive Crypto
  Y el plugin Receive Cash On Hand
  Y el plugin Receive Cash Delivery
  Y el plugin Receive Offline Bank Deposit
  Y el plugin Send Crypto
  Y el plugin Give Cash On Hand
  Y el plugin Send Cash Delivery
  Y el plugin Make Offline Bank Deposit
  Y el plugin Crypto Customer Crypto Sale
  Y el plugin Crypto Customer Cash Sale
  Y el plugin Crypto Customer Bank Sale
  Y el plugin Wallet Crypto Broker
  Y el plugin Wallet Cash
  Y el plugin Wallet Bank

Escenario: Crypto Broker recibe Crypto currency del Crypto Customer.
  Dado que ya se cerro una negociacion con el Crypto Customer  
    Y se establecio que el modo de pago va a ser Crypto Currency
    Y el Crypto Customer envio la Crypto Currency que se acepto como pago 
  Cuando la Crypto Broker Wallet reciba el pago
  Entonces la Crypto Broker Wallet debe registrar el pago usando el plugin Receive Crypto
    Y la Crypto Broker Wallet debe actualizar el balance usando el plugin Wallet Crypto Broker
    Y la Crypto Broker Wallet recibe la notificacion de recepcion de pago en Crypto Currency
    Y la Crypto Broker Wallet actualiza el "Sale Negotiation Contract" como RECIBIDO
    Y se registra esta actualizacion en el Log

Escenario: Crypto Broker envio Crypto currency del Crypto Customer.
  Dado que ya se cerro una negociacion con el Crypto Customer  
    Y se establecio que la mercaderia a entregar va a ser Crypto Currency 
  Cuando el Crypto Broker envia la mercaderia a travez de la Crypto Broker Wallet 
  Entonces la Crypto Broker Wallet debe registrar el envio usando el plugin Send Crypto
    Y la Crypto Broker Wallet debe actualizar el balance usando el plugin Wallet Crypto Broker
    Y la Crypto Broker Wallet actualiza el "Sale Negotiation Contract" como ENTREGADO
    Y se registra esta actualizacion en el Log


Escenario: Crypto Broker recibe pago Offline del Crypto Customer.
  Dado que ya se cerro una negociacion con el Crypto Customer  
    Y se establecio que el modo de pago va a ser Offline
    Y se notifico al Crypto Broker que el pago se a de realizar a travez de "Tipo Pago Offline" 
  Cuando el Crypto Broker procede a ingresar la informacion del "Tipo Pago Offline" en la Crypto Broker Wallet
  Entonces la Crypto Broker Wallet debe registrar el pago usando el "Plugin de Recepcion Offline"
    Y la Crypto Broker Wallet debe actualizar el balance usando el "Crypto Wallet Transaction Offline"
    Y la Crypto Broker Wallet actualiza el "Sale Negotiation Contract" como RECIBIDO
    Y se registra esta actualizacion en el Log
Permutacion:
|Tipo Pago Offline     |Plugin de Recepcion Offline   |Crypto Wallet Transaction Offline
|Cash On Hand          |Receive Cash On Hand          |Cash
|Cash Delivery         |Receive Cash Delivery         |Bank
|Offline Bank Deposit  |Receive Offline Bank Deposit  |

Escenario: Crypto Broker envio de mercancia a travez de Cash on Hand o Cash Delivery                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                currency del Crypto Customer.
  Dado que ya se cerro una negociacion con el Crypto Customer  
    Y se establecio que la mercaderia se va a entregar por Cash on Hand o Cash Delivery
    Y el Crypto Broker hace entrego la mercaderia
  Cuando Crypto Broker procede a registrar el envio de la mercaderia
  Entonces la Crypto Broker Wallet debe registrar el envio usando el "Plugin de Envio Cash"
    Y la Crypto Broker Wallet debe actualizar el balance usando el plugin Wallet Cash
    Y la Crypto Broker Wallet actualiza el "Sale Negotiation Contract" como ENTREGADO
    Y se registra esta actualizacion en el Log
|Plugin de Envio Cash   |
|Give Cash On Hand      |
|Send Cash Delivery     |

Escenario: Crypto Broker envio mercaderia a travez de Bank                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          currency del Crypto Customer.
  Dado que ya se cerro una negociacion con el Crypto Customer  
    Y se establecio que la mercaderia se va a entregar por Bank 
  Cuando el Crypto Broker registra que realizo el envio por Bank
  Entonces la Crypto Broker Wallet debe registrar el envio usando el plugin Make Offline Bank Deposit
    Y la Crypto Broker Wallet debe actualizar el balance usando el plugin Wallet Bank
    Y la Crypto Broker Wallet actualiza el "Sale Negotiation Contract" como ENTREGADO
    Y se registra esta actualizacion en el Log
    Y la Crypto Broker Wallet envia la notificacion con la informacion del envio de la mercaderia a traves del plugin Crypto Customer Network Service
