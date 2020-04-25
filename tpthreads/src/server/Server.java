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
          context.setHandler(Server::handleRequestReserva);
          context2.setHandler(Server::handleRequestCompra);
          server.start();
          System.out.println("Servidor escuchando en puerto 4000");
      }

      private static void handleRequestReserva(HttpExchange exchange) 
      {
    
          new HiloReserva(cantHilos, evento, exchange).start();
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
    
        private static void handleRequestCompra(HttpExchange exchange) throws IOException {
        
        URI requestURI = exchange.getRequestURI();
        String query = requestURI.getQuery();
        String[] partes = query.split("=");
        int nroTicket = Integer.parseInt(partes[1]); 
        new HiloCompra(cantHilos, evento, exchange, nroTicket).start();
        cantHilos++;
    
    }

    
}
