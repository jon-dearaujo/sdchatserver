package gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import program.ServerLog;
import server.Server;


public class ServerFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JButton startButton;
	private JButton stopButton;
	private JTextArea logArea;
	private JScrollPane logScrollPane;
	public ServerFrame()
	{
		configureLayout();
		basicConfiguration();
		this.setVisible(true);
		ServerLog.addDefaultLogAppender(new GuiLogAppender(this.logArea));
	}

	private void basicConfiguration()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Server Chat");
		this.setSize(500, 300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon("res/chat_icon.png").getImage());
	}
	
	private void configureLayout()
	{
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		Container container = this.getContentPane();
		container.setLayout(layout);
		
		startButton = new JButton(new ImageIcon("res/start.png"));
		startButton.setToolTipText("Start server");
		stopButton = new JButton(new ImageIcon("res/stop.png"));
		logArea = new JTextArea();
		logArea.setEditable(false);
		startButton.addActionListener(new StartButtonHandler(stopButton));
		stopButton.addActionListener(new StopButtonHandler(startButton));
		stopButton.setToolTipText("Stop server");
		stopButton.setEnabled(false);
		logScrollPane = new JScrollPane(logArea);
		
		constraints.weightx = 1;
		constraints.weighty = 0.1;
		constraints.ipady = -7;
		constraints.ipadx = -30;
		container.add(startButton, constraints);
		container.add(stopButton, constraints);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridy = 1;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.gridwidth = 2;
		constraints.weighty = 1;
		container.add(logScrollPane, constraints);
	}
}

class StartButtonHandler implements ActionListener
{
	private JButton buttonToEnable;
	public StartButtonHandler(JButton buttonToEnable)
	{
		this.buttonToEnable = buttonToEnable;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		((JButton)e.getSource()).setEnabled(false);
		this.buttonToEnable.setEnabled(true);
		
		Server.getInstance().start();
	}
}

class StopButtonHandler implements ActionListener
{
	private JButton buttonToDisable;
	public StopButtonHandler(JButton buttonToDisable)
	{
		this.buttonToDisable = buttonToDisable;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		((JButton)e.getSource()).setEnabled(false);
		this.buttonToDisable.setEnabled(true);
		Server.getInstance().stop();
	}
}