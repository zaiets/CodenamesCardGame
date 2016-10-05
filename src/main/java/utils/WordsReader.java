package utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class WordsReader {

    public static final String SPLIT_BY = " ";

    private WordsReader() {
    }

    public static List<String> read(File file) {
        List<String> data = null;
        String text = null;
        try (RandomAccessFile f = new RandomAccessFile(file, "r")) {
            byte[] bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            text = new String(bytes, Charset.forName("UTF-8"));
            text = text.toUpperCase();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        if (text != null) {
            data = new ArrayList<>();
            data.addAll(Arrays.asList(text.split(SPLIT_BY)));
        }
        return data;
    }

    public static List<String> read (WordContainer container) {
        return Arrays.asList(container.getText().toUpperCase().split(SPLIT_BY));
    }
}
