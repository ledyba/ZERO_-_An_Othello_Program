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

public class pass extends JDialog {
  JPanel panel1 = new JPanel();
  JLabel jLabel1 = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton OK = new JButton();

  public pass(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public pass() {
    this(null, "", false);
  }
  private void jbInit() throws Exception {
    panel1.setLayout(gridBagLayout1);
    jLabel1.setText("置く場所がないのでパスします");
    OK.setText("OK");
    OK.addActionListener(new pass_OK_actionAdapter(this));
    getContentPane().add(panel1);
    panel1.add(jLabel1,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(OK,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }

  void OK_actionPerformed(ActionEvent e) {
    this.hide();
  }
}

class pass_OK_actionAdapter implements java.awt.event.ActionListener {
  pass adaptee;

  pass_OK_actionAdapter(pass adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.OK_actionPerformed(e);
  }
}