package arbeitsprobe.automat.artikel;

public final class Artikel {

    private String name;
    private int preis;

    // Nur im Paket arbeitsprobe.automat.artikel verwendbar.
    // Artikel sollen nur ueber den ArtikelManager erzeugt werden.
    Artikel(String name, int preis) {
        this.name = name;
        this.preis = preis;
    }

    public String holeName() {
        return name;
    }

    public void setzeName(String name) {
        this.name = name;
    }

    public int holePreis() {
        return preis;
    }

    public void setzePreis(int preis) {
        this.preis = preis;
    }
}
