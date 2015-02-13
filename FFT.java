/*
Quoc Lien, 816097211
CS559 - Computer Vision
FFT.java
*/

import java.awt.image.*;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import com.pearsoneduc.ip.op.ImageFFT;
import com.pearsoneduc.ip.op.FFTException;
import java.awt.Color;

public class FFT extends ImageFFT {
	float xCenter, yCenter;
	static Scanner scanner = new Scanner(System.in);
	public enum option {
		NONE, LPCF, LPBF, HPCF,HPBF, LPBF2, HPBF2, EXIT
	}
	public static option userOption = option.NONE;

	public FFT(BufferedImage image) throws FFTException {
		super(image);
		xCenter = getWidth()/2;
		yCenter = getHeight()/2;
	}

	public FFT(BufferedImage image, int win) throws FFTException {
		super(image, win);
		xCenter = getWidth()/2;
		yCenter = getHeight()/2;
	}

	public FFT(BufferedImage image, String filename) throws FFTException {
		super(image);
		xCenter = getWidth()/2;
		yCenter = getHeight()/2;
	}

	public static BufferedImage convert2gray(BufferedImage image) {
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

	public void transform() {
		super.transform();
	}

	public void reverse(int x) {
		int b = 0;
        while (x != 0) {
            b <<= 1;
            b |= (x & 1);
            x >>= 1;
        }
		System.out.println(b);
	}

	public void export(String file) {
		BufferedImage export = null;
		try {
			if(isSpectral()) {
				export = getSpectrum();
			}
			else {
				export = toImage(null);
			}
		} catch (FFTException e) {
			System.out.println(e);
			System.exit(2);
		}

		try {
			ImageIO.write(export, "jpg", new File(file));
		} catch (Exception e) {
			System.out.println(e);
			System.exit(3);
		}
	}

	public void LPCF(double radius) {
		int width = getWidth();
		int height = getHeight();
		int xCenter = width/2;
	    int yCenter = height/2;
	    int i = 0;

	    double r = 0, u2, v2;
	    double r0 = radius * radius;
	    double r1 = Math.min(xCenter, yCenter);
	    for (int v = 0; v < height; ++v) {
	    	v2 = shift(v, yCenter) - yCenter;
	    	for (int u = 0; u < width; ++u, ++i) {
	    		u2 = shift(u, xCenter) - xCenter;

	    		r = (v2*v2 + u2*u2)/(r1*r1);
	    		
		        if (r > r0)
		          data[i].re = data[i].im = 0.0f;
		    }
	    }

	    System.out.println(r + " " + radius);
	}

	public double distance(double u, double v) {
		return Math.sqrt((u*u) + (v*v));
	}

	public void LPBF(int n, double radius) {
		int width = getWidth();
		int height = getHeight();
		int xCenter = width/2;
	    int yCenter = height/2;
	    int i = 0;

	    double r = 0, u2, v2;
	    double r0 = radius * radius;
	    double r1 = Math.min(xCenter, yCenter);
	    for (int v = 0; v < height; ++v) {
	    	v2 = shift(v, yCenter) - yCenter;
	    	for (int u = 0; u < width; ++u, ++i) {
	    		u2 = shift(u, xCenter) - xCenter;

	    		r = (v2*v2 + u2*u2)/(r1*r1);
	    		double filter = 1/(1+Math.pow(r/(r0), n));
	    		data[i].re *= filter; 
	    		data[i].im *= filter;
		    }
	    }

	    System.out.println(r + " " + radius);
	}

	public void LPBFx(int n, double radius) {
		try {
			butterworthLowPassFilter(n, radius);
		} catch(FFTException e) {
			System.out.println(e);
		}
	}

	public void HPCF(double radius) {
		int width = getWidth();
		int height = getHeight();
		int xCenter = width/2;
	    int yCenter = height/2;
	    int i = 0;

	    double r = 0, u2, v2;
	    double r0 = radius * radius;
	    double r1 = Math.min(xCenter, yCenter);
	    for (int v = 0; v < height; ++v) {
	    	v2 = shift(v, yCenter) - yCenter;
	    	for (int u = 0; u < width; ++u, ++i) {
	    		u2 = shift(u, xCenter) - xCenter;

	    		r = (v2*v2 + u2*u2)/(r1*r1);
	    		
		        if (r < r0)
		          data[i].re = data[i].im = 0.0f;
		    }
	    }

	    System.out.println(r + " " + radius);
	}

	public void HPBF(int n, double radius) {
		int width = getWidth();
		int height = getHeight();
		int xCenter = width/2;
	    int yCenter = height/2;
	    int i = 0;

	    double r = 0, u2, v2;
	    double r0 = radius * radius;
	    double r1 = Math.min(xCenter, yCenter);
	    for (int v = 0; v < height; ++v) {
	    	v2 = shift(v, yCenter) - yCenter;
	    	for (int u = 0; u < width; ++u, ++i) {
	    		u2 = shift(u, xCenter) - xCenter;

	    		r = (v2*v2 + u2*u2)/(r1*r1);
	    		double filter = 1 /(1+(Math.pow(r/(r0), n)));
	    		data[i].re *= (1-filter); 
		      	data[i].im *= (1-filter);
		    }
	    }

	    System.out.println(r + " " + radius);
	}

	public void HPBFx(int n, double radius) {
		try {
			butterworthHighPassFilter(n, radius);
		} catch (FFTException e) {
			System.out.println(e);
		}
	}

	public static void main(String[] argv) {
		String filename;
		double filterMode = 0;
		initializeGUI();
	    if (argv.length < 1) {
	    	System.out.println("Enter <Filename>: ");
	    	filename = scanner.nextLine();
	    }
	    else {
	    	filename = argv[0];
    	}

    	BufferedImage img = null;
    	try {
    		img = ImageIO.read(new File(filename));
    	} catch (IOException e) {
    		System.out.println(e);
    	}

    	BufferedImage gray = convert2gray(img);

    	FFT fft = null;
    	try {
    		fft = new FFT(gray, filename);
    	} catch (FFTException e) {
    		System.err.println(e);
    	}

    	fft.transform();
    	fft.export("fftImg.jpg");
    	while(userOption == option.NONE) {
    		fft.options();
    		try {
		    	filterMode = getValue(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.err.println("Invalid Option - Option must be an integer value!");
		    	System.exit(1);	
			}
	    	userOption = getOption(filterMode);
	    	fft.filtering(userOption);
	    }
	}

	public static void initializeGUI() {
		System.out.println("<START> TEXT-BASED GUI");
    	System.out.println("INITIALIZING TEXT-BASED GUI...");
    	System.out.println("BUFFERING TEXT-BASED GUI...");
    	for(int i = 0; i < 1000; i++) {
    		System.out.print(".");
    		try {
    			Thread.sleep(5);
    		} catch (Exception e) {
    			System.out.println(e);
    		}
    	}
    	System.out.println("\nTEXT-BASED GUI INITIALIZED!\n");
	}

	public static void options() {
		System.out.println("Please Select A Filtering Option: ");
		System.out.println("-Low-Pass Filtering-\n\t (1) - Circular Filter \n\t (2) - Butterworth Filter");
		System.out.println("-High-Pass Filtering-\n\t (3) - Circular Filter \n\t (4) - Butterworth Filter");
		System.out.println("-Imported L/H-Pass Filtering-\n\t (5) - BW LP Filter \n\t (6) - BW HP Filter");
		System.out.print("% ");
	}

	public static option getOption(double o) {
		option temp;
		if(o == 1) {
			temp = option.LPCF;
		}
		else if(o == 2) {
			temp = option.LPBF;
		}
		else if(o == 3) {
			temp = option.HPCF;
		}
		else if(o == 4) {
			temp = option.HPBF;
		}
		else if(o == 5) {
			temp = option.LPBF2;
		}
		else if(o == 6) {
			temp = option.HPBF2;
		}
		else {
			return option.NONE; 
		}
		return temp;
	}

	public void filtering(option o) {

		double r0 = 0, m = 0;
		switch (o) {
	    	case LPCF :
	    		System.out.println("Low-Pass Circular Filtering Selected.");
	    		System.out.print("Enter Length of Radius: ");
	    		r0 = getValue(scanner.nextLine());
	    		LPCF(r0);
	    		export("lpcf_fftFiltImg.jpg");
	    		transform();
	    		export("lpcf_filtImage.jpg");
	    		break;
	    	case LPBF :
    			System.out.println("Low-Pass Butterworth Filtering Selected.");
    			System.out.print("Enter Length of Radius: ");
	    		r0 = getValue(scanner.nextLine());
	    		System.out.print("Enter the Order: ");
	    		m = getValue(scanner.nextLine());
	    		LPBF((int)m, r0);
	    		export("lpbf_fftFiltImg.jpg");
	    		transform();
	    		export("lpbf_filtImage.jpg");
    			break;
	    	case HPCF :
	   			System.out.println("High-Pass Circular Filtering Selected.");
	   			System.out.print("Enter Length of Radius: ");
	    		r0 = getValue(scanner.nextLine());
	    		HPCF(r0);
	    		export("hpcf_fftFiltImg.jpg");
	    		transform();
	    		export("hpcf_filtImage.jpg");
	   			break;
	    	case HPBF :
	   			System.out.println("High-Pass Butterworth Filtering Selected.");
	   			System.out.print("Enter Length of Radius: ");
	    		r0 = getValue(scanner.nextLine());
	    		System.out.print("Enter the Order: ");
	    		m = getValue(scanner.nextLine());
	    		HPBF((int)m, r0);
	    		export("hpbf_fftFiltImg.jpg");
	    		transform();
	    		export("hpbf_filtImage.jpg");
	   			break;
	   		case LPBF2 :
	   			System.out.println("Low-Pass Butterworth Filtering Selected.");
	   			System.out.print("Enter Length of Radius: ");
	    		r0 = getValue(scanner.nextLine());
	    		System.out.print("Enter the Order: ");
	    		m = getValue(scanner.nextLine());
	    		LPBFx((int)m, r0);
	    		export("lpbf2_fftFiltImg.jpg");
	    		transform();
	    		export("lpbf2_filtImage.jpg");
	   			break;
	   		case HPBF2 :
	   			System.out.println("High-Pass Butterworth Filtering Selected.");
	   			System.out.print("Enter Length of Radius: ");
	    		r0 = getValue(scanner.nextLine());
	    		System.out.print("Enter the Order: ");
	    		m = getValue(scanner.nextLine());
	    		HPBFx((int)m, r0);
	    		export("hpbf2_fftFiltImg.jpg");
	    		transform();
	    		export("hpbf2_filtImage.jpg");
	   			break;	   			
	    	}
	}

	public static double getValue(String s) {
		double temp = 0;
		temp = Double.valueOf(s);
		return temp;
	}
}

