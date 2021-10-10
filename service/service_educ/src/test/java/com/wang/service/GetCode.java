package com.wang.service;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.wang.edu.EduApplication;
import com.wang.edu.bean.Teacher;
import com.wang.edu.service.TeacherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
@SpringBootTest(classes = EduApplication.class)
@RunWith(SpringRunner.class)

public class GetCode {
    @Test
    public void main1() {
// 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();
// 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        gc.setOutputDir("D:\\IDEA\\guli_parent\\service\\service_educ" + "/src/main/java");
        gc.setAuthor("Mr.wang");
        gc.setOpen(false); //生成后是否打开资源管理器
        gc.setFileOverride(false); //重新生成时文件是否覆盖
        /*
         * mp生成service层代码，默认接口名称第一个字母有 I
         * UcenterService
         * */
        gc.setServiceName("%sService"); //去掉Service接口的首字母I
        gc.setIdType(IdType.ID_WORKER); //主键策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        gc.setSwagger2(true);//开启Swagger2模式
        mpg.setGlobalConfig(gc);
// 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/guli?serverTimezone=UTC");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("password");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);
// 4、包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("edu"); //模块名
        pc.setParent("com.wang");
        pc.setController("controller");
        pc.setEntity("bean");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);
// 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("edu_chapter","edu_course","edu_course_description","edu_video");
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的

        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段
        //映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain =true) setter链式操作
        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符
        mpg.setStrategy(strategy);
// 6、执行
        mpg.execute();
    }

    @Autowired
    private TeacherService teacherService;

    @Test
    public void test(){
        Teacher teacher=new Teacher();
        teacher.setId("12");
        System.out.println(teacher);
        for (int i=1;i<10;i++) {
            teacherService.save(new Teacher(i+"2", "王二"+i, "", "高级讲师", 1, "https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg", 0, 0, new Date(), new Date()));
            //teacherMapper.insert(teacher);
        }

    }
}

