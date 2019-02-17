package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Crossword;
import model.CrosswordComparator;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {

    @FXML
    public Button createButtonId;
    @FXML
    public Button sortButtonId;
    @FXML
    public Button deleteButton;
    @FXML
    public Button quitButtonId;
    @FXML
    public Menu filePicker;
    @FXML
    public TextField passwordId;
    @FXML
    public TextField descriptionId;


    @FXML
    public TableView<Crossword> tableView;
    @FXML
    public TableColumn<Crossword, String> passColumnId;
    @FXML
    public TableColumn<Crossword, String> descriptionColumnId;
    @FXML
    public TextField search;


    private Collection<Crossword> list = Files.readAllLines(new File("/Users/pawel/IdeaProjects/homework/Pawel Kumanowski Mid Project/src/crossword.txt")
            .toPath())
            .stream()
            .map(line -> {
                String[] details = line.split(";");
                Crossword cross = new Crossword();
                cross.setPassword(details[0]);
                cross.setDescription(details[1]);
                return cross;
            })
            .collect(Collectors.toList());

    private ObservableList<Crossword> passwordList = FXCollections.observableArrayList(list);


    public MainWindowController() throws IOException {

    }

    FilteredList filter = new FilteredList(passwordList, e -> true);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        filePicker.setOnAction(event -> {
            loadFile();
        });

        createButtonId.setOnAction(event -> {
            addPasswordToFile();

        });

        sortButtonId.setOnAction(event -> {
            sortFileContents();

        });

        deleteButton.setOnAction(event -> {
            deleteSelectedRow();
        });

        quitButtonId.setOnAction(event -> {
            quitApplication();
        });
    }

    public void setMain(Main main, Stage primaryStage) {
    }

    public void addPasswordToFile() {
        Crossword cross = new Crossword();
        cross.setPassword(passwordId.getText());
        cross.setDescription(descriptionId.getText());
        passColumnId.setCellValueFactory(new PropertyValueFactory<>("password"));
        descriptionColumnId.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.getItems().add(cross);

        Writer writer = null;
        List<Crossword> temp = passwordList;
        try {
            File file = new File("/Users/pawel/IdeaProjects/homework/Pawel Kumanowski Mid Project/src/crossword.txt");
            writer = new BufferedWriter(new FileWriter(file));
            for (Crossword crossword : temp) {
                String text = crossword.getPassword() + ";" + crossword.getDescription() + ";" + "\n";
                writer.write(text);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            {
                try {
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sortFileContents() {
        Writer writer = null;
        List<Crossword> temp = passwordList;
        try {
            File file = new File("/Users/pawel/IdeaProjects/homework/Pawel Kumanowski Mid Project/src/crossword.txt");
            writer = new BufferedWriter(new FileWriter(file));
            for (Crossword crossword : temp) {
                String text = crossword.getPassword() + ";" + crossword.getDescription() + ";" + "\n";
                Collections.sort(temp, new CrosswordComparator());
                writer.write(text);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            {
                try {
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void quitApplication() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Closing Application");
        alert.setHeaderText("Are you really sure you want to quit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {
        }
    }

    public void loadFile() {
        passColumnId.setCellValueFactory(data -> data.getValue().passwordProperty());
        descriptionColumnId.setCellValueFactory(data -> data.getValue().descriptionProperty());
        tableView.setItems(passwordList);
    }

    public void search() {
        search.textProperty().addListener((observable, oldValue, newValue) -> {

            filter.setPredicate((Predicate<? super Crossword>) (Crossword cross) -> {


                if (newValue.isEmpty() || newValue == null) {
                    return true;
                } else if (cross.getPassword().contains(newValue)) { //filtrowanie po znakach w password
                    return true;
                } else if (cross.getDescription().contains(newValue)) { //filtrowanie po znakach w description
                    return true;
                } else if (cross.getLength() == 5) { //filrtowanie po długosci hasla
                    return true;
                }
                return false;
            });
        });
        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sort);
    }

    public void deleteSelectedRow() {
        Crossword selectedItem = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(selectedItem);
    }
}
