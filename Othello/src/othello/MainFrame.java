package othello;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import othello.piece_turn_over.*;
import othello.computer.*;

/**
 * <p>タイトル: Othello Program "ZERO"</p>
 * <p>説明: I want this program to be good at othello!</p>
 * <p>著作権: Copyright (c) 2003-2004 by Sun-Soft</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version Version 0.5
 */

public class MainFrame extends JFrame {
  //ここから自分で定義したもの
  public static JButton piece[] = new JButton[89];//こっちはボタン
  public static int p[] = new int[89];//こっちはデータ
  public static ImageIcon white_img = new ImageIcon(othello.MainFrame.class.getResource("img/white.gif"));
  public static ImageIcon black_img = new ImageIcon(othello.MainFrame.class.getResource("img/black.gif"));
  public static ImageIcon null_img = new ImageIcon(othello.MainFrame.class.getResource("img/null.gif"));
  public static boolean man_select_lock = true;//入力にロックがかかっているか？
  public static boolean game_start = false;//ゲーム中か？
  public static boolean edit_mode = false;
  public static int turn = 1;
  public static int turn_count = 0;
  public static boolean pass_check_before = true;//前回のパスチェックの結果(falseなら前回パスだった)
  public static String player_names[] = new String[2];
  public static piece_turn_over pto = new piece_turn_over();//ひっくり返しチェック　＋ひっくり返し
  public static can_piece_turn_over cpto = new can_piece_turn_over();//ひっくり返しチェックのみ．

  public static String[] kifu_table = {//ゲーム終了時の棋譜表示用．
      "","","","","","","","","","",
      "","a1","b1","c1","d1","e1","f1","g1","h1","",
      "","a2","b2","c2","d2","e2","f2","g2","h2","",
      "","a3","b3","c3","d3","e3","f3","g3","h3","",
      "","a4","b4","c4","d4","e4","f4","g4","h4","",
      "","a5","b5","c5","d5","e5","f5","g5","h5","",
      "","a6","b6","c6","d6","e6","f6","g6","h6","",
      "","a7","b7","c7","d7","e7","f7","g7","h7","",
      "","a8","b8","c8","d8","e8","f8","g8","h8","",};

  public static String kifu="";//棋譜記録用

  /*
    p[]における数値と駒の関係
    1 先手(黒)
   -1　後手(白)
    0　なし
  */
  //ここまで
  JPanel contentPane;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuFileExit = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpAbout = new JMenuItem();
  public static JLabel statusBar = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel program_name_panel = new JPanel();
  JLabel program_name_label = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel bord_panel = new JPanel();
  JMenu jMenu2 = new JMenu();
  JMenuItem jMenuItem1 = new JMenuItem();
  JMenuItem jMenuItem3 = new JMenuItem();
  JMenu jMenu1 = new JMenu();
  JMenuItem jMenuItem2 = new JMenuItem();
  JMenuItem jMenuItem4 = new JMenuItem();
  JCheckBoxMenuItem jCheckBoxMenuItem1 = new JCheckBoxMenuItem();


  //フレームのビルド
  public MainFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //コンポーネントの初期化
  private void jbInit() throws Exception  {
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(270, 330));
    this.setTitle("ZERO");
    statusBar.setText(" ");
    jMenuFile.setText("File");
    jMenuFileExit.setText("Exit");
    jMenuFileExit.addActionListener(new jMenuFileExit_actionPerformed(this));
    jMenuHelp.setText("Help");
    jMenuHelpAbout.setText("About...");
    jMenuHelpAbout.addActionListener(new jMenuHelpAbout_actionPerformed(this));
    program_name_label.setFont(new java.awt.Font("DialogInput", 0, 18));
    program_name_label.setRequestFocusEnabled(true);
    program_name_label.setVerifyInputWhenFocusTarget(true);
    program_name_label.setIconTextGap(4);
    program_name_label.setText("Othello Program \"ZERO\"");

    bord_panel.setLayout(gridBagLayout1);
    jMenu2.setText("Game");
    jMenuFile.addSeparator();
    jMenuItem1.setText("ZERO vs MAN");
    jMenuItem1.addActionListener(new MainFrame_jMenuItem1_actionAdapter(this));
    jMenuItem3.setText("MAN vs ZERO");
    jMenuItem3.addActionListener(new MainFrame_jMenuItem3_actionAdapter(this));
    jMenu1.setText("New game");
    jMenuItem2.setText("ZERO vs ZERO");
    jMenuItem2.addActionListener(new MainFrame_jMenuItem2_actionAdapter(this));
    jMenuItem4.setText("MAN vs MAN");
    jMenuItem4.addActionListener(new MainFrame_jMenuItem4_actionAdapter(this));
    jCheckBoxMenuItem1.setText("Edit board");
    jCheckBoxMenuItem1.addActionListener(new MainFrame_jCheckBoxMenuItem1_actionAdapter(this));
    jMenuFile.add(jMenuFileExit);
    jMenuHelp.add(jMenuHelpAbout);
    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(jMenu2);
    jMenuBar1.add(jMenuHelp);
    this.setJMenuBar(jMenuBar1);
    contentPane.add(statusBar, BorderLayout.SOUTH);
    contentPane.add(program_name_panel, BorderLayout.NORTH);
    program_name_panel.add(program_name_label, null);
    contentPane.add(bord_panel,  BorderLayout.CENTER);
        for(int i=0;i<8;i++){
          for(int j=0;j<8;j++){
            piece[10*(i+1)+j+1]= new JButton();
            bord_panel.add(piece[(10*(i+1)) + (j+1)]
                           , new GridBagConstraints(i, j, 1, 1, 0.0, 0.0
                           ,GridBagConstraints.CENTER, GridBagConstraints.NONE
                           , new Insets(0, 0, 0, 0), 0, 0));
        piece[10*(i+1)+j+1].setMargin(new Insets(0, 0, 0, 0));
        piece[10*(i+1)+j+1].setIcon(null_img);
          }
        }
        piece[11].addActionListener(new MainFrame_piece11_actionAdapter(this));
        piece[12].addActionListener(new MainFrame_piece12_actionAdapter(this));
        piece[13].addActionListener(new MainFrame_piece13_actionAdapter(this));
        piece[14].addActionListener(new MainFrame_piece14_actionAdapter(this));
        piece[15].addActionListener(new MainFrame_piece15_actionAdapter(this));
        piece[16].addActionListener(new MainFrame_piece16_actionAdapter(this));
        piece[17].addActionListener(new MainFrame_piece17_actionAdapter(this));
        piece[18].addActionListener(new MainFrame_piece18_actionAdapter(this));
        piece[21].addActionListener(new MainFrame_piece21_actionAdapter(this));
        piece[22].addActionListener(new MainFrame_piece22_actionAdapter(this));
        piece[23].addActionListener(new MainFrame_piece23_actionAdapter(this));
        piece[24].addActionListener(new MainFrame_piece24_actionAdapter(this));
        piece[25].addActionListener(new MainFrame_piece25_actionAdapter(this));
        piece[26].addActionListener(new MainFrame_piece26_actionAdapter(this));
        piece[27].addActionListener(new MainFrame_piece27_actionAdapter(this));
        piece[28].addActionListener(new MainFrame_piece28_actionAdapter(this));
        piece[31].addActionListener(new MainFrame_piece31_actionAdapter(this));
        piece[32].addActionListener(new MainFrame_piece32_actionAdapter(this));
        piece[33].addActionListener(new MainFrame_piece33_actionAdapter(this));
        piece[34].addActionListener(new MainFrame_piece34_actionAdapter(this));
        piece[35].addActionListener(new MainFrame_piece35_actionAdapter(this));
        piece[36].addActionListener(new MainFrame_piece36_actionAdapter(this));
        piece[37].addActionListener(new MainFrame_piece37_actionAdapter(this));
        piece[38].addActionListener(new MainFrame_piece38_actionAdapter(this));
        piece[41].addActionListener(new MainFrame_piece41_actionAdapter(this));
        piece[42].addActionListener(new MainFrame_piece42_actionAdapter(this));
        piece[43].addActionListener(new MainFrame_piece43_actionAdapter(this));
        piece[44].addActionListener(new MainFrame_piece44_actionAdapter(this));
        piece[45].addActionListener(new MainFrame_piece45_actionAdapter(this));
        piece[46].addActionListener(new MainFrame_piece46_actionAdapter(this));
        piece[47].addActionListener(new MainFrame_piece47_actionAdapter(this));
        piece[48].addActionListener(new MainFrame_piece48_actionAdapter(this));
        piece[51].addActionListener(new MainFrame_piece51_actionAdapter(this));
        piece[52].addActionListener(new MainFrame_piece52_actionAdapter(this));
        piece[53].addActionListener(new MainFrame_piece53_actionAdapter(this));
        piece[54].addActionListener(new MainFrame_piece54_actionAdapter(this));
        piece[55].addActionListener(new MainFrame_piece55_actionAdapter(this));
        piece[56].addActionListener(new MainFrame_piece56_actionAdapter(this));
        piece[57].addActionListener(new MainFrame_piece57_actionAdapter(this));
        piece[58].addActionListener(new MainFrame_piece58_actionAdapter(this));
        piece[61].addActionListener(new MainFrame_piece61_actionAdapter(this));
        piece[62].addActionListener(new MainFrame_piece62_actionAdapter(this));
        piece[63].addActionListener(new MainFrame_piece63_actionAdapter(this));
        piece[64].addActionListener(new MainFrame_piece64_actionAdapter(this));
        piece[65].addActionListener(new MainFrame_piece65_actionAdapter(this));
        piece[66].addActionListener(new MainFrame_piece66_actionAdapter(this));
        piece[67].addActionListener(new MainFrame_piece67_actionAdapter(this));
        piece[68].addActionListener(new MainFrame_piece68_actionAdapter(this));
        piece[71].addActionListener(new MainFrame_piece71_actionAdapter(this));
        piece[72].addActionListener(new MainFrame_piece72_actionAdapter(this));
        piece[73].addActionListener(new MainFrame_piece73_actionAdapter(this));
        piece[74].addActionListener(new MainFrame_piece74_actionAdapter(this));
        piece[75].addActionListener(new MainFrame_piece75_actionAdapter(this));
        piece[76].addActionListener(new MainFrame_piece76_actionAdapter(this));
        piece[77].addActionListener(new MainFrame_piece77_actionAdapter(this));
        piece[78].addActionListener(new MainFrame_piece78_actionAdapter(this));
        piece[81].addActionListener(new MainFrame_piece81_actionAdapter(this));
        piece[82].addActionListener(new MainFrame_piece82_actionAdapter(this));
        piece[83].addActionListener(new MainFrame_piece83_actionAdapter(this));
        piece[84].addActionListener(new MainFrame_piece84_actionAdapter(this));
        piece[85].addActionListener(new MainFrame_piece85_actionAdapter(this));
        piece[86].addActionListener(new MainFrame_piece86_actionAdapter(this));
        piece[87].addActionListener(new MainFrame_piece87_actionAdapter(this));
        piece[88].addActionListener(new MainFrame_piece88_actionAdapter(this));

    jMenu1.add(jMenuItem3);
    jMenu1.add(jMenuItem1);
    jMenu1.add(jMenuItem2);
    jMenu1.add(jMenuItem4);
    jMenu2.add(jCheckBoxMenuItem1);
    jMenu2.add(jMenu1);
  }
  //[ファイル|終了]
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }
  //[ヘルプ|バージョン情報]
  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
    MainFrame_AboutBox dlg = new MainFrame_AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.pack();
    dlg.show();
  }
  //ウィンドウが閉じられたときに終了するようにオーバーライド
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      jMenuFileExit_actionPerformed(null);
    }
  }
  public static void man_choice(int x , int y){
    if(man_select_lock != true){
      if(!edit_mode){//非エディットモード
        if(turn > 0){
          if (pto.main(x, y, turn, black_img)) {
            game_control(10*y+x);
          }
        }else if(turn < 0){
          if (pto.main(x, y, turn, white_img)) {
            game_control(10*y+x);
          }

        }
      }else{//editモード
        if(p[x*10+y]==1){//黒
          p[x*10+y]=-1;
          piece[x*10+y].setIcon(white_img);
        }else if(p[x*10+y]==-1){//白
          p[x*10+y]=0;
          piece[x*10+y].setIcon(null_img);
        }else if(p[x*10+y]==0){//なし
          p[x*10+y]=1;
          piece[x*10+y].setIcon(black_img);
       }
      }
    }
  }
  public static void game_control(int kifu){
    int black_piece=0;
    int white_piece=0;
    boolean pass_check=false;
    man_select_lock=true;

    if(kifu != 0)//kifu　が0だったら，それはさしてない．
    MainFrame.kifu += MainFrame.kifu_table[kifu] + ",";

  for(int x=1;x<=8;x++){
      for(int y=1;y<=8;y++){
        if(p[10*x+y]==1){
          black_piece++;
        }else if(p[10*x+y]==-1){
          white_piece++;
        }
        if(!pass_check)//パスチェック
          pass_check=cpto.main(x,y,turn*-1);//ターン変更前なので*-1
      }
    }
    turn_count = white_piece+black_piece - 4;
    if(64 <= white_piece + black_piece){
      end(black_piece,white_piece);
    }else{
      turn *= -1; //ターン変更
      if(!pass_check){//パスチェックがfalseだと・・・
        System.out.println("You must pass!Go to next turn...");
        if(!pass_check_before && !pass_check){//前回もダメだったら
          end(black_piece,white_piece);
          return;
        }
        pass_check_before = false;//試合終了の悪魔のフラグｗ
        //前回はできて，今回はダメだったというのであれば，
        game_control(0);//強制的に再度ターン変更．両者打てなくなると60になるまで暴走(藁→修正(2004/11/25)
        return;
      }else{
        //今回は大丈夫だったので戻しておく．
        pass_check_before = true;
      }

      if(turn == 1){//黒(先手)
        if(player_names[0].equals("man")){//先手は人間
          man_select_lock=false;
        }else if(player_names[0].equals("zero")){//先手はZERO
          computer thread = new computer();
          thread.setPriority(thread.MIN_PRIORITY);
          thread.start(turn,turn_count);
          thread = null;
        }
        statusBar.setText("TURN:"+player_names[0]+"(Black)  NOW BLACK："+black_piece+" WHITE："+white_piece);
      }else if(turn == -1){//白(後手)
        if(player_names[1].equals("man")){//後手は人間
          man_select_lock=false;
        }else if(player_names[1].equals("zero")){//後手はZERO
          computer thread = new computer();
          thread.setPriority(thread.MIN_PRIORITY);
          thread.start(turn,turn_count);
          thread = null;
        }
      statusBar.setText("TURN:"+player_names[1]+"(White)  NOW BLACK："+black_piece+" WHITE："+white_piece);
      }
    }
/*
   System.out.println("\n--------------------");
    System.out.println("Turn:"+turn_count);
   System.out.println("--------------------");
    for(int x=1;x<=8;x++){
     for(int y=1;y<=8;y++){
       if(p[10*y+x]==1){
         System.out.print("●");
       }else if(p[10*y+x]==-1){
         System.out.print("○");
       }else{
         System.out.print("　");
       }
     }
     System.out.println("");
   }
   System.out.println("--------------------");
   System.out.println("Information");
   System.out.println("--------------------");
   System.out.println("black:"+black_piece+"("+player_names[0]+")");
   System.out.println("white:"+white_piece+"("+player_names[1]+")");
   System.out.println("--------------------");
 */
  }

  public static void end(int b,int w){
    if(w > b){
      statusBar.setText(player_names[1]+" won this game. black:"+b+" white:"+w);
    }else if(b > w){
      statusBar.setText(player_names[0]+" won this game. black:"+b+" white:"+w);
    }else{
      statusBar.setText("Dwaw black:"+b+" white:"+w);
    }
    System.out.println("record:\n"+kifu);
  }
  public static boolean can_piece_turn_over(int this_turn , int x , int y){
    boolean check=false;
    return check;
  }
  public static void game_start(){
    for(int x=1;x<=8;x++){
      for(int y=1;y<=8;y++){
        piece[10*x+y].setIcon(null_img);
        p[10*x+y]=0;
      }
    }
    piece[44].setIcon(white_img);
    piece[55].setIcon(white_img);
    piece[45].setIcon(black_img);
    piece[54].setIcon(black_img);
    p[44]=-1;
    p[55]=-1;
    p[45]=1;
    p[54]=1;
    game_start=true;
    turn_count=0;
    turn=-1;
    kifu = "";
    game_control(0);
  }
  void jMenuItem3_actionPerformed(ActionEvent e) {//man vs zero
    player_names[0]="man";
    player_names[1]="zero";
    game_start();
  }
  void jMenuItem1_actionPerformed(ActionEvent e) {//zero vs man
    player_names[0]="zero";
    player_names[1]="man";
    game_start();
  }

  void jMenuItem2_actionPerformed(ActionEvent e) {//zero vs zero
    player_names[0]="zero";
    player_names[1]="zero";
    game_start();
  }

  void jMenuItem4_actionPerformed(ActionEvent e) {//man vs man
    player_names[0]="man";
    player_names[1]="man";
    game_start();
  }
  void jCheckBoxMenuItem1_actionPerformed(ActionEvent e) {//edit mode
    if(game_start && !man_select_lock){
      if(!edit_mode){
        edit_mode = true;
      }else{
        edit_mode = false;
      }
    }
  }

  void piece11_actionPerformed(ActionEvent e) {
  man_choice(1,1);
  }
  void piece21_actionPerformed(ActionEvent e) {
  man_choice(2,1);
  }
  void piece31_actionPerformed(ActionEvent e) {
  man_choice(3,1);
  }
  void piece41_actionPerformed(ActionEvent e) {
  man_choice(4,1);
  }
  void piece51_actionPerformed(ActionEvent e) {
  man_choice(5,1);
  }
  void piece61_actionPerformed(ActionEvent e) {
  man_choice(6,1);
  }
  void piece71_actionPerformed(ActionEvent e) {
  man_choice(7,1);
  }
  void piece81_actionPerformed(ActionEvent e) {
  man_choice(8,1);
  }
  void piece12_actionPerformed(ActionEvent e) {
  man_choice(1,2);
  }
  void piece22_actionPerformed(ActionEvent e) {
  man_choice(2,2);
  }
  void piece32_actionPerformed(ActionEvent e) {
  man_choice(3,2);
  }
  void piece42_actionPerformed(ActionEvent e) {
  man_choice(4,2);
  }
  void piece52_actionPerformed(ActionEvent e) {
  man_choice(5,2);
  }
  void piece62_actionPerformed(ActionEvent e) {
  man_choice(6,2);
  }
  void piece72_actionPerformed(ActionEvent e) {
  man_choice(7,2);
  }
  void piece82_actionPerformed(ActionEvent e) {
  man_choice(8,2);
  }
  void piece13_actionPerformed(ActionEvent e) {
  man_choice(1,3);
  }
  void piece23_actionPerformed(ActionEvent e) {
  man_choice(2,3);
  }
  void piece33_actionPerformed(ActionEvent e) {
  man_choice(3,3);
  }
  void piece43_actionPerformed(ActionEvent e) {
  man_choice(4,3);
  }
  void piece53_actionPerformed(ActionEvent e) {
  man_choice(5,3);
  }
  void piece63_actionPerformed(ActionEvent e) {
  man_choice(6,3);
  }
  void piece73_actionPerformed(ActionEvent e) {
  man_choice(7,3);
  }
  void piece83_actionPerformed(ActionEvent e) {
  man_choice(8,3);
  }
  void piece14_actionPerformed(ActionEvent e) {
  man_choice(1,4);
  }
  void piece24_actionPerformed(ActionEvent e) {
  man_choice(2,4);
  }
  void piece34_actionPerformed(ActionEvent e) {
  man_choice(3,4);
  }
  void piece44_actionPerformed(ActionEvent e) {
  man_choice(4,4);
  }
  void piece54_actionPerformed(ActionEvent e) {
  man_choice(5,4);
  }
  void piece64_actionPerformed(ActionEvent e) {
  man_choice(6,4);
  }
  void piece74_actionPerformed(ActionEvent e) {
  man_choice(7,4);
  }
  void piece84_actionPerformed(ActionEvent e) {
  man_choice(8,4);
  }
  void piece15_actionPerformed(ActionEvent e) {
  man_choice(1,5);
  }
  void piece25_actionPerformed(ActionEvent e) {
  man_choice(2,5);
  }
  void piece35_actionPerformed(ActionEvent e) {
  man_choice(3,5);
  }
  void piece45_actionPerformed(ActionEvent e) {
  man_choice(4,5);
  }
  void piece55_actionPerformed(ActionEvent e) {
  man_choice(5,5);
  }
  void piece65_actionPerformed(ActionEvent e) {
  man_choice(6,5);
  }
  void piece75_actionPerformed(ActionEvent e) {
  man_choice(7,5);
  }
  void piece85_actionPerformed(ActionEvent e) {
  man_choice(8,5);
  }
  void piece16_actionPerformed(ActionEvent e) {
  man_choice(1,6);
  }
  void piece26_actionPerformed(ActionEvent e) {
  man_choice(2,6);
  }
  void piece36_actionPerformed(ActionEvent e) {
  man_choice(3,6);
  }
  void piece46_actionPerformed(ActionEvent e) {
  man_choice(4,6);
  }
  void piece56_actionPerformed(ActionEvent e) {
  man_choice(5,6);
  }
  void piece66_actionPerformed(ActionEvent e) {
  man_choice(6,6);
  }
  void piece76_actionPerformed(ActionEvent e) {
  man_choice(7,6);
  }
  void piece86_actionPerformed(ActionEvent e) {
  man_choice(8,6);
  }
  void piece17_actionPerformed(ActionEvent e) {
  man_choice(1,7);
  }
  void piece27_actionPerformed(ActionEvent e) {
  man_choice(2,7);
  }
  void piece37_actionPerformed(ActionEvent e) {
  man_choice(3,7);
  }
  void piece47_actionPerformed(ActionEvent e) {
  man_choice(4,7);
  }
  void piece57_actionPerformed(ActionEvent e) {
  man_choice(5,7);
  }
  void piece67_actionPerformed(ActionEvent e) {
  man_choice(6,7);
  }
  void piece77_actionPerformed(ActionEvent e) {
  man_choice(7,7);
  }
  void piece87_actionPerformed(ActionEvent e) {
  man_choice(8,7);
  }
  void piece18_actionPerformed(ActionEvent e) {
  man_choice(1,8);
  }
  void piece28_actionPerformed(ActionEvent e) {
  man_choice(2,8);
  }
  void piece38_actionPerformed(ActionEvent e) {
  man_choice(3,8);
  }
  void piece48_actionPerformed(ActionEvent e) {
  man_choice(4,8);
  }
  void piece58_actionPerformed(ActionEvent e) {
  man_choice(5,8);
  }
  void piece68_actionPerformed(ActionEvent e) {
  man_choice(6,8);
  }
  void piece78_actionPerformed(ActionEvent e) {
  man_choice(7,8);
  }
  void piece88_actionPerformed(ActionEvent e) {
  man_choice(8,8);
  }

}

class jMenuFileExit_actionPerformed implements ActionListener {
  MainFrame adaptee;

  jMenuFileExit_actionPerformed(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuFileExit_actionPerformed(e);
  }
}

class jMenuHelpAbout_actionPerformed implements ActionListener {
  MainFrame adaptee;

  jMenuHelpAbout_actionPerformed(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuHelpAbout_actionPerformed(e);
  }
}
class MainFrame_piece11_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece11_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece11_actionPerformed(e);
  }
}
class MainFrame_piece12_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece12_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece12_actionPerformed(e);
  }
}
class MainFrame_piece13_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece13_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece13_actionPerformed(e);
  }
}
class MainFrame_piece14_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece14_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece14_actionPerformed(e);
  }
}
class MainFrame_piece15_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece15_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece15_actionPerformed(e);
  }
}
class MainFrame_piece16_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece16_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece16_actionPerformed(e);
  }
}
class MainFrame_piece17_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece17_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece17_actionPerformed(e);
  }
}
class MainFrame_piece18_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece18_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece18_actionPerformed(e);
  }
}
class MainFrame_piece21_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece21_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece21_actionPerformed(e);
  }
}
class MainFrame_piece22_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece22_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece22_actionPerformed(e);
  }
}
class MainFrame_piece23_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece23_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece23_actionPerformed(e);
  }
}
class MainFrame_piece24_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece24_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece24_actionPerformed(e);
  }
}
class MainFrame_piece25_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece25_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece25_actionPerformed(e);
  }
}
class MainFrame_piece26_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece26_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece26_actionPerformed(e);
  }
}
class MainFrame_piece27_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece27_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece27_actionPerformed(e);
  }
}
class MainFrame_piece28_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece28_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece28_actionPerformed(e);
  }
}
class MainFrame_piece31_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece31_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece31_actionPerformed(e);
  }
}
class MainFrame_piece32_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece32_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece32_actionPerformed(e);
  }
}
class MainFrame_piece33_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece33_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece33_actionPerformed(e);
  }
}
class MainFrame_piece34_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece34_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece34_actionPerformed(e);
  }
}
class MainFrame_piece35_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece35_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece35_actionPerformed(e);
  }
}
class MainFrame_piece36_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece36_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece36_actionPerformed(e);
  }
}
class MainFrame_piece37_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece37_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece37_actionPerformed(e);
  }
}
class MainFrame_piece38_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece38_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece38_actionPerformed(e);
  }
}
class MainFrame_piece41_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece41_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece41_actionPerformed(e);
  }
}
class MainFrame_piece42_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece42_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece42_actionPerformed(e);
  }
}
class MainFrame_piece43_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece43_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece43_actionPerformed(e);
  }
}
class MainFrame_piece44_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece44_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece44_actionPerformed(e);
  }
}
class MainFrame_piece45_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece45_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece45_actionPerformed(e);
  }
}
class MainFrame_piece46_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece46_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece46_actionPerformed(e);
  }
}
class MainFrame_piece47_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece47_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece47_actionPerformed(e);
  }
}
class MainFrame_piece48_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece48_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece48_actionPerformed(e);
  }
}
class MainFrame_piece51_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece51_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece51_actionPerformed(e);
  }
}
class MainFrame_piece52_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece52_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece52_actionPerformed(e);
  }
}
class MainFrame_piece53_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece53_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece53_actionPerformed(e);
  }
}
class MainFrame_piece54_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece54_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece54_actionPerformed(e);
  }
}
class MainFrame_piece55_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece55_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece55_actionPerformed(e);
  }
}
class MainFrame_piece56_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece56_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece56_actionPerformed(e);
  }
}
class MainFrame_piece57_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece57_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece57_actionPerformed(e);
  }
}
class MainFrame_piece58_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece58_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece58_actionPerformed(e);
  }
}
class MainFrame_piece61_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece61_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece61_actionPerformed(e);
  }
}
class MainFrame_piece62_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece62_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece62_actionPerformed(e);
  }
}
class MainFrame_piece63_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece63_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece63_actionPerformed(e);
  }
}
class MainFrame_piece64_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece64_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece64_actionPerformed(e);
  }
}
class MainFrame_piece65_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece65_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece65_actionPerformed(e);
  }
}
class MainFrame_piece66_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece66_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece66_actionPerformed(e);
  }
}
class MainFrame_piece67_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece67_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece67_actionPerformed(e);
  }
}
class MainFrame_piece68_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece68_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece68_actionPerformed(e);
  }
}
class MainFrame_piece71_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece71_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece71_actionPerformed(e);
  }
}
class MainFrame_piece72_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece72_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece72_actionPerformed(e);
  }
}
class MainFrame_piece73_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece73_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece73_actionPerformed(e);
  }
}
class MainFrame_piece74_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece74_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece74_actionPerformed(e);
  }
}
class MainFrame_piece75_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece75_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece75_actionPerformed(e);
  }
}
class MainFrame_piece76_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece76_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece76_actionPerformed(e);
  }
}
class MainFrame_piece77_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece77_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece77_actionPerformed(e);
  }
}
class MainFrame_piece78_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece78_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece78_actionPerformed(e);
  }
}
class MainFrame_piece81_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece81_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece81_actionPerformed(e);
  }
}
class MainFrame_piece82_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece82_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece82_actionPerformed(e);
  }
}
class MainFrame_piece83_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece83_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece83_actionPerformed(e);
  }
}
class MainFrame_piece84_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece84_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece84_actionPerformed(e);
  }
}
class MainFrame_piece85_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece85_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece85_actionPerformed(e);
  }
}
class MainFrame_piece86_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece86_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece86_actionPerformed(e);
  }
}
class MainFrame_piece87_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece87_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece87_actionPerformed(e);
  }
}
class MainFrame_piece88_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_piece88_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.piece88_actionPerformed(e);
  }
}

class MainFrame_jMenuItem3_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_jMenuItem3_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuItem3_actionPerformed(e);
  }
}

class MainFrame_jMenuItem1_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_jMenuItem1_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuItem1_actionPerformed(e);
  }
}

class MainFrame_jMenuItem2_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_jMenuItem2_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuItem2_actionPerformed(e);
  }
}

class MainFrame_jMenuItem4_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_jMenuItem4_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuItem4_actionPerformed(e);
  }
}

class MainFrame_jCheckBoxMenuItem1_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_jCheckBoxMenuItem1_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuItem1_actionPerformed(e);
  }
}

