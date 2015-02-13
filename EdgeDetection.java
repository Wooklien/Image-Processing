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

public class EdgeDetection {
	private int[] kernelX = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
	private int[] kernelY = { -1, -2, -1, 0, 0, 0, 1, 2, 1};

	int[] dx = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
	int[] dy = {-1, -1, -1, 0, 0, 0, 1, 1, 1};

	int[][] m =  {{ 2, 2, 2, 2, 2, 2, 2, 2 },
                  { 2, 2, 2, 2, 2, 2, 2, 7 },
                  { 2, 2, 2, 2, 2, 2, 7, 7 },
                  { 2, 2, 2, 2, 2, 7, 7, 7 },
                  { 2, 2, 2, 2, 7, 7, 7, 7 },
                  { 2, 2, 2, 7, 7, 7, 7, 7 },
                  { 2, 2, 7, 7, 7, 7, 7, 7 },
                  { 2, 7, 7, 7, 7, 7, 7, 7 }
                 };

	public EdgeDetection() {
		int[][] n = new int[6][6];
		System.out.println("\nY Sobel Edge Detection\n");
		for(int y = 1; y < 6; y++) {
			for(int x = 1; x < 6; x++) {
				int yValue = (kernelY[0] * m[x-1][y-1]) + 
              			 (kernelY[1] * m[x][y-1]) + 
              			 (kernelY[2] * m[x+1][y-1]) +
              			 (kernelY[3] * m[x-1][y]) + 
              			 (kernelY[4] * m[x][y]) + 
              			 (kernelY[5] * m[x+1][y]) +
              			 (kernelY[6] * m[x-1][y+1]) + 
              			 (kernelY[7] * m[x][y+1]) + 
              			 (kernelY[8] * m[x+1][y+1]);
              	System.out.print(yValue + "\t");
			}
			System.out.println("");
		}

		System.out.println("\nX Sobel Edge Detection\n");

		for(int y = 1; y < 6; y++) {
			for(int x = 1; x < 6; x++) {
				int yValue = (kernelX[0] * m[x-1][y-1]) + 
              			 (kernelX[1] * m[x][y-1]) + 
              			 (kernelX[2] * m[x+1][y-1]) +
              			 (kernelX[3] * m[x-1][y]) + 
              			 (kernelX[4] * m[x][y]) + 
              			 (kernelX[5] * m[x+1][y]) +
              			 (kernelX[6] * m[x-1][y+1]) + 
              			 (kernelX[7] * m[x][y+1]) + 
              			 (kernelX[8] * m[x+1][y+1]);
              	System.out.print(yValue + "\t");
			}
			System.out.println("");
		}

		System.out.println("\nMagnitude Sobel Edge Detection\n");


		for(int y = 1; y < 6; y++) {
			for(int x = 1; x < 6; x++) {
				int xValue = (kernelX[0] * m[x-1][y-1]) + 
              			 (kernelX[1] * m[x][y-1]) + 
              			 (kernelX[2] * m[x+1][y-1]) +
              			 (kernelX[3] * m[x-1][y]) + 
              			 (kernelX[4] * m[x][y]) + 
              			 (kernelX[5] * m[x+1][y]) +
              			 (kernelX[6] * m[x-1][y+1]) + 
              			 (kernelX[7] * m[x][y+1]) + 
              			 (kernelX[8] * m[x+1][y+1]);

              	int yValue = (kernelY[0] * m[x-1][y-1]) + 
              			 (kernelY[1] * m[x][y-1]) + 
              			 (kernelY[2] * m[x+1][y-1]) +
              			 (kernelY[3] * m[x-1][y]) + 
              			 (kernelY[4] * m[x][y]) + 
              			 (kernelY[5] * m[x+1][y]) +
              			 (kernelY[6] * m[x-1][y+1]) + 
              			 (kernelY[7] * m[x][y+1]) + 
              			 (kernelY[8] * m[x+1][y+1]);

              	int magnitude = Math.abs(xValue) + Math.abs(yValue);
              	System.out.print( magnitude + "\t");
			}
			System.out.println("");
		}
	}

	public static void main(String[] argv) {

    EdgeDetection ced = new EdgeDetection();
  }
}
