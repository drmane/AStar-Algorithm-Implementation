/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algoritmoaestrella.view;

/**
 *
 * @author Dani
 */
public class MainFrame extends javax.swing.JFrame {
    
    private Board tablero;
    
    private InputFrame vistaEntrada;
    
    /**
     * Permite crear la pantalla principal del programa
     * @param rows número de columnas del tablero
     * @param columns número de filas del tablero
     */
    public MainFrame(int rows, int columns) {
        initComponents(rows,columns);
    }
                          
    private void initComponents(int rows, int columns) {
        tablero = new Board(rows,columns,true);
        
        this.setTitle("Algoritmo A*");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        javax.swing.GroupLayout tableroGUI1Layout = new javax.swing.GroupLayout(tablero);
        tablero.setLayout(tableroGUI1Layout);
        tableroGUI1Layout.setHorizontalGroup(
            tableroGUI1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 349, Short.MAX_VALUE)
        );
        tableroGUI1Layout.setVerticalGroup(
            tableroGUI1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 349, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        
        this.setResizable(false);
        
        vistaEntrada = new InputFrame();
        vistaEntrada.setVisible(true);
        
        vistaEntrada.setTablero(tablero);
        
        pack();
    }

    public Board getTablero() {
        return tablero;
    }
}
