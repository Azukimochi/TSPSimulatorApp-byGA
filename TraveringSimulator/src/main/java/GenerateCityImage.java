


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import GenomAlgo.GA;
import instance.City;
import instance.Genom;

public class GenerateCityImage {

	static InputStream SaveIMG;

	/**
	 * GAクラスへ街の生成委譲
	 * @param cityNum
	 */
	public GenerateCityImage(int cityNum) {
		GA.GenerateCity(cityNum);
	}
	public GenerateCityImage() {
	}
	/**
	 * 街の画像生成
	 * @param genom 遺伝子情報
	 * @param drawLine 街の接続線の描画をするか
	 * @return 画像
	 */
	public InputStream Generate(Genom genom, boolean drawLine) {

		BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
		City[] Cities = GA.getCity();

		Graphics2D off = image.createGraphics();
		off.setColor(Color.lightGray);
		off.fillRect(0, 0, 400, 400);
		off.setColor(Color.red);
		for(City g: Cities)
			off.fillOval(g.getPosX()+50, g.getPosY()+50, 8, 8);
		off.setColor(Color.blue);
		if(drawLine && genom != null) {
			for(int i = 1; i < Cities.length; i++) {
				off.drawLine(
						(int)Cities[genom.getParam(i-1)].getPosX()+54,
						(int)Cities[genom.getParam(i-1)].getPosY()+54,
						(int)Cities[genom.getParam(i)].getPosX()+54,
						(int)Cities[genom.getParam(i)].getPosY()+54
						);
			}
			off.drawLine(
					(int)Cities[genom.getParam(0)].getPosX()+54,
					(int)Cities[genom.getParam(0)].getPosY()+54,
					(int)Cities[genom.getParam(Cities.length-1)].getPosX()+54,
					(int)Cities[genom.getParam(Cities.length-1)].getPosY()+54
					);
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] imageInByte = null;

		try {
			ImageIO.write( image, "png", baos );
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert imageInByte != null : "image can't convert Error";

		// byte配列をInputStreamに変換
		GenerateCityImage.SaveIMG = new ByteArrayInputStream(imageInByte);
		return new ByteArrayInputStream(imageInByte);
	}
	/**
	 * 初期化用画像生成
	 * @return 画像
	 */
	public static InputStream clearCityImage() {

		BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
		Graphics2D off = image.createGraphics();
		off.setColor(Color.lightGray);
		off.fillRect(0, 0, 400, 400);

		byte[] imageInByte = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write( image, "png", baos );
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(imageInByte);
	}
	/**
	 * 保存用画像
	 * @return 都市画像変換後BufferedImage
	 */
	public BufferedImage getCityBufferedImage() {

		System.out.println("出力");
		BufferedImage img = null;
		try {
			img =  ImageIO.read(GenerateCityImage.SaveIMG);

			if(GA.getBestGenom().size() != 0) {
				Genom best = GA.getBestGenom().get(GA.getBestGenom().size()-1);
				Graphics2D off = img.createGraphics();
				off.setColor(Color.BLACK);
				off.drawString("評価：" + best.getEval(), 5, 370);
				off.drawString("距離：" + best.getDistance(), 5, 385);
			}

			//ImageIO.write(img, "png", new File("F:\\1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}
