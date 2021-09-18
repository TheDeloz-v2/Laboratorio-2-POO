import java.util.Scanner;

/**
 * Clase Vista
 * Encargada de recibir y mostrarle la información al usuario.
 * 
 * @version 1.0, 15/09/2021
 * finalizacion 17/09/2021
 * 
 * @author Diego E. Lemus L. - 21469
 */

 public class Vista{
    //-----PROPIEDADES-----
    Scanner scan = new Scanner(System.in);

    /** 
     * Método que muestra el menú, además lee y devuelve la opción del usuario.
     * @return int op, seleccionada por el usuario (1-9)
     */
    public int mostrarMenu(){
        System.out.println("\n\n\n--------------------Menu--------------------");
        System.out.println("Opcion 1: Inicializar una nueva RAM");
        System.out.println("Opcion 2: Ingresar programa");
        System.out.println("Opcion 3: Visualizar cantidad de memoria RAM");
        System.out.println("Opcion 4: Visualizar espacio de programa");
        System.out.println("Opcion 5: Visualizar programas en ejecucion");
        System.out.println("Opcion 6: Visualizar programas en cola de espera");
        System.out.println("Opcion 7: Visualizar estado de memoria RAM");
        System.out.println("Opcion 8: Realizar ciclo de reloj");
        System.out.println("OPcion 9: Guardar y salir\n");
        int op = Integer.parseInt(scan.nextLine());
        return op;
    }

    /** 
     * Método que imprime la bienvenida.
     */
    public void bienvenida(){
        System.out.println("\nBienvenido a su Memoria RAM");
    }

    public String pedirtipoRAM() throws Exception{
        boolean validacion = false;
        String tipo = null;

        try{
            System.out.println("Ingrese el tipo de memoria RAM (SDR / DDR):");
            while(validacion==false){
                tipo = scan.nextLine().toUpperCase();
                if(tipo.equals("DDR")){
                    validacion = true;
                }else if(tipo.equals("SDR")){
                    validacion = true;
                }else{
                    System.out.println("\n%% Se ingreso una opcion invalida [Ingrese DDR o SDR] %%");
                }
            }
        }catch(Exception e){
            String f = "Error en la lectura"+e.toString();
            throw new Exception(f);
        }
        return tipo;
    }

    public int pedirTamanoRAM() throws Exception{
        boolean validacion = false;
        int tamano = 0;

        try{
            System.out.println("Ingrese el tamano de la memoria (GB):");
            while(validacion==false){
                tamano = Integer.parseInt(scan.nextLine());
                if(tamano>0){
                    validacion = true;
                }else{
                    System.out.println("\n%% Se ingreso una opcion invalida [Ingrese una cantidad mayor a 0] %%"); 
                }
            }
        }catch (Exception e){
            String f = "Error en la lectura"+e.toString();
            throw new Exception(f);
        }
        return tamano;
    }

    public String pedirnombrePrograma() throws Exception{
        boolean validacion = false;
        String nombre = null;

        try{
            System.out.println("Ingrese el nombre del programa:");
            while(validacion==false){
                nombre = scan.nextLine();
                if(nombre.equals("") || nombre.equals(null)){
                    System.out.println("\n%% Se ingreso una opcion invalida [Ingrese un nombre valido] %%");
                }else{
                    validacion = true;
                }
            }
        }catch(Exception e){
            String f = "Error en la lectura"+e.toString();
            throw new Exception(f);
        }
        return nombre;
    }

    public int pedirEspacioPrograma() throws Exception{
        boolean validacion = false;
        int espacio = 0;

        try{
            System.out.println("Ingrese el espacio del programa:");
            while(validacion==false){
                espacio = Integer.parseInt(scan.nextLine());
                if(espacio>0){
                    validacion = true;
                }else{
                    System.out.println("\n%% Se ingreso una opcion invalida [Ingrese una cantidad mayor a 0] %%");
                }
            }
        }catch(Exception e){
            String f = "Error en la lectura"+e.toString();
            throw new Exception(f);
        }
        return espacio;
    }

    public int pedirCiclosPrograma() throws Exception{
        boolean validacion = false;
        int ciclos = 0;

        try{
            System.out.println("Ingrese los ciclos del programa:");
            while(validacion==false){
                ciclos = Integer.parseInt(scan.nextLine());
                if(ciclos>0){
                    validacion = true;
                }else{
                    System.out.println("\n%% Se ingreso una opcion invalida [Ingrese una cantidad mayor a 0] %%");
                }
            }
        }catch(Exception e){
            String f = "Error en la lectura"+e.toString();
            throw new Exception(f);
        }
        return ciclos;
    }

    public void estadoPrograma(boolean est){
        if(est==true){
            System.out.println("El programa ha sido ingresado a la memoria.");
        }else{
            System.out.println("El programa ha ido a cola de espera.");
        }
    }

    public void avisoProgramaEjecutado(){
        System.out.println("El programa ha sido ejecutado.");
    }

    public void avisoCicloReloj(){
        System.out.println("Se ha avanzado un ciclo de reloj.");
    }

    public void mostrarProgramasFinalizados(String programasFinalizados){
        System.out.println("Los programas que han sido finalizados son: "+programasFinalizados);;
    }

    public void avisoNoPrograma(String nombreP){
        System.out.println("El programa ingresado *" + nombreP + "* no se encuentra en la memoria.");
    }

    /** 
     * Método que indica que el ingreso de opcion no es válido.
     */
    public void ingresoIncorrecto(){
        System.out.println("\n%% Opcion invalida, intentelo otra vez. %%");
    }

    /** 
     * Método que imprime la despedida.
     */
    public void despedida(){
        System.out.println("\n\nHa salido de su memoria, que tenga un feliz día. :)\n");
    }

    public void avisoError(){
        System.out.println("Ha ocurrido un error al ejecutar el programa.");
    }

    public void estadoRAM(String estadoRAM){
        System.out.println(estadoRAM);
    }

    public boolean leerArchivo(){
        boolean respuesta = false;
        System.out.println("\nExiste un archivo con programas ya instalados. ¿Desea leerlo? (Si/No)");
        String resp = scan.nextLine();
        
        if(resp.equals("Si") || resp.equals("si") || resp.equals("SI")){
            respuesta = true;
            System.out.println("-> Se ha elegido abrir el archivo.");
        }else if(resp.equals("No") || resp.equals("no") || resp.equals("NO")){
            respuesta = false;
            System.out.println("-> Se ha elegido no abrir el archivo.");
        }else{
            System.out.println("\n%% Se ingreso una opcion invalida [Ingrese *Si* o *No*] %%");
        }
        return respuesta;
    }

    public void mostrarRAM(int total, int disp, int nodisp){
        System.out.println("\n@ La cantidad de memoria total es: "+total);
        System.out.println("@ La cantidad de memoria disponible es: "+disp);
        System.out.println("@ La cantidad de memoria no disponible (uso) es: "+nodisp);
    }

    public void mostrarProgramasEjecucion(String programasE){
        System.out.println("\n@ Programas en ejecucion: "+programasE);
    }

    public void mostrarProgramasCola(String programasC){
        System.out.println("\n@ Programas en cola: "+programasC);
    }

    public void mostrarEspacioPrograma(String nombrePrograma, int espacioPrograma){
        System.out.println("\nEl programa: *"+nombrePrograma+"* ocupa "+espacioPrograma+" espacios.");
    }

    public void mostrarEstadoRAM (int tamanoM, String estadoM){
        System.out.println("\nEl tamano total de la memoria: "+tamanoM+"\n");
        System.out.println(estadoM);
    }
}