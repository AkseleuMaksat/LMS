package kz.bitlab.lms.service;

import kz.bitlab.lms.dto.AttachmentRs;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    AttachmentRs uploadAttachment(MultipartFile file, Long lessonId);
    List<AttachmentRs> getAttachments();
    ByteArrayResource download(String fileName);
}
