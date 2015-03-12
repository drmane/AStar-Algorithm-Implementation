/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algoritmoaestrella.logic;

import algoritmoaestrella.structures.AStarNode;
import algoritmoaestrella.structures.Position;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import javafx.util.Pair;

/**
 *
 * @author Dani
 */
public class AStar {
        
    /*
    NOTA
    ####
    
    Tenemos una matriz de NodosAEstrella. 
    
    El resto de estructuras trabajan sobre posiciones y no nodos
    */
    
    //El tablero
    private final AStarNode [][] tablero;
    
    //Una salidas
    private Position posicion_nodo_origen;
    
    //Una sola meta
    private Position posicion_nodo_meta;
    
    //Lista waypoints
    private List<Position> lista_waypoints;
    
    //Estructura donde aparecen las posiciones que han sido alcanzados por una expansión
    private final List<Position> lista_procesados;
    
    //Indica los límites del tablero
    private final int limite_fila;
        
    private final int limite_columna;

    public AStar(int filas, int columnas) {

        
        //El tablero va de 0 a n - 1 en las filas y de 0 a m - 1 en las columnas
        
        tablero = new AStarNode [filas][columnas];
        
        //Instancia los nodos de la estructura
        for(int i = 0; i < filas; i++)
            for(int j = 0; j < columnas; j++){
                tablero[i][j] = new AStarNode();
                
                tablero[i][j].setAbierto(true);
            }
        
        //Lista de waypoints
        lista_waypoints = new ArrayList<Position>();
        
        //Creamos la lista de procesados
        lista_procesados = new ArrayList<Position>();
        
        limite_fila = filas;
        
        limite_columna = columnas;
    }
    
    /**
     * Permite calcular los caminos de los nodos salidas a la meta
     * No devuelve ni el origen ni la meta
     */
    public List<Position> calcularCamino(){
        
        //Estructuras para el cálculo de la solución
        List<Position> solucion_aux;
        
        List<Position> solucion = new ArrayList<Position>();
        
        //Cuando queden dos waypoints, se hará el ultimo recorrido
        while(lista_waypoints.size() > 1){
            
            //Cogemos el primer elemento y ese es el origen
            posicion_nodo_origen = lista_waypoints.get(0);
            
            //El segundo elemento será la meta
            posicion_nodo_meta = lista_waypoints.get(1);
            
            //Ponemos la valoración inicial de la g
            AStarNode nodo_origen = tablero[posicion_nodo_origen.getPositionX()][posicion_nodo_origen.getPositionY()];
            nodo_origen.setG(0);

            //Inserta la salida en la lista
            insertar(posicion_nodo_origen,posicion_nodo_origen,false);

            Position posicion_nodo_seleccionado = posicion_nodo_origen;

            //El programa acaba cuando la meta se cierra
            while(estaMetaCerrada()){

                //Realizar expansión
                List<Position> lista_nodos_expansion = expandir(posicion_nodo_seleccionado);

                //Inserta los nodos a los cuales ha llegado la expansión
                for (Position nodo_insertar : lista_nodos_expansion) {

                    //En las salidas, su padre es el mismo, el nodo seleccionado en este caso es el padre
                    insertar(nodo_insertar,posicion_nodo_seleccionado,true);
                }

                //Selecciona el nodo (en este caso, el que tenga menor f)
                posicion_nodo_seleccionado = seleccionar();

                //Cerramos el nodo seleccionado
                tablero[posicion_nodo_seleccionado.getPositionX()][posicion_nodo_seleccionado.getPositionY()].setAbierto(false);
            }
        
        //Se borra el primer waypoint de la lista
        lista_waypoints.remove(0);
        
        //obtenemos la solución parcial
        solucion_aux = obtenerSolucion();
        
        //Lo introducimos en la solución total
        for(Position p:solucion_aux)
            solucion.add(p);
        }
 
        return solucion;
    }
    
    /**
     * Permite insertar las posiciones de los nodos a los que ha llegado la expansión a la lista de los procesados
     * También calcula la G y la H del nodo
     * En el caso de que ya esté en la lista, se actualizan los valores correspondientes
     * @param nodo_insertar La posición del nodo que se debe insertar en la lista de procesados
     * @param padre La posición del nodo desde el que se ha realizado la expansión
     * @param abierto Indica si el nodo a insertar está abierto o no (solo se insertan cerrados la salida)
     */
    private void insertar(Position posicion_nodo_insertar, Position  posicion_nodo_padre, boolean abierto){
        
        //Obtenemos el nodo correspondiente
        AStarNode nodo_insertar = tablero[posicion_nodo_insertar.getPositionX()][posicion_nodo_insertar.getPositionY()];
        
        //Establece el padre del nodo a insertar
        nodo_insertar.setPadre(posicion_nodo_padre);
        
        //Cambia el flag de abierto
        nodo_insertar.setAbierto(abierto);
        
        //Calcula la g y la h
        //nodo_insertar.setG(distancia_euclidea(posicion_nodo_origen,posicion_nodo_insertar));
        
        //calculo de la g mediante la distancia real
        //g = g0 + variacion distancia
        
        AStarNode nodo_padre = tablero[posicion_nodo_padre.getPositionX()][posicion_nodo_padre.getPositionY()];
        nodo_insertar.setG(nodo_padre.getG() + distancia_euclidea(posicion_nodo_padre,posicion_nodo_insertar));
        
        nodo_insertar.setH(distancia_euclidea(posicion_nodo_insertar,posicion_nodo_meta));
        
        //Si es elemento ya estaba en la lista, lo borra y lo vuelve a insertar
        
        Iterator it = lista_procesados.iterator();
        
        while(it.hasNext()){
            
            Position p = (Position) it.next();
            
            if((p.getPositionX() == posicion_nodo_insertar.getPositionX())&&(p.getPositionY() == posicion_nodo_insertar.getPositionY())){
                it.remove();
            }
        }
        
        lista_procesados.add(posicion_nodo_insertar); 
    }
    
    /**
     * Permite comprobar si una posición de un nodo está en la lista de procesados
     * @param nodo_buscar la posicion del nodo que se desea buscar
     * @return si está en la lista de procesados o no
     */
    private boolean estaListaProcesados(Position nodo_buscar){
        
        Iterator it_nodos_procesados = lista_procesados.iterator();
        
        boolean nodo_encontrado = false;
        
        while(it_nodos_procesados.hasNext()){
            
            Position buscado = (Position) it_nodos_procesados.next();
            
            if((buscado.getPositionX() == nodo_buscar.getPositionX()) && (buscado.getPositionY() == nodo_buscar.getPositionY())){
                nodo_encontrado = true;
            }
        }
        
        return nodo_encontrado;
    }
    
    /**
     * Devuelve una lista de posiciones de nodos hasta donde llega la expansión
     * La expansión no llega a los nodos cerrados, las posiciones de los nodos cerrados no los devuelve
     * @param posicion_nodo_expandir La posicion del nodo desde el que se quiere expandir
     * @return Una lista de posiciones de nodos hasta donde llega la expansión
     */
    private List<Position> expandir(Position posicion_nodo_expandir){

        List<Position> lista_expansion = new ArrayList<Position>();
        
        /*
        p1 | p2 | p3
        p4 | posicion_nodo_expandir | p5
        p6 | p7 | p8
        
        */
        
        //Creamos una lista de posiciones auxiliares
        List<Position> lista_posible_expansion = new ArrayList<Position>();
        
        int x = posicion_nodo_expandir.getPositionX();
        int y = posicion_nodo_expandir.getPositionY();
        
        lista_posible_expansion.add(new Position(x-1,y-1)); //p1
        lista_posible_expansion.add(new Position(x-1,y)); //p2
        lista_posible_expansion.add(new Position(x-1,y+1)); //p3
        lista_posible_expansion.add(new Position(x,y-1)); //p4
        lista_posible_expansion.add(new Position(x,y+1)); //p5
        lista_posible_expansion.add(new Position(x+1,y-1)); //p6
        lista_posible_expansion.add(new Position(x+1,y)); //p7
        lista_posible_expansion.add(new Position(x+1,y+1)); //p8
                
        //Recorremos las posibles posiciones de la expansion
        for(Position p : lista_posible_expansion){
            
            if(estaDentro(p)){
                
                AStarNode n = tablero[p.getPositionX()][p.getPositionY()];
                            
                if(n.isAbierto()){
                    lista_expansion.add(p);
                }
                
            }
        }  
	return lista_expansion; //devuelve falso si el numero esta en la region
    }
    
    /**
     * Indica si la posicion está dentro del tablero
     * @param p la posición
     * @return si esta dentro del tablero o no
     */
    public boolean estaDentro(Position p){
        
        boolean dentro = true;
        
        //Si alguna componente no es correcta
        if((p.getPositionX() < 0)||(p.getPositionY() < 0)||(p.getPositionX() >= limite_fila)||(p.getPositionY() >= limite_columna)){
            dentro = false;
        }
        
        return dentro;
    }
    
    /**
     * Selecciona la siguiente posicion que se debe procesar
     * Se selecciona aquel nodo con la f más pequeña que esté abierto
     * Aqui no se realiza el cerrado del nodo
     * @return La posición del nodo cuya f es menor
     */
    private Position seleccionar(){
        
        //Creamos las estructuras correspondientes al nodo con menor f
        Position posicion_nodo_menor_f = null;
        AStarNode nodo_menor_f = null;
        
        //Sirve para seleccionar el primer nodo abierto
        boolean primero_abierto = true;
        
        //Se recorren las posiciones procesadas
        for (Position posicion_nodo_procesar : lista_procesados) {
            
            //Obtenemos ese nodo
            AStarNode nodo_procesar = tablero[posicion_nodo_procesar.getPositionX()][posicion_nodo_procesar.getPositionY()];

            //Si está abierto y su f es menor que la encontrada, el nodo menor cambia
            if(nodo_procesar.isAbierto()){
                
                //Si no hay ningún nodo seleccionado, selecciona el primer nodo abierto de la lista
                if(primero_abierto){
                    posicion_nodo_menor_f = posicion_nodo_procesar;
                    nodo_menor_f = nodo_procesar;
                
                    primero_abierto = false;
                }
                else if(nodo_procesar.getF() < nodo_menor_f.getF()){
                
                    //Cambiamos la posicion y el nodo con menor f
                    posicion_nodo_menor_f = posicion_nodo_procesar;
                    nodo_menor_f = nodo_procesar;
                }
            }
        }
        
        return posicion_nodo_menor_f;
    }
    
    /**
     * Permite obtener la solución, salida y meta incluidas
     * La solución es una lista de posiciones
     * @return Devuelve una lista de nodos que indica la solución
     */
    private List<Position> obtenerSolucion(){
        
        Stack<Position> pila = new Stack();
        
        boolean llegadoSalida = false;
        
        Position posicion_nodo = posicion_nodo_meta;
        
        while(llegadoSalida == false){
        
            //Mientras no se haya llegado a la salida
            while(!posicion_nodo.equals(posicion_nodo_origen)){

                pila.push(posicion_nodo);

                //Obtenemos ese nodo
                AStarNode nodo = tablero[posicion_nodo.getPositionX()][posicion_nodo.getPositionY()];

                //
                posicion_nodo = nodo.getPadre();
            }
        
            //Se mete la salida
            pila.push(posicion_nodo_origen);

            //Comprueba si se ha llegado a la primera salida, se comprueba viendo si su padre es el mismo

            AStarNode nodo_aux = tablero[posicion_nodo_origen.getPositionX()][posicion_nodo_origen.getPositionY()];

            if((posicion_nodo_origen.getPositionX() == nodo_aux.getPadre().getPositionX())&&((posicion_nodo_origen.getPositionY() == nodo_aux.getPadre().getPositionY()))){
                llegadoSalida = true;
            }
        }
       
        List<Position> solucion = new ArrayList();
        
        //Quitamos el origen
        pila.pop();
        
        while(pila.size() > 1){
            solucion.add(pila.pop());
        }
        
        return solucion;
    }
    
    /**
     * Devuelve si la meta está cerrada
     * @return si la meta está cerrada o no
     */
    private boolean estaMetaCerrada(){
        
        boolean abierto;
        
        //Obtenemos el nodo meta
        AStarNode nodo_meta = tablero[posicion_nodo_meta.getPositionX()][posicion_nodo_meta.getPositionY()];
        
        //Devuelve si la meta está en la lista de procesados o no
        boolean meta_encontrada = estaListaProcesados(posicion_nodo_meta);
        
        if(meta_encontrada == false){
            abierto = true;
        }
        else{
            abierto = nodo_meta.isAbierto();
        }
        
        return abierto;
    }
    
    /**
     * Calcula la distancia euclidea entre dos 
     * @param n1 La posicion del punto n1
     * @param n2 La posicion del punto n2
     * @return la distancia entre esos puntos
     */
    private double distancia_euclidea(Position n1, Position n2){
        
        //Se elevan al cuadrdo ambos miembros
        double m1 = Math.pow(n1.getPositionX() - n2.getPositionX(),2);
        
        double m2 = Math.pow(n1.getPositionY() - n2.getPositionY(),2);
        
        //Se hace la raiz cuadrada de la suma de los miembros
        return Math.sqrt(m1 + m2);
    }
    
    //A partir de aqui, los métodos sirven para cargar la estructura
    
    //Pequeña dependencia con la clas casilla de la vista
    
    /**
     * Permite cargar la salida en la estructura
     * @param posicion posicion de la salida
     */
    public void cargarSalida(Position posicion){
        posicion_nodo_origen = posicion;
    }
    
    /**
     * Permite cargar la meta en la estructura
     * @param posicion posicion de la meta
     */
    public void cargarMeta(Position posicion){
        posicion_nodo_meta = posicion;
    }
    
    /**
     * Permite cargar los obstaculos en la estructura
     * Los obstaculos son posiciones cuya nodo el flag de abierto lo tiene cerrado
     * @param posiciones la lista de posiciones de los obstáculos
     */
    public void cargarObstaculos(List<Position> posiciones){
        for(Position posicion: posiciones)
            tablero[posicion.getPositionX()][posicion.getPositionY()].setAbierto(false);
    }
    
    /**
     * Permite cargar los waypoints en la estructura
     * Los waypoints se recorrerán en el orden que se carguen, siendo considerada la meta el último waypoint
     * La salida y la meta son considerados el primer y último waypoint
     * Llamar a esta función tras haber cargado la salida y la meta
     * @param posiciones las posiciones de los waypoints
     */
    public void cargarWaypoints(List<Position> posiciones){
        lista_waypoints.add(posicion_nodo_origen);
        
        for(Position p: posiciones)
            lista_waypoints.add(p);
        lista_waypoints.add(posicion_nodo_meta);
    }  
    
    public void cargarPeligros(List< Pair<Position,Double> > peligros){
        
        for(Pair elemento : peligros){
            Position p = (Position) elemento.getKey();
            
            double valor_numerico = (double) elemento.getValue();
            
            AStarNode nodo = tablero[p.getPositionX()][p.getPositionY()];
            
            nodo.setF_prima(valor_numerico);
        }   
    }
}
