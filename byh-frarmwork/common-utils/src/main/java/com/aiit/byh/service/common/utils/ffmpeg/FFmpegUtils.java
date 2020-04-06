package com.aiit.byh.service.common.utils.ffmpeg;

import com.aiit.byh.service.common.utils.file.FileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * cwma
 *
 * @datetime 2018/10/19
 * @description ffmpeg 帮助类。前提：配置ffmpeg的环境变量
 */
public final class FFmpegUtils {

    private final static Logger logger = LoggerFactory.getLogger(FFmpegUtils.class);

    /**
     * 视频配字幕
     *
     * @param sourceVideoPath 原视频
     * @param srtPath         字幕文件
     * @param matchVideoPath  合成视频
     * @return
     */
    public static boolean matchVideoSubtitle(String sourceVideoPath, String srtPath, String matchVideoPath) {
        try {
            // 校验文件是否存在
            if (!FileHelper.isFileExists(sourceVideoPath)) {
                logger.info("****视频配字幕,原视频文件不存在****");
                return false;
            }
            if (!FileHelper.isFileExists(srtPath)) {
                logger.info("****视频配字幕,原字幕文件不存在****");
                return false;
            }
            // 命令参数
            //ffmpeg -i 1.mp4 -vf subtitles=1.srt out.mp4
            List<String> args = new ArrayList<String>();
            args.add("ffmpeg");
            args.add("-i");
            args.add(sourceVideoPath);
            args.add("-vf");
            args.add("subtitles="+srtPath.trim());
            args.add(matchVideoPath);
            printArgs(args); // 输出命令参数
            processBuilder(args);
            return true;
        } catch (Throwable e) {
            logger.error("****视频配字幕异常****", e);
            return false;
        }
    }

    private static void processBuilder(List<String> args) throws IOException {
        // 开始转码
        ProcessBuilder pBuilder = new ProcessBuilder(args);
        pBuilder.redirectErrorStream(true);
        Process p = pBuilder.start();
        InputStream in = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(System.getProperty("line.separator"));
        }
        in.close();
    }

    /**
     * 提取视频音频
     *
     * @param videoPath
     * @param mp3Path
     * @return
     */
    public static boolean extractVideoToAudio(String videoPath, String mp3Path) {
        try {
            // 校验文件是否存在
            if (!FileHelper.isFileExists(videoPath)) {
                logger.info("****提取视频音频,原视频文件不存在****");
                return false;
            }
            // 命令参数
            //ffmpeg -i 1.mp4 -f mp3 -vn apple.mp3
            List<String> args = new ArrayList<String>();
            args.add("ffmpeg");
            args.add("-i");
            args.add(videoPath);
            args.add("-f");
            args.add("mp3");
            args.add("-vn");
            args.add(mp3Path);

            printArgs(args); // 输出命令参数

            // 开始转码
            processBuilder(args);
            return true;
        } catch (Throwable e) {
            logger.error("****提取视频音频,提取视频音频异常****", e);
            return false;
        }
    }

    /**
     * 获取视频第一帧
     *
     * @param videoPath
     * @param imgPath
     * @return
     */
    public static boolean getVideoFirstFrame(String videoPath, String imgPath) {
        // 校验文件是否存在
        if (!FileHelper.isFileExists(videoPath)) {
            logger.error("****获取视频第一帧,原视频文件不存在****");
            return false;
        }
        try {
            // 命令参数 ffmpeg -i caiilin.wmv -vframes 1 wm.jpg
            List<String> args = new ArrayList<String>();
            args.add("ffmpeg");
            args.add("-i");
            args.add(videoPath);
            args.add("-vframes");
            args.add("1");
            args.add(imgPath);

            printArgs(args); // 输出命令参数
            // 开始转码
            processBuilder(args);
            return true;
        } catch (Exception e) {
            logger.error("****获取视频第一帧异常:,path:{}****", e, videoPath);
            return false;
        }
    }


    /**
     * 获取视频音频总时间
     *
     * @param videoPath
     * @return
     */
    public static long getVideoDuration(String videoPath) {
        // 校验文件是否存在
        if (!FileHelper.isFileExists(videoPath)) {
            logger.error("****获取视频音频总时间,原视频文件不存在****");
            return 0;
        }
        List<String> commands = new ArrayList<>();
        commands.add("ffmpeg");
        commands.add("-i");
        commands.add(videoPath);
        printArgs(commands);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            Process p = builder.start();
            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
            br.close();
            //从视频信息中解析时长
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(stringBuilder.toString());
            if (m.find()) {
                return getTimelen(m.group(1));
            }
        } catch (Exception e) {
            logger.error("****获取视频时长异常：****", e);
            return 0;
        }
        return 0;
    }

    // 格式:"00:00:10.68"
    private static long getTimelen(String timelen) {
        int min = 0;
        String[] strs = timelen.split(":");
        if(strs[0].compareTo("0") > 0) {
            min += Integer.valueOf(strs[0]).intValue() * 60 * 60;
        }
        if(strs[1].compareTo("0") > 0) {
            min += Integer.valueOf(strs[1]).intValue() * 60;
        }
        if(strs[2].compareTo("0") > 0) {
            String[] split = strs[2].split("\\.");
            min += Integer.valueOf(split[0]).intValue();
            if(split[1].compareTo("0") > 0){
                min+=Integer.valueOf(split[1]).intValue()/1000;
            }
        }
        return (long)min;
    }
    private static void printArgs(List<String> args) {
        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            builder.append(arg).append(" ");
        }
        logger.info("****command:{}****", builder.toString());
    }
}