-- Stored Procedure to take RAP SQL DB backups.
-- @author: Avin
-- @Date: 28/04/2016

DECLARE @name VARCHAR(50) -- database name  
DECLARE @path VARCHAR(256) -- path for backup files  
DECLARE @fileName VARCHAR(256) -- filename for backup  
DECLARE @fileDate VARCHAR(20) -- used for file name


-- specify database backup directory
-- SET @name = 'QASIB2' 
SET @path = 'C:\Program Files\Microsoft SQL Server\MSSQL10_50.MSSQLSERVER\MSSQL\Backup\'
-- SET @fileDate = CONVERT(VARCHAR(20), GETDATE(),112);
SET @fileDate = REPLACE(convert(nvarchar(20),GetDate(),120),':','-')
-- 112--> gives date in YYYYMMDD format and 120--> gives yyyy-mm-dd hh:mi:ss(24h) format


-- specify filename format
SELECT @fileDate = REPLACE(convert(nvarchar(20),GetDate(),120),':','-') 


DECLARE db_cursor CURSOR FOR  
SELECT name 
FROM master.dbo.sysdatabases 
WHERE name IN ('QASIB2','QASIB2_ARCH','QASIB2_queue')  -- include these databases
-- WHERE name NOT IN ('master','model','msdb','tempdb')  -- exclude these databases

OPEN db_cursor   
FETCH NEXT FROM db_cursor INTO @name   


WHILE @@FETCH_STATUS = 0   
BEGIN   
       SET @fileName = @name+'_'+@fileDate+'.BAK'  
       BACKUP DATABASE @name TO DISK = @fileName  


       FETCH NEXT FROM db_cursor INTO @name   
END   


CLOSE db_cursor   
DEALLOCATE db_cursor