package othello;

import javax.swing.UIManager;
import java.awt.*;

/**
 * <p>�^�C�g��: Othello Program "ZERO"</p>
 * <p>����: I want this program to be good at othello!</p>
 * <p>���쌠: Copyright (c) 2003-2004 by Sun-Soft</p>
 * <p>��Ж�: </p>
 * @author ������
 * @version Version 0.5
 */

public class zero {
  boolean packFrame = false;

  //�A�v���P�[�V�����̃r���h
  public zero() {
    MainFrame frame = new MainFrame();
    //validate() �̓T�C�Y�𒲐�����
    //pack() �͗L���ȃT�C�Y�������C�A�E�g�Ȃǂ���擾����
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //�E�B���h�E�𒆉��ɔz�u
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);
  }
  //Main ���\�b�h
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new zero();
  }
}