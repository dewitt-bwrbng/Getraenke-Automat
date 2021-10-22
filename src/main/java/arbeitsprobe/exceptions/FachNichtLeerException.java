package arbeitsprobe.exceptions;

public class FachNichtLeerException extends Exception {

    public FachNichtLeerException() {
        super("Das Fach ist nicht leer.");
    }

}
