package mx.com.teclo.sviestadisticas.sviestadisticas.estadistica;

import java.io.IOException;
import java.util.List;

import mx.com.teclo.sviestadisticas.sviestadisticas.estadistica.dbconnection.DBConnect;
import mx.com.teclo.sviestadisticas.sviestadisticas.estadistica.vo.ExpedienteVO;
import mx.com.teclo.sviestadisticas.sviestadisticas.muestreo.MethodsMuestreo;

public class Main {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		System.out.println("*******Versión 1.3");
		MethodsMuestreo muestreo = new MethodsMuestreo();
		MethodsEstadisticaV2 estadistica = new MethodsEstadisticaV2();
		int condicionparo=2050;
		System.out.println("*******El proceso puede tardar un poco...");
		DBConnect baseDatos = new DBConnect();
		List<ExpedienteVO> listExpedientes = baseDatos.getAllRegistros(condicionparo);
		List<ExpedienteVO> listmuestra = muestreo.obtenerDataMuestreo(listExpedientes);		
		
		System.out.println("********RESUMEN GENERAL*********");

//		System.out.println("Se obtuvieron datos de muestreo");
//		String listarray="154071,248563,248787,252574,252807,253908,256946,302130,302134,303575,303766,306903,310615,342495,345401,475792,505628,505629,523236,633845,698519,700953,717587,718277,720869,753726,778382,781689,1034846,1037557,1040179,1040785,1040922";
//		List<ExpedienteVO> listExpedientes = baseDatos.getAllRegistrosByListOfIdRegistros(listarray);
//		System.out.println("Se ha terminado");
//		String listarray="21,1037,1462,1573,1760,1842,1993,3544,3674,3936,4315,4551, 4723,6566,6725,8480,8722,9378,9793,13778,14097,14542";
//		List<ExpedienteVO> listExpedientes = baseDatos.getAllRegistrosByListOfIdRegistros(listarray);
		
		
		estadistica.analisisDatos(listmuestra);
		
        System.out.println("\n********Lista de Expedientes Muestra*********");
        estadistica.printListExpedientesAll(listmuestra);
	}

}
