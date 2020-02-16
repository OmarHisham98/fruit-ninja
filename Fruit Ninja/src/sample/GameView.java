package sample;


import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.ImageCursor;
import java.util.*;

import static sample.Main.currentStage;


public class GameView  {
    private static GameView instance = null;
    GameController gameController = new GameController();
    GameTimer timer = new GameTimer();
    Background bg=new Background();
    Canvas canvas;
    Group group;
    int level = gameController.getGameLevel();
    List <ImageView> crossesList = new ArrayList<ImageView>();
    Cross cross1  = new Cross();
    Cross cross2  = new Cross();
    Cross cross3  = new Cross();
    ImageView iV1 = new ImageView();
    ImageView iV2 = new ImageView();
    ImageView iV3 = new ImageView();
    GraphicsContext gc;

    int i=0;
    Queue<Point2D> queue = new LinkedList<Point2D>();
    FontLoader fontLoader;
    {
        try {
            fontLoader = new FontLoader();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setFruits(List<GameObject> fruits) {
        this.fruits = fruits;
    }

    List<GameObject>fruits;
    private GameView(){

    }

    public static GameView getInstance(){
        if(instance == null) {
            instance = new GameView();
            return instance;
        }
        else
            return instance;
    }

    public void engineStart(){

        Knife knife = new Knife();
        group = new Group();
        Scene scene = new Scene(group);
        scene.setCursor(new ImageCursor(knife.getImage()));
        canvas=new Canvas(800,600);
        gc = canvas.getGraphicsContext2D();

        group.getChildren().add(canvas);
        group.getChildren().add(crosses());
        Background bg=new Background();
        gc.drawImage(bg.getImage(),0,0);
        timer.runTimer();
        setFruits(gameController.generateObjects());
        draw(gc);
        currentStage.setScene(scene);
        currentStage.show();


        canvas.setOnMouseDragged(e->{
            queue.add(new Point2D(e.getX(),e.getY()));
            if(queue.size()>5){
                queue.remove();
            }
            gameController.sliceFruits(fruits,e.getX(),e.getY());
        });

        canvas.setOnMouseReleased(e->{
            queue.clear();
            gameController.updateComboScore();
        });
    }

    AnimationTimer animationTimer;
    ///NEW GAME CLASS
    public void draw(GraphicsContext gc){

        animationTimer = new AnimationTimer() {

            @Override
            public void handle(long l) {
                int i=0;
                gameController.checkOutOfBoundries(fruits);
                if(fruits.isEmpty()){
                    queue.clear();
                    gameController.updateComboScore();
                    gameController.setCombo(0);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    setFruits(gameController.generateObjects());
                }

                gc.drawImage(bg.getImage(),0,0);
                if(level ==4){
                    gc.fillText(timer.countDown(GameTimer.i),350,30);
                    if(timer.countDown(GameTimer.i).equals("00:0"))
                        loseGame();
                }
                else
                    gc.fillText(timer.getTime(GameTimer.i),350,30);
                gc.setFont(fontLoader.getFont());
                gc.setFill(Color.RED);
                gc.fillText("Score: "+ gameController.getScore(),100,30);
                if(level == 4){

                    if(gameController.getScore() > gameController.getArcadeScore()){

                        gameController.setArcadeScore(gameController.getScore());
                    }
                    gc.fillText("High Score: "+gameController.getArcadeScore(),100,60);
                }
                else if(level == 1){

                    if(gameController.getScore() > gameController.getEasyScore()){

                        gameController.setEasyScore(gameController.getScore());
                    }
                    gc.fillText("High Score: "+gameController.getEasyScore(),100,60);
                }
                else if(level == 2){

                    if(gameController.getScore() > gameController.getHardScore()){

                        gameController.setHardScore(gameController.getScore());
                    }
                    gc.fillText("High Score: "+gameController.getHardScore(),100,60);
                }
                else if(level == 3){

                    if(gameController.getScore() > gameController.getInsaneScore()){

                        gameController.setInsaneScore(gameController.getScore());
                    }
                    gc.fillText("High Score: "+gameController.getInsaneScore(),100,60);
                }

                drawFruits(fruits,gc);
                drawSliceLine(gc);
                updateAllFruits(fruits);

            }
        };
        animationTimer.start();
    }

    ///UPDATE
    public void updateAllFruits(List<GameObject>fruits){
        Iterator<GameObject>it= fruits.iterator();
        while (it.hasNext()) {
            it.next().update();
        }
    }


    public void drawFruits(List<GameObject>fruits,GraphicsContext gc){

        Iterator<GameObject>it= fruits.iterator();
        GameObject fruit;
        while (it.hasNext()) {
            fruit=it.next();
            gc.drawImage(fruit.getImage(),fruit.getX(),fruit.getY());
        }
    }




    public HBox crosses(){

        iV1.setVisible(false);
        iV1.setImage(cross1.getImage());
        iV2.setVisible(false);
        iV2.setImage(cross2.getImage());
        iV3.setVisible(false);
        iV3.setImage(cross3.getImage());
        crossesList.add(iV1);
        crossesList.add(iV2);
        crossesList.add(iV3);
        HBox horizontal = new HBox(10);
        horizontal.getChildren().addAll(iV1,iV2,iV3);
        horizontal.setAlignment(Pos.CENTER);
        horizontal.setPadding(new Insets(15, 12, 15, 600));
        return horizontal;
    }

    public void loseGame(){
        Label label = new Label();
        label.setStyle("-fx-background-color: transparent;");
        label.setText("GAME OVER!");
        label.setMinSize(150,50);
        label.setFont(fontLoader.getGameOverFont());
        label.setTextFill(Color.RED);
        label.setPadding(new Insets(300,300,300,120));
        group.getChildren().add(label);
        Button exit = new Button();
        exit.setStyle("-fx-background-color: transparent;");
        exit.setText("");
        exit.setMinSize(900,700);
        exit.setFont(fontLoader.getGameOverFont());
        exit.setTextFill(Color.GREEN);
        exit.setPadding(new Insets(300,300,300,120));
        timer.resetTime();
        exit.setOnAction(e->{
            System.exit(0);
        });
        group.getChildren().add(exit);
        Invoker invoker2 = new Invoker(new PlayEndGameSound());
        invoker2.press();
        Invoker invoker3 = new Invoker(new PlayBackGroundMusic());
        invoker3.undo();
        gameController.saveGame();
        animationTimer.stop();
    }

    public void drawSliceLine(GraphicsContext gc){
        Point2D point;
        Point2D pointbefore=new Point2D(0,0);
        Iterator<Point2D>it2=queue.iterator();
        while (it2.hasNext()){
            point=it2.next();
            if((pointbefore.getX()!=0&&pointbefore.getY()!=0)&&(Math.abs(pointbefore.getX()-point.getX())>5||Math.abs(pointbefore.getY()-point.getY())>5)){
                drawLine(gc,pointbefore.getX(),point.getX(),pointbefore.getY(),point.getY());
            }
            else {
                gc.fillOval(point.getX(),point.getY(),7,7);}
            pointbefore=point;
        }
    }


    public void drawLine(GraphicsContext gc,double x1,double x2,double y1,double y2){
        double slope=(y2-y1)/(x2-x1);
        double c=y1-slope*x1;
        for (int i = (int)Math.min(x1,x2); i <Math.max(x2,x1); i++) {
            gc.fillOval(i,slope*i+c,7,7);
        }

    }
    public void setLifeLabels(int lives){
        System.out.println(lives);
        for(int x=0;x<3;x++)
            crossesList.get(x).setVisible(false);
        for(int x =0;x<lives;x++)
            crossesList.get(x).setVisible(true);

    }
public void showScore(){
    gc.fillText("Score: "+ gameController.getScore(),100,30);
    if(level == 4)
        gc.fillText("High score: "+ gameController.getArcadeScore(),100,60);
    else if(level == 1)
        gc.fillText("High score: "+ gameController.getEasyScore(),100,60);
    else if(level == 2)
        gc.fillText("High score: "+ gameController.getHardScore(),100,60);
    else if(level == 3)
        gc.fillText("High score: "+ gameController.getInsaneScore(),100,60);
}
}