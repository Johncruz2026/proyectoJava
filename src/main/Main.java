package main;

import dao.UsuarioDAO;

public class Main {

    public static void main(String[] args) {

        UsuarioDAO dao = new UsuarioDAO();

        // LISTAR
        System.out.println("Lista de usuarios:");
        dao.listar().forEach(u ->
                System.out.println(u.getId() + " - " + u.getNombre() + " - " + u.getCorreo())
        );
    }
}
