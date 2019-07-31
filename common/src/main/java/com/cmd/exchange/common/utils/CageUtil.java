package com.cmd.exchange.common.utils;

import com.github.cage.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

@SuppressWarnings("ALL")
public class CageUtil {
    private static final GCage _G = new GCage();
    private static final YCage _Y = new YCage();

    private static final Cage __G = new Cage(_G.getPainter(),
            (IGenerator) (new ObjectRoulette(new Random(), new Font[]{new Font("sans serif", 0, _G.getPainter().getHeight() / 2), new Font("serif", 0, _G.getPainter().getHeight()), new Font("monospace", 1, _G.getPainter().getHeight())})),
            _G.getForegrounds(),
            "jpeg",
            _G.getCompressRatio(),
            _G.getTokenGenerator(),
            new Random()
    );

    private static final char[] wordsNumber = "1234567890".toCharArray();
    private static final char[] words = "23456789abcdefghijkmnpqrstuvwxyz".toCharArray();
    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {
        generate(new GCage(), 10, "cg1", ".jpg", "colding");
        generate(new YCage(), 10, "cy1", ".jpg", "eT6wLAH");
        generate(new GCage(), 100, "cg2", ".jpg", null);
        generate(new YCage(), 100, "cy2", ".jpg", null);
    }

    public static void generate(Cage cage, int num, String namePrefix, String namePostfix, String text) throws IOException {
        for (int fi = 0; fi < num; fi++) {
            OutputStream os = new FileOutputStream(namePrefix + fi + namePostfix, false);
            try {
                cage.draw(text != null ? text : cage.getTokenGenerator().next(), os);
            } finally {
                os.close();
            }
        }
    }

    public static ResponseEntity<byte[]> generate(String type, String text) {
        Cage cage = "G".equals(type) ? __G : _Y;

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "image/" + cage.getFormat());
        headers.set(HttpHeaders.CACHE_CONTROL, "no-cache, no-store");
        headers.set(HttpHeaders.PRAGMA, "no-cache");

        byte[] data = cage.draw(text != null ? text : cage.getTokenGenerator().next());

        System.out.print("data==" + data);
        return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
    }

    public static String getWords(int length) {
        if (length < 4) {
            length = 4;
        }
        if (length > 8) {
            length = 8;
        }

        int i = 0;
        StringBuilder result = new StringBuilder();

        do {
            result.append(words[random.nextInt(words.length)]);
        } while (++i < length);

        return result.toString();
    }

    public static String getWordsNumber(int length) {
        if (length < 4) {
            length = 4;
        }
        if (length > 8) {
            length = 8;
        }

        int i = 0;
        StringBuilder result = new StringBuilder();

        do {
            result.append(wordsNumber[random.nextInt(wordsNumber.length)]);
        } while (++i < length);

        return result.toString();
    }
}
