/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algoritmoaestrella.view;

import algoritmoaestrella.structures.Position;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javafx.util.Pair;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Dani
 */
public class Box extends javax.swing.JPanel implements MouseListener {
    
    //Referencia al tablero, para poder acceder a la foto que debe ponerse de fondo
    private Board tablero;
    
    //Imagen del fondo
    private ImageIcon fondo;
    
    //Nombre de la imagen del fondo
    private String imagenFondo;
    
    private static int [] casillaMarcada = new int[2];
    
    public Box() {        
        // este constructor no se usará, se deja para poder crear el bean.        
    }
    
    public Box(Board t) {
        initComponents();        
        this.tablero = t;
        if(this.tablero.getTipoTablero() == true){// tablero responde a clics?
            this.addMouseListener(this);
        }
    }
    
    public void setFondo(ImageIcon fondo){
        this.fondo = fondo;
    }
    
    public ImageIcon getFondo(){        
        return this.fondo;
    }
    
                          
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 161, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 193, Short.MAX_VALUE)
        );
    }                       
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(fondo.getImage(), 0,0,this.getWidth(),this.getHeight(),this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e){}
    
    @Override
    public void mouseEntered(MouseEvent e){}
    
    @Override
    public void mouseExited(MouseEvent e){}
    
    @Override
    public void mousePressed(MouseEvent e){
        
        //Valoración de la meta
        if(tablero.getTocadoString().equals(ConstantesGUI.meta)){
            
            if(tablero.isMetaPuesta()){
                
              new JOptionPane().showMessageDialog(null, "ERROR!! Solo puedes introducir una meta", "ERROR", JOptionPane.ERROR_MESSAGE);    
            }
            else{
                
                this.setCasillaMarcada(tablero.getCoordenadas((Box)e.getComponent())); 
        
                this.tablero.pintar(this.getCasillaMarcada()[0],this.getCasillaMarcada()[1]);
                
                imagenFondo = this.tablero.getTocadoString();
                
                tablero.setMetaPuesta(true);
            }
        }
        else{
            this.setCasillaMarcada(tablero.getCoordenadas((Box)e.getComponent())); 
        
            this.tablero.pintar(this.getCasillaMarcada()[0],this.getCasillaMarcada()[1]); 

            //Si es un waypoint, guardamos la posición para que los recorra en orden
            if(tablero.getTocadoString().equals(ConstantesGUI.waypoint)){

                //se crea la posicion con las casillas marcadas al revés
                Position p = new Position(this.getCasillaMarcada()[1],this.getCasillaMarcada()[0]);

                tablero.getLista_waypoints().add(p);
            }
            
            //Si es un peligro
            if(tablero.getTocadoString().equals(ConstantesGUI.peligro)){
                
                //se crea la posicion con las casillas marcadas al revés
                Position p = new Position(this.getCasillaMarcada()[1],this.getCasillaMarcada()[0]);
                
                tablero.getPeligros().add(new Pair(p,tablero.getFactor_peligro()));
            }

            imagenFondo = this.tablero.getTocadoString();
        }    
    }
    
    @Override
    public void mouseReleased(MouseEvent e){}
    
    public static int[] getCasillaMarcada() {
        return casillaMarcada;
    }
    public static void setCasillaMarcada(int[] aCasillaMarcada) {
        casillaMarcada = aCasillaMarcada;
    }                  

    public Board getTablero() {
        return tablero;
    }

    public void setTablero(Board tablero) {
        this.tablero = tablero;
    }

    public String getImagenFondo() {
        return imagenFondo;
    }

    public void setImagenFondo(String imagenFondo) {
        this.imagenFondo = imagenFondo;
    }  
}