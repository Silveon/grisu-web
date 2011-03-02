package org.vpac.grisu.webclient.client.files;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.vpac.grisu.webclient.client.EventBus;
import org.vpac.grisu.webclient.client.GrisuClientService;
import org.vpac.grisu.webclient.server.GrisuClientServiceImpl;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class FileUploadObject extends FileTransferObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileUploadObject(List<GrisuFileObject> sources,
			GrisuFileObject target) {
		super(sources, target);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean startTransfer()
	{
		if ( ! GrisuFileObject.FILETYPE_FOLDER.equals(target.getFileType())
				&& ! GrisuFileObject.FILETYPE_MOUNTPOINT.equals(target.getFileType()) ) {
			return false;
		}

		
		for ( GrisuFileObject file : sources ) {

			if ( ! GrisuFileObject.FILETYPE_FILE.equals(file.getFileType())
					&& ! GrisuFileObject.FILETYPE_FOLDER.equals(file.getFileType()) ) {
				return false;
			}

		
		}
		

		GrisuClientService.Util.getInstance().uploadFilesToGrid(sources, target.getUrl(), true, new AsyncCallback<HashMap<String,String>>() {

			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub
				
			}


			public void onSuccess(HashMap<String, String> arg0) {
				// TODO Auto-generated method stub
			//	handle = arg0;
				started = true;
				set(STARTED_KEY, true);
			}
		
		
		
		});


		

		FileTransferStartedEvent startEvent = new FileTransferStartedEvent(this);
		
		EventBus.get().fireEvent(startEvent);

		return true;
	}
	
	
	

}
