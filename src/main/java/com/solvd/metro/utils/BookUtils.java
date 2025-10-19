package com.solvd.metro.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class BookUtils {

    private static final Logger logger = LogManager.getLogger(BookUtils.class);

    public List<String> readTheBook(String filePath) {
        List<String> bookText;
        try {
           bookText = FileUtils.readLines(
                    new File(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bookText;
    }

    public Set<String> getUniqueWords(List<String> bookText) {

        return bookText.stream()
                .flatMap(line -> Arrays.stream(line.split("\\W+")))
                .map(String::toLowerCase)
                .filter(word -> !StringUtils.isBlank(word))
                .filter(StringUtils::isAlpha)
                .filter(word -> StringUtils.length(word) > 1)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public void writeInFile(Set<String> words, String filePath) {
        logger.info(words.size());
        try {
            FileUtils.writeLines(new File(filePath), words);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
