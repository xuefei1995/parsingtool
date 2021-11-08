package com.xuefei.gui.impl;

import com.xuefei.gui.Window;
import com.xuefei.pojo.Param;
import com.xuefei.service.deal.HNDownloadDealData;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class HNDownloadWindow implements Window {
    @Override
    public void createWindow() {
        JFrame frame = new JFrame("转换DB软件");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createUI(frame);
        frame.setSize(300, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createUI(final JFrame frame) {
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);

        Param param = new Param();

        //获取选择的文件以及输出目录
        createButton1(frame, panel, param);
        createButton2(frame, panel, param);

        //创建生成按钮
        createButton3(frame, panel, param);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void createButton1(JFrame frame, JPanel panel, Param param) {
        JButton button = new JButton("请选择数据所在的文件夹~");
        button.setPreferredSize(new Dimension(180, 45));
        final JLabel label = new JLabel();
        button.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                param.setSrcPath(file.getAbsolutePath());
                label.setText("选择读取的文件夹为: " + file.getName());
            }
        });
        panel.add(button);
        panel.add(label);
    }

    private void createButton2(JFrame frame, JPanel panel, Param param) {
        JButton button = new JButton("请选择输出文件目录~");
        button.setPreferredSize(new Dimension(180, 45));
        final JLabel label = new JLabel();
        button.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                param.setOutPath(file.getAbsolutePath());
                label.setText("选择生成的目录为: " + file.getName());
            }
        });
        panel.add(button);
        panel.add(label);
    }

    private void createButton3(JFrame frame, JPanel panel, Param param) {
        JButton button = new JButton("点击生成文件");
        button.setPreferredSize(new Dimension(180, 45));
        final JLabel label = new JLabel();

        button.addActionListener(event -> {
            if (param.getSrcPath() == null || "".equals(param.getSrcPath())) {
                JOptionPane.showMessageDialog(frame, "请选择文件读取文件！");
                return;
            }
            if (param.getOutPath() == null || "".equals(param.getOutPath())) {
                JOptionPane.showMessageDialog(frame, "请选择文件生成目录！");
                return;
            }
            try {
                new HNDownloadDealData().createData(param);
                JOptionPane.showMessageDialog(frame, "文件生成成功！");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "文件生成失败！");
                e.printStackTrace();
            }
        });

        panel.add(button);
        panel.add(label);
    }
}
