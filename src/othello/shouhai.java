package othello;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * <p>タイトル: ZERO</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2003 RyoHirafuji</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */

public class shouhai extends JDialog {
  JPanel panel1 = new JPanel();
  JLabel Message1 = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton OK = new JButton();
  JLabel Message2 = new JLabel();
  JLabel Message3 = new JLabel();

  public shouhai(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public shouhai() {
    this(null, "", false);
  }
  private void jbInit() throws Exception {
    int turn = Frame.win_turn;
    String sente_name = Frame.sente_name;
    String gote_name = Frame.gote_name;
    panel1.setLayout(gridBagLayout1);
    panel1.setDebugGraphicsOptions(0);
    Message1.setText("この勝負，");
    Message2.setText("Black:"+Frame.b_isi+"　対　"+"White:"+Frame.w_isi);
    if(Math.max(Frame.b_isi,Frame.w_isi) == Frame.b_isi){
        Message3.setText("で　"+sente_name+"(Black)　の勝ち！");
    }if(Math.max(Frame.b_isi,Frame.w_isi) == Frame.w_isi){
        Message3.setText("で　"+gote_name+"(White)　の勝ち！");
    }if(Frame.w_isi == Frame.b_isi){
      Message3.setText("で引分け！");
    }
    OK.setSelected(false);
    OK.setText("OK");
    OK.addActionListener(new shouhai_OK_actionAdapter(this));
    getContentPane().add(panel1);
    panel1.add(OK,   new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(Message1,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(Message2,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(Message3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }

  void OK_actionPerformed(ActionEvent e) {
    this.hide();
  }
}

class shouhai_OK_actionAdapter implements java.awt.event.ActionListener {
  shouhai adaptee;

  shouhai_OK_actionAdapter(shouhai adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.OK_actionPerformed(e);
  }
}