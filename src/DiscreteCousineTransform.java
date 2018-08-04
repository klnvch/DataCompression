
public class DiscreteCousineTransform {
	
	private static double c(double i, double N){
		if(i==0){
			return Math.sqrt(1.0/N);
		}else{
			return Math.sqrt(2.0/N);
		}
	}
	
	private static double DCT(int u, int v, Block I){
		double result = 0.0;
		double x = (double)u;
		double y = (double)v;
		double T = Main.blockSize;
		for(int i=0; i!=T; ++i){
			for(int j=0; j!=T; ++j){
				double cos1 = Math.cos(((2.0*i+1)*x*Math.PI)/(2.0*T));
				double cos2 = Math.cos(((2.0*j+1)*y*Math.PI)/(2.0*T));
				result += I.getD(i, j) * cos1 * cos2;
			}
		}
		
		result *= c(x, T);
		result *= c(y, T);
		
		return result;
	}
	
	private static double inverseDCT(int u, int v, Block t){
		double result = 0.0;
		double x = (double)u;
		double y = (double)v;
		double T = Main.blockSize;
		for(int i=0; i!=T; ++i){
			for(int j=0; j!=T; ++j){
				double cos1 = Math.cos(((2.0*x+1)*i*Math.PI)/(2.0*T));
				double cos2 = Math.cos(((2.0*y+1)*j*Math.PI)/(2.0*T));
				result += c(i, T) * c(j, T) * t.getD(i, j) * cos1 * cos2;
			}
		}
		return result;
	}
	
	public static void transform(Block input, Block output){
		for(int i=0; i!=Main.blockSize; ++i){
			for(int j=0; j!=Main.blockSize; ++j){
				double newvalue = DCT(i, j, input);
				output.set(newvalue, i, j);
			}
		}
	}
	
	public static void detransform(Block input, Block output){
		for(int i=0; i!=Main.blockSize; ++i){
			for(int j=0; j!=Main.blockSize; ++j){
				double newvalue = inverseDCT(i, j, input);
				output.set(newvalue, i, j);
			}
		}
	}
	
	/*
	 *  
	 */
	public static void transform(BlockedImage inputImage, BlockedImage outputImage){
		
		Block[][] input = inputImage.get();
		Block[][] output = outputImage.get();
		
		for(int i=0; i!= input.length; ++i){
			for(int j=0; j!=input[i].length; ++j){
				transform(input[i][j], output[i][j]);
			}
		}
	}
	
	public static void detransform(BlockedImage inputImage, BlockedImage outputImage){
		
		Block[][] input = inputImage.get();
		Block[][] output = outputImage.get();
		
		for(int i=0; i!= input.length; ++i){
			for(int j=0; j!=input[i].length; ++j){
				detransform(input[i][j], output[i][j]);
			}
		}
	}
}
