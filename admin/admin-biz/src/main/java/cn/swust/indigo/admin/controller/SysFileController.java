package cn.swust.indigo.admin.controller;

import cn.hutool.json.JSONUtil;
import cn.swust.indigo.admin.entity.po.SysFile;
import cn.swust.indigo.admin.entity.vo.QSysDict;
import cn.swust.indigo.admin.entity.vo.QSysFile;
import cn.swust.indigo.admin.service.SysFileService;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.data.mybatis.util.CreateQuery;
import cn.swust.indigo.common.log.annotation.Log;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 文件管理
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin/sys-file")
@Api(value = "sys-file", tags = "文件管理")
public class SysFileController {
    private final SysFileService sysFileService;

    /**
     * 分页查询
     *
     * @param page    分页参数
     * @param sysFile 查询条件
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public R getSysFilePage(Page<SysFile> page, QSysDict sysFile) {
        return R.ok(sysFileService.page(page, CreateQuery.getQueryWrapper(sysFile)));
    }

    /**
     * 根据id查询
     *
     * @param fileId   文件id
     * @return
     */
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @GetMapping("/fileId")
    public R getSysFileById(@RequestParam("fileId") Integer fileId, HttpServletResponse response) {
        response.setContentType("application/octet-stream; charset=UTF-8");
        SysFile sysFile = sysFileService.getById(fileId);
        response.setHeader("Content-Disposition", "attachment;filename=" + sysFile.getOriginal());
        return R.ok(sysFile);
    }

    /**
     * 根据id查询
     *
     * @param fileIds   文件ids
     * @return
     */
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @GetMapping("/fileIds")
    public R getSysFileById(@RequestParam("fileIds") String fileIds) {
        List<SysFile> sysFileList = new ArrayList<SysFile>();
        if(fileIds.equals("null")){
            return R.ok(sysFileList);
        }
        String[] split = fileIds.split(",");
        for(int i=0;i<split.length;i++){
            SysFile sysFile = sysFileService.getById(Integer.parseInt(split[i]));
            sysFileList.add(sysFile);
        }
        return R.ok(sysFileList);
    }

    /**
     * 通过fileName删除文件管理
     *
     * @param fileName fileName
     * @return R
     */
    @ApiOperation(value = "通过fileName删除文件管理", notes = "通过fileName删除文件管理")
    @Log("删除文件管理")
    @DeleteMapping("/del")
//    @PreAuthorize("hasAuthority('sys_file_del')")
    public R removeById(@RequestParam("fileName") String fileName) {
        Boolean flag = sysFileService.deleteFile(fileName);
        return flag? R.ok("操作成功"):R.failed("操作失败");
    }

    /**
     * 上传文件
     * 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
     *
     * @param file 资源
     * @return R(bucketName, filename)
     */
    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    public R upload(@RequestParam("file") MultipartFile file) {
        return sysFileService.uploadFile(file);
    }

    /**
     * 获取文件
     *
     * @param fileName 文件空间/名称
     * @param response
     * @return
     */
    @GetMapping("/get")
    @ApiOperation(value = "获取文件")
    public void fileByName(@RequestParam("fileName") @NotNull(message = "fileName不能为空") String fileName, HttpServletResponse response) {
        sysFileService.getFile(fileName, response);
    }

    /*    获取图片url
     * @param imgName 桶名称-文件名称
     * @param response
     * @return 图片url
     */
    @GetMapping("/getImg")
    @ApiOperation(value = "获取图片url")
    public R imageByName(@RequestParam("imgName") @NotNull(message = "imgName不能为空") String imgName) {
        String url = sysFileService.getImg(imgName);
        if (url == null) {
            return R.failed("文件不存在");
        }
        return R.ok(url);
    }


    /**
     * 分页查询按照id逆序排序
     *
     * @param page    分页对象
     * @param sysFile 文件管理
     * @return
     */
    @ApiOperation(value = "分页并且按逆序查询", notes = "分页查询")
    @GetMapping("/pageBydesc")
    public R getSysFilePageByDesc(Page page, QSysFile sysFile) {
        return R.ok(sysFileService.page(page, (Wrapper<SysFile>) CreateQuery.getQueryWrapper(sysFile).orderByDesc("create_time")));
    }

}
