# Trabajo de fin de grado

## Aplicación web para la creación de itinerarios turísticos personalizados

### Web application for the creation of personalised tourist itineraries

#### Acoidán Mesa Hernández - [alu0101206479](https://github.com/alu0101206479) - [alu0101206479@ull.edu.es](alu0101206479@ull.edu.es)

##### Descripción

El turismo es de los sectores más importantes de la sociedad actual, siendo millones el número de personas que se desplazan de un lugar a otro en los distintos medios de transporte, como aviones o barcos.

Los turistas quieren llevarse de su viajes experiencias insuperables, visitando los mejores lugares de la mejor manera posible, siempre ajustándose a sus preferencias y limitaciones. Es decir, quieren tener el mejor itinerario turístico posible.

La elaboración de estos itinerarios puede causar problemas, debido al gran bombardeo de información, ofertas y promociones que hay en internet. Por lo que hay poca probabilidad de que se realice una valoración completa de esta. Para evadir este problema, los turistas suelen acudir a empresas físicas como agencias de viajes o utilizar aplicaciones informáticas que preparen sus itinerarios.

Con este trabajo de fin de grado se quiere resolver el *“Tourist Trip Design Problem”* (TTDP) a través de un sistema de recomendación que, valorando distintos criterios sea capaz de generar un itinerario turístico personalizado, con un conjunto de visitas ordenadas en el tiempo a distintos puntos de interés.

Este trabajo se basa en una aplicación web “Full Stack” El aplicativo que constituye este trabajo de fin de grado es un sistema generador de itinerarios turísticos en una aplicación web full stack que trata el TTDP, con el fin de generar itinerarios turísticos personalizados. En esta aplicación se dispone de una interfaz web donde se pueden registrar puntos de interés, con unos determinados atributos como lo son:

* Nombre
* Municipio
* Código postal
* Coste medio en euros que los turistas gastan en el punto.
* Número medio de horas que se está.
* Aplicaciones del punto
* Categorías (sol y playa, rural, naturaleza, gastronomía, cultura)
* Días de apertura
* Hora de apertura y hora de cierre
* Descripción del punto
* Coordenadas del punto (latitud y longitud)

También se pueden registrar turistas (nombre, apellidos, correo electrónico y DNI) y para cada uno de ellos, sus preferencias para cada viaje con el fin de que se les genere un itinerario personalizado para su viaje. Para ello se ha desarrollado un sistema de recomendación cuya funcionalidad se basa en el uso de una metaheurística que tiene como entrada:

* Los puntos de interés que están registrados en la base de datos.
* Las preferencias del turista:
* Coordenadas del hotel en el que se va a alojar (latitud y longitud).
* Presupuesto con el que cuenta en euros.
* Día y hora a la que llega al destino y día y hora a la que se va de este.
* Hora de inicio y de finalización dedicada al turismo.
* Actividades que quiera realizar (sol y playa, rural, naturaleza, gastronomía, cultura).
* Si tiene transporte para desplazarse en largas distancias o no.

Este sistema de recomendación asigna unas puntuaciones a los puntos de interés con respecto a las distancias que tienen con la zona del hotel y con respecto a cómo acoplan con las actividades que se ha indicado en las preferencias de viaje que el turista quiere hacer. Después de dar las puntuaciones, el algoritmo recomendador genera el itinerario turístico personalizado y lo exporta como JSON para que se pueda visualizar en la interfaz gráfica web.

Respecto a los usos de la aplicación, se pueden observar en la figura del diagrama de casos de usos UML Figura 1.

![Diagrama de casos de uso](https://user-images.githubusercontent.com/72864430/177739615-ee30c63c-fb7a-4199-848c-020960c81990.png)

Figura 1. Diagrama de casos de usos UML del proyecto ([https://bit.ly/3u4ZE00](https://bit.ly/3u4ZE00))


##### Ejecución

Para el despliegue de la aplicación se ha utilizado *Docker* y se guardan las imágenes creadas a través de las *GitHub Actions* en *Docker Hub*, un servicio de repositorios de Docker para alojar imágenes *Docker* en la nube.

Se tiene un archivo *“Dockerfile”* en el back-end que indica el despliegue de este y otro en el front-end. Y por último, un archivo denominado *“docker-compose.yml”*, que se encuentra en el directorio raíz.

El *“docker-compose.yml”*, se puede ejecutar con dos perfiles:
* *dev*: Perfil de desarrollo, inicia la imagen subida a Docker Hub del back-end, la imagen subida a Docker Hub del front-end y un servicio de MongoDB para la base de datos, por lo que la base de datos empieza vacía cuando se despliega con este perfil.
* *prod*: Perfil de producción, inicia la imagen subida a Docker Hub del back-end, la imagen subida a Docker Hub del front-end y se conecta a una base de datos de MongoDB Atlas, por lo que la aplicación empieza con datos cargados.

Para realizar el despliegue del aplicativo con el perfil de desarrollador, se debe utilizar el comando:

```bash
  sudo COMPOSE_PROFILES=prod docker-compose up
```

Pero sin embargo, se quiere desplegar con el perfil de desarrollador, se deberá ejecutar con el comando:

```bash
  sudo COMPOSE_PROFILES=dev docker-compose up
```

Una vez ejecutados alguno de los dos comandos anteriores, se podrá visualizar la aplicación en la URL "[localhost:8081/es](http://localhost:8081/es)".
