package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.CreateFolderErrorException;
import com.dropbox.core.v2.files.CreateFolderResult;
import com.dropbox.core.v2.files.DeleteErrorException;
import com.dropbox.core.v2.files.DownloadZipResult;
import com.dropbox.core.v2.files.FileOpsResult;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderContinueErrorException;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

import exceptions.directory.CreateDirectoryExceptions;
import exceptions.directory.DeleteDirectoryExceptions;
import exceptions.directory.MoveDirectoryExceptions;
import exceptions.directory.RenameDirectoryExceptions;
import exceptions.directory.SearchDirectoryExceptions;
import exceptions.directory.ZipDirectoryExceptions;

public class DropboxDirectory implements Directory {
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
	 * Token used for establishing connection with app's directory in dropbox.
	 */
	private String ACCESS_TOKEN;

	/**
	 * Dropbox directory constructor.
	 *
	 * @param accessToken sets access token read from config file.
	 */
	public DropboxDirectory(String accessToken) {
		this.ACCESS_TOKEN = accessToken;
		initClient("remote-storage-software-component");
	}

	/**
	 * Initializing client.
	 *
	 * @param clientID
	 */
	public void initClient(String clientID) {
		this.config = new DbxRequestConfig(clientID);
		this.client = new DbxClientV2(config, ACCESS_TOKEN);

		try {
			FullAccount account = this.client.users().getCurrentAccount();
			System.out.println("Account: " + account.getName().getDisplayName());
		} catch (DbxException dbxe) {
			dbxe.printStackTrace();
		}
	}

	public DbxClientV2 getClient() {
		return client;
	}

	
	
	@Override
	public void create(String name, String path) throws CreateDirectoryExceptions {
		try {
			FileOpsResult dir = client.files().createFolderV2(name);
			//System.out.println(dir.toString());
		} catch (CreateFolderErrorException err) {
			if (err.errorValue.isPath() && err.errorValue.getPathValue().isConflict()) {
				System.out.println("Something already exists at the path.");
				System.out.println(err.errorValue.getPathValue());
			} else {
				System.out.print("Some other CreateFolderErrorException occurred...");
				System.out.print(err.toString());
			}
		} catch (Exception err) {
//			System.out.print("Some other Exception occurred...");
//			System.out.print(err.toString());
			throw new CreateDirectoryExceptions();
		}
	}
		
	

	@Override
	public void delete(String path) throws DeleteDirectoryExceptions {
		try {
			client.files().deleteV2(path);
		} catch (DeleteErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void zip(String path) throws ZipDirectoryExceptions {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(String pathFile, String destination) throws MoveDirectoryExceptions {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rename(String name, String path) throws RenameDirectoryExceptions {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<File> listAllinDirectory(String path) throws SearchDirectoryExceptions {
	     // Get files and folder metadata from Dropbox root directory
      ListFolderResult result = null;
	try {
		result = client.files().listFolder(path);
	} catch (ListFolderErrorException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (DbxException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      while (true) {
          for (Metadata metadata : result.getEntries()) {
              System.out.println(metadata.getPathLower());
          }

          if (!result.getHasMore()) {
              break;
          }
         
          try {
			result = client.files().listFolderContinue(result.getCursor());
		} catch (ListFolderContinueErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
		return null;
	}

	@Override
	public List<File> listAllinDirectoryInDirectory(String path) throws SearchDirectoryExceptions {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<File> listAllFileinDirectory(String path) throws SearchDirectoryExceptions {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<File> listAllinDirectoryAndSubdirectory(String path) throws SearchDirectoryExceptions {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<File> listAllinDirectoryWithExtension(String path, String extension) throws SearchDirectoryExceptions {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<File> listAllinDirectoryWithMetadata(String path) throws SearchDirectoryExceptions {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<File> listAllinDirectoryWithoutMetadata(String path) throws SearchDirectoryExceptions {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void download(String src, String dest) {
		try {
			DbxDownloader<DownloadZipResult> result = client.files().downloadZip(src);
			result.download(new FileOutputStream(dest));
		} catch (DbxException | IOException e) {
			e.printStackTrace();
			
		}
	}

	@Override
	public void upload(String src, String dest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uploadMultiple(List<File> directories, String dest, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uploadMultipleZip(List<File> directories, String dest, String name) {
		// TODO Auto-generated method stub
		
	}

}
