public class Tarea {
    private String descripcion;
    private Usuario programador;
    private EstadoTarea estado;

    public Tarea(String descripcion, Usuario programador) {
        this.descripcion = descripcion;
        this.programador = programador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Usuario getProgramador() {
        return programador;
    }

    public EstadoTarea getEstado() {
        return estado;
    }

    public void marcarFinalizada() {
        this.estado = EstadoTarea.FINALIZADA;
    }

    public void marcarAsignada() {
        this.estado = EstadoTarea.ASIGNADO;
    }

    @Override
    public String toString() {
        return descripcion + " - " + programador.getNombre() + " [" + estado + "]";
    }
}

