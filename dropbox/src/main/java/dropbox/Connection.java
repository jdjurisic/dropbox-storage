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
		
		
		// dodavanje novih korisnika
//		while(true) {
//			String noviUsername = scanner.nextLine();
//			
//			String novaSifra = scanner.nextLine();
//			Boolean snimanje,brisanje,preuzimanje;
//			
//			snimanje = scanner.nextBoolean();
//			brisanje = scanner.nextBoolean();
//			preuzimanje = scanner.nextBoolean();
//			
//			User noviKorisnik = new User(noviUsername,novaSifra,false,snimanje,brisanje,preuzimanje);
//			users.getUsers().add(noviKorisnik);
//			
//			
//			System.out.println("Da li zelite da unesete jos korisnika?");
//			Boolean daNe = scanner.nextBoolean();
//			if(!daNe) {
//				break;
//			}
//			if(scanner.hasNext()) {
//				
//				System.out.println("A"+scanner.nextLine()+"B");
//			}
//		}
		
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
		Scanner scanner = new Scanner(System.in);
		String path;
		try {
			System.out.println("Dostupna skladista na dropboxu:");
			dropbox.listAllinDirectoryInDirectory("");
		} catch (SearchDirectoryExceptions e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		System.out.println("\nUnesite putanju do skladista na koje zelite da se povezete: \n(ukoliko ne postoji,kreira se novo skladiste) ");
		path = scanner.nextLine();
		
		try {
			dropbox.create(path, "");
		} catch (CreateDirectoryExceptions e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}



	@Override
	public void disconnectFromStorage() {
		// TODO Auto-generated method stub
		
	}




	
	

}
