package sep3.webshop.persistence.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageResizer {
    public static String resizeBase64Image(String base64Image, int targetSize) {
        try {
            // Step 1: Convert base64 string to byte array
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            // Step 2: Decode byte array into BufferedImage
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            BufferedImage originalImage = javax.imageio.ImageIO.read(bis);
            bis.close();

            // Calculate new dimensions with padding to make the image square
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            int newWidth, newHeight;

            if (width > height) {
                newWidth = targetSize;
                newHeight = (int) Math.round(targetSize * (double) height / width);
            } else {
                newWidth = (int) Math.round(targetSize * (double) width / height);
                newHeight = targetSize;
            }

            // Step 3: Resize the image with padding
            Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            // Step 4: Add padding to make the image square
            BufferedImage squaredImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = squaredImage.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, targetSize, targetSize);

            int x = (targetSize - newWidth) / 2;
            int y = (targetSize - newHeight) / 2;
            g2d.drawImage(resizedImage, x, y, null);
            g2d.dispose();

            // Step 5: Encode the squared image back to base64
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(squaredImage, "png", bos);

            byte[] squaredImageBytes = bos.toByteArray();
            bos.close();

            return Base64.getEncoder().encodeToString(squaredImageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
