package me.cuiyijie.nongmo.service;

import ch.qos.logback.core.util.CloseUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import me.cuiyijie.nongmo.config.MinioConfig;
import me.cuiyijie.nongmo.dao.FileUploadDao;
import me.cuiyijie.nongmo.entity.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class FileUploadService extends ServiceImpl<FileUploadDao, FileUpload> {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfig minioConfig;

    public FileUpload uploadFile(String fileMd5, MultipartFile multipartFile) {
        try {
            return uploadFile(fileMd5, multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        } catch (Exception exception) {

        }
        return null;
    }


    /**
     * @param originFileMd5  文件的md5
     * @param originFileName 文件原始名称（用来记录以及判断文件的contentType）
     * @param inputStream    上传文件流
     * @return
     */
    public FileUpload uploadFile(String originFileMd5, String originFileName, InputStream inputStream) {
        try {
            FileUpload existFile = this.baseMapper.selectByMd5(originFileMd5);
            if (null != existFile) {
                return existFile;
            } else {
                String bucketName = minioConfig.getBucketName();
                //判断bucket是否存在
                boolean bucketExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
                if (!bucketExist) {
                    MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build();
                    minioClient.makeBucket(makeBucketArgs);
                    log.info("create bucket【{}】 success!", bucketName);
                }
                String originFileSuffix = FileUtil.getSuffix(originFileName);
                String contentType = MediaTypeFactory.getMediaType(originFileName).orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
                String fileName = String.format("%s/%s.%s", new SimpleDateFormat("yyyy/MM/dd").format(new Date()), UUID.randomUUID().toString(), originFileSuffix);
                PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                        .bucket(bucketName)
                        .contentType(contentType)
                        .object(fileName)
                        .stream(inputStream, inputStream.available(), PutObjectBaseArgs.MIN_MULTIPART_SIZE)
                        .build();
                ObjectWriteResponse objectWriteResponse = minioClient.putObject(putObjectArgs);

                FileUpload fileUpload = FileUpload
                        .builder()
                        .originFileName(originFileName)
                        .fileMd5(objectWriteResponse.etag())
                        .objectName(String.format("/%s/%s", minioConfig.getBucketName(), fileName))
                        .build();
                this.baseMapper.insert(fileUpload);
                return fileUpload;
            }
        } catch (Exception exception) {
            log.error("文件上传失败：", exception);
            throw new RuntimeException("文件上传失败：" + exception.getMessage());
        } finally {
            CloseUtil.closeQuietly(inputStream);
        }
    }
}
