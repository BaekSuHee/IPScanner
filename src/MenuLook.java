import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

public class MenuLook extends JFrame {

	public MenuLook() {
		// menu begin
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);

		JMenu scanMenu = new JMenu("Scan");
		JMenu gotoMenu = new JMenu("Go to");
		JMenu commandsMenu = new JMenu("Commands");
		JMenu favoritesMenu = new JMenu("Favorites");
		JMenu toolsMenu = new JMenu("Tools");
		JMenu helpMenu = new JMenu("Help");

		menubar.add(scanMenu);
		menubar.add(gotoMenu);
		menubar.add(commandsMenu);
		menubar.add(favoritesMenu);
		menubar.add(toolsMenu);
		menubar.add(helpMenu);

		JMenuItem loadFromFilesAction = new JMenuItem("Load from file..");
		JMenuItem exportAllAction = new JMenuItem("Export All...");
		JMenuItem exportselectionAction = new JMenuItem("Export selection...");
		JMenuItem quitAction = new JMenuItem("Quit");

		scanMenu.add(loadFromFilesAction);
		scanMenu.add(exportselectionAction);
		scanMenu.add(exportAllAction);
		scanMenu.addSeparator();
		scanMenu.add(quitAction);

		JMenuItem nextAliveHostAction = new JMenuItem("Next alive host");
		JMenuItem nextOpenPortAction = new JMenuItem("Next open port");
		JMenuItem nextDeadHostAction = new JMenuItem("Next dead host");
		JMenuItem previousAliveHostAction = new JMenuItem("Previous alive host");
		JMenuItem previousOpenHostAction = new JMenuItem("Previous open port");
		JMenuItem previousDeadHostAction = new JMenuItem("Previous dead host");
		JMenuItem findAction = new JMenuItem("Find...");

		gotoMenu.add(nextAliveHostAction);
		gotoMenu.add(nextOpenPortAction);
		gotoMenu.add(nextDeadHostAction);
		gotoMenu.addSeparator();
		gotoMenu.add(previousAliveHostAction);
		gotoMenu.add(previousOpenHostAction);
		gotoMenu.add(previousDeadHostAction);
		gotoMenu.addSeparator();
		gotoMenu.add(findAction);

		JMenuItem showDetailsAction = new JMenuItem("Show details");
		JMenuItem rescanIpsAction = new JMenuItem("Rescan IP(s)");
		JMenuItem deleteIpsAction = new JMenuItem("Delete IP(s)");
		JMenuItem copyIpAction = new JMenuItem("Copy IP");
		JMenuItem copyDetailsAction = new JMenuItem("Copy details");
		JMenuItem openAction = new JMenuItem("Open");

		commandsMenu.add(showDetailsAction);
		commandsMenu.addSeparator();
		commandsMenu.add(rescanIpsAction);
		commandsMenu.add(deleteIpsAction);
		commandsMenu.addSeparator();
		commandsMenu.add(copyIpAction);
		commandsMenu.add(copyDetailsAction);
		commandsMenu.addSeparator();
		commandsMenu.add(openAction);

		JMenuItem addCurrentAction = new JMenuItem("Add current...");
		JMenuItem manageFavoritesAction = new JMenuItem("Manage favorites...");

		favoritesMenu.add(addCurrentAction);
		favoritesMenu.add(manageFavoritesAction);
		favoritesMenu.addSeparator();

		JMenuItem preferencesAction = new JMenuItem("Preferences...");
		JMenuItem fetchersAction = new JMenuItem("Fetchers...");
		JMenuItem selectionAction = new JMenuItem("Selection");
		JMenuItem scanStatisticsAction = new JMenuItem("Scan statistics");

		toolsMenu.add(preferencesAction);
		toolsMenu.add(fetchersAction);
		toolsMenu.addSeparator();
		toolsMenu.add(selectionAction);
		toolsMenu.add(scanStatisticsAction);

		JMenuItem gettingStartedAction = new JMenuItem("Getting Started");
		JMenuItem officialWebsiteAction = new JMenuItem("Official Website");
		JMenuItem faqAction = new JMenuItem("FAQ");
		JMenuItem reportAnIssueAction = new JMenuItem("Report an issue");
		JMenuItem pluginsAction = new JMenuItem("Plugins");
		JMenuItem commandLineUsageAction = new JMenuItem("Command-line usage");
		JMenuItem checkForNewerVersionAction = new JMenuItem("Check for newer version...");
		JMenuItem aboutAction = new JMenuItem("About");

		helpMenu.add(gettingStartedAction);
		helpMenu.addSeparator();
		helpMenu.add(officialWebsiteAction);
		helpMenu.add(faqAction);
		helpMenu.add(reportAnIssueAction);
		helpMenu.add(pluginsAction);
		helpMenu.addSeparator();
		helpMenu.add(commandLineUsageAction);
		helpMenu.addSeparator();
		helpMenu.add(checkForNewerVersionAction);
		helpMenu.addSeparator();
		helpMenu.add(aboutAction);

		quitAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		// menu end

		// status bar begin

		JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		statusPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		add(statusPanel, BorderLayout.SOUTH);
		JLabel readyLabel = new JLabel("Ready");
		readyLabel.setPreferredSize(new Dimension(350,16));
		readyLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		JLabel displayLabel = new JLabel("Display:All");
		displayLabel.setPreferredSize(new Dimension(150,16));
		displayLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		JLabel threadLabel = new JLabel("Thread:0");
		threadLabel.setPreferredSize(new Dimension(150,16));
		threadLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.add(readyLabel);
		statusPanel.add(displayLabel);
		statusPanel.add(threadLabel);

		// status bar end

		// table begin

		String[] titles = new String[] {
				"IP", "Ping", "TTL", "Hostname", "Ports[0+]"};
		Object[][] stats = initTable() ;

		JTable jTable = new JTable(stats, titles);

		JScrollPane sp = new JScrollPane(jTable);
		add(sp, BorderLayout.CENTER);
		// table end

		// toolbar begin
		Font myFont=new Font("Serif", Font.BOLD, 16);
		JToolBar toolbar1=new JToolBar();
		toolbar1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JToolBar toolbar2=new JToolBar();
		toolbar2.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel rangeStartLabel = new JLabel ("IP Range: ");
		JTextField rangeStartTextField= new JTextField(10);
		JLabel rangeEndLabel = new JLabel ("to");
		JTextField rangeEndTextField= new JTextField(10);
		JComboBox optionComboBox1 = new JComboBox();
		optionComboBox1.addItem("IP Range"); //ip林家啊 c鞭
		optionComboBox1.addItem("/26");
		JButton setButton = new JButton();
		setButton.setIcon(new ImageIcon("./image/setimagee.jpg"));
		
		rangeStartLabel.setFont(myFont);
		rangeStartLabel.setPreferredSize(new Dimension(90, 30));
		rangeEndLabel.setFont(myFont);
		rangeEndLabel.setPreferredSize(new Dimension(90, 30));
		optionComboBox1.setPreferredSize(new Dimension(90, 30));
		setButton.setFont(myFont);
		setButton.setPreferredSize(new Dimension(30, 30));

		toolbar1.add(rangeStartLabel);
		toolbar1.add(rangeStartTextField);
		toolbar1.add(rangeEndLabel);
		toolbar1.add(rangeEndTextField);
		toolbar1.add(optionComboBox1);
		toolbar1.add(setButton);

		JLabel hostNameLabel=new JLabel("Hostname: ");
		JTextField hostNameTextField = new JTextField(10);
		JButton upButton = new JButton("IP¤");
		JComboBox optionComboBox = new JComboBox();
		optionComboBox.addItem("/24"); //ip林家啊 c鞭
		optionComboBox.addItem("/26");
		JButton startButton = new JButton("⒑Start");
		JButton setButton2 = new JButton();
		setButton2.setIcon(new ImageIcon("./image/menu.png"));

		hostNameLabel.setFont(myFont);
		hostNameTextField.setPreferredSize(new Dimension(90, 30));
		upButton.setPreferredSize(new Dimension(50, 30));
		optionComboBox.setPreferredSize(new Dimension(90, 30));
		startButton.setPreferredSize(new Dimension(60, 30));
		setButton2.setFont(myFont);
		setButton2.setPreferredSize(new Dimension(30, 30));

		toolbar2.add(hostNameLabel);
		toolbar2.add(hostNameTextField);
		toolbar2.add(upButton);
		toolbar2.add(optionComboBox);
		toolbar2.add(startButton);
		toolbar2.add(setButton2);

		JPanel pane= new JPanel(new BorderLayout());
		pane.add(toolbar1, BorderLayout.NORTH);
		pane.add(toolbar2, BorderLayout.SOUTH);

		add(pane, BorderLayout.NORTH);
		// toolbar end

		String myIP = null;
		String myHostname = null;
		try {
			InetAddress ia = InetAddress.getLocalHost();

			myIP = ia.getHostAddress();
			myHostname = ia.getHostName();
		} catch (Exception e) {

		}
		String fixedIP = myIP.substring(0, myIP.lastIndexOf(".") + 1);
		rangeStartTextField.setText(fixedIP + "1");
		rangeEndTextField.setText(fixedIP + "254");
		hostNameTextField.setText(myHostname);

		setSize(700,700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		startButton.addActionListener(new ActionListener() {

			@Override
			
			public void actionPerformed(ActionEvent e) {
				Pinging[] pg = new Pinging[254];

				for(int i=0; i<=253; i++) {
					pg[i] = new Pinging(fixedIP+ (i+1));
					pg[i].start();
				}
				for(int i=0; i<=253; i++) {
					Object[] msg = pg[i].getMsg();
					if (msg[1] == null) {
						msg[1] = "[n/a]";
						msg[2] = "[n/s]";
						msg[3] = "[n/s]";

					} else if (msg[3] == null) {
						msg[3] = "[n/a]";
					}
					stats[i][0] = msg[0];
					stats[i][1] = msg[1];
					stats[i][2] = msg[2];
					stats[i][3] = msg[3];

					if( msg[1] != null || msg[2] != null || msg[3] !=null ) {

						final ExecutorService es = Executors.newFixedThreadPool(20);
						final String ip = (String)msg[0];
						final int timeout = 10;
						final List<Future<ScanResult>> futures = new ArrayList<>();	
						// 65535, 1024
						for (int port = 1; port <=1024; port++) {
							// for (int port = 1; port <= 80; port++{
							futures.add(portIsOpen(es, ip, port, timeout));
						}
						try {
							es.awaitTermination(200L, TimeUnit.MILLISECONDS);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						int openPorts = 0;
						String openPortNumber = "";
						for (final Future<ScanResult> f : futures) {
							try {
								if (f.get().isOpen()) {
									openPorts++;
									openPortNumber += f.get().getPort()+",";
								}
							} catch (InterruptedException e1) {

								e1.printStackTrace();
							} catch (ExecutionException e1) {

								e1.printStackTrace();

							}

						}
						if(openPortNumber != null) {
							stats[i][4] = openPortNumber;
						}else {
							stats[i][4] = "[n/s]";
						}
					}
				}

				jTable.repaint();
			}
		});	 	
	}

	public Object[][] initTable() {
		Object[][] result = new Object[254][5];
		return result;
	}

	public static Future<ScanResult> portIsOpen(final ExecutorService es, final
			String ip, final int port,
			final int timeout) {
		return es.submit(new Callable<ScanResult>() {
			@Override
			public ScanResult call() {
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(ip, port),timeout);
					socket.close();
					return new ScanResult(port, true);
				} catch (Exception ex) {
					return new ScanResult(port, false);
				}
			}
		});
	}

	public static void main(String[] args) {
		MenuLook op = new MenuLook();

	}
}


