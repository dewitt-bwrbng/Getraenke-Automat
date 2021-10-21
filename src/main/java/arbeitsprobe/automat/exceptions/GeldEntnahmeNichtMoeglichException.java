package arbeitsprobe.automat.exceptions;

public class GeldEntnahmeNichtMoeglichException extends Throwable {
    public GeldEntnahmeNichtMoeglichException(int wert) {
        super("Geldentnahme in Hoehe von " + wert + " Cent ist nicht moeglich.");
    }

    public GeldEntnahmeNichtMoeglichException(int wert, Throwable e) {
        super("Geldentnahme in Hoehe von " + wert + " Cent ist nicht moeglich. Es ist ein uebergeordneter Fehler aufgetreten.", e);
    }

}
