package com.djh.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author David Hancock
 */
public class UDPBroadcastLoader {

    //public static final String TARGET_IP_ADDRESS = "94.0.19.133";

    public static final String TARGET_IP_ADDRESS = "192.168.0.1";

    public static void main(String[] args) throws IOException, InterruptedException {

        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(TARGET_IP_ADDRESS);

        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        while (true) {

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 7);
            clientSocket.send(sendPacket);

            System.out.println("SENT PACKET:" + sendPacket);
            Thread.sleep(3000);
        }
    }

}
