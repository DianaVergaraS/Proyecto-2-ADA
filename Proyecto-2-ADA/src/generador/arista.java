package generador;

public class arista implements Comparable <arista>{
    
    private int A1, A2; // para identificar nodos
    private int ID; // identificador de arista
    private double printarista;
    private boolean flag_arista;
    
    
    public arista (int an_from, int an_to){
        this.A1=an_from;
        this.A2=an_to;
        this.printarista=0;
        this.flag_arista=false;
    }
    
    public arista (int an_from, int an_to, double pb){
        this.A1=an_from;
        this.A2=an_to;
        this.printarista=pb;
        this.flag_arista=false;
    }
    
    public arista (arista ax){
        this.A1=ax.getan_from();
        this.A2=ax.getan_to();
        this.printarista=ax.getP();
        this.flag_arista=ax.getfal();
    }
    
    public int getan_from (){
        return this.A1;
    }
    public int getan_to (){
        return this.A2;
    }
    public int getID(){
        return this.ID;
    }
    public boolean getfal(){
        return this.flag_arista;
    }
    public void setAn1 (int idNodo){
        this.A1 = idNodo;
    }
    public void setAn2 (int idNodo){
        this.A2 = idNodo;
    }
    public void setID (int a){
        this.ID = a;
    }
    public void setfal (boolean a){
        this.flag_arista = a;
    }
    public void setP (double p){
        this.printarista=p;
    }
    public double getP (){
        return this.printarista;
    }
    
    public void Cop (arista ay){
        this.A1=ay.getan_from();
        this.A2=ay.getan_to();
        this.printarista=ay.getP();
    }
    
@Override
public int compareTo(arista A1){
    if (this.printarista > A1.getP()) return 1;
    else if (this.printarista < A1.getP()) return -1;
    else return 0;
}
}
