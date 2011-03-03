package org.vpac.grisu.webclient.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.vpac.grisu.webclient.server.serverexceptions.ServerConfigNotFoundException;





/**
 *  This is a class used to extract the Server Configurations from the Serverconfiguration.xml
 *  
 * @author Daniel Davidson
 *
 */
public class ServerConfiguration {

 private static ServerConfiguration serverconfiguration;
 
 public final static String MY_PROXY_USER_NAME_ATTRIBUTE_KEY = "MyProxyUserNameAttributeKey";
 public final static String MY_PROXY_PASSPHRASE_ATTRIBUTE_KEY = "MyProxyPassphrasettributeKey"; 
 public final static String SERVICE_INTERFACE_URL = "ServiceInterfaceURL"; 
 public final static String APPLICATION_URL = "ApplicationURL";
 public static final String URL_PARAMETER_NAME = "URLParameterName";
 public static final String LANYARD_URL = "LanyardURL";
 public static final String TEMP_FILE_STORAGE_DIR = "TemporyFileStorageDir";
 public static final String HOME_URL = "HomeURL";
 
 private Properties properties;
 static final Logger myLogger = Logger.getLogger(ServerConfiguration.class
   .getName());



 

 /**
  *  Singleton Constructor that try's to load the ServerConfiguration.xml
  *  From /etc/grisu/grisu-web/ if not found it then loads the default configuration
  */
 private ServerConfiguration(){
  properties = new Properties();
  try {
   properties.loadFromXML(new FileInputStream("/etc/grisu/grisu-web/ServerConfiguration.xml"));
  } catch (Exception exception) {
   myLogger
     .warn("Could Load Server Configuration XML from etc/grisu/grisu-web/ . Error Message : "
       + exception + "Loading Default Configuration");
   try
   {
    properties.loadFromXML(getClass().getResourceAsStream("ServerConfiguration.xml"));
   }
   catch(IOException e)
   {
    myLogger.error("Couldn't Load Default ServerConfiguration XML Error: " + e);
    
   }
   
  }

 }
 
 /**
  * Singleton Method for getting the instance of the ServerConfiguration Class
  * 
  * @return ServerConfiguration
  */   
 public static ServerConfiguration getInstance(){
  if (serverconfiguration == null) {
   serverconfiguration = new ServerConfiguration();
  }
  
  return serverconfiguration;
 }


 /**
  *  /**
  * Method for Retrieving  the Properties from the Server Configuration.
  * @return String 
  * @param configurationName
  * @return String Configuration Value 
  *     
  */
 public String getConfiguration(String configurationName) {
  
  myLogger.debug("Getting property" +configurationName+" = " + properties.getProperty(configurationName));
  return properties.getProperty(configurationName);
 }

 

 
 @Override
 public Object clone() throws CloneNotSupportedException
  {throw new CloneNotSupportedException();
  }



}