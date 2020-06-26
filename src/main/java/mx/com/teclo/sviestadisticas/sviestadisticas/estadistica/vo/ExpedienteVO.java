package mx.com.teclo.sviestadisticas.sviestadisticas.estadistica.vo;

public class ExpedienteVO {
	
	private int idregistrocsv;
	private String nu_expediente;
	private String cdplacadel;
	private String cdplacatras;
	private String cdentidadel;
	private String cdentidadtras;
	private String cdperfiloriginal;
	private String cdplacadeloriginal;
	private String cdplacatrasloriginal;
	private String cdentidadeloriginal;
	private String cdentidadtrasoriginal;
	private String cdperfil;
	private int idperfil;
    private int idmarca;
    private int idsubmarca;
    private String txperfil;
    private int iduser;
    
    private boolean errorplaca=false;
	private boolean errorentidad=false;
	private boolean errorperfil=false;
	private double probacertacionperfil=0;
    private String txperfilcorrecto;
    private int idperfilcorrecto;
    private int idmarcacorrecto;
    private int idsubmarcacorrecto;
	private int totalerrores;
	
	private boolean isFiable;

	public int getIdregistrocsv() {
		return idregistrocsv;
	}

	public void setIdregistrocsv(int idregistrocsv) {
		this.idregistrocsv = idregistrocsv;
	}

	public String getNu_expediente() {
		return nu_expediente;
	}

	public void setNu_expediente(String nu_expediente) {
		this.nu_expediente = nu_expediente;
	}

	public String getCdplacadel() {
		return cdplacadel;
	}

	public void setCdplacadel(String cdplacadel) {
		this.cdplacadel = cdplacadel;
	}

	public String getCdplacatras() {
		return cdplacatras;
	}

	public void setCdplacatras(String cdplacatras) {
		this.cdplacatras = cdplacatras;
	}

	public String getCdentidadel() {
		return cdentidadel;
	}

	public void setCdentidadel(String cdentidadel) {
		this.cdentidadel = cdentidadel;
	}

	public String getCdentidadtras() {
		return cdentidadtras;
	}

	public void setCdentidadtras(String cdentidadtras) {
		this.cdentidadtras = cdentidadtras;
	}

	public String getCdperfiloriginal() {
		return cdperfiloriginal;
	}

	public void setCdperfiloriginal(String cdperfiloriginal) {
		this.cdperfiloriginal = cdperfiloriginal;
	}

	public String getCdplacadeloriginal() {
		return cdplacadeloriginal;
	}

	public void setCdplacadeloriginal(String cdplacadeloriginal) {
		this.cdplacadeloriginal = cdplacadeloriginal;
	}

	public String getCdplacatrasloriginal() {
		return cdplacatrasloriginal;
	}

	public void setCdplacatrasloriginal(String cdplacatrasloriginal) {
		this.cdplacatrasloriginal = cdplacatrasloriginal;
	}

	public String getCdentidadeloriginal() {
		return cdentidadeloriginal;
	}

	public void setCdentidadeloriginal(String cdentidadeloriginal) {
		this.cdentidadeloriginal = cdentidadeloriginal;
	}

	public String getCdentidadtrasoriginal() {
		return cdentidadtrasoriginal;
	}

	public void setCdentidadtrasoriginal(String cdentidadtrasoriginal) {
		this.cdentidadtrasoriginal = cdentidadtrasoriginal;
	}

	public String getCdperfil() {
		return cdperfil;
	}

	public void setCdperfil(String cdperfil) {
		this.cdperfil = cdperfil;
	}

	public int getIdperfil() {
		return idperfil;
	}

	public void setIdperfil(int idperfil) {
		this.idperfil = idperfil;
	}

	public int getIdmarca() {
		return idmarca;
	}

	public void setIdmarca(int idmarca) {
		this.idmarca = idmarca;
	}

	public int getIdsubmarca() {
		return idsubmarca;
	}

	public void setIdsubmarca(int idsubmarca) {
		this.idsubmarca = idsubmarca;
	}

	public String getTxperfil() {
		return txperfil;
	}

	public void setTxperfil(String txperfil) {
		this.txperfil = txperfil;
	}

	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public boolean isErrorplaca() {
		return errorplaca;
	}

	public void setErrorplaca(boolean errorplaca) {
		this.errorplaca = errorplaca;
	}

	public boolean isErrorentidad() {
		return errorentidad;
	}

	public void setErrorentidad(boolean errorentidad) {
		this.errorentidad = errorentidad;
	}

	public boolean isErrorperfil() {
		return errorperfil;
	}

	public void setErrorperfil(boolean errorperfil) {
		this.errorperfil = errorperfil;
	}

	public double getProbacertacionperfil() {
		return probacertacionperfil;
	}

	public void setProbacertacionperfil(double probacertacionperfil) {
		this.probacertacionperfil = probacertacionperfil;
	}

	public String getTxperfilcorrecto() {
		return txperfilcorrecto;
	}

	public void setTxperfilcorrecto(String txperfilcorrecto) {
		this.txperfilcorrecto = txperfilcorrecto;
	}

	public int getIdperfilcorrecto() {
		return idperfilcorrecto;
	}

	public void setIdperfilcorrecto(int idperfilcorrecto) {
		this.idperfilcorrecto = idperfilcorrecto;
	}

	public int getIdmarcacorrecto() {
		return idmarcacorrecto;
	}

	public void setIdmarcacorrecto(int idmarcacorrecto) {
		this.idmarcacorrecto = idmarcacorrecto;
	}

	public int getIdsubmarcacorrecto() {
		return idsubmarcacorrecto;
	}

	public void setIdsubmarcacorrecto(int idsubmarcacorrecto) {
		this.idsubmarcacorrecto = idsubmarcacorrecto;
	}

	public int getTotalerrores() {
		return totalerrores;
	}

	public void setTotalerrores(int totalerrores) {
		this.totalerrores = totalerrores;
	}

	public boolean isFiable() {
		return isFiable;
	}

	public void setFiable(boolean isFiable) {
		this.isFiable = isFiable;
	}
	
	
	
    
	
    
	
    
	
}
