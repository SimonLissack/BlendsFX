/*
 * ---------------
 * BlendCompositeFX.java
 * ---------------
 */

package blendcompositefx;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Simon
 */
public class BlendCompositeFX extends Application {
    private BorderPane root;
    private Group center;
    private Slider s;
    private ImageView a;
    private ImageView b;
    
    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        
        
        // Center
        center = new Group();
        setupCenter();
        
        
        // Bottom
        HBox btm = new HBox(5);
        ComboBox<BlendMode> blends = new ComboBox<BlendMode>();
        blends.getItems().setAll(BlendMode.values());
        blends.valueProperty().addListener((e, old, newVal) ->{setBlendMode(newVal);});
        blends.setValue(BlendMode.values()[0]);
        
        s = new Slider(0,1,0.01);
        s.valueProperty().addListener((e,oldVal,newVal) -> {
            setAlpha(b, newVal.doubleValue());
        });
        
        Button btn = new Button("Snapshot");
        btn.setOnAction(e ->{snapshot();});
        
        btm.getChildren().addAll(blends, s, btn);
        btm.setPadding(new Insets(5));
        
        root.setCenter(center);
        root.setBottom(btm);
        Scene scene = new Scene(root, 450, 500);
        
        stage.setScene(scene);
        stage.show();
    }
    
    private void setupCenter(){
        a = new ImageView(new Image(getClass().getResource("/images/1.png").toExternalForm()));
        b = new ImageView(new Image(getClass().getResource("/images/2.png").toExternalForm()));
        
        a.setPreserveRatio(true);
        b.setPreserveRatio(true);
        
        a.setFitWidth(100);
        b.setFitWidth(100);
        
        a.setX(0);
        a.setY(0);
        b.setX(0);
        b.setY(0);
        
        setAlpha(b, 0.0);
        
        center.getChildren().add(a);
        center.getChildren().add(b);
    }
    
    private void setAlpha(ImageView wi, double a){
        wi.setOpacity(a);
    }
    
    private void setBlendMode(BlendMode newMode){
        b.setBlendMode(newMode);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private void snapshot(){
        Image screen = root.snapshot(null, null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(screen, null), "PNG", new File("output/screens/" + System.currentTimeMillis() + ".png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
