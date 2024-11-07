package apps;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator
    {

        private static final int X_LOC = 0;
        private static final int Y_LOC = 0;
        private static final int WIDTH = 400;
        private static final int HEIGHT = 400;
        private static final String NAME = "Calculator";
        private static final String RESULT_PREAMBLE = "Result = ";
        private static final String ERROR_MESSAGE = "Error";

        private JFrame frame;
        private JTextField infixExpression;
        private JLabel resultLabel;
        private JButton calculateButton;
        private JButton clearButton;
        private JPanel text;
        
        public Calculator ()
        {
            createFrame();
            initializeComponents();
            displayFrame();

        }

        public JFrame getFrame ()
        {
            return frame;
        }

        private void createFrame ()
        {
            frame = new JFrame(NAME);
            frame.setSize(WIDTH, HEIGHT);
            frame.setLocation(X_LOC, Y_LOC);
            frame.setLayout(new GridLayout(1, 2));
        }

        private void initializeComponents ()
        {
            text = new JPanel(new GridLayout(2, 1));
            initializeInput();
            initializeResult();
            initializeButtons();
        }

        private void displayFrame ()
        {
            frame.pack();
            frame.setVisible(true);
        }

        private void initializeInput ()
        {
            infixExpression = new JTextField(25);
            infixExpression.setName("infixExpression");
            text.add(infixExpression);
        }

        private void initializeResult ()
        {
            resultLabel = new JLabel();
            resultLabel.setName("resultLabel");
            text.add(resultLabel);
            frame.add(text);
        }

        private void initializeButtons ()
        {
            calculateButton = new JButton("Calculate");
            calculateButton.setName("calculateButton");
            calculateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    try {
                    updateResult(calculate());
                    }
                    catch (Exception f)
                    {
                        resultLabel.setText(ERROR_MESSAGE);
                    }
                }
            });

            clearButton = new JButton("Clear");
            clearButton.setName("clearButton");
            clearButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    resultLabel.setText("");
                    infixExpression.setText("");
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(2, 1));
            buttonPanel.add(calculateButton);

            buttonPanel.add(clearButton);

            frame.add(buttonPanel);
        }

        private String calculate ()
        {
            double temp = ExpressionEvaluator.evaluate(ExpressionEvaluator.toPostfix(infixExpression.getText()));
            if (temp == Double.NaN)
            {
                return null;
            }
            return Double.toString(temp);
        }

        private void updateResult (String result)
        {
            if ( result.equals("NaN"))
                throw new NullPointerException();
            else
            resultLabel.setText(RESULT_PREAMBLE + result);
        }
    }