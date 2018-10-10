package tz.co.nezatech.neighbor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

public class IOUtil {
    public static String convert(InputStream inputStream) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
