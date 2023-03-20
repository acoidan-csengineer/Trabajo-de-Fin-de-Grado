generateTourismItinerary();



function generateTourismItinerary() {

    // CONSTRUCCIÓN DE DATOS

    let pois = null;
    let viaje = null;
    let timeBetweenPairPOIS = null;
    let daysEnum = [
        "MONDAY",
        "TUESDAY",
        "WEDNESDAY",
        "THURSDAY",
        "FRIDAY",
        "SATURDAY",
        "SUNDAY"
    ]
    
    // Construimos los datos necesarios leyendo los ficheros
    process.argv.forEach((val, index) => {
        if (index == 2 || index == 3 || index == 4) {
            const fs = require('fs');
    
            try {
            const data = fs.readFileSync(val, 'utf8');
    
            switch (index) {
                case 2:
                    pois = JSON.parse(data)
                    break;
    
                case 3:
                    viaje = JSON.parse(data)
                    break;
    
                case 4:
                    timeBetweenPairPOIS = JSON.parse(data).timeBetweenPairPOIS
                    break;
            
                default:
                    break;
            }
            } catch (err) {
                console.error(err);
            }
        }
    });

    let timeBetweenTouristAndPois = getDistanceBetweenTouristAndPois(viaje.destinationGeolocation, pois);
        



    // ASIGNACIÓN DE PUNTUACIONES

    let puntuaciones = [];        
    let puntuacion = 0.0;


    // Normalización de tiempos para obtener puntuaciones: z i = (x i – mínimo (x)) / (máximo (x) – mínimo (x))
    for (let i = 0; i < timeBetweenTouristAndPois.length; i++) {
        puntuacion = (timeBetweenTouristAndPois[i][1] - timeBetweenTouristAndPois[timeBetweenTouristAndPois.length-1][1]) / (timeBetweenTouristAndPois[0][1] - timeBetweenTouristAndPois[timeBetweenTouristAndPois.length-1][1]);
        
        puntuaciones.push([timeBetweenTouristAndPois[i][0], puntuacion]);
    }

    let actividadesViaje = viaje.activities;
    let categoriesPoint = [];
    let preferenciasComun = [];
    let preferenciasDistintas = [];

    // Recorremos los puntos de interés mirando las categorías de cada uno y viendo como se acoplan con las preferencias de actividades que el usuario quiere hacer en su viaje, con esto le damos le sumamos una puntiación a la que ya tiene el punto de interés
    for (let i = 0; i < puntuaciones.length; i++) {
        let poi = puntuaciones[i][0];
        categoriesPoint = poi.categories;
        if (categoriesPoint == actividadesViaje) {
            puntuaciones[i] = [poi, puntuaciones[i][1]+1.0];
        } else {
            preferenciasComun = [];
            preferenciasDistintas = [];
            categoriesPoint.forEach((categoriePoint) => {
                if (actividadesViaje.includes(categoriePoint)) {
                    preferenciasComun.push(categoriePoint);
                } else {
                    preferenciasDistintas.push(categoriePoint);
                }
            });

            if (preferenciasComun.length == 0) {    // No acopla nada con el usuario
                puntuaciones[i] = [poi, puntuaciones[i][1]+0.0];
            } else {
                if (preferenciasDistintas.length == 0) {    // Acopla perfectamente
                    puntuaciones[i] = [poi, puntuaciones[i][1]+1.0];
                } else {    // Acopla algo y se le pone la puntuación correspondiente según lo que acople
                    puntuacion = preferenciasComun.length/categoriesPoint.length;
                    puntuaciones[i] = [poi, puntuaciones[i][1]+puntuacion];
                }
            }
        }
    }
    
    // Ordenamos el vector de puntuaciones de mayor puntuación a menor
    puntuaciones.sort(function(a, b) {
        if (a[1] < b[1]) {
            return 1;
        }
        if (a[1] > b[1]) {
        return -1;
        }
        // a must be equal to b
        return 0;
    });



    // CONSTRUCCIÓN DE DATOS QUE SE UTILIZARÁN EN EL GENERADOR

    let itinerarioMultiple = {
        simpleItineraries: []
    };
    let itinerarioSimple = {};
    let visit = {};

    let horaInicioTurismo = new Date(0, 0, 0, viaje.tourismStartTime[0], viaje.tourismStartTime[1], 0);
    let horaFinalizacionTurismo = new Date(0, 0, 0, viaje.tourismEndTime[0], viaje.tourismEndTime[1], 0);

    // Creo las fechas sin las horas para sacar el número de días del itinerario
    let returnDatetime = new Date(viaje.returnDatetime[0], parseInt(viaje.returnDatetime[1])-1, viaje.returnDatetime[2])
    let departureDatetime = new Date(viaje.departureDatetime[0], parseInt(viaje.departureDatetime[1])-1, viaje.departureDatetime[2])

    // Variables para tener en cuenta que les prepara itinerario el día de llegada y/o de salida
    let departureDay = true;
    let returnDay = true;
    
    let numeroDiasItinerario = Math.floor((returnDatetime.getTime() - departureDatetime.getTime()) / (1000 * 60 * 60 * 24))+1;
    
    // Le añado las horas a las fechas
    returnDatetime = new Date(viaje.returnDatetime[0], parseInt(viaje.returnDatetime[1])-1, viaje.returnDatetime[2], viaje.returnDatetime[3], viaje.returnDatetime[4], 0)
    departureDatetime = new Date(viaje.departureDatetime[0], parseInt(viaje.departureDatetime[1])-1, viaje.departureDatetime[2], viaje.departureDatetime[3], viaje.departureDatetime[4], 0)

    let hora = new Date(0, 0, 0, departureDatetime.getHours()+3, departureDatetime.getMinutes(), departureDatetime.getSeconds());
    let dayOfWeek = (departureDatetime.getDay()+6)%7; // El domingo es el 0 y por eso le hago el +6 %7, para que sea el 6
    let itineraryDay = departureDatetime;

    // El -7, es porque se supone que el usuario tarde 3 horas en llegar al hotel dejar las cosas y estar preparado para hacer turismo, por lo que en caso de que no haya 7 horas de margen (3 para dejar las cosas y 4 para organizarle), se le quita un día
    if (departureDatetime.getHours() > horaFinalizacionTurismo.getHours()-7) {
        numeroDiasItinerario = numeroDiasItinerario-1;
        hora = horaInicioTurismo;
        dayOfWeek = (dayOfWeek+1)%7;
        itineraryDay.setDate(itineraryDay.getDate()+1);
        departureDay = false;
    }
    
    // Si el usuario se va antes de la 1 de la tarde, a ese usuario ese día no se le organiza nada
    if (returnDatetime.getHours() < 15) {
        numeroDiasItinerario = numeroDiasItinerario-1;
        returnDay = false;
    }
        

    
    let poisUsados = [];
    let indicesUsados = [];

    let poiAAñadir = {};
    let horaApertura = null;
    let horaCierre = null;
    let horaAux = null;
    let timeSpentOnTransport = null;
    let timeSpentOnTourism = null;
    let timeSpentOnLeisure = null;
    let timeSpentOnTransportInTotal = [0, 0];
    let timeSpentOnTourismInTotal = [0, 0];
    let timeSpentOnLeisureInTotal = [0, 0];

    
    let presupuesto = viaje.budget;
    let limiteNumerosAleatorios = 30;
    let numeroAleatorio = 0;
    let horasASumar = 0;
    let coste;
    let minutesOfTransport = 0;
    let transportMinutesPerDay = 0;
    let millisecondsSpentOnTourism = 0;
    let millisecondsSpentOnTourismInTotal = 0;
    let porcentaje = 0;

    let numIteraciones = 0; // Variable para que no se quede el while siguiente en un bucle infinito si no encuentra punto de interés que pueda introducir en el itinerario




    // GENERADOR DE ITINERIO

    // Hasta que no se completen todos los días del itinerario
    while(itinerarioMultiple.simpleItineraries.length < numeroDiasItinerario) {
        itinerarioSimple = {  // Se le asigna el día al itinerario simple
            dayOfWeek: itineraryDay.toLocaleDateString('en-GB'),
            visits: []
        } 

        visit = {   // Registramos el inicio en el alojamiento
            pointOfInterest: {
                name: "Alojamiento",
                geolocation: viaje.destinationGeolocation
            },
            departureTime: hora.toLocaleTimeString()
        }

        itinerarioSimple.visits.push(visit);

        // Si se esta rellenando el itinerario del primer día y es el día que llega al destino y se va a hacer itinerario ese día
        if (itinerarioMultiple.simpleItineraries.length == 0 && departureDay == true) {
            horaInicioTurismo = hora
            console.log(horaInicioTurismo.toLocaleTimeString())
        } else {
            horaInicioTurismo = new Date(0, 0, 0, viaje.tourismStartTime[0], viaje.tourismStartTime[1], 0);
            hora = horaInicioTurismo;
        }
        

        // Si se esta rellenando el itinerario del último día y es el día que se va del destino y se va a hacer itinerario ese día y la hora a la que se va -3 es menor a la hora de finalización de turimo
        if (itinerarioMultiple.simpleItineraries.length == numeroDiasItinerario-1 && returnDay == true && (returnDatetime.getHours()-3 <= horaFinalizacionTurismo.getHours())) {
            horaFinalizacionTurismo = new Date (0, 0, 0, returnDatetime.getHours()-3, returnDatetime.getMinutes(), 0)
        }

        millisecondsSpentOnTourism = horaFinalizacionTurismo - horaInicioTurismo;
        millisecondsSpentOnTourismInTotal = millisecondsSpentOnTourismInTotal + millisecondsSpentOnTourism;

        // Hasta que no se completen las horas que tiene el viajero disponible para hacer turismo
        while ((hora.getTime() >= horaInicioTurismo.getTime() && hora.getTime() <= horaFinalizacionTurismo.getTime()) && numIteraciones < puntuaciones.length) {
            // Sacar números aleatorios hasta un limite y hasta que se encuentre un número que corresponda al indice de un punto de interés que no se haya usado ni valorado
            do {
                numeroAleatorio = parseInt((Math.random() * (limiteNumerosAleatorios - 0)) + 0);
                poiAAñadir = puntuaciones[numeroAleatorio][0];
                
                if (indicesUsados.length == limiteNumerosAleatorios) {
                    limiteNumerosAleatorios = limiteNumerosAleatorios+10;
                    if (limiteNumerosAleatorios > puntuaciones.length) {
                        limiteNumerosAleatorios = puntuaciones.length;
                    }
                }

                indicesUsados.sort();
            } while((indicesUsados.includes(numeroAleatorio) || poisUsados.filter(poi => poi.id === poiAAñadir.id).length > 0) && indicesUsados.length < puntuaciones.length);
            
            indicesUsados.push(numeroAleatorio);

            numIteraciones++;
            horasASumar = poiAAñadir.stayHourNumber;
            coste = poiAAñadir.averageCost;
                
            // Si el usuario no tiene transporte no se le recomiendan puntos que le queden a más de 30 minutos de la zona que selecciono en su viaje
            if (!viaje.transport && getDistanceBetweenTouristAndPoiFromArray(timeBetweenTouristAndPois, poiAAñadir.id) > 30) {
                continue;
            }

            // Miramos si el día de este itinerario está el punto abierto
            if (!poiAAñadir.openingDays.includes(daysEnum[dayOfWeek]))
                continue;

            // Miramos si la hora en la que se va a encontrar el turista esta el punto a añadir abierto
            if (poiAAñadir.hourly !== null) {
                horaApertura = new Date(0, 0, 0, parseInt(poiAAñadir.hourly.substring(0, 2)), parseInt(poiAAñadir.hourly.substring(3, 4)), 0);
                horaCierre = new Date(0, 0, 0, parseInt(poiAAñadir.hourly.substring(8, 10)), parseInt(poiAAñadir.hourly.substring(11, 13), 0));
                if (horaCierre.getTime() < horaApertura.getTime()) {
                    if (hora.getTime() < horaApertura.getTime() || hora.getHours() >= horaCierre.getHours()+24)
                        continue;
                } else {
                    if (hora.getTime() < horaApertura.getTime() || hora.getTime() > horaCierre.getTime())
                        continue;
                }
            }
            
            
            // Si las horas medias a estar en el punto no superan la hora limite maxima del viajero para hacer turismo y si el turista le queda presupuesto como para visitar dicho punto y si el punto no se ha visitado
            let horaDespuesDeVisitarElPunto = new Date (0, 0, 0, (hora.getHours()+horasASumar)%24, hora.getMinutes(), hora.getSeconds());
            if ((horaDespuesDeVisitarElPunto.getTime() >= horaInicioTurismo && horaDespuesDeVisitarElPunto.getTime() <= horaFinalizacionTurismo.getTime()) && presupuesto-coste > 0 && poisUsados.filter(poi => poi.id === poiAAñadir.id).length == 0) {
                
                if (itinerarioSimple.visits.length > 1) {
                    minutesOfTransport = getDistanceBetweenPoisFromArray(timeBetweenPairPOIS, poisUsados[poisUsados.length-1], poiAAñadir.id);
                } else {
                    minutesOfTransport = getDistanceBetweenTouristAndPoiFromArray(timeBetweenTouristAndPois, poiAAñadir.id);
                }

                horaAux = new Date(0, 0, 0, hora.getHours()+horasASumar, hora.getMinutes() + minutesOfTransport, hora.getSeconds());
                // Compruebo si el tiempo en llegar al punto más el tiempo en el punto no sobrepasa el rango de tiempo destinado para hacer turismo
                if (horaAux.getTime() < horaInicioTurismo.getTime() || horaAux.getTime() > horaFinalizacionTurismo.getTime()) {
                    continue;
                }
                
                hora = new Date(0, 0, 0, hora.getHours(), hora.getMinutes() + minutesOfTransport, hora.getSeconds());

                transportMinutesPerDay = transportMinutesPerDay + minutesOfTransport;
                visit = JSON.parse(JSON.stringify({}))
                visit = {   // Registramos la visita
                    pointOfInterest: poiAAñadir,
                    transportTime: minutesOfTransport,
                    arrivalTime: hora.toLocaleTimeString(),
                    departureTime: horaAux.toLocaleTimeString(),
                    timeOnSite: horasASumar

                } 

                poisUsados.push(poiAAñadir.id);
                
                console.log("Tiempo que se tarda en llegar: ", minutesOfTransport);
                console.log("Nombre del POI a introducir: ", poiAAñadir.name);
                console.log("Presupuesto antes de añadirlo: ", presupuesto);
                console.log("Hora antes de añadirlo: ", hora.toLocaleTimeString());

                hora = horaAux;
                presupuesto = presupuesto-coste;

                console.log("Presupuesto después de añadirlo: ", presupuesto);
                console.log("Horas a sumar: ", horasASumar);
                console.log("Hora después de añadirlo: ", hora.toLocaleTimeString());
                console.log();

                // Insertamos la visita en el itinerario del día
                itinerarioSimple.visits.push(visit);

                indicesUsados = [];
                puntuaciones.splice(numeroAleatorio, 1);
                limiteNumerosAleatorios = 30;
            } else {
                continue;
            }
        }

        minutesOfTransport =  getDistanceBetweenTouristAndPoiFromArray(timeBetweenTouristAndPois, itinerarioSimple.visits[itinerarioSimple.visits.length-1].pointOfInterest.id);
        transportMinutesPerDay = transportMinutesPerDay + minutesOfTransport;
        hora = new Date(0, 0, 0, hora.getHours(), hora.getMinutes()+minutesOfTransport, 0);

        visit = {   // Registramos el final en el alojamiento
            pointOfInterest: {
                name: "Alojamiento",
                geolocation: viaje.destinationGeolocation
            },
            transportTime: minutesOfTransport,
            arrivalTime: hora.toLocaleTimeString()
        }

        itinerarioSimple.visits.push(visit);

        // Indicador: Tiempo dedicado al transporte
        timeSpentOnTransport = new Date(0, 0, 0, 0, 0, 0);
        porcentaje = 0;
        if (transportMinutesPerDay > 0) {
            timeSpentOnTransport = new Date(transportMinutesPerDay*60*1000);
            porcentaje = ((timeSpentOnTransport*100)/millisecondsSpentOnTourism).toFixed(2)
        }
        
        itinerarioSimple.timeSpentOnTransport = timeSpentOnTransport.getHours() + "h " + timeSpentOnTransport.getMinutes() + "m ("  + porcentaje + "%)"
        timeSpentOnTransportInTotal[0] = timeSpentOnTransportInTotal[0] + timeSpentOnTransport.getHours();
        timeSpentOnTransportInTotal[1] = timeSpentOnTransportInTotal[1] + timeSpentOnTransport.getMinutes();


        // Indicador: Tiempo dedicado al turimo
        timeSpentOnTourism = new Date(0, 0, 0, 0, 0, 0);
        porcentaje = 0;
        if (hora-horaInicioTurismo-timeSpentOnTransport > 0) {
            console.log(hora.toLocaleTimeString(), horaInicioTurismo.toLocaleTimeString(), timeSpentOnTransport.toLocaleTimeString());
            timeSpentOnTourism = new Date(hora-horaInicioTurismo-timeSpentOnTransport);
            porcentaje = ((timeSpentOnTourism*100)/millisecondsSpentOnTourism).toFixed(2);
        }

        itinerarioSimple.timeSpentOnTourism = timeSpentOnTourism.getHours() + "h " + timeSpentOnTourism.getMinutes() + "m ("  + porcentaje + "%)"
        timeSpentOnTourismInTotal[0] = timeSpentOnTourismInTotal[0] + timeSpentOnTourism.getHours();
        timeSpentOnTourismInTotal[1] = timeSpentOnTourismInTotal[1] + timeSpentOnTourism.getMinutes();


        // Indicador: Tiempo dedicado al ocio
        timeSpentOnLeisure = new Date(0, 0, 0, 0, 0, 0);
        porcentaje = 0;
        if (horaFinalizacionTurismo-hora > 0) {
            timeSpentOnLeisure = new Date(horaFinalizacionTurismo-hora);
            porcentaje = ((timeSpentOnLeisure*100)/millisecondsSpentOnTourism).toFixed(2);
        }
        
        itinerarioSimple.timeSpentOnLeisure = timeSpentOnLeisure.getHours() + "h " + timeSpentOnLeisure.getMinutes() + "m ("  + porcentaje + "%)"
        timeSpentOnLeisureInTotal[0] = timeSpentOnLeisureInTotal[0] + timeSpentOnLeisure.getHours();
        timeSpentOnLeisureInTotal[1] = timeSpentOnLeisureInTotal[1] + timeSpentOnLeisure.getMinutes();


        itinerarioMultiple.simpleItineraries.push(itinerarioSimple);   // Se añade al itinerario multiple el itinerario simple creado

        // Se pasa al siguiente día
        dayOfWeek = (dayOfWeek+1)%7
        itineraryDay.setDate(itineraryDay.getDate()+1)
            
        // Reinicio de variables
        hora = horaInicioTurismo; // Se inicializa la hora
        horaAux = null;
        transportMinutesPerDay = 0;
        numIteraciones = 0;
        indicesUsados = [];
        limiteNumerosAleatorios = 30;

        console.log("");
        console.log("-----------------------------------------------------------------------------");
        console.log("");
    }

    // Ajusto número de horas y minutos totales dedicados al transporte y las pongo en el objeto
    if (timeSpentOnTransportInTotal[1] >= 60) {
        timeSpentOnTransportInTotal[0] = timeSpentOnTransportInTotal[0] + parseInt(timeSpentOnTransportInTotal[1]/60);
        timeSpentOnTransportInTotal[1] = timeSpentOnTransportInTotal[1]%60;
    }
    porcentaje = (((timeSpentOnTransportInTotal[0]*3600000 + 60000*timeSpentOnTransportInTotal[1])*100)/millisecondsSpentOnTourismInTotal).toFixed(2);
    itinerarioMultiple.timeSpentOnTransportInTotal = timeSpentOnTransportInTotal[0] + "h " + timeSpentOnTransportInTotal[1] + "m (" + porcentaje + "%)";


    // Ajusto número de horas y minutos totales dedicados al turismo y las pongo en el objeto
    if (timeSpentOnTourismInTotal[1] >= 60) {
        timeSpentOnTourismInTotal[0] = timeSpentOnTourismInTotal[0] + parseInt(timeSpentOnTourismInTotal[1]/60);
        timeSpentOnTourismInTotal[1] = timeSpentOnTourismInTotal[1]%60;
    }
    porcentaje = (((timeSpentOnTourismInTotal[0]*3600000 + 60000*timeSpentOnTourismInTotal[1])*100)/millisecondsSpentOnTourismInTotal).toFixed(2);
    itinerarioMultiple.timeSpentOnTourismInTotal = timeSpentOnTourismInTotal[0] + "h " + timeSpentOnTourismInTotal[1] + "m (" + porcentaje + "%)";


    // Ajusto número de horas y minutos ocio dedicados al turismo y las pongo en el objeto
    if (timeSpentOnLeisureInTotal[1] >= 60) {
        timeSpentOnLeisureInTotal[0] = timeSpentOnLeisureInTotal[0] + parseInt(timeSpentOnLeisureInTotal[1]/60);
        timeSpentOnLeisureInTotal[1] = timeSpentOnLeisureInTotal[1]%60;
    }
    porcentaje = (((timeSpentOnLeisureInTotal[0]*3600000 + 60000*timeSpentOnLeisureInTotal[1])*100)/millisecondsSpentOnTourismInTotal).toFixed(2);
    itinerarioMultiple.timeSpentOnLeisureInTotal = timeSpentOnLeisureInTotal[0] + "h " + timeSpentOnLeisureInTotal[1] + "m (" + porcentaje + "%)";



    const fs = require('fs');
    
    fs.writeFile(process.argv[5], JSON.stringify(itinerarioMultiple), function(err) {
        if (err) {
          return console.log(err);
        }
    });
    
    return itinerarioMultiple;
}








function getDistanceBetweenTouristAndPois(touristPosition, pois) {
    let timeBetweenTouristAndPoint = [];
    let auxPair;
    let coordenadasViaje = [];
    coordenadasViaje.push(touristPosition.y);
    coordenadasViaje.push(touristPosition.x);
    let coordenadasPoi = [];

    let distance = 0.0;
    let timeInMinutes = 0;
    let min = 100000000;
    
    pois.forEach((poi) => {
        if (poi.geolocationPoint != null) {
            coordenadasPoi = [poi.geolocationPoint.y, poi.geolocationPoint.x]

            distance = getDistance(coordenadasViaje, coordenadasPoi);
            timeInMinutes = Math.round(distance);
            auxPair = [poi, timeInMinutes];
            timeBetweenTouristAndPoint.push(auxPair);
        } else {
            if (poi.geolocationLineString != null) {
                min = 100000000;
                poi.geolocationLineString.coordinates.forEach((coordenada) => {
                    coordenadasPoi = [coordenada[1], coordenada[0]]

                    distance = getDistance(coordenadasViaje, coordenadasPoi);
                    timeInMinutes = Math.round(distance);
                    if (min > timeInMinutes) {
                        min = timeInMinutes;
                    }
                });

                auxPair = [poi, min];
                timeBetweenTouristAndPoint.push(auxPair);
            } else {
                if (poi.geolocationPolygon != null) {
                    poi.geolocationPolygon.coordinates.forEach((lineas) => {
                        lineas.coordinates.forEach((coordenada) => {
                            coordenadasPoi = [coordenada[1], coordenada[0]]

                            distance = getDistance(coordenadasViaje, coordenadasPoi);
                            timeInMinutes = Math.round(distance);
                            if (min > timeInMinutes) {
                                min = timeInMinutes;
                            }
                        });
                    });

                    auxPair = [poi, min];
                    timeBetweenTouristAndPoint.push(auxPair);
                }
            }
        }
    });

    timeBetweenTouristAndPoint.sort(function(a, b) {
        if (a[1] > b[1]) {
            return 1;
        }
        if (a[1] < b[1]) {
        return -1;
        }
        // a must be equal to b
        return 0;
    });

    return timeBetweenTouristAndPoint;
}








function getDistanceBetweenPoisFromArray(timesBetweenPois, idPoiOne, idPoiTwo) {
    for(let i = 0; i < timesBetweenPois.length; i++) {
        if (timesBetweenPois[i].first == idPoiOne) {
            
            for (let j = 0; j < timesBetweenPois[i].second.length; j++) {
                if (timesBetweenPois[i].second[j].first == idPoiTwo) {
                    
                    return timesBetweenPois[i].second[j].second;
                }
            }
        }
    }
    
    return null;
}








function getDistanceBetweenTouristAndPoiFromArray(timesBetweenTouristAndPois, idPoi) {
    let time = timesBetweenTouristAndPois.find(element => element[0].id == idPoi);
    if (typeof time !== 'undefined')
        return time[1]
    else
        return null;
}








function getDistance(pointOne, pointTwo) {
    let radiusEarth = 6378;
    let a = 0.0;
    let c = 0.0;
    let distance = 0.0;
    let latitudeDistance = 0.0;
    let longitudeDistance = 0.0;

    latitudeDistance = toRadians(pointTwo[0] - pointOne[0]);
    longitudeDistance = toRadians(pointTwo[1] - pointOne[1]);

    a = Math.pow(Math.sin(latitudeDistance/2), 2) + Math.cos(toRadians(pointOne[0])) * Math.cos(toRadians(pointTwo[0])) * Math.pow(Math.sin(longitudeDistance/2), 2);

    c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

    distance = radiusEarth * c;

    return distance;
}










function toRadians(degrees) {
    let pi = Math.PI;
    return degrees * (pi/180);
}









function dayOfYear(degrees) {
    let pi = Math.PI;
    return degrees * (pi/180);
}
