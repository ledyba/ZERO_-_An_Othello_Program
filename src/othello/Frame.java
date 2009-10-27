package othello;

import othello.AI.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>タイトル: ZERO</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2003 RyoHirafuji</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */

public class Frame extends JFrame {
  public static JButton ban[][]=new JButton[9][9];
  //0=null
  //1=black
  //2=white
  public static int b[][]=new int[9][9];
  static boolean banmen_henshu=false;//盤面編集モード？
  public static boolean game_start=false;//ゲームは始まった？
  public static int isi=4;//石のおかれた数
  public static int win_turn=0;//かった方はどちらですか？
  public static int b_isi=0;
  public static int w_isi=0;
  public static int sasite[][] = new int[65][2];
  public static String kifu = "";
  public static int turn=0;//0が先手，1が後手
  static String[][] kyokumen_text = {//棋譜記録のためのテーブル
      { "","","","","","","","","",},
      { "","a1","b1","c1","d1","e1","f1","g1","h1",},
      { "","a2","b2","c2","d2","e2","f2","g2","h2",},
      { "","a3","b3","c3","d3","e3","f3","g3","h3",},
      { "","a4","b4","c4","d4","e4","f4","g4","h4",},
      { "","a5","b5","c5","d5","e5","f5","g5","h5",},
      { "","a6","b6","c6","d6","e6","f6","g6","h6",},
      { "","a7","b7","c7","d7","e7","f7","g7","h7",},
      { "","a8","b8","c8","d8","e8","f8","g8","h8",},};
  public static int sentaku[]=new int[2];
  public static String sente_name;
  public static String gote_name;
  JPanel contentPane;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuFileExit = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpAbout = new JMenuItem();
  JToolBar jToolBar = new JToolBar();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  ImageIcon image1;
  ImageIcon image2;
  ImageIcon image3;
  public static ImageIcon white_img;
  public static ImageIcon black_img;
  public static ImageIcon null_img;

  static JLabel statusBar = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  JMenu jMenu1 = new JMenu();
  JMenuItem jMenuItem1 = new JMenuItem();
  JCheckBoxMenuItem jCheckBoxMenuItem1 = new JCheckBoxMenuItem();
  JPanel jPanel10 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel6 = new JPanel();
  JPanel jPanel8 = new JPanel();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel5 = new JPanel();
  JPanel jPanel9 = new JPanel();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel11 = new JPanel();
  JLabel program_name = new JLabel();
  BorderLayout borderLayout2 = new BorderLayout();
  //フレームのビルド
  public Frame() {
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
    for(int i=0;i<=8;i++){
      for(int j=0;j<=8;j++){
      ban[i][j]=new JButton();
      }
    }
    image1 = new ImageIcon(othello.Frame.class.getResource("openFile.png"));
    image2 = new ImageIcon(othello.Frame.class.getResource("closeFile.png"));
    image3 = new ImageIcon(othello.Frame.class.getResource("help.png"));
    white_img = new ImageIcon(othello.Frame.class.getResource("img/white.gif"));
    black_img = new ImageIcon(othello.Frame.class.getResource("img/black.gif"));
    null_img = new ImageIcon(othello.Frame.class.getResource("img/null.gif"));

    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(526, 545));
    this.setTitle("ZERO");
    statusBar.setText(" ");
    jMenuFile.setText("ファイル");
    jMenuFileExit.setText("終了");
    jMenuFileExit.addActionListener(new Frame_jMenuFileExit_ActionAdapter(this));
    jMenuHelp.setText("ヘルプ");
    jMenuHelpAbout.setText("バージョン情報");
    jMenuHelpAbout.addActionListener(new Frame_jMenuHelpAbout_ActionAdapter(this));
    jButton1.setIcon(image1);
    jButton1.setToolTipText("ファイルを開く");
    jButton2.setIcon(image2);
    jButton2.setToolTipText("ファイルを閉じる");
    jButton3.setIcon(image3);
    jButton3.setToolTipText("ヘルプ");
    ban[1][1].addActionListener(new Frame_ban11_actionAdapter(this));
    ban[1][2].addActionListener(new Frame_ban12_actionAdapter(this));
    ban[1][3].addActionListener(new Frame_ban13_actionAdapter(this));
    ban[1][4].addActionListener(new Frame_ban14_actionAdapter(this));
    ban[1][5].addActionListener(new Frame_ban15_actionAdapter(this));
    ban[1][6].addActionListener(new Frame_ban16_actionAdapter(this));
    ban[1][7].addActionListener(new Frame_ban17_actionAdapter(this));
    ban[1][8].addActionListener(new Frame_ban18_actionAdapter(this));
    ban[2][1].addActionListener(new Frame_ban21_actionAdapter(this));
    ban[2][2].addActionListener(new Frame_ban22_actionAdapter(this));
    ban[2][3].addActionListener(new Frame_ban23_actionAdapter(this));
    ban[2][4].addActionListener(new Frame_ban24_actionAdapter(this));
    ban[2][5].addActionListener(new Frame_ban25_actionAdapter(this));
    ban[2][6].addActionListener(new Frame_ban26_actionAdapter(this));
    ban[2][7].addActionListener(new Frame_ban27_actionAdapter(this));
    ban[2][8].addActionListener(new Frame_ban28_actionAdapter(this));
    ban[3][1].addActionListener(new Frame_ban31_actionAdapter(this));
    ban[3][2].addActionListener(new Frame_ban32_actionAdapter(this));
    ban[3][3].addActionListener(new Frame_ban33_actionAdapter(this));
    ban[3][4].addActionListener(new Frame_ban34_actionAdapter(this));
    ban[3][5].addActionListener(new Frame_ban35_actionAdapter(this));
    ban[3][6].addActionListener(new Frame_ban36_actionAdapter(this));
    ban[3][7].addActionListener(new Frame_ban37_actionAdapter(this));
    ban[3][8].addActionListener(new Frame_ban38_actionAdapter(this));
    ban[4][1].addActionListener(new Frame_ban41_actionAdapter(this));
    ban[4][2].addActionListener(new Frame_ban42_actionAdapter(this));
    ban[4][3].addActionListener(new Frame_ban43_actionAdapter(this));
    ban[4][4].addActionListener(new Frame_ban44_actionAdapter(this));
    ban[4][5].addActionListener(new Frame_ban45_actionAdapter(this));
    ban[4][6].addActionListener(new Frame_ban46_actionAdapter(this));
    ban[4][7].addActionListener(new Frame_ban47_actionAdapter(this));
    ban[4][8].addActionListener(new Frame_ban48_actionAdapter(this));
    ban[5][1].addActionListener(new Frame_ban51_actionAdapter(this));
    ban[5][2].addActionListener(new Frame_ban52_actionAdapter(this));
    ban[5][3].addActionListener(new Frame_ban53_actionAdapter(this));
    ban[5][4].addActionListener(new Frame_ban54_actionAdapter(this));
    ban[5][5].addActionListener(new Frame_ban55_actionAdapter(this));
    ban[5][6].addActionListener(new Frame_ban56_actionAdapter(this));
    ban[5][7].addActionListener(new Frame_ban57_actionAdapter(this));
    ban[5][8].addActionListener(new Frame_ban58_actionAdapter(this));
    ban[6][1].addActionListener(new Frame_ban61_actionAdapter(this));
    ban[6][2].addActionListener(new Frame_ban62_actionAdapter(this));
    ban[6][3].addActionListener(new Frame_ban63_actionAdapter(this));
    ban[6][4].addActionListener(new Frame_ban64_actionAdapter(this));
    ban[6][5].addActionListener(new Frame_ban65_actionAdapter(this));
    ban[6][6].addActionListener(new Frame_ban66_actionAdapter(this));
    ban[6][7].addActionListener(new Frame_ban67_actionAdapter(this));
    ban[6][8].addActionListener(new Frame_ban68_actionAdapter(this));
    ban[7][1].addActionListener(new Frame_ban71_actionAdapter(this));
    ban[7][2].addActionListener(new Frame_ban72_actionAdapter(this));
    ban[7][3].addActionListener(new Frame_ban73_actionAdapter(this));
    ban[7][4].addActionListener(new Frame_ban74_actionAdapter(this));
    ban[7][5].addActionListener(new Frame_ban75_actionAdapter(this));
    ban[7][6].addActionListener(new Frame_ban76_actionAdapter(this));
    ban[7][7].addActionListener(new Frame_ban77_actionAdapter(this));
    ban[7][8].addActionListener(new Frame_ban78_actionAdapter(this));
    ban[8][1].addActionListener(new Frame_ban81_actionAdapter(this));
    ban[8][2].addActionListener(new Frame_ban82_actionAdapter(this));
    ban[8][3].addActionListener(new Frame_ban83_actionAdapter(this));
    ban[8][4].addActionListener(new Frame_ban84_actionAdapter(this));
    ban[8][5].addActionListener(new Frame_ban85_actionAdapter(this));
    ban[8][6].addActionListener(new Frame_ban86_actionAdapter(this));
    ban[8][7].addActionListener(new Frame_ban87_actionAdapter(this));
    ban[8][8].addActionListener(new Frame_ban88_actionAdapter(this));
    jMenu1.setText("ゲーム");
    jMenuItem1.setText("開始");
    jMenuItem1.addActionListener(new Frame_jMenuItem1_actionAdapter(this));
    jCheckBoxMenuItem1.setToolTipText("");
    jCheckBoxMenuItem1.setVerifyInputWhenFocusTarget(true);
    jCheckBoxMenuItem1.setText("盤面編集");
    jCheckBoxMenuItem1.setState(false);
    jCheckBoxMenuItem1.addActionListener(new Frame_jCheckBoxMenuItem1_actionAdapter(this));
    jToolBar.setOrientation(JToolBar.HORIZONTAL);
    jToolBar.setEnabled(true);
    jToolBar.setDebugGraphicsOptions(0);
    jToolBar.setBorderPainted(true);
    jToolBar.setFloatable(true);
    program_name.setFont(new java.awt.Font("DialogInput", 1, 30));
    program_name.setText("おせろぷろぐらむ　ZERO");
    jPanel10.setLayout(borderLayout2);
    jToolBar.add(jButton1);
    jToolBar.add(jButton2);
    jToolBar.add(jButton3);
    jMenuFile.add(jCheckBoxMenuItem1);
    jMenuFile.addSeparator();
    jMenuFile.add(jMenuFileExit);
    jMenuHelp.add(jMenuHelpAbout);
    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(jMenu1);
    jMenuBar1.add(jMenuHelp);
    this.setJMenuBar(jMenuBar1);
    contentPane.add(jToolBar,  BorderLayout.NORTH);
    contentPane.add(statusBar, BorderLayout.SOUTH);
    contentPane.add(jPanel10, BorderLayout.CENTER);
    jPanel10.add(jPanel11, BorderLayout.NORTH);
    jPanel11.add(program_name, null);
    jPanel10.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jPanel2, null);
    jPanel1.add(jPanel3, null);
    jPanel1.add(jPanel4, null);
    jPanel1.add(jPanel5, null);
    jPanel1.add(jPanel6, null);
    jPanel1.add(jPanel7, null);
    jPanel1.add(jPanel8, null);
    jPanel1.add(jPanel9, null);
    jMenu1.add(jMenuItem1);
    for(int i=8;i >= 1;i--){
      jPanel2.add(ban[i][1], null);
      jPanel3.add(ban[i][2], null);
      jPanel4.add(ban[i][3], null);
      jPanel5.add(ban[i][4], null);
      jPanel6.add(ban[i][5], null);
      jPanel7.add(ban[i][6], null);
      jPanel8.add(ban[i][7], null);
      jPanel9.add(ban[i][8], null);
    }
    for(int i=1;i<=8;i++){
      for(int j=1;j<=8;j++){
        b[i][j]=0;
        ban[i][j].setIcon(null_img);
      }
    }
  }
  //選択
  public void sentaku(){
    if(!banmen_henshu && game_start){
      Frame_hikkuri Frame_hikkuri = new Frame_hikkuri();
      System.out.println("turn:" + turn + "　" + sentaku[0] + "," + sentaku[1]);
      if (b[sentaku[0]][sentaku[1]] == 0) {
        boolean rule_check = Frame_rule.rule(sentaku[0], sentaku[1], turn);
        if (game_start && rule_check) {
          b[sentaku[0]][sentaku[1]] = turn + 1;
          if (turn == 0) {
            ban[sentaku[0]][sentaku[1]].setIcon(black_img);
          }
          else {
            ban[sentaku[0]][sentaku[1]].setIcon(white_img);
          }
          game();
        }
      }
    }else if(banmen_henshu){
      if(b[sentaku[0]][sentaku[1]] == 0){
        ban[sentaku[0]][sentaku[1]].setIcon(black_img);
        b[sentaku[0]][sentaku[1]] = 1;
      }else if(b[sentaku[0]][sentaku[1]] == 1){
        ban[sentaku[0]][sentaku[1]].setIcon(white_img);
        b[sentaku[0]][sentaku[1]] = 2;
      }else if(b[sentaku[0]][sentaku[1]] == 2){
        ban[sentaku[0]][sentaku[1]].setIcon(null_img);
        b[sentaku[0]][sentaku[1]] = 0;
      }
    }
  }

  public static void game(){
    Frame_hikkuri.rule(sentaku[0] , sentaku[1] , turn);
    w_isi=0;
    b_isi=0;
    int t=0;
    String tmp_name="";
    boolean tmp_check=false;
    boolean end_check=false;
    switch (turn) {
      case 1:
        t=0;
        break;
      case 0:
        t=1;
        break;
    }
    for (int i = 1; i <= 8; i++) {
      for (int j = 8; j >= 1; j--) {
        switch (b[j][i]) {
          case 1:
            b_isi++;
            break;
          case 2:
            w_isi++;
            break;
        }
        System.out.print(b[j][i] + ",");
        if(tmp_check==false){
          tmp_check = Frame_rule.rule(i, j ,t);
        }if(!end_check){
          end_check = Frame_rule.rule(i, j ,t);
        }if(end_check == false){
          end_check = Frame_rule.rule(i, j ,turn);
        }
      }
      System.out.print("\n");
    }
    isi = b_isi + w_isi;
    sasite[isi][0] = sentaku[0];
    sasite[isi][1] = sentaku[1];
    System.out.println("turn:"+t+"　rule:"+tmp_check+"　石:"+isi);
    System.out.println("終了チェック:"+end_check);
    if(isi >= 64 || !end_check){//石が64個置かれたら,そしてパスしても打てなかったら
      game_start=false;
      end_hantei();
    }else{
      if (w_isi == 0 || b_isi == 0) {
        game_start = false;
        end_hantei();
      }
      else {
        if (game_start) {
          if (tmp_check == false) {
            System.out.println("おける場所が無いのでパスします");
            pass pass = new pass();
            pass.show();
            switch (turn) {
              case 0:
                turn = 1;
                break;
              case 1:
                turn = 0;
                break;
            }
          }
          if (turn == 0) {
            tmp_name = gote_name + "(White)";
            turn = 1;
          }
          else {
            tmp_name = sente_name + "(Black)";
            Frame.turn = 0;
          }
          statusBar.setText("現在のターン:" + tmp_name + "　Black:" + b_isi + "/" +
                            isi +
                            "　White" + w_isi + "/" + isi);
          if (turn == 0) {
            if (sente_name.equals("ZERO")) { //パソコン
              System.out.println("探索を開始します"+"　ターン：黒");
              ai ai = new ai(turn);
              ai.main();
              ai = null;
            }
            else {
            }
          }
          else {
            if (gote_name.equals("ZERO")) { //パソコン
              System.out.println("探索を開始します"+"　ターン：白");
              ai ai = new ai(turn);
              ai.main();
              ai = null;
            }
            else {
            }
          }
        }
      }
    }
  }
  public static void start(String tsente_name , String tgote_name){
    sente_name=tsente_name;
    gote_name=tgote_name;
    Frame.turn = 0;
    isi=4;
    for(int i=1;i<=8;i++){
      for(int j=1;j<=8;j++){
        b[i][j]=0;
        ban[i][j].setIcon(null_img);
      }
    }
    b[5][4]=2;
    ban[5][4].setIcon(white_img);
    b[4][5]=2;
    ban[4][5].setIcon(white_img);
    b[4][4]=1;
    ban[4][4].setIcon(black_img);
    b[5][5]=1;
    ban[5][5].setIcon(black_img);
    statusBar.setText("現在のターン:"+sente_name+"(Black)　Black:2/4　White:2/4");
    if(sente_name.equals("ZERO")){
      ai ai = new ai(turn);
      ai.main();
      ai=null;
    }
  }

  public static void end_hantei(){
    shouhai shouhai = new shouhai();
    shouhai.show();
    System.out.println("Black:"+b_isi+"　対　"+"White:"+w_isi);
    System.out.print("\n棋譜：");
    kifu = "";
    for(int i = 0;i<=64;i++){
      if(sasite[i][0] == 0 || sasite[i][1] == 0){
      }else{
        kifu = kifu + kyokumen_text[sasite[i][1]][8 - sasite[i][0] + 1];
      }
    }
    System.out.println(kifu);
    game_start=false;
  }
  //[ファイル|終了]
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }
  //[ヘルプ|バージョン情報]
  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
    Frame_AboutBox dlg = new Frame_AboutBox(this);
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
  void ban11_actionPerformed(ActionEvent e) {
  sentaku[0]=1;
  sentaku[1]=1;
  sentaku();
  }
  void ban21_actionPerformed(ActionEvent e) {
  sentaku[0]=2;
  sentaku[1]=1;
  sentaku();
  }
  void ban31_actionPerformed(ActionEvent e) {
  sentaku[0]=3;
  sentaku[1]=1;
  sentaku();
  }
  void ban41_actionPerformed(ActionEvent e) {
  sentaku[0]=4;
  sentaku[1]=1;
  sentaku();
  }
  void ban51_actionPerformed(ActionEvent e) {
  sentaku[0]=5;
  sentaku[1]=1;
  sentaku();
  }
  void ban61_actionPerformed(ActionEvent e) {
  sentaku[0]=6;
  sentaku[1]=1;
  sentaku();
  }
  void ban71_actionPerformed(ActionEvent e) {
  sentaku[0]=7;
  sentaku[1]=1;
  sentaku();
  }
  void ban81_actionPerformed(ActionEvent e) {
  sentaku[0]=8;
  sentaku[1]=1;
  sentaku();
  }
  void ban12_actionPerformed(ActionEvent e) {
  sentaku[0]=1;
  sentaku[1]=2;
  sentaku();
  }
  void ban22_actionPerformed(ActionEvent e) {
  sentaku[0]=2;
  sentaku[1]=2;
  sentaku();
  }
  void ban32_actionPerformed(ActionEvent e) {
  sentaku[0]=3;
  sentaku[1]=2;
  sentaku();
  }
  void ban42_actionPerformed(ActionEvent e) {
  sentaku[0]=4;
  sentaku[1]=2;
  sentaku();
  }
  void ban52_actionPerformed(ActionEvent e) {
  sentaku[0]=5;
  sentaku[1]=2;
  sentaku();
  }
  void ban62_actionPerformed(ActionEvent e) {
  sentaku[0]=6;
  sentaku[1]=2;
  sentaku();
  }
  void ban72_actionPerformed(ActionEvent e) {
  sentaku[0]=7;
  sentaku[1]=2;
  sentaku();
  }
  void ban82_actionPerformed(ActionEvent e) {
  sentaku[0]=8;
  sentaku[1]=2;
  sentaku();
  }
  void ban13_actionPerformed(ActionEvent e) {
  sentaku[0]=1;
  sentaku[1]=3;
  sentaku();
  }
  void ban23_actionPerformed(ActionEvent e) {
  sentaku[0]=2;
  sentaku[1]=3;
  sentaku();
  }
  void ban33_actionPerformed(ActionEvent e) {
  sentaku[0]=3;
  sentaku[1]=3;
  sentaku();
  }
  void ban43_actionPerformed(ActionEvent e) {
  sentaku[0]=4;
  sentaku[1]=3;
  sentaku();
  }
  void ban53_actionPerformed(ActionEvent e) {
  sentaku[0]=5;
  sentaku[1]=3;
  sentaku();
  }
  void ban63_actionPerformed(ActionEvent e) {
  sentaku[0]=6;
  sentaku[1]=3;
  sentaku();
  }
  void ban73_actionPerformed(ActionEvent e) {
  sentaku[0]=7;
  sentaku[1]=3;
  sentaku();
  }
  void ban83_actionPerformed(ActionEvent e) {
  sentaku[0]=8;
  sentaku[1]=3;
  sentaku();
  }
  void ban14_actionPerformed(ActionEvent e) {
  sentaku[0]=1;
  sentaku[1]=4;
  sentaku();
  }
  void ban24_actionPerformed(ActionEvent e) {
  sentaku[0]=2;
  sentaku[1]=4;
  sentaku();
  }
  void ban34_actionPerformed(ActionEvent e) {
  sentaku[0]=3;
  sentaku[1]=4;
  sentaku();
  }
  void ban44_actionPerformed(ActionEvent e) {
  sentaku[0]=4;
  sentaku[1]=4;
  sentaku();
  }
  void ban54_actionPerformed(ActionEvent e) {
  sentaku[0]=5;
  sentaku[1]=4;
  sentaku();
  }
  void ban64_actionPerformed(ActionEvent e) {
  sentaku[0]=6;
  sentaku[1]=4;
  sentaku();
  }
  void ban74_actionPerformed(ActionEvent e) {
  sentaku[0]=7;
  sentaku[1]=4;
  sentaku();
  }
  void ban84_actionPerformed(ActionEvent e) {
  sentaku[0]=8;
  sentaku[1]=4;
  sentaku();
  }
  void ban15_actionPerformed(ActionEvent e) {
  sentaku[0]=1;
  sentaku[1]=5;
  sentaku();
  }
  void ban25_actionPerformed(ActionEvent e) {
  sentaku[0]=2;
  sentaku[1]=5;
  sentaku();
  }
  void ban35_actionPerformed(ActionEvent e) {
  sentaku[0]=3;
  sentaku[1]=5;
  sentaku();
  }
  void ban45_actionPerformed(ActionEvent e) {
  sentaku[0]=4;
  sentaku[1]=5;
  sentaku();
  }
  void ban55_actionPerformed(ActionEvent e) {
  sentaku[0]=5;
  sentaku[1]=5;
  sentaku();
  }
  void ban65_actionPerformed(ActionEvent e) {
  sentaku[0]=6;
  sentaku[1]=5;
  sentaku();
  }
  void ban75_actionPerformed(ActionEvent e) {
  sentaku[0]=7;
  sentaku[1]=5;
  sentaku();
  }
  void ban85_actionPerformed(ActionEvent e) {
  sentaku[0]=8;
  sentaku[1]=5;
  sentaku();
  }
  void ban16_actionPerformed(ActionEvent e) {
  sentaku[0]=1;
  sentaku[1]=6;
  sentaku();
  }
  void ban26_actionPerformed(ActionEvent e) {
  sentaku[0]=2;
  sentaku[1]=6;
  sentaku();
  }
  void ban36_actionPerformed(ActionEvent e) {
  sentaku[0]=3;
  sentaku[1]=6;
  sentaku();
  }
  void ban46_actionPerformed(ActionEvent e) {
  sentaku[0]=4;
  sentaku[1]=6;
  sentaku();
  }
  void ban56_actionPerformed(ActionEvent e) {
  sentaku[0]=5;
  sentaku[1]=6;
  sentaku();
  }
  void ban66_actionPerformed(ActionEvent e) {
  sentaku[0]=6;
  sentaku[1]=6;
  sentaku();
  }
  void ban76_actionPerformed(ActionEvent e) {
  sentaku[0]=7;
  sentaku[1]=6;
  sentaku();
  }
  void ban86_actionPerformed(ActionEvent e) {
  sentaku[0]=8;
  sentaku[1]=6;
  sentaku();
  }
  void ban17_actionPerformed(ActionEvent e) {
  sentaku[0]=1;
  sentaku[1]=7;
  sentaku();
  }
  void ban27_actionPerformed(ActionEvent e) {
  sentaku[0]=2;
  sentaku[1]=7;
  sentaku();
  }
  void ban37_actionPerformed(ActionEvent e) {
  sentaku[0]=3;
  sentaku[1]=7;
  sentaku();
  }
  void ban47_actionPerformed(ActionEvent e) {
  sentaku[0]=4;
  sentaku[1]=7;
  sentaku();
  }
  void ban57_actionPerformed(ActionEvent e) {
  sentaku[0]=5;
  sentaku[1]=7;
  sentaku();
  }
  void ban67_actionPerformed(ActionEvent e) {
  sentaku[0]=6;
  sentaku[1]=7;
  sentaku();
  }
  void ban77_actionPerformed(ActionEvent e) {
  sentaku[0]=7;
  sentaku[1]=7;
  sentaku();
  }
  void ban87_actionPerformed(ActionEvent e) {
  sentaku[0]=8;
  sentaku[1]=7;
  sentaku();
  }
  void ban18_actionPerformed(ActionEvent e) {
  sentaku[0]=1;
  sentaku[1]=8;
  sentaku();
  }
  void ban28_actionPerformed(ActionEvent e) {
  sentaku[0]=2;
  sentaku[1]=8;
  sentaku();
  }
  void ban38_actionPerformed(ActionEvent e) {
  sentaku[0]=3;
  sentaku[1]=8;
  sentaku();
  }
  void ban48_actionPerformed(ActionEvent e) {
  sentaku[0]=4;
  sentaku[1]=8;
  sentaku();
  }
  void ban58_actionPerformed(ActionEvent e) {
  sentaku[0]=5;
  sentaku[1]=8;
  sentaku();
  }
  void ban68_actionPerformed(ActionEvent e) {
  sentaku[0]=6;
  sentaku[1]=8;
  sentaku();
  }
  void ban78_actionPerformed(ActionEvent e) {
  sentaku[0]=7;
  sentaku[1]=8;
  sentaku();
  }
  void ban88_actionPerformed(ActionEvent e) {
  sentaku[0]=8;
  sentaku[1]=8;
  sentaku();
  }

  void jMenuItem1_actionPerformed(ActionEvent e) {
    if(!banmen_henshu){
      game game = new game();
      game.show();
    }
  }

  void jMenuItem2_actionPerformed(ActionEvent e) {
  }

  void jCheckBoxMenuItem1_actionPerformed(ActionEvent e) {
      banmen_henshu = !banmen_henshu;
      game_start = !game_start;
  }
}

class Frame_jMenuFileExit_ActionAdapter implements ActionListener {
  Frame adaptee;

  Frame_jMenuFileExit_ActionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuFileExit_actionPerformed(e);
  }
}

class Frame_jMenuHelpAbout_ActionAdapter implements ActionListener {
  Frame adaptee;

  Frame_jMenuHelpAbout_ActionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuHelpAbout_actionPerformed(e);
  }
}

class Frame_ban11_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban11_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban11_actionPerformed(e);
  }
}
class Frame_ban12_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban12_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban12_actionPerformed(e);
  }
}
class Frame_ban13_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban13_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban13_actionPerformed(e);
  }
}
class Frame_ban14_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban14_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban14_actionPerformed(e);
  }
}
class Frame_ban15_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban15_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban15_actionPerformed(e);
  }
}
class Frame_ban16_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban16_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban16_actionPerformed(e);
  }
}
class Frame_ban17_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban17_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban17_actionPerformed(e);
  }
}
class Frame_ban18_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban18_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban18_actionPerformed(e);
  }
}
class Frame_ban21_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban21_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban21_actionPerformed(e);
  }
}
class Frame_ban22_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban22_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban22_actionPerformed(e);
  }
}
class Frame_ban23_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban23_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban23_actionPerformed(e);
  }
}
class Frame_ban24_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban24_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban24_actionPerformed(e);
  }
}
class Frame_ban25_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban25_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban25_actionPerformed(e);
  }
}
class Frame_ban26_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban26_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban26_actionPerformed(e);
  }
}
class Frame_ban27_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban27_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban27_actionPerformed(e);
  }
}
class Frame_ban28_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban28_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban28_actionPerformed(e);
  }
}
class Frame_ban31_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban31_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban31_actionPerformed(e);
  }
}
class Frame_ban32_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban32_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban32_actionPerformed(e);
  }
}
class Frame_ban33_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban33_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban33_actionPerformed(e);
  }
}
class Frame_ban34_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban34_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban34_actionPerformed(e);
  }
}
class Frame_ban35_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban35_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban35_actionPerformed(e);
  }
}
class Frame_ban36_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban36_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban36_actionPerformed(e);
  }
}
class Frame_ban37_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban37_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban37_actionPerformed(e);
  }
}
class Frame_ban38_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban38_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban38_actionPerformed(e);
  }
}
class Frame_ban41_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban41_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban41_actionPerformed(e);
  }
}
class Frame_ban42_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban42_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban42_actionPerformed(e);
  }
}
class Frame_ban43_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban43_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban43_actionPerformed(e);
  }
}
class Frame_ban44_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban44_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban44_actionPerformed(e);
  }
}
class Frame_ban45_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban45_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban45_actionPerformed(e);
  }
}
class Frame_ban46_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban46_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban46_actionPerformed(e);
  }
}
class Frame_ban47_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban47_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban47_actionPerformed(e);
  }
}
class Frame_ban48_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban48_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban48_actionPerformed(e);
  }
}
class Frame_ban51_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban51_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban51_actionPerformed(e);
  }
}
class Frame_ban52_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban52_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban52_actionPerformed(e);
  }
}
class Frame_ban53_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban53_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban53_actionPerformed(e);
  }
}
class Frame_ban54_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban54_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban54_actionPerformed(e);
  }
}
class Frame_ban55_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban55_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban55_actionPerformed(e);
  }
}
class Frame_ban56_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban56_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban56_actionPerformed(e);
  }
}
class Frame_ban57_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban57_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban57_actionPerformed(e);
  }
}
class Frame_ban58_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban58_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban58_actionPerformed(e);
  }
}
class Frame_ban61_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban61_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban61_actionPerformed(e);
  }
}
class Frame_ban62_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban62_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban62_actionPerformed(e);
  }
}
class Frame_ban63_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban63_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban63_actionPerformed(e);
  }
}
class Frame_ban64_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban64_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban64_actionPerformed(e);
  }
}
class Frame_ban65_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban65_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban65_actionPerformed(e);
  }
}
class Frame_ban66_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban66_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban66_actionPerformed(e);
  }
}
class Frame_ban67_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban67_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban67_actionPerformed(e);
  }
}
class Frame_ban68_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban68_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban68_actionPerformed(e);
  }
}
class Frame_ban71_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban71_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban71_actionPerformed(e);
  }
}
class Frame_ban72_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban72_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban72_actionPerformed(e);
  }
}
class Frame_ban73_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban73_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban73_actionPerformed(e);
  }
}
class Frame_ban74_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban74_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban74_actionPerformed(e);
  }
}
class Frame_ban75_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban75_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban75_actionPerformed(e);
  }
}
class Frame_ban76_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban76_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban76_actionPerformed(e);
  }
}
class Frame_ban77_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban77_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban77_actionPerformed(e);
  }
}
class Frame_ban78_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban78_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban78_actionPerformed(e);
  }
}
class Frame_ban81_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban81_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban81_actionPerformed(e);
  }
}
class Frame_ban82_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban82_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban82_actionPerformed(e);
  }
}
class Frame_ban83_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban83_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban83_actionPerformed(e);
  }
}
class Frame_ban84_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban84_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban84_actionPerformed(e);
  }
}
class Frame_ban85_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban85_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban85_actionPerformed(e);
  }
}
class Frame_ban86_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban86_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban86_actionPerformed(e);
  }
}
class Frame_ban87_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban87_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban87_actionPerformed(e);
  }
}
class Frame_ban88_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_ban88_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.ban88_actionPerformed(e);
  }
}

class Frame_jMenuItem1_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_jMenuItem1_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuItem1_actionPerformed(e);
  }
}

class Frame_jCheckBoxMenuItem1_actionAdapter implements java.awt.event.ActionListener {
  Frame adaptee;

  Frame_jCheckBoxMenuItem1_actionAdapter(Frame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuItem1_actionPerformed(e);
  }
}
