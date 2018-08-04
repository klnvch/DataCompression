
public class Codebook {
	int[][][] codebook;
	
	public Codebook(int h, int w){
		codebook = new int[h][w][2];
	}
	
	public void set(int i, int j, int ki, int kj){
		codebook[i][j][0] = ki;
		codebook[i][j][1] = kj;
	}
	
	public int getI(int i, int j){
		return codebook[i][j][0];
	}
	
	public int getJ(int i, int j){
		return codebook[i][j][1];
	}
	
	public int length() {
		int result = 0;
		
		for(int i=0; i!=codebook.length; ++i){
			for(int j=0; j!=codebook[i].length; ++j){
				result += 1 + 1;
			}
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		String result = "";
		for(int i=0; i!=codebook.length; ++i){
			for(int j=0; j!=codebook[0].length; ++j){
				result += "(" + codebook[i][j][0] + "," + codebook[i][j][1] + ")";
			}
		}
		return result;
	}
}
