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
            vista.bienvenida();

            while(op!=9){
                op = vista.mostrarMenu();

                switch(op){
                    case 1:
                        String tipoRAM = vista.pedirtipoRAM();

                        if(tipoRAM.equals("DDR")){
                            memoria = new Memoria(tipoRAM);
                        }else if(tipoRAM.equals("SDR")){
                            int tamanoRAM = vista.pedirTamanoRAM();
                            memoria = new Memoria(tipoRAM, tamanoRAM);
                        }

                        boolean leerArchivoC = vista.leerArchivo();
                        if(leerArchivoC==true){
                            memoria.leerArchivo();
                        }
                        System.out.println("xd");
                    break;

                    case 2:
                        String nombreP = vista.pedirnombrePrograma();
                        int espacioP = vista.pedirEspacioPrograma();
                        int ciclosP = vista.pedirCiclosPrograma();
                        
                        programa = new Programa(nombreP, espacioP, ciclosP);
                        memoria.ingresarPrograma(programa);
                    break;

                    case 3:
                        int[] cantidadRAM = memoria.cantMemoria();
                        int total = cantidadRAM[0];
                        int disp = cantidadRAM[1];
                        int nodisp = cantidadRAM[2];

                        vista.mostrarRAM(total, disp, nodisp);
                    break;

                    case 4:
                        String nombrePBuscar = vista.pedirnombrePrograma();
                        int espacioPBuscar = memoria.EspacioPrograma(nombrePBuscar);
                        
                        if(espacioPBuscar != 0){
                            vista.mostrarEspacioPrograma(nombrePBuscar, espacioPBuscar);
                        }else{
                            vista.avisoNoPrograma(nombrePBuscar);
                        }
                    break;

                    case 5:
                        String todosprogramasE = memoria.getProgramasEjecucion();
                        vista.mostrarProgramasEjecucion(todosprogramasE);
                    break;

                    case 6:
                        String todosprogramasC = memoria.getProgramasCola();
                        vista.mostrarProgramasEjecucion(todosprogramasC);
                    break;

                    case 7:
                        int tamtotal = memoria.getTamanoTotal();
                        String estadoM = memoria.estadoRAM();
                        vista.mostrarEstadoRAM(tamtotal, estadoM);
                    break;

                    case 8:
                        vista.avisoCicloReloj();
                        memoria.realizarCicloReloj();
                    break;

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
            
        }
    }
}
