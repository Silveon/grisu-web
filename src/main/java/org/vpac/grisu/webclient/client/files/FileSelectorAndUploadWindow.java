package org.vpac.grisu.webclient.client.files;

import java.util.ArrayList;
import java.util.List;

import org.vpac.grisu.webclient.client.files.FileUploadPanel.UploadFilePanelType;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class FileSelectorAndUploadWindow extends Window implements
		ValueChangeHandler<GrisuFileObject>,
		HasValueChangeHandlers<List<GrisuFileObject>> {
	private TabPanel tabPanel;
	private TabItem tbtmSelectFile;
	private Button addFileButton;
	private Button closeButton;
	private Button addUploadedFileButton;
	private Button uploadCloseButton;
	
	private ContentPanel selectFileContentPanel;
	private ContentPanel uploadFileContentPanel;
 	private FileListPanel fileListPanel;

	// used to get the last used directory;
	private String fileListName = null;

	SelectionListener<ButtonEvent> l = new SelectionListener<ButtonEvent>() {

		@Override
		public void componentSelected(ButtonEvent ce) {

			fireSelectedFileEvent(getFileListPanel_1().getSelectedItems());

		}
	};

	SelectionListener<ButtonEvent> uploaderButtonListener = new SelectionListener<ButtonEvent>() {

		@Override
		public void componentSelected(ButtonEvent ce) {

			
			fireSelectedFileEvent(getFileUploadPanel().getSelectedItems());

		}
	};
	private TabItem tbtmUploadFile;
	private FileUploadPanel fileUploadPanel;
	

	public FileSelectorAndUploadWindow(String optionalFileListName) {
		this.fileListName = optionalFileListName;
		setHeading("File selection");
		setLayout(new FitLayout());
		setSize(500, 300);
		setPlain(true);
		setModal(true);
		setBlinkModal(true);
		add(getTabPanel());
	}

	private TabPanel getTabPanel() {
		if (tabPanel == null) {
			tabPanel = new TabPanel();
			tabPanel.add(getTbtmSelectFile());
			tabPanel.add(getTbtmUploadFile());
		}
		return tabPanel;
	}

	private TabItem getTbtmSelectFile() {
		if (tbtmSelectFile == null) {
			tbtmSelectFile = new TabItem("Select file");
			tbtmSelectFile.setLayout(new FitLayout());
			tbtmSelectFile.add(getContentPanel());
		}
		return tbtmSelectFile;
	}

	private Button getAddFileButton() {

		if (addFileButton == null) {
			addFileButton = new Button("Add", l);
		}
		return addFileButton;
	}

	private Button getCloseButton() {

		if (closeButton == null) {
			closeButton = new Button("Close",l);
			closeButton.addListener(Events.Select, new Listener<BaseEvent>() {

				public void handleEvent(BaseEvent be) {

					close();
				}
			});
		}
		return closeButton;
	}

	private ContentPanel getContentPanel() {
		if (selectFileContentPanel == null) {
			selectFileContentPanel = new ContentPanel();
			selectFileContentPanel.setHeaderVisible(false);
			selectFileContentPanel.setBodyBorder(false);
			selectFileContentPanel.setLayout(new FitLayout());
			selectFileContentPanel.add(getFileListPanel_1());
			selectFileContentPanel.setCollapsible(true);
			selectFileContentPanel.addButton(getAddFileButton());
			selectFileContentPanel.addButton(getCloseButton());
		}
		return selectFileContentPanel;
	}

	private FileListPanel getFileListPanel_1() {
		if (fileListPanel == null) {
			fileListPanel = new FileListPanel(null, fileListName);
			fileListPanel.addValueChangeHandler(this);
		}
		return fileListPanel;
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
		ValueChangeEvent.fire(this, result);

	}

	public void onValueChange(ValueChangeEvent<GrisuFileObject> arg0) {

		List<GrisuFileObject> list = new ArrayList<GrisuFileObject>();
		list.add(arg0.getValue());
		fireSelectedFileEvent(list);

	}

	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<List<GrisuFileObject>> arg0) {

		return addHandler(arg0, ValueChangeEvent.getType());
	}

	private TabItem getTbtmUploadFile() {
		if (tbtmUploadFile == null) {
			tbtmUploadFile = new TabItem("Upload file");
			tbtmUploadFile.setLayout(new FitLayout());
			tbtmUploadFile.add(getUploadContentPanel());
		
			
			
		}
		return tbtmUploadFile;
	}
	
	private ContentPanel getUploadContentPanel() {
		if (uploadFileContentPanel == null) {
			uploadFileContentPanel = new ContentPanel();
			uploadFileContentPanel.setHeaderVisible(false);
			uploadFileContentPanel.setBodyBorder(false);
			uploadFileContentPanel.setLayout(new FitLayout());
			uploadFileContentPanel.add(getFileUploadPanel());
			uploadFileContentPanel.setCollapsible(true);
			uploadFileContentPanel.addButton(getAddUplodedFileButton());
			uploadFileContentPanel.addButton(getUploadCloseButton());
		}
		return uploadFileContentPanel;
	}

	private FileUploadPanel getFileUploadPanel() {
		if (fileUploadPanel == null) {
			fileUploadPanel = new FileUploadPanel(UploadFilePanelType.JOB_SUBMISSION_UPLOAD);
			fileUploadPanel.setSize("482px", "202px");
			
		}
		return fileUploadPanel;
	}

	
	public Button getUploadCloseButton()
	{
		
		if(uploadCloseButton == null)
		{
			uploadCloseButton = new Button("Close",uploaderButtonListener);
			uploadCloseButton.addListener(Events.Select, new Listener<BaseEvent>() {

				public void handleEvent(BaseEvent be) {
				
					close();
					
				}
			});
		}
		return uploadCloseButton;
	}
	public Button getAddUplodedFileButton() {

		if (addUploadedFileButton == null) {
		addUploadedFileButton = new Button("Add Files", uploaderButtonListener);
	}
		return addUploadedFileButton;
	}

}
