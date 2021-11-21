public class Vertice {
    public final String name;

    public Vertice(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return name.compareToIgnoreCase(((Vertice) obj).name) == 0;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
