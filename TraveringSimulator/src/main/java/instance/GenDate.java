package instance;

public class GenDate {
	private int gen;
	private double eval;
	private String genom;

	public GenDate (int gen, double eval, String genom) {
		this.gen = gen;
		this.eval = eval;
		this.genom = genom;
	}
	public int getGen() { return gen;};
	public double getEval() { return eval;};
	public String getGenom() { return genom;};
	public void setGen(int gen) { this.gen = gen;};
	public void setEval(double eval) {this.eval = eval;};
	public void setGenom(String genom) {this.genom = genom;};

}
