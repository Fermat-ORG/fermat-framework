###USE CASES CHT V.2

**Pruebas realizadas por:**

<table style="width:100%">
  <tr>
    <td><b>Prueba Nº 1    Creación de Identidad</b></td>
    <td><b>Descripción</b></td> 
    <td><b>Resultados</b></td>
  </tr>
  <tr>
    <td>Crear una nueva identidad en el chat con el nombre e imagen del perfil</td>
    <td>
		<ol>
			<li>Presionar enlace de referido a identidades de chat para solicitar creación de identidad uicado en las identidades de fermat.</li>
			<li>Introducir nombre de usuario y de perfil en campos de texto.</li>
			<li>Presionar botón Create para creación de nueva identidad en el Chat.
		</ol>
	</td> 
    <td></td>
  </tr>
	<tr>
		<td><b>Prueba Nº 2   Notificaciones</b></td>
		<td><b>Descripción</b></td>
		<td><b>Restultados</b></td>
	</tr>
	<tr>
		<td>Verificar que las notificaciones se muestren en lista y recibir notificación cuando se ha sido agregado por otro contacto.</td>
		<td>
			<ol>
				<li>Presionar enlace referido a las notificaciones.</li>
				<li>Mostrar Lista de Notificaciones</li>
				<li>Mostrar en pantalla "Un contacto te ha agregado"</li>
			</ol>
		</td>
		<td></td>
	</tr>
	<tr>
		<td><b>Prueba Nº 3   Lista de Conexiones</b></td>
		<td><b>Descripción</b></td>
		<td><b>Resultados</b></td>
	</tr>
	<tr>
		<td>El usuario podrá solicitar la lista de conexiones disponibles de chat.</td>
		<td>
			<ol>
				<li>Botón "Mostrar Conexiones"</li>
				<li>Mostrar lista de conexiones</li>
			</ol>
		</td>
		<td></td>
	</tr>
	</tr>
	<tr>
		<td><b>Pruaba Nº 4   Agregar Contacto</b></td>
		<td><b>Descripción</b></td>
		<td><b>Resultados</b></td>
	</tr>
	<tr>
		<td>Al momento de agregar un contacto, debe salir un estado de "esperando aprobación", en caso de que el contacto haya aceptado la solicitud, debe iniciar el chat sin problemas, caso contrario, se borrar el contacto y la conexión con el mismo.</td>
		<td>
			<ol>
				<li>Agregar conexión como contacto</li>
				<li>Aparecer en pantalla "Esperando Aprobación"</li>
				<li>Mandar Solicitud</li>
				<li>Mostrar estado "Aprobado"</li>
				<li>Iniciat chat en caso de tener estado "Aprobado"</li>
				<li>Mostrar estado "Denegado"</li>
				<li>Borrar contacto en caso de tener estado "Denegado"</li>
			</ol>
		</td>
		<td></td>
	</tr>
	<tr>
		<td><b>Pruaba Nº 5   Comunidad</b></td>
		<td><b>Descripción</b></td>
		<td><b>Resultados</b></td>
	</tr>
	<tr>
		<td>En el momento en que se abra la comunidad, debe aparecer una lista de contactos con quien podamos iniciar chat en pantalla</td>
		<td>
			<ol>
				<li>Presionar en el enlace para la lista de contactos</li>
				<li>Mostrar lista de identidades</li>
			</ol>
		</td>
		<td></td>
	</tr>
	<tr>
		<td><b>Pruaba Nº 6   Difusión</b></td>
		<td><b>Descripción</b></td>
		<td><b>Resultados</b></td>
	</tr>
	<tr>
		<td>La prueba de broadcasting es para mandar mensajes a múltiples contactos, pero escribiendo en un solo chat.</td>
		<td>
			<ol>
				<li>Salir la opción "Broadcast" en el menú</li>
				<li>Salir lista de selección de múltiples contactos</li>
				<li>Abrir conexión de un chat con múltiples contactos</li>
				<li>Recibir mensae en un solo chat con múltiples contactos</li>
			</ol>
		</td>
		<td></td>
	</tr>
	<tr>
		<td><b>Pruaba Nº 7   Difusión Grupal</b></td>
		<td><b>Descripción</b></td>
		<td><b>Resultados</b></td>
	</tr>
	<tr>
		<td>La prueba de Group Broadcasting es para mandar mensajes a multiples contactos y con mensaje programado para llegar en una fecha en específico. Abrir pantalla en un chat por contacto, es decir, debe llegar el mensaje de manera personal, no en un chat grupal.</td>
		<td>
			<ol>
				<li>Opción "Group Broadcasting"</li>
				<li>Programación de mensaje: día, fecha y hora.</li>
				<li>Selección de múltiples contactos</li>
				<li>Recepción de mensaje, un chat por contacto</li>
			</ol>
		</td>
		<td></td>
	</tr>
	<tr>
		<td><b>Pruaba Nº 8   Borrar contacto</b></td>
		<td><b>Descripción</b></td>
		<td><b>Resultados</b></td>
	</tr>
	<tr>
		<td>Prueba para borrar contacto de chat</td>
		<td>
			<ol>
				<li>Borrar Contacto</li>
				<li>Mensaje de Confirmación</li>
				<li>Actualización de lista de contactos</li>
			</ol>
		</td>
		<td></td>
	</tr>
	<tr>
		<td><b>Pruaba Nº 9   Bloquear contacto</b></td>
		<td><b>Descripción</b></td>
		<td><b>Resultados</b></td>
	</tr>
	<tr>
		<td>Prueba para bloquear contacto de chat</td>
		<td>
			<ol>
				<li>Bloquear contacto</li>
				<li>Mensaje de confirmación</li>
				<li>Actualización de lista de contactos</li>
			</ol>
		</td>
		<td></td>
	</tr>
	<tr>
		<td><b>Pruaba Nº 10   Borrar Chat</b></td>
		<td><b>Descripción</b></td>
		<td><b>Resultados</b></td>
	</tr>
	<tr>
		<td>Prueba para borrar los chats de la base de datos.</td>
		<td>
			<ol>
				<li>Borrar Chats</li>
				<li>Mostrar mensaje de confirmación</li>
				<li>Actualización de pantalla con chat borrados</li>
			</ol>
		</td>
		<td></td>
	</tr>
	<tr>
		<td><b>Pruaba Nº 11   Borrar todos los chats</b></td>
		<td><b>Descripción</b></td>
		<td><b>Resultados</b></td>
	</tr>
	<tr>
		<td>Prueba para borrar todos los chats de la base de datos.</td>
		<td>
			<ol>
				<li>Borrar Chats</li>
				<li>Mensaje de confirmación para borrar mensajes</li>
				<li>Actualización de pantalla</li>
			</ol>
		</td>
		<td></td>
	</tr>
	<tr>
		<td><b>Pruaba Nº 12   Limpiar Chat</b></td>
		<td><b>Descripción</b></td>
		<td><b>Resultados</b></td>
	</tr>
	<tr>
		<td>Limpiar los mensajes dentro de un chat.</td>
		<td>
			<ol>
				<li>Botón "Clean Chat"</li>
				<li>Botón "Ok"</li>
				<li>Mensaje de confirmación</li>
				<li>Chat actualizado</li>
			</ol>
		</td>
		<td></td>
	</tr>
	<tr>
		<td><b>Pruaba Nº 13   Enviar Chat por e-mail</b></td>
		<td><b>Descripción</b></td>
		<td><b>Resultados</b></td>
	</tr>
	<tr>
		<td>Prueba para mandar un chat vía correo electrónico.</td>
		<td>
			<ol>
				<li>Botón "Send chat by e-mail"</li>
				<li>Botón "ok"</li>
				<li>Mensaje de confirmación</li>
				<li>Enviar contenido via e-mail</li>
				<li>Pantalla de actualización</li>
			</ol>
		</td>
		<td></td>
	</tr>
	<tr>
		<td><b>Pruaba Nº 14   Paginación de Mensajes</b></td>
		<td><b>Descripción</b></td>
		<td><b>Resultados</b></td>
	</tr>
	<tr>
		<td>Pruab para paginación de mensajes desde el mensaje más antiguo.</td>
		<td>
			<ol>
				<li>Scroll deslizable en los mensaes</li>
				<li>Mostrar mensaje antiguo</li>
			</ol>
		</td>
		<td></td>
	</tr>
	<tr>
		<td><b>Pruaba Nº 15   Mostrar cuando el contacto esté escribiendo</b></td>
		<td><b>Descripción</b></td>
		<td><b>Resultados</b></td>
	</tr>
	<tr>
		<td>En esta prueba se debe mostrar cuando un contacto esté escribiendo en tiempo real.</td>
		<td>
			<ol>
				<li>Mostrar leyenda "is writing..." mientras el contacto escribe</li>
				<li>Quitar leyenda cuando el contacto no esté escribiendo</li>
			</ol>
		</td>
		<td></td>
	</tr>
	<tr>
		<td><b>Pruaba Nº 16   Mostrar cuando el contacto esté en línea</b></td>
		<td><b>Descripción</b></td>
		<td><b>Resultados</b></td>
	</tr>
	<tr>
		<td>En esta prueba se debe mostrar el estado de un contacto, si se encuentra en línea o no.</td>
		<td>
			<ol>
				<li>Punto verde cuando se encuentra conectado</li>
				<li>Punto gris cuando el contacto no esté conectado</li>
			</ol>
		</td>
		<td></td>
	</tr>		
</table>
