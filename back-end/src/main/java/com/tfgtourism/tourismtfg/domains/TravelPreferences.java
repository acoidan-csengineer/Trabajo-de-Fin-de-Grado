package com.tfgtourism.tourismtfg.domains;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;


public class TravelPreferences
{
    //Atributos de la clase
    @Id
    private String id;
    private GeoJsonPoint destinationGeolocation;
    private float budget;
    private ArrayList<ActivitiesEnum> activities;
    private Boolean transport;
    private LocalDateTime departureDatetime;
    private LocalDateTime returnDatetime;
    private LocalTime tourismStartTime;
    private LocalTime tourismEndTime;
    

    // Constructor sin parametros
    public TravelPreferences(){
    }

    // Constructor con parametros
    public TravelPreferences(String idP, GeoJsonPoint destinationGeolocationP, Float budgetP, ArrayList<ActivitiesEnum> activitiesP, Boolean transportP, LocalDateTime departureDatetimeP, LocalDateTime returnDatetimeP, LocalTime tourismStartTimeP, LocalTime tourismEndTimeP){
        id = idP;
        destinationGeolocation = destinationGeolocationP;
        budget = budgetP;
        activities = activitiesP;
        transport = transportP;
        departureDatetime = departureDatetimeP;
        returnDatetime = returnDatetimeP;
        tourismStartTime = tourismStartTimeP;
        tourismEndTime = tourismEndTimeP;
    }

    // Método para obtener el atributo id
    public String getId() {
        return id;
    }

    // Método para cambiar el atributo id
    public void setId(String id_new) {
        id = id_new;
    }

    // Método para obtener el atributo destinationGeolocation
    public GeoJsonPoint getDestinationGeolocation() {
        return destinationGeolocation;
    }

    // Método para cambiar el atributo destinationGeolocation
    public void setDestinationGeolocation(GeoJsonPoint destinationGeolocation_new) {
        destinationGeolocation = destinationGeolocation_new;
    }

    // Método para obtener el atributo budget
    public Float getBudget() {
        return budget;
    }

    // Método para cambiar el atributo budget
    public void setBudget(Float budget_new) {
        budget = budget_new;
    }

    // Método para obtener el atributo activities
    public ArrayList<ActivitiesEnum> getActivities() {
        return activities;
    }

    // Método para cambiar el atributo activities
    public void setActivities(ArrayList<ActivitiesEnum> activities_new) {
        activities = activities_new;
    }

    // Método para obtener el atributo transport
    public Boolean getTransport() {
        return transport;
    }

    // Método para cambiar el atributo transport
    public void setTransport(Boolean transport_new) {
        transport = transport_new;
    }

    // Método para obtener el atributo departureDatetime
    public LocalDateTime getDepartureDatetime() {
        return departureDatetime;
    }

    // Método para cambiar el atributo departureDatetime
    public void setDepartureDatetime(LocalDateTime departureDatetime_new) {
        departureDatetime = departureDatetime_new;
    }

    // Método para obtener el atributo departureDatetime
    public LocalDateTime getReturnDatetime() {
        return returnDatetime;
    }

    // Método para cambiar el atributo returnDatetime
    public void setReturnDatetime(LocalDateTime returnDatetime_new) {
        returnDatetime = returnDatetime_new;
    }

    // Método para obtener el atributo tourismStartTime
    public LocalTime getTourismStartTime() {
        return tourismStartTime;
    }

    // Método para cambiar el atributo tourismStartTime
    public void setTourismStartTime(LocalTime tourismStartTime_new) {
        tourismStartTime = tourismStartTime_new;
    }

    // Método para obtener el atributo tourismEndTime
    public LocalTime getTourismEndTime() {
        return tourismEndTime;
    }

    // Método para cambiar el atributo tourismEndTime
    public void setTourismEndTime(LocalTime tourismEndTime_new) {
        tourismEndTime = tourismEndTime_new;
    }
}