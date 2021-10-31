public class Vertice {
    public final String name;

    public Vertice(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return name.compareTo(((Vertice) obj).name) == 0;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
