/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.javafxapp1;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.CustomTextField;
import org.tyaa.javafxapp1.connector.JsonFetchr;
import org.tyaa.javafxapp1.connector.JsonParser;
import org.tyaa.javafxapp1.entity.Achievement;
import org.tyaa.javafxapp1.global.Global;

/**
 *
 * @author student
 */
public class FXMLDocumentController implements Initializable {

    private ObservableList<Achievement> achievements
            = FXCollections.observableArrayList();

    //@FXML
    //private Label label;
    @FXML
    TableView<Achievement> achievementsTable;

    @FXML
    TableColumn<Achievement, Integer> idCol;
    @FXML
    TableColumn<Achievement, String> titleCol;
    @FXML
    TableColumn<Achievement, String> contentCol;

    @FXML
    CustomTextField titleTextField;
    @FXML
    TextArea contentTextField;

    /*@FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //initData();
        // устанавливаем тип и значение которое должно хранится в колонке
        idCol.setCellValueFactory(new PropertyValueFactory<Achievement, Integer>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Achievement, String>("title"));
        contentCol.setCellValueFactory(new PropertyValueFactory<Achievement, String>("content"));

        // заполняем таблицу данными
        achievementsTable.setItems(achievements);

        populateTable();
    }

    // подготавливаем данные для таблицы
    // вы можете получать их с базы данных
    private void initData() {

        achievements.add(new Achievement("title1", "cont1", ""));
        achievements.add(new Achievement("title2", "cont2", ""));
        achievements.add(new Achievement("title2", "cont3", ""));
    }

    private void populateTable() {

        //
        achievements.clear();
        Task task
                = new Task() {
            @Override
            protected Object call() throws Exception {

                String jsonString = "";
                JsonFetchr jsonFetcher = new JsonFetchr();
                JsonParser jsonParser = new JsonParser();
                //Achievement achievement = null;
                //List<Achievement> achievements = new ArrayList<>();

                jsonString = jsonFetcher.fetchByUrl(
                        "http://localhost:8084/WebApp1/FirstServlet?action=all-achievements"
                    );
                System.out.println(jsonString);
                Global.achievements.addAll(jsonParser.parseAchievements(jsonString));
                for (Achievement achievement : Global.achievements) {
                    System.out.println(achievement.getTitle());
                }
                return true;
            }
        };
        
        task.setOnSucceeded(ev -> {
            
            for (Achievement achievement : Global.achievements) {
                System.out.println(achievement.getTitle());
            }
            achievements.addAll(Global.achievements);
        });
        
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void addAchievementAction() {

        //System.out.println("addAchievementAction");
        //System.out.println(titleTextField.getText());
        //System.out.println(contentTextField.getText());
        achievements.add(
            new Achievement(
                titleTextField.getText()
                , contentTextField.getText()
                , ""
            )
        );
    }

    @FXML
    public void delAchievementAction() {

        Achievement selAchievement
                = achievementsTable.getSelectionModel().getSelectedItem();

        if (selAchievement != null) {

            //System.out.println(selAchievement.getId());
            /*Optional<Achievement> delAchievement = achievements.stream()
                    .filter((achievement) -> {
                return (achievement.getId().equals(selAchievement.getId()));
            }).findFirst();*/
            //achievements.remove(delAchievement.get());
            achievements.remove(selAchievement);
        }

    }
}
