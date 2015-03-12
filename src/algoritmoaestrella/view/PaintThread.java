/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algoritmoaestrella.view;

import algoritmoaestrella.structures.Position;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Javi
 */
public class PaintThread extends Thread{
    
    private final Position salida;
    
    private final Position meta;
    
    private final List<Position> solucion;
    
    private final Board tablero;

    public PaintThread(Position salida, Position meta, List<Position> solucion, Board tablero) {
        this.salida = salida;
        this.meta = meta;
        this.solucion = solucion;
        this.tablero = tablero;
    }

    @Override
    public void run(){
        
        //No se puede editar el tablero mientras se pinta
        tablero.setTipoTablero(false);
        
        //Consideramos los obstaculos como si el programa los pintase
        //Por eso pinta un obstaculo es como pintar cuando se toca
        
        tablero.setTocadoString(ConstantesGUI.paso);
            
        tablero.cargarImagenes();
        
        for(Position p: solucion){
                     
                tablero.pintar(p.getPositionY(), p.getPositionX());
        
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                Logger.getLogger(PaintThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Volvemos a activar la edición del tablero
        tablero.setTipoTablero(true);
    }
}
