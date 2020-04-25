package model;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;


public class HiloReserva extends Thread
{
    private Evento evento;
    private int id;
    private HttpExchange request;
    
    public HiloReserva(int id, Evento evento, HttpExchange request)
    {
        super();
        this.id = id;
        this.evento = evento;
        this.request = request;
    }
    
    
    public void run()
    {
        try
        {
            evento.reservarTicket(request, this.id);
        } catch (IOException e)
        {
        }

    }

    
}
