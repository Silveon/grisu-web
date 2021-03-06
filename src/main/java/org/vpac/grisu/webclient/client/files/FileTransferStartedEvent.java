package org.vpac.grisu.webclient.client.files;

import java.util.List;

import org.vpac.grisu.webclient.client.GrisuClientService;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class FileTransferStartedEvent extends GwtEvent<FileTransferStartedEvent.Handler> {
	
    /**
     * Interface to describe this event. Handlers must implement.
     */
    public interface Handler extends EventHandler {
            public void onFileTransferStarted(FileTransferStartedEvent e);
    }

    public static final GwtEvent.Type<FileTransferStartedEvent.Handler> TYPE = new GwtEvent.Type<FileTransferStartedEvent.Handler>();

    @Override
	protected void dispatch(Handler handler) {
		handler.onFileTransferStarted(this);
		
	}
	
    @Override
    public GwtEvent.Type<FileTransferStartedEvent.Handler> getAssociatedType() {
            return TYPE;
    }
    
    private FileTransferObject fto;
    
    public FileTransferStartedEvent() {
    }
    
    public FileTransferStartedEvent(FileTransferObject fto) {
    	this.fto = fto;
    }

	public List<GrisuFileObject> getSources() {
		return fto.getSources();
	}

	public GrisuFileObject getTarget() {
		return fto.getTarget();
	}

	public void setFileTransferObject(FileTransferObject fto) {
		this.fto = fto;
	}
	
	public FileTransferObject getFileTransferObject() {
		return this.fto;
	}
	

}
