/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juniarto.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 *
 * @author juniarto
 */
public class server {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Server server = new Server(8081);
        //ServletContextHandler handler = new ServletContextHandler(server,"/example");
        //ServletContextHandler handler = new ServletContextHandler(server,"/example");
        //handler.addServlet(ExampleServlet.class,"/*");
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        //handler.addServletWithMapping(ExampleServlet.class, "/example");
        //handler.addServletWithMapping(Example2Servlet.class,"/example2");
        handler.addServletWithMapping(FileUploadServlet.class,"/fileupload");
        server.start();
        server.dumpStdErr();
        server.join();
    }
    
}
