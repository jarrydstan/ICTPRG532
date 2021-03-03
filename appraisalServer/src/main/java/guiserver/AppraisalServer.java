package main.java.guiserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class AppraisalServer {
	static String msgRecv, msgSent;
	static PrintWriter pw;
	private static final int PORT = 3030;
	static ServerSocket jServer;
	static String line;
	static DataInputStream is;
	static PrintStream os;
	static BufferedReader sbr;
	static Socket clientSocket;
	static int[] CLIENT_ID_LIST = {5, 3, 10, 7, 6, 8, 1, 9, 2, 4};
	static String msg;
	
	
	public static void main(String[] args) {
		
		try {
			jServer = new ServerSocket(PORT);
			if (jServer != null) {
				System.out.println("Server Socket is running at port: " + PORT);
				
			}
		}
		catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();	
		}
		try {
			clientSocket = jServer.accept();
			if (clientSocket != null) {
				System.out.printf("Client/Server binding successful on address:%s localport:%d serverport:%d \n", clientSocket.getLocalAddress().toString(), clientSocket.getLocalPort(), clientSocket.getPort());
			}
			sbr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			pw = new PrintWriter(clientSocket.getOutputStream(), true);
			boolean active = true;
			while(active) {
				line = sbr.readLine();
				if(line != null) {
					System.out.println("A message is received from the client: " + line +"\n" + "Start Processing request on ");
					
					////////////////////WRITE SERVER FUNCTIONS HERE//////////////////////////
						
					/////////////////////////////////////////////////////////////////////////
					
					pw.println(line);
					DataTypes.addressList(line);
				}
				else {
					System.out.println("Message has not been received from the client!");
					pw.println(line);
						break;
				}
			}
			} 
		catch (IOException e1) {
			System.out.println(e1);
			e1.printStackTrace();
		}
		try {
		clientSocket.close();
		sbr.close();
		pw.close();
		jServer.close();
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
}	
class DataTypes{	
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static boolean addressList(String addresses) {
		
		String myAddress = addresses;
		LinkedList<String> streets = new LinkedList<String>();
		
		streets.add(0, "1 First Street");
		streets.add(1, "2 First Street");
		streets.add(2, "3 First Street");
		streets.add(3, "4 First Street");
		streets.add(4, "5 First Street");
		
		    for (String street : streets) {
		    	
		    	if(street==myAddress) {
		    		System.out.println("Address found for: "+street);
		    		return true;
		    	}
		    }
		return false;
	}
	
	
	////////////////////////////////////////////
	
	public static Integer myHashMap(String addresses) {
		
		String myAddress1 = addresses;
		HashMap<String, Integer> streets1 = new HashMap<String, Integer>();
		
		streets1.put("1 First Street", 210000);
		streets1.put("2 First Street", 220000);
		streets1.put("3 First Street", 230000);
		streets1.put("4 First Street", 240000);
		streets1.put("5 First Street", 250000); 
		
		
		if(streets1.get(myAddress1) != null) {
			System.out.printf("Market Value for %s is found at $", myAddress1);
			return(streets1.get(myAddress1));
		}else {
			System.out.println("Address not found");
			return null;
		}
		
	}
	/////////////////////////////////////////////
	public static void myListHash() {
		
		LinkedList<HashMap<String, String>> myList = new LinkedList<HashMap<String, String>>();
		HashMap<String, String> myMap1 = new HashMap<String, String>();

		myMap1.put("ClientID", "30000000");
		myMap1.put("ClientName", "Givenname Surname");
		myMap1.put("Email", "someone@domain.com.au");
		myMap1.put("Product", "Homeloan");
		myList.add(myMap1);
		
		
	}
	static int hashFunction(String houseAddress) {
		int a = 0;
		String myAddress = houseAddress;
		for (int i=0;i<myAddress.length();i++) {
			a=(31*a + myAddress.charAt(i)%myAddress.length());
		}
		int hashCode = Math.abs(a);
		
		return hashCode;
	}
	class SortByString implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			String myString1 = o1;
			String myString2 = o2;
			return myString1.compareTo(myString2);
		}
	}
	class SortByInt implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
			int myInt1 = o1;
			int myInt2 = o2;
			return myInt1-myInt2;
		}
	}
	static void bubSort(int clientID[]) {
		int[] client = clientID;
		int tempint = 0;
		int i, j;
		System.out.println(client.length);
		
		for (i = 0; i < client.length-1; i++)
			for(j = 0; j < client.length-1-i; j++)
				if (client[j]>client[j+1]) {
					tempint = client[j];
					client[j] = client[j+1];
					client[j+1] = tempint;
				}
		
	}
	static boolean myBinarySearch(int[] clientId, int toFind) {
		int[] client = clientId;
		int findItem = toFind;
		System.out.printf("Finding: %d \n", findItem);
		int h=0, t=client.length;
		System.out.println(client.length);
		int mid;
		
		while(h<t) {
			mid = ((t+h)/2);
			System.out.println("Searching: "+client[mid]);
			if(client[mid] == findItem)
				{System.out.printf("Found: %d", findItem);
				return true;
				}
			else if(client[mid] > findItem) {
				t = mid-1;
				System.out.println("too high");
			}
			else {
				h = mid+1;
				System.out.println("too low");
				
			}
		}
		return false;
	}
	class TreeNode {
		int treeItem;
		TreeNode leftNode, rightNode;

		TreeNode(int leaves) {
			treeItem = leaves;
			leftNode = rightNode = null;
		}
	}

	class BinaryTree {
		TreeNode root;

		public void traverseTree(TreeNode tNode) {
			if (tNode != null) {
				traverseTree(tNode.leftNode);
				System.out.print("Left node" + tNode.treeItem);
				traverseTree(tNode.rightNode);
				System.out.print("Right node " + tNode.treeItem);
			}


		}

		public void treeCreation() {
			BinaryTree myTree = new BinaryTree();

			myTree.root = new TreeNode(1);
			myTree.root.leftNode = new TreeNode(2);
			myTree.root.rightNode = new TreeNode(3);
			myTree.root.leftNode.leftNode = new TreeNode(4);
			myTree.root.leftNode.rightNode = new TreeNode(5);
			myTree.traverseTree(myTree.root);
		}
	}
}

