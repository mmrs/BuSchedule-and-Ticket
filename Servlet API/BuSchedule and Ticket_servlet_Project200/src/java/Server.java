/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Siyam
 */
public class Server extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String filename = null;
    String[] SeatStatusFlag = new String[45];
    String[]  items = new String[100];
    String root = "D:\\BuScheduleandTicket\\";
    
    
    String ret = "";
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String command = request.getParameter("command");
        String from = request.getParameter("from");
        String time = request.getParameter("time");
        String bus = request.getParameter("bus");
        String date = request.getParameter("date");
        
        
        filename = "";
        filename += root + from + "-" + bus +"-"+ time +"-"+date+".txt";
        if (command.equals("get")) {
            loadSeatStatus();
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
            response.setContentType("text/plain");
            writer.write(ret);
            writer.flush();
            writer.close();
        
        // response will be send from here..the flag array wil be sent.
        } else if (command.equals("set")) {

            setSeatStatus(request, response);

//            try (PrintWriter out = response.getWriter()) {
//                out.println("Command = " + command);
//            }
        }
        else if(command.equals("getSchedule")){
            
            loadSchedule(bus);
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
            response.setContentType("text/plain");
            writer.write(ret);
            writer.flush();
            writer.close();
            
        }
        else if(command.equals("getTransportList")){
            loadTransportList(bus);
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
            response.setContentType("text/plain");
            writer.write(ret);
            writer.flush();
            writer.close();
        }
        else if(command.equals("getRouteList")){
            
            getRoutes();
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
            response.setContentType("text/plain");
            writer.write(ret);
            writer.flush();
            writer.close();
        }
        else if(command.equals("setRoutes")){
            setRoutes(request, response);
        }
        else {
            
        File file = new File(root + command+".txt");
        FileWriter fw = new FileWriter(file);
        items = request.getParameterValues("value");
        PrintWriter pw = response.getWriter();
        pw.println("total : "+items.length);
        for (int i = 0; i < items.length; i++) {

            pw.println(items[i]);
            fw.write(items[i]);
            fw.write(" ");
        }
        pw.println("\nmmrsiyam");
        fw.close();
            
        }
        

    }

    void loadSeatStatus() throws FileNotFoundException, IOException {

        File file = new File(filename);
        if(!file.exists())
            file.createNewFile();
        try (Scanner in = new Scanner(file)) {
            int i = 0;
            ret = "";
            while (in.hasNext()) {
                SeatStatusFlag[i] = in.next();
                ret+= SeatStatusFlag[i]+" ";
            }
        }
    }
    
    void loadSchedule(String bus) throws IOException{
       
        filename = root + bus + ".txt";
          File file = new File(filename);
        if(!file.exists())
            file.createNewFile();
        ret = "";
        try (Scanner in = new Scanner(file)) {
            ret = "";
            while (in.hasNext()) {
                ret+= in.next() + " ";
            }
        }
    }
    
     private void loadTransportList(String bus) throws FileNotFoundException, IOException {
        filename = root + bus + ".txt";
          File file = new File(filename);
        if(!file.exists())
            file.createNewFile();
        ret = "";
        try (Scanner in = new Scanner(file)) {
            ret = "";
            while (in.hasNext()) {
                ret+= (in.next() + " ");
            }
        }
    }
      private void getRoutes() throws FileNotFoundException, IOException {
          filename = root +"routes.txt";
          File file = new File(filename);
        if(!file.exists())
            file.createNewFile();
        ret = "";
        try (Scanner in = new Scanner(file)) {
            ret = "";
            while (in.hasNext()) {
                ret+= in.next() + " ";
            }
        }
    }

    void setSeatStatus(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpServletRequest request = req;
        HttpServletResponse response = res;
        File file = new File(filename);
        FileWriter fw = new FileWriter(file);
        SeatStatusFlag = request.getParameterValues("no");
        PrintWriter pw = res.getWriter();
        pw.println("total : "+SeatStatusFlag.length);
        for (int i = 0; i < SeatStatusFlag.length; i++) {

            pw.println(SeatStatusFlag[i]);
            fw.write(SeatStatusFlag[i]);
            fw.write(" ");
        }
        pw.println("\nmmrsiyam");
        fw.close();
    }
    
   void setRoutes(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpServletRequest request = req;
        HttpServletResponse response = res;
        
        filename = root;
        File file = new File(filename);
        file.mkdirs();
        
        filename = root +"routes.txt";
        file = new File(filename);
        if(!file.exists())
            file.createNewFile();
        FileWriter fw = new FileWriter(file);
        items = request.getParameterValues("value");
        PrintWriter pw = res.getWriter();
        pw.println("total : "+ items.length);
        for (int i = 0; i < items.length; i++) {

            pw.println(items[i]);
            fw.write(items[i]);
            fw.write(" ");
        }
        pw.println("mmrsiyam");
        fw.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold


}
