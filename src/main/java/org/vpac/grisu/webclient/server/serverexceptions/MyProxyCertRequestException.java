package org.vpac.grisu.webclient.server.serverexceptions;

/**
 * 
 * @author danny
 *
 */
public class MyProxyCertRequestException extends Exception {
 
 private String errorMessage;
 
 /**
  *  Default Constructor for Creating MyProxyCertRequestException
  *  
  */
 public MyProxyCertRequestException()
 {
  super();
 }
 
 /**
  * Constructor for Creating MyProxyCertRequestException
  * 
  * @param errorMessage
  *    String of the error message  
  */   
 public MyProxyCertRequestException(String errorMessage) {
  this();
  this.errorMessage = errorMessage;
  
 }
 
 /**
  * Getter method for getting the error message
  * 
  * @return String errorMessage
  *   
  */
 public String getMyProxyCertRequestError() {
  return errorMessage;
 }

}