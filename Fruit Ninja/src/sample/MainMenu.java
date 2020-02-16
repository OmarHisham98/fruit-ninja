package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    public static GameView gameView;
    public static GameController gameController;
    public static int level = 0;
    @FXML
    Button classicModeButton;
    @FXML Button arcadeModeButton;
    @FXML ImageView backGroundImage;
    @FXML ImageView characterImage;
    @FXML RadioButton easyButton,hardButton,insaneButton;
    Circle circle = new Circle(100,100,25);
    Invoker invokerSliceSound = new Invoker(new PlaySlicingSound());
    Invoker invokerBackGroundSound = new Invoker(new PlayBackGroundMusic());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FontLoader fontLoader=null;
        try {
            fontLoader = new FontLoader();
        } catch (Exception e) {
            e.printStackTrace();
        }
        classicModeButton.setFont(fontLoader.getFont());
        arcadeModeButton.setFont(fontLoader.getFont());
        easyButton.setFont(fontLoader.getFont());
        hardButton.setFont(fontLoader.getFont());
        insaneButton.setFont(fontLoader.getFont());
        classicModeButton.setTextFill(Color.YELLOW);
        arcadeModeButton.setTextFill(Color.YELLOW);
        easyButton.setTextFill(Color.YELLOW);
        hardButton.setTextFill(Color.YELLOW);
        insaneButton.setTextFill(Color.YELLOW);
        easyButton.setVisible(false);
        hardButton.setVisible(false);
        insaneButton.setVisible(false);
        characterImage.setDisable(true);
        invokerBackGroundSound.press();
        Image backGround = new Image("fruit ninja background.jpg");
        Image character = new Image("Character 1.png");
        backGroundImage.setImage(backGround);
        characterImage.setImage(character);
    }

    public void classicModeButtonClicked(ActionEvent event)throws Exception{
        invokerSliceSound.press();
        easyButton.setVisible(true);
        hardButton.setVisible(true);
        insaneButton.setVisible(true);
        }
    public void arcadeModeButtonClicked(ActionEvent event)throws Exception{
        GameModel gameModel = GameModel.getInstance();
        gameModel.setLevel(4);
        invokerSliceSound.press();
        gameController = new GameController();
        gameView = GameView.getInstance();
        gameView.engineStart();
        gameController.loadGame();
    }
    public void easyButtonClicked(ActionEvent event)throws Exception{
        GameModel gameModel = GameModel.getInstance();
        gameModel.setLevel(1);
        gameController = new GameController();
        gameView = GameView.getInstance();
        gameView.engineStart();
        gameController.loadGame();

    }
    public void hardButtonClicked(ActionEvent event)throws Exception{
        GameModel gameModel = GameModel.getInstance();
        gameModel.setLevel(2);
        gameController = new GameController();
        gameView = GameView.getInstance();
        gameView.engineStart();
        gameController.loadGame();

    }
    public void insaneButtonClicked(ActionEvent event)throws Exception{

        GameModel gameModel = GameModel.getInstance();
        gameModel.setLevel(3);
        gameController = new GameController();
        gameView = GameView.getInstance();
        gameView.engineStart();
        gameController.loadGame();
    }

}