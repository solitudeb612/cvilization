package cn.swust.indigo.mce.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MangeLocals {
   List<Department> departments;
   List<Site> sites;
}
