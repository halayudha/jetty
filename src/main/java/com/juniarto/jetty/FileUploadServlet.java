/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juniarto.jetty;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.eclipse.jetty.http.HttpStatus;

/**
 *
 * @author juniarto
 */
public class FileUploadServlet extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "upload";
    
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; //3MB
    private static final int MAX_FILE_SIZE    = 1024 * 1024 * 6000; //40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 6000; //50MB
    
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException{
        
        //check if the request actually contains upload file
        if (!ServletFileUpload.isMultipartContent(request)){
            PrintWriter writer = response.getWriter();
            writer.println("Error: Form must has enctype=multipart/form-data");
            writer.flush();
            return;
        }
        /*
        else{
            response.setStatus(HttpStatus.OK_200);
            response.getWriter().println("FileUploadServlet Accessed!");
        }*/
        //configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //set memory threshold - beyond which files are stored in disk
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        //sets temporary location to store files
        //System.out.println("tmpdir: " + System.getProperty("java.io.tmpdir"));
       
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        //sets maximum size of upload file
        upload.setFileSizeMax(MAX_FILE_SIZE);
        
        //sets maximum size of request (include file + form data)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        
        //constructs the directory path to store upload file
        //this path is relative to application's directory
        
        /*
        String uploadPath = System.getProperty("user.dir")
        //String uploadPath = getServletContext().getRealPath("\\")
                + File.separator + UPLOAD_DIRECTORY;
        System.out.println(uploadPath);
        */
        //creates the directory if it does no exist
        /*
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()){
            System.out.println("UPLOAD DIRECTORY DOESNT EXIST, CREATE NOW!");
            uploadDir.mkdir();
        }else{
            System.out.println("UPLOAD DIRECTORY EXIST!");
        }*/
        
        String jobID = null;
        String mapperID = null;
        String partitionNo = null;
        String uploadPath = null;
     
        try{
            List<FileItem> formItems = upload.parseRequest(request);
            if (formItems != null && formItems.size() > 0){
                //iterates over form's fields
                for (FileItem item : formItems){
                    //processes only fields that are not form fields
                    //System.out.println("IS FORM FIELD");
                    if (item.isFormField()){
                        System.out.println("IS FORM FIELDS");
                        System.out.println("FF getName:" + item.getName());
                        System.out.println("FF getFieldName:" + item.getFieldName());
                        System.out.println("FF getString:" + item.getString());
                        switch(item.getFieldName()){
                            case "jobID":
                                jobID = item.getString();
                                break;
                            case "mapperID":
                                mapperID = item.getString();
                                break;
                            case "partitionNo":
                                partitionNo = item.getString();
                                break;
                            default:
                                System.out.println("INVALID!");
                        }
                        /*
                        uploadPath = "/home/hduser/" + jobID + "/" + mapperID;
                        //creates the directory if it does no exist
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()){
                            System.out.println("UPLOAD DIRECTORY DOESNT EXIST, CREATE NOW!");
                            if(uploadDir.mkdirs()){
                                System.out.println("DIRECTORY IS CREATED!");
                            }else{
                                System.out.println("Failed to create directory!");
                            };
                        }else{
                            System.out.println("UPLOAD DIRECTORY EXIST!");
                        }*/
                    }
                    if (!item.isFormField()){
                        System.out.println("NOT FORM FIELDS");
                        System.out.println(item.getName());
                        System.out.println(item.getFieldName());
                        String fileName = new File(item.getName()).getName();
                        System.out.println(fileName);
                        
                        //uploadPath = "/home/hduser/" + jobID + File.separator + mapperID + File.separator + "reduce_" + fileName.substring(fileName.length()-1);
                        uploadPath = "/home/hduser/" + jobID + File.separator + mapperID.substring(0, 35) + File.separator + "reduce_" + partitionNo;
                        //creates the directory if it does no exist
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()){
                            System.out.println("UPLOAD DIRECTORY DOESNT EXIST, CREATE NOW!");
                            if(uploadDir.mkdirs()){
                                System.out.println("DIRECTORY IS CREATED!");
                            }else{
                                System.out.println("Failed to create directory!");
                            };
                        }else{
                            System.out.println("UPLOAD DIRECTORY EXIST!");
                        }
                        
                        
                        
                        
                        
                        
                        //String filePath = uploadPath + File.separator + "reduce_" + fileName.substring(fileName.length()-1) + File.separator + fileName;
                        
                        String filePath = uploadPath + File.separator + fileName;
                        System.out.println(filePath);
                        File storeFile = new File(filePath);
                        
                        //saves the file on disk
                        item.write(storeFile);
                        //request.setAttribute("message", "upload has been done successfully");
                        System.out.println("UPLOAD SUCCESSFUL");
                    }
                }
            }
        } catch (FileUploadException ex) {
            Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
