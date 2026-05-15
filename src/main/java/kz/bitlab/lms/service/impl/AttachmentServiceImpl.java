package kz.bitlab.lms.service.impl;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import kz.bitlab.lms.dto.AttachmentRs;
import kz.bitlab.lms.exception.LmsException;
import kz.bitlab.lms.exception.enums.ExceptionStatus;
import kz.bitlab.lms.mapper.AttachmentMapper;
import kz.bitlab.lms.model.Attachment;
import kz.bitlab.lms.model.Lesson;
import kz.bitlab.lms.repository.AttachmentRepository;
import kz.bitlab.lms.repository.LessonRepository;
import kz.bitlab.lms.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentMapper attachmentMapper;
    private final AttachmentRepository attachmentRepository;
    private final LessonRepository lessonRepository;
    private final MinioClient minioClient;

    @Value("${minio.bucket:lms-files}")
    private String bucket;

    @Override
    @Transactional
    public AttachmentRs uploadAttachment(MultipartFile file, Long lessonId) {
        log.info("Starting upload for file: {} to lesson ID: {}", file.getOriginalFilename(), lessonId);

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> {
                    log.error("Lesson with ID {} not found", lessonId);
                    return new LmsException("Lesson not found", ExceptionStatus.LESSON_NOT_FOUND);
                });

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = StringUtils.getFilenameExtension(originalFilename);
            String hashedFileName = UUID.randomUUID().toString();

            if (StringUtils.hasText(extension)) {
                hashedFileName = hashedFileName + "." + extension;
            }

            log.debug("Generated hashed file name: {}", hashedFileName);

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(hashedFileName)
                            .stream(file.getInputStream(), file.getSize(), -1L)
                            .contentType(file.getContentType())
                            .build()
            );

            Attachment attachment = new Attachment();
            attachment.setName(originalFilename);
            attachment.setUrl(hashedFileName);
            attachment.setLesson(lesson);

            Attachment savedAttachment = attachmentRepository.save(attachment);

            log.info("Successfully uploaded and saved attachment ID: {}", savedAttachment.getId());
            return attachmentMapper.toDto(savedAttachment);

        } catch (Exception e) {
            log.error("Failed to upload file to MinIO: {}", e.getMessage(), e);
            throw new LmsException("Error uploading file: " + e.getMessage(), ExceptionStatus.FILE_UPLOAD_FAILED);
        }
    }

    @Override
    public ByteArrayResource download(String fileName) {
        log.info("Downloading file from MinIO: {}", fileName);
        try (InputStream inputStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .build())) {

            byte[] fileBytes = IOUtils.toByteArray(inputStream);
            return new ByteArrayResource(fileBytes);

        } catch (Exception e) {
            log.error("Failed to download file {} from MinIO: {}", fileName, e.getMessage(), e);
            throw new LmsException("Error downloading file: " + e.getMessage(), ExceptionStatus.FILE_DOWNLOAD_FAILED);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttachmentRs> getAttachments() {
        log.info("Fetching all attachments");
        return attachmentMapper.toDtos(attachmentRepository.findAll());
    }
}
