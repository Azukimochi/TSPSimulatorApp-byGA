package instance;

public class GenDate {
	private String gen;
	private String eval;
	private String dist;
	private String genom;

	public GenDate(int gen, double eval, double dist, String genom) {
		this.gen = String.valueOf(gen);
		this.eval = String.valueOf(eval);
		this.dist = String.valueOf(dist);
		this.genom = genom;
	}

	public String getGen() {
		return gen;
	};

	public String getEval() {
		return eval;
	};

	public String getDist() {
		return dist;
	};

	public String getGenom() {
		return genom;
	};

	public void setGen(String gen) {
		this.gen = gen;
	};

	public void setEval(String eval) {
		this.eval = eval;
	};

	public void setDist(String dist) {
		this.dist = dist;
	};

	public void setGenom(String genom) {
		this.genom = genom;
	};

}
