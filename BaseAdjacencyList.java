import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseAdjacencyList {
    public List<List<Vertice>> adjacencyList;

    protected BaseAdjacencyList() {
        adjacencyList = new ArrayList<List<Vertice>>();
    }

    public void showAdjacencyList() {
        Iterator<Vertice> listIterator;

        for (List<Vertice> list : adjacencyList) {
            listIterator = list.iterator();
            System.out.print(listIterator.next().name + " -> ");

            while (listIterator.hasNext()) {
                System.out.print(listIterator.next().name + " ; ");
            }

            System.out.println();
        }
    }

    public List<Vertice> getInnerListWithThisHead(Vertice listHead) {
        for (List<Vertice> list : adjacencyList) {
            if (list.get(0).name.compareTo(listHead.name) == 0) {
                return list;
            }
        }

        return null;
    }
}
