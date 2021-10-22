package arbeitsprobe.exceptions;

public class UngueltigerPreisException extends Exception{
    public UngueltigerPreisException(int preis) {
        super("Der eingegebene Preis von " + preis + " ist ungültig. Preise müssen größer als 0 sein und da die kleinste Münze einen Wert von 10 Cent beträgt muss der Preis durch 10 Teilbar sein.");
    }
}
