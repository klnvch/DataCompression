import java.util.Random;

/*
 *  block of TxT pixels
 */
public class Block {
	//public final static int T = 4;
	private int[][] block = new int[Main.blockSize][Main.blockSize];
	private double[][] blockD = new double[Main.blockSize][Main.blockSize];
	
	public int getI(int i, int j){
		return block[i][j];
	}
	
	public double getD(int i, int j){
		return blockD[i][j];
	}
	
	public void set(int value, int i, int j){
		block[i][j] = value;
		blockD[i][j] = value;
	}
	
	public void set(double value, int i, int j){
		blockD[i][j] = value;
		block[i][j] = (int)Math.round(value);
	}
	
	public void copy(Block b){
		for(int i=0; i!=Main.blockSize; ++i){
			for(int j=0; j!=Main.blockSize; ++j){
				block[i][j] = b.getI(i, j);
				blockD[i][j] = b.getD(i, j);
			}
		}
	}
	
	public void fillByRandomValues(){
		Random r = new Random();
		for(int i=0; i!=Main.blockSize; ++i){
			for(int j=0; j!=Main.blockSize; ++j){
				blockD[i][j] = block[i][j] = r.nextInt(10) - 5;
			}
		}
	}
	
	public void fillByZeros(){
		for(int i=0; i!=Main.blockSize; ++i){
			for(int j=0; j!=Main.blockSize; ++j){
				blockD[i][j] = block[i][j] = 0;
			}
		}
	}
	
	@Override
	public String toString() {
		String result = "";
		for(int i=0; i!=Main.blockSize; ++i){
			for(int j=0; j!=Main.blockSize; ++j){
				result += block[i][j];
				//result += blockD[i][j];
				result += " ";
			}
			result += '\n';
		}
		return result;
	}
}
