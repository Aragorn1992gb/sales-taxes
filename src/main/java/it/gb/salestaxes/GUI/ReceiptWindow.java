package it.gb.salestaxes.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

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
	static UtilConstants utilC = new UtilConstants();
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
				confirmation = utilC.OK;
				productsDAOImpl.updateCSV(gs.getProductslist());
				gs.clearAll();
			} catch (IOException e) {
				confirmation = utilC.KO;
				e.printStackTrace();
			}
		    
		    JFrame frame = new JFrame();
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(new JLabel(confirmation), BorderLayout.NORTH);
			
			JButton bMenu = new JButton("MENU");
			
			bMenu.addActionListener(listenerP.actionListener(utilM, "redirect", objMainWindow.getClass(), "mainWindow", frame));
			JPanel bPanel = new JPanel();
			bPanel.add(bMenu);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Container containerPane = frame.getContentPane();
			containerPane.add(panel, BorderLayout.NORTH);
			containerPane.add(bPanel, BorderLayout.SOUTH);

			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setUndecorated(true);

			// frame.setPreferredSize(new Dimension(400, 300));
			frame.pack();
			frame.setVisible(true);
		}

		
	}
	
	
}
