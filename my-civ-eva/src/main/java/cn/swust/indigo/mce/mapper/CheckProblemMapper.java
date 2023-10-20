package cn.swust.indigo.mce.mapper;

import cn.swust.indigo.mce.entity.po.CheckProblem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * (CheckProblem)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-25 16:07:39
 */
@Mapper
public interface CheckProblemMapper extends BaseMapper<CheckProblem> {

    void updateByList(@Param("checkProblems")  List<CheckProblem> checkProblems);

    IPage<CheckProblem> getCheckProblemPage(@Param("page") Page<CheckProblem> page, @Param("address")String address, @Param("name")String name, @Param("type")Integer type);

    CheckProblem allInfo (@Param("id")Integer id);
}
