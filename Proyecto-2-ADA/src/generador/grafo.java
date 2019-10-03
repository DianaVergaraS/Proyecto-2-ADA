package generador;

import java.io.FileWriter; //Para escribir archivos
import java.io.PrintWriter; //Para imprimir archivos
import java.util.HashMap;  //Para las claves únicas
import java.util.Stack;

public class grafo {

    private HashMap<Integer, arista> Aristas;
    private HashMap<Integer, vertice> Vertices;


    public grafo(HashMap<Integer, vertice> N, HashMap<Integer, arista> A) {
        this.Aristas = new HashMap();
        for (int i = 0; i < A.size(); i++) {
            this.Aristas.put(i, new arista(A.get(i)));
        }
        this.Vertices = new HashMap();
        for (int i = 0; i < N.size(); i++) {
            this.Vertices.put(i, new vertice(N.get(i)));
        }
    }

    public grafo() {
        this.Aristas = new HashMap();
        this.Vertices = new HashMap();
    }

    public grafo(grafo k) {
        this.Aristas = new HashMap();
        for (int i = 0; i < k.getAristas().size(); i++) {
            this.Aristas.put(i, new arista(k.getAristas().get(i)));
        }
        this.Vertices = new HashMap();
        for (int i = 0; i < k.getVertices().size(); i++) {
            this.Vertices.put(i, new vertice(k.getVertices().get(i)));
        }
    }

    public void setNodos(HashMap<Integer, vertice> w) {
        this.Vertices = w;
    }

    public void setAristas(HashMap<Integer, arista> w) {
        this.Aristas = w;
    }

    public HashMap<Integer, arista> getAristas() {
        return this.Aristas;
    }

    public HashMap<Integer, vertice> getVertices() {
        return this.Vertices;
    }

    // Método de Erdos-Rényi
    public static grafo ErdosRenyi(int NumVertices, int NumAristas, int dirigido) {
        HashMap<Integer, vertice> VerticeS = new HashMap();
        HashMap<Integer, arista> AristaS = new HashMap();

        int AristasHechas;

        for (int i = 0; i < NumVertices; i++) {
            VerticeS.put(i, new vertice(i));
        }

        int x1 = (int) (Math.random() * NumVertices), x2 = (int) (Math.random() * NumVertices);
        AristaS.put(0, new arista(VerticeS.get(x1).get(), VerticeS.get(x2).get(), Math.random()));

        while (x1 == x2 && dirigido == 0) {
            x1 = (int) (Math.random() * NumVertices);
            x2 = (int) (Math.random() * NumVertices);
            AristaS.put(0, new arista(VerticeS.get(x1).get(), VerticeS.get(x2).get(), Math.random()));
        }

        VerticeS.get(x1).conectar();
        VerticeS.get(x2).conectar();
        if (x1 != x2) {
            VerticeS.get(x1).IncGrado(1);
        }
        VerticeS.get(x2).IncGrado(1);

        AristasHechas = 1;
        while (AristasHechas < NumAristas) {
            x1 = (int) (Math.random() * NumVertices);
            x2 = (int) (Math.random() * NumVertices);

            if (x1 != x2 || dirigido == 1) {
                int c1 = 1, cont = 0;
                while (c1 == 1 && cont < AristasHechas) {
                    int a = AristaS.get(cont).getan_from(), b = AristaS.get(cont).getan_to();
                    if ((x1 == a && x2 == b) || (x1 == b && x2 == a)) {
                        c1 = 0;
                    }
                    cont++;
                }
                if (c1 == 1) {
                    AristaS.put(AristasHechas, new arista(VerticeS.get(x1).get(), VerticeS.get(x2).get(), Math.random()));
                    VerticeS.get(x1).conectar();
                    VerticeS.get(x2).conectar();
                    if (x1 != x2) {
                        VerticeS.get(x1).IncGrado(1);
                    }
                    VerticeS.get(x2).IncGrado(1);
                    AristasHechas++;
                }
            }
        }

       
        grafo G = new grafo(VerticeS, AristaS);
        return G;

    }
    
 // Método de Gilbert
    public static grafo Gilbert(int NumVertices, double probabilidad, int dirigido) {
        HashMap<Integer, vertice> VerticeS = new HashMap();
        HashMap<Integer, arista> AristaS = new HashMap();

        int NumAristas = 0;

        for (int i = 0; i < NumVertices; i++) {
            VerticeS.put(i, new vertice(i));
        }

        for (int i = 0; i < NumVertices; i++) {
            for (int j = i; j < NumVertices; j++) {
                if (j != i || dirigido == 1) {
                    if (Math.random() <= probabilidad) {
                        AristaS.put(NumAristas, new arista(VerticeS.get(i).get(), VerticeS.get(j).get()));
                        VerticeS.get(i).conectar();
                        VerticeS.get(j).conectar();
                        if (i != j) {
                            VerticeS.get(i).IncGrado(1);
                        }
                        VerticeS.get(j).IncGrado(1);
                        NumAristas++;
                    }
                }
            }
        }
        grafo G = new grafo(VerticeS, AristaS);
        return G;
    }
    
    //Método Geográfico
    public static grafo Geografico(int NumVertices, double distancia, int dirigido) {
        HashMap<Integer, vertice> VerticeS = new HashMap();
        HashMap<Integer, arista> AristaS = new HashMap();
        int NumAristas = 0;

        for (int i = 0; i < NumVertices; i++) {
            VerticeS.put(i, new vertice(i, Math.random(), Math.random()));
        }

        for (int i = 0; i < NumVertices; i++) {
            for (int j = i; j < NumVertices; j++) {
                if (j != i || dirigido == 1) {
                    double dis = Math.sqrt(Math.pow(VerticeS.get(j).getX() - VerticeS.get(i).getX(), 2)
                            + Math.pow(VerticeS.get(j).getY() - VerticeS.get(i).getY(), 2));
                    if (dis <= distancia) {
                        AristaS.put(NumAristas, new arista(VerticeS.get(i).get(), VerticeS.get(j).get()));
                        VerticeS.get(i).IncGrado(1);
                        VerticeS.get(i).conectar();
                        if (j != i) {
                            VerticeS.get(j).IncGrado(1);
                            VerticeS.get(j).conectar();
                        }
                        NumAristas++;
                    }
                }
            }
        }
        grafo G = new grafo(VerticeS, AristaS);
        return G;
    }

// Método Barábasi
    public static grafo Barabasi(int NumVertices, double d, int dirigido) {
        HashMap<Integer, vertice> VerticeS = new HashMap();
        HashMap<Integer, arista> AristaS = new HashMap();

        int NumAristas = 0;

        for (int i = 0; i < NumVertices; i++) {
            VerticeS.put(i, new vertice(i));
        }

        for (int i = 0; i < NumVertices; i++) {
            int j = 0;
            while (j <= i && VerticeS.get(i).getGrado() <= d) {
                if (j != i || dirigido == 1) {
                    if (Math.random() <= 1 - VerticeS.get(j).getGrado() / d) {
                        AristaS.put(NumAristas, new arista(VerticeS.get(i).get(), VerticeS.get(j).get()));
                        VerticeS.get(i).IncGrado(1);
                        VerticeS.get(i).conectar();
                        if (j != i) {
                            VerticeS.get(j).IncGrado(1);
                            VerticeS.get(j).conectar();
                        }
                        NumAristas++;
                    }
                }
                j++;
            }
        }
        grafo G = new grafo(VerticeS, AristaS);
        return G;
    }

    public static void construir(String nombre, grafo g) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        System.out.println(g.getVertices().size());
        try {
            fichero = new FileWriter(nombre + ".gv");
            pw = new PrintWriter(fichero);
            pw.println("graph {"); //Para Gephi
            pw.println();
            for (int i = 0; i < g.getAristas().size(); i++) {
                pw.println(g.getAristas().get(i).getan_from() + "--" + g.getAristas().get(i).getan_to() + "  " + " ");
            }
            pw.println("}"); // print vértices y aristas

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    
    public void setG(HashMap<Integer, vertice> a, HashMap<Integer, arista> b) {
        this.Vertices = (HashMap) a;
        this.Aristas = (HashMap) b;
    }
    
    public static grafo BFS(grafo G1, vertice nod) {
        grafo G = new grafo(G1.getVertices(), G1.getAristas());
        HashMap<Integer, HashMap> Ls = new HashMap();
        HashMap<Integer, vertice> Ln1 = new HashMap();
        HashMap<Integer, vertice> Ln2 = new HashMap();
        HashMap<Integer, vertice> V = new HashMap();
        HashMap<Integer, arista> Edg = new HashMap();
        int numL = 0, cv = 0, num = 0;

        G.getVertices().get(nod.get()).setfal(true);
        Ln1.put(0, G.getVertices().get(nod.get()));
        Ls.put(numL, (HashMap) Ln1.clone());
        V.put(cv, G.getVertices().get(nod.get()));

        while (Ln1.isEmpty() == false) {
            Ln2.clear();
            num = 0;
            for (int i = 0; i < Ln1.size(); i++) {
                for (int j = 0; j < G.getAristas().size(); j++) {
                    if (Ln1.get(i).get() == G.getAristas().get(j).getan_from() && G.getVertices().get(G.getAristas().get(j).getan_to()).getfal() == false) {
                        G.getVertices().get(G.getAristas().get(j).getan_to()).setfal(true);
                        Ln2.put(num, G.getVertices().get(G.getAristas().get(j).getan_to()));
                        num++;
                        Edg.put(cv, G.getAristas().get(j));
                        cv++;
                        V.put(cv, G.getVertices().get(G.getAristas().get(j).getan_to()));
                    }
                    if (Ln1.get(i).get() == G.getAristas().get(j).getan_to() && G.getVertices().get(G.getAristas().get(j).getan_from()).getfal() == false) {
                        G.getVertices().get(G.getAristas().get(j).getan_from()).setfal(true);
                        Ln2.put(num, G.getVertices().get(G.getAristas().get(j).getan_from()));
                        num++;
                        Edg.put(cv, G.getAristas().get(j));
                        cv++;
                        V.put(cv, G.getVertices().get(G.getAristas().get(j).getan_from()));
                    }
                }
            }
            numL++;
            Ln1 = (HashMap) Ln2.clone();
            Ls.put(numL, (HashMap) Ln2.clone());
        }
        grafo A = new grafo();
        A.setG(V, Edg);
        return A;
    }
    
    
    public static grafo DFS_I(grafo G1, vertice nod) {
        grafo G = new grafo(G1.getVertices(), G1.getAristas());
        grafo A = new grafo();
        int z, cA = 0;
        boolean fl;

        boolean MA[][] = new boolean[G.getVertices().size()][G.getVertices().size()];
        
        for (int i = 0; i < G.getAristas().size(); i++) {
            if (G.getAristas().get(i).getfal() == false) {
                MA[G.getAristas().get(i).getan_from()][G.getAristas().get(i).getan_to()]=true;
                MA[G.getAristas().get(i).getan_to()][G.getAristas().get(i).getan_from()]=true;
            }
        }

        Stack<Integer> pila = new Stack<>();
        pila.push(G.getVertices().get(nod.get()).get());
        G.getVertices().get(nod.get()).setfal(true);
        A.getVertices().put(cA, new vertice(G.getVertices().get(nod.get())));

        while (pila.isEmpty() == false) {
            z = pila.peek();
            fl = false;
            for (int i = 0; i < G.getVertices().size(); i++) {
                if (MA[z][i] == true && G.getVertices().get(i).getfal() == false) {
                    G.getVertices().get(i).setfal(true);
                    A.getAristas().put(cA, new arista(z, i));
                    cA++;
                    A.getVertices().put(cA, new vertice(G.getVertices().get(i)));
                    pila.push(i);
                    fl = true;
                    i = G.getVertices().size();
                }
                if (i == G.getVertices().size() - 1 && fl == false) {
                    pila.pop();
                }
            }
        }

    
        return A;
    }

    public static grafo DFS_R(grafo G, vertice N0) {
        grafo A = new grafo();
        grafo B;
        boolean MA[][] = new boolean[G.getVertices().size()][G.getVertices().size()];
        for (int i = 0; i < G.getAristas().size(); i++) {
            MA[G.getAristas().get(i).getan_from()][G.getAristas().get(i).getan_to()] = true;
            MA[G.getAristas().get(i).getan_to()][G.getAristas().get(i).getan_from()] = true;
        }
        G.getVertices().get(N0.get()).setfal(true);
        A.getVertices().put(0, new vertice(G.getVertices().get(N0.get())));
        for (int i = 0; i < G.getVertices().size(); i++) {
            if (MA[N0.get()][i] == true && G.getVertices().get(i).getfal() == false) {
                B = DFS_R(G, G.getVertices().get(i));
                int tN = A.getVertices().size();
                for (int j = 0; j < B.getVertices().size(); j++) {
                    A.getVertices().put(tN + j, B.getVertices().get(j));
                }
                A.getAristas().put(A.getAristas().size(), new arista(N0.get(), i));
                tN = A.getAristas().size();
                if (B.getAristas().isEmpty() != true) {
                    for (int j = 0; j < B.getAristas().size(); j++) {
                        A.getAristas().put(tN + j, B.getAristas().get(j));
                    }
                }
            }
        }
        return A;
    }
    
    public static void main(String[] args) {
    	//Primera Entrega
        grafo grafo_creado = new grafo();
        grafo_creado = ErdosRenyi(30,50,0);
        //grafo_creado = Gilbert(30,.4,0);
        //grafo_creado= Geografico(30,.4,0);        
        //grafo_creado = Barabasi(30,50,0);
        construir("ER1",grafo_creado);
        
        //Segunda Entrega
        grafo grafoBFS = new grafo(BFS(grafo_creado,grafo_creado.getVertices().get(0)));
        construir("grafoBFS",grafoBFS);
        grafo grafoDFSI = new grafo(DFS_I(grafo_creado,grafo_creado.getVertices().get(0)));
        construir("grafoDFS-I",grafoDFSI);
        grafo grafoDFSR = new grafo(DFS_R(grafo_creado,grafo_creado.getVertices().get(0)));
        construir("grafoDFS-R",grafoDFSR);
        
    }
}
