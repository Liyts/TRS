import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

//Класс
public class Main {
	private String expansionFile;
	private int width, height;

	public void myFilter(BufferedImage img) {
		BufferedImage outputImg = img;
		int X, Y;
		int[] masBig = new int[25];
		masBig[0] = 0;
		masBig[1] = -1;
		masBig[2] = -2;
		masBig[3] = -1;
		masBig[4] = 0;
		masBig[5] = -1;
		masBig[6] = -2;
		masBig[7] = -3;
		masBig[8] = -2;
		masBig[9] = -1;
		masBig[10] = -2;
		masBig[11] = -3;
		masBig[12] = 37;
		masBig[13] = -3;
		masBig[14] = -2;
		masBig[15] = -1;
		masBig[16] = -2;
		masBig[17] = -3;
		masBig[18] = -2;
		masBig[19] = -1;
		masBig[20] = 0;
		masBig[21] = -1;
		masBig[22] = -2;
		masBig[23] = -1;
		masBig[24] = 0;

		int count;
		for (Y = 2; Y < height - 2; Y++) {
			for (X = 2; X < width - 2; X++) {
				int bx = 0, ax = 0;
				count = 0;
				int resault_R = 0, resault_G = 0, resault_B = 0;
				int alpha = 255;
				for (bx = -2; bx < 3; bx++) {
					for (ax = -2; ax < 3; ax++) {
						int inputColor = img.getRGB(X + bx, Y + ax);
						Color color = new Color(inputColor, true);
						alpha = color.getAlpha();
						int red = color.getRed();
						int green = color.getGreen();
						int blue = color.getBlue();
						resault_R += red * masBig[count];
						resault_G += green * masBig[count];
						resault_B += blue * masBig[count];
						count++;
					}
				}
				if (resault_R < 0)
					resault_R = 0;
				if (resault_R > 255)
					resault_R = 255;
				if (resault_G < 0)
					resault_G = 0;
				if (resault_G > 255)
					resault_G = 255;
				if (resault_B < 0)
					resault_B = 0;
				if (resault_B > 255)
					resault_B = 255;
				Color newColor = new Color(resault_R, resault_G, resault_B,
						alpha);
				int outputColorInt = newColor.getRGB();
				outputImg.setRGB(X, Y, outputColorInt);
			}
		}
		try {
			String fileName = "newFile" + expansionFile;
			ImageIO.write(outputImg, expansionFile, new File("nibody+20."
					+ expansionFile.toLowerCase()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Filter(BufferedImage img) {// метод модификации, который
											// реализовывает правильный
											// алгоритм, остальное дерьмо
											// полнейшее
		BufferedImage outputImg = img;
		int X, Y;
		int[] mat = new int[9];

		mat[0] = -0;
		mat[1] = -1;
		mat[2] = -0;
		mat[3] = -1;
		mat[4] = 5;
		mat[5] = -1;
		mat[6] = 0;
		mat[7] = -1;
		mat[8] = 0;

		int count;
		for (Y = 1; Y < height - 1; Y++) {
			for (X = 1; X < width - 1; X++) {
				int bx = 0, ax = 0;
				count = 0;
				int resault_R = 0, resault_G = 0, resault_B = 0, alpha = 255;
				for (bx = -1; bx < 2; bx++) {
					for (ax = -1; ax < 2; ax++) {
						int inputColor = img.getRGB(X + bx, Y + ax);
						Color color = new Color(inputColor, true);
						alpha = color.getAlpha();
						int red = color.getRed();
						int green = color.getGreen();
						int blue = color.getBlue();
						resault_R += red * mat[count];
						resault_G += green * mat[count];
						resault_B += blue * mat[count];
						count++;
					}
				}
				if (resault_R < 0)
					resault_R = 0;
				if (resault_R > 255)
					resault_R = 255;
				if (resault_G < 0)
					resault_G = 0;
				if (resault_G > 255)
					resault_G = 255;
				if (resault_B < 0)
					resault_B = 0;
				if (resault_B > 255)
					resault_B = 255;
				Color newColor = new Color(resault_R, resault_G, resault_B,
						alpha);
				int outputColorInt = newColor.getRGB();
				outputImg.setRGB(X, Y, outputColorInt);
			}
		}
		try {
			String fileName = "newFile." + expansionFile.toLowerCase();
			ImageIO.write(outputImg, expansionFile, new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readToArray(String path) { // метод обработки изображения и
											// вызывающий метод модификации
											// изображения
		BufferedImage bufImg;
		File file = new File(path);
		try {
			bufImg = ImageIO.read(file);
			width = bufImg.getWidth();
			height = bufImg.getHeight();
			expansionFile = path.substring(
					path.indexOf('.', path.lastIndexOf('/')) + 1).toUpperCase();
			// realization(bufImg);
			myFilter(bufImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void realization(BufferedImage inputImage) { // метод увеличения
														// значения каждого
														// пикселя на 5 едениц
		BufferedImage outputImage = inputImage;
		byte[][] mas = new byte[height][width];
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				int color = inputImage.getRGB(x, y);
				mas[x][y] = (byte) color;
				outputImage.setRGB(x, y, chekColor(color));
			}
		}
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				System.out.print(mas[y][x]);
			}
			System.out.println();
		}
		/*
		 * try { String fileName = "newFile"+expansionFile;
		 * ImageIO.write(outputImage, expansionFile, new
		 * File("nibody+20."+expansionFile.toLowerCase())); } catch (IOException
		 * e) { e.printStackTrace(); }
		 */

	}

	public int chekColor(int inputColor) {// непосредственное увеличение каждого
											// пикселя на 5 едениц
		Color color = new Color(inputColor, true);
		int count = 5;
		int alpha = color.getAlpha();
		int red = (color.getRed()) - count;
		if (red > 255)
			red -= count;
		if (red < 0)
			red += count;
		int green = (color.getGreen()) - count;
		if (green > 255)
			green -= count;
		if (green < 0)
			green += count;
		int blue = (color.getBlue()) - count;
		if (blue > 255)
			blue -= count;
		if (blue < 0)
			blue += count;
		Color newColor = new Color(red, green, blue, alpha);
		int outputColor = newColor.getRGB();
		return outputColor;
	}

	public static void main(String[] args) {
		Main m = new Main();
		m.readToArray("Penguins.jpg");
	}

}

class MyThread extends Thread {
	BufferedImage img = null;
	BufferedImage outputImg = null;
	int X = 0, Y = 0;
	int count = 0;
	int[] mat = new int[9];
	{
		mat[0] = 0;
		mat[1] = -1;
		mat[2] = 0;
		mat[3] = -1;
		mat[4] = 5;
		mat[5] = -1;
		mat[6] = 0;
		mat[7] = -1;
		mat[8] = 0;
	}

	public MyThread(BufferedImage img, int X, int Y) {
		this.img = img;
		outputImg = img;
		this.X = X;
		this.Y = Y;
	}

	@Override
	public void run() {
		int bx = 0, ax = 0;
		count = 0;
		int resault_R = 0, resault_G = 0, resault_B = 0, alpha = 255;
		for (bx = -1; bx < 2; bx++) {
			for (ax = -1; ax < 2; ax++) {
				int inputColor = img.getRGB(X + bx, Y + ax);
				Color color = new Color(inputColor, true);
				alpha = color.getAlpha();
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();
				resault_R += red * mat[count];
				resault_G += green * mat[count];
				resault_B += blue * mat[count];
				count++;
			}
		}
		if (resault_R < 0)
			resault_R = 0;
		if (resault_R > 255)
			resault_R = 255;
		if (resault_G < 0)
			resault_G = 0;
		if (resault_G > 255)
			resault_G = 255;
		if (resault_B < 0)
			resault_B = 0;
		if (resault_B > 255)
			resault_B = 255;
		Color newColor = new Color(resault_R, resault_G, resault_B, alpha);
		int outputColorInt = newColor.getRGB();
		outputImg.setRGB(X, Y, outputColorInt);
	}
}
