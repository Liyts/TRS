import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        BufferedImage bufImg;
        try {
            bufImg= ImageIO.read(new File("example.png"));

        byte[][] green = new byte[200][200];
        for (int x=0;x<200;x++){
            for (int y=0;y<200;y++){
                int color = bufImg.getRGB(x,y);
                green[x][y] = (byte)(color>>8);
            }
        }
        for (int x=0;x<200;x++){
            for (int y=0;y<200;y++){
                System.out.print(green[x][y]);
            }
            System.out.println();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
