import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//adb reverse tcp:3832 tcp:3832

public class ADBServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;

        try {
            serverSocket = new ServerSocket(3832);
            System.out.println("Listening :3832");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        while (true) {
            try {
                socket = serverSocket.accept();
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                System.out.println("ip: " + socket.getInetAddress());
                System.out.println("message: " + dataInputStream.readUTF());
                dataOutputStream.writeUTF("received!");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}