
public class BlockedImage {
	private Block[][] image;
	
	public BlockedImage(int h, int w) {
		image = new Block[h][w];
		for(int i=0; i!=image.length; ++i){
			for(int j=0; j!=image[0].length; ++j){
				image[i][j] = new Block();
			}
		}
	}
	
	public Block[][] get(){
		return image;
	}
	
	@Override
	public String toString() {
		return image[0][0].toString();
	}
}
