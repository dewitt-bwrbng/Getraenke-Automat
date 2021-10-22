package arbeitsprobe.exceptions;

public class KaufFehlgeschlagenException extends Exception {

    public KaufFehlgeschlagenException(int fachIndex, Throwable t) {
        super("Der Artikel in Fach " + fachIndex + " konnte nicht gekauft werden.", t);
    }
}
