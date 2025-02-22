# NMRS-backup-module
A module developed to backup NMRS tables, stored functions, procedures and views.
Below is a sample README in Markdown format that summarizes our work and provides instructions on building, configuring, and using the backup module.

---

# OpenMRS Database Backup Module

This module provides a self-contained solution for backing up your OpenMRS database. It dumps all database objects including table structures, table data, view definitions, and stored routines (procedures and functions) into an SQL file.

## Features

- **Table Structures and Data:**  
  Automatically dumps all tables with their `CREATE TABLE` statements and data.
  
- **View Definitions:**  
  Retrieves and dumps all views using the `SHOW CREATE VIEW` command.
  
- **Stored Routines:**  
  Dumps stored procedures and functions by looping through available routines and executing `SHOW CREATE PROCEDURE` and `SHOW CREATE FUNCTION`.
  
- **SQL Escaping:**  
  Properly escapes SQL tokens to generate a safe and complete backup file.
  
- **Customizable:**  
  Easily configurable properties for output filename, backup folder, and database connection details.

## Prerequisites

- **Java JDK 8+**  
- **Apache Maven**  
- **MySQL Database** (or a compatible database) with proper JDBC drivers installed.  
- **OpenMRS Instance** for deployment.

## Building the Module

1. **Clone or Download the Module Source**  
   Ensure you have the module source, including the `pom.xml` file.

2. **Open a Terminal/Command Prompt**  
   Navigate to the module's root directory.

3. **Run Maven Build Command:**

   ```bash
   mvn clean install
   ```
   
   This will compile the module, run any tests, and package it as an `.omod` file located in the `target/` directory.

## Configuration

Before using the module, you must configure a set of properties. For example:

- **filename**: Name of the SQL backup file (e.g., `backup_2025-02-22-123456.sql`)
- **folder**: Absolute path to the folder where the backup file should be stored.
- **driver.class**: JDBC driver class (e.g., `com.mysql.jdbc.Driver`)
- **driver.url**: JDBC URL (e.g., `jdbc:mysql://localhost:3306/your_database`)
- **user**: Database username.
- **password**: Database password.

## Usage

Integrate the backup functionality by calling the static method `DbDump.dumpDB(Properties props, boolean showProgress, Class showProgressToClass)` from your backup task or controller. The method will:

1. Dump table structures and data.
2. Dump view definitions.
3. Dump stored routines (procedures and functions).
4. Write the complete backup to the specified file, ensuring foreign key checks are correctly toggled.

### Example Code

```java
Properties props = new Properties();
props.setProperty("filename", "backup_2025-02-22-123456.sql");
props.setProperty("folder", "/path/to/backup/folder/");
props.setProperty("driver.class", "com.mysql.jdbc.Driver");
props.setProperty("driver.url", "jdbc:mysql://localhost:3306/your_database");
props.setProperty("user", "your_db_user");
props.setProperty("password", "your_db_password");

try {
    DbDump.dumpDB(props, false, null);
} catch (Exception e) {
    e.printStackTrace();
}
```

## Deployment

- **Deploy the Module:**  
  Copy the generated `.omod` file from the `target/` directory into the OpenMRS modules directory or upload it via the OpenMRS Administration UI.

- **Restart OpenMRS:**  
  Restart your OpenMRS instance and verify that the module is loaded.

- **Access Backup Functionality:**  
  Trigger the backup via the web interface (if provided) or through a scheduled task that calls the backup method.

## Troubleshooting

- **Connection Issues:**  
  Verify your JDBC URL, username, and password.
  
- **Permission Errors:**  
  Ensure the specified backup folder is writable by the OpenMRS process.

- **Dump Errors:**  
  Review OpenMRS logs for detailed error messages and verify that all required database objects (tables, views, routines) are accessible.

## License

This module is subject to the [OpenMRS Public License Version 1.0](http://license.openmrs.org). See the license for more details.

## Contact

For issues or further assistance, please contact the OpenMRS community or the module maintainer.

---
