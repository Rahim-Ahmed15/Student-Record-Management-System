package studentrecord;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

class Student {
    String id;
    String name;
    double gpa;

    Student(String id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return String.format("ID: %-10s | Name: %-20s | GPA: %.2f", id, name, gpa);
    }
}

class StudentHashTable {
    private Map<String, Student> table = new HashMap<>();

    public void addStudent(Student s) {
        table.put(s.id, s);
    }

    public Student getStudent(String id) {
        return table.get(id);
    }

    public void removeStudent(String id) {
        table.remove(id);
    }

    public Collection<Student> getAllStudents() {
        return table.values();
    }

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Student s : table.values()) {
                bw.write(s.id + "," + s.name + "," + s.gpa);
                bw.newLine();
            }
        }
    }

    public void loadFromFile(String filename) throws IOException {
        table.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    addStudent(new Student(parts[0], parts[1], Double.parseDouble(parts[2])));
                }
            }
        }
    }
}

public class StudentRecordApp extends Application {

    private StudentHashTable database = new StudentHashTable();
    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Record Management System");
        
        // Create a gradient background
        BackgroundFill backgroundFill = new BackgroundFill(
                Color.rgb(240, 240, 240), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);

        // Main container
        BorderPane root = new BorderPane();
        root.setBackground(background);
        root.setPadding(new Insets(20));

        // Header
        Label header = new Label("STUDENT RECORD MANAGEMENT SYSTEM");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        header.setTextFill(Color.rgb(50, 50, 150));
        BorderPane.setAlignment(header, Pos.CENTER);
        root.setTop(header);

        // Center content
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setPadding(new Insets(20));

        // Input fields with labels
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(15);
        inputGrid.setVgap(15);
        inputGrid.setPadding(new Insets(15));
        inputGrid.setBackground(new Background(new BackgroundFill(
                Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
        inputGrid.setEffect(new javafx.scene.effect.DropShadow(5, Color.GRAY));

        Label idLabel = createLabel("Student ID:");
        TextField idField = createTextField();
        GridPane.setConstraints(idLabel, 0, 0);
        GridPane.setConstraints(idField, 1, 0);

        Label nameLabel = createLabel("Name:");
        TextField nameField = createTextField();
        GridPane.setConstraints(nameLabel, 0, 1);
        GridPane.setConstraints(nameField, 1, 1);

        Label gpaLabel = createLabel("GPA:");
        TextField gpaField = createTextField();
        GridPane.setConstraints(gpaLabel, 0, 2);
        GridPane.setConstraints(gpaField, 1, 2);

        inputGrid.getChildren().addAll(idLabel, idField, nameLabel, nameField, gpaLabel, gpaField);

        // Output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setFont(Font.font("Consolas", 14));
        outputArea.setWrapText(true);
        outputArea.setPrefHeight(300);
        outputArea.setStyle("-fx-control-inner-background: #f8f8f8; -fx-text-fill: #333;");
        outputArea.setEffect(new javafx.scene.effect.DropShadow(3, Color.LIGHTGRAY));

        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button addBtn = createStyledButton("Add Student", "#4CAF50");
        Button searchBtn = createStyledButton("Search", "#2196F3");
        Button deleteBtn = createStyledButton("Delete", "#F44336");
        Button showAllBtn = createStyledButton("Show All", "#9C27B0");
        Button saveBtn = createStyledButton("Save", "#607D8B");
        Button loadBtn = createStyledButton("Load", "#FF9800");
        Button filterBtn = createStyledButton("Filter GPA > 3.0", "#009688");
        Button sortByGpaBtn = createStyledButton("Sort by GPA", "#795548");

        buttonBox.getChildren().addAll(addBtn, searchBtn, deleteBtn, showAllBtn, 
                saveBtn, loadBtn, filterBtn, sortByGpaBtn);

        // Button actions
        addBtn.setOnAction(e -> handleAddStudent(idField, nameField, gpaField));
        searchBtn.setOnAction(e -> handleSearchStudent(idField));
        deleteBtn.setOnAction(e -> handleDeleteStudent(idField));
        showAllBtn.setOnAction(e -> handleShowAllStudents());
        saveBtn.setOnAction(e -> handleSave(primaryStage));
        loadBtn.setOnAction(e -> handleLoad(primaryStage));
        filterBtn.setOnAction(e -> handleFilterGPA());
        sortByGpaBtn.setOnAction(e -> handleSortByGPA());

        // Add components to center box
        centerBox.getChildren().addAll(inputGrid, buttonBox, outputArea);
        root.setCenter(centerBox);

        // Status bar
        Label statusBar = new Label("Ready");
        statusBar.setFont(Font.font("Arial", 12));
        statusBar.setTextFill(Color.GRAY);
        statusBar.setPadding(new Insets(10));
        BorderPane.setAlignment(statusBar, Pos.CENTER_RIGHT);
        root.setBottom(statusBar);

        // Create scene and show stage
        Scene scene = new Scene(root, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        label.setTextFill(Color.rgb(70, 70, 70));
        return label;
    }

    private TextField createTextField() {
        TextField field = new TextField();
        field.setFont(Font.font("Arial", 14));
        field.setPrefWidth(250);
        field.setStyle("-fx-padding: 8; -fx-background-radius: 5;");
        return field;
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle(String.format(
                "-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-padding: 8 15; -fx-background-radius: 5; -fx-font-size: 14;", color));
        button.setOnMouseEntered(e -> button.setStyle(String.format(
                "-fx-background-color: derive(%s, -20%%); -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-padding: 8 15; -fx-background-radius: 5; -fx-font-size: 14;", color)));
        button.setOnMouseExited(e -> button.setStyle(String.format(
                "-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-padding: 8 15; -fx-background-radius: 5; -fx-font-size: 14;", color)));
        return button;
    }

    private void handleAddStudent(TextField idField, TextField nameField, TextField gpaField) {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            double gpa = Double.parseDouble(gpaField.getText().trim());
            
            if (id.isEmpty() || name.isEmpty()) {
                outputArea.setText("Error: ID and Name cannot be empty!");
                return;
            }
            
            if (gpa < 0 || gpa > 4.0) {
                outputArea.setText("Error: GPA must be between 0 and 4.0!");
                return;
            }
            
            database.addStudent(new Student(id, name, gpa));
            outputArea.setText(String.format("Student added successfully!\n\n%s", database.getStudent(id)));
            clearFields(idField, nameField, gpaField);
        } catch (NumberFormatException ex) {
            outputArea.setText("Error: Invalid GPA format! Please enter a numeric value.");
        }
    }

    private void handleSearchStudent(TextField idField) {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            outputArea.setText("Error: Please enter a Student ID to search!");
            return;
        }
        
        Student s = database.getStudent(id);
        if (s != null) {
            outputArea.setText("Student found:\n\n" + s.toString());
        } else {
            outputArea.setText("No student found with ID: " + id);
        }
    }

    private void handleDeleteStudent(TextField idField) {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            outputArea.setText("Error: Please enter a Student ID to delete!");
            return;
        }
        
        Student s = database.getStudent(id);
        if (s != null) {
            database.removeStudent(id);
            outputArea.setText("Student deleted successfully:\n\n" + s.toString());
            idField.clear();
        } else {
            outputArea.setText("No student found with ID: " + id);
        }
    }

    private void handleShowAllStudents() {
        Collection<Student> students = database.getAllStudents();
        if (students.isEmpty()) {
            outputArea.setText("No students in the database.");
            return;
        }
        
        StringBuilder sb = new StringBuilder("All Students (" + students.size() + "):\n\n");
        for (Student s : students) {
            sb.append(s.toString()).append("\n\n");
        }
        outputArea.setText(sb.toString());
    }

    private void handleSave(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Student Records");
        fileChooser.setInitialFileName("students.txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showSaveDialog(primaryStage);
        if (selectedFile != null) {
            try {
                database.saveToFile(selectedFile.getAbsolutePath());
                outputArea.setText("Data saved successfully to:\n" + selectedFile.getAbsolutePath());
            } catch (IOException ex) {
                outputArea.setText("Error saving file: " + ex.getMessage());
            }
        }
    }

    private void handleLoad(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Student Record File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            try {
                database.loadFromFile(selectedFile.getAbsolutePath());
                outputArea.setText("Data loaded successfully from:\n" + selectedFile.getAbsolutePath() + 
                                 "\n\nTotal students loaded: " + database.getAllStudents().size());
            } catch (IOException ex) {
                outputArea.setText("Error loading file: " + ex.getMessage());
            }
        }
    }

    private void handleFilterGPA() {
        Collection<Student> students = database.getAllStudents();
        if (students.isEmpty()) {
            outputArea.setText("No students in the database.");
            return;
        }
        
        StringBuilder sb = new StringBuilder("Students with GPA > 3.0:\n\n");
        int count = 0;
        for (Student s : students) {
            if (s.gpa > 3.0) {
                sb.append(s.toString()).append("\n\n");
                count++;
            }
        }
        
        if (count == 0) {
            outputArea.setText("No students found with GPA > 3.0");
        } else {
            outputArea.setText(sb.insert(0, "Found " + count + " students:\n\n").toString());
        }
    }

    private void handleSortByGPA() {
        List<Student> students = new ArrayList<>(database.getAllStudents());
        if (students.isEmpty()) {
            outputArea.setText("No students in the database.");
            return;
        }
        
        students.sort((s1, s2) -> Double.compare(s2.gpa, s1.gpa)); // Descending order
        
        StringBuilder sb = new StringBuilder("Students sorted by GPA (highest first):\n\n");
        for (Student s : students) {
            sb.append(s.toString()).append("\n\n");
        }
        outputArea.setText(sb.toString());
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}