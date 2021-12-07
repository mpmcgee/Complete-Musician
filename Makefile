JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class: 
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		Component.java \
        Countdown.java \
        Improv.java \
        LinkedList.java \
        LLSong.java \
		LLUser.java \
		Loader.java \
		LoginDialog.java \
		Main.java \
		Node.java \
		RegisterDialog.java \
		Repertoire.java \
		RepertoirePanel.java \
		Report.java \
		ReportPanel.java \
		Routine.java \
		RoutinePanel.java \
		Session.java \
		SimpleTabbedPane.java \
		Song.java \
		SongPractice.java \
		Technique.java \
		User.java \
		WarmUp.java 


default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class
		
		
run: Main.class
	 java Main