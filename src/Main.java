import java.awt.*;
import java.awt.image.BufferedImage;

import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;

public class Main
{
    public static BufferedImage limpaImagem(BufferedImage input) throws Exception {
        Raster raster = input.getRaster();
        //Cria rasterizador de saida
        BufferedImage outImg = new BufferedImage(raster.getWidth(), raster.getHeight(), input.getType());
        WritableRaster wr = outImg.getRaster();

        //Cria arrays com a quantidade de bytes por pixel
        int[] pixel = new int[raster.getNumBands()];
        int[] pixelOut = new int[pixel.length];

        //Loopa os pixels de entrada
        for (int ix = 0; ix < raster.getWidth(); ++ix){
            for (int iy = 0; iy < raster.getHeight(); iy++){
                //pega valor do pixel e joga no array
                raster.getPixel(ix,iy, pixel);
                for(int i = 0; i < pixel.length; ++i)
                {
                     if (pixel[i] > 104)
                        pixelOut[i] = 255;
                     else
                         pixelOut[i] = 0;
                }

                //escreve pixel no raster de saida
                wr.setPixel(ix, iy, pixelOut);
            }
        }
        return outImg;
    }

    public static BufferedImage detectaRobo(BufferedImage input) throws Exception{
        Raster raster = input.getRaster();
        //Cria rasterizador de saida
        BufferedImage outImg = new BufferedImage(raster.getWidth(), raster.getHeight(), input.getType());
        WritableRaster wr = outImg.getRaster();

        //Cria arrays com a quantidade de bytes por pixel
        int[] pixel = new int[raster.getNumBands()];
        int[] pixelOut = new int[pixel.length];

        //Loopa os pixels de entrada
        for (int ix = 0; ix < raster.getWidth(); ++ix){
            for (int iy = 0; iy < raster.getHeight(); iy++){
                //pega valor do pixel e joga no array
                raster.getPixel(ix,iy, pixel);
                for(int i = 0; i < pixel.length; ++i)
                {
                    if (pixel[i] >= 251)
                        pixelOut[i] = 255;
                    else
                        pixelOut[i] = 0;
                }

                //escreve pixel no raster de saida
                wr.setPixel(ix, iy, pixelOut);
            }
        }

        return outImg;
    }


    private static BufferedImage aplicaErosao(BufferedImage input) {
        float estrutura[] = {
                0, 0, 0,
                0, 1, 0,
                0, 0, 0,
        };

        KernelJAI kernel = new KernelJAI(3, 3, estrutura);
        ParameterBlock p = new ParameterBlock();
        p.addSource(input);
        p.add(kernel);
        return  JAI.create("erode", p).getAsBufferedImage();
    }

    private static BufferedImage aplicaErosao7(BufferedImage input) {
        float estrutura[] = {
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 1, 1, 1, 0, 0,
                0, 0, 1, 1, 1, 0, 0,
                0, 0, 1, 1, 1, 0, 0,
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0,

        };

        KernelJAI kernel = new KernelJAI(7, 7, estrutura);
        ParameterBlock p = new ParameterBlock();
        p.addSource(input);
        p.add(kernel);
        return  JAI.create("erode", p).getAsBufferedImage();
    }

    public static void main(String args[]) throws Exception{

        //evitar problema com o JAI quando esta propriedade está ativa

        BufferedImage input = ImageIO.read(new File("c://Temp/img/Teste.gif"));


        System.setProperty("com.sun.media.jai.disableMediaLib", "true");
        BufferedImage limpo = limpaImagem(input);
        BufferedImage robo = detectaRobo(input);
        BufferedImage erosao = aplicaErosao(robo);
        BufferedImage mapa = aplicaErosao7(limpo);


        ImageIO.write(mapa, "GIF", new File("c://temp/output/dilate.gif"));
    }

}
