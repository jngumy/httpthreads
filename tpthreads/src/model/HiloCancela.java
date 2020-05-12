package model;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class HiloCancela extends Thread
{
    private Evento evento;
    private int id;
    private HttpExchange request;
    private int nroTicket;
    private int nroCliente;
    
    public HiloCancela(int id, Evento evento, HttpExchange request, int nroTicket, int nroCliente)
    {
        super();
        this.id = id;
        this.evento = evento;
        this.request = request;
        this.nroTicket= nroTicket;
        this.nroCliente = nroCliente;
    }
    
    public void run()
    {
        try
        {
            evento.cancelaReservaTicket(this.nroTicket, this.nroCliente, this.request, this.id);
        } catch (IOException e)
        {
        } catch (InterruptedException e)
        {
        }
    }
    
    
}
