package arbeitsprobe.exceptions;

public class FachNichtZugewiesenException extends Throwable {
    public FachNichtZugewiesenException(int fach) {
        super("Dem Fach " + fach  + " ist kein Artikel zugewiesen");
    }
}
