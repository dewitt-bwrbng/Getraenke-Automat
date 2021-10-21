package arbeitsprobe.automat.exceptions;

public class UngueltigesFachException extends Exception {
    public UngueltigesFachException(int fachIndex) {
        super("Uebergebenes Fach mit dem Index " + fachIndex + " existiert nicht.");
    }
}
