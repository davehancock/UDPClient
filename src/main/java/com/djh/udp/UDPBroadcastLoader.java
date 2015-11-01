package com.djh.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPBroadcastLoader {

    public static final String TARGET_IP_ADDRESS = "192.168.0.255";

    public static final String TARGET_MAC_ADDRESS = "10-BF-48-86-56-40";


    public static void main(String[] args) throws IOException, InterruptedException {

        // Send a series of magic packets at a fixed interval for debugging purposes.
        while (true) {
            sendMagicPacket(TARGET_IP_ADDRESS, TARGET_MAC_ADDRESS);
            Thread.sleep(3000);
        }
    }


    public static void sendMagicPacket(String ipAddress, String macAddress) throws IOException {

        byte[] macBytes = getMacBytes(macAddress);
        byte[] bytes = new byte[6 + 16 * macBytes.length];

        for (int i = 0; i < 6; i++) {
            bytes[i] = (byte) 0xff;
        }

        for (int i = 6; i < bytes.length; i += macBytes.length) {
            System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
        }

        InetAddress address = InetAddress.getByName(ipAddress);

        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, 9);
        DatagramSocket socket = new DatagramSocket();

        socket.send(packet);

        System.out.println("Sending Wake-on-LAN packet");

        socket.close();
    }


    private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {

        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-)");

        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }

        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }

        return bytes;
    }

}
