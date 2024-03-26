package ru.netology.diploma.customMultipart;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Getter
public class CustomMultipart implements MultipartFile {

    private final byte[] fileContent;

    private final String fileName;

    private String contentType;

    private FileOutputStream fileOutputStream;

    public CustomMultipart(byte[] fileData, String name) {
        this.fileContent = fileData;
        this.fileName = name;
        file = new File(fileName);
    }




    private File file;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return fileContent ==null || fileContent.length == 0;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return fileContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(fileContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        fileOutputStream = new FileOutputStream(dest);
        fileOutputStream.write(fileContent);
    }

    public void clearOutStreams() throws IOException {
        if (null != fileOutputStream) {
            fileOutputStream.flush();
            fileOutputStream.close();
            file.deleteOnExit();
        }
    }
}
