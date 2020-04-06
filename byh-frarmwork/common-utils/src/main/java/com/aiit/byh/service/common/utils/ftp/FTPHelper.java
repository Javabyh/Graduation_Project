package com.aiit.byh.service.common.utils.ftp;

import com.aiit.byh.service.common.utils.CommonUtils;
import com.aiit.byh.service.common.utils.config.ConfigUtil;
import com.aiit.byh.service.common.utils.file.FileHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

public class FTPHelper {
    private static Logger logger = LoggerFactory.getLogger(FTPHelper.class);

    private static int datatimeout = ConfigUtil.getInt("ftp.data.timeout", 7200);
    private static int conntimeout = ConfigUtil.getInt("ftp.connect.timeout", 7200);

    private String server; // ip地址
    private int port; // 端口号
    private String username; // 用户名
    private String password; // 密码

    /**
     * 下拉FTP指定文件
     *
     * @param filePath
     * @param fileName
     * @param downloadFileName
     * @return
     */
    public boolean downloadFile(String filePath, String fileName, String downloadFileName) {
        boolean result = true;
        FTPClient ftpClient = null;
        logger.info("****开始下拉FTP文件 FileName:{}****", fileName);
        try {
            // 获取ftp连接
            ftpClient = connectServer();
            ftpClient.setControlKeepAliveTimeout(300);
            ftpClient.changeWorkingDirectory(new String(filePath.getBytes("UTF-8"), "ISO-8859-1"));

            // 下拉文件
            FileHelper.createDirs(downloadFileName);
            File downloadFile = new File(downloadFileName);
            OutputStream outputStream = new FileOutputStream(downloadFile);
            ftpClient.enterLocalPassiveMode();
            result = ftpClient.retrieveFile(new String(fileName.getBytes("UTF-8"), "ISO-8859-1"), outputStream);
            outputStream.close();
            logger.info("****FTP文件下拉成功 FilePath:{}, FileName:{}, downloadFileName:{}****", filePath, fileName, downloadFileName);
        } catch (Exception ex) {
            logger.error("****FTP文件下拉出错 FilePath:{}, FileName:{}****", filePath, fileName, ex);
            return false;
        } finally {
            closeServer(ftpClient);
        }

        return result;
    }

    /**
     * 下拉ftp指定目录文件
     *
     * @param ftpPathName
     * @param localPathName
     * @param downloadFileNames
     * @return
     */
    public boolean downloadFiles(String ftpPathName, String localPathName, List<String> downloadFileNames, FtpFileNameFilter filter) {
        boolean result = true;
        FTPClient ftpClient = null;
        logger.info("****开始下拉联通FTP文件 FtpPathName:{}****", ftpPathName);
        try {
            // 获取ftp连接
            ftpClient = connectServer();
            ftpClient.setControlKeepAliveTimeout(300);
            ftpClient.enterLocalPassiveMode();

            // 下拉满足要求的文件
            ftpClient.changeWorkingDirectory(ftpPathName);
            String[] fileNames = ftpClient.listNames(ftpPathName);
            if (CommonUtils.isNotEmpty(fileNames)) {
                for (String fileName : fileNames) {
                    // 判断文件是否需要下拉
                    if (null != filter
                            && !filter.accept(fileName)) {
                        continue;
                    }
                    // 生成本地文件名称
                    String syncFileName = localPathName + fileName;

                    // 下拉文件
                    FileHelper.createDirs(syncFileName);
                    File syncFile = new File(syncFileName);
                    OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(syncFile));
                    result = ftpClient.retrieveFile(new String(fileName.getBytes("UTF-8"), "ISO-8859-1"), outputStream);
                    outputStream.close();
                    downloadFileNames.add(syncFileName);
                    if (!result) {
                        logger.error("****联通FTP文件下拉出错 FtpPathName:{}****", ftpPathName);
                        return false;
                    }
                }
            }

            logger.info("****联通FTP文件下拉成功 FtpPathName:{}, 本地文件路径:{}****", ftpPathName, downloadFileNames);
        } catch (Exception ex) {
            logger.error("****联通FTP文件下拉出错 FtpPathName:{}****", ftpPathName, ex);
            return false;
        } finally {
            closeServer(ftpClient);
        }

        return result;
    }

    /**
     * 读取ftp文件
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public byte[] readFile(String filePath, String fileName) {
        FTPClient ftpClient = null;
        logger.info("****开始读取FTP文件 FileName:{}****", fileName);
        try {
            // 获取ftp连接
            ftpClient = connectServer();
            ftpClient.setControlKeepAliveTimeout(300);
            if (StringUtils.isNotBlank(filePath)) {
                ftpClient.changeWorkingDirectory(new String(filePath.getBytes("UTF-8"), "ISO-8859-1"));
            }

            // 读取文件内容
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ftpClient.enterLocalPassiveMode();
            ftpClient.retrieveFile(new String(fileName.getBytes("UTF-8"), "ISO-8859-1"), outputStream);
            byte[] result = outputStream.toByteArray();
            outputStream.close();
            logger.info("****读取FTP文件成功 FilePath:{}, FileName:{}****", filePath, fileName);
            return result;
        } catch (Exception ex) {
            logger.error("****读取FTP文件出错 FilePath:{}, FileName:{}****", filePath, fileName, ex);
            return null;
        } finally {
            closeServer(ftpClient);
        }
    }

    /**
     * 上传数据到ftp
     *
     * @param pathName
     * @param fileName
     * @param datas
     * @return
     */
    public boolean uploadDatas(String pathName,
                               String fileName,
                               List<String> datas) {
        FTPClient ftpClient = null;
        logger.info("****FTP文件开始上传 PathName:{} FileName:{}****", pathName, fileName);

        try {
            // 获取ftp连接
            ftpClient = connectServer();
            boolean flag = ftpClient.changeWorkingDirectory(pathName);
            if (!flag) {
                logger.error("****上传目录不存在:{}****", pathName);
                return false;
            }

            // 上传文件
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftpClient.setControlKeepAliveTimeout(300);
            ftpClient.enterLocalPassiveMode();
            InputStream inputStream = write(datas);
            boolean result = ftpClient.storeFile(new String(fileName.getBytes("UTF-8"), "iso-8859-1"), inputStream);
            if (!result) {
                logger.error("****FTP上传文件失败 FileName:{}****", fileName);
                return false;
            }
            logger.info("****FTP上传文件成功 FileName:{}****", fileName);
        } catch (Exception ex) {
            logger.error("****FTP上传文件出错 FileName:{}****", fileName, ex);
            return false;
        } finally {
            closeServer(ftpClient);
        }

        return true;
    }

    private FTPClient connectServer() {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(server, port);// 连接FTP服务器
            ftpClient.login(username, password);// 登陆FTP服务器
            ftpClient.sendCommand("OPTS UTF8", "ON");
            ftpClient.setControlEncoding("UTF-8");

            ftpClient.setRemoteVerificationEnabled(false);
            ftpClient.setDataTimeout(datatimeout);
            ftpClient.setConnectTimeout(conntimeout);
            ftpClient.setKeepAlive(true);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                logger.info("****未连接到FTP，用户名或密码错误****");
                ftpClient.disconnect();
            } else {
                logger.info("****FTP连接成功****");
            }
        } catch (Exception ex) {
            logger.info("****FTP连接出错****", ex);
        }
        return ftpClient;
    }

    private void closeServer(FTPClient ftpClient) {
        if (null != ftpClient && ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException ex) {
                logger.error("****FTP连接关闭出错****", ex);
            }
        }
    }

    public InputStream write(List<String> datas) throws UnsupportedEncodingException {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            if (CommonUtils.isNotEmpty(datas)) {
                for (String data : datas) {
                    stringBuilder.append(data).append("\n");
                }
            }
            return new ByteArrayInputStream(stringBuilder.toString().getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
            throw e;
        }
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}