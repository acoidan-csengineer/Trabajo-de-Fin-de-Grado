package com.tfgtourism.tourismtfg.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tfgtourism.tourismtfg.domains.Tourist;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;

@Configuration
public class TouristValidator {
    
    public final String NOT_CHANGES = "No changes to modify the item";
    private final String ID_INSERT = "It isn't allowed to pass ID to the tourist item.";

    private final String NAME_EMPTY = "The tourist item to be introduced hasn't name";
    private final String NAME_LONG = "The tourist item to be entered has too long a name (Maximum 40 characters)";
    private final String NAME_CHARACTERS = "The tourist's name may not contain numbers or special characters";
    private final int NAME_SIZE = 40;

    private final String SURNAME_EMPTY = "The tourist item to be introduced hasn't surname";
    private final String SURNAME_LONG = "The tourist item to be entered has too long a surname (Maximum 100 characters)";
    private final String SURNAME_CHARACTERS = "The tourist's surname may not contain numbers or special characters";
    private final int SURNAME_SIZE = 100;

    private final String EMAIL_EMPTY = "The tourist item to be introduced hasn't email";
    private final String EMAIL_SYNTAX = "The syntax of the tourist's email isn't correct, the correct syntax is 'username@provider.provider-type'";
    private final String EMAIL_SYNTAX_START = "The tourist's email address must begin with a letter (a-z, A-Z) or a number (0-9)";
    private final String EMAIL_SYNTAX_NUSER = "The tourist's email user name can only contain alphanumeric characters and the '.', '-', '_' signs";
    private final String EMAIL_SYNTAX_NUSER_MORE_POINTS = "The tourist's email username can't have a consecutive colon";
    private final String EMAIL_SYNTAX_NUSER_SIZE = "The tourist's email username must be between 6 and 30 characters long";
    private final String EMAIL_SYNTAX_PROVIDER_START = "The tourist's email provider must start with a letter (a-z, A-Z)";
    private final String EMAIL_SYNTAX_PROVIDER = "The tourist's email provider can only contain alphanumeric characters";
    private final String EMAIL_SYNTAX_PROVIDER_SIZE = "The tourist's email provider must be between 2 and 20 characters long";
    private final String EMAIL_SYNTAX_DOMAIN_MORE_POINTS = "The domain of the tourist's email cannot have two consecutive dots";
    private final String EMAIL_SYNTAX_TPROVIDER= "The tourist's email provider type must contain only letters";
    private final String EMAIL_SYNTAX_TPROVIDER_SIZE= "The type of the tourist's email provider must be between 2 and 10 characters";

    public TouristValidator() {

    }

    public Pair<HttpStatus, String> validate(Tourist resource) {
        Pair<HttpStatus, String> error = this.validateId(resource.getId());

        if (error != null) {
            return error;
        } else {
            error = this.validateName(resource.getName());
            if (error != null)
                return error;
            else {
                error = this.validateSurname(resource.getSurname());
                if (error != null)
                    return error;
                else {
                    error = this.validateEmail(resource.getEmail());
                    if (error != null)
                        return error;
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
            else {
                Pattern pat = Pattern.compile(".*[\\d¿?\\[\\]{}()+_%@<>¬¡!#~]+.*");    // Este patrón encuentra si hay un número o caracteres especiales en la cadena
                Matcher mat = pat.matcher(name);

                if (mat.matches()) {
                    error = Pair.of(HttpStatus.BAD_REQUEST, this.NAME_CHARACTERS);
                    return error;
                }
                    
            }
                

        return null;
    }

    public Pair<HttpStatus, String> validateSurname(String surname) {
        Pair<HttpStatus, String> error = Pair.of(HttpStatus.BAD_REQUEST, this.SURNAME_EMPTY);
        if (surname == "" || surname == null)
            return error;
        else
            if (surname.length() > SURNAME_SIZE){
                error = Pair.of(HttpStatus.BAD_REQUEST, this.SURNAME_LONG);
                return error;
            }
                
            else {
                Pattern pat = Pattern.compile(".*[\\d¿?\\[\\]{}()+_%@<>¬¡!#~]+.*");    // Este patrón encuentra si hay un número o caracteres especiales en la cadena
                Matcher mat = pat.matcher(surname);

                if (mat.matches()) {
                    error = Pair.of(HttpStatus.BAD_REQUEST, this.SURNAME_CHARACTERS);
                    return error;
                }
                    
            }
                

        return null;
    }

    public Pair<HttpStatus, String> validateEmail(String email) {
        Pair<HttpStatus, String> error = Pair.of(HttpStatus.BAD_REQUEST, this.EMAIL_SYNTAX);

        if (email != "" || email == null) {
            Pattern pat = Pattern.compile("^.*@.*(\\.).*$");
            Matcher mat = pat.matcher(email);
            
            if (!mat.matches())
                return error;
            else {
                pat = Pattern.compile("^[A-Za-z0-9].*");
                mat = pat.matcher(email);
                
                if (!mat.matches()) {
                    error = Pair.of(HttpStatus.BAD_REQUEST, this.EMAIL_SYNTAX_START);
                    return error;
                }
                     
                else {
                    pat = Pattern.compile("^[A-Za-z0-9].*\\.{2,}.*@.*");
                    mat = pat.matcher(email);
                    
                    if (mat.matches()) {
                        error = Pair.of(HttpStatus.BAD_REQUEST, this.EMAIL_SYNTAX_NUSER_MORE_POINTS);
                        return error;
                    }
                        

                    else {
                        pat = Pattern.compile("^[A-Za-z0-9](\\.{0,1}[\\w-])*@.*");
                        mat = pat.matcher(email);

                        if (!mat.matches()) {
                            error = Pair.of(HttpStatus.BAD_REQUEST, this.EMAIL_SYNTAX_NUSER);     
                            return error; 
                        } else {
                            pat = Pattern.compile("^[A-Za-z0-9](\\.{0,1}[\\w-]){5,29}@.*");
                            mat = pat.matcher(email);

                            if (!mat.matches()) {
                                error = Pair.of(HttpStatus.BAD_REQUEST, this.EMAIL_SYNTAX_NUSER_SIZE);
                                return error;
                            } else {
                                pat = Pattern.compile("^[A-Za-z0-9](\\.{0,1}[\\w-]){5,29}@[A-Za-z0-9].*");
                                mat = pat.matcher(email);
                
                                if (!mat.matches()) {
                                    error = Pair.of(HttpStatus.BAD_REQUEST, this.EMAIL_SYNTAX_PROVIDER_START);
                                    return error;
                                } else {
                                    pat = Pattern.compile("^[A-Za-z0-9](\\.{0,1}[\\w-]){5,29}@[A-Za-z0-9].*\\.{2,}.*");
                                    mat = pat.matcher(email);
                    
                                    if (mat.matches()) {
                                        error = Pair.of(HttpStatus.BAD_REQUEST, this.EMAIL_SYNTAX_DOMAIN_MORE_POINTS);
                                        return error;
                                    } else {
                                        pat = Pattern.compile("^[A-Za-z0-9](\\.{0,1}[\\w-]){5,29}@[A-Za-z0-9](\\.{0,1}[A-Za-z0-9])+\\.\\w+$");
                                        mat = pat.matcher(email);
                        
                                        if (!mat.matches()) {
                                            error = Pair.of(HttpStatus.BAD_REQUEST, this.EMAIL_SYNTAX_PROVIDER);
                                            return error;
                                        } else {
                                            pat = Pattern.compile("^[A-Za-z0-9](\\.{0,1}[\\w-]){5,29}@[A-Za-z0-9](\\.{0,1}[A-Za-z0-9]){1,19}\\.\\w+$");
                                            mat = pat.matcher(email);
                            
                                            if (!mat.matches()) {
                                                error = Pair.of(HttpStatus.BAD_REQUEST, this.EMAIL_SYNTAX_PROVIDER_SIZE);
                                                return error;
                                            } else {
                                                pat = Pattern.compile("^[A-Za-z0-9](\\.{0,1}[\\w-]){5,29}@[A-Za-z0-9](\\.{0,1}[A-Za-z0-9]){1,19}\\.([a-zA-Z])+$");
                                                mat = pat.matcher(email);
                                
                                                if (!mat.matches()) {
                                                    error = Pair.of(HttpStatus.BAD_REQUEST, this.EMAIL_SYNTAX_TPROVIDER);
                                                    return error;
                                                } else {
                                                    pat = Pattern.compile("^[A-Za-z0-9](\\.{0,1}[\\w-]){5,29}@[A-Za-z0-9](\\.{0,1}[A-Za-z0-9]){1,19}(\\.[A-Za-z]{2,10})$");
                                                    mat = pat.matcher(email);
                                    
                                                    if (!mat.matches()) {
                                                        error = Pair.of(HttpStatus.BAD_REQUEST, this.EMAIL_SYNTAX_TPROVIDER_SIZE);
                                                        return error;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            error = Pair.of(HttpStatus.BAD_REQUEST, this.EMAIL_EMPTY);
            return error;
        }

        return null;
    }
}
