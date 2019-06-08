package org.projpi.jetCharacters.io;

import java.io.File;

/**
 * Created by Hunter Henrichsen on 02-Jan-17.
 * (c) 2017 Hunter Henrichsen, all rights reserved.
 */
public class InternalFile
{
    private String name, fileName;
    private File file;

    public InternalFile(String name, String fileName, File file)
    {
        this.name = name;
        this.fileName = fileName;
        this.file = file;
    }

    public String getName()
    {
        return name;
    }

    public String getFileName()
    {
        return fileName;
    }

    public File getFile()
    {
        return file;
    }
}
