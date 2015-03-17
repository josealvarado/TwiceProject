import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


public class Twice {

	static HashMap<String, String> words = new HashMap<String, String>();
	static ArrayList<String> list = new ArrayList<String>();
	static HashMap<String, String> subWords = new HashMap<String, String>();
	
	public static void main(String[] args){
		
		String word = args[0];
	
		if (word.length() < 1){
			System.out.println("Word must contain more than 1 character");
			return ;
		}
		
		loadWords();
		search(word.toLowerCase());
		System.out.println("Word: " + word.toLowerCase());
		System.out.println("Words: " + list);
	}
	
	public static void search(String word){
		
		if (word.length() < 1)
			return;
		
		if (exist(word)){
			if(!subWords.containsKey(word)){
				list.add(word);
				subWords.put(word, word);
			}  
		}
		
		for (int i = 0; i < word.length(); i++){
			String sub = word.substring(0, i) + word.substring(i + 1, word.length());
			search(sub);
		}
		
		String reverse = new StringBuilder(word).reverse().toString();
		if (exist(reverse)){
			if(!subWords.containsKey(reverse)){
				list.add(reverse);
				subWords.put(reverse, reverse);
			} 
		}
		
		for (int i = 0; i < reverse.length(); i++){
			String sub = reverse.substring(0, i) + reverse.substring(i + 1, reverse.length());
			search(sub);
		} 
	}
	
	public static boolean exist(String word){
		if (words.containsKey(word)){
			return true;
		}
			
		return false;
	}
	
	public static void loadWords(){
		try {
			Files.walk(Paths.get("src/Words")).forEach(filePath -> {
				
			    if (Files.isRegularFile(filePath)) {
			        
					try(BufferedReader br = new BufferedReader(new FileReader(""+filePath))) {
				        String line = br.readLine();
				        			        
				        while (line != null) {
				        	words.put(line.toLowerCase(), line.toLowerCase());
				        				        	 
				            line = br.readLine();
				        }				        
				        
				    } catch (FileNotFoundException e) {
						System.out.println("FileNotFoundException " + e.getLocalizedMessage());
					} catch (IOException e) {
						System.out.println("IOException " + e.getLocalizedMessage());
					}
			    }
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
