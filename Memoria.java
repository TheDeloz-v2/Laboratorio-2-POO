import java.util.*;
import java.io.*;
import java.util.Scanner;

/**
 * Clase Memoria
 * Encargada de realizar los procesos de la memoria y manejar los programas.
 * 
 * @version 1.0, 15/09/2021
 * finalizacion 17/09/2021
 * 
 * @author Diego E. Lemus L. - 21469
 */

public class Memoria{

    //-----PROPIEDADES-----
    private String tipo;
    private int tamano;
    private ArrayList <Programa> DDR;
    private Programa[] SDR;
    private ArrayDeque <Programa> cola = new ArrayDeque<>();
    private ArrayList<String> programasEjecucion = new ArrayList<String>();

    //-----METODOS-----
    /** 
     * Constructor Memoria DDR
     */
    public Memoria(String tip){
        this.tipo = tip;
        int bloquest = 4 * 1024 / 64;
        this.tamano = bloquest;
        DDR = new ArrayList<Programa>(tamano);
        for(int i=0; i<this.tamano; i++){
            DDR.add(null);
        }
    }

    /** 
     * Constructor Memoria SDR
     */
    public Memoria(String tip, int tam){
        this.tipo = tip;
        int bloquest = tam * 1024 / 64;
        this.tamano = bloquest;
        SDR = new Programa[this.tamano];
        for(int i=0; i<this.tamano; i++){
            SDR[i] = null;
        }
    }
    
    /** 
     * @return int Calcula el tamano total de la memoria
     */
    public int getTamanoTotal(){
        int tamtotal = this.tamano*1024;
        return tamtotal;
    }

    /** 
     * Metodo para leer el archivo
     */
    public void leerArchivo() throws Exception{
        File archivo = new File(".\\ProgramasInst.txt");//abre el archivo
        try {
            Scanner scan = new Scanner(archivo);
            String[] datos = new String[3];

            while(scan.hasNextLine()){
                String linea = scan.nextLine();
                datos = linea.split(",");
                String n = datos[0];
                int e = Integer.parseInt(datos[1]);
                int c = Integer.parseInt(datos[2]);

                Programa programaInst = new Programa(n, e, c);
                ingresarPrograma(programaInst);
            }
            //Se cierra la lectura
            scan.close();
        }
        //En caso de no haber archivo
        catch(Exception e){
           String f = "Error al leer" + e.toString();
           throw new Exception(f);
        }
    }
    
    /** 
     * Metodo para ingresar un programa a la memoria
     * @param programa
     * @return boolean Confirmacion de ingreso del programa
     * @throws Exception
     */
    public boolean ingresarPrograma(Programa programa) throws Exception{
        int espacio = programa.getEpacios();
        int tamparcial = this.tamano;
        boolean estado = false;
        boolean flag = false;
        double esp = espacio/64;
        int bloquesP = (int) Math.ceil(esp);

        try{
            //Memoria tipo DDR
            if(tipo.equals("DDR")){
                while(flag==false){
                    int disponible = 0;

                    //Se calcula el espacio disponible
                    for(int i=0; i<DDR.size();i++){
                        if(DDR.get(i)==null){
                            disponible++;
                        }
                    }

                    //Se asegura si el espacio del programa es menor al disponible
                    if(bloquesP<=disponible){
                        if(bloquesP!=0){
                            for(int i = 0;i<DDR.size();i++){
                                if(DDR.get(i)==null){
                                    DDR.set(i, programa);
                                    bloquesP--;
                                }
                            }
                        }
                        estado = true;
                        flag = true;
                    //Se aumenta el espacio de la memoria dinamica DDR
                    }else{
                        int limite = 64 * 1024 / 64;
                        if(this.tamano<=limite){
                            this.tamano = this.tamano*2;
                            for(int i=tamparcial;i<this.tamano;i++){
                                DDR.add(null);
                            }
                    //El programa se ingresa a la cola
                        }else{
                            cola.add(programa);

                            boolean tamanoCorrecto = false;
                            int espacioVacio = 0;

                            while(tamanoCorrecto == false){
                                int espacioOcupado = 0;
                                for(int i=0;i<DDR.size();i++){
                                    if(DDR.get(i)==null){
                                        espacioOcupado++;
                                    }
                                }
                                if(tamparcial > espacioOcupado){
                                    if(this.tamano>(4 * 1024 / 64)){
                                        for(int i = (this.tamano/2);i<this.tamano;i++){
                                            if(DDR.get(i) == null){
                                                espacioVacio++;
                                            }
                                        }
                                    }
                                    this.tamano = tamparcial;
                                }else{
                                    tamanoCorrecto = true;
                                }
                            }
                            for(int i = 1024-1;i>=(1024-espacioVacio);i--){
                                DDR.remove(i);
                            }
                            flag = true;
                        }
                    }
                }
            }

            //Memoria tipo SDR
            if(tipo.equals("SDR")){
                int disponible = 0;
                for(int i=0; i<SDR.length;i++){
                    if(SDR[i]!=null){
                        disponible++;
                    }
                }

                //Se asegura si el espacio del programa es menor al disponible
                if(bloquesP<=disponible){
                    if(bloquesP!=0){
                        for(int i=0;i<SDR.length;i++){
                            if(SDR[i]!=null){
                                SDR[i]=programa;
                                bloquesP--;
                            }
                        }
                    }
                    estado = true;
                //El programa se ingresa a la cola
                }else{
                    cola.add(programa);
                }
            }
        }catch(Exception e){
            String s = "Error en ingresar programa" + e.toString();
            throw new Exception(s);
        }
        return estado;
    }

    /** 
     * Metodo para calcular el estado de la RAM
     * @return String
     */
    public String estadoRAM(){
        //String para mostrar los programas
        String estadoRAM = "";

        //Tipo de memoria DDR
        if(this.tipo.equals("DDR")){
            for(int i=0;i<DDR.size();i++){
                if(DDR.get(i)!=null){
                    String NombrePrograma = DDR.get(i).getNombre();
                    estadoRAM += "["+"\t"+NombrePrograma+"\t"+"]";
                }else{
                    estadoRAM += "["+"\t"+"null"+"\t"+"]";
                }
            }
        }

        //Tipo de memoria SDR
        if(this.tipo.equals("SDR")){
            for(int i=0;i<SDR.length;i++){
                if(SDR[i]!=null){
                    String NombrePrograma = SDR[i].getNombre();
                    estadoRAM += "["+"\t"+NombrePrograma+"\t"+"]";
                }else{
                    estadoRAM += "["+"\t"+"null"+"\t"+"]";
                }
            }
        }
        return estadoRAM;
    }

    /** 
     * Metodo para calcular la cantidad de la memoria
     * @return int[]
     */
    public int[] cantMemoria(){
        //Lista que contiene la memoria total, memoria disponible y memoria no disponible
        int[] cantMemoria = new int[3];
        int tam = this.tamano*64;
        int memoriaDisp = 0;
        int memoriaNoDisp = 0;

        //Memoria total
        cantMemoria[0] = tam;

        //Tipo de memoria DDR
        if(this.tipo.equals("DDR")){
            for(int i=0; i<DDR.size();i++){
                if(DDR.get(i)==null){
                    memoriaDisp++;
                }else{
                    memoriaNoDisp++;
                }
            }
        }

        //Tipo de memoria SDR
        if(this.tipo.equals("SDR")){
            for(int i=0; i<SDR.length; i++){
                if(SDR[i]==null){
                    memoriaDisp++;
                }else{
                    memoriaNoDisp++;
                }
            }
        }

        //Memoria disponible
        cantMemoria[1] = memoriaDisp*64;
        //Memoria no disponible
        cantMemoria[2] = memoriaNoDisp*64;

        return cantMemoria;
    }

    /** 
     * Metodo para obtener los programas en ejecucion
     * @return String
     * @throws Exception
     */
    public String getProgramasEjecucion() throws Exception{
        //String que contiene los programas en ejecucion
        String pe = "";

        try{
            //Tipo de memoria DDR
            if(this.tipo.equals("DDR")){
                for(int i=0; i<DDR.size();i++){
                    if(DDR.get(i)!=null){
                        Programa programaC = DDR.get(i);
                        String NombrePrograma = programaC.getNombre();
                        //Se agrega el programa a la lista
                        programasEjecucion.add(NombrePrograma);
                    }
                }
            }

            //Tipo de memoria SDR
            if(this.tipo.equals("SDR")){
                for(int i=0; i<SDR.length;i++){
                    if(SDR[i]!=null){
                        Programa programaC = SDR[i];
                        String NombrePrograma = programaC.getNombre();
                        //Se agrega el programa a la lista
                        programasEjecucion.add(NombrePrograma);
                    }
                }
            }
            //Se ordena el array
            Collections.sort(programasEjecucion);
            //Se crea un listado de los programas en ejecucion
            String[] listadoEjecucion = new String[programasEjecucion.size()];
            for(int i=0;i<programasEjecucion.size();i++){
                if(programasEjecucion.get(i)!=null){
                    listadoEjecucion[i] = programasEjecucion.get(i);
                }
            }

            //Se ordena el array y se coloca en el string
            if(listadoEjecucion.length > 0){
                pe += " - " + listadoEjecucion[0]  + " - ";
                for (int i = 1; i < listadoEjecucion.length; i++){
                    if (listadoEjecucion[i].equals(listadoEjecucion[i-1])){
                    }else{
                        pe += " - " + listadoEjecucion[i]  + " - ";
                    }
                }
            }
        }catch(Exception e){
            String f = "Error en clasificacion de programas" + e.toString();
            throw new Exception(f);
        }
        return pe;
    }

    /** 
     * Metodo para obtener los programas en cola
     * @return String
     * @throws Exception
     */
    public String getProgramasCola() throws Exception{
        //String que contiene los programas en cola
        String pc = "";
        
        try{
            //Se verifica que la cola no este vacia
            if(cola!=null){
                for(Programa programa: cola){
                    String nombre = programa.getNombre();
                    pc += " - " + nombre + " - ";
                }
            }
        }catch(Exception e){
            String f = "Error en clasificacion de programas" + e.toString();
            throw new Exception(f);
        }
        return pc;
    }

    /** 
     * Metodo para obtener el espacio de un programa en especifico
     * @param NombrePrograma
     * @return int
     * @throws Exception
     */
    public int EspacioPrograma(String NombrePrograma) throws Exception{
        //Contador de los espacios de un programa
        int espacioPrograma = 0;

        //Memoria tipo DDR
        if(this.tipo.equals("DDR")){
            for(int i=0;i<DDR.size();i++){
                if(DDR.get(i)!=null){
                    if(DDR.get(i).getNombre().equals(NombrePrograma)){
                        //El contador de espacio aumenta si se encuenta en la memoria
                        espacioPrograma++;
                    }
                }
            }
        }

        //Memoria tipo SDR
        if(this.tipo.equals("SDR")){
            for(int i=0;i<SDR.length;i++){
                if(SDR[i]!=null){
                    String nombreN = SDR[i].getNombre();
                    System.out.println(nombreN);
                    if(nombreN.equals(NombrePrograma)){
                        //El contador de espacio aumenta si se encuenta en la memoria
                        espacioPrograma++;
                    }
                }
            }
        }
        return espacioPrograma;
    }
    
    /** 
     * Metodo para realiza un ciclo de reloj
     * @return String Lista de programas que pudieron terminar su ciclo
     * @throws Exception
     */
    public String realizarCicloReloj() throws Exception{
        //String de programas finalizados
        String pf = "";
        ArrayList <String> finalizados = new ArrayList<String>();

        //Tipo de memoria DDR
        if(this.tipo.equals("DDR")){
            boolean pasaCola = false;

            //Se reduce un ciclo de los programas en ejecucion
            for(int i=0;i<DDR.size();i++){
                if(DDR.get(i)!=null){
                    Programa programaC = DDR.get(i);
                    programaC.reducirCiclos();
                }
            }

            //Se verifica si el programa finalizo su proceso
            for(int i=0;i<DDR.size();i++){
                if(DDR.get(i)!=null){
                    Programa programaC = DDR.get(i);
                    int tiempoProgramaC = programaC.getCiclos();
                    if(tiempoProgramaC <= 0){
                        finalizados.add(programaC.getNombre());
                        DDR.set(i, null);
                    }
                }
            }

            //En caso de haber espacio para un programa de la cola
            if(cola.isEmpty()){
                //Si la cola esta vacia
                pasaCola = false;
            }else{
                if(pasaCola == false){
                    for(Programa programaCola: cola){
                        pasaCola = ingresarPrograma(programaCola);
                        cola.poll();
                        pasaCola = true;
                    }
                }
            }
        }

        //Tipo de memoria SDR
        if(this.tipo.equals("SDR")){
            boolean pasaCola2 = false;

            //Se reduce un ciclo de los programas en ejecucion
            for(int i=0;i<SDR.length;i++){
                if(SDR[i]!=null){
                    Programa programaC = SDR[i];
                    programaC.reducirCiclos();
                }
            }

            //Se verifica si el programa finalizo su proceso
            for(int i=0;i<SDR.length;i++){
                if(SDR[i]!=null){
                    Programa programaC = SDR[i];
                    int tiempoProgramaC = programaC.getCiclos();
                    if(tiempoProgramaC <= 0){
                        finalizados.add(programaC.getNombre());
                        SDR[i] = null;
                        pasaCola2 = true;

                        //En caso de haber espacio para un programa de la cola
                        if(cola.isEmpty()){
                            //Si la cola esta vacia
                            pasaCola2 = false;
                        }else{
                            for(Programa programaCola: cola){
                                int bloquesP = programaCola.getEpacios();
                                double espacioP = bloquesP/64;
                                if(espacioP<=tamano){
                                    pasaCola2 = ingresarPrograma(programaCola);
                                    cola.poll();
                                    pasaCola2 = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        //Se ordena el array de finalizados
        Collections.sort(finalizados);
        //Se crea un listado del programa del array de nializados
        String[] listadoFinalizados = new String[finalizados.size()];
            for(int i=0;i<finalizados.size();i++){
                if(finalizados.get(i)!=null){
                    listadoFinalizados[i] = finalizados.get(i);
                }
            }

            //Se ordena el array y se coloca en el string
            if(listadoFinalizados.length > 0){
                pf += " - " + listadoFinalizados[0]  + " - ";
                for (int i = 1; i < listadoFinalizados.length; i++){
                    if (listadoFinalizados[i].equals(listadoFinalizados[i-1])){
                    }else{
                        pf += " - " + listadoFinalizados[i]  + " - ";
                    }
                }
            }
        return pf;
    }
}