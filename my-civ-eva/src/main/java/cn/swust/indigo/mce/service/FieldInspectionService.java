package cn.swust.indigo.mce.service;

import cn.swust.indigo.mce.entity.vo.QFieldInspection;
import cn.swust.indigo.mce.entity.vo.ReviewVo;

public interface FieldInspectionService {
    ReviewVo getFieldInspection(QFieldInspection qFieldInspection);
}
