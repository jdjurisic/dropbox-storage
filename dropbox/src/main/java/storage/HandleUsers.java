package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import usersComponent.User;
import usersComponent.UserDatabase;

public class HandleUsers implements usersComponent.HandleUsers {

	@Override
	public void saveUserDatabase(String path, UserDatabase ub) {
		// TODO Auto-generated method stub
		
	}

	
	public void createNewUserDatabase(String path) {
		File f = new File("users.json");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/*
	 * 
	 * Dobija putanju do jsona sa korisnicima i pravi objekat klase UserDatabase.
	 * 
	 */
	public UserDatabase readFromUserDatabase(String path) {
		Gson gson = new Gson();
		UserDatabase users = null;
		
		try {
			users = gson.fromJson(new FileReader(path), UserDatabase.class);
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
		
		
		return users; 
		
		
	}
	
	/*
	 * Pri zavrsetku rada sa bazom , cuva listu korisnika na zadatoj putanji.
	 */
	public void saveUsers(String path,UserDatabase ub) {
		File korisnici =new  File("users.json");
		Gson gson = new Gson();
		String t = gson.toJson(ub);
		
		try {
			FileWriter fw =  new FileWriter(korisnici);
			fw.write(t);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
