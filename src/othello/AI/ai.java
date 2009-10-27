package othello.AI;
import othello.*;
/**
 * <p>ƒ^ƒCƒgƒ‹: ZERO</p>
 * <p>à–¾: </p>
 * <p>’˜ìŒ : Copyright (c) 2003 RyoHirafuji</p>
 * <p>‰ïĞ–¼: </p>
 * @author –¢“ü—Í
 * @version 1.0
 */

public class ai {
  public static int my_koma;
  public static int b[][]=new int[9][9];
  public static int sentaku[]=new int[2];
  public static int turn;
  public static int isi=Frame.isi;
  public static tujo tujo = new tujo();
  public ai(int t) {
    for (int i = 1; i <= 8; i++) {
        for (int j = 1; j <= 8; j++) {
          b[i][j] = Frame.b[i][j];
        }
      }
      turn = t;
      my_koma = turn + 1;
  }
  public static void main() {
    tujo.tujo(turn);
  }
  public static void game(){
    Frame.sentaku[0]=ai.sentaku[0];
    Frame.sentaku[1]=ai.sentaku[1];
    Frame.game();
  }
}