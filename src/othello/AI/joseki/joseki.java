package othello.AI.joseki;
import othello.AI.*;
import java.io.*;
/**
 * <p>タイトル: ZERO</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2003 RyoHirafuji</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */

public class joseki {
  private static int b[][] = new int[9][9];
  static String hash[] = new String[4];
  private static int x;
  private static int y;
  public joseki() {
  }
  public static void main(String[] args) {
    joseki joseki1 = new joseki();
  }
  public static void ban_copy(){
    for (int i = 1; i <= 8; i++) {
      for (int j = 1; j <= 8; j++) {
        b[i][j] = ai.b[i][j];
      }
    }
  }
  public static void hash_seisei(){
    for (int i = 1; i <= 8; i++) {
      for (int j = 1; j <= 8; j++) {
        hash[0] += String.valueOf(b[j][i]);
      }
    }
    for (int i = 1; i <= 8; i++) {
      for (int j = 1; j <= 8; j++) {
        hash[1] += String.valueOf(b[i][j]);
      }
    }
    for (int i = 8; i >= 1; i--) {
      for (int j = 8; j >= 1; j--) {
        hash[2] += String.valueOf(b[i][j]);
      }
    }
    for (int i = 8; i >= 1; i--) {
      for (int j = 8; j >= 1; j--) {
        hash[3] += String.valueOf(b[j][i]);
      }
    }
  }
  public static boolean serch(int turn){
    hash[0]="";
    hash[1]="";
    hash[2]="";
    hash[3]="";
    boolean check = false;
    try{
      String temp = null;
      ban_copy();
      hash_seisei();
      BufferedReader br = new BufferedReader(new FileReader("joseki/"
          + "joseki" + ".txt"));
      BufferedReader black_br = new BufferedReader(new FileReader("joseki/"
          + "black" + ".txt"));
      BufferedReader white_br = new BufferedReader(new FileReader("joseki/"
          + "white" + ".txt"));
      System.out.println("定跡探索開始！");
      System.out.println("現在:"+hash[0]);
      System.out.println("現在:"+hash[1]);
      System.out.println("現在:"+hash[2]);
      System.out.println("現在:"+hash[3]);
      if(!check && turn == 0){//黒に都合のよい定跡を探す
        temp = black_br.readLine();
        while ( (temp == null || "".equals(temp)) == false) {
          for(int q=0;q<=3;q++){
            if (temp.startsWith(hash[q])) {
              System.out.println("定跡:" + temp);
              int i = temp.indexOf(",", 1);
              int i2 = temp.indexOf(",", i + 1);
              x = Integer.parseInt(temp.substring(i + 1, i2));
              y = Integer.parseInt(temp.substring(i2 + 1, temp.length()));
              if(q==0){
//                x = 8-x+1;
//                y = 8-y+1;
                int tmp = x;
                x = y;
                y = tmp;
              }else if(q == 1){
//                x = 8-x;
//                y = 8-y;
              }else if(q== 2){
                x = 8-x+1;
                y = 8-y+1;
              }else if(q==3){
                int tmp = x;
                x = y;
                y = tmp;
                x = 8-x+1;
                y = 8-y+1;
              }
              check = true;
            }
          }
          temp = black_br.readLine();
        }
      }
      if(!check && turn == 1){//白に都合のよい定跡を探す
        temp = white_br.readLine();
        while ( (temp == null || "".equals(temp)) == false) {
          for(int q=0;q<=3;q++){
            if (temp.startsWith(hash[q])) {
              System.out.println("定跡:"+temp);
              int i = temp.indexOf(",", 1);
              int i2 = temp.indexOf(",", i + 1);
              x = Integer.parseInt(temp.substring(i + 1, i2));
              y = Integer.parseInt(temp.substring(i2 + 1, temp.length()));
              if(q==0){
//                x = 8-x+1;
//                y = 8-y+1;
                int tmp = x;
                x = y;
                y = tmp;
              }else if(q == 1){
//                x = 8-x;
//                y = 8-y;
              }else if(q== 2){
                x = 8-x+1;
                y = 8-y+1;
              }else if(q==3){
                int tmp = x;
                x = y;
                y = tmp;
                x = 8-x+1;
                y = 8-y+1;

              }
              check = true;
            }
          }
          temp = white_br.readLine();
        }
      }
      if(!check){//どちらでもない定跡をさがす
        temp = br.readLine();
        while ( (temp == null || "".equals(temp)) == false) {
          for(int q=0;q<=3;q++){
            if (temp.startsWith(hash[q])) {
              System.out.println("定跡:"+temp);
              int i = temp.indexOf(",", 1);
              int i2 = temp.indexOf(",", i + 1);
              x = Integer.parseInt(temp.substring(i + 1, i2));
              y = Integer.parseInt(temp.substring(i2 + 1, temp.length()));
              if(q==0){
//                x = 8-x+1;
//                y = 8-y+1;
                int tmp = x;
                x = y;
                y = tmp;
              }else if(q == 1){
//                x = 8-x;
//                y = 8-y;
              }else if(q== 2){
                x = 8-x+1;
                y = 8-y+1;
              }else if(q==3){
                int tmp = x;
                x = y;
                y = tmp;
                x = 8-x+1;
                y = 8-y+1;

              }
              check = true;
            }
          }
          temp = br.readLine();
        }
      }
      br.close();
      black_br.close();
      white_br.close();
      if(check){
        System.out.println("結局，x:" + x + "　y:" + y + "をさすことにしました");
      }
      ai.sentaku[0] = x;
      ai.sentaku[1] = y;
    }catch(Exception e){System.out.println("エラー："+e);}
    return check;
  }
}