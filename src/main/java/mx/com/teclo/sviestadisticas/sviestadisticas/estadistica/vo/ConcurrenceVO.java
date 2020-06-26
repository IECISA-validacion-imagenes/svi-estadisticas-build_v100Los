package mx.com.teclo.sviestadisticas.sviestadisticas.estadistica.vo;

public class ConcurrenceVO {
	
	private int number;
    private int times;
    private double percentagepresence;
    
    
    
	public ConcurrenceVO(int number, int times) {
		super();
		this.number = number;
		this.times = times;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public double getPercentagepresence() {
		return percentagepresence;
	}
	public void setPercentagepresence(double percentagepresence) {
		this.percentagepresence = percentagepresence;
	}
    
    

}
