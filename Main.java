import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    private String expansionFile;
    public static BufferedImage bufImg;
    public static BufferedImage outputImg;
    public static int width, height;

    public Main(String path){
        File file = new File(path);
        try {
            bufImg = ImageIO.read(file);
            outputImg = bufImg;
            width = bufImg.getWidth();
            height = bufImg.getHeight();
            expansionFile = path.substring(
                    path.indexOf('.', path.lastIndexOf('/')) + 1).toUpperCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Filter() {// метод модификации, который
        // реализовывает правильный
        // алгоритм, остальное дерьмо
        // полнейшее
        int X, Y;
        int[] mat = new int[9];

        mat[0] = -0;
        mat[1] = -1;
        mat[2] = -0;
        mat[3] = -1;
        mat[4] = 5;
        mat[5] = -1;
        mat[6] = 0;
        mat[7] = -1;
        mat[8] = 0;

        int count;
        for (Y = 1; Y < height - 1; Y++) {
            for (X = 1; X < width - 1; X++) {
                int bx = 0, ax = 0;
                count = 0;
                int resault_R = 0, resault_G = 0, resault_B = 0, alpha = 255;
                for (bx = -1; bx < 2; bx++) {
                    for (ax = -1; ax < 2; ax++) {
                        int inputColor = bufImg.getRGB(X + bx, Y + ax);
                        Color color = new Color(inputColor, true);
                        alpha = color.getAlpha();
                        int red = color.getRed();
                        int green = color.getGreen();
                        int blue = color.getBlue();
                        resault_R += red * mat[count];
                        resault_G += green * mat[count];
                        resault_B += blue * mat[count];
                        count++;
                    }
                }
                if (resault_R < 0)
                    resault_R = 0;
                if (resault_R > 255)
                    resault_R = 255;
                if (resault_G < 0)
                    resault_G = 0;
                if (resault_G > 255)
                    resault_G = 255;
                if (resault_B < 0)
                    resault_B = 0;
                if (resault_B > 255)
                    resault_B = 255;
                Color newColor = new Color(resault_R, resault_G, resault_B,
                        alpha);
                int outputColorInt = newColor.getRGB();
                outputImg.setRGB(X, Y, outputColorInt);
            }
        }
        try {
            String fileName = "newFile." + expansionFile.toLowerCase();
            ImageIO.write(outputImg, expansionFile, new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runThread(int countThread){
        MyThread[] thread = new MyThread[countThread];
        int split = 0;
        int rowCount = 1;//с какой строки определенный поток должен начинать считывать
        if(countThread>Main.width-2){
            System.out.println("Неправильное число потоков");//если потоков больше чем строк нет смысла
            return;
        }
        else{
            split = (Main.width-2)/countThread;//переменная, которая определяет сколько строк должен обрабатывать один поток
            for(int i = 0;i<countThread-1;i++){
                thread[i] = new MyThread(rowCount, (rowCount+split));
                thread[i].start();
                rowCount+=split;
            }
            thread[countThread-1] = new MyThread(rowCount,Main.width-1);//последний поток вынесен отдельно т.к. если общее количесво строк не делится нацело на
                                                                                //число потоков, то он должен обработать что осталось
            thread[countThread-1].start();
            for (int i = 0;i<countThread;i++){
                try {
                    thread[i].join(); // ждем пока все потоки закончат обработку
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                String fileName = "newFileThread." + expansionFile.toLowerCase(); //записываем в файл
                ImageIO.write(outputImg, expansionFile, new File(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class MyThread extends Thread {
        int X = 0, Y = 0;
        int rowCount, rowMax;//с какой и по какую строку поток должен считывать пиксели
        int[] mat = new int[9];

        {
            mat[0] = 0;
            mat[1] = -1;
            mat[2] = 0;
            mat[3] = -1;
            mat[4] = 5;
            mat[5] = -1;
            mat[6] = 0;
            mat[7] = -1;
            mat[8] = 0;
        }

        public MyThread(int rowCount, int rowMax) {
            this.rowCount = rowCount;
            this.rowMax = rowMax;
        }

        @Override
        public void run() {
            int count;
            for (Y = rowCount; Y < rowMax - 1; Y++) {
                for (X = 1; X < Main.width - 1; X++) {
                    int bx = 0, ax = 0;
                    count = 0;
                    int resault_R = 0, resault_G = 0, resault_B = 0, alpha = 255;
                    for (bx = -1; bx < 2; bx++) {
                        for (ax = -1; ax < 2; ax++) {
                            int inputColor = bufImg.getRGB(X + bx, Y + ax);
                            Color color = new Color(inputColor, true);
                            alpha = color.getAlpha();
                            int red = color.getRed();
                            int green = color.getGreen();
                            int blue = color.getBlue();
                            resault_R += red * mat[count];
                            resault_G += green * mat[count];
                            resault_B += blue * mat[count];
                            count++;
                        }
                    }
                    if (resault_R < 0)
                        resault_R = 0;
                    if (resault_R > 255)
                        resault_R = 255;
                    if (resault_G < 0)
                        resault_G = 0;
                    if (resault_G > 255)
                        resault_G = 255;
                    if (resault_B < 0)
                        resault_B = 0;
                    if (resault_B > 255)
                        resault_B = 255;
                    Color newColor = new Color(resault_R, resault_G, resault_B,
                            alpha);
                    int outputColorInt = newColor.getRGB();
                    outputImg.setRGB(X, Y, outputColorInt);
                }
            }
        }

    }
    public void readToArray(String path) { // метод обработки изображения и
        // вызывающий метод модификации
        // изображения


    }
    public static void main(String[] args) {
        Main main = new  Main("sa.jpg");
        //main.runThread(10);//вызывается метод и передается количество потоков
        main.Filter();
    }

}
