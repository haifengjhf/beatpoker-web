package com.jhf.beatpoker.web.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 对称加密算法，加解密工具类
 */
public class AesEncryptUtil {
    private static final String TAG =AesEncryptUtil.class.getSimpleName();

    /**
     * 加密算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * AES 的 密钥长度，32 字节，范围：16 - 32 字节
     */
    public static final int SECRET_KEY_LENGTH = 16;//32*8= 256

    /**
     * 字符编码
     */
    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /**
     * 秘钥长度不足 16 个字节时，默认填充位数
     * 秘钥长度不要求，可以填充可以不填充
     */
    private static final String DEFAULT_VALUE = "0";
    /**
     * 加解密算法/工作模式/填充方式
     * 默认是128位
     */
    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";

    /**
     * 因为AES/CBC/PKCS5Padding是128，IV必须和密文分块长度一直，因此填充16个字节
     */
    private static final byte[] DEFAULT_IV = {0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f};

    public static final byte[] DEFAULT_KEY = {0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f,0x00};
    /**
     * AES 加密
     *
     * @param data      待加密内容
     * @param key 加密密码，长度：16
     * @return 返回Base64转码后的加密数据
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        try {
            Cipher cipher = initAES256CBCCipher(Cipher.ENCRYPT_MODE,CIPHER_TRANSFORMATION,KEY_ALGORITHM,key,DEFAULT_IV);
            return cipher.doFinal(data);
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    /**
     * AES 解密
     *
     * @param data       加密的密文
     * @param key        解密的密钥，长度16
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        try {
            Cipher cipher = initAES256CBCCipher(Cipher.DECRYPT_MODE,CIPHER_TRANSFORMATION,KEY_ALGORITHM,key,DEFAULT_IV);
            return cipher.doFinal(data);
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    /**
     * 处理异常
     */
    private static void handleException(Exception e) {
        e.printStackTrace();
    }

    /**
    * 对文件进行AES加密
    *
    * @param sourceFile 待加密文件
    * @param dir        加密后的文件存储路径
    * @param toFileName 加密后的文件名称
    * @param key        密钥
    * @return 加密后的文件
    */
    public static File encryptFile(File sourceFile, String dir, String toFileName, byte[] key) {
        try {
            // 创建加密后的文件
            File encryptFile = new File(dir, toFileName);
            // 根据文件创建输出流
            FileOutputStream outputStream = new FileOutputStream(encryptFile);
            // 初始化 Cipher
            Cipher cipher = initAES256CBCCipher(Cipher.ENCRYPT_MODE,CIPHER_TRANSFORMATION,KEY_ALGORITHM,key, DEFAULT_IV);
            // 以加密流写入文件
            CipherInputStream cipherInputStream = new CipherInputStream(
                    new FileInputStream(sourceFile), cipher);
            // 创建缓存字节数组
            byte[] buffer = new byte[1024 * 2];
            // 读取
            int len;
            // 读取加密并写入文件
            while ((len = cipherInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                outputStream.flush();
            }
            // 关闭加密输入流
            cipherInputStream.close();
            closeStream(outputStream);
            return encryptFile;
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }


    /**
    * AES解密文件
    *
    * @param sourceFile 源加密文件
    * @param dir        解密后的文件存储路径
    * @param toFileName 解密后的文件名称
    * @param key        密钥
    */
    public static File decryptFile(File sourceFile, String dir, String toFileName, byte[] key) {
        try {
            // 创建解密文件
            File decryptFile = new File(dir, toFileName);
            // 初始化Cipher
            Cipher cipher = initAES256CBCCipher(Cipher.DECRYPT_MODE,CIPHER_TRANSFORMATION,KEY_ALGORITHM,key, DEFAULT_IV);
            // 根据源文件创建输入流
            FileInputStream inputStream = new FileInputStream(sourceFile);
            // 获取解密输出流
            CipherOutputStream cipherOutputStream = new CipherOutputStream(
                    new FileOutputStream(decryptFile), cipher);
            // 创建缓冲字节数组
            byte[] buffer = new byte[1024 * 2];
            int len;
            // 读取解密并写入
            while ((len = inputStream.read(buffer)) >= 0) {
                cipherOutputStream.write(buffer, 0, len);
                cipherOutputStream.flush();
            }
            // 关闭流
            cipherOutputStream.close();
            closeStream(inputStream);
            return decryptFile;
        } catch (IOException e) {
            handleException(e);
        }
        return null;
    }

    /**
    * 初始化 AES Cipher
    *
    * @param cipherMode 加解密模式
    * @param transformation
    * @return 密钥
    */
    private static Cipher initAES256CBCCipher(int cipherMode,String transformation,String algorithm,byte[] key,byte[] iv) {
        Cipher cipher = null;
        try {
            // 创建密钥规格
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, algorithm);
            // 获取密钥
            cipher = Cipher.getInstance(transformation);
            // 初始化
            cipher.init(cipherMode, secretKeySpec, new IvParameterSpec(iv));
            return cipher;
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    /**
     * 初始化 AES Cipher
     *
     * @param cipherMode 加密模式
     * @return 密钥
     */
    private static Cipher initAES256CBCCipher(int cipherMode) {
        return initAES256CBCCipher(cipherMode,CIPHER_TRANSFORMATION,KEY_ALGORITHM,DEFAULT_KEY,DEFAULT_IV);
    }


    /**
    * 关闭流
    *
    * @param closeable 实现Closeable接口
    */
    private static void closeStream(Closeable closeable) {
        try {
            if (closeable != null) closeable.close();
        } catch (Exception e) {
            handleException(e);
        }
    }


    /**
     * AES解密文件
     *
     * @param inputStream 源数据流
     * @param dir        解密后的文件存储路径
     * @param toFileName 解密后的文件名称
     * @param secretKey  密钥
     */
    public static File decryptStreamToFile(InputStream inputStream, String dir, String toFileName, String secretKey) {
        try {
            // 创建解密文件
            File decryptFile = new File(dir, toFileName);
            // 初始化Cipher
            Cipher cipher = initAES256CBCCipher(Cipher.DECRYPT_MODE);

            // 获取解密输出流
            CipherOutputStream cipherOutputStream = new CipherOutputStream(
                    new FileOutputStream(decryptFile), cipher);
            // 创建缓冲字节数组
            byte[] buffer = new byte[1024 * 2];
            int len;
            // 读取解密并写入
            while ((len = inputStream.read(buffer)) >= 0) {
                cipherOutputStream.write(buffer, 0, len);
                cipherOutputStream.flush();
            }
            // 关闭流
            cipherOutputStream.close();
            closeStream(inputStream);
            return decryptFile;
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    public static ByteArrayOutputStream decryptStreamToStream(InputStream inputStream) {
        CipherOutputStream cipherOutputStream = null;
        try {
            Cipher cipher = initAES256CBCCipher(Cipher.DECRYPT_MODE);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // 获取解密输出流
            cipherOutputStream = new CipherOutputStream(outputStream,cipher);
            // 创建缓冲字节数组
            byte[] buffer = new byte[1024 * 2];
            int len;
            // 读取解密并写入
            while ((len = inputStream.read(buffer)) >= 0) {
                cipherOutputStream.write(buffer, 0, len);
                cipherOutputStream.flush();
            }
            // 关闭流
            closeStream(cipherOutputStream);
            closeStream(inputStream);
            return outputStream;
        } catch (Exception e) {
            handleException(e);
        }
        finally {
            closeStream(inputStream);
            closeStream(cipherOutputStream);
        }
        return null;
    }
}
