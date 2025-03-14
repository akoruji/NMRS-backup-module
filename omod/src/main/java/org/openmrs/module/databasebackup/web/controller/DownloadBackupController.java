package org.openmrs.module.databasebackup.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.databasebackup.DatabaseBackupTask;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Controller to handle downloading of database backup files.
 */
@Controller
public class DownloadBackupController {

    private final Log log = LogFactory.getLog(getClass());

    @RequestMapping("/module/databasebackup/download.form")
    public void downloadBackup(@RequestParam("fileId") String fileId, HttpServletResponse response) {
        if (!Context.isAuthenticated()) {
            log.warn("Unauthorized access attempt to download backup file: " + fileId);
            return;
        }

        String backupFolderPath = DatabaseBackupTask.getAbsoluteBackupFolderPath();
        File backupFile = new File(backupFolderPath, fileId + ".zip");

        if (!backupFile.exists() || !backupFile.isFile()) {
            log.error("Requested backup file does not exist: " + backupFile.getAbsolutePath());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + backupFile.getName() + "\"");
        response.setContentLength((int) backupFile.length());

        try (InputStream inputStream = new FileInputStream(backupFile);
             OutputStream outputStream = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();

        } catch (IOException e) {
            log.error("Error while downloading backup file: " + fileId, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
