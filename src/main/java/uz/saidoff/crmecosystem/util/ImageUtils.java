package uz.saidoff.crmecosystem.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

    public static byte[] compresImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] temp = new byte[4 * 1024];

        while (!deflater.finished()) {
            int size = deflater.deflate(temp);
            outputStream.write(temp, 0, size);

        }
        try {
            outputStream.close();
        } catch (Exception ignore) {
            throw new RuntimeException(ignore);
        }
        return outputStream.toByteArray();

    }

    public static byte[] deCompresImages(byte[] data) {

        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
        byte[] temp = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(temp);
                byteArrayOutputStream.write(temp, 0, count);
            }
            byteArrayOutputStream.close();
        } catch (Exception ignore) {
            throw new RuntimeException(ignore);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
