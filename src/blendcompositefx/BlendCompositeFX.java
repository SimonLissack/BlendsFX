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
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Simon Lissack
 */
public class BlendCompositeFX extends Application {
    private BorderPane root;
    private Group center;
    private ImageView src;
    private ImageView dst;
    
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
        
        Slider s = new Slider(0,1,0.01);
        s.setTooltip(new Tooltip("Adjust opcaity of overlaying image"));
        s.setValue(1);
        src.opacityProperty().bind(s.valueProperty());
        
        Button snapshot = new Button("Snapshot");
        snapshot.setOnAction(e ->{snapshot();});
        
        btm.getChildren().addAll(blends, s, snapshot);
        btm.setPadding(new Insets(5));
        btm.setAlignment(Pos.CENTER);
        
        root.setCenter(center);
        root.setBottom(btm);
        Scene scene = new Scene(root, 520, 700);
        
        stage.setScene(scene);
        stage.show();
    }
    
    private void setupCenter(){
        src = new ImageView(new Image(getClass().getResource("/images/circle.png").toExternalForm()));
        dst = new ImageView(new Image(getClass().getResource("/images/bar.png").toExternalForm()));
        
        src.setPreserveRatio(true);
        dst.setPreserveRatio(true);
        
        src.setFitWidth(500);
        dst.setFitWidth(500);
        
        center.getChildren().addAll(dst,src);
    }
    
    private void setBlendMode(BlendMode newMode){
        src.setBlendMode(newMode);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private void snapshot(){
        Image screen = center.snapshot(null, null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(screen, null), "PNG", new File("output/screens/" + System.currentTimeMillis() + ".png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
