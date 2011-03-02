package org.vpac.grisu.webclient.client.files;

import java.util.ArrayList;
import java.util.List;

import org.vpac.grisu.webclient.client.files.FileUploadPanel.UploadFilePanelType;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

public class FileUploadSelectorGridPanel extends LayoutContainer
{
	  private List<ColumnConfig> configs;
	     
	   private ColumnConfig column;   
	   private ListStore<GrisuFileObject> filesList;
	   private Grid<GrisuFileObject> grid;
	  
	
	
	public FileUploadSelectorGridPanel() {
		
		
		
		
		filesList = new ListStore<GrisuFileObject>();
		configs = new ArrayList<ColumnConfig>();
		
		column = new ColumnConfig();

		   column = new ColumnConfig(GrisuFileObject.FILENAME, "File Name", 150);    
		   column.setAlignment(HorizontalAlignment.LEFT);    
		   configs.add(column);  
		    
		 column = new ColumnConfig(GrisuFileObject.FILESIZE, "Size", 150);    
		  column.setAlignment(HorizontalAlignment.LEFT);    
		  configs.add(column);  
		     
		  
		  ColumnModel columnModel = new ColumnModel(configs);
		  grid = new Grid<GrisuFileObject>(filesList,columnModel);
		  grid.setStyleAttribute("borderTop", "none");   
		  grid.setAutoExpandColumn("fileName");
		  
		  grid.setBorders(true);   
		  grid.setStripeRows(true);  
		  add(grid);

		  
		
		  
	}
	
	
	
	public List<GrisuFileObject> getSelectedItems()
	{
		return grid.getSelectionModel().getSelectedItems();
	}
	
	
	public void addFileToList(GrisuFileObject gfo)
	{
		filesList.add(gfo);
	}
	
	
	public void removeFileFromList(GrisuFileObject gfo)
	{
		filesList.remove(gfo);
	}



	public List<GrisuFileObject> getList() {
		return filesList.getModels();
	}



//	public void onUploadComplete(UploadFileCompleteEvent event) {
//		
//		
//		filesList.add(event.getFile());
//		
//	}
	
}
