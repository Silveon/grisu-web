package org.vpac.grisu.webclient.client.files;

import java.util.ArrayList;
import java.util.List;

import org.vpac.grisu.webclient.client.files.FileUploadPanel.UploadFilePanelType;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FileUpload;



public class FileUploaderWindow extends Window  {

	
	private TabPanel tabPanel;
	private TabItem tbtmUploadFile;
	private TabItem tbtmRedyFiles;
	private ContentPanel uploadFileContentPanel;
	private ContentPanel uploadRedyToBeSentContentPanel;
	private FileUploadPanel fileUploadPanel;
	private Button beginTransferButton;
	private Button hideButton;
	
	
	private SelectionListener<ButtonEvent> uploaderButtonListener = new SelectionListener<ButtonEvent>() {

		@Override
		public void componentSelected(ButtonEvent ce) {

			beginTransferToGrid();
		}

	
	};
	
	
	private String targetURL;
	private FileUploadSelectorGridPanel fileUploadSelectorPanel;
	private FileUpload fileUpload;
	private FormPanel formPanel;

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
			tbtmUploadFile = new TabItem("Upload Files");
			tbtmUploadFile.setLayout(new FitLayout());
			tbtmUploadFile.add(getUploadFileContentPanel());
		}
		return tbtmUploadFile;
	}

	private ContentPanel getUploadFileContentPanel() {
		if (uploadFileContentPanel == null) {
			uploadFileContentPanel = new ContentPanel();
			uploadFileContentPanel.setHeaderVisible(false);
			uploadFileContentPanel.setBodyBorder(false);
			uploadFileContentPanel.setLayout(new FitLayout());
			uploadFileContentPanel.add(getFileuploadPanel());
			uploadFileContentPanel.setCollapsible(true);
			
			uploadFileContentPanel.addButton(getBeginTransferButton());
			
		}
		return uploadFileContentPanel;
	}
	
	


 

private Button getHidebutton() {
	
	if (hideButton == null) {
		hideButton = new Button("Upload to Grid" , new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				
				
			}
		});
	}
		return hideButton;
	}




//	private FileUploadPanel getFileUploadPanel()
//	{
//		if (fileUploadPanel == null) {
//			fileUploadPanel = new FileUploadPanel(UploadFilePanelType.GRID_UPLOAD);
//			fileUploadPanel.setSize("482px","202px");
//			
//		}
//		
//		fileUploadPanel.setPathToUploadTo(getTargetURL());
//		setHeading("Select file to Upload to" + getTargetURL());
//		
//		
//		return fileUploadPanel;
//	}
//	
	
	



	private Button getBeginTransferButton() 
	{
		if (beginTransferButton == null) {
			beginTransferButton = new Button("Upload to Grid", uploaderButtonListener);
		}
			return beginTransferButton;
	}

	
	
	public void fireEvent(GwtEvent<?> arg0) {
		// TODO Auto-generated method stub

	}

	public void onValueChange(ValueChangeEvent<List<GrisuFileObject>> arg0) {
		
		
	}
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<List<GrisuFileObject>> arg0) {
		// TODO Auto-generated method stub
		return null;
	}




	public void beginTransferToGrid()
	{
		
			FileTransferObject fto = new FileTransferObject(getFileuploadPanel().getSelectedItems(),new GrisuFileObject(targetURL,targetURL,GrisuFileObject.FILETYPE_FOLDER,new Long(-1),null ));
			if(fto.startTransfer())
			{
				Info.display("Beginging Transfer ", "Transfer to grid has successfully started");
			}
			else
			{
				Info.display("Error ", "Transfer to grid has not started");
			}
	}



	public void setTargetURL(String targetURL) {
		this.targetURL = targetURL;
	}







	public String getTargetURL() {
		return targetURL;
	}
	
	
	
	
	private FileUploadPanel getFileuploadPanel()
	{
		if(fileUploadPanel == null)
		{
			fileUploadPanel = new FileUploadPanel(UploadFilePanelType.GRID_UPLOAD);
			fileUploadPanel.setSize("482px","50px");
			
			fileUploadPanel.setScrollMode(Scroll.AUTO);
			
		}
		
		return fileUploadPanel;
	}
	
	

	

}
