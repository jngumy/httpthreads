package model;

public class Respuesta
{
    private  int idHilo;
    private String mensaje;
    private int codigo;


    public Respuesta(int idHilo, String mensaje, int codigo)
    {
        this.idHilo = idHilo;
        this.mensaje = mensaje;
        this.codigo = codigo;
    }
    
    public int getCodigo()
    {
        return codigo;
    }

    public void setIdHilo(int idHilo)
    {
        this.idHilo = idHilo;
    }

 
    public void setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

   

    public int getIdHilo()
    {
        return idHilo;
    }

  

    public String getMensaje()
    {
        return mensaje;
    }

}
