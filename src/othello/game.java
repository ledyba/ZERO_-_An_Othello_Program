package othello;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * <p>É^ÉCÉgÉã: ZERO</p>
 * <p>ê‡ñæ: </p>
 * <p>íòçÏå†: Copyright (c) 2003 RyoHirafuji</p>
 * <p>âÔé–ñº: </p>
 * @author ñ¢ì¸óÕ
 * @version 1.0
 */

public class game extends JDialog {
  JPanel panel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  public static String[] sentakunaiyou = {"Ç†Ç»ÇΩ","ZERO"};
  JList jList1 = new JList(sentakunaiyou);
  JList jList2 = new JList(sentakunaiyou);
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JButton OK = new JButton();
  JButton cancel = new JButton();
  JLabel jLabel3 = new JLabel();

  public game(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public game() {
    this(null, "", false);
  }
  private void jbInit() throws Exception {
    panel1.setLayout(gridBagLayout1);
    panel1.setDebugGraphicsOptions(0);
    jLabel1.setText("white");
    jLabel2.setText("White");
    OK.setText("OK");
    OK.addActionListener(new game_OK_actionAdapter(this));
    cancel.setText("Cancel");
    cancel.addActionListener(new game_cancel_actionAdapter(this));
    jLabel3.setText("black");
    getContentPane().add(panel1);
    panel1.add(jList1,           new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 4, 0, 0), 100, 150));
    panel1.add(jList2,            new GridBagConstraints(1, 1, GridBagConstraints.REMAINDER, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 4, 0, 0), 100, 150));
    panel1.add(jLabel1,     new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(OK,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(cancel,   new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(jLabel3,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }

  void OK_actionPerformed(ActionEvent e) {
    Frame.game_start=true;
    for(int i=0;i<=64;i++){
      Frame.sasite[i][0] = 0;
      Frame.sasite[i][1] = 0;
    }
    Frame.start(jList1.getSelectedValue().toString() , jList2.getSelectedValue().toString());
    this.hide();
  }

  void cancel_actionPerformed(ActionEvent e) {
    this.hide();
  }
}

class game_OK_actionAdapter implements java.awt.event.ActionListener {
  game adaptee;

  game_OK_actionAdapter(game adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.OK_actionPerformed(e);
  }
}

class game_cancel_actionAdapter implements java.awt.event.ActionListener {
  game adaptee;

  game_cancel_actionAdapter(game adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancel_actionPerformed(e);
  }
}