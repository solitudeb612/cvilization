package cn.swust.indigo.common.minio.service;

import cn.swust.indigo.common.minio.MinioTestApplication;
import io.minio.messages.Bucket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = MinioTestApplication.class)
class MinioTemplateTest2 {
    @Autowired
    MinioTemplate template;

    @Test
    void getAllBuckets() {
        List<Bucket> list = template.getAllBuckets();
        System.out.printf(list.toString());
    }
}