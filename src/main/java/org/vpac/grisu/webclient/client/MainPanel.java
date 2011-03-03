package org.vpac.grisu.webclient.client;

import java.util.List;
import java.util.Map;


import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.store.TreeStoreEvent;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.Style;
import com.google.gwt.user.client.ui.Label;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.extjs.gxt.ui.client.widget.menu.MenuBar;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;

public class MainPanel extends LayoutContainer {
	private ContentPanel contentPanel;
	private CenterPanel centerPanel;
	private Label lblBasic;
	private Label lblJoblist;
	private Label lblNewJob;
	private Label lblMonitoring;
	private Label lblFiles;
	private Label label;
	private ToolBar toolBar;
	private Button btnLogout;
	private SeparatorToolItem separatorToolItem;
	private LabelToolItem lbltltmNewLabeltoolitem;
	private LayoutContainer northContentLayout;

	public MainPanel() {
		setWidth("960");
		setLayout(new BorderLayout());
		getContentPanel().setLayout(new RowLayout(Orientation.VERTICAL));
		BorderLayoutData borderLayoutData = new BorderLayoutData(LayoutRegion.WEST);
		borderLayoutData.setMargins(new Margins(10, 10, 10, 10));
		add(getContentPanel(), borderLayoutData);
		BorderLayoutData borderLayoutData_1 = new BorderLayoutData(LayoutRegion.CENTER);
		borderLayoutData_1.setMargins(new Margins(10, 10, 10, 10));
		add(getCenterPanel(), borderLayoutData_1);
		add(getNorthContenLayout(),new BorderLayoutData(LayoutRegion.NORTH, 30.0f));

	}

	private LayoutContainer getNorthContenLayout()
	{
		if(northContentLayout == null)
		{
			northContentLayout = new LayoutContainer();
			northContentLayout.setLayout(new FitLayout());
			northContentLayout.add(getToolBar());
			
		}
		return northContentLayout;
	}

	private ContentPanel getContentPanel() {
		if (contentPanel == null) {
			contentPanel = new ContentPanel();
			contentPanel.setHideCollapseTool(true);
			contentPanel.setHeading("Navigation");
			contentPanel.setCollapsible(true);
			contentPanel.add(getLblNewJob(), new RowData(Style.DEFAULT, Style.DEFAULT, new Margins(10, 0, 5, 0)));
			contentPanel.add(getLblBasic(), new RowData(Style.DEFAULT, Style.DEFAULT, new Margins(0, 0, 0, 0)));
			contentPanel.add(getLblMonitoring(), new RowData(Style.DEFAULT, Style.DEFAULT, new Margins(10, 0, 5, 0)));
			contentPanel.add(getLblJoblist(), new RowData(Style.DEFAULT, Style.DEFAULT, new Margins(0, 0, 0, 0)));
			contentPanel.add(getLblFiles(), new RowData(Style.DEFAULT, Style.DEFAULT, new Margins(10, 0, 5, 0)));
			contentPanel.add(getLabel());

		}
		return contentPanel;
	}
	private CenterPanel getCenterPanel() {
		if (centerPanel == null) {
			centerPanel = new CenterPanel();
		}
		return centerPanel;
	}

	
	private Folder navigationMenu() {
		
		Folder[] folders = new Folder[] {
				new Folder( "Create jobs", 
						new Node[]{
							new Node("Basic job"),
							new Node("Advanced job")
						}),
				new Folder( "Monitor jobs", 
						new Node[]{
							new Node("Job list")
						}),
				new Folder( "Files", 
						new Node[]{
							new Node("File manager")
						})
			};
		
		Folder root = new Folder("root");
	    for (int i = 0; i < folders.length; i++) {
	      root.add((Folder) folders[i]);
	    }
		
		return root;
		
	}
	private Label getLblBasic() {
		if (lblBasic == null) {
			lblBasic = new Label("Basic");
			lblBasic.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent arg0) {
					getCenterPanel().setVisiblePanel(CenterPanel.JOBCREATION);
				}
			});
			lblBasic.setStyleName("navigationLink");
		}
		return lblBasic;
	}
	private Label getLblJoblist() {
		if (lblJoblist == null) {
			lblJoblist = new Label("Joblist");
			lblJoblist.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent arg0) {
					getCenterPanel().setVisiblePanel(CenterPanel.JOBLIST);
				}
			});
			lblJoblist.setStyleName("navigationLink");
		}
		return lblJoblist;
	}
	private Label getLblNewJob() {
		if (lblNewJob == null) {
			lblNewJob = new Label("New job");
			lblNewJob.setStyleName("navigationFolder");
		}
		return lblNewJob;
	}
	private Label getLblMonitoring() {
		if (lblMonitoring == null) {
			lblMonitoring = new Label("Monitoring");
			lblMonitoring.setStyleName("navigationFolder");
		}
		return lblMonitoring;
	}
	private Label getLblFiles() {
		if (lblFiles == null) {
			lblFiles = new Label("Files");
			lblFiles.setStyleName("navigationFolder");
		}
		return lblFiles;
	}
	private Label getLabel() {
		if (label == null) {
			label = new Label("File management");
			label.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent arg0) {
					getCenterPanel().setVisiblePanel(CenterPanel.FILEMANAGER);
				}
			});
			label.setStyleName("navigationLink");
		}
		return label;
	}
	private ToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new ToolBar();
			toolBar.setAlignment(Style.HorizontalAlignment.RIGHT);
			toolBar.setSize("900", "50");
			toolBar.setShadow(true);
		//	toolBar.setLayoutData(new FitLayout());
			toolBar.add(getLbltltmNewLabeltoolitem());
			toolBar.add(getSeparatorToolItem());
			toolBar.add(getBtnLogout());
			
		}
		return toolBar;
	}
	private Button getBtnLogout() {
		if (btnLogout == null) {
			btnLogout = new Button("Logout");
			btnLogout.addSelectionListener(new SelectionListener<ButtonEvent>() {
				
				@Override
				public void componentSelected(ButtonEvent ce) {
					GrisuClientService.Util.getInstance().logout(new AsyncCallback<String>() {
						
						public void onSuccess(String url) {
							redirect(url);
							
						}
						
						public void onFailure(Throwable arg0) {
							// TODO Auto-generated method stub
							GWT.log("Error Loging Out");
						}
					});
					
				}
			});
		}
		return btnLogout;
	}
	native void redirect(String url) 
	/*-{ 
	        $wnd.location.replace(url); 
	}-*/; 
	private SeparatorToolItem getSeparatorToolItem() {
		if (separatorToolItem == null) {
			separatorToolItem = new SeparatorToolItem();
		}
		return separatorToolItem;
	}
	private LabelToolItem getLbltltmNewLabeltoolitem() {
		if (lbltltmNewLabeltoolitem == null) {
			lbltltmNewLabeltoolitem = new LabelToolItem();
			GrisuClientService.Util.getInstance().getCN(new AsyncCallback<String>() {

				public void onFailure(Throwable arg0) {
					Info.display("Error","Couldn't get Display Name From Grisu Service Interface");
					
				}

				public void onSuccess(String arg0) {
					lbltltmNewLabeltoolitem.setLabel(arg0);
					
				}
			});
			
		}
		return lbltltmNewLabeltoolitem;
	}
	
	
}
