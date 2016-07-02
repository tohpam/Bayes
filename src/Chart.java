
public abstract class Chart {

	protected StringBuffer buffer;
	protected Simulation sim;
	protected int line;
	protected String name;
	
	protected Chart(Simulation s, String name){
		this.buffer = new StringBuffer();
		this.sim = s;
		this.line = 0;
		this.name = name;
		
		this.buffer.append("\n>>> "+name+":\n\n");
		
		this.printHead();
	}
	
	public abstract void printHead();
	
	public abstract void printLine();
	
	public String toString(){
		return this.buffer.toString();
	}
}
