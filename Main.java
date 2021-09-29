import java.util.Scanner;

public class Main {

    public static Scanner in = new Scanner(System.in);
    public static int quantNos;
    public static int quantArestas;
    public static int[] no;

    public static void main(String args[]) {
        String entry = in.nextLine();
        Arestas[] arestas = readEntry(entry);
        ordenaArestasA(arestas);

        //Matriz de Incidência
        System.out.println("\n-- Matriz de Incidência --\n");
        MatrixIncidency matrixI = new MatrixIncidency(quantNos, quantArestas);
        matrixI.fillMatrixI(arestas);
        matrixI.printMatrixI(quantNos, quantArestas);

        //Matriz de Adjacência
        System.out.println("\n-- Matriz de Adjacência --\n");
        MatrixAdjacency matrixA = new MatrixAdjacency(quantNos);
        matrixA.fillMatrixA(arestas, quantNos);
        matrixA.printMatrixA(quantNos);

        //Lista de Adjacência Sucessores
        System.out.println("\n-- Lista de Adjacência Sucessores--\n");
        ordenaArestasA(arestas);
        ADJLSuccessors adjListS = new ADJLSuccessors(quantNos, no, quantArestas);
        adjListS.fillSuccessors(arestas);
        adjListS.printADJSUccessors();

        //Lista de Adjacência Antecessores
        System.out.println("\n-- Lista de Adjacência Antecessores--\n");
        ordenaArestasB(arestas);
        ADJLAntecessors adjListA = new ADJLAntecessors(quantNos, no, quantArestas);
        adjListA.fillAntecessors(arestas);
        adjListA.printADJAntecessors();

        //Lista de Adjacência Sucessores - Vector
        System.out.println("\n-- Lista de Adjacência Sucessores - Vector --\n");
        ordenaArestasA(arestas);
        adjListS.fillVectorADJS2();
        adjListS.printVectorADJS();

        //Lista de Adjacência Sucessores - Vector
        System.out.println("\n-- Lista de Adjacência Antecessores - Vector --\n");
        ordenaArestasB(arestas);
        adjListA.fillVectorADJA();
        adjListA.printVectorADJA();
    }

    public static Arestas[] readEntry(String entry){
        System.out.println(entry);
        entry = replace(entry);
        quantNos = countNos(entry);
        quantArestas = entry.length()/2;
        defineNos(entry);

        Arestas[] n = new Arestas[quantArestas];
        for(int i=0, c=0; c<quantArestas; i+=2, c++){
            n[c] = new Arestas(Character.getNumericValue(entry.charAt(i)),Character.getNumericValue(entry.charAt(i+1)));
        }
        return n;
    }

    public static String replace(String entry){
        entry = entry.replace("{", "");
        entry = entry.replace("(", "");
        entry = entry.replace(",", "");
        entry = entry.replace(";", "");
        entry = entry.replace(")", "");
        entry = entry.replace("}", "");
        return entry;
    }

    public static int countNos(String entry){
        int n=0;
        do{
            entry = entry.replaceAll(Character.toString(entry.charAt(0)), "");
            n++;
        }while(entry.length()>0);
        return n;
    }

    public static void defineNos(String entry){
        int n=0;
        no = new int[quantNos];
        do{
            no[n] = Character.getNumericValue(entry.charAt(0));
            entry = entry.replaceAll(Character.toString(entry.charAt(0)), "");
            n++;
        }while(entry.length()>0);

        //ordena os nós
        for (int i = 0; i < (no.length - 1); i++) {
            int menor = i;
            for (int j = (i + 1); j < no.length; j++){
                int comp = (no[menor] > (no[j])) ? 1:0;
               if (comp>0){
                  menor = j;
               }
            }
            int temp = no[menor];
            no[menor] = no[i];
            no[i] = temp;
         }
    }

    public static Arestas[] ordenaArestasA(Arestas arest[]){
        for (int i = 0; i < (arest.length - 1); i++) {
            int menor = i;
            for (int j = (i + 1); j < arest.length; j++){
                int comp = (arest[menor].getA() > (arest[j].getA())) ? 1:0;
               if (comp>0){
                  menor = j;
               }
            }
            swap(menor, i, arest);
         }
         return arest;
    }

    public static Arestas[] ordenaArestasB(Arestas arest[]){
        for (int i = 0; i < (arest.length - 1); i++) {
            int menor = i;
            for (int j = (i + 1); j < arest.length; j++){
                int comp = (arest[menor].getB() > (arest[j].getB())) ? 1:0;
               if (comp>0){
                  menor = j;
               }
            }
            swap(menor, i, arest);
         }
         return arest;
    }
  
    public static void swap(int i, int j, Arestas arest[]) {
        Arestas[] temp = new Arestas[1];
        temp[0]=new Arestas(arest[i].getA(),arest[i].getB());
        arest[i].setA(arest[j].getA());
        arest[i].setB(arest[j].getB());
        arest[j].setA(temp[0].getA());
        arest[j].setB(temp[0].getB());
     }

     public static void printArestas(Arestas[] arest){
        System.out.println("\nPRINT\n");
         for(int i=0; i<arest.length; i++){
             System.out.println("("+arest[i].getA()+","+arest[i].getB()+")");
         }
     }

}

class Arestas{
    private int a;
    private int b;

    public Arestas(int a, int b){
        this.a = a;
        this.b = b;
    }

    public void setA(int i){
        this.a = i;
    }

    public int getA(){
        return this.a;
    }

    public void setB(int i){
        this.b = i;
    }

    public int getB(){
        return this.b;
    }

}

class MatrixIncidency{
    public int[][] matrix;

    public MatrixIncidency(int n, int m){
        this.matrix = new int[n][m];
        for(int i=0; i<n; i++){
            for(int x=0; x<m; x++){
                this.matrix[i][x] = 0;
            }
        } 
    }

    public void fillMatrixI(Arestas[] arestas){
        for(int i=0; i<arestas.length; i++){
            this.matrix[arestas[i].getA()-1][i] = 1;
            this.matrix[arestas[i].getB()-1][i] = -1;
        }
    }

    public void printMatrixI(int n, int m){
        for(int i=0; i<n; i++){
            for(int x=0; x<m; x++){
                System.out.print(this.matrix[i][x] + "   ");
            }
            System.out.println("");
        }
    }

}

class MatrixAdjacency{
    public int[][] matrix;

    public MatrixAdjacency(int n){
        this.matrix = new int[n][n];
        for(int i=0; i<n; i++){
            for(int x=0; x<n; x++){
                this.matrix[i][x] = 0;
            }
        } 
    }

    public void fillMatrixA(Arestas[] arestas, int quantNos){
        for(int i=0; i<arestas.length; i++){
            this.matrix[arestas[i].getA()-1][arestas[i].getB()-1] = 1;
        }
    }

    public void printMatrixA(int n){
        for(int i=0; i<n; i++){
            for(int x=0; x<n; x++){
                System.out.print(this.matrix[i][x] + "   ");
            }
            System.out.println("");
        }
    }
}

class AdjacencyList{
    protected NoLista[] vet;

    public AdjacencyList(int quantNos, int[] no){
        this.vet = new NoLista[quantNos];
        for(int i=0; i<quantNos; i++){
            this.vet[i] = new NoLista(no[i], null);
        }
    }
}

class ADJLSuccessors extends AdjacencyList{

    int[] vectorOneADJList;
    int[] vectorTwoADJList;

    public ADJLSuccessors(int quantNos, int[] no, int quantArestas) {
        super(quantNos, no);
        this.vectorOneADJList = new int[quantArestas];
        this.vectorTwoADJList = new int[quantArestas];
    }

    public void fillSuccessors(Arestas[] arestas){
        NoLista temp;
        for(int i=0; i<this.vet.length; i++){
            temp = this.vet[i];
            for(int x=0; x<arestas.length; x++){
                if(arestas[x].getA() == this.vet[i].getValue()){
                    temp.prox = new NoLista(arestas[x].getB());
                    temp = temp.prox;
                }
            }
        }
    }

    public void printADJSUccessors(){
        NoLista temp;
        for(int i=0; i<this.vet.length; i++){
            temp = this.vet[i];
            while(temp!=null){
                System.out.print(temp.getValue() + " ");
                temp = temp.prox;
            }
            System.out.println("");
        }
    }

    public void fillVectorADJS(){
        NoLista temp;
        int n=0;
        for(int i=0; i<this.vet.length; i++){
            temp = this.vet[i].prox;
            while(temp!=null){
                this.vectorOneADJList[n] = this.vet[i].getValue();
                this.vectorTwoADJList[n] = temp.getValue();
                temp = temp.prox;
                n++;
            }
        }
    }

    public void fillVectorADJS2(){
        NoLista temp;
        int n=0;
        int i=0;
        for(i=0; i<this.vet.length; i++){
            temp = this.vet[i].prox;
            this.vectorOneADJList[i] = n+1;
            while(temp!=null){
                this.vectorTwoADJList[n] = temp.getValue();
                temp = temp.prox;
                n++;
            }
        }
        this.vectorOneADJList[i]= n+1;
    }

    public void printVectorADJS(){
        for(int i=0; i<this.vectorOneADJList.length; i++){
            System.out.println(this.vectorOneADJList[i] + " -> " + this.vectorTwoADJList[i]);
        }
    }


}

class ADJLAntecessors extends AdjacencyList{

    int[] vectorOneADJList;
    int[] vectorTwoADJList;

    public ADJLAntecessors(int quantNos, int[] no, int quantArestas) {
        super(quantNos, no);
        this.vectorOneADJList = new int[quantArestas];
        this.vectorTwoADJList = new int[quantArestas];
    }

    public void fillAntecessors(Arestas[] arestas){
        NoLista temp;
        for(int i=0; i<this.vet.length; i++){
            temp = this.vet[i];
            for(int x=0; x<arestas.length; x++){
                if(arestas[x].getB() == this.vet[i].getValue()){
                    temp.prox = new NoLista(arestas[x].getA());
                    temp = temp.prox;
                }
            }
        }
    }

    public void printADJAntecessors(){
        NoLista temp;
        for(int i=0; i<this.vet.length; i++){
            temp = this.vet[i];
            while(temp!=null){
                System.out.print(temp.getValue() + " ");
                temp = temp.prox;
            }
            System.out.println("");
        }
    }

    public void fillVectorADJA(){
        NoLista temp;
        int n=0;
        for(int i=0; i<this.vet.length; i++){
            temp = this.vet[i].prox;
            while(temp!=null){
                this.vectorOneADJList[n] = this.vet[i].getValue();
                this.vectorTwoADJList[n] = temp.getValue();
                temp = temp.prox;
                n++;
            }
        }
    }

    /*public void fillVectorADJA2(){
        NoLista temp;
        int n=0;
        int i=0;
        for(i=0; i<this.vet.length; i++){
            temp = this.vet[i].prox;
            this.vectorOneADJList[i] = n+1;
            while(temp!=null){
                this.vectorTwoADJList[n] = temp.getValue();
                temp = temp.prox;
                n++;
            }
        }
        this.vectorOneADJList[i]= n+1;
    }*/

    public void printVectorADJA(){
        for(int i=0; i<this.vectorOneADJList.length; i++){
            System.out.println(this.vectorOneADJList[i] + " -> " + this.vectorTwoADJList[i]);
        }
    }


}

class NoLista{
    protected int value;
    protected NoLista prox;

    public NoLista(int v, NoLista p){
        this.value = v;
        this.prox = p;
    }

    public NoLista(int v){
        this.value = v;
        this.prox = null;
    }
    public void setValue(int v){
        this.value = v;
    }

    public int getValue(){
        return this.value;
    }

}

