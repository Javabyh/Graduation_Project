package com.aiit.byh.service.common.utils.ftp;

/**
 * Created by pjwang2 on 2018\10\16 0016.
 */
public interface FtpFileNameFilter {

    // 根据文件名过滤下载文件
    public boolean accept(String fileName);
}
