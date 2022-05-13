package instance;

public class GenDate {
	private int gen;
	private double eval;
	private double dist;
	private String genom;

	public GenDate (int gen, double eval, double dist, String genom) {
		this.gen = gen;
		this.eval = eval;
		this.dist = dist;
		this.genom = genom;
	}
	public int getGen() { return gen;};
	public double getEval() { return eval;};
	public double getDist() {return dist;};
	public String getGenom() { return genom;};
	public void setGen(int gen) { this.gen = gen;};
	public void setEval(double eval) {this.eval = eval;};
	public void setDist(double dist) {this.dist = dist;};
	public void setGenom(String genom) {this.genom = genom;};

}
