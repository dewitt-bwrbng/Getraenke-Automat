public class Artikel {

    private String name;
    private int preis;

    public Artikel(String name, int preis) {
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
