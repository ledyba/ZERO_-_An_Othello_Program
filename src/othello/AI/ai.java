package othello.AI;
import othello.*;
import othello.AI.shuban.*;
import othello.AI.joban.*;
import othello.AI.tyuban.*;
import othello.AI.joseki.*;
import java.util.*;
import java.io.*;
/**
 * <p>タイトル: ZERO</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2003 RyoHirafuji</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */

public class ai {
  public static int my_koma;
  public static int b[][]=new int[9][9];
  public static int sentaku[]=new int[2];
  public static int turn;
  public static int isi=Frame.isi;
  public static othello.AI.joban.tujo joban = new othello.AI.joban.tujo();
  public static othello.AI.tyuban.tujo tyuban = new othello.AI.tyuban.tujo();
  public static shuban shuban = new shuban();
  public ai(int t) {
    turn = t;
      my_koma = turn + 1;
    }
  public static void main() {
    for (int i = 1; i <= 8; i++) {
      for (int j = 1; j <= 8; j++) {
        b[i][j] = Frame.b[i][j];
      }
    }
    isi = Frame.isi;
    int tyuban_start = 16;
    int shuban_start = 50;
    int joban_hukasa = 6;
    int tyuban_hukasa = 6;
    int shuban_hukasa = 14;
    try {
      Properties prop = new Properties();
      prop.load(new FileInputStream("zero.properties"));
      tyuban_start = Integer.parseInt(prop.getProperty("zero.tyuban_start"));
      shuban_start = Integer.parseInt(prop.getProperty("zero.shuban_start"));
      joban_hukasa = Integer.parseInt(prop.getProperty("zero.joban_hukasa"));
      tyuban_hukasa = Integer.parseInt(prop.getProperty("zero.tyuban_hukasa"));
      shuban_hukasa = Integer.parseInt(prop.getProperty("zero.shuban_hukasa"));
    }catch(Exception e){}
    try{
      if (joseki.serch(turn)) {
        game();
      }
      else {
        System.out.println("定跡が無かったので通常探索");
        if (isi < tyuban_start) {
          System.out.println("石が　" + isi + " < " + tyuban_start + "　なので序盤ルーチン！");
          joban.start(turn, joban_hukasa);
        }
        else if (isi >= tyuban_start && isi < shuban_start) {
          System.out.println("石が　" + tyuban_start + " <= " + isi + " < " +
                             shuban_start + "　なので中盤ルーチン！");
          tyuban.start(turn, tyuban_hukasa);
        }
        else {
          System.out.println("石が　" + isi + " >= " + shuban_start +
                             "　なので終盤ルーチン！");
          shuban.shuban(turn, shuban_hukasa);
        }
      }
    }
    catch (Exception e) {}
  }
   public static void game(){
     System.out.println("turn:"+turn+"　x:"+sentaku[0]+"　y:"+sentaku[1]);
     Frame.sentaku[0]=ai.sentaku[0];
     Frame.sentaku[1]=ai.sentaku[1];
     Frame.game();
   }
 }