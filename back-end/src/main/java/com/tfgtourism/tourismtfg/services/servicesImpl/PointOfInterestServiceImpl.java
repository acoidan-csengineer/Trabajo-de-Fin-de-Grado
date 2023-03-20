package com.tfgtourism.tourismtfg.services.servicesImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.tfgtourism.tourismtfg.domains.PointOfInterest;
import com.tfgtourism.tourismtfg.repositories.PointOfInterestRepository;
import com.tfgtourism.tourismtfg.services.services.PointOfInterestService;

@Service
public class PointOfInterestServiceImpl implements PointOfInterestService {
    @Autowired
    private PointOfInterestRepository repository;

    @Override
    public List<PointOfInterest> findAll() {
        return this.repository.findAll();
    }
    
    @Override
    public void delete(PointOfInterest resource) {
        this.repository.delete(resource);
    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();
    }

    @Override
    public PointOfInterest save(PointOfInterest resource) {
        return this.repository.save(resource);
    }

    @Override
    public Optional<PointOfInterest> findById(String id) {
        return this.repository.findById(id);
    }

    @Override
    public ArrayList<Pair<String, ArrayList<Pair<String, Integer>>>> getTimeBetweenPairsPOIS() {
        List<PointOfInterest> pois = this.repository.findAll();
        
        // Distancias entre cada par de puntos
        ArrayList<Pair<String, ArrayList<Pair<String, Integer>>>> distancesBetweenPoints = new ArrayList<>();
        ArrayList<Pair<String, Integer>> auxArray = new ArrayList<>();
        Pair<String, Integer> auxPair;
        ArrayList<Double> coordenadasPoiUno = new ArrayList<Double>();
        ArrayList<Double> coordenadasPoiDos = new ArrayList<Double>();
        Double distance = 0.0;
        Integer timeInMinutes = 0;
        Integer min = 100000000;
        for(PointOfInterest poiUno: pois) {
            auxArray = new ArrayList<>();
            for(PointOfInterest poiDos: pois) {
                // Si el punto 1 es un punto
                if (!Objects.equals(poiUno, poiDos)) {

                    // Si el punto de interés 1 es un punto
                    if (!Objects.equals(poiUno.getGeolocationPoint(), null)) {
                        coordenadasPoiUno.clear();
                        coordenadasPoiUno.add(poiUno.getGeolocationPoint().getY());
                        coordenadasPoiUno.add(poiUno.getGeolocationPoint().getX());
                        if (!Objects.equals(poiDos.getGeolocationPoint(), null)) {
                            coordenadasPoiDos.clear();
                            coordenadasPoiDos.add(poiDos.getGeolocationPoint().getY());
                            coordenadasPoiDos.add(poiDos.getGeolocationPoint().getX());

                            distance = getDistance(coordenadasPoiUno, coordenadasPoiDos);
                            timeInMinutes = (int) Math.round(distance);
                            auxPair = Pair.of(poiDos.getId(), timeInMinutes);
                            auxArray.add(auxPair);
                        } else {
                            if (!Objects.equals(poiDos.getGeolocationLineString(), null)) {
                                min = 100000000;
                                for(Point coordenada: poiDos.getGeolocationLineString().getCoordinates()) {
                                    coordenadasPoiDos.clear();
                                    coordenadasPoiDos.add(coordenada.getY());
                                    coordenadasPoiDos.add(coordenada.getX());

                                    distance = getDistance(coordenadasPoiUno, coordenadasPoiDos);
                                    timeInMinutes = (int) Math.round(distance);
                                    if (min > timeInMinutes) {
                                        min = timeInMinutes;
                                    }
                                }

                                auxPair = Pair.of(poiDos.getId(), min);
                                auxArray.add(auxPair);
                            } else {
                                if (!Objects.equals(poiDos.getGeolocationPolygon(), null)) {
                                    for(GeoJsonLineString lineas: poiDos.getGeolocationPolygon().getCoordinates()) {
                                        for(Point coordenada: lineas.getCoordinates()) {
                                            coordenadasPoiDos.clear();
                                            coordenadasPoiDos.add(coordenada.getY());
                                            coordenadasPoiDos.add(coordenada.getX());
    
                                            distance = getDistance(coordenadasPoiUno, coordenadasPoiDos);
                                            timeInMinutes = (int) Math.round(distance);
                                            if (min > timeInMinutes) {
                                                min = timeInMinutes;
                                            }
                                        }
                                    }
    
                                    auxPair = Pair.of(poiDos.getId(), min);
                                    auxArray.add(auxPair);
                                }
                            }
                        }
                    } else {

                        // Si el punto de interés 1 es una línea
                        if (!Objects.equals(poiUno.getGeolocationLineString(), null)) {
                            coordenadasPoiUno.add((poiUno.getGeolocationLineString().getCoordinates().get(0).getY()+poiUno.getGeolocationLineString().getCoordinates().get(1).getY())/2);
                            coordenadasPoiUno.add((poiUno.getGeolocationLineString().getCoordinates().get(0).getX()+poiUno.getGeolocationLineString().getCoordinates().get(1).getX())/2);

                            if (!Objects.equals(poiDos.getGeolocationPoint(), null)) {
                                coordenadasPoiDos.clear();
                                coordenadasPoiDos.add(poiDos.getGeolocationPoint().getY());
                                coordenadasPoiDos.add(poiDos.getGeolocationPoint().getX());
    
                                distance = getDistance(coordenadasPoiUno, coordenadasPoiDos);
                                timeInMinutes = (int) Math.round(distance);
                                auxPair = Pair.of(poiDos.getId(), timeInMinutes);
                                auxArray.add(auxPair);
                            } else {
                                if (!Objects.equals(poiDos.getGeolocationLineString(), null)) {
                                    min = 100000000;
                                    for(Point coordenada: poiDos.getGeolocationLineString().getCoordinates()) {
                                        coordenadasPoiDos.clear();
                                        coordenadasPoiDos.add(coordenada.getY());
                                        coordenadasPoiDos.add(coordenada.getX());
    
                                        distance = getDistance(coordenadasPoiUno, coordenadasPoiDos);
                                        timeInMinutes = (int) Math.round(distance);
                                        if (min > timeInMinutes) {
                                            min = timeInMinutes;
                                        }
                                    }
    
                                    auxPair = Pair.of(poiDos.getId(), min);
                                    auxArray.add(auxPair);
                                } else {
                                    if (!Objects.equals(poiDos.getGeolocationPolygon(), null)) {
                                        for(GeoJsonLineString lineas: poiDos.getGeolocationPolygon().getCoordinates()) {
                                            for(Point coordenada: lineas.getCoordinates()) {
                                                coordenadasPoiDos.clear();
                                                coordenadasPoiDos.add(coordenada.getY());
                                                coordenadasPoiDos.add(coordenada.getX());
        
                                                distance = getDistance(coordenadasPoiUno, coordenadasPoiDos);
                                                timeInMinutes = (int) Math.round(distance);
                                                if (min > timeInMinutes) {
                                                    min = timeInMinutes;
                                                }
                                            }
                                        }
        
                                        auxPair = Pair.of(poiDos.getId(), min);
                                        auxArray.add(auxPair);
                                    }
                                }
                            }
                        } else {
                            
                            // Si el punto de interés 1 es una polígono
                            if (!Objects.equals(poiUno.getGeolocationPolygon(), null)) {
                                coordenadasPoiUno.add(poiUno.getGeolocationPolygon().getCoordinates().get(0).getCoordinates().get(0).getY());
                                coordenadasPoiUno.add(poiUno.getGeolocationPolygon().getCoordinates().get(0).getCoordinates().get(0).getX());

                                if (!Objects.equals(poiDos.getGeolocationPoint(), null)) {
                                    coordenadasPoiDos.clear();
                                    coordenadasPoiDos.add(poiDos.getGeolocationPoint().getY());
                                    coordenadasPoiDos.add(poiDos.getGeolocationPoint().getX());
        
                                    distance = getDistance(coordenadasPoiUno, coordenadasPoiDos);
                                    timeInMinutes = (int) Math.round(distance);
                                    auxPair = Pair.of(poiDos.getId(), timeInMinutes);
                                    auxArray.add(auxPair);
                                } else {
                                    if (!Objects.equals(poiDos.getGeolocationLineString(), null)) {
                                        min = 100000000;
                                        for(Point coordenada: poiDos.getGeolocationLineString().getCoordinates()) {
                                            coordenadasPoiDos.clear();
                                            coordenadasPoiDos.add(coordenada.getY());
                                            coordenadasPoiDos.add(coordenada.getX());
        
                                            distance = getDistance(coordenadasPoiUno, coordenadasPoiDos);
                                            timeInMinutes = (int) Math.round(distance);
                                            if (min > timeInMinutes) {
                                                min = timeInMinutes;
                                            }
                                        }
        
                                        auxPair = Pair.of(poiDos.getId(), min);
                                        auxArray.add(auxPair);
                                    } else {
                                        if (!Objects.equals(poiDos.getGeolocationPolygon(), null)) {
                                            for(GeoJsonLineString lineas: poiDos.getGeolocationPolygon().getCoordinates()) {
                                                for(Point coordenada: lineas.getCoordinates()) {
                                                    coordenadasPoiDos.clear();
                                                    coordenadasPoiDos.add(coordenada.getY());
                                                    coordenadasPoiDos.add(coordenada.getX());
            
                                                    distance = getDistance(coordenadasPoiUno, coordenadasPoiDos);
                                                    timeInMinutes = (int) Math.round(distance);
                                                    if (min > timeInMinutes) {
                                                        min = timeInMinutes;
                                                    }
                                                }
                                            }
            
                                            auxPair = Pair.of(poiDos.getId(), min);
                                            auxArray.add(auxPair);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            auxArray.sort(new Comparator<Pair<String, Integer>>() {
                @Override
                public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                    return o1.getSecond().compareTo(o2.getSecond());
                }
            });

            distancesBetweenPoints.add(Pair.of(poiUno.getId(), auxArray));
        }

        return distancesBetweenPoints;
    }


    @Override
    public Double getDistance(ArrayList<Double> pointOne, ArrayList<Double> pointTwo) {
        final Integer radiusEarth = 6378;
        Double a = 0.0;
        Double c = 0.0;
        Double distance = 0.0;
        Double latitudeDistance = 0.0;
        Double longitudeDistance = 0.0;
    
        latitudeDistance = Math.toRadians(pointTwo.get(0) - pointOne.get(0));
        longitudeDistance = Math.toRadians(pointTwo.get(1) - pointOne.get(1));
    
        a = Math.pow(Math.sin(latitudeDistance/2), 2) + Math.cos(Math.toRadians(pointOne.get(0))) * Math.cos(Math.toRadians(pointTwo.get(0))) * Math.pow(Math.sin(longitudeDistance/2), 2);
    
        c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    
        distance = radiusEarth * c;
    
        return distance;
    }
}