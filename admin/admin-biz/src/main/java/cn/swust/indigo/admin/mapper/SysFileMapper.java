package cn.swust.indigo.admin.mapper;

import cn.swust.indigo.admin.entity.po.SysFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@Mapper
public interface SysFileMapper extends BaseMapper<SysFile> {

    /**
     * 分页查询
     *
     * @param page    分页
     * @param sysFile 查询参数
     * @return list
     */
    IPage<List<SysFile>> getFilePages(Page page, @Param("query") SysFile sysFile);


    /**
     * 根据 文件名 桶名 得到文件
     *
     * @param fileName   文件名
     * @param bucketName 桶名
     * @return
     */
    SysFile getFileByName(@Param("fileName") String fileName, @Param("bucketName") String bucketName);

    Boolean deleteByFileName(@Param("fileName") String fileName, @Param("bucketName") String bucketName);
}
