import java.awt.Color;
import java.awt.image.BufferedImage;


public class Main {
	
	//for 1 million
	//public static int limit = 1000000;
	//public static int kohonenSizeHeight = 8;
	//public static int kohonenSizeWidth = 8;
	// learning rate
	//public static double L0 = 0.7;
	//public static double lambda1 = 765.0;
	// neigbour influence
	//public static double S0 = 30;
	//public static double lambda2 = 95000;
	
	// block size
	public static int blockSize = 4;	
	// for 10000 iterations
	public static int limit = 1000;//10000;
	public static int kohonenSizeHeight = 8;
	public static int kohonenSizeWidth = 8;
	// learning rate
	public static double L0 = 0.1;//0.7;
	public static double lambda1 = 1530.0;
	// neigbour influence
	public static double S0 = 1.5;
	public static double lambda2 = 3200;
	// low pass filter
	public static double D0 = 4;
	// bytes info
	public static int bytesBefore = 0;
	public static int bytesAfter = 0;
	public static int bytesCodebook = 0;
	
	public static void process(int[][] input, int[][] output){
		
		//number of blocks
		int h = input.length/Main.blockSize;
		int w = input[0].length/Main.blockSize;
		
		
		BlockedImage output0 = new BlockedImage(h, w);
		bytesBefore += Vectorizator.vectorize(input, output0);
		
		System.out.println("Vectorization... done!");
		
		BlockedImage output1 = new BlockedImage(h, w);
		DiscreteCousineTransform.transform(output0, output1);
		
		System.out.println("DCT... done!");
		
		BlockedImage output2 = new BlockedImage(h, w);
		LowPassFilter.filter(output1, output2);
		
		System.out.println("Low pass filter... done!");
		
		BlockedImage output3 = new BlockedImage(kohonenSizeHeight, kohonenSizeWidth);
		Codebook codebook = new Codebook(h, w);
		Quantizier.quantize(output2, output3, codebook);
		bytesCodebook += codebook.length();
		
		System.out.println("Quantization... done!");
		
		BlockedImage output4 = new BlockedImage(kohonenSizeHeight, kohonenSizeWidth);
		DifferentialCoding.code(output3, output4);
		
		System.out.println("Differential coding... done!");
		
		String[][] output5 = new String[kohonenSizeHeight][kohonenSizeWidth];
		bytesAfter += UniversalVariableLengthCoder.code(output4, output5);
		
		System.out.println("UVLC... done!");
		
		///
		
		BlockedImage output6 = new BlockedImage(kohonenSizeHeight, kohonenSizeWidth);
		UniversalVariableLengthCoder.decode(output5, output6);
		
		System.out.println("UVLC(-1)... done!");
		
		BlockedImage output7 = new BlockedImage(kohonenSizeHeight, kohonenSizeWidth);
		DifferentialCoding.decode(output4, output7);
		
		System.out.println("Differential Coding(-1)... done!");
		
		BlockedImage output8 = new BlockedImage(h, w);
		Quantizier.dequantize(output7, output8, codebook);
		
		System.out.println("Dequantization... done!");
		
		BlockedImage output9 = new BlockedImage(h, w);
		DiscreteCousineTransform.detransform(output8, output9);
		
		System.out.println("DCT(-1)... done!");
		
		Vectorizator.devectorize(output9, output);
		
		System.out.println("Devectorization... done!");
	}
	
	public static void compress(int[][] input, String[][] output, Codebook codebook){
		int h = input.length/Main.blockSize;
		int w = input[0].length/Main.blockSize;
		
		// vectorize
		BlockedImage image1 = new BlockedImage(h, w);
		Vectorizator.vectorize(input, image1);
		
		// DFT
		BlockedImage image2 = new BlockedImage(h, w);
		DiscreteCousineTransform.transform(image1, image2);
		
		// Low Pass Filter
		BlockedImage image3 = new BlockedImage(h, w);
		LowPassFilter.filter(image2, image3);
		
		// quantize
		int kh = 4;
		int kw = 4;
		BlockedImage image4 = new BlockedImage(kh, kw);
		Quantizier.quantize(image3, image4, codebook);
		
		// Differential coding
		BlockedImage image5 = new BlockedImage(kh, kw);
		DifferentialCoding.code(image4, image5);
		
		// UVLC
		UniversalVariableLengthCoder.code(image4, output);
	}
	
	public static void decompress(int[][] output, String[][] input, Codebook codebook){
		// UVLC
		int kh = 100;
		int kw = 100;
		BlockedImage image1 = new BlockedImage(kh, kw);
		UniversalVariableLengthCoder.decode(input, image1);
		
		// Differential coding
		BlockedImage image2 = new BlockedImage(kh, kw);
		DifferentialCoding.decode(image1, image2);
		
		// quantizer
		int h = output.length/Main.blockSize;
		int w = output[0].length/Main.blockSize;
		BlockedImage image3 = new BlockedImage(h, w);
		Quantizier.dequantize(image2, image3, codebook);
		
		// vectorize
		Vectorizator.devectorize(image3, output);
	}

	public static BufferedImage createImage(int[][] r, int[][] g, int[][] b){
		BufferedImage result = new BufferedImage(r.length, r[0].length, BufferedImage.TYPE_INT_RGB);
		
		for(int i=0; i!=r.length; ++i){
			for(int j=0; j!=r[i].length; ++j){
				
				if(r[i][j] < 0)		r[i][j] = 0;
				if(r[i][j] > 255)	r[i][j] = 255;
				
				if(g[i][j] < 0)		g[i][j] = 0;
				if(g[i][j] > 255)	g[i][j] = 255;
				
				if(b[i][j] < 0)		b[i][j] = 0;
				if(b[i][j] > 255)	b[i][j] = 255;
				
				Color color = new Color(r[i][j], g[i][j], b[i][j]);
				result.setRGB(i, j, color.getRGB());
			}
		}
		
		return result;
	}
	
}
