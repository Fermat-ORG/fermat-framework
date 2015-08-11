#Importar el Proyecto Local al IDE

Una vez hayamos clonado el Fork en un directorio local podemos usar nuestro IDE para abrir el Proyecto y programar nuestros aportes a **Fermat**.

**NOTA:** Indistintamente si nuestro IDE es *Android Developer Studio* o *IntelliJ Idea*, las opciones seran las mismas, aunque quizas las pantallas se vean un poco diferentes.

* Primero que nada, en la pantalla de inicio del IDE, escogemos la opcion **Import project**
![Import project][paso1]

* Acto seguido, ubicamos el directorio donde hemos clonado nuestro repositorio; dentro del mismo escogemos ya sea el *__build.gradle__* o *__settings.gradle__*, ya que para el IDE ambos representan la descripcion del Proyecto
![Seleccionar Proyecto][paso2]

* Una vez hecho esto, solo queda esperar a que la importacion se complete de forma exitosa. De formar opcional podemos escoger en el menu *__Build__*, la opcion *__Rebuild Project__* para verificar la compilacion exitosa de nuestro entorno.
![Importacion][paso3]

**NOTA:** Ya que removimos los archivos *__IML__* y el directorio *__.idea__* del repositorio central del proyecto, la opcion de **Checkout Project From Version Control** no es viable para realizar la Importacion del proyecto.

[paso1]: ./01.png
[paso2]: ./02.png
[paso3]: ./03.png
