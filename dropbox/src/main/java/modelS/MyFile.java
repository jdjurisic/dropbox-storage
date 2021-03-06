package modelS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DeleteResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadUploader;
import com.dropbox.core.v2.users.FullAccount;

import exceptions.file.CreateFileException;
import exceptions.file.DeleteFileExeption;
import exceptions.file.DownloadFileExeption;
import exceptions.file.MoveFileExeption;
import exceptions.file.RenameFileExeption;
import exceptions.file.SelectFileExeption;
import exceptions.file.UploadFileExeption;
import exceptions.file.ZipFilesExeption;
import formatComponent.ExtensionList;
import model.MetaCreator;



public class MyFile implements model.MyFile {
	private static final String ACCESS_TOKEN = "8AcMbJiKViAAAAAAAAAAHP2ZlQkD9e5Wgsiv1rBVoM7anm9RbEXR-Do0W8YI9V5E";
	/**
	 * A grouping of a few configuration parameters for how we should make requests to the Dropbox servers.
	 */
	private DbxRequestConfig config = null;
	/**
	 * Use this variable to make remote calls to the Dropbox API user endpoints.
	 */
	private DbxClientV2 client = null;
	/**
	 * Detailed information about the current user's account.
	 */
	private FullAccount account = null;
	/**
	 * Use this variable to make remote calls to the Dropbox API user endpoints.
	 */


	/**
	 * Dropbox file constructor
	 */
	public MyFile() {
		initClient("remote-storage-software-component");
	}


	public void initClient(String clientID) {
		this.config = new DbxRequestConfig(clientID);
		this.client = new DbxClientV2(config, ACCESS_TOKEN);

		try {
			FullAccount account = this.client.users().getCurrentAccount();
			//System.out.println("Account: " + account.getName().getDisplayName());
		} catch (DbxException dbxe) {
			dbxe.printStackTrace();
		}
	}

	public DbxClientV2 getClient() {
		return client;
	}


	public void delete(String path) throws DeleteFileExeption {
		try {
			DeleteResult metadata = client.files().deleteV2(path);
		} catch (DbxException dbxe) {
			dbxe.printStackTrace();
			throw new DeleteFileExeption();
		}
	}

	@Override
	public void download(String pathStorage, String pathDesktop) throws DownloadFileExeption {
		try {
			OutputStream downloadFile = new FileOutputStream(pathDesktop);

			try {
				FileMetadata metadata = client.files()
						.downloadBuilder(pathStorage)
						.download(downloadFile);
			} finally {
				downloadFile.close();
			}
		} catch (DbxException | IOException e) {
			System.out.println("Unable to download file to local system\n Error: " + e);
			throw new DownloadFileExeption();
		}
	}




	@Override
	public void zip(String path) throws ZipFilesExeption {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(String pathFile, String destination) throws MoveFileExeption {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveWithMetadata(String pathFile, String destination) throws MoveFileExeption {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rename(String name, String path) throws RenameFileExeption {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(String name, String path, ExtensionList extensiontList) throws CreateFileException {
		if (name.equals("") || (name.indexOf('.') == -1)) {
			System.out.println("Ime fajla nije dobro!!!");
			return;
		}

		String extension = name.substring(name.indexOf('.') + 1);// ovo da se ukloni tacka

		//System.out.println(extension);
		for (int i = 0; i < extensiontList.getExtensionList().size(); i++) {
			if (extensiontList.getExtensionList().get(i).equals(extension)) {
				System.out.println("Fajl sa ovom ekstenzijom se ne moze napraviti");
				return;
			}
		}
		File f = new File(name);
		try {
			f.createNewFile();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		try {
			this.upload(name, path, null);
		} catch (UploadFileExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		
	}

	@Override
	public void createWithMetadata(String name, String path, ExtensionList extensiontList) throws CreateFileException {
		
		File f = new File(name);
		try {
			f.createNewFile();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		try {
			this.upload(name, path+"/"+name, null);
		} catch (UploadFileExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MetaCreator.createNewMetaDescription(name);
		
	System.out.println("bezEkstenzije :"+ name);
	name = name.substring(0, name.lastIndexOf("."));
	name += "-META.json";
		
		
		
		try {
			this.upload(name, path+"/"+name, null);
		} catch (UploadFileExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}


	@Override
	public void upload(String pathDesktop, String pathStorage, ExtensionList extensiontList) throws UploadFileExeption {
		try {
			InputStream in = new FileInputStream(pathDesktop);
			FileMetadata metadata = client.files().uploadBuilder(pathStorage).uploadAndFinish(in);
		} catch (IOException | DbxException ioe) {
			ioe.printStackTrace();
			throw new UploadFileExeption();
		}
	}


	@Override
	public void uploadMultiple(List<String> files, String pathStorage, ExtensionList extensiontList)
			throws UploadFileExeption {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void uploadMultipleZip(List<String> files, String destination) throws Exception {
		// TODO Auto-generated method stub
		
	}







	






}
