![alt text](https://github.com/bitDubai/media-kit/blob/master/Readme%20Image/Fermat%20Logotype/Fermat_Logo_3D.png "Fermat
Logo")

<br><br>
## Introduction: Platform Chat
  Un plugin que agrega la funcionalidad de chat o mensajeria instantánea y que podrá ser integrada a cualquier actividad dentro de Fermat.<br>
  
  
## Sup App
  TransactionList: List of transactions in process.<br>
  MessagesList: List for voice and text messages access.<br>
  ChatInput: Input for text messaging.<br>
  ContactsList: List for manage contacts.<br>
  
## Module
Transaction: To view transactions in process.<br>
  - Methods:<br>
      getTransactionList(): to ask for all the transactions in process of the actual entity.  <br>
      getTransactionDetail(): to ask for details of the selected transaction. <br>

SendText: To send message.<br>
  - Methods:<br>
      sendTextMessage(): to send a text message from the actual entity to the other entity related to the selected transaction in process.  <br>

Messages: To view text messages or play voice messages.<br>
  - Methods:<br>
      getMessageList(): to ask for all the messages of the actual entity related to the selected transaction.  <br>
      getMessageDetail(): to ask for details of the selected message. i.e: voice recording, images, date/time, etc.<br>

Contacts: To add, modify, delete and view contacts.<br>
  - Methods:<br>
      getContactList(): to ask for all the contacts saved in the platform due to previous transactions with the actual entity.  <br>
      getContactDetail(): to ask for details of the selected contact. i.e: previous transactions between the actual entity and the contact entity, name, etc.<br>
  
## Middleware



Esto va a tener la siguiente base de datos:

### Base de Datos

#### Chats<br>
** Id Chat** <br>
** Id Objeto **<br>
** Local Actor Type **<br>
** Local Actor Pub Key **<br>
** Remote Actor Type **<br>
** Remote Actor Pub Key **<br>
** Chat Name **<br>
** Status ** : Invissible | Vissible<br>
** Creation Datetime **<br>
** Last Message Dateime **<br>

#### Mensajes<br>
** Id Mensaje**<br>
** Id Contacto **<br>
** Id Chat **<br>
** Message Text **<br>
** Status ** : Created | Sent | Delivered | Read<br>
** Type ** : Incomming | Outgoing<br>
** Timestamp **<br>

#### Contactos<br>
** Id Contacto**<br>
** Name **<br>
** Alias **<br>
** Timestamp **<br>

## Network Service
