/*
Quoc Lien, 816097211
CS559 - Computer Vision
SobelEdgeDetection.java
*/

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.lang.Math;

import javax.imageio.ImageIO;
import java.awt.Color;

public class SobelEdgeDetection {
	private BufferedImage img, gray;
	private BufferedImage edgeX, edgeY;
	private int[] kernelX = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
	private int[] kernelY = { -1, -2, -1, 0, 0, 0, 1, 2, 1};

	public SobelEdgeDetection(String path) {
		try {
		    img = ImageIO.read(new File(path));
		    gray = convert2gray(img);
		    edgeX = edgeX(gray);
		    edgeY = edgeY(gray);
		    ImageIO.write(edgeY, "jpg", new File("Sobel_Y_" + path));
		    ImageIO.write(edgeX, "jpg", new File("Sobel_X_" + path));
		    ImageIO.write(edgeMagnitude(gray), "jpg", new File("Sobel_Magnitude_" + path));
		    ImageIO.write(phase(gray), "jpg", new File("Phase_" + path));
		} catch (IOException e) {
			e.printStackTrace();
	    	System.exit(1);	
		}
	}

	public BufferedImage edgeY(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = newImage.getRaster();
		WritableRaster tmp = image.getRaster();
		int yValue;
		for(int y = 1; y < img.getHeight() - 2; y++) {
			yValue = 0;
			for(int x = 1; x < img.getWidth() - 2; x++) {
              	yValue = (kernelY[0] * tmp.getSample(x-1,y-1, 0)) + 
              			 (kernelY[1] * tmp.getSample(x,y-1, 0)) + 
              			 (kernelY[2] * tmp.getSample(x+1,y-1, 0)) +
              			 (kernelY[3] * tmp.getSample(x-1,y, 0)) + 
              			 (kernelY[4] * tmp.getSample(x,y, 0)) + 
              			 (kernelY[5] * tmp.getSample(x+1,y, 0)) +
              			 (kernelY[6] * tmp.getSample(x-1,y+1, 0)) + 
              			 (kernelY[7] * tmp.getSample(x,y+1, 0)) + 
              			 (kernelY[8] * tmp.getSample(x+1,y+1, 0));

              	if(yValue < 30) {
              		yValue = 0;
              	}

              	raster.setSample(x, y, 0, yValue);
			}
		}

		return newImage;
	}

	public BufferedImage edgeX(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = newImage.getRaster();
		WritableRaster tmp = image.getRaster();
		int xValue;
		for(int y = 1; y < img.getHeight() - 2; y++) {
			xValue = 0;
			for(int x = 1; x < img.getWidth() - 2; x++) {
              	xValue = (kernelX[0] * tmp.getSample(x-1,y-1, 0)) + 
              			 (kernelX[1] * tmp.getSample(x,y-1, 0)) + 
              			 (kernelX[2] * tmp.getSample(x+1,y-1, 0)) +
              			 (kernelX[3] * tmp.getSample(x-1,y, 0)) + 
              			 (kernelX[4] * tmp.getSample(x,y, 0)) + 
              			 (kernelX[5] * tmp.getSample(x+1,y, 0)) +
              			 (kernelX[6] * tmp.getSample(x-1,y+1, 0)) + 
              			 (kernelX[7] * tmp.getSample(x,y+1, 0)) + 
              			 (kernelX[8] * tmp.getSample(x+1,y+1, 0));
              	if(xValue < 30) {
              		xValue = 0;
              	}
              	raster.setSample(x, y, 0, xValue);

			}
		}

		return newImage;
	}

	public BufferedImage edgeMagnitude(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = newImage.getRaster();
		WritableRaster tmp = image.getRaster();

		int magnitude, xValue, yValue;
		for(int y = 1; y < img.getHeight() - 2; y++) {
			for(int x = 1; x < img.getWidth() - 2; x++) {
				xValue = (kernelX[0] * tmp.getSample(x-1,y-1, 0)) + (kernelX[1] * tmp.getSample(x,y-1, 0)) + (kernelX[2] * tmp.getSample(x+1,y-1, 0)) +
              				 (kernelX[3] * tmp.getSample(x-1,y, 0))   + (kernelX[4] * tmp.getSample(x,y, 0))   + (kernelX[5] * tmp.getSample(x+1,y, 0)) +
              				 (kernelX[6] * tmp.getSample(x-1,y+1, 0)) + (kernelX[7] * tmp.getSample(x,y+1, 0)) + (kernelX[8] * tmp.getSample(x+1,y+1, 0));
              	yValue = (kernelY[0] * tmp.getSample(x-1,y-1, 0)) + (kernelY[1] * tmp.getSample(x,y-1, 0)) + (kernelY[2] * tmp.getSample(x+1,y-1, 0)) +
              				 (kernelY[3] * tmp.getSample(x-1,y, 0))   + (kernelY[4] * tmp.getSample(x,y, 0))   + (kernelY[5] * tmp.getSample(x+1,y, 0)) +
              				 (kernelY[6] * tmp.getSample(x-1,y+1, 0)) + (kernelY[7] * tmp.getSample(x,y+1, 0)) + (kernelY[8] * tmp.getSample(x+1,y+1, 0));

              	magnitude = Math.abs(xValue) + Math.abs(yValue);

              	if(magnitude < 100) {
              		magnitude = 0;
              	}

              	raster.setSample(x, y, 0, magnitude);
			}
		}

		return newImage;
	}

	public BufferedImage phase(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = newImage.getRaster();
		WritableRaster tmp = image.getRaster();

		int xValue, yValue;
		for(int y = 1; y < img.getHeight() - 2; y++) {
			for(int x = 1; x < img.getWidth() - 2; x++) {
				xValue = (kernelX[0] * tmp.getSample(x-1,y-1, 0)) + (kernelX[1] * tmp.getSample(x,y-1, 0)) + (kernelX[2] * tmp.getSample(x+1,y-1, 0)) +
              				 (kernelX[3] * tmp.getSample(x-1,y, 0))   + (kernelX[4] * tmp.getSample(x,y, 0))   + (kernelX[5] * tmp.getSample(x+1,y, 0)) +
              				 (kernelX[6] * tmp.getSample(x-1,y+1, 0)) + (kernelX[7] * tmp.getSample(x,y+1, 0)) + (kernelX[8] * tmp.getSample(x+1,y+1, 0));
              	yValue = (kernelY[0] * tmp.getSample(x-1,y-1, 0)) + (kernelY[1] * tmp.getSample(x,y-1, 0)) + (kernelY[2] * tmp.getSample(x+1,y-1, 0)) +
              				 (kernelY[3] * tmp.getSample(x-1,y, 0))   + (kernelY[4] * tmp.getSample(x,y, 0))   + (kernelY[5] * tmp.getSample(x+1,y, 0)) +
              				 (kernelY[6] * tmp.getSample(x-1,y+1, 0)) + (kernelY[7] * tmp.getSample(x,y+1, 0)) + (kernelY[8] * tmp.getSample(x+1,y+1, 0));

              	if(xValue == 0) {
              		xValue = 1;
              	}
              	if(yValue == 0) {
              		yValue = 1;
              	}

              	double magnitude = Math.atan(yValue/xValue);

              	raster.setSample(x, y, 0, magnitude);
			}
		}

		return newImage;
	}

	public BufferedImage convert2gray(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = newImage.getRaster();
		int r,g,b;
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				Color c = new Color(image.getRGB(x, y));
				r = c.getRed();
				g = c.getGreen();
				b = c.getBlue();

				raster.setSample(x, y, 0, (r + g + b) / 3);
			}
		}
		return newImage;
	}

	public static void main(String[] argv) {
		String filename;
    if (argv.length < 1) {
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("Enter <Filename>: ");
    	filename = scanner.nextLine();	
    }
    else {
    	filename = argv[0];
    }

    SobelEdgeDetection sbd = new SobelEdgeDetection(filename);
  }
}
