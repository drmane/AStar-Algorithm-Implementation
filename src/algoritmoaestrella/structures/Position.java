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
public class Position {
    
    private int positionX;
    
    private int positionY;

    /**
     * Constructor de la calse posicion
     * @param positionX componente X
     * @param positionY componente Y
     */
    public Position(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
    
    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }  
}
