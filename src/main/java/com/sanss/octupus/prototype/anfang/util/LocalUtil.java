package com.sanss.octupus.prototype.anfang.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Utility class of some static methods.
 * Created by simon on 2014/12/1.
 */
public class LocalUtil {

    /** Used to close Closeable instance, such as InputStream, OutputStream */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
//                e.printStackTrace();
                // just ignore
            }
        }
    }
}
