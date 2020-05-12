package model;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;


public class HiloReserva extends Thread
{
    private Evento evento;
    private int id;
    private HttpExchange request;
    private int nroCliente;
    
    public HiloReserva(int id, Evento evento, HttpExchange request, int nroCliente)
    {
        super();
        this.id = id;
        this.evento = evento;
        this.request = request;
        this.nroCliente = nroCliente;
    }


    public void setNroCliente(int nroCliente)
    {
        this.nroCliente = nroCliente;
    }

    public int getNroCliente()
    {
        return nroCliente;
    }

    public void run()
    {
        try
        {
            evento.reservarTicket(request, this.id, this.nroCliente);
        } catch (IOException e)
        {
        } catch (InterruptedException e)
        {
        }

    }

    
}
