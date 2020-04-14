package it.gb.salestaxes.GUI;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.gb.salestaxes.util.ListenerProxies;
import it.gb.salestaxes.util.UtilConstants;
import it.gb.salestaxes.util.UtilMethods;

public class MainWindow extends Frame {
	private static final long serialVersionUID = 1L;

	// starting window
	public static void mainWindow() {
		UtilMethods utilM = new UtilMethods();
		Object obj = new SecondWindow();
		
		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();

		panel.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel labelWelcome = new JLabel("Welcome to SalesTaxes store");
		labelWelcome.setFont(UtilConstants.FONT_TITLE);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipady = 0;
		panel.add(labelWelcome, gbc);

		JLabel labelSpace = new JLabel("");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipady = 100;
		panel.add(labelSpace, gbc);

		JLabel labelShopping = new JLabel("Click to go shopping");
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.ipady = 0;
		panel.add(labelShopping, gbc);

		JButton bProducts = new JButton("Product List");
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 10;
		gbc.ipady = 10;
		panel.add(bProducts, gbc);

		ListenerProxies listenerP = new ListenerProxies();
		JFrame frame = new JFrame("Menu");
		bProducts.addActionListener(
				listenerP.actionListener(utilM, "redirect", obj.getClass(), "productsWindow", frame));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container containerPane = frame.getContentPane();
		containerPane.add(panel, BorderLayout.NORTH);

		frame.setPreferredSize(new Dimension(400, 300));
		frame.pack();
		frame.setVisible(true);

	}

	public static void main(String[] arg) {
		mainWindow();
		System.out.println("New Window");
	}

}