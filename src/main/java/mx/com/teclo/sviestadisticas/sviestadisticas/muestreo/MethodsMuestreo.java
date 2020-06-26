package mx.com.teclo.sviestadisticas.sviestadisticas.muestreo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mx.com.teclo.sviestadisticas.sviestadisticas.estadistica.vo.DataMuestreoVO;
import mx.com.teclo.sviestadisticas.sviestadisticas.estadistica.vo.ExpedienteVO;

public class MethodsMuestreo {

	static String [][] matrizCodTamMuestra = {
			{"ti","tf","s1","s2","s3","s4","I","II","III"},
			{"2","8","A","A","A","A","A","A","B"},
			{"9","15","A","A","A","A","A","B","C"}, 
			{"16","25","A","A","B","B","B","C","D"},
			{"26","50","A","B","B","C","C","D","E"},
			{"51","90","B","B","C","C","C","E","F"},
			{"91","150","B","B","C","D","D","F","G"},
			{"151","280","B","C","D","E","E","G","H"},
			{"281","500","B","C","D","E","F","H","J"},
			{"501","1200","C","C","E","F","G","J","K"},
			{"1201","3200","C","D","E","G","H","K","L"},
			{"3201","10000","C","D","F","G","J","L","M"},
			{"10001","35000","C","D","F","H","K","M","N"},
			{"35001","150000","D","E","G","J","L","N","P"},
			{"150001","500000","D","E","G","J","M","P","Q"},
			{"500001","2000000","D","E","E","J","N","Q","R"}
	};

	static String [][] tablaGeneralInspeccionNormal = {
			{"le","tm","ac","re"},
			{"A","2","0","1"},
			{"B","3","0","1"},
			{"C","5","0","1"},
			{"D","8","0","1"},
			{"E","13","0","1"},
			{"F","20","1","2"},
			{"G","32","1","2"},
			{"H","50","1","2"},
			{"J","80","2","3"},
			{"K","125","3","4"},
			{"L","200","5","6"},
			{"M","315","7","8"},
			{"N","500","10","11"},
			{"P","800","14","15"},
			{"Q","1250","21","22"},
			{"R","2000","21","22"},
	};

	static String [][] tablaGeneralInspeccionRigurosa = {
			{"le","tm","ac","re"},
			{"A","2","0","1"},
			{"B","3","0","1"},
			{"C","5","0","1"},
			{"D","8","0","1"},
			{"E","13","0","1"},
			{"F","20","0","1"},
			{"G","32","1","2"},
			{"H","50","1","2"},
			{"J","80","1","2"},
			{"K","125","2","3"},
			{"L","200","3","4"},
			{"M","315","5","6"},
			{"N","500","8","9"},
			{"P","800","12","13"},
			{"Q","1250","18","19"},
			{"R","2000","18","19"},
			{"S","3150","18","19"},
	};

	public DataMuestreoVO getTamMuestra(int tamLote,String nivelInspeccion,String tipoInspeccion){
		DataMuestreoVO data = new DataMuestreoVO();

		for(int i=1;i<matrizCodTamMuestra.length;i++){
			int ti= Integer.parseInt(matrizCodTamMuestra[i][0]);
			int tf= Integer.parseInt(matrizCodTamMuestra[i][1]);
			if(tamLote>=ti&&tamLote<=tf){
				for(int j1=0;j1<1;j1++){
					for(int j2=0;j2<9;j2++){
						if(matrizCodTamMuestra[j1][j2].equals(nivelInspeccion)){
							data.setLetra(matrizCodTamMuestra[i][j2]);
						}
					}   
				}
			}
		}

		//	        Aquí se obtiene el tamaño de la muestra y la cantidad de aceptción y rechazo
		if(tipoInspeccion.equals("rigurosa")){
			for(int i=1;i<tablaGeneralInspeccionRigurosa.length;i++){
				if(data.getLetra().equals(tablaGeneralInspeccionRigurosa[i][0])){
					data.setTamMuestra(Integer.parseInt(tablaGeneralInspeccionRigurosa[i][1]));
					data.setCantAceptacion(Integer.parseInt(tablaGeneralInspeccionRigurosa[i][2]));
					data.setCantRechazo(Integer.parseInt(tablaGeneralInspeccionRigurosa[i][3]));

				}
			}
		}else if(tipoInspeccion.equals("normal")){
			for(int i=1;i<tablaGeneralInspeccionNormal.length;i++){
				if(data.getLetra().equals(tablaGeneralInspeccionNormal[i][0])){
					data.setTamMuestra(Integer.parseInt(tablaGeneralInspeccionNormal[i][1]));
					data.setCantAceptacion(Integer.parseInt(tablaGeneralInspeccionNormal[i][2]));
					data.setCantRechazo(Integer.parseInt(tablaGeneralInspeccionNormal[i][3]));
				}
			}
		}

		return data;
	}


	public ArrayList<Integer> getListOfUniqueRandomValues(int tamLote, int tamMuestra){
		ArrayList<Integer> list = new ArrayList<Integer>();
		ArrayList<Integer> listMuestra = new ArrayList<Integer>();
		for (int i=0; i<tamLote; i++) {
			list.add(new Integer(i));
		}
		Collections.shuffle(list);
		for (int i=0; i<tamMuestra; i++) {
			listMuestra.add(list.get(i));
		}

		return listMuestra;
	}

	public List<ExpedienteVO> getRows(ArrayList<Integer> lista, List<ExpedienteVO> listExpedientes){
		List<ExpedienteVO> registrosMuestraCSV = new ArrayList<ExpedienteVO>();
		for(int i=0;i<lista.size();i++){
			registrosMuestraCSV.add(listExpedientes.get(lista.get(i)));
		}
		return registrosMuestraCSV;
	}

	public List<ExpedienteVO> obtenerDataMuestreo(List<ExpedienteVO> listExpedientes) {
		//Parámetros:
		String nivelInspeccion="III";
		System.out.println("Nivel de Inspección: "+nivelInspeccion);
		System.out.println("Tipo: rigurosa");
		int tamLote = listExpedientes.size();
		//Procedimiento:
		//2. Obtenemos los datos de la muestra conforme al metodo de muestreo:
		DataMuestreoVO data = getTamMuestra(tamLote,nivelInspeccion,"rigurosa");
		data.setTamLote(tamLote);

		//3. Obtenemos los registros a evaluar:
		System.out.println("Tamaño del Lote: "+data.getTamLote());
		ArrayList<Integer> listaRows = getListOfUniqueRandomValues(data.getTamLote(), data.getTamMuestra());
		System.out.println("Tamaño muestra: "+listaRows.size());

		System.out.println("Letra: "+data.getLetra()+"\n"+
				"Cant. Aceptacion: "+data.getCantAceptacion()+"\n"+
				"Cant. Rechazo: "+data.getCantRechazo()
				);
		List<ExpedienteVO> registrosMuestraCSV = getRows(listaRows, listExpedientes);
		return registrosMuestraCSV;
	}
}
