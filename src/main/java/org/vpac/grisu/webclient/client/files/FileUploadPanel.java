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

public class FileUploadPanel extends LayoutContainer {
	private MultiUploader multiUploader = null;
	private HashMap<String, GrisuFileObject> uploadedFilesMap;
	private Button button;
	public FileUploadPanel() {

		uploadedFilesMap = new HashMap<String, GrisuFileObject>();
		setLayout(new RowLayout(Orientation.VERTICAL));
		add(getMultiUploader(), new RowData(1.0, 1.0, new Margins()));
		// add(getFileUploadModule(), new RowData(1.0, 1.0, new Margins()));
		multiUploader.setEnabled(true);
		getMultiUploader().addOnFinishUploadHandler(onFinishUploaderHandler);
	}

	public FileUploadPanel(Button button)
	{
		this();
		this.button = button;
		
		this.add(button);
		
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
				String size = Utils.getXmlNodeValue(doc, "file-1-size");
				String type = Utils.getXmlNodeValue(doc, "file-1-type");
				String fileName = Utils.getXmlNodeValue(doc, "file-1-name");
				String fileURL = Utils.getXmlNodeValue(doc, "file-1-URL");
				System.out.println(size + " " + type);
				GWT.log("file URL " + fileURL + "fileName " + fileName);
			
				GrisuFileObject gfo = new GrisuFileObject(fileName, fileURL,
						type, Long.valueOf(size), new Date());
				uploadedFilesMap.put(fileName, gfo);
			}
		}
	};

	public List<GrisuFileObject> getSelectedItems() {

		List<GrisuFileObject> gfol = (List<GrisuFileObject>) uploadedFilesMap
				.values();

		return gfol;

	}

}
