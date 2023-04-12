# Front-end

## Descripción

El front-end se ha realizado con el lenguaje de programación *Javascript*, un lenguaje interpretado que se usa en la mayoría de proyectos en el mundo de la programación y que se caracteriza entre otras cosas, por servir de scripting en programación web. En concreto se ha utilizado el framework *VueJS* (configurándolo con el instalador de paquetes *yarn*), cuyas funciones principales son el renderizado y el sistema de componentes. Además, se le pueden añadir muchas funcionalidades añadiendo bibliotecas externas. Se ha utilizado porque la curva de aprendizaje de este es poco pronunciada y se puede llevar a cabo una buena estructura para la visualización debido al desglose que permite en componentes.

Cabe destacar que se ha usado la librería *Vuetify*, la cual permite usar componentes basados en *“Material Design”* para de esta manera, facilitar la creación de la interfaz gráfica. Y se ha utilizado para crear mapas interactivos, *Leaflet* , una biblioteca de código abierto que es utilizada para crear mapas web con posibilidad de interactividad con estos.

Por otro lado, se ha usado la librería *Vue I18n* para la realización de la internacionalización del aplicativo, haciendo que este disponible en los idiomas español e inglés. Y para hacer posible el enrutamiento de la aplicación se ha usado la librería *Vue Router*.

La estructura de directorios del front-end es una estructura bastante ordenada por partes, creada en su totalidad por el framework *VueJS*, y que permite al desarrollador tener todos sus archivos organizados.

En cuanto al análisis de la estructura, en lo que refiere a los archivos que se encuentran en el directorio raíz, hay numerosos archivos de configuración, como por ejemplo el *“vue.config.js”*, que sirve para la configuración de *VueJS*. O *“nginx.conf”* y *“jsconfig.json”*, que sirven para el despliegue y para la configuración de Node.js, respectivamente, entre otros. Es importante tener en cuenta que *“App.vue”*, es el archivo donde empieza a crearse la aplicación Vue.

Respecto a los directorios, se puede encontrar la carpeta *“node modules”*, en la cual se almacenan todos los paquetes o dependencias necesarios para la ejecución del front-end. También está la carpeta *“public”*, donde se almacenan los ficheros estáticos que no serán procesados por el framework, como el *“index.html"* y el *“favicon.ico”*.

Y por otro lado, está el directorio más importante y más complejo internamente, ya que en este se almacena el código fuente del proyecto Vue. Este es el *“src”* y dentro se pueden encontrar directorios como *“assets”*, donde se encuentran archivos estáticos como imágenes, vídeos, etc. O como *“plugins”*, en el cual, como indica su nombre, se almacenarán los ficheros relacionados con plugins, en el caso del aplicativo sólo se encuentra el archivo *“vuetify.js”*. Además, se encuentra el directorio *“router”*, que está relacionado con la librería Vue Router y el directorio *“locales”* que está relacionado con la librería *Vue I18n*.

Dentro de *“src”*, también está el directorio *“services”*, el cual contiene los servicios que hacen posible la conexión con el back-end, debido a que son clases que cuentan con funciones que se encargan de realizar las peticiones al back-end que este es capaz de contemplar. Hay 3 servicios, *“PointOfInterestService”*, *“TouristsService”* y *“TourismItineraryService”*, los cuales realizan peticiones al back-end que tienen que ver con puntos de interés, turistas y preferencias de viajes, y con la generación de itinerarios turísticos, respectivamente.

Los componentes son almacenados en el directorio “components” dentro de “src”, hay un gran número de componentes, entre ellos se encuentran:
* *AddPointOfInterestComponent*: Componente que construye un formulario para añadir puntos de interés. Este proporciona un mapa interactivo en el que el usuario que los está introduciendo puede pulsar para que se carguen automáticamente las coordenadas de la geolocalización del punto (latitud y longitud). También proporciona entradas para que se introduzcan los distintos datos con los que un punto de interés puede contar, como el nombre, la descripción, las categorías, etc. Cuando se pulsa el botón *“ADD”* o *“AÑADIR”*, el servicio *“PointOfInterestService”*, se encarga de mandar una petición POST al back-end con el objeto construido con los datos del formulario, para que se añada a la base de datos.
* *AddTouristComponent*: Componente que construye el formulario para añadir turistas. Este proporciona entradas para que se introduzcan los distintos datos con los que un turista puede contar, como el nombre, apellidos, DNI y correo electrónico. Cuando se pulsa el botón *“ADD”* o *“AÑADIR”*, el servicio *“TouristService”*, se encarga de mandar una petición POST al back-end con el objeto construido con los datos del formulario, para que se añada a la base de datos.
* *AddTravelPreferencesComponent*: Componente que construye el formulario para añadir preferencias de viajes. Este formulario cuenta con un selector para elegir el turista al que se le quiere asignar las preferencias de viaje, con un mapa interactivo para que cuando el turista pulse se carguen automáticamente las coordenadas de la geolocalización del hotel en el que se va a alojar, y con distintas entradas para introducir datos con respecto a estas preferencias, como las actividades que desea hacer el turista, si tiene transporte o no, el presupuesto con el que cuenta, etc. Cuando se pulsa el botón *“ADD”* o *"AÑADIR"*, el servicio *“TouristService”*, se encarga de mandar una petición POST al back-end con el objeto construido con los datos del formulario, para que se añadan las preferencias de viaje del turista indicado a la base de datos.
* *HomeComponent*: Componente que construye la página de inicio, que cuenta con un título y el logo de la página web.
LanguageSwitcherComponent: Componente que se utiliza dentro del componente *”ToolbarComponent”*, que construye un selector que se encarga de cambiar el idioma del aplicativo según la opción seleccionada, el cual se puede cambiar también en la URL. Por defecto la opción seleccionada de este selector es *“en”*, por lo que el idioma con el que empieza la aplicación es el inglés.
* *PointsOfInterestComponent*: Componente a través del cual se visualizan los puntos de interés registrados en la base de datos. Para ello, primeramente, el servicio *“PointOfInterestService”* realiza una petición GET al back-end para obtener todos los puntos de interés que se encuentran en la base de datos. Posteriormente a través de un mapa interactivo se muestran con marcadores y a través de de una tabla se muestran listados. En la tabla, a la derecha de cada registro en *“Actions”* o *“Acciones”*, se puede encontrar un botón con el icono de un lápiz para modificar el registro y otro con el icono de una papelera para eliminarlo.
Cuando se vaya a modificar un punto de interés porque se pulsó el marcador o el botón del lápiz, se abrirá en la parte superior un formulario igual al de *“AddPointOfInteretsComponent”*, pero en este, cuando se pulsa el botón “MODIFY”, o “MODIFICAR”, el servicio “AddPointOfInteretsComponent” mandará al back-end una petición PUT para que este modifique el documento relacionado a dicho punto de interés. 
Cabe destacar que en la parte superior de este componente hay tres botones:
    * *“AÑADIR PUNTO DE INTERÉS”* o *“ADD POINT OF INTEREST”*: Redirige al usuario al formulario para añadir un punto de interés.
    * *“BORRAR TODOS LOS PUNTOS DE INTERÉS”* o *“DELETE ALL POINTS OF INTEREST”*: Cuando se pulsa se abre una ventana emergente de seguridad para asegurar que el botón no se pulsó sin querer, y cuando se confirma el borrado, el servicio nombrado anteriormente manda una petición DELETE al back-end para borrar todos los puntos de interés
    * *“INSERCIÓN MASIVA DE PUNTOS DE INTERÉS”* o *“MASIVE INSERTION OF POINTS OF INTEREST”*: Cuando se pulsa se despliega una ventana emergente con un formulario, que tiene una entrada para seleccionar un fichero que contenga JSONs con la estructura de puntos de interés, los cuales cuando se pulse el botón *“ADD”* o *“AÑADIR”*, se añadirán a la base de datos. Para que se añadan los JSON deben tener la estructura que muestra la figura Figura 4.
   
![Captura de pantalla de 2022-06-17 18-13-38](https://user-images.githubusercontent.com/72864430/177583964-a3d404b9-b22c-4c58-a545-2a11d0d7c20c.png)
   
Figura 4. JSON de ejemplo de un punto de interés

* *ToolbarComponent*: Componente que construye la barra superior de la aplicación web. La cual cuenta con el logo del aplicativo a la izquierda y a la derecha con el componente *“LanguageSwitcherComponent”* y con 3 botones:
    * Botón con icono de avión: Redirige a la página de preferencias de viajes de los turistas
    * Botón con icono de persona: Redirige a la página de gestión de turistas
    * Botón con icono de ruta: Redirige a la página de gestión de los puntos de interés turísticos.
* *TourismItineraryComponent*: Componente que construye la visualización de un itinerario turístico personalizado a través de un mapa interactivo dónde se ven los puntos que el usuario visitará y unas tarjetas (una por día), con la descripción detallada de cada visita. Para ello al componente se le pasan unas propiedades, las cuales son el ID del turista y el ID de las preferencias de viaje con las que se quiere generar el itinerario. Posteriormente con estos datos, el servicio *“TourismItineraryService”* realiza una petición GET al back-end para obtener el itinerario turístico personalizado, generado de la ejecución del algoritmo de recomendación.
* *TouristsComponent*: Componente a través del cual se visualizan los turistas registrados en la base de datos. Para ello, primeramente, el servicio *“TouristsService”* realiza una petición GET al back-end para obtener todos los turistas que se encuentran en la base de datos. Posteriormente a través de una tabla se muestran listados. También cabe destacar que en la tabla, a la derecha de cada registro en *“Actions”* o *“Acciones”*, se puede encontrar un botón con el icono de un lápiz para modificar el registro y otro con el icono de una papelera para eliminarlo. También se encuentra en la parte superior del componente el botón *“AÑADIR TURISTA”* o *“ADD TOURIST”*, el cual redirige al usuario al formulario del *“AddTouristComponent”*.
* *TravelPreferencesComponent*: Componente a través del cual se visualizan las preferencias de viajes registrados en la base de datos. Para ello, primeramente, el servicio *“TouristsService”* realiza una petición GET al back-end para obtener todos los turistas que se encuentran en la base de datos y de estos se extraen las preferencias de viajes. Posteriormente a través de una tabla se muestran listadas. También cabe destacar que en la tabla, a la derecha de cada registro en *“Actions”* o *“Acciones”*, se puede encontrar un botón con el icono de un lápiz para modificar el registro, otro con el icono de una papelera para eliminarlo y otro con el icono de un avión y un marcador para generar un itinerario turístico acorde a las preferencias del registro en el que se encuentra el botón (esta es la manera de generar los itinerario turísticos personalizados). Este último botón redirige al usuario a la visualización del itinerario turístico mediante el componente *“TourismItineraryComponent”*. También se encuentra en la parte superior del componente el botón *“AÑADIR PREFERENCIAS DE VIAJE”* o *“ADD TRAVEL PREFERENCES”*, el cual redirige al usuario al formulario del componente *“AddTravelPreferencesComponent”*.

Todos estos componentes descritos son usados en las vistas, que se encuentran en el directorio *“views”*. Estas cuelgan de *“App.vue”*, donde se utiliza el componente *“ToolbarComponent”*, para que se vea en todas las vistas. La vistas creadas son las siguientes:
* *AddPointOfInterestView*: Utiliza el componente *“AddPointOfInterestComponent”*.
* *AddTouristView*: Utiliza el componente *“AddTouristComponent”*.
* *AddTravelPreferencesView*: Utiliza el componente *“AddTravelPreferencesComponent”*.
* *HomeView*: Utiliza el componente *“HomeComponent”*.
* *PointsOfInterestView*: Utiliza el componente *“PointOfInterestComponent”*.
* *TourismItineraryView*: Utiliza el componente *“TourismItineraryComponent”*.
* *TouristsView*: Utiliza el componente *“TouristsComponent”*.
* *TravelsPreferencesView*: Utiliza el componente *“TravelsPreferencesComponent”*.


## Ejecución

Para ejecutar el front-end de manera independiente, se deberá primeramente ejecutar el back-end de manera independiente (La manera de hacerlo se puede encontrar en el [README.md](https://github.com/acoidan-csengineer/Trabajo-de-Fin-de-Grado/blob/main/README.md) del back-end). Posteriormente se deberán instalar todas las dependencias del proyecto front-end con el comando:

```bash
yarn install
```

Y finalmente se deberá ejecutar el front-end con el comando:

```bash
yarn serve
```
