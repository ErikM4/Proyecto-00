import java.util.ArrayList;
import java.util.List;

public class Proyecto {
    private String nombre;
    private Usuario gestor;
    private List<Usuario> programadores;
    private List<Tarea> tareas;
    private EstadoTarea estado;

    public Proyecto(String nombre, Usuario gestor) {
        this.nombre = nombre;
        this.gestor = gestor;
        this.programadores = new ArrayList<>();
        this.tareas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public Usuario getGestor() {
        return gestor;
    }

    public List<Usuario> getProgramadores() {
        return programadores;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void asignarProgramador(Usuario programador) {
        if (programador.getRol() == Rol.PROGRAMADOR) {
            programadores.add(programador);
        }
    }

    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
        if (tarea.getProgramador() != null){
            this.estado = EstadoTarea.ASIGNADO;

        }
        else {
            this.estado = EstadoTarea.ASIGNADO;

        }
    }

    @Override
    public String toString() {
        return "Proyecto: " + nombre + " (Gestor: " + gestor.getNombre() + ")";
    }
}

