# Git Management
Para poder entender mejor el contenido, favor de leer el siguiente documento: [Contributing](https://github.com/bitDubai/fermat/CONTRIBUTING.md)

A la hora de contribuir a través de git identificamos dos roles, uno es el que compromete sus cambios, mientras que el otro es el responsable de mezclar los cambios comprometidos.
A partir de ahora los llamaremos Developer y Responsable respectivamente.

Actualmente tenemos una estructura jerárquica de varios niveles sobre la cuál estamos trabajando para gestionar las contribuciones:
* **Fermat Core Developers**: [Responsable | Developer] Son los únicos que pueden enviar/aprobar Pull Requests al repositorio *fermat* de _bitDubai_; ellos se encuentran identificados en .-
* **Fermat Team Leaders**: [Responsable | Developer] Gestionan equipos revisando y aprobando los Pull Request de los _Developers_ u otros _Team Leaders_ que dependan de ellos y envían Pull Requests a los Core Developers para que lleguen a _bitDubai_.
* **Fermat Developers**: [Developer] Sólo pueden realizar Pull Requests hacia el Responsable (Team Leader o Core Developer) del cuál dependan.

Entendiendo que de leer este documento ya tenéis visibilidad sobre el repositorio, ambos Developer y Responsable debéis también tener un fork del repositorio y una copia local del mismo.

#### Forkeando el repositorio Fermat
Desde GitHub, luego de acceder al repositorio Fermat ([Link Repo](https://github.com/bitDubai/fermat)), debéis dirigirse al sector superior izquierdo y dar click sobre el botón **Fork**, saldrá un pop-up preguntando "`Where should we fork this repository?`", aquí debéis seleccionar su usuario.

#### Realizando una copia local de vuestro Fork
Sobreentendemos entonces que leído el artículo Contributing ya tenéis el cliente git instalado en su ordenador.
Para realizar la copia local de vuestro fork debéis dirigirse al directorio donde quieráis descargarlo y ejecutar el comando ` git clone https://github.com/$YOUR_USER/fermat `

El proceso tardará unos minutos y tendréis una copia local de todos los fuentes del fork.

## Rol Developer
Un Developer podrá realizar cambios y comprometerlos, pero solo podrá mantener la línea git con el Responsable del cual dependa.
Es fundamental que antes de realizar los cambios el Developer actualice su copia local con la rama `develop` de su Responsable.
También podrá comprometer cambios y realizar un pull request a su Responsable.

#### Actualizando vuestra copia local
La forma más sencilla de actualizar vuestro fork es a través de consola, con el **cliente git**.
Siempre debéis trabajar sobre la rama develop, razón por la cuál deberías crear una rama develop local y agregar una branch remota de su Responsable.
##### Creando rama develop en la copia local
Para crear una rama develop en nuestra copia local, debéis ejecutar el comando:
```bash
git checkout -b develop
```
##### Agregando branch remota
Para agregar una branch remota, deberán ejecutar el comando:
```bash
git remote add responsible https://github.com/$RESPONSIBLE_USER/fermat 
```
Para corroborar que se encuentre todo en correctas condiciones, ejecuten el comando ` git remote -v `
Vueltra consola deberá mostrarles una salida similar a esta:
```js
origin https://github.com/$YOUR_USER/fermat (fetch)
origin https://github.com/$YOUR_USER/fermat (push)
responsible https://github.com/$RESPONSIBLE_USER/fermat (fetch)
responsible https://github.com/$RESPONSIBLE_USER/fermat (push)
```
##### Jalar cambios del Responsable
Para traeros las últimas actualizaciones de vuestro Responsable, primero dirigíos a su rama develop (que ya deberíais tener creada) a través del comando:
```bash
git checkout develop
```
Para actualizar ahora la misma deberíais ejecutar la siguiente sentencia:
```bash
git pull responsible develop 
```

#### Comprometer vuestros cambios
A la hora de comprometer nuestros cambios, siempre ponemos un mensaje indicando cuáles fueron, para poder identificar rápidamente los commits. Corroboramos **SIEMPRE** cuáles son aquellos cambios que hicimos, para así no mandar cosas de más o de menos.
Recomendamos siempre comprometer cambios a través de las integraciones con git de los IDE IntelliJ o Android Studio y realizarlos cada 30 minutos/1 hora para no perder los cambios locales.
##### Consola
Para comprometer cambios a través de consola se usan los comandos:
```bash
git commit -m"Mensaje del Commit"
```
##### IDE
Para comprometer cambios a través del IDE, debeís dirigirse al menú **VCS -> Show Changes View**, al darle click, se desplegará un menú con cuatro tabs, una de ellas dice **Local Changes**.
Aquí se listarán todos los cambios realizados luego del último commit. Podráis seleccionar uno o más archivos e ir haciendo commits parciales o totales a través de:
**Click derecho -> Git -> Commit Changes**.

#### Actualizar Vuestro Fork
Para actualizar vuestro Fork debéis primero actualizarse su copia local con los cambios de su Responsable, realizando nuevamente un pull (explicado unos puntos más arriba).
Luego debiérais realizar un push a vuestro fork:
```bash
git push origin develop
```

#### Realizar un Pull Request a vuestro Responsable
Un Pull Request es un pedido de "jalación" en dónde le solicitamos a nuestro Responsable que se lleve los últimos cambios que realizamos.
La forma más sencilla y visual de realizar esto es a través de GitHub.
Para ello, dirigíos a su Fork en GitHub; sobre el margen derecho verán una opción que dice **Pull Request**, ingresad.
Desde allí se verá en Verde grande un botón que dice **New Pull Request**
La página os mostrará en pantalla una serie de menú desplegables en donde indica:
* ¿A quién deseáis realizar el Pull Request?, ¿A qué rama?
* ¿Desde dónde deseáis realizar el Pull Request?, ¿A qué rama?

Aseguráos elegir el fork de vuestro Responsable, la rama develop en los primeros y vuestro fork y la rama develop en los segundos.
Completad los campos indicando el motivo del Pull Requests y cuáles fueron las modificaciones realizadas.
Confirmad el Pull Request haciendo click sobre el botón **Crear Pull Request** y aquí termina vuestra responsabilidad.

## Rol Responsable
Como Responsable podéis estar en los dos roles al mismo tiempo, como Developer y como Responsable, pero vuestras responsabilidades son bastante mayores.
Debéis aprobar los Pull Requests de los Developers que dependan de vosotros.
Para aprobar los Pull Requests tenéis que seguir religiosamente una serie de pasos que os dan mayor seguridad:

#### Revisión de cambios
* Si los Pull Requests son pequeños, siempre es bueno revisar que archivos fueron modificados, y por arriba, cuales son las modificaciones.
* Si los Pull Requests son grandes, revisar los nombres de los archivos teniendo en cuenta de que no hayan modificado cosas que no debiesen ser modificadas o que no se encuentren descriptas en el Pull Request.
* En caso de encontrar alguna irregularidad o conflictos informar al Developer y/o cancelar el Pull Request y pedir que actualicen el mismo.

#### Crear una rama local para realizar la mezcla
Crearáis una rama para realizar la mezcla con la ejecución del comando:
```bash
git checkout -b mezcla$nombreDeveloper
```

#### Jalar los cambios del Developer
Podráis jalar los cambios a través del comando:
```bash
git pull https://github.com/$nombreDeveloper/fermat.git develop
```

#### Compilar y Correr el proyecto en el IDE
Es en verdad justo y necesario realizar una prueba sobre el contenido del Pull Request, testeando las nuevas funcionalidades o cambios que indica poseer.

#### Aprobar la mezcla
Una vez decidida la aprobación del Pull Request, para hacerla efectivo debéis realizar los siguientes pasos:
##### Volver a vuestra rama develop
```bash
git checkout develop
```
##### Realizar el merge
```bash
git merge --no-ff mezcla$nombreDeveloper
```
##### Actualizar vuestro Fork
```bash
git push origin develop
```
##### Borrar rama temporal para mezcla
```bash
git branch -d mezcla$nombreDeveloper
```

