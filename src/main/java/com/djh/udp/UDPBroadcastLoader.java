package com.djh.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author David Hancock
 */
public class UDPBroadcastLoader {

    private static final int PORT_NUMBER = 9;

    private static final Logger LOG = LoggerFactory.getLogger(UDPBroadcastLoader.class);

    public static void sendMagicPacket(String ipAddress, String macAddress) throws IOException {

        byte[] macBytes = convertMACToBytes(macAddress);
        byte[] bytes = new byte[6 + 16 * macBytes.length];

        for (int i = 0; i < 6; i++) {
            bytes[i] = (byte) 0xff;
        }

        for (int i = 6; i < bytes.length; i += macBytes.length) {
            System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
        }

        InetAddress address = InetAddress.getByName(ipAddress);

        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, PORT_NUMBER);

        DatagramSocket socket = new DatagramSocket();
        socket.send(packet);

        LOG.info("Sent Wake-on-LAN packet");

        socket.close();
    }

    static byte[] convertMACToBytes(String macStr) throws IllegalArgumentException {

        byte[] bytes = new byte[6];
        String[] hexChars = macStr.split("(\\:|\\-)");

        if (hexChars.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }

        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hexChars[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }

        return bytes;
    }

}
