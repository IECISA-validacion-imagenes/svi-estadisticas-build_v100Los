package mx.com.teclo.sviestadisticas.sviestadisticas.estadistica.vo;

public class DataMuestreoVO {

	private int tamLote;
    private int tamMuestra;
    private int cantAceptacion;
    private int cantRechazo;
    private String letra;
	public int getTamLote() {
		return tamLote;
	}
	public void setTamLote(int tamLote) {
		this.tamLote = tamLote;
	}
	public int getTamMuestra() {
		return tamMuestra;
	}
	public void setTamMuestra(int tamMuestra) {
		this.tamMuestra = tamMuestra;
	}
	public int getCantAceptacion() {
		return cantAceptacion;
	}
	public void setCantAceptacion(int cantAceptacion) {
		this.cantAceptacion = cantAceptacion;
	}
	public int getCantRechazo() {
		return cantRechazo;
	}
	public void setCantRechazo(int cantRechazo) {
		this.cantRechazo = cantRechazo;
	}
	public String getLetra() {
		return letra;
	}
	public void setLetra(String letra) {
		this.letra = letra;
	}
    
    
}
