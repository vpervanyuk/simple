package ru.wsill.HelloServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

@WebServlet("/ts")
public class ThreadsServlet extends HttpServlet {

    int j;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        synchronized (this) {
            int cnt = Integer.parseInt(req.getParameter("cnt"));
            for (int i = 0; i < cnt*1000000; i++) {
                j++;
                        }
            Writer writer = resp.getWriter();
            writer.write("<body><div>"+Integer.toString(j)+"<div></body>");
            System.out.println(Thread.currentThread().getName());
            System.out.println(j);
        }
    }


/*    public static void main(String[] args){
        for (int i = 0; i < 3; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        URLConnection urlConnection = new URL("http://localhost:8080/ts?cnt=3").openConnection();
                        urlConnection.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            }
        }*/
    }


