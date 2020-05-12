package server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

import java.io.OutputStream;

import java.net.InetSocketAddress;
import java.net.URI;

import model.Evento;
import model.HiloCancela;
import model.HiloCompra;
import model.HiloReserva;
import model.Respuesta;


//entry points localhost:4000/reserva
//             localhost:4000/compra?ticket=3

public class Server
{
    private static int cantHilos =1  ;
    private static Evento evento = new Evento();
    
  
    public static void main(String[] args) throws IOException {
          HttpServer server = HttpServer.create(new InetSocketAddress(4000), 0);
          HttpContext context = server.createContext("/reserva");
          HttpContext context2 = server.createContext("/compra");
          HttpContext context3 = server.createContext("/cancela");
          
          context.setHandler(Server::handleRequestReserva);
          context2.setHandler(Server::handleRequestCompra);
          context3.setHandler(Server::handleRequestCancela);
          server.start();
          System.out.println("Servidor escuchando en puerto 4000");
      }

      private static void handleRequestReserva(HttpExchange exchange) 
      {
          URI requestURI = exchange.getRequestURI();
          String query = requestURI.getQuery();
          String[] partes = query.split("=");
          int nroCliente = Integer.parseInt(partes[1]);
          new HiloReserva(cantHilos, evento, exchange, nroCliente).start();
          cantHilos++;
     
      }
      public static void responseReserva(HttpExchange exchange, Respuesta respuesta) throws IOException 
      {
          exchange.sendResponseHeaders(respuesta.getCodigo(), respuesta.getMensaje().getBytes().length);
          OutputStream os = exchange.getResponseBody();
          os.write(respuesta.getMensaje().getBytes());
          os.close();
      }
      
    public static void responseCompra(HttpExchange exchange, Respuesta respuesta) throws IOException 
    {
        exchange.sendResponseHeaders(respuesta.getCodigo(), respuesta.getMensaje().getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(respuesta.getMensaje().getBytes());
        os.close();
    }
      //compra?nroticket=3&nrocliente=2
        private static void handleRequestCompra(HttpExchange exchange) throws IOException {
        
        URI requestURI = exchange.getRequestURI();
        String query = requestURI.getQuery();
        String[] partes = query.split("&");
        String[] partesTicket = partes[0].split("=");
        String[] partesCliente = partes[1].split("=");
        

        int nroTicket = Integer.parseInt(partesTicket[1]); 
        int nroCliente =  Integer.parseInt(partesCliente[1]);
        new HiloCompra(cantHilos, evento, exchange, nroTicket, nroCliente).start();
        cantHilos++;
    
    }
        
    private static void handleRequestCancela(HttpExchange exchange) throws IOException {
    
    URI requestURI = exchange.getRequestURI();
    String query = requestURI.getQuery();
    String[] partes = query.split("&");
    String[] partesTicket = partes[0].split("=");
    String[] partesCliente = partes[1].split("=");
    

    int nroTicket = Integer.parseInt(partesTicket[1]); 
    int nroCliente =  Integer.parseInt(partesCliente[1]);
    new HiloCancela(cantHilos, evento, exchange, nroTicket, nroCliente).start();
    cantHilos++;
    
    }


        public static void responseCancela(HttpExchange exchange, Respuesta respuesta) throws IOException
        {
            exchange.sendResponseHeaders(respuesta.getCodigo(), respuesta.getMensaje().getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(respuesta.getMensaje().getBytes());
            os.close();
        }
}
