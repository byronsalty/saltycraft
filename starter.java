import com.saltylabs.minecraft.Game;
import com.saltylabs.minecraft.MineHandler;

class Starter {
  
  public static void main(String[] args) {
    System.out.println("Hello");


    Game myGame = new Game();
    MineHandler mine = new MineHandler(myGame);
    myGame.setWriteHandler(mine);
    mine.runMinecraft();

  }

}
