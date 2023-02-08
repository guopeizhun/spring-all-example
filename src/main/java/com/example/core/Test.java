package com.example.core;

import cn.hutool.Hutool;

import cn.hutool.cache.CacheUtil;
import cn.hutool.core.compiler.JavaFileObjectUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.ModifierUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.dfa.SensitiveUtil;
import cn.hutool.extra.compress.CompressUtil;
import cn.hutool.extra.compress.archiver.Archiver;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.extra.tokenizer.TokenizerUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.WorkbookUtil;
import cn.hutool.poi.word.DocUtil;
import cn.hutool.script.ScriptUtil;
import cn.hutool.system.oshi.OshiUtil;
import com.example.core.ExecutorPoolUtil;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.script.CompiledScript;
import javax.script.ScriptException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.invoke.SerializedLambda;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * @Author:letg(pz)
 * @Date: 2023/2/6 13:57
 * @Description:
 */

@Slf4j(topic = "c.test")
public class Test {
    public static void main(String[] args) throws IOException, ScriptException, InterruptedException {
        String containerName = "myContainer";
        ExecutorPoolUtil.initPool(containerName);

        try {
            ThreadGroup group = ExecutorPoolUtil.createThreadGroup("test1");

            ArrayList<Runnable> list = new ArrayList<>();
            Thread countThread = new Thread(group, () -> {
                while (true) {
                    try {
                        Thread.sleep(200);
                        log.info("完成任务：{}", ExecutorPoolUtil.getFinishedCount(containerName));
                        if (ExecutorPoolUtil.getFinishedCount(containerName) >= Long.parseLong(list.size()-1 + "")) {
                            log.info("任务全部完成");
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            list.add(countThread);
            for (int i = 0; i < 1000000; i++) {
                int x = i;
                Thread thread = new Thread(group, () -> System.out.println(x));
                list.add(thread);
            }

//            监控任务完成线程

            ExecutorPoolUtil.exec(list,containerName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ExecutorPoolUtil.shutdown(containerName);
        }

    }
}
