package com.tfgtourism.tourismtfg.domains;

import java.util.ArrayList;

import org.springframework.data.util.Pair;


// Clase para representar los puntos de interés
public class TimeBetweenPairPOIS
{
    private ArrayList<Pair<String, ArrayList<Pair<String, Integer>>>> timeBetweenPairPOIS;

    // Constructor sin parametros
    public TimeBetweenPairPOIS(){
        timeBetweenPairPOIS = new ArrayList<Pair<String, ArrayList<Pair<String, Integer>>>>();
    }

    // Constructor con parametros
    public TimeBetweenPairPOIS(ArrayList<Pair<String, ArrayList<Pair<String, Integer>>>> timeBetweenPairPOISP){
        timeBetweenPairPOIS = timeBetweenPairPOISP;
    }

    // Método para obtener el atributo timeBetweenPairPOIS
    public ArrayList<Pair<String, ArrayList<Pair<String, Integer>>>> gettimeBetweenPairPOIS() {
        return timeBetweenPairPOIS;
    }

    // Método para cambiar el atributo timeBetweenPairPOIS
    public void settimeBetweenPairPOIS(ArrayList<Pair<String, ArrayList<Pair<String, Integer>>>> timeBetweenPairPOIS_new) {
        timeBetweenPairPOIS = timeBetweenPairPOIS_new;
    }
}