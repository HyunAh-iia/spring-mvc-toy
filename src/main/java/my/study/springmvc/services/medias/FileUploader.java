package my.study.springmvc.services.medias;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class FileUploader implements Uploader {
    private static final String UPLOADED_PATH = "https://ko.wikipedia.org/wiki/%EC%9C%84%ED%82%A4%EB%B0%B1%EA%B3%BC:%EB%8C%80%EB%AC%B8";

    @Override
    public String upload(MultipartFile file) {
        return UPLOADED_PATH;
    }

    @Override
    public List<String> upload(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return null;
        }
        return files.stream().map(file -> UPLOADED_PATH).toList();
    }
}
