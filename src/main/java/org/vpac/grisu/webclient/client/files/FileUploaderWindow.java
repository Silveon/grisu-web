package org.vpac.grisu.webclient.client.files;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Widget;


public class FileUploaderWindow extends Window  implements
		ValueChangeHandler<GrisuFileObject>, HasValueChangeHandlers<List<GrisuFileObject>> {

	
	private TabPanel tabPanel;
	private TabItem tbtmUploadFile;
	private ContentPanel uploadFileContentPanel;
	private FileUploadPanel fileUploadPanel;
	private Button addUploadedFileButton;
	
	
	
	private SelectionListener<ButtonEvent> uploaderButtonListener = new SelectionListener<ButtonEvent>() {

		@Override
		public void componentSelected(ButtonEvent ce) {

			
			fireSelectedFileEvent(getFileUploadPanel().getSelectedItems());

		}

	
	};
	
	private static FileUploaderWindow fileUploaderWindow = null;
	private String targetURL;

	public FileUploaderWindow()
	{
		
		setHeading("Select files To Upload to the Grid");
		setLayout(new FitLayout());
		setSize(500, 300);
		setPlain(true);
		setModal(true);
		setBlinkModal(true);
		add(getTabPanel());
	}
	
	
	
	
	private TabPanel getTabPanel() {
		// TODO Auto-generated method stub
		if (tabPanel == null) {
			tabPanel = new TabPanel();
			tabPanel.add(getTbtmUploadFile());
		}
		return tabPanel;
	}
	private TabItem getTbtmUploadFile() {
		if (tbtmUploadFile == null) {
			tbtmUploadFile = new TabItem("Select file");
			tbtmUploadFile.setLayout(new FitLayout());
			tbtmUploadFile.add(getContentPanel());
		}
		return tbtmUploadFile;
	}
	private ContentPanel getContentPanel() {
		if (uploadFileContentPanel == null) {
			uploadFileContentPanel = new ContentPanel();
			uploadFileContentPanel.setHeaderVisible(false);
			uploadFileContentPanel.setBodyBorder(false);
			uploadFileContentPanel.setLayout(new FitLayout());
			uploadFileContentPanel.add(getFileUploadPanel());
			uploadFileContentPanel.setCollapsible(true);
			uploadFileContentPanel.addButton(getAddUplodedFileButton());
		}
		return uploadFileContentPanel;
	}
	
	
	private FileUploadPanel getFileUploadPanel()
	{
		if (fileUploadPanel == null) {
			fileUploadPanel = new FileUploadPanel(getTargetURL());
			fileUploadPanel.setSize("482px", "202px");
			
		}
		return fileUploadPanel;
	}
	
	
	private Button getAddUplodedFileButton() 
	{
		if (addUploadedFileButton == null) {
			addUploadedFileButton = new Button("Add Files", uploaderButtonListener);
		}
			return addUploadedFileButton;
	}

	
	public void fireSelectedFileEvent(List<GrisuFileObject> files) {

		
		GWT.log("fired secleted Event Handle");
		List<GrisuFileObject> result = new ArrayList<GrisuFileObject>();
		for (GrisuFileObject file : files) {
			
			GWT.log("Iterating throu files current file"  + file.getFileName() );
			GWT.log("File type " + file.getFileType());
			if (GrisuFileObject.FILETYPE_FILE.equals(file.getFileType())
					|| GrisuFileObject.FILETYPE_FOLDER.equals(file
							.getFileType())) {
				result.add(file);
			}
		}
        
		GWT.log("Result Size is = " + result.size());
		ValueChangeEvent.fire(this,result);

	}
	public void fireEvent(GwtEvent<?> arg0) {
		// TODO Auto-generated method stub

	}

	public void onValueChange(ValueChangeEvent<GrisuFileObject> arg0) {
		// TODO Auto-generated method stub
		
	}
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<List<GrisuFileObject>> arg0) {
		// TODO Auto-generated method stub
		return null;
	}







	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}







	public String getTargetURL() {
		return targetURL;
	}
	
	

}
