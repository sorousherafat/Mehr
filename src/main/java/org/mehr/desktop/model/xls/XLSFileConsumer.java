package org.mehr.desktop.model.xls;

import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class XLSFileConsumer implements Consumer<File> {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void accept(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
            POIFSFileSystem fileSystem = new POIFSFileSystem(fileInputStream);
            InputStream documentInputStream = fileSystem.createDocumentInputStream("Workbook");
            HSSFRequest request = new HSSFRequest();
            XLSListener listener = new XLSListener();
            request.addListener(listener, XLSListener.sid);
            HSSFEventFactory factory = new HSSFEventFactory();
            factory.processEvents(request, documentInputStream);
            System.out.printf("total rows: %d\n", listener.getRowCount());
        } catch (Exception e) {
            logger.severe(e.toString());
            System.exit(1);
        }
    }
}
