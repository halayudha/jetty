/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juniarto.jetty;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.HttpConnection;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

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
        //Server server = new Server(8081);
        
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMaxThreads(500);
        
        
        Server server = new Server (threadPool);
        HttpConfiguration http_config = new HttpConfiguration();
        
      
        
        ServerConnector connector = new ServerConnector(server, new HttpConnectionFactory(http_config));
        connector.setPort(8081);
        server.addConnector(connector);
        
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
