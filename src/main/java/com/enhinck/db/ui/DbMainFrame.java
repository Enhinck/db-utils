package com.enhinck.db.ui;

import com.enhinck.db.entity.InformationSchemaColumns;
import com.enhinck.db.entity.InformationSchemaTables;
import com.enhinck.db.excel.FieldTypeEnum;
import com.enhinck.db.excel.JavaDefineEntity;
import com.enhinck.db.excel.JavaDefineExcelReadUtil;
import com.enhinck.db.excel.JavaFieldEntity;
import com.enhinck.db.freemark.BaseFacotry;
import com.enhinck.db.freemark.FreemarkUtil;
import com.enhinck.db.freemark.mybatisplus.*;
import com.enhinck.db.freemark.tkmapper.*;
import com.enhinck.db.util.Database;
import com.enhinck.db.util.MysqlDbUtil;
import com.enhinck.db.util.SqlUtil;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

/**
 * @author HEB
 */
@Slf4j
public class DbMainFrame extends JFrame {
    private static final long serialVersionUID = 4380988512910634728L;
    private JPanel mainpanel;

    private JLabel jlbltips;
    //
    private JButton jbtnSelectOutPut;

    // 生成按钮
    private JButton jbtnCreate;
    // 测试数据库连接按钮
    private JButton jbtnTest;
    private JTextField outputPath;
    private JTextField packageNameText;

    private JTextField tableNames;

    private JTextField jdbcAdress;

    private JTextField jdbcUserName;
    private JTextField jdbcPassword;

    private JComboBox jComboBox;


    private Image backgroundImage;
    // 创建一个进度条
    final JProgressBar progressBar = new JProgressBar();

    int currentProgress = 0;

    public DbMainFrame() {
        FreemarkUtil.init();
        // backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("login_select.jpg")).getImage();
        initGUI();
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocation(550, 200);
    }

    public static final int ROW_HIGTH = 30;

    public static final int WIDTH_EMPTY = 5;

    public static final int LEFT_EMPTY = 40;

    public static final int TOP_EMPTY = 30;

    public static final int HIGTH_EMPTY = 5;

    Map<String, JTextField> textFieldHashMap = new HashMap<>();

    Map<Integer, java.util.List<JComponent>> map = new HashMap<>();

    /**
     * row从0行开始
     *
     * @param jComponent
     * @param row
     */
    public void addJComponent(JComponent jComponent, int row) {
        java.util.List<JComponent> jComponents = map.get(row);
        if (jComponents == null) {
            jComponents = new ArrayList<>();
            map.put(row, jComponents);
        }
        int y = TOP_EMPTY + row * ROW_HIGTH + jComponent.getHeight() + HIGTH_EMPTY * row;
        int x = LEFT_EMPTY;
        if (jComponents.size() != 0) {
            for (int i = 0; i < jComponents.size(); i++) {
                x = x + jComponents.get(i).getWidth() + WIDTH_EMPTY;
            }
        }
        // 重新定位位置
        jComponent.setBounds(x, y, jComponent.getWidth(), jComponent.getHeight());
        mainpanel.add(jComponent);
        jComponents.add(jComponent);

        setSize(x + jComponent.getWidth() + 200, y + jComponent.getHeight() + 200);
        mainpanel.setSize(x + jComponent.getWidth() + 150, y + jComponent.getHeight() + 200);
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
                        JLabel title = new JLabel("数据库地址:");
                        title.setSize(80, 20);
                        addJComponent(title, 0);
                        jdbcAdress = new JTextField();
                        jdbcAdress.setText("jdbc:mysql://10.0.5.133:4461/ioc");
                        jdbcAdress.setSize(300, 20);
                        addJComponent(jdbcAdress, 0);
                    }


                    {
                        JLabel title = new JLabel("账号:");
                        title.setSize(80, 20);
                        addJComponent(title, 1);
                        jdbcUserName = new JTextField();
                        jdbcUserName.setText("ssc");
                        jdbcUserName.setSize(250, 20);
                        addJComponent(jdbcUserName, 1);
                    }

                    {
                        JLabel title = new JLabel("密码:");
                        title.setSize(80, 20);
                        addJComponent(title, 2);
                        jdbcPassword = new JTextField();
                        jdbcPassword.setText("Greentown@123");
                        jdbcPassword.setSize(250, 20);
                        addJComponent(jdbcPassword, 2);
                    }

                    {
                        JLabel title = new JLabel("需要生成的表名:");
                        title.setSize(100, 20);
                        addJComponent(title, 3);
                        tableNames = new JTextField();
                        tableNames.setText("tb_event_config");
                        tableNames.setSize(250, 20);
                        addJComponent(tableNames, 3);
                    }


                    {
                        JLabel title = new JLabel("output:");
                        title.setSize(80, 20);
                        addJComponent(title, 4);
                        outputPath = new JTextField("/Users/huenbin/java/");
                        outputPath.setSize(250, 20);
                        addJComponent(outputPath, 4);
                        jbtnSelectOutPut = new JButton("Select");
                        jbtnSelectOutPut.setSize(80, 20);
                        addJComponent(jbtnSelectOutPut, 4);
                    }


                    {
                        JLabel title = new JLabel("package:");
                        title.setSize(80, 20);
                        addJComponent(title, 5);
                        packageNameText = new JTextField("com.greentown.demo");
                        packageNameText.setSize(250, 20);
                        addJComponent(packageNameText, 5);
                    }


                    {
                        JLabel title = new JLabel("框架:");
                        title.setSize(60, 20);
                        addJComponent(title, 6);
                        jComboBox = new JComboBox();
                        jComboBox.setSize(250, 20);
                        //向下拉列表中添加一项
                        jComboBox.addItem("--请选择--");
                        jComboBox.addItem("IOC-mybatisPlus");
                        jComboBox.addItem("应用商店-mybatisPlus");
                        jComboBox.addItem("IOC-tkMapper");
                        addJComponent(jComboBox, 6);
                    }


                    {
                        JLabel title = new JLabel("");
                        title.setSize(60, 20);
                        addJComponent(title, 7);
                        jbtnTest = new JButton("测试连接");
                        jbtnTest.setSize(100, 20);
                        addJComponent(jbtnTest, 7);

                        jbtnCreate = new JButton("生成");
                        jbtnCreate.setSize(100, 20);
                        addJComponent(jbtnCreate, 7);

                    }

                    {
                        jlbltips = new JLabel();
                        mainpanel.add(jlbltips);
                        jlbltips.setText("代码生成器V1.0");
                        jlbltips.setBounds(180, 21, 150, 17);
                    }

                    {


                        progressBar.setSize(300, 20);
                        addJComponent(progressBar, 8);
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


                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        addmyaction();
    }

    private void addmyaction() {
        jbtnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                create(evt);
            }
        });

//        jbtnSelectExcel.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                btnSelectOriginalActionPerformed(evt);
//            }
//        });
//
        jbtnSelectOutPut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnSelectFolder(evt);
            }
        });

        jbtnTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                test(evt);
            }
        });


//        jbtnCancel.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                cancel(evt);
//            }
//        });

    }

    private void test(ActionEvent evt) {
        String url = jdbcAdress.getText().trim();
        String username = jdbcUserName.getText().trim();
        String password = jdbcPassword.getText().trim();
        Database database = new Database(url, username, password);
        Connection connection = database.getConnection();
        try {
            if (connection != null) {
                JOptionPane.showMessageDialog(this, "连接成功");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void create(ActionEvent evt) {
        currentProgress = 0;
        String output = outputPath.getText().trim();
        Integer selectIndex = jComboBox.getSelectedIndex();

        String packageName = packageNameText.getText().trim();

        String url = jdbcAdress.getText().trim();
        String username = jdbcUserName.getText().trim();
        String password = jdbcPassword.getText().trim();



//        if (StringUtils.isBlank(excel)) {
//            JOptionPane.showMessageDialog(this, "请选择excel");
//            return;
//        }
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
        log.info("{}-{}", output, selectIndex);


        Database database = new Database(url, username, password);

        String tableName = tableNames.getText().trim();

        String[] tables = tableName.split(",");

        java.util.List<JavaDefineEntity<JavaFieldEntity>> javaDefineEntities = new ArrayList<>();


        Connection connection = database.getConnection();
        for (String table : tables) {
            Map<String, InformationSchemaColumns> tableColumns = MysqlDbUtil.getColumnsByTableName(table, connection);
            List<InformationSchemaTables> informationSchemaTables = MysqlDbUtil.getTables(connection, table);
            InformationSchemaTables informationSchemaTable = informationSchemaTables.get(0);
            String describe = informationSchemaTable.getTableComment();
            JavaDefineEntity javaDefineEntity = new JavaDefineEntity();
            javaDefineEntity.setTableName(table);
            javaDefineEntity.setDescribe(describe);
            if (table.startsWith("tb_")) {
                table = table.substring(3);
            }
            String javaName = SqlUtil.toJavaName(table);
            javaDefineEntity.setJavaName(javaName);

            tableColumns.forEach((key, tableColumn) -> {

                JavaFieldEntity javaFieldEntity = new JavaFieldEntity();
                javaFieldEntity.setName(tableColumn.getColumnName());
                javaFieldEntity.setDescribe(tableColumn.getColumnComment());

                String columnType = tableColumn.getColumnType();
                if (columnType.toLowerCase().contains("text")) {
                    javaFieldEntity.setFieldType(FieldTypeEnum.STRING);
                    javaFieldEntity.setLength(1000);
                }else  if (columnType.toLowerCase().contains("varchar")) {
                    javaFieldEntity.setFieldType(FieldTypeEnum.STRING);
                    javaFieldEntity.setLength(tableColumn.getCharacterMaximumLength());
                }else  if (columnType.toLowerCase().contains("datetime")) {
                    javaFieldEntity.setFieldType(FieldTypeEnum.DATE);
                    javaFieldEntity.setLength(tableColumn.getCharacterMaximumLength());
                }else  if (columnType.toLowerCase().contains("bigint")) {
                    javaFieldEntity.setFieldType(FieldTypeEnum.LONG);
                    javaFieldEntity.setLength(tableColumn.getCharacterMaximumLength());
                }else  if (columnType.toLowerCase().contains("int")) {
                    javaFieldEntity.setFieldType(FieldTypeEnum.INTEGER);
                    javaFieldEntity.setLength(tableColumn.getCharacterMaximumLength());
                }else  {
                    javaFieldEntity.setFieldType(FieldTypeEnum.STRING);
                    javaFieldEntity.setLength(tableColumn.getCharacterMaximumLength());
                }
                javaDefineEntity.addField(javaFieldEntity);

            });

            javaDefineEntities.add(javaDefineEntity);

        }


        if (selectIndex == 1) {
            iocMybatisPlusGenerate(javaDefineEntities, packageName);
        }
        if (selectIndex == 2) {
            shopMybatisPlusGenerate(javaDefineEntities, packageName);
        }

        if (selectIndex == 3) {
            iocTkmapperGenerate(javaDefineEntities, packageName);
        }

        try {


        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "\u8FDE\u63A5\u5931\u8D25" + e.getMessage());
        }

    }

    public static final String IOC_GMT_CREATE = "gmtCreate";
    public static final String IOC_GMT_MODIFY = "gmtModify";

    private void addIocDateColumn(java.util.List<JavaDefineEntity<JavaFieldEntity>> javaDefineEntities) {

        javaDefineEntities.forEach(javaFieldEntityJavaDefineEntity -> {
            java.util.List<JavaFieldEntity> javaFieldEntityList = javaFieldEntityJavaDefineEntity.getList();

            Set<String> columns = new HashSet<>();
            javaFieldEntityList.forEach(javaFieldEntity -> columns.add(javaFieldEntity.javaFieldName()));

            if (!columns.contains(IOC_GMT_CREATE)) {
                JavaFieldEntity javaFieldEntity = new JavaFieldEntity();
                javaFieldEntity.setName(IOC_GMT_CREATE);
                javaFieldEntity.setDescribe("创建日期");
                javaFieldEntity.setFieldType(FieldTypeEnum.DATE);
                javaFieldEntityList.add(javaFieldEntity);
            }
            if (!columns.contains(IOC_GMT_MODIFY)) {
                JavaFieldEntity javaFieldEntity = new JavaFieldEntity();
                javaFieldEntity.setName(IOC_GMT_MODIFY);
                javaFieldEntity.setDescribe("修改日期");
                javaFieldEntity.setFieldType(FieldTypeEnum.DATE);
                javaFieldEntityList.add(javaFieldEntity);
            }


        });

    }


    private void shopMybatisPlusGenerate(java.util.List<JavaDefineEntity<JavaFieldEntity>> javaDefineEntities, String packageName) {
        BaseFacotry.isIoc = false;

        try {

            // 设置进度的 最小值 和 最大值
            progressBar.setMinimum(0);
            progressBar.setMaximum(javaDefineEntities.size() * 7);

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


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void iocMybatisPlusGenerate(java.util.List<JavaDefineEntity<JavaFieldEntity>> javaDefineEntities, String packageName) {
        try {
            // 设置进度的 最小值 和 最大值
            progressBar.setMinimum(0);
            progressBar.setMaximum(javaDefineEntities.size() * 7 );

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

            addIocDateColumn(javaDefineEntities);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void iocTkmapperGenerate(java.util.List<JavaDefineEntity<JavaFieldEntity>> javaDefineEntities, String packageName) {
        try {
            // 设置进度的 最小值 和 最大值
            progressBar.setMinimum(0);
            progressBar.setMaximum(javaDefineEntities.size() * 9 );

            javaDefineEntities.forEach(javaFieldEntityJavaDefineEntity -> {
                javaFieldEntityJavaDefineEntity.setBasePackageName(packageName);
                TkDOFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                DTOFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                VOFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                TkMapperFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                TkServiceFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                TkServiceImplFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                TkControllerFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);

                QueryDTOFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
                QueryVOFactory.getInstance().write(javaFieldEntityJavaDefineEntity);
                currentProgress++;
                progressBar.setValue(currentProgress);
            });

            addIocDateColumn(javaDefineEntities);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void btnSelectFolder(ActionEvent evt) {
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
        new DbMainFrame();
    }
}