package it.polimi.ingsw.GC_21.CLIENT;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import it.polimi.ingsw.GC_21.VIEW.ServerInterface;
import it.polimi.ingsw.GC_21.fx.ViewType;

public class MainClient {
    public static void main(String[] args) {

    System.out.println("Welcome to Lorenzo il Magnifico! \n Choose your connection to start the game! \n 1:RMI \n 2:Socket");
    Scanner scanner = new Scanner(System.in);
    String choice = scanner.nextLine();
    switch (choice) {
	case  "1": try {
			factoryRmi();
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}

		break;
	case "2" : factorySocket();
	break;
	default: ; factorySocket();
		break;
	}
        
   }
    	
    public static void factorySocket() {
    try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			SocketClient client1 = new SocketClient(ip, 6620, ViewType.CLI);
			 RunCli runCli = new RunCli(client1);
		     runCli.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e2) {
            System.out.println("IO excption");
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	        

     }
    
    public static void factoryRmi() throws RemoteException, NotBoundException {
        try {
        	Registry reg = LocateRegistry.getRegistry(8000);
            ServerInterface srv = (ServerInterface) reg.lookup("server"); 
        	RmiClient client2 = new RmiClient(ViewType.CLI);
            srv.join(client2);
            RunCli runCli = new RunCli(client2);
			runCli.start();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
    
}
