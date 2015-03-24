package org.rural.test;

import org.rural.utils.StringUtil;

/**
 * Created by yuantao on 2014/8/9.
 */
public class StringUtilTest {
    public static void main(String[] args) {
        for (String s : ".d.d.".split("\\.", -1)) {
            System.out.println("--" + s);
        }
    }
}
