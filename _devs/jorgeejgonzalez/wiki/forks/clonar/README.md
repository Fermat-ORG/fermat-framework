#Creacion y Clonacion de un Fork de Fermat

* Para crear un fork del repositorio principal de **Fermat** debemos dirigirnos a la pagina Github principal [https://github.com/bitDubai/fermat](https://github.com/bitDubai/fermat)
* Una vez alli, ubicamos el boton de *__fork__* en la esquina superior derecha
![Fork][paso1]
* Esto nos levantara una ventana en la cual debemos escoger el usuario Github en el que queremos crear el Fork del proyecto
![Usuario][paso2]
* El proceso de Clonacion puede tardar un poco en procesarse pero eventualmente veremos una copia del repositorio en nuestro propio fork
![Esperar][paso3]
![Listo][paso4]
* Cuando el fork este listo, podemos ubicar debajo del menu lateral derecho la direccion Clonacion la cual debemos copiar para utilizar desde la consola
![URL][paso5]
* El siguiente paso es utilizar la consola de Linux para realizar la Clonacion del Fork en nuestro computador; el proceso lo ejecutamos utilizando el comando
```bash
git clone URL_QUE_COPIAMOS
```
* Al ser **Fermat** un proyecto privado, el repositorio Github nos solicitara autenticarnos con nuestro usuario y password
![Autenticacion][paso6]
* Un paso opcional es que una vez se haya clonado el repositorio local asignar permisos de propiedad al usuario Linux con el que trabajemos para evitar tener que utilizar el comando *sudo* para interactuar con el mismo.
```bash
chown -R miusuarioenlinux fermat/
```

**NOTA:** Recuerda que por defecto el proyecto se clonara en un directorio llamado *__fermat__* dentro del directorio donde hayamos ejecutado *git clone*.

[paso1]: ./01.png
[paso2]: ./02.png
[paso3]: ./03.png
[paso4]: ./04.png
[paso5]: ./05.png
[paso6]: ./06.png
