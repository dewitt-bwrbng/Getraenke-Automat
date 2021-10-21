package arbeitsprobe.automat.exceptions;

import arbeitsprobe.automat.Muenze;

public class MuenzeNichtVorhandenException extends Throwable {

    public MuenzeNichtVorhandenException(Muenze muenze) {
        super("Es sind keine " + muenze.name() + " Muenzen im Speicher vorhanden.");
    }

}
