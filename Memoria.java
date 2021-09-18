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
    public Memoria(String tip){
        this.tipo = tip;
        int bloquest = 4 * 1024 / 64;
        this.tamano = bloquest;
        DDR = new ArrayList<Programa>(tamano);
        for(int i=0; i<this.tamano; i++){
            DDR.add(null);
        }
    }

    public Memoria(String tip, int tam){
        this.tipo = tip;
        int bloquest = tam * 1024 / 64;
        this.tamano = bloquest;
        SDR = new Programa[this.tamano];
        for(int i=0; i<this.tamano; i++){
            SDR[i] = null;
        }
    }

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

    public boolean ingresarPrograma(Programa programa) throws Exception{
        int espacio = programa.getEpacios();
        int tamparcial = this.tamano;
        boolean estado = false;
        boolean flag = false;
        double esp = espacio/64;
        int bloquesP = (int) Math.ceil(esp);

        try{
            if(tipo.equals("DDR")){
                while(flag==false){
                    int disponible = 0;

                    for(int i=0; i<DDR.size();i++){
                        if(DDR.get(i)==null){
                            disponible++;
                        }
                    }
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
                    }else{
                        int limite = 64 * 1024 / 64;
                        if(this.tamano<=limite){
                            this.tamano = this.tamano*2;
                            for(int i=tamparcial;i<this.tamano;i++){
                                DDR.add(null);
                            }
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
                                if(this.tamano/2 > espacioOcupado){
                                    if(this.tamano>(4 * 1024 / 64)){
                                        for(int i = (this.tamano/2);i<this.tamano;i++){
                                            if(DDR.get(i) == null){
                                                espacioVacio++;
                                            }
                                        }
                                    }
                                    this.tamano/=2;
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

            if(tipo.equals("SDR")){
                int disponible = 0;
                for(int i=0; i<SDR.length;i++){
                    if(SDR[i]!=null){
                        disponible++;
                    }
                }
                if(bloquesP<=disponible){
                    for(int i=0;i<SDR.length && bloquesP!=0;i++){
                        if(SDR[i]!=null){
                            SDR[i]=programa;
                            bloquesP--;
                        }
                    }
                    estado = true;
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

    public String estadoRAM(){
        String estadoRAM = "";

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

    public int[] cantMemoria(){
        int[] cantMemoria = new int[3];
        int tam = this.tamano*64;
        int memoriaDisp = 0;
        int memoriaNoDisp = 0;

        cantMemoria[0] = tam;

        if(this.tipo.equals("DDR")){
            for(int i=0; i<DDR.size();i++){
                if(DDR.get(i)==null){
                    memoriaDisp++;
                }else{
                    memoriaNoDisp++;
                }
            }
        }

        if(this.tipo.equals("SDR")){
            for(int i=0; i<SDR.length; i++){
                if(SDR[i]==null){
                    memoriaDisp++;
                }else{
                    memoriaNoDisp++;
                }
            }
        }

        cantMemoria[1] = memoriaDisp*64;
        cantMemoria[2] = memoriaNoDisp*64;

        return cantMemoria;
    }

    public String getProgramasEjecucion() throws Exception{
        String pe = "";

        try{
            if(this.tipo.equals("DDR")){
                for(int i=0; i<DDR.size();i++){
                    if(DDR.get(i)!=null){
                        Programa programaC = DDR.get(i);
                        String NombrePrograma = programaC.getNombre();
                        programasEjecucion.add(NombrePrograma);
                    }
                }
            }

            if(this.tipo.equals("SDR")){
                for(int i=0; i<SDR.length;i++){
                    if(SDR[i]!=null){
                        Programa programaC = SDR[i];
                        String NombrePrograma = programaC.getNombre();
                        programasEjecucion.add(NombrePrograma);
                    }
                }
            }
            Collections.sort(programasEjecucion);
            String[] listadoEjecucion = new String[programasEjecucion.size()];
            for(int i=0;i<programasEjecucion.size();i++){
                if(programasEjecucion.get(i)!=null){
                    listadoEjecucion[i] = programasEjecucion.get(i);
                }
            }

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

    public String getProgramasCola() throws Exception{
        String pc = "";
        
        try{
            if(cola!=null){
                for(Programa programa: cola){
                    String nombre = programa.getNombre();
                    pc += "/n- " + nombre;
                }
            }
        }catch(Exception e){
            String f = "Error en clasificacion de programas" + e.toString();
            throw new Exception(f);
        }
        return pc;
    }

    public int EspacioPrograma(String NombrePrograma) throws Exception{
        int espacioPrograma = 0;
        System.out.println(this.tipo);

        if(this.tipo.equals("DDR")){
            for(int i=0;i<DDR.size();i++){
                if(DDR.get(i)!=null){
                    if(DDR.get(i).getNombre().equals(NombrePrograma)){
                        espacioPrograma++;
                    }
                }
            }
        }

        if(this.tipo.equals("SDR")){
            for(int i=0;i<SDR.length;i++){
                if(SDR[i]!=null){
                    String nombreN = SDR[i].getNombre();
                    System.out.println(nombreN);
                    if(nombreN.equals(NombrePrograma)){
                        espacioPrograma++;
                    }
                }
            }
        }
        return espacioPrograma;
    }

    public String realizarCicloReloj() throws Exception{
        String pf = "";
        ArrayList <String> finalizados = new ArrayList<String>();

        if(this.tipo.equals("DDR")){
            boolean pasaCola = false;

            for(int i=0;i<DDR.size();i++){
                if(DDR.get(i)!=null){
                    Programa programaC = DDR.get(i);
                    programaC.reducirCiclos();
                }
            }

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

            if(cola.isEmpty()){
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

        if(this.tipo.equals("SDR")){
            boolean pasaCola2 = false;

            for(int i=0;i<SDR.length;i++){
                if(SDR[i]!=null){
                    Programa programaC = SDR[i];
                    programaC.reducirCiclos();
                }
            }

            for(int i=0;i<SDR.length;i++){
                if(SDR[i]!=null){
                    Programa programaC = SDR[i];
                    int tiempoProgramaC = programaC.getCiclos();
                    if(tiempoProgramaC <= 0){
                        finalizados.add(programaC.getNombre());
                        SDR[i] = null;
                        pasaCola2 = true;

                        if(cola.isEmpty()){
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

        Collections.sort(finalizados);
        String[] listadoFinalizados = new String[finalizados.size()];
            for(int i=0;i<finalizados.size();i++){
                if(finalizados.get(i)!=null){
                    listadoFinalizados[i] = finalizados.get(i);
                }
            }

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