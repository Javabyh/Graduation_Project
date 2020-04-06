package com.aiit.byh.service.common.utils.compress;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by pjwang2 on 2019\4\28 0028.
 */
public class RarUtil {

    /**
     * 解压rar文件
     *
     * @param fileName
     * @param outputDirectory
     * @throws IOException
     */
    public static boolean uncompress(String fileName, String outputDirectory) throws IOException, RarException {
        File destDiretory = new File(outputDirectory);
        if (!destDiretory.exists() && !destDiretory.mkdirs()) {
            return false;
        }
        File file = new File(fileName);
        Archive archive = new Archive(new FileInputStream(file));

        FileHeader fh = null;
        File destFileName = null;
        FileOutputStream fos = null;
        try {
            while ((fh = archive.nextFileHeader()) != null) {
                String dirName = StringUtils.isBlank(fh.getFileNameW()) ? fh.getFileNameString() : fh.getFileNameW();
                destFileName = new File(outputDirectory + File.separator + dirName);
                if (fh.isDirectory()) {// 文件夹
                    if (!destFileName.exists()) {
                        destFileName.mkdirs();
                    }
                } else {// 文件
                    if (!destFileName.getParentFile().exists()) {
                        destFileName.getParentFile().mkdirs();
                    }
                    fos = new FileOutputStream(destFileName);
                    archive.extractFile(fh, fos);
                    fos.close();
                }
            }
        } finally {
            if (null != fos) {
                fos.close();
            }
            if (null != archive) {
                archive.close();
            }
        }
        return true;
    }

    public static void main(String[] args) {
        try {
            RarUtil.uncompress("C:\\Users\\Administrator\\Desktop\\test\\test.rar",
                    "C:\\Users\\Administrator\\Desktop\\test");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RarException e) {
            e.printStackTrace();
        }
    }
}
