package arbeitsprobe.exceptions;

public class FalschesErgebnisGeldEntnahmeException extends Exception {

    public FalschesErgebnisGeldEntnahmeException(int wert, int differenz) {
        super("Der Betrag " + wert + " konnte nicht vollständig entnommen werden. " + differenz + " ist noch zu entnehmen.");
    }

}
