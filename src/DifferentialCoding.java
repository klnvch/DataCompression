
public class DifferentialCoding {

	/*
	 *  as input we have 2-dimensial array of blocks
	 */
	public static void code(BlockedImage inputImage, BlockedImage outputImage){
		
		Block[][] input = inputImage.get();
		Block[][] output = outputImage.get();
		
		for(int i=0; i!=input.length; ++i){
			for(int j=0; j!=input[i].length; ++j){
				code(input[i][j], output[i][j]);
			}
		}
	}
	public static void decode(BlockedImage inputImage, BlockedImage outputImage){
		
		Block[][] input = inputImage.get();
		Block[][] output = outputImage.get();
		
		for(int i=0; i!=input.length; ++i){
			for(int j=0; j!=input[i].length; ++j){
				decode(input[i][j], output[i][j]);
			}
		}
	}
	/*
	 * Coefficient a1, a2, a3, ... must be computed
	 */
	private static void code(Block input, Block output){
		for(int i=Main.blockSize-1; i>=0; --i){
			for(int j=Main.blockSize-1; j>=0; --j){
				if( i==Main.blockSize-1 || j==Main.blockSize-1 ){
					output.set(input.getD(i, j), i, j);
				}else{
					double value = 0.5*input.getD(i, j) - 0.25*input.getD(i+1, j) - 0.25*input.getD(i, j+1);
					output.set(value, i, j);
				}
			}
		}
	}
	
	private static void decode(Block input, Block output){
		for(int i=Main.blockSize-1; i>=0; --i){
			for(int j=Main.blockSize-1; j>=0; --j){
				if(i==Main.blockSize-1 || j==Main.blockSize-1){
					output.set(input.getD(i, j), i, j);
				}else{
					double value = (0.25*output.getD(i+1, j) + 0.25*output.getD(i, j+1) + input.getD(i, j))/0.5;
					output.set(value, i, j);
				}
			}
		}
	}

}
