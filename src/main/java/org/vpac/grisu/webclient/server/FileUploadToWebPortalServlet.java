package org.vpac.grisu.webclient.server;

import grisu.control.ServiceInterface;
import grisu.model.FileManager;
import grisu.model.GrisuRegistryManager;
import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.vpac.grisu.webclient.client.files.GrisuFileObject;



/**
 * This is an example of how to use UploadAction class.
 *  
 * This servlet saves all received files in a temporary folder, 
 * and deletes them when the user sends a remove request.
 * 
 * @author Manolo Carrasco Moñino
 *
 */
public class FileUploadToWebPortalServlet extends UploadAction {

	private static final Logger myLogger = Logger.getLogger(FileUploadToWebPortalServlet.class.getName());
	
  private static final long serialVersionUID = 1L;
  
  Hashtable<String, String> receivedContentTypes = new Hashtable<String, String>();
  /**
   * Maintain a list with received files and their content types. 
   */
  Hashtable<String, File> receivedFiles = new Hashtable<String, File>();

  /**
   * Override executeAction to save the received files in a custom place
   * and delete this items from session.  
   */
  @Override
  public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException {
    String response = "";
    myLogger.debug("About to start uploading process");
    int cont = 0;
    for (FileItem item : sessionFiles) {
      if (false == item.isFormField()) {
        cont ++;
        try {
          /// Create a new file based on the remote file name in the client
          // String saveName = item.getName().replaceAll("[\\\\/><\\|\\s\"'{}()\\[\\]]+", "_");
          // File file =new File("/tmp/" + saveName);
          myLogger.debug("Uploading file " + item.getContentType());
        
          
          
          /// Create a temporary file placed in /tmp (only works in unix)
          File file = File.createTempFile("upload-", ".bin", new File(ServerConfiguration.getInstance().getConfiguration(ServerConfiguration.TEMP_FILE_STORAGE_DIR)));
          item.write(file);
          
          myLogger.debug("Finished uploading file " + item.getContentType());
          /// Save a list with the received files
          receivedFiles.put(item.getFieldName(), file);
          receivedContentTypes.put(item.getFieldName(), item.getContentType());
          
          /// Compose a xml message with the full file information which can be parsed in client side
          response += "<file-" + cont + "-field>" + item.getFieldName() + "</file-" + cont + "-field>\n";
          response += "<file-" + cont + "-name>" + item.getName() + "</file-" + cont + "-name>\n";
          response += "<file-" + cont + "-size>" + item.getSize() + "</file-" + cont + "-size>\n";
          response += "<file-" + cont + "-type>" + item.getContentType()+ "</file-" + cont + "-type>\n";
        } catch (Exception e) {
        	
        	myLogger.error("Error uploading file "  + e.getMessage());
          throw new UploadActionException(e.getMessage());
        }
      }
    }
    
    /// Remove files from session because we have a copy of them
    removeSessionFileItems(request);
    
    
    /// Send information of the received files to the client.
    return "<response>\n" + response + "</response>\n";
  }
  
  
	private ServiceInterface getServiceInterface(HttpSession session) {

		ServiceInterface si = (ServiceInterface) (session
				.getAttribute("serviceInterface"));
		if (si == null) {
			myLogger.error("ServiceInterface not in session (yet?).");
			throw new RuntimeException("Not logged in.");
		}
		return si;
	}
  /**
   * Get the content of an uploaded file.
   */
  @Override
  public void getUploadedFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String fieldName = request.getParameter(PARAM_SHOW);
    File f = receivedFiles.get(fieldName);
    if (f != null) {
      response.setContentType(receivedContentTypes.get(fieldName));
      FileInputStream is = new FileInputStream(f);
      copyFromInputStreamToOutputStream(is, response.getOutputStream());
    } else {
      renderXmlResponse(request, response, ERROR_ITEM_NOT_FOUND);
   }
  }
  
  /**
   * Remove a file when the user sends a delete request.
   */
  @Override
  public void removeItem(HttpServletRequest request, String fieldName)  throws UploadActionException {
    File file = receivedFiles.get(fieldName);
    receivedFiles.remove(fieldName);
    receivedContentTypes.remove(fieldName);
    if (file != null) {
      file.delete();
    }
  }
}