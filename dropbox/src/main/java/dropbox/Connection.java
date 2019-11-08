package dropbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderContinueErrorException;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;

import exceptions.directory.CreateDirectoryExceptions;
import exceptions.directory.SearchDirectoryExceptions;
import exceptions.file.DownloadFileExeption;
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
	private User userLoggedin;
	
   	public MyDirectory dropbox = new MyDirectory();
	public MyFile dropboxFile = new MyFile();
	
	
	public ArrayList<String> storageList(String path){
		ArrayList<String> listaStorage = new ArrayList<>();
		 ListFolderResult result = null;
			try {
				result = dropbox.getClient().files().listFolder(path);
			} catch (ListFolderErrorException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DbxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		     while (true) {
		         for (Metadata metadata : result.getEntries()) {
		       	  if(metadata instanceof FolderMetadata) {
		                 System.out.println(metadata.getPathLower());
		                 listaStorage.add(metadata.getPathLower());
		             }
		         }

		         if (!result.getHasMore()) {
		             break;
		         }
		        
		         try {
					result = dropbox.getClient().files().listFolderContinue(result.getCursor());
				} catch (ListFolderContinueErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DbxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		     }
		return listaStorage;
	}
	
	@Override
	public void createNewStorage(String path) {
		// napravi folder
		currentPath.setPath(path);
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
		this.userLoggedin = korisnik;
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
		return this.currentPath;
	}

	@Override
	public UserDatabase getUsers() {

		return users;
	}

	@Override
	public ExtensionList getExtension() {
		// TODO Auto-generated method stub
		return extensions;
	}

	@Override
	public User getLogin() {
		// TODO Auto-generated method stub
		return userLoggedin;
	}



	@Override
	public void connectToStorage() {
		Scanner scanner = new Scanner(System.in);
		String path="";
//		try {
//			System.out.println("Dostupna skladista na dropboxu:");
//			//dropbox.listAllinDirectoryInDirectory("");
//		} catch (SearchDirectoryExceptions e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
		
		

		
		ArrayList<String> lista = storageList(path);
		//System.out.println(lista.toString());
		System.out.println("\nUnesite putanju do skladista na koje zelite da se povezete: \n(ukoliko ne postoji,kreira se novo skladiste) ");
		path = scanner.nextLine();
		
		if(lista.contains(path)) {
			try {
				dropboxFile.download(path+"/users.json", "users.json");
				dropboxFile.download(path+"/extensions.json", "extensions.json");
			} catch (DownloadFileExeption e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			createNewStorage(path);
			return;
		}
		
		HandleUsers handlerUsr = new HandleUsers();
		users = handlerUsr.readFromUserDatabase("users.json");
		
		ExtensionHandler handleExt = new ExtensionHandler();
		extensions = handleExt.readExtensions("extensions.json");
		
		System.out.println(users.getUsers());
		if(extensions != null) {
			System.out.println(extensions.getExtensionList());
		}else {
			System.out.println("Nema ogranicenja za ekstenzije");
		}
		
		currentPath.setPath(path);
		
		 //vlada login
		Scanner scr = new Scanner(System.in);
		this.userLoggedin = null;
		//if(scr.hasNext())
			//scr.next();
		System.out.print("Login na skladiste\n");
		while(true) {
			System.out.print("Unesite username:");
			String username = scr.nextLine();
			
			System.out.print("Unesi password :");
			String password = scr.nextLine();
			
			for (int j = 0; j < this.users.getUsers().size(); j++) {
				if (users.getUsers().get(j).getUsername().equals(username)) {
					if (users.getUsers().get(j).getPassword().equals(password)) {
						userLoggedin = users.getUsers().get(j);
					}
					
				}
			}
			//System.out.println(username);
			if(this.userLoggedin == null) {
				System.out.println("Korisnik nije pronadjen, pokusajte ponovo");
			}else {
				System.out.println("Korisnik "+ this.userLoggedin.getUsername() +" uspesno ulogovan.");
				break;
			}
		}
	}



	@Override
	public void disconnectFromStorage() {
		try {
			dropboxFile.upload("users.json",getMyPath().getPath()+"/users.json",null);
		} catch (UploadFileExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			dropboxFile.upload("users.json",getMyPath().getPath()+"/users.json",null);
		} catch (UploadFileExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}




	
	

}
