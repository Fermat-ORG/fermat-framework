package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util;

import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Created by rrequena on 06/07/16.
 */
public class ThumbnailUtil {

    /**
     * Generate a image Thumbnail from the original image
     * pass by parameters
     *
     * @param originalImage
     * @return byte [] of the thumbnails
     */
    public static byte [] generateThumbnail(byte [] originalImage, String format) throws IOException {
        return getByteArray(generateThumbnail(getBufferedImage(originalImage)), "JPG");
    }


    /**
     * Generate a image Thumbnail from the original image
     * pass by parameters
     *
     * @param originalImage
     * @return BufferedImage of the thumbnails
     */
    public static BufferedImage generateThumbnail(BufferedImage originalImage) throws IOException {
        return Thumbnails.of(originalImage).size(80, 80).asBufferedImage();
    }

    /**
     * Get the bytes array that represent the image
     *
     * @param originalImage
     * @param format
     * @return byte []
     * @throws IOException
     */
    public static  byte [] getByteArray(BufferedImage originalImage, String format) throws IOException {

        ByteArrayOutputStream temporal = new ByteArrayOutputStream();
        ImageIO.write(originalImage, format, temporal);
        temporal.close();
        byte [] bytes = temporal.toByteArray();
        temporal.close();

        return bytes;
    }

    /**
     * Create a BufferedImage from byte array
     *
     * @param data
     * @return BufferedImage
     * @throws IOException
     */
    public static BufferedImage getBufferedImage(byte [] data) throws IOException {

        InputStream inputStream = new ByteArrayInputStream(data);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        inputStream.close();

        return bufferedImage;
    }

}
