package optimizingLatency;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class main {
//	public static final String _SOURCE_FILE = "src/test.jpg";
//	public static final String _DESTINATION_FILE = "src/out.jpg";
//	public static final String _SOURCE_FILE = "src/bang.jpg";
	public static final String _SOURCE_FILE = "src/test2.jpg";
	public static final String _DESTINATION_FILE = "src/test2result.jpg";
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedImage originalImage = ImageIO.read(new File(_SOURCE_FILE));
		BufferedImage resultImage = new BufferedImage(originalImage.getWidth(),originalImage.getHeight(),originalImage.TYPE_INT_RGB);
		
		long startTime = System.currentTimeMillis();
		
//		podejscie 1
//		recolorSingleThreaded(originalImage, resultImage);
		
//		podejscie 2 - multithread
		int noThreads = 4;
		recolorMultiThreaded(originalImage, resultImage, noThreads);
		
		long stopTime = System.currentTimeMillis();
		
		
		
		long duration = stopTime - startTime;
		
		File outFile = new File(_DESTINATION_FILE);
		ImageIO.write(resultImage,"jpg",outFile);
		
		System.out.println(String.valueOf(duration));
		
	}
	
	public static void recolorMultiThreaded(BufferedImage inImage, BufferedImage outImage, int noThreads) {
		List<Thread> threads = new ArrayList<>();
		
		int width = inImage.getWidth();
		int height = inImage.getHeight()/noThreads;
		
		for(int i=0;i<noThreads;i++) 
		{
			final int threadMultiplier = i;
			
			Thread thread = new Thread(()-> {
				int leftCorner = 0;
				int topCorner = height * threadMultiplier;
				
				recolorImage(inImage, outImage, leftCorner, topCorner, width, height);
			});
			threads.add(thread);
		}
		for(Thread thread : threads) {
			thread.start();
		}
		for(Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public static void recolorSingleThreaded(BufferedImage inImage,BufferedImage outImage) {
		recolorImage(inImage, outImage, 0, 0, inImage.getWidth(), inImage.getHeight());
	}
	
	public static void recolorImage(BufferedImage inImage, BufferedImage outImage, int leftCorner, int topCorner, int width, int height) {
		for(int x=leftCorner; x<leftCorner+width && x<outImage.getWidth(); x++) {
			for(int y=topCorner; y<topCorner+height && y<outImage.getHeight(); y++) {
				recolorPixel(inImage, outImage, x, y);
				
			}
		}
	}
	
	public static void recolorPixel(BufferedImage inImage,BufferedImage outImage,int x,int y) {
		int rgb = inImage.getRGB(x, y);
		
		int red = getRed(rgb);
		int green = getGreen(rgb);
		int blue = getBlue(rgb);
		
		int newRed, newGreen, newBlue;
		
		if(isShadeOfGray(red,green,blue)) {
			newRed  = Math.min(255,red + 10);
			newGreen = Math.min(180,green + 10);
			newBlue = Math.max(0, blue + 10);
			
		}else {
			newRed = red;
			newGreen = green;
			newBlue = blue;
		}
		
		int newRGB = createRGBFromColors(newRed,newGreen,newBlue);
		setRGB(outImage,x,y,newRGB);
	}
	
	public static void setRGB(BufferedImage image, int x, int y, int rgb) {
		image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
	}
	
	public static boolean isShadeOfGray(int red,int green,int blue) {
		return Math.abs(red-green)<30 && Math.abs(red-blue)<30 && Math.abs(blue-green)<30;
	}
	
	public static int createRGBFromColors(int red,int green,int blue) {
		int rgb = 0;
		
		rgb |= blue;
		rgb |= green << 8;
		rgb |= red <<16;
		
		rgb |= 0xFF000000;
		
		return rgb;
	}
	
	public static int getRed(int rgb) {
		return (rgb & 0x00FF0000) >> 16;
	}
	
	public static int getGreen(int rgb) {
		return (rgb & 0x0000FF00) >> 8;
	}
	
	public static int getBlue(int rgb) {
		return (rgb & 0x000000FF);
	}
}
