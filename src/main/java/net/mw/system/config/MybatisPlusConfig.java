package net.mw.system.config;


import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@MapperScan("net.mw.*.dao") // 设置mapper接口的扫描包，会自动生成实例
public class MybatisPlusConfig {
    /**
     * 配置mybatis拦截器(插件)
     */
/*    @Bean
    public MyInterceptor myInterceptor() {
        return new MyInterceptor();
    }*/

    /**
     * 配置乐观锁：当更新一条记录时，希望这条记录没有被别人更新
     *  实现方式： 1.取出记录时，获取当前的version 2.更新时，带上这个version 3.执行更新时，set version = newVersion where version = oldVersion
     *           4.若version不对，就更新失败
     *  仅支持：updateById()、update(entity wrapper)  注意这个wrapper不能复用！
     */
//    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        //可以通过环境变量获取你的mapper路径,这样mapper扫描可以通过配置文件配置了
        scannerConfigurer.setBasePackage("net.mw.*.dao");
        return scannerConfigurer;
    }
}
