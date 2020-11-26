package com.modularity.common.utils.managers.manager;

import android.graphics.Bitmap;

import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PdfManager {

    private PdfManager() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /***
     * PDF文件转PNG图片
     * @param pdfFilePath pdf完整路径
     * @param dstImgFolder 图片存放的文件夹
     * @param dpi dpi越大转换后越清晰，相对转换速度越慢
     * @param flag 页数 为0则转换全部页数
     */
    public static void pdf2Image(String pdfFilePath, String dstImgFolder, int dpi, int flag, PdfManagerConvertListener listener) {
        File file = new File(pdfFilePath);
        PDDocument pdDocument;
        try {
            String imgPDFPath = file.getParent();
            int dot = file.getName().lastIndexOf('.');
            String imagePDFName = file.getName().substring(0, dot);
            String imgFolderPath;
            if (dstImgFolder.equals("")) {
                imgFolderPath = imgPDFPath;
            } else {
                imgFolderPath = dstImgFolder;
            }

            if (createDirectory(imgFolderPath)) {
                pdDocument = PDDocument.load(file);

                PDFRenderer renderer = new PDFRenderer(pdDocument);
                int pages = pdDocument.getNumberOfPages();
                if (flag > 0) {//大于0则打印具体页数
                    if (flag < pages) {
                        pages = flag;
                    }
                }
                ArrayList<String> pathList = new ArrayList<>();
                StringBuffer imgFilePath;
                for (int i = 0; i < pages; i++) {
                    String imgFilePathPrefix = imgFolderPath + imagePDFName;
                    imgFilePath = new StringBuffer();
                    imgFilePath.append(imgFilePathPrefix);
                    imgFilePath.append("_");
                    imgFilePath.append((i + 1));
                    imgFilePath.append(".jpg");
                    Bitmap image = renderer.renderImageWithDPI(i, dpi);
                    ImageManager.save(image, imgFilePath.toString(), Bitmap.CompressFormat.JPEG);
                    pathList.add(imgFilePath.toString());
                }
                if (listener != null) {
                    listener.onPdfConvertComplete(true, pathList);
                }
            } else {
                if (listener != null) {
                    listener.onPdfConvertComplete(false, null);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onPdfConvertComplete(false, null);
            }
        }
    }

    private static boolean createDirectory(String folder) {
        File dir = new File(folder);
        if (dir.exists()) {
            return true;
        } else {
            return dir.mkdirs();
        }
    }

    public interface PdfManagerConvertListener {
        void onPdfConvertComplete(boolean success, ArrayList<String> imgPathList);
    }

}
