package it.gb.salestaxes.GUI;

import java.awt.*;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.gb.salestaxes.util.ListenerProxies;
import it.gb.salestaxes.util.UtilMethods;
public class MainWindow extends Frame
{
	private static final long serialVersionUID = 1L;

	// starting window
public static void mainWindow()
  {
	UtilMethods utilM = new UtilMethods();
	Object obj = new SecondWindow();   
	JPanel panel = new JPanel();
	panel.setLayout(new BorderLayout());
	panel.add(new JLabel("Welcome SalesTaxes store"), BorderLayout.NORTH);
	panel.add(new JLabel("Click to go shopping"), BorderLayout.SOUTH);
	JButton bProducts = new JButton("Product List");
	
	// ListenerProxies class contains methods that allows to execute generics methods
	ListenerProxies listenerP = new ListenerProxies();
	JFrame frame = new JFrame("Menu");
	// The button has his own ActionListener that executes a specific method
	bProducts.addActionListener(listenerP.actionListener(utilM, "redirect", obj.getClass(), "productsWindow", frame));
	JPanel bPanel = new JPanel();
	bPanel.add(bProducts);
	
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Container containerPane = frame.getContentPane();
	containerPane.add(panel, BorderLayout.NORTH);
	containerPane.add(bPanel, BorderLayout.SOUTH);
	frame.setPreferredSize(new Dimension(400, 300));
	frame.pack();
	frame.setVisible(true);

  }
  
  public static void main(String[] arg)
  {
    mainWindow();
    System.out.println("New Window");
  }

}