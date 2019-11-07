package dropbox;

import java.util.List;
import java.util.Scanner;

import exceptions.directory.CreateDirectoryExceptions;
import exceptions.directory.SearchDirectoryExceptions;
import exceptions.file.UploadFileExeption;
import dropbox.ExtensionHandler;
import formatComponent.ExtensionList;

import model.MyPath;
import modelDropbox.MyDirectory;
import modelDropbox.MyFile;
import usersComponent.User;
import usersComponent.UserDatabase;

public class Connection implements connectionComponent.Connection {
	private static final String ACCESS_TOKEN = "8AcMbJiKViAAAAAAAAAADUXBL3xJ67ZwtLJa3NBEJRVooHCeoIlAWbXH1bsf6QZq";
	
	private UserDatabase users;
	private ExtensionList extensions;
	public MyPath currentPath = new MyPath();
	
   	public MyDirectory dropbox = new MyDirectory(ACCESS_TOKEN);
	public MyFile dropboxFile = new MyFile(dropbox.getClient());
	
	
	@Override
	public void createNewStorage(String path) {
		// napravi folder
		try {
			dropbox.create(path,"");
		} catch (CreateDirectoryExceptions e) {
			e.printStackTrace();
		}
		
		// unesi glavnog korisnika
		Scanner scanner = new Scanner(System.in);
		
		users = new UserDatabase();
		User korisnik;
		System.out.println("Unesite username glavnog admina :");
		String username = scanner.nextLine();
		System.out.println("Unesite password glavnog admina :");
		String password = scanner.nextLine();
		
		korisnik = new User(username,password,true,true,true,true);
		users.getUsers().add(korisnik);
		
		// napravi json sa korisnicima
		HandleUsers handler = new HandleUsers();
		handler.saveUsers("", users);
		
		try {
			dropboxFile.upload("users.json", path+"/users.json",null);
		} catch (UploadFileExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// napravi json sa ekstenzijama
		ExtensionHandler ext = new ExtensionHandler();
		ext.createNewExtensionsList(path);
		
		try {
			dropboxFile.upload("extensions.json", path+"/extensions.json",null);
		} catch (UploadFileExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		scanner.close();
		currentPath.setPath(path);
		System.out.println("Uspesno kreiran");
	}



	@Override
	public MyPath getMyPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDatabase getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExtensionList getExtension() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getLogin() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void connectToStorage() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void disconnectFromStorage() {
		// TODO Auto-generated method stub
		
	}




	
	

}
