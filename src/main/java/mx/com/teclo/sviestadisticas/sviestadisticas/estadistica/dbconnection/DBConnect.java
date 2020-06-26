package mx.com.teclo.sviestadisticas.sviestadisticas.estadistica.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mx.com.teclo.sviestadisticas.sviestadisticas.estadistica.vo.ExpedienteVO;


public class DBConnect {

//	String server ="jdbc:oracle:thin:@172.25.25.13:1521:tecloqa";
	String server = "jdbc:oracle:thin:@172.25.25.87:1521:teclo";
	String user = "teclo";
	String password = "T3c10inf0*usr";
	public List<ExpedienteVO> getAllRegistros(Integer condicionparo) {
		
		List<ExpedienteVO> listExpedientes = new ArrayList<ExpedienteVO>();
		
		
		String GETDETEVABYFOLIO="SELECT   \r\n" + 
				"	DET.ID_REGISTRO_CSV, DET.NU_EXPEDIENTE, ORIG.CD_PLACA_DELANTERA AS ORG_PLACDEL, DET.CD_PLACA_DELANTERA, DET.CD_PLACA_TRASERA, DET.CD_ENTIDAD_DELANTERA, DET.CD_ENTIDAD_TRASERA, \r\n" + 
				"	ORIG.CD_PERFIL AS PERF_ORIG, DET.CD_PERFIL, DET.ID_PERFIL, DET.ID_MARCA, DET.ID_SUBMARCA, MARC.TXT_MARCA || ' - ' || SUBMARC.TXT_MARCA || ' - ' || DET.CD_PERFIL AS TXPERFIL,\r\n" + 
				"	DET.ID_USR_MODIFICA, ORIG.CD_PLACA_TRASERA, ORIG.CD_ENTIDAD_DELANTERA, ORIG.CD_ENTIDAD_TRASERA  \r\n" +
				"FROM TCI005D_PT_ARCHIVO_DETALLE_EVA DET\r\n" + 
				"	JOIN TCI004D_PT_ARCHIVO_DETALLE ORIG ON ORIG.ID_REGISTRO_CSV = DET.ID_REGISTRO_CSV\r\n" + 
				"	JOIN TCI003D_PT_ARCHIVO_CSV CSV ON CSV.ID_ARCHIVO_CSV = DET.ID_ARCHIVO_CSV\r\n" + 
				"	JOIN TCI002D_PT_LOTE LOTE ON LOTE.ID_PT_LOTE = CSV.ID_PT_LOTE\r\n" + 
				"	JOIN TCI001D_PT_ENTREGA ENT ON ENT.ID_ENTREGA = LOTE.ID_ENTREGA\r\n" + 
				"	JOIN TCI017C_PT_MARCAS MARC ON MARC.ID_PT_MARCA = DET.ID_MARCA\r\n" + 
				"	JOIN TCI018C_PT_SUBMARCA SUBMARC ON SUBMARC.ID_PT_SUBMARCA = DET.ID_SUBMARCA \r\n" +
//				"	JOIN TCI026D_PT_DETALLE_REVALIDA DETREV ON DETREV.ID_REGISTRO_CSV = DET.ID_REGISTRO_CSV " +
//				"WHERE ENT.ID_ENTREGA=3 "+
//				"AND DETREV.ID_CICLO_VALIDACION=2" + 
//				"ORDER BY ID_REGISTRO_CSV ASC";
				"ORDER BY dbms_random.value";
		
		 Connection con = null;
	        Statement stmt = null;
	        ResultSet rs = null;
	        try {
	            Class.forName("oracle.jdbc.OracleDriver");
	            con = DriverManager.getConnection(server,user,password);
	            stmt = con.createStatement();
	            rs = stmt.executeQuery(GETDETEVABYFOLIO);
	            
	            Integer iteraciones=0;
	            ExpedienteVO expediente = null;
	            System.out.println("Se establece conexión");
	            while(rs.next()) {
	            	if(iteraciones.equals(condicionparo)) {
	            		break;
	            	}
	            	expediente = new ExpedienteVO();
	            	expediente.setIdregistrocsv(rs.getInt(1));
	            	expediente.setNu_expediente(rs.getString(2));
	            	expediente.setCdplacadeloriginal(rs.getString(3));
	            	expediente.setCdplacadel(rs.getString(4));
	            	expediente.setCdplacatras(rs.getString(5));
	            	expediente.setCdentidadel(rs.getString(6));
	            	expediente.setCdentidadtras(rs.getString(7));
	            	expediente.setCdperfiloriginal(rs.getString(8));
	            	expediente.setCdperfil(rs.getString(9));
	            	expediente.setIdperfil(rs.getInt(10));
	            	expediente.setIdmarca(rs.getInt(11));
	            	expediente.setIdsubmarca(rs.getInt(12));
	            	expediente.setTxperfil(rs.getString(13));
	            	expediente.setIduser(rs.getInt(14));
	            	expediente.setCdplacatrasloriginal(rs.getString(15));
	            	expediente.setCdentidadeloriginal(rs.getString(16));
	            	expediente.setCdentidadtrasoriginal(rs.getString(17));
	            	
	            	listExpedientes.add(expediente);
	            	iteraciones++;
	            }
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                rs.close();
	                stmt.close();
	                con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        return listExpedientes;
		
	}
	
public List<ExpedienteVO> getAllRegistrosByListOfIdRegistros(String registros) {
		
		List<ExpedienteVO> listExpedientes = new ArrayList<ExpedienteVO>();
		
		
		String GETDETEVABYFOLIO="SELECT   \r\n" + 
				"	DET.ID_REGISTRO_CSV, DET.NU_EXPEDIENTE, ORIG.CD_PLACA_DELANTERA AS ORG_PLACDEL, DET.CD_PLACA_DELANTERA, DET.CD_PLACA_TRASERA, DET.CD_ENTIDAD_DELANTERA, DET.CD_ENTIDAD_TRASERA, \r\n" + 
				"	ORIG.CD_PERFIL AS PERF_ORIG, DET.CD_PERFIL, DET.ID_PERFIL, DET.ID_MARCA, DET.ID_SUBMARCA, MARC.TXT_MARCA || ' - ' || SUBMARC.TXT_MARCA || ' - ' || DET.CD_PERFIL AS TXPERFIL,\r\n" + 
				"	DET.ID_USR_MODIFICA, ORIG.CD_PLACA_TRASERA, ORIG.CD_ENTIDAD_DELANTERA, ORIG.CD_ENTIDAD_TRASERA  \r\n" + 
				"FROM TCI005D_PT_ARCHIVO_DETALLE_EVA DET\r\n" + 
				"	JOIN TCI004D_PT_ARCHIVO_DETALLE ORIG ON ORIG.ID_REGISTRO_CSV = DET.ID_REGISTRO_CSV\r\n" + 
				"	JOIN TCI017C_PT_MARCAS MARC ON MARC.ID_PT_MARCA = DET.ID_MARCA\r\n" + 
				"	JOIN TCI018C_PT_SUBMARCA SUBMARC ON SUBMARC.ID_PT_SUBMARCA = DET.ID_SUBMARCA \r\n"+
				"WHERE DET.ID_REGISTRO_CSV in (" + registros +" )"+
				"ORDER BY ID_REGISTRO_CSV ASC";
		
		Connection con = null;
	        Statement stmt = null;
	        ResultSet rs = null;
	        try {
	            Class.forName("oracle.jdbc.OracleDriver");
	            con = DriverManager.getConnection(server,user,password);
	            stmt = con.createStatement();
	            rs = stmt.executeQuery(GETDETEVABYFOLIO);
	            
	            int iteraciones=0;
	            ExpedienteVO expediente = null;
	            System.out.println("Se establece conexión");
	            while(rs.next()) {
	            	expediente = new ExpedienteVO();
	            	expediente.setIdregistrocsv(rs.getInt(1));
	            	expediente.setNu_expediente(rs.getString(2));
	            	expediente.setCdplacadeloriginal(rs.getString(3));
	            	expediente.setCdplacadel(rs.getString(4));
	            	expediente.setCdplacatras(rs.getString(5));
	            	expediente.setCdentidadel(rs.getString(6));
	            	expediente.setCdentidadtras(rs.getString(7));
	            	expediente.setCdperfiloriginal(rs.getString(8));
	            	expediente.setCdperfil(rs.getString(9));
	            	expediente.setIdperfil(rs.getInt(10));
	            	expediente.setIdmarca(rs.getInt(11));
	            	expediente.setIdsubmarca(rs.getInt(12));
	            	expediente.setTxperfil(rs.getString(13));
	            	expediente.setIduser(rs.getInt(14));
	            	expediente.setCdplacatrasloriginal(rs.getString(15));
	            	expediente.setCdentidadeloriginal(rs.getString(16));
	            	expediente.setCdentidadtrasoriginal(rs.getString(17));
	            	
	            	listExpedientes.add(expediente);
	            	iteraciones++;
	            }
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                rs.close();
	                stmt.close();
	                con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        return listExpedientes;
		
	}
	
public List<ExpedienteVO> getBitValidacionByFolio(String nu_folio) {
		
		List<ExpedienteVO> listExpedientes = new ArrayList<ExpedienteVO>();
		
		
		String GETDETEVABYFOLIO="SELECT \r\n" + 
				"	DET.ID_REGISTRO_CSV, DET.NU_EXPEDIENTE, ORIG.CD_PLACA_DELANTERA AS ORG_PLACDEL, DET.CD_PLACA_DELANTERA, DET.CD_PLACA_TRASERA, DET.CD_ENTIDAD_DELANTERA, DET.CD_ENTIDAD_TRASERA, \r\n" + 
				"	ORIG.CD_PERFIL AS PERF_ORIG, DET.CD_PERFIL, DET.ID_PERFIL, DET.ID_MARCA, DET.ID_SUBMARCA, MARC.TXT_MARCA || ' - ' || SUBMARC.TXT_MARCA || ' - ' || DET.CD_PERFIL AS TXPERFIL,\r\n" + 
				"	DET.ID_USR_MODIFICA, ORIG.CD_PLACA_TRASERA, ORIG.CD_ENTIDAD_DELANTERA, ORIG.CD_ENTIDAD_TRASERA   \r\n" + 
				"FROM TCI006D_PT_ARCHIVO_DET_BIT_EVA DET   \r\n" + 
				"	JOIN TCI004D_PT_ARCHIVO_DETALLE ORIG ON ORIG.ID_REGISTRO_CSV = DET.ID_REGISTRO_CSV     \r\n" + 
				"	JOIN TCI017C_PT_MARCAS MARC ON MARC.ID_PT_MARCA = DET.ID_MARCA\r\n" + 
				"	JOIN TCI018C_PT_SUBMARCA SUBMARC ON SUBMARC.ID_PT_SUBMARCA = DET.ID_SUBMARCA\r\n" + 
				"WHERE DET.NU_EXPEDIENTE= '"+nu_folio+"' ORDER BY DET.FH_MODIFICACION DESC";
		
		 Connection con = null;
	        Statement stmt = null;
	        ResultSet rs = null;
	        try {
	            Class.forName("oracle.jdbc.OracleDriver");
	            con = DriverManager.getConnection(server,user,password);
	            stmt = con.createStatement();
	            rs = stmt.executeQuery(GETDETEVABYFOLIO);
	            
	            
	            ExpedienteVO expediente = null;
	            while(rs.next()) {
	            	expediente = new ExpedienteVO();
	            	expediente.setIdregistrocsv(rs.getInt(1));
	            	expediente.setNu_expediente(rs.getString(2));
	            	expediente.setCdplacadeloriginal(rs.getString(3));
	            	expediente.setCdplacadel(rs.getString(4));
	            	expediente.setCdplacatras(rs.getString(5));
	            	expediente.setCdentidadel(rs.getString(6));
	            	expediente.setCdentidadtras(rs.getString(7));
	            	expediente.setCdperfiloriginal(rs.getString(8));
	            	expediente.setCdperfil(rs.getString(9));
	            	expediente.setIdperfil(rs.getInt(10));
	            	expediente.setIdmarca(rs.getInt(11));
	            	expediente.setIdsubmarca(rs.getInt(12));
	            	expediente.setTxperfil(rs.getString(13));
	            	expediente.setIduser(rs.getInt(14));
	            	expediente.setCdplacatrasloriginal(rs.getString(15));
	            	expediente.setCdentidadeloriginal(rs.getString(16));
	            	expediente.setCdentidadtrasoriginal(rs.getString(17));
	            	
	            	listExpedientes.add(expediente);
	            }
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                rs.close();
	                stmt.close();
	                con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        return listExpedientes;
		
	}
}
