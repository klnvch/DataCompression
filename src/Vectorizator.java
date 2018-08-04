
public class Vectorizator {

	/*
	 * input - array of RGB values of the image
	 * 
	 * 	w1 - width of the image
	 *  
	 *  h1 - height of the image
	 *  
	 *  output - array of vectors
	 *  
	 *  blocksize - size of block of the image that is written as vector
	 *  
	 */
	
	
	public static int vectorize(int[][] input, BlockedImage outputImage) {
		
		Block[][] output = outputImage.get();
		
		int result = 0;
		for(int i=0; i!=input.length; ++i){
			for(int j=0; j!=input[i].length; ++j){
				Block b = output[i/Main.blockSize][j/Main.blockSize];
				b.set(input[i][j], i%Main.blockSize, j%Main.blockSize);
				++result;
			}
		}
		return result;
	}
	
	public static void devectorize(BlockedImage inputImage, int[][] output){
		
		Block[][] input = inputImage.get();
		
		for(int i=0; i!=output.length; ++i){
			for(int j=0; j!=output[i].length; ++j){
				Block b = input[i/Main.blockSize][j/Main.blockSize];
				output[i][j] = b.getI(i%Main.blockSize, j%Main.blockSize);
			}
		}
	}

}
