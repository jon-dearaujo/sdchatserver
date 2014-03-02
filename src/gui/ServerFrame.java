package gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ServerFrame extends JFrame
{
	public ServerFrame()
	{
		configureLayout();
		basicConfiguration();
		this.setVisible(true);
	}

	private void basicConfiguration()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Server Chat");
		this.setSize(500, 300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	private void configureLayout()
	{
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		Container container = this.getContentPane();
		container.setLayout(layout);
		
		JButton startButton = new JButton(new ImageIcon("res/start.png"));
		startButton.setToolTipText("Start server");
		JButton stopButton = new JButton(new ImageIcon("res/stop.png"));
		JTextArea logArea = new JTextArea();
		stopButton.setToolTipText("Stop server");
		JScrollPane logScrollPane = new JScrollPane(logArea);
		
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
