package homeloan.gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class GUI {

	private JFrame frmLoanApplication;
	static private JTextField txtName;
	static private JTextField txtAddress;
	static private JTextField txtPrice;
	static private JTextField txtValue;
	static private JTextPane textPane;
	static private JTextPane textPane_1;
	static private JTextPane textPane_2;
	
	static String clientName, propertyAddress;
	static int offeredPrice, marketValue;
	
	static String toServer, fromServer;
	static String toFile, fromFile;
	
	static BufferedReader reader;
	static FileWriter fw;
	static PrintWriter pw;
	static boolean dataEntered = false;
	static Socket jSocket = null;
	static int port = 3030;
	
	
	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmLoanApplication.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLoanApplication = new JFrame();
		frmLoanApplication.setTitle("Loan Application");
		frmLoanApplication.setBounds(100, 100, 800, 600);
		frmLoanApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLoanApplication.getContentPane().setLayout(null);
		
		/*class DataEntry{
			boolean dataPicker() {
				While (true){
					if(textField.getText()==null) {
						(textpane)
					}
				}
			}
		}*/





		class DnD extends JFrame implements DropTargetListener {
		  /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			JTextArea mytext = new JTextArea();
			public DnD() {
				super("GUI");
				setSize(650, 480);
				getContentPane().add(new JLabel("Please drag a file and drop it here:"), BorderLayout.NORTH); // or JtextAarea


				getContentPane().add(mytext, BorderLayout.CENTER);
				DropTarget dt = new DropTarget(mytext, this);
				// set the textfield as the drop target
				setVisible(true);
				
			}

			public void dragEnter(DropTargetDragEvent dtde) {
				// Drag Enter
			}

			public void dragExit(DropTargetEvent dte) {
				//Drag Exit
			}

			public void dragOver(DropTargetDragEvent dtde) {
				//Drag Over
			}

			public void dropActionChanged(DropTargetDragEvent dtde) {
				// When Drop Action is changed;
			}


			public void drop(DropTargetDropEvent dtde) {
				try {
					Transferable tr = dtde.getTransferable();
					DataFlavor[] flavors = tr.getTransferDataFlavors();
					// put data flavors in a array, consider the following data flavors:
					System.out.println(flavors.length);
					// testing to see how many flavors are in the array
					for (int i = 0; i < flavors.length; i++) {
						System.out.println("flavor type: "+i+"\n"+flavors[i].toString());

						if (flavors[i].isFlavorJavaFileListType()) {
							dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
							@SuppressWarnings("rawtypes")
							List list = (List) tr.getTransferData(flavors[i]);
							for (int j = 0; j < list.size(); j++) {
								mytext.setText("You dropped a file:"+"\n\n");
								mytext.append(list.get(j) + "\n");

							}
							dtde.dropComplete(true);
							//return;

						} else if (flavors[i].isFlavorTextType()) {
							
												
							dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
							mytext.setText("You dropped a text:"+"\n\n");
							String mystring = tr.getTransferData(flavors[i]).toString();
							mytext.append(mystring+"\n\n");
							dtde.dropComplete(true);
							//return;
						}

						else if (flavors[i].isFlavorSerializedObjectType()) {
							dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
							Object obj = tr.getTransferData(flavors[i]);
							mytext.setText("You dropped an Object"+"\n\n");
							mytext.append("Object: " + obj);
							dtde.dropComplete(true);
							//return;

						} else if (flavors[i].isRepresentationClassInputStream()) {
							dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
							mytext.read(new InputStreamReader((InputStream) tr.getTransferData(flavors[i])),
									"You dropped something from the clipboard"+"\n\n");
							dtde.dropComplete(true);
							//return;
						}
						
					}
					return;
					//dtde.rejectDrop();
				} catch (Exception ex) {
					ex.printStackTrace();

					dtde.rejectDrop();
				}
			}
		  
		    
		  
		}		
		//////////////////////////BUTTONS////////////////////////////////
		JButton btnApprove = new JButton("Approve");
		btnApprove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				///////////////////////////Write event code///////////////////////////////////////
				
				textPane_2.setText("Approval has been sent for client: " + clientName);
			}
		});
		btnApprove.setBounds(73, 410, 117, 25);
		frmLoanApplication.getContentPane().add(btnApprove);
		
		JButton btnDecline = new JButton("Decline");
		btnDecline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane_2.setText("Denial has been sent for client: " + clientName);

			}
		});
		btnDecline.setBounds(256, 410, 117, 25);
		frmLoanApplication.getContentPane().add(btnDecline);

		
		JButton btnCreditCheck = new JButton("Credit Check");
		btnCreditCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane.setText("Getting Credit Check");
				toFile = "Start writing to j2p.txt"+"\n";
				System.out.println(toFile);
				
				File wfile = new File("j2p.txt");
				try {
					fw = new FileWriter(wfile);
					fw.write(toFile);
					fw.close();
				} catch (IOException e_2) {
					System.out.println(e_2);
					e_2.printStackTrace();
				}
				
				File rfile = new File("j2p.txt");
				
				rfile.setWritable(true);
				try {
					Scanner fsc = new Scanner(rfile);
					while(fsc.hasNext())
					{
						fromFile = fsc.next();
						System.out.println("Read strings from file p2j.txt: " + "\n" + fromFile);
						
					}
					fsc.close();
					textPane.setText("Credit Check received");
				} catch (IOException e_3) {
					e_3.printStackTrace();
					System.out.println(e_3);
					
				}
			}
		});
		btnCreditCheck.setBounds(73, 115, 124, 25);
		frmLoanApplication.getContentPane().add(btnCreditCheck);
		
		JButton btnAppraisal = new JButton("Appraisal");
		btnAppraisal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					clientName = txtName.getText();
					propertyAddress = txtAddress.getText();
					offeredPrice = Integer.parseInt(txtPrice.getText());
					marketValue = Integer.parseInt(txtValue.getText());
				}
				catch(Exception f){
					System.out.println("[ERROR] All fields must be filled in");
					return;
				}
				System.out.println("Data entered");
				try {
					jSocket = new Socket("127.0.0.1", port);
					
					pw = new PrintWriter(jSocket.getOutputStream(), true);
					System.out.printf("[CLIENT][APPRAISAL REQUEST] %s \n", propertyAddress);
					textPane_1.setText("Sending message to server");
					
					pw.print("[SERVER][APPRAISAL REQUEST] ");
					pw.println(propertyAddress);
					
					reader = new BufferedReader(new InputStreamReader(jSocket.getInputStream()));
					fromServer = reader.readLine();
					
					System.out.println("Received a message from the server");
					System.out.println(fromServer);
					textPane_1.setText("Message received");
					
				} catch (IOException e) {
					System.out.println(e);
					e.printStackTrace();
					pw.close();
					try {
						jSocket.close();
						reader.close();
					} catch (IOException e_1) {
						System.out.println(e);
						e.printStackTrace();
					}
					
					
				}
			}
		});
		btnAppraisal.setBounds(73, 246, 124, 25);
		frmLoanApplication.getContentPane().add(btnAppraisal);
		
		JButton btnDragndrop = new JButton("DragnDrop");
		btnDragndrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new DnD();
			}
		});
		btnDragndrop.setBounds(219, 246, 117, 25);
		frmLoanApplication.getContentPane().add(btnDragndrop);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane_2.setText("Closing application");
				frmLoanApplication.dispose();
			}
		});
		btnCancel.setBounds(591, 410, 117, 25);
		frmLoanApplication.getContentPane().add(btnCancel);
		
		///////////////////////////////////////HELP/////////////////////////////
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop d = Desktop.getDesktop();
					URI URL = new URI("https://drive.google.com/drive/folders/1-1MNdPlOEce4QoSKHh0amojU1XaOuGfV?usp=sharing");
					d.browse(URL);
				}
				catch(Exception e0) {
					e0.printStackTrace();
				}
			}
		});
		btnHelp.setBounds(430, 410, 117, 25);
		frmLoanApplication.getContentPane().add(btnHelp);
		///////////////////////////////////////////////////////////////////////
		
		
		//////////////////////LABELS//////////////////////
		JLabel lblApplicantName = new JLabel("Applicant Name:");
		lblApplicantName.setBounds(73, 35, 124, 15);
		frmLoanApplication.getContentPane().add(lblApplicantName);
		
		JLabel lblPropertyAddress = new JLabel("Property Address:");
		lblPropertyAddress.setBounds(369, 35, 148, 15);
		frmLoanApplication.getContentPane().add(lblPropertyAddress);
		
		JLabel lblLoanAmount = new JLabel("Offered Price:");
		lblLoanAmount.setBounds(73, 62, 108, 15);
		frmLoanApplication.getContentPane().add(lblLoanAmount);
		
		JLabel lblMarketValue = new JLabel("Market Value:");
		lblMarketValue.setBounds(369, 62, 135, 15);
		frmLoanApplication.getContentPane().add(lblMarketValue);
		
		/////////////////////////TEXT_FIELDS////////////////////////////////
		txtName = new JTextField();
		txtName.setToolTipText("Full name");
		txtName.getText();
		txtName.setBounds(204, 33, 114, 19);
		frmLoanApplication.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		txtAddress = new JTextField();
		txtAddress.setToolTipText("Address of property");
		txtAddress.setBounds(512, 33, 114, 19);
		frmLoanApplication.getContentPane().add(txtAddress);
		txtAddress.setColumns(10);
		
		txtPrice = new JTextField();
		txtPrice.setToolTipText("Requested amount from applicant");
		txtPrice.setBounds(204, 60, 114, 19);
		frmLoanApplication.getContentPane().add(txtPrice);
		txtPrice.setColumns(10);

		txtValue = new JTextField();
		txtValue.setToolTipText("Market value of property");
		txtValue.setBounds(512, 60, 114, 19);
		frmLoanApplication.getContentPane().add(txtValue);
		txtValue.setColumns(10);

		textPane = new JTextPane();
		textPane.setBounds(73, 152, 676, 75);
		frmLoanApplication.getContentPane().add(textPane);

		textPane_1 = new JTextPane();
		textPane_1.setBounds(73, 283, 676, 75);
		frmLoanApplication.getContentPane().add(textPane_1);

		textPane_2 = new JTextPane();
		textPane_2.setBounds(73, 448, 676, 75);
		frmLoanApplication.getContentPane().add(textPane_2);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(73, 389, 676, 9);
		frmLoanApplication.getContentPane().add(separator);
		

		
		////////////////////////////////////////////////////////////////////
		
	}
}
