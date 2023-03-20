package com.tfgtourism.tourismtfg.domains;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.mapping.Document;


// Clase para representar los puntos de interés
@Document(collection = "points-of-interest")
public class PointOfInterest
{
    //Atributos de la clase
    @Id
    private String id;

    private String name;
    private String description;
    private GeoJsonPoint geolocationPoint;
    private GeoJsonLineString geolocationLineString;
    private GeoJsonPolygon geolocationPolygon;
    private String town;
    private String postalCode;
    private String applications;
    private ArrayList<ActivitiesEnum> categories;
    private ArrayList<DaysEnum> openingDays;
    private String hourly;
    private Float averageCost;
    private Integer stayHourNumber;
    // private String images;

    // Constructor sin parametros
    public PointOfInterest(){
    }

    // Constructor con parametros
    public PointOfInterest(String nameP, String descriptionP, GeoJsonPoint geolocationPointP, GeoJsonLineString geolocationLineStringP, GeoJsonPolygon geolocationPolygonP, String townP, String postalCodeP, ArrayList<ActivitiesEnum> categoriesP, String applicationsP, ArrayList<DaysEnum> openingDaysP, String hourlyP, Float averageCostP, Integer stayHourNumberP){
        name = nameP;
        description = descriptionP;
        geolocationPoint = geolocationPointP;
        geolocationLineString = geolocationLineStringP;
        geolocationPolygon = geolocationPolygonP;
        town = townP;
        postalCode = postalCodeP;
        applications = applicationsP;
        categories = categoriesP;
        openingDays = openingDaysP;
        hourly = hourlyP;
        averageCost = averageCostP;
        stayHourNumber = stayHourNumberP;
    }

    // Método para obtener el atributo id
    public String getId() {
        return id;
    }

    // Método para cambiar el atributo id
    public void setId(String id_new) {
        id = id_new;
    }

    // Método para obtener el atributo name
    public String getName() {
        return name;
    }

    // Método para cambiar el atributo name
    public void setName(String name_new) {
        name = name_new;
    }

    // Método para obtener el atributo description
    public String getDescription() {
        return description;
    }

    // Método para cambiar el atributo description
    public void setDescription(String description_new) {
        description = description_new;
    }

    // Método para obtener el atributo geolocation
    public GeoJsonPoint getGeolocationPoint() {
        return geolocationPoint;
    }

    // Método para cambiar el atributo geolocation
    public void setGeolocationPoint(GeoJsonPoint geolocation_new) {
        geolocationPoint = geolocation_new;
    }

    // Método para obtener el atributo geolocation
    public GeoJsonLineString getGeolocationLineString() {
        return geolocationLineString;
    }

    // Método para cambiar el atributo geolocation
    public void setGeolocationLineString(GeoJsonLineString geolocation_new) {
        geolocationLineString = geolocation_new;
    }

    // Método para obtener el atributo geolocation
    public GeoJsonPolygon getGeolocationPolygon() {
        return geolocationPolygon;
    }

    // Método para cambiar el atributo geolocation
    public void setGeolocationPolygon(GeoJsonPolygon geolocation_new) {
        geolocationPolygon = geolocation_new;
    }

    // Método para obtener el atributo town
    public String getTown() {
        return town;
    }

    // Método para cambiar el atributo town
    public void setTown(String town_new) {
        town = town_new;
    }

    // Método para obtener el atributo postalCode
    public String getPostalCode() {
        return postalCode;
    }

    // Método para cambiar el atributo postalCode
    public void setPostalCode(String postalCode_new) {
        postalCode = postalCode_new;
    }

    // Método para obtener el atributo categories
    public ArrayList<ActivitiesEnum> getCategories() {
        return categories;
    }

    // Método para cambiar el atributo categories
    public void setCategories(ArrayList<ActivitiesEnum> categories_new) {
        categories = categories_new;
    }

    // Método para obtener el atributo applications
    public String getApplications() {
        return applications;
    }

    // Método para cambiar el atributo applications
    public void setApplications(String applications_new) {
        applications = applications_new;
    }

    // Método para obtener el atributo openingDays
    public ArrayList<DaysEnum> getOpeningDays() {
        return openingDays;
    }

    // Método para cambiar el atributo openingDays
    public void setOpeningDays(ArrayList<DaysEnum> openingDays_new) {
        openingDays = openingDays_new;
    }

    // Método para obtener el atributo hourly
    public String getHourly() {
        return hourly;
    }

    // Método para cambiar el atributo hourly
    public void setHourly(String hourly_new) {
        hourly = hourly_new;
    }

    // Método para obtener el atributo averageCost
    public Float getAverageCost() {
        return averageCost;
    }

    // Método para cambiar el atributo averageCost
    public void setAverageCost(Float averageCost_new) {
        averageCost = averageCost_new;
    }

    // Método para obtener el atributo stayHourNumber
    public Integer getStayHourNumber() {
        return stayHourNumber;
    }

    // Método para cambiar el atributo stayHourNumber
    public void setStayHourNumber(Integer stayHourNumber_new) {
        stayHourNumber = stayHourNumber_new;
    }
}