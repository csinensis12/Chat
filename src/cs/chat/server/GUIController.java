package cs.chat.server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class GUIController  extends Thread implements Initializable, Output, Input{
    @FXML private TextField textField;
    @FXML private TextArea textArea;
    @FXML private Button button;

    Server server;

    @Override
    public int getPort() {
        // TODO error when you type anything but int
        return Integer.parseInt(textField.getText().trim());
    }

    @Override
    public void displayLog(String str) {
        System.out.println(str);
        textArea.appendText(str);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server = new Server(this,this);
    }

    @FXML protected void handleSubmitButtonAction(ActionEvent event){
        button.setDisable(true);
        start();
    }

    @Override
    public void run() {
        server.startWorking();
    }
}