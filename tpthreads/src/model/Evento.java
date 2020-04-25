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
    
    public synchronized void reservarTicket(HttpExchange request, int id) throws IOException
    {
        Ticket ticket = disponible();
        int codigo =404;
        String mensaje = "Solicitud id: "+ id + " no pudo realizar una reserva";
        if(ticket != null)
        {
            ticket.cambiarEstado(RESERVADO);
            codigo = 200;
            mensaje = "Solicitud id: "+ id + " reservó el ticket nro: " + ticket.getNroTicket();
            
        }
        Respuesta respuesta = new Respuesta(id, mensaje,codigo);
        System.out.println(respuesta.getMensaje());
        Server.responseReserva(request, respuesta);
      
    }
    
    public synchronized void confirmarCompra(int nroTicket, HttpExchange request, int id) throws IOException
    {
        
        int codigo =404;
        String mensaje = " ";
        
        if(this.tickets.containsKey(nroTicket) && this.tickets.get(nroTicket).getEstado() == RESERVADO)
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
                mensaje = "Solicitud id: "+ id + " no pudo realizar la compra del ticket nro: " + nroTicket +". No existe el ticket";
            
        Respuesta respuesta = new Respuesta(id, mensaje,codigo);
        System.out.println(respuesta.getMensaje());
        Server.responseCompra(request, respuesta);
        
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
