package modelo;

import java.util.ArrayList;

/**
 *
 * @author acer
 */
public interface CRUD {

    public boolean Agregar(Object obj);

    public boolean Modificar(Object obj);

    public boolean Eliminar(Object obj);

    public ArrayList<Object[]> consultar();

}
