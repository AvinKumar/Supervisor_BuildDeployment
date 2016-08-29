
DECLARE @dbname1 VARCHAR(50)
DECLARE @dbname2 VARCHAR(50)
DECLARE @FileName1 NVARCHAR(255)
DECLARE @FileName2 NVARCHAR(255)



SET @dbname1 = 'avin1'
SET @FileName1 = N'C:\Program Files\Microsoft SQL Server\MSSQL10_50.MSSQLSERVER\MSSQL\Backup\avin1.bak' 


SET @dbname2 = 'avin2'
SET @FileName2 = N'C:\Program Files\Microsoft SQL Server\MSSQL10_50.MSSQLSERVER\MSSQL\Backup\avin2.bak' 


Print 'Restoring database "' + @dbname1 + '"'
RESTORE DATABASE @dbname1
FROM DISK = @FileName1
WITH FILE = 1,  

NOUNLOAD, REPLACE,  
STATS = 10
Print '*********************************'
Print ''


Print 'Restoring database "' + @dbname2 + '"'
RESTORE DATABASE @dbname2
FROM DISK = @FileName2
WITH FILE = 1,
NOUNLOAD, REPLACE,  
STATS = 10
Print '*********************************'
Print ''

GO
