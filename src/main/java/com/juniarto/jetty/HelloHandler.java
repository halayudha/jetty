/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juniarto.jetty;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 *
 * @author juniarto
 */
public class HelloHandler extends AbstractHandler {
    final String greeting;
    final String body;
    
    public HelloHandler(){
        this("Hello World");
    }
    
    public HelloHandler(String greeting){
        this(greeting, null);
    }
    public HelloHandler(String greeting, String body){
        this.greeting = greeting;
        this.body = body;
        
    }
    
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException{
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        
        PrintWriter out = response.getWriter();
        out.println("<h1>" + greeting + "</h1>");
        
        if (body != null){
            out.println("<h1>" + body + "</h1>");
        }
        baseRequest.setHandled(true);
        }
}
