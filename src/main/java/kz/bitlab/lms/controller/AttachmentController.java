package kz.bitlab.lms.controller;

import kz.bitlab.lms.dto.AttachmentRs;
import kz.bitlab.lms.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping(value = "/upload/{lessonId}")
    public ResponseEntity<AttachmentRs> upload(@RequestParam("file") MultipartFile file, @PathVariable Long lessonId) {
        AttachmentRs saved = attachmentService.uploadAttachment(file, lessonId);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping("/download/{file}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable(name = "file") String fileName) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(attachmentService.download(fileName));
    }

    @GetMapping("/list")
    public ResponseEntity<List<AttachmentRs>> listFiles() {
        return ResponseEntity.ok(attachmentService.getAttachments());
    }

}
