package modelDropbox;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DeleteResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadUploader;

import exceptions.file.CreateFileException;
import exceptions.file.DeleteFileExeption;
import exceptions.file.DownloadFileExeption;
import exceptions.file.MoveFileExeption;
import exceptions.file.RenameFileExeption;
import exceptions.file.SelectFileExeption;
import exceptions.file.UploadFileExeption;
import exceptions.file.ZipFilesExeption;
import formatComponent.ExtensionList;



public class File implements model.File {
	
	/**
	 * Use this variable to make remote calls to the Dropbox API user endpoints.
	 */
	private DbxClientV2 client = null;

	/**
	 * Dropbox file constructor
	 * @param client contains access token
	 */
	public File(DbxClientV2 client) {
		this.client = client;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectMutlipleFile(List<String> path) throws SelectFileExeption {
		// TODO Auto-generated method stub
		
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
	public void uploadMultiple(List<model.File> files, String pathStorage, ExtensionList extensiontList)
			throws UploadFileExeption {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void uploadMultipleZip(List<model.File> files, String destination) throws Exception {
		// TODO Auto-generated method stub
		
	}
		
	






}
