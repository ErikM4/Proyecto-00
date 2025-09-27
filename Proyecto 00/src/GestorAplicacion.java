import java.util.*;

public class GestorAplicacion {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Proyecto> proyectos = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public GestorAplicacion() {
        // Crear admin,gestor y programador por defecto
        usuarios.add(new Usuario("admin", Rol.ADMIN));
        usuarios.add(new Usuario("Erik", Rol.GESTOR));
        usuarios.add(new Usuario("Ali", Rol.PROGRAMADOR));
    }

    public void iniciar() {
        System.out.print("Ingrese su usuario: ");
        String nombre = sc.nextLine();

        Usuario actual = usuarios.stream()
                .filter(u -> u.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);

        if (actual == null) {
            System.out.println("Usuario no encontrado.");
            iniciar();
            return;
        }

        switch (actual.getRol()) {
            case ADMIN -> menuAdmin(actual);
            case GESTOR -> menuGestor(actual);
            case PROGRAMADOR -> menuProgramador(actual);
        }
    }

    private void menuAdmin(Usuario admin) {
        int opcion;
        do {
            System.out.println("\n--- MENU ADMIN ---");
            System.out.println("1. Crear usuario");
            System.out.println("2. Eliminar usuario");
            System.out.println("3. Ver lista de usuarios");
            System.out.println("4. Volver");
            System.out.println("0. Salir");
            opcion = sc.nextInt();
            sc.nextLine();

            switch(opcion) {
                case 1 -> crearUsuario();
                case 2 -> eliminarUsuario();
                case 3 -> listarUsuarios();
                case 4 -> iniciar();
            }
        } while(opcion != 0);
    }

    private void crearUsuario() {
        System.out.print("Nombre de usuario: ");
        String nombre = sc.nextLine();
        System.out.print("Rol (GESTOR o PROGRAMADOR): ");
        String rol = sc.nextLine();
        usuarios.add(new Usuario(nombre, Rol.valueOf(rol.toUpperCase())));
    }

    private void eliminarUsuario() {
        System.out.print("Nombre del usuario que desea eliminar: ");
        String elim = sc.nextLine();
        usuarios.removeIf(u -> u.getNombre().equalsIgnoreCase(elim));
    }

    private void listarUsuarios() {
        usuarios.forEach(System.out::println);
    }

    private void menuGestor(Usuario gestor) {
        int opcion;
        do {
            System.out.println("\n--- MENU GESTOR ---");
            System.out.println("1. Crear nuevo proyecto");
            System.out.println("2. Ver lista de proyectos del gestor");
            System.out.println("3. Ver lista de programadores disponibles");
            System.out.println("4. Asignar un programador a un proyecto");
            System.out.println("5. Crear una tarea en un proyecto");
            System.out.println("6. Ver lista de programadores de un proyecto");
            System.out.println("7. Ver lista de tareas de un proyecto");
            System.out.println("8. Volver");
            System.out.println("0. Salir");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> crearProyecto(gestor);
                case 2 -> listarProyectosGestor(gestor);
                case 3 -> listarProgramadores();
                case 4 -> asignarProgramador(gestor);
                case 5 -> crearTarea(gestor);
                case 6 -> listarProgramadoresProyecto(gestor);
                case 7 -> listarTareasProyecto(gestor);
                case 8 -> iniciar();
            }
        } while (opcion != 0);
    }
    private void crearProyecto(Usuario gestor) {
        System.out.print("Nombre del proyecto: ");
        String nombre = sc.nextLine();
        proyectos.add(new Proyecto(nombre, gestor));
    }

    private void listarProyectosGestor(Usuario gestor) {
        proyectos.stream()
                .filter(p -> p.getGestor().equals(gestor))
                .forEach(System.out::println);
    }

    private void listarProgramadores() {
        usuarios.stream()
                .filter(u -> u.getRol() == Rol.PROGRAMADOR)
                .forEach(System.out::println);
    }

    private void asignarProgramador(Usuario gestor) {
        listarProyectosGestor(gestor);
        System.out.print("Nombre del proyecto: ");
        String nombreProyecto = sc.nextLine();

        Proyecto proyecto = proyectos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombreProyecto) && p.getGestor().equals(gestor))
                .findFirst()
                .orElse(null);

        if (proyecto == null) {
            System.out.println("Proyecto no encontrado o no pertenece a este gestor.");
            return;
        }

        listarProgramadores();
        System.out.print("Nombre del programador: ");
        String nombreProg = sc.nextLine();

        Usuario prog = usuarios.stream()
                .filter(u -> u.getNombre().equalsIgnoreCase(nombreProg) && u.getRol() == Rol.PROGRAMADOR)
                .findFirst()
                .orElse(null);

        if ((prog != null)) {
            proyecto.asignarProgramador(prog);
            System.out.println("Programador asignado correctamente.");
        } else {
            System.out.println("Programador no encontrado.");
        }
    }

    private void listarProgramadoresProyecto(Usuario gestor) {
        listarProyectosGestor(gestor);
        System.out.print("Nombre del proyecto: ");
        String nombreProyecto = sc.nextLine();

        Proyecto proyecto = proyectos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombreProyecto) && p.getGestor().equals(gestor))
                .findFirst()
                .orElse(null);

        if (proyecto != null) {
            proyecto.getProgramadores().forEach(System.out::println);
        } else {
            System.out.println("Proyecto no encontrado.");
        }
    }

    private void crearTarea(Usuario gestor) {
        listarProyectosGestor(gestor);
        System.out.print("Elige un proyecto: ");
        String nombreProyecto = sc.nextLine();

        Proyecto proyecto = proyectos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombreProyecto) && p.getGestor().equals(gestor))
                .findFirst()
                .orElse(null);

        if (proyecto == null) {
            System.out.println("Proyecto no encontrado o no pertenece a este gestor.");
            return;
        }

        System.out.print("Descripción de la tarea: ");
        String desc = sc.nextLine();

        proyecto.getProgramadores().forEach(System.out::println);
        System.out.print("Nombre del programador a asignar: ");
        String nombreProg = sc.nextLine();

        Usuario prog = proyecto.getProgramadores().stream()
                .filter(u -> u.getNombre().equalsIgnoreCase(nombreProg))
                .findFirst()
                .orElse(null);

        if ((prog != null)){

            proyecto.agregarTarea(new Tarea(desc, prog));
            System.out.println("Tarea creada y asignada.");
        } else {
            System.out.println("El programador no existe.");
        }
    }

    private void listarTareasProyecto(Usuario gestor) {
        listarProyectosGestor(gestor);
        System.out.print("Proyecto: ");
        String nombreProyecto = sc.nextLine();

        Proyecto proyecto = proyectos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombreProyecto) && p.getGestor().equals(gestor))
                .findFirst()
                .orElse(null);

        if (proyecto != null) {
            proyecto.getTareas().forEach(System.out::println);
        } else {
            System.out.println("Proyecto no encontrado.");
        }
    }
    private void menuProgramador(Usuario programador) {
        int opcion;
        do {
            System.out.println("\n--- MENU PROGRAMADOR ---");
            System.out.println("1. Consultar proyectos asignados");
            System.out.println("2. Consultar tareas en un proyecto");
            System.out.println("3. Marcar tarea como FINALIZADA");
            System.out.println("4. Volver");
            System.out.println("0. Salir");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> consultarProyectos(programador);
                case 2 -> consultarTareas(programador);
                case 3 -> marcarTareaFinalizada(programador);
                case 4 -> iniciar();
            }
        } while (opcion != 0);
    }
    private void consultarProyectos(Usuario programador) {
        proyectos.stream()
                .filter(p -> p.getProgramadores().contains(programador))
                .forEach(System.out::println);
    }

    private void consultarTareas(Usuario programador) {
        consultarProyectos(programador);
        System.out.print("Nombre del proyecto: ");
        String nombreProyecto = sc.nextLine();

        Proyecto proyecto = proyectos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombreProyecto)
                        && p.getProgramadores().contains(programador))
                .findFirst()
                .orElse(null);

        if (proyecto != null) {
            proyecto.getTareas().stream()
                    .filter(t -> t.getEstado().equals(EstadoTarea.ASIGNADO))
                    .forEach(System.out::println);
        } else {
            System.out.println("No tienes ese proyecto asignado o no hay proyectos.");
        }
    }

    private void marcarTareaFinalizada(Usuario programador) {
        consultarProyectos(programador);
        System.out.print("Nombre del proyecto: ");
        String nombreProyecto = sc.nextLine();

        Proyecto proyecto = proyectos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombreProyecto)
                        && p.getProgramadores().contains(programador))
                .findFirst()
                .orElse(null);

        if (proyecto == null) {
            System.out.println("Proyecto no encontrado o no asignado.");
            return;
        }

        proyecto.getTareas().stream()
                .filter(t -> t.getEstado().equals(EstadoTarea.ASIGNADO))
                .forEach(System.out::println);

        System.out.print("Descripción de la tarea a marcar como finalizada: ");
        String desc = sc.nextLine();

        Tarea tarea = proyecto.getTareas().stream()
                .filter(t -> t.getDescripcion().equalsIgnoreCase(desc)
                        && t.getEstado().equals(EstadoTarea.ASIGNADO))
                .findFirst()
                .orElse(null);

        if (tarea != null) {
            tarea.marcarFinalizada();
            System.out.println("Tarea marcada como finalizada.");
        } else {
            System.out.println("Tarea no encontrada.");
        }
    }

}

