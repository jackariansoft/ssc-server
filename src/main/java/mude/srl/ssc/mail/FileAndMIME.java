/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.mail;

import java.io.File;

/**
 *
 * @author jackarian
 */
public class FileAndMIME {

    private File file = null;
    private String mime = null;
    public static final String MIME_EXCEL = "application/vnd.ms-excel";
    public static final String MIME_PDF = "application/pdf";
    public static final String MIME_ZIP = "application/zip";
    public static final String MIME_TEXT = "text/plain";

    public FileAndMIME(File file, String mime) {
        this.file = file;
        this.mime = mime;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

}
