package catTool;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ConvolveOp;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import java.io.BufferedReader;
import java.io.BufferedWriter;
//import java.io.DataInputStream;
import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.LineNumberReader;
import java.io.Serializable;
import java.util.ArrayList;
//import java.util.EmptyStackException;
import java.util.List;
import java.util.Scanner;
//import java.util.Stack;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Element;
import javax.swing.text.Highlighter;

public class Debugtool1 extends JPanel implements Serializable {
	private static final long serialVersionUID = 1L;
	final Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
	/** stores end of file character */
	final static int AE5D_EOF_CODE = 0x1a;

	private static boolean fixedFormat = true;

	private boolean fromFile = false;

	protected String newline = "\n";

	// private static final String PREVIOUS = null;

	// private static final String UP = null;

	// private static final String NEXT = null;
	// protected static final BuildEvent Event = null;
	public static final String EOL = "\n";
	// private static final int[] noLines = null;
	// private static final int l = 1;
	protected static int line = 0;

	/**
	 * stores true or false value based on whether the format of the file is
	 * fixed or free
	 */
	String[] Line;
	// private List<Log> logs;
	// private static boolean fixedFormat = true;

	/**
	 * stores true or false value based on whether the data of file is coming as
	 * a IFile object or as a string
	 */
	// private boolean fromFile = false;

	/**
	 * stores the value of the root element in the tree containing the structure
	 * of the cobol program
	 */
	// private Element startElement = new Element();

	static String someText;
	JFrame jfDebug;
	JMenuBar JMenu;
	JMenu fileMenu, editMenu, DebugMenu, subMenu, subMenu1;

	int n1;
	JMenuItem New, open, save, exit, copy, paste, SelectAll, cut, Find,
			Findnext, Delete, Debug;
	JTextArea jtaProgramContent, jtaErrorDisplay, jtaLinenumber, status1;
	JFileChooser fc;
	JFormattedTextField jft;
	long numlines1 = 0;

	protected static String filename;
	JScrollPane scroll, scroll1;
	JScrollPane scroll2;
	JLabel l1;
	JProgressBar jpb;
	JPanel jp;

	protected StringBuffer strSrc;

	protected int Index;
	JButton Toolcut, Toolopen,tlDebug,tlToggleNextLine,tlStop;
	JToolBar JTool;
	String wholeText, findString, fileName = null;
	int ind = 0;
	private JTextField status;
	protected int findString1;
	protected CharSequence[] storeContents;
	private int lineNumber;
	private String[] colourNames = { "RED", "ORANGE", "CYAN", "pink" };
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cbox = new JComboBox(colourNames);
	private JTextField lineField;
	JTextField tNext, tCurrent, tPrevious,tTotalLines;

	private Highlighter.HighlightPainter painter;
	private Highlighter.HighlightPainter painter1;
	Image ImageX;
	ImageIcon ImageIconX;

	/*String[] cblKeyword = { "ADD", "SUBSTRACT", "MULTIPLY", "DIVIDE", "ACCEPT",
			"DISPLAY", "STOP RUN." };*/

	List<String> Variables = new ArrayList<>();
	List<String> DataTypes = new ArrayList<>();
	List<String> VariableValues = new ArrayList<>();
	List<String> VariableSize = new ArrayList<>();
	
	//----This is the variable to check the Divisions------
	
	boolean flgID = false;
	boolean flgProgrmID = false;
	boolean flgED = false;
	boolean flgDD = false;
	boolean flgDec = false;
	boolean flgWSS = false;
	boolean flgPD = false;

	public Debugtool1(final String FilePath) {

		// logs = new ArrayList<Log>();
		tNext = new JTextField();
		// next.setVisible(false);
		tNext.setSize(100, 100);
		tCurrent = new JTextField();
		tPrevious = new JTextField();
		jfDebug = new JFrame("Debugging Tool");
		jfDebug.setVisible(true);
		jfDebug.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfDebug.setLocation(250, 250);
		// Dimension size = extracted();
		jfDebug.setSize(1024, 1024);
		JMenu = new JMenuBar();
		JMenu.setBorder(new BevelBorder(BevelBorder.RAISED));

		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);

		// subMenu = new JMenu("SubMenu");
		// subMenu1 = new JMenu("SubMenu2");

		DebugMenu = new JMenu("Run");
		DebugMenu.setMnemonic(KeyEvent.VK_R);

		New = new JMenuItem("New");
		New.setToolTipText("New File");
		open = new JMenuItem("Open");
		save = new JMenuItem("Save");
		save.setMnemonic(KeyEvent.VK_S);
		KeyStroke ks = KeyStroke.getKeyStroke("control findString");
		save.setAccelerator(ks);
		exit = new JMenuItem("Exit");

		copy = new JMenuItem("Copy");
		cut = new JMenuItem("Cut");
		paste = new JMenuItem("Paste");
		Delete = new JMenuItem("Delete");
		Find = new JMenuItem("Find");
		SelectAll = new JMenuItem("" + "SelectAll");
		Findnext = new JMenuItem("Findnext");
		Debug = new JMenuItem("Debug");

		fileMenu.add(New);
		fileMenu.add(open);
		fileMenu.addSeparator();
		fileMenu.add(save);
		fileMenu.add(exit);

		editMenu.add(copy);
		editMenu.add(cut);
		editMenu.add(paste);
		editMenu.add(Delete);
		editMenu.add(Find);
		editMenu.add(Findnext);
		// editMenu.add(Goto);
		editMenu.add(SelectAll);

		// .add(Build);
		DebugMenu.add(Debug);
		// Finally, add all the menus to the menu bar
		JMenu.add(fileMenu);
		JMenu.add(editMenu);
		JMenu.add(DebugMenu);

		jfDebug.setJMenuBar(JMenu);

		JTool = new JToolBar();
		JTool.setBorder(new EtchedBorder());
		// JTool.setSize(1000, 100);

		// JTool = new JToolBar();
		ImageIcon iconnew = new ImageIcon("D:/allimages/new.png");
		JButton bnew = new JButton(iconnew);
		JTool.add(bnew);
		bnew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent NewEvent) {

				jtaProgramContent.setText("");

			}
		});

		ImageIcon iconopen = new ImageIcon("D:/allimages/open.png");
		JButton bopen = new JButton(iconopen);
		JTool.add(bopen);
		bopen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(jfDebug)) {
					File file = fc.getSelectedFile();
					jtaProgramContent.setText("");
					Scanner in = null;
					try {
						in = new Scanner(file);
						while (in.hasNext()) {
							String line = in.nextLine();
							jtaProgramContent.append(line + "\n");
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						in.close();
					}
				}
			}
		});

		ImageIcon iconSave = new ImageIcon("D:/allimages/save.png");
		JButton bSave = new JButton(iconSave);
		JTool.add(bSave);
		bSave.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				FileDialog fdsave = new FileDialog(jfDebug, "Save this file:",
						FileDialog.SAVE);
				fdsave.setVisible(true);
				if (fdsave.getFile() != null) {
					filename = fdsave.getDirectory() + fdsave.getFile();
					fdsave.setFile(filename);
					// File file=new File(filename);
					try {
						BufferedWriter fileOut = new BufferedWriter(
								new FileWriter(filename));
						String myString1 = jtaProgramContent.getText();
						int totalLines = jtaProgramContent.getLineCount();
						for (int i = 0; i < totalLines; i++) {
							int start = jtaProgramContent.getLineStartOffset(i);

							int end = jtaProgramContent.getLineEndOffset(i);
							String line = myString1.substring(start, end);
							// String myString2 = myString1.replace("\r\n",
							// "\n");

							// System.out.println(myString2);

							fileOut.write(line);
							fileOut.write("\r\n");
							fileOut.flush();
						}
						fileOut.close();
					} catch (IOException | BadLocationException ioe) {
						ioe.printStackTrace();
					}
				}
			}
		});

		ImageIcon iconcut = new ImageIcon("D:/allimages/cut.png");
		JButton bcut = new JButton(iconcut);
		JTool.add(bcut);
		bcut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				String selection = jtaProgramContent.getSelectedText();
				StringSelection data = new StringSelection(selection);
				clip.setContents(data, data);
				jtaProgramContent.replaceRange("",
						jtaProgramContent.getSelectionStart(),
						jtaProgramContent.getSelectionEnd());
			}
		});

		ImageIcon iconcopy = new ImageIcon("D:/allimages/copy.png");
		JButton bcopy = new JButton(iconcopy);
		JTool.add(bcopy);
		bcopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selection = jtaProgramContent.getSelectedText();
				StringSelection data = new StringSelection(selection);
				clip.setContents(data, data);
			}
		});
		
				ImageIcon iconDebug = new ImageIcon("D:/allimages/Debug1.png");

		tlDebug=new JButton(iconDebug);
		JTool.add(tlDebug);
		
		ImageIcon iconNext = new ImageIcon("D:/allimages/Next.png");
		tlToggleNextLine=new JButton(iconNext);
		tlToggleNextLine.setEnabled(false);
		
		KeyStroke ks1 = KeyStroke.getKeyStroke("control findString");
	 
		JTool.add(tlToggleNextLine);
		
		ImageIcon iconStop = new ImageIcon("D:/allimages/Stop.png");
		tlStop=new JButton(iconStop);		
		JTool.add(tlStop);
		tlStop.setEnabled(false);
		
		tTotalLines=new JTextField();
		JTool.add(tTotalLines);
		JTool.add(tPrevious);
		JTool.add(tCurrent);
		JTool.add(tNext);
		
		tTotalLines.setVisible(false);
		tPrevious.setVisible(false);
		tCurrent.setVisible(false);
		tNext.setVisible(false);
		
		jtaProgramContent = new JTextArea();
		jtaProgramContent.setSize(10, 10);
		jtaProgramContent.setBackground(Color.white);
		jtaProgramContent.setLineWrap(true);

		jtaProgramContent.setWrapStyleWord(true);
		jtaProgramContent.addCaretListener(new CaretListener() {

			public void caretUpdate(CaretEvent e) {

				JTextArea editArea = (JTextArea) e.getSource();

				int linenum = 1;
				int columnnum = 1;

				try {

					int caretpos = editArea.getCaretPosition();
					// jtaLinenumber.append("line" + caretpos);
					linenum = editArea.getLineOfOffset(caretpos);

					columnnum = caretpos - editArea.getLineStartOffset(linenum);

					linenum += 1;
				} catch (Exception ex) {
				}

				// Once we know the position of the line and the column, pass it
				// to a helper function for updating the status bar.
				updateStatus(linenum, columnnum);
			}
		});
		jtaProgramContent.getDocument().addDocumentListener(
				new DocumentListener() {
					public String getText() {
						int caretPosition = jtaProgramContent.getDocument()
								.getLength();
						Element root = jtaProgramContent.getDocument()
								.getDefaultRootElement();
						String text = "1"
								+ System.getProperty("line.separator");
						for (int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
							text += i + System.getProperty("line.separator");
						}
						return text;
					}

					@Override
					public void changedUpdate(DocumentEvent de) {
						jtaLinenumber.setText(getText());
					}

					@Override
					public void insertUpdate(DocumentEvent de) {
						jtaLinenumber.setText(getText());
					}

					@Override
					public void removeUpdate(DocumentEvent de) {
						jtaLinenumber.setText(getText());

					}

				});
		jtaProgramContent.addKeyListener(new KeyAdapter() {
			public void KeyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F12) {
					// JOptionPane.showMessageDialog(jtaProgramContent,
					// jtaProgramContent.getText());
					DefaultHighlighter h = (DefaultHighlighter) jtaProgramContent
							.getHighlighter();
					Highlighter.Highlight[] higt = h.getHighlights();
					try {
						int start = jtaProgramContent
								.getLineStartOffset(lineNumber);
						int end = jtaProgramContent
								.getLineEndOffset(lineNumber);
						DefaultHighlightPainter pink = new DefaultHighlighter.DefaultHighlightPainter(
								Color.pink);
						h.addHighlight(end, start, pink);
						for (int i = 0; i < ((CharSequence) pink).length(); i++) {
							while (Character.isWhitespace(((CharSequence) pink)
									.charAt(i))) {
								start++;
								break;
							}
						}
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						Logger.getLogger(jfDebug.getName()).log(Level.SEVERE,
								null, e);
					}
				}
			}
		});

		scroll = new JScrollPane();

		jtaErrorDisplay = new JTextArea();
		jtaErrorDisplay.setSize(300, 100);
		jtaErrorDisplay.setBackground(Color.LIGHT_GRAY);
		jtaErrorDisplay.setLineWrap(true);
		jtaErrorDisplay.setEditable(false);
		scroll1 = new JScrollPane(jtaErrorDisplay);

		jtaLinenumber = new JTextArea("1");

		jtaLinenumber.setBackground(Color.LIGHT_GRAY);
		jtaLinenumber.setEditable(false);

		status = new JTextField();
		status.setBackground(Color.LIGHT_GRAY);
		status.setBorder(new BevelBorder(BevelBorder.LOWERED));

		// scroll2.add(status1);
		jfDebug.setJMenuBar(JMenu);
		jfDebug.getContentPane().add(JTool, BorderLayout.NORTH);
		scroll.getViewport().add(jtaProgramContent);
		scroll.setRowHeaderView(jtaLinenumber);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		jfDebug.add(scroll, BorderLayout.CENTER);
		jfDebug.add(scroll1, BorderLayout.EAST);

		jfDebug.add(status, BorderLayout.SOUTH);
		jfDebug.setSize(500, 500);
		jfDebug.setVisible(true);

		fc = new JFileChooser();

		jtaProgramContent.append(FilePath);
		// String str4 = new String();
		
       
      
		New.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent NewEvent) {

				jtaProgramContent.setText("");

			}
		});

		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});

		open.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(jfDebug)) {
					File file = fc.getSelectedFile();
					jtaProgramContent.setText("");
					Scanner in = null;
					try {
						in = new Scanner(file);
						while (in.hasNext()) {
							String line = in.nextLine();
							jtaProgramContent.append(line);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						in.close();
					}
				}
			}
		});

		save.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				FileDialog fdsave = new FileDialog(jfDebug, "Save this file:",
						FileDialog.SAVE);
				fdsave.setVisible(true);
				if (fdsave.getFile() != null) {
					filename = fdsave.getDirectory() + fdsave.getFile();
					fdsave.setFile(filename);
					// File file=new File(filename);
					try {
						BufferedWriter fileOut = new BufferedWriter(
								new FileWriter(filename));
						String myString1 = jtaProgramContent.getText();
						int totalLines = jtaProgramContent.getLineCount();
						for (int i = 0; i < totalLines; i++) {
							int start = jtaProgramContent.getLineStartOffset(i);

							int end = jtaProgramContent.getLineEndOffset(i);
							String line = myString1.substring(start, end);
							// String myString2 = myString1.replace("\r\n",
							// "\n");

							// System.out.println(myString2);

							fileOut.write(line);
							// fileOut.write("\n");
							fileOut.flush();
						}
						fileOut.close();
					} catch (IOException | BadLocationException ioe) {
						ioe.printStackTrace();
					}
				}
			}
		});

		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selection = jtaProgramContent.getSelectedText();
				StringSelection data = new StringSelection(selection);
				clip.setContents(data, data);
			}
		});

		paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Transferable cliptran = clip.getContents(jfDebug);
				try {
					String sel = (String) cliptran
							.getTransferData(DataFlavor.stringFlavor);
					jtaProgramContent.replaceRange(sel,
							jtaProgramContent.getSelectionStart(),
							jtaProgramContent.getSelectionEnd());
				} catch (Exception exc) {
					System.out.println("not string flavour");
				}
			}
		});

		cut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				String selection = jtaProgramContent.getSelectedText();
				StringSelection data = new StringSelection(selection);
				clip.setContents(data, data);
				jtaProgramContent.replaceRange("",
						jtaProgramContent.getSelectionStart(),
						jtaProgramContent.getSelectionEnd());
			}
		});
		SelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent selEvent) {
				if (selEvent.getSource() == SelectAll)
					jtaProgramContent.selectAll();
			}
		});
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent DelEvent) {
				String selection = jtaProgramContent.getSelectedText();
				StringSelection data = new StringSelection(selection);
				clip.setContents(data, data);
				jtaProgramContent.replaceRange("",
						jtaProgramContent.getSelectionStart(),
						jtaProgramContent.getSelectionEnd());
			}
		});
		Find.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent FindEvent) {
				wholeText = jtaProgramContent.getText();
				findString = JOptionPane.showInputDialog(null,
						"Find what(UPPER-CASE)", "Find",
						JOptionPane.INFORMATION_MESSAGE);
				ind = wholeText.indexOf(findString, 0);
				jtaProgramContent.setCaretPosition(ind);
				jtaProgramContent.setSelectionStart(ind);
				int a = ind + findString.length();
				// txtArea.SelectionEnd( a );
				jtaProgramContent.setSelectionEnd(a);

			}

		});
		Findnext.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent FindnextEvent) {
				if ((FindnextEvent.getSource() == Findnext)) {
					wholeText = jtaProgramContent.getText();
					findString = JOptionPane.showInputDialog(null,
							"Find what(UPPER-CASE)", "Find Next",
							JOptionPane.INFORMATION_MESSAGE);
					ind = wholeText.indexOf(findString,
							jtaProgramContent.getCaretPosition());
					jtaProgramContent.setCaretPosition(ind);

					jtaProgramContent.setSelectionStart(ind);

					jtaProgramContent.setSelectionEnd(ind + findString.length());
				}

			}
		});
		//Debug.disable();

		tlDebug.addActionListener(new ActionListener() {		
			
			public void actionPerformed(ActionEvent jtDebugEvent) {
				Debugg();				
			}
		});
		Debug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent DebugEvent) {
				Debugg();
			}
		});
		
		tlToggleNextLine.addActionListener(new ActionListener() {			
				public void actionPerformed(ActionEvent jtDebugEvent) {
					int totalline = jtaProgramContent.getLineCount();
					int p,c,n,t;
					
					//jtaProgramContent.setEditable(true);
					t = Integer.parseInt(tTotalLines.getText());
					p = Integer.parseInt(tPrevious.getText());
					c = Integer.parseInt(tCurrent.getText());				
					n = Integer.parseInt(tNext.getText());
					
					if(DebugMethod(c)==true)
					{
						p = Integer.parseInt(tPrevious.getText())+1;
						c = Integer.parseInt(tCurrent.getText())+1;				
						n = Integer.parseInt(tNext.getText());
					}
					else
					{
						jtaErrorDisplay.append("\nCorrect the error");
					}
					if(n<t)
						n = Integer.parseInt(tNext.getText()) + 1;
					if(p!=-1)
					{					
						try 
						{						
							int pstart = jtaProgramContent.getLineStartOffset(p);
							int pend = jtaProgramContent.getLineEndOffset(p);
							
							painter = new DefaultHighlighter.DefaultHighlightPainter(Color.WHITE);
							jtaProgramContent.getHighlighter().addHighlight(pstart, pend, painter);
						}
						catch(Exception e)
						{
								
						}
					}
					
					tPrevious.setText(String.valueOf(p));
					tCurrent.setText(String.valueOf(c));
					tNext.setText(String.valueOf(n));				
				}
			});
	       tlStop.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent jtDebugEvent) {
					jtaProgramContent.setEditable(true);
					Debug.setEnabled(true);
					tlDebug.setEnabled(true);
					tlToggleNextLine.setEnabled(false);
					tlStop.setEnabled(false);
					jtaProgramContent.getHighlighter().removeAllHighlights();
					jtaErrorDisplay.setText("");
					jtaProgramContent.setEditable(true);
				}
			});
	        
		
	}
	public void Debugg() {
		Variables.clear();
		DataTypes.clear();
		VariableValues.clear();
		VariableSize.clear();
		flgID = false;
		flgProgrmID = false;
		flgED = false;
		flgDD = false;
		flgDec = false;
		flgWSS = false;
		flgPD = false;
		jtaProgramContent.setEditable(false);
		int totalline = jtaProgramContent.getLineCount();
		if(jtaProgramContent.getText().length()==0)
		{
			jtaErrorDisplay.setText("Load Cobol Program");
			return;
		}
		tTotalLines.setText(String.valueOf(totalline));
		tPrevious.setText(String.valueOf("-1"));
		tCurrent.setText(String.valueOf("0"));
		tNext.setText(String.valueOf("1"));
		//for (int i = 0; i < totalline; i++) 
		//{
		DebugMethod(0);
		Debug.setEnabled(false);
		tlDebug.setEnabled(false);
		tlToggleNextLine.setEnabled(true);
		tlStop.setEnabled(true);
		//}
		//jtaErrorDisplay.append("No. of Variables :" + Variables.size());
		for (int i = 0; i < Variables.size(); i++) {
			jtaErrorDisplay.append("\n" + Variables.get(i) + "    "
					+ VariableSize.get(i) + "   "
					+ VariableValues.get(i));
		}
		/*int i=0;
		int t =0;
		try {
			t = jtaProgramContent.getLineStartOffset(i);
		} catch (BadLocationException e) {
			
			e.printStackTrace();
		}
		tPrevious.setText(String.valueOf(t-1));
		tCurrent.setText(String.valueOf(t));
		tNext.setText(String.valueOf(++t));*/

	}
	public boolean DebugMethod(int i)
	{
		String str = jtaProgramContent.getText();
		String[] words = str.split(" ");
		String strNewLine = str.toString();
		/*
		 * int index = 0; String identification =
		 * "IDENTIFICATION DIVISION."; //String id = "ID"; String
		 * Env="ENVIRONMENT DIVISION."; String Data="DATA DIVISION.";
		 * String Procedure="PROCEDURE DIVISION.";
		 */
		/*boolean flgID = false;
		boolean flgProgrmID = false;
		boolean flgED = false;
		boolean flgDD = false;
		boolean flgDec = false;
		boolean flgWSS = false;
		boolean flgPD = false;*/
	
		//JOptionPane.showMessageDialog(null,jtaProgramContent.getLineCount(), "text", 1);
	
		int ct = 1;
		int opt = 0;
		int flag = 0;
		String text = jtaProgramContent.getText();
		int totalline = jtaProgramContent.getLineCount();
		jtaErrorDisplay.setText(null);
		//Variables.clear();
		//DataTypes.clear();
		//VariableValues.clear();
		//VariableSize.clear();
		//for (int i = 0; i < totalline; i++) 
		//{
		
			try 
			{
				//1/jtaProgramContent.setEditable(false);
				//int i=0;
				int start = jtaProgramContent.getLineStartOffset(i);
				int end = jtaProgramContent.getLineEndOffset(i);
				int pstart=0;
				int pend=0 ;
				/*if(i-1!=-1)
				{
					pstart = jtaProgramContent.getLineStartOffset(0);
					pend = jtaProgramContent.getLineEndOffset(0);
					
					painter1 = new DefaultHighlighter.DefaultHighlightPainter(Color.WHITE);
					jtaProgramContent.getHighlighter().addHighlight(pstart, pend, painter1);
					//jtaProgramContent.getHighlighter().removeHighlight(tag)
					
				}*/
				
				
				String line = text.substring(start, end);
				painter = new DefaultHighlighter.DefaultHighlightPainter(Color.PINK);
				jtaProgramContent.getHighlighter().removeAllHighlights();
				jtaProgramContent.getHighlighter().addHighlight(start, end, painter);
				
				line = line.replaceAll("\t", "");
				
				if (!line.trim().equals("")) 
				{
					if (line.trim().endsWith(".\n") || line.trim().endsWith(".")) 
					{
						// jtaErrorDisplay.append("Line "+(i+1)+": Valid Termination\n");
	
					} 
					else 
					{
						jtaErrorDisplay.append("Line " + (i + 1)+ ": \".\" is expected \n");
						//return false;
					}
					if (flgID == false && ct == 1) 
					{
						if (CheckIdentificationDivision(line) == true) 
						{
							flgID = true;
							ct++;
							opt = i;
							jtaErrorDisplay.append("Line " + (i + 1)+ ": IDENTIFICATION DIVISION "+"\n");
							//JOptionPane.showMessageDialog(null,"IDENTIFICATION DIVISION is Correct","text", 1);
						} 
						else 
						{
							jtaErrorDisplay.append("Line " + (i + 1)+ ": IDENTIFICATION DIVISION is missing"+"\n");
							//JOptionPane.showMessageDialog(null,"IDENTIFICATION DIVISION is incorrect","text", 0);
							return false;
						}
					}
	
					if (flgProgrmID == false && i > opt) 
					{
						if (CheckProgramID(line) == true) 
						{
							flgProgrmID = true;
							ct++;
							opt = i;
							jtaErrorDisplay.append("Line " + (i + 1)+ ": IPROGRAM-ID is Correct"+"\n");
							//JOptionPane.showMessageDialog(null,"PROGRAM-ID is Correct", "text", 1);
						} 
						else 
						{
							jtaErrorDisplay.append("Line " + (i + 1)+ ": PROGRAM-ID is missing"+"\n");
							//JOptionPane.showMessageDialog(null,"PROGRAM-ID is incorrect", "text",0);
							return false;
						}
					}
	
					if (flgED == false && i > opt) 
					{
						if (CheckEnvironmentDivision(line) == true) 
						{
							flgED = true;
							ct++;
							opt = i;
							jtaErrorDisplay.append("Line " + (i + 1)+ ": ENVIRONMENT DIVISION is Correct"+"\n");
							//JOptionPane.showMessageDialog(null,"ENVIRONMENT DIVISION is Correct","text", 1);
						} 
						else 
						{
							jtaErrorDisplay.append("Line " + (i + 1)+ ": ENVIRONMENT DIVISION is missing"+"\n");
							//JOptionPane.showMessageDialog(null,"ENVIRONMENT DIVISION is incorrect","text", 0);
							return false;
						}
					}
					if (flgED == false && i > opt) 
					{
						if (CheckEnvironmentDivision(line) == true) 
						{
							flgED = true;
							ct++;
							opt = i;
							jtaErrorDisplay.append("Line " + (i + 1)+ ": ENVIRONMENT DIVISION is Correct"+"\n");
							//JOptionPane.showMessageDialog(null,"ENVIRONMENT DIVISION is Correct","text", 1);
						} 
						else 
						{
							jtaErrorDisplay.append("Line " + (i + 1)+ ": ENVIRONMENT DIVISION is missing"+"\n");
							//JOptionPane.showMessageDialog(null,"ENVIRONMENT DIVISION is incorrect","text", 0);
							return false;
						}
					}
	
					if (flgDD == false && i > opt) 
					{
						if (CheckDataDivision(line) == true) 
						{
							flgDD = true;
							ct++;
							opt = i;
							jtaErrorDisplay.append("Line " + (i + 1)+ ": DATA DIVISION is Correct"+"\n");
							//JOptionPane.showMessageDialog(null,"DATA DIVISION is Correct", "text",1);
						} 
						else 
						{
							jtaErrorDisplay.append("Line " + (i + 1)+ ": DATA DIVISION is missing"+"\n");
							//JOptionPane.showMessageDialog(null,"DATA DIVISION is missing","text", 0);
							return false;
						}
					}
	
					if (flgWSS == false && i > opt) 
					{
						if (CheckWorkStorageSection(line) == true) 
						{
							flgWSS = true;
							ct++;
							opt = i;
							jtaErrorDisplay.append("Line " + (i + 1)+ ": VARIABLE WORK-STORAGE is Correct"+"\n");
							//JOptionPane.showMessageDialog(null,"VARIABLE WORK-STORAGE is Correct","text", 1);
						} 
						else 
						{
							jtaErrorDisplay.append("Line " + (i + 1)+ ": VARIABLE WORK-STORAGE is missing"+"\n");
							//JOptionPane.showMessageDialog(null,"VARIABLE WORK-STORAGE is incorrect",													"text", 0);
							return false;
						}
					}
					if (flgDec == false && i > opt) 
					{
						boolean flg = false;
						if (CheckVariableDelcaration(line) == true) 
						{
							// flgDec=true;
							ct++;
							opt = i;
							jtaErrorDisplay.append("Line " + (i + 1)+ ": VARIABLE DECLARATION is Correct"+"\n");
							//JOptionPane.showMessageDialog(null,"VARIABLE DECLARATION is Correct","text", 1);
						} 
						else 
						{
							if (CheckProcedureDivision(line) == true) 
							{
								flgDec = true;
								flgPD = true;
								ct++;
								opt = i;
								jtaErrorDisplay.append("Line " + (i + 1)+ ": PROCEDURE DIVISION is Correct"+"\n");
								//JOptionPane.showMessageDialog(null,"PROCEDURE DIVISION is Correct","text", 1);
							} 
							else if (CheckProcedureDivision(line) == false) 
							{
								jtaErrorDisplay.append("Line " + (i + 1)+ ": PROCEDURE DIVISION is missing"+"\n");
								//JOptionPane.showMessageDialog(null,"VARIABLE DECLARATION is incorrect","text", 0);
								return false;
							}
						}
	
					}
	
					if (flgPD == true && i > opt) 
					{
						String strKeyWord;
						strKeyWord = GetCobolKeyWords(line);
						if (strKeyWord != "ERROR") 
						{
							switch (strKeyWord) 
							{
								case "ACCEPT":
									if (CheckAccept(line) == true) 
									{
										jtaErrorDisplay.append("Accepts the value");
												
									} else 
									{
										jtaErrorDisplay.append("Line " + (i + 1)+ ": Error in accept"+"\n");
										//JOptionPane.showMessageDialog(null,"Line "	+ (i + 1)+ ": Error in accept","text", 0);
												
										return false;
									}
									break;
	
							case "ADD":
								if (CheckAdd(line) == true) 
								{
									jtaErrorDisplay.append("Add the values");
								} 
								else 
								{
									jtaErrorDisplay.append("Line " + (i + 1)+ ": Error in ADD"+"\n");
									//JOptionPane.showMessageDialog(null,"Line " + (i + 1)+ ": Error in ADD",													"text", 0);
								}
							break;
	
							case "SUBTRACT":
								if (Checksub(line) == true) 
								{
									jtaErrorDisplay
											.append("Sub the values");
	
								} 
								else 
								{
									jtaErrorDisplay.append("Line " + (i + 1)+ ": Error in SUB"+"\n");
									//JOptionPane.showMessageDialog(null,"Line " + (i + 1)+ ": Error in SUB",												"text", 0);
								}
							break;
							case "DIVIDE":
								if(CheckDiv(line) == true) 
								{
									jtaErrorDisplay
											.append("Divide the values");
	
								} 
								else 
								{
									jtaErrorDisplay.append("Line " + (i + 1)+ ": Error in DIV"+"\n");
									/*JOptionPane.showMessageDialog(null,
											"Line " + (i + 1)
													+ ": Error in DIV",
											"text", 0);*/
								}
								break;
							case "MULTIPLY":
								if (CheckMul(line) == true) {
									jtaErrorDisplay.append("Multiply the values");
	
								} else {
									jtaErrorDisplay.append("Line " + (i + 1)+ ": Error in Multiply"+"\n");
									//JOptionPane.showMessageDialog(null,"Line " + (i + 1)+ ": Error in Multiply","text", 0);
								}
	
						
							break;
							case "STOP":
								if (Checkstop(line) == true) {
									jtaErrorDisplay.append("executiion ended");
	
								} else {
									jtaErrorDisplay.append("Line " + (i + 1)+ ": ended"+"\n");
									//JOptionPane.showMessageDialog(null,"Line " + (i + 1)+ ": Error in Multiply","text", 0);
								}
	
						
							break;

							default:
								jtaErrorDisplay.append("Line " + (i + 1)+ ": Unknow keyword"+"\n");
								return false;								
							}
						}
							// JOptionPane.showMessageDialog(null,
							// strKeyWord , "text", 2);
					else if (strKeyWord == "ERROR") 
					{
							JOptionPane.showMessageDialog(null,
									strKeyWord, "text", 0);
							return false;
					}
					}
	
					/*
					 * if(flgPD==false && i>opt) {
					 * if(CheckProcedureDivision(line)==true) {
					 * flgPD=true; ct++; opt=i;
					 * JOptionPane.showMessageDialog(null,
					 * "PROCEDURE DIVISION is Correct" , "text", 1); }
					 * else { JOptionPane.showMessageDialog(null,
					 * "PROCEDURE DIVISION is incorrect" , "text", 0);
					 * return; } }
					 */
	
				}
			}
			 catch (Exception ex) {
			}
			//return true;
		//}
	
		/*
		 * for (String word : words) { if(word.endsWith(".") &&
		 * !isCommentLine(strNewLine) ){ //
		 * jtaErrorDisplay.append("All statments are terminated properly"
		 * ); // JOptionPane.showMessageDialog(null,
		 * "All statments are terminated properly"); flag=1; } else {
		 * jtaErrorDisplay.append(""); } }
		 */
		//jtaErrorDisplay.append("No. of Variables :" + Variables.size());
		for (int j = 0; j < Variables.size(); j++) {
			jtaErrorDisplay.append("\n" + Variables.get(j) + "    "
					+ VariableSize.get(j) + "   "
					+ VariableValues.get(j));
		}
		return true;
	}

	public boolean CheckIdentificationDivision(String InstructionStr) {
		String s = InstructionStr;

		String[] IdDivStr = InstructionStr.split(" ");
		int len;
		len = InstructionStr.length();

		// IdDivStr = InstructionStr.Split(' ');

		for (int j = 0; j < IdDivStr.length; j++) {
			// Console.WriteLine(IdDivStr[j]);
		}

		int ct = 1;
		for (int j = 0; j < IdDivStr.length; j++) {
			if (!IdDivStr[j].equals("")) {
				if (ct == 1) {
					Pattern pattern;
					Matcher regex;
					String strPat = "^[0-9]{6}$";

					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(IdDivStr[j]);
					// Compare a string against the regular expression
					if (regex.matches()) {
						//jtaErrorDisplay.append("\nExecute 1");
						// Console.WriteLine("Execute 1");
					} else {
						jtaErrorDisplay.append("Invalidation in Numbering");
						// Console.WriteLine("Invalidation in Numbering");
						return false;
					}
					ct++;

				} else if (ct == 2) {
					if (IdDivStr[j].equals("IDENTIFICATION")) {
						//jtaErrorDisplay.append("\nExecute 2");
						// Console.WriteLine("Execute 2");
					} else {
						jtaErrorDisplay.append("\nSyntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}

					ct++;
				} else if (ct == 3) {
					if (IdDivStr[j].equals("DIVISION.\n")
							|| IdDivStr[j].equals("DIVISION.")
							|| IdDivStr[j].equals("DIVISION.\t\n")) {
						//jtaErrorDisplay.append("Execute ");
						// Console.WriteLine("Execute 3");
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}

					ct++;
				} else if (ct == 4) {
					if (IdDivStr[j].equals("\n") || IdDivStr[j].equals("")
							|| IdDivStr[j].equals("\t")) {
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}
				}
			}
		}
		return true;
	}

	public boolean CheckProgramID(String InstructionStr) {
		String s = InstructionStr;

		String[] IdDivStr = InstructionStr.split(" ");
		int len;
		len = InstructionStr.length();

		// IdDivStr = InstructionStr.Split(' ');

		for (int j = 0; j < IdDivStr.length; j++) {
			// Console.WriteLine(IdDivStr[j]);
		}

		int ct = 1;
		for (int j = 0; j < IdDivStr.length; j++) {
			if (!IdDivStr[j].equals("")) {
				if (ct == 1) {
					Pattern pattern;
					Matcher regex;
					String strPat = "^[0-9]{6}$";

					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(IdDivStr[j]);
					// Compare a string against the regular expression
					if (regex.matches()) {
						//jtaErrorDisplay.append("Execute 1:" + IdDivStr[j]);
						// Console.WriteLine("Execute 1");
					} else {
						jtaErrorDisplay.append("Invalidation in Numbering");
						// Console.WriteLine("Invalidation in Numbering");
						return false;
					}
					ct++;

				} else if (ct == 2) {
					if (IdDivStr[j].equals("PROGRAM-ID.\n")
							|| IdDivStr[j].equals("PROGRAM-ID.")) {
						//jtaErrorDisplay.append("Execute 2:" + IdDivStr[j]);
						// Console.WriteLine("Execute 2");
					} else {
						jtaErrorDisplay.append("Syntax Error:" + IdDivStr[j]);
						// Console.WriteLine("Syntax Error");
						return false;
					}

					ct++;
				} else if (ct == 3) {
					if (IdDivStr[j].equals("\n") || IdDivStr[j].equals("")) {
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}
				}
			}
		}
		return true;
	}

	public boolean CheckEnvironmentDivision(String InstructionStr) {
		String s = InstructionStr;

		String[] IdDivStr = InstructionStr.split(" ");
		int len;
		len = InstructionStr.length();

		// IdDivStr = InstructionStr.Split(' ');

		for (int j = 0; j < IdDivStr.length; j++) {
			// Console.WriteLine(IdDivStr[j]);
		}

		int ct = 1;
		for (int j = 0; j < IdDivStr.length; j++) {
			if (!IdDivStr[j].equals("")) {
				if (ct == 1) {
					Pattern pattern;
					Matcher regex;
					String strPat = "^[0-9]{6}$";

					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(IdDivStr[j]);
					// Compare a string against the regular expression
					if (regex.matches()) {
						//jtaErrorDisplay.append("\nExecute 1");
						// Console.WriteLine("Execute 1");
					} else {
						jtaErrorDisplay.append("Invalidation in Numbering");
						// Console.WriteLine("Invalidation in Numbering");
						return false;
					}
					ct++;

				} else if (ct == 2) {
					if (IdDivStr[j].equals("ENVIRONMENT")) {
						//jtaErrorDisplay.append("\nExecute 2");
						// Console.WriteLine("Execute 2");
					} else {
						jtaErrorDisplay.append("\nSyntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}

					ct++;
				} else if (ct == 3) {
					if (IdDivStr[j].equals("DIVISION.\n")
							|| IdDivStr[j].equals("DIVISION.")) {
						//jtaErrorDisplay.append("Execute ");
						// Console.WriteLine("Execute 3");
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}

					ct++;
				} else if (ct == 4) {
					if (IdDivStr[j].equals("\n") || IdDivStr[j].equals("")) {
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}
				}

			}
		}
		return true;
	}

	public boolean CheckProcedureDivision(String InstructionStr) {
		String s = InstructionStr;

		String[] IdDivStr = InstructionStr.split(" ");
		int len;
		len = InstructionStr.length();

		// IdDivStr = InstructionStr.Split(' ');

		for (int j = 0; j < IdDivStr.length; j++) {
			// Console.WriteLine(IdDivStr[j]);
		}

		int ct = 1;
		for (int j = 0; j < IdDivStr.length; j++) {
			if (IdDivStr[j] != "") {
				if (ct == 1) {
					Pattern pattern;
					Matcher regex;
					String strPat = "^[0-9]{6}$";

					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(IdDivStr[j]);
					// Compare a string against the regular expression
					if (regex.matches()) {
						//jtaErrorDisplay.append("\nExecute 1");
						// Console.WriteLine("Execute 1");
					} else {
						//jtaErrorDisplay.append("Invalidation in Numbering");
						// Console.WriteLine("Invalidation in Numbering");
						return false;
					}
					ct++;

				} else if (ct == 2) {
					if (IdDivStr[j].equals("PROCEDURE")) {
						//jtaErrorDisplay.append("\nExecute 2");
						// Console.WriteLine("Execute 2");
					} else {
						jtaErrorDisplay.append("\nSyntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}

					ct++;
				} else if (ct == 3) {
					if (IdDivStr[j].equals("DIVISION.\n")
							|| IdDivStr[j].equals("DIVISION.")) {
						//jtaErrorDisplay.append("Execute ");
						// Console.WriteLine("Execute 3");
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}

					ct++;
				} else if (ct == 4) {
					if (IdDivStr[j].equals("\n") || IdDivStr[j].equals("")) {
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}
				}

			}
		}
		return true;
	}

	public boolean CheckDataDivision(String InstructionStr) {
		String s = InstructionStr;

		String[] IdDivStr = InstructionStr.split(" ");
		int len;
		len = InstructionStr.length();

		// IdDivStr = InstructionStr.Split(' ');

		for (int j = 0; j < IdDivStr.length; j++) {
			// Console.WriteLine(IdDivStr[j]);
		}

		int ct = 1;
		for (int j = 0; j < IdDivStr.length; j++) {
			if (IdDivStr[j] != "") {
				if (ct == 1) {
					Pattern pattern;
					Matcher regex;
					String strPat = "^[0-9]{6}$";

					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(IdDivStr[j]);
					// Compare a string against the regular expression
					if (regex.matches()) {
						//jtaErrorDisplay.append("\nExecute 1");

						// Console.WriteLine("Execute 1");
					} else {
						jtaErrorDisplay.append("Invalidation in Numbering");
						// Console.WriteLine("Invalidation in Numbering");
						return false;
					}
					ct++;

				} else if (ct == 2) {
					if (IdDivStr[j].equals("DATA")) {
						//jtaErrorDisplay.append("\nExecute 2");
						// Console.WriteLine("Execute 2");
					} else {
						jtaErrorDisplay.append("\nSyntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}

					ct++;
				} else if (ct == 3) {
					if (IdDivStr[j].equals("DIVISION.\n")
							|| IdDivStr[j].equals("DIVISION.")) {
						//jtaErrorDisplay.append("Execute ");
						// Console.WriteLine("Execute 3");
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}
					ct++;
				} else if (ct == 4) {
					if (IdDivStr[j].equals("\n") || IdDivStr[j].equals("")) {
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}
				}

			}
		}
		return true;
	}

	public boolean CheckWorkStorageSection(String InstructionStr) {
		String s = InstructionStr;

		String[] IdDivStr = InstructionStr.split(" ");
		int len;
		len = InstructionStr.length();

		// IdDivStr = InstructionStr.Split(' ');

		for (int j = 0; j < IdDivStr.length; j++) {
			// Console.WriteLine(IdDivStr[j]);
		}

		int ct = 1;
		for (int j = 0; j < IdDivStr.length; j++) {
			if (IdDivStr[j] != "") {
				if (ct == 1) {
					Pattern pattern;
					Matcher regex;
					String strPat = "^[0-9]{6}$";

					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(IdDivStr[j]);
					// Compare a string against the regular expression
					if (regex.matches()) {
						//jtaErrorDisplay.append("\nExecute 1");

						// Console.WriteLine("Execute 1");
					} else {
						jtaErrorDisplay.append("Invalidation in Numbering");
						// Console.WriteLine("Invalidation in Numbering");
						return false;
					}
					ct++;

				} else if (ct == 2) {
					if (IdDivStr[j].equals("WORKING-STORAGE")) {
						//jtaErrorDisplay.append("\nExecute 2");
						// Console.WriteLine("Execute 2");
					} else {
						jtaErrorDisplay.append("\nSyntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}

					ct++;
				} else if (ct == 3) {
					if (IdDivStr[j].equals("SECTION.\n")
							|| IdDivStr[j].equals("SECTION.")) {
						//jtaErrorDisplay.append("Execute ");
						// Console.WriteLine("Execute 3");
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}

					ct++;
				} else if (ct == 4) {
					if (IdDivStr[j].equals("\n") || IdDivStr[j].equals("")) {
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}
				}

			}
		}
		return true;
	}

	public boolean CheckVariableDelcaration(String DeclarationStr) {
		String s = DeclarationStr;
		// DeclarationStr = " " + s + " ";
		String[] DecStr = new String[30];
		int len;
		len = DeclarationStr.length();
		Pattern pattern;
		Matcher regex;
		String strPat;

		int Variable, DataType, VarSize;

		Variable = DataType = VarSize = 0;
		DecStr = DeclarationStr.split(" ");

		for (int j = 0; j < DecStr.length; j++) {
			// Console.WriteLine(DecStr[j]);
		}
		int ct = 1;
		for (int j = 0; j < DecStr.length; j++) {
			if (!DecStr[j].equals("")) {
				if (ct == 1) {
					// string pattern = @"^[0-9]{6}$";
					strPat = "^[0-9]{6}$";

					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(DecStr[j]);

					if (regex.matches()) {

						//jtaErrorDisplay.append("\nExecute 1");
						// Console.WriteLine("Execute 1");
					} else {
						//jtaErrorDisplay.append("Invalidation in Numbering");
						// Console.WriteLine("Invalidation in Numbering");
						return false;
					}

					ct++;
				} else if (ct == 2) {
					// string pattern = @"^[0-9]{2}$";

					strPat = "^[0-9]{2}$";
					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(DecStr[j]);
					// Compare a string against the regular expression
					if (regex.matches()) {
						//jtaErrorDisplay.append("\nExecute 2");
					} else {
						//jtaErrorDisplay.append("Invalid in Numbering");
						// Console.WriteLine("Invalidation in Numbering");
						return false;
					}
					ct++;
				} else if (ct == 3) {
					strPat = "^[A-Z]+$";
					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(DecStr[j]);
					// Compare a string against the regular expression
					if (regex.matches()) {
						Variable = j;
						//jtaErrorDisplay.append("\nExecute 3");
					} else {
						jtaErrorDisplay.append("Invalid variable name");
						// Console.WriteLine("Invalidation in Numbering");
						return false;
					}

					ct++;
				} else if (ct == 4) {
					if (DecStr[j].equals("PIC")) {

						//jtaErrorDisplay.append("\nExecute 4");
						// Console.WriteLine("Execute 4");
					} else {
						jtaErrorDisplay.append("\nError 4");
						// Console.WriteLine("Error 4");
						return false;
					}
					ct++;
				} else if (ct == 5) {
					if (DecStr[j].substring(0, 1).equals("X")) {
						// strPat="\bX\b([0-9]{2})$";
						strPat = "[X][(][0-9]{2}[)][.]";

						pattern = Pattern.compile(strPat);
						regex = pattern.matcher(DecStr[j]);

						String strPat2 = "[X][(][0-9]{2}[)][.][\n]";

						Pattern pattern2;
						Matcher matcher2;

						pattern2 = Pattern.compile(strPat2);
						matcher2 = pattern2.matcher(DecStr[j]);
						// Compare a string against the regular expression
						if (regex.matches() || matcher2.matches()) {
							DataType = j;
							//jtaErrorDisplay.append("\nExecute 5");
						} else {
							jtaErrorDisplay.append("Invalid variable size");
							// Console.WriteLine("Invalidation in Numbering");
							return false;
						}
					} else if (DecStr[j].substring(0, 1).equals("9")) {
						// ([(0-9)][(][0-9]*[)][.])
						// strPat="\b9[(][0-9]{2}[)][.]";
						strPat = "([9][(][0-9]{2}[)][.][\n])";
						pattern = Pattern.compile(strPat);
						regex = pattern.matcher(DecStr[j]);

						String strPat2 = "([9][(][0-9]{2}[)][.])";

						Pattern pattern2;
						Matcher matcher2;

						pattern2 = Pattern.compile(strPat2);
						matcher2 = pattern2.matcher(DecStr[j]);
						// Compare a string against the regular expression
						if (regex.matches() || matcher2.matches()) {
							DataType = j;
							//jtaErrorDisplay.append("\nExecute 5");
						} else {
							jtaErrorDisplay.append("Invalid variable size");
							// Console.WriteLine("Invalidation in Numbering");
							return false;
						}
					} else {
						jtaErrorDisplay.append("Error 4");
						return false;
					}
					ct++;
				} else if (ct == 6) {
					if (DecStr[j].equals("\n") || DecStr[j].equals("")) {
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}
				}
			}
		}
		if (Variables.indexOf(DecStr[Variable]) == -1) {
			Variables.add(DecStr[Variable]);
			DataTypes.add(DecStr[DataType]);
			VariableValues.add("Null");
			VariableSize.add(DecStr[DataType].substring(2, 4));
		} else {
			jtaErrorDisplay.append("The variable name : \"" + DecStr[Variable]
					+ "\" is already exists");
			return false;
		}
		return true;
	}

	public boolean CheckAccept(String InstructionStr) {
		String s = InstructionStr;
		String Variable;
		String[] IdDivStr = InstructionStr.split(" ");
		int len;
		len = InstructionStr.length();

		// IdDivStr = InstructionStr.Split(' ');

		for (int j = 0; j < IdDivStr.length; j++) {
			// Console.WriteLine(IdDivStr[j]);
		}

		int ct = 1;
		for (int j = 0; j < IdDivStr.length; j++) {
			if (!IdDivStr[j].equals("")) {
				if (ct == 1) {
					Pattern pattern;
					Matcher regex;
					String strPat = "^[0-9]{6}$";

					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(IdDivStr[j]);
					// Compare a string against the regular expression
					if (regex.matches()) {
						// jtaErrorDisplay.append("Execute 1:"+IdDivStr[j]);
						// Console.WriteLine("Execute 1");
					} else {
						//jtaErrorDisplay.append("Invalidation in Numbering");
						// Console.WriteLine("Invalidation in Numbering");
						return false;
					}
					ct++;

				} 
				else if (ct == 2) 
				{
					if (IdDivStr[j].equals("ACCEPT")) {
						// jtaErrorDisplay.append("Execute 2:"+IdDivStr[j]);
						// Console.WriteLine("Execute 2");
					} else {
						jtaErrorDisplay.append("Syntax Error:" + IdDivStr[j]);
						// Console.WriteLine("Syntax Error");
						return false;
					}
					ct++;
				} 
				else if (ct == 3) 
				{
					Variable = IdDivStr[j].replace(".", "");
					Variable = Variable.replace("\n", "");
					int index = Variables.indexOf(Variable);
					if (index != -1) {
						JOptionPane.showMessageDialog(null, "Data Type :"+ DataTypes.get(index), "text", 0);
						String str = JOptionPane.showInputDialog(null,"Variable: " + Variable + ";\nType: "+ DataTypes.get(index).trim()+ "\nValue="+ VariableValues.get(index),"Ender the value of Variable:", 1);
						VariableValues.set(index, str);
						JOptionPane.showMessageDialog(null, "Value :"+ VariableValues.get(index), "text", 0);
					} 
					else 
					{
						jtaErrorDisplay.append("Error : Undefined variable \""
								+ Variable + "\"");
						return false;
					}
					ct++;
				} else if (ct == 4) {
					if (IdDivStr[j].equals("\n") || IdDivStr[j].equals("")
							|| IdDivStr[j].equals(".\n")
							|| IdDivStr[j].equals(".")) {
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public boolean Checkstop(String InstructionStr) {
		@SuppressWarnings("unused")
		String s = InstructionStr;
		//String Variable;
		String[] IdDivStr = InstructionStr.split(" ");
		@SuppressWarnings("unused")
		int len;
		len = InstructionStr.length();

		// IdDivStr = InstructionStr.Split(' ');

		for (int j = 0; j < IdDivStr.length; j++) {
			// Console.WriteLine(IdDivStr[j]);
		}

		int ct = 1;
		for (int j = 0; j < IdDivStr.length; j++) {
			if (!IdDivStr[j].equals("")) {
				if (ct == 1) {
					Pattern pattern;
					Matcher regex;
					String strPat = "^[0-9]{6}$";

					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(IdDivStr[j]);
					// Compare a string against the regular expression
					if (regex.matches()) {
						// jtaErrorDisplay.append("Execute 1:"+IdDivStr[j]);
						// Console.WriteLine("Execute 1");
					} else {
						//jtaErrorDisplay.append("Invalidation in Numbering");
						// Console.WriteLine("Invalidation in Numbering");
						return false;
					}
					ct++;

				} 
				else if (ct == 2) 
				{
					if (IdDivStr[j].equals("STOP")) {
						// jtaErrorDisplay.append("Execute 2:"+IdDivStr[j]);
						// Console.WriteLine("Execute 2");
					} else {
						jtaErrorDisplay.append("Syntax Error:" + IdDivStr[j]);
						// Console.WriteLine("Syntax Error");
						return false;
					}
					ct++;
				} 

				else if (ct == 3) 
				{
					if (IdDivStr[j].equals("RUN")) {
						// jtaErrorDisplay.append("Execute 2:"+IdDivStr[j]);
						// Console.WriteLine("Execute 2");
					} else {
						jtaErrorDisplay.append("Syntax Error:" + IdDivStr[j]);
						// Console.WriteLine("Syntax Error");
						return false;
					}
					ct++;
				} 
				 else if (ct == 4) {
					if (IdDivStr[j].equals("\n") || IdDivStr[j].equals("")
							|| IdDivStr[j].equals(".\n")
							|| IdDivStr[j].equals(".")) {
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}
				}
			}
		}
		return true;
	}

	public boolean CheckAdd(String InstructionStr) {
		String s = InstructionStr;
		String Variable;
		String[] IdDivStr = InstructionStr.split(" ");
		int len;
		len = InstructionStr.length();

		int index = 0;
		int value1 = 0, value2 = 0, value3 = 0;
		for (int j = 0; j < IdDivStr.length; j++) {
			// Console.WriteLine(IdDivStr[j]);
		}
		int ct = 1;
		for (int j = 0; j < IdDivStr.length; j++) {
			if (!IdDivStr[j].equals("")) {
				if (ct == 1) {
					Pattern pattern;
					Matcher regex;
					String strPat = "^[0-9]{6}$";

					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(IdDivStr[j]);
					// Compare a string against the regular expression
					if (regex.matches()) {
						 //jtaErrorDisplay.append("\nExecute ADD1:"+IdDivStr[j]);
						// Console.WriteLine("Execute 1");
					} else {
						jtaErrorDisplay.append("Invalidation in Numbering");
						// Console.WriteLine("Invalidation in Numbering");
						return false;
					}
					ct++;

				} 
				else if (ct == 2) {
					if (IdDivStr[j].equals("ADD")) {
						// jtaErrorDisplay.append("\nExecute ADD2:"+IdDivStr[j]);
						// Console.WriteLine("Execute 2");
					} else {
						jtaErrorDisplay.append("ADDsyntaxerror:" + IdDivStr[j]);
						// Console.WriteLine("Syntax Error");
						return false;
					}
					ct++;
				} 
				else if (ct == 3) 
				{
					Variable = IdDivStr[j].replace(".", "");
					Variable = Variable.replace("\n", "");
					index = Variables.indexOf(Variable);
					if (index != -1) {					

						String str = VariableValues.get(index);
						value1 = Integer.parseInt(str);
						//jtaErrorDisplay.append("ADD VARIABLE1" + value1+"\n");
						JOptionPane.showMessageDialog(null, "Value :" + value1,
								"text", 0);
					} else {
						return false;
					}
					ct++;
				}
				else if (ct == 4) {
					if (IdDivStr[j].equals("TO")) {
						//jtaErrorDisplay.append("\nADDExecute 4" + "\n");
					} else {
						jtaErrorDisplay.append("Syntax Error" + "\n");
						return false;
					}
					ct++;
				}
				else if (ct == 5) {
					Variable = IdDivStr[j].replace(".", "");
					Variable = Variable.replace("\n", "");
					index = Variables.indexOf(Variable);
					if (index != -1) {
						// JOptionPane.showMessageDialog(null,
						// "Data Type :"+DataTypes.get(index) , "text", 0);
						// String str = JOptionPane.showInputDialog(null,
						// "Variable: "+Variable+";\nType: "+DataTypes.get(index).trim()+"\nValue="+VariableValues.get(index),
						// "Ender the value of Variable:", 1);

						String str = VariableValues.get(index);
						value2 = Integer.parseInt(str);
						//jtaErrorDisplay.append("ADD VARIABL2 " + value2);
						JOptionPane.showMessageDialog(null, "Value :" + value2,
								"text", 0);
					} else {
						return false;
					}
					ct++;
				}
				else if (ct == 6) {
					if (IdDivStr[j].equals("GIVING")) {
						ct++;
					} else {
						jtaErrorDisplay.append("ADDSyntax Error" + "\n");
					}
				}
				else if (ct == 7) {
					Variable = IdDivStr[j].replace(".", "");
					Variable = Variable.replace("\n", "");
					index = Variables.indexOf(Variable);
					if (index != -1) {
						
						value3 = value1 + value2;
						String str = VariableValues.set(index,String.valueOf(value3));

						jtaErrorDisplay.append("\n"+"SUM" + value3);
						JOptionPane.showMessageDialog(null, "Value :" + value3,
								"text", 0);
					} else {
						jtaErrorDisplay.append("ADD Syntax error");
						return false;
					}
				} 
				else if (ct == 8) {
					if (IdDivStr[j].equals("\n") || IdDivStr[j].equals("")
							|| IdDivStr[j].equals(".\n")
							|| IdDivStr[j].equals(".")) {
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}
				}
			}
		}
		return true;

	}
	public boolean Checksub(String InstructionStr) {
		String s = InstructionStr;
		String Variable;
		String[] IdDivStr = InstructionStr.split(" ");
		int len;
		len = InstructionStr.length();

		int index = 0;
		int value1 = 0, value2 = 0, value3 = 0;
		for (int j = 0; j < IdDivStr.length; j++) {
			// Console.WriteLine(IdDivStr[j]);
		}
		int ct = 1;
		for (int j = 0; j < IdDivStr.length; j++) {
			if (!IdDivStr[j].equals("")) {
				if (ct == 1) {
					Pattern pattern;
					Matcher regex;
					String strPat = "^[0-9]{6}$";

					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(IdDivStr[j]);
					// Compare a string against the regular expression
					if (regex.matches()) {
						 //jtaErrorDisplay.append("\nExecute ADD1:"+IdDivStr[j]);
						// Console.WriteLine("Execute 1");
					} else {
						jtaErrorDisplay.append("Invalidation in Numbering");
						// Console.WriteLine("Invalidation in Numbering");
						return false;
					}
					ct++;

				} 
				else if (ct == 2) {
					if (IdDivStr[j].equals("SUBTRACT")) {
						// jtaErrorDisplay.append("\nExecute 2:"+IdDivStr[j]);
						// Console.WriteLine("Execute 2");
					} else {
						jtaErrorDisplay.append("Syntax Error:" + IdDivStr[j]);
						// Console.WriteLine("Syntax Error");
						return false;
					}
					ct++;
				} 
				else if (ct == 3) 
				{
					Variable = IdDivStr[j].replace(".", "");
					Variable = Variable.replace("\n", "");
					index = Variables.indexOf(Variable);
					if (index != -1) {					

						String str = VariableValues.get(index);
						value1 = Integer.parseInt(str);
						//jtaErrorDisplay.append("Value of value1 " + value1);
						JOptionPane.showMessageDialog(null, "Value :" + value1,
								"text", 0);
					} else {
						return false;
					}
					ct++;
				}
				else if (ct == 4) {
					if (IdDivStr[j].equals("FROM")) {
						//jtaErrorDisplay.append("\nExecute 4" + "\n");
					} else {
						jtaErrorDisplay.append("Syntax Error" + "\n");
						return false;
					}
					ct++;
				}
				else if (ct == 5) {
					Variable = IdDivStr[j].replace(".", "");
					Variable = Variable.replace("\n", "");
					index = Variables.indexOf(Variable);
					if (index != -1) {
						// JOptionPane.showMessageDialog(null,
						// "Data Type :"+DataTypes.get(index) , "text", 0);
						// String str = JOptionPane.showInputDialog(null,
						// "Variable: "+Variable+";\nType: "+DataTypes.get(index).trim()+"\nValue="+VariableValues.get(index),
						// "Ender the value of Variable:", 1);

						String str = VariableValues.get(index);
						value2 = Integer.parseInt(str);
						//jtaErrorDisplay.append("Value of value2 " + value2);
						JOptionPane.showMessageDialog(null, "Value :" + value2,
								"text", 0);
					} else {
						return false;
					}
					ct++;
				}
				else if (ct == 6) {
					if (IdDivStr[j].equals("GIVING")) {
						ct++;
					} else {
						jtaErrorDisplay.append("Syntax Error" + "\n");
					}
				}
				else if (ct == 7) {
					Variable = IdDivStr[j].replace(".", "");
					Variable = Variable.replace("\n", "");
					index = Variables.indexOf(Variable);
					if (index != -1) {
						
						value3 = value1 - value2;
						String str = VariableValues.set(index,String.valueOf(value3));

						//jtaErrorDisplay.append("Value of value3 " + value3);
						JOptionPane.showMessageDialog(null, "Value :" + value3,
								"text", 0);
					} else {
						jtaErrorDisplay.append("SUB Syntax error");
						return false;
					}
				} 
				else if (ct == 8) {
					if (IdDivStr[j].equals("\n") || IdDivStr[j].equals("")
							|| IdDivStr[j].equals(".\n")
							|| IdDivStr[j].equals(".")) {
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}
				}
			}
		}
		return true;

	}
	public boolean CheckDiv(String InstructionStr) {
		String s = InstructionStr;
		String Variable;
		String[] IdDivStr = InstructionStr.split(" ");
		int len;
		len = InstructionStr.length();

		int index = 0;
		int value1 = 0, value2 = 0, value3 = 0;
		for (int j = 0; j < IdDivStr.length; j++) {
			// Console.WriteLine(IdDivStr[j]);
		}
		int ct = 1;
		for (int j = 0; j < IdDivStr.length; j++) {
			if (!IdDivStr[j].equals("")) {
				if (ct == 1) {
					Pattern pattern;
					Matcher regex;
					String strPat = "^[0-9]{6}$";

					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(IdDivStr[j]);
					// Compare a string against the regular expression
					if (regex.matches()) {
						 //jtaErrorDisplay.append("\nExecute DIVISION1:"+IdDivStr[j]);
						// Console.WriteLine("Execute 1");
					} else {
						jtaErrorDisplay.append("Invalidation in Numbering");
						// Console.WriteLine("Invalidation in Numbering");
						return false;
					}
					ct++;

				} 
				else if (ct == 2) 
				{
					if (IdDivStr[j].equals("DIVIDE")) 
					{
						// jtaErrorDisplay.append("\nExecute 2:"+IdDivStr[j]);
						// Console.WriteLine("Execute 2");
					} 
					else 
					{
						jtaErrorDisplay.append("Syntax Error:" + IdDivStr[j]);
						// Console.WriteLine("Syntax Error");
						return false;
					}
					ct++;
				} 
				else if (ct == 3) 
				{
					Variable = IdDivStr[j].replace(".", "");
					Variable = Variable.replace("\n", "");
					index = Variables.indexOf(Variable);
					if (index != -1) {					

						String str = VariableValues.get(index);
						value1 = Integer.parseInt(str);
						//jtaErrorDisplay.append("\n"+"VARIABLE1 " + value1);
						JOptionPane.showMessageDialog(null, "Value :" + value1,
								"text", 0);
					} else {
						return false;
					}
					ct++;
				}
				else if (ct == 4) {
					if (IdDivStr[j].equals("INTO")||IdDivStr[j].equals("BY")) {
					//	jtaErrorDisplay.append("\nExecute 4" + "\n");
					} else {
						jtaErrorDisplay.append("Syntax Error" + "\n");
						return false;
					}
					ct++;
				}
				else if (ct == 5) {
					Variable = IdDivStr[j].replace(".", "");
					Variable = Variable.replace("\n", "");
					index = Variables.indexOf(Variable);
					if (index != -1) {
						// JOptionPane.showMessageDialog(null,
						// "Data Type :"+DataTypes.get(index) , "text", 0);
						// String str = JOptionPane.showInputDialog(null,
						// "Variable: "+Variable+";\nType: "+DataTypes.get(index).trim()+"\nValue="+VariableValues.get(index),
						// "Ender the value of Variable:", 1);

						String str = VariableValues.get(index);
						value2 = Integer.parseInt(str);
						//jtaErrorDisplay.append("\n "+"variable2" + value2);
						JOptionPane.showMessageDialog(null, "Value :" + value2,
								"text", 0);
					} else {
						return false;
					}
					ct++;
				}
				else if (ct == 6) {
					if (IdDivStr[j].equals("GIVING")) {
						ct++;
					} else {
						jtaErrorDisplay.append("Syntax Error" + "\n");
					}
				}
				else if (ct == 7) {
					Variable = IdDivStr[j].replace(".", "");
					Variable = Variable.replace("\n", "");
					index = Variables.indexOf(Variable);
					if (index != -1) {
						
						value3 = value1 / value2;
						String str = VariableValues.set(index,String.valueOf(value3));

						//jtaErrorDisplay.append("\n"+"divide value" + value3);
						JOptionPane.showMessageDialog(null, "Value :" + value3,
								"text", 0);
					} else {
						jtaErrorDisplay.append("Divide Syntax error");
						return false;
					}
				} 
				else if (ct == 8) {
					if (IdDivStr[j].equals("\n") || IdDivStr[j].equals("")
							|| IdDivStr[j].equals(".\n")
							|| IdDivStr[j].equals(".")) {
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}
				}
			}
		}
		return true;

	}
	public boolean CheckMul(String InstructionStr) {
		String s = InstructionStr;
		String Variable;
		String[] IdDivStr = InstructionStr.split(" ");
		int len;
		len = InstructionStr.length();

		int index = 0;
		int value1 = 0, value2 = 0, value3 = 0;
		for (int j = 0; j < IdDivStr.length; j++) {
			// Console.WriteLine(IdDivStr[j]);
		}
		int ct = 1;
		for (int j = 0; j < IdDivStr.length; j++) {
			if (!IdDivStr[j].equals("")) {
				if (ct == 1) {
					Pattern pattern;
					Matcher regex;
					String strPat = "^[0-9]{6}$";

					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(IdDivStr[j]);
					// Compare a string against the regular expression
					if (regex.matches()) {
						// jtaErrorDisplay.append("\nExecute ADD1:"+IdDivStr[j]);
						// Console.WriteLine("Execute 1");
					} else {
						jtaErrorDisplay.append("Invalidation in Numbering");
						// Console.WriteLine("Invalidation in Numbering");
						return false;
					}
					ct++;

				} 
				else if (ct == 2) {
					if (IdDivStr[j].equals("MULTIPLY")) {
						// jtaErrorDisplay.append("\nExecute 2:"+IdDivStr[j]);
						// Console.WriteLine("Execute 2");
					} else {
						jtaErrorDisplay.append("Syntax Error:" + IdDivStr[j]);
						// Console.WriteLine("Syntax Error");
						return false;
					}
					ct++;
				} 
				else if (ct == 3) 
				{
					Variable = IdDivStr[j].replace(".", "");
					Variable = Variable.replace("\n", "");
					index = Variables.indexOf(Variable);
					if (index != -1) {					

						String str = VariableValues.get(index);
						value1 = Integer.parseInt(str);
						//jtaErrorDisplay.append("Value of value1 " + value1);
						JOptionPane.showMessageDialog(null, "Value :" + value1,
								"text", 0);
					} else {
						return false;
					}
					ct++;
				}
				else if (ct == 4) {
					if (IdDivStr[j].equals("BY")) {
						//jtaErrorDisplay.append("\nExecute 4" + "\n");
					} else {
						jtaErrorDisplay.append("Syntax Error" + "\n");
						return false;
					}
					ct++;
				}
				else if (ct == 5) {
					Variable = IdDivStr[j].replace(".", "");
					Variable = Variable.replace("\n", "");
					index = Variables.indexOf(Variable);
					if (index != -1) {
						// JOptionPane.showMessageDialog(null,
						// "Data Type :"+DataTypes.get(index) , "text", 0);
						// String str = JOptionPane.showInputDialog(null,
						// "Variable: "+Variable+";\nType: "+DataTypes.get(index).trim()+"\nValue="+VariableValues.get(index),
						// "Ender the value of Variable:", 1);

						String str = VariableValues.get(index);
						value2 = Integer.parseInt(str);
						//jtaErrorDisplay.append("Value of value2 " + value2);
						JOptionPane.showMessageDialog(null, "Value :" + value2,
								"text", 0);
					} else {
						return false;
					}
					ct++;
				}
				else if (ct == 6) {
					if (IdDivStr[j].equals("GIVING")) {
						ct++;
					} else {
						jtaErrorDisplay.append("Syntax Error" + "\n");
					}
				}
				else if (ct == 7) {
					Variable = IdDivStr[j].replace(".", "");
					Variable = Variable.replace("\n", "");
					index = Variables.indexOf(Variable);
					if (index != -1) {
						
						value3 = value1 * value2;
						String str = VariableValues.set(index,String.valueOf(value3));

						//jtaErrorDisplay.append("MULTIPLY VALUE " + value3);
						JOptionPane.showMessageDialog(null, "Value :" + value3,
								"text", 0);
					} else {
						jtaErrorDisplay.append("Mul Syntax error");
						return false;
					}
				} 
				else if (ct == 8) {
					if (IdDivStr[j].equals("\n") || IdDivStr[j].equals("")
							|| IdDivStr[j].equals(".\n")
							|| IdDivStr[j].equals(".")) {
					} else {
						jtaErrorDisplay.append("Syntax Error");
						// Console.WriteLine("Syntax Error");
						return false;
					}
				}
			}
		}
		return true;

	}

	public String GetCobolKeyWords(String InstructionStr) {
		String s = InstructionStr;

		String[] IdDivStr = InstructionStr.split(" ");
		int len;
		len = InstructionStr.length();

		// IdDivStr = InstructionStr.Split(' ');

		for (int j = 0; j < IdDivStr.length; j++) {
			// Console.WriteLine(IdDivStr[j]);
		}

		int ct = 1;
		for (int j = 0; j < IdDivStr.length; j++) {
			if (!IdDivStr[j].equals("")) {
				if (ct == 1) {
					Pattern pattern;
					Matcher regex;
					String strPat = "^[0-9]{6}$";

					pattern = Pattern.compile(strPat);
					regex = pattern.matcher(IdDivStr[j]);
					// Compare a string against the regular expression
					if (regex.matches()) {
						// jtaErrorDisplay.append("\nExecute 1");
						// Console.WriteLine("Execute 1");
					} else {
						jtaErrorDisplay.append("Invalidation in Numbering");
						// Console.WriteLine("Invalidation in Numbering");
						return "ERROR";
					}
					ct++;
				} else if (ct == 2) {

					return IdDivStr[j];
					// ct++;
				}
			}
		}
		return "";
	}

	public short getParagraphColumn(String strSrc, String str, boolean period) {
		strSrc = jtaProgramContent.getText();
		String strNewLine = strSrc;
		String strSubString = "";

		boolean sw = false;
		char nChar = 0;
		char genChar = 0;
		int index = 0;
		int iLoop = 0;
		int compareIndex = 0;

		if (fixedFormat && fromFile) {
			index = 6;
		}

		// if the line contains less than 6 chars returns false
		if (index >= strNewLine.length()) {
			return 0;
		}

		// if the line is commnet line returns false
		if (isCommentLine(strNewLine)) {
			return 0;
		} else {

			if (fixedFormat) {
				index += 1;
			}

			index = skipSpace(strNewLine, index);

			int index1 = (strNewLine.length() - index);

			while (iLoop < index1) {

				if (sw == false) {

					index = skipSpace(strNewLine, index);

					if (index >= strNewLine.length()) {
						return 0;
					}

					if (('\"' == strNewLine.charAt(index))
							|| ('\'' == strNewLine.charAt(index))) {

						sw = true;

						if ((strNewLine.charAt(index) == '\"')) {
							nChar = '\"';
						} else {
							nChar = '\'';
						}

					} else if (('*' == strNewLine.charAt(index))
							&& ('>' == strNewLine.charAt(index + 1))) {
						return 0;
					} else {

						if (index + str.length() >= strNewLine.length()) {
							return 0;
						}

						if (fixedFormat) {
							strSubString = strNewLine.substring(index, index
									+ str.length());
							compareIndex = strncmpi(strSubString, str);
						} else {
							strSubString = strNewLine.substring(index);
							compareIndex = isKeywordAvailableInLine(
									strSubString, str);
						}

						if (compareIndex >= 0) {

							if (fixedFormat) {
								index += compareIndex + str.length();
							} else {
								index += compareIndex + str.length();
							}

							genChar = strNewLine.charAt(index);

							if (isSpace(genChar) || isCRNL(genChar)
									|| isEOF(genChar) || ('.' == genChar)) {
								index = skipSpace(strNewLine, index);

								if (false == period
										|| ('.' == strNewLine.charAt(index))) {
									return (short) (index + 1);
								}
							}
							return 0;
						} else {
							return 0;
						}
					}

				} else {
					if (strNewLine.charAt(index) == nChar) {
						if (strNewLine.charAt(index + 1) != nChar) {
							sw = false;
						} else {
							index++;
						}
					}
				}
				index++;
				iLoop++;
			}
		}

		return 0;
	}

	public boolean getProgidColumn(String strLine, String str1) {
		@SuppressWarnings("unused")
		String str = jtaProgramContent.getText();
		short pCol = 0;

		pCol = getParagraphColumn(strLine.toString(), "PROGRAM-ID", true);

		if (pCol > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Checks for Identification line
	 * 
	 * @param strSrc
	 *            StringBuffer which has to be checked
	 * @return true if the string is Identification Division line
	 */
	public boolean isIdentificationLine(String strSrc) {
		strSrc = jtaProgramContent.getText();
		String strNewLine = strSrc.toString();
		boolean sw = false;
		char nChar = 0;
		int index = 0;
		int iLoop = 0;
		String identification = "IDENTIFICATION";
		String id = "ID";
		String division = "DIVISION";

		if (fixedFormat && fromFile) {
			index = 6;
		}

		// if it is not a valid line return false
		if (!isValidLine(strNewLine)) {
			return false;
		}

		// if it is a comment line return false
		if (isCommentLine(strNewLine)) {
			return false;
		} else {

			index = skipSpace(strNewLine, index);

			while (iLoop < (strNewLine.length() - index)) {

				if (false == sw) {
					index = skipSpace(strNewLine, index);

					if (index >= strNewLine.length()) {
						return false;
					}

					if (('\"' == strNewLine.charAt(index))
							|| ('\'' == strNewLine.charAt(index))) {
						sw = true;

						if ((strNewLine.charAt(index) == '\"')) {
							nChar = '\"';
						} else {
							nChar = '\'';
						}

					} else if ('*' == (strNewLine.charAt(index))
							&& ('>' == strNewLine.charAt(index + 1))) {
						return false;

					} else if ((index + identification.length()) < strNewLine
							.length()) {

						// checks for string IDENTIFICATION
						String strSubString = strNewLine.substring(index, index
								+ identification.length());

						if (0 == strncmpi(strSubString, identification)) {
							index += identification.length();
							char genChar = 0;
							genChar = strNewLine.charAt(index);

							if (isSpace(genChar)) {
								index++;

								while (index < strNewLine.length()
										&& isSpace(strNewLine.charAt(index)))
									index++;
								if ((index + division.length()) >= strNewLine
										.length()) {
									return false;
								}
								strSubString = strNewLine.substring(index,
										index + 8);

								// checks for the string DIVISION
								if (0 == strncmpi(strSubString, division)) {
									index += division.length();
									genChar = strNewLine.charAt(index);
									if (isSpace(genChar) || isCRNL(genChar)
											|| isEOF(genChar)
											|| ('.' == genChar)) {
										return true;
									}
								}
							}
							return false;
						}
					}

					if ((index + id.length()) < strNewLine.length()) {

						String strSubString = strNewLine.substring(index,
								index + 2);

						if (0 == strncmpi(strSubString, id)) {
							index += id.length();
							if (index >= strNewLine.length()) {
								return false;
							}
							char genChar = strNewLine.charAt(index);

							if (isSpace(genChar)) {
								index++;
								index = skipSpace(strNewLine, index);
								if ((index + division.length()) >= strNewLine
										.length()) {
									return false;
								}
								strSubString = strNewLine.substring(index,
										index + 8);

								// checks for the string DIVISION
								if (0 == strncmpi(strSubString, division)) {
									index += division.length();
									genChar = strNewLine.charAt(index);
									if (isSpace(genChar) || isCRNL(genChar)
											|| isEOF(genChar)
											|| ('.' == genChar)) {
										return true;
									}
								}
							}

							return false;
						} else {
							return false;
						}
					}
				} else {
					if (strNewLine.charAt(index) == nChar) {
						if (strNewLine.charAt(index + 1) != nChar) {
							sw = false;
						} else {
							index++;
						}
					}
				}
				index++;
				iLoop++;
			}
		}
		return false;
	}

	public boolean getDivisionLine(String strSrc, String str, String strSecond) {

		String strNewLine = strSrc;
		String strSubString = "";
		int compareIndex = 0;
		boolean sw = false;
		char nChar = 0;
		int index = 0;
		int iLoop = 0;

		/*
		 * Assign index a value of 6 if the file is of fixed format and data is
		 * from file
		 */
		if (fixedFormat && fromFile) {
			index = 6;
		}

		/*
		 * return false if the index is greater than or equal to string
		 * strNewLine length
		 */
		if (index >= strNewLine.length()) {
			return false;
		}

		// if string strNewLine is a comment line return false
		if (isCommentLine(strNewLine)) {
			return false;
		} else {
			index = skipSpace(strNewLine, index);

			while (iLoop < (strNewLine.length() - index)) {

				if (sw == false) {
					index = skipSpace(strNewLine, index);

					if (index >= strNewLine.length()) {
						return false;
					}

					// check for " or '
					if (('\"' == strNewLine.charAt(index))
							|| ('\'' == strNewLine.charAt(index))) {
						sw = true;
						if ((strNewLine.charAt(index) == '\"')) {
							nChar = '\"';
						} else {
							nChar = '\'';
						}
					} else if (('*' == strNewLine.charAt(index))
							&& ('>' == strNewLine.charAt(index + 1))) { // check
																		// for
																		// inline-comment
						return false;

					} else {

						if (index + str.length() >= strNewLine.length()) {
							return false;
						}

						if (fixedFormat) {
							strSubString = strNewLine.substring(index, index
									+ str.length());
							compareIndex = strncmpi(strSubString, str);
						} else {
							strSubString = strNewLine.substring(index);
							compareIndex = isKeywordAvailableInLine(
									strSubString, str);
						}

						if (compareIndex >= 0) {

							if (fixedFormat) {
								index += compareIndex + str.length();
							} else {
								index += compareIndex + str.length();
							}

							if ((' ' == strNewLine.charAt(index))
									|| ('\t' == strNewLine.charAt(index))) {
								index++;
								index = skipSpace(strNewLine, index);

								if (index + strSecond.length() >= strNewLine
										.length()) {
									return false;
								}

								strSubString = strNewLine.substring(index,
										index + strSecond.length());

								if (0 == strncmpi(strSubString, strSecond)) {
									index += strSecond.length();

									final char genChar = strNewLine
											.charAt(index);

									if (isSpace(genChar) || isCRNL(genChar)
											|| isEOF(genChar)
											|| (genChar == '.')) {
										if (index + 1 > 0) {
											return true;
										} else {
											return false;
										}
									}
								}
							}

							return false;
						} else {
							return false;
						}
					}

				} else {

					if (strNewLine.charAt(index) == nChar) {

						if (strNewLine.charAt(index + 1) != nChar) {
							sw = false;
						} else {
							index++;
						}
					}
				}
				index++;
				iLoop++;
			}
		}
		return false;
	}

	public int isKeywordAvailableInLine(String strLine, String str) {

		int keywordIndex = 0;
		int commentIndex = 0;

		try {
			strLine = strLine.toUpperCase();
			commentIndex = strLine.indexOf("*>");
			keywordIndex = strLine.indexOf(str);

			if (keywordIndex != -1) {

				if ((commentIndex == -1) || (keywordIndex < commentIndex)) {

					if (keywordIndex == 0) {
						return keywordIndex;
					}
					if (strLine.charAt(keywordIndex - 1) == ' '
							|| (strLine.charAt(keywordIndex - 1) == '\t')) {
						return keywordIndex;
					} else {
						return -1;
					}
				}

			}

		} catch (NullPointerException ex) {
			// CBDTUiPlugin.logError(ex);
		} catch (Exception e) {

			// CBDTUiPlugin.logError(e);
		}

		return -1;

	}

	/**
	 * Clears the element before each operation
	 */

	public boolean isDivisionLine(String strSrc, String str, String strSecond) {

		return (getDivisionLine(strSrc, str, strSecond));
	}

	public boolean isCommentLine(String strLine) {
		strLine = jtaProgramContent.getText();
		String strNewLine = strLine;
		char commentChar = 0;
		char genChar = 0;
		int index = 0;

		if (fixedFormat && fromFile) {
			index = 6;
		}
		if (index >= strNewLine.length()) {
			return false;
		}

		commentChar = strNewLine.charAt(index);

		// check for comment character * or / for fixed format
		if ((('*' == commentChar) || ('/' == commentChar)) && fixedFormat) {
			return true;
		} else {
			if (fixedFormat) {
				index += 1;
			}

			// increase the index for the no of spaces at the start of a line
			index = skipSpace(strNewLine, index);

			if (index + 1 >= strNewLine.length()) {
				return false;
			}

			if ((strLine.charAt(index)) == '*'
					&& (strNewLine.charAt(index + 1)) == '>') {
				return true;
			}

			genChar = strNewLine.charAt(index);

			if (isCRNL(genChar) || (0 == genChar)) {
				return true;
			}
		}
		return false;
	}

	public boolean isCRNL(char charCRNL) {

		if (('\r' == charCRNL) || ('\n' == charCRNL)) {
			return true;
		}

		return false;
	}

	public int skipSpace(String strLine, int index) {

		strLine = jtaProgramContent.getText();
		if (index >= strLine.length() || index < 0) {
			return index;
		}
		while (isSpace(strLine.charAt(index))) {
			index++;
			if (index >= strLine.length()) {
				return index;
			}

		}
		return index;
	}

	public boolean isSpace(char charSpace) {

		if ((' ' == charSpace) || ('\t' == charSpace)) {
			return true;
		}

		return false;
	}

	public int strncmpi(String strSubString, String strKeyWord) {

		if ((strSubString.compareToIgnoreCase(strKeyWord)) != 0) {
			return -1;
		}
		return 0;
	}

	public boolean isEOF(char charEOF) {

		if (charEOF == AE5D_EOF_CODE) {
			return true;
		}

		return false;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;

	}

	public int getLineNumber() {
		return lineNumber;
	}

	private void updateStatus(int linenumber, int columnnumber) {
		status.setText("Line: " + linenumber + " Column: " + columnnumber);

		// jtaLinenumber.setText("line" + linenumber);

	}

	private JPanel getOptionPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 5, 5));

		JLabel lineNumberLabel = new JLabel("Enter Line Number : ");
		lineField = new JTextField(10);

		JLabel colourLabel = new JLabel("Select One Colour : ");

		panel.add(lineNumberLabel);
		panel.add(lineField);
		panel.add(colourLabel);
		panel.add(cbox);

		return panel;
	}

	public void paint(Graphics G) {
		Graphics2D G2D = (Graphics2D) G;
		G2D.drawImage(ImageX, 10, 10, null);
	}

	/**
	 * Validates the given line
	 * 
	 * @param strText
	 *            line of text to be validated
	 * @return true if it is a valid line
	 */
	public boolean isValidLine(String strText) {

		if (strText.length() <= 6) {
			return false;
		}

		strText = strText.substring(6);

		if (strText.length() < 1) {
			return false;
		}

		int index = 0;

		while (index < strText.length()) {

			if ('\n' == strText.charAt(index) || '\r' == strText.charAt(index)) {
				index++;
			} else {
				return true;
			}
		}
		return false;

	}

	/**
	 * Splits the given string and constructs the node tree after parsing
	 * 
	 * @param lpText
	 *            String to be parsed
	 */

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */

	// *======**======**======**======**======**======**======**======**======**======**======**======*
	public static void main(String args[]) throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {

			public void run() {
				new Debugtool1(filename);

			}
		});

		/*
		 * Pattern pattern = Pattern.compile("([(0-9)][(][0-9]*[)][.])");
		 * Matcher matcher = pattern.matcher("X(10).");
		 * 
		 * /* System.out.println("Current REGEX is: "+REGEX);
		 * System.out.println("Current INPUT is: "+INPUT);
		 */
		// System.out.println("lookingAt(): "+matcher.lookingAt());
		// System.out.println("matches(): "+matcher.matches());

		/*
		 * List<String> names = new ArrayList<String>(); names.add("John");
		 * names.add("Welcome"); names.add("World");
		 * 
		 * 
		 * //for(string)
		 * 
		 * for(String str : names) {
		 * 
		 * System.out.println(str); } System.out.print(names.indexOf("Wo"));
		 */
	}

}

