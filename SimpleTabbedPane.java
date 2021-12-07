import javax.swing.*;

public class SimpleTabbedPane extends JFrame {

    JTabbedPane tabs;
    RoutinePanel routinePanel;
    RepertoirePanel repertoirePanel;
    ReportPanel reportPanel;
    User user;


    SimpleTabbedPane(User user) {
        super("Complete Musician");
        this.user = user;

        //exit program if window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //create new tabbed pane with tabs on top
        tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

        //create routine, repertoire, and report panels, set size, and make visible
        routinePanel = new RoutinePanel(this.user);
        repertoirePanel = new RepertoirePanel(this.user);
        reportPanel = new ReportPanel(this.user);
        tabs.addTab("Routine", routinePanel);
        tabs.addTab("Repertoire", repertoirePanel);
        tabs.addTab("Report", reportPanel);
        setSize(450, 500);
        setVisible(true);

        //add tabs to content pane
        getContentPane().add(tabs);
    }
}
