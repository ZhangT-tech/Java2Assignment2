import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientView extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel northPanel; // 上方的panel
    private JTextField markField; // 第几个player
    private JTextField nameField; // player的name是什么
    private JPanel centerPanel; // 中间的panel
    private JButton[][] buttons = new JButton[3][3]; // button的个数
    private JPanel southPanel; // 右面的panel
    private JTextArea textArea; // 输出text
    private JScrollPane scrollPane;

    public ClientView()
    {
        super("Tic-Tac-Toe"); // 标题名
        //this.base_square = base_square;
        initVariables(); // 初始化变量
        initContainer(); // 初始化容器
        initPanels(); // 初始化Panel
    }

    public void enableButtons(boolean enable) { // buttons 接收相应
        for (JButton[] button : buttons)
            for (int j = 0; j < buttons[0].length; j++)
                button[j].setEnabled(enable);
    }

    public void updateButtonText(int[][] board) { // 更改button的赋值
        for (int i = 0; i < buttons.length; i++)
            for (int j = 0; j < buttons[0].length; j++) {
                if (board[i][j] == 1)
                    buttons[i][j].setText("O"); // 对应board
                else if (board[i][j] == 2)
                    buttons[i][j].setText("X");
                else buttons[i][j].setText(" ");
            }
    }

    private void initVariables() {

        // North
        northPanel = new JPanel(); // 上方的panel
        markField = new JTextField(5); // 标记field
        nameField = new JTextField(5); // name filed
        // Center
        centerPanel = new JPanel(); // 中间的panel
        for (int i = 0; i < buttons.length; i++)
            for (int j = 0; j < buttons[0].length; j++) {
                buttons[i][j] = new JButton(""); // 放9个button
            }

        southPanel = new JPanel(); // 右面的panel
        textArea = new JTextArea(1, 40);
        scrollPane = new JScrollPane(textArea); // 当有很多的时候可以滚动
        // Disable the tic-tac-toe buttons
        // 初始化参数的时候，将这些设置为不可更改，client在玩的时候，不能自己改里面的参数，只能server端设置
        enableButtons(false); //
        // Make textFields not editable
        markField.setEditable(false);
        nameField.setEditable(false);
        textArea.setEditable(false);
    }

    private void initContainer() {
        // Get container for frame components
        Container container = getContentPane(); // 放这些component的容器
        // Set program to stop upon the window closing
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Set the preferred size of the window
        setPreferredSize(new Dimension(500, 300));
        // Set layout for main window frame
        setLayout(new BorderLayout());
        // Place panels onto frame
        container.add("North", northPanel);
        container.add("Center", centerPanel);
        container.add("South", southPanel);
        // Make the frame visible
        setVisible(true);
        pack();
    }
    private void initPanels() {
        // Set layout for center panel
        centerPanel.setLayout(new GridLayout(3, 3, 5, 5));
        // Place the components into their respective panels
        northPanel.add(new JLabel("Current Player:")); // 名字
        northPanel.add(nameField); // field
        northPanel.add(new JLabel("Mark:")); // 名字
        northPanel.add(markField); // field
        centerPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        for (JButton[] button : buttons)
            for (int j = 0; j < buttons.length; j++) {
                centerPanel.add(button[j]); // 加到grade里面
            }
        southPanel.add(scrollPane);
    }
    public void addListener(ActionListener listener, int i, int j) {
        buttons[i][j].addActionListener(listener);
    }
    public void setMarkField(String mark) { this.markField.setText(mark); }
    public void setNameField(String text) { this.nameField.setText(text); }
    public void setTextArea(String text) { this.textArea.setText(text); }
}
