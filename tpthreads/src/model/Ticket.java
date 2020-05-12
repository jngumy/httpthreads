package model;

public class Ticket
{
    private int nroTicket;
    private int nroCliente;
    private int estado;
    private static int cant =1 ;

    public Ticket()
    {
        this.nroTicket = cant;
        cant ++;
        this.estado = Evento.LIBRE;
        this.nroCliente = -1;
    }

    public int getNroTicket()
    {
        return nroTicket;
    }

    public int getEstado()
    {
        return estado;
    }

    public void setNroCliente(int nroCliente)
    {
        this.nroCliente = nroCliente;
    }

    public int getNroCliente()
    {
        return nroCliente;
    }

    public void cambiarEstado(int estado)
    {
        this.estado = estado;
    }
}
