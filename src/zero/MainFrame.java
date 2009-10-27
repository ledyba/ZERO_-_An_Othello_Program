package zero;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import zero.player.zero.*;
import zero.player.GameManager;
import zero.player.common.Board;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import zero.player.common.Point;
import java.awt.Color;
import zero.player.gameController;
import zero.player.human.humanMG;

/**
 * <p>�^�C�g��: Othello Program "ZERO"</p>
 *
 * <p>����: </p>
 *
 * <p>���쌠: Copyright (c) 2003-2005 PSI</p>
 *
 * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
 *
 * @author PSI
 * @version 1.0
 */
public class MainFrame extends JFrame {
    //�����Œ�`�����ϐ�
    private GameManager GM;
    private BoardButton[] BoardButton = new BoardButton[64]; //�{�^��
    public static final String[] Alphabet = {
                                     "", "a", "b", "c", "d", "e", "f", "g", "h",
    };

    private gameController gc;//�Q�[���R���g���[��

    public static final Image WinIcon = Toolkit.getDefaultToolkit().createImage(zero.
            MainFrame.class.getResource("icon.png"));
    private final LogFrame LogWindow = new LogFrame();//���O�\���p
    JPanel contentPane;
    BorderLayout borderLayout1 = new BorderLayout();
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();
    JMenu jMenuHelp = new JMenu();
    JMenuItem jMenuHelpAbout = new JMenuItem();
    JLabel statusBar = new JLabel();
    JPanel TitlePanel = new JPanel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JLabel TitleLabel = new JLabel();
    JPanel BoardPanel = new JPanel();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    JMenu jMenuGame = new JMenu();
    JMenuItem jMenuLogWindow = new JMenuItem();
    JMenuItem jMenu_MZ = new JMenuItem();
    JMenu jMenuGameStart = new JMenu();
    JMenuItem jMenuItem_MM = new JMenuItem();
    JMenuItem jMenuItem_ZZ = new JMenuItem();
    JMenuItem jMenuItem_ZM = new JMenuItem();
    JMenuItem jMenuItem_MZ = new JMenuItem();
    public MainFrame() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * �R���|�[�l���g�̏������B
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(borderLayout1);
        setSize(new Dimension(400, 300));
        setTitle("Othello Program  \"ZERO\"");
        statusBar.setText(" ");
        jMenuFile.setText("File");
        jMenuFileExit.setText("Exit");
        jMenuFileExit.addActionListener(new
                                        MainFrame_jMenuFileExit_ActionAdapter(this));
        jMenuHelp.setText("Help");
        jMenuHelpAbout.setText("About \"ZERO\"");
        jMenuHelpAbout.addActionListener(new
                                         MainFrame_jMenuHelpAbout_ActionAdapter(this));
        TitlePanel.setLayout(gridBagLayout1);
        TitleLabel.setFont(new java.awt.Font("DialogInput", Font.PLAIN, 17));
        TitleLabel.setText("Othello Program \"ZERO\"");
        BoardPanel.setLayout(gridBagLayout2);
        jMenuGame.setText("Game");
        jMenuLogWindow.addActionListener(new
                                         MainFrame_jMenuLogWindow_actionAdapter(this));
        jMenuGameStart.setText("Game Start");
        jMenuItem_MZ.setText("MAN VS ZERO");
        jMenuItem_MZ.addActionListener(new MainFrame_jMenuItem_MZ_actionAdapter(this));
        jMenuItem_ZM.setText("ZERO VS MAN");
        jMenuItem_ZM.addActionListener(new MainFrame_jMenuItem_ZM_actionAdapter(this));
        jMenuItem_ZZ.setText("ZERO VS ZERO");
        jMenuItem_ZZ.addActionListener(new MainFrame_jMenuItem_ZZ_actionAdapter(this));
        jMenuItem_MM.setText("MAN VS MAN");
        jMenuItem_MM.addActionListener(new MainFrame_jMenuItem_MM_actionAdapter(this));
        jMenuBar1.add(jMenuFile);
        jMenuBar1.add(jMenuGame);
        jMenuFile.add(jMenuFileExit);
        jMenuBar1.add(jMenuHelp);
        jMenuHelp.add(jMenuHelpAbout);
        setJMenuBar(jMenuBar1);
        contentPane.add(statusBar, BorderLayout.SOUTH);
        TitlePanel.add(TitleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0));
        contentPane.add(BoardPanel, java.awt.BorderLayout.CENTER);
        contentPane.add(TitlePanel, java.awt.BorderLayout.NORTH);
        jMenuGame.add(jMenuLogWindow);
        jMenuGame.addSeparator();
        jMenuGame.add(jMenuGameStart);
        jMenuGameStart.add(jMenuItem_MZ);
        jMenuGameStart.add(jMenuItem_ZM);
        jMenuGameStart.add(jMenuItem_ZZ);
        jMenuGameStart.add(jMenuItem_MM);
        jMenuLogWindow.setText("Log Window");
        BoardPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        this.setIconImage(WinIcon);
        BoardAdd();
        GM = new GameManager(BoardPanel);
    }

    /**
     * �{�[�h������킷�{�^����z�u���܂��D
     */

    void BoardAdd() {
        for (int i = 1; i <= 8; i++) { //�����̃A���t�@�x�b�g
            BoardPanel.add(new JLabel(Alphabet[i])
                           , new GridBagConstraints(i, 0, 1, 1, 0.0, 0.0
                    , GridBagConstraints.CENTER, GridBagConstraints.NONE
                    , new Insets(0, 0, 3, 0), 0, 0));
        }
        for (int i = 1; i <= 8; i++) { //�c���̐����\��
            BoardPanel.add(new JLabel(String.valueOf(i))
                           , new GridBagConstraints(0, i, 1, 1, 0.0, 0.0
                    , GridBagConstraints.CENTER, GridBagConstraints.NONE
                    , new Insets(0, 0, 0, 3), 0, 0));
        }

        for (byte x = 1; x <= 8; x++) {
            for (byte y = 1; y <= 8; y++) { //�{�^���̒ǉ�
                //�{�^���̐���
                Point bp = new Point(x,y);
                //System.out.println("("+x+","+y+"):"+lib.PointToButton(x, y));
                byte num = bp.getNumber();
                BoardButton[num] = new BoardButton(this);
                BoardButton[num].setBoardPoint(bp);//���W��ݒ�
                //�z�u
                BoardPanel.add(BoardButton[num],
                               new
                               GridBagConstraints(x, y, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0, 0, 0, 0), 0, 0));

                BoardButton[num].setMargin(new Insets(0, 0, 0,
                        0));
                BoardButton[num].addActionListener(new
                        BoardButtonActionListener());
                //�{�[�_��ύX���Ă��I�Z���Ղ��ۂ�
                int top = 0,bottom = 0,left = 0,right = 0;
                if(x == 1){
                    left = 2;
                }else if(x == 8){
                    right = 2;
                }
                if(y == 1){
                    top = 2;
                }else if(y == 8){
                    bottom = 2;
                }

                BoardButton[num].setBorder(
                        BorderFactory.createEmptyBorder(top, left, bottom, right));

                BoardButton[num].setBackground(Color.black);
                //BoardButton[lib.PointToButton(x, y)].setText("("+String.valueOf(x)+","+String.valueOf(y)+"):"+String.valueOf(lib.PointToButton(x, y)));
            }
        }
    }

    /**
     *
     * <p>�^�C�g��: Othello Program "ZERO"</p>
     *
     * <p>����: �{�^�����������Ƃ��̏����ł�</p>
     *
     * <p>���쌠: Copyright (c) 2003-2005 PSI</p>
     *
     * <p>��Ж�: �Ձi�v�T�C�j�̋����֐S���</p>
     *
     * @author PSI
     * @version 1.0
     */

    class BoardButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            BoardButton button = ((BoardButton)actionEvent.getSource());//�����ꂽ�{�^�����Q�b�g
            if(gc != null){
                gc.getNowPlayer().pressedDiscButton(button.getBoardPoint()); //�������{�^����ʒm���܂��D
            }
        }
    }


    /**
     * [�t�@�C��|�I��] �A�N�V���������s����܂����B
     *
     * @param actionEvent ActionEvent
     */
    void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * [�w���v|�o�[�W�������] �A�N�V���������s����܂����B
     *
     * @param actionEvent ActionEvent
     */
    void jMenuHelpAbout_actionPerformed(ActionEvent actionEvent) {
        MainFrame_AboutBox dlg = new MainFrame_AboutBox(this);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        java.awt.Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.pack();
        dlg.show();
    }
    public Board getBoard(){
        return this.GM.getBoard();
    }

    public void jMenuLogWindow_actionPerformed(ActionEvent e) {//[Log Dialog]�������ꂽ�Ƃ�
        if(!LogWindow.isVisible()){//�����Ă��Ȃ��Ƃ��͌�����
            Dimension frmSize = getSize();
            LogWindow.setSize(frmSize.width + 100, frmSize.height);
            Dimension dlgSize = LogWindow.getSize();
            java.awt.Point loc = getLocation();
            LogWindow.setLocation(frmSize.width + loc.x, loc.y);
            LogWindow.setVisible(true);
        }else{//�����Ă���Ƃ��͉B��
            LogWindow.setVisible(false);
        }
    }
    /**
     * MAN VS ZERO
     * @param e ActionEvent
     */
    public void jMenuItem_MZ_actionPerformed(ActionEvent e) {
        gc = GM.makeGC(new humanMG(),new zeroMG());
        gc.start();
    }
    /**
     * ZERO VS MAN
     * @param e ActionEvent
     */
    public void jMenuItem_ZM_actionPerformed(ActionEvent e) {
        gc = GM.makeGC(new zeroMG(),new humanMG());
        gc.start();
    }
    /**
     * ZERO VS ZERO
     * @param e ActionEvent
     */
    public void jMenuItem_ZZ_actionPerformed(ActionEvent e) {
        gc = GM.makeGC(new zeroMG(),new zeroMG());
        gc.start();
    }
    /**
     * HUMAN VS HUMAN
     * @param e ActionEvent
     */
    public void jMenuItem_MM_actionPerformed(ActionEvent e) {
        gc = GM.makeGC(new humanMG(),new humanMG());
        gc.start();
    }
}


class MainFrame_jMenuItem_MM_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_jMenuItem_MM_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jMenuItem_MM_actionPerformed(e);
    }
}


class MainFrame_jMenuItem_ZZ_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_jMenuItem_ZZ_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jMenuItem_ZZ_actionPerformed(e);
    }
}


class MainFrame_jMenuItem_MZ_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_jMenuItem_MZ_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jMenuItem_MZ_actionPerformed(e);
    }
}


class MainFrame_jMenuItem_ZM_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_jMenuItem_ZM_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jMenuItem_ZM_actionPerformed(e);
    }
}


class MainFrame_jMenuLogWindow_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_jMenuLogWindow_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jMenuLogWindow_actionPerformed(e);
    }
}


class MainFrame_jMenuFileExit_ActionAdapter implements ActionListener {
    MainFrame adaptee;

    MainFrame_jMenuFileExit_ActionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuFileExit_actionPerformed(actionEvent);
    }
}


class MainFrame_jMenuHelpAbout_ActionAdapter implements ActionListener {
    MainFrame adaptee;

    MainFrame_jMenuHelpAbout_ActionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuHelpAbout_actionPerformed(actionEvent);
    }
}
