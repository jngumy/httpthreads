package model;

public class Ticket
{
    private int nroTicket;
    private int estado;
    private static int cant =1 ;

    public Ticket()
    {
        this.nroTicket = cant;
        cant ++;
        this.estado = Evento.LIBRE;
    }

    public int getNroTicket()
    {
        return nroTicket;
    }

    public int getEstado()
    {
        return estado;
    }
    
    public void cambiarEstado(int estado)
    {
        this.estado = estado;
    }
}
