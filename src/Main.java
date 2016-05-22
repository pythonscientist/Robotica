import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;

public class Main
{
    public static BufferedImage limpaImagem(BufferedImage input) throws Exception {

        System.out.println(String.format("Input size = %dx%dx%d", input.getWidth(), input.getHeight(), input.getType()));

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

        System.out.println(String.format("Input size = %dx%dx%d", input.getWidth(), input.getHeight(), input.getType()));

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

    private static BufferedImage desenhaGrid(BufferedImage input) {

        System.out.println(String.format("Input size = %dx%dx%d", input.getWidth(), input.getHeight(), input.getType()));

        Raster raster = input.getRaster();
        //Cria rasterizador de saida
        BufferedImage outImg = new BufferedImage(raster.getWidth(), raster.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
        WritableRaster wr = outImg.getRaster();

        //Cria arrays com a quantidade de bytes por pixel
        int[] pixel = new int[raster.getNumBands()];
        int[] pixelOut = new int[pixel.length];

        int pixels_largura = (int)(raster.getWidth() / 7.0f);
        int pixels_altura  = (int)(raster.getHeight() / 6.0f);

        //Loopa os pixels de entrada
        for (int ix = 0; ix < raster.getWidth(); ++ix){
            for (int iy = 0; iy < raster.getHeight(); iy++) {
                //pega valor do pixel e joga no array
                raster.getPixel(ix, iy, pixel);

                if ((ix % pixels_largura) == 0 || (iy % pixels_altura) == 0) {
                    for (int i = 0; i < pixel.length; ++i) {
                        pixelOut[i] = 99;
                    }
                } else {
                    for (int i = 0; i < pixel.length; ++i) {
                        pixelOut[i] = pixel[i];
                    }
                }


                //escreve pixel no raster de saida
                wr.setPixel(ix, iy, pixelOut);
            }
        }

        return outImg;
    }


    private static float[][] porcentagemCor(BufferedImage input, int cor) {

        System.out.println(String.format("Input size = %dx%dx%d", input.getWidth(), input.getHeight(), input.getType()));

        float porcentagem_pixels[][] = new float[6][7];

        Raster raster = input.getRaster();

        //Cria arrays com a quantidade de bytes por pixel
        int[] pixel = new int[raster.getNumBands()];

        int pixels_altura = (int)(raster.getHeight() / 6.0f);
        int pixels_largura  = (int)(raster.getWidth()/ 7.0f);
        System.out.println(String.format("Pixel Largura = %d", pixels_altura));
        System.out.println(String.format("Pixel Altura  = %d", pixels_largura));

        int c_linha  = 0;
        int c_coluna = 0;

        int count_pixels[][] = new int[6][7];

        //Loopa os pixels de entrada
        for (int ix = 0; ix < raster.getWidth(); ++ix){
            for (int iy = 0; iy < raster.getHeight(); iy++) {
                //pega valor do pixel e joga no array
                raster.getPixel(ix, iy, pixel);

                // largura
                if (ix <= pixels_largura) {
                    c_coluna = 0;
                } else

                if (ix <= pixels_largura*2) {
                    c_coluna = 1;
                } else

                if (ix <= pixels_largura*3) {
                    c_coluna = 2;
                } else

                if (ix <= pixels_largura*4) {
                    c_coluna = 3;
                } else

                if (ix <= pixels_largura*5) {
                    c_coluna = 4;
                } else

                if (ix <= pixels_largura*6) {
                    c_coluna = 5;
                }else

                if (ix <= pixels_largura*7) {
                    c_coluna = 6;
                }

                // altura
                if (iy <= pixels_altura) {
                    c_linha = 0;
                } else

                if (iy <= pixels_altura*2) {
                    c_linha = 1;
                } else

                if (iy <= pixels_altura*3) {
                    c_linha = 2;
                } else

                if (iy <= pixels_altura*4) {
                    c_linha = 3;
                } else

                if (iy <= pixels_altura*5) {
                    c_linha = 4;
                } else

                if (iy <= pixels_altura*6) {
                    c_linha = 5;
                }


                if (pixel[0] == cor) {
                    count_pixels[c_linha][c_coluna] += 1;
                }

            }
        }

        System.out.println("Porcentagem mapa");
        for (int i=0; i<6; i++) {
            for (int j=0; j<7; j++) {
                Float porcentagem = Float.valueOf(count_pixels[i][j]) / Float.valueOf(pixels_largura*pixels_altura);
                System.out.println(String.format("%dx%d = %f", i, j, porcentagem.floatValue() * 100));
                porcentagem_pixels[i][j] = porcentagem.floatValue() * 100;
            }
        }

        return porcentagem_pixels;
    }

    public static void main(String args[]) throws Exception{

        //evitar problema com o JAI quando esta propriedade está ativa

        BufferedImage input = ImageIO.read(new File("c://Temp/img/Teste.gif"));

        System.setProperty("com.sun.media.jai.disableMediaLib", "true");
        BufferedImage limpo = limpaImagem(input);

        BufferedImage robo = detectaRobo(input);
        robo = desenhaGrid(robo);

        BufferedImage mapa = desenhaGrid(limpo);
        float[][] porcentagem_pixel_preto  = porcentagemCor(mapa, 0);
        float[][] porcentagem_pixel_branco = porcentagemCor(robo, 255);

        float[][] matrix_final = processaMatrixFinal(porcentagem_pixel_preto, porcentagem_pixel_branco, 10.0f, 15.0f);

        BufferedImage mapa_final = desenhaMapaFinal(matrix_final, mapa.getWidth(), mapa.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
        mapa_final = desenhaGrid(mapa_final);

        float[][] matrix_wavefront = montaWavefront(matrix_final, 6.0f, 6.0f);

        ImageIO.write(mapa, "GIF", new File("c://temp/output/mapa.gif"));
        ImageIO.write(robo, "GIF", new File("c://temp/output/robo.gif"));
        ImageIO.write(mapa_final, "GIF", new File("c://temp/output/mapa_final.gif"));
    }

    private static float[][] montaWavefront(float[][] matrix_final, float dest_x, float dest_y) {
        return null;
    }

    private static void bfs() {

    }


    private static BufferedImage desenhaMapaFinal(float[][] matrix_final, int width, int height, int type) {
        BufferedImage outImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
        WritableRaster wr = outImg.getRaster();
        int pixels_altura = (int)(height / 6.0f);
        int pixels_largura  = (int)(width/ 7.0f);
        System.out.println(String.format("Pixel Largura = %d", pixels_largura));
        System.out.println(String.format("Pixel Altura  = %d", pixels_altura));

        for (int i=0; i<6; i++) {
            for (int j = 0; j <7; j++) {
                if (matrix_final[i][j] == 1.0f) {
                    desenhaRetangulo(i, j, pixels_altura, pixels_largura, width, height, wr, 0);
                } else

                if (matrix_final[i][j] == 2.0f) {
                    desenhaRetangulo(i, j, pixels_altura, pixels_largura, width, height, wr, 33);
                } else {
                    desenhaRetangulo(i, j, pixels_altura, pixels_largura, width, height, wr, 255);
                }

            }
        }

        return outImg;
    }

    private static void desenhaRetangulo(int i, int j, int pixels_altura, int pixels_largura, int width, int height, WritableRaster wr, int cor) {
        int pixelOut[] = {cor};
        for (int y=0; y<pixels_altura; y++) {
            for (int x=0; x<pixels_largura; x++) {

                // aqui inverte o i e o j pois o i corresponde a contagem dos quadrados da largura e o j corresponde a linha
                int rx = (j*pixels_largura)+x;
                int ry = (i*pixels_altura)+y;

                if (rx>width) {
                    throw  new RuntimeException("rx maior que Width");
                }

                if (ry>height) {
                    throw  new RuntimeException("ry maior que Height");
                }
                
                wr.setPixel(rx, ry, pixelOut);

            }
        }
    }

    private static float[][] processaMatrixFinal(float[][] porcentagem_mapa, float[][] porcentagem_robo,
                                                    float threshold_mapa, float threshold_robo) {

        float mapa_final[][] = new float[6][7];
        for (int i=0; i<6; i++) {
            for (int j = 0; j < 7; j++) {

                if (porcentagem_mapa[i][j] > threshold_mapa) {
                    mapa_final[i][j] = 1.0f;
                }

                if (porcentagem_robo[i][j] > threshold_robo) {
                    mapa_final[i][j] = 2.0f;
                }

            }
        }

        System.out.println("Mapa Final");
        for (int i=0; i<6; i++) {
            for (int j=0; j<7; j++) {
                System.out.println(String.format("%dx%d = %f", i, j, mapa_final[i][j]));
            }
        }
        return mapa_final;
    }
}
