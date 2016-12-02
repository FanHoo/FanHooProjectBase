package com.fanhoo.lib.utils.file;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 创建人 胡焕
 * 创建时间 2016年11月30日  09:31
 * 用途
 */

public final class FileUtil {
    public static final String TAG = "FileUtil";

    private static int bufferSize = 1024;


    /**
     * 将文件读取为字符串
     *
     * @author 胡焕
     * @date 2016/11/30 11:57
     */
    public static String readFileToString(File target) {
        String str = "";
        if (target == null)
            throw new NullPointerException("读取的文件不能为NULL");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(target));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            str = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(bufferedReader);
        }

        return str;

    }

    /**
     * 创建一个文件或者一个目录
     *
     * @param file  要创建的文件
     * @param isDir 要创建的是文件还是目录 true创建目录 false 创建文件
     * @author 胡焕
     * @date 2016/11/30 13:37
     */
    public static boolean createFile(File file, boolean isDir) {
        try {
            if (isDir) {
                if (!file.exists()) {
                    return file.mkdirs();
                }
            } else {
                File parent = file.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                return file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 保存文本到文件中
     *
     * @param target 用于存储的文件
     * @param str    要保存的文本
     * @param append 是否附加到已有的文本后 true 附加, false覆盖
     * @author 胡焕
     * @date 2016/11/30 13:54
     */
    public static boolean saveStringToFile(File target, String str, boolean append) {
        boolean result = false;
        FileOutputStream fileOutputStream = null;
        try {
            createFile(target, false);
            fileOutputStream = new FileOutputStream(target, append);
            byte[] bytes = str.getBytes("utf-8");
            fileOutputStream.write(bytes);
            result = true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            result = false;
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            close(fileOutputStream);
        }

        return result;

    }

    /**
     * 保存流到文件
     *
     * @author 胡焕
     * @date 2016/11/30 11:55
     */
    public static boolean saveFile(File target, InputStream inputStream) {
        boolean result = false;
        int len = 0;
        byte[] buffer = new byte[bufferSize];
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(target);
            while ((len = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, len);
            }
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fileOutputStream, inputStream);
        }

        return result;
    }

    /**
     * 通过通道的方式复制文件
     *
     * @author 胡焕
     * @date 2016/11/30 09:38
     */
    public static boolean fileChannelCopy(File source, File target) {
        boolean result = false;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            FileChannel inputChannel = fileInputStream.getChannel();
            FileChannel outputChannel = fileOutputStream.getChannel();
            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            close(fileInputStream, fileOutputStream);
        }
        return result;
    }

    /**
     * 删除目录及目录下的所有文件.包括子目录及子目录下所有文件
     *
     * @author 胡焕
     * @date 2016/11/30 10:11
     */
    public static boolean delete(File dir) {
        if (dir.isDirectory()) {
            final File[] files = dir.listFiles();
            if (files != null) {
                for (final File child : files) {
                    delete(child);
                }
            }
        }
        return dir.delete();

    }

    /**
     * 根据文件路径返回文件名
     *
     * @author 胡焕
     * @date 2016/11/30 10:49
     */
    public static String getFileName(String filePath) {
        int index = filePath.lastIndexOf(File.separatorChar);
        if (index > 0) {
            return filePath.substring(index + 1);
        }
        return filePath;
    }

    /**
     * 获取文件后缀
     *
     * @author 胡焕
     * @date 2016/11/30 10:49
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String extension = null;
        final int lastDot = fileName.lastIndexOf('.');
        if ((lastDot >= 0)) {
            extension = fileName.substring(lastDot + 1).toLowerCase();
        }
        return extension;
    }

    /**
     * 计算文件大小,如果是目录的话,则计算所有文件的大小和
     *
     * @author 胡焕
     * @date 2016/11/30 14:06
     */
    public static long getFileSize(File file) {
        long size = 0l;
        if (!file.exists())
            throw new NullPointerException("文件不存在!" + file.getAbsolutePath());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                size += getFileSize(f);
            }
        } else
            size += file.length();
        return size;
    }

    /**
     * 将文件的长度转换为KMG的形式方便查看.
     *
     * @author 胡焕
     * @date 2016/11/30 09:59
     */
    public static String formatFileSizeToString(long fileLength) {
        DecimalFormat format = new DecimalFormat("#.00");
        String fileSizeStr = "";
        if (fileLength < 1024)
            fileSizeStr = format.format((double) fileLength) + "B";
        else if (fileLength < 1024 * 1024)
            fileSizeStr = format.format((double) fileLength / 1024) + "K";
        else if (fileLength < 1024 * 1024 * 1024)
            fileSizeStr = format.format((double) fileLength / (1024 * 1024)) + "M";
        else if (fileLength < 1024 * 1024 * 1024 * 1024)
            fileSizeStr = format.format((double) fileLength / (1024 * 1024 * 1024)) + "G";
        return fileSizeStr;
    }

    public static void close(Closeable... closeable) {
        try {
            for (Closeable cb : closeable)
                if (cb != null)
                    cb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean exists(File file) {
        boolean result = false;
        try {
            result = file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void unzip(String source, String target, String rename) {
        ZipInputStream zipInputStream = null;
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(source));
            ZipFile zipFile = new ZipFile(source);
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                unzip(zipFile, zipEntry, target, rename);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(zipInputStream);
        }
    }

    public static void unzip(ZipFile zipFile, ZipEntry zipEntry, String target, String rename) {
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            String name = zipEntry.getName();
            File file;
            if (TextUtils.isEmpty(rename)) {
                file = new File(target, rename + name.substring(name.indexOf("/")));
            } else {
                file = new File(target, name);
            }
            if (FileUtil.exists(file)) {
                FileUtil.delete(file);
            }
            if (file.isFile()) {
                file.createNewFile();
            }
            createFile(file, zipEntry.isDirectory());
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            byte[] buffer = new byte[1024];
            int length;
            bufferedInputStream = new BufferedInputStream(zipFile.getInputStream(zipEntry));
            while ((length = bufferedInputStream.read(buffer)) > 0) {
                bufferedOutputStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(bufferedOutputStream);
            close(bufferedInputStream);
        }
    }
}
