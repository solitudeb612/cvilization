package cn.swust.indigo.admin.service;

import cn.swust.indigo.admin.entity.po.SysFile;
import cn.swust.indigo.common.core.util.R;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 文件管理
 */
public interface SysFileService extends IService<SysFile> {

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    R uploadFile(MultipartFile file);

    /**
     * 读取文件
     *
     * @param fileName
     * @param response
     */
    void getFile(String fileName, HttpServletResponse response);

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    Boolean deleteFile(String fileName);

    /*
        获取图片url
        * @param imgName 桶名称-文件名称
         * @param response
         * @return 图片url
         */
    String getImg(String imgName);


    /**
     * 分页查询所有文件按照id逆序排序
     *
     * @param page    分页对象
     * @param sysFile 参数列表
     * @return
     */
    IPage getPageOrderById(Page page, SysFile sysFile);

    void downloadStarFiles(List<SysFile> sysFileList, HttpServletResponse httpServletResponse) throws IOException;
}
