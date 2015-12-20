package com.landenlabs.encrypnotes;

/*
 * (c) 2009.-2010. Ivan Voras <ivoras@fer.hr>
 * Released under the 2-clause BSDL.
 */

import com.landenlabs.encrypnotes.ui.LogIt;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

/**
 * @author ivoras
 */
public class Util {

    /**
     * @return  Current date.
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }
    
    /**
     * @param filename
     * @return {@code true} if file exists.
     */
    public static boolean fileExists(String filename) {
        return new File(filename).exists();
    }

    
    /**
     * Returns a binary MD5 hash of the given string.
     *
     * @param s
     * @return
     */
    public static byte[] md5hash(String s) {
        return hash(s, "MD5");
    }


    /**
     * Returns a binary MD5 hash of the given binary buffer.
     *
     * @param buf
     * @return
     */
    public static byte[] md5hash(byte[] buf) {
        return hash(buf, "MD5");
    }


    /**
     * Returns a binary SHA1 hash of the given string.
     *
     * @param s
     * @return
     */
    public static byte[] sha1hash(String s) {
        return hash(s, "SHA1");
    }


    /**
     * Returns a binary SHA1 hash of the given buffer.
     *
     * @param buf
     * @return
     */
    public static byte[] sha1hash(byte[] buf) {
        return hash(buf, "SHA1");
    }


    /**
     * Returns a binary hash calculated with the specified algorithm of the
     * given string.
     *
     * @param s
     * @param hashAlg
     * @return
     */
    public static byte[] hash(String s, String hashAlg) {
        byte b[] = null;
        try {
            b = s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            LogIt.log(Util.class, LogIt.ERROR, null, ex);
            System.exit(1);
        }
        return hash(b, hashAlg);
    }


    /**
     * Converts a binary buffer to a string of lowercase hexadecimal characters.
     *
     * @param h
     * @return
     */
    public static String bytea2hex(byte[] h) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < h.length; i++)
            sb.append(String.format("%02x", h[i] & 0xff));
        return sb.toString();
    }


    /**
     * Returns a binary hash calculated with the specified algorithm of the
     * given input buffer.
     *
     * @param buf
     * @param hashAlg
     * @return
     */
    public static byte[] hash(byte[] buf, String hashAlg) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(hashAlg);
        } catch (NoSuchAlgorithmException ex) {
            LogIt.log(Util.class, LogIt.ERROR, null, ex);
            System.exit(1);
        }
        return md.digest(buf);
    }


    /**
     * Concatenates two byte arrays and returns the result.
     *
     * @param src1
     * @param src2
     * @return
     */
    public static byte[] concat(byte[] src1, byte[] src2) {
        byte[] dst = new byte[src1.length + src2.length];
        System.arraycopy(src1, 0, dst, 0, src1.length);
        System.arraycopy(src2, 0, dst, src1.length, src2.length);
        return dst;
    }
}
