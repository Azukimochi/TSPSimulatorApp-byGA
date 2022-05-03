package instance;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import GenomAlgo.GA;

public class Genom implements Comparable<Genom>{

	private List<Integer> genom;
	private double distance = 0;
	double eval;

	/**
	 * 引数による遺伝子長の配列を新しく生成します
	 * @param length 遺伝子長
	 */
	public Genom(int length) {
		genom = IntStream.range(0, length).boxed().collect(Collectors.toList());
		Collections.shuffle(genom);
		initialize();
	}
	/**
	 * 引数の配列の内容で遺伝子を生成します
	 * @param genom 遺伝子配列
	 */
	public Genom(List<Integer> genom) {
		this.genom = genom;
		initialize();
	}
	private void initialize() {
		City[] cities = GA.getCity();
		for(int i = 0; i < genom.size(); i++) {
			if(i != 0) {
				distance  +=  (int)Math.sqrt(
						Math.pow(cities[genom.get(i)].getPosX() - cities[genom.get(i-1)].getPosX(), 2.0) +
						Math.pow(cities[genom.get(i)].getPosY() - cities[genom.get(i-1)].getPosY(), 2.0));
			}
			else {
				distance += (int)Math.sqrt(
						Math.pow(cities[genom.get(0)].getPosX() - cities[genom.get(genom.size() -1)].getPosX(), 2.0) +
						Math.pow(cities[genom.get(0)].getPosY() - cities[genom.get(genom.size() -1)].getPosY(), 2.0));
			}
		}
		eval = (double)(1.0d / distance);
	}
	@Override
	public String toString() {
		return "Genom [genom=" + genom + ", distance=" + distance + ", eval=" + eval + "]";
	}
	public List<Integer> getList() {
		return this.genom;
	}
	public int getParam(int i) {
		return this.genom.get(i);
	}
	public double getEval() {
		return this.eval;
	}
	public double getDistance() {
		return this.distance;
	}
	@Override
	public int compareTo(Genom o) {
		return (int) (this.getDistance() - o.getDistance());
	}
}
