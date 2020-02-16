package sample;

import java.util.List;
import static sample.MainMenu.gameView;

public class GameController{
    GameModel gameModel = GameModel.getInstance();
    public int getScore(){
        return gameModel.getScore();
    }

    public void loseGame(){
        //TODO: show "YOU LOST" message
        gameModel.setLives(0);
        gameView.loseGame();
    }
    public List<GameObject> generateObjects(){
        return gameModel.generateFruits();
    }
  public void showLivesLabels(){
        gameView.setLifeLabels(3-gameModel.getLives());
  }
  public void checkOutOfBoundries(List<GameObject> list){
        gameModel.checkOutOfBoundries(list);
  }
  public void sliceFruits(List<GameObject> list,double x,double y){
        gameModel.sliceFruits(list,x,y);
  }
  public void setCombo(int val){
        gameModel.setCombo(val);
  }
  public void showScore(){
        gameView.showScore();
  }
  public void updateComboScore(){
        gameModel.updateComboScore();
  }
  public int getGameLevel(){
        return gameModel.getLevel();
  }

  public int getEasyScore(){
        return gameModel.getEasyScore();
  }
  public void setEasyScore(int easyScore){
        gameModel.setEasyScore(easyScore);
  }
    public int getHardScore(){
        return gameModel.getHardScore();
    }
    public void setHardScore(int hardScore){
        gameModel.setHardScore(hardScore);
    }
    public int getInsaneScore(){
        return gameModel.getInsaneScore();
    }
    public void setInsaneScore(int insaneScore){
        gameModel.setInsaneScore(insaneScore);
    }
    public int getArcadeScore(){
        return gameModel.getArcadeScore();
    }
    public void setArcadeScore(int arcadeScore){
        gameModel.setArcadeScore(arcadeScore);
    }

  public void saveGame(){
        gameModel.saveGame();
  }
  public void loadGame(){
        gameModel.loadGame();
  }

}
