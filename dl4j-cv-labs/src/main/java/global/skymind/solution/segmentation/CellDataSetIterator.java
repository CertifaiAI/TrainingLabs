package global.skymind.solution.segmentation;

import global.skymind.Helper;
import org.datavec.image.transform.ImageTransform;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.datavec.api.io.filters.BalancedPathFilter;
import org.datavec.api.split.FileSplit;
import org.datavec.api.split.InputSplit;
import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.recordreader.ImageRecordReader;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

public class CellDataSetIterator {
    private static final int height = 224;
    private static final int width = 224;
    private static final int channels = 1;
    private static final long seed = 12345;
    private static final Random random = new Random(seed);
    private static File parentDir = new File(System.getProperty("user.home"), ".deeplearning4j\\data\\data-science-bowl-2018");
    private static String inputDir;
    private static File file = new File(parentDir + "\\data-science-bowl-2018.zip");
//    private static String downloadLink = "https://drive.google.com/a/skymind.my/uc?authuser=0&id=1zHn593J13dxLO1AJ0N2jKhpahs0yYGa0&export=download";
    private static String downloadLink;
    private static CustomLabelGenerator labelMaker = new CustomLabelGenerator(height, width, channels);
    private static InputSplit trainData,valData;
    private static int batchSize;

    //scale input to 0 - 1
    private static DataNormalization scaler = new ImagePreProcessingScaler(0, 1);
    private static ImageTransform transform;

    public CellDataSetIterator() throws IOException {
    }

    private static RecordReaderDataSetIterator makeIterator(InputSplit split, boolean training) throws IOException {
        ImageRecordReader recordReader = new ImageRecordReader(height,width,channels,labelMaker);
        if (training && transform != null){
            recordReader.initialize(split,transform);
        }else{
            recordReader.initialize(split);
        }
        RecordReaderDataSetIterator iter = new RecordReaderDataSetIterator(recordReader, batchSize, 1, 1, true);
        iter.setPreProcessor(scaler);

        return iter;
    }

    public static RecordReaderDataSetIterator trainIterator() throws IOException {
        return makeIterator(trainData, true);
    }

    public static RecordReaderDataSetIterator valIterator() throws IOException {
        return makeIterator(valData, false);
    }

    public static void setup(int batchSizeArg, double trainPerc, ImageTransform imageTransform) throws IOException {
        transform=imageTransform;
        setup(batchSizeArg,trainPerc);
    }

    public static void setup(int batchSizeArg, double trainPerc) throws IOException {

        downloadData();
        unzipAllDataSet();

        batchSize = batchSizeArg;

        inputDir = Paths.get(
                System.getProperty("user.home"),
                Helper.getPropValues("dl4j_home.data")
        ).toString();

        File imagesPath = new File(Paths.get(inputDir, "data-science-bowl-2018/data-science-bowl-2018/data-science-bowl-2018-2/train/inputs").toString());
        FileSplit imageFileSplit = new FileSplit(imagesPath, NativeImageLoader.ALLOWED_FORMATS, random);
        BalancedPathFilter imageSplitPathFilter = new BalancedPathFilter(random, NativeImageLoader.ALLOWED_FORMATS, labelMaker);
        InputSplit[] imagesSplits = imageFileSplit.sample(imageSplitPathFilter, trainPerc, 1-trainPerc);

        trainData = imagesSplits[0];
        valData = imagesSplits[1];
    }

    public static void downloadData() throws IOException {
        downloadLink = Helper.getPropValues("dataset.segmentationCell.url");

        if (!file.exists()) {
            System.out.println("Creating dataset folder ...");
            file.getParentFile().mkdirs();
            HttpClientBuilder builder = HttpClientBuilder.create();
            CloseableHttpClient client = builder.build();
            System.out.println("Downloading dataset ...");
            try (CloseableHttpResponse response = client.execute(new HttpGet(downloadLink))) {
                HttpEntity entity = response.getEntity();

                System.out.println(entity);

                if (entity != null) {
                    try (FileOutputStream outstream = new FileOutputStream(file)) {
                        entity.writeTo(outstream);
                        outstream.flush();
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }


        }

    }

    public static void unzip(String source, String destination){
        try {
            ZipFile zipFile = new ZipFile(source);
            zipFile.extractAll(destination);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static void unzipAllDataSet() throws IOException {
        //unzip training data set
        inputDir = Paths.get(
                System.getProperty("user.home"),
                Helper.getPropValues("dl4j_home.data")
        ).toString();

        File zipClassFilePath = new File(Paths.get(inputDir, "data-science-bowl-2018/data-science-bowl-2018.zip").toString());

        File classFolder = new File(Paths.get(inputDir, "data-science-bowl-2018/data-science-bowl-2018").toString());
        if (!classFolder.exists()){
            classFolder.mkdir();
            System.out.println("Unzipping dataset ...");
            unzip(zipClassFilePath.toString(), classFolder.toString());
        }
    }

}