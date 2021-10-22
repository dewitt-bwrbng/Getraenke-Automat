package arbeitsprobe.exceptions;

public class MindestwertUnterschrittenException extends Exception {

    public MindestwertUnterschrittenException(int wert, int mindestWert) {
        super("Der Wert " + wert + " unterschreitet den Mindestwert " + mindestWert + ".");
    }

}
