package it.gb.salestaxes.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.gb.salestaxes.daoImpl.ProductsDAOImpl;
import it.gb.salestaxes.util.GlobalStorage;
import it.gb.salestaxes.util.ListenerProxies;
import it.gb.salestaxes.util.UtilConstants;
import it.gb.salestaxes.util.UtilMethods;

public class ReceiptWindow {
	static GlobalStorage gs = GlobalStorage.getInstance();
	static ListenerProxies listenerP = new ListenerProxies();
	static ClassLoader classL = new CartWindow().getClass().getClassLoader();
	static UtilMethods utilM = new UtilMethods();
	static Object objMainWindow = new MainWindow();
	static ProductsDAOImpl productsDAOImpl = new ProductsDAOImpl();

	public static void receiptWindow() {

		JFrame frameSave = new JFrame();

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save your receipt as: ");

		String confirmation = "";

		int userSelection = fileChooser.showSaveDialog(frameSave);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			System.out.println("Save your receipt as: " + fileToSave.getAbsolutePath());
			try {
				Files.write(fileToSave.toPath(), gs.getLines(), StandardCharsets.UTF_8);
				confirmation = UtilConstants.OK;
				productsDAOImpl.updateCSV(gs.getProductslist());
				gs.clearAll();
			} catch (IOException e) {
				confirmation = UtilConstants.KO;
				e.printStackTrace();
			}

			JFrame frame = new JFrame();
			JPanel panel = new JPanel();
			GridBagLayout layout = new GridBagLayout();

			panel.setLayout(layout);
			GridBagConstraints gbc = new GridBagConstraints();

			JLabel labelCheckout = new JLabel(confirmation);

			labelCheckout.setFont(UtilConstants.FONT_DESCRIPTION);
			gbc.fill = GridBagConstraints.VERTICAL;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.ipady = 20;
			panel.add(labelCheckout, gbc);

			JLabel labelSpace = new JLabel("");
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.ipady = 100;
			panel.add(labelSpace, gbc);

			JButton bMenu = new JButton("MENU");
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.ipady = 0;
			panel.add(bMenu, gbc);

			bMenu.addActionListener(listenerP.actionListener(utilM, "redirect", objMainWindow.getClass(), "mainWindow", frame));

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Container containerPane = frame.getContentPane();
			containerPane.add(panel, BorderLayout.NORTH);

			frame.setUndecorated(true);
			frame.setPreferredSize(new Dimension(800, 300));

			frame.pack();
			frame.setVisible(true);
		}

	}

}
