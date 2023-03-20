package com.tfgtourism.tourismtfg.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfgtourism.tourismtfg.domains.PointOfInterest;
import com.tfgtourism.tourismtfg.domains.TimeBetweenPairPOIS;
import com.tfgtourism.tourismtfg.domains.TravelPreferences;
import com.tfgtourism.tourismtfg.services.services.PointOfInterestService;
import com.tfgtourism.tourismtfg.services.services.TouristService;

@RestController
@RequestMapping(value = "/recommendation-system", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class RecommendationSystemController {
    private final PointOfInterestService pointOfInterestService;
    private final TouristService touristService;

    public RecommendationSystemController(PointOfInterestService pointOfInterestServiceP, TouristService touristServiceP) {
        this.pointOfInterestService = pointOfInterestServiceP;
        this.touristService = touristServiceP;
    }

    @GetMapping(value = "/{idT}/{idTP}")
    public String recommendationSystem(@PathVariable String idT, @PathVariable String idTP) throws IOException {
        List<PointOfInterest> pois = this.pointOfInterestService.findAll(); // Todos los puntos
        TravelPreferences viaje = this.touristService.findById(idT).get().getTravelsPreferences().get(Integer.parseInt(idTP)-1); // Un viaje determinado
        ArrayList<Pair<String, ArrayList<Pair<String, Integer>>>> timeBetweenPairPois = this.pointOfInterestService.getTimeBetweenPairsPOIS();

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String point = "";
        GeoJsonLineString linea = null;
        GeoJsonPolygon poligono = null;
        ArrayList<ArrayList<ArrayList<Double>>> arrayCoordinatesPolygon = new ArrayList<>();
        ArrayList<ArrayList<Double>> arrayCoordinatesLineString = new ArrayList<>();
        ArrayList<Double> auxC = new ArrayList<>();
        String json_send = "[";

        // Hago esto para preparar la entrada al sistema recomendador, ya que me daba problemas con los LineString y los Polygon, porque el parseado automatico me añadir "type: Point" en las coordenadas de cada punto del poligono/linea
        for (PointOfInterest poi: pois) {
            if (!Objects.equals(poi.getGeolocationLineString(), null)) {
                linea = poi.getGeolocationLineString();
                poi.setGeolocationLineString(null);
                point = mapper.writeValueAsString(poi);

                arrayCoordinatesLineString.clear();
                for (Point coordinates: linea.getCoordinates()) {
                    auxC.clear();
                    auxC.add(coordinates.getX());
                    auxC.add(coordinates.getY());
                    arrayCoordinatesLineString.add(new ArrayList<>(auxC));
                }
                json_send += point.replaceAll("\"geolocationLineString\":null", "\"geolocationLineString\":{\"type\":\"LineString\",\"coordinates\":" + mapper.writeValueAsString(arrayCoordinatesLineString)+"}");
            
            } else {
                if (!Objects.equals(poi.getGeolocationPolygon(), null)) {
                    poligono = poi.getGeolocationPolygon();
                    poi.setGeolocationPolygon(null);
                    point = mapper.writeValueAsString(poi);
    
                    arrayCoordinatesPolygon.clear();
                    for (GeoJsonLineString lines: poligono.getCoordinates()) {
                        arrayCoordinatesLineString.clear();
                        for (Point coordinates: lines.getCoordinates()) {
                            auxC.clear();
                            auxC.add(coordinates.getX());
                            auxC.add(coordinates.getY());
                            arrayCoordinatesLineString.add(new ArrayList<>(auxC));
                        }
                        arrayCoordinatesPolygon.add(new ArrayList<>(arrayCoordinatesLineString));

                    }
                    json_send += point.replaceAll("\"geolocationPolygon\":null", "\"geolocationPolygon\":{\"type\":\"Polygon\",\"coordinates\":" + mapper.writeValueAsString(arrayCoordinatesPolygon)+"}");
                } else {
                    json_send += mapper.writeValueAsString(poi);
                }
                
            }

            if (!Objects.equals(poi, pois.get(pois.size()-1)))
                json_send += ",";
        }


        // Escribo los ficheros
        json_send += "]";
        BufferedWriter writer = new BufferedWriter(new FileWriter("/app/pois.json"));
        writer.write(json_send);
        writer.close();

        json_send = mapper.writeValueAsString(viaje);
        writer = new BufferedWriter(new FileWriter("/app/travelPreferences.json"));
        writer.write(json_send);
        writer.close();

        TimeBetweenPairPOIS aux = new TimeBetweenPairPOIS(timeBetweenPairPois);
        json_send = mapper.writeValueAsString(aux);
        writer = new BufferedWriter(new FileWriter("/app/timeBetweenPairPOIS.json"));
        writer.write(json_send);
        writer.close();

        
        ProcessBuilder processBuilder = new ProcessBuilder();

        // -- Linux --
    
        // Run a shell command
        processBuilder.command("bash", "-c", "node /app/recommendationSystem.js /app/pois.json /app/travelPreferences.json /app/timeBetweenPairPOIS.json /app/tourismItinerary.json");
        

        try {
            Process process = processBuilder.start();
    
            StringBuilder output = new StringBuilder();
    
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
    
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
    
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);

                BufferedReader buffer = new BufferedReader(new FileReader("/app/tourismItinerary.json"));
                StringBuilder stringBuffer = new StringBuilder();
                line = "";
                while ((line = buffer.readLine()) != null) {
                    stringBuffer.append(line+"\n");
                }
                buffer.close();
    
                return stringBuffer.toString();
            } else {
                System.out.println("Ha pasado algo con la ejecución del comando desde RecommendationSystemController");
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
