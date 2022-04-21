package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * The class is responsible for showing fetched channels to the user
 * @author id18hll
 * @version 1.0
 */
public class ImageLoader extends ImageIcon {

    /**
     * Returns a image icon for the current program. The image is either taken from the programs image URL or if the
     * URL is not given the image ia a generic SR's logo image
     * @param imageURL The URL for a program's image
     * @return The image icon to use for the program
     * @throws IOException If the method could not get an image
     */
    public ImageIcon getProgramImageIcon(String imageURL) throws IOException {
        ImageIcon imageIcon;
        BufferedImage image;
        if (imageURL != null){
            image = ImageIO.read(new URL(imageURL));
        } else {
            image = getGenericImage();
        }
        image = getRoundedImage(image, 50);
        Image img = image.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(img);
        return imageIcon;
    }

    /**
     * Takes a generic image of SR's logo ad creates a buffered image of it
     * @return The buffered image
     * @throws IOException If the image can not be read
     */
    private BufferedImage getGenericImage() throws IOException {
        URL resource = Frame.class.getResource("images/SR1.png");
        BufferedImage image;
        image = ImageIO.read(resource);
        return image;
    }

    /**
     * For a given image, creates an copy in in Graphics2D, rounds the corners and makes it info an buffered image.
     * @param image Image to round corners on
     * @param cornerRadius The radius of the corners
     * @return An buffered image with rounded corners
     */
    private BufferedImage getRoundedImage(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return output;
    }
}
