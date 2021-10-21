package arbeitsprobe.automat.artikel;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artikel artikel = (Artikel) o;
        return preis == artikel.preis && Objects.equals(name, artikel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, preis);
    }
}
