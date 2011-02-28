package org.vpac.grisu.webclient.client.files;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.map.HashedMap;

import gwtupload.client.IUploader;
import gwtupload.client.MultiUploader;
import gwtupload.client.PreloadedImage;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader.Utils;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.sun.xml.messaging.saaj.soap.GifDataContentHandler;

public class FileUploadPanel extends LayoutContainer {
	private MultiUploader multiUploader = null;
	private HashMap<String, GrisuFileObject> uploadedFilesMap;

	public FileUploadPanel() {

		uploadedFilesMap = new HashMap<String, GrisuFileObject>();
		setLayout(new RowLayout(Orientation.VERTICAL));
		add(getMultiUploader(), new RowData(1.0, 1.0, new Margins()));
		// add(getFileUploadModule(), new RowData(1.0, 1.0, new Margins()));
		multiUploader.setEnabled(true);
		getMultiUploader().addOnFinishUploadHandler(onFinishUploaderHandler);
	}

	
	private MultiUploader getMultiUploader() {
		if (multiUploader == null) {
			multiUploader = new MultiUploader();
		}
		return multiUploader;
	}

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
