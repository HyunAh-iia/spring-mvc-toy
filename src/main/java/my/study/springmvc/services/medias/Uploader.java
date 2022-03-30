package my.study.springmvc.services.medias;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Uploader {
    String upload(MultipartFile file);

    List<String> upload(List<MultipartFile> file);
}
