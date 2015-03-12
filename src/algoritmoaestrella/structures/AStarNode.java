/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algoritmoaestrella.structures;

/**
 *
 * @author Dani
 */
public class AStarNode extends Node{
    
    //Guardamos la posicion del nodo padre
    private Position padre;
    
    private boolean abierto;
    
    private double g;
    
    private double h;
    
    //Cuando haya mas coste por pasar por ahi
    private double f_prima;

    public AStarNode() {
        //Por defecto es 0
        
        f_prima = 0; 
    }
    
    public double getF(){
        return g + h + f_prima;
    }

    public Position getPadre() {
        return padre;
    }

    public void setPadre(Position padre) {
        this.padre = padre;
    }

    public boolean isAbierto() {
        return abierto;
    }

    public void setAbierto(boolean abierto) {
        this.abierto = abierto;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF_prima() {
        return f_prima;
    }

    public void setF_prima(double f_prima) {
        this.f_prima = f_prima;
    }
}
