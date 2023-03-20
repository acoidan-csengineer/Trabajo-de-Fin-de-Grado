# Back-end

## Descripción

El back-end se ha realizado con el lenguaje de programación *Java*, un lenguaje comercializado por primera vez en 1995 por Sun Microsystems. En concreto se ha utilizado el framework *Spring*, debido a que es el framework más utilizado de *Java*. Sus principios son la rapidez, la simpleza y la productividad, y es utilizado para crear aplicaciones de alto rendimiento utilizando objetos *Java* sencillos o *Plain Old Java Objects (POJO)*.

Dentro de *Spring*, se ha utilizado *Maven*, una herramienta para la gestión y construcción de aplicaciones *Java*, que proporciona una estructura de directorios que es común en todos los proyectos que lo utilizan. Usa un archivo *"pom.xml"* (*Project Object Model*), a través del cual se realiza toda la construcción del proyecto, desde el orden de construcción hasta las dependencias. Para la geolocalización se ha usado *GeoJson*.

El back-end se basa en una API REST que sirve para la transferencia de datos del aplicativo, API REST, actúa como una capa intermedia entre la interfaz de usuario del cliente y la base de datos. Dicha API REST, cuenta con distintos “endpoints” que se explicarán posteriormente, a los que se puede acceder con distintas peticiones HTTP para el manejo los datos, entre ellas *“GET”* (para obtener datos), *“POST”* (para obtener datos), *“PUT”* (para modificar datos) y *“DELETE”* (para eliminar datos).

La estructura de directorios del back-end es bastante extensa, hay algunos directorios que es importante describirlos. Entre ellos, a destacar, se encuentra la carpeta *“src/main/resources”* dónde se almacenan los ficheros con los recursos que necesite el back-end.

Y por otro lado, la carpeta *“src/main/java/…”*, donde se guardan los archivos que contienen el código fuente de la aplicación. Dentro de este directorio se pueden encontrar más, como pueden ser *“domains”*, aquí se encuentran los modelos a través de los cuales se manejan los datos que la aplicación utiliza. Entre ellos se encuentran los que se muestran en el diagrama de clases UML Figura 3.

![Diagrama de clases (2)](https://user-images.githubusercontent.com/72864430/177576224-3744f820-8c19-4802-9855-6264ef075a48.png)

Figura 1. Diagrama de clases del back-end (https://bit.ly/3bzorD3)

Como se puede apreciar, hay 6 clases que se conectan entre sí para que sea posible la arquitectura lógica de datos, estas clases son las siguientes:
* *ActivitiesEnum*: Un enumerado con todos los tipos de actividades/categorías que los turistas pueden querer hacer y que los puntos tienen disponibles para pertenecer (*SUNANDBEACH*, *RURAL*, *NATURE*, *GASTRONOMY*, *CULTURE*)
* *DaysEnum*: Un enumerado con los días de la semana (*MONDAY*, *TUESDAY*, *WEDNESDAY*, *THURSDAY*, *FRIDAY*, *SATURDAY*, *SUNDAY*).
* *PointOfInterest*: Clase que representa un punto de interés, cabe destacar que esta clase referencia a la colección “points-of-interest” de la base de datos y cuenta con distintas funciones para acceder a los atributos privados y en estos se encuentran:
    * private String *name*
    * private String *description*
    * private GeoJsonPoint *geolocationPoint*
    * private GeoJsonLineString *geolocationLineString*
    * private GeoJsonPolygon *geolocationPolygon*
    * private String *town*
    * private String *postalCode*
    * private String *applications*
    * private ArrayList<ActivitiesEnum> *categories*
    * private ArrayList<DaysEnum> *openingDays*
    * private String *hourly*
    * private Float *averageCost*
    * private Integer *stayHourNumber*
* TimeBetweenPairPOIS: Clase a través de la cual se puede representar los tiempos entre cada par de puntos a partir de la distancia, ya que tiene un atributo que es un tipo “ArrayList”, que sirve para almacenar el ID de los puntos de interés y los tiempos entre estos.
* *Tourist*: Clase que representa a un turista, cabe destacar que esta clase referencia a la colección “tourists” de la base de datos y cuenta con distintas funciones para acceder a los atributos privados y en estos se encuentran:
    * private String *id*
    * private String *name*
    * private String *surname*
    * private String *email*
    * private String *dni*
    * private ArrayList<TravelPreferences> *travelsPreferences*
* *TravelPreferences*: Clase unas preferencias de viaje de turista, a través de esta clase el sistema recomendador puede generar los itinerarios, comparando estas preferencias con los puntos de interés. Cuenta con distintas funciones para acceder a los atributos privados y en estos se encuentran:
    * private String *id*
    * private GeoJsonPoint *destinationGeolocation*
    * private float *budget*
    * private ArrayList<ActivitiesEnum> *activities*
    * private Boolean *transport*
    * private LocalDateTime *departureDatetime*
    * private LocalDateTime *returnDatetime*
    * private LocalTime *tourismStartTime*
    * private LocalTime *tourismEndTime*

En el directorio *“repositories”* hay dos archivos, estos son interfaces que extienden la interfaz del repositorio de MongoDB para poder utilizar las funciones para gestionar la base de datos (*“MongoRepository”*). A estas interfaces se le denominan repositorios, y hay un repositorio para las operaciones de gestión de la base relacionadas con puntos de interés, y otro para las relacionadas con turistas.

Estas interfaces se usan en los servicios, que son las clases en las que se define la lógica de las funciones relacionadas con la base de datos y se determina que va a pasar cuando se llame a los métodos para gestionarla. Los servicios se encuentran en la carpeta “services” y esta, a su vez, tiene dos subdirectorios: *“services”*, donde se definen la interfaces con los métodos que van a usarse y *“servicesImpl”*, donde se extienden las interfaces de la carpeta *“services”* y se implementan los métodos que se definen en estas interfaces con un atributo que representan los repositorios.

Estos servicios son usados en los controladores, los cuales se pueden encontrar en la carpeta *“controllers”*. Los controladores con los que cuenta el back-end son *“PointOfInterestController”*, *“TouristController”* y *“RecommendationSystemController”*.

Estos controladores son los que definen los “endpoints” (la URL a la que la interfaz de usuario del cliente manda las peticiones HTTP esperando una gestión de datos y una respuesta con un código de éxito, 2xx) de la API REST y las operaciones que se realiza cuando se manda una petición a alguno de estos. Los “endpoints” que gestionan son los siguientes:
* */pois*. Este endpoint acepta el siguiente tipo de peticiones HTTP:
    * *HTTP GET*: Cuando se mande esta petición, se recogerán de la batos todos los puntos de interés y se devolverán al cliente que mandó la petición, junto con el código de estado HTTP 200.
    * *HTTP POST*: Cuando el cliente mande esta petición deberá pasar en el cuerpo de la petición un objeto JSON con la estructura de un objeto punto de interés. Si no lo hace la API REST le responderá con una excepción con el código de estado HTTP 405, que representa un error del cliente y con el mensaje de error *“Method Not Allowed”*.
Dicho objeto pasado se transformará en un objeto de la clase correspondiente, después se comprobarán los datos de este y si son válidos, se almacenará en la base de datos y si no lo son, se lanzará una excepción con el código de estado HTTP 400, con el mensaje de error *“Bad Request”* y con un mensaje determinado según el fallo en la estructura del JSON pasado. Pero si todo se ha ejecutado correctamente, se devolverá en la respuesta la estructura del objeto punto de interés añadido y un código de estado HTTP 201, con el mensaje *“Created”*.
    * *HTTP DELETE*: Cuando se mande este tipo de petición, se borrarán de la base de datos todos los puntos de interés.
* */pois/{id}*. Este endpoint acepta el siguiente tipo de peticiones HTTP:
    * *HTTP GET*: Cuando se mande esta petición, se buscará en la base de datos el documento del punto de interés correspondiente al ID pasado en la URL como {id} y se devolverá junto con un código de estado HTTP 200. Sin embargo, si ese ID no se encuentra en la base de datos, la API REST lanzará una excepción con el código de estado HTTP 404 y con el mensaje de error *“Not Found”*.
    * *HTTP DELETE*: Si se manda esta petición, se borrará de la base de datos el punto de interés correspondiente al ID pasado en la URL como {id}. Pero si este no se encuentra, la API REST lanzará una excepción con el código de estado HTTP 404 y con el mensaje de error *“Not Found”*.
    * *HTTP PUT*: Cuando se mande esta petición, se modificará el punto de interés correspondiente al ID pasado en la URL como {id} y se devolverá en la respuesta el JSON del punto de interés modificado y un código de estado HTTP 201 con el mensaje *“Created”*. Pero, sería necesario pasar en el cuerpo de la petición un objeto JSON con la estructura de un objeto punto de interés con las modificaciones que se intenten hacer. Si este objeto se manda con algún dato erróneo en lo que refiere a estructura (por ejemplo, con el nombre vacío) se lanzará una excepción con el código de estado HTTP 400, con el mensaje de error *“Bad Request”* y con un mensaje determinado según el fallo en la estructura del JSON pasado. 
Si no hay modificaciones entre el que está registrado en la base de datos y el que se está pasando en la petición, será lanzada una excepción con el código de estado HTTP 400, con el mensaje de error *“Bad Request”* y el mensaje *“No changes to modify the item”*.
* */tourists*. Este endpoint acepta el siguiente tipo de peticiones HTTP:
    * *HTTP GET*: Cuando se mande esta petición, se le solicitarán a la base de datos todos los turistas y se devolverán al cliente que mandó la petición, junto con el código de estado HTTP 200.
    * *HTTP POST*: Cuando el cliente mande esta petición deberá pasar en el cuerpo de la petición un objeto JSON con la estructura de un objeto turista. Si no lo hace la API REST le responderá con una excepción con el código de estado HTTP 405 y con el mensaje de error *“Method Not Allowed”*.
Dicho objeto pasado se transformará en un objeto de la clase correspondiente, después se comprobarán los datos de este y si son válidos, se almacenará en la base de datos y si no lo son, se lanzará una excepción con el código de estado HTTP 400, con el mensaje de error *“Bad Request”* y con un mensaje determinado según el fallo en la estructura del JSON pasado. Pero si todo se ha ejecutado correctamente, se devolverá en la respuesta la estructura del objeto turista añadido y un código de estado HTTP 201, con el mensaje *“Created”*.
* */tourists/{id}*. Este endpoint acepta el siguiente tipo de peticiones HTTP:
    * *HTTP GET*: Cuando se mande esta petición, se buscará en la base de datos el documento del turista correspondiente al ID pasado en la URL como {id} y se devolverá junto con un código de estado HTTP 200. Sin embargo, si ese ID no se encuentra en la base de datos, la API REST lanzará una excepción con el código de estado HTTP 404 y con el mensaje de error *“Not Found”*.
    * *HTTP DELETE*: Si se manda esta petición, se borrará de la base de datos el turista correspondiente al ID pasado en la URL como {id}. Pero si este no se encuentra, la API REST lanzará una excepción con el código de estado HTTP 404 y con el mensaje de error *“Not Found”*.
    * *HTTP PUT*: Cuando se mande esta petición, se modificará el turista correspondiente al ID pasado en la URL como {id} y se devolverá en la respuesta el JSON del punto de interés modificado y un código de estado HTTP 201 con el mensaje *“Created”*. Pero, sería necesario pasar en el cuerpo de la petición un objeto JSON con la estructura de un objeto turista con las modificaciones que se intenten hacer. Si este objeto se manda con algún dato erróneo en lo que refiere a estructura (por ejemplo, con el nombre vacío) se lanzará una excepción con el código de estado HTTP 400, con el mensaje de error *“Bad Request”* y con un mensaje determinado según el fallo en la estructura del JSON pasado. 
Si no hay modificaciones entre el que está registrado en la base de datos y el que se está pasando en la petición, será lanzada una excepción con el código de estado HTTP 400, con el mensaje de error *“Bad Request”* y el mensaje *“No changes to modify the item”*.
* */tourists/{id}/travels-preferences*. Este endpoint acepta el siguiente tipo de peticiones HTTP:
    * *HTTP POST*: Cuando se manda esta petición se deberá pasar en el cuerpo un objeto JSON con la estructura de un objeto preferencias de viajes. Para que se pueda almacenar un nuevo registro acorde a las preferencias de viajes en el documento del turista correspondiente al ID pasado en la url como {id}. Si no pasa el objeto JSON, la API REST le responderá con una excepción con el código de estado HTTP 405 y con el mensaje de error *“Method Not Allowed”*.
Si todo se ha ejecutado correctamente y se han añadido las preferencias de viajes al documento del turista, se devolverá en la respuesta la estructura de las preferencias de viaje añadidas y un código de estado HTTP 201, con el mensaje *“Created”*
* */{id}/travels-preferences/{idT}*. Este endpoint acepta el siguiente tipo de peticiones HTTP:
    * *HTTP PUT*: Si se manda esta petición, también se deberá pasar en el cuerpo un objeto JSON con la estructura de un objeto preferencias de viajes, ya que este tipo de petición sirve para modificar las preferencias de viaje correspondientes al ID {idT}, del documento del turista correspondiente al ID pasado en la url como {id}. Si se modifica correctamente se devolverá en la respuesta la estructura de las preferencias de viaje añadidas y un código de estado HTTP 201, con el mensaje *“Created”*. Pero sin embargo, si por ejemplo, no se pasa el objeto JSON en el cuerpo de la petición, se lanzará una excepción con el código de estado HTTP 405 y con el mensaje de error *“Method Not Allowed”*.
    * *HTTP DELETE*: Cuando se mande esta petición, se borrará el registro de las preferencias de viaje correspondientes al ID {idT}, del documento del turista correspondiente al ID pasado en la url como {id}
* */recommendation-system/{idT}/{idTP}*. Este endpoint acepta el siguiente tipo de peticiones HTTP:
    * *HTTP GET*: A este “endpoint” se accede a través de una petición HTTP de tipo GET y pasándole el ID del turista al que se le quiere generar el itinerario como {idT} y el ID de las preferencias que se quieren tener en cuenta para generarlo como {idTP}, se encarga de devolver un itinerario turístico personalizado ejecutando el algoritmo de recomendación. Para ello, lo primero que hace la función asociada a este “endpoint” es preparar los datos para el sistema, como puede ser las preferencias de viaje indicadas del turista indicado, los puntos de interés y las tiempos entre todos los pares de estos. Estos datos se escriben en ficheros que se almacenarán en la carpeta *“src/main/resources”* y posteriormente se ejecuta el script con el algoritmo de recomendación que se encuentra en esa carpeta. Este trata esos datos, genera el itinerario turístico personalizado y lo devuelve para que se pueda mandar como respuesta al cliente que mandó la petición, junto con un código de estado HTTP 200.
    Si hubiera cualquier fallo, así sea en la preparación de los datos o en la generación del itinerario, se lanzaría una excepción con un código de estado HTTP que represente error (4xx – error del cliente o 5xx – error del servidor).

Cuando los controladores intentan introducir documentos en la base de datos, antes se comprueba que estos datos son correctos y válidos para su ingreso. De eso se encargan los validadores que se encuentran en el directorio *“validators”*. Existen en el proyecto dos validadores, PointOfInterestValidator y TouristValidator.

En estos validadores se comprueban los datos de los atributos del objeto a introducir. Como por ejemplo, se comprueba que los nombres no estén vacíos, que la estructura de los correos electrónicos de los turistas cumpla con unos criterios acorde a unas expresiones regulares (como que sea *“xxxxxx@xxxx.xxx”*), etc.


## Ejecución

Si se quisiera ejecutar el back-end independientemente, se puede ejecutar con dos perfiles diferentes al igual que el aplicativo, un perfil de desarrollo (Conecta con base de datos de MongoDB) o con un perfil de producción (Conecta con base de datos de MongoDB Atlas).

Si se quiere iniciar con el perfil de desarrollo hay que tener en cuenta que antes se debe iniciar el servicio de MongoDB, para poder acceder a la base de datos local. Para ejecutar el back-end con este perfil se deben ejecutar los siguientes comandos en la terminal:

```bash
    export spring_profiles_active=dev
```

```bash
    ./mvnw spring-boot:run
```

Pero si se quiere ejecutar con el perfil de producción, se deben ejecutar los siguientes:

```bash
    export spring_profiles_active=prod
```

```bash
    ./mvnw spring-boot:run
```
