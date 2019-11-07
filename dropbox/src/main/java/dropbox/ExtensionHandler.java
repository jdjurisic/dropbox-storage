package dropbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import formatComponent.ExtensionList;

public class ExtensionHandler implements formatComponent.ExtensionHandler {


	@Override
	public void createNewExtensionsList(String path) {
		// TODO Auto-generated method stub
		
			File f = new File("extensions.json");
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
	}

	@Override
	public ExtensionList readExtensions(String path) {
		Gson gson = new Gson();
		ExtensionList formati = null;
		
		try {
			formati = gson.fromJson(new FileReader(path),ExtensionList.class);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return formati; 
		
		
	}
	


	@Override
	public void saveExtensions(String path, ExtensionList fl) {
		
			File formati =new  File("extensions.json");
			Gson gson = new Gson();
			String t = gson.toJson(fl);
			
			try {
				FileWriter fw =  new FileWriter(formati);
				fw.write(t);
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		
		
	}

}
