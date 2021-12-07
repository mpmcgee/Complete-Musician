import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReportPanel extends JPanel {
    User user;
    Report report;

    //parametrized constructor
    ReportPanel(User user) {
        this.user = user;
        JLabel title = new JLabel("Practice Report");
        //String builder for appending text
        StringBuilder sb = new StringBuilder();
        //Button and JLabel
        JButton generateButton = new JButton("Generate");


        //Create panels and layout, then add
        JPanel p1 = new JPanel(new GridLayout(0, 1));
        p1.add(title);
        JPanel p2 = new JPanel(new GridLayout(0, 1));
        JPanel p3 = new JPanel(new GridLayout(0, 1));
        p3.add(generateButton);


        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.add(p1, BorderLayout.NORTH);
        panel.add(p2, BorderLayout.CENTER);
        panel.add(p3, BorderLayout.SOUTH);

        add(panel);

        //generateButton listener
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //remove all from p2
                p2.removeAll();
                //add sessionPanel to p2
                p2.add(getSessionPanel());
                //remove all from p1
                p1.removeAll();
                //add title to p1
                p1.add(title);
                //add time totals and itemized practice data to panel
                p1.add(new JLabel("Totals:"));
                p1.add(new JLabel("Warm Up: " +report.getConvertedWarmUpTime()));
                p1.add(new JLabel("Tecnhique: " +report.getConvertedTechniquePracticeTime()));
                p1.add(new JLabel("Repertoire: " +report.getConvertedSongPracticeTime()));
                p1.add(new JLabel("Improv: " +report.getConvertedImprovPracticeTime()));
                p1.add(new JLabel("Overall: " + report.getFormattedTotalPracticeTime()));
                panel.add(p2, BorderLayout.CENTER);
                //refresh content
                refresh();

            }
        });
    }

    //gets and returns session panel
    public JPanel getSessionPanel() {
        //create a new report using this user info
        this.report = new Report(this.user);
        //create an arraylist of JLabels for sessions data
        ArrayList<JLabel> sessionsData = new ArrayList<>();
        //for each session in this report object
        for (Session session : report.getSessions()) {
            //add a new JLabel to sessionsData list with the session payload as text
            sessionsData.add(new JLabel(session.getPayload()));
        }
        //create a new panel with infinite rows and 1 col
        JPanel panel = new JPanel(new GridLayout(0, 1));
        //for each Jlabel in sessionsData list
        for (JLabel label : sessionsData) {
            //add said label to the panel
            panel.add(label);
        }
        //return this panel
        return panel;
    }

    //refreshes content in panes
    public void refresh() {
        invalidate();
        validate();
        repaint();
    }


}
