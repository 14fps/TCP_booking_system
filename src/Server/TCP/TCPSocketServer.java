package Server.TCP;

import java.net.*;
import java.io.*;
import Server.Common.*;

public class TCPSocketServer {
    private static String s_serverName = "Server";
    private static String s_TCPPrefix = "group15";
    private static TCPController controller;
    private static int port = 56665;
    private static ResourceManager resourceManager;

    private static String s_serverHost_Customer = "cs-32.cs.mcgill.ca";
    private static int s_serverPort_Customer = 56665;
    private static String s_serverName_Customer = "Customers";


    public static void main(String[] args) throws IOException {

        if(args.length > 0)
        {
            s_serverName = args[0];
        }

        resourceManager = new ResourceManager(s_serverName);
        controller = new TCPController(port);

        while (true){
            Socket socket = controller.acceptNewSocket();
            if(socket == null){
                System.out.println("Server can't get new socket.");
                System.exit(-1);
            }
            Runnable r = new Listener(socket, resourceManager);
            Thread t = new Thread(r);
            t.start();
        }
    }

    public static Socket connect(String ip, int port){
        Socket socket = null;
        try{
            socket = new Socket(ip, port);
            PrintWriter output = new PrintWriter(socket.getOutputStream(),
                    true);
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host");
            System.exit(1);
        } catch  (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
        return socket;
    }

    public static String execute(String command, Socket socket){
        PrintWriter output = null;
        BufferedReader input = null;
        try{
            output = new PrintWriter(socket.getOutputStream(),
                    true);
            input = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
        }
        catch (IOException e){
            System.out.println("IO exception during execute time.");
            System.exit(-1);
        }
        output.println(command);
        output.flush();
        String returnValue = "";
        try{
            returnValue = input.readLine();
        }
        catch(IOException e){
            System.out.println("input fail");
        }
        try{
            socket.close();
        }
        catch (IOException e){
            System.out.println("Socket close failed.");
            System.exit(-1);
        }
        return returnValue;
    }

    public static String customerExecute(String command){
        Socket socket = connect(s_serverHost_Customer, s_serverPort_Customer);
        return execute(command, socket);
    }

}
