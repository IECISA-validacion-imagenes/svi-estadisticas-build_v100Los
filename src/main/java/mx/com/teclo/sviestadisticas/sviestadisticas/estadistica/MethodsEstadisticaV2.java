package mx.com.teclo.sviestadisticas.sviestadisticas.estadistica;

import java.util.List;

import mx.com.teclo.sviestadisticas.sviestadisticas.estadistica.dbconnection.DBConnect;
import mx.com.teclo.sviestadisticas.sviestadisticas.estadistica.vo.ExpedienteVO;

public class MethodsEstadisticaV2 {

	public ExpedienteVO validaPlacayEntidadyFiabilidadDeExpediente(ExpedienteVO expediente) {
		Boolean isFiable = true;

		if (expediente.getCdperfil().equals("MOTOCICLETA") || expediente.getIdsubmarca() == 430) {
			if (expediente.getCdplacadel().toUpperCase().equals("NO_PLATE")) {
				if (!expediente.getCdplacatras().toUpperCase().equals("NO_PLATE")) {
					expediente.setErrorplaca(false);
				} else {
					expediente.setErrorplaca(true);
					isFiable = false;
				}
			} else {
				expediente.setErrorplaca(true);
				isFiable = false;
			}

		} else if (expediente.getCdperfil().equals("BICICLETA") || expediente.getCdperfil().equals("PERSONAS")
				|| expediente.getCdperfil().equals("NOCLASIF") || expediente.getCdperfil().equals("TRICICLO")
				|| expediente.getCdperfil().equals("OTROS")) {
			if (expediente.getCdplacadel().toUpperCase().equals("NO_PLATE")) {
				if (expediente.getCdplacatras().toUpperCase().equals("NO_PLATE")) {
					expediente.setErrorplaca(false);
				} else {
					expediente.setErrorplaca(true);
					isFiable = false;
				}
			} else {
				expediente.setErrorplaca(true);
				isFiable = false;
			}
		} else {
			if ((!expediente.getCdplacadel().toUpperCase().equals("NO_PLATE"))
					&& (!expediente.getCdplacatras().toUpperCase().equals("NO_PLATE"))) {
				// Nota: La fiabilidad no la vallidamos al nivel más bajo
				if (expediente.getCdplacadel().toUpperCase().equals(expediente.getCdplacatras().toUpperCase())) {
					expediente.setErrorplaca(false);
				} else {
					expediente.setErrorplaca(true);
				}
			} else {
				expediente.setErrorplaca(true);
				isFiable = false;
			}
		}
		// Validación Entidad
		if (expediente.getCdperfil().equals("MOTOCICLETA") || expediente.getIdsubmarca() == 430) {
			if (expediente.getCdentidadel().toUpperCase().equals("NF")
					|| expediente.getCdentidadel().toUpperCase().equals("OTRO")) {
				if (!expediente.getCdentidadtras().toUpperCase().equals("NF")
						&& !expediente.getCdentidadtras().toUpperCase().equals("OTRO")) {
					expediente.setErrorentidad(false);
				} else {
					expediente.setErrorentidad(true);
					isFiable = false;
				}
			} else {
				expediente.setErrorentidad(true);
				isFiable = false;
			}

		} else if (expediente.getCdperfil().equals("BICICLETA") || expediente.getCdperfil().equals("PERSONAS")
				|| expediente.getCdperfil().equals("NOCLASIF") || expediente.getCdperfil().equals("TRICICLO")
				|| expediente.getCdperfil().equals("OTROS")) {
			if (expediente.getCdentidadel().toUpperCase().equals("NF")
					|| expediente.getCdentidadel().toUpperCase().equals("OTRO")) {
				if (expediente.getCdentidadtras().toUpperCase().equals("NF")
						|| expediente.getCdentidadtras().toUpperCase().equals("OTRO")) {
					expediente.setErrorentidad(false);
				} else {
					expediente.setErrorentidad(true);
					isFiable = false;
				}
			} else {
				expediente.setErrorentidad(true);
				isFiable = false;
			}
		} else {
			if ((!expediente.getCdentidadel().toUpperCase().equals("NF")
					&& !expediente.getCdentidadel().toUpperCase().equals("OTRO"))
					&& (!expediente.getCdentidadtras().toUpperCase().equals("NF")
							&& !expediente.getCdentidadtras().toUpperCase().equals("OTRO"))) {
				// Nota: La fiabilidad no la vallidamos al nivel más bajo
				if (expediente.getCdentidadel().toUpperCase().equals(expediente.getCdentidadtras().toUpperCase())) {
					expediente.setErrorentidad(false);
				} else {
					expediente.setErrorentidad(true);
				}
			} else {
				expediente.setErrorentidad(true);
				isFiable = false;
			}
		}
		expediente.setFiable(isFiable);
		return expediente;
	}

	public ExpedienteVO getExpedienteOld(ExpedienteVO expediente) {
		ExpedienteVO expedienteOld = new ExpedienteVO();
		expedienteOld.setCdperfil(expediente.getCdperfiloriginal());
		expedienteOld.setCdplacadel(expediente.getCdplacadeloriginal());
		expedienteOld.setCdplacatras(expediente.getCdplacatrasloriginal());
		expedienteOld.setCdentidadel(expediente.getCdentidadeloriginal());
		expedienteOld.setCdentidadtras(expediente.getCdentidadtrasoriginal());
		expedienteOld.setIdsubmarca(expediente.getIdsubmarca());
		return expedienteOld;
	}

	public boolean sonIgualesPlacaEntidadRespectoAlOriginal(ExpedienteVO expediente) {
		boolean sonIguales = false;
		if (expediente.getCdplacadel().toUpperCase().equals(expediente.getCdplacadeloriginal())
				&& expediente.getCdplacatras().toUpperCase().equals(expediente.getCdplacatrasloriginal())
				&& expediente.getCdentidadel().toUpperCase().equals(expediente.getCdentidadeloriginal())
				&& expediente.getCdentidadtras().toUpperCase().equals(expediente.getCdentidadtrasoriginal())) {
			sonIguales = true;
		}
		return sonIguales;
	}

	public boolean sonPerfilesIgualesRespectoOriginal(String perfOriginal, String perfilValidado) {
		if ((perfOriginal.equals("CARGO") || perfOriginal.equals("4X4 OFF ROAD") || perfOriginal.equals("4X4 OF ROAD")
				|| perfOriginal.equals("PICKUP"))
				&& (perfilValidado.equals("CARGO") || perfilValidado.equals("4X4 OFF ROAD")
						|| perfilValidado.equals("4X4 OF ROAD") || perfilValidado.equals("PICKUP"))) {
			return true;
		} else if ((perfOriginal.equals("SUV") || perfOriginal.equals("CAMIONETA TOURING"))
				&& (perfilValidado.equals("SUV") || perfilValidado.equals("CAMIONETA TOURING"))) {
			return true;
		} else if ((perfOriginal.equals("LIMUSINE") || perfOriginal.equals("FUNEBRE"))
				&& (perfilValidado.equals("LIMUSINE") || perfilValidado.equals("FUNEBRE"))) {
			return true;
		} else if (perfOriginal.equals(perfilValidado)) {
			return true;
		}

		return false;
	}
	
	public ExpedienteVO handleNull(ExpedienteVO expediente) {
		if(expediente.getCdperfil()==null) {
			expediente.setCdperfil("");
		}
		if(expediente.getCdplacadel()==null) {
			expediente.setCdplacadel("");
		}
		if(expediente.getCdplacatras()==null) {
			expediente.setCdplacatras("");
		}
		if(expediente.getCdentidadel()==null) {
			expediente.setCdentidadel("");
		}
		if(expediente.getCdentidadtras()==null) {
			expediente.setCdentidadtras("");
		}
		//
		if(expediente.getCdperfiloriginal()==null) {
			expediente.setCdperfiloriginal("");
		}
		if(expediente.getCdplacadeloriginal()==null) {
			expediente.setCdplacadeloriginal("");
		}
		if(expediente.getCdplacatrasloriginal()==null) {
			expediente.setCdplacatrasloriginal("");
		}
		if(expediente.getCdentidadeloriginal()==null) {
			expediente.setCdentidadeloriginal("");
		}
		if(expediente.getCdentidadtrasoriginal()==null) {
			expediente.setCdentidadtrasoriginal("");
		}
		
		return expediente;
	}

	public ExpedienteVO algoritmoAnalisisPerfiles(ExpedienteVO expediente, ExpedienteVO expedienteOld) {
		DBConnect baseDatos = new DBConnect();
		List<ExpedienteVO> listExpedientesValidacionBIt = baseDatos.getBitValidacionByFolio(expediente.getNu_expediente());
		
		
		if (!expediente.isFiable()) {

			expediente.setErrorperfil(true);

		} else if (expediente.isFiable() && !expedienteOld.isFiable()) {

			expediente.setErrorperfil(false);

		} else if (expediente.isFiable() && expedienteOld.isFiable()) {
			if (sonIgualesPlacaEntidadRespectoAlOriginal(expediente)) {

				if (sonPerfilesIgualesRespectoOriginal(expediente.getCdperfiloriginal(), expediente.getCdperfil())) {
					expediente.setErrorperfil(false);// C+1
				} else {
					// Si la probabilidad de que se equivoque el perfil original respecto del
					// validador
					// es muy baja entonces asumo que el validador se equivoca (listado por
					// perfiles)
					if (expediente.getCdperfiloriginal().toUpperCase().equals("4X4 OFF ROAD")
							|| expediente.getCdperfiloriginal().toUpperCase().equals("4X4 OF ROAD")) {
						expediente.setErrorperfil(true);// C+1
					} else {
						expediente.setErrorperfil(false);// C+1
					}
					
//					if(expediente.getCdplacadeloriginal().toUpperCase().equals(expediente.getCdplacatrasloriginal().toUpperCase())) {
//
//						expediente.setErrorperfil(true);
//						
//					}else {
//						expediente.setErrorperfil(false);
//					}
				}

			} else {
				if (sonPerfilesIgualesRespectoOriginal(expediente.getCdperfiloriginal(), expediente.getCdperfil())) {
					expediente.setErrorperfil(false);// C+1
				} else {
					if(expediente.getCdplacadeloriginal().toUpperCase().equals(expediente.getCdplacatrasloriginal().toUpperCase())) {

						expediente.setErrorperfil(true);
//						System.out.println("el exp: "+expediente.getNu_expediente()+" se va a comparar con otras validaciones: "+listExpedientesValidacionBIt.size());
						
					}else {
						expediente.setErrorperfil(false);
					}
					// Si la probabilidad de que se equivoque el perfil original respecto del
					// validador
					// es muy baja entonces asumo que el validador se equivoca (listado por
					// perfiles)
//					if (expediente.getCdperfiloriginal().toUpperCase().equals("4X4 OFF ROAD")
//							|| expediente.getCdperfiloriginal().toUpperCase().equals("4X4 OF ROAD")) {
//						expediente.setErrorperfil(true);// C+1
//					} else {
//						expediente.setErrorperfil(false);// C+1
//					}
				}
			}
		}
		return expediente;
	}

	public void analisisDatos(List<ExpedienteVO> listExpedientes) {
		DBConnect baseDatos = new DBConnect();
		ExpedienteVO expediente = null;
		int erroresEntidad = 0;
		int erroresPlaca = 0;
		int erroresPerfil = 0;
		int totErrores = 0;
		for (int x = 0; x < listExpedientes.size(); x++) {
//			System.out.println(x);
			expediente = listExpedientes.get(x);
			expediente = handleNull(expediente);
			
			// Obtenemos validacion de placa, entidad y fiabilidad
			expediente = validaPlacayEntidadyFiabilidadDeExpediente(expediente);

			// Preparamos expediente Viejo para obtener fiabilidad
			ExpedienteVO expedienteOld = getExpedienteOld(expediente);
			expedienteOld = validaPlacayEntidadyFiabilidadDeExpediente(expedienteOld);

			// Validación de Expedientes
			expediente = algoritmoAnalisisPerfiles(expediente, expedienteOld);

			int errorexp = 0;
			if (expediente.isErrorentidad() || expediente.isErrorplaca() || expediente.isErrorperfil()) {
				totErrores++;
				if (expediente.isErrorentidad()) {
					errorexp++;
					erroresEntidad++;
				}
				if (expediente.isErrorplaca()) {
					errorexp++;
					erroresPlaca++;
				}
				if (expediente.isErrorperfil()) {
					errorexp++;
					erroresPerfil++;
				}
			}
			expediente.setTotalerrores(errorexp);

			listExpedientes.set(x, expediente);

		}

		float porcerror = (float) totErrores * 100 / (float) listExpedientes.size();

		System.out.println("Errores Perfil: " + erroresPerfil);
		System.out.println("Errores Placa: " + erroresPlaca);
		System.out.println("Errores Entidad: " + erroresEntidad);

		System.out.println("**Total de Errores: " + totErrores);

		System.out.println("**Procentaje Error: " + porcerror + "\n");

		System.out.println("********DETALLES DE ERRORES*********");
//		System.out.println("Total exp.: "+listExpedientes+" erroresPerfil: "+erroresPerfil+", erroresPlaca: "+erroresPlaca+", erroresEntidad: "+erroresEntidad);
		System.out.println(
				"getIdregistrocsv | getNu_expediente | getCdplacadel | getCdplacatras | getCdentidadel | getCdentidadtras"
						+ " | getCdperfiloriginal  | getCdperfil  | getIdperfil  | getIdmarca  | getIdsubmarca  | getTxperfil"
						+ "| isErrorplaca  | isErrorentidad  | isErrorperfil  | getProbacertacionperfil  | getTxperfilcorrecto  | getIdperfilcorrecto"
						+ "| getIdmarcacorrecto  | getIdsubmarcacorrecto  | getTotalerrores");
		printErrores(listExpedientes);
		printListExpedientes(listExpedientes);

	}

	public void printErrores(List<ExpedienteVO> listExpedientes) {
		for (int x = 0; x < listExpedientes.size(); x++) {
			if (listExpedientes.get(x).getTotalerrores() >= 1) {
				printExpediente(listExpedientes.get(x));
			}
		}
	}

	public void printListExpedientes(List<ExpedienteVO> listExpedientes) {
		for (int x = 0; x < listExpedientes.size(); x++) {
			if (listExpedientes.get(x).getTotalerrores() >= 1) {
				System.out.println(listExpedientes.get(x).getIdregistrocsv() + ", '"
						+ listExpedientes.get(x).getNu_expediente() + "',");
			}
		}
	}
	
	public void printListExpedientes2(List<ExpedienteVO> listExpedientes) {
		for (int x = 0; x < listExpedientes.size(); x++) {
			if (listExpedientes.get(x).getTotalerrores() >= 1) {
				System.out.println("'"+listExpedientes.get(x).getNu_expediente() + "',");
			}
		}
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
	
	public void printListExpedientesAll(List<ExpedienteVO> listExpedientes){
		for (int x = 0; x < listExpedientes.size(); x++) {
			System.out.println(listExpedientes.get(x).getIdregistrocsv() + ", '"
					+ listExpedientes.get(x).getNu_expediente() + "',");
		}
	}

}
