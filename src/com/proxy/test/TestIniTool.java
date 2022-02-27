package com.proxy.test;
import org.dtools.ini.*;

import java.io.File;
import java.io.IOException;

public class TestIniTool {

    public static void main(String[] args) throws IOException {

        IniFile iniFile=new BasicIniFile();
        File file=new File("c:\\test_config.ini");
        IniFileReader reader=new IniFileReader(iniFile,file);

        IniFileWriter writer=new IniFileWriter(iniFile,file);

        IniSection iniSection=iniFile.addSection("local");

        IniItem iniItem=new IniItem("host");
        iniItem.setValue("127.0.0.1");

        iniSection.addItem(iniItem);
        writer.write();


    }
}
