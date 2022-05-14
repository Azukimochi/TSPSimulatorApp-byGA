package instance;

import java.util.Random;

public class City{
	private int posX;
	private int posY;

	public City() {
		Random rnd = new Random();
		this.posX = rnd.nextInt(300);
		this.posY = rnd.nextInt(300);
	}
	public City(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
	@Override
	public String toString() {
		return "City [posX=" + posX + ", posY=" + posY + "]";
	}
}
