package com.aiit.byh.service.common.utils.file;

import com.aiit.byh.service.common.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHelper {
    // 截取url扩展名正则表达式
    private static final Pattern EXT_PATTERN = Pattern.compile("\\.([^\\./\\\\]+?)(?=\\?|$)", Pattern.MULTILINE);

    public static boolean deleteFile(String filePath) {
        if (null != filePath) {
            File file = new File(filePath);
            if (!file.exists()) {
                return true;
            }
            return file.delete();
        }
        return true;
    }

    public static boolean deleteFile(File file) {
        if (null != file) {
            return file.delete();
        }
        return true;
    }

    public static List<String> readLine(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader reader = null;
        List<String> lines = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if (StringUtils.isBlank(tempString)) {
                    continue;
                }
                lines.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return lines;
    }

    /**
     * 获取扩展名称
     *
     * @param fileName
     * @param split
     * @return
     */
    public static String getExtName(String fileName, String split) {
        int i = fileName.lastIndexOf(split);
        int leg = fileName.length();
        return (i > 0 ? (i + 1) == leg ? " " : fileName.substring(i,
                fileName.length()) : " ");
    }

    /**
     * 生成本地文件路径
     *
     * @param url  网络路径url
     * @param path 本地路径前缀
     * @param name 文件名称
     * @return
     */
    public static String genLocalFilePathByUrl(String url, String path, String name) {
        String extName = "mp3";
        Matcher matcher = EXT_PATTERN.matcher(url);
        if (matcher.find()) {
            extName = matcher.group(1);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        String currDate = sdf.format(new Date());

        if (path.endsWith("\\") || path.endsWith("/")) {
            return path + currDate + File.separator + name + "." + extName;
        } else {
            return path + File.separator + currDate + File.separator + name + "." + extName;
        }
    }

    /**
     * 文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static boolean createDirs(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 写文件
     *
     * @return
     */
    public static boolean writeFile(String filePath, List<String> lines) {
        if (CommonUtils.isEmpty(lines)) {
            return false;
        }

        BufferedWriter outputStream = null;
        Writer writer = null;
        try {
            writer = new FileWriter(new File(filePath));
            outputStream = new BufferedWriter(writer);
            for (String warningMessage : lines) {
                outputStream.write(warningMessage);
                outputStream.newLine();
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception ex) {
            return false;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
            }
        }
        return true;
    }
}
