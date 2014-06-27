package com.isoftframework.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public final class GZipUtil {

    /**
     *  Do a gzip operation.
     * @param data 未压缩前的数据
     * @return 压缩后的数据
     */
    public static byte[] gzip(byte[] data) {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(10240);
        GZIPOutputStream output = null;
        try {
            output = new GZIPOutputStream(byteOutput);
            //压缩输出到ByteArrayOutputStream
            output.write(data);
        }
        catch (IOException e) {}
        finally {
            if(output!=null) {
                try {
                    output.close();
                }
                catch (IOException e) {}
            }
        }
        return byteOutput.toByteArray();
    }

}
