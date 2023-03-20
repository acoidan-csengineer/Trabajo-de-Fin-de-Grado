# Sistema de recomendación turístico

## Descripción

 Los sistemas de recomendación son últimamente muy utilizados en la sociedad para todo tipo de ámbitos, como para recomendar ítems a los distintos usuarios en tiendas o para recomendar puntos de interés a turistas. Hay distintos tipos de sistemas de recomendación, entre estos se encuentran los *“Sistemas de recomendación basados en contenido”* o los *“Sistemas de recomendación colaborativos”*.

 El sistema de recomendación que se ha realizado para el aplicativo, está basado en una técnica heurística la cual valorando distintos tipos de criterios añade al itinerario a generar unos puntos de interés u otros. Se ha realizado con el lenguaje de programación *Javascript*.

 En el algoritmo de recomendación creado, lo primero que hace es recoger los datos acerca de los puntos de interés, los tiempos entre todos los pares de estos y las preferencias del viaje con las que se quiere generar el itinerario.

 A cada punto de interés se le da una puntuación de 0 a 1 con respecto a la distancia a la que están del hotel en el que el turista se va a alojar. Esta puntuación se suma con otra puntuación de 0 a 1 que corresponde a cómo se relacionan las categorías del punto con las actividades que el turista quiere realizar. Por ejemplo:

* Si un punto de interés tiene como categoría *[SUNANDBEACH]*, el turista tiene como actividades a realizar *[SUNANDBEACH, RURAL]*, y, a su vez, es el punto más cercano, este tendrá una puntuación de 2 (1 por acoplar perfectamente con las actividades del turista y 1 por ser el punto más cercano).
* Si un punto de interés tiene como categorías *[SUNANDBEACH, RURAL]* y el turista tiene de actividades que quiere hacer *[SUNANDBEACH]*, la puntuación correspondiente a esta relación será de 0.5, porque el punto de interés acopla a la mitad de sus actividades. Ya que el punto no es solo de *SUNANDBEACH*, sino que también tiene *RURAL*, actividad que el turista no quiere hacer. Posteriormente a esta puntuación se le sumará la puntuación con respecto a la distancia y si fuera el punto más cercano al alojamiento del turista, su puntuación sería de 1.5 (1 de la distancia y 0.5 del acoplamiento con las actividades del turista).

Una vez hayan sido calculadas las puntuaciones, se ordenan los puntos de interés acorde a su puntuación y se van preparando todas las variables necesarias para que se ejecute el bucle que genera el itinerario turístico.

Entre estas variables que se preparan se tienen en cuenta varios factores:

* Si el turista llega al destino a una hora la cual no tiene un margen de 7 horas con la hora de finalización de turismo que indicó en las preferencias, no se le prepara itinerario para el día de su llegada. Pero sin embargo, si tiene ese margen de horas, sí se le prepara itinerario para ese día, pero se le prepara 3 horas después de su llegada ya que se considera que este tiempo es el que el turista necesita para adaptarse al lugar, dejar sus maletas en el hotel, etc.
* Si el turista se va del destino antes de las 13:00 horas, no se le prepara itinerario para ese día, debido a que se le prepararía un itinerario demasiado corto.

Posteriormente empieza el bucle que genera el itinerario turístico múltiple. Este bucle no para hasta que no se ha completado un itinerario para cada día que el turista está de viaje. Dentro de este, hay otro bucle que sirve para generar el itinerario turístico simple de cada día. Y no termina hasta que se hayan completado las horas de turismo (hora de finalización de turismo - hora de inicio de turismo) o hasta que no se hayan comprobado todos los puntos de interés registrados.

En cada iteración del bucle que genera los itinerarios de cada día, se va mirando un punto de interés aleatorio entre los 30 primeros (límite puesto) del vector de puntos de interés ordenados por puntuaciones . Si estos 30 están valorados y no se pueden introducir porque están cerrados, están a una distancia mayor de la que el turista se puede permitir moverse debido al transporte, etc. o se han usado (Es decir, no se puede introducir ninguno), se va incrementando el límite de 10 en 10 (valorándose posteriormente los 40 primeros, 50..). Si el límite llega al número de puntos de interés registrados, significa que se han valorado todos los puntos y, están usados o no pueden introducirse en el itinerario. Por lo que se deja el tiempo restante hasta la hora de finalización de turismo como tiempo de ocio y se pasa a generar el itinerario turístico simple del siguiente día.

Cuando se escoge un número correspondiente al índice de un punto de interés, se valoran para estos los criterios en el siguiente orden:
* Si el turista no tiene transporte no se le recomiendan puntos que le queden a más de 30 minutos de su alojamiento.
* Si el día que se está generando el itinerario el punto de interés no está abierto se descarta.
* Si la hora a la que va a encontrar el turista en el punto está cerrado se descarta.
* Si las horas medias a estar en el punto no superan la hora límite máxima del viajero para hacer turismo, si al turista le queda presupuesto como para visitar dicho punto y si el punto no se ha visitado, se introduce en el itinerario.
* Cuando se introducen los puntos de interés en los itinerarios simples se introducen como visitas (La primera y la última visita de cada día siempre es el alojamiento), las cuales cuentan con:
* Hora de llegada al punto de interés
* Hora de salida del punto de interés
* Punto de interés a visitar
* Una vez se generan los itinerarios simples, se introducen en el itinerario múltiple junto con el día en el que se hará el itinerario y con los tiempos y porcentajes dedicados al turismo, al ocio y al transporte, con respecto a las horas disponibles del turista para hacer turismo.  
* Cuando se hayan completado todos los días, el algoritmo devuelve el itinerario turístico múltiple, junto con indicadores del itinerario completo que se van recogiendo a lo largo de la generación del itinerario, los cuales son:
* Tiempo y porcentaje dedicado al turismo
* Tiempo y porcentaje dedicado al transporte
* Tiempo y porcentaje dedicado al ocio


## Ejecución
Si se quisiera ejecutar el algoritmo de recomendación de manera independiente, antes se deben tener preparados los 3 ficheros de entrada siguientes:
* Puntos de interés
* Tiempos entre pares de puntos de interés
* Prefeencias de viaje con las que se quiere generar el itinerario

La estructura de estos archivos se pueden ver en la siguiente carpeta de Google Drive:
[https://drive.google.com/drive/folders/1onCFVf-QhECPrma4GvXiLWgMwIR2JDC2?usp=sharing](https://drive.google.com/drive/folders/1onCFVf-QhECPrma4GvXiLWgMwIR2JDC2?usp=sharing)

Teniendo los ficheros, la ejecución del script que contiene el algoritmo se realiza con el siguiente comando (Al que se le pasan el nombre de los 3 archivos de entrada y el nombre que tendrá el archivo de salida con el itinerario generado):

```bash
node recommendationSystem.js pois.json travelPreferences.json timeBetweenPairPOIS.json tourismItinerary.json
```
