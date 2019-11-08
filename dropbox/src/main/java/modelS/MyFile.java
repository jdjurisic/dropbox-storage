package modelS;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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



public class MyFile implements model.MyFile {
	private static final String ACCESS_TOKEN = "8AcMbJiKViAAAAAAAAAADUXBL3xJ67ZwtLJa3NBEJRVooHCeoIlAWbXH1bsf6QZq";
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
	 * @param client contains access token
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createWithMetadata(String name, String path, ExtensionList extensiontList) throws CreateFileException {
		// TODO Auto-generated method stub
		
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
