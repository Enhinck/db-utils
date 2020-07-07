package com.enhinck.db.ui;

import com.enhinck.db.excel.JavaDefineEntity;
import com.enhinck.db.excel.JavaDefineExcelReadUtil;
import com.enhinck.db.excel.JavaFieldEntity;
import com.enhinck.db.freemark.BaseFacotry;
import com.enhinck.db.freemark.FreemarkUtil;
import com.enhinck.db.freemark.mybatisplus.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author HEB
 */
@Slf4j
public class MainFrame extends JFrame {
    private static final long serialVersionUID = 4380988512910634728L;
    private JPanel mainpanel;
    private JLabel excelSelectTitle;
    private JTextField excelSelectText;

    private JButton jbtnSelectExcel;

    private JTextField outputPath;
    private JLabel jlbltips;
    private JButton jbtnSelectOutPut;

    private JButton jbtnCancel;
    private JButton jbtnLogin;
    private JLabel outputTitle;
    private JLabel jlblpwd2;

    private JLabel packageNameTitle;
    private JTextField packageNameText;

    private JComboBox cmb;
    private Image backgroundImage;

    // 创建一个进度条
    final JProgressBar progressBar = new JProgressBar();

    int currentProgress = 0;

    public MainFrame() {
        FreemarkUtil.init();
        // backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("login_select.jpg")).getImage();
        initGUI();
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocation(550, 200);
    }

    private void initGUI() {
        try {
            {
                //	this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("loginico.png")).getImage());
                getContentPane().setLayout(null);
                this.setTitle("代码生成器");
                {
                    mainpanel = new JPanel() {
                        /**
                         *
                         */
                        private static final long serialVersionUID = 8250596394522699662L;

                        @Override
                        protected void paintComponent(Graphics g) {

                            super.paintComponent(g);
                            if (backgroundImage != null) {
                                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                            }
                        }
                    };
                    getContentPane().add(mainpanel, "Center");
                    mainpanel.setLayout(null);
                    mainpanel.setBounds(0, 0, 450, 300);
                    {
                        excelSelectTitle = new JLabel();
                        mainpanel.add(excelSelectTitle);
                        excelSelectTitle.setText("xlsx路径:");
                        excelSelectTitle.setBounds(41, 66, 60, 17);
                    }
                    {
                        excelSelectText = new JTextField();
                        mainpanel.add(excelSelectText);
                        excelSelectText.setText("/Users/huenbin/代码生成模板.xlsx");
                        excelSelectText.setBounds(100, 63, 250, 24);
                    }

                    {
                        jbtnSelectExcel = new JButton("Select");
                        mainpanel.add(jbtnSelectExcel);
                        jbtnSelectExcel.setBounds(350, 63, 80, 24);
                    }


                    //-------------------------------

                    {
                        outputTitle = new JLabel();
                        mainpanel.add(outputTitle);
                        outputTitle.setText("output:");
                        outputTitle.setBounds(41, 110, 46, 17);
                    }

                    {
                        outputPath = new JTextField();
                        outputPath.setText("/Users/huenbin/java/");
                        mainpanel.add(outputPath);
                        outputPath.setBounds(100, 107, 250, 24);
                    }

                    {
                        jbtnSelectOutPut = new JButton("Select");
                        mainpanel.add(jbtnSelectOutPut);
                        jbtnSelectOutPut.setBounds(350, 107, 80, 24);
                    }
                    //-------------------------------


                    {
                        packageNameTitle = new JLabel();
                        mainpanel.add(packageNameTitle);
                        packageNameTitle.setText("package:");
                        packageNameTitle.setBounds(41, 150, 60, 17);
                    }

                    {
                        packageNameText = new JTextField();
                        packageNameText.setText("com.greentown.demo");
                        mainpanel.add(packageNameText);
                        packageNameText.setBounds(105, 147, 250, 24);
                    }


                    {
                        jlblpwd2 = new JLabel();
                        mainpanel.add(jlblpwd2);
                        jlblpwd2.setText("框架:");
                        jlblpwd2.setBounds(41, 180, 60, 17);
                    }

                    {
//						jpwdtxt2 = new JTextField();
//						jpwdtxt2.setText("grid");
//						mainpanel.add(jpwdtxt2);

                        //创建JComboBox
                        cmb = new JComboBox();
                        //向下拉列表中添加一项
                        cmb.addItem("--请选择--");
                        cmb.addItem("IOC-mybatisPlus");
                        cmb.addItem("应用商店-mybatisPlus");
                        // cmb.addItem("IOC-tkMapper");
                        mainpanel.add(cmb);
                        cmb.setBounds(100, 180, 250, 24);
                        //jpwdtxt2.setBounds(100, 150, 136, 24);
                    }


                    {
                        jbtnLogin = new JButton();
                        mainpanel.add(jbtnLogin);
                        jbtnLogin.setText("生成");
                        jbtnLogin.setBounds(180, 210, 100, 24);
                    }
//                    {
//                        jbtnCancel = new JButton();
//                        mainpanel.add(jbtnCancel);
//                        jbtnCancel.setText("CREATE");
//                        jbtnCancel.setBounds(154, 190, 100, 24);
//                    }
                    {
                        jlbltips = new JLabel();
                        mainpanel.add(jlbltips);
                        jlbltips.setText("代码生成器V1.0");
                        jlbltips.setBounds(180, 21, 150, 17);
                    }

                    {

                        progressBar.setBounds(70, 235, 300, 20);


                        // 设置当前进度值
                        progressBar.setValue(currentProgress);

                        // 绘制百分比文本（进度条中间显示的百分数）
                        progressBar.setStringPainted(true);

                        // 添加进度改变通知
                        progressBar.addChangeListener(new ChangeListener() {
                            @Override
                            public void stateChanged(ChangeEvent e) {
                                System.out.println("当前进度值: " + progressBar.getValue() + "; " +
                                        "进度百分比: " + progressBar.getPercentComplete());
                            }
                        });

                        // 添加到内容面板
                        mainpanel.add(progressBar);


                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        addmyaction();
    }

    private void addmyaction() {
        jbtnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                create(evt);
            }
        });

        jbtnSelectExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnSelectOriginalActionPerformed(evt);
            }
        });

        jbtnSelectOutPut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnSelectFolder(evt);
            }
        });


//        jbtnCancel.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                cancel(evt);
//            }
//        });

    }

    private void create(ActionEvent evt) {
        currentProgress = 0;
        String excel = excelSelectText.getText().trim();
        String output = outputPath.getText().trim();
        Integer selectIndex = cmb.getSelectedIndex();

        String packageName = packageNameText.getText().trim();


        if (StringUtils.isBlank(excel)) {
            JOptionPane.showMessageDialog(this, "请选择excel");
            return;
        }
        if (StringUtils.isBlank(output)) {
            JOptionPane.showMessageDialog(this, "请选择output");
            return;
        }
        if (StringUtils.isBlank(packageName)) {
            JOptionPane.showMessageDialog(this, "请填写package");
            return;
        }

        if (selectIndex == 0) {
            JOptionPane.showMessageDialog(this, "请选择框架");
            return;
        }

        FreemarkUtil.setClassPath(output);


        File file = new File(output);
        if (!file.exists()) {
            file.mkdirs();
            log.info("已生成输出路径文件夹{}", output);
        }
        log.info("{}-{}-{}", excel, output, selectIndex);


        if (selectIndex == 1) {
            iocMybatisPlusGenerate(excel, packageName);
        }
        if (selectIndex == 2) {
            shopMybatisPlusGenerate(excel, packageName);
        }

//        // 模拟延时操作进度, 每隔 0.5 秒更新进度
//        new Timer(50, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                if (currentProgress >= 100) {
//                    //currentProgress = 0;
//                    //  progressBar.setString("生成完成");
//                } else {
//                    currentProgress++;
//                    progressBar.setValue(currentProgress);
//                }
//            }
//        }).start();
        try {


        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "\u8FDE\u63A5\u5931\u8D25" + e.getMessage());
        }

    }

    private void shopMybatisPlusGenerate(String excel, String packageName) {
        BaseFacotry.isIoc = false;

        try {
            File excelFile = new File(excel);
            java.util.List<JavaDefineEntity<JavaFieldEntity>> javaDefineEntities = JavaDefineExcelReadUtil.getDataFromExcel(new FileInputStream(excelFile), JavaFieldEntity.class);
            // 设置进度的 最小值 和 最大值
            progressBar.setMinimum(0);
            progressBar.setMaximum(javaDefineEntities.size() * 7 + 1);

            javaDefineEntities.forEach(javaFieldEntityJavaDefineEntity -> {
                javaFieldEntityJavaDefineEntity.setBasePackageName(packageName);
                DOFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                DTOFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                VOFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                MapperFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                ServiceFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                ServiceImplFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                ControllerFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
            });

            MySQLFactory.getInstance().write(javaDefineEntities);
            currentProgress++;
            progressBar.setValue(currentProgress);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    private void iocMybatisPlusGenerate(String excel, String packageName) {
        try {
            File excelFile = new File(excel);
            java.util.List<JavaDefineEntity<JavaFieldEntity>> javaDefineEntities = JavaDefineExcelReadUtil.getDataFromExcel(new FileInputStream(excelFile), JavaFieldEntity.class);
            // 设置进度的 最小值 和 最大值
            progressBar.setMinimum(0);
            progressBar.setMaximum(javaDefineEntities.size() * 7 + 1);

            javaDefineEntities.forEach(javaFieldEntityJavaDefineEntity -> {
                javaFieldEntityJavaDefineEntity.setBasePackageName(packageName);
                DOFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                DTOFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                VOFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                MapperFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                ServiceFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                ServiceImplFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                ControllerFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
            });

            MySQLFactory.getInstance().write(javaDefineEntities);
            currentProgress++;
            progressBar.setValue(currentProgress);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void btnSelectOriginalActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();  //对话框
        fileChooser.setCurrentDirectory(new File(""));//设置当前目录
        fileChooser.setAcceptAllFileFilterUsed(false); //禁用选择 所有文件
        fileChooser.setMultiSelectionEnabled(false);
        ExampleFileFilter filter = new ExampleFileFilter(); //选择文件过滤器
        filter.addExtension("xlsx");
        filter.addExtension("xls");
        filter.setDescription("请选择文档");
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(getContentPane());  //opendialog
        if (returnVal == JFileChooser.APPROVE_OPTION)  //判断是否为打开的按钮
        {
            File selectedFile = fileChooser.getSelectedFile();  //取得选中的文件
            log.info(selectedFile.getPath());

            excelSelectText.setText(selectedFile.getPath());
        }
    }

    private void btnSelectFolder(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();  //对话框
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File(""));//设置当前目录
        fileChooser.setAcceptAllFileFilterUsed(false); //禁用选择 所有文件
        ExampleFileFilter filter = new ExampleFileFilter(); //选择文件过滤器
        filter.setDescription("请选择文件夹");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(getContentPane());  //opendialog
        if (returnVal == JFileChooser.APPROVE_OPTION)  //判断是否为打开的按钮
        {
            File selectedFile = fileChooser.getSelectedFile();  //取得选中的文件
            log.info(selectedFile.getPath());
            outputPath.setText(selectedFile.getPath());
        }
    }


    public static void main(String[] args) {
        new MainFrame();
    }
}