package org.vpac.grisu.webclient.client.files;


import gwtupload.client.IUploader;
import gwtupload.client.MultiUploader;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader.Utils;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

public class FileUploadPanel extends LayoutContainer {
	private MultiUploader multiUploader = null;

	

	public FileUploadPanel() {
		setLayout(new RowLayout(Orientation.VERTICAL));
		add(getMultiUploader(), new RowData(1.0, 1.0, new Margins()));
		//add(getFileUploadModule(), new RowData(1.0, 1.0, new Margins()));
		
		Button button = new Button("Submit");
		button.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				// TODO Auo-generated method stub
				getMultiUploader().submit();
			}
		});
		add(button);
		
		getMultiUploader().addOnFinishUploadHandler(onFinishUploaderHandler);
	}
	private MultiUploader getMultiUploader() {
		if (multiUploader == null) {
			multiUploader = new MultiUploader();
		}
		return multiUploader;
	}
	
	// Load the image in the document and in the case of success attach it to the viewer
	  private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
	    public void onFinish(IUploader uploader) {
	      if (uploader.getStatus() == Status.SUCCESS) {	        
	        // The server can send information to the client.
	        // You can parse this information using XML or JSON libraries
	        Document doc = XMLParser.parse(uploader.getServerResponse());
	        String size = Utils.getXmlNodeValue(doc, "file-1-size");
	        String type = Utils.getXmlNodeValue(doc, "file-1-type");
	        System.out.println(size + " " + type);
	      }
	    }
	  };


}
