/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algoritmoaestrella.view;

import algoritmoaestrella.structures.Position;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;
import javax.swing.ImageIcon;

public class Board extends javax.swing.JPanel {
    
    //Indica la imagen del fondo de la casilla y la imagen que se pone al hacer click
    private ImageIcon fondo, tocado;
    
    //String que indica que imagen se pone al hacer click
    private String tocadoString;
    
    //Indica si el tablero es editable o no
    private boolean tipoTablero;
    
    //Número de columnas y de filas
    private int columnas;
    private int filas;
    
    //Las casillas del tablero
    private Box [][] casillas ;
    
    //Tamaño en pixeles
    private int tamañoCasilla = 36;
    
    //Indica si se ha insertado una meta en el tablero
    private boolean metaPuesta;
    
    private List<Position> lista_waypoints;
    
    private List< Pair<Position,Double> > peligros;
    
    private double factor_peligro;
        
    public Board() {
        initComponents(351,351);

        metaPuesta = false;
        
        factor_peligro = 0;
          
        lista_waypoints = new LinkedList<Position>();
        
        peligros = new ArrayList< Pair<Position,Double> >();
    }

    public Board(int filas, int columnas, boolean tipo) {
        
        //Si las filas o columnas pasan de 20, el tamaño de la casilla se divide entre 2
        
        this.columnas = columnas;
        this.filas = filas;
        
        int coulmnas_aux = columnas;
        
        int filas_aux = filas;
        
        //Reduce el tamaño de las casillas en caso de que el tablero sea grande
        while((coulmnas_aux > 20)|| (filas_aux > 20)){
            tamañoCasilla = tamañoCasilla / 2;
            
            coulmnas_aux = coulmnas_aux - 20;
            
            filas_aux = filas_aux - 20;
        }
        
        int width = tamañoCasilla * columnas;
        int height = tamañoCasilla * filas;
        
        
        
        initComponents(width,height);
        int x,y;
        setLayout(new java.awt.GridLayout(columnas, filas));
        
        this.tipoTablero = tipo;
        
        tocadoString = ConstantesGUI.origen;
        
        cargarImagenes();
        casillas = new Box[columnas][filas];
        for (int i = 0; i < columnas; i++){
            for (int j = 0; j < filas; j++){
                casillas[i][j] = new Box(this); 
                casillas[i][j].setFondo(fondo);
                x = (i * tamañoCasilla)+1;
                y = (j * tamañoCasilla)+1;
                casillas[i][j].setBounds(x, y, tamañoCasilla - 1, tamañoCasilla -1);
                this.add(casillas[i][j]);
            }
        }
        
       metaPuesta = false;
       
       factor_peligro = 0;
       
       lista_waypoints = new LinkedList<Position>();
       
       peligros = new ArrayList< Pair<Position,Double> >();
    }
    
     /**
      * Inicia los parametros del tablero
      * @param width ancho del tablero
      * @param height altura del tablero
      */  
    private void initComponents(int width, int height) {

        setLayout(null);

        setBackground(new java.awt.Color(0, 0, 0));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setPreferredSize(new java.awt.Dimension(width + 1, height + 1));
    }                      
                  
    /**
     * Pinta la imagen sobre una casilla
     * @param x coordenada x de la casilla
     * @param y coordenada y de  la casilla
     */
    public void pintar(int x, int y){
        this.casillas[x][y].setFondo(tocado);
        
        this.repaint();
    }
    
       /**
     * Pinta la imagen de un paso sobre una casilla
     * @param x coordenada x de la casilla
     * @param y coordenada y de  la casilla
     * @param imagen la imagen a pintar
     */
    public void pintar_imagen(int x, int y, String imagen){
       
        this.casillas[x][y].setFondo(new ImageIcon(imagen));
        
        this.repaint();
    }
    
    /**
     * Carga las imagenes del fondo de la casilla y cuando la casilla recibe un click
     * @param imagenTocado la ruta de la imagen que se pone al hacer un click
     */
    public void cargarImagenes() {
        this.fondo = this.cargarFondo(ConstantesGUI.fondo);
        this.tocado = this.cargarFondo(tocadoString);
    }
    
    protected static ImageIcon cargarFondo(String ruta) {
        java.net.URL localizacion = Board.class.getResource(ruta);
        if (localizacion != null) {
            return new ImageIcon(localizacion);
        } else {
            System.err.println("No se ha encontrado el archivo: " + ruta);
            return null;
        }
    }
    
    public int[] getCoordenadas(Box casilla) {
        int [] coordenadas = new int[2];
        for (int i=0; i < columnas; i++) {
            for (int j=0; j < filas; j++) {
                if (this.casillas[i][j] == casilla) {
                    coordenadas[0] = i;
                    coordenadas[1] = j;
                }
            }
        }
        return coordenadas;
    }
    
    public Box[][] getCasillas() {
        return casillas;
    }
    
    public void setCasillas(Box[][] casillas) {
        this.casillas = casillas;
    }
    
    public boolean isTipoTablero() {
        return tipoTablero;
    }    
    public void setTipoTablero(boolean tipoTablero) {
        this.tipoTablero = tipoTablero;
    }

    public ImageIcon getFondo() {
        return fondo;
    }

    public void setFondo(ImageIcon fondo) {
        this.fondo = fondo;
    }

    public ImageIcon getTocado() {
        return tocado;
    }

    public void setTocado(ImageIcon tocado) {
        this.tocado = tocado;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getTamañoCasilla() {
        return tamañoCasilla;
    }

    public void setTamañoCasilla(int tamañoCasilla) {
        this.tamañoCasilla = tamañoCasilla;
    } 
    
    public boolean getTipoTablero(){
        return this.isTipoTablero();
    }

    public String getTocadoString() {
        return tocadoString;
    }

    public void setTocadoString(String tocadoString) {
        this.tocadoString = tocadoString;
    }

    public boolean isMetaPuesta() {
        return metaPuesta;
    }

    public void setMetaPuesta(boolean metaPuesta) {
        this.metaPuesta = metaPuesta;
    }

    public List<Position> getLista_waypoints() {
        return lista_waypoints;
    }

    public void setLista_waypoints(List<Position> lista_waypoints) {
        this.lista_waypoints = lista_waypoints;
    }

    public List<Pair<Position, Double>> getPeligros() {
        return peligros;
    }

    public void setPeligros(List<Pair<Position, Double>> peligros) {
        this.peligros = peligros;
    }

    public double getFactor_peligro() {
        return factor_peligro;
    }

    public void setFactor_peligro(double factor_peligro) {
        this.factor_peligro = factor_peligro;
    }
}
