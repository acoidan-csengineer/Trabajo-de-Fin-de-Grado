package com.tfgtourism.tourismtfg.domains;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


// Clase para representar los puntos de interés
@Document(collection = "tourists")
public class Tourist
{
    //Atributos de la clase
    @Id
    private String id;

    private String name;
    private String surname;
    private String email;
    private String dni;
    private ArrayList<TravelPreferences> travelsPreferences;

    // Constructor sin parametros
    public Tourist(){
    }

    // Constructor con parametros
    public Tourist(String nameP, String surnameP, String dniP, String emailP){
        name = nameP;
        surname = surnameP;
        dni = dniP;
        email = emailP;
        travelsPreferences = new ArrayList<TravelPreferences>();
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

    // Método para obtener el atributo surname
    public String getSurname() {
        return surname;
    }

    // Método para cambiar el atributo surname
    public void setSurname(String surname_new) {
        surname = surname_new;
    }

    // Método para obtener el atributo email
    public String getEmail() {
        return email;
    }

    // Método para cambiar el atributo email
    public void setEmail(String email_new) {
        email = email_new;
    }

    // Método para obtener el atributo dni
    public String getDNI() {
        return dni;
    }

    // Método para cambiar el atributo dni
    public void setDNI(String dni_new) {
        dni = dni_new;
    }

    // Método para obtener el atributo travels
    public ArrayList<TravelPreferences> getTravelsPreferences() {
        return travelsPreferences;
    }

    // Método para cambiar el atributo travels
    public void setTravelsPreferences(ArrayList<TravelPreferences> travelsPreferences_new) {
        travelsPreferences = travelsPreferences_new;
    }

    // Método para cambiar el atributo travels
    public void pushTravelPreferences(TravelPreferences travel) {
        this.travelsPreferences.add(travel);
    }
}