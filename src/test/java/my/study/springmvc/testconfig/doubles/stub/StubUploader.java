package my.study.springmvc.testconfig.doubles.stub;

import my.study.springmvc.services.medias.Uploader;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class StubUploader implements Uploader {
    @Override
    public String upload(MultipartFile file) {
        return "http://fake-url";
    }

    @Override
    public List<String> upload(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return null;
        }

        return files.stream().map(file -> "http://fake-url").toList();
    }
}
