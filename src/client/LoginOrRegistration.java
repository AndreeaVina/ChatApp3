package client;

import database.Dao;
import database.DataBaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LoginOrRegistration {
    @FXML
    public Button buttonSignIn;
    @FXML
    public Button buttonSignUp;
    @FXML
    public Button getStarted;
    @FXML
    public TextField userName;
    @FXML
    public TextField passWord;
    @FXML
    public TextField regFullName;
    @FXML
    public TextField regUserName;
    @FXML
    public TextField regEmail;
    @FXML
    public TextField regPassword;
    @FXML
    public TextField regPhoneNumber;
    @FXML
    public RadioButton male;
    @FXML
    public RadioButton female;
    @FXML
    public Label success;
    @FXML
    public Label goBack;
    @FXML
    public Label nameExists;
    @FXML
    public Label emailExists;
    @FXML
    public Label controlRegLabel;
    @FXML
    public Label loginNotifier;
    @FXML
    public Pane panelSignIn;
    @FXML
    public Pane panelSignUp;
    public static String username, gender, password, email, phoneNumber, fullName;
    Connection connection = DataBaseConnection.getConnection();

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource().equals(getStarted)) {
            panelSignIn.toFront();
        }
        if (event.getSource().equals(buttonSignUp)) {
            panelSignUp.toFront();
        }
        userName.setText("");
        passWord.setText("");
    }

    @FXML
    private void login() throws SQLException {
        var dao4 = new Dao();
        var user = new User();
        user = dao4.getUserByUserName(connection, userName.getText());
        if (user.getPassword().equals(passWord.getText())) {
            fullName = user.getFullName();
            username = userName.getText();
            password = passWord.getText();
            gender = user.getGender();
            email = user.getEmail();
            phoneNumber = user.getPhoneNumber();
            dao4.updateStatus(connection,username,"activ");
            changeWindow();
        } else loginNotifier.setOpacity(1);
    }

    @FXML
    private void registration() throws SQLException {
        if (!regFullName.getText().equalsIgnoreCase("")
                && !regUserName.getText().equalsIgnoreCase("")
                && !regEmail.getText().equalsIgnoreCase("")
                && !regPassword.getText().equalsIgnoreCase("")
                && !regPhoneNumber.getText().equalsIgnoreCase("")) {
            var dao2 = new Dao();
            if (dao2.searchUserByUserName(connection, regUserName.getText()) == 0) {
                var dao3 = new Dao();
                if (dao3.searchUserByEmail(connection, regEmail.getText()) == 0) {
                    var user = new User();
                    user.setFullName(regFullName.getText());
                    user.setUserName(regUserName.getText());
                    user.setEmail(regEmail.getText());
                    user.setPassword(regPassword.getText());
                    user.setPhoneNumber(regPhoneNumber.getText());
                    if (male.isSelected() == true)
                        user.setGender("male");
                    else user.setGender("female");
                    Dao dao1 = new Dao();
                    dao1.insertUser(connection, user);
                    success.setOpacity(1);
                    goBack.setOpacity(1);
                    resetInfo();
                    emailExists.setOpacity(0);
                    nameExists.setOpacity(0);
                } else {
                    emailExists.setOpacity(1);
                    setOpacity(success, goBack, nameExists, controlRegLabel);
                }
            } else {
                nameExists.setOpacity(1);
                setOpacity(success, goBack, emailExists, controlRegLabel);
            }
        } else {
            controlRegLabel.setOpacity(1);
            setOpacity(success, goBack, emailExists, nameExists);
        }
    }

    private void setOpacity(Label a, Label b, Label c, Label d) {
        if (a.getOpacity() == 1 || b.getOpacity() == 1 || c.getOpacity() == 1 || d.getOpacity() == 1) {
            a.setOpacity(0);
            b.setOpacity(0);
            c.setOpacity(0);
            d.setOpacity(0);
        }
    }

    private void resetInfo() {
        regFullName.setText("");
        regUserName.setText("");
        regEmail.setText("");
        regPassword.setText("");
        regPhoneNumber.setText("");
        male.setSelected(true);
        female.setSelected(false);
    }

    public void changeWindow() {
        try {
            Stage stage = (Stage) userName.getScene().getWindow();
            Parent root = FXMLLoader.load(this.getClass().getResource("ChatRoom.fxml"));
            stage.setScene(new Scene(root, 330, 560));
            stage.setTitle(userName.getText() + "");
            stage.setOnCloseRequest(event -> {
                var dao = new Dao();
                try {
                    dao.updateStatus(connection, username, "deconectat");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                System.exit(0);
            });
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}