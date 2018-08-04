
public class LowPassFilter {
	
	private static int x0 = 0;
	private static int y0 = 0;
	
	static public void filter(BlockedImage inputImage, BlockedImage outputImage){
		
		Block[][] input = inputImage.get();
		Block[][] output = outputImage.get();
		
		for(int i=0; i!=input.length; ++i){
			for(int j=0; j!=input[i].length; ++j){
				filter(input[i][j], output[i][j]);
			}
		}
	}
	
	private static void filter(Block input, Block output){
		for(int i=0; i!=Main.blockSize; ++i){
			for(int j=0; j!=Main.blockSize; ++j){
				double distance = Math.sqrt((x0-i)*(x0-i)+(y0-j)*(y0-j));
				if(distance < Main.D0){
					double value = input.getD(i, j);
					output.set(value, i, j);
				}else{
					output.set(0, i, j);
			    }
			}
		}
	}
	
	
}
