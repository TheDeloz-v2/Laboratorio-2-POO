/**
 * Clase Controlador
 * Encargada de ejecutar el programa, llevar a cabo la seleccion de opciones, comunicarse con las
 * demás clases y sus respectivos procesos.
 * 
 * @version 1.0, 15/09/2021
 * finalizacion 17/09/2021
 * 
 * @author Diego E. Lemus L. - 21469
 */

 public class Controlador{
     /** 
     * @param args
     */
	public static void main(String[] args) throws Exception{

		//-----PROPIEDADES-----
        Memoria memoria = null;
        Vista vista = new Vista();
        Programa programa;
        int op = 0;

        try{
            //Muestra la bienvenida
            vista.bienvenida();

            while(op!=9){
                //Muestra el menu
                op = vista.mostrarMenu();

                switch(op){
                    //OPCION 1: Inicializar una nueva RAM
                    case 1:
                        String tipoRAM = vista.pedirtipoRAM();

                        //Se elige tipo DDR
                        if(tipoRAM.equals("DDR")){
                            memoria = new Memoria(tipoRAM);
                        //Se elige tipo SDR
                        }else if(tipoRAM.equals("SDR")){
                            int tamanoRAM = vista.pedirTamanoRAM();
                            memoria = new Memoria(tipoRAM, tamanoRAM);
                        }

                        //Se pregunta si se lee el archivo programas instalados
                        boolean leerArchivoC = vista.leerArchivo();
                        if(leerArchivoC==true){
                            memoria.leerArchivo();
                        }
                    break;

                    //OPCION 2: Ingresar programa
                    case 2:
                        String nombreP = vista.pedirnombrePrograma();
                        int espacioP = vista.pedirEspacioPrograma();
                        int ciclosP = vista.pedirCiclosPrograma();
                        
                        programa = new Programa(nombreP, espacioP, ciclosP);
                        //Se guarda el programa
                        memoria.ingresarPrograma(programa);
                    break;
                    
                    //OPCION 3: Visualizar cantidad de memoria RAM
                    case 3:
                        int[] cantidadRAM = memoria.cantMemoria();
                        int total = cantidadRAM[0];
                        int disp = cantidadRAM[1];
                        int nodisp = cantidadRAM[2];

                        vista.mostrarRAM(total, disp, nodisp);
                    break;

                    //OPCION 4: Visualizar espacio de un programa
                    case 4:
                        String nombrePBuscar = vista.pedirnombrePrograma();
                        int espacioPBuscar = memoria.EspacioPrograma(nombrePBuscar);
                        
                        //En caso de que el programa no exista
                        if(espacioPBuscar != 0){
                            vista.mostrarEspacioPrograma(nombrePBuscar, espacioPBuscar);
                        }else{
                            vista.avisoNoPrograma(nombrePBuscar);
                        }
                    break;

                    //OPCION 5: Visualizar programas en ejecucion
                    case 5:
                        String todosprogramasE = memoria.getProgramasEjecucion();
                        vista.mostrarProgramasEjecucion(todosprogramasE);
                    break;

                    //OPCION 6: Visualizar programas en cola de espera
                    case 6:
                        String todosprogramasC = memoria.getProgramasCola();
                        vista.mostrarProgramasEjecucion(todosprogramasC);
                    break;

                    //OPCION 7: Visualizar estado de memoria RAM
                    case 7:
                        int tamtotal = memoria.getTamanoTotal();
                        String estadoM = memoria.estadoRAM();
                        vista.mostrarEstadoRAM(tamtotal, estadoM);
                    break;

                    //OPCION 8: Realizar ciclo de reloj
                    case 8:
                        vista.avisoCicloReloj();
                        memoria.realizarCicloReloj();
                    break;

                    //OPCION 9: Salir
                    case 9:
                        op = 9;
                    break;

                    default://En caso de valor de opcion invalido
                        vista.ingresoIncorrecto();
                }
            }
            //Mensaje de despedida
            vista.despedida();
        }catch(Exception e){
            vista.avisoError();
        }
    }
}
