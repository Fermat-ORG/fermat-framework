Historia: Crypto Broker Buy Execution

Como deseo poder finalizar la compra establecida en la negociación con un Crypto Broker
 Como un Crypto Customer

Utilizando la Crypto Broker Wallet SubApp 
  Y el plugin Receive Crypto Crypto Transaction 
  Y el plugin Send Crypto Crypto Transaction
  Y el plugin Give Cash On Hand Fiat Cash Transaction
  Y el plugin Send Cash Delivery Fiat Cash Transaction
  Y el plugin Crypto Broker Market Crypto Wallet
  Y el plugin Crypto Broker Fiat Cash Wallet
  Y el plugin Crypto Customer Market Money Sale
  Y el plugin Crypto Customer Fiat Money Sale
  Y el plugin Receive Fiat Cash On Hand Fiat Cash Transaction
  Y el plugin Receive Fiat Cash Delivery Fiat Cash Transaction
  Y el plugin Receive Offline Fiat Bank Transfer Fiat Bank Transaction


Escenario: Crypto Customer recibe Market Crypto del Crypto Broker.
  Dado que ya se cerro una negociacion con el Crypto Broker
    Y se establecio que la mercancia es Market Crypto
    Y el Crypto Broker envio la mercancia
  Cuando el Receive Crypto Crypto Transaction registre una transferencia a la direccion de la operacion
  Entonces Receive Crypto Crypto Transaction debe notificar a la "Crypto Customer Wallet" de la transaccion
    Y la Crypto Customer Market Crypto Wallet debe registrar la transaccion como un credito
    Y la Crypto Csutomer Market Crypto Wallet debe actualizar su balance
    Y el "Buy Negotiation Contract" debe recibir la notificacion de la transaccion
    Y el "Buy Negotiation Contract" verifica que el monto de la transaccion sea el monto acordado
    Y el "Buy Negotation Contract" actualiza su estado como COMPLETADO
    Y se registra esta actualizacion en el Log Transaccional del "Buy Negotiation Contract"
Contratos:
|!Buy Negotation Contract        |
|Crypto Broker Market Money Buy  |
|Crypto Broker Fiat Money Buy    |

Escenario: Crypto Customer envia el Market Crypto al Crypto Broker.
  Dado que ya se cerro una negociacion con el Crypto Broker
    Y se establecio que el pago va a ser Market Crypto
  Cuando el Crypto Customer envia el pago a traves de la Crypto Customer Wallet
  Entonces la Crypto Customer Wallet debe registrar el envio usando el plugin Send Crypto Crypto Transaction
    Y la Crypto Customer Market Crypto Wallet debe debitar el monto del balance
    Y la Crypto Customer Wallet actualiza el "Buy Negotiation Contract" como PENDIENTE_ENTREGA
    Y se registra esta actualizacion en el Log Transaccional del "Buy Negotiation Contract"

Escenario: Crypto Customer recibe Fiat Cash del Crypto Broker
  Dado que ya se cerro una negociacion con el Crypto Customer
    Y se establecio que el modo de pago va a ser Fiat Cash
    Y se especificaron las condiciones del "Tipo de Pago"
  Cuando el Crypto Broker procede a ingresar la informacion del pago en la Crypto Broker Wallet
  Entonces la Crypto Broker Wallet debe registrar el pago usando una "Fiat Cash Transaction"
    Y la "Fiat Cash Transaction" debe registrar un credito en la Crypto Broker Fiat Cash Wallet
    Y la Crypto Broker Wallet actualiza el estado  del "Sale Negotiation Contract" como PENDIENTE_ENTREGA
    Y se registra esta actualizacion en el Log Transaccional del "Sale Negotiation Contract"
Permutacion:
|!Tipo Pago             |!Fiat Cash Transaction                             |
|Cash On Hand           |Receive Fiat Cash On Hand Fiat Cash Transaction    |
|Cash Delivery          |Receive Fiat Cash Delivery Fiat Cash Transaction   |

Escenario: Crypto Broker recibe pago utilizando Fiat Bank del Crypto Customer
  Dado que ya se cerro una negociacion con el Crypto Customer
    Y se establecio que el modo de pago va a ser Fiat Bank
  Cuando el Crypto Broker procede a ingresar la informacion del pago en la Crypto Broker Wallet
  Entonces la Crypto Broker Wallet debe registrar el pago a traves del plugin Receive Offline Fiat Bank Transfer Fiat Bank Transaction
    Y la Receive Offline Fiat Bank Transfer Fiat Bank Transaction debe registrar un credito en la Crypto Broker Fiat Cash Wallet
    Y el "Sale Negotiation Contract" actualiza su estado como PENDIENTE_ENTREGA
    Y se registra esta actualizacion en el Log Transaccional del "Sale Negotiation Contract"

Escenario: Crypto Broker envio de mercancia Fiat Cash                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 currency del Crypto Customer.
  Dado que ya se cerro una negociacion con el Crypto Customer
    Y se establecio que la mercaderia se va a entregar por Cash on Hand o Cash Delivery
    Y hago entrega de la mercaderia
  Cuando procedo a registrar el envio de la mercaderia en la Crypto Broker Wallet
  Entonces la Crypto Broker Wallet debe registrar el envio usando el "Plugin de Envio Cash"
    Y el "Plugin de Envio Cash" debe registrar un debito en la Crypto Broker Fiat Cash Wallet
    Y el "Sale Negotiation Contract" actualiza su estado como COMPLETADO
    Y se registra esta actualizacion en el Log Transaccional del "Sale Negotiation Contract"
|!Plugin de Envio Cash                    |
|Give Cash On Hand Fiat Cash Transaction  |
|Send Cash Delivery Fiat Cash Transaction |

Escenario: Crypto Broker envio mercaderia a Fiat Bank                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          currency del Crypto Customer.
  Dado que ya se cerro una negociacion con el Crypto Customer
    Y se establecio que la mercaderia se va a entregar por Bank Deposit
  Cuando procedo a registrar el Bank Deposit en la Crypto Broker Wallet
  Entonces la Crypto Broker Wallet debe registrar el envio usando el plugin Make Offline Bank Transfer Fiat Bank Transaction
    Y la Make Offline Bank Transfer Fiat Bank Transaction debe realizar un debito en la cuenta registrada en el Crypto Broker Fiat Bank Wallet
    Y el "Sale Negotiation Contract" actualiza su estado como COMPLETADO
    Y se registra esta actualizacion en el Log Transaccional del "Sale Negotiation Contract"
    Y la Crypto Broker Wallet envia la notificacion con la informacion del envio de la mercaderia a traves del plugin Crypto Customer Network Service
