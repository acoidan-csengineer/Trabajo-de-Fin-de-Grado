package com.tfgtourism.tourismtfg.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tfgtourism.tourismtfg.domains.PointOfInterest;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;

@Configuration
public class PointOfInterestValidator {
    
    public final String NOT_CHANGES = "No changes to modify the item";
    private final String ID_INSERT = "It isn't allowed to pass ID to the point of interest.";

    private final String NAME_EMPTY = "The point of interest to be introduced hasn't name";
    private final String NAME_LONG = "The point of interest to be entered has too long a name (Maximum 100 characters)";
    private final int NAME_SIZE = 100;
    
    private final String HOURLY_SINTAX = "The time of the point of interest is incorrectly entered, the syntax should be 'hh:mm - hh:mm'.";
    
    private final String TOWN_CHARACTERS = "The town of the point of interest may not contain numbers or special characters";
    private final String POSTALCODE_ONLYLETTERS = "The postal code can only contain numbers";

    public PointOfInterestValidator() {

    }

    public Pair<HttpStatus, String> validate(PointOfInterest resource) {
        Pair<HttpStatus, String> error = this.validateId(resource.getId());
        if (error != null) {
            return error;
        } else {
            error = this.validateName(resource.getName());
            if (error != null)     
                return error;
            else {
                error = this.validateHourly(resource.getHourly());
                if (error != null) {
                    return error;
                } else {
                    error = this.validateTown(resource.getTown());
                    if (error != null) {
                        return error;
                    } else {
                        error = this.validatePostalCode(resource.getPostalCode());
                        if (error != null) {
                            return error;
                        }
                    }
                }
            }
        }

        return null;
    }

    public Pair<HttpStatus, String> validateId(String id) {
        Pair<HttpStatus, String> error = Pair.of(HttpStatus.BAD_REQUEST, this.ID_INSERT);
        if (id != null) 
            return error;

        return null;
    }

    public Pair<HttpStatus, String> validateName(String name) {
        Pair<HttpStatus, String> error = Pair.of(HttpStatus.BAD_REQUEST, this.NAME_EMPTY);
        if (name == "" || name == null)
            return error;
        else
            if (name.length() > NAME_SIZE) {
                error = Pair.of(HttpStatus.BAD_REQUEST, this.NAME_LONG);
                return error;
            }
                
        return null;
    }

    public Pair<HttpStatus, String> validateHourly(String hourly) {
        if (hourly != "" && hourly != null) {
            Pattern pat = Pattern.compile("^([\\d]{2}):([\\d]{2})(\\s-\\s)([\\d]{2}):([\\d]{2})$");    // Este patrón encuentra si hay un número en la cadena
            Matcher mat = pat.matcher(hourly);

            if (!mat.matches()) {
                Pair<HttpStatus, String> error = Pair.of(HttpStatus.BAD_REQUEST, this.HOURLY_SINTAX);
                return error;
            }
        }

        return null;
    }


    public Pair<HttpStatus, String> validateTown(String town) {
        if (town != "" && town != null) {
            Pattern pat = Pattern.compile(".*[¿?\\[\\]{}()+_%@<>¬¡!#~]+.*");    // Este patrón encuentra si hay un número o caracteres especiales en la cadena
            Matcher mat = pat.matcher(town);

            if (mat.matches()) {
                Pair<HttpStatus, String> error = Pair.of(HttpStatus.BAD_REQUEST, this.TOWN_CHARACTERS);
                return error;
            }
        }

        return null;
    }

    public Pair<HttpStatus, String> validatePostalCode(String postalCode) {
        if (postalCode != "" && postalCode != null) {
            Pattern pat = Pattern.compile("^([\\d]*)$");    // Este patrón encuentra si hay un número o caracteres especiales en la cadena
            Matcher mat = pat.matcher(postalCode);

            if (!mat.matches()) {
                Pair<HttpStatus, String> error = Pair.of(HttpStatus.BAD_REQUEST, this.POSTALCODE_ONLYLETTERS);
                return error;
            }
        }

        return null;
    }
}
