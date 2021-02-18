/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnpm;

import com.gluonhq.charm.glisten.control.CardPane; 
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author ADMIN
 */
public class FXMLDocumentController implements Initializable {
    
    
   @FXML private MediaView mv;
   @FXML private MediaPlayer mp;
   @FXML private Slider slider;
   @FXML private Slider seeSlider;
   @FXML private Button btnPause_Play;
   
   @FXML Text textVolume;
   
    
    private String filePath;
    private int count = 1; // bien dem pause_play
    
    @FXML
    private void ClickMenuItem_Openfile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        filePath = file.toURI().toString();
        
        if (filePath != null) {
             Media m = new Media(filePath);
             mp = new MediaPlayer(m);
             mv.setMediaPlayer(mp);
//                DoubleProperty width = mv.fitWidthProperty();
//                DoubleProperty hight = mv.fitHeightProperty();               
//                width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
//                hight.bind(Bindings.selectDouble(mv.sceneProperty(), "hight"));
             //seeSlider.setMax(mp.getCurrentTime().toSeconds());
             slider.setValue(mp.getVolume() * 0);
             mp.setVolume(0);
             slider.valueProperty().addListener(new InvalidationListener() {
                 @Override
                 public void invalidated(Observable observable) {
                     mp.setVolume(slider.getValue() / 100);
                     double a = slider.getValue();
                     long name = Math.round(a * 10) / 10;
             textVolume.setText(Long.toString(name));
                 }
             });
             
             
             mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                 @Override
                 public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                       seeSlider.setValue(newValue.toSeconds());
                 }              
             });
             
             seeSlider.setOnMouseClicked(new EventHandler<MouseEvent>(){
                 @Override
                 public void handle(MouseEvent event) {
                       mp.seek(Duration.seconds(seeSlider.getValue()));
                 }
                 
             });
            mp.play();
        }
             
}
    public void ChangeVolume(ActionEvent event) {
             
    }
    
    public void ClickButtonPause_Play(ActionEvent event) {     
        if (count == 0) {
            mp.play();
            count = 1;
            btnPause_Play.setText("PAUSE");
        } else {
            mp.pause();   
            count = 0;
            btnPause_Play.setText("PLAY");
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
