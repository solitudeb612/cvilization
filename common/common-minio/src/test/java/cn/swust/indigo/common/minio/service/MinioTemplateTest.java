package cn.swust.indigo.common.minio.service;

import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import org.junit.jupiter.api.Test;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

class MinioTemplateTest {

    @Test
    void getAllBuckets() {
//        minio.url=http://47.92.112.6:9000
//        minio.accessKey=smlminio
//        minio.secretKey=ZXCasd8102

        try {
            MinioClient client = new MinioClient("http://47.92.112.6:9000", "smlminio", "ZXCasd8102");

            try {
                List<Bucket> list = client.listBuckets();
                System.out.printf(list.toString());
            } catch (InvalidBucketNameException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InsufficientDataException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoResponseException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (ErrorResponseException e) {
                e.printStackTrace();
            } catch (InternalException e) {
                e.printStackTrace();
            }

        } catch (InvalidEndpointException e) {
            e.printStackTrace();
        } catch (InvalidPortException e) {
            e.printStackTrace();
        }
    }
}