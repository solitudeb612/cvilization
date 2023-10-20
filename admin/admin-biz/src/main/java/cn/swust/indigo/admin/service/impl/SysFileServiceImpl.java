package cn.swust.indigo.admin.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.http.ContentType;
import cn.swust.indigo.admin.entity.po.SysFile;
import cn.swust.indigo.admin.entity.vo.ImgInfo;
import cn.swust.indigo.admin.mapper.SysFileMapper;
import cn.swust.indigo.admin.service.SysFileService;
import cn.swust.indigo.common.core.constants.CommonConstants;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.minio.service.MinioTemplate;
import cn.swust.indigo.common.minio.vo.MinioObject;
import cn.swust.indigo.common.security.util.SecurityUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.api.client.util.IOUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.tags.form.InputTag;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件管理
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    private final MinioTemplate minioTemplate;

    private SysFileMapper sysFileMapper;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Override
    public R uploadFile(MultipartFile file) {
        String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
        Map<String, String> resultMap = new HashMap<>(4);
        resultMap.put("bucketName", CommonConstants.BUCKET_NAME);
        resultMap.put("fileName", fileName);
        SysFile sysFile = new SysFile();
        try {
            minioTemplate.putObject(CommonConstants.BUCKET_NAME, fileName, file.getInputStream());
            //文件管理数据记录,收集管理追踪文件
            sysFile = fileLog(file, fileName);
        } catch (Exception e) {
            log.error("上传失败", e);
            return R.failed(e.getLocalizedMessage());
        }
//        Map<Map<String,String>, String> ImgMap = new HashMap<>();
        String type = FileUtil.extName(file.getOriginalFilename());
        if (type.equals("jpg") || type.equals("jpeg") || type.equals("png") || type.equals("gif")) {
            String objectURL = minioTemplate.getObjectURL(CommonConstants.BUCKET_NAME, fileName, 7200);
//            ImgMap.put(resultMap, objectURL);
            ImgInfo imgInfo = new ImgInfo();
            BeanUtils.copyProperties(sysFile, imgInfo);
            imgInfo.setObjectURL(objectURL);
//            ImgInfo imgInfo = new ImgInfo(resultMap.get("bucketName"), resultMap.get("fileName"), objectURL);
            return R.ok(imgInfo);
        }

        return R.ok(sysFile);
    }

    /**
     * 读取文件
     *
     * @param fileName
     * @param response
     */
    @Override
    public void getFile(String fileName, HttpServletResponse response) {
        try (InputStream inputStream = minioTemplate.getObject(CommonConstants.BUCKET_NAME, fileName)) {
            response.setContentType("application/octet-stream; charset=UTF-8");
            SysFile fileByName = sysFileMapper.getFileByName(fileName, CommonConstants.BUCKET_NAME);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileByName.getOriginal());
            IOUtils.copy(inputStream, response.getOutputStream());
            inputStream.close();
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteFile(String fileName) {
        minioTemplate.removeObject(CommonConstants.BUCKET_NAME, fileName);
        return sysFileMapper.deleteByFileName(fileName, CommonConstants.BUCKET_NAME);
    }

    /**
     * 文件管理数据记录,收集管理追踪文件
     *
     * @param file     上传文件格式
     * @param fileName 文件名
     */
    private SysFile fileLog(MultipartFile file, String fileName) {
        SysFile sysFile = new SysFile();
        //原文件名
        String original = file.getOriginalFilename();
        sysFile.setFileName(fileName);
        sysFile.setOriginal(original);
        sysFile.setFileSize(file.getSize());
        sysFile.setType(FileUtil.extName(original));
        sysFile.setBucketName(CommonConstants.BUCKET_NAME);
//		sysFile.setCreateUser(SecurityUtils.getUser().getUsername());
        this.save(sysFile);
        return sysFile;
    }

    /*
        获取图片url
        * @param imgName 桶名称-文件名称
         * @param response
         * @return 图片url
         */
    @Override
    public String getImg(String imgName) {
        int separator = imgName.lastIndexOf(StrUtil.DASHED);
        try {
//            String objectURL = minioTemplate.getObjectURL(imgName.substring(0, separator),
//                    imgName.substring(separator + 1), 3600);
            String objectURL = minioTemplate.getObjectURL(CommonConstants.BUCKET_NAME, imgName, 3600);
            System.out.println(objectURL);
            return objectURL;

        } catch (Exception e) {
            log.error("文件读取异常", e);
            return null;
        }
    }

    @Override
    public IPage getPageOrderById(Page page, SysFile sysFile) {
        return sysFileMapper.getFilePages(page, sysFile);
    }

    @Override
    public void downloadStarFiles(List<SysFile> sysFileList, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("application/octet-stream; charset=UTF-8");
        httpServletResponse.setHeader("Content-Disposition","attachment;filename=" + "StarFiles.zip");
        ZipOutputStream zipOutputStream = new ZipOutputStream(httpServletResponse.getOutputStream());
        InputStream inputStream = null;
        for (SysFile sysFile : sysFileList) {
            inputStream = minioTemplate.getObject(CommonConstants.BUCKET_NAME, sysFile.getFileName());
            // 使用名字不重名
            String name = sysFile.getOriginal().substring(0,sysFile.getOriginal().lastIndexOf(".")) +
                    sysFile.getCreateTime() + sysFile.getOriginal().substring(sysFile.getOriginal().lastIndexOf("."));
            ZipEntry zipEntry = new ZipEntry(name);
            zipOutputStream.putNextEntry(zipEntry);
            int len = -1;
            byte[] bt= new byte[1024];
            while((len = inputStream.read(bt)) > -1){
                zipOutputStream.write(bt, 0, len);
            }
            inputStream.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        zipOutputStream.closeEntry();
        zipOutputStream.close();
    }

    @Override
    public ImgInfo getById(Serializable id) {
        SysFile sysFile = super.getById(id);
        ImgInfo imgInfo = new ImgInfo();
        BeanUtils.copyProperties(sysFile, imgInfo);
//        String type = FileUtil.extName(sysFile.getOriginal());
//        if(type.equals("jpg") || type.equals("jpeg") || type.equals("png") || type.equals("gif")){
//            String objectURL = minioTemplate.getObjectURL(CommonConstants.BUCKET_NAME, sysFile.getFileName(), 7200);
////            ImgMap.put(resultMap, objectURL);
//            imgInfo.setObjectURL(objectURL);
////            ImgInfo imgInfo = new ImgInfo(resultMap.get("bucketName"), resultMap.get("fileName"), objectURL);
//        }
        String objectURL = minioTemplate.getObjectURL(CommonConstants.BUCKET_NAME, sysFile.getFileName(), 7200);
        imgInfo.setObjectURL(objectURL);
        return imgInfo;
    }
}
