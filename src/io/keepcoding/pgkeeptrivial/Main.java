package io.keepcoding.pgkeeptrivial;

import java.util.InputMismatchException;
import java.util.Scanner;

import io.keepcoding.pgkeeptrivial.model.Game;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("");
        System.out.println("BIENVENIDOS AL JUEGO KEEP-TRIVIAL");
        System.out.println("=================================");
        System.out.println("");
        
        int numEquipos = 0;
        boolean validInput = false;
        
        while (!validInput) {
            System.out.println("Introduce el número de equipos:");
            try {
                numEquipos = scanner.nextInt();
                validInput = true; // Entrada válida, salir del bucle
            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduce un número entero válido.");
                scanner.next(); // Limpiar la entrada no válida
            }
        }
        
        scanner.nextLine();  // Consumir el salto de línea
        System.out.println("");
        
        for (int i = 0; i < numEquipos; i++) {
            System.out.println("Introduce el nombre del equipo " + (i + 1) + ":");
            String name = scanner.nextLine();
            game.addTeam(name);
        }

        game.startGame();
    }
}
