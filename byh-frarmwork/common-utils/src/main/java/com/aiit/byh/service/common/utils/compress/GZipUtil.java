/**  
 * @Title: GZipUtil.java
 * @Package com.aiit.byhc.util
 * @Description: GZip工具类
 * @author sdwan
 * @date 2014-4-11
 * @version V1.0  
 */
package com.aiit.byh.service.common.utils.compress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @Description: GZip工具类
 * 
 */
public class GZipUtil {

	public static final int BUFFER = 1024;
	public static final String EXT = ".gz";
	
	private final static Logger logger = LoggerFactory
			.getLogger(GZipUtil.class);
	
	public static void compress(byte[] data, String fileName) {
		try {
			// 打开需压缩文件作为文件输入流
			if (data == null || data.length == 0) {
				return;
			}
			DateFormat df = new SimpleDateFormat("MMddHHmmss.SSS");
			ByteArrayInputStream fin = new ByteArrayInputStream(data);
			// 建立压缩文件输出流
			FileOutputStream fout = new FileOutputStream(fileName
					+ "_" + df.format(new Date()) + EXT);
			// 建立gzip压缩输出流
			GZIPOutputStream gzout = new GZIPOutputStream(fout);
			// 设定读入缓冲区尺寸
			byte[] buf = new byte[1024];
			int num;
			while ((num = fin.read(buf)) != -1) {
				gzout.write(buf, 0, num);
			}

			gzout.close();
			fout.close();
			fin.close();
		} catch (Exception ex) {
			logger.error("异常", ex);
		}
	}

	/**
	 * 数据压缩
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] compress(byte[] data) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GZIPOutputStream gos = new GZIPOutputStream(baos);

			int count;
			byte[] buffer = new byte[BUFFER];
			while ((count = bais.read(buffer, 0, BUFFER)) != -1) {
				gos.write(buffer, 0, count);
			}

			gos.finish();
			gos.flush();
			gos.close();
			bais.close();

			byte[] bytes = baos.toByteArray();
			baos.close();

			return bytes;
		} catch (Exception ex) {
			logger.error("异常", ex);
			return null;
		}
	}

	/**
	 * 数据压缩
	 * 
	 * @param is
	 * @throws Exception
	 */
	public static byte[] compress(InputStream is) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GZIPOutputStream gos = new GZIPOutputStream(baos);

			int count;
			byte data[] = new byte[BUFFER];
			while ((count = is.read(data, 0, BUFFER)) != -1) {
				gos.write(data, 0, count);
			}

			gos.finish();
			gos.flush();
			gos.close();
			is.close();

			byte[] bytes = baos.toByteArray();
			baos.close();

			return bytes;
		} catch (Exception ex) {
			logger.error("异常", ex);
			return null;
		}
	}

	/**
	 * @Title: uncompress
	 * @Description: 解压缩
	 * @param inBytes
	 * @throws IOException
	 * @return byte[]
	 */
	public static byte[] uncompress(byte[] inBytes) throws IOException {
		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		GZIPInputStream gunzip = null;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(inBytes);
			gunzip = new GZIPInputStream(in);
			byte[] buffer = new byte[BUFFER];
			int n;
			while ((n = gunzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}			
			return out.toByteArray();
		} catch (Exception ex) {
			logger.warn("****gzip解压异常****", ex);
		} finally {			
			if (out != null) {
				out.flush();
				out.close();
			}
			if (gunzip != null) {
				gunzip.close();
			}
			if (in != null) {
				in.close();
			}
		}
		return null;
	}

	/**
	 * 数据解压缩
	 * 
	 * @param is
	 * @throws IOException
	 */
	public static byte[] uncompress(InputStream is) throws IOException {
		GZIPInputStream gis = new GZIPInputStream(is);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = gis.read(data, 0, BUFFER)) != -1) {
			baos.write(data, 0, count);
		}
		
		byte[] bytes = baos.toByteArray();
		baos.flush();
		baos.close();
		gis.close();

		return bytes;
	}
}
