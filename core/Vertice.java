<<<<<<< HEAD:core/Vertice.java
package AboutGraphs.core;

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
=======
package AboutGraphs.core;

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
>>>>>>> 1b387950559dd2ef737390340f12853d43947502:graph_core/Vertice.java
