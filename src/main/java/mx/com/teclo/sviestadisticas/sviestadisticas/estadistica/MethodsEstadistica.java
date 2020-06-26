package mx.com.teclo.sviestadisticas.sviestadisticas.estadistica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.teclo.sviestadisticas.sviestadisticas.estadistica.dbconnection.DBConnect;
import mx.com.teclo.sviestadisticas.sviestadisticas.estadistica.vo.ConcurrenceVO;
import mx.com.teclo.sviestadisticas.sviestadisticas.estadistica.vo.ExpedienteVO;

public class MethodsEstadistica {

	public int[] getListOfAny(String tipo, List<ExpedienteVO> array){
		int[] listacols = new int[array.size()];
		for(int i=0;i<array.size();i++){
			if(tipo.equals("usuarios")){
				listacols[i]=array.get(i).getIduser();
			}else if(tipo.equals("moda2cols")){
				String concat=array.get(i).getIdperfil()+""+array.get(i).getIdmarca();
				listacols[i]=Integer.parseInt(concat);
			}else if(tipo.equals("moda3cols")){
				String concat=array.get(i).getIdperfil()+""+
						array.get(i).getIdmarca()+""+array.get(i).getIdsubmarca();
				listacols[i]=Integer.parseInt(concat);
			}
		}
		return listacols;
	}

	public List<ConcurrenceVO> getModaAndFeatures(String tipo, List<ExpedienteVO> listExpedientesValidacionBIt) {
		int[] modas = null;
		if(tipo.equals("moda2cols")) {
			modas = getListOfAny("moda2cols",listExpedientesValidacionBIt);
		}else if(tipo.equals("moda3cols")) {
			modas = getListOfAny("moda3cols",listExpedientesValidacionBIt);
		}

		List<ConcurrenceVO> uniquelistconcModas = findConcurrenceOfItems(modas);
		List<ConcurrenceVO> featuresModas = getFeaturesOfModas(uniquelistconcModas);
		//        printConcurrency(featuresModas);

		return featuresModas;
	}

	public List<ConcurrenceVO> getFeaturesOfModas(List<ConcurrenceVO> concurrency){

		int sizerepeated=0;
		for(int i=0;i<concurrency.size();i++){
			if(concurrency.get(i).getTimes()>=2){
				sizerepeated=sizerepeated+concurrency.get(i).getTimes();
			}
		}

		List<ConcurrenceVO> listModas = new ArrayList<ConcurrenceVO>();


		for(int i=0;i<concurrency.size();i++){
			int unique=0;
			if(concurrency.get(i).getTimes()>=2){
				double percentagepresence = (float)concurrency.get(i).getTimes()/(float)sizerepeated;
				concurrency.get(i).setPercentagepresence(percentagepresence);
				listModas.add(concurrency.get(i));
			}
		}
		return listModas;
	}

	public List<ConcurrenceVO> findConcurrenceOfItems(int[] array){
		List<ConcurrenceVO> listconc = new ArrayList<ConcurrenceVO>();
		List<Integer> listUnique = new ArrayList<Integer>();
		for(int i=0;i<array.length;i++){
			int times=0;
			//            System.out.println("existe: "+listUnique.contains(array[i]));
			if(!listUnique.contains(array[i])){
				for(int j=0;j<array.length;j++){
					if(array[i]==array[j]){
						times++;
					}
				}
				listUnique.add(array[i]);
				listconc.add(new ConcurrenceVO(array[i],times));
			}
		}
		return listconc;
	}

	public void printConcurrency(List<ConcurrenceVO> concurrency){
		for(int i=0;i<concurrency.size();i++){
			System.out.println("number: "+concurrency.get(i).getNumber()
					+" Veces: "+concurrency.get(i).getTimes()
					+" Porcentage "+concurrency.get(i).getPercentagepresence()+" | ");
		}
	}
	
	public ExpedienteVO filtroErrorMoto(ExpedienteVO expediente) {
		Map<Integer,ExpedienteVO> errorMoto = new HashMap<Integer,ExpedienteVO>();

		if(expediente.getCdperfiloriginal().equals("MOTOCICLETA")&&expediente.getCdperfil().equals("MOTOCICLETA")) {
			if(expediente.getCdplacadeloriginal().toUpperCase().equals("NO_PLATE")||
					expediente.getCdplacadel().equals("NO_PLATE")) {
				expediente.setErrorperfil(true);
				expediente.setProbacertacionperfil(75.0);
				expediente.setIdperfilcorrecto(0);
				expediente.setIdmarcacorrecto(0);
				expediente.setIdsubmarca(0);
				expediente.setTxperfilcorrecto("");
			}
			return expediente;
		}
		return null;
	}

	public ExpedienteVO algoritmoAnalisisPerfiles(ExpedienteVO expediente) {
		DBConnect baseDatos = new DBConnect();
		
		ExpedienteVO errorMoto = filtroErrorMoto(expediente);
		if(errorMoto!=null) {
			return errorMoto;
		}
		
		if(expediente.getCdperfiloriginal().equals("MOTOCICLETA")&&expediente.getCdperfil().equals("MOTOCICLETA")) {
			if(expediente.getCdplacadeloriginal().toUpperCase().equals("NO_PLATE")||
					expediente.getCdplacadel().equals("NO_PLATE")) {
				expediente.setErrorperfil(true);
				expediente.setProbacertacionperfil(75.0);
				expediente.setIdperfilcorrecto(0);
				expediente.setIdmarcacorrecto(0);
				expediente.setIdsubmarca(0);
				expediente.setTxperfilcorrecto("");
			}
			return expediente;
		}

		//		met.printExpediente(expediente);
		int pac=Integer.parseInt(expediente.getIdperfil()+""+expediente.getIdmarca()+""+expediente.getIdsubmarca());

		//		OBTENEMOS INFORMACION DE BITACORA		
		List<ExpedienteVO> listExpedientesValidacionBIt = baseDatos.getBitValidacionByFolio(expediente.getNu_expediente());
		//		met.printListExpediente(listExpedientesValidacionBIt);
		String str_pOld=expediente.getCdperfiloriginal();

		//		Aquí obtenemos información sobre las modas encontradas de 2 cols
		List<ConcurrenceVO> listOfmodas2cols= getModaAndFeatures("moda2cols",listExpedientesValidacionBIt);

		//		Aquí obtenemos información sobre las modas encontradas de 3 cols
		List<ConcurrenceVO> listOfmodas3cols= getModaAndFeatures("moda3cols",listExpedientesValidacionBIt);


		//		COMIENZA EL ALGORITMO DE VERIFICACIÓN
		if(listOfmodas2cols.size()==1){
			if(listOfmodas3cols.size()>=1){

				//                System.out.println("pac: "+pac);
				double porcpresence=0;
				for(int i=0;i<listOfmodas3cols.size();i++){
					if(pac==listOfmodas3cols.get(i).getNumber()){
						porcpresence=listOfmodas3cols.get(i).getPercentagepresence();
						if(porcpresence<0.5){
							//                            System.out.println("%MOD_PAC_3COLS <=0.5");
							//                            System.out.println("SET MOD_MAY_PORC: "+met.getModaMayPorc(listOfmodas3cols));
							expediente.setErrorperfil(true);
							expediente.setProbacertacionperfil(75.0);
							expediente = findPerfilFromConcat(getModaMayPorc(listOfmodas3cols), listExpedientesValidacionBIt, expediente);
						}else if(porcpresence==0.5) {
							//                        	System.out.println("SET SUBMARCA NO DETECTABLE SE RECOMIENDA VALIDACIÓN PERO NO ES TAN GRAVE -> cambiar a incorrecto");
						}else if(porcpresence>0.5){
							//                            System.out.println("%MOD_PAC_3COLS >0.5");
							//                            System.out.println("SET PAC: "+pac);
							expediente.setErrorperfil(false);
							expediente.setProbacertacionperfil(75.0);
						}

						break;
					}
				}
				if(porcpresence==0) {
					//               	 System.out.println("SET MOD_MAY_PORC: "+met.getModaMayPorc(listOfmodas3cols));
					expediente.setErrorperfil(true);
					expediente.setProbacertacionperfil(85.0);
					expediente = findPerfilFromConcat(getModaMayPorc(listOfmodas3cols), listExpedientesValidacionBIt, expediente);
				}

			}else if(listOfmodas3cols.size()==0){
				//                System.out.println("No hayModa3cols: "+listOfmodas3cols.size());
				//                System.out.println("SET PAC AND SUBMARC_NO_DETECTABLE: "+pac);
				expediente.setErrorperfil(true);
				expediente.setProbacertacionperfil(85.0);
				expediente = findPerfilFromConcat(getModaMayPorc(listOfmodas3cols), listExpedientesValidacionBIt, expediente);
			}
		}else if(listOfmodas2cols.size()>1||listOfmodas2cols.size()==0){
			//            System.out.println("featuresModas2cols: "+listOfmodas2cols.size());
			if(expediente.getCdperfil().equals(str_pOld)) {
				//            	System.out.println("SET PAC: "+pac);
			}else {
				int perfIgualOld = getThe1rtPacEqualsPold(listExpedientesValidacionBIt, str_pOld);
				if(perfIgualOld==0) {//Si no hay un perfil coincidente con el original
					//            		System.out.println("Ningun perfil coincide con el viejo");
					if(listOfmodas3cols.size()>=1) {
						//            			System.out.println("pac: "+pac);
						double porcpresence=0;
						for(int i=0;i<listOfmodas3cols.size();i++){
							if(pac==listOfmodas3cols.get(i).getNumber()){
								porcpresence=listOfmodas3cols.get(i).getPercentagepresence();
								if(porcpresence<0.5){
									//                                    System.out.println("%MOD_PAC_3COLS <=0.5");
									//                                    System.out.println("SET MOD_MAY_PORC: "+met.getModaMayPorc(listOfmodas3cols));
									expediente.setErrorperfil(true);
									expediente.setProbacertacionperfil(65.0);
									expediente = findPerfilFromConcat(getModaMayPorc(listOfmodas3cols), listExpedientesValidacionBIt, expediente);
								}else if(porcpresence==0.5) {
									if(expediente.getCdperfiloriginal().equals("4X4 OFF ROAD")||expediente.getCdperfiloriginal().equals("4X4 OF ROAD")||
											expediente.getCdperfiloriginal().equals("PICKUP")) {
										if(expediente.getCdperfil().equals("4X4 OFF ROAD")||
												expediente.getCdperfil().equals("4X4 OF ROAD")||
												expediente.getCdperfil().equals("PICKUP")
												) {

										}else {
											System.out.println("SET PERFIL  NO DETECTABLE SE RECOMIENDA VALIDACIÓN");
											expediente.setErrorperfil(true);
											expediente.setProbacertacionperfil(85.0);
										}
									}else if(expediente.getCdperfiloriginal().equals("SUV")||expediente.getCdperfiloriginal().equals("CAMIONETA TOURING")) {
										if(expediente.getCdperfil().equals("CAMIONETA TOURING")||
												expediente.getCdperfil().equals("SUV")
												) {

										}else {
											System.out.println("SET PERFIL  NO DETECTABLE SE RECOMIENDA VALIDACIÓN");
											expediente.setErrorperfil(true);
											expediente.setProbacertacionperfil(85.0);
										}
									}else if(expediente.getCdperfiloriginal().equals("LIMUSINE")||expediente.getCdperfiloriginal().equals("FUNEBRE")) {
										if(expediente.getCdperfil().equals("FUNEBRE")||
												expediente.getCdperfil().equals("LIMUSINE")
												) {

										}else {
											System.out.println("SET PERFIL  NO DETECTABLE SE RECOMIENDA VALIDACIÓN");
											expediente.setErrorperfil(true);
											expediente.setProbacertacionperfil(85.0);
										}
									}
									//                                	System.out.println("SET PERFIL  NO DETECTABLE SE RECOMIENDA VALIDACIÓN");

								}else if(porcpresence>0.5){
									//                                    System.out.println("%MOD_PAC_3COLS >0.5");
									//                                    System.out.println("SET PAC: "+pac);
									expediente.setErrorperfil(false);
									expediente.setProbacertacionperfil(55.0);
								}
								break;
							}
						}
						if(porcpresence==0) {
							//                        	 System.out.println("Sin % presencia, ET MOD_MAY_PORC: "+met.getModaMayPorc(listOfmodas3cols));
							expediente.setErrorperfil(true);
							expediente.setProbacertacionperfil(95.0);
							expediente = findPerfilFromConcat(getModaMayPorc(listOfmodas3cols), listExpedientesValidacionBIt, expediente);
						}
					}else {
						if(listExpedientesValidacionBIt.size()==1||listExpedientesValidacionBIt.size()==2) {
							//            				System.out.println("UNA SOLA VALIDACIÓN NO SE MARCA COMO ERROR");
						}else {
							//            				System.out.println("SET PERFIL  NO DETECTABLE ");
							expediente.setErrorperfil(true);
							expediente.setProbacertacionperfil(85.0);
						}

					}

				}else {
					//Perfil debe ser correcto
					//            		System.out.println("Si hay perfil que coincide con el viejo");
					//            		System.out.println("SET the1rst==POLD: "+perfIgualOld);
					//            		expediente.setErrorperfil(true);
					//                    expediente.setProbacertacionperfil(75.0);
					//                    expediente = met.findPerfilFromConcat(perfIgualOld, listExpedientesValidacionBIt, expediente);
				}
			}
		}
		return expediente;
	}

	public int getModaMayPorc(List<ConcurrenceVO> listmodas){
		double[] decMax = getListOfAnyConcurrency("percentage",listmodas);
		double max = getMaxInList(decMax);
		int modamayporc=0;
		for(int i=0;i<listmodas.size();i++){
			if(max==listmodas.get(i).getPercentagepresence()){
				modamayporc=listmodas.get(i).getNumber();
			}
		}
		return modamayporc;
	}

	public double[] getListOfAnyConcurrency(String tipo, List<ConcurrenceVO> array){
		double[] listacols = new double[array.size()];
		for(int i=0;i<array.size();i++){
			if(tipo.equals("percentage")){
				listacols[i]=array.get(i).getPercentagepresence();
			}
		}
		return listacols;
	}

	public double getMaxInList(double[] decMax){
		double max = 0;
		if(decMax.length>1) {
			max = decMax[0];
			for (int counter = 1; counter < decMax.length; counter++)
			{
				if (decMax[counter] > max)
				{
					max = decMax[counter];
				}
			}
		}
		
		return max;
	}

	public int getThe1rtPacEqualsPold(List<ExpedienteVO> listExpedientesValidacionBIt, String pOld) {
		int perfil=0;
		for(int i=0;i<listExpedientesValidacionBIt.size();i++) {
			if(listExpedientesValidacionBIt.get(i).getCdperfil().equals(pOld)) {
				perfil=Integer.parseInt(
						listExpedientesValidacionBIt.get(i).getIdperfil()+""+
								listExpedientesValidacionBIt.get(i).getIdmarca()+""+
								listExpedientesValidacionBIt.get(i).getIdsubmarca());
				break;
			}

		}
		return perfil;
	}

	public ExpedienteVO validaPlaca(ExpedienteVO expediente) {
		if(expediente.getCdperfil().equals("MOTOCICLETA")) {
			if(!(expediente.getCdplacadel().toUpperCase()).equals(expediente.getCdplacatras().toUpperCase())){
				expediente.setErrorplaca(true);
			}
		}else {
			expediente.setErrorplaca(false);
		}	
		return expediente;
	}

	public ExpedienteVO validaEntidad(ExpedienteVO expediente) {
		if(expediente.getCdperfil().equals("MOTOCICLETA")) {
			if(!(expediente.getCdentidadel().toUpperCase()).equals(expediente.getCdentidadtras().toUpperCase())){
				expediente.setErrorentidad(true);
			}
		}else {
			expediente.setErrorentidad(false);
		}	
		return expediente;
	}

	public void printErrores(List<ExpedienteVO> listExpedientes) {
		for(int x=0;x<listExpedientes.size();x++) {
			if(listExpedientes.get(x).getTotalerrores()>=1) {
				printExpediente(listExpedientes.get(x));
			}
		}
	}

	public void analisisDatos(List<ExpedienteVO> listExpedientes) {
		DBConnect baseDatos = new DBConnect();
		ExpedienteVO expediente = null;
		int erroresEntidad=0;
		int erroresPlaca=0;
		int erroresPerfil=0;
		int totErrores=0;
		for(int x=0;x<listExpedientes.size();x++) {
//			System.out.println(x);
			expediente = listExpedientes.get(x);
			//			OBTENEMOS INFORMACION DEL PERFILACTUAL
			expediente = algoritmoAnalisisPerfiles(expediente);
			expediente = validaPlaca(expediente);
			expediente = validaEntidad(expediente);

			int errorexp=0;
			if(expediente.isErrorentidad()||expediente.isErrorplaca()||expediente.isErrorperfil()) {
				totErrores++;
				if(expediente.isErrorentidad()) {errorexp++;erroresEntidad++;}
				if(expediente.isErrorplaca()) {errorexp++;erroresPlaca++;}
				if(expediente.isErrorperfil()) {errorexp++;erroresPerfil++;}
			}
			
			expediente.setTotalerrores(errorexp);

			listExpedientes.set(x, expediente);
			//			met.printExpediente(listExpedientes.get(x));
		}
		
		float porcerror = (float)totErrores*100/(float)listExpedientes.size();
		
		System.out.println("Errores Perfil: "+erroresPerfil);
		System.out.println("Errores Placa: "+erroresPlaca);
		System.out.println("Errores Entidad: "+erroresEntidad);

		System.out.println("**Total de Errores: "+totErrores);

		System.out.println("**Procentaje Error: "+porcerror+"\n");
		
		System.out.println("********DETALLES DE ERRORES*********");
//		System.out.println("Total exp.: "+listExpedientes+" erroresPerfil: "+erroresPerfil+", erroresPlaca: "+erroresPlaca+", erroresEntidad: "+erroresEntidad);
		System.out.println("getIdregistrocsv | getNu_expediente | getCdplacadel | getCdplacatras | getCdentidadel | getCdentidadtras"
				+ " | getCdperfiloriginal  | getCdperfil  | getIdperfil  | getIdmarca  | getIdsubmarca  | getTxperfil"
				+ "| isErrorplaca  | isErrorentidad  | isErrorperfil  | getProbacertacionperfil  | getTxperfilcorrecto  | getIdperfilcorrecto"
				+ "| getIdmarcacorrecto  | getIdsubmarcacorrecto  | getTotalerrores");
		printErrores(listExpedientes);
	}

	public void printExpediente(ExpedienteVO expediente){
		System.out.println(expediente.getIdregistrocsv()
				+" | "+expediente.getNu_expediente()
				+" | "+expediente.getCdplacadel()
				+"  | "+expediente.getCdplacatras()
				+" | "+expediente.getCdentidadel()
				+" | "+expediente.getCdentidadtras()
				+" | "+expediente.getCdperfiloriginal()
				+" | "+expediente.getCdperfil()
				+" | "+expediente.getIdperfil()
				+" | "+expediente.getIdmarca()
				+" | "+expediente.getIdsubmarca()
				+" | "+expediente.getTxperfil()

				+" | "+expediente.isErrorplaca()
				+" | "+expediente.isErrorentidad()
				+" | "+expediente.isErrorperfil()
				+" | "+expediente.getProbacertacionperfil()
				+" | "+expediente.getTxperfilcorrecto()
				+" | "+expediente.getIdperfilcorrecto()
				+" | "+expediente.getIdmarcacorrecto()
				+" | "+expediente.getIdsubmarcacorrecto()
				+" | "+expediente.getTotalerrores()
				);
	}

	public ExpedienteVO findPerfilFromConcat(int concat, List<ExpedienteVO> listExpedientesValidacionBIt, ExpedienteVO expediente) {
		for(int i=0;i<listExpedientesValidacionBIt.size();i++) {
			int concattemp=Integer.parseInt(listExpedientesValidacionBIt.get(i).getIdperfil()+""
					+listExpedientesValidacionBIt.get(i).getIdmarca()+""
					+listExpedientesValidacionBIt.get(i).getIdsubmarca());
			if(concattemp==concat) {
				expediente.setIdperfilcorrecto(listExpedientesValidacionBIt.get(i).getIdperfil());
				expediente.setIdmarcacorrecto(listExpedientesValidacionBIt.get(i).getIdmarca());
				expediente.setIdsubmarca(listExpedientesValidacionBIt.get(i).getIdsubmarca());
				expediente.setTxperfilcorrecto(listExpedientesValidacionBIt.get(i).getTxperfilcorrecto());
			}
		}
		return expediente;	
	}
}
