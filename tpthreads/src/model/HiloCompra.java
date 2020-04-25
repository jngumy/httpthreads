package model;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class HiloCompra extends Thread
{
    private Evento evento;
    private int id;
    private HttpExchange request;
    private int nroTicket;
    
    public HiloCompra(int id, Evento evento, HttpExchange request, int nroTicket)
    {
        super();
        this.id = id;
        this.evento = evento;
        this.request = request;
        this.nroTicket= nroTicket;
    }
    
    public void run()
    {
        try
        {
            evento.confirmarCompra(this.nroTicket, this.request, this.id);
        } catch (IOException e)
        {
        }
    }
    
    
}
