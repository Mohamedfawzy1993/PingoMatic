package sample;

import com.sun.deploy.util.StringUtils;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class Controller  {

    @FXML private TextField text1;
    @FXML private TextField text2;
    @FXML private TextField text3;
    @FXML private TextField text4;
//-------------------------------
    @FXML private Circle red1;
    @FXML private Circle red2;
    @FXML private Circle red3;
    @FXML private Circle red4;
//-------------------------------
    @FXML private Circle Green1;
    @FXML private Circle Green2;
    @FXML private Circle Green3;
    @FXML private Circle Green4;
    @FXML private Label label;
//-------------------------------

    public boolean flag = false;
    public boolean[] RED = new boolean[4];
    public boolean[] GREEN = new boolean[4];
    public Timer time ;

    //-------------------------------

    public void startPing(ActionEvent actionEvent) {
        if (flag == true)
            return;

        time = new Timer();

        time.schedule(new TimerTask() {

            @Override
            public void run() {
                flag = true;

                final Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            String[] res = new String[4];
                            res[0] = PingIpAddr(text1.getText());
                            res[1] = PingIpAddr(text2.getText());
                            res[2] = PingIpAddr(text3.getText());
                            res[3] = PingIpAddr(text4.getText());

                            res[0] = res[0].split(" ")[0];
                            res[1] = res[1].split(" ")[0];
                            res[2] = res[2].split(" ")[0];
                            res[3] = res[3].split(" ")[0];

                            for (int x = 0 ; x<=3 ; x++) {
                                if (res[x].intern() == "Reply") {
                                    RED[x] = false;
                                    GREEN[x] = true;
                                } else {
                                    RED[x] = true;
                                    GREEN[x] = false;
                                }
                            }

                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    label.setText("Running");
                                    red1.setVisible(RED[0]);
                                    Green1.setVisible(GREEN[0]);
                                    red2.setVisible(RED[1]);
                                    Green2.setVisible(GREEN[1]);
                                    red3.setVisible(RED[2]);
                                    Green3.setVisible(GREEN[2]);
                                    red4.setVisible(RED[3]);
                                    Green4.setVisible(GREEN[3]);
                                }
                            });



                        }
                        catch (Exception e){
                            Alert alert = new Alert(Alert.AlertType.ERROR, e.toString());
                            alert.showAndWait();
                        }


                        return null;
                    }
                };
                new Thread(task).start();
            }
        }, 0, 2000);


    }







    public void stopPing(ActionEvent actionEvent) {
        time.cancel();
        flag = false;
        label.setText("Stopped");

        red1.setVisible(false);
        Green1.setVisible(false);
        red2.setVisible(false);
        Green2.setVisible(false);
        red3.setVisible(false);
        Green3.setVisible(false);
        red4.setVisible(false);
        Green4.setVisible(false);
    }

    public static String PingIpAddr(String ip) throws IOException
    {   int counter = 0 ;
        ProcessBuilder pb = new ProcessBuilder("ping", ip);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(pb.start().getInputStream()));

        String line;
        while ((line = stdInput.readLine()) != null)
        {   counter++;

            if (counter == 3 ){

                return line;

            }

        }
        return " "    ;
    }


}


