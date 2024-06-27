import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class WebQuiz extends JFrame
{
    private JLabel questionLabel;
    private JRadioButton[] options;
    private JButton submitButton;
    private JLabel timerLabel;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;

    private String[] questions =
            {
                    "who invented java programming?",
                    "which one of the following is not a java feature",
                    "what is extension of java code file?"
            };
    private String[][] choices =
            {
                    {"Guido van rossom", "James Gosling", "Dennis Ritchie", "Bajarne Stroustrup"},
                    {"Dynamic and Extensible", "Portable", "use pointers", "Object Oriented"},
                    {".java", ".py", ".js", ".class"}
            };
    private int[] correctAnswers = {1, 2, 0};

    public WebQuiz ()
    {
        setTitle("Quiz WebApp");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 300);
        setLayout(new BorderLayout());

        questionLabel = new JLabel();
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        options = new JRadioButton[4];
        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++)
        {
            options[i] = new JRadioButton();
            options[i].setText(choices[currentQuestionIndex][i]);
            buttonGroup.add(options[i]);
            optionsPanel.add(options[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        submitButton = new JButton("Submit");
        add(submitButton, BorderLayout.SOUTH);

        submitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                checkAnswer();
                displayNextQue();
            }
        });

        timerLabel = new JLabel();
        add(timerLabel, BorderLayout.EAST); // Add the timer label

        currentQuestionIndex = 0;
        displayQue();
        startTimer();
    }

    private void displayQue()
    {
        questionLabel.setText(questions[currentQuestionIndex]);
        for (int i = 0; i < 4; i++)
        {
            options[i].setText(choices[currentQuestionIndex][i]);
            options[i].setSelected(false);
        }
    }

    private void displayNextQue()
    {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.length) {
            displayQue();
            resetTimer();
        }
        else{
            displayResult();
        }
    }

    private void checkAnswer()
    {
        for (int i = 0; i < 4; i++)
        {
            if (options[i].isSelected() && i == correctAnswers[currentQuestionIndex])
            {
                score++;
            }
        }
    }

    private void displayResult()
    {
        JOptionPane.showMessageDialog(this, "Quiz Completed!\nScore: " + score + "/" + questions.length);
        System.exit(0);
    }

    private void startTimer()
    {
        timer = new Timer(1000, new ActionListener()
        {
            private int secondsLeft = 15;

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (secondsLeft >= 0)
                {
                    timerLabel.setText("Time left: " + secondsLeft + " seconds");
                    secondsLeft--;
                }
                else {
                    timer.stop();
                    checkAnswer();
                    displayNextQue();
                }
            }
        });
        timer.start();
    }

    private void resetTimer()
    {
        timer.stop();
        startTimer();
    }

}
class Task_4
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new WebQuiz ().setVisible(true);
            }
        });
    }
}