package org.vpac.grisu.webclient.client.files;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.map.HashedMap;
import org.vpac.grisu.webclient.client.GrisuClientService;
import org.vpac.grisu.webclient.client.GrisuClientServiceAsync;

import gwtupload.client.IUploader;
import gwtupload.client.MultiUploader;
import gwtupload.client.PreloadedImage;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader.OnStartUploaderHandler;
import gwtupload.client.IUploader.Utils;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.sun.xml.messaging.saaj.soap.GifDataContentHandler;

public class FileUploadPanel extends LayoutContainer {
	private MultiUploader multiUploader = null;
	private HashMap<String, GrisuFileObject> uploadedFilesMap;
	private String pathToUploadTo;
	
	

	public FileUploadPanel() {

		uploadedFilesMap = new HashMap<String, GrisuFileObject>();
		setLayout(new RowLayout(Orientation.VERTICAL));
		add(getMultiUploader(), new RowData(1.0, 1.0, new Margins()));
		// add(getFileUploadModule(), new RowData(1.0, 1.0, new Margins()));
		multiUploader.setEnabled(true);
		pathToUploadTo = null;
		getMultiUploader().addOnFinishUploadHandler(onFinishUploaderHandler);
		multiUploader.addOnStartUploadHandler(onStartUploaderHandler);
		
	}
	
	
	public FileUploadPanel(String pathToUploadTo)
	{
		this();
		this.pathToUploadTo = pathToUploadTo;
		
		
	}

	
	private MultiUploader getMultiUploader() {
		if (multiUploader == null) {
			multiUploader = new MultiUploader();
			
		}
		return multiUploader;
	}

	private OnStartUploaderHandler onStartUploaderHandler = new OnStartUploaderHandler() {
		
		public void onStart(IUploader uploader) {
			
			
		}
	};
	// Load the image in the document and in the case of success attach it to
	// the viewer
	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
		public void onFinish(IUploader uploader) {
			if (uploader.getStatus() == Status.SUCCESS) {
				// The server can send information to the client.

				
				// You can parse this information using XML or JSON libraries
				Document doc = XMLParser.parse(uploader.getServerResponse());
				GWT.log("Response Document "  + doc);
				
				String size = Utils.getXmlNodeValue(doc, "size");
				String type = Utils.getXmlNodeValue(doc, "type");
				String fileName = Utils.getXmlNodeValue(doc, "name");
				
				GWT.log("File Uploaded " + fileName + " URL :" + uploader.fileUrl()  );
				
			
				GrisuFileObject gfo = new GrisuFileObject(fileName, uploader.fileUrl(),
						GrisuFileObject.FILETYPE_FILE, Long.valueOf(size), new Date());
				uploadedFilesMap.put(uploader.fileUrl(), gfo);
				if(pathToUploadTo != null)
				{
					GrisuClientService.Util.getInstance().uploadFileToGrid(gfo,pathToUploadTo , true, new AsyncCallback<Boolean>() 
							{
						
						public void onSuccess(Boolean arg0) {
							
							if(arg0)
							{
							Info.display("Success ", "SuccessFully Uploaded Your Files to the Grid");
							}
							else
							{
							Info.display("UnSuccessful ", "Please contact Grid Admins");
							GWT.log("Error with the Actual upload");
							}
						}
						
						public void onFailure(Throwable arg0) {
							// TODO Auto-generated method stub
							Info.display("Failure ", "Error = " + arg0 +" Please try again later");
							GWT.log("Error with async Tryed uploading files to the Grid. Error message = " + arg0 );
						}
					});
				}
				
			}
			else if (uploader.getStatus() == Status.DELETED)
			{
				uploadedFilesMap.remove(uploader.fileUrl());
				GWT.log("Removing Uploaded File :" + uploader.fileUrl());
			}
		}
	};

	/**
	 * 
	 * @return List of all the uploaded files
	 */
	public List<GrisuFileObject> getSelectedItems() {

		List<GrisuFileObject> grisuFileObjectList = new LinkedList<GrisuFileObject>();
		
		for(GrisuFileObject gfo : uploadedFilesMap.values() )
		{
			GWT.log("getting the file" + gfo.getUrl());
			grisuFileObjectList.add(gfo);
		}

		return grisuFileObjectList;

	}


	
}
