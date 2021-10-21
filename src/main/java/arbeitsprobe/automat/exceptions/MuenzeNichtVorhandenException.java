package arbeitsprobe.automat.exceptions;

import arbeitsprobe.automat.Muenze;

public class MuenzeNichtVorhandenException extends Exception {

    public MuenzeNichtVorhandenException(Muenze muenze) {
        super("Es sind keine " + muenze.name() + " Muenzen im Speicher vorhanden.");
    }

}
