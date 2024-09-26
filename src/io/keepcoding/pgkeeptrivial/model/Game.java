package io.keepcoding.pgkeeptrivial.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Game {
	private List<Teams> teams;
	private Map<String, List<Questions>> questionsPerCategory;
	private static Random random = new Random();
	
	public Game() {
		this.teams = new ArrayList<>();
		this.questionsPerCategory = questionsPerCategory();
		questionsList(); // Cargar preguntas al inicio del juego
	}
	
	/**
     * Cargar preguntas desde archivos usando el método getQuestions().
     */
	
	private void questionsList() {
	    List<Questions> questions = getQuestions();  // Cargar las preguntas desde el archivo .txt

	    for (Questions question : questions) {
	        String category = question.getCategory();  // Obtener la categoría desde la pregunta

	        // if (!questionsPerCategory.containsKey(category)) {
	        if (!questionsPerCategory.equals(category)) {
	            questionsPerCategory.put(category, new ArrayList<>());
	        }
	        questionsPerCategory.get(category).add(question);
	    }
	}
	
	private static Map<String, List<Questions>> questionsPerCategory() {
	    Map<String, List<Questions>> questionsPerCategory = new HashMap<>();

	    File folder = new File("questions");
	    if (!folder.exists()) {
	        System.out.println("Error: no se encontró la carpeta de 'questions'.");
	        return questionsPerCategory; // Retornar un mapa vacío en caso de error
	    }

	    File[] filesList = folder.listFiles();

	    // Obtener las preguntas usando el método getQuestions()
	    List<Questions> questions = getQuestions();

	    int index = 0;
	    for (File file : filesList) {
	        if (file.isFile() && file.getName().endsWith(".txt")) {
	            // Obtener el nombre del archivo como categoría (sin la extensión .txt)
	            String category = file.getName().substring(0, file.getName().length() - 4);

	            // Crear una lista de preguntas para esta categoría si no existe
	            // if (!questionsPerCategory.containsKey(category)) {
	            if (!questionsPerCategory.equals(category)) {
	                questionsPerCategory.put(category, new ArrayList<>());
	            }

	            // Asignar las preguntas a la categoría
	            if (index < questions.size()) {
	                questionsPerCategory.get(category).add(questions.get(index));
	                index++;
	            }
	        }
	    }

	    return questionsPerCategory;
	}
	
	public void addTeam(String name) {
		teams.add(new Teams(name));
	}	
	
	public void startGame() {
		boolean gameOver = false;
		while (!gameOver) {
			for (Teams team: teams) {
				System.out.println("Turno de: " + team.getName());
				makeQuestion(team);
				if (team.gameWon()) {
					Title.title("¡El equipo " + team.getName() + " ha ganado!");
					gameOver = true;
					break;
				}
			}
		}
	}
	
	private static int getRandomInt(int max) {
	    if (max <= 0) {
	        throw new IllegalArgumentException("El valor máximo debe ser mayor que 0");
	    }
	    return random.nextInt(max);
	}
	
	 /**
     * Lógica para hacer una pregunta a un equipo.
     */
		
	private void makeQuestion(Teams team) {
		// Seleccionar una categoría aleatoria que el equipo no haya ganado
        String category = selectRandonCategory(team);
        List<Questions> questionsCategory = questionsPerCategory.get(category);

        // Seleccionar una pregunta al azar de la categoría
        Questions question = questionsCategory.get(getRandomInt(questionsCategory.size()));

        // Mostrar la pregunta y opciones
        System.out.println("Categoría: " + category);
        System.out.println(question.getStatement());

        String[] options = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println(i+1 + ". " + options[i]);
            }
        
        Scanner scanner = new Scanner(System.in);
        int answer = scanner.nextInt();

        if (question.isCorrect(answer)) {
            System.out.println("¡Correcto!");
            team.addCheese(category);
        } else {
            System.out.println("Incorrecto :'(, estás muy lejos de ser un Keepcoding jedi");
        }
        
     // Eliminar la pregunta ya utilizada de la lista de preguntas de la categoría
        // questionsCategory.remove(question);  // ¡Esto es clave para que la pregunta no se repita para otros equipos!

        showScore();
	}
	
	/**
     * Seleccionar una categoría aleatoria para la pregunta, excluyendo las que el equipo ya ha ganado.
     */
    private String selectRandonCategory(Teams team) {
        List<String> categories = new ArrayList<>(questionsPerCategory.keySet());
        categories.removeAll(team.getCheesesObtained());  // Excluir las categorías que ya ganó
        return categories.get(random.nextInt(categories.size()));
    }
    
    private void showScore() {
        for (Teams team : teams) {
            Title.title("Equipo " + team.getName() + ": " + team.getCheesesObtained().size() + " quesitos.");
        }
    }
	
    private static ArrayList<Questions> getQuestions() {
        ArrayList<Questions> list = new ArrayList<>();

        File folder = new File("questions");
        if (!folder.exists()) {
            Title.title("Error al cargar el fichero");
        } else {
            File[] filesList = folder.listFiles();

            for (File file : filesList) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    // Obtener el nombre del archivo como categoría
                    String topicName = file.getName().substring(0, file.getName().length() - 4);

                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        List<String> block = new ArrayList<>();

                        while ((line = br.readLine()) != null) {
                            block.add(line);

                            // Crear preguntas cada vez que se lean 6 líneas
                            if (block.size() == 6) {
                                String questionText = block.get(0);
                                String answer1 = block.get(1);
                                String answer2 = block.get(2);
                                String answer3 = block.get(3);
                                String answer4 = block.get(4);
                                int rightOption = Integer.parseInt(block.get(5));

                                // Crear la pregunta y agregarla a la lista
                                Questions question = new Questions(questionText, new String[]{answer1, answer2, answer3, answer4}, rightOption, topicName);
                                list.add(question);

                                block.clear(); // Limpiar para la siguiente pregunta
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return list;
    }

}
