-- Stored Procedure to Restore RAP SQL DB's.
-- @author: Avin
-- @Date: 23/05/2016

DECLARE @dbname1 VARCHAR(50)
DECLARE @dbname2 VARCHAR(50)
DECLARE @dbname3 VARCHAR(50)
DECLARE @FileName1 NVARCHAR(255)
DECLARE @FileName2 NVARCHAR(255)
DECLARE @FileName3 NVARCHAR(255)

-- Details of database QASIB2
SET @dbname1 = 'QASIB2'
SET @FileName1 = N'C:\Program Files\Microsoft SQL Server\MSSQL10_50.MSSQLSERVER\MSSQL\Backup\QASIB2.bak' 

-- Details of database QASIB2_ARCH
SET @dbname2 = 'QASIB2_ARCH'
SET @FileName2 = N'C:\Program Files\Microsoft SQL Server\MSSQL10_50.MSSQLSERVER\MSSQL\Backup\QASIB2_ARCH.bak' 

-- Details of database QASIB2_QUEUE
SET @dbname3 = 'QASIB2_QUEUE'
SET @FileName3 = N'C:\Program Files\Microsoft SQL Server\MSSQL10_50.MSSQLSERVER\MSSQL\Backup\QASIB2_QUEUE.bak'


Print 'Restoring database "' + @dbname1 + '"'
RESTORE DATABASE @dbname1 FROM DISK = @FileName1 WITH FILE = 1,   
-- MOVE N'YourDatabase_Data' TO N'C:\MSSQL\Data\$(dbname1).mdf',  
-- MOVE N'YourDatbase_Log' TO N'C:\MSSQL\Data\$(dbname1)_Log.ldf',  
NOUNLOAD, REPLACE,  
STATS = 10
Print '*********************************'
Print ''


Print 'Restoring database "' + @dbname2 + '"'
RESTORE DATABASE @dbname2 FROM DISK = @FileName2 WITH FILE = 1, NOUNLOAD, REPLACE,  
STATS = 10
Print '*********************************'
Print ''

 
Print 'Restoring database "' + @dbname3 + '"'
RESTORE DATABASE @dbname3 FROM DISK = @FileName3 WITH FILE = 1, NOUNLOAD, REPLACE,  
STATS = 10
Print '*********************************'
Print '';

-- GO -- Use this 'GO' command when you are triggering this stored proc directly from SSMS.
