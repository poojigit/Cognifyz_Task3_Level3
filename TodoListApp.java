import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TodoListApp extends Application {

    private ObservableList<Task> tasks = FXCollections.observableArrayList();
    private ListView<Task> taskListView = new ListView<>(tasks);
    private TextField taskInputField = new TextField();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("To-Do List Application");

        taskListView.setCellFactory(lv -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                } else {
                    setText(task.isCompleted() ? "[✓] " + task.getDescription() : "[ ] " + task.getDescription());
                }
            }
        });

        Button addButton = new Button("Add Task");
        addButton.setOnAction(e -> addTask());

        Button completeButton = new Button("Mark as Complete");
        completeButton.setOnAction(e -> markTaskComplete());

        Button deleteButton = new Button("Delete Task");
        deleteButton.setOnAction(e -> deleteTask());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(taskInputField, addButton, completeButton, deleteButton, taskListView);

        Scene scene = new Scene(layout, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addTask() {
        String taskDescription = taskInputField.getText();
        if (!taskDescription.isEmpty()) {
            tasks.add(new Task(taskDescription));
            taskInputField.clear();
        }
    }

    private void markTaskComplete() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            selectedTask.setCompleted(true);
            taskListView.refresh();
        }
    }

    private void deleteTask() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            tasks.remove(selectedTask);
        }
    }

    public static class Task {
        private String description;
        private boolean completed;

        public Task(String description) {
            this.description = description;
            this.completed = false;
        }

        public String getDescription() {
            return description;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}