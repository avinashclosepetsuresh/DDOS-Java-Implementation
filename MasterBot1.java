
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MasterBot1 {

	public class Connections {

		public String IPaddress;
		public String HostName;
		public int portNumber;
		public String date;

		public String getIPaddress() {
			return IPaddress;
		}

		public void setIPaddress(String iPaddress) {
			IPaddress = iPaddress;
		}

		public String getHostName() {
			return HostName;
		}

		public void setHostName(String hostName) {
			HostName = hostName;
		}

		public int getPortNumber() {
			return portNumber;
		}

		public void setPortNumber(int portNumber) {
			this.portNumber = portNumber;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String string) {
			this.date = string;
		}

	}


	public static void main(String[] args) {

			// if(args[0]== "-p")
			// {
			HandleClient.port_no = Integer.valueOf(args[1]);
			HandleClient mrt = new HandleClient();
			Thread t = new Thread(mrt);
			t.start();

			for (;;) {
				// System.out.println("To check list of clients type list");
				Scanner scn = new Scanner(System.in);
				String command = scn.nextLine();
				String Store = command;
				String[] parts = Store.split(" ");
				String Take = parts[0];
				if (command.equals("list")) {

					HandleClient.DisplayConnections();
				} 
				else if (Take.equals("connect")) {
					String userinput = command;
					// scn.nextLine()
					//System.out.println(userinput);
					//System.out.println("disconnect");
					HandleClient.SendtoClient(userinput);
				}

				else if (Take.equals("disconnect")) {
					String userinput = command;
					HandleClient.Sendtodisconnect_Client(userinput);
				}
				// }

			}

		}

	public static class HandleClient implements Runnable {
		public static int port_no;
		public static int client = 0;
		public static Connections[] clientdetails = new Connections[100];
		public static ServerSocket MBS1;
		public static Socket s1;
		public static ArrayList<Socket> arr_socket = new ArrayList<Socket>();
		final static String INET_ADDR = "127.0.0.1";
		MasterBot1 msb = new MasterBot1();

		@Override
		public void run() {

			// TODO Auto-generated method stub
			try {
				MBS1 = new ServerSocket(port_no);
				//System.out.println("Master listening on port no:" + port_no);
				while (true) {

					s1 = MBS1.accept();
					arr_socket.add(s1);
					InetAddress address = s1.getInetAddress();
					String Display_IP = address.getHostAddress();
					String Host_Name = s1.getInetAddress().getHostName();
					//System.out.println(Host_Name);
					int Port_Number = s1.getPort();
					Date date_Registration = new Date();
					SimpleDateFormat myFormat = new SimpleDateFormat("YYYY-MM-dd");
					clientdetails[client] = msb.new Connections();
					clientdetails[client].setDate(myFormat.format(date_Registration));
					clientdetails[client].setHostName(Host_Name);
					clientdetails[client].setIPaddress(Display_IP);
					clientdetails[client].setPortNumber(Port_Number);
					client++;
					System.out.println("Listening to client:" + client);
					// System.out.println(Display_IP + " " + Host_Name + " " +
					// Port_Number + " " + date_Registration);

				}

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public static void DisplayConnections() {
			for (int i = 0; i < client; i++) {
				System.out.println("Client" + (i + 1) + " Details");
				// System.out.println("SlaveHostName:" +
				// clientdetails[i].getHostName());
				// System.out.println("IPAddress:" +
				// clientdetails[i].getIPaddress());
				// System.out.println("SourcePortNumber:" +
				// clientdetails[i].getPortNumber());
				// System.out.println("RegistrationDate:" +
				// clientdetails[i].getDate().toString());
				System.out.println(clientdetails[i].getHostName() + " " + clientdetails[i].getIPaddress() + " "
						+ clientdetails[i].getPortNumber() + " " + clientdetails[i].getDate().toString());
				PrintStream out;
				try {
					out = new PrintStream(new FileOutputStream("MasterBot.txt"));
					out.print(clientdetails[i].getHostName() + clientdetails[i].getIPaddress()
							+ clientdetails[i].getPortNumber() + clientdetails[i].getDate().toString());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		public static void SendtoClient(String userinput) {
			if (userinput.contains("all")) {
				try {
					for (int i = 0; i < client; i++) {
						/*
						 * StringTokenizer st = new StringTokenizer(userinput);
						 * st.nextToken(); String str=st.nextToken();
						 */
						OutputStream send_command = arr_socket.get(i).getOutputStream();
						OutputStreamWriter write = new OutputStreamWriter(send_command);
						BufferedWriter buffer_write = new BufferedWriter(write);
						buffer_write.write(userinput + "\n");
						// System.out.println(userinput);
						// System.out.println(buffer_write+"\n");
						buffer_write.flush();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			else {
				String IP_check = userinput;
				//System.out.println(IP_check);
				String[] parts = IP_check.split(" ");

				String I_P = parts[1];
				//System.out.println(I_P);

				try {
					for (int i = 0; i < client; i++) {
						Socket variable = arr_socket.get(i);
						//System.out.println(variable);
						InetAddress CompareIP = variable.getInetAddress();
						String CompareHN = variable.getInetAddress().getLocalHost().getHostName();
						//System.out.println(CompareIP);
						String Rama = CompareIP.toString();
						String Sita = CompareHN;

						if (Rama.contains(I_P)||Sita.equals(I_P)) {
							// Socket send_command = arr_socket.get(i);

							OutputStream send_command = variable.getOutputStream();
							OutputStreamWriter write = new OutputStreamWriter(send_command);
							BufferedWriter buffer_write = new BufferedWriter(write);
							buffer_write.write(userinput + "\n");
							// System.out.println(userinput);
							// System.out.println(buffer_write+"\n");
							buffer_write.flush();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		public static void Sendtodisconnect_Client(String userinput) {
			if (userinput.contains("all")) {
				try {
					for (int i = 0; i < client; i++) {
						//StringTokenizer st = new StringTokenizer(userinput);
						//st.nextToken();
						//String str = st.nextToken();
						//OutputStream send_command = arr_socket.get(i).getOutputStream();
						//OutputStreamWriter write = new OutputStreamWriter(send_command);
						//BufferedWriter buffer_write = new BufferedWriter(write);
						//buffer_write.write(str + "\n");
					//	System.out.println(buffer_write + "\n");
						//buffer_write.flush();
						
						OutputStream send_command = arr_socket.get(i).getOutputStream();
						OutputStreamWriter write = new OutputStreamWriter(send_command);
						BufferedWriter buffer_write = new BufferedWriter(write);
						buffer_write.write(userinput + "\n");
						// System.out.println(userinput);
						// System.out.println(buffer_write+"\n");
						buffer_write.flush();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				String IP_check = userinput;
				//System.out.println(IP_check);
				String[] parts = IP_check.split(" ");
				String I_P = parts[1];
				//System.out.println("part1:"+I_P);

				try {
					for (int i = 0; i < client; i++) {
						Socket variable = arr_socket.get(i);
						//System.out.println(variable);
						InetAddress CompareIP = variable.getInetAddress();
						String CompareHN = variable.getInetAddress().getLocalHost().getHostName();
						//System.out.println("hostname:"+CompareHN);
						//System.out.println(CompareIP);
						String Rama = CompareIP.toString();
						String Sita = CompareHN;
						
						if (Rama.contains(I_P)||Sita.equals(I_P)) {
							// Socket send_command = arr_socket.get(i);

							OutputStream send_command = variable.getOutputStream();
							OutputStreamWriter write = new OutputStreamWriter(send_command);
							BufferedWriter buffer_write = new BufferedWriter(write);
							buffer_write.write(userinput + "\n");
							// System.out.println(userinput);
							// System.out.println(buffer_write+"\n");
							buffer_write.flush();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			// }

			// }

		}

		//// }

		
	}
}

// }
