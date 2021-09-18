/**
 * Clase Programa
 * Encargada de generar los programas y sus caracteristicas.
 * 
 * @version 1.0, 15/09/2021
 * finalizacion 17/09/2021
 * 
 * @author Diego E. Lemus L. - 21469
 */

public class Programa{
    private String nombre;
    private int espacios;
    private int ciclos;

    /** 
     * Constructor Programa
     */
    public Programa(String n, int e, int c){
        nombre = n;
        espacios = e;
        ciclos = c;
    }

    /** 
     * @return String
     */
    public String getNombre(){
        return nombre;
    }

    /** 
     * @return int
     */
    public int getEpacios(){
        return espacios;
    }

    /** 
     * @return int
     */
    public int getCiclos(){
        return ciclos;
    }

    public void reducirCiclos(){
        ciclos = ciclos - 1;
    }
}