package com.company;

        import javax.swing.*;
        import javax.swing.border.MatteBorder;
        import java.awt.*;
        import java.util.Random;
        import java.awt.event.KeyAdapter;
        import java.awt.event.KeyEvent;

class GUI extends JFrame {
    private static final String TITLE = "Hang Man TEAM-5";
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int RANDOMIZE_LENGTH = 2;

    private int counts = 0;
    private String ANSWER = "ANSWER";
    private static char[] WORDS;
    private JLabel[] labels;
    private final Container container = getContentPane();
    private final MatteBorder BOTTOM_BORDER = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
    private JPanel TextFieldPanel, WordsPanel;


    public void init() {
        ANSWER = "ANSWER";
        counts = 0;
        WORDS = new char[ANSWER.length()];
        // Label Component 초기화
        WordsPanel.removeAll();
        labels = new JLabel[ANSWER.length()];

        for(int i = 0; i < ANSWER.length(); i++) {
            WORDS[i] = ' ';
            labels[i] = makeLabel(" ");
            WordsPanel.add(labels[i]);
            WordsPanel.revalidate();
            WordsPanel.repaint();
        }

        for(int i = 0; i < RANDOMIZE_LENGTH; i++){
            Random rand = new Random();
            int iValue = rand.nextInt(ANSWER.length());
            char word = ANSWER.toCharArray()[iValue];
            WORDS[iValue] = word;
            labels[iValue].setText(String.valueOf(word));
        }
    }

    public JLabel makeLabel(String labelStr) {
        JLabel label_ = new JLabel();

        label_.setBorder(BOTTOM_BORDER);
        label_.setText(labelStr);
        label_.setVisible(true);
        return label_;
    }

    public JTextField makeTextField() {
        JTextField textField = new JTextField(5);
        JLabel alertMessage = new JLabel(" ");
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BOTTOM_BORDER);
        TextFieldPanel.add(alertMessage);
        alertMessage.setVisible(true);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                JTextField source = (JTextField)e.getSource();
                if(source.getText().length() > 0) {
                    e.consume();
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                JTextField source = (JTextField)e.getSource();
                String word = source.getText();
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if(!word.equals("")) {
                        if(ANSWER.contains(word)) {
                            // 답에 word 가 있으면
                            int idx = ANSWER.indexOf(word);
                            labels[idx].setText(word);
                            alertMessage.setText("정답 입니다!");
                            alertMessage.setForeground(Color.GREEN);
                            WORDS[idx] = word.toCharArray()[0];
                            source.setText("");
                        } else {
                            // 답에 word 가 없으면
                            alertMessage.setText("오답 입니다!");
                            alertMessage.setForeground(Color.RED);
                            counts += 1;
                        }
                        String words = "";
                        for(int i = 0; i < WORDS.length; i++) {
                            words += WORDS[i];
                        }
                        if(words.equals(ANSWER) || counts == 3) {
                            alertMessage.setText(" ");
                            init();
                        }
                    }
                }
            }
        });
        textField.setSize(100, 30);

        return textField;
    }

    public Container setContainer() {
        container.setLayout(new GridLayout(2, 0));
        container.setBackground(Color.white);
        TextFieldPanel = makePanel(new FlowLayout(FlowLayout.CENTER, WIDTH,20));
        WordsPanel = makePanel(new FlowLayout());
        TextFieldPanel.add(makeTextField());

        container.add(WordsPanel, TOP_ALIGNMENT);
        container.add(TextFieldPanel, BOTTOM_ALIGNMENT);

        init();
        return container;
    }

    public JPanel makePanel(LayoutManager layout) {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(layout);

        return newPanel;
    };


    public GUI() {
        super(TITLE);
        setContainer();

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setResizable(false);
        setVisible(true);
    }
}


public class Main {

    public static void main(String[] args) {
        // write your code here
        new GUI();
    }
}
