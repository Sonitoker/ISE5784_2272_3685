package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Image writer tests
 */
class ImageWriterTests {


    //==== the size of the view plane ====//
    int nX = 800;
    int nY = 500;

    //Color yellowColor = new Color(java.awt.Color.YELLOW);

    Color purpleColor = new Color(255d, 0d, 255d); // if r=255 the color is red (for the net)
    Color orangeColor = new Color(255d, 165d, 0d); // if r=255 the color is red (for the net)

    /**
     * Test method for {@link renderer.ImageWriter#writeToImage()}.
     */
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("purpleOrange", nX, nY);
        //=== running on the view plane===//
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                //=== create the net ===//
                if (i % 50 == 0 || j % 50 == 0) { //we want the squares to be 10x16 so every 50 pixels we change the color- 50*10=500, 50*16=800
                    imageWriter.writePixel(i, j, orangeColor);
                } else {
                    imageWriter.writePixel(i, j, purpleColor);
                }
            }


        }
        imageWriter.writeToImage(); //write the image
    }
}