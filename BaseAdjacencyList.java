import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BaseAdjacencyList {
    public final Map<Vertice, List<Vertice>> adjacencyList;

    protected BaseAdjacencyList() {
        adjacencyList = new HashMap<>();
    }

    public void showAdjacencyList() {
        Iterator<Vertice> listIterator;

        for (Map.Entry<Vertice, List<Vertice>> entry : adjacencyList.entrySet()) {
            listIterator = entry.getValue().iterator();
            System.out.print(listIterator.next().name + " -> ");

            while (listIterator.hasNext()) {
                System.out.print(listIterator.next().name + " ; ");
            }

            System.out.println();
        }
    }
}
