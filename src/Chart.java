
public abstract class Chart {

	protected StringBuffer buffer;
	protected Simulation sim;
	
	protected Chart(Simulation s){
		this.buffer = new StringBuffer();
		this.sim = s;
	}
	
	public abstract void printHead();
	
	public abstract void printLine();
	
	public String toString(){
		return this.buffer.toString();
	}
}
