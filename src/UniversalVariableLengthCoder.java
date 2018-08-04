
public class UniversalVariableLengthCoder {
	/*
	 * output array of strings of size input height * input width
	 */
	public static int code(BlockedImage inputImage, String[][] output){
		
		Block[][] input = inputImage.get();
		
		int result = 0;
		for(int i=0; i!=input.length; ++i){
			for(int j=0; j!=input[i].length; ++j){
				output[i][j] = code(input[i][j]);
				result += output[i][j].length();
			}
		}
		result /= 8;
		return result;
	}
	public static void decode(String[][] input, BlockedImage outputImage){
		
		Block[][] output = outputImage.get();
		
		for(int i=0; i!=output.length; ++i){
			for(int j=0; j!=output[i].length; ++j){
				decode(input[i][j], output[i][j]);
			}
		}
	}
	
	private static String code(Block input){
		String result = "";
		for(int i=0; i!=Main.blockSize; ++i){
			for(int j=0; j!=Main.blockSize; ++j){
				int k = input.getI(i, j);
				result += generateCode(k);
			}
		}
		return result;
	}
	
	private static void decode(String input, Block output){
		String temp = input;
		for(int i=0; i!=Main.blockSize; ++i){
			for(int j=0; j!=Main.blockSize; ++j){
				String str = extract(temp);
				int k = degenerateCode(str);
				output.set(k, i, j);
				temp = temp.substring(str.length());
			}
		}
	}
	
	// generate codes "01010101010010101010100101"
	
	public static String generateCode(int k){
		if(k == 0){
			return "1";
		}else if(k < 0){
			return generateCode1(Math.abs(k)) + "001";
		}else{
			return generateCode1(Math.abs(k)) + "011";
		}
	}
	
	private static String generateCode1(int k){
		if(k == 1 || k == 0){
			return "";
		}else{
			int n = k % 2;
			int m = k / 2;
			if(n == 0){
				return generateCode1(m) + "00";
			}else{
				return generateCode1(m) + "01";
			}
		}
	}
	
	// read codes "01010101001010101010"
	
	public static int degenerateCode(String str){
		if(str.equals("1")){
			return 0;
		}else if(str.endsWith("001")){
			return -1 * degerateCode1(str.substring(0, str.length()-3));
		}else{
			return degerateCode1(str.substring(0, str.length()-3));
		}
	}
	
	private static int degerateCode1(String str){
		if(str.equals("")){
			return 1;
		}else if(str.endsWith("00")){
			return 2 * degerateCode1(str.substring(0, str.length()-2));
		}else{
			return 2 * degerateCode1(str.substring(0, str.length()-2)) + 1;
		}
	}
	
	public static String extract(String str){
		if(str.startsWith("1")){
			return "1";
		}else if(str.startsWith("00")){
			return "00" + extract(str.substring(2));
		}else{
			return "01" + extract(str.substring(2));
		}
	}
}
