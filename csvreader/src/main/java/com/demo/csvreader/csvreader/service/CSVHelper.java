package com.demo.csvreader.csvreader.service;

import com.demo.csvreader.csvreader.entity.FileEntity;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";

    static String[] HEADERs = { "Id", "Title", "Description", "Published" };

    public static boolean hasCSVFormat(MultipartFile file) {
        if (TYPE.equals(file.getContentType())
                || file.getContentType().equals("application/vnd.ms-excel")) {
            return true;
        }

        return false;
    }

    public static List<FileEntity> csvToData(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<FileEntity> fileEntityList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                FileEntity fileEntity = new FileEntity(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("Title"),
                        csvRecord.get("Description"),
                        Boolean.parseBoolean(csvRecord.get("Published"))
                );

                fileEntityList.add(fileEntity);
            }

            return fileEntityList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream dataToCsv(List<FileEntity> fileEntityList) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (FileEntity fileEntity : fileEntityList) {
                List<String> data = Arrays.asList(
                        String.valueOf(fileEntity.getId()),
                        fileEntity.getTitle(),
                        fileEntity.getDescription(),
                        String.valueOf(fileEntity.isPublished())
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
