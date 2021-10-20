package exceptions;

public class MindestwertUnterschrittenException extends Throwable {

    public MindestwertUnterschrittenException(int wert, int mindestWert) {
        super("Der Wert " + wert + " unterschreitet den Mindestwert " + mindestWert + ".");
    }

}
