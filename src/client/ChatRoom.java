package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

public class ChatRoom extends Thread implements Initializable {

    @FXML
    public ImageView chatImage;
    @FXML
    public Label clientName;
    @FXML
    public Button profileBtn;
    @FXML
    public Pane profilePanel;
    @FXML
    public Pane chatPanel;
    @FXML
    public TextArea msgRoom;
    @FXML
    public Label profileName;
    @FXML
    public Label profileEmail;
    @FXML
    public Label profileNumber;
    @FXML
    public Label profileGender;
    @FXML
    public Circle circlePic;
    @FXML
    public TextField msgField;
    @FXML
    public TextField fileChoosePath;
    private FileChooser fileChooser;
    private File filePath;
    private boolean saveControl = false;
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;

    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
//                System.out.println(tokens);
                String cmd = tokens[0];
//                System.out.println(cmd);
                StringBuilder fulmsg = new StringBuilder();
                for(int i = 0; i < tokens.length; i++) {
                    fulmsg.append(tokens[i]);
                    System.out.println(tokens[i]);
                }
                System.out.println(fulmsg);
                if (cmd.equalsIgnoreCase(LoginOrRegistration.username + ":")) {
                    continue;
                } else if(fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }
                msgRoom.appendText(msg + "\n");
            }
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = null;
        if(LoginOrRegistration.gender.equalsIgnoreCase("Male"))
            image = new Image("icons/man.png", false);
        else if(LoginOrRegistration.gender.equalsIgnoreCase("Female"))
            image = new Image("icons/female.png", false);
        circlePic.setFill(new ImagePattern(image));
        clientName.setText(LoginOrRegistration.username);
        setProfile();
        connectSocket();
    }
    public void sendMessageByKey(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            send();
        }
    }
    public void send() {
        String msg = msgField.getText();
        writer.println(LoginOrRegistration.username + ": " + msg);
        System.out.println(writer);
        msgRoom.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        msgRoom.appendText("Me: " + msg + "\n");
        msgField.setText("");
        if(msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            writer.println(LoginOrRegistration.username + " left the chat ...  " );
            System.exit(0);
        }
    }
    public void connectSocket() {
        try {
            socket = new Socket("localhost", 8889);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleButtonAction2(ActionEvent event) {
        if (event.getSource().equals(profileBtn)) {
            if(profileBtn.getText().equalsIgnoreCase("Profile")){
                profilePanel.toFront();
                profileBtn.setText("Back");
            }
            else {
                chatPanel.toFront();
                profileBtn.setText("Profile");
            }
        }
    }
    public void chooseImageButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);
        fileChoosePath.setText(filePath.getPath());
        saveControl = true;
    }
    public void saveImage() {
        if (saveControl) {
            try {
                BufferedImage bufferedImage = ImageIO.read(filePath);
                var image = new Image(String.valueOf(filePath.toURI()));
                circlePic.setFill(new ImagePattern(image));
                saveControl = false;
                fileChoosePath.setText("");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    private void setProfile(){
        profileName.setText(LoginOrRegistration.fullName);
        profileEmail.setText(LoginOrRegistration.email);
        profileNumber.setText(LoginOrRegistration.phoneNumber);
        profileGender.setText(LoginOrRegistration.gender);
    }
}