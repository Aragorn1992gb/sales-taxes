package it.gb.salestaxes.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import it.gb.salestaxes.bean.ProductsBean;
import it.gb.salestaxes.util.ButtonColumn;
import it.gb.salestaxes.util.GlobalStorage;
import it.gb.salestaxes.util.UtilConstants;
import it.gb.salestaxes.util.UtilMethods;

import static org.mockito.Mockito.*;

public class UtilMethodsTest {
	
	List<ProductsBean> mockList = new ArrayList<>();
	Object[][] data = new Object[mockList.size()][UtilConstants.columnTypes.size() + UtilConstants.OTHER_COLUMNS];
	List<String> mockColumns  = mock(List.class);
	
	
	HashMap<Integer, Class> columnTypes = UtilConstants.columnTypes;
	
	int[] editableColumns = { 5 };
	int disabledColumn = 0;
	
	static GlobalStorage gs = GlobalStorage.getInstance();

	

	@Test
	public void canDefineEnabledColumn() {
		DefaultTableModel tableModel =UtilMethods.generateTypeTableModel(data, mockColumns, columnTypes, editableColumns);
		assertTrue(tableModel.isCellEditable(0, editableColumns[0]));
		assertFalse(tableModel.isCellEditable(0, disabledColumn));
		System.out.println("@Test that checks considered column is enabled ora disabled");
	}
	
	@Test
	public void canGetColumnType() {
		DefaultTableModel tableModel =UtilMethods.generateTypeTableModel(data, mockColumns, columnTypes, editableColumns);
		assertEquals(tableModel.getColumnClass(0), UtilConstants.columnTypes.get(0));
		assertEquals(tableModel.getColumnClass(0),String.class);
		assertNotEquals(tableModel.getColumnClass(0), Integer.class);
		System.out.println("@Test that check the column type");
	}
	
//	@Test
//	public void canPutToCart() {
//		int units = gs.getProductslist().get(1).getUnits();
//		CSVRecord productHeaders = productsDAOImpl.findProductsHeaders();
//		List<String> columns = new ArrayList<String>();
//
//		columns.add(productHeaders.get(1));
//		columns.add(productHeaders.get(8));
//		columns.add(productHeaders.get(10));
//		columns.add(productHeaders.get(2));
//		columns.add(productHeaders.get(3));
//		columns.add("Add To Cart");
//		DefaultTableModel tableModel = UtilMethods.generateTypeTableModel(data, columns, UtilConstants.columnTypes, editableColumns);
//
//		JTable table = new JTable(tableModel);
//		Action a = UtilMethods.addToCartClick;
//		ActionEvent e = null;
//		ActionEvent ee = new ActionEvent(, a, 5);
//		ButtonColumn buttonColumn = new ButtonColumn(table, utilM.addToCartClick, 5);
//		e.setSource("java.awt.event.ActionEvent[ACTION_PERFORMED,cmd=1,when=0,modifiers=] on javax.swing.JTable[,0,0,450x900,invalid,alignmentX=0.0,alignmentY=0.0,border=,flags=251658568,maximumSize=,minimumSize=,preferredSize=,autoCreateColumnsFromModel=true,autoResizeMode=AUTO_RESIZE_SUBSEQUENT_COLUMNS,cellSelectionEnabled=false,editingColumn=-1,editingRow=-1,gridColor=javax.swing.plaf.ColorUIResource[r=122,g=138,b=153],preferredViewportSize=java.awt.Dimension[width=450,height=480],rowHeight=30,rowMargin=1,rowSelectionAllowed=true,selectionBackground=javax.swing.plaf.ColorUIResource[r=184,g=207,b=229],selectionForeground=sun.swing.PrintColorUIResource[r=51,g=51,b=51],showHorizontalLines=true,showVerticalLines=true]");
//		int modelRow = Integer.valueOf(e.getActionCommand());
//		//	a.actionPerformed(5);
//		String as = "ciao";
//	}
	
//	@Test
//	public void testEventListener() {
//		
//	  ActionEvent mockEvent = mock(ActionEvent.class);
//	  // Or just create a new ActionEvent, e.g. new ActionEvent();
//	  // setup mock
//
//	  subjectUnderTest.actionPerformed(mockEvent);
//
//	  // validate
//	}
	

}
