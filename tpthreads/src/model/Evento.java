package model;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;

import server.Server;


public class Evento
{
    private HashMap<Integer, Ticket> tickets = new HashMap<Integer, Ticket>();
    public static final int LIBRE = 0;
    public static final int RESERVADO = 1;
    public static final int COMPRADO = 2;
    public static final int CANT_TICKETS = 11;   //cantidad de tickets
    
    
    public Evento()
    {
        for (int i=1; i<CANT_TICKETS; i++)
            this.tickets.put(i, new Ticket());
    }
    
    public synchronized void reservarTicket(HttpExchange request, int id, int nroCliente) throws IOException, InterruptedException
    {
        Ticket ticket = disponible();
        int codigo =404;
        String mensaje = "Solicitud id: "+ id + " no pudo realizar una reserva";
       
        Thread.sleep((int)Math.random() * 100000); //simular tiempo de reserva
        while(ticket == null)
        {   
            System.out.println("Solicitud id: "+ id + " en la espera de un ticket libre para reservar");
            wait();
            ticket = disponible();
        }
        ticket.cambiarEstado(RESERVADO);
        ticket.setNroCliente(nroCliente);
        codigo = 200;
        mensaje = "Solicitud id: "+ id + " reservó el ticket nro: " + ticket.getNroTicket() + " (Nro de cliente: "+ nroCliente + " ).";
        Respuesta respuesta = new Respuesta(id, mensaje,codigo);
        System.out.println(respuesta.getMensaje());
        Server.responseReserva(request, respuesta);
      
    }
    
    public synchronized void confirmarCompra(int nroTicket, int nroCliente, HttpExchange request, int id) throws IOException
    {
        
        int codigo =404;
        String mensaje = " ";
        
        if(this.tickets.containsKey(nroTicket) && this.tickets.get(nroTicket).getEstado() == RESERVADO && this.tickets.get(nroTicket).getNroCliente()== nroCliente)
        {
            codigo = 200;
            this.tickets.get(nroTicket).cambiarEstado(COMPRADO);
            mensaje ="Solicitud id: "+ id + " pudo realizar la compra del ticket nro: " + nroTicket;
        }
        else
        if(this.tickets.containsKey(nroTicket) && this.tickets.get(nroTicket).getEstado() == LIBRE)
            mensaje = "Solicitud id: "+ id + " no pudo realizar la compra del ticket nro: " + nroTicket +". No fue reservado";
        else
            if(this.tickets.containsKey(nroTicket) && this.tickets.get(nroTicket).getEstado() == COMPRADO)
                mensaje = "Solicitud id: "+ id + " no pudo realizar la compra del ticket nro: " + nroTicket +". Ya fue comprado previamente";
            else
                if(this.tickets.containsKey(nroTicket) && this.tickets.get(nroTicket).getEstado() == RESERVADO && this.tickets.get(nroTicket).getNroCliente() != nroCliente )
                    mensaje = "Solicitud id: "+ id + " no pudo realizar la compra del ticket nro: " + nroTicket +". El nro de cliente es incorrecto";
                else
                    mensaje = "Solicitud id: "+ id + " no pudo realizar la compra del ticket nro: " + nroTicket +". No existe el ticket";
            
        Respuesta respuesta = new Respuesta(id, mensaje,codigo);
        System.out.println(respuesta.getMensaje());
        Server.responseCompra(request, respuesta);
        
    }
    
    public synchronized void cancelaReservaTicket( int nroTicket, int nroCliente , HttpExchange request, int id) throws IOException, InterruptedException
    {
        int codigo =404;
        String mensaje = "Solicitud id: "+ id + " no pudo cancelar la reserva";
       
        if(this.tickets.containsKey(nroTicket) && this.tickets.get(nroTicket).getEstado() == RESERVADO && this.tickets.get(nroTicket).getNroCliente()== nroCliente)
        {
           this.tickets.get(nroTicket).cambiarEstado(LIBRE);
           this.tickets.get(nroTicket).setNroCliente(-1);
           codigo = 200;
           mensaje = "Solicitud id: "+ id + " canceló la reserva del ticket nro: " + nroTicket + " (Nro de cliente: "+ nroCliente + " ).";
           notifyAll();
        }
        Respuesta respuesta = new Respuesta(id, mensaje,codigo);
        System.out.println(respuesta.getMensaje());
        Server.responseCancela(request, respuesta);
    }
    
    private Ticket disponible()
    {
        Iterator it = this.tickets.values().iterator();
        boolean found = false;
        Ticket auxTicket = null;
        Ticket disponible = null;
        while(it.hasNext() && !found)
        {
            auxTicket = (Ticket) it.next();
            if(auxTicket.getEstado()== Evento.LIBRE)
            {
                found = true;
                disponible = auxTicket;
            }
        }
        return disponible;   
    }
    
    
}
