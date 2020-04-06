package com.aiit.byh.service.common.utils.validator;

import com.google.common.collect.Maps;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.math.NumberUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 图片验证码工具类
 *
 * @author dsqin
 * @datetime 2016/12/8
 */
public class ValidateImageUtils {

    private static final String VALIDATE_CODE = "validateCode";

    private static final String PARAM_WIDTH = "width";

    private static final String PARAM_HEIGHT = "height";

    private static int DEFAULT_WIDTH = 70;
    private static int DEFAULT_HEIGHT = 26;

    /**
     * 返回验证码和验证码图片
     * @param request
     * @return
     * @throws IOException
     */
    public static Map<String, String> createImage(HttpServletRequest request) throws IOException {

        Map<String, String> map = Maps.newHashMapWithExpectedSize(2);

		/*
         * 得到参数高，宽，都为数字时，则使用设置高宽，否则使用默认值
		 */
        String width = request.getParameter(PARAM_WIDTH);
        String height = request.getParameter(PARAM_HEIGHT);

        int w = NumberUtils.toInt(width, DEFAULT_WIDTH);
        int h = NumberUtils.toInt(height, DEFAULT_HEIGHT);

        BufferedImage image = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

		/*
         * 生成背景
		 */
        createBackground(g, w, h);

		/*
         * 生成字符
		 */
        String s = createCharacter(g);
        map.put("code", s);

        g.dispose();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ImageIO.write(image, "JPEG", out);
        out.close();

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String img = encoder.encode(out.toByteArray());
        map.put("pic", "data:image/jpg;base64," + replaceBlank(img));

        return map;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    private static Color getRandColor(int fc, int bc) {
        int f = fc;
        int b = bc;
        Random random = new Random();
        if (f > 255) {
            f = 255;
        }
        if (b > 255) {
            b = 255;
        }
        return new Color(f + random.nextInt(b - f), f + random.nextInt(b - f),
                f + random.nextInt(b - f));
    }

    /**
     * 创建验证码背景
     *
     * @param g
     */
    private static void createBackground(Graphics g, int w, int h) {
        // 填充背景
        g.setColor(getRandColor(220, 250));
        g.fillRect(0, 0, w, h);
        // 加入干扰线条
        for (int i = 0; i < 8; i++) {
            g.setColor(getRandColor(40, 150));
            Random random = new Random();
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int x1 = random.nextInt(w);
            int y1 = random.nextInt(h);
            g.drawLine(x, y, x1, y1);
        }
    }

    /**
     * 创建验证码包含的字符
     *
     * @param g
     * @return
     */
    private static String createCharacter(Graphics g) {
        char[] codeSeq = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String[] fontTypes = {"\u5b8b\u4f53", "\u65b0\u5b8b\u4f53",
                "\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66"};
        Random random = new Random();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);// random.nextInt(10));
            g.setColor(new Color(50 + random.nextInt(100), 50 + random
                    .nextInt(100), 50 + random.nextInt(100)));
            g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)],
                    Font.BOLD, 26));
            g.drawString(r, 15 * i + 5, 19 + random.nextInt(8));
            // g.drawString(r, i*w/4, h-5);
            s.append(r);
        }
        return s.toString();
    }
}
