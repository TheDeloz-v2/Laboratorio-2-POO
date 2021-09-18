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
    //-----PROPIEDADES-----
    private String nombre;
    private int espacios;
    private int ciclos;

    //-----METODOS-----
    /** 
     * Constructor Programa
     */
    public Programa(String n, int e, int c){
        nombre = n;
        espacios = e;
        ciclos = c;
    }

    /** 
     * @return String Nombre del programa
     */
    public String getNombre(){
        return nombre;
    }

    /** 
     * @return int Espacio del programa
     */
    public int getEpacios(){
        return espacios;
    }

    /** 
     * @return int ciclos del programa
     */
    public int getCiclos(){
        return ciclos;
    }

    /** 
     * Reducir ciclos del programa
     */
    public void reducirCiclos(){
        ciclos = ciclos - 1;
    }
}