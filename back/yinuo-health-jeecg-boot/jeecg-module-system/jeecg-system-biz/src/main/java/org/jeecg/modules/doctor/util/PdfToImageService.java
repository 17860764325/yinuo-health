package org.jeecg.modules.doctor.util;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * PDF转图片并存储到SQL Server的工具类
 */
@Service
public class PdfToImageService {

    // SQL Server连接信息，实际使用中建议放在配置文件
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=yourDB;encrypt=false";
    private static final String USER = "yourUsername";
    private static final String PASS = "yourPassword";

    /**
     * 将PDF的第一页转换为图片字节数组
     * @param pdfUrl PDF的URL地址
     * @return 图片的字节数组
     * @throws IOException 处理过程中发生的IO异常
     */
    public byte[] convertPdfFirstPageToImage(String pdfUrl) throws IOException {
        // 使用Hutool下载PDF文件到临时输入流
        try (InputStream pdfStream = HttpUtil.createGet(pdfUrl).execute().bodyStream();
             PDDocument document = PDDocument.load(pdfStream)) {

            // 创建PDF渲染器
            PDFRenderer renderer = new PDFRenderer(document);

            // 渲染第一页（索引从0开始），设置适当的DPI（300为高清）
            BufferedImage image = renderer.renderImage(0, 2.0f);

            // 将BufferedImage转换为字节数组
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                // 保存为PNG格式，也可以改为JPG等其他格式
                ImageIO.write(image, "png", outputStream);
                return outputStream.toByteArray();
            }
        }
    }

    /**
     * 将图片字节数组存储到SQL Server的image类型字段
     * @param imageData 图片字节数组
     * @param pdfUrl 原始PDF的URL，用于记录
     * @return 是否存储成功
     * @throws SQLException 数据库操作异常
     */
    public boolean saveImageToSqlServer(byte[] imageData, String pdfUrl) throws SQLException {
        String sql = "INSERT INTO PdfImages (PdfUrl, ImageData, CreateTime) VALUES (?, ?, GETDATE())";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pdfUrl);
            pstmt.setBytes(2, imageData);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * 处理PDF并存储图片的完整流程
     * @param pdfUrl PDF的URL地址
     * @return 是否处理成功
     */
    public boolean processAndSavePdfImage(String pdfUrl) {
        try {
            // 转换PDF为图片
            byte[] imageData = convertPdfFirstPageToImage(pdfUrl);
            if (imageData == null || imageData.length == 0) {
                System.out.println("转换PDF失败，未获取到图片数据");
                return false;
            }

            // 存储到数据库
            return saveImageToSqlServer(imageData, pdfUrl);
        } catch (Exception e) {
            System.err.println("处理PDF并存储图片时发生错误: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        // 测试用的PDF URL
        String testPdfUrl = "https://www.pwithe.com/Public/Upload/download/20170211/589ebf8e5bb13.pdf"; // 替换为实际的PDF URL

        PdfToImageService saver = new PdfToImageService();

        // 测试转换功能
        try {
            byte[] imageData = saver.convertPdfFirstPageToImage(testPdfUrl);
            System.out.println("PDF转换成功，图片大小: " + imageData.length + " 字节");

            // 先保存到本地文件查看效果
            FileUtil.writeBytes(imageData, "test_pdf_image.png");
            System.out.println("图片已保存到本地: test_pdf_image.png");

            // 测试存储到数据库
            boolean saveSuccess = saver.saveImageToSqlServer(imageData, testPdfUrl);
            if (saveSuccess) {
                System.out.println("图片成功存储到SQL Server");
            } else {
                System.out.println("图片存储到SQL Server失败");
            }
        } catch (Exception e) {
            System.err.println("测试过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
