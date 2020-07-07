package com.enhinck.db.ui;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author huenbin
 * @date 2020-07-07 09:40
 */
public class ExampleFileFilter extends FileFilter {
    private String description;

    private List<String> ends = new ArrayList<>();

    public void addExtension(String end) {
        ends.add(end);
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory())
            return true;
        for (int i = 0; i < ends.size(); i++) {
            if (f.getName().endsWith(ends.get(i))) {
                return true;
            }
        }



        return false;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
