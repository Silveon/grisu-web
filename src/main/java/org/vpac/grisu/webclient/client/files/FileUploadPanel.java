package org.vpac.grisu.webclient.client.files;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.map.HashedMap;
import org.vpac.grisu.webclient.client.EventBus;
import org.vpac.grisu.webclient.client.GrisuClientService;
import org.vpac.grisu.webclient.client.GrisuClientServiceAsync;

import gwtupload.client.IUploader;
import gwtupload.client.MultiUploader;
import gwtupload.client.PreloadedImage;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader.OnChangeUploaderHandler;
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
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.sun.xml.messaging.saaj.soap.GifDataContentHandler;

public class FileUploadPanel extends LayoutContainer implements HasHandlers
{
	private MultiUploader multiUploader = null;
	private HashMap<String, GrisuFileObject> uploadedFilesMap;
	private UploadFilePanelType uploadPanelType;
	
	private SimpleEventBus handleManager;
	
	
	
	

	public FileUploadPanel(UploadFilePanelType uploadPanelType) {

		uploadedFilesMap = new HashMap<String, GrisuFileObject>();
		this.uploadPanelType = uploadPanelType;
		setLayout(new RowLayout(Orientation.VERTICAL));
		add(getMultiUploader(), new RowData(1.0, 1.0, new Margins()));
		// add(getFileUploadModule(), new RowData(1.0, 1.0, new Margins()));
		multiUploader.setEnabled(true);
		
		getMultiUploader().addOnFinishUploadHandler(onFinishUploaderHandler);
		multiUploader.addOnStartUploadHandler(onStartUploaderHandler);
		
		multiUploader.addOnChangeUploadHandler(new OnChangeUploaderHandler() {
			
			public void onChange(IUploader uploader) {
				// 
				if (uploader.getStatus() == Status.DELETED)
				{
					uploadedFilesMap.remove(uploader.fileUrl());
					GWT.log("Removing Uploaded File :" + uploader.fileUrl());
				}
			}
		});
		
		
	}
	
	public void reset()
	{
		multiUploader.reset();
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
				
				String serverResponse = uploader.getServerResponse();
				serverResponse.replace("<![CDATA[", "");
				serverResponse.replace("]]", "");
				
				GWT.log("SErver Response " + serverResponse);
				Document doc = XMLParser.parse(serverResponse);
				
				GWT.log("Response Document "  + doc);
				
				String size = Utils.getXmlNodeValue(doc, "size");
				String type = Utils.getXmlNodeValue(doc, "type");
				String fileName = Utils.getXmlNodeValue(doc, "name");
				String localfileURI = Utils.getXmlNodeValue(doc,"message");
				
				
				
				GWT.log("File Uploaded " + fileName + " URL :" + localfileURI  );
				
			
				GrisuFileObject gfo = new GrisuFileObject(fileName, localfileURI,
						GrisuFileObject.FILETYPE_FILE, Long.valueOf(size), new Date());
				uploadedFilesMap.put(uploader.fileUrl(), gfo);
				
			
				
				
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





public enum UploadFilePanelType{JOB_SUBMISSION_UPLOAD,GRID_UPLOAD}
	
}
