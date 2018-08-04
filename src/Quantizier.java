import java.util.Random;


public class Quantizier {
	
	private static double mapRadius = 0;
	private static double timeConst = 0;
	
	private static double sigma(double iteration){
		//return Main.S0 * Math.exp(-iteration/Main.lambda2);
		return mapRadius * Math.exp(-iteration / timeConst);
	}
	
	/*
	 * learning rate
	 */
	private static double L(double iteration){
		//return Main.L0 * Math.exp(-iteration/Main.lambda1);
		return Main.L0 * Math.exp( -iteration / ( Main.limit - iteration ));
	}
	
	private static double theta(double squaredDistance, double sigma){
		return Math.exp(-squaredDistance/(2.0*sigma*sigma));
	}
	
	/*
	 *  nodes - cells in the map
	 *  
	 *  ci, cj - the best cell in the map
	 *  
	 *  block - the current block from the image
	 *  
	 *  iteration - the current iteration
	 */
	public static void updateNodes(Block[][] nodes, int ci, int cj,  Block block, double learningRate, double sigma){
		for(int i=0; i!=nodes.length; ++i){
			for(int j=0; j!=nodes[i].length; ++j){
				double squaredDistance = squaredDistance(ci, cj, i, j);
				
				if( squaredDistance < sigma * sigma ){
					
					double T = theta(squaredDistance, sigma);
				
					for(int i1=0; i1!=Main.blockSize; ++i1){
						for(int j1=0; j1!=Main.blockSize; ++j1){
							double oldvalue = nodes[i][j].getD(i1, j1);
							double idealvalue = block.getD(i1, j1);
							double newvalue = oldvalue + learningRate * T * (idealvalue-oldvalue);
							nodes[i][j].set(newvalue, i1, j1);
						}
					}
				
				}
			}
		}
	}
	
	/*
	 * i, j - the center cell
	 * 
	 * ni, nj - the neighbour cell
	 */
	private static double squaredDistance(double ci, double cj, double ni, double nj){
		double result = (ci - ni) * (ci - ni) + (cj - nj) * (cj - nj);
		return result;
	}
	
	private static double distance(Block p, Block q){
		double result = 0.0;
		for(int i=0; i!=Main.blockSize; ++i){
			for(int j=0; j!=Main.blockSize; ++j){
				result += (p.getD(i, j) - q.getD(i, j)) * (p.getD(i, j) - q.getD(i, j));
			}
		}
		//result = Math.sqrt(result);
		return result;
	}
	
	/*
	 * input - image
	 * 
	 * output - cells in Kohonen map filled by random values
	 * 
	 * limit - number of iterations
	 */
	public static void quantize(BlockedImage inputImage, BlockedImage outputImage, Codebook codebook){
		
		// count constants
		mapRadius = Math.max(Main.kohonenSizeHeight, Main.kohonenSizeWidth)/2.0;
		timeConst = Main.limit / Math.log(mapRadius);
		
		////
		
		Block[][] input = inputImage.get();
		Block[][] output = outputImage.get();
		
		// initialize kohonen map by vectors from inputImage
		//double dx = input.length/output.length;
		//double dy = input[0].length/output[0].length;
		
		for(int i=0; i!=output.length; ++i){
			for(int j=0; j!=output[i].length; ++j){
				//output[i][j].copy(input[(int)(i*dx)][(int)(j*dy)]);
				output[i][j].fillByRandomValues();
			}
		}
		
		// learn kohonen map
		Random r = new Random();
		for(int k=0; k!=Main.limit; ++k){// number of iterations
			
			// 2. Take next input vector
			int i1 = r.nextInt(input.length);
			int j1 = r.nextInt(input[0].length);
			
			int nearesti = -1;
			int nearestj = -1;
			double bestDistance = Double.MAX_VALUE;
			for(int i2=0; i2!=output.length; ++i2){
				for(int j2=0; j2!= output[i2].length; ++j2){ // for every cell in the map
					// 3. a. Use Euclidian distance
					double distance = distance(input[i1][j1], output[i2][j2]);
					// 3. b. Track the node in the smallest distance
					if(distance < bestDistance){
						bestDistance = distance;
						nearesti = i2;
						nearestj = j2;
					}
				}
			}
			double L = L(k);
			//System.out.println(L);
			double sigma = sigma(k);
			updateNodes(output, nearesti, nearestj, input[i1][j1], L, sigma);

		}
		
		// init code book and zeros useless nodes
		boolean[][] useless = new boolean[Main.kohonenSizeHeight][Main.kohonenSizeWidth];
		for(int i=0; i!=input.length; ++i){
			for(int j=0; j!=input[i].length; ++j){
				int nearesti = -1;
				int nearestj = -1;
				double bestDistance = Double.MAX_VALUE;
				for(int i2=0; i2!=output.length; ++i2){
					for(int j2=0; j2!= output[i2].length; ++j2){ // for every cell in the map
						// 3. a. Use Euclidian distance
						double distance = distance(input[i][j], output[i2][j2]);
						// 3. b. Track the node in the smallest distance
						if(distance < bestDistance){
							bestDistance = distance;
							nearesti = i2;
							nearestj = j2;
						}
					}
				}
				codebook.set(i, j, nearesti, nearestj);
				useless[nearesti][nearestj] = true;
			}
		}
		for(int i=0; i!=Main.kohonenSizeHeight; ++i){
			for(int j=0; j!=Main.kohonenSizeWidth; ++j){
				if(useless[i][j] == false){
					output[i][j].fillByZeros();
				}
			}
		}
	}
	
	/*
	 * input - Kohonen map
	 * 
	 * output - image
	 */
	public static void dequantize(BlockedImage inputImage, BlockedImage outputImage, Codebook codebook){
		
		Block[][] input = inputImage.get();
		Block[][] output = outputImage.get();
		
		for(int i=0; i!=output.length; ++i){
			for(int j=0; j!=output[i].length; ++j){
				int i1 = codebook.getI(i, j);
				int j1 = codebook.getJ(i, j);
				output[i][j].copy(input[i1][j1]);
			}
		}
	}
}
