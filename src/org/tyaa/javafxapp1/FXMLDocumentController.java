/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.javafxapp1;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import org.controlsfx.control.textfield.CustomTextField;
import org.tyaa.javafxapp1.entity.Achievement;
import org.tyaa.javafxapp1.json.AchievementMessageBodyReader;

/**
 *
 * @author student
 */
public class FXMLDocumentController implements Initializable {
    
    private ObservableList<Achievement> achievements =
            FXCollections.observableArrayList();
    
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
        
        WebTarget clientTarget;
        //
        achievements.clear();
        
        Client client = ClientBuilder.newClient();
        client.register(AchievementMessageBodyReader.class);
        /*if (textFieldSearch.getText().length() > 0) {
            clientTarget = client.target("http://localhost:8080/BackForJavaFx/webresources/customer/search/{beginBy}");
            clientTarget = clientTarget.resolveTemplate("beginBy", textFieldSearch.getText());
        } else {
            clientTarget = client.target("http://localhost:8080/BackForJavaFx/webresources/customer");
        }*/
        clientTarget = client.target("http://localhost:8080/WebApp1/FirstServlet?action=all-achievements-array");
        GenericType<List<Achievement>> listc =
                new GenericType<List<Achievement>>() {
            };
        
        final List<Achievement> achievements = new ArrayList<>();
        //(new Thread(() -> {
            
            //achievements.addAll(clientTarget.request("application/json").get(listc));
        //})).start();
        
        Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(achievements.size());
                            for (Achievement achievement : achievements) {
                                System.out.println(achievement.getTitle());
                            }
                            achievements.addAll(clientTarget.request("application/json").get(listc));
                        }
                    });

        /*for (Achievement a : achievements) {
            achievements.add(a);
            //System.out.println(a.toString());
        }*/
    }

    @FXML
    public void addAchievementAction(){
    
        //System.out.println("addAchievementAction");
        //System.out.println(titleTextField.getText());
        //System.out.println(contentTextField.getText());
        achievements.add(new Achievement(titleTextField.getText(), contentTextField.getText(), ""));
    }
    
    @FXML
    public void delAchievementAction(){
    
        Achievement selAchievement =
            achievementsTable.getSelectionModel().getSelectedItem();
        
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
