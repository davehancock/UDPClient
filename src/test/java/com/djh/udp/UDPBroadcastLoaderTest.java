package com.djh.udp;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author David Hancock
 */
public class UDPBroadcastLoaderTest {

    private static final String VALID_IP_ADDRESS = "192.168.0.255";

    private static final String VALID_MAC_ADDRESS_1 = "10-BF-48-86-56-40";

    private static final String VALID_MAC_ADDRESS_2 = "10:BF:48:86:56:40";

    private static final String INVALID_DELIMITED_MAC_ADDRESS = "10.BF.48.86.56.40";

    private static final String INVALID_LENGTH_MAC_ADDRESS = "10:BF:48:86:56:";

    private static final byte[] EXPECTED_BYTES = new byte[]{16, -65, 72, -122, 86, 64};

    @Test
    public void testSendMagicPacket() throws IOException {

        UDPBroadcastLoader.sendMagicPacket(VALID_IP_ADDRESS, VALID_MAC_ADDRESS_1);
    }

    @Test
    public void testConvertMACToBytes_whenTypical() {

        byte[] bytes = UDPBroadcastLoader.convertMACToBytes(VALID_MAC_ADDRESS_1);
        Assert.assertTrue(Arrays.equals(EXPECTED_BYTES, bytes));

        bytes = UDPBroadcastLoader.convertMACToBytes(VALID_MAC_ADDRESS_2);
        Assert.assertTrue(Arrays.equals(EXPECTED_BYTES, bytes));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertMACToBytes_whenInvalidDelimitedMACAddress() {

        UDPBroadcastLoader.convertMACToBytes(INVALID_DELIMITED_MAC_ADDRESS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertMACToBytes_whenInvalidLengthMACAddress() {

        UDPBroadcastLoader.convertMACToBytes(INVALID_LENGTH_MAC_ADDRESS);
    }

}
