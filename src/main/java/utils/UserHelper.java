package utils;


import com.adam.kiss.jbugger.enums.UserNotValidErrorMessages;
import com.adam.kiss.jbugger.exceptions.UserNotValidException;

public class UserHelper {
    public static String generateUserName(String nume,String prenume,int nr) throws UserNotValidException {
        StringBuilder generatedUName = new StringBuilder();
        if(nume.length() < nr){
            nr = nume.length();
        }
        try{
            generatedUName.append(nume.substring(0,nr));
            generatedUName.append(prenume.substring(0,6-nr));
        }catch (StringIndexOutOfBoundsException e){
            throw new UserNotValidException(UserNotValidErrorMessages.NAME_IS_TOO_SHORT);
        }

        return generatedUName.toString().toLowerCase();
    }
}
